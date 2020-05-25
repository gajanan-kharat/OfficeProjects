package org.seedstack.pv2.utils;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.seedstack.pv2.constants.PictoConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class PictoUtils.
 */
public class PictoUtils {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(PictoUtils.class);

	/**
	 * Make directory.
	 *
	 * @param directory the directory
	 */
	public static void makeDirectory(File directory) {
		if (!directory.exists()) {
			directory.mkdir();
		}
	}

	/**
	 * Change files of folder.
	 *
	 * @param folder the folder
	 * @param ref the ref
	 * @param Name the name
	 */
	public static void changeFilesOfFolder(File folder, String ref, String Name) {
		File[] listOfFiles = folder.listFiles();

		if (listOfFiles != null) {
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					File file = new File(folder.getPath() + "/" + listOfFiles[i].getName());
					String filePath = FilenameUtils.getFullPath(file.toString());
					String fileBaseName = FilenameUtils.getName(file.toString());

					int index = StringUtils.ordinalIndexOf(fileBaseName, "_", 1);
					int index1 = StringUtils.ordinalIndexOf(fileBaseName, "_", 2);
					String baseName = fileBaseName.substring(0, index);
					String lastName = fileBaseName.substring(index1, fileBaseName.length());

					StringBuilder sb = new StringBuilder();
					if (ref == null) {
						sb.append(filePath).append(baseName).append("_").append(Name).append(lastName);
					} else {
						sb.append(filePath).append(baseName).append("_").append(ref).append(Name).append(lastName);
					}
					file.renameTo(new File(sb.toString()));

				} else if (listOfFiles[i].isDirectory() && !PictoConstants.SPECIFICDRAW.equals(listOfFiles[i].getName())) {
					changeFilesOfFolder(listOfFiles[i], ref, Name);
				}
			}
		} else {
			logger.info("No files in the path");

		}
	}

	/**
	 * Change sub folder.
	 *
	 * @param folder the folder
	 * @param ref the ref
	 * @param Name the name
	 */
	public static void changeSubFolder(File folder, String ref, String Name) {

		File[] listOfFiles = folder.listFiles();
		if (listOfFiles != null) {
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isDirectory() && !PictoConstants.SPECIFICDRAW.equals(listOfFiles[i].getName())) {

					File file = new File(folder.getPath() + "/" + listOfFiles[i].getName());

					String filePath = FilenameUtils.getFullPath(file.toString());
					String fileBaseName = FilenameUtils.getName(file.toString());

					int index = StringUtils.ordinalIndexOf(fileBaseName, "_", 1);
					int index1 = StringUtils.ordinalIndexOf(fileBaseName, "_", 2);
					String baseName = fileBaseName.substring(0, index);
					String lastName = fileBaseName.substring(index1, fileBaseName.length());

					StringBuilder sb = new StringBuilder();
					if (ref == null) {
						sb.append(filePath).append(baseName).append("_").append(Name).append(lastName);
					} else {
						sb.append(filePath).append(baseName).append("_").append(ref).append(Name).append(lastName);
					}
					file.renameTo(new File(sb.toString()));

				}

			}
		}

	}
}
