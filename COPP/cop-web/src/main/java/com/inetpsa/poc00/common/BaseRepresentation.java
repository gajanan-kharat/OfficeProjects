/*
 * Creation : Apr 7, 2016
 */
package com.inetpsa.poc00.common;

/**
 * The Class BaseRepresentation.
 */
public class BaseRepresentation {
	
	/** The modified flag. */
	private String modifiedFlag;
	
	/** The edited. */
	private boolean edited;
	
	/** The type of list. */
	private String typeOfList;

	/**
	 * Gets the modified flag.
	 *
	 * @return the modified flag
	 */
	public String getModifiedFlag() {
		return modifiedFlag;
	}

	/**
	 * Sets the modified flag.
	 *
	 * @param modifiedFlag the new modified flag
	 */
	public void setModifiedFlag(String modifiedFlag) {
		this.modifiedFlag = modifiedFlag;
	}

	/**
	 * Checks if is edited.
	 *
	 * @return true, if is edited
	 */
	public boolean isEdited() {
		return edited;
	}

	/**
	 * Sets the edited.
	 *
	 * @param edited the new edited
	 */
	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	/**
	 * Gets the type of list.
	 *
	 * @return the type of list
	 */
	public String getTypeOfList() {
		return typeOfList;
	}

	/**
	 * Sets the type of list.
	 *
	 * @param typeOfList the new type of list
	 */
	public void setTypeOfList(String typeOfList) {
		this.typeOfList = typeOfList;
	}

}
