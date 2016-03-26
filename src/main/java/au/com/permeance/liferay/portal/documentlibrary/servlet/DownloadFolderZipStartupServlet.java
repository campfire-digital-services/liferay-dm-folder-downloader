/**
 * Copyright (C) 2013-present Permeance Technologies. ALl rights reserved.
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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;


/**
 * StartupServlet
 * 
 * In testing, we are experiencing in some cases if the plugin security manager has been enabled,
 * registered ServletContextListener(s) would not fire (plausibly due to timing between when the
 * deploy event is set and when SecurePluginContextListener registers it). This happens on both CE
 * and EE.
 * 
 * This startup servlet is a workaround for those cases that need it.
 * 
 * @author Chun Ho <chun.ho@permeance.com.au>
 */
public class DownloadFolderZipStartupServlet extends HttpServlet {

    private static final long serialVersionUID = -6917677483398490295L;
    
    private static final Log LOG = LogFactoryUtil.getLog(DownloadFolderZipStartupServlet.class);
    

    @Override
    public void init(final ServletConfig config) throws ServletException {
    	LOG.debug("init");
        super.init(config);
        DownloadFolderZipActionsMenuHookListener.startApplication();
    }

    @Override
    public void destroy() {
    	LOG.debug("destroy");
        DownloadFolderZipActionsMenuHookListener.stopApplication();
        super.destroy();
    }

}
