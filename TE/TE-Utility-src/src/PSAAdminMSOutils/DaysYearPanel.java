/**********************************************************************************************************
 *
 * FILE NAME	  : DaysYearPanel.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class used to display year date on TimeSlotSelection panel.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/
package com.psa.PSAAdminMSOutils;

//Standard class import
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

//User class import
import com.psa.tools.PSADatePicker;
import com.psa.tools.PSALanguageHandler;

/**
* Class used to display year date on TimeSlotSelection panel.
* It gives the delete date list and add date list.
*/
@SuppressWarnings("serial")
public class DaysYearPanel extends JPanel {
	private final String dateFormatDayMonthYear = "dd/MM/yyyy";
	private final String dateFormatYearMonthDay = "yyyy/MM/dd";
	private JButton m_ButtonSelect;								//Select button
	private JButton m_ButtonRemove;								//Remove button
	private JLabel m_LabelSelection;							//Label for selection
	private JList m_ListSelected;								//Selected date list
	private JScrollPane m_ScrollPaneSelected;					//Selected date scroll pane
	private PSALanguageHandler m_ObjLanguageHandler = null;		//Language Handler Object
	private PSARequestController m_ObjRequestController = null;	//RequestController 
	private JFrame m_FrameParent;								//Parent window
	private DefaultListModel defaultListModel;					//Default list model
	private ArrayList<String> m_RemoveArrayList;				//Removed date list	
	private ArrayList<String> m_AddArrayList;					//Added date list
	private ButtonEnableListener m_buttonEnableListener;		//Button Listener
	public  String m_StrExpiryDate;
	
	/**
	 * Constructor of the class
	 * @param frameParent        : Parent window of this panel.
	 * @param psaLanguageHandler : Languge handler to show label on the panel.
	 */
	public DaysYearPanel(JFrame frameParent ,PSARequestController psaRequestController ) {
		super();
		m_ObjRequestController = psaRequestController;
		m_ObjLanguageHandler = psaRequestController.m_ObjLanguageHandler;
		initGUI();
		m_FrameParent = frameParent;
		m_RemoveArrayList = new ArrayList<String>();
		m_AddArrayList = new ArrayList<String>();
	}

	/**
	 * It  creates and initialize components. 
	 */
	private void initGUI() {
		try {
					
				GridBagLayout panelDateSelectionLayout = new GridBagLayout();
				panelDateSelectionLayout.rowWeights = new double[] {0.0, 0.0, 0.1, 0.0, 0.0, 0.0, 0.1};
				panelDateSelectionLayout.rowHeights = new int[] {9, 30, 7, 35, 24, 35, 7};
				panelDateSelectionLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1};
				panelDateSelectionLayout.columnWidths = new int[] {60, 60, 50, 60, 60};
				this.setLayout(panelDateSelectionLayout);
				this.setPreferredSize(new java.awt.Dimension(398, 210));
									
				//Label Date Choosen
				m_LabelSelection = new JLabel();
				this.add(m_LabelSelection, new GridBagConstraints(3, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
				m_LabelSelection.setText("Date Choosen");
				m_LabelSelection.setText(m_ObjLanguageHandler.Getlabel("Label.DateChoosen","Date Choosen"));
				m_LabelSelection.setHorizontalAlignment(SwingConstants.CENTER);
				
				//Choosen Date
				defaultListModel = new DefaultListModel();
				m_ListSelected = new JList(defaultListModel);
				m_ListSelected.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
				m_ListSelected.setSize(90, 204);
				m_ListSelected.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				m_ScrollPaneSelected = new JScrollPane(m_ListSelected);
				add(m_ScrollPaneSelected, new GridBagConstraints(3, 2, 2, 5, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
				
				//Select Button 
				m_ButtonSelect = new JButton();
				this.add(m_ButtonSelect, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 0, 0));
				m_ButtonSelect.setText("Select Date");
				m_ButtonSelect.setText(m_ObjLanguageHandler.Getlabel("Label.SelectDate","Select Date"));
				m_ButtonSelect.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						selectActionPerformed(evt);
					}
				});
					
				//Remove button
				m_ButtonRemove = new JButton();
				this.add(m_ButtonRemove, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 0, 0));
				m_ButtonRemove.setText("Remove");
				m_ButtonRemove.setText(m_ObjLanguageHandler.Getlabel("Label.RemoveDate","Remove"));
				m_ButtonRemove.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						removeActionPerformed(evt);
					}
				});
				CheckRemoveButtons();
				
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Add the frequency list from selected date to expiry date.
	 * @param iStrSelectedDate		Selected Date
	 */
	@SuppressWarnings("deprecation")
	private void addYearFreq(String iStrSelectedDate)
	{
		//Get selected date.
		m_StrExpiryDate = m_ObjRequestController.m_CurrentOpr.getExpiryDate();
		System.out.println("Selected Date : " + iStrSelectedDate);
		
		if(iStrSelectedDate.length() > 0)
		{
				Object[] date_conv = UtilityFunctions.String_to_date_Conversion(iStrSelectedDate, dateFormatDayMonthYear, m_ObjLanguageHandler);
				Object[] expDate_conv = UtilityFunctions.String_to_date_Conversion(m_StrExpiryDate, dateFormatYearMonthDay, m_ObjLanguageHandler);
	
				if(((Boolean)expDate_conv[0]).booleanValue() ==  false || ((Boolean)date_conv[0]).booleanValue() ==  false)
				{
					return;
				}
				
				Date selected_date = (Date)date_conv[1];
				Date expiryDate = (Date)expDate_conv[1];
				//To check selected date is 
				if(selected_date.compareTo(expiryDate) > 0)
				{
					m_ObjRequestController.displayMessage("Error.InvalidSelectDate");
					return;
				}
			
				//Extract Years for selected date and expiry date.
			 	int  expireYear = expiryDate.getYear()+1900;
			 	int  startYear = selected_date.getYear()+1900;

				//Set calender for start date
			 	Calendar calendar = Calendar.getInstance();
			 	calendar.set(Calendar.MONTH, selected_date.getMonth());
			 	calendar.set(Calendar.DATE,selected_date.getDate());

				for (int year = startYear; year <= expireYear; year++) 
				{
					//Set Year				
					calendar.set(Calendar.YEAR,year);
					//Get date in yyyy/MM/dd format
					String strTempDate = UtilityFunctions.getFormatedDate(calendar, dateFormatYearMonthDay);
					
					//Covert to Date format for validation
					Object[] temp_date_conv = UtilityFunctions.String_to_date_Conversion(strTempDate, dateFormatYearMonthDay, m_ObjLanguageHandler);
					if(((Boolean)temp_date_conv[0]).booleanValue() ==  false )
					{
						continue;
					}
					Date temp_date = (Date)temp_date_conv[1];
					if(temp_date.compareTo(expiryDate) > 0 )
					{
						continue;
					}
					
					//Check date is exist or not in list.
					if(!defaultListModel.contains(strTempDate))
					{
						 //Add the date to list.
						defaultListModel.addElement(strTempDate);
						//Check date is exist in remove date list.
						 if(false == m_RemoveArrayList.contains(strTempDate))
						 {
							//Add the date to  Add List.	
							 m_AddArrayList.add(strTempDate);
						 }
						 else
						 {
							//Remove from remove list.	
							 m_RemoveArrayList.remove(strTempDate);
						 }
					}
			
				}
		 }		
	}
	
	/**
	 * It used  to add selected date  to list.
	 * @param evt : Select button action event
	 */
	private void selectActionPerformed(ActionEvent evt) {
		 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		 Date date = new Date();
		 String strCurrentDate = dateFormat.format(date);
		 //Display date picker.
		 PSADatePicker psaDatePicker =  new PSADatePicker(m_FrameParent, strCurrentDate ,m_ObjLanguageHandler);
		 String selectedDate = psaDatePicker.GetDateSelected();
		 addYearFreq(selectedDate);
	 
		 if(m_buttonEnableListener != null)
		 {
			 //fire the event to listener.	
			 m_buttonEnableListener.fireButtonEnableEvent(evt);
		 }
		 CheckRemoveButtons();
	}
	
	/**
	 * It used  to remove selected date  from list.
	 * @param evt : Remove button action event
	 */
	private void removeActionPerformed(ActionEvent evt) {
		int index = m_ListSelected.getSelectedIndex();
		if(index >= 0)
		{
			//Get selected date from datelist.
			String str = m_ListSelected.getSelectedValue().toString();
			System.out.println("DeSelected Site ="+str);
			
			//remove from date list
			defaultListModel.removeElementAt(index);
			//Check date is exist in add date list.
			if(false == m_AddArrayList.contains(str))
			{
				//Add the date to  Deleted List.
				m_RemoveArrayList.add(str);
			}
			else
			{
				//Remove from add list.
				m_AddArrayList.remove(str);
			}
			if(m_buttonEnableListener != null)
			{
				//fire the event to listener.	
				m_buttonEnableListener.fireButtonEnableEvent(evt);
			}
		} 
		else
		{
			System.out.println("No item selected in list.");
		}
		CheckRemoveButtons();
		
	}

	/**
	 * It used to return selected date list.
	 * @return ArrayList<String> : list of selected date. 
	 */
	public ArrayList<String> getSelectedDateList() {
		ArrayList<String> selectedList = new ArrayList<String>();
		int size = defaultListModel.getSize();
		for (int index = 0; index < size; index++) {
			selectedList.add((String)defaultListModel.get(index));
		}
		return selectedList;
		
	}

	/**
	 * It adds selected date to the List
	 * @param dayList : Selected date list
	 */
	public void setSelectedValues(ArrayList<String> dayList) {
		defaultListModel.clear();
		if(null != dayList && dayList.size() > 0)
		{
			int size = dayList.size();
			for(int index=0; index < size; index++)
			{
				defaultListModel.addElement(dayList.get(index));
			}
			m_ListSelected.setSelectedIndex(0);
			CheckRemoveButtons();
		}
	}
	
	/**
	 * @return deleted item list.
	 */
	public ArrayList<String> getDeletedList()
	{
		return m_RemoveArrayList;
	}

	/**
	 * @return added item list.
	 */
	public ArrayList<String> getAddedList()
	{
		return m_AddArrayList;
	}

    /**
     * It does enable or disable remove button. 
     */
	private void CheckRemoveButtons() {
		m_ButtonRemove.setEnabled(defaultListModel.size() > 0 ? true : false);
	}
	
	/**
	 * This is register the listener. 
	 * @param buttonEnableListener : ButtonEnableListener  
	 */
	public void addButtonEnabledListener(ButtonEnableListener buttonEnableListener)
	{
		m_buttonEnableListener =   buttonEnableListener;
	}

	/**
	 * Set the expiry date from clone opr.
	 */
	public void setPrevExpiryDate(String iStrExpiryDate)
	{
		m_StrExpiryDate = iStrExpiryDate;
	}
	
	/**
	 * Get the expiry date for comparision.
	 */
	public String getExpiryDate()
	{
		return m_StrExpiryDate;
	}
}
