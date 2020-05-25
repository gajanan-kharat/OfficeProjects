/**********************************************************************************************************
 *
 * FILE NAME	  : PSAConsultationLogPanel.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class used to display , delete  and save log of the request.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/
package com.psa.PSAAdminMSOutils;

//Standard class import
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;

//User class import
import com.psa.tools.PSAErrorHandler;
import com.psa.tools.PSAMessageDialog;

/**
 * 
 *Class used to display , delete  and save log of the request.
 */
public class PSAConsultationLogPanel extends PSARequestBasePanel {

	private JFrame ConsultationLogPanel = null;
	private JPanel m_PanelRequest;
	
	private JScrollPane m_ScrollPaneRequestDetails;
	PSAConsulationLogDataModel DataModel;
	private JTable m_TableReqDetailsList;
	
	private JLabel m_LabelRequest;
	private JComboBox m_ComboBoxRequestName;
	private JButton m_ButtonDeleteLog;
	private JButton m_ButtonShowLog;
	
	private JPanel m_PanelButton;
	private JButton m_ButtonCancel;
	private JButton m_ButtonSaveLog;
	private String[] m_requestNameList;
	private File Prev_path;
	
	/**
	 * Constructor of the class.
	 * @param iController :PSARequestController
	 * @throws PSAErrorHandler 
	 */
	public PSAConsultationLogPanel(PSARequestController iController, String[] requestNameList) throws PSAErrorHandler {
		super(iController);
		m_requestNameList = requestNameList;
		initGUI();
	}
	
	/**
	 * It  creates and initialize components. 
	 */
	private void initGUI()throws PSAErrorHandler {
		try {
			System.out.println("ConsultationLogPanel Init()");
			ConsultationLogPanel = new JFrame();
			BorderLayout consulationLogLayout = new BorderLayout();
			ConsultationLogPanel.setLayout(consulationLogLayout);
			ConsultationLogPanel.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			ConsultationLogPanel.setTitle(m_Controller.m_ObjLanguageHandler.Getlabel("Label.ProcessingLogTitle", "Log of processing for request"));
			ConsultationLogPanel.setPreferredSize(new java.awt.Dimension(699, 363));
			ConsultationLogPanel.setSize(699, 363);
			
			createRequestPanel();
			createRequestDetailsPanel();
			createButtonPanel();
			

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create request detail panel.
	 */
	private void createRequestDetailsPanel() {
		
			//Request detail panel.
			JPanel m_PaneRequestDetails =  new JPanel();
			Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
			m_PaneRequestDetails.setLayout(new BorderLayout());
			m_PaneRequestDetails.setBorder(emptyBorder);
			
			//Request detail table header.
		    String[] Headers = {
									m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderRequest", "Request") ,
									m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderAction", "Action"),
									m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderType", "Type"),
									m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderDate", "Date"),
									m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderUtilisateur", "Utilisateur"),
									m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderValuer", "Valuer")
								};
		    
		   //Request detail table.
		    DataModel = new PSAConsulationLogDataModel(Headers);
			m_TableReqDetailsList = new JTable();
			m_TableReqDetailsList.setModel(DataModel);
			m_TableReqDetailsList.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			m_TableReqDetailsList.setColumnSelectionAllowed(false);
			m_TableReqDetailsList.getTableHeader().setReorderingAllowed(false);
			m_ScrollPaneRequestDetails = new JScrollPane(m_TableReqDetailsList);
			m_PaneRequestDetails.add(m_ScrollPaneRequestDetails, BorderLayout.CENTER);
			ConsultationLogPanel.add(m_PaneRequestDetails, BorderLayout.CENTER);
	}

	/**
	 * It displays the Request log data  on the panel.
	 * @param selectedItem : Selected request name from combobox.
	 * @return
	 */
	private int InsertRequestLogData(String selectedItem) {
		
		if(selectedItem.length() == 0)
		{
			return 0;
		}
		ArrayList<PSARequestLog> requestLogsArrayList = null;
		int size=0;
		try 
		{
			ConsultationLogPanel.setCursor(WAIT_CURSOR);
			
		    //Get request detail of the selected item.	
			requestLogsArrayList = m_Controller.m_CurrentOpr.GetRequestDetails(selectedItem);
			if(requestLogsArrayList != null)
			{
				size = requestLogsArrayList.size();
			}
			ConsultationLogPanel.setCursor(DEFAULT_CURSOR);
		} catch (PSAErrorHandler e) {
			ConsultationLogPanel.setCursor(DEFAULT_CURSOR);
			m_Controller.displayMessage(e.m_StrErrorLabel);
		}
		
		DataModel.refreshList(requestLogsArrayList);
		m_ScrollPaneRequestDetails.setViewportView(m_TableReqDetailsList);
		return size;
	}
	
    /**
     * Create request panel.
     * @throws PSAErrorHandler
     */
	private void createRequestPanel() throws PSAErrorHandler {
		//Request panel.
		m_PanelRequest  = new JPanel();
		GridBagLayout requestPanelLayout = new GridBagLayout();
		requestPanelLayout.rowWeights = new double[] {0.1, 0.1};
		requestPanelLayout.rowHeights = new int[] {7, 7};
		requestPanelLayout.columnWeights = new double[] {0.1, 0.1, 0.0, 0.0, 0.1};
		requestPanelLayout.columnWidths = new int[] {7, 7, 256, 95, 7};
		ConsultationLogPanel.getContentPane().add(m_PanelRequest, BorderLayout.NORTH);
		m_PanelRequest.setLayout(requestPanelLayout);
		
	   //Request Label 
		m_LabelRequest = new JLabel();
		m_PanelRequest.add(m_LabelRequest, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		m_LabelRequest.setText("Request / PSN");
		m_LabelRequest.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.RequestPSN", "Request / PSN"));
		m_LabelRequest.setSize(50, 21);
		m_LabelRequest.setHorizontalAlignment(SwingConstants.RIGHT);

		//Request name Combobox.
		ConsultationLogPanel.setCursor(WAIT_CURSOR);
		m_ComboBoxRequestName = new JComboBox(m_requestNameList);
		m_ComboBoxRequestName.setSelectedIndex(0);
		ConsultationLogPanel.setCursor(DEFAULT_CURSOR);
		m_PanelRequest.add(m_ComboBoxRequestName, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		m_ComboBoxRequestName.setSize(100, 21);
		m_ComboBoxRequestName.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent eve) {
						ComboBoxRequestNameActionPerformed(eve)	;				
					}
		});
		
		//Show log button.
		m_ButtonShowLog = new JButton();
		m_PanelRequest.add(m_ButtonShowLog, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
		m_ButtonShowLog.setText("Show Log");
		m_ButtonShowLog.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.ShowLog", "Show Log"));
		m_ButtonShowLog.setSize(85, 21);
		m_ButtonShowLog.setEnabled(false);
		m_ButtonShowLog.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					showLogActionPerformed(evt);
				}		
		});
		
		//Delete log button.
		m_ButtonDeleteLog = new JButton();
		m_PanelRequest.add(m_ButtonDeleteLog, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
		m_ButtonDeleteLog.setText("Delete Log");
		m_ButtonDeleteLog.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.DeleteLog", "Delete Log"));
		m_ButtonDeleteLog.setSize(85, 21);
		m_ButtonDeleteLog.setEnabled(false);
		m_ButtonDeleteLog.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					deleteLogActionPerformed(evt);
				}
		});
		
		m_PanelRequest.setSize(new java.awt.Dimension(549, 100));
		m_PanelRequest.setPreferredSize(new java.awt.Dimension(540, 47));
		
	}

	/**
	 * It has enable and disable related button.
	 * @param eve : ActionEvent
	 */
	private void ComboBoxRequestNameActionPerformed(ActionEvent eve) {
		String selectedItem = (String)m_ComboBoxRequestName.getSelectedItem();
		if(!selectedItem.equals(""))
		{
			m_ButtonShowLog.setEnabled(true);
		}else
		{
			m_ButtonShowLog.setEnabled(false);
		}
		refreshTable();
		m_ButtonSaveLog.setEnabled(false);
		m_ButtonDeleteLog.setEnabled(false);
		
	}
	
	/**
	 * Display the ditail of the selected request.
	 * @param evt : ActionEvent
	 */
	private void showLogActionPerformed(ActionEvent evt) {
		String selectedItem = (String)m_ComboBoxRequestName.getSelectedItem();
		m_ButtonShowLog.setEnabled(false);
		int Req_Count = InsertRequestLogData(selectedItem);
		
		if(Req_Count > 0)
		{
			m_ButtonSaveLog.setEnabled(true);
			m_ButtonDeleteLog.setEnabled(true);
		}else
		{
			m_ButtonSaveLog.setEnabled(false);
			m_ButtonDeleteLog.setEnabled(false);
		}
		
	}
	
	/**
	 * Create the button panel.
	 */
	private void createButtonPanel() {
		//button panel
		m_PanelButton = new JPanel();
		GridBagLayout m_PanelButtonLayout = new GridBagLayout();
		m_PanelButtonLayout.rowWeights = new double[] {0.1, 0.1};
		m_PanelButtonLayout.rowHeights = new int[] {7, 7};
		m_PanelButtonLayout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.1};
		m_PanelButtonLayout.columnWidths = new int[] {135, 135, 135, 135};
		ConsultationLogPanel.getContentPane().add(m_PanelButton, BorderLayout.SOUTH);
		m_PanelButton.setLayout(m_PanelButtonLayout);
		
		//Cancel Button.
		m_ButtonCancel = new JButton();
		m_PanelButton.add(m_ButtonCancel, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		//m_ButtonCancel.setText("Cancel");
		m_ButtonCancel.setToolTipText("Cancel");
		m_ButtonCancel.setToolTipText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.CancelButton", "CANCEL"));
		m_ButtonCancel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/cancel.png")));
		m_ButtonCancel.setSize(85, 21);
		m_ButtonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				m_Controller.CancelActionCallback();
			}
		});
		
		//SaveLog Button
		m_ButtonSaveLog = new JButton();
		m_PanelButton.add(m_ButtonSaveLog, new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		m_ButtonSaveLog.setText("Save Log");
		m_ButtonSaveLog.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.SaveLog", "Save Log"));
		m_ButtonSaveLog.setSize(85, 21);
		m_ButtonSaveLog.setEnabled(false);
		m_ButtonSaveLog.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					saveLogActionPerformed(evt);
				}
		});
		m_PanelButton.setSize(new java.awt.Dimension(549, 100));
		
	}

	/**
	 * It used to save details of the seletected request to the given file.
	 * @param logFileName : Save File path 
	 */
	private void SaveDataToCSVFile(String logFileName) {
		FileOutputStream fileOutputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		try
		{
			//Save the file data.
			if(logFileName != null && logFileName.length() > 0)
			{
				fileOutputStream = new FileOutputStream(logFileName);
				 bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
				ArrayList<PSARequestLog> psaLogList = DataModel.getRequestLogList();
				for (PSARequestLog psaRequestLog : psaLogList) {
						bufferedOutputStream.write(psaRequestLog.toString().getBytes());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Close open stream.
		try 
		{
			if(bufferedOutputStream != null)
				bufferedOutputStream.close();
			
			if(fileOutputStream != null)
				fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    /**
     * This function selected file to save log contain. 
     * @param evt : ActionEvent
     */
	private void saveLogActionPerformed(ActionEvent evt) {
		
		System.out.println("ButtonSaveFileSelectionActionPerformed");
		//File Chooser
		JFileChooser filechooser =  new JFileChooser(Prev_path);
		FileFilter filter = new ExtensionFileFilter("CSV Files", new String("csv"));
		filechooser.setFileFilter(filter);
		boolean bFileselectionStatus = false;
		String strDefaultName= (String)m_ComboBoxRequestName.getSelectedItem()  + ".csv";
		File f = new File(strDefaultName);
		filechooser.setSelectedFile(f);
		int retVal = filechooser.showSaveDialog(ConsultationLogPanel);
		String StrCSV_file_name = "";
		
		//File selected.
		if(retVal == JFileChooser.APPROVE_OPTION)					
		{
			File file = filechooser.getSelectedFile();
			StrCSV_file_name = file.getName();
			System.out.println("Selected File Name = "+StrCSV_file_name);
			StrCSV_file_name = StrCSV_file_name.toUpperCase();
			int indexOfDot = StrCSV_file_name.lastIndexOf(".");
			//file path contains  extension or not.
			if(indexOfDot != -1) 
			{
				Prev_path = filechooser.getCurrentDirectory();
				 String extension = StrCSV_file_name.substring(indexOfDot+1);
				if(extension.equals("CSV"))
				{
					StrCSV_file_name = file.getPath();
					System.out.println("Selected File = "+StrCSV_file_name);
					bFileselectionStatus = true;
				} 
				else
				{
					String error_msg = "Selected File is not CSV File.";
					error_msg = m_Controller.m_ObjLanguageHandler.Getlabel("Error.FileSelectionError",error_msg);
					@SuppressWarnings("unused")
					PSAMessageDialog.OPTION opt = new PSAMessageDialog().showMessageDialog(null, error_msg,m_Controller.m_ObjLanguageHandler);
					bFileselectionStatus =false;
				}
			} 
			else
			{		
				//Extension is not mentioned so attaching .csv extension with file name.
				Prev_path = filechooser.getCurrentDirectory();
				StrCSV_file_name = file.getPath() + ".csv";
				System.out.println("Without extension Selected File = "+StrCSV_file_name);
				bFileselectionStatus = true;
			}
		} 
		else 
		{
			bFileselectionStatus = false;
		}
		
		//Selected CSV file for Save.
		if(true == bFileselectionStatus)
		{
			if(StrCSV_file_name.length() > 0)
			{
				File File_fp = new File(StrCSV_file_name);
				if(File_fp.exists())
				{
					String msg = "Do you want to overwrite Exisiting File ?";
					msg = m_Controller.m_ObjLanguageHandler.Getlabel("Message.ConfirmationMsg",msg);
					String title = "Confirmation";
					title = m_Controller.m_ObjLanguageHandler.Getlabel("Title.ConfirmationMsg",title);
					PSAMessageDialog.OPTION opt = new PSAMessageDialog().showMessageDialog(null, msg, title, PSAMessageDialog.BUTTON_OPTION.YES_NO_OPTION,m_Controller.m_ObjLanguageHandler);
					if(opt == PSAMessageDialog.OPTION.NO_OPTION)
					{
						System.out.println("ButtonSaveFileSelectionActionPerformed End");
						return;
					} 
				} 
				
				//Save the log data into the selected file.
				ConsultationLogPanel.setCursor(WAIT_CURSOR);
				SaveDataToCSVFile(StrCSV_file_name);
				ConsultationLogPanel.setCursor(DEFAULT_CURSOR);
			}
		}
	}

	/**
	 * Delete the selected request details.
	 * @param evt
	 */
	private void deleteLogActionPerformed(ActionEvent evt) {
		String strRequestName = (String)m_ComboBoxRequestName.getSelectedItem();
		if(strRequestName != null && strRequestName.length() > 0)
		{
			try
			{
				String msg = "Are you sure to delete log for selected request ?";
				msg = m_Controller.m_ObjLanguageHandler.Getlabel("Message.DeleteLogConfirmation",msg);
				String title = "Confirmation";
				title = m_Controller.m_ObjLanguageHandler.Getlabel("Title.ConfirmationMsg",title);
				PSAMessageDialog.OPTION opt = new PSAMessageDialog().showMessageDialog(null, msg, title, PSAMessageDialog.BUTTON_OPTION.YES_NO_OPTION,m_Controller.m_ObjLanguageHandler);
				if(opt == PSAMessageDialog.OPTION.YES_OPTION){
					
					//Delete request log.
					ConsultationLogPanel.setCursor(WAIT_CURSOR);
					m_Controller.m_CurrentOpr.DeleteRequestLog(strRequestName);
					m_ComboBoxRequestName.removeItem(strRequestName);
					refreshTable();
					ConsultationLogPanel.setCursor(DEFAULT_CURSOR);
					m_ComboBoxRequestName.setSelectedIndex(0);
				} else if(opt == PSAMessageDialog.OPTION.NO_OPTION){
				} 

			} catch (PSAErrorHandler e) {
			    System.out.println("Delete log error : "+e.m_StrErrorLabel);
			}
		}
		
	}
	/** 
	 * Hide or Show the Panel
	 * @param	status	- false to Hide the panel.
	 * 					- true to show the panel
	 */
	@Override
	public void ShowWindow(boolean bstatus)
	{
		System.out.println("PSAConsultationLogPanel::ShowWindow");
		ConsultationLogPanel.setLocationRelativeTo(null);
		ConsultationLogPanel.setVisible(bstatus);
		System.out.println("PSAConsultationLogPanel::ShowWindow End");
	}
	
	/**
	 * Refresh request details table.
	 */
	private void refreshTable()
	{
		DataModel.clearList();
		m_ScrollPaneRequestDetails.setViewportView(m_TableReqDetailsList);
		
	}
}




