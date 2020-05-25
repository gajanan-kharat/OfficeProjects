/**********************************************************************************************************
 *
 * FILE NAME	  : PSAAddModifyRequestPanel.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class used to create and update request.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/

package com.psa.PSAAdminMSOutils;

//Standard class import
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

//User class import
import com.psa.tools.PSADatePicker;
import com.psa.tools.PSAErrorHandler;
import com.psa.tools.PSAMessageDialog;

/**
 * Class used to create and update request.
 *
 */
public class PSAAddModifyRequestPanel extends PSARequestBasePanel {
	private JFrame AddModifyPanel;
	
	// Request Detail Panel
	private JPanel m_PanelRequestDetails;
	private JLabel m_LabelPanelInfo;
	private JLabel m_LabelHeading;
	private JLabel m_LabelNameofRequest;
	private JLabel m_LabelPSNFile;
	private JLabel m_LabelReference;
	private JLabel m_LabelDesignation;
	private JLabel m_LabelPSNFileName;
	private JLabel m_LabelRacineReference;
	private JLabel m_LabelRacineDesignation;
	private JTextField m_TextFieldRequestName;

	// Request Type Panel
	private JPanel m_PanelRequestType;
	private JLabel m_LabelExpiryDate;
	private JLabel m_LabelTypeofRequest;
	private JRadioButton m_RadioVCO;
	private JRadioButton m_RadioInitOneShot;
	private JTextField m_TextFieldExpiryDate;
	private JButton m_ButtonDate;
    private ButtonGroup buttonGroupMode;
    private JLabel LabelConfigHandler;
    private JLabel LabelDesignation;
    private JLabel LabelParentRef;
    private JTextField m_TextFieldParentDesignation;
    private JTextField m_TextFieldDesignation;
    private JButton m_ButtonDelete;

	// Config handler panel
	private JPanel m_PanelConfHandler;
	private JPanel m_PanelAssociatedParts;
	private JLabel m_LabelFilteringPart;
	private JHorrizontalComboBox m_ComboBoxConfHandler;
	private JComboBox m_ComboBoxReference;
	private JTextField m_TextFieldReferencePart;
	private JButton m_ButtonLeft;
	private JButton m_ButtonRight;

	// Button panel
	private JPanel m_PanelButtons;
	private JButton m_ButtonCancel;
	private JButton m_ButtonNext;

	private int partIndex = -1;
	private ArrayList<PSAFilteredPartDetails> m_FilteredPartList; 

	/**
	 * Constructor of the class 
	 * @param iController : PSARequestController object
	 */
	public PSAAddModifyRequestPanel(PSARequestController iController) {
		super(iController);
		m_Controller = iController;
		System.out.println("PSAAddModifyRequestPanel::PSAAddModifyRequestPanel");
		initGUI();
		
		//Check action is create request or not. 
		if (m_Controller.GetActionType() == ACTION.CREATEREQUEST)
		{
			AddModifyPanel.setTitle(m_Controller.m_ObjLanguageHandler.Getlabel("Label.NewRequestCreation", "New Request Creation"));
			m_LabelPanelInfo.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.RequestCreation", "Request Creation"));
		} 
		else
		{
			AddModifyPanel.setTitle(m_Controller.m_ObjLanguageHandler.Getlabel("Label.RequestModificationPSN","Modification of Request / PSN"));
			m_LabelPanelInfo.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.RequestModification", "Modification Request"));
		}
		InitializeValues();
		EnableNextButton();
	}

	/**
	 * Initialize fields value.
	 */
	private void InitializeValues() {
        //Set request name.		
		m_TextFieldRequestName.setText(m_Controller.m_CurrentOpr.getRequestName());
		String strRequestMode = m_Controller.m_CurrentOpr.getRequestMod();

		//set root details
		m_LabelRacineReference.setText(m_Controller.m_CurrentOpr.getRootPartID());
		m_LabelRacineDesignation.setText(m_Controller.m_CurrentOpr.getRootDescription());
		
		//Set request mode radio button 
		if(strRequestMode != null && strRequestMode.equals("VCO"))
		{
		  m_RadioVCO.setSelected(true);	
		}else
		{
		  m_RadioInitOneShot.setSelected(true);	
		}
		
		m_FilteredPartList = m_Controller.m_CurrentOpr.getFilterPartDetails();
		System.out.println("Filtered Part List Size  "+ m_FilteredPartList.size());
		if(m_FilteredPartList.size() > 0)
		{
			//Set expiry date 
			m_TextFieldExpiryDate.setText(m_Controller.m_CurrentOpr.getExpiryDate());
			partIndex = 0;
			
			//Set reference detail.
			SetReferencePartDetail(partIndex);
			updatePartListDetails();
			CheckPartListButtons();
			checkDeleteButton();
		}
	}

	/**
	 * Initialize the components.
	 */
	private void initGUI() {
		try {
			AddModifyPanel = new JFrame();
			GridBagLayout MainPanelLayout = new GridBagLayout();
			AddModifyPanel.getContentPane().setLayout(MainPanelLayout);
			AddModifyPanel.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			int buttonpos = 8;
			
			//Check the Request type one shot or Init/VCO.
			if (m_Controller.m_CurrentOpr.getFilterPartListSize() > 0) 
			{
				AddModifyPanel.setPreferredSize(new java.awt.Dimension(551, 580));
				MainPanelLayout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.1, 0.0, 0.1};
				MainPanelLayout.rowHeights = new int[] {40, 112, 12, 87, 7, 216, 7};
				MainPanelLayout.columnWeights = new double[] { 0.1, 0.1, 0.1,0.1, 0.1, 0.1, 0.1, 0.1 };
				MainPanelLayout.columnWidths = new int[] { 7, 7, 7, 7, 7, 7, 7,7 };
				
				DisplayRequestDetailPanel();
				DisplayButtonPanel(buttonpos);
				DisplayRequestTypePanel();
				DisplayAssociatePartPanel();
				AddModifyPanel.setSize(551, 580);
				
			} 
			else
			{
				AddModifyPanel.setPreferredSize(new java.awt.Dimension(531, 375));
				MainPanelLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,0.0, 0.1 };
				MainPanelLayout.rowHeights = new int[] { 40, 112, 12, 87, 44, 7 };
				MainPanelLayout.columnWeights = new double[] { 0.1, 0.1, 0.1,0.1, 0.1 };
				MainPanelLayout.columnWidths = new int[] { 7, 7, 7, 7, 7 };

				DisplayRequestDetailPanel();
				buttonpos = 4;
				DisplayButtonPanel(buttonpos);
				DisplayRequestTypePanel();
				AddModifyPanel.setSize(531, 375);
				 
			}

			AddModifyPanel.pack();
			AddModifyPanel.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * It displays associated part details panel.
	 * 
	 */
	private void DisplayAssociatePartPanel() {
		
		//Associated Parts Panel
		m_PanelAssociatedParts = new JPanel();
		GridBagLayout jPanelAssociatedPartsLayout = new GridBagLayout();
		jPanelAssociatedPartsLayout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.1};
		jPanelAssociatedPartsLayout.rowHeights = new int[] {27, 25, 28, 75, 20};
		jPanelAssociatedPartsLayout.columnWeights = new double[] {0.1, 0.0, 0.1, 0.0, 0.0, 0.0, 0.1, 0.1};
		jPanelAssociatedPartsLayout.columnWidths = new int[] {20, 20, 30, 100, 150, 30, 20, 20};
		m_PanelAssociatedParts.setLayout(jPanelAssociatedPartsLayout);
		AddModifyPanel.getContentPane().add(m_PanelAssociatedParts, new GridBagConstraints(0, 4, 8, 2, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		m_PanelAssociatedParts.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
		
		//Heading Label
		m_LabelHeading = new JLabel();
		m_PanelAssociatedParts.add(m_LabelHeading, new GridBagConstraints(0, 0, 8, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		m_LabelHeading.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.PartAssociation","Association of Configuration handler to Filtering Part(s)"));
		m_LabelHeading.setHorizontalAlignment(SwingConstants.CENTER);
		m_LabelHeading.setHorizontalTextPosition(SwingConstants.CENTER);
	
		//Filtering part Label
		m_LabelFilteringPart = new JLabel();
		m_PanelAssociatedParts.add(m_LabelFilteringPart, new GridBagConstraints(0, 1, 8, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		m_LabelFilteringPart.setHorizontalAlignment(SwingConstants.CENTER);
		m_LabelFilteringPart.setHorizontalTextPosition(SwingConstants.CENTER);
		
		//Reference PArt TextField
		m_TextFieldReferencePart = new JTextField();
		m_TextFieldReferencePart.setText("");
		m_TextFieldReferencePart.setEditable(false);
		m_PanelAssociatedParts.add(m_TextFieldReferencePart, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

		//Left Button
		m_ButtonLeft = new JButton();
		m_PanelAssociatedParts.add(m_ButtonLeft, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		//m_ButtonLeft.setText("<<");
		m_ButtonLeft.setToolTipText("Previous Part");
		m_ButtonLeft.setToolTipText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.PreviousPart","Previous Part"));
		m_ButtonLeft.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/resultset_previous.png")));
		m_ButtonLeft.setEnabled(false);
		m_ButtonLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) 
			{
				LeftButtonCallback(evt);
			}
		});
		
		//Right Button
		m_ButtonRight = new JButton();
		m_PanelAssociatedParts.add(m_ButtonRight, new GridBagConstraints(6, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		//m_ButtonRight.setText(">>");
		m_ButtonRight.setToolTipText("Next Part");
		m_ButtonRight.setToolTipText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.NextPart","Next Part"));
		m_ButtonRight.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/resultset_next.png")));
		m_ButtonRight.setEnabled(false);
		m_ButtonRight.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) 
				{
					RightButtonCallback(evt);
				}
		});
		
		//Config Handler Panel
		m_PanelConfHandler = new JPanel();
		GridBagLayout jPanelConfHandlerLayout = new GridBagLayout();
		m_PanelAssociatedParts.add(m_PanelConfHandler, new GridBagConstraints(0, 3, 8, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));
		jPanelConfHandlerLayout.rowWeights = new double[] {0.0, 0.1, 0.0};
		jPanelConfHandlerLayout.rowHeights = new int[] {9, 20, 34};
		jPanelConfHandlerLayout.columnWeights = new double[] {0.1, 0.0, 0.0, 0.0, 0.0, 0.1};
		jPanelConfHandlerLayout.columnWidths = new int[] {3, 110, 172, 132, 64, 3};
		m_PanelConfHandler.setLayout(jPanelConfHandlerLayout);
		
		//Designation textfield
		
		m_TextFieldDesignation = new JTextField();
		m_PanelAssociatedParts.add(m_TextFieldDesignation, new GridBagConstraints(4, 2, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		m_TextFieldDesignation.setEditable(false);

		//Parent Designation
		m_TextFieldParentDesignation = new JTextField();
		m_PanelConfHandler.add(m_TextFieldParentDesignation, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 2), 0, 0));
		m_TextFieldParentDesignation.setEditable(false);
		
		//Delete Button
		m_ButtonDelete = new JButton();
		m_PanelAssociatedParts.add(m_ButtonDelete, new GridBagConstraints(0, 4, 8, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		m_ButtonDelete.setText("Delete NF");
		m_ButtonDelete.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.DeleteNF","Delete NF"));
		m_ButtonDelete.setSize(90, 21);
		m_ButtonDelete.setPreferredSize(new java.awt.Dimension(90, 21));
		m_ButtonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
					deleteActionPerformed(evt);
			}
		});
	

		//Reference Combobox
		ComboBoxModel jComboBoxReferenceModel = new DefaultComboBoxModel(new String[] {  });
		m_ComboBoxReference = new JComboBox();
		m_PanelConfHandler.add(m_ComboBoxReference, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 2), 0, 0));
		m_ComboBoxReference.setModel(jComboBoxReferenceModel);
		m_ComboBoxReference.setPreferredSize(new Dimension(25, 25));
		m_ComboBoxReference.setMaximumSize(new Dimension(25, 25));
		m_ComboBoxReference.setMaximumRowCount(6);
		m_ComboBoxReference.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fillConfigHandler();						
			}
		});
			
		//Config Handler Combobox	
		ComboBoxModel jComboBoxConfHandlerModel = new DefaultComboBoxModel(new String[] { });
		m_ComboBoxConfHandler = new JHorrizontalComboBox();
		m_PanelConfHandler.add(m_ComboBoxConfHandler, new GridBagConstraints(3, 2, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 2, 5, 2), 0, 0));
		m_ComboBoxConfHandler.setModel(jComboBoxConfHandlerModel);
		m_ComboBoxConfHandler.setPreferredSize(new Dimension(110, 25));
		m_ComboBoxConfHandler.setMaximumSize(new Dimension(110, 25));
		m_ComboBoxConfHandler.setMaximumRowCount(10);
		
		//Parent Reference Label
		LabelParentRef = new JLabel();
		m_PanelConfHandler.add(LabelParentRef, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		LabelParentRef.setText("Parent Reference");
		LabelParentRef.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.ParentReference","Parent Reference"));
		LabelParentRef.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Parent Designation label
		LabelDesignation = new JLabel();
		m_PanelConfHandler.add(LabelDesignation, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		LabelDesignation.setText("Designation");
		LabelDesignation.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.Designation","Designation"));
		LabelDesignation.setHorizontalAlignment(SwingConstants.CENTER);

		//Config Handler label
		LabelConfigHandler = new JLabel();
		m_PanelConfHandler.add(LabelConfigHandler, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		LabelConfigHandler.setText("Config Handler");
		LabelConfigHandler.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.ConfigHandler","Config Handler"));
		LabelConfigHandler.setHorizontalAlignment(SwingConstants.CENTER);

	}

	/**
	 * It displays Request type panel.
	 */
	private void DisplayRequestTypePanel() {
		//Request type panel.
		m_PanelRequestType = new JPanel();
		GridBagLayout jPanelRequestTypeLayout = new GridBagLayout();
		AddModifyPanel.getContentPane().add(m_PanelRequestType, new GridBagConstraints(0, 3, 8, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		jPanelRequestTypeLayout.rowWeights = new double[] { 0.1, 0.0,0.1, 0.1 };
		jPanelRequestTypeLayout.rowHeights = new int[] { 7, 22, 7, 7 };
		jPanelRequestTypeLayout.columnWeights = new double[] { 0.0,0.0, 0.1, 0.0, 0.0, 0.1 };
		jPanelRequestTypeLayout.columnWidths = new int[] { 110, 110, 7,103, 49, 7 };
		m_PanelRequestType.setLayout(jPanelRequestTypeLayout);
		m_PanelRequestType.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				
		//Type of request label
		m_LabelTypeofRequest = new JLabel();
		m_PanelRequestType.add(m_LabelTypeofRequest,new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 5,5, 5), 0, 0));
		m_LabelTypeofRequest.setText("Type of Request");
		m_LabelTypeofRequest.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.RequestType", "Type of Request"));
		m_LabelTypeofRequest.setHorizontalAlignment(SwingConstants.CENTER);
		m_LabelTypeofRequest.setHorizontalTextPosition(SwingConstants.CENTER);

		// Init/OneShot Checkbox
		m_RadioInitOneShot = new JRadioButton();
		m_RadioInitOneShot.setHorizontalAlignment(SwingConstants.CENTER);
				
		if(m_Controller.m_CurrentOpr.getFilterPartListSize() > 0)
		{
			//Init Radio button
			m_PanelRequestType.add(m_RadioInitOneShot,new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 5,5, 10), 0, 0));
			m_RadioInitOneShot.setText("INIT");
			m_RadioInitOneShot.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.Init","Init"));
			m_RadioInitOneShot.setSelected(true);
			m_RadioInitOneShot.setEnabled(true);
			
			//VCO  Radio button
			m_RadioVCO = new JRadioButton();
			m_PanelRequestType.add(m_RadioVCO,new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 5,5, 10), 0, 0));
			m_RadioVCO.setText("VCO");
			m_RadioVCO.setHorizontalAlignment(SwingConstants.CENTER);
			m_RadioVCO.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.VCOButton", "VCO"));
			
			buttonGroupMode =  new ButtonGroup();
			buttonGroupMode.add(m_RadioInitOneShot);
			buttonGroupMode.add(m_RadioVCO);
			
			// Expiry Date Label
			m_LabelExpiryDate = new JLabel();
			m_PanelRequestType.add(m_LabelExpiryDate,new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 5,5, 10), 0, 0));
			m_LabelExpiryDate.setText("Expiry Date :");
			m_LabelExpiryDate.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.ExpiryDate", "Expiry Date :"));
			m_LabelExpiryDate.setHorizontalAlignment(SwingConstants.RIGHT);

			// Expiry Date TextField
			m_TextFieldExpiryDate = new JTextField();
			m_TextFieldExpiryDate.setEnabled(true);
			m_TextFieldExpiryDate.setEditable(false);
			m_PanelRequestType.add(m_TextFieldExpiryDate,new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER,	GridBagConstraints.BOTH, new Insets(5, 10,5, 5), 0, 0));

			// Expiry Date Button
			m_ButtonDate = new JButton();
			m_PanelRequestType.add(m_ButtonDate,new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 10,5, 10), 0, 0));
			m_ButtonDate.setText("...");
			m_ButtonDate.setEnabled(true);
			m_ButtonDate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						DateCallback(evt);
						EnableNextButton();
					}
			});
			
		}else
		{
			//One Shot Radio button
			m_PanelRequestType.add(m_RadioInitOneShot,new GridBagConstraints(0, 1, 6, 1, 0.0, 0.0,GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(5, 15,5, 10), 0, 0));
			m_RadioInitOneShot.setText("ONE SHOT");
			m_RadioInitOneShot.setText(m_Controller.m_ObjLanguageHandler.Getlabel( "Label.OneShot", "One Shot"));
			m_RadioInitOneShot.setSelected(true);
			m_RadioInitOneShot.setEnabled(false);
			m_RadioInitOneShot.setFocusable(false);
		}
		
	}

	/**
	 * It display button panel.
	 * @param buttonpos : represent the positon of button panel.
	 */
	private void DisplayButtonPanel(int buttonpos) {
		//Button panel
		m_PanelButtons = new JPanel();
		GridBagLayout jPanelButtonsLayout = new GridBagLayout();
		AddModifyPanel.getContentPane().add(m_PanelButtons, new GridBagConstraints(0, buttonpos, 8, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		jPanelButtonsLayout.rowWeights = new double[] {0.0, 0.0, 0.1, 0.0};
		jPanelButtonsLayout.rowHeights = new int[] {0, 38, 7, 0};
		jPanelButtonsLayout.columnWeights = new double[] { 0.1, 0.1,0.1 };
		jPanelButtonsLayout.columnWidths = new int[] { 7, 7, 7 };
		m_PanelButtons.setLayout(jPanelButtonsLayout);
		
		//Cancel Button
		m_ButtonCancel = new JButton();
		m_PanelButtons.add(m_ButtonCancel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,GridBagConstraints.NONE, new Insets(5, 10, 5, 10),0, 0));
		//jButtonCancel.setText("CANCEL");
		//jButtonCancel.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.CancelButton", "CANCEL"));
		m_ButtonCancel.setToolTipText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.CancelButton", "CANCEL"));
		m_ButtonCancel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/cancel.png")));
		m_ButtonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if(m_Controller.GetActionType() == ACTION.CREATEREQUEST)
				{
					m_Controller.CancelActionCallback();
				} 
				else
				{
					try {
						//Reset Request Lock.
						m_Controller.m_CurrentOpr.LockRequestForManip("INERT");
					} catch (PSAErrorHandler e)
					{
						m_Controller.displayMessage(e.m_StrErrorLabel);
					}
					m_Controller.m_CloneOpr.Clone(m_Controller.m_CurrentOpr);
					m_Controller.HidePanel(PANEL.CREATION_PANEL);
					m_Controller.ShowPanel(PANEL.REQUESTLISTE_PANEL);
				}
			}
		});
		
		//Next Button
		m_ButtonNext = new JButton();
		m_PanelButtons.add(m_ButtonNext, new GridBagConstraints(2,1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,GridBagConstraints.NONE, new Insets(5, 10, 5, 10),0, 0));
		//m_ButtonNext.setText("NEXT");
		//m_ButtonNext.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.NextButton", "NEXT"));
		m_ButtonNext.setToolTipText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.NextButton", "NEXT"));
		m_ButtonNext.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/resultset_next.png")));
		m_ButtonNext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					nextActionPerformed(evt);
				}
		});
	}

	/**
	 * Display request detail panel.
	 */
	private void DisplayRequestDetailPanel() {
		//Request detail panel
		m_PanelRequestDetails = new JPanel();
		GridBagLayout jPanelRequestDetailsLayout = new GridBagLayout();
		AddModifyPanel.getContentPane().add(m_PanelRequestDetails,new GridBagConstraints(0, 0, 8, 3, 0.0, 0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(5, 5, 5, 5), 0, 0));
		jPanelRequestDetailsLayout.rowWeights = new double[] { 0.0,	0.0, 0.0, 0.0, 0.1 };
		jPanelRequestDetailsLayout.rowHeights = new int[] { 35, 40, 40,	34, 7 };
		jPanelRequestDetailsLayout.columnWeights = new double[] { 0.1,0.1, 0.1, 0.1, 0.1, 0.1 };
		jPanelRequestDetailsLayout.columnWidths = new int[] { 7, 7, 7,	7, 7, 7 };
		m_PanelRequestDetails.setLayout(jPanelRequestDetailsLayout);
		m_PanelRequestDetails.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
		
		//Panel Info Label
		m_LabelPanelInfo = new JLabel();
		m_PanelRequestDetails.add(m_LabelPanelInfo,	new GridBagConstraints(0, 0, 6, 1, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5,5, 5), 0, 0));
		m_LabelPanelInfo.setHorizontalAlignment(SwingConstants.CENTER);
		m_LabelPanelInfo.setHorizontalTextPosition(SwingConstants.CENTER);
		m_LabelPanelInfo.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
		
       //Request Name panel
		m_LabelNameofRequest = new JLabel();
		m_PanelRequestDetails.add(m_LabelNameofRequest,	new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 10,5, 10), 0, 0));
		m_LabelNameofRequest.setText("Name of Request");
		m_LabelNameofRequest.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.RequestName", "Name of Request"));
		m_LabelNameofRequest.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//Root Reference Label
		m_LabelReference = new JLabel();
		m_PanelRequestDetails.add(m_LabelReference,new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 10,5, 10), 0, 0));
		m_LabelReference.setText("Reference of Root Part");
		m_LabelReference.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.PartReference", "Reference of Racine"));
		m_LabelReference.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//Root Designation Label
		m_LabelDesignation = new JLabel();
		m_PanelRequestDetails.add(m_LabelDesignation,new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 10,5, 10), 0, 0));
		m_LabelDesignation.setText("Desination of Racine");
		m_LabelDesignation.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.RacineDesignation", "Racine Designation"));
		m_LabelDesignation.setHorizontalAlignment(SwingConstants.RIGHT);

		//PSN File Label
		m_LabelPSNFile = new JLabel();
		m_PanelRequestDetails.add(m_LabelPSNFile,new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 10,5, 10), 0, 0));
		m_LabelPSNFile.setText("PSN File");
		m_LabelPSNFile.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.PSNFile", "PSN File"));
		m_LabelPSNFile.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//Request Name TextField
		m_TextFieldRequestName = new JTextField();
		m_PanelRequestDetails.add(m_TextFieldRequestName, new GridBagConstraints(1, 1, 4, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 10, 5, 10), 0, 0));
		m_TextFieldRequestName.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent evt) {
					EnableNextButton();
				}
		});
			
		//Reference Label
		m_LabelRacineReference = new JLabel();
		m_PanelRequestDetails.add(m_LabelRacineReference,new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 10,5, 10), 0, 0));
		m_LabelRacineReference.setText("AA90876543");

		//Racine Designation Label
		m_LabelRacineDesignation = new JLabel();
		m_PanelRequestDetails.add(m_LabelRacineDesignation,new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 10,5, 10), 0, 0));

		//PSN File Name Label
		m_LabelPSNFileName = new JLabel();
		m_PanelRequestDetails.add(m_LabelPSNFileName,new GridBagConstraints(1, 4, 5, 1, 0.0, 0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(5, 10,5, 10), 0, 0));
		
	}

	/**
	 * This function fill the reference parent combobox and set reference part.
	 * 
	 * @param index : Index of the PSNPart in the list.
	 */
	private void SetReferencePartDetail(int index) {
		
		//Check for valid index.
		if(index >= 0 || index < m_FilteredPartList.size())
		{
			
			m_ComboBoxReference.removeAllItems();
			m_ComboBoxConfHandler.removeAllItems();
			PSNPart psnPart = m_FilteredPartList.get(index).m_part;
			if(psnPart != null)
			{
				//Set reference part and designation.
				m_TextFieldReferencePart.setText(psnPart.m_StrRef);
				m_TextFieldDesignation.setText(psnPart.m_StrDesignation);
				m_ComboBoxReference.addItem("----------");
				
				//Fill parent combobox.
				ArrayList<ParentDetails> parent_details = m_FilteredPartList.get(index).m_parentList;
				for (ParentDetails parentDetails : parent_details) {
					m_ComboBoxReference.addItem(parentDetails.m_ParentRef);
					m_TextFieldParentDesignation.setText(parentDetails.m_parentDesignation);
				}
			}
		}
	}

	/**
	 * It sets selected parent details and config handler to the PSNPart object. 
	 */
	private void SetParentAndConfig() {
		
		if( partIndex != -1)
		{
			//Set selected value to PSNPart object.
			PSNPart psnPart = m_FilteredPartList.get(partIndex).m_part;
			psnPart.m_StrSelectedParentREF = (String)m_ComboBoxReference.getSelectedItem();
			psnPart.m_StrSelectedParentDesignation = m_TextFieldParentDesignation.getText().trim();
			int selectedparentindex = m_ComboBoxReference.getSelectedIndex()-1;
			if(selectedparentindex >=0)
			{
				psnPart.m_StrSelectedParentCOID = m_FilteredPartList.get(partIndex).m_parentList.get(selectedparentindex).m_ParentCOID;
				//psnPart.m_StrSelectedParentCOMPID = m_FilteredPartList.get(partIndex).m_parentList.get(selectedparentindex).m_ParentCOMPID;
				psnPart.m_StrSelectedParentEnv = m_FilteredPartList.get(partIndex).m_parentList.get(selectedparentindex).m_parentEnv;
			}
			psnPart.m_StrSelectedConf = (String)m_ComboBoxConfHandler.getSelectedItem();
			System.out.println("Selected value Parent =  " + psnPart.m_StrSelectedParentREF + " Handler = " + psnPart.m_StrSelectedConf);
		}
	}

	/**
	 * It set selected parent to parent Reference Combobox.
	 */
	private void SetSelectedParent() {

		if( partIndex >= 0 && partIndex < m_FilteredPartList.size())
		{
			try 
			{
				//Set selected part Reference to m_ComboBoxReference.
				PSNPart psnPart = m_FilteredPartList.get(partIndex).m_part;
				System.out.println("Selected Parent :"+ psnPart.m_StrSelectedParentREF);
				if(psnPart.m_StrSelectedParentREF.equals(""))
					m_ComboBoxReference.setSelectedIndex(0);
				else
					m_ComboBoxReference.setSelectedItem(psnPart.m_StrSelectedParentREF);
			}catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * It set selected Config handler to Config Handler Combobox.
	 */
	private void SetSelectedConfig() {
		int selPartIndex = partIndex;
		if( selPartIndex >= 0 && selPartIndex < m_FilteredPartList.size())
		{
			try 
			{
				//Set selected  Config Handler to m_ComboBoxConfHandler.
				PSNPart psnPart = m_FilteredPartList.get(partIndex).m_part;
				System.out.println("SelectedConf Value = " + psnPart.m_StrSelectedConf);
				if(psnPart.m_StrSelectedConf.equals(""))
					m_ComboBoxConfHandler.setSelectedIndex(0);
				else
					m_ComboBoxConfHandler.setSelectedItem(psnPart.m_StrSelectedConf);
			}catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Fill the config handler values of parent to config handler combobox.
	 */
	private void fillConfigHandler() {
		int parentIndex = m_ComboBoxReference.getSelectedIndex();
		System.out.println("parentIndex : " + parentIndex);
		m_ComboBoxConfHandler.removeAllItems();
		m_ComboBoxConfHandler.addItem("----------");
		m_TextFieldParentDesignation.setText("");
		if(parentIndex > 0)
		{
			if(m_FilteredPartList.get(partIndex).m_parentList.size() >= parentIndex)
			{
				m_TextFieldParentDesignation.setText(m_FilteredPartList.get(partIndex).m_parentList.get(parentIndex-1).m_parentDesignation);
				ArrayList<String> conf_Handler = m_FilteredPartList.get(partIndex).m_parentList.get(parentIndex-1).m_listCongHandler;
				
				//Fill config handler values.
				for (String strConfig : conf_Handler) 
				{
					m_ComboBoxConfHandler.addItem(strConfig);
				}
			}
		}
	}
	
	/**
	 * Hide or Show the Panel
	 * 
	 * @param status	  - false to Hide the panel. 
	 *            		  - true to show the panel
	 */
	public void ShowWindow(boolean bstatus) {
		System.out.println("PSAAddModifyRequestPanel::ShowWindow");
		AddModifyPanel.setLocationRelativeTo(null);
		AddModifyPanel.setVisible(bstatus);
		System.out.println("PSAAddModifyRequestPanel::ShowWindow End");
	}

	/**
	 * It hide current panel and display next panel.
	 * @param evt : Button Event
	 */
	private void nextActionPerformed(ActionEvent evt) {
		
		//Validate request name.
		String strRequestName = m_TextFieldRequestName.getText().trim();
		boolean  bValidFlag = isValidRequestName(strRequestName);
		PSAMessageDialog psaMessageDialog =  new PSAMessageDialog();
		if(bValidFlag)
		{
			String oldRequestName = "";
			if(m_Controller.GetActionType() == ACTION.CREATEREQUEST)
			{
				m_Controller.m_CurrentOpr.setRequestStatus("ACTIVE");
			} else 
			{
				oldRequestName = m_Controller.m_CloneOpr.getRequestName();
			}
			//Check request name is exist or not
			if(!strRequestName.equals(oldRequestName))
			{
				try {
					if(false == m_Controller.m_CurrentOpr.ValidateRequestName(strRequestName))
					{
						m_Controller.m_CurrentOpr.setRequestName(strRequestName);
					} else 
					{
						String error_msg = "Request with same name is already present.";
						error_msg = m_Controller.m_ObjLanguageHandler.Getlabel("Error.RequestExists", "Request with same name is already present.");
						System.out.println(error_msg);
						m_Controller.displayMessage("Error.RequestAlreadyPresent");
						return;
					}
				} catch (PSAErrorHandler e) {
					String error_msg = "Request with same name is already present.";
					error_msg = m_Controller.m_ObjLanguageHandler.Getlabel("Error.RequestExists", "Request with same name is already present.");
					System.out.println(error_msg);
					m_Controller.displayMessage("Error.RequestAlreadyPresent");
					return;
				}
			}else
			{
				m_Controller.m_CurrentOpr.setRequestName(oldRequestName);
			}
			//Expiry date 
			if(m_Controller.m_CurrentOpr.getFilterPartListSize() == 0)
			{
				m_Controller.m_CurrentOpr.setRequestMod("ONESHOT");
				m_Controller.m_CurrentOpr.setExpiryDate("");
			}else
			{
				//m_Controller.m_CurrentOpr.setRootPartID(m_LabelReference.getText());
				
				String strExpireDate = m_TextFieldExpiryDate.getText();
				System.out.println("strExpireDate : " + strExpireDate);
				m_Controller.m_CurrentOpr.setExpiryDate(strExpireDate);
				
				//set parent and config handler.
				SetParentAndConfig();
				if(m_RadioInitOneShot.isSelected())
					m_Controller.m_CurrentOpr.setRequestMod("INIT");
				else
					m_Controller.m_CurrentOpr.setRequestMod("VCO");
			}
		
			m_Controller.HidePanel(PSARequestBasePanel.PANEL.CREATION_PANEL);
			m_Controller.ShowPanel(PSARequestBasePanel.PANEL.SITESELECTION_PANEL);
			
		}
		else
		{
			String error_msg = "Request name should not be more than 40 characters.";
			error_msg = m_Controller.m_ObjLanguageHandler.Getlabel("Error.InvalidRequestName", "Request name should not be more than 40 characters.");
			System.out.println(error_msg);
			@SuppressWarnings("unused")
			PSAMessageDialog.OPTION opt =psaMessageDialog.showMessageDialog(null, error_msg, m_Controller.m_ObjLanguageHandler);
		}
	}

	/**
	 * It validate request name.
	 * @param strRequestName : Request name  
	 * @return
	 */
	private boolean isValidRequestName(String strRequestName) {
		
		if(strRequestName.length() > 40)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * It used to set the expiry date.
	 * @param evt :Button Event
	 */
	private void DateCallback(ActionEvent evt) {
		String StrDate = new PSADatePicker(AddModifyPanel,"",m_Controller.m_ObjLanguageHandler).GetDateSelected();
		if(StrDate.length() > 0)
		{
			Object[] date_conv = UtilityFunctions.String_to_date_Conversion(StrDate, "dd/MM/yyyy", m_Controller.m_ObjLanguageHandler);
			if(((Boolean)date_conv[0]).booleanValue() ==  false)
			{
				return;
			}
			Date modified_date = (Date)date_conv[1];

			StrDate = UtilityFunctions.Date_to_String_Conversion(modified_date, "yyyy/MM/dd");
		}
		m_TextFieldExpiryDate.setText(StrDate);
	}

	/**
	 * It used to set previous PSNPart details.
	 * @param evt :Button Event
	 */
	private void LeftButtonCallback(ActionEvent evt) {
		System.out.println("LeftButtonCallback Start");
		if ((partIndex+1) > 0) 
		{
			m_ButtonRight.setEnabled(true);
			SetParentAndConfig();
			partIndex--;
			updatePartListDetails();
		}
		CheckPartListButtons();
		System.out.println("LeftButtonCallback end");
	}
	
	/**
	 * It used to set next PSNPart details.
	 * @param evt :Button Event
	 */
	private void RightButtonCallback(ActionEvent evt) {
		System.out.println("RightButtonCallback Start");
		int iPartListSize = m_FilteredPartList.size();
		if ((partIndex+1) < iPartListSize)
		{
			m_ButtonLeft.setEnabled(true);
			SetParentAndConfig();
			partIndex++;
			updatePartListDetails();
		}
		CheckPartListButtons();
		System.out.println("RightButtonCallback end");
	}

	/**
	 * It delete part  from list. 
	 * @param evt : ActionEvent
	 */
	private void deleteActionPerformed(ActionEvent evt) {
		
		String msg = "Are you sure to delete selected part ?";
		msg = m_Controller.m_ObjLanguageHandler.Getlabel("Message.DeletePartConfirmation",msg);
		String title = "Confirmation";
		title = m_Controller.m_ObjLanguageHandler.Getlabel("Title.ConfirmationMsg",title);
		PSAMessageDialog.OPTION opt = new PSAMessageDialog().showMessageDialog(null, msg, title, PSAMessageDialog.BUTTON_OPTION.YES_NO_OPTION,m_Controller.m_ObjLanguageHandler);
		if(opt == PSAMessageDialog.OPTION.YES_OPTION)
		{ 
			  PSAFilteredPartDetails partDetails =   m_FilteredPartList.get(partIndex);
		  
			  if(partDetails != null)
			  {
				   try {
						m_Controller.m_CurrentOpr.addDeletePart(partDetails.m_part);
						m_FilteredPartList.remove(partIndex);
						
						if(partIndex >= m_FilteredPartList.size())
						{
							partIndex = m_FilteredPartList.size()-1;
						}
						updatePartListDetails();
				   } catch (Exception e) {
					   e.printStackTrace();
				   }
			  }
		}
	  checkDeleteButton();
	  CheckPartListButtons();
	}
	
	/**
	 * It enable or deisable Delete NF button.
	 */
	private void checkDeleteButton() {
		m_ButtonDelete.setEnabled((m_FilteredPartList.size() == 1) ? false : true);
	}

	/**
	 * It update the value parent combobox  ,config handler combobox .
	 * 
	 * */
	private void updatePartListDetails() {
		System.out.println("PSAAddModifyRequestPanel::updatePartListDetails");
		int iPartListSize = 0;
		iPartListSize = m_FilteredPartList.size();
		String str = m_Controller.m_ObjLanguageHandler.Getlabel("Label.FilteringPart", "Filtering Part : ")
					+ (partIndex+1) + "/ " + iPartListSize;
		m_LabelFilteringPart.setText(str);
		
		SetReferencePartDetail(partIndex);
		SetSelectedParent();
		fillConfigHandler();
		SetSelectedConfig();
	}

	/**
	 * It used to enable or disable left and right button.
	 * 
	 */
	private void CheckPartListButtons() {
		int iPartListSize = 0;
		iPartListSize = m_FilteredPartList.size();
		System.out.println("Part Inde value  == "+partIndex);
		m_ButtonLeft.setEnabled((partIndex > 0) ? true : false);
		m_ButtonRight.setEnabled((partIndex < (iPartListSize-1)) ? true : false);
	}

	/**
	 * It used to set PSN file name.  
	 * @param name : PSN File name 
	 */
	public void setPSNFileName(String name) {
		m_LabelPSNFileName.setText(name);
	}
	
	/**
	 *  It used to enable or disable next button.
	 */
	private void EnableNextButton()
	{
		String requestName = m_TextFieldRequestName.getText().trim();
		boolean bFlag = false;
		if(requestName != null && requestName.length() > 0)
		{
			bFlag = true;
		}
		if(m_Controller.m_CurrentOpr.getFilterPartListSize() > 0 && bFlag == true)
		{
			String expireDate = m_TextFieldExpiryDate.getText().trim();
		
			if(expireDate == null || expireDate.length() == 0)
			{
				bFlag = false;
			}
		}
		m_ButtonNext.setEnabled(bFlag);
	}
	
}


