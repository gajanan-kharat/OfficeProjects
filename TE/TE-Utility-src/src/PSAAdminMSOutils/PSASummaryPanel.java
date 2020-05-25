/**********************************************************************************************************
 *
 * FILE NAME	  : PSASummaryPanel.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class used to display request details.
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
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

//User class import
import com.psa.tools.PSATableDataModel;

/**
 * 
 *Class used to display request details.
 */
public class PSASummaryPanel extends PSARequestBasePanel {
	private JFrame SummaryPanel;
	private JPanel m_PanelButtons;
	private JButton m_ButtonBack;
	private JButton m_ButtonExit;
	private JTextField m_TextFieldRacine;
	private JPanel m_PanelFilteringParts;
	private JList m_ListSubscription;
	private JLabel m_LabelSiteSubscription;
	private JLabel m_LabelDateSelection;
	private JList m_ListDate;
	private JPanel m_PanelSites;
	private JTable m_TableParts;
	private JTextField m_TextFieldParts;
	private JLabel m_LabelTotalParts;
	private JTextField m_TextFieldDesc;
	private JLabel m_LabelRacineDesc;
	private JLabel m_LabelRacine;
	private JPanel m_PanelRacine;
	private JButton m_ButtonCreate;
	private JScrollPane m_ScrollPaneDate;
	private JScrollPane m_ScrollPaneSubscription;
	private PSARequestDetails m_RequestDetails = null;
	private DefaultListModel m_ListSubscriptionModel;
	private DefaultListModel m_ListDateModel;
	private PSATableDataModel partListDataModel;
	private JScrollPane partTablescrollPane;
	private JPanel m_PanelDeleteParts;
	private JLabel m_LabelTotalDeletedParts;
	private JScrollPane deletedPartTablescrollPane;
	private JTable m_TableDeletedParts;
	private PSATableDataModel deletedPartListDataModel;
	private JTextField m_TextFieldDeleteParts;

	/**
	 * Constructor of the class.
	 * @param iController : PSARequestController
	 */
	public PSASummaryPanel(PSARequestController iController) {
		super(iController);
		initGUI();
	}

	/**
	 * It creates and initialize components for summary panel. 
	 */
	private void initGUI() {
		try
		{
			SummaryPanel = new JFrame();
			GridBagLayout SummaryPanelLayout = new GridBagLayout();

			//Set the layout of panel for INIT/VCO
			if (m_Controller.m_CurrentOpr.getFilterPartListSize() > 0) 
			{
				//If Deleted NF's then show Deleted Nf's in summary panel.
				if(m_Controller.m_CurrentOpr.getDeletedPartListSize() > 0)
				{
					SummaryPanelLayout.rowWeights = new double[] { 0.0, 0.1, 0.0,0.1, 0.0, 0.1, 0.1, 0.1, 0.1,0.1, 0.1, 0.1, 0.0, 0.1 ,0.1, 0.1 ,0.1};
					SummaryPanelLayout.rowHeights = new int[] { 30, 30, 26, 30, 28,30,30,30,30, 30, 30, 30, 30,30, 30, 60, 30 };
				}
				else
				{
					SummaryPanelLayout.rowWeights = new double[] { 0.0, 0.1, 0.0,0.1, 0.0, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.0, 0.1 };
					SummaryPanelLayout.rowHeights = new int[] { 30, 30, 26, 30, 28,30, 30, 30, 30, 30, 30, 60, 30 };
				}
				SummaryPanelLayout.columnWeights = new double[] { 0.1, 0.1,0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1 };
				SummaryPanelLayout.columnWidths = new int[] { 7, 7, 7, 7, 7, 7,7, 7, 7, 7, 7, 7 };
						
				SummaryPanel.getContentPane().setLayout(SummaryPanelLayout);
				SummaryPanel.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

				//Display Filtering part detils.
				DisplayFilteringPartDetails();
				
				//Check if there are any NF's Deleted.
				if(m_Controller.m_CurrentOpr.getDeletedPartListSize() > 0)
				{
					SummaryPanel.setSize(578, 693);
					//Display Deleted parts
					DisplayDeletePart();
					
					//Display Dates / Sites
					DisplayDatesSites(13);
					
					//Display Buttons BACK and ACTION.
					DisplayButtons(16);;
				}
				else
				{
					SummaryPanel.setSize(578, 623);
					DisplayDatesSites(9);
					DisplayButtons(12);
				}
				

			}
			//Set the layout of panel for One shot
			else {
				SummaryPanelLayout.rowWeights = new double[] { 0.0, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1 };
				SummaryPanelLayout.rowHeights = new int[] { 30, 30, 26, 30, 28, 30, 30,	60, 30 };
				SummaryPanelLayout.columnWeights = new double[] { 0.1, 0.1,	0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1 };
				SummaryPanelLayout.columnWidths = new int[] { 7, 7, 7, 7, 7, 7,
						7, 7, 7, 7, 7, 7 };
				SummaryPanel.getContentPane().setLayout(SummaryPanelLayout);
				SummaryPanel.setSize(535, 460);

				DisplayDatesSites(5);
				DisplayButtons(8);
			}

			//Create request details panel. 
			m_RequestDetails = new PSARequestDetails(m_Controller);
			SummaryPanel.getContentPane().add(m_RequestDetails,new GridBagConstraints(0, 0, 12, 3, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH,new Insets(5, 5, 5, 5), 0, 0));
			DisplayRacineInformation();

		} 
		catch (Exception e) {

		}
		
		//SummaryPanel.setResizable(false);
		SummaryPanel.setPreferredSize(new java.awt.Dimension(578, 633));
		if (m_Controller.GetActionType() == ACTION.CREATEREQUEST) {
			SummaryPanel.setTitle(m_Controller.m_ObjLanguageHandler.Getlabel("Label.NewRequestCreationSummary", "Creation of Request Summary"));
		} else if (m_Controller.GetActionType() == ACTION.UPDATEREQUEST) {
			SummaryPanel.setTitle(m_Controller.m_ObjLanguageHandler.Getlabel("Label.RequestModificationSummary", "Modification of Request Summary"));
		}else if (m_Controller.GetActionType() == ACTION.VIEWREQUEST) {
			SummaryPanel.setTitle(m_Controller.m_ObjLanguageHandler.Getlabel("Label.RequestSummary", "Request Summary"));
		}
	}

	/**
	 * It used to display delete part table.
	 */
	private void DisplayDeletePart() {
			
			//Filtering part panel.
			m_PanelDeleteParts = new JPanel();
			GridBagLayout deletePartsLayout = new GridBagLayout();
			SummaryPanel.getContentPane().add(m_PanelDeleteParts,new GridBagConstraints(0, 9, 12, 4, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH,new Insets(5, 5, 5, 5), 0, 0));
			m_PanelDeleteParts.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
			deletePartsLayout.rowWeights = new double[] { 0.0, 0.1, 0.1,0.1 };
			deletePartsLayout.rowHeights = new int[] { 30, 7, 7, 7 };
			deletePartsLayout.columnWeights = new double[] { 0.0, 0.0,0.1, 0.1 };
			deletePartsLayout.columnWidths = new int[] { 158, 47, 7, 7 };
			m_PanelDeleteParts.setLayout(deletePartsLayout);
			
			//Total part label.
			m_LabelTotalDeletedParts = new JLabel();
			m_PanelDeleteParts.add(m_LabelTotalDeletedParts, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
			m_LabelTotalDeletedParts.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.TotalDeletedParts","Total Deleted Parts:"));
			m_LabelTotalDeletedParts.setHorizontalAlignment(SwingConstants.RIGHT);
			m_LabelTotalDeletedParts.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

			//Part Text Field
			m_TextFieldDeleteParts = new JTextField();
			m_PanelDeleteParts.add(m_TextFieldDeleteParts,new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5,5), 0, 0));
			m_TextFieldDeleteParts.setEditable(false);
			m_TextFieldDeleteParts.setHorizontalAlignment(SwingConstants.LEFT);

			//Part details
			String[] Headers = {
					m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderRef", "Part Reference"),
					m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderDesignation", "Designation")};

			deletedPartListDataModel = new PSATableDataModel(1, Headers);
			m_TableDeletedParts = new JTable(deletedPartListDataModel);
			m_TableDeletedParts.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			m_TableDeletedParts.setColumnSelectionAllowed(false);
			m_TableDeletedParts.getTableHeader().setReorderingAllowed(false);

			deletedPartTablescrollPane = new JScrollPane(m_TableDeletedParts);
			m_PanelDeleteParts.add(deletedPartTablescrollPane, new GridBagConstraints(0,1, 4, 3, 0.0, 0.0, GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 20, 5, 20), 0, 0));
			partTablescrollPane.setViewportView(m_TableDeletedParts);
	}


	/**
	 * It initialize the value of all fields.
	 */
	private void initializeValues() {
		m_RequestDetails.initializeValues();
		
		//Get selected site list. 
		ArrayList<String> siteTempList = m_Controller.m_CurrentOpr.getListOfSelectedSites();
		m_ListSubscriptionModel.clear();
		for (String strSite : siteTempList) {
			m_ListSubscriptionModel.addElement(strSite);
		}
		
		//Set root part details.
		m_TextFieldRacine.setText(m_Controller.m_CurrentOpr.getRootPartID());
		m_TextFieldDesc.setText(m_Controller.m_CurrentOpr.getRootDescription());
		int iFilterCount = m_Controller.m_CurrentOpr.getFilterPartListSize();
		
		//Check for INIT/VCO
		if (iFilterCount > 0) 
		{
            //Get date list.
			ArrayList<String> dateList = m_Controller.m_CurrentOpr.getFreqList();
			m_ListDateModel.clear();
			for (String strDate : dateList) {
				m_ListDateModel.addElement(strDate);
			}
			m_TextFieldParts.setText(""+iFilterCount);
			
			//Get filter part.
			ArrayList<PSNPart> psnPartsList =  m_Controller.m_CurrentOpr.getFilterParts();
			partListDataModel.Clear();
			partListDataModel.SetRowCount(iFilterCount);
			for (int index = 0; index < iFilterCount; index++) {
				PSNPart psnPart = psnPartsList.get(index);
				System.out.println(psnPart.m_StrRef + "\t"+psnPart.m_StrSelectedConf+ "\t"+psnPart.m_StrSelectedParentREF);
				partListDataModel.setValueAt(psnPart.m_StrRef, index, 0);
				partListDataModel.setValueAt(psnPart.m_StrDesignation, index, 1);
				partListDataModel.setValueAt(psnPart.m_StrSelectedConf, index, 2);
				partListDataModel.setValueAt(psnPart.m_StrSelectedParentREF, index, 3);
				partListDataModel.setValueAt(psnPart.m_StrSelectedParentDesignation, index, 4);
			}
			System.out.println("Set all the part.");
			partTablescrollPane.setViewportView(m_TableParts);
			
			
		}
		int deletePartsCount = m_Controller.m_CurrentOpr.getDeletedPartListSize();
		if(deletePartsCount > 0)
		{
			m_TextFieldDeleteParts.setText(""+deletePartsCount);
			ArrayList<PSNPart> psnDeleteParts = m_Controller.m_CurrentOpr.getDeletedPartList();	
			deletedPartListDataModel.Clear();
			deletedPartListDataModel.SetRowCount(deletePartsCount);
			for (int index = 0; index < deletePartsCount; index++) {
				PSNPart psnPart = psnDeleteParts.get(index);
				System.out.println(psnPart.m_StrRef + "\t"+psnPart.m_StrSelectedConf+ "\t"+psnPart.m_StrSelectedParentREF);
				deletedPartListDataModel.setValueAt(psnPart.m_StrRef, index, 0);
				deletedPartListDataModel.setValueAt(psnPart.m_StrDesignation, index, 1);
			}
			System.out.println("Set all the part.");
			deletedPartTablescrollPane.setViewportView(m_TableDeletedParts);
		}
	}

	/**
	 * It display selected dates and sites.
	 * @param pos : int
	 */
	private void DisplayDatesSites(int pos) {

		//Sites panel.
		m_PanelSites = new JPanel();
		GridBagLayout jPanelSitesLayout = new GridBagLayout();
		SummaryPanel.getContentPane().add(m_PanelSites,new GridBagConstraints(0, pos, 12, 3, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH,new Insets(5, 5, 5, 5), 0, 0));
		jPanelSitesLayout.rowWeights = new double[] { 0.0, 0.1, 0.1, 0.1 };
		jPanelSitesLayout.rowHeights = new int[] { 30, 30, 30, 30 };
		jPanelSitesLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.1 };
		jPanelSitesLayout.columnWidths = new int[] { 100, 125, 50, 125, 100};
		m_PanelSites.setLayout(jPanelSitesLayout);
		m_PanelSites.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));

		// Controls for Date
		if (m_Controller.m_CurrentOpr.getFilterPartListSize() > 0)
		{
			m_LabelDateSelection = new JLabel();
			m_PanelSites.add(m_LabelDateSelection, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
			m_LabelDateSelection.setText("Dates / Days Choosen");
			m_LabelDateSelection.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.Date", "Dates / Days Choosen"));
			m_LabelDateSelection.setHorizontalAlignment(SwingConstants.CENTER);

			m_ListDateModel = new DefaultListModel();
			m_ListDate = new JList();
			m_ListDate.setModel(m_ListDateModel);
			m_ListDate.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
			m_ListDate.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			// jListDate.setPreferredSize(new java.awt.Dimension(107, 97));

			m_ScrollPaneDate = new JScrollPane(m_ListDate);
			m_PanelSites.add(m_ScrollPaneDate, new GridBagConstraints(1, 1, 1, 3, 0.0,0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		}

		// Controls for Subscription list
		m_LabelSiteSubscription = new JLabel();
		if (m_Controller.m_CurrentOpr.getFilterPartListSize() > 0)
		{
			m_PanelSites.add(m_LabelSiteSubscription, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));			
		} else {
			m_PanelSites.add(m_LabelSiteSubscription, new GridBagConstraints(1, 1, 1, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		}
		m_LabelSiteSubscription.setText("Site(s) for Subscription");
		m_LabelSiteSubscription.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.SubscriptionSites", "Site(s) for Subscription"));
		m_LabelSiteSubscription.setHorizontalAlignment(SwingConstants.CENTER);

		m_ListSubscriptionModel = new DefaultListModel();
		m_ListSubscription = new JList();
		m_ListSubscription.setModel(m_ListSubscriptionModel);
		m_ListSubscription.setBorder(new LineBorder(new java.awt.Color(0, 0,	0), 1, false));
		m_ListSubscription.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		m_ScrollPaneSubscription = new JScrollPane(m_ListSubscription);
		if (m_Controller.m_CurrentOpr.getFilterPartListSize() > 0)
			m_PanelSites.add(m_ScrollPaneSubscription, new GridBagConstraints(3, 1, 1, 3, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		else 
			m_PanelSites.add(m_ScrollPaneSubscription, new GridBagConstraints(2, 0, 2, 4, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
	}

	/**
	 * It display filter part details.
	 */
	private void DisplayFilteringPartDetails() {
		//Filtering part panel.
		m_PanelFilteringParts = new JPanel();
		GridBagLayout jPanelFilteringPartsLayout = new GridBagLayout();
		SummaryPanel.getContentPane().add(m_PanelFilteringParts,new GridBagConstraints(0, 5, 12, 4, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH,new Insets(5, 5, 5, 5), 0, 0));
		m_PanelFilteringParts.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
		jPanelFilteringPartsLayout.rowWeights = new double[] { 0.0, 0.1, 0.1,0.1 };
		jPanelFilteringPartsLayout.rowHeights = new int[] { 30, 7, 7, 7 };
		jPanelFilteringPartsLayout.columnWeights = new double[] { 0.0, 0.0,0.1, 0.1 };
		jPanelFilteringPartsLayout.columnWidths = new int[] { 158, 47, 7, 7 };
		m_PanelFilteringParts.setLayout(jPanelFilteringPartsLayout);
		
		//Total part lable.
		m_LabelTotalParts = new JLabel();
		m_PanelFilteringParts.add(m_LabelTotalParts, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		// jLabelTotalParts.setText("Total Filtering Parts:");
		m_LabelTotalParts.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.TotalFileringParts","Total Filtering Parts:"));
		m_LabelTotalParts.setHorizontalAlignment(SwingConstants.RIGHT);
		m_LabelTotalParts.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		//Part Text Field
		m_TextFieldParts = new JTextField();
		m_PanelFilteringParts.add(m_TextFieldParts,new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5,5), 0, 0));
		m_TextFieldParts.setEditable(false);
		m_TextFieldParts.setHorizontalAlignment(SwingConstants.LEFT);

		//Part details table.
		String[] Headers = {
				m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderRef", "Part Reference"),
				m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderDesignation", "Part Designation"),
				m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderConfHandler", "Conf. Handler"),
				m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderConfPartParent","Conf. Part Parent") ,
				m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderParentDesignation", "Parent Designation")};

		partListDataModel = new PSATableDataModel(1, Headers);
		m_TableParts = new JTable(partListDataModel);
		m_TableParts.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m_TableParts.setColumnSelectionAllowed(false);
		m_TableParts.getTableHeader().setReorderingAllowed(false);

		partTablescrollPane = new JScrollPane(m_TableParts);
		m_PanelFilteringParts.add(partTablescrollPane, new GridBagConstraints(0,1, 4, 3, 0.0, 0.0, GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 20, 5, 20), 0, 0));
		partTablescrollPane.setViewportView(m_TableParts);
	}

	/**
	 * It displays racine information.
	 */
	private void DisplayRacineInformation() {
		//Racine panel
		m_PanelRacine = new JPanel();
		GridBagLayout jPanelRacineLayout = new GridBagLayout();
		SummaryPanel.getContentPane().add(m_PanelRacine,new GridBagConstraints(0, 3, 12, 2, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH,new Insets(5, 5, 5, 5), 0, 0));
		jPanelRacineLayout.rowWeights = new double[] { 0.0, 0.0 };
		jPanelRacineLayout.rowHeights = new int[] { 31, 30 };
		jPanelRacineLayout.columnWeights = new double[] { 0.0, 0.1, 0.1, 0.1 };
		jPanelRacineLayout.columnWidths = new int[] { 162, 7, 7, 7 };
		m_PanelRacine.setLayout(jPanelRacineLayout);
		m_PanelRacine.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				
	    //Racine Label.
		m_LabelRacine = new JLabel();
		m_PanelRacine.add(m_LabelRacine, new GridBagConstraints(0, 0, 1, 1,0.0, 0.0, GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		m_LabelRacine.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.RacineRequest", "Racine of Request :"));
		m_LabelRacine.setHorizontalAlignment(SwingConstants.RIGHT);
     
		//Racine Description.
		m_LabelRacineDesc = new JLabel();
		m_PanelRacine.add(m_LabelRacineDesc, new GridBagConstraints(0, 1, 1,1, 0.0, 0.0, GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		m_LabelRacineDesc.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.RacineDescription", "Designation of Racine :"));
		m_LabelRacineDesc.setHorizontalAlignment(SwingConstants.RIGHT);

		//Racine TextField.
		m_TextFieldRacine = new JTextField();
		m_PanelRacine.add(m_TextFieldRacine, new GridBagConstraints(1, 0, 1,1, 0.0, 0.0, GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		m_TextFieldRacine.setEditable(false);
		m_TextFieldRacine.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		//Racine Description TextField.
		m_TextFieldDesc = new JTextField();
		m_PanelRacine.add(m_TextFieldDesc, new GridBagConstraints(1, 1, 2, 1,0.0, 0.0, GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		m_TextFieldDesc.setEditable(false);
		m_TextFieldDesc.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
	}
	
	/**
	 * It creates button panel.
	 * @param pos : int
	 */
	private void DisplayButtons(int pos) {

		//Button panel.
		m_PanelButtons = new JPanel();
		GridBagLayout jPanelButtonsLayout = new GridBagLayout();
		SummaryPanel.getContentPane().add(m_PanelButtons,new GridBagConstraints(0, pos, 12, 1, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH,new Insets(5, 5, 5, 5), 0, 0));
		jPanelButtonsLayout.rowWeights = new double[] { 0.1 };
		jPanelButtonsLayout.rowHeights = new int[] { 7 };
		jPanelButtonsLayout.columnWeights = new double[] { 0.1, 0.1, 0.1, 0.1 };
		jPanelButtonsLayout.columnWidths = new int[] { 7, 7, 7, 7 };
		m_PanelButtons.setLayout(jPanelButtonsLayout);
		
		//Back Button.
		m_ButtonBack = new JButton();
		m_ButtonBack.setToolTipText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.BackButton", "BACK"));
		m_ButtonBack.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/resultset_previous.png")));
		m_ButtonBack.setSize(50, 21);
		m_ButtonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonBackActionPerformed(evt);
			}
		});
		if(m_Controller.GetActionType() == ACTION.VIEWREQUEST)
		{
			m_ButtonExit= new JButton();
			m_ButtonExit.setToolTipText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.ExitBtn", "EXIT"));
			m_ButtonExit.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/cancel.png")));
			m_PanelButtons.add(m_ButtonBack, new GridBagConstraints(0, 0, 1, 1,0.0, 0.0, GridBagConstraints.CENTER,GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
			m_PanelButtons.add(m_ButtonExit, new GridBagConstraints(3, 0, 1, 1,0.0, 0.0, GridBagConstraints.CENTER,GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
			m_ButtonExit.setSize(50, 21);
			m_ButtonExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jButtonQuitActionPerformed(evt);
				}
			});			
		}

		//Check for create or update request.
		if (m_Controller.GetActionType() == ACTION.CREATEREQUEST || m_Controller.GetActionType() == ACTION.UPDATEREQUEST)
		{
			m_PanelButtons.add(m_ButtonBack, new GridBagConstraints(0, 0, 1, 1,0.0, 0.0, GridBagConstraints.CENTER,GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
			m_ButtonCreate = new JButton();
			m_PanelButtons.add(m_ButtonCreate, new GridBagConstraints(3, 0, 1, 1,0.0, 0.0, GridBagConstraints.CENTER,GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
			if (m_Controller.GetActionType() == ACTION.CREATEREQUEST)
				m_ButtonCreate.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.CreateButton", "Create"));
			else if (m_Controller.GetActionType() == ACTION.UPDATEREQUEST)
				m_ButtonCreate.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.UpdateButton", "Update"));

			m_ButtonCreate.setSize(50, 21);
			m_ButtonCreate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jButtonCreateActionPerformed(evt);
				}
			});
		} else if (m_Controller.GetActionType() != ACTION.VIEWREQUEST){
			m_PanelButtons.add(m_ButtonBack, new GridBagConstraints(0, 0, 4, 1,0.0, 0.0, GridBagConstraints.CENTER,GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		}
	}

	/**
	 * Hide or Show the Panel
	 * 
	 * @param status
	 *            - false to Hide the panel. - true to show the panel
	 */
	public void ShowWindow(boolean bstatus) {
		System.out.println("PSASummaryPanel::ShowWindow");
		if(bstatus)
		{
			initializeValues();
		}
		SummaryPanel.setLocationRelativeTo(null);
		SummaryPanel.setVisible(bstatus);
		
		System.out.println("PSASummaryPanel::ShowWindow End");
	}

	/**
	 * It handles the back button event.
	 * @param evt : ActionEvent
	 */
	private void jButtonBackActionPerformed(ActionEvent evt) {
		m_Controller.HidePanel(PANEL.SUMMARY_PANEL);
		m_Controller.ShowPanel(PANEL.SITESELECTION_PANEL);
	}

	/**
	 * It handles the create button event.
	 * @param evt : ActionEvent
	 */
	private void jButtonCreateActionPerformed(ActionEvent evt) {
		m_Controller.ValiderCallback();
		if (ACTION.CREATEREQUEST == GetActionType()) {
			m_Controller.DisplayMainPanel();
		} else {
			m_Controller.HidePanel(PANEL.SUMMARY_PANEL);
			m_Controller.ShowPanel(PANEL.REQUESTLISTE_PANEL);
		}
	}
	
	/**
	 * It handles the exit button event during view request.
	 * @param evt : ActionEvent
	 */
	private void jButtonQuitActionPerformed(ActionEvent evt) {
		m_Controller.exitApplication();
	}

}
