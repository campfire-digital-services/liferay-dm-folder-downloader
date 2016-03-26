/**
 * Copyright (C) 2013-present Permeance Technologies. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.service.ServiceContext;


/**
 * Document Library Folder Export ZIP Service.
 * 
 * @author Tim Telcik <tim.telcik@permeance.com.au>
 */
public interface DLFolderExportZipService {
	
    public void exportFolderToZipFile(
    		long groupId, long repositoryId, long folderId, ServiceContext serviceContext, String zipFileName)
    	throws PortalException, SystemException;
    
    
    public void exportFolderToZipFile(
    		long groupId, long repositoryId, long folderId, ServiceContext serviceContext, java.io.File zipFile)
    	throws PortalException, SystemException;    

}
