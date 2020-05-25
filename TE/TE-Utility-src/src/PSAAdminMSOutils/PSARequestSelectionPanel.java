//COPYRIGHT PSA Peugeot Citroen 2011
/**********************************************************************************************************
 *
 * FILE NAME	  : PSARequestSelectionPanel.java
 *
 * SOCIETE        : PSA
 * PROJET         : 
 * VERSION        : V1.0
 * DATE           : 05/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    : Class for Display and Handling of Main Panel.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/

package com.psa.PSAAdminMSOutils;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JButton;

import com.psa.tools.PSALanguageHandler;



/**
 * Class for Display and Handling of Main Panel. 
 *  
 * @author      Mahendra Chuttar
 *  
 * @version     %I%, %G%
 *  
 * @since       1.0
 */
public class PSARequestSelectionPanel extends PSARequestBasePanel {
	//Controls on Main Panel
	private JFrame  m_MainPanel;
	private JButton m_ButtonCreateRequest;
	private JButton m_ButtonUpdateRequest;
	private JButton m_ButtonViewRequest;
	private JButton m_ButtonEstimateData;
	private JButton m_ButtonBlockRequest;
	private JButton m_ButtonDeleteRequest;
	private JButton m_ButtonConsultationLog;
	private JButton m_ButtonExit;
	

	/**
	 * Constructor of the class.
	 * @param iController	Language Handler Object
	 */
	public PSARequestSelectionPanel(PSARequestController iController) {
		super(iController);
		initGUI();
	}

	/**
	 * Initializes the controls on the Panel.
	 */
	private void initGUI() {
		try 
		{
			PSALanguageHandler lang_handler = m_Controller.m_ObjLanguageHandler;
			//Main Panel
			m_MainPanel =  new JFrame();
			//GridBag Layout
			GridBagLayout thisLayout = new GridBagLayout();
			thisLayout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1};
			thisLayout.rowHeights = new int[] {11, 36, 36, 36, 36, 36, 36, 36, 20};
			thisLayout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.1};
			thisLayout.columnWidths = new int[] {30, 66, 14, 113, 20};
			m_MainPanel.getContentPane().setLayout(thisLayout);
			m_MainPanel.getContentPane().setBackground(new java.awt.Color(236,233,216));
			m_MainPanel.setAlwaysOnTop(true);
			m_MainPanel.setResizable(false);
			m_MainPanel.setPreferredSize(new java.awt.Dimension(234, 337));
			m_MainPanel.setTitle(lang_handler.Getlabel("Title.MainPanel", "Admin Multisite Europe"));
			
			//CreateRequest Button
			m_ButtonCreateRequest = new JButton();
			m_ButtonCreateRequest.setText("Create Request");
			m_ButtonCreateRequest.setText(lang_handler.Getlabel("Label.CreateRequestBtn","Create Request"));
			m_MainPanel.getContentPane().add(m_ButtonCreateRequest, new GridBagConstraints(0, 1, 4, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 20, 0, 20), 0, 0));
			m_ButtonCreateRequest.setSize(152, 30);
			m_ButtonCreateRequest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					SetActionType(ACTION.CREATEREQUEST);
					m_Controller.OnActionCallback(evt);
				}
			});

			//Update Request Button			
			m_ButtonUpdateRequest = new JButton();
			m_ButtonUpdateRequest.setText("Update Request");
			m_ButtonUpdateRequest.setText(lang_handler.Getlabel("Label.UpdateRequestBtn","Update Request"));
			m_MainPanel.getContentPane().add(m_ButtonUpdateRequest, new GridBagConstraints(0, 2, 4, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 20, 0, 20), 0, 0));
			m_ButtonUpdateRequest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					SetActionType(ACTION.UPDATEREQUEST);
					m_Controller.OnActionCallback(evt);
				}
			});
					
			//View Request Button
			m_ButtonViewRequest = new JButton();
			m_ButtonViewRequest.setText("View Request");
			m_ButtonViewRequest.setText(lang_handler.Getlabel("Label.ViewRequestBtn","View Request"));
			m_MainPanel.getContentPane().add(m_ButtonViewRequest, new GridBagConstraints(0, 3, 4, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 20, 0, 20), 0, 0));
			m_ButtonViewRequest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					SetActionType(ACTION.VIEWREQUEST);	
					m_Controller.OnActionCallback(evt);
				}
			});
			
			//Estimate Data Button
			m_ButtonEstimateData = new JButton();
			m_ButtonEstimateData.setText("Estimate Data");
			m_ButtonEstimateData.setText(lang_handler.Getlabel("Label.EstimateDataBtn","Estimate Data"));
			m_MainPanel.getContentPane().add(m_ButtonEstimateData, new GridBagConstraints(0, 4, 4, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 20, 0, 20), 0, 0));
			m_ButtonEstimateData.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					SetActionType(ACTION.ESTIMATEDATA);
					m_Controller.OnActionCallback(evt);
				}
			});
			
			//Block / Unblock Request Button
			m_ButtonBlockRequest = new JButton();
			m_MainPanel.getContentPane().add(m_ButtonBlockRequest, new GridBagConstraints(0, 5, 4, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 20, 0, 20), 0, 0));
			m_ButtonBlockRequest.setText("Block / Unblock Request");
			m_ButtonBlockRequest.setText(lang_handler.Getlabel("Label.BlockRequestBtn","Block / Unblock Request"));
			m_ButtonBlockRequest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					SetActionType(ACTION.BLOCKREQUEST);
					m_Controller.OnActionCallback(evt);
				}
			});

			//Delete Request Button
			m_ButtonDeleteRequest = new JButton();
			m_MainPanel.getContentPane().add(m_ButtonDeleteRequest, new GridBagConstraints(0, 6, 4, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 20, 0, 20), 0, 0));
			m_ButtonDeleteRequest.setText("Delete Request");
			m_ButtonDeleteRequest.setText(lang_handler.Getlabel("Label.DeleteRequestBtn","Delete Request"));
			m_ButtonDeleteRequest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					SetActionType(ACTION.DELETEREQUEST);
					m_Controller.OnActionCallback(evt);
				}
			});

			//Consultation Log Button
			m_ButtonConsultationLog = new JButton();
			m_MainPanel.getContentPane().add(m_ButtonConsultationLog, new GridBagConstraints(0, 7, 4, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 20, 0, 20), 0, 0));
			m_ButtonConsultationLog.setText("Consultation Log");
			m_ButtonConsultationLog.setText(lang_handler.Getlabel("Label.ConsultationLogBtn","Consultation Log"));
			m_ButtonConsultationLog.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					SetActionType(ACTION.CONSULTATIONLOG);
					m_Controller.OnActionCallback(evt);
				}
			});

			//Exit Button
			m_ButtonExit = new JButton();
			m_ButtonExit.setText("Exit");
			m_ButtonExit.setText(lang_handler.Getlabel("Label.ExitBtn","Exit"));
			m_MainPanel.getContentPane().add(m_ButtonExit, new GridBagConstraints(0, 8, 4, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 20, 0, 20), 0, 0));
			m_ButtonExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					SetActionType(ACTION.EXIT);
					m_Controller.OnActionCallback(evt);
				}
			});
			
			m_MainPanel.pack();
			m_MainPanel.setSize(234, 337);
			m_MainPanel.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
					SetActionType(ACTION.EXIT);
					m_Controller.exitApplication();
				}
				
			});
		} catch (Exception e) {
			System.out.println("Exception in PSARequestSelectionPanel::InitGUI");
			e.printStackTrace();
		}
	}

	/** 
	 * Hide or Show the Panel
	 * @param	status	- false to Hide the panel.
	 * 					- true to show the panel
	 */
	public void ShowWindow(boolean status)
	{
		m_MainPanel.setLocationRelativeTo(null);
		m_MainPanel.setVisible(status);
	}
	
	/** 
	 * Dispose Panel
	 */
	public void disposePanel()
	{
		m_MainPanel.dispose();
	}
	
}
