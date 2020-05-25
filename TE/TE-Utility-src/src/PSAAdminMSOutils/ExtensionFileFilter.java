/**********************************************************************************************************
 *
 * FILE NAME	  : ExtensionFileFilter.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class used for adding file extensions in File chooser panel.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/
package com.psa.PSAAdminMSOutils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * Class used for adding file extensions in File chooser panel.
 */
class ExtensionFileFilter extends FileFilter {
	String description;				//Description
	String extensions[];			//File Extrnsions

	/**
	 * Constructor.
	 * @param description		description of file type
	 * @param extension			File Extension
	 */
	public ExtensionFileFilter(String description, String extension) {
		this(description, new String[] { extension });
	}

	/**
	 * Constructor.
	 * @param description		description of file type
	 * @param extension[]		File Extensions array
	 */
	public ExtensionFileFilter(String description, String extensions[]) {
		if (0 == description.length()) {
			this.description = extensions[0];
		} else {
			this.description = description;
		}
		this.extensions = (String[]) extensions.clone();
		toLower(this.extensions);
	}

	private void toLower(String array[]) {
		for (int i = 0, n = array.length; i < n; i++) {
			array[i] = array[i].toLowerCase();
		}
	}

	/**
	 * provides description of file types.
	 * @return descriptiom of file types
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Checks wheteher to accept the selected file or not.
	 * @param 	file	Selected file
	 */
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return true;
		} else {
			String path = file.getAbsolutePath().toLowerCase();
			for (int i = 0, n = extensions.length; i < n; i++) {
				String extension = extensions[i];
				if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
					return true;
				}
			}
		}
		return false;
	}
}
