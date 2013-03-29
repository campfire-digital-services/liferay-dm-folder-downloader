/**
 * Copyright (C) 2013 Permeance Technologies
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

package au.com.permeance.liferay.portlet.documentlibrary.service.impl;

import au.com.permeance.liferay.util.zip.ZipWriter;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;


/**
 * Document Library Folder Export Helper.
 * 
 * This helper provides functions to export folders.
 * 
 * @author Tim Telcik <tim.telcik@permeance.com.au>
 * @author Chun Ho <chun.ho@permeance.com.au>
 * 
 * @see FileUtil
 * @see StreamUtil
 */
public class DLFolderExportZipHelper {

    private static final Log s_log = LogFactoryUtil.getLog(DLFolderExportZipHelper.class);

    
    public static void exportFolderToZipFile(
    		long groupId, long repositoryId, long folderId, ServiceContext serviceContext, String zipFileName) 
    	throws PortalException, SystemException
   	{
    	
    	try {
    		
    		java.io.File zipFile = new java.io.File(zipFileName);
        	exportFolderToZipFile(groupId, repositoryId, folderId, serviceContext, zipFile);
        	
    	} catch (Exception e) {
    		
            String msg = "Error exporting folder " + folderId 
            		+ " from repository " + repositoryId 
            		+ " to ZIP file " + zipFileName 
            		+ " : " + e.getMessage();
    		
    		s_log.error( msg, e );
    		
            if (e instanceof PortalException) {
                throw (PortalException) e;

            } else if (e instanceof SystemException) {
                throw (SystemException) e;
            }
    	}
   	}
    
    	
    public static void exportFolderToZipFile(
    		long groupId, long repositoryId, long folderId, ServiceContext serviceContext, File zipFile) 
    	throws PortalException, SystemException 
    {

        ZipWriter zipWriter = null;

        try {

            zipWriter = new ZipWriter(zipFile);

            exportFolderToZipWriter(groupId, repositoryId, folderId, serviceContext, zipWriter);
            
            zipWriter.close();

        } catch (Exception e) {

        	String fileName = zipFile.getName();
        	
            String msg = "Error exporting folder " + folderId 
            		+ " from repository " + repositoryId 
            		+ " to ZIP file " + fileName 
            		+ " : " + e.getMessage();

            s_log.error(msg, e);

            if (e instanceof PortalException) {
                throw (PortalException) e;

            } else if (e instanceof SystemException) {
                throw (SystemException) e;
            }
        }
    }

    
    /**
     * Export folder to ZIP writer.
     * 
     * @param groupId group id
     * @param repositoryId source repository containing folder to export
     * @param folderId source folder to export
     * @param serviceContext service context
     * @param zipWriter destination ZIP writer
     * 
     * @throws PortalException
     * @throws SystemException
     * @throws RemoteException
     */
    public static void exportFolderToZipWriter(
    		long groupId, long repositoryId, long folderId, ServiceContext serviceContext, ZipWriter zipWriter) 
    	throws PortalException, SystemException 
    {

        try {

            Folder folder = DLAppServiceUtil.getFolder(folderId);

            exportFolderToZipWriter(groupId, repositoryId, folder, StringPool.BLANK, serviceContext, zipWriter);

        } catch (Exception e) {

            String zipWriterLabel = zipWriter.getPath();

            String msg = "Error exporting folder " + folderId 
            		+ " from repository " + repositoryId 
            		+ " to ZIP writer " + zipWriterLabel 
            		+ " : " + e.getMessage();

            s_log.error(msg, e);

            if (e instanceof PortalException) {
                throw (PortalException) e;

            } else if (e instanceof SystemException) {
                throw (SystemException) e;
            }
        }
    }

    
    /**
     * Export folder to ZIP writer.
     * 
     * @param groupId group id
     * @param repositoryId source repository containing folder to export
     * @param folder source folder to export
     * @param folderPath source folder path to export
     * @param serviceContext service context
     * @param zipWriter destination ZIP writer
     * 
     * @throws PortalException
     * @throws SystemException
     * @throws RemoteException
     */    
    public static void exportFolderToZipWriter(
    		long groupId, long repositoryId, Folder folder, String folderPath, ServiceContext serviceContext, ZipWriter zipWriter) 
    	throws PortalException, SystemException 
    {

        // Export file entries in folder to ZIP writer

        List<FileEntry> fileEntryList = DLAppServiceUtil.getFileEntries(folder.getRepositoryId(), folder.getFolderId(), QueryUtil.ALL_POS,
                QueryUtil.ALL_POS, null);

        for (FileEntry fileEntry : fileEntryList) {
            try {
                exportFileEntryToZipWriter(fileEntry, folderPath, zipWriter);
            } catch (Exception e) {
            	String msg = "Error exporting file entry to ZIP file : " + e.getMessage();
            	s_log.error(msg, e);
            	throw new PortalException(msg, e);
            }
        }

        
        // Export sub-folders in folder to ZIP writer

        List<Folder> subFolderList = DLAppServiceUtil.getFolders(folder.getRepositoryId(), folder.getFolderId(), QueryUtil.ALL_POS,
                QueryUtil.ALL_POS, null);

        for (Folder subFolder : subFolderList) {
            String subFolderName = subFolder.getName();
            String subFolderPath = folderPath + subFolderName + StringPool.FORWARD_SLASH;
            exportFolderToZipWriter(groupId, repositoryId, subFolder, subFolderPath, serviceContext, zipWriter);
        }
    }

    
    /**
     * Adds a file entry as a ZIP entry.
     *
     * @param fileEntry file entry
     * @param folderPath folder path
     * @param zipWriter ZIP writer
     * 
     * @throws PortalException
     * @throws SystemException
     * @throws IOException
     */
    public static void exportFileEntryToZipWriter(FileEntry fileEntry, String folderPath, ZipWriter zipWriter) 
    		throws PortalException, SystemException, IOException 
    {

        InputStream fileInputStream = null;
        try {

            String zipEntryName = buildZipEntryName(fileEntry, folderPath, zipWriter);
            fileInputStream = fileEntry.getContentStream();
            zipWriter.addEntry(zipEntryName, fileInputStream);

        } catch (Exception e) {

            String zipWriterLabel = zipWriter.getPath();

            String msg = "Error exporting file entry " + fileEntry + " to ZIP writer " + zipWriterLabel + " : " + e.getMessage();

            s_log.error(msg, e);

            if (e instanceof PortalException) {
                throw (PortalException) e;
                
            } else if (e instanceof SystemException) {
                throw (SystemException) e;
                
            } else if (e instanceof IOException) {
                throw (IOException) e;
                
            } else {
                throw new PortalException( msg, e );
            }
            
        } finally {
            try {
                if (fileInputStream != null) {
                	fileInputStream.close();
                	fileInputStream = null;
                }
            } catch (Exception e) {
            }
        }
    }

    
    /**
     * Returns a ZIP entry name for the file entry.
     * 
     * @param fileEntry file entry
     * @param folderPath file path
     * @param zipWriter ZIP writer
     * 
     * @return ZIP entry name
     * 
     * @throws SystemException
     * @throws PortalException
     */
    public static String buildZipEntryName(FileEntry fileEntry, String folderPath, ZipWriter zipWriter) 
    		throws SystemException, PortalException 
    {

        // Use file entry title as file name
        String fileEntryBaseName = fileEntry.getTitle();

        // normalize base name by stripping extension and replacing non-alphanum chars with underscore
        fileEntryBaseName = FilenameUtils.getBaseName(fileEntryBaseName);
        fileEntryBaseName = fileEntryBaseName.replaceAll("\\W+", "_");

        // build zip entry name
        String zipEntryName = folderPath + fileEntryBaseName + FilenameUtils.EXTENSION_SEPARATOR_STR + fileEntry.getExtension();

        if (zipWriter.hasAllocatedPath(zipEntryName)) {
            String oldZipEntryName = zipEntryName;
            int counter = 1;
            while (true) {
                zipEntryName = folderPath + fileEntryBaseName + StringPool.OPEN_BRACKET + counter + StringPool.CLOSE_BRACKET
                        + FilenameUtils.EXTENSION_SEPARATOR_STR + fileEntry.getExtension();
                if (!zipWriter.hasAllocatedPath(zipEntryName)) {
                    break;
                }
                counter++;
            }

            if (s_log.isDebugEnabled()) {
                s_log.debug(oldZipEntryName + " already exists in ZIP file, renaming to " + zipEntryName);
            }
        }

        return zipEntryName;
    }

}
