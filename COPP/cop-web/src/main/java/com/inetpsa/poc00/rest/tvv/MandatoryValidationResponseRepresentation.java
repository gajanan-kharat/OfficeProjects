/*
 * Creation : Jul 14, 2016
 */
package com.inetpsa.poc00.rest.tvv;

import java.util.ArrayList;

/**
 * The Class MandatoryValidationResponseRepresentation.
 */
public class MandatoryValidationResponseRepresentation {

	/** The validation status. */
	private String validationStatus;

	/** The validation response list. */
	private ArrayList<TvvValidationResponseRepresentation> validationResponseList;

	/**
	 * Gets the validation status.
	 * 
	 * @return the validation status
	 */
	public String getValidationStatus() {
		return validationStatus;
	}

	/**
	 * Sets the validation status.
	 * 
	 * @param validationStatus the new validation status
	 */
	public void setValidationStatus(String validationStatus) {
		this.validationStatus = validationStatus;
	}

	/**
	 * Gets the validation response list.
	 * 
	 * @return the validation response list
	 */
	public ArrayList<TvvValidationResponseRepresentation> getValidationResponseList() {
		return validationResponseList;
	}

	/**
	 * Sets the validation response list.
	 * 
	 * @param validationResponseList the new validation response list
	 */
	public void setValidationResponseList(ArrayList<TvvValidationResponseRepresentation> validationResponseList) {
		this.validationResponseList = validationResponseList;
	}

}
