//COPYRIGHT PSA Peugeot Citroen 2010
/**********************************************************************************************************
 *
 * FILE NAME	: UniqueCOID.java
 *
 * SOCIETE        : PSA
 * PROJET         : 151AP - Gestion d'Accès à la MNU Vues Fournisseurs - UI
 * VERSION        : V1.0
 * DATE           : 20/07/2010
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    : Class used to generate Unique COID in java applications.
 *					JNI is used to generate Unique COID by using libUniqueCOID.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/
package com.psa.UniqueCOID;


/**
 * Class used to generate Unique COID in java applications.
 * JNI is used to generate Unique COID by using libUniqueCOID. 
 * 
 * @author      Mahendra Chuttar
 *  
 * @version     %I%, %G%
 *  
 * @since       1.0
 */
public class UniqueCOID
{

	static
	{
		System.out.println("Loading Library");
		System.loadLibrary("UniqueCOID");
	}

	public native String getUniqueCOID(); // We need to declare this method in class to access getUniqueCOID function in C Library

	/**
	 * Provides the Unique COID.
	 * @return Object[0]	boolean true  - Unique COID generation success.
	 *								false - Unique COID generation failure.
	 *		   Object[1]	genrated COID.
	 */
	public Object[] GetUniqueCOID() 
        {
			boolean bRet = false;
			String StrCOID = null;

			try
			{
				StrCOID = getUniqueCOID();
				bRet = true;
			}
			catch (Exception e)
			{
				e.printStackTrace();				
			}

			return new Object[] {bRet, StrCOID};
        }
}
