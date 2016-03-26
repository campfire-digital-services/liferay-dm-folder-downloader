<%--
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
--%>


<%@ include file="/document_library/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.log.Log" %>
<%@ page import="com.liferay.portal.kernel.log.LogFactoryUtil" %>

<%
String randomNamespace = null;

if (portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY) || portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)) {
	randomNamespace = PortalUtil.generateRandomKey(request, "portlet_document_library_folder_action") + StringPool.UNDERLINE;
}
else {
	randomNamespace = PortalUtil.generateRandomKey(request, "portlet_image_gallery_display_folder_action") + StringPool.UNDERLINE;
}

String redirect = currentURL;

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Folder folder = null;

long repositoryId = 0;

if (row != null) {
	Object result = row.getObject();

	if (result instanceof Folder) {
		folder = (Folder)result;

		repositoryId = folder.getRepositoryId();
	}
}
else {
	if (portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY)) {
		folder = (Folder)request.getAttribute("info_panel.jsp-folder");

		repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));
	}
	else {
		folder = (Folder)request.getAttribute("info_panel.jsp-folder");

		repositoryId = GetterUtil.getLong((String)request.getAttribute("view_entries.jsp-repositoryId"));
	}
}

long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

if (folder != null) {
	folderId = folder.getFolderId();
}

int status = WorkflowConstants.STATUS_APPROVED;

if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
	status = WorkflowConstants.STATUS_ANY;
}

boolean folderSelected = GetterUtil.getBoolean((String)request.getAttribute("view_entries.jsp-folderSelected"));

String modelResource = null;
String modelResourceDescription = null;
String resourcePrimKey = null;

boolean showPermissionsURL = false;

if (folder != null) {
	modelResource = DLFolderConstants.getClassName();
	modelResourceDescription = folder.getName();
	resourcePrimKey = String.valueOf(folderId);

	showPermissionsURL = DLFolderPermission.contains(permissionChecker, folder, ActionKeys.PERMISSIONS);
}
else {
	modelResource = "com.liferay.document.library";
	modelResourceDescription = themeDisplay.getScopeGroupName();
	resourcePrimKey = String.valueOf(scopeGroupId);

	showPermissionsURL = DLPermission.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
}

DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(dlRequestHelper);

boolean view = false;

if ((row == null) && portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY)) {
	view = true;
}
%>

<%
if (LOG.isDebugEnabled()) {
	LOG.debug("currentURL: " + currentURL);		
	LOG.debug("folderId: " + folderId);
	LOG.debug("repositoryId: " + repositoryId);
}
%>

<liferay-util:buffer var="iconMenuExt">

	<%
	boolean hasViewPermission = DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.VIEW);
	LOG.debug("hasViewPermission: " + hasViewPermission);		
	%>

	<c:if test="<%= dlPortletInstanceSettingsHelper.isShowActions() %>">	
		<c:if test="<%= hasViewPermission %>">
	        <portlet:resourceURL var="downloadResourceURL">
	            <portlet:param name="struts_action" value="/document_library/download_folder" />
	            <portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
	            <portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
	        </portlet:resourceURL>
	        
	        <liferay-ui:icon
	            iconCssClass="download"
	            message="download-folder-as-zip-file"
				method="get"	            
	            url='<%= downloadResourceURL %>'
	        />				        		
		</c:if>
	</c:if>
</liferay-util:buffer>

<%= iconMenuExt %> 

<%!
private static Log LOG = LogFactoryUtil.getLog("portal-web.docroot.document_library.folder_actions_menu_ext.download_folder_zip.jsp");
%>
