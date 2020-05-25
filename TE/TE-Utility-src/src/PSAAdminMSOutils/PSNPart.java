/**********************************************************************************************************
 *
 * FILE NAME	  : PSNPart.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class used to hold part infomation.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/
package com.psa.PSAAdminMSOutils;

import java.util.ArrayList;

/**
 * Class used to hold part infomation.
 *
 */
public class PSNPart {

	String m_StrRef;
	String m_StrDesignation;
	String m_StrCOID;
	String m_StrCOMPID;
	String m_StrEnv;
	String m_StrTable;
	String m_StrType;
	boolean m_bSelect;
	int m_iLevel;
	int m_iDico;
	ArrayList<String> m_listConfigHandler = null;
	ArrayList<PSNPart> m_listParent = null;
	ArrayList<PSNPart> m_listChild = null;
	boolean m_bIsInUse;
	String m_StrSelectedParentCOID;
	String m_StrSelectedParentEnv;
	//String m_StrSelectedParentCOMPID;
	String m_StrSelectedConf;
	String m_StrSelectedParentREF;
	String m_StrSelectedParentDesignation;

	ArrayList<String> m_listLinkCOID = null;
	ArrayList<String> m_listLinkCOMPID = null;
	
	/**
	 * Constructor of the class.
	 */
	PSNPart()
	{
		m_bIsInUse = false;
		m_StrRef = "";
		m_StrDesignation = "";
		m_StrCOID = "";
		m_StrCOMPID = "";
		m_StrEnv = "";
		m_StrTable = "";
		m_StrType = "";
		m_bSelect = false;
		m_iDico = 0;
		m_iLevel = 0;
		m_listConfigHandler = new ArrayList<String>();
		m_listParent = new ArrayList<PSNPart>();
		m_listChild = new ArrayList<PSNPart>();
		
		m_StrSelectedParentCOID = "";
		//m_StrSelectedParentCOMPID = "";
		m_StrSelectedConf = "";
		m_StrSelectedParentREF = "";
		m_StrSelectedParentDesignation = "";
		m_listLinkCOID = new ArrayList<String>();
		m_listLinkCOMPID = new ArrayList<String>();
	}
	
	/** 
	 * Validate the input part is same or not.
	 * @return boolean 	true - If parts are same.
	 * 					false - If parts are not same.
	 */
	boolean IsEqual(PSNPart iDiffPsnPart)
	{
		if( (this.m_StrEnv.equals(iDiffPsnPart.m_StrEnv)) &&
		   (this.m_StrCOID.equalsIgnoreCase(iDiffPsnPart.m_StrCOID)) &&
		   (this.m_StrCOMPID.equalsIgnoreCase(iDiffPsnPart.m_StrCOMPID)) )
			return true;
		
		return false;
	}
	
	public void trace()
	{
		System.out.println("**********Part details*****************");
		System.out.println("ENV : "+m_StrEnv + "  COID : "+ m_StrCOID + "  COMPID : " + m_StrCOMPID);
		
		for(PSNPart parent_part:m_listParent)
		{
			System.out.println("Parent :: ENV : "+parent_part.m_StrEnv + "  COID : "+ parent_part.m_StrCOID + "  COMPID : " + parent_part.m_StrCOMPID);
		}
		
		for(PSNPart child_part:m_listChild)
		{
			System.out.println("Child :: ENV : "+child_part.m_StrEnv + "  COID : "+ child_part.m_StrCOID + "  COMPID : " + child_part.m_StrCOMPID);
		}
		
		for(String str_coid:m_listLinkCOID)
		{
			System.out.println("COID List :: COID :"  +str_coid);
		}

		for(String str_compid:m_listLinkCOMPID)
		{
			System.out.println("COMPID List :: COMPID :"  +str_compid);
		}
		System.out.println("*******************************************");
	}
}
