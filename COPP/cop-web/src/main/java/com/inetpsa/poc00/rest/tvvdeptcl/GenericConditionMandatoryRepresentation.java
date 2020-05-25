/*
 * Creation : Apr 27, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptcl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.rest.status.StatusNatureRepresentation;

/**
 * The Class GenericConditionMandatoryRepresentation.
 */
public class GenericConditionMandatoryRepresentation {

	/** The generic test condition. */
	@JsonIgnore
	private GenericTestConditionRepresentation genericTestCondition;

	/** The entity id. */
	protected long entityId;

	/** The value. */
	private boolean value;

	/** The status nature. */
	StatusNatureRepresentation statusNature;

	/** The status label. */
	String statusLabel;

	/** The test nature label. */
	String testNatureLabel;

	/** The status nature label. */
	String statusNatureLabel;

	/**
	 * Instantiates a new generic condition mandatory representation.
	 */
	public GenericConditionMandatoryRepresentation() {
		super();
	}

	/**
	 * Instantiates a new generic condition mandatory representation.
	 *
	 * @param entityId the entity id
	 * @param value the value
	 * @param statusNatureId the status nature id
	 */
	public GenericConditionMandatoryRepresentation(long entityId, boolean value, long statusNatureId) {
		this.entityId = entityId;
		this.value = value;
		this.statusNature = new StatusNatureRepresentation(statusNatureId);
	}

	/**
	 * Instantiates a new generic condition mandatory representation.
	 *
	 * @param entityId the entity id
	 * @param value the value
	 * @param statusNatureId the status nature id
	 * @param statusLabel the status label
	 * @param testLabel the test label
	 */
	public GenericConditionMandatoryRepresentation(long entityId, boolean value, long statusNatureId, String statusLabel, String testLabel) {
		this.entityId = entityId;
		this.value = value;
		this.statusNature = new StatusNatureRepresentation(statusNatureId);
		this.statusLabel = statusLabel;
		this.testNatureLabel = testLabel;
		this.statusNatureLabel = statusLabel + testLabel;
	}

	/**
	 * Gets the entity id.
	 *
	 * @return the entity id
	 */
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Checks if is value.
	 *
	 * @return true, if is value
	 */
	public boolean isValue() {
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
	 * Gets the generic test condition.
	 *
	 * @return the generic test condition
	 */
	public GenericTestConditionRepresentation getGenericTestCondition() {
		return genericTestCondition;
	}

	/**
	 * Sets the generic test condition.
	 *
	 * @param genericTestCondition the new generic test condition
	 */
	public void setGenericTestCondition(GenericTestConditionRepresentation genericTestCondition) {
		this.genericTestCondition = genericTestCondition;
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
	 * Sets the status nature.
	 *
	 * @param statusNature the new status nature
	 */
	public void setStatusNature(StatusNatureRepresentation statusNature) {
		this.statusNature = statusNature;
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

	/**
	 * Sets the entity id.
	 *
	 * @param entityId the new entity id
	 */
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

}
