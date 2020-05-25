package com.inetpsa.poc00.mandatorydata;

import java.util.List;

/**
 * The Class ManageMandatoryDataRequestDto.
 */
public class ManageMandatoryDataRequestDto {

	/** The mandatory data representation list. */
	public List<MandatoryDataRepresentation> genericDataList;


	/**
	 * Gets the generic data list.
	 * 
	 * @return the genericDataList
	 */
	public List<MandatoryDataRepresentation> getGenericDataList() {
		return genericDataList;
	}

	/**
	 * Sets the generic data list.
	 * 
	 * @param genericDataList the genericDataList to set
	 */
	public void setGenericDataList(List<MandatoryDataRepresentation> genericDataList) {
		this.genericDataList = genericDataList;
	}

}
