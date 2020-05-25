/**********************************************************************************************************
 *
 * FILE NAME	  : PSARequestDetails.java
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
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

//User class import
import com.psa.tools.PSALanguageHandler;

/**
 *  Class used to display request details.
 *
 */
@SuppressWarnings("serial")
public class PSARequestDetails extends javax.swing.JPanel{
	private JLabel m_LabelDate;
	private JLabel m_LabelState;
	private JLabel m_LabelRequestName;
	private JLabel m_LabelType;
	private JTextField m_TextFieldType;
	private JTextField m_TextFieldNextLaunchDate;
	private JLabel m_LabelNextLaunchDate;
	private JTextField m_TextFieldDate;
	private JTextField m_TextFieldState;
	private JTextField m_TextFieldname;
	private PSARequestController m_Controller = null;
	
	/**
	 * Constructor of the class.
	 * @param controller : PSARequestController
	 */
	PSARequestDetails(PSARequestController controller)
	{
		m_Controller = controller;
		initGUI();
	}
	
	/**
	 * It  creates and initialize components. 
	 */
	private void initGUI() {
		try 
		{
			PSALanguageHandler lang_handler = m_Controller.m_ObjLanguageHandler;
			GridBagLayout jPanelRequestDetailsLayout = new GridBagLayout();
			if(m_Controller.m_CurrentOpr.getRequestMod().compareTo("ONESHOT") == 0)
			{
				jPanelRequestDetailsLayout.rowWeights = new double[] {0.0, 0.0, 0.0};
				jPanelRequestDetailsLayout.rowHeights = new int[] {30, 30, 30};
			} else {
				jPanelRequestDetailsLayout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0};
				jPanelRequestDetailsLayout.rowHeights = new int[] {30, 30, 30, 30, 30};
			}
			jPanelRequestDetailsLayout.columnWeights = new double[] {0.0, 0.1, 0.1, 0.1};
			jPanelRequestDetailsLayout.columnWidths = new int[] {161, 7, 20, 20};
			this.setLayout(jPanelRequestDetailsLayout);
			this.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
			
			//Request Name Label
			m_LabelRequestName = new JLabel();
			this.add(m_LabelRequestName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
			m_LabelRequestName.setText("Name of Request / PSN :");
			m_LabelRequestName.setText(lang_handler.Getlabel("Label.RequestName","Name of Request / PSN :"));
			m_LabelRequestName.setHorizontalAlignment(SwingConstants.RIGHT);
			m_LabelRequestName.setPreferredSize(new java.awt.Dimension(40, 0));
			
			//Request Name TextField
			m_TextFieldname = new JTextField();
			this.add(m_TextFieldname, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
			m_TextFieldname.setEditable(false);
			m_TextFieldname.setPreferredSize(new java.awt.Dimension(100, 21));
			m_TextFieldname.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			
			//Request Type Label
			m_LabelType = new JLabel();
			this.add(m_LabelType, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
			m_LabelType.setText("Type of Request / PSN :");
			m_LabelType.setText(lang_handler.Getlabel("Label.RequestType","Type of Request / PSN :"));
			m_LabelType.setHorizontalAlignment(SwingConstants.RIGHT);
			m_LabelType.setPreferredSize(new java.awt.Dimension(40, 0));

			//Request Type TextField
			m_TextFieldType = new JTextField();
			this.add(m_TextFieldType, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
			m_TextFieldType.setEditable(false);
			m_TextFieldType.setPreferredSize(new java.awt.Dimension(60, 21));
			m_TextFieldType.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				
			//Status of Request Label	
			m_LabelState = new JLabel();
			this.add(m_LabelState, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
			m_LabelState.setText("State of Request / PSN :");
			m_LabelState.setText(lang_handler.Getlabel("Label.RequestState","State of Request / PSN :"));
			m_LabelState.setHorizontalAlignment(SwingConstants.RIGHT);
			m_LabelState.setPreferredSize(new java.awt.Dimension(40, 0));
			
			//Status of Request TextField
			m_TextFieldState = new JTextField();
			this.add(m_TextFieldState, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
			m_TextFieldState.setEditable(false);
			m_TextFieldState.setPreferredSize(new java.awt.Dimension(60, 21));
			m_TextFieldState.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		    //Check for Init/VCO
			if(m_Controller.m_CurrentOpr.getRequestMod().compareTo("ONESHOT") != 0)
			{
				//Next Launch Date Label
				m_LabelNextLaunchDate = new JLabel();
				this.add(m_LabelNextLaunchDate, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
				m_LabelNextLaunchDate.setText("Next Launch Date :");
				m_LabelNextLaunchDate.setText(lang_handler.Getlabel("Label.NextLaunchDate","Next Launch Date :"));
				m_LabelNextLaunchDate.setHorizontalAlignment(SwingConstants.RIGHT);

				//Next Launch Date TextField
				m_TextFieldNextLaunchDate = new JTextField();
				this.add(m_TextFieldNextLaunchDate, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
				m_TextFieldNextLaunchDate.setEditable(false);
				m_TextFieldNextLaunchDate.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				m_TextFieldNextLaunchDate.setPreferredSize(new java.awt.Dimension(60, 21));

				//Expire Date Label
				m_LabelDate = new JLabel();
				this.add(m_LabelDate, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
				m_LabelDate.setText("Request / PSN active till :");
				m_LabelDate.setText(lang_handler.Getlabel("Label.RequestDate","Request / PSN active till :"));
				m_LabelDate.setHorizontalAlignment(SwingConstants.RIGHT);
				m_LabelDate.setPreferredSize(new java.awt.Dimension(40, 0));
				
				//Expire Date TextField
				m_TextFieldDate = new JTextField();
				this.add(m_TextFieldDate, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
				m_TextFieldDate.setEditable(false);
				m_TextFieldDate.setPreferredSize(new java.awt.Dimension(60, 21));
				m_TextFieldDate.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				this.setPreferredSize(new java.awt.Dimension(499, 172));
			} else 
			{
				this.setPreferredSize(new java.awt.Dimension(499, 140));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * It used to initalize the field value.
	 */
	public void initializeValues() {
		
		m_TextFieldname.setText(m_Controller.m_CurrentOpr.getRequestName());
		m_TextFieldType.setText(m_Controller.m_CurrentOpr.getRequestMod());
		if(m_Controller.m_CurrentOpr.getRequestMod().compareTo("ONESHOT") != 0)
		{
			m_TextFieldDate.setText(m_Controller.m_CurrentOpr.getExpiryDate());
			m_TextFieldNextLaunchDate.setText(m_Controller.m_CurrentOpr.getNextLaunchDate());
		}
		m_TextFieldState.setText(m_Controller.m_CurrentOpr.getRequestStatus());
	}
	
}
