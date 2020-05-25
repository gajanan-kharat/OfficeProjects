/*
 * Creation : Sep 22, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.rest.typeoftest;

/**
 * TypeOfTestRepresentation class
 * 
 * @author mehaj
 */
public class TypeOfTestRepresentation {

	/** The type of test Id */
	private Long typeOfTestId;

	/** The type of test label */
	private String label;

	/** The test nature value */
	private String testNatureType;

	/**
	 * Instantiates a new TypeOfTest representation.
	 */
	public TypeOfTestRepresentation() {
		// Default Constructor
	}

	/**
	 * Instantiates a new TypeOfTest representation.
	 * 
	 * @param entityId the typeOfTestId
	 * @param label the label
	 */
	public TypeOfTestRepresentation(Long typeOfTestId, String label) {
		this.typeOfTestId = typeOfTestId;
		this.label = label;

	}

	/**
	 * Instantiates a new TypeOfTest representation.
	 * 
	 * @param typeOfTestId the entity id
	 * @param testNatureType the testNature value
	 */
	public TypeOfTestRepresentation(Long typeOfTestId, String label, String testNatureType) {
		this.typeOfTestId = typeOfTestId;
		this.label = label;
		this.testNatureType = testNatureType;
	}

	/**
	 * Getter typeOfTestId
	 * 
	 * @return the typeOfTestId
	 */
	public Long getTypeOfTestId() {
		return typeOfTestId;
	}

	/**
	 * Setter typeOfTestId
	 * 
	 * @param typeOfTestId the typeOfTestId to set
	 */
	public void setTypeOfTestId(Long typeOfTestId) {
		this.typeOfTestId = typeOfTestId;
	}

	/**
	 * Getter label
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Setter label
	 * 
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Getter testNatureType
	 * 
	 * @return the testNatureType
	 */
	public String getTestNatureType() {
		return testNatureType;
	}

	/**
	 * Setter testNatureType
	 * 
	 * @param testNatureType the testNatureType to set
	 */
	public void setTestNatureType(String testNatureType) {
		this.testNatureType = testNatureType;
	}
}
