//COPYRIGHT PSA Peugeot Citroen 2010
/**********************************************************************************************************
 *
 * FILE NAME	: PSADatePicker.java
 *
 * SOCIETE        : PSA
 * PROJET         : 151AP - Gestion d'Accès à la MNU Vues Fournisseurs - UI
 * VERSION        : V1.0
 * DATE           : 20/07/2010
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    : Class for Calender Control (Date Picker)
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/

package com.psa.tools;

import com.psa.tools.PSALanguageHandler;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.text.*;

/**
 * Class for Calender Control (Date Picker)
 *  
 * @author      Mahendra Chuttar
 *  
 * @version     %I%, %G%
 *  
 * @since       1.0
 */
public class PSADatePicker 
{
	JButton[] btn = new JButton[49];
	int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
	int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);;
	int current_month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
	int current_year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
	int current_day = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);
	JLabel lbl = new JLabel("",JLabel.CENTER);
	String day = "";
	JDialog dlg;
	JButton prevYearBtn;
	JButton prevMonthBtn;
	boolean Date_enableDisable;
	
	/**
	 * Constructor of the class.
	 * @param 	parent		Parent Frame where Date Picker is displayed
	 * @param 	iController		Controller Object
	 */
	public PSADatePicker(JFrame parent,PSALanguageHandler iLanguageHandler)
	{
		Date_enableDisable = false;
		CreateDialog(parent,iLanguageHandler);
	}

	/**
	 * Constructor of the class.
	 * @param 	parent		Parent Frame where Date Picker is displayed
	 * @param 	current_date		Start date to be displaed.
	 * @param 	iController		Controller Object
	 */
	@SuppressWarnings("deprecation")
	public PSADatePicker(JFrame parent, String current_date,PSALanguageHandler iLanguageHandler)
	{
		DateFormat formatter; 
	    Date date; 
	    formatter = new SimpleDateFormat("dd/MM/yyyy");
	    try {
	    	if(current_date.length() > 0){
	    		formatter.setLenient(false);
	    		date = (Date)formatter.parse(current_date);
	    		month = date.getMonth();
	    		year = date.getYear()+1900;
	    		System.out.println("month = "+month+" year = "+year);
	    	}
		} catch (ParseException e) {
			e.printStackTrace();
		}   		
		Date_enableDisable = true;
		CreateDialog(parent,iLanguageHandler);
	}
	
	/**
	 * Crates the Panel for Calender control.
	 * @param 	parent		Parent Frame where Date Picker is displayed
	 * @param 	iController		Controller Object
	 */
	private void CreateDialog(JFrame parent,PSALanguageHandler iLanguageHandler)
	{
		dlg = new JDialog();
		dlg.setModal(true);
		dlg.setResizable(false);
		dlg.setBackground(new java.awt.Color(236,233,216));
		
		String[] header = {
				iLanguageHandler.Getlabel("Label.Sunday","Sun"),
				iLanguageHandler.Getlabel("Label.Monday","Mon"),
				iLanguageHandler.Getlabel("Label.Tuesday","Tue"),
				iLanguageHandler.Getlabel("Label.Wedensday","Wed"),
				iLanguageHandler.Getlabel("Label.Thursday","Thur"),
				iLanguageHandler.Getlabel("Label.Friday","Fri"),
				iLanguageHandler.Getlabel("Label.Saturday","Sat")};
		JPanel topPanel = new JPanel(new GridLayout(1,2));
		
		JPanel midPanel = new JPanel(new GridLayout(7,7));
		midPanel.setPreferredSize(new Dimension(400,400));
		for(int x = 0; x < btn.length; x++)
		{
			final int selection = x;
			btn[x] = new JButton();
			btn[x].setFocusPainted(false);
			if(x>6)btn[x].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
				//displayDatePicked(btn[selection].getActionCommand());}});
					day = btn[selection].getActionCommand();
					dlg.dispose();}
				});
			if(x < 7)
			{
				btn[x].setFont(new Font("Lucida",Font.PLAIN,10)); 
				btn[x].setText(header[x]);
			}
			midPanel.add(btn[x]);
		}
		
		topPanel.add(lbl);

		JPanel lowPanel = new JPanel(new GridLayout(1,3));
		prevYearBtn = new JButton("<<");
		
		if(Date_enableDisable){
			if(year <= current_year){
				prevYearBtn.setEnabled(false);
			} else
			{
				prevYearBtn.setEnabled(true);
			}
		}
		
		prevYearBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				year--;
				setDates();
			}
		});
		lowPanel.add(prevYearBtn);

		prevMonthBtn = new JButton("<");
		prevMonthBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				month--;
				setDates();
			}
		});
		if(Date_enableDisable){
			if(year <= current_year && month <= current_month){
				prevMonthBtn.setEnabled(false);
			} else
			{
				prevMonthBtn.setEnabled(true);
			}
		}
		
		lowPanel.add(prevMonthBtn);
		
		JButton nextMonthBtn = new JButton(">");
		nextMonthBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				month++;
				setDates();
			}
		});
		lowPanel.add(nextMonthBtn);
		
		JButton nextYearBtn = new JButton(">>");
		nextYearBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				year++;
				setDates();
			}
		});
		lowPanel.add(nextYearBtn);

		dlg.add(topPanel,BorderLayout.NORTH);
		dlg.add(midPanel,BorderLayout.CENTER);
		dlg.add(lowPanel,BorderLayout.SOUTH);
		
		dlg.pack();
		dlg.setLocationRelativeTo(parent);
		setDates();
		dlg.setVisible(true);
	}
  
	/**
	 * Sets dates as per selection of Month and Year 
	 */
	public void setDates()
	{
		for(int x = 7; x < btn.length; x++) 
		{
			btn[x].setText("");
			btn[x].setVisible(false);
			btn[x].setEnabled(true);
		}
		
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM yyyy");
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(year,month,1);
		int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
		int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
		for(int x = 6+dayOfWeek,day = 1; day <= daysInMonth; x++,day++)
		{
			btn[x].setText(""+day);
			btn[x].setVisible(true);
			if(Date_enableDisable){
				if(year < current_year)
				{
					btn[x].setEnabled(false);
				} 
				else if(year == current_year)
				{
					if(month < current_month)
					{
						btn[x].setEnabled(false);
					}
					else if(day <= current_day && month == current_month)
					{
						btn[x].setEnabled(false);
					}
				}
			}
		}
		month = cal.get(java.util.Calendar.MONTH);
		year = cal.get(java.util.Calendar.YEAR);
		
		if(Date_enableDisable)
		{
			if(year <= current_year){
				prevYearBtn.setEnabled(false);
			}else {
				prevYearBtn.setEnabled(true);
			}
			
			if(year < current_year){
				prevMonthBtn.setEnabled(false);
			} else if(month <= current_month && year == current_year){
				prevMonthBtn.setEnabled(false);
			} else {
				prevMonthBtn.setEnabled(true);
			}
		}
		lbl.setText(sdf.format(cal.getTime()));
		dlg.setTitle(lbl.getText());
	}

	/**
	 * Retrives the Date selected from Calender Control 
	 * @return	Selected Date
	 */
	public String GetDateSelected()
	{
		if(day.equals("")) 
			return day;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(year,month,Integer.parseInt(day));
		return sdf.format(cal.getTime());
	}
}
