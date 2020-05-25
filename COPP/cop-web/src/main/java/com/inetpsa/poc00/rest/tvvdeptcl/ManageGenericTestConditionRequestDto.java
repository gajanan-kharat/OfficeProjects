/*
 * Creation : Apr 27, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptcl;

import java.util.List;

/**
 * The Class ManageGenericTestConditionRequestDto.
 */
public class ManageGenericTestConditionRequestDto {
	
	/** The generic condition list. */
	List<GenericTestConditionRepresentation> genericConditionList;

	/**
	 * Gets the generic condition list.
	 *
	 * @return the generic condition list
	 */
	public List<GenericTestConditionRepresentation> getGenericConditionList() {
		return genericConditionList;
	}

	/**
	 * Sets the generic condition list.
	 *
	 * @param genericConditionList the new generic condition list
	 */
	public void setGenericConditionList(List<GenericTestConditionRepresentation> genericConditionList) {
		this.genericConditionList = genericConditionList;
	}

}
