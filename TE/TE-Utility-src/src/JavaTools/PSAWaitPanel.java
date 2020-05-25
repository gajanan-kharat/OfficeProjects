//COPYRIGHT PSA Peugeot Citroen 2011
/**********************************************************************************************************
 *
 * FILE NAME	: PSAWaitPanel.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    : Class for Display Wait message dialog.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/

package com.psa.tools;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.Cursor;

/**
 * Class for Display Modal Wait message dialog.
 *  
 * @author      Mahendra Chuttar
 *  
 * @version     %I%, %G%
 *  
 * @since       1.0
 */
public class PSAWaitPanel 
{
	public static final Cursor DEFAULT_CURSOR = Cursor.getDefaultCursor();						//Default cursor
	public static final Cursor WAIT_CURSOR = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);	//Wait cursor
	
	private JLabel Label;			//Used for message to be displayed
	private JDialog dlg;
	private String m_StrMessage;
	private String m_StrMessageTitle;
	/**
	 * Constructor of the class.
	 * 
	 */ 

	public PSAWaitPanel(String strPanelTitle, String strMessage) {
		super();
		m_StrMessageTitle = strPanelTitle;
		m_StrMessage = strMessage;
		initGUI();
	}

	/**
	 * Displays wait mesage panel.
	 */ 

	public void DisplayPanel()
	{
		dlg.setLocationRelativeTo(null);
		dlg.setCursor(WAIT_CURSOR);
		dlg.setVisible(true);
	}

	/**
	 * Displays or hides wait cursor on wait message panel.
	 * @param 	flag	true - To display wait cursor, false - To display default cursor. 
	 */ 

	public void ShowCursor(boolean flag)
	{
		if(flag == true)
		{
			dlg.setCursor(WAIT_CURSOR);
		} else
		{
			dlg.setCursor(DEFAULT_CURSOR);
		}
	}
	
	/**
	 * Distroy wait message panel.
	 */ 
	public void Dispose()
	{
		dlg.setCursor(DEFAULT_CURSOR);		dlg.dispose();
	}
	
	/**
	 * Initialises the controls on the Panel.
	 */
	private void initGUI() {
		try {
			//Dialog control creation
				dlg = new JDialog();
				dlg.setModal(true);
				dlg.setAlwaysOnTop(true); 
				
				GridBagLayout thisLayout = new GridBagLayout();
				thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
				thisLayout.rowHeights = new int[] {7, 7, 7, 7};
				thisLayout.columnWeights = new double[] {0.0, 0.1, 0.1, 0.1, 0.0, 0.0};
				thisLayout.columnWidths = new int[] {26, 7, 7, 7, 34, 7};
				dlg.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				dlg.setBackground(new java.awt.Color(236,233,216));
				dlg.getContentPane().setLayout(thisLayout);
				dlg.setTitle(m_StrMessageTitle);
				
				//Label for mesasge display
				Label = new JLabel();
				dlg.getContentPane().add(Label, new GridBagConstraints(1, 1, 4, 2, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));				System.out.println("Wait panel message = "+m_StrMessage);
				Label.setText(m_StrMessage);
	
				Label.setHorizontalTextPosition(SwingConstants.CENTER);
				Label.setHorizontalAlignment(SwingConstants.CENTER);
	
				dlg.pack();
				dlg.setSize(355,171);
			
			} catch (Exception e) {
				e.printStackTrace();
		}
	}

}
