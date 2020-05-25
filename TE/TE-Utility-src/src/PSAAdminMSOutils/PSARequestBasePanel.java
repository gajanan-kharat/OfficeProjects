/**********************************************************************************************************
 *
 * FILE NAME	  : PSARequestBasePanel.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class used to provide common functionality to all panel.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/
package com.psa.PSAAdminMSOutils;


import java.awt.Cursor;

/**
 * 
 *  Class used to provide common functionality to all panel.
 *
 */
public class PSARequestBasePanel {
	public static final Cursor DEFAULT_CURSOR = Cursor.getDefaultCursor();						//Default cursor
	public static final Cursor WAIT_CURSOR = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);	//Wait cursor
	
	public PSARequestController m_Controller;				//Controller Object
	
	public enum PANEL {
		DEFAULT_PANEL,
		MAIN_PANEL,
		CREATION_PANEL,
		REQUESTLISTE_PANEL,
		SITESELECTION_PANEL,
		SUMMARY_PANEL,
		ESTIMATEDATA_PANEL,
		BLOCKUNBLOCK_PANEL,
		CONSULTATIONLOG_PANEL
	}
	public enum ACTION
	{
		DEFAULT,
		MAIN,
		CREATEREQUEST,
		UPDATEREQUEST,
		VIEWREQUEST,
		ESTIMATEDATA,
		BLOCKREQUEST,
		DELETEREQUEST,
		CONSULTATIONLOG,
		EXIT
	}
	
	/**
	 * Constructor of the class.
	 * @param iController		Controller Object
	 */
	public PSARequestBasePanel(PSARequestController iController)
	{
		m_Controller = iController; 		
	}
	
	/**
	 * Sets the Action Type as per the selection in Main Panel 
	 * @param iActionType		Selected Action Type
	 */
	public void SetActionType(ACTION iActionType)
	{
		m_Controller.setActionType(iActionType);
	}
	
	/**
	 * Provides the Action Type
	 * @return	Retrieves the Action Type from Controller
	 */
	public ACTION GetActionType()
	{
		return m_Controller.GetActionType();
	}
	
	/**
	 * DUMMY METHOD. OVERWRITTEN IN ALL CHILD CLASSES to hide or show the panel
	 * @param status	true - To Show panel, false -  To Hide panel 
	 */
	public void ShowWindow(boolean status)
	{
		
	}
	
	/**
	 * DUMMY METHOD. OVERWRITTEN IN ALL CHILD CLASSES to dispose panels.
	 */
	public void disposePanel()
	{
		
	}

}
