/**********************************************************************************************************
 *
 * FILE NAME	  : PSAEstimateDataPanel.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class used to display estimated data.
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
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

//User class import
import com.psa.tools.PSAErrorHandler;
import com.psa.tools.PSATableDataModel;

/**
 * 
 *Class used to display estimated data.
 */
public class PSAEstimateDataPanel extends PSARequestBasePanel{

	private JFrame EstimateDataPanel;
	
	private JLabel m_LabelReplicationPart;
	private JPanel m_PanelReplicationParts;
	private JLabel m_LabelTotalScriptionParts;
	private JPanel m_PanelSubscription;
	private JPanel m_PanelButtons;
	private JButton  m_ButtonBack;
	private JTable m_TableSubscriptionList;
	private JScrollPane m_ScrollPaneSubscription;
	private PSATableDataModel replicationDataModel;
	private JTable m_TableReplicationList;
	private JScrollPane m_ScrollPaneReplication;
	private PSATableDataModel subscriptionDataModel;
	private PSARequestDetails requestDetails;
	
	/**
	 * Constructor of the class.
	 * @param iController : PSARequestController
	 */
	public PSAEstimateDataPanel(PSARequestController iController) throws PSAErrorHandler 
	{
		super(iController);
		initGUI();
	}
	
	/**
	 * It  creates and initialize components. 
	 */
	private void initGUI()  throws PSAErrorHandler 
	{
		EstimateDataPanel = new JFrame();
		GridBagLayout EstimateDataPanelLayout = new GridBagLayout();
		EstimateDataPanelLayout.rowWeights = new double[] {0.0, 0.1, 0.0, 0.1, 0.0, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.0};
		EstimateDataPanelLayout.rowHeights = new int[] {30, 30, 26, 30, 28, 30, 30, 30, 30, 30, 30, 35};
		EstimateDataPanelLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
		EstimateDataPanelLayout.columnWidths = new int[] {7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7};
		EstimateDataPanel.getContentPane().setLayout(EstimateDataPanelLayout);
		EstimateDataPanel.setTitle("Estimation of Request");
		EstimateDataPanel.setTitle(m_Controller.m_ObjLanguageHandler.Getlabel("Title.EstimateRequestPanel","Estimation of Request"));
		EstimateDataPanel.setSize(616, 588);
		EstimateDataPanel.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		EstimateDataPanel.setPreferredSize(new java.awt.Dimension(616, 588));

		//Request details panel.
		requestDetails = new PSARequestDetails(m_Controller);
		EstimateDataPanel.getContentPane().add(requestDetails, new GridBagConstraints(0, 0, 12, 3, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		
		createButtonPanel();
		createSubScriptionPartPanel();
		createReplicationPartPanel();
		insertData();
	}
	
	/**
	 * It creates button panel.
	 */
	private void createButtonPanel() {
		//Button panel.
		m_PanelButtons = new JPanel();
		GridBagLayout jPanelButtonsLayout = new GridBagLayout();
		EstimateDataPanel.getContentPane().add(m_PanelButtons, new GridBagConstraints(0, 11, 12, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		jPanelButtonsLayout.rowWeights = new double[] {0.1};
		jPanelButtonsLayout.rowHeights = new int[] {7};
		jPanelButtonsLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
		jPanelButtonsLayout.columnWidths = new int[] {7, 7, 7, 7};
		m_PanelButtons.setLayout(jPanelButtonsLayout);
		
		//Back button
		m_ButtonBack = new JButton();
		m_PanelButtons.add(m_ButtonBack, new GridBagConstraints(0, 0, 4, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		//jButtonBack.setText("Back");
		//jButtonBack.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.BackButton","Back"));
		m_ButtonBack.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/resultset_previous.png")));
		m_ButtonBack.setToolTipText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.BackButton","Back"));
		m_ButtonBack.setSize(50, 21);
		m_ButtonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonBackActionPerformed(evt);
			}
		});
		
	}
	
	/**
	 * It creates subscription panel.
	 */
	private void createSubScriptionPartPanel() {
		
		//Subscription panel.
		m_PanelSubscription = new JPanel();
		BorderLayout jPanelSubscriptionLayout = new BorderLayout();
		m_PanelSubscription.setLayout(jPanelSubscriptionLayout);
		EstimateDataPanel.getContentPane().add(m_PanelSubscription, new GridBagConstraints(0, 3, 12, 4, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		m_PanelSubscription.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
		
		//Total Subscription Label.
		m_LabelTotalScriptionParts = new JLabel();
		m_PanelSubscription.add(m_LabelTotalScriptionParts, BorderLayout.NORTH);
		m_LabelTotalScriptionParts.setText("Total parts for Subscription");
		m_LabelTotalScriptionParts.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.TotalSubscriptionParts","Total parts for Subscription"));
		m_LabelTotalScriptionParts.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Subscription Table Header.
		 String[] Headers = {
				    m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderBlank", "") ,
					m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderParts", "Parts") ,
					m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderModel", "Model"),
					m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderDocument", "Document")
					};

		//Subscription Table.
		subscriptionDataModel = new PSATableDataModel(1,Headers);
		m_TableSubscriptionList = new JTable(subscriptionDataModel);
		m_TableSubscriptionList.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m_TableSubscriptionList.setColumnSelectionAllowed(false);
		m_TableSubscriptionList.getTableHeader().setReorderingAllowed(false);
		m_ScrollPaneSubscription = new JScrollPane(m_TableSubscriptionList);
		m_PanelSubscription.add(m_ScrollPaneSubscription, BorderLayout.CENTER);
		m_ScrollPaneSubscription.setViewportView(m_TableSubscriptionList);
		
		//To render column of table.
		TableColumnModel columnModel =  m_TableSubscriptionList.getColumnModel();
		columnModel.getColumn(0).setCellRenderer( new ColorCellRenderer(Color.LIGHT_GRAY , Color.BLACK));
	}
	
	/**
	 * Create replication part.
	 */
	private void createReplicationPartPanel() {
		
		//Replication  Parts panel.
		m_PanelReplicationParts = new JPanel();
		BorderLayout jPanelReplicationLayout = new BorderLayout();
		m_PanelReplicationParts.setLayout(jPanelReplicationLayout);
		EstimateDataPanel.getContentPane().add(m_PanelReplicationParts, new GridBagConstraints(0, 7, 12, 4, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		m_PanelReplicationParts.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
		
		//Total Replication Parts Label.
		m_LabelReplicationPart = new JLabel();
		m_PanelReplicationParts.add(m_LabelReplicationPart, BorderLayout.NORTH);
		m_LabelReplicationPart.setText("Total parts for Replication");
		m_LabelReplicationPart.setText(m_Controller.m_ObjLanguageHandler.Getlabel("Label.TotalReplicationParts","Total Parts for Replication"));
		m_LabelReplicationPart.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Replication Parts  Table Header.
		 String[] Headers = {
				    m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderBlank", "") ,
					m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderParts", "Parts") ,
					m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderModel", "Model"),
					m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderDocument", "Document"),
					m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderVolumne", "Volume (Mbs)"),
					m_Controller.m_ObjLanguageHandler.Getlabel("Label.TableHeaderMaxAllowed", "Max Allowed (Mbs)")
					};

		//Replication Parts  Table.
		replicationDataModel = new PSATableDataModel(1,Headers);
		m_TableReplicationList = new JTable(replicationDataModel);
		m_TableReplicationList.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m_TableReplicationList.setColumnSelectionAllowed(false);
		m_TableReplicationList.getTableHeader().setReorderingAllowed(false);
		m_ScrollPaneReplication = new JScrollPane(m_TableReplicationList);
		m_PanelReplicationParts.add(m_ScrollPaneReplication, BorderLayout.CENTER);
		m_ScrollPaneReplication.setViewportView(m_TableReplicationList);
		
		//To render column of table.
		TableColumnModel columnModel =  m_TableReplicationList.getColumnModel();
		columnModel.getColumn(0).setCellRenderer( new ColorCellRenderer(Color.LIGHT_GRAY , Color.BLACK));
	}

	/** 
	 * Hide or Show the Panel
	 * @param	status	- false to Hide the panel.
	 * 					- true to show the panel
	 */
	@Override
	public void ShowWindow(boolean bstatus)
	{
		System.out.println("PSAEstimateDataPanel::ShowWindow");
		//Initialize the value of fields.
		if(bstatus){
			requestDetails.initializeValues();
		}
		EstimateDataPanel.setLocationRelativeTo(null);
		EstimateDataPanel.setVisible(bstatus);
		System.out.println("PSAEstimateDataPanel::ShowWindow End");
	}

	private void jButtonBackActionPerformed(ActionEvent evt) {
		m_Controller.HidePanel(PANEL.ESTIMATEDATA_PANEL);
		m_Controller.ShowPanel(PANEL.REQUESTLISTE_PANEL);
	}
	
	/**
	 * Insertion of Subscription and Replication data in table.
	 */
	private int insertData() throws PSAErrorHandler
	{
		System.out.println("PSAEstimateDataPanel::insertData");
		m_TableSubscriptionList.removeAll();
		ArrayList<EstimationData> listestimate = null;
		//Retrieve Estimate Data.
		listestimate = m_Controller.m_CurrentOpr.getEstimateData();
		
		int Req_count = listestimate.size();
		subscriptionDataModel.SetRowCount(Req_count);
		replicationDataModel.SetRowCount(Req_count);
		
		//Add Estimation Data to tables.
		for(int i=0;i<Req_count;i++)
		{
			//Fill the subscription data.
			Object[] subscription_data = 
			{
					listestimate.get(i).strSite,
					listestimate.get(i).nTotPart,
					(listestimate.get(i).nTotV4 + listestimate.get(i).nTotV5),
					listestimate.get(i).nTotDoc,
					Integer.toString(i)
			};
			
			for(int j=0;j<subscription_data.length;j++)
			{
				subscriptionDataModel.setValueAt(subscription_data[j],i,j);
			}
			
			String str = String.format("%.3f", (listestimate.get(i).dVolume / 1024));
			
			//Fill the replication data.
			Object[] replication_data = 
			{
					listestimate.get(i).strSite,
					listestimate.get(i).nTotPart,
					(listestimate.get(i).nTotV4 + listestimate.get(i).nTotV5),
					listestimate.get(i).nTotDoc,
					str,
					listestimate.get(i).maxAllowedSize,
					Integer.toString(i)
			};
			
			for(int j=0;j<replication_data.length;j++)
			{
				replicationDataModel.setValueAt(replication_data[j],i,j);
			}
		}
		System.out.println("PSAEstimateDataPanel::insertData End");
		return Req_count;
	}
}
/**
 * To change table cell color.
 * 
 */
class ColorCellRenderer extends DefaultTableCellRenderer{
	
	private static final long serialVersionUID = 1L;
		Color backColor;
		Color foreColor;
		ColorCellRenderer(Color backcolor,Color forecolor)
		{
			super();
			backColor = backcolor;
			foreColor =forecolor;
		}
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
														boolean hasFocus, int row, int column) 
		{
		  Component cell = super.getTableCellRendererComponent
		     (table, value, isSelected, hasFocus, row, column);
		
		  cell.setBackground(backColor);
		  cell.setForeground(foreColor );
		 
		  return cell;
		}
}