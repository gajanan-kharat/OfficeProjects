/**********************************************************************************************************
 *
 * FILE NAME	  : JHorrizontalComboBox.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    : Class used to create combobox with horrizontal scrollbar.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/
package com.psa.PSAAdminMSOutils;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;
/**
 * Class used to create combobox with horrizontal scrollbar.
 *
 */
@SuppressWarnings("serial")
public class JHorrizontalComboBox extends JComboBox
{
	/**
	 * Constructor of the class.
	 */
	public JHorrizontalComboBox() 
	{
		super();
		setUI(new JHorrizontalComboBoxUI());
	}

	/**
	 * 
	 *To set horrizotal scroll bar and UI display. 
	 */
	public class JHorrizontalComboBoxUI extends MetalComboBoxUI {
		
		/**
		 * create popup window of combobox.
		 */
		protected ComboPopup createPopup() {
			
			BasicComboPopup popup = new BasicComboPopup(comboBox) {
				/**
				 * Create scrollbar for popup window.
				 */
				protected JScrollPane createScroller() {
					return new JScrollPane(list,
							ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
							ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				}// end of method createScroller
			};
			return popup;
		}// end of method createPopup
	}// end of inner class myComboUI
}
