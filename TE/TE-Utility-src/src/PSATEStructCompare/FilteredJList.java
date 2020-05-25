//COPYRIGHT PSA Peugeot Citroen 2012
/**********************************************************************************************************
 *
 * FILE NAME	  : FilteredJList.java
 *
 * SOCIETE        : PSA
 * PROJET         : 171BO : Filtrage Maquette Num�rique configur�e
 * VERSION        : V1.0
 * DATE           : 11/01/2012
 *
 * AUTEUR         : Pankaj Pardhi (GEOMETRIC GLOBAL)
 *
 * DESCRIPTION    : Class used for creating list which needs to be filter.
 *					
**********************************************************************************************************/
package com.psa.PSATEStructCompare;

//Standard class import
import javax.swing.JList;
import javax.swing.ListModel;

/**
 * Class used for creating list which needs to be filter.
 */
@SuppressWarnings("serial")
public class FilteredJList extends JList {

    /**
     * Constructor of class.
     */
    public FilteredJList() {
        super();
        setModel(new FilterModel());
    }

    /**
     * Sets FilterModel to List.
     * 
     * @param model ListModel to add
     */
    @Override
    public void setModel(ListModel model) {
        if (!(model instanceof FilterModel))
            throw new IllegalArgumentException();
        super.setModel(model);
    }

    /**
     * Adds single element to List. Overriden function for FilterModel.
     * 
     * @param Object o Element to add
     */
    public void addItem(Object o) {
        ((FilterModel) getModel()).addElement(o);
    }

    /**
     * Adds Array of Elements to List. Overriden function for FilterModel.
     * 
     * @param Object[] o Add Array of Elements to List
     */
    public void addItem(Object[] o) {
        ((FilterModel) getModel()).addElement(o);
    }

    /**
     * Filter the List based on the string.
     * 
     * @param strToFilter String to Filter
     */
    public void filter(String strToFilter) {
        String filter = strToFilter.toUpperCase();
        ((FilterModel) getModel()).filter(filter);
    }

    /**
     * Remove element from list.
     * 
     * @param index index which entry needs to be removed
     */
    public void removeElementAt(int index) {
        ((FilterModel) getModel()).removeElementAt(index);
    }

    /**
     * Clears list.
     */
    public void clear() {
        ((FilterModel) getModel()).clear();
    }
}