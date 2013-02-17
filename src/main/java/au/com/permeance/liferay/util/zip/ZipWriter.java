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

package au.com.permeance.liferay.util.zip;

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
 * NOTE: This class is similar to Liferay's ZipWriter, but does not share the PACL issues
 *       inherited from using the underlying TrueZip library.
 * 
 * @author Chun Ho <chun.ho@permeance.com.au>
 * @author Tim Telcik <tim.telcik@permeance.com.au>
 * 
 * @see com.liferay.portal.kernel.zip.ZipWriter
 * @see ZipWriterFactory
 * @see ZipWriterFactoryUtil
 */
public class ZipWriter {

    private final ZipOutputStream zos;
    private final File zipFile;
    private final Set<String> allocatedPaths = new HashSet<String>();


    public ZipWriter(File file) throws IOException {
        this.zipFile = file;
        this.zos = new ZipOutputStream(new FileOutputStream(this.zipFile));
    }

    public void close() {
        try {
            this.zos.close();
        } catch (Exception e) {
        }
        this.allocatedPaths.clear();
    }

    
    public void addEntry(String path, InputStream is) throws IOException {
        this.zos.putNextEntry(new ZipEntry(path));
        StreamUtil.transfer(is, zos, false);
        this.allocatedPaths.add(path);
    }


    public String getPath() {
        return this.zipFile.getAbsolutePath();
    }

    public boolean hasAllocatedPath(String path) {
        return this.allocatedPaths.contains(path);
    }

}
