# Liferay Documents and Media Folder Downloader

*liferay-dm-folder-downloader*

This project provides a Liferay Portal plugin to download the contents of a Documents and Media folder (and subfolders) as a ZIP file.


## Supported Products

### GitHub Project Branch 7.0.x

* Liferay Portal 7.0 CE : 7.0 CE B8 (7.0.0+)

### GitHub Project Master and Branch 6.2.x

* Liferay Portal 6.2 CE : 6.2 CE GA1 (6.2.0+)
* Liferay Portal 6.2 EE : 6.2 EE GA1 (6.2.10+)

### GitHub Project Branch 6.1.x

* Liferay Portal 6.1 CE : 6.1 CE GA2, GA3 (6.1.1+)
* Liferay Portal 6.1 CE : 6.1 EE GA2, GA3 (6.1.20+)


## Downloads

The latest release is available from [SourceForge - Documents and Media Downloader](https://sourceforge.net/projects/permeance-apps/files/liferay-documents-and-media-downloader/ "Documents and Media Downloader").

You can also download or install the hook from [Liferay Marketplace - Documents and Media Downloader](https://www.liferay.com/marketplace/-/mp/application/21674918?_7_WAR_osbportlet_backURL=%2Fmarketplace%2F-%2Fmp%2Fcategory%2F11232561 "Documents and Media Downloader")


## Usage

Step 1. Navigate to Liferay Portal page containing Documents and Media portlet.

![Documents and Media Portlet](/docs/images/01-liferay-dm-portlet-local-repos-root-folder-view-20130209-annot.png "Documents an Media Portlet")

Step 2. Click on the "Action" button for a folder and select the "Download Folder (ZIP)" menu item.

![Documents and Media Folder Action Menu](/docs/images/02-liferay-dm-portlet-download-folder-action-menu-20130131-annot.png "Documents an Media Folder Action Menu")

Step 3. Open ZIP file or save to local download folder in your web browser.

![Firefox Download File Dialog](/docs/images/03-firefox-download-file-dialog-20130209.png "Firefox Download File Dialog")


## Building

Step 1. Checkout source from GitHub project

e.g. Checkout master from GitHub project

NOTE: GitHub master and branch 7.0.x should always be the same.

    $ git clone https://github.com/permeance/liferay-dm-folder-downloader
    Cloning into 'liferay-dm-folder-downloader'...
    . . .
    $ git branch
    * master
    $ git status
    On branch master

e.g. Checkout branch 6.1.x, 6.2.x or 7.0.x from GitHub project

NOTE: This sample shows checkout for branch 6.2.x. The same process applies for 6.1.x or 7.0.x

    % git clone https://github.com/permeance/liferay-dm-folder-downloader
    Cloning into 'liferay-dm-folder-downloader'...
    . . .
    % cd liferay-dm-folder-downloader
    % git checkout 6.2.x
    Switched to branch '6.2.x'
    $ git branch
    * 6.2.x
    $ git status
    On branch 6.2.x    

Step 2. Build and package

    % mvn -U clean package

This will build "liferay-dm-folder-download-hook-A.B.C.war" in the project target tolder.

NOTE: You will require JDK 1.6+ and Maven 3.


## Installation

### Liferay Portal + Apache Tomcat Bundle

Deploy "liferay-dm-folder-download-hook-A.B.C.war" to "LIFERAY_HOME/deploy" folder.

e.g.

    $ cp liferay-dm-folder-download-hook-A.B.C.war" to "LIFERAY_HOME/deploy

## Project Team

* Tim Telcik - tim.telcik@permeance.com.au
* Chun Ho - chun.ho@permeance.com.au


## Related Projects

* [GitHub - Sample Liferay Documents and Media Action Menu Extension](https://github.com/permeance/sample-liferay-dm-action-menu-extension "GitHub - Sample Liferay Documents and Media Action Menu Extension")
