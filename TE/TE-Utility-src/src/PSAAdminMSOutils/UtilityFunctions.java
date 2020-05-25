/**********************************************************************************************************
 *
 * FILE NAME	  : UtilityFunctions.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class used to perform date operation.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/
package com.psa.PSAAdminMSOutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.psa.tools.PSALanguageHandler;
import com.psa.tools.PSAMessageDialog;

/**
 *  Class used to perform date operation.
 *
 */
public class UtilityFunctions {
	/**
	 * Helper Function for conversion of String format date to Date.
	 * @param 	istrdate 	Conversion of String date to convert.
	 * @param 	istrFormat	Format to convert
	 * @return	Object[0]	- boolean 	true  - success 
	 * 									false - faliure
	 * 			Object[1]	- converted Date in cse of success or null in case of failure.  
	 */
	static public Object[] String_to_date_Conversion(String istrdate, String istrFormat, PSALanguageHandler psaLanguageHandler)
	{
		Date date =  new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(istrFormat); 
		boolean status = false;
		try {
			formatter.setLenient(false);
			date = formatter.parse(istrdate);
			status = true;
		} catch (ParseException e) {
			String msg = "Invalid Date format. Format is %s";
			msg = psaLanguageHandler.Getlabel("Error.Invaliddate",msg);
			msg = String.format(msg, istrFormat.toLowerCase());
			@SuppressWarnings("unused")
			PSAMessageDialog.OPTION opt = new PSAMessageDialog().showMessageDialog(null, msg,psaLanguageHandler);
			return new Object[]{status,null};
		}
		return new Object[]{status,date};
	}
	
	/**
	 * Helper Function for conversion of Date format date to String.
	 * @param 	idate 			Conversion of Date to convert
	 * @param 	istrFormat	Format to convert
	 * @return	Object[0]	- boolean 	true  - success 
	 * 									false - faliure
	 * 			Object[1]	- converted string format date in cse of success or null in case of failure.  
	 */
	static public String Date_to_String_Conversion(Date idate, String istrFormat)
	{
		String strdate = "";
		SimpleDateFormat formatter = new SimpleDateFormat(istrFormat); 
		formatter.setLenient(false);
		strdate = formatter.format(idate);
		return strdate;
	}

	/**
	 * It used to get  formated string date  from Calendar  object.
	 * @param iCalendar : Calendar instance.
	 * @param iStrFormat : Format in which date has to be convert
	 * @return iStrFormat : Formated date string
	 */
	static public String getFormatedDate(Calendar iCalendar,String iStrFormat)
	{
		  SimpleDateFormat sdf = new SimpleDateFormat(iStrFormat);
	      String date = sdf.format(iCalendar.getTime());
	      System.out.println(date);
	      return date;
	}
	
}
