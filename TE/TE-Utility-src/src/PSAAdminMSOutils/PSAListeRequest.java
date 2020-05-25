/**********************************************************************************************************
 *
 * FILE NAME	  : PSAListeRequest.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class used to display request list for update, delete, view or estimate request.
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

//User class import
import com.psa.tools.PSAErrorHandler;
import com.psa.tools.PSAMessageDialog;
import com.psa.tools.PSATableDataModel;

/**
 * Class used to display request list for update, delete, view or estimate request
 *
 */
public class PSAListeRequest extends PSARequestBasePanel{
	private JFrame ListeRequestPanel;
	private JButton m_ButtonBack;
	private JButton m_ButtonAction;
	private JLabel m_LabelListRequest;
	private JTextField m_TextFieldFilterRequest;
	private JLabel m_LabelFilterRequest;
	private JTextField m_TextFieldUserID;
	private JTable m_TableReqList;
	private JScrollPane TablescrollPane;
	private JLabel m_LabelUserID;
	private JPanel m_PanelFilter;
	private PSATableDataModel DataModel;

	/**
	 * Constructor of the class.
	 * @param iController : PSARequestController
	 */
	public PSAListeRequest(PSARequestController iController)
	{
		super(iController);
		m_Controller = iController;
		initGUI();
	}
	
	/**
	 * It  creates and initialize components. 
	 */
	private void initGUI() {
		try
		{
			//List request panel.
			ListeRequestPanel =  new JFrame();
			GridBagLayout ListeRequestPanelLayout = new GridBagLayout();
			ListeRequestPanel.setResizable(false);
			ListeRequestPanelLayout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.1};
			ListeRequestPanelLayout.rowHeights = new int[] {30, 80, 80, 90, 110, 7};
			ListeRequestPanelLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			ListeRequestPanelLayout.columnWidths = new int[] {7, 7, 7, 7};
			ListeRequestPanel.setTitle("List of Availbale Requests");
			ListeRequestPanel.setTitle(m_Controller.m_ObjLanguageHandler.Getlabel("Title.ListRequestPanel","List of Availbale Requests"));
			ListeRequestPanel.getContentPane().setLayout(ListeRequestPanelLayout);
			ListeRequestPanel.setPreferredSize(new java.awt.Dimension(500, 472));
			ListeRequestPanel.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			
			//Request Label
			m_LabelListRequest = new JLabel();
			ListeRequestPanel.getContentPane().add(m_LabelListRequest, new GridBagConstraints(0, 0, 4, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
			m_LabelListRequest.setText("List of ACTIVE Request / PSN");
			m_LabelListRequest.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.ListActiveRequest","List of ACTIVE Request / PSN"));
			m_LabelListRequest.setHorizontalAlignment(SwingConstants.CENTER);
		
			createRequestListTable();
			createButton();
			createFilterRequest();
			actionButtonEnable();
			
			ListeRequestPanel.setSize(500, 472);
		}catch(Exception e){
			
		}
	}
	
	/**
	 * It enable or disable button.
	 */
	private void actionButtonEnable() {
		int index = m_TableReqList.getSelectedRow();
		m_ButtonAction.setEnabled((-1 == index) ? false : true);
	}

	/**
	 * It creates request list table.
	 */
	private void createRequestListTable() {
			
		System.out.println("createRequestListTable start");
		
		//Request list table header.
		String[] Headers = {m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderRequestName", "Request Name"),
							m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderUserID", "User ID")};

		//Request list table.
		DataModel = new PSATableDataModel(2,Headers);
		m_TableReqList = new JTable(DataModel);
		
		if(m_Controller.GetActionType() == ACTION.DELETEREQUEST)
		{
			System.out.println("Multiple selection.");
			m_TableReqList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		} else 
		{
			m_TableReqList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		m_TableReqList.setColumnSelectionAllowed(false);
		m_TableReqList.getTableHeader().setReorderingAllowed(false);
		TablescrollPane = new JScrollPane(m_TableReqList);
		ListeRequestPanel.add(TablescrollPane, new GridBagConstraints(0, 1, 4, 3, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 20, 5, 20), 0, 0));
	    TablescrollPane.setViewportView(m_TableReqList);
    	int Req_Count = InsertRequestData();
    	m_TableReqList.setEnabled((Req_Count > 0)? true : false);
    	
    	m_TableReqList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				int clickCount = mouseEvent.getClickCount();
				if(clickCount == 1)
				{
					actionButtonEnable();
				}
				else if(clickCount == 2)
				{
					selectRequestOperation(null);
				}
			}
		});
		
	}

	/**
	 * It creates buttons.
	 */
	private void createButton() {
		//Back button
		m_ButtonBack = new JButton();
		ListeRequestPanel.getContentPane().add(m_ButtonBack, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		//jButtonBack.setText("Back");
		//jButtonBack.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.BackButton","Back"));
		m_ButtonBack.setToolTipText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.BackButton","Back"));
		m_ButtonBack.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/resultset_previous.png")));
		m_ButtonBack.setSize(50, 21);
		m_ButtonBack.setPreferredSize(new java.awt.Dimension(50, 21));
		m_ButtonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				m_Controller.CancelActionCallback();
			}
		});
		
		//Action button
		m_ButtonAction = new JButton();
		m_ButtonAction.setText("Action");
		m_ButtonAction.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.ActionButton","Action"));
		ListeRequestPanel.getContentPane().add(m_ButtonAction, new GridBagConstraints(2, 5, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		m_ButtonAction.setSize(50, 21);
		m_ButtonAction.setPreferredSize(new java.awt.Dimension(50, 21));
		m_ButtonAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				selectRequestOperation(evt);
			}
		});
		
		//Set text on action button.
		switch(m_Controller.GetActionType())
		{
			case CREATEREQUEST:
				m_ButtonAction.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.CreateButton","Create"));
				break;
			case UPDATEREQUEST:
				m_ButtonAction.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.ModifyButton","Modify"));
				break;
			case DELETEREQUEST:
				m_ButtonAction.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.DeleteButton","Delete"));
				break;
			case VIEWREQUEST:
				m_ButtonAction.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.ViewButton","View"));
				break;
			case ESTIMATEDATA:
				m_ButtonAction.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.EstimateButton","Estimate"));
				break;
			default:
				m_ButtonAction.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.ActionButton","Action"));
				break;
		}
	}

	/**
	 * It creates Filter request.
	 */
	private void createFilterRequest() {
		
		    //Filter Panel
			m_PanelFilter = new JPanel();
			ListeRequestPanel.getContentPane().add(m_PanelFilter, new GridBagConstraints(0, 4, 4, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 20, 5, 20), 0, 0));
			GridBagLayout filterPanelLayout = new GridBagLayout();
			ListeRequestPanel.setResizable(false);
			filterPanelLayout.rowWeights = new double[] {0.0, 0.0, 0.1, 0.1};
			filterPanelLayout.rowHeights = new int[] {5, 35, 20, 5};
			filterPanelLayout.columnWeights = new double[] {0.0, 0.1, 0.0, 0.0, 0.1};
			filterPanelLayout.columnWidths = new int[] {108, 7, 37, 158, 7};
			m_PanelFilter.setLayout(filterPanelLayout);
			m_PanelFilter.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
			m_PanelFilter.setSize(480, 30);
			
			//Request Filter Label
			m_LabelFilterRequest = new JLabel();
			m_PanelFilter.add(m_LabelFilterRequest, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
			m_LabelFilterRequest.setText("Filter Request");
			m_LabelFilterRequest.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.FilterRequest","Filter Request"));
			m_LabelFilterRequest.setHorizontalTextPosition(SwingConstants.RIGHT);
			m_LabelFilterRequest.setHorizontalAlignment(SwingConstants.RIGHT);
			
			//Request Filter Textfiled
			m_TextFieldFilterRequest = new JTextField();
			m_PanelFilter.add(m_TextFieldFilterRequest, new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
			m_TextFieldFilterRequest.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					filterRequestList();
				}
			});
		
			//User Filter Label
			m_LabelUserID = new JLabel();
			m_PanelFilter.add(m_LabelUserID, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
			m_LabelUserID.setText("User ID");
			m_LabelUserID.setHorizontalAlignment(SwingConstants.RIGHT);
			m_LabelUserID.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.UserID","User ID"));
			
			////User Filter Text Field.
			m_TextFieldUserID = new JTextField();
			m_PanelFilter.add(m_TextFieldUserID, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
			m_TextFieldUserID.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					filterRequestList();
				}
			});
		
	}
	/**
	 * It used to take action (delete , modify , view or estimate )on selected request.
	 * @param evt : ActionEvent
	 */
	private void selectRequestOperation(ActionEvent evt) {
		
		//Get selected index.
		int index = m_TableReqList.getSelectedRow();
		if(-1 == index)
		{
			m_Controller.displayMessage("Error.SelectItemFromRequestList");
		}
		else
		{
			
			if(m_Controller.GetActionType() == ACTION.DELETEREQUEST)
			{
				
				String msg = "Etes-vous sur de vouloir supprimer définitivement la requests ?";
				msg = m_Controller.m_ObjLanguageHandler.Getlabel("Message.DeleteConfirmation",msg);
				String title = "Confirmation";
				title = m_Controller.m_ObjLanguageHandler.Getlabel("Title.ConfirmationMsg",title);
				PSAMessageDialog.OPTION opt = new PSAMessageDialog().showMessageDialog(null, msg, title, PSAMessageDialog.BUTTON_OPTION.YES_NO_OPTION, m_Controller.m_ObjLanguageHandler);
				if(opt == PSAMessageDialog.OPTION.YES_OPTION){
					int selected_indexes[]  = m_TableReqList.getSelectedRows();
					
					//Delete the selected request.
					for(int i=selected_indexes.length-1; i>=0; i--)
					{
						m_Controller.m_CurrentOpr = m_Controller.m_oprList.get(selected_indexes[i]);
						try {
							m_Controller.m_CurrentOpr.ReadRequestDetails();
							if(m_Controller.m_CurrentOpr.getLockStatus().compareToIgnoreCase("INERT") == 0)
							{
								m_Controller.m_CurrentOpr.DeleteRequest();
								m_Controller.m_oprList.remove(selected_indexes[i]);
							} else 
							{
								System.out.println("Request ID locked.");
								throw new PSAErrorHandler(-1,"Error.RequestLocked");
							}
						}
						catch(PSAErrorHandler e)
						{
							m_Controller.displayMessage(e.m_StrErrorLabel);
						}
					}
					
					//Update Table
					DataModel.Clear();
					InsertRequestData();
						
					TablescrollPane.setViewportView(m_TableReqList);
				} else if(opt == PSAMessageDialog.OPTION.NO_OPTION){
				} 
			}
			else 
			{
				String StrReqName = DataModel.getValueAt(index, 0).toString();
				System.out.println("Selected Request name : "+ StrReqName);
				Object opr_index_str = DataModel.getValueAt(index, 2);
				
				int opr_index = Integer.parseInt(opr_index_str.toString());
				m_Controller.setOprIndex(opr_index);
				System.out.println("Opr Index : "+ opr_index);
				
				//Set selected opr to opr in used for all panels. 
				m_Controller.m_CurrentOpr = m_Controller.m_oprList.get(opr_index);
				
				System.out.println("Request Deatils : ID "+ m_Controller.m_CurrentOpr.m_StrReqID +
						" Request Name : "+ m_Controller.m_CurrentOpr.getRequestName());

				m_Controller.ListRequestActionCallback(evt);
			}
		}
		
	}
	
	/**
	 * It used to filter list.
	 */
	private void filterRequestList() {
		
		//Get request name and used id.
		String RequestText = m_TextFieldFilterRequest.getText().trim();
		RequestText = RequestText.toUpperCase();
		String UserIDText = m_TextFieldUserID.getText().trim();
		UserIDText = UserIDText.toUpperCase();
		
		//Filter request list.
		if(RequestText.length() > 0 && UserIDText.length() > 0)
		{
			DataModel.Filter(RequestText, 0, UserIDText, 1);
		} else if(RequestText.length() > 0) {
			DataModel.Filter(RequestText, 0);
		} else if(UserIDText.length() > 0) {
			DataModel.Filter(UserIDText, 1);
		} else 
		{
			DataModel.Filter("*", 0);
		}
		
		//Refresh table data.
		m_TableReqList.clearSelection();
		TablescrollPane.setViewportView(m_TableReqList);
		actionButtonEnable();
	}

	/** 
	 * Hide or Show the Panel
	 * @param	status	- false to Hide the panel.
	 * 					- true to show the panel
	 */
	@Override
	public void ShowWindow(boolean bstatus)
	{
		System.out.println("PSAListeRequest::ShowWindow");
		ListeRequestPanel.setLocationRelativeTo(null);
		ListeRequestPanel.setVisible(bstatus);
		System.out.println("PSAListeRequest::ShowWindow End");
	}
	
	/**
	 * It used to add request to table.
	 * @return int : Number of the request read.
	 */
	private int InsertRequestData()
	{
		System.out.println("PSAListeRequest::InsertRequestData");
		m_TableReqList.removeAll();
		int Req_count = 0;
		
		if(null != m_Controller.m_oprList)
		{
			//Fill the request data.
			Req_count = m_Controller.m_oprList.size();
			DataModel.SetRowCount(Req_count);
			for(int i=0;i<Req_count;i++)
			{
				Object[] Reqdata = 
				{
						m_Controller.m_oprList.get(i).getRequestName(),
						m_Controller.m_oprList.get(i).getReqUserID(),
						Integer.toString(i)
				};
				
				for(int j=0;j<Reqdata.length;j++)
				{
					DataModel.setValueAt(Reqdata[j],i,j);
				}
			}
		} 
		System.out.println("PSAListeRequest::InsertRequestData End");
		return Req_count;
	}

}
