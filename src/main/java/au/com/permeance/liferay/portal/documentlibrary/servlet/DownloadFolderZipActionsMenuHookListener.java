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
package au.com.permeance.liferay.portal.documentlibrary.servlet;

import au.com.permeance.liferay.portlet.kernel.util.HookSysPropsKeys;
import au.com.permeance.liferay.portlet.util.DownloadFolderZipPropsValues;
import au.com.permeance.liferay.portlet.util.StringUtilHelper;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * Download Folder ZIP Action Menu Hook Listener.
 * 
 * @author Chun Ho <chun.ho@permeance.com.au>
 * @author Tim Telcik <tim.telcik@permeance.com.au>
 */
public class DownloadFolderZipActionsMenuHookListener implements ServletContextListener {

    private static final Log LOG = LogFactoryUtil.getLog(DownloadFolderZipActionsMenuHookListener.class);
	

    @Override
    public void contextInitialized(ServletContextEvent event) {
    	LOG.debug("contextInitialized");
        startApplication();
    }


    @Override
    public void contextDestroyed(ServletContextEvent event) {
    	LOG.debug("contextDestroyed");
        stopApplication();
    }
    

    public static void startApplication() {
    	
    	System.out.println("startApplication");
    	
    	String newMenuItemsStr = buildNewMenuItemsDelimString();
    	if (LOG.isDebugEnabled()) {
        	LOG.debug("newMenuItemsStr: " + newMenuItemsStr);
    	}

    	String curMenuItemsStr = buildCurrentMenuItemsDelimString();
    	if (LOG.isDebugEnabled()) {
        	LOG.debug("curMenuItemsStr: " + curMenuItemsStr);
    	}

        String mergedMenuItemsStr = StringUtilHelper.addDelimItems( curMenuItemsStr, newMenuItemsStr, StringPool.COMMA );
        LOG.debug("mergedMenuItemsStr: " + mergedMenuItemsStr);
        
        System.setProperty( HookSysPropsKeys.LIFERAY_DL_FOLDER_ACTIONS_MENU_EXT, mergedMenuItemsStr );
        LOG.info(HookSysPropsKeys.LIFERAY_DL_FOLDER_ACTIONS_MENU_EXT + ": " + System.getProperty(HookSysPropsKeys.LIFERAY_DL_FOLDER_ACTIONS_MENU_EXT));
    }

    
    public static void stopApplication() {
    	
    	System.out.println("stopApplication");
    	
    	String newMenuItemsStr = buildNewMenuItemsDelimString();
    	if (LOG.isDebugEnabled()) {
        	LOG.debug("newMenuItemsStr: " + newMenuItemsStr);
    	}

    	String curMenuItemsStr = buildCurrentMenuItemsDelimString();
    	if (LOG.isDebugEnabled()) {
        	LOG.debug("curMenuItemsStr: " + curMenuItemsStr);
    	}
        
        String mergedMenuItemsStr = StringUtilHelper.removeDelimItems( curMenuItemsStr, newMenuItemsStr, StringPool.COMMA );
        LOG.debug("mergedMenuItemsStr: " + mergedMenuItemsStr);
        
        System.setProperty( HookSysPropsKeys.LIFERAY_DL_FOLDER_ACTIONS_MENU_EXT, mergedMenuItemsStr );
        LOG.info(HookSysPropsKeys.LIFERAY_DL_FOLDER_ACTIONS_MENU_EXT + ": " + System.getProperty(HookSysPropsKeys.LIFERAY_DL_FOLDER_ACTIONS_MENU_EXT));
    }
    
    
    private static String buildNewMenuItemsDelimString() {
    	String[] newMenuItems = DownloadFolderZipPropsValues.DL_FOLDER_DOWNLOAD_ACTIONS_MENU_EXT;
    	String newMenuItemsStr = StringUtil.merge(newMenuItems);
    	if (LOG.isDebugEnabled()) {
        	LOG.debug("newMenuItems: " + newMenuItems);
        	List<String> newMenuItemsList = Arrays.asList(newMenuItems);
        	LOG.debug("newMenuItemsList: " + newMenuItemsList);
        	LOG.debug("newMenuItemsStr: " + newMenuItemsStr);
    	}
    	return newMenuItemsStr;
    }
    
    
    private static String buildCurrentMenuItemsDelimString() {
    	String[] curMenuItems = StringUtil.splitLines(System.getProperty(HookSysPropsKeys.LIFERAY_DL_FOLDER_ACTIONS_MENU_EXT));
    	String curMenuItemsStr = StringUtil.merge(curMenuItems);
    	if (LOG.isDebugEnabled()) {
        	LOG.debug("curMenuItems: " + curMenuItems);
        	List<String> curMenuItemsList = Arrays.asList(curMenuItems);
        	LOG.debug("curMenuItemsList: " + curMenuItemsList);
        	LOG.debug("curMenuItemsStr: " + curMenuItemsStr);
    	}
    	return curMenuItemsStr;
    }

}
