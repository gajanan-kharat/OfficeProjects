//COPYRIGHT PSA Peugeot Citroen 2010
/**********************************************************************************************************
 *
 * FILE NAME	: PSALanguageHandler.java
 *
 * SOCIETE        : PSA
 * PROJET         : 151AP - Gestion d'Accès à la MNU Vues Fournisseurs - UI
 * VERSION        : V1.0
 * DATE           : 20/07/2010
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    : Class for language support
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/

package com.psa.tools;

import java.io.*;
import java.util.*;

/**
 * Class for language support
 *  
 * @author      Mahendra Chuttar
 *  
 * @version     %I%, %G%
 *  
 * @since       1.0
 */
public class PSALanguageHandler {
	private Properties language = null;
	private String defaultLanguage="fr";

	/**
	 * Constructor of the class.
	 */
	public PSALanguageHandler()
	{
		if(System.getenv("VPM_USER_LANGUAGE") == null)
			defaultLanguage="fr";
		else if(System.getenv("VPM_USER_LANGUAGE") == "")
			defaultLanguage="fr";
		else 
			defaultLanguage=System.getenv("VPM_USER_LANGUAGE");
			
	}
	/**
	 * Loads new language.
	 * @param language_name		Language Name to load language file
	 * @return	Returns the boolean status and error messgae if language file loading error.
	 */
	public Object[] LoadLanguage(String iStrModule_name)
	{
		return LoadLanguage(defaultLanguage,iStrModule_name);
	}
	/**
	 * Loads new language.
	 * @param language_name		Language Name to load language file
	 * @return	Returns the boolean status and error messgae if language file loading error.
	 */
	public Object[] LoadLanguage(String iStrlanguage_name, String iStrModule_name)
	{
		boolean status = true;
		language = new Properties();
		String lang_filename = System.getenv("APPVPM");
		String err_msg = "";
		if(null == lang_filename){
			status = false;
			language = null;
			err_msg = "Unable to get Environment Variable $APPVPM.";
		} else
		{
			lang_filename += "/EtudeVpm/lang/";
			//[FVE 29.09.14 VCOAMSCAD-102/VPM2-9699 Add ETUDE_VPM_LANG env. variable for an easier messages overload
			String lang_path = System.getenv("ETUDE_VPM_LANG");
			System.out.println("ETUDE_VPM_LANG = "+lang_path);
			if(null != lang_path){
				lang_filename = lang_path;
			}
			//]
			lang_filename += iStrlanguage_name;
			lang_filename += "/";
			lang_filename += iStrModule_name;
			lang_filename += ".txt";
			try {
				System.out.println("Language File Name = "+lang_filename);
				language.load(new FileInputStream(lang_filename));
			} catch (FileNotFoundException e) {
				//e.printStackTrace();
				status = false;
				language = null;
				err_msg = "Unable to load language file " + lang_filename;
			} catch (IOException e) {
				//e.printStackTrace();
				status = false;
				language = null;
				err_msg = "Unable to load language file " + lang_filename;
			}
		}
		return new Object[]{Boolean.valueOf(status),err_msg};
	}

	/**
	 * provides string for the input Key.
	 * @param 	Key			Key to load the string from language file.
	 * @param 	Value		Default value for Key. If Key not found Value will be returned.
	 * @return	String loaded for input Key.
	 */
	public String Getlabel(String Key, String Value)
	{
		String str = "";
		if(null != language){
			try{
				str = language.getProperty(Key);
			}catch (Exception e) {
				e.printStackTrace(); 
			}
			if(null == str || (0 == str.length()) ){
				System.out.println("Unable to read string for "+Key);
				str = Value;
			}
		} else
		{
			str = Value;
		}
		return str;
	}

}
