/**********************************************************************************************************
 *
 * FILE NAME	  : ReqFreq.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class used to hold date information.
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
 * 
 *Class used to hold date information.
 *
 */
public class ReqFreq {

	String m_StrType;
	ArrayList<String> m_listValues = new ArrayList<String>();
	
	/**
	 * Constructor of the class.
	 * 
	 */
	ReqFreq()
	{
		m_StrType = "";
		m_listValues.clear();
	}
}
