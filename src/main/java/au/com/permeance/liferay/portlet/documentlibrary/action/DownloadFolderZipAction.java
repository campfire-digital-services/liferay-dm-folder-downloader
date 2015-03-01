/**
 * Copyright (C) 2013-2015 Permeance Technologies
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 */

package au.com.permeance.liferay.portlet.documentlibrary.action;

import au.com.permeance.liferay.portlet.documentlibrary.service.DLFolderExportZipServiceUtil;
import au.com.permeance.liferay.portlet.util.DownloadFolderZipPropsValues;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.struts.BaseStrutsPortletAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.portlet.PortletConfig;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;


/**
 * Download Folder ZIP Action.
 * 
 * This action downloads the contents of a folder (including sub-folders and files) as a ZIP file.
 * 
 * @author Tim Telcik <tim.telcik@permeance.com.au>
 * @author Chun Ho <chun.ho@permeance.com.au>
 */
public class DownloadFolderZipAction extends BaseStrutsPortletAction {

    private static final Log s_log = LogFactoryUtil.getLog(DownloadFolderZipAction.class);

    private static final String FILE_EXT_SEP = FilenameUtils.EXTENSION_SEPARATOR_STR;
    private static final String ZIP_FILE_EXT_NAME = "zip";
    private static final String ZIP_FILE_EXT = FILE_EXT_SEP + ZIP_FILE_EXT_NAME;

    
    @Override
    public void serveResource(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig,
            ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {

        long repositoryId = ParamUtil.getLong(resourceRequest, "repositoryId");
        long folderId = ParamUtil.getLong(resourceRequest, "folderId");

        try {

            downloadFolder(resourceRequest, resourceResponse);

        } catch (Exception e) {

            String msg = "Error downloading folder " + folderId + " from repository " + repositoryId + " : " + e.getMessage();
            s_log.error(msg, e);
            
            SessionErrors.add(resourceRequest, e.getClass().getName());
            resourceResponse.setProperty(ResourceResponse.HTTP_STATUS_CODE, "" + HttpStatus.SC_MOVED_TEMPORARILY);
            resourceResponse.addProperty("Location", resourceResponse.createRenderURL().toString());

        }
    }

    
    protected void downloadFolder(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {

        ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
        long groupId = themeDisplay.getScopeGroupId();
        long repositoryId = ParamUtil.getLong(resourceRequest, "repositoryId");
        long folderId = ParamUtil.getLong(resourceRequest, "folderId");
        Folder folder = DLAppServiceUtil.getFolder(folderId);
        ServiceContext serviceContext = ServiceContextFactory.getInstance(Folder.class.getName(), resourceRequest);

        PermissionChecker permissionChecker = PermissionThreadLocal.getPermissionChecker();

        if (!folder.containsPermission(permissionChecker, ActionKeys.VIEW)) {
        	throw new PrincipalException();
        }
        
        File tempZipFile = null;

        try {
        	
        	tempZipFile = createTempZipFile();
            DLFolderExportZipServiceUtil.exportFolderToZipFile(groupId, repositoryId, folderId, serviceContext, tempZipFile);
            String downloadZipFileName = folder.getName() + ZIP_FILE_EXT;
            sendZipFile(resourceRequest, resourceResponse, tempZipFile, downloadZipFileName);

        } catch (Exception e) {
        	
        	String msg = "Error downloading folder " + folderId 
        				+ " from repository " + repositoryId
        				+ " : " + e.getMessage();
        	
        	s_log.error( msg ,e );
        	
        	throw new PortalException( msg, e ); 
        	
        } finally {
        	
        	safeDeleteFile(tempZipFile);
        	tempZipFile = null;
            
        }
    }

    
    protected void sendZipFile(ResourceRequest resourceRequest, ResourceResponse resourceResponse, File zipFile, String zipFileName)
            throws Exception {

        FileInputStream zipFileInputStream = null;

        try {

            if (StringUtils.isEmpty(zipFileName)) {
                zipFileName = FilenameUtils.getBaseName(zipFile.getName());
            }

            String zipFileMimeType = MimeTypesUtil.getContentType(zipFileName);
            resourceResponse.setContentType(zipFileMimeType);

            int folderDownloadCacheMaxAge = DownloadFolderZipPropsValues.DL_FOLDER_DOWNLOAD_CACHE_MAX_AGE;
            if (folderDownloadCacheMaxAge > 0) {
                String cacheControlValue = "max-age=" + folderDownloadCacheMaxAge + ", must-revalidate";
                resourceResponse.addProperty(HttpHeaders.CACHE_CONTROL, cacheControlValue);
            }

            String contentDispositionValue = "attachment; filename=\"" + zipFileName + "\"";
            resourceResponse.addProperty(HttpHeaders.CONTENT_DISPOSITION, contentDispositionValue);

            // NOTE: java.io.File may return a length of 0 (zero) for a valid file
            // @see java.io.File#length()
            long zipFileLength = zipFile.length();
            if ((zipFileLength > 0L) && (zipFileLength < (long)Integer.MAX_VALUE)) {
                resourceResponse.setContentLength((int) zipFileLength);
            }

            zipFileInputStream = new FileInputStream(zipFile);
            OutputStream responseOutputStream = resourceResponse.getPortletOutputStream();
            long responseByteCount = IOUtils.copy(zipFileInputStream, responseOutputStream);
            responseOutputStream.flush();
            responseOutputStream.close();
            zipFileInputStream.close();
            zipFileInputStream = null;

            if (s_log.isDebugEnabled()) {
                s_log.debug("sent " + responseByteCount + " byte(s) for ZIP file " + zipFileName);
            }

        } catch (Exception e) {

        	String name = StringPool.BLANK;
        	if (zipFile != null) {
        		name = zipFile.getName();
        	}
        	
            String msg = "Error sending ZIP file " + name + " : " + e.getMessage();
            s_log.error(msg);
            throw new PortalException(msg, e);

        } finally {

        	if (zipFileInputStream != null) {
            	try {
					zipFileInputStream.close();
					zipFileInputStream = null;
				} catch (Exception e) {
	                String msg = "Error closing ZIP input stream : " + e.getMessage();
	                s_log.error(msg);
				}
        	}

        }
    }
    
    
    private File createTempZipFile() throws IOException {
    	
    	String tempFilePrefix = PortalUUIDUtil.generate();
    	String tempFileSuffix = ZIP_FILE_EXT;
    	File tempFile = File.createTempFile(tempFilePrefix, tempFileSuffix);
    	return tempFile;
    }
    
    
    private void safeDeleteFile( File targetFile ) {
    	
    	if (targetFile != null) {
        	try {
                if (targetFile.exists()) {
                    if (!targetFile.delete()) {
                    	targetFile.deleteOnExit();
                    }
                }
        	} catch (Exception e) {
                String msg = "Error safely deleting file " + targetFile + " : " + e.getMessage();
                s_log.error(msg);
        	} finally {
                if (targetFile.exists()) {
                	targetFile.deleteOnExit();
                }
        	}
    	}
    }

}
