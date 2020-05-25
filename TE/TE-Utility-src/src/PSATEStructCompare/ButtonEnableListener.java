//COPYRIGHT PSA Peugeot Citroen 2012
/**********************************************************************************************************
 *
 * FILE NAME	  : ButtonEnableListener.java
 *
 * SOCIETE        : PSA
 * PROJET         : 171BO : Filtrage Maquette Num�rique configur�e
 * VERSION        : V1.0
 * DATE           : 11/01/2012
 *
 * AUTEUR         : Pankaj Pardhi (GEOMETRIC GLOBAL)
 *
 * DESCRIPTION    :  Interface for fire event
 *					
**********************************************************************************************************/

package com.psa.PSATEStructCompare;

//Standard class import
import java.awt.AWTEvent;

public interface ButtonEnableListener {
    /**
     * This method fired by PSADoubleList and DaysYearPanel.
     * 
     * @param event : Event of Control
     */
    public void fireButtonEnableEvent(AWTEvent event);
}
