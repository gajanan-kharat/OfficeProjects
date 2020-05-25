/*
 * Creation : Apr 27, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptdl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.rest.status.StatusNatureRepresentation;

/**
 * The Class GenericTechDataMandatoryRepresentation.
 */
public class GenericTechDataMandatoryRepresentation {

	/** The technical data. */
	private static String technicalData = "Donnée Technique";
	
	/** The tech condition data. */
	private static String techConditionData = "Condition d’essai";
	
	/** The technical limit data. */
	private static String technicalLimitData = "Limite";
	
	/** The Object type. */
	private String ObjectType; // use for saving
	
	/** The object type label. */
	private String objectTypeLabel; // TCL or TDL list Label
	
	/** The entity id. */
	long entityId;
	
	/** The label. */
	String label;
	
	/** The value. */
	boolean value;
	
	/** The Listlabel. */
	private String Listlabel ; //parent Label
	
	/** The mandatory flag list. */
	List<StatusNatureRepresentation> mandatoryFlagList;         // contain id and Mandat Flag
	
	/** The generic technical data. */
	@JsonIgnore
	private GenericTechnicalDataRepresentation genericTechnicalData;
	
	/** The status nature. */
	StatusNatureRepresentation statusNature;
	
	/** The status label. */
	String statusLabel;
	
	/** The test nature label. */
	String testNatureLabel;
	
	/** The status nature label. */
	String statusNatureLabel;

	/**
	 * Instantiates a new generic tech data mandatory representation.
	 */
	public GenericTechDataMandatoryRepresentation() {
		super();

	}

	/**
	 * Instantiates a new generic tech data mandatory representation.
	 *
	 * @param entityId the entity id
	 * @param value the value
	 * @param statusNatureId the status nature id
	 * @param statusLabel the status label
	 * @param testLabel the test label
	 */
	public GenericTechDataMandatoryRepresentation(long entityId, boolean value, long statusNatureId, String statusLabel, String testLabel) {
		this.entityId = entityId;
		this.value = value;
		this.statusNature = new StatusNatureRepresentation(statusNatureId);
		this.statusLabel = statusLabel;
		this.testNatureLabel = testLabel;
		this.statusNatureLabel = statusLabel + testLabel;
	}

	/**
	 * Instantiates a new generic tech data mandatory representation.
	 *
	 * @param isMandatory the is mandatory
	 */
	public GenericTechDataMandatoryRepresentation(boolean isMandatory) {
		this.value = isMandatory;
	}

	/**
	 * Gets the entity id.
	 *
	 * @return the entity id
	 */
	public long getEntityId() {
		return entityId;
	}

	/**
	 * Sets the entity id.
	 *
	 * @param entityId the new entity id
	 */
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Boolean getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(boolean value) {
		this.value = value;
	}

	/**
	 * Gets the generic technical data.
	 *
	 * @return the generic technical data
	 */
	public GenericTechnicalDataRepresentation getGenericTechnicalData() {
		return genericTechnicalData;
	}

	/**
	 * Sets the generic technical data.
	 *
	 * @param genericTechnicalData the new generic technical data
	 */
	public void setGenericTechnicalData(GenericTechnicalDataRepresentation genericTechnicalData) {
		this.genericTechnicalData = genericTechnicalData;
	}

	/**
	 * Sets the status nature.
	 *
	 * @param statusNature the new status nature
	 */
	public void setStatusNature(StatusNatureRepresentation statusNature) {
		this.statusNature = statusNature;

	}

	/**
	 * Gets the status nature.
	 *
	 * @return the status nature
	 */
	public StatusNatureRepresentation getStatusNature() {

		return this.statusNature;
	}

	/**
	 * Gets the status label.
	 *
	 * @return the status label
	 */
	public String getStatusLabel() {
		return statusLabel;
	}

	/**
	 * Sets the status label.
	 *
	 * @param statusLabel the new status label
	 */
	public void setStatusLabel(String statusLabel) {
		this.statusLabel = statusLabel;
	}

	/**
	 * Gets the test nature label.
	 *
	 * @return the test nature label
	 */
	public String getTestNatureLabel() {
		return testNatureLabel;
	}

	/**
	 * Sets the test nature label.
	 *
	 * @param testNatureLabel the new test nature label
	 */
	public void setTestNatureLabel(String testNatureLabel) {
		this.testNatureLabel = testNatureLabel;
	}

	/**
	 * Gets the status nature label.
	 *
	 * @return the status nature label
	 */
	public String getStatusNatureLabel() {
		return statusNatureLabel;
	}

	/**
	 * Sets the status nature label.
	 *
	 * @param statusNatureLabel the new status nature label
	 */
	public void setStatusNatureLabel(String statusNatureLabel) {
		this.statusNatureLabel = statusNatureLabel;
	}

}
