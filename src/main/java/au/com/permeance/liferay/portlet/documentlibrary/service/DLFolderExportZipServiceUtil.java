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

package au.com.permeance.liferay.portlet.documentlibrary.service;

import au.com.permeance.liferay.portlet.documentlibrary.service.impl.DLFolderExportZipHelper;
import au.com.permeance.liferay.portlet.util.HookPropsValues;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.io.File;

import org.apache.commons.lang.StringUtils;


/**
 * Document Library Folder Export ZIP Service Utility.
 * 
 * @author Tim Telcik <tim.telcik@permeance.com.au>
 */
public class DLFolderExportZipServiceUtil {

    private static final String SERVICE_FIELD_NAME = "_service";
    
    private static DLFolderExportZipService _service;

    
    public static void exportFolderToZipFile(long groupId, long repositoryId, long folderId, ServiceContext serviceContext, String zipFileName)
    		throws PortalException, SystemException 
    {
        DLFolderExportZipHelper.exportFolderToZipFile(groupId, repositoryId, folderId, serviceContext, zipFileName);
    }

    
    public static void exportFolderToZipFile(
    		long groupId, long repositoryId, long folderId, ServiceContext serviceContext, File zipFile) 
    	throws PortalException, SystemException 
    {
    	DLFolderExportZipHelper.exportFolderToZipFile(groupId, repositoryId, folderId, serviceContext, zipFile);
    }

    
    public static DLFolderExportZipService getService() {

        if (_service == null) {
        	String servletContextName = HookPropsValues.DL_FOLDER_DOWNLOAD_SERVLET_CONTEXT_NAME;
        	if (StringUtils.isEmpty(servletContextName)) {
        		throw new IllegalStateException("Servlet context name is undefined");
        	}
        	String beanName = DLFolderExportZipService.class.getName();
            _service = (DLFolderExportZipService) PortletBeanLocatorUtil.locate(servletContextName, beanName);
            ReferenceRegistry.registerReference(DLFolderExportZipServiceUtil.class, SERVICE_FIELD_NAME);
        }

        return _service;
    }

    
    /**
     * @deprecated
     */
    @Deprecated
    public void setService(DLFolderExportZipService service) {
    }

}
