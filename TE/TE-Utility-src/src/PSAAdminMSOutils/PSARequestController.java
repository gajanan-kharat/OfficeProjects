/**********************************************************************************************************
 *
 * FILE NAME	  : PSARequestController.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class used to control the flow of panel.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/
package com.psa.PSAAdminMSOutils;

//Standard class import
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

//User class import
import com.psa.PSAAdminMSOutils.PSARequestBasePanel.ACTION;
import com.psa.PSAAdminMSOutils.PSARequestBasePanel.PANEL;
import com.psa.tools.PSAErrorHandler;
import com.psa.tools.PSALanguageHandler;
import com.psa.tools.PSAMessageDialog;
import com.psa.tools.PSAWaitPanel;

/**
 * Class used to control the flow of panel.
 */
public class PSARequestController {

	private ArrayList<PSARequestBasePanel> List_Panel = new ArrayList<PSARequestBasePanel>(); // Panel  List to Hide and chow the panel.
	private ACTION m_ActionType = ACTION.DEFAULT; 						//Selected Action from Main Panel.
	private String m_StringLanguageName = "fr"; 						//Selected Language.
	private String m_StringModuleName = "PSAAdminMultiSiteOutils"; 		//Module Name
	public PSALanguageHandler m_ObjLanguageHandler = null; 				//Language Handler object
	public PSAAdminMSOutilsOpr m_CurrentOpr = null;						//Opr for currently selected Request.
	public PSAAdminMSOutilsOpr m_CloneOpr = null;						//Clone Opr to check previous values
	public int m_OprIndex = 0;											//Selected Opr Index
	public ArrayList<PSAAdminMSOutilsOpr> m_oprList = null;				//Opr List
	JFileChooser fileChooser = null;									//Used for file selection
	FileFilter filter = null;											//File FIlter
	private PSAWaitPanel wait_panel_obj = null;							//Wait panel object
	private boolean m_ErrorStatus = false;								//Error status
	private String m_StrPSNFileName = "";								//Selected PSN FIle Name

	/**
	 * Constructor of class.
	 */
	PSARequestController() {
		m_ObjLanguageHandler = new PSALanguageHandler();
		fileChooser = new JFileChooser();
		filter = new ExtensionFileFilter("PSN Files", new String("psn"));
	}

	/**
	 * Main function of executable.
	 * 
	 * @param String
	 *            [] args Parameters
	 */
	public static void main(String[] args) {

		System.out.println("PSARequestController::Main");

		PSARequestController controller = new PSARequestController();
		controller.Init();
	}

	/**
	 * It used to  initalize the lanuage handler and display the  main panel
	 */
	public void Init() {
		System.out.println("PSARequestController::Init()");

		// Load Language File.
		Object[] error = m_ObjLanguageHandler.LoadLanguage(m_StringLanguageName, m_StringModuleName);
		
		// Language file loading failure handling
		if (false == ((Boolean) error[0]).booleanValue()) {
			JOptionPane.showMessageDialog(null, error[1]);
			System.out.println("PSARequestController::Init() End");
			System.exit(0);
		}
		
		//Validation of User
		try {
			PSAAdminMSOutilsOpr.ValidateUser();
		} catch (PSAErrorHandler e) {
			//Failure of User Validation 
			displayMessage(e.m_StrErrorLabel);
			PSAAdminMSOutilsOpr.CloseDatabaseConnection();
			System.exit(0);
		} catch(Exception e){
			//Failure of User Validation 
			System.out.println("Error in JDBC connection.");
			PSAAdminMSOutilsOpr.CloseDatabaseConnection();
			System.exit(0);
		}

		// Creation and display of Main Panel. Created Panel will be added in
		// List_Panel list.
		PSARequestBasePanel panelCommand = new PSARequestSelectionPanel(this);
		List_Panel.add(panelCommand);
		DisplayMainPanel();
		System.out.println("PSARequestController::Init() End");
	}

	/**
	 * Sets the selected Action Type
	 * 
	 * @param ACTION
	 *            iActionType Selected Action
	 */
	public void setActionType(ACTION iActionType) {
		m_ActionType = iActionType;
	}

	/**
	 * Provides the selected Action Type
	 * 
	 * @return Provides Action Type
	 */
	public ACTION GetActionType() {
		return m_ActionType;
	}

	/**
	 * Set selected Opr Index related to m_Oprlist.
	 * @param index 	Opr index
	 */
	public void setOprIndex(int index) {
		m_OprIndex = index;
	}

	/**
	 * Get Selected opr index  related to m_Oprlist
	 * @return 	Selected Opr index
	 */
	public int  getOprIndex() {
		return m_OprIndex;
	}
	
	/**
	 * Displays Main Panel. Destroys earlier created panels from List_Panel
	 * list.
	 */
	public void DisplayMainPanel() {
		try {
			int iPanelCount = List_Panel.size();
			for (int i = 1; i < iPanelCount; i++){
				List_Panel.get(i).ShowWindow(false);
				List_Panel.get(i).disposePanel();
			}

			PSARequestBasePanel panel = List_Panel.get(0);
			List_Panel.clear();
			List_Panel.add(panel);
			panel.ShowWindow(true);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in DisplayMainPanel");
		}
	}

	/**
	 * Action callback of the Request List Panel.
	 * 
	 * @param evt
	 *            Action Event
	 */
	public void OnActionCallback(ActionEvent evt) {
		List_Panel.get(0).ShowWindow(false);
		DisplayPanel();
	}

	/**
	 * Class to process opr operations in background after displaying wait panel.
	 */
	public class ProcessThread extends Thread
	{
		int m_Action;			//0 - Query List
								//1 - Create Request
								//2 - Update Request
								//3 - Fill Object & Validate PSN
								//4 - Validate PSN
								//5 - Fill Object, Estimate Data
		/**
		 * Constructor of Class
		 */
		public ProcessThread(int Action)
		{
			m_Action = Action;
		}
		
		/**
		 * Will statrt thread.
		 */
		public void run()
	    {
			m_ErrorStatus = false;
			try
			{
				wait_panel_obj.ShowCursor(true);
				switch(m_Action)
				{
					case 0:
						m_oprList = PSAAdminMSOutilsOpr.QueryListOfActiveREQ();
						break;
					case 1:
						m_CurrentOpr.CreateRequest();
						break;
					case 2:
						m_CurrentOpr.ModifyRequest(m_CloneOpr);
						break;
					case 3:
						m_CurrentOpr.FillObject(m_ActionType);
						m_CloneOpr = new PSAAdminMSOutilsOpr();
						m_CurrentOpr.Clone(m_CloneOpr);
						if(m_StrPSNFileName.length() > 0)
						{
							m_CurrentOpr.ValidatePSN(m_ActionType, m_StrPSNFileName); 
						}
						break;
					case 4:
						if(m_StrPSNFileName.length() > 0)
						{
							m_CurrentOpr.ValidatePSN(m_ActionType, m_StrPSNFileName); 
						}
						break;
					case 5:
						m_CurrentOpr.FillObject(m_ActionType);
						m_CurrentOpr.estimateRequest();
						break;
						
					default:
						break;
				}

				wait_panel_obj.ShowCursor(false);
				wait_panel_obj.Dispose();
				m_ErrorStatus = false;
			}catch (PSAErrorHandler e)
			{
				//Dispose wait panel
				wait_panel_obj.ShowCursor(false);
				
				m_ErrorStatus = true;
				//Failure of Request List creation. 
				displayMessage(e.m_StrErrorLabel);
				
				wait_panel_obj.Dispose();
			}
	    }
	}
	
	/**
	 * Displays corresponding panel as per action selection.
	 * 
	 */
	public void DisplayPanel() {
		switch (m_ActionType) {
		case CREATEREQUEST:
			createAddModifyRequestPanel();
			break;
			
		case UPDATEREQUEST:
		case DELETEREQUEST:
		case VIEWREQUEST:
		case ESTIMATEDATA:
			if(null == m_oprList)
			{
				wait_panel_obj = new PSAWaitPanel(m_ObjLanguageHandler.Getlabel("Title.InformationMsg","Information"),
						m_ObjLanguageHandler.Getlabel("Message.WaitInformationMsg","Initialization is in progress, please wait..."));

				//Request List creation
				ProcessThread waitThread =  new ProcessThread(0);
				waitThread.start();

				wait_panel_obj.DisplayPanel();
				if(m_ErrorStatus == true)
				{
					List_Panel.get(0).ShowWindow(true);
					return;
				}
			}

			if(m_oprList!= null && m_oprList.size() > 0)
			{
				try
				{
					//Thread.sleep(1000);
					PSAListeRequest ListeRequestPanel = new PSAListeRequest(this);
					List_Panel.add(ListeRequestPanel);
					ListeRequestPanel.ShowWindow(true);
				}
				catch(Exception e)
				{
					System.out.println("PSA Wait Panel Exception : +e");
				}
			}
			break;
		case BLOCKREQUEST:
			try {
				m_CurrentOpr = new PSAAdminMSOutilsOpr();
				PSABlockUnblockPanel BlockUnblockPanel = new PSABlockUnblockPanel(this);
				if(BlockUnblockPanel.InsertRequest())
				{
					List_Panel.add(BlockUnblockPanel);
					BlockUnblockPanel.ShowWindow(true);
				}else
				{
					displayMessage("Error.ActiveInActiveRequestNotAvailable"); 
					List_Panel.get(0).ShowWindow(true);
					
				}
			} catch (PSAErrorHandler e) {
				displayMessage(e.m_StrErrorLabel);
				List_Panel.get(0).ShowWindow(true);
			}
			break;
		case CONSULTATIONLOG:
			System.out.println("Controller : Consulation Log");
			try {
				m_CurrentOpr = new PSAAdminMSOutilsOpr();
				String[] requestNameList  = m_CurrentOpr.getLogRequestList();
				if(requestNameList.length > 0)
				{
					PSAConsultationLogPanel consultationLogPanel;
					consultationLogPanel = new PSAConsultationLogPanel(this, requestNameList);
					List_Panel.add(consultationLogPanel);
					consultationLogPanel.ShowWindow(true);
				}else
				{
					displayMessage("Error.NoLOgData"); 
					List_Panel.get(0).ShowWindow(true);
					
				}
			} catch (PSAErrorHandler e) {
				displayMessage(e.m_StrErrorLabel);
				List_Panel.get(0).ShowWindow(true);
			}
			break;
		case EXIT:
			exitApplication();
			break;
		default:
			System.out.println("Unable to find UI for selected operation");
			List_Panel.get(0).ShowWindow(true);
			break;
		}
	}

	/**
	 * It used to exit from application.
	 */
	public void exitApplication() {
		//Dispose all panels.
		int panel_count = List_Panel.size();
		for (int i = 1; i < panel_count; i++) {
			List_Panel.get(i).ShowWindow(false);
			List_Panel.get(i).disposePanel();
		}
		PSAAdminMSOutilsOpr.CloseDatabaseConnection();		
		System.exit(0);
	}
	
  /**
   * It used to display the main panel.
   */
	public void CancelActionCallback() {
		DisplayMainPanel();
	}

	/**
	 * It is used to hide panel.
	 * @param panel :  PANEL , Panel type
	 */
	public void HidePanel(PANEL panel) {
		System.out.println("HidePanel() Start");
		try {
			int AddModify_panel_index = 0;
			if (m_ActionType == ACTION.UPDATEREQUEST)
				AddModify_panel_index = 2;
			else if (m_ActionType == ACTION.CREATEREQUEST)
				AddModify_panel_index = 1;

			switch (panel) {
			case CREATION_PANEL:
				List_Panel.get(AddModify_panel_index).ShowWindow(false);
				break;
			case REQUESTLISTE_PANEL:
			case BLOCKUNBLOCK_PANEL:
			case CONSULTATIONLOG_PANEL :
				List_Panel.get(1).ShowWindow(false);
				break;
			case SITESELECTION_PANEL:
				List_Panel.get(AddModify_panel_index + 1).ShowWindow(false);
				break;
			case SUMMARY_PANEL:
				List_Panel.get(AddModify_panel_index + 2).ShowWindow(false);
				break;
			case ESTIMATEDATA_PANEL:
				if((List_Panel.size()-1) == (AddModify_panel_index + 2))
				{
					List_Panel.get(AddModify_panel_index + 2).ShowWindow(false);
					List_Panel.remove(AddModify_panel_index + 2);
				}
				break;
			
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in HidePanel" + panel + "ShowWindow");
		}
		System.out.println("HidePanel() End");
	}

	/**
	 * It used to show specified panel.
	 * @param panel PANEL : Type of panel.
	 */
	public void ShowPanel(PANEL panel) {
		System.out.println("PSARequestController::ShowPanel");
		try {
			int AddModify_panel_index = 0;
			if (m_ActionType == ACTION.UPDATEREQUEST)
				AddModify_panel_index = 2;
			else if (m_ActionType == ACTION.CREATEREQUEST)
				AddModify_panel_index = 1;

			switch (panel) {
				case MAIN_PANEL:
					List_Panel.get(0).ShowWindow(true);
					break;
				case CREATION_PANEL:
					if (AddModify_panel_index >= List_Panel.size()) {
						createAddModifyRequestPanel();
					} else {
						List_Panel.get(AddModify_panel_index).ShowWindow(true);
					}
					break;
				case REQUESTLISTE_PANEL:
				case BLOCKUNBLOCK_PANEL:
				case CONSULTATIONLOG_PANEL:
					List_Panel.get(1).ShowWindow(true);
					break;
				case SITESELECTION_PANEL:
					if (AddModify_panel_index + 1 >= List_Panel.size()) {
						try {
							PSASiteTimeSlotSelection SiteSelectionPanel = new PSASiteTimeSlotSelection(this);
							List_Panel.add(SiteSelectionPanel);
							SiteSelectionPanel.ShowWindow(true);
						} catch(PSAErrorHandler e)
						{
							displayMessage(e.m_StrErrorLabel);
							ShowPanel(PANEL.CREATION_PANEL);
						}
					} else {
						List_Panel.get(AddModify_panel_index + 1).ShowWindow(true);
					}
					break;
				case ESTIMATEDATA_PANEL:
					System.out.println("Create panel Estimate Data Panel");
					try {
						PSAEstimateDataPanel EstimateDataPanel = new PSAEstimateDataPanel(
								this);
						List_Panel.add(EstimateDataPanel);
						EstimateDataPanel.ShowWindow(true);
					} catch(PSAErrorHandler e)
					{
						displayMessage(e.m_StrErrorLabel);
						ShowPanel(PSARequestSelectionPanel.PANEL.REQUESTLISTE_PANEL);
					}
					break;
	
				case SUMMARY_PANEL:
					if (AddModify_panel_index + 2 < List_Panel.size()) {
						List_Panel.remove(AddModify_panel_index + 2);
					} 
					System.out.println("Create panel SummaryPanel");
					PSASummaryPanel SummaryPanel = new PSASummaryPanel(this);
					List_Panel.add(SummaryPanel);
					SummaryPanel.ShowWindow(true);
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in ShowPanel" + panel + "ShowWindow");
		}
	}

	/**
	 * It used to handle event of action button of list panel.
	 * @param evt : ActionEvent
	 * @return boolean
	 */
	public boolean ListRequestActionCallback(ActionEvent evt) {
		System.out.println("ListRequestActionCallback() Start");
		boolean bstatus = false;

		switch (GetActionType()) {
		case UPDATEREQUEST:
			// Destroy earlier created panels except Main Panel and Request List Panel.
			if (List_Panel.size() > 2) {
				for (int i = 2; i < List_Panel.size(); i++) {
					List_Panel.get(i).ShowWindow(false);
					List_Panel.get(i).disposePanel();
				}
				PSARequestBasePanel panel1 = List_Panel.get(0);
				PSARequestBasePanel panel2 = List_Panel.get(1);
				List_Panel.clear();
				List_Panel.add(panel1);
				List_Panel.add(panel2);
			}

			bstatus = true;
			HidePanel(PSARequestBasePanel.PANEL.REQUESTLISTE_PANEL);
			ShowPanel(PSARequestBasePanel.PANEL.CREATION_PANEL);
			break;
		case DELETEREQUEST:
			wait_panel_obj = new PSAWaitPanel(m_ObjLanguageHandler.Getlabel("Title.InformationMsg","Information"),
					m_ObjLanguageHandler.Getlabel("Message.WaitInformationMsg","Initialization is in progress, please wait..."));

			//Request List creation
			ProcessThread waitThread =  new ProcessThread(0);
			waitThread.start();

			wait_panel_obj.DisplayPanel();
			break;
		case VIEWREQUEST:
			wait_panel_obj = new PSAWaitPanel(m_ObjLanguageHandler.Getlabel("Title.InformationMsg","Information"),
					m_ObjLanguageHandler.Getlabel("Message.WaitInformationMsg","Initialization is in progress, please wait..."));

			m_StrPSNFileName = "";
			//Fill Object
			waitThread =  new ProcessThread(3);
			waitThread.start();

			wait_panel_obj.DisplayPanel();

			if(m_ErrorStatus == false)
			{
				bstatus = true;
				HidePanel(PSARequestSelectionPanel.PANEL.REQUESTLISTE_PANEL);
				ShowPanel(PSARequestSelectionPanel.PANEL.SUMMARY_PANEL);
			}
			break;
		case ESTIMATEDATA:
			wait_panel_obj = new PSAWaitPanel(m_ObjLanguageHandler.Getlabel("Title.InformationMsg","Information"),
					m_ObjLanguageHandler.Getlabel("Message.WaitInformationMsg","Initialization is in progress, please wait..."));

			m_StrPSNFileName = "";
			//Fill Object
			waitThread =  new ProcessThread(5);
			waitThread.start();

			wait_panel_obj.DisplayPanel();

			if(m_ErrorStatus == false)
			{
				HidePanel(PSARequestSelectionPanel.PANEL.REQUESTLISTE_PANEL);
				ShowPanel(PSARequestSelectionPanel.PANEL.ESTIMATEDATA_PANEL);
			}
			break;
		}
		System.out.println("ListRequestActionCallback() End");
		return bstatus;
	}
 
	/**
	 *  It used to handle valider event.
	 * @return boolean
	 */
	public boolean ValiderCallback() {
		System.out.println("PSARequestController::ValiderCallback");
	
		boolean bstatus = true;
		switch(m_ActionType)
		{
			case CREATEREQUEST:
				wait_panel_obj = new PSAWaitPanel(m_ObjLanguageHandler.Getlabel("Title.InformationMsg","Information"),
						m_ObjLanguageHandler.Getlabel("Message.CreateWaitInformationMsg","Request Creation is in progress, please wait..."));

				//Request List creation
				ProcessThread waitThread =  new ProcessThread(1);
				waitThread.start();

				wait_panel_obj.DisplayPanel();
				
				if(m_ErrorStatus == false && null != m_oprList && (m_CurrentOpr.getRequestMod().compareTo("ONESHOT") != 0)){
					m_oprList.add(m_CurrentOpr);
				}
				break;
			case UPDATEREQUEST:
				wait_panel_obj = new PSAWaitPanel(m_ObjLanguageHandler.Getlabel("Title.InformationMsg","Information"),
						m_ObjLanguageHandler.Getlabel("Message.UpdateWaitInformationMsg","Request Updation is in progress, please wait..."));

				//Request List creation
				waitThread =  new ProcessThread(2);
				waitThread.start();

				wait_panel_obj.DisplayPanel();
				
				//Unlock the request.
				try
				{
					m_CurrentOpr.LockRequestForManip("INERT");
				}catch(PSAErrorHandler e)
				{
					displayMessage(e.m_StrErrorLabel);
				}
				m_CloneOpr =  null;
				break;
		}
		return bstatus;
	}
	
	/**
	 * It used to get selected PSN file.
	 * @return
	 */
	private File getSelectedFile()
	{
		File file = null;
		fileChooser.setFileFilter(filter);
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You choose to open this file: " + fileChooser.getSelectedFile().getName());
			file =  fileChooser.getSelectedFile();
			
			String file_name = file.getName();
			System.out.println("Selected File Name = "+file_name);
	
			 boolean flag= false;
			//file path contains  extension or not.
			if(true == file_name.contains(".")) 
			{
				if(file_name.contains(".psn"))
				{
					flag = true;
				}
			
			}else
			{
				file_name = file.getPath() + ".psn";
				System.out.println("Without extension Selected File = "+file_name);
				file = new File(file_name);
				flag = true;
			}
						
			if(flag == false || (flag && !file.exists()))
			{
				file = getSelectedFile();
			}
			
		}
		return file;
	}
	
	/**
	 * It used to create and modify request.
	 */
	private void createAddModifyRequestPanel() {
		
		File selectedFile =  null;
		wait_panel_obj = new PSAWaitPanel(m_ObjLanguageHandler.Getlabel("Title.InformationMsg","Information"),
				m_ObjLanguageHandler.Getlabel("Message.WaitInformationMsg","Initialization is in progress, please wait..."));
		m_StrPSNFileName = "";
		boolean displayFlag =false;
		switch(m_ActionType)
		{
		case CREATEREQUEST:
			m_CurrentOpr = new PSAAdminMSOutilsOpr();
			selectedFile = getSelectedFile();
			if(selectedFile != null)
			{
				m_StrPSNFileName = selectedFile.getPath();
				//Fill Object
				ProcessThread waitThread =  new ProcessThread(4);
				waitThread.start();

				wait_panel_obj.DisplayPanel();

				//Reset PSN FIle Name, used in Thread. 
				m_StrPSNFileName = "";

				if(m_ErrorStatus == true)
				{
					DisplayMainPanel();
					return;
				}
				displayFlag = true;
			}else
			{
				DisplayMainPanel();
				return;
			}
			break;
		case UPDATEREQUEST:
			String msg = "Do you want to modify request using new PSN file ?";
			msg = m_ObjLanguageHandler.Getlabel("Message.PSNFIleSelectionConfirmation",msg);
			String title = "Confirmation";
			title = m_ObjLanguageHandler.Getlabel("Title.ConfirmationMsg",title);
			PSAMessageDialog.OPTION opt = new PSAMessageDialog().showMessageDialog(null, msg, title, PSAMessageDialog.BUTTON_OPTION.YES_NO_OPTION,m_ObjLanguageHandler);
			if(opt == PSAMessageDialog.OPTION.YES_OPTION){
				selectedFile = getSelectedFile();
			} else if(opt == PSAMessageDialog.OPTION.NO_OPTION){
			} 

			if(selectedFile != null)
			{
				m_StrPSNFileName = selectedFile.getPath();
			} 
			//Fill Object
			ProcessThread waitThread =  new ProcessThread(3);
			waitThread.start();
			wait_panel_obj.DisplayPanel();

			//Reset PSN FIle Name, used in Thread. 
			m_StrPSNFileName = "";
			
			if(m_ErrorStatus == true)
			{
				//Unlock the request.
				try
				{
					m_CurrentOpr.LockRequestForManip("INERT");
				}catch(PSAErrorHandler e)
				{
					displayMessage(e.m_StrErrorLabel);
				}

				//As unable to read Opr information displaying List panel again.
				ShowPanel(PSARequestBasePanel.PANEL.REQUESTLISTE_PANEL);
				return;
			}
			
			displayFlag = true;
			break;
		}
		
		if(displayFlag)
		{
			PSAAddModifyRequestPanel AddModifPanel = new PSAAddModifyRequestPanel(this);
			List_Panel.add(AddModifPanel);
			if(selectedFile != null)
			{
				AddModifPanel.setPSNFileName(selectedFile.getName());
				m_CurrentOpr.setPSNName(selectedFile.getName());
			}else
			{
				AddModifPanel.setPSNFileName("---");
				m_CurrentOpr.setPSNName("");
			}
			AddModifPanel.ShowWindow(true);
		}
		
	}

	/**
	 * It used to display the message on message dialog.
	 * @param ierror_msg_label : String 
	 */
	public void displayMessage(String ierror_msg_label)
	{
		if(null != ierror_msg_label)
		{
			String error_msg = "";
			error_msg = m_ObjLanguageHandler.Getlabel(ierror_msg_label, error_msg);
			System.out.println("Error : "+error_msg);
			@SuppressWarnings("unused")
			PSAMessageDialog.OPTION opt = new PSAMessageDialog().showMessageDialog(null, error_msg,m_ObjLanguageHandler);
		}
	}
}
