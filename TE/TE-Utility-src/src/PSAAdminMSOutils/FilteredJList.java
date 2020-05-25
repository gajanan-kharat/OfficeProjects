/**********************************************************************************************************
 *
 * FILE NAME	  : FilteredJList.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    : Class used for creating list which needs to be filter.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/

package com.psa.PSAAdminMSOutils;

//Standard class import
import javax.swing.JList;
import javax.swing.ListModel;

/**
 * Class used for creating list which needs to be filter.
 *  
 * @author      Mahendra Chuttar
 *  
 * @version     %I%, %G%
 *  
 * @since       1.0
 */
@SuppressWarnings("serial")
public class FilteredJList extends JList {

	/**
	 * Constructor of class.
	 */
   public FilteredJList() {
        super();
        setModel (new FilterModel());
     }

	/**
	 * Sets FilterModel to List.
	 * @param model		ListModel to add
	 */
   public void setModel (ListModel model) {
        if (! (model instanceof FilterModel))
            throw new IllegalArgumentException();
        super.setModel (model);
    }

	/**
	 * Adds single element to List. Overriden function for FilterModel.
	 * @param	Object o		Element to add
	 */
    public void addItem (Object o) {
        ((FilterModel)getModel()).addElement (o);
    }

	/**
	 * Adds Array of Elements to List. Overriden function for FilterModel.
	 * @param	Object[] o		Add Array of Elements to List
	 */
    public void addItem (Object[] o) {
        ((FilterModel)getModel()).addElement (o);
    }
    
	/**
	 * Filter the List based on the string.
	 * @param	str_to_filter		String to Filter
	 */
    public void filter(String str_to_filter){
    	str_to_filter = str_to_filter.toUpperCase();
    	((FilterModel)getModel()).filter(str_to_filter);
    }
    
	/**
	 * Remove element from list.
	 * @param	index	index which entry needs to be removed
	 */
   public void removeElementAt(int index) {
    	((FilterModel)getModel()).removeElementAt(index);
    }
    
	/**
	 * Clears list.
	 */
    public void clear(){
    	((FilterModel)getModel()).clear();
    }
}