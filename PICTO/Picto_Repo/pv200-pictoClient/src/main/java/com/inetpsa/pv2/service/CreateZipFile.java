/*
 * Creation : Jun 20, 2016
 */
package com.inetpsa.pv2.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.batch.PictoConstants;

public class CreateZipFile {
    /**
     * Logger log4j to write messages
     */
    private static Logger logger = LoggerFactory.getLogger(CreateZipFile.class);

    public boolean createZipFile(String directoryPathToZip, String fileName) {
        boolean zipCreatedFlag = false;
        File directoryToZip = new File(directoryPathToZip + File.separator + fileName.trim());
        try {
            List<File> fileList = new ArrayList<File>();

            File[] files = directoryToZip.listFiles();
            for (File file : files) {
                fileList.add(file);
                if (file.isDirectory()) {
                    getAllFiles(file, fileList);
                }
            }

            writeZipFile(directoryToZip, fileList, directoryPathToZip + File.separator, fileName);
            zipCreatedFlag = true;
        } catch (Exception e) {
            logger.error("Exception in creating ZIP file", e);

        }
        return zipCreatedFlag;
    }

    public static void getAllFiles(File dir, List<File> fileList) {
        File[] files = dir.listFiles();
        for (File file : files) {
            fileList.add(file);
            if (file.isDirectory()) {
                getAllFiles(file, fileList);
            }
        }
    }

    public static void writeZipFile(File directoryToZip, List<File> fileList, String directoryPathToZip, String fileName) {
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            FileInputStream fis = null;
            fos = new FileOutputStream(directoryPathToZip + File.separator + fileName.trim() + PictoConstants.ZIP_FILE);
            zos = new ZipOutputStream(fos);

            for (File file : fileList) {
                if (!file.isDirectory()) { // we only zip files, not directories
                    fis = new FileInputStream(file);

                    // we want the zipEntry's path to be a relative path that is relative
                    // to the directory being zipped, so chop off the rest of the path
                    String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1,
                            file.getCanonicalPath().length());
                    logger.info("Writing '" + zipFilePath + "' to zip file");
                    ZipEntry zipEntry = new ZipEntry(zipFilePath);
                    zos.putNextEntry(zipEntry);

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zos.write(bytes, 0, length);
                    }

                    zos.closeEntry();
                    fis.close();

                }
            }

            logger.info("ZIP file created");
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException Occured in method writeZipFile of Create Zip File Class", e);
        } catch (IOException e) {
            logger.error("IOException Occured in method writeZipFile of Create Zip File Class", e);
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                logger.error("Exception in closing stream.", e);
            }

        }
    }

    public static void addToZip(File directoryToZip, File file, ZipOutputStream zos) throws FileNotFoundException, IOException {

        FileInputStream fis = new FileInputStream(file);

        // we want the zipEntry's path to be a relative path that is relative
        // to the directory being zipped, so chop off the rest of the path
        String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1, file.getCanonicalPath().length());
        logger.info("Writing '" + zipFilePath + "' to zip file");
        ZipEntry zipEntry = new ZipEntry(zipFilePath);
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }

}
