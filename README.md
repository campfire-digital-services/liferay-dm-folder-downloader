# Liferay Documents and Media Folder Download Hook

*liferay-dm-folder-download-hook*

This project provides a Liferay Portal plugin to download the contents of a Documents and Media folder (and subfolders) as a ZIP file.


## Supported Products

* Liferay Portal 6.1 CE GA2 (6.1.1+)
* Liferay Portal 6.1 EE GA2 (6.1.20+)


## Downloads

The latest releases are available from [SourceForge](https://sourceforge.net/projects/permeance-apps/files/liferay-documents-and-media-downloader/ "Documents and Media Downloader").


## Usage

Step 1. Navigate to Liferay Portal page containing Documents and Media portlet.

![Documents and Media Portlet](/docs/images/dm-portlet-20130209.png "Documents an Media Portlet")

Step 2. Click on the "Action" button for a folder and select the "Download Folder (ZIP)" menu item.

![Documents and Media Folder Action Menu](/docs/images/dm-folder-action-menu-20130131-annot.png "Documents an Media Folder Action Menu")

Step 3. Open ZIP file or save to local download folder in your web browser.

![Firefox Download File Dialog](/docs/images/firefox-download-file-dialog-20130209.png "Firefox Download File Dialog")


## Building

Step 1. Checkout source from GitHub project

    % git  clone  https://github.com/permeance/liferay-dm-folder-download-hook

Step 2. Build and package

    % mvn  -U  clean  package

This will build "liferay-dm-folder-download-hook-XXX.war" in the targets tolder.

NOTE: You will require JDK 1.6+ and Maven 3.


## Installation

### Liferay Portal + Apache Tomcat Bundle

eg.

Deploy "liferay-dm-folder-download-hook-1.0.0.0.war" to "LIFERAY_HOME/deploy" folder.


## Project Team

* Tim Telcik - tim.telcik@permeance.com.au
* Chun Ho - chun.ho@permeance.com.au

