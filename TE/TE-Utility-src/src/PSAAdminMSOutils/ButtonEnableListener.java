/**********************************************************************************************************
 *
 * FILE NAME	  : ButtonEnableListener.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Interface for fire event
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/

package com.psa.PSAAdminMSOutils;

//Standard class import
import java.awt.AWTEvent;


public interface ButtonEnableListener {
	 /**
	  * This method fired by PSADoubleList  and DaysYearPanel. 
	  * @param event : Event of Control
	  */
     public void fireButtonEnableEvent(AWTEvent event);
}
