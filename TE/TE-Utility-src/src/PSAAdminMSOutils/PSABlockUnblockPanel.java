/**********************************************************************************************************
 *
 * FILE NAME	  : PSABlockUnblockPanel.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class used to block or unblock request.
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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;

//User class import
import com.psa.tools.PSAErrorHandler;

/**
* Class used to block or unblock request.
*/
public class PSABlockUnblockPanel extends PSARequestBasePanel{

	private JFrame BlockUnblockPanel;
	private JButton m_ButtonOk;
	private JButton m_ButtonCancel;
	private JPanel m_PanelButton;
	private JPanel m_ListPanel;
	private PSADoubleList m_ListBlockUnblock;

	/**
	 * Constructor of the class.
	 * @param iController : PSARequestController
	 * @throws PSAErrorHandler
	 */
	public PSABlockUnblockPanel(PSARequestController iController) throws PSAErrorHandler {
		super(iController);
		initGUI();
	}
	
	/**
	 *  It  creates and initialize components. 
	 * @throws PSAErrorHandler
	 */
	private void initGUI()  throws PSAErrorHandler{
		try 
		{
			BlockUnblockPanel =  new JFrame();
			GridBagLayout BlockUnblockPanelLayout = new GridBagLayout();
			//BlockUnblockPanelLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.0, 0.0};
			BlockUnblockPanelLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.0, 0.0};
			BlockUnblockPanelLayout.rowHeights = new int[] {7, 7, 7, 7, 7, 7, 7, 7, 39, 56};
			BlockUnblockPanelLayout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
			BlockUnblockPanelLayout.columnWidths = new int[] {80, 80, 179, 90, 125, 212};
			BlockUnblockPanel.getContentPane().setLayout(BlockUnblockPanelLayout);
			BlockUnblockPanel.setTitle("Block / Unblock Request");
			BlockUnblockPanel.setTitle(m_Controller.m_ObjLanguageHandler.Getlabel("Title.BlockUnblockRequestPanel","Block / Unblock Request"));
			BlockUnblockPanel.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			BlockUnblockPanel.setPreferredSize(new java.awt.Dimension(765, 493));

			createListPanel();
			createButtonPanel();
			BlockUnblockPanel.setSize(774, 431);
			//BlockUnblockPanel.setResizable(false);
		}catch(Exception e){
			
		}
	}
	
	/**
	 *It creates buttons. 
	 */
	private void createButtonPanel() {
		//Button panel
		m_PanelButton = new JPanel();
		GridBagLayout m_PanelButtonLayout = new GridBagLayout();
		m_PanelButtonLayout.rowWeights = new double[] {0.0};
		m_PanelButtonLayout.rowHeights = new int[] {53};
		m_PanelButtonLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
		m_PanelButtonLayout.columnWidths = new int[] {7, 7, 7, 7};
		BlockUnblockPanel.getContentPane().add(m_PanelButton, new GridBagConstraints(0, 9, 6, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		m_PanelButton.setLayout(m_PanelButtonLayout);
		
		//Cancel button
		m_ButtonCancel = new JButton();
		m_PanelButton.add(m_ButtonCancel, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		//m_ButtonCancel.setText("Cancel");
		//m_ButtonCancel.setText(lang_handler.Getlabel("Label.CancelButton","Cancel"));
		m_ButtonCancel.setToolTipText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.CancelButton","Cancel"));
		m_ButtonCancel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/cancel.png")));
		m_ButtonCancel.setSize(70, 25);
		m_ButtonCancel.setPreferredSize(new java.awt.Dimension(70, 25));
		m_ButtonCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					m_Controller.CancelActionCallback();
				}
		});
		
		//Ok Button
		m_ButtonOk = new JButton();
		m_PanelButton.add(m_ButtonOk, new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		//m_ButtonOk.setText("OK");
		m_ButtonOk.setToolTipText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.OKButton","OK"));
		m_ButtonOk.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/tick.png")));
		m_ButtonOk.setSize(70, 25);
		m_ButtonOk.setPreferredSize(new java.awt.Dimension(70, 25));
		m_ButtonOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					okActionPerformed(evt);
				}
		});
		
		m_PanelButton.setSize(new java.awt.Dimension(480, 100));
		//m_PanelButton.setPreferredSize(new java.awt.Dimension(541, 47));

	}
  
	/**
	 * It set block and unblock request.
	 * @param evt : ActionEvent
	 */
	private void okActionPerformed(ActionEvent evt) {
		System.out.println("jButtonOK.actionPerformed, event="+evt);
		try {
			m_Controller.m_CurrentOpr.BlockUnblockRequest(m_ListBlockUnblock.getDeletedList(), m_ListBlockUnblock.getAddedList());
			
			//Remove earlier contents of m_oprList
			m_Controller.m_oprList = null;
			
			m_Controller.DisplayMainPanel();
		} catch (PSAErrorHandler e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * It creates List panel.
	 */
	private void createListPanel() {
		
		//List panel
		m_ListPanel = new JPanel();
		BorderLayout m_ListPanelLayout = new BorderLayout();
		m_ListPanel.setLayout(m_ListPanelLayout);
		m_ListPanel.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
		BlockUnblockPanel.getContentPane().add(m_ListPanel, new GridBagConstraints(0, 0, 6, 9, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

		//Block and Unblock list
		m_ListBlockUnblock = new PSADoubleList(m_Controller.m_ObjLanguageHandler.Getlabel("Label.RequestActive","Request Active"),
													m_Controller.m_ObjLanguageHandler.Getlabel("Label.RequestBlocked","Request Blocked"));
		m_ListPanel.add(m_ListBlockUnblock, BorderLayout.CENTER);
		m_ListBlockUnblock.setToolTipLeftToRight(m_Controller.m_ObjLanguageHandler.Getlabel("Label.BlockButton","Block"));
		m_ListBlockUnblock.setToolTipRightToLeft(m_Controller.m_ObjLanguageHandler.Getlabel("Label.UnBlockButton","Unblock"));
		m_ListBlockUnblock.setWidth(150);
		//m_ListBlockUnblock.setPreferredSize(new java.awt.Dimension(447, 297));
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
		BlockUnblockPanel.setLocationRelativeTo(null);
		BlockUnblockPanel.setVisible(bstatus);
		System.out.println("PSASiteTimeSlotSelection::ShowWindow End");
	}
	
	/**
	 * It inserts values unblock list and  block list.
	 * @return boolean
	 * @throws PSAErrorHandler
	 */
	public boolean InsertRequest() throws PSAErrorHandler {
		ArrayList<String> activeRequestList = new ArrayList<String>();
		ArrayList<String> inactiveRequestList = new ArrayList<String>();
		
		//Get active and inactive request list. 
		m_Controller.m_CurrentOpr.QueryListOfREQ(activeRequestList,inactiveRequestList);
		if(activeRequestList.size() == 0 && inactiveRequestList.size() == 0)
		{
			return  false;
		}	
		m_ListBlockUnblock.setSelectedValues(inactiveRequestList);
		m_ListBlockUnblock.setAvailableValues(activeRequestList);
		return  true;
	}

	

}


