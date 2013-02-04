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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;


/**
 * Document Library Folder Export ZIP Service Utility.
 * 
 * @author Tim Telcik <tim.telcik@permeance.com.au>
 */
public class DLFolderExportZipServiceUtil {

    private static final String SERVLET_CONTEXT_NAME = "liferay-dl-folder-hook";

    private static final String SERVICE_FIELD_NAME = "_service";

    // TODO: wait for Liferay patch to fix issues between PACL and ReferenceRegistry:
    // [RuntimeChecker:256] Attempted to access declared members
    // [ReferenceRegistry:42] Not allowed to get field _service for class
    // au.com.permeance.liferay.portlet.documentlibrary.service.DLFolderExportZipServiceUtil

    
    public static void exportFolderToZipFile(long groupId, long repositoryId, long folderId, ServiceContext serviceContext, String zipFileName)
    		throws PortalException, SystemException 
    {
        DLFolderExportZipHelper.exportFolderToZipFile(groupId, repositoryId, folderId, serviceContext, zipFileName);
    }

    
    public static void exportFolderToZipFile(
    		long groupId, long repositoryId, long folderId, ServiceContext serviceContext, java.io.File zipFile) 
    	throws PortalException, SystemException 
    {
    	DLFolderExportZipHelper.exportFolderToZipFile(groupId, repositoryId, folderId, serviceContext, zipFile);
    }

    
    public static DLFolderExportZipService getService() {

        if (_service == null) {

            // NOTE: PortalBeanLocatorUtil does not resolve bean from hook applicationContext
            // _service = (DLFolderExportService)PortalBeanLocatorUtil.locate(DLFolderExportService.class.getName());

            // NOTE: PortletBeanLocatorUtil requires hook web app context name to resolve bean
            _service = (DLFolderExportZipService) PortletBeanLocatorUtil.locate(
            		SERVLET_CONTEXT_NAME, DLFolderExportZipService.class.getName());

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

    
    private static DLFolderExportZipService _service;

}
