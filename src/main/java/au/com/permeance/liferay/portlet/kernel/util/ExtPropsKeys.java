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
package au.com.permeance.liferay.portlet.kernel.util;

import com.liferay.portal.kernel.util.PropsKeys;


/**
 * Extension Property Keys.
 * 
 * NOTE: The ExtPropsKeys interface and ExtPropValues class
 *       is based on Liferay's PropsKeys and PropsValues approach to property management.
 * 
 * @author Tim Telcik <tim.telcik@permeance.com.au>
 * 
 * @see PropsKeys
 */
public interface ExtPropsKeys {
	
    public static final String DL_FOLDER_DOWNLOAD_CACHE_MAX_AGE = "dl.folder.download.cache.max.age";
    
    public static final String DL_FOLDER_DOWNLOAD_IGNORE_ERRORS = "dl.folder.download.ignore-errors";
    
}
