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
package au.com.permeance.liferay.portlet.util;

import au.com.permeance.liferay.portlet.kernel.util.ExtPropsKeys;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;


/**
 * Extension Property Values.
 * 
 * NOTE: The ExtPropsKeys interface and ExtPropValues class is based on Liferay's PropsKeys and
 * PropsValues approach to property management.
 * 
 * @author Tim Telcik <tim.telcik@permeance.com.au>
 * 
 * @see ExtPropsKeys
 * @see PropsValues
 */
public class ExtPropsValues {

    public static final int DL_FOLDER_DOWNLOAD_CACHE_MAX_AGE 
    	= GetterUtil.getInteger(PropsUtil.get(ExtPropsKeys.DL_FOLDER_DOWNLOAD_CACHE_MAX_AGE));
    
    public static final boolean DL_FOLDER_DOWNLOAD_IGNORE_ERRORS 
		= GetterUtil.getBoolean(PropsUtil.get(ExtPropsKeys.DL_FOLDER_DOWNLOAD_IGNORE_ERRORS),true);

}
