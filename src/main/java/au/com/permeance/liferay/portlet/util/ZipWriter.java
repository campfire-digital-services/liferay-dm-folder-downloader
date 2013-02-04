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

import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.zip.ZipWriterFactory;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FilenameUtils;


/**
 * Utility class to create ZIP file.
 *  
 * NOTE: This class is similar to Liferay's ZipWriter, but does not share the same PACL issues.
 * 
 * @author Chun Ho <chun.ho@permeance.com.au>
 * 
 * @see com.liferay.portal.kernel.zip.ZipWriter
 * @see ZipWriterFactory
 * @see ZipWriterFactoryUtil
 */
public class ZipWriter {
	
    private static final String FILE_EXT_SEP = FilenameUtils.EXTENSION_SEPARATOR_STR;
    private static final String ZIP_FILE_EXT_NAME = "zip";
    private static final String ZIP_FILE_EXT = FILE_EXT_SEP + ZIP_FILE_EXT_NAME;


    private ZipOutputStream zos = null;
    private File targetfile = null;
    private final Set<String> allocatedPaths = new HashSet<String>();

    
    public ZipWriter() throws IOException {
    	String tempFilePrefix = PortalUUIDUtil.generate();
    	String tempFileSuffix = ZIP_FILE_EXT;
    	targetfile = File.createTempFile(tempFilePrefix, tempFileSuffix);
        zos = new ZipOutputStream(new FileOutputStream(targetfile));
    }

    
    public ZipWriter(File file) throws IOException {
        targetfile = file;
        zos = new ZipOutputStream(new FileOutputStream(targetfile));
    }

    
    public void close() {
        try {
            if (zos != null) {
                zos.close();
            }
        } catch (Exception e) {
        }
    }

    
    public void addEntry(String path, InputStream is) throws IOException {
        zos.putNextEntry(new ZipEntry(path));
        StreamUtil.transfer(is, zos, false);
        allocatedPaths.add(path);
    }

    
    public File getFile() {
        close();
        return targetfile;
    }

    
    public String getPath() {
        return targetfile.getAbsolutePath();
    }

    
    public boolean hasAllocatedPath(String path) {
        return allocatedPaths.contains(path);
    }

    
    @Override
    protected void finalize() throws Throwable {
        close();
    }

}
