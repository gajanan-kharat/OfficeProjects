//COPYRIGHT PSA Peugeot Citroen 2010
/**********************************************************************************************************
 *
 * FILE NAME	: PSAErrorHandler.java
 *
 * SOCIETE        : PSA
 * PROJET         : 151AP - Gestion d'Accès à la MNU Vues Fournisseurs - UI
 * VERSION        : V1.0
 * DATE           : 20/07/2010
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class for Error Handling used in Exception Handling
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/

package com.psa.tools; 

/**
 * Class for Error Handling used in Exception Handling
 * Exception PSAErrorHandler is thrown with Error Label and error code .
 *  
 * @author      Mahendra Chuttar
 *  
 * @version     %I%, %G%
 *  
 * @since       1.0
 */
@SuppressWarnings("serial")
public class PSAErrorHandler extends Exception{ 
        public int ErrorCode; 			//Error Code
        public String m_StrErrorLabel; 	//Error Label
        
        /**
         * Constructor of class
         * @param iErrorCode		Errro Code
         * @param iStrErrorLabel	Error Label
         */
        public PSAErrorHandler(int iErrorCode,String iStrErrorLabel) { 
                ErrorCode = iErrorCode; 
                m_StrErrorLabel = iStrErrorLabel; 
        } 
        
}
