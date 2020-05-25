/**********************************************************************************************************
 *
 * FILE NAME	  : PSAFilteredPartDetails
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Classes used as a data structure for displaying filtered part in Creation / Updation request panel.
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
 * Class containing the data structure to store the parent details with its config handler.
 */
class ParentDetails
{
	String m_ParentRef;
	String m_ParentCOID;
	String m_ParentCOMPID;
	String m_parentDesignation;
	String m_parentEnv;
	ArrayList<String> m_listCongHandler = new ArrayList<String>();
}

/**
 * Class containing the data structure to store the filtered parts with its parents.
 */
public class PSAFilteredPartDetails {
	
	PSNPart m_part;
	ArrayList<ParentDetails> m_parentList = new ArrayList<ParentDetails>();
}
