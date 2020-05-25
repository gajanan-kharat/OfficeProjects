//COPYRIGHT PSA Peugeot Citroen 2012
/**********************************************************************************************************
 *
 * FILE NAME	  : SupportFunctions.java
 *
 * SOCIETE        : PSA
 * PROJET         : 182AN-Robustesse Vue Fournisseur pour les JV
 * VERSION        : V1.0
 * DATE           : 25/07/2012
 *
 * AUTEUR         : Pankaj Pardhi (GEOMETRIC GLOBAL)
 *
 * DESCRIPTION    : Class used to perform support operation.
 *
**********************************************************************************************************/
package com.psa.tools;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.psa.tools.PSALanguageHandler;
import com.psa.tools.PSAMessageDialog;

/**
 *  Class used to perform support operation.
 *
 */
public class SupportFunctions {
	/**
	 * Helper Function for conversion of String format date to Date.
	 * @param 	istrdate 	Conversion of String date to convert.
	 * @param 	istrFormat	Format to convert
	 * @return	Object[0]	- boolean 	true  - success
	 * 									false - failure
	 * 			Object[1]	- converted Date in case of success or null in case of failure.
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
			System.out.println(msg);
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
	 * 									false - failure
	 * 			Object[1]	- converted string format date in case of success or null in case of failure.
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
	 * @return iStrFormat : Formated date string
	 */
	static public String getFormatedDate(Calendar iCalendar,String iStrFormat)
	{
		  SimpleDateFormat sdf = new SimpleDateFormat(iStrFormat);
	      String date = sdf.format(iCalendar.getTime());
	      return date;
	}

	/**
	 * It is used to get the center positon  for given component.
	 * @param component : Component
	 * @return : Point contains x ,y position for window.
	 */
	public static Point getCenterPosition(Component component)
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		// Determine the new location of the window
		int width = component.getSize().width;
		int height = component.getSize().height;
		int x = (dim.width-width)/2;
		int y = (dim.height-height)/2;
		return new Point(x, y);
	}

	
	

	/**
	 * It compare the start date and end date.
	 * @param iStrStartDate		: String
	 * @param iStrEndDate		: String
	 * @param iStrDateFormat	: String
	 * @param psaLanguageHandler: PSALanguageHandler
	 * @return int
	 *     = 0 			if the Start Date is equal to End Date;
	 *     < 0			if Start Date  is before End Date argument; 
	 *     > 0 			if Start Date is after End Date argument. 
	 * @throws Exception 

	 */
	public static int compareDate(String iStrStartDate , String iStrEndDate , String iStrDateFormat ,PSALanguageHandler psaLanguageHandler) throws Exception
	{
		
		Object[] objStartDate = String_to_date_Conversion(iStrStartDate , iStrDateFormat , psaLanguageHandler);
		Object[] objEndDate = String_to_date_Conversion(iStrEndDate , iStrDateFormat , psaLanguageHandler);
		
		if(objStartDate[1] == null || objEndDate[1] == null ){
			throw new Exception("Invalid Date : StartDate = "+iStrStartDate +" And End Date = "+iStrEndDate);
		}
		Date  startDate = (Date) objStartDate[1];
		Date  endDate = (Date) objEndDate[1];
		
		System.out.println("Start Date = "+startDate);
		System.out.println("End Date = "+endDate);
		return endDate.compareTo(startDate);
	}

	/**
	 * It converts the second to day ,hours, minute and second.
	 * @param seconds :  long
	 * @return String	: Return string in the format <No. of Days> Day(s), <No. of Hours> Hours(s), <No. of Min> Min, <No. of Sec> Sec.
	 * 				
	 */
	public static String convertSecondsToDD_HH_MM_SS(long seconds) {
		
		String strConvertString ="";
		 //Get the days .
		 int day = (int) TimeUnit.SECONDS.toDays(seconds);
		 //Get the hours.
	     long hours = TimeUnit.SECONDS.toHours(seconds) - TimeUnit.DAYS.toHours(day);
	     //Get the Minute.
	     long minute = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.DAYS.toMinutes(day) - TimeUnit.HOURS.toMinutes(hours);
	     //Get the seconds.
	     long second = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.DAYS.toSeconds(day) -
	                  TimeUnit.HOURS.toSeconds(hours) -  TimeUnit.MINUTES.toSeconds(minute);

	     //Set Convert string.
	     //Add Days to Convert String 
         if(day > 0)
         {
        	strConvertString = day+" Day(s)";
         }
         //Add hours to Convert String 
         if(hours > 0)
         {
        	 if(strConvertString.length() > 0)
        	 {
        		 strConvertString +=", ";
        	 }
        	strConvertString += hours+" Hour(s)";
         }
         //Add minute to Convert String 
         if(minute > 0)
         {
        	 if(strConvertString.length() > 0)
        	 {
        		 strConvertString +=", ";
        	 }
        	 strConvertString += minute+" Min";
         }
         //Add second to Convert String 
         if(second > 0)
         {
        	 if(strConvertString.length() > 0)
        	 {
        		 strConvertString +=", ";
        	 }
        	 strConvertString += second+" Sec";
         }
         
         System.out.println("Formated String = "+strConvertString);
         return strConvertString;

    }
 
	/**
	 * ConvertDate to the formatted string
	 * @param iDate			Date
	 * @param istrFormat	Foramt
	 * @return
	 */
	static public String convertDateToTimeStamp(Date iDate, String istrFormat)
	{
		String strdate = "";
		SimpleDateFormat formatter = new SimpleDateFormat(istrFormat);
		strdate = formatter.format(iDate);
		return strdate;
	}
}


