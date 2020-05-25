/**********************************************************************************************************
 *
 * FILE NAME	  : PSASiteTimeSlotSelection.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class used to display  TimeSlotSelection and site selection panel.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/
package com.psa.PSAAdminMSOutils;

//Standard class import
import java.awt.AWTEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

//User class import
import com.psa.tools.PSAErrorHandler;
import com.psa.tools.PSALanguageHandler;

/**
 * 
 * Class used to display  TimeSlotSelection and site selection panel.
 */
public class PSASiteTimeSlotSelection extends PSARequestBasePanel{
	
	private JFrame SiteTimeSlotSelectionPanel;
	
	private JPanel m_PanelTimeSlotSelection;
	private JLabel m_LabelTimeSlotSelection;
	private JPanel m_PanelRadioButton;
	private JPanel m_PanelDate;
	private JPanel m_DateTitlePanel;
	private JPanel m_PanelDaysList;
	private JRadioButton m_RadioDaysPerYear;
	private JRadioButton m_RadioDaysPerMonth;
	private JRadioButton m_RadioDaysPerWeek;
	private ButtonGroup m_ButtonGroupDate;
	private PSADoubleList m_ListDayPerWeek;
	private PSADoubleList m_ListDayPerMonth;
	private DaysYearPanel m_PanelDaysYear;
	
	private JPanel m_PanelSiteSelection;
	private JLabel m_LabelSiteSelection;
	private PSADoubleList m_ListSite;
	
	private JPanel m_PanelButtons;
	private JButton m_ButtonBack;
	private JButton m_ButtonNext;
	
	//week days 
	String  weekDays[] = {"MONDAY" , "TUESDAY", "WEDNESDAY", "THURSDAY","FRIDAY"};

	ArrayList<String> weekDaysList;
	ArrayList<String> monthDaysList;
	
	/**
	 * Constructor of the class.
	 * @param iController : PSARequestController
	 */
	PSASiteTimeSlotSelection(PSARequestController iController) throws PSAErrorHandler
	{
		super(iController);
		System.out.println("PSASiteTimeSlotSelection::PSASiteTimeSlotSelection");
		initializeDayList();
		initGUI();
	}
	
	/**
	 * It initialize the value in week days and  month days list.
	 */
	private void initializeDayList() {
		
		//set week days.
		weekDays[0] = m_Controller.m_ObjLanguageHandler.Getlabel("Label.Monday","MONDAY");
		weekDays[1] = m_Controller.m_ObjLanguageHandler.Getlabel("Label.Tuesday","TUESDAY");
		weekDays[2] = m_Controller.m_ObjLanguageHandler.Getlabel("Label.Wednesday","WEDNESDAY");
		weekDays[3] = m_Controller.m_ObjLanguageHandler.Getlabel("Label.Thursday","THURSDAY");
		weekDays[4] = m_Controller.m_ObjLanguageHandler.Getlabel("Label.Friday","FRIDAY");
		weekDaysList = new ArrayList<String>();
		for(int index=0;index<weekDays.length;index++)
		{
			weekDaysList.add(weekDays[index]);
		}
		
		//set month days.
		monthDaysList = new ArrayList<String>();
		String  daysCount[] = {"FIRST" , "SECOND", "THIRD", "FOURTH"};
		daysCount[0] = m_Controller.m_ObjLanguageHandler.Getlabel("Label.First","FIRST");
		daysCount[1] = m_Controller.m_ObjLanguageHandler.Getlabel("Label.Second","SECOND");
		daysCount[2] = m_Controller.m_ObjLanguageHandler.Getlabel("Label.Third","THIRD");
		daysCount[3] = m_Controller.m_ObjLanguageHandler.Getlabel("Label.Fourth","FOURTH");
		
		for(int count=0;count<daysCount.length;count++)
		{
			String strTemp = daysCount[count];
			for(int index=0;index<weekDays.length;index++)
			{
				monthDaysList.add(strTemp + " "+weekDays[index]);
			}
		}
		
	}

	/**
	 * It  creates and initialize components. 
	 */
	private void initGUI() throws PSAErrorHandler
	{
		try 
		{
			PSALanguageHandler lang_handler = m_Controller.m_ObjLanguageHandler;
			SiteTimeSlotSelectionPanel =  new JFrame();
			GridBagLayout SiteTimeSlotSelectionPanelLayout = new GridBagLayout();
			SiteTimeSlotSelectionPanel.getContentPane().setLayout(SiteTimeSlotSelectionPanelLayout);
			SiteTimeSlotSelectionPanel.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			int siteselectionpos = 0;
			if(m_Controller.m_CurrentOpr.getFilterPartListSize() > 0)
			{
				SiteTimeSlotSelectionPanelLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.0, 0.0};
				SiteTimeSlotSelectionPanelLayout.rowHeights = new int[] {7, 7, 7, 7, 7, 7, 7, 7, 39, 57};
				SiteTimeSlotSelectionPanelLayout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.1, 0.1};
				SiteTimeSlotSelectionPanelLayout.columnWidths = new int[] {80, 80, 80, 80, 80, 80, 80, 80};
				SiteTimeSlotSelectionPanel.setPreferredSize(new java.awt.Dimension(682, 400));
				SiteTimeSlotSelectionPanel.setSize(682, 400);
				siteselectionpos = 4;
				
				DisplayTimeSlotPanel();
			} else
			{
				SiteTimeSlotSelectionPanelLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.0, 0.0};
				SiteTimeSlotSelectionPanelLayout.rowHeights = new int[] {7, 7, 7, 7, 7, 7, 7, 7, 39, 57};
				SiteTimeSlotSelectionPanelLayout.columnWeights = new double[] {0.0, 0.1, 0.1, 0.1};
				SiteTimeSlotSelectionPanelLayout.columnWidths = new int[] {80, 80, 80, 80};
				SiteTimeSlotSelectionPanel.setPreferredSize(new java.awt.Dimension(382, 400));
				SiteTimeSlotSelectionPanel.setSize(382, 400);
			}
						
			DisplaySiteSelectionPanel(siteselectionpos);
			DisplayButtons();
			
			SiteTimeSlotSelectionPanel.setResizable(false);
			if(m_Controller.GetActionType() == ACTION.CREATEREQUEST)
			{
				SiteTimeSlotSelectionPanel.setTitle(lang_handler.Getlabel("Label.NewRequestCreation","New Request Creation"));
			}else {
				SiteTimeSlotSelectionPanel.setTitle(lang_handler.Getlabel("Label.RequestModificationPSN","Modification of Request / PSN"));
			}
		} catch(PSAErrorHandler e)
		{
			throw e;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * It creates button panel.
	 */
	private void DisplayButtons() {
		//Button panel
		m_PanelButtons = new JPanel();
		GridBagLayout jPanelButtonsLayout = new GridBagLayout();
		SiteTimeSlotSelectionPanel.getContentPane().add(m_PanelButtons, new GridBagConstraints(0, 9, 8, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		m_PanelButtons.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
		jPanelButtonsLayout.rowWeights = new double[] {0.0, 0.0};
		jPanelButtonsLayout.rowHeights = new int[] {5, 44};
		jPanelButtonsLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
		jPanelButtonsLayout.columnWidths = new int[] {7, 7, 7, 7};
		m_PanelButtons.setLayout(jPanelButtonsLayout);
		
		//Back button
		m_ButtonBack = new JButton();
		m_PanelButtons.add(m_ButtonBack, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		//jButtonBack.setSize(50, 21);
		//jButtonBack.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.BackButton","BACK"));
		m_ButtonBack.setToolTipText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.BackButton","BACK"));
		m_ButtonBack.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/resultset_previous.png")));
		m_ButtonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				BackActionCallback(evt);
			}
		});
		
		//Next Button
		m_ButtonNext = new JButton();
		m_PanelButtons.add(m_ButtonNext, new GridBagConstraints(2, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		//jButtonNext.setSize(50, 21);
		//jButtonNext.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.NextButton","Next"));
		m_ButtonNext.setToolTipText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.NextButton","Next"));
		m_ButtonNext.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/resultset_next.png")));
		m_ButtonNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				NextActionCallback(evt);
			}
		});
	}

	/**
	 * It creates Sites panel.
	 * @param pos : int 
	 */
	private void DisplaySiteSelectionPanel(int pos) throws PSAErrorHandler
	{
		//Site selection panel.
		m_PanelSiteSelection = new JPanel();
		m_PanelSiteSelection.setSize(new java.awt.Dimension(591, 321));
		BorderLayout jPanelSiteSelectionLayout = new BorderLayout();
		m_PanelSiteSelection.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
		m_PanelSiteSelection.setLayout(jPanelSiteSelectionLayout);
		
		//Site selection label.
		m_LabelSiteSelection = new JLabel();
		m_PanelSiteSelection.add(m_LabelSiteSelection, BorderLayout.NORTH);
		m_LabelSiteSelection.setText("Select Site(s) for Subscription");
		m_LabelSiteSelection.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.SiteSubscription","Select Site(s) for Subscription"));
		m_LabelSiteSelection.setHorizontalAlignment(SwingConstants.CENTER);
		m_LabelSiteSelection.setPreferredSize(new java.awt.Dimension(303, 22));
		
		//Site list
		m_ListSite = new PSADoubleList(m_Controller.m_ObjLanguageHandler.Getlabel("Label.SiteAvailable","Site Available"),m_Controller.m_ObjLanguageHandler.Getlabel("Label.SiteChoosen","Site Choosen"));
		m_PanelSiteSelection.add(m_ListSite, BorderLayout.CENTER);
		m_ListSite.setToolTipLeftToRight(m_Controller.m_ObjLanguageHandler.Getlabel("Label.Select","Select"));
		m_ListSite.setToolTipRightToLeft(m_Controller.m_ObjLanguageHandler.Getlabel("Label.Deselect","Deselect"));
		m_ListSite.setPreferredSize(new java.awt.Dimension(326, 247));
		m_ListSite.addButtonEnabledListener(new ButtonEnableListener() {
			
			public void fireButtonEnableEvent(AWTEvent event) {
				EnableNextButton();
				
			}
		});
		
		SiteTimeSlotSelectionPanel.getContentPane().add(m_PanelSiteSelection, new GridBagConstraints(pos, 0, 4, 9, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		InsertSites();
		
	}

	/**
	 * It creates Time slot panel.
	 */
	private void DisplayTimeSlotPanel() throws PSAErrorHandler 
	{
		
		//Time Slot panel.
		m_PanelTimeSlotSelection = new JPanel();
		BorderLayout jPanelTimeSlotSelectionLayout = new BorderLayout();
		m_PanelTimeSlotSelection.setLayout(jPanelTimeSlotSelectionLayout);
		m_PanelTimeSlotSelection.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
		
		//Date Title panel
		m_DateTitlePanel = new JPanel();
		m_PanelTimeSlotSelection.add(m_DateTitlePanel, BorderLayout.NORTH);
		m_PanelTimeSlotSelection.setPreferredSize(new java.awt.Dimension(306, 27));
		
		//Label Time Slot Selection
		m_LabelTimeSlotSelection = new JLabel();
		m_LabelTimeSlotSelection.setHorizontalAlignment(SwingConstants.CENTER);
		m_LabelTimeSlotSelection.setHorizontalTextPosition(SwingConstants.CENTER);
		m_LabelTimeSlotSelection.setSize(240, 16);
		m_LabelTimeSlotSelection.setText("Time Slot Selection");
		m_LabelTimeSlotSelection.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.TimeSlotSelection","Time Slot Selection"));
		m_DateTitlePanel.add(m_LabelTimeSlotSelection);

		//Date Panel
		m_PanelDate = new JPanel();
		BorderLayout m_PanelDateLayout = new BorderLayout();
		m_PanelDate.setLayout(m_PanelDateLayout);
		m_PanelTimeSlotSelection.add(m_PanelDate, BorderLayout.CENTER);
		m_PanelDate.setPreferredSize(new java.awt.Dimension(306, 263));
			
		//Radio button panel.
		m_PanelRadioButton = new JPanel();
		BoxLayout m_PanelRadioButtonLayout = new BoxLayout(m_PanelRadioButton, javax.swing.BoxLayout.Y_AXIS);
		m_PanelRadioButton.setLayout(m_PanelRadioButtonLayout);
		m_PanelDate.add(m_PanelRadioButton, BorderLayout.NORTH);
		m_PanelRadioButton.setPreferredSize(new java.awt.Dimension(306, 70));
		
		//Read frequency detail  from database.
		ArrayList<String> freqTypeArrayList = new ArrayList<String>(); 
		ArrayList<String> freqDetailsArrayList = new ArrayList<String>();
		
		m_Controller.m_CurrentOpr.ReadRequestDesc(freqTypeArrayList, freqDetailsArrayList);
		
		//Radio button group.
		m_ButtonGroupDate = new ButtonGroup();
		m_RadioDaysPerWeek = new JRadioButton();
		m_RadioDaysPerMonth = new JRadioButton();
		m_RadioDaysPerYear = new JRadioButton();
		
		for (int index = 0; index < freqTypeArrayList.size(); index++) {
			String strFreqType = freqTypeArrayList.get(index);
				
			if(strFreqType.equalsIgnoreCase("DPW")){
				//Create Week Radiobutton
				
				m_PanelRadioButton.add(m_RadioDaysPerWeek);
				m_RadioDaysPerWeek.setSize(90, 25);
				m_RadioDaysPerWeek.setPreferredSize(new java.awt.Dimension(91, 32));
				m_RadioDaysPerWeek.setSelected(true);
				m_RadioDaysPerWeek.setText(freqDetailsArrayList.get(index));
				//m_RadioDaysPerWeek.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.DaysPerWeek","Days Per Week"));
				m_RadioDaysPerWeek.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ShowDaysPerWeekPanel();
						EnableNextButton();
					}
				});
				m_ButtonGroupDate.add(m_RadioDaysPerWeek);
			}else if(strFreqType.equalsIgnoreCase("DPM"))
			{
			
				//Create Month Radiobutton
				
				m_PanelRadioButton.add(m_RadioDaysPerMonth);
				m_RadioDaysPerMonth.setSize(90, 25);
				m_RadioDaysPerMonth.setPreferredSize(new java.awt.Dimension(94, 25));
				m_RadioDaysPerMonth.setText(freqDetailsArrayList.get(index));
				//m_RadioDaysPerMonth.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.DaysPerMonth","Days per Month"));
				
				m_RadioDaysPerMonth.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ShowDaysPerMonthPanel();
						EnableNextButton();
					}
				});
				m_ButtonGroupDate.add(m_RadioDaysPerMonth);
			}else if(strFreqType.equalsIgnoreCase("DPY"))
			{
				//Create Year Radiobutton
				
				m_PanelRadioButton.add(m_RadioDaysPerYear);
				m_RadioDaysPerYear.setSize(90, 25);
				m_RadioDaysPerYear.setPreferredSize(new java.awt.Dimension(86, 24));
				m_RadioDaysPerYear.setLocation(new java.awt.Point(0, 0));
				m_RadioDaysPerYear.setText(freqDetailsArrayList.get(index));
				//m_RadioDaysPerYear.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.DaysPerYear","Days Per Year"));
				
				m_RadioDaysPerYear.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ShowDaysYearList();
						EnableNextButton();
					}
				});
				m_ButtonGroupDate.add(m_RadioDaysPerYear);
			}
		}
		
		//Days List Panel
		m_PanelDaysList = new JPanel();
		BorderLayout m_PanelDaysLayout = new BorderLayout();
		m_PanelDaysList.setLayout(m_PanelDaysLayout);
		m_PanelDate.add(m_PanelDaysList, BorderLayout.CENTER);
		
		//Days per week List
		m_ListDayPerWeek = new PSADoubleList(m_Controller.m_ObjLanguageHandler.Getlabel("Label.DaysAvailable","Days Available"),
											 m_Controller.m_ObjLanguageHandler.Getlabel("Label.DaysChoosen","Days Choosen"));
		m_ListDayPerWeek.setToolTipLeftToRight(m_Controller.m_ObjLanguageHandler.Getlabel("Label.Select","Select"));
		m_ListDayPerWeek.setToolTipRightToLeft(m_Controller.m_ObjLanguageHandler.Getlabel("Label.Deselect","Deselect"));
		m_ListDayPerWeek.setPreferredSize(new java.awt.Dimension(326, 247));
		m_ListDayPerWeek.addButtonEnabledListener(new ButtonEnableListener() {
			
			public void fireButtonEnableEvent(AWTEvent event) {
				EnableNextButton();
			}
		});
		
		//Days Per Month List
		m_ListDayPerMonth = new PSADoubleList(m_Controller.m_ObjLanguageHandler.Getlabel("Label.DaysAvailable","Days Available"),
				 							  m_Controller.m_ObjLanguageHandler.Getlabel("Label.DaysChoosen","Days Choosen"));
		m_ListDayPerMonth.setToolTipLeftToRight(m_Controller.m_ObjLanguageHandler.Getlabel("Label.Select","Select"));
		m_ListDayPerMonth.setToolTipRightToLeft(m_Controller.m_ObjLanguageHandler.Getlabel("Label.Deselect","Deselect"));
		m_ListDayPerMonth.setPreferredSize(new java.awt.Dimension(326, 247));
		m_ListDayPerMonth.addButtonEnabledListener(new ButtonEnableListener() {
			
			public void fireButtonEnableEvent(AWTEvent event) {
				EnableNextButton();
			}
		});
				
		//Days Per Year List.		
		m_PanelDaysYear = new DaysYearPanel(SiteTimeSlotSelectionPanel,m_Controller);
		m_PanelDaysYear.addButtonEnabledListener(new ButtonEnableListener() {
			
			public void fireButtonEnableEvent(AWTEvent event) {
				EnableNextButton();
			}
		});
			
		InsertDates();
		SiteTimeSlotSelectionPanel.getContentPane().add(m_PanelTimeSlotSelection, new GridBagConstraints(0, 0, 4, 9, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
	}

	/** 
	 * Hide or Show the Panel
	 * @param	status	- false to Hide the panel.
	 * 					- true to show the panel
	 */
	@Override
	public void ShowWindow(boolean bstatus)
	{
		System.out.println("PSASiteTimeSlotSelection::ShowWindow");
		if(bstatus)
		{
			EnableNextButton();
		}
		SiteTimeSlotSelectionPanel.setLocationRelativeTo(null);
		SiteTimeSlotSelectionPanel.setVisible(bstatus);
		System.out.println("PSASiteTimeSlotSelection::ShowWindow End");
	}

	/**
	 * It handles the back button event.
	 * @param evt : ActionEvent
	 */
	private void BackActionCallback(ActionEvent evt) {
		m_Controller.HidePanel(PSARequestSelectionPanel.PANEL.SITESELECTION_PANEL);
		m_Controller.ShowPanel(PSARequestSelectionPanel.PANEL.CREATION_PANEL);
	}
	
	/**
	 * It handles the next button event.
	 * @param evt : ActionEvent
	 */
	private void NextActionCallback(ActionEvent evt) {
		ArrayList<String> selectedSiteList= m_ListSite.getSelectedList();
		
		//Set selected , deleted and added sites to opr.
		m_Controller.m_CurrentOpr.setListSelSite(selectedSiteList);
		m_Controller.m_CurrentOpr.setDeletedSites(m_ListSite.getDeletedList());
		m_Controller.m_CurrentOpr.setAddedSites(m_ListSite.getAddedList());

		boolean flag = true;
		
		//Check for Init or VCO request.
		if(m_Controller.m_CurrentOpr.getFilterPartListSize() > 0)
		{
			ArrayList<String> selectedDateList = null;
			String freqValuesTypes = null;
			
			//Check selected date frequency.
			if(m_RadioDaysPerYear.isSelected())
			{
				selectedDateList = m_PanelDaysYear.getSelectedDateList();
				freqValuesTypes = "DPY";
				m_Controller.m_CurrentOpr.setAddedFreq("DPY", m_PanelDaysYear.getAddedList());
				m_Controller.m_CurrentOpr.setDeletedFreq("DPY", m_PanelDaysYear.getDeletedList());
				
			}else if(m_RadioDaysPerWeek.isSelected())
			{
				freqValuesTypes = "DPW";
				selectedDateList = m_ListDayPerWeek.getSelectedList();
				m_Controller.m_CurrentOpr.setAddedFreq("DPW", m_ListDayPerWeek.getAddedList());
				m_Controller.m_CurrentOpr.setDeletedFreq("DPW", m_ListDayPerWeek.getDeletedList());
				
			}
			else if(m_RadioDaysPerMonth.isSelected())
			{
				freqValuesTypes = "DPM";
				selectedDateList = m_ListDayPerMonth.getSelectedList();
				m_Controller.m_CurrentOpr.setAddedFreq("DPM", m_ListDayPerMonth.getAddedList());
				m_Controller.m_CurrentOpr.setDeletedFreq("DPM", m_ListDayPerMonth.getDeletedList());
			}
			
			if(m_Controller.m_CurrentOpr.getFreqType().equals(freqValuesTypes) == false)
			{
				m_Controller.m_CurrentOpr.setDeletedFreq(m_Controller.m_CurrentOpr.getFreqType(),
														 m_Controller.m_CurrentOpr.getFreqList());
				m_Controller.m_CurrentOpr.setAddedFreq(freqValuesTypes, selectedDateList);
			}
			
			m_Controller.m_CurrentOpr.setFreq(freqValuesTypes, selectedDateList);
			m_Controller.m_CurrentOpr.CalculateNextLaunchDate();			
		}
		if(flag)
		{
			m_Controller.HidePanel(PSARequestSelectionPanel.PANEL.SITESELECTION_PANEL);
			m_Controller.ShowPanel(PSARequestSelectionPanel.PANEL.SUMMARY_PANEL);
		}
	}
	
	/**
	 * Insertion of Sites in Availables List.
	 */
	private void InsertSites() throws PSAErrorHandler
	{
		System.out.println("PSASiteTimeSlotSelection::InsertSites");
		//Get Sites List from Database.
		ArrayList<String> selectedSiteList = m_Controller.m_CurrentOpr.getListOfSelectedSites();
		m_ListSite.setSelectedValues(selectedSiteList);
		ArrayList<String> availableSiteList = null;
		availableSiteList = m_Controller.m_CurrentOpr.GetListOfActiveSite();
		m_ListSite.setAvailableValues(availableSiteList);
		System.out.println("PSASiteTimeSlotSelection::InsertSites End");
	}
	
	

	/**
	 * Insertion of Dates in Selected List.
	 */
	private void InsertDates()
	{
		System.out.println("PSASiteTimeSlotSelection::InsertSelectedSites");
		 String strFreqType= m_Controller.m_CurrentOpr.getFreqType();
		ArrayList<String> dayList = m_Controller.m_CurrentOpr.getFreqList();
		System.out.println("strFreqType = "+strFreqType);
		
		//Check the frequency type.
		if(strFreqType.equals("DPM"))
		{
			m_ListDayPerMonth.setSelectedValues(dayList);
			m_PanelDaysList.add(m_ListDayPerMonth, BorderLayout.CENTER);
			m_RadioDaysPerMonth.setSelected(true);
		}else if(strFreqType.equals("DPY"))
		{
			
			m_PanelDaysYear.setSelectedValues(dayList);
			m_PanelDaysList.add(m_PanelDaysYear, BorderLayout.CENTER);
			m_RadioDaysPerYear.setSelected(true);
		}
		else 
		{
			m_PanelDaysList.add(m_ListDayPerWeek, BorderLayout.CENTER);
			m_ListDayPerWeek.setSelectedValues(dayList);
			m_RadioDaysPerWeek.setSelected(true);
		}
		m_ListDayPerWeek.setAvailableValues(weekDaysList);
		m_ListDayPerMonth.setAvailableValues(monthDaysList);	
		System.out.println("PSASiteTimeSlotSelection::InsertSelectedSites End");
	}

    /**
     * It displays the Days per week panel.
     */
	private void ShowDaysPerWeekPanel() {
		m_PanelDaysList.removeAll();
		m_PanelDaysList.add(m_ListDayPerWeek, BorderLayout.CENTER);
		m_ListDayPerWeek.repaint();
		m_PanelDaysList.validate();
		
	}
	
	/**
     * It displays the Days per month panel.
     */
	private void ShowDaysPerMonthPanel() {
		m_PanelDaysList.removeAll();
		m_PanelDaysList.add(m_ListDayPerMonth, BorderLayout.CENTER);
		m_ListDayPerMonth.repaint();
		m_PanelDaysList.validate();
	}
	
	/**
     * It displays the Days per year panel.
     */
	private void ShowDaysYearList() {
		m_PanelDaysList.removeAll();
		m_PanelDaysList.add(m_PanelDaysYear, BorderLayout.CENTER);
		m_PanelDaysYear.repaint();
		m_PanelDaysList.validate();
				
	}
	
	/**
     * It enable or disable next button.
     */
	private void EnableNextButton()
	{
		ArrayList<String> selectedSiteList= m_ListSite.getSelectedList();
		boolean bFlag = false;
		if(selectedSiteList.size() > 0)
		{
			bFlag = true;
		}
		 
		//Check for init /VCO  request.
		if(m_Controller.m_CurrentOpr.getFilterPartListSize() > 0 && bFlag == true)
		{
			ArrayList<String> selectedDateList  = null;	
			
			//Check selected frequency.
			if(m_RadioDaysPerMonth.isSelected())
			{
				selectedDateList = m_ListDayPerMonth.getSelectedList();
			}else if(m_RadioDaysPerWeek.isSelected())
			{
				selectedDateList = m_ListDayPerWeek.getSelectedList();
			}else
			{
				selectedDateList = m_PanelDaysYear.getSelectedDateList();
			}
				
			if(selectedDateList == null || selectedDateList.size() ==  0)
			{
				bFlag = false;
			}
		}
		m_ButtonNext.setEnabled(bFlag);
		
	}

}


