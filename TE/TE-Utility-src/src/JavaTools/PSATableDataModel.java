//COPYRIGHT PSA Peugeot Citroen 2010
/**********************************************************************************************************
 *
 * FILE NAME	: PSATableDataModel.java
 *
 * SOCIETE        : PSA
 * PROJET         : 151AP - Gestion d'Accès à la MNU Vues Fournisseurs - UI
 * VERSION        : V1.0
 * DATE           : 20/07/2010
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    : Class for Handling Data Model of Vue List
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/
package com.psa.tools;

import java.util.Date;
import java.util.Hashtable;
import javax.swing.table.AbstractTableModel;
import java.awt.Point;
import java.text.ParseException;
import java.text.SimpleDateFormat;
// --> Debut Modif [159AN - Gestion d'Acces à la MNU] Used Regex filter patterns for filtering table. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// --> Fin Modif [159AN - Gestion d'Acces à la MNU] Used Regex filter patterns for filtering table. [Mahendra Chuttar Geometric Limited 10-Dec-2010]

/**
 * Class for Handling Data Model of Vue List
 *  
 * @author      Mahendra Chuttar
 *  
 * @version     %I%, %G%
 *  
 * @since       1.0
 */
@SuppressWarnings("serial")
public class PSATableDataModel extends AbstractTableModel {

 	private Hashtable lookup;
	private Hashtable clone_lookup;	
	private int m_rows;									//Rows diaplsyed in table
	private int m_total_rows;							//Total rows in Table
	private int m_total_columns;						//Toatal Columns in Table
	private final int m_columns;						//Columns displayed in Table
	private final String m_strHeaders[];				//Headers of the table

	/**
	 * Constructor of the class.
	 * @param rows	Rows to be insert in Table
	 * @param columnHeaders		Headers of the table
	 */
	public PSATableDataModel(int irows, String istrcolumnHeaders[]) {
	    if ((irows < 0) || (istrcolumnHeaders == null)) {
	      throw new IllegalArgumentException(
	          "Invalid row count/columnHeaders");
	    }
	    this.m_rows = irows;
	    this.m_columns = istrcolumnHeaders.length;
	    m_total_columns = m_columns +1; 
	    m_strHeaders = istrcolumnHeaders;
	    lookup = new Hashtable();
	    clone_lookup = new Hashtable();
	  }
	
	/** 
	 * Provides Column count of the table
	 * @return Column Count 
	 */
	public int getColumnCount() {
	    return m_columns;
	}
	
	/** 
	 * Provides Row count of the table
	 * @return Row Count 
	 */
	public int getRowCount() {
	    return m_rows;
	}
	  
	 /**
	  * Sets the row count to total_rows and rows.
	 * @param row_count		Sets the total row count of table
	 */
	public void SetRowCount(int irow_count) {
		m_total_rows = m_rows = irow_count;
	}
	
	  /* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int icolumn) {
	    return m_strHeaders[icolumn];
	}
	
	/** 
	 * Provides the Value at specified row, column.
	 * @param row		row index of table to read the data
	 * @param column	column index of table to read the data
	 */
	public Object getValueAt(int irow, int icolumn) {
	    return lookup.get(new Point(irow, icolumn));
	  }
	
	/** 
	 * Clears table.
	 */
	public void Clear() {
		lookup.clear();
		m_rows = 0;
	  }

	/** 
	 * Sets the Value at specified row, column.
	 * @param value		Value to be set at [row][column] position in table.
	 * @param row		row index of table to read the data
	 * @param column	column index of table to read the data
	 */
	  @SuppressWarnings("unchecked")
	public void setValueAt(Object ivalue, int irow, int icolumn) {
	    if ((m_rows < 0) || (m_columns < 0)) {
	      throw new IllegalArgumentException("Invalid row/column setting");
	    }
	    if ((irow < m_total_rows) && (icolumn < m_total_columns)) {
	      lookup.put(new Point(irow, icolumn), ivalue);
	      clone_lookup.put(new Point(irow, icolumn), ivalue);
	    }
	  }
	
	/**
	 * Inserts array of data in table.
	 * @param new_data	Array of Data to insert
	 */
	public void Insert(Object[][] inew_data)
	{
		int count = inew_data.length;
		m_total_rows = m_rows = count;
		for(int i=0; i < count;i++)
		{
			int column = inew_data[i].length;
			for(int j=0;j<column;j++)
			{	
				Object temp_obj = inew_data[i][j];
				setValueAt(temp_obj,i,j);
			}
		}
	}
	
	/**
	 * Validates whether the data is within the range of start date and end date
	 * @param startDate		Start Date
	 * @param endDate		End Date
	 * @param testDate		Date to test within range
	 * @return		true - Date is within range.
	 * 				false - Dtae in not within range
	 */
	private boolean isDateWithinRange(Date startDate, Date endDate, Date testDate) { 
	    return (testDate.getTime() >= startDate.getTime() && 
	             testDate.getTime() <= endDate.getTime());
	}

	// --> Debut Modif [159AN - Gestion d'Acces à la MNU] Added to genrate pattern string from the string provided by user for filter. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
	/**
	 * Generates pattern string by replacing '*' with ".+".
	 * @param FilterStr		String which is used for filter.
	 * @return	String		String in Pattern format which will be used to mactch with table strings.
	 */
	private String PreparePatternString(String FilterStr)
	{
		String pattern_str = "";
		int len =FilterStr.length();

    	//Replace * with .+ in string in order to form pattern for Pattern class. 
		//'.' stands for any character and * stand for zero or more occurance of previous character.
		for(int i=0;i<len;i++)
		{
			if(FilterStr.charAt(i) == '*')
			{
				pattern_str += ".*";
				continue;
			} 
			pattern_str +=FilterStr.charAt(i);
		}
		return pattern_str;
	}
	// --> Fin Modif [159AN - Gestion d'Acces à la MNU] Added to genrate pattern string from the string provided by user for filter. [Mahendra Chuttar Geometric Limited 10-Dec-2010]

	/**
	 * Filter method for filteration of Reference or Description Single filter 
	 * @param str			String to Filter
	 * @param column_index	Colum index in which filter has to be applied
	 */
	@SuppressWarnings({ "unchecked", "unchecked", "unchecked" })
	public void Filter(String str, int column_index)
    {
    	if(0 == str.length()){
    		return;
    	}
		// --> Debut Modif [159AN - Gestion d'Acces à la MNU] Modified filtering using regex patterns. [Mahendra Chuttar Geometric Limited 10-Dec-2010]

		if(str.compareTo("*") ==0)
		{
			DisplayAllContains();
		} else
		{
    		String str1 = PreparePatternString(str);
  			System.out.println("String to be search : "+ str1);
			Pattern p = Pattern.compile(str1);

    		lookup.clear();
    		int k=0;
    		for(int i=0; i<m_total_rows;i++)
    		{
    			String data_element = clone_lookup.get(new Point(i, column_index)).toString();
				data_element = data_element.toUpperCase();
				//Validate if pattern provided by user matches with Element
				Matcher m = p.matcher(data_element);
    			boolean filter_status = m.matches();

				//If it matched then add the element in list
				if(false != filter_status){
    				for(int j=0; j<m_columns+1;j++)
    				{
    					lookup.put(new Point(k, j), clone_lookup.get(new Point(i, j)));
    				}
    				k++;
    			}
    		}
    		m_rows = k;

		}
		// --> Fin Modif [159AN - Gestion d'Acces à la MNU] Modified filtering using regex patterns. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
   		System.out.println("Filter complete");
    }

	/**
	 * Filter method for filteration of Date column within Start_date and End_Date 
	 * @param start_date	Start date
	 * @param End_date		End date
	 * @param column_index	Column index in which filter has to be applied
	 */
	@SuppressWarnings({ "unchecked", "unchecked", "unchecked", "deprecation" })
	public void Filter(Date start_date, Date End_date, int column_index)
    {
    	lookup.clear();
    	int k=0;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		boolean valid_date = false;
		
    	for(int i=0; i<m_total_rows;i++)
    	{
    		String data_element = clone_lookup.get(new Point(i, column_index)).toString();
    		Date table_date = new Date();
    		try {
    			formatter.setLenient(false);
    			table_date = formatter.parse(data_element);
    		} catch (ParseException e) {
    			continue;
    		}
    		//Check date is within range or not
    		valid_date =  isDateWithinRange(start_date, End_date, table_date);
			if(false != valid_date)
			{
    			for(int j=0; j<m_columns+1;j++)
    			{
    				lookup.put(new Point(k, j), clone_lookup.get(new Point(i, j)));
    			}
    			k++;
    		}
    	}
    	m_rows = k;
    	System.out.println("Filter complete");
    }
	
	/**
	 * Filter method for filteration of Reference and Description filter 
	 * @param str1			String1 to Filter
	 * @param column_index1	Column index for str1 in which filter has to be applied
	 * @param str2			String2 to Filter
	 * @param column_index2	Column index for str2 in which filter has to be applied
	 */
	@SuppressWarnings("unchecked")
	public void Filter(String str1, int column_index1, String str2, int column_index2)
    {
    	if(0 == str1.length() || 0 == str2.length()){
    		return;
    	}
		// --> Debut Modif [159AN - Gestion d'Acces à la MNU] Modified filtering using regex patterns. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
		if((0 == str1.compareTo("*"))&& (0 == str2.compareTo("*")))
		{
			DisplayAllContains();
			return;
		} 
 	   	//Replace * with .+ in string in order to form pattern for Pattern class. 
		//'.' stands for any character and + stand for one or more occurance of previous character.
   		String string1 = PreparePatternString(str1);
   		String string2 = PreparePatternString(str2);
		System.out.println("String 1 : "+ string1 + " String 2 :"+ string2);

		Pattern p1 = Pattern.compile(string1);
		Pattern p2 = Pattern.compile(string2);

		lookup.clear();
		int k=0;
		for(int i=0; i<m_total_rows;i++)
		{
			String data_element1 = clone_lookup.get(new Point(i, column_index1)).toString();
			data_element1 = data_element1.toUpperCase();
    		String data_element2 = clone_lookup.get(new Point(i, column_index2)).toString();
			data_element2 = data_element2.toUpperCase();

			Matcher m1 = p1.matcher(data_element1);
			Matcher m2 = p2.matcher(data_element2);

			boolean filter_status1 = m1.matches();
			boolean filter_status2 = m2.matches();

			if(false != filter_status1 && false != filter_status2 )
			{
				for(int j=0; j<m_columns+1;j++)
				{
					lookup.put(new Point(k, j), clone_lookup.get(new Point(i, j)));
				}
				k++;
			}
		}
		m_rows = k;
 		// --> Fin Modif [159AN - Gestion d'Acces à la MNU] Modified filtering using regex patterns. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
   		System.out.println("Filter complete");
    }
	
	/**
	 * Filter method for filteration of Date column within Start_date and End_Date  with reference or description
	 * @param str				String to Filter
	 * @param column_index		Column index for str in which filter has to be applied
	 * @param start_date		Start date
	 * @param End_date			End date
	 * @param datecolumn_index	Date Column index in which filter has to be applied
	 */
	@SuppressWarnings({ "unchecked", "unchecked", "unchecked", "deprecation" })
	public void Filter(String str, int strcolumn_index, Date start_date, Date End_date, int datecolumn_index)
    {
 		// --> Debut Modif [159AN - Gestion d'Acces à la MNU] Modified filtering using regex patterns. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
    	if((0 == str.length()) || (0 == str.compareTo("*")))
		{
    		Filter(start_date,End_date,datecolumn_index);
     		return;
    	} 

		//Replace * with .+ in string in order to form pattern for Pattern class. 
		//'.' stands for any character and + stand for one or more occurance of previoys character.
   		String str1 = PreparePatternString(str);
		System.out.println("String to be search : "+ str1);
		Pattern p = Pattern.compile(str1);

		lookup.clear();
		int k=0;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		boolean valid_date = false;
		for(int i=0; i<m_total_rows;i++)
		{

			String data_element = clone_lookup.get(new Point(i, strcolumn_index)).toString();
			data_element = data_element.toUpperCase();
			String date_data_element = clone_lookup.get(new Point(i, datecolumn_index)).toString();
			Date table_date = new Date();
			try {
				formatter.setLenient(false);
				table_date = formatter.parse(date_data_element);
			} catch (ParseException e) {
				continue;
			}

			//Check date is within range or not
			valid_date =  isDateWithinRange(start_date, End_date, table_date);
    		
			//Validate String with row contents.
			Matcher m = p.matcher(data_element);
			boolean filter_status = m.matches();

			if((false != valid_date) && (false != filter_status))
			{
				//Date and String validation success.
				for(int j=0; j<m_columns+1;j++)
				{
					lookup.put(new Point(k, j), clone_lookup.get(new Point(i, j)));
				}
				k++;
			}
		}

		m_rows = k;
  		// --> Fin Modif [159AN - Gestion d'Acces à la MNU] Modified filtering using regex patterns. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
   		System.out.println("Filter complete");
    }

	/**
	 * Filter method for filteration of Date column within Start_date and End_Date with reference or description
	 * @param str1				String1 to Filter
	 * @param column1_index		Column index for str1 in which filter has to be applied
	 * @param str2				String2 to Filter
	 * @param column2_index		Column index for str2 in which filter has to be applied
	 * @param start_date		Start date
	 * @param End_date			End date
	 * @param datecolumn_index	Date Column index in which filter has to be applied
	 */
	//Date Filter with Reference and Description
	@SuppressWarnings({ "unchecked", "unchecked", "unchecked", "deprecation" })
	public void Filter(String str1, int str1column_index, String str2, int str2column_index, Date start_date, Date End_date, int datecolumn_index)
    {
 		// --> Debut Modif [159AN - Gestion d'Acces à la MNU] Modified filtering using regex patterns. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
    	if((0 == str1.length()) && (0 == str2.length()) || ((0 == str1.compareTo("*")) && (0 == str2.compareTo("*"))))
    	{
    		Filter(start_date,End_date,datecolumn_index);
    		return;
    	}

 	   	//Replace * with .+ in string in order to form pattern for Pattern class. 
		//'.' stands for any character and + stand for one or more occurance of previoys character.
   		String string1 = PreparePatternString(str1);
   		String string2 = PreparePatternString(str2);
		System.out.println("String 1 : "+ string1 + " String 2 :"+ string2);

		Pattern p1 = Pattern.compile(string1);
		Pattern p2 = Pattern.compile(string2);

    	lookup.clear();
    	
    	int k=0;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		boolean valid_date = false;
		
    	for(int i=0; i<m_total_rows;i++)
    	{
    		String data_element1 = clone_lookup.get(new Point(i, str1column_index)).toString();
			data_element1 = data_element1.toUpperCase();
    		String data_element2 = clone_lookup.get(new Point(i, str2column_index)).toString();
			data_element2 = data_element2.toUpperCase();
    		String date_data_element = clone_lookup.get(new Point(i, datecolumn_index)).toString();
    		
    		Date table_date = new Date();
    		try {
    			formatter.setLenient(false);
    			table_date = formatter.parse(date_data_element);
    		} catch (ParseException e) {
    			continue;
    		}
    		
    		//Check date is within range or not
    		valid_date =  isDateWithinRange(start_date, End_date, table_date);

			//Validate strings for the row.
			Matcher m1 = p1.matcher(data_element1);
			Matcher m2 = p2.matcher(data_element2);

			boolean filter_status1 = m1.matches();
			boolean filter_status2 = m2.matches();
    		
    		if((false != valid_date) && (false != filter_status1) && (false != filter_status2))
			{
				//Date and String validation success.
    			for(int j=0; j<m_columns+1;j++)
    			{
    				lookup.put(new Point(k, j), clone_lookup.get(new Point(i, j)));
    			}
    			k++;
    		}
    	}
 		// --> Fin Modif [159AN - Gestion d'Acces à la MNU] Modified filtering using regex patterns. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
    	m_rows = k;
    	System.out.println("Filter complete");
    }

	// --> Debut Modif [159AN - Gestion d'Acces à la MNU] Added function to display all the contents of the table. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
	/** 
	 * Display all the contents of the table.
	 */
	public void DisplayAllContains()
	{
		if(m_rows == m_total_rows){
			return;
		}
		for(int i=0; i<m_total_rows;i++)
    	{
			for(int j=0; j<m_columns+1;j++)
			{
				lookup.put(new Point(i, j), clone_lookup.get(new Point(i, j)));
			}
    	}
		m_rows = m_total_rows;
	}
	// --> Fin Modif [159AN - Gestion d'Acces à la MNU] Added function to display all the contents of the table. [Mahendra Chuttar Geometric Limited 10-Dec-2010]

}

