/**********************************************************************************************************
 *
 * FILE NAME	  : LaunchDate.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class used to generate next launch date for week ,month and year.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/
package com.psa.tools;

//Standard class import
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

/**
 * Class used to generate next launch date for week ,month and year.
 *
 */

public class LaunchDate {
		private Hashtable<String, Integer> weekDays;
		private Hashtable<String, Integer> weekNumber;
		boolean m_bConsiderTodayDate = false;

		/**
		 *Constructor of the class. 
		 */
		public LaunchDate()
		{
			//Add week days to hashtable.
			weekDays = new Hashtable<String, Integer>();
			weekDays.put("SUNDAY", new Integer(Calendar.SUNDAY));//1
			weekDays.put("MONDAY", new Integer(Calendar.MONDAY));
			weekDays.put("TUESDAY", new Integer(Calendar.TUESDAY));
			weekDays.put("WEDNESDAY", new Integer(Calendar.WEDNESDAY));
			weekDays.put("THURSDAY", new Integer(Calendar.THURSDAY));
			weekDays.put("FRIDAY", new Integer(Calendar.FRIDAY));
			weekDays.put("SATURDAY", new Integer(Calendar.SATURDAY));
			
			//Add week number to hashtable.
			weekNumber = new Hashtable<String, Integer>();
			weekNumber.put("FIRST", new Integer(1));
			weekNumber.put("SECOND", new Integer(2));
			weekNumber.put("THIRD", new Integer(3));
			weekNumber.put("FOURTH", new Integer(4));
			weekNumber.put("LAST", new Integer(5));
		}


		/**
		 *Constructor of the class. 
		 */
		public LaunchDate(boolean bConsiderTodayDate)
		{
			this();
			Calendar calendar = Calendar.getInstance();
			Date currentDate = clearTimeStamp(calendar);		//Clear timestamp for current date
			if (currentDate.getHours() < 20)
				m_bConsiderTodayDate = bConsiderTodayDate;
			else
				m_bConsiderTodayDate = false;
		}

		/**
		 * It finds the next  launch date for week.
		 * @param daysList :list contains the days like MONDAY, TUESDAY etc...
		 * @return String : next launch date.
		 */
		public String nextWeekDayLaunchDate(ArrayList<String> daysList)
		{
			String strNextDate=null;
			//Check days list empty or not.
			if(daysList != null && daysList.size() > 0)
			{
				Calendar calendar = Calendar.getInstance();
			    Date nextLaunchDate = null;
				Date currentDate = clearTimeStamp(calendar);		//Clear timestamp for current date
				
				for (String day : daysList)
				{
					//Get week day number from hashtable 
					Integer weekDayNo = (Integer) weekDays.get(day.toUpperCase());
					if(weekDayNo != null)
					{
						Calendar calendar1 = Calendar.getInstance();
						calendar1.set(Calendar.DAY_OF_WEEK,weekDayNo);			//Set the day  of the week.
						Date newDate = clearTimeStamp(calendar1);				//Clear timestamp for new date
						//if(newDate.compareTo(currentDate) <= 0 )
						if((true == m_bConsiderTodayDate) && (newDate.compareTo(currentDate) < 0) ||
						   (false == m_bConsiderTodayDate) && (newDate.compareTo(currentDate) <=0) )
						{
							int weekofMonth = calendar1.get(Calendar.WEEK_OF_MONTH);	//Get week of month
							calendar1.set(Calendar.WEEK_OF_MONTH,weekofMonth+1);
							newDate = calendar1.getTime();						//Get modified date
						}
						//Set next launch date
						//if(newDate.compareTo(currentDate) != 0)
						{
							if(nextLaunchDate == null)
								nextLaunchDate =  newDate;
							else
							{
								//For FILTER consider today's date also for calculation of next launch date.
								if(true == m_bConsiderTodayDate)
								{
									//If calculated next launch date is current date and calucltaion is done before filter processing time, set nextlaunch date as current date.
									if( newDate.getDate() == currentDate.getDate())
									{
										nextLaunchDate = newDate;
									} else if((nextLaunchDate.after(newDate)))			//Check next launchdate is recent
									{	
										nextLaunchDate = newDate;
									}
								} 
								else
								{
									if((nextLaunchDate.after(newDate)))		//Check next launchdate is recent
										nextLaunchDate = newDate;
								}
							}
						}
					}
				}
				//Get formated date from date object.
				if(nextLaunchDate != null)
				{
					strNextDate = getFormatedDate(nextLaunchDate);
				}
			}
			return strNextDate;
		}

	    /**
	     * It finds the next launch date for week based on Start Date passed.
	     * 
	     * @param daysList :list contains the days like MONDAY, TUESDAY etc...
	     * @param dateStartDate : "yyyy-MM-dd" - Start Date to be considered while calculating Next Start Date
	     * @return String : next launch date.
	     */
	    public String nextWeekDayLaunchDate(List<String> daysList, String iStrStartDate) {
	        String strNextLaunchDate = null;
	        // Check days list empty or not.
	        if (daysList != null && daysList.size() > 0)
	        {
	            Date nextLaunchDate = null;
	            Date dateStartDate = new Date();
	            String strFormat = "yyyy-MM-dd";
	            SimpleDateFormat formatter = new SimpleDateFormat(strFormat);
	            try
	            {
	                formatter.setLenient(false);
	                dateStartDate = formatter.parse(iStrStartDate);
	            } catch (ParseException e)
	            {
	                String msg = "Invalid Date format. Format is %s";
	                msg = String.format(msg, strFormat.toLowerCase());
	                return strNextLaunchDate;
	            }
	            System.out.println("[LaunchDate:nextWeekDayLaunchDate] Formatted Start date is:" + dateStartDate);
                System.out.println("[LaunchDate:nextWeekDayLaunchDate] WeekDays List:" + daysList);
                
	            for (String day : daysList)
	            {
	                // Get week day number from hashtable
	                Integer weekDayNo = (Integer) weekDays.get(day.toUpperCase());
	                if (weekDayNo != null)
	                {
	                    Calendar calendar = Calendar.getInstance();//Current date and time
	                    calendar.setTime(dateStartDate);
	                    calendar.set(Calendar.DAY_OF_WEEK, weekDayNo); // Set the day of the week.
	                    Date newDate = clearTimeStamp(calendar); // Clear timestamp for new date
	                    System.out.println("[LaunchDate:nextWeekDayLaunchDate] Interative - First New date is:" + newDate);
	                    
	                    if( newDate.compareTo(dateStartDate) < 0)
	                    {
	                        int weekofMonth = calendar.get(Calendar.WEEK_OF_MONTH); // Get week of month
	                        calendar.set(Calendar.WEEK_OF_MONTH, weekofMonth + 1);
	                        newDate = calendar.getTime(); // Get modified date
	                        System.out.println("[LaunchDate:nextWeekDayLaunchDate] Interative - Next Week New date is:" + newDate);
	                    }

	                    // Set next launch date
                        if (nextLaunchDate == null)
                        {
                            nextLaunchDate = newDate;
                        }
                        else 
                        {
                            //If calculated next launch date is start date, set
                            if ((newDate.getDate() == dateStartDate.getDate()) || (nextLaunchDate.after(newDate)))
                            {
                                nextLaunchDate = newDate;
                            }
                        }
                        System.out.println("[LaunchDate:nextWeekDayLaunchDate] Iterative - Next Launch Date is:" + nextLaunchDate);
	                }
	            }
	            
	            // Get formated date from date object.
	            if (nextLaunchDate != null)
	            {
	                strNextLaunchDate = getFormatedDate(nextLaunchDate);
	                strNextLaunchDate = strNextLaunchDate.replace('/', '-');
	            }
                System.out.println("[LaunchDate:nextWeekDayLaunchDate] Calculated Next Launch Date is:" + strNextLaunchDate);
	        }
	        return strNextLaunchDate;
	    }

		/**
		 * It finds the next  launch date for month.
		 * @param daysList :list contains the days like FIRST MONDAY,FIRST TUESDAY etc...
		 * @return String : next launch date.
		 */
		public String nextMonthLaunchDate(ArrayList<String> daysList)
		{
			String strNextDate=null;
			Calendar calendar = Calendar.getInstance();
		    Date nextLaunchDate = null;
			Date currentDate = clearTimeStamp(calendar);			//Clear timestamp for current date
			if(daysList != null && daysList.size() > 0)
			{
				for (String day : daysList)
				{
					//Split the string by space.
					String[] tempArray = day.split(" "); // "FIRST MONDAY" into FIRST  and MONDAY
					if(tempArray.length == 2)
					{
						//Get Week number from hashtable.
						Integer weekNo = (Integer) weekNumber.get(tempArray[0].toUpperCase()); 
						//Get Week day from hashtable.
						Integer dayNo = (Integer)weekDays.get(tempArray[1].toUpperCase());
						if(dayNo != null && weekNo != null)
						{
							Calendar calendar1 = Calendar.getInstance();
							calendar1.set(Calendar.DAY_OF_WEEK,dayNo);					//Set the day of the week.
							calendar1.set(Calendar.DAY_OF_WEEK_IN_MONTH,weekNo);		//Set the week in month.
							Date newDate = clearTimeStamp(calendar1);					//Clear timestamp for new date
							if(newDate.compareTo(currentDate) <= 0 )
							if((true == m_bConsiderTodayDate) && (newDate.compareTo(currentDate) < 0) ||
							   (false == m_bConsiderTodayDate) && (newDate.compareTo(currentDate) <=0) )
							{
								int month = calendar1.get(Calendar.MONTH);
								calendar1.set(Calendar.MONTH,month+1);					//Set new month
								calendar1.set(Calendar.DAY_OF_WEEK,dayNo);				//Set day for new date
								calendar1.set(Calendar.DAY_OF_WEEK_IN_MONTH,weekNo);	//set week no for new date
								newDate = calendar1.getTime();
							}
							
							//Set next launch date
							//if(newDate.compareTo(currentDate) != 0)
							{
								if(nextLaunchDate == null)
									nextLaunchDate =  newDate;
								else
								{
									//For FILTER consider today's date also for calculation of next launch date.
									if(true == m_bConsiderTodayDate)
									{
										//If calculated next launch date is current date and calucltaion is done before filter processing time, set nextlaunch date as current date.
										if( newDate.getDate() == currentDate.getDate())
										{
											nextLaunchDate = newDate;
										} else if((nextLaunchDate.after(newDate)))			//Check next launchdate is recent
										{	
											nextLaunchDate = newDate;
										}
									} 
									else
									{
										if((nextLaunchDate.after(newDate)))		//Check next launchdate is recent
											nextLaunchDate = newDate;
									}
								}
							}
						}
					}
				}
				//Get formated date from date object.
				if(nextLaunchDate != null)
				{
					strNextDate = getFormatedDate(nextLaunchDate);
				}
			}
			return strNextDate;
		}

	    /**
	     * It finds the next launch date for month.
	     * 
	     * @param daysList :list contains the days like FIRST MONDAY,FIRST TUESDAY etc...
	     * @return String : next launch date.
	     */
	    public String nextMonthLaunchDate(List<String> daysList, String iStrStartDate)
	    {
	        String strNextLaunchDate = null;
            Date nextLaunchDate = null;
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
	        
	        //Converting Start Date from String to Date
	        Date dateStartDate = new Date();
	        String strFormat = "yyyy-MM-dd";
	        SimpleDateFormat formatter = new SimpleDateFormat(strFormat);
	        try
	        {
	            formatter.setLenient(false);
	            dateStartDate = formatter.parse(iStrStartDate);
	        } catch (ParseException e)
	        {
	            String msg = "Invalid Date format. Format is %s";
	            msg = String.format(msg, strFormat.toLowerCase());
	            return strNextLaunchDate;
	        }
	        
	        if (daysList != null && daysList.size() > 0)
	        {
	            for (String day : daysList)
	            {
	                // Split the string by space.
	                String[] tempArray = day.split(" "); // "FIRST MONDAY" into FIRST and MONDAY
	                if (tempArray.length == 2)
	                {
	                    // Get Week number from hashtable.
	                    Integer weekNo = (Integer) weekNumber.get(tempArray[0].toUpperCase());

	                    // Handling case or LAST for non-leap year
	                    if ((currentMonth == 1) && (!isLeapYear(currentYear)) && (weekNo == (Integer) 5))
	                    {
	                        weekNo = (Integer) 4;
	                    }

	                    // Get Week day from hashtable
	                    Integer dayNo = (Integer) weekDays.get(tempArray[1].toUpperCase());
	                    if (dayNo != null && weekNo != null)
	                    {
	                        Date newDateMonthly = null;
	                        // Handling for "LAST"
	                        if (weekNo == 5)
	                        {
	                            // Values for 4th week
	                            Calendar calendarWk4 = Calendar.getInstance();//Current date and time
                                calendarWk4.setTime(dateStartDate);
	                            calendarWk4.set(Calendar.DAY_OF_WEEK, dayNo); // Set the day of the week.
	                            calendarWk4.set(Calendar.DAY_OF_WEEK_IN_MONTH, (weekNo - 1)); // Set the week in month.
	                            Date newDateWk4 = clearTimeStamp(calendarWk4); // Clear timestamp for new date
	                            if (newDateWk4.compareTo(dateStartDate) < 0)
	                            {
                                    int month = calendarWk4.get(Calendar.MONTH);
                                    calendarWk4.set(Calendar.MONTH, month + 1); // Set new month
                                    calendarWk4.set(Calendar.DAY_OF_WEEK, dayNo); // Set day for new date
                                    calendarWk4.set(Calendar.DAY_OF_WEEK_IN_MONTH, (weekNo - 1)); // set week no for new date
                                    newDateWk4 = calendarWk4.getTime();
	                            }

	                            // Values for 5th week
	                            Calendar calendarWk5 = Calendar.getInstance();//Current date and time
                                calendarWk5.setTime(dateStartDate);
	                            calendarWk5.set(Calendar.DAY_OF_WEEK, dayNo); // Set the day of the week.
	                            calendarWk5.set(Calendar.DAY_OF_WEEK_IN_MONTH, weekNo); // Set the week in month.
	                            Date newDateWk5 = clearTimeStamp(calendarWk5); // Clear timestamp for new date
	                            if (newDateWk5.compareTo(dateStartDate) < 0)
	                            {
                                    int month = calendarWk5.get(Calendar.MONTH);
                                    calendarWk5.set(Calendar.MONTH, month + 1); // Set new month
                                    calendarWk5.set(Calendar.DAY_OF_WEEK, dayNo); // Set day for new date
                                    calendarWk5.set(Calendar.DAY_OF_WEEK_IN_MONTH, weekNo); // set week no for new date
                                    newDateWk5 = calendarWk5.getTime();
	                            }

	                            // Get month values for resultant dates
	                            int iMonthForNewDateWk4 = newDateWk4.getMonth();
	                            int iMonthForNewDateWk5 = newDateWk5.getMonth();
	                            if (iMonthForNewDateWk4 < iMonthForNewDateWk5)
	                            {
	                                newDateMonthly = newDateWk4;
	                            }
	                            else
	                            {
	                                newDateMonthly = newDateWk5;
	                            }
	                        }
	                        else
	                        {
	                            Calendar calendarNewMonthlyDate = Calendar.getInstance();//Current date and time
	                            calendarNewMonthlyDate.setTime(dateStartDate);
	                            calendarNewMonthlyDate.set(Calendar.DAY_OF_WEEK, dayNo); // Set the day of the week.
	                            calendarNewMonthlyDate.set(Calendar.DAY_OF_WEEK_IN_MONTH, weekNo); // Set the week in month.
	                            newDateMonthly = clearTimeStamp(calendarNewMonthlyDate); // Clear timestamp for new date
	                            if (newDateMonthly.compareTo(dateStartDate) < 0)
	                            {
                                    int month = calendarNewMonthlyDate.get(Calendar.MONTH);
                                    calendarNewMonthlyDate.set(Calendar.MONTH, month + 1); // Set new month
                                    calendarNewMonthlyDate.set(Calendar.DAY_OF_WEEK, dayNo); // Set day for new date
                                    calendarNewMonthlyDate.set(Calendar.DAY_OF_WEEK_IN_MONTH, weekNo); // set week no for new date
                                    newDateMonthly = calendarNewMonthlyDate.getTime();
	                            }
	                        }

	                        // Set next launch date
	                        if (nextLaunchDate == null)
	                        {
	                            nextLaunchDate = newDateMonthly;
	                        }
	                        else 
	                        {
	                            //If calculated next launch date is start date, set
	                            if ((newDateMonthly.getDate() == dateStartDate.getDate()) || (nextLaunchDate.after(newDateMonthly)))
	                            {
	                                nextLaunchDate = newDateMonthly;
	                            }
	                        }
	                        System.out.println("[LaunchDate:nextMonthLaunchDate] Iterative - Next Launch Date is:" + nextLaunchDate);
	                    }
	                }
	            }//for
	            
	            // Get formated date from date object.
	            if (nextLaunchDate != null)
	            {
	                strNextLaunchDate = getFormatedDate(nextLaunchDate);
	                strNextLaunchDate = strNextLaunchDate.replace('/', '-');
	            }
	            System.out.println("[LaunchDate:nextMonthLaunchDate] Calculated Next Launch Date is:" + strNextLaunchDate);
	        }
	        return strNextLaunchDate;
	    }

	    /**
	     * It finds the next launch date for month.
	     * 
	     * @param daysList :list contains the DAY_OF_MONTH like 1,2,3....31
	     * @return String : next launch date.
	     */
	    public String nextMonthLaunchDayOfMonth(List<String> daysOfMonthList, String iStrStartDate)
	    {
	        String strNextLaunchDate = null;
            Date nextLaunchDate = null;
	        
	        //Converting Start Date from String to Date
	        Date dateStartDate = new Date();
	        String strFormat = "yyyy-MM-dd";
	        SimpleDateFormat formatter = new SimpleDateFormat(strFormat);
	        try 
	        {
	            formatter.setLenient(false);
	            dateStartDate = formatter.parse(iStrStartDate);
	        }
	        catch (ParseException e)
	        {
	            String msg = "Invalid Date format. Format is %s";
	            msg = String.format(msg, strFormat.toLowerCase());
	            return strNextLaunchDate;
	        }

	        if (daysOfMonthList != null && daysOfMonthList.size() > 0)
	        {
	            for (String dayOfMonth : daysOfMonthList)
	            {
	                //Convert this DayOfMonth String to Integer
	                int iDayOfMonth = Integer.parseInt(dayOfMonth);
	                Calendar calendarNewMonthlyDay = Calendar.getInstance();//Current date and time
	                calendarNewMonthlyDay.setTime(dateStartDate);
	                calendarNewMonthlyDay.set(Calendar.DAY_OF_MONTH, iDayOfMonth); // Set the Day of the Month.
	                Date newDateMonthlyDay = clearTimeStamp(calendarNewMonthlyDay); // Clear timestamp for new date

	                if (newDateMonthlyDay.compareTo(dateStartDate) < 0)
	                {
                        int month = calendarNewMonthlyDay.get(Calendar.MONTH);
                        calendarNewMonthlyDay.set(Calendar.MONTH, month + 1); // Set new month
                        calendarNewMonthlyDay.set(Calendar.DAY_OF_MONTH, iDayOfMonth); // Set the Day of the Month.
                        newDateMonthlyDay = calendarNewMonthlyDay.getTime();
	                }

                    // Set next launch date
                    if (nextLaunchDate == null)
                    {
                        nextLaunchDate = newDateMonthlyDay;
                    }
                    else 
                    {
                        //If calculated next launch date is start date, set
                        if ((newDateMonthlyDay.getDate() == dateStartDate.getDate()) || (nextLaunchDate.after(newDateMonthlyDay)))
                        {
                            nextLaunchDate = newDateMonthlyDay;
                        }
                    }
                    System.out.println("[LaunchDate:nextMonthLaunchDayOfMonth] Iterative - Next Launch Date is:" + nextLaunchDate);
	            }//for

	            // Get formated date from date object.
	            if (nextLaunchDate != null) {
	                strNextLaunchDate = getFormatedDate(nextLaunchDate);
	                strNextLaunchDate = strNextLaunchDate.replace('/', '-');
	            }
                System.out.println("[LaunchDate:nextMonthLaunchDayOfMonth] Calculated Next Launch Date is:" + strNextLaunchDate);
	        }
	        return strNextLaunchDate;
	    }

		/**
		 * It finds the next  launch date for year.
		 * @param daysList :list contains dates.
		 * @return String : next launch date.
		 */
		public String nextYearLaunchDate(ArrayList<String> daysList)
		{
			String strNextDate = null;
			SimpleDateFormat sdf ;
			Date nextLaunchDate = null;

			int size = daysList.size();
			if (size > 0)					//Check date list is empty or not.
			{
				try 
				{
					sdf = new SimpleDateFormat("yyyy/MM/dd");
					Date currentDate = new Date();
					for (int index = 0; index < size ; index++) 
					{
						String strDate = daysList.get(index);
						Date tempDate = sdf.parse(strDate);
						System.out.println("Year temp date = "+tempDate);
						//Check next launchdate is greater than tempdate.
						if((nextLaunchDate == null && currentDate.compareTo(tempDate) < 0) || 
								(nextLaunchDate != null &&	currentDate.compareTo(tempDate) < 0 && nextLaunchDate.compareTo(tempDate)> 0))
						{
								nextLaunchDate = tempDate;
						}
					}
					
					if(nextLaunchDate != null)
					{
						strNextDate = getFormatedDate(nextLaunchDate);
					}
				} catch (ParseException e) {
					System.out.println("NextYearLaunchDate : "+e);
				}
			}
			return strNextDate;
		}
		
	    /**
	     * It finds the next launch date for week.
	     * 
	     * @param iStrStartDate : Start Date to be considered while calculating Next Start Date
	     * @return String : next launch date.
	     */
	    public String nextDayLaunchDate(String iStrStartDate) {
	        String strNextLaunchDate = null;
	        // Check days list empty or not.
	        Date nextLaunchDate = null;
	        Date dateStartDate = new Date();
	        String strFormat = "yyyy-MM-dd";
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        try {
	            // formatter.setLenient(false);
	            dateStartDate = formatter.parse(iStrStartDate);
	        } catch (ParseException e) {
	            String msg = "Invalid Date format. Format is %s";
	            msg = String.format(msg, strFormat.toLowerCase());
	            return strNextLaunchDate;
	        }

	        Calendar calendar1 = Calendar.getInstance();
	        calendar1.setTime(dateStartDate);
	        calendar1.add(Calendar.DATE, 1);
	        nextLaunchDate = calendar1.getTime();

	        // Get formated date from date object.
	        if (nextLaunchDate != null) {
	            strNextLaunchDate = getFormatedDate(nextLaunchDate);
	            strNextLaunchDate = strNextLaunchDate.replace('/', '-');
	        }

	        return strNextLaunchDate;
	    }

        /**
         * It gives today's date.
         * 
         * @return date : Today's Date.
         */
        public Date getTodaysDate() {
            Calendar calendar = Calendar.getInstance();
            Date todaysDate = clearTimeStamp(calendar); // Clear timestamp for current date
            return todaysDate;
        }

        /**
         * It gives today's date.
         * 
         * @return date : Today's Date.
         */
        public Date getDateWithoutTimestamp(Date dateInput) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateInput);
            Date dateWithoutTimestamp = clearTimeStamp(calendar); // Clear timestamp for current date
            return dateWithoutTimestamp;
        }
    
		/**
		 * It used to get  formated string date  from Date  object.
		 * @param date    : Date instance.
		 * @return String : Formated date string
		 */
		private String getFormatedDate(Date date)
		{
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		      String strDate = sdf.format(date);
		      System.out.println(strDate);
		      return strDate;
		}

		/*
		 * Clears the timestamp for the calender and provides the date.
		 * @param cal	calender item for which timestamp has to be clear.
		 * @return Date		date object for the corresponding calender without timestamp.
		 */
		private Date clearTimeStamp(Calendar cal)
		{
			cal.clear( Calendar.HOUR_OF_DAY ); cal.clear( Calendar.MINUTE ); cal.clear( Calendar.SECOND ); cal.clear( Calendar.MILLISECOND );
			return cal.getTime();
		}

        /**
         * It used to check if this is Leap year.
         * @param year    : int year
         * @return boolean : true if Leap Year
         */
        private boolean isLeapYear(int year)
        {
            if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
                return true;
            } else {
                return false;
            }
        }
}
