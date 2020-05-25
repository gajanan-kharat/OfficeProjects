//COPYRIGHT PSA Peugeot Citroen 2012
/**********************************************************************************************************
 *
 * FILE NAME	  : FilterModel.java
 *
 * SOCIETE        : PSA
 * PROJET         : 171BO : Filtrage Maquette Num�rique configur�e
 * VERSION        : V1.0
 * DATE           : 11/01/2012
 *
 * AUTEUR         : Pankaj Pardhi (GEOMETRIC GLOBAL)
 *
 * DESCRIPTION    : Class used for creating model for list which needs to be filter.
 *					
**********************************************************************************************************/
package com.psa.PSATEStructCompare;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.AbstractListModel;

/**
 * Class used for creating model for list which needs to be filter.
 */
@SuppressWarnings("serial")
public class FilterModel extends AbstractListModel {
    private static final Logger logger = Logger.getLogger("FilterModel");
    List<Object> arrayListItems;
    List<Object> arrayListFilterItems;

    /**
     * Constructor of the class.
     */
    public FilterModel() {
        super();
        arrayListItems = new ArrayList<Object>();
        arrayListFilterItems = new ArrayList<Object>();
    }

    /**
     * Provides element at specified index.
     * 
     * @param index Index to read the data
     * @return Object - String at the specified index. If index is out of Bound returns null.
     */
    @Override
    public Object getElementAt(int index) {
        try {
            if (index < arrayListFilterItems.size())
                return arrayListFilterItems.get(index);
            return null;
        } catch (Exception e) {
            logger.info("Error in getElementAt");
            return null;
        }
    }

    /**
     * Provides list size.
     * 
     * @return Size of elements in list
     */
    @Override
    public int getSize() {
        return arrayListFilterItems.size();
    }

    /**
     * Insert a single element in list Model
     * 
     * @param obj Data to be add into the list.
     */
    public void addElement(Object obj) {
        arrayListItems.add(obj);
        arrayListFilterItems.add(obj);
        fireContentsChanged(this, 0, getSize());
    }

    /**
     * Insert a data array in list Model
     * 
     * @param Object[] obj Array of data to insert
     */
    public void addElement(Object[] obj) {
        for (int i = 0; i < obj.length; i++) {
            arrayListItems.add(obj[i]);
            arrayListFilterItems.add(obj[i]);
        }
        fireContentsChanged(this, 0, getSize());
    }

    /**
     * Filters the Model List based on the string passed to it.
     * 
     * @param pattern string to filter
     */
    public void filter(String pattern1) {
        arrayListFilterItems.clear();
        int size = arrayListItems.size();
        String pattern = pattern1.replace("*", ".*");
        for (int index = 0; index < size; index++) {
            String value = (String) arrayListItems.get(index);
            // Match Pattern with value.
            if (value.matches(pattern)) {
                arrayListFilterItems.add(value);
            }
        }
        fireContentsChanged(this, 0, getSize());
    }

    /**
     * Remove element from list Model.
     * 
     * @param index index which entry needs to be removed
     */
    public void removeElementAt(int index) {
        arrayListItems.remove(index);
        arrayListFilterItems.remove(index);
        fireContentsChanged(this, 0, getSize());
    }

    /**
     * Clears list.
     */
    public void clear() {
        arrayListItems.clear();
        arrayListFilterItems.clear();
        fireContentsChanged(this, 0, getSize());
    }

    /**
     * Clears list.
     */
    public void removeSelectedElements(List<String> deletedList) {

        arrayListItems.removeAll(deletedList);
        arrayListFilterItems.removeAll(deletedList);
        fireContentsChanged(this, 0, getSize());
    }
}
