/*
 * Creation : Apr 28, 2016
 */
package com.inetpsa.poc00.pollutantorgas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.rest.status.StatusNatureRepresentation;

/**
 * The Class PollutantLimitMandatoryRepresentation.
 */
public class PollutantLimitMandatoryRepresentation {

	/** The entity id. */
	protected long entityId;

	/** The value. */
	private boolean value;

	/** The pollutant gas limit. */
	@JsonIgnore
	PollutantGasLimitRepresentation pollutantGasLimit;

	/** The status nature. */
	StatusNatureRepresentation statusNature;

	/** The status label. */
	String statusLabel;

	/** The test nature label. */
	String testNatureLabel;

	/** The status nature label. */
	String statusNatureLabel;

	/**
	 * Instantiates a new pollutant limit mandatory representation.
	 *
	 * @param entityId the entity id
	 * @param value the value
	 * @param statusNatureId the status nature id
	 * @param statusLabel the status label
	 * @param testLabel the test label
	 */
	public PollutantLimitMandatoryRepresentation(long entityId, boolean value, long statusNatureId, String statusLabel, String testLabel) {
		this.entityId = entityId;
		this.value = value;
		this.statusNature = new StatusNatureRepresentation(statusNatureId);
		this.statusLabel = statusLabel;
		this.testNatureLabel = testLabel;
		this.statusNatureLabel = statusLabel + testLabel;
	}

	/**
	 * Instantiates a new pollutant limit mandatory representation.
	 */
	public PollutantLimitMandatoryRepresentation() {
		// Default Constructor
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
	 * Gets the pollutant gas limit.
	 *
	 * @return the pollutant gas limit
	 */
	public PollutantGasLimitRepresentation getPollutantGasLimit() {
		return pollutantGasLimit;
	}

	/**
	 * Sets the pollutant gas limit.
	 *
	 * @param pollutantGasLimit the new pollutant gas limit
	 */
	public void setPollutantGasLimit(PollutantGasLimitRepresentation pollutantGasLimit) {
		this.pollutantGasLimit = pollutantGasLimit;
	}

	/**
	 * Gets the status nature.
	 *
	 * @return the status nature
	 */
	public StatusNatureRepresentation getStatusNature() {
		return statusNature;
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

}
