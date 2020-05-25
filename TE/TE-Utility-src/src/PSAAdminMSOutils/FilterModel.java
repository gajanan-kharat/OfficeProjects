/**********************************************************************************************************
 *
 * FILE NAME	  : FilterModel.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    : Class used for creating model for list which needs to be filter.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/
package com.psa.PSAAdminMSOutils;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

/**
 * Class used for creating model for list which needs to be filter.
 *  
 * @author      Mahendra Chuttar
 *  
 * @version     %I%, %G%
 *  
 * @since       1.0
 */
@SuppressWarnings("serial")
public class FilterModel extends AbstractListModel {
	ArrayList<Object> items;
    ArrayList<Object> filterItems;

	/**
	 * Constructor of the class.
	 */
	public FilterModel() {
	   super();
	   items = new ArrayList<Object>();
	   filterItems = new ArrayList<Object>();
    }
    

    /** 
     * Provides element at specified index.
     * @param	index		Index to read the data
     * @return 	Object	- String at the specified index.	
	 *			If index is out of Bound returns null.
     */
    public Object getElementAt (int index) {
    	try{
    		if (index < filterItems.size())
    			return filterItems.get (index);
    		else
    			return null;
    	} catch(Exception e){
    		System.out.println("Error in getElementAt");
    		return null;
    	}
    }
    
    /** 
     * Provides list size.
     * @return 	Size of elements in list
     */
    public int getSize() {
        return filterItems.size();
    }
    
	/**
	 * Insert a single element in list Model
	 * @param 	obj		Data to be add into the list.
	 */
	public void addElement (Object obj) {
		items.add (obj);
//		filter("");
		filterItems.add (obj);  
		fireContentsChanged (this, 0, getSize());
	}

	/**
	 * Insert a data array in list Model
	 * @param 	Object[] obj		Array of data to insert
	 */
	public void addElement (Object[] obj) {
		for(int i=0;i<obj.length;i++){
			items.add (obj[i]);
			filterItems.add (obj[i]);  
		}
		fireContentsChanged (this, 0, getSize());
	}

	/**
	 * Filters the Model List based on the string passed to it.
	 * @param 	str		string to filter
	 */
	public void filter(String str) {
		filterItems.clear();
	
		int filter_option = 0;
    	if(str.contains("*")){
    		filter_option = 1;
    		String temp_str = str;
    		str = "";
    		for(int i=0;i<temp_str.length();i++)
    		{
    			if(temp_str.charAt(i) == '*')
    				continue;
    			str += temp_str.charAt(i);
    		}
    	}

    	for (int i=0; i<items.size(); i++){
			if(0 == filter_option)
			{
			  if(false != items.get(i).toString().startsWith(str,0))
					filterItems.add (items.get(i));            
			} 
			else 
			{
				  if(false != items.get(i).toString().contains(str))
						filterItems.add (items.get(i));            
			}
		}
		
		fireContentsChanged (this, 0, getSize());
	}
	
	/**
	 * Remove element from list Model.
	 * @param 	index		index which entry needs to be removed
	 */
	public void removeElementAt(int index){
		items.remove(index);
		filterItems.remove(index);
		fireContentsChanged (this, 0, getSize());
	}
	
	/**
	 * Clears list.
	 */
	public void clear() {
		items.clear();
		filterItems.clear();
		fireContentsChanged (this, 0, getSize());
	}
}
