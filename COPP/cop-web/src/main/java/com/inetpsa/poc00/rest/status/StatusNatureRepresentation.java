/*
 * Creation : Apr 28, 2016
 */
package com.inetpsa.poc00.rest.status;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.rest.testnature.TestNatureRepresentation;

/**
 * The Class StatusNatureRepresentation.
 */
public class StatusNatureRepresentation {

	/** The entity id. */
	long entityId;

	/** The status. */
	@JsonIgnore
	StatusRepresentation status;

	/** The status id. */
	long statusId;

	/** The status label. */
	String statusLabel;

	/** The test nature. */
	@JsonIgnore
	TestNatureRepresentation testNature;

	/** The test nature id. */
	long testNatureId;

	/** The test nature label. */
	String testNatureLabel;

	/** The flag. */
	private boolean flag = Boolean.FALSE;

	/**
	 * Instantiates a new status nature representation.
	 */
	public StatusNatureRepresentation() {
		super();
	}

	/**
	 * Instantiates a new status nature representation.
	 * 
	 * @param entityId the entity id
	 * @param statusId the status id
	 * @param testNatureId the test nature id
	 */
	public StatusNatureRepresentation(long entityId, long statusId, long testNatureId) {
		this.entityId = entityId;
		this.statusId = statusId;
		this.testNatureId = testNatureId;
	}

	/**
	 * Instantiates a new status nature representation.
	 * 
	 * @param entityId the entity id
	 * @param statusLabel the status label
	 * @param testNatureLabel the test nature label
	 */
	public StatusNatureRepresentation(long entityId, String statusLabel, String testNatureLabel) {
		this.entityId = entityId;
		this.statusLabel = statusLabel;
		this.testNatureLabel = testNatureLabel;
	}

	/**
	 * Instantiates a new status nature representation.
	 * 
	 * @param statusNatureId the status nature id
	 */
	public StatusNatureRepresentation(long statusNatureId) {
		this.entityId = statusNatureId;
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
	 * Sets the entity id.
	 * 
	 * @param entityId the new entity id
	 */
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public StatusRepresentation getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status the new status
	 */
	public void setStatus(StatusRepresentation status) {
		this.status = status;
	}

	/**
	 * Gets the test nature.
	 * 
	 * @return the test nature
	 */
	public TestNatureRepresentation getTestNature() {
		return testNature;
	}

	/**
	 * Sets the test nature.
	 * 
	 * @param testNature the new test nature
	 */
	public void setTestNature(TestNatureRepresentation testNature) {
		this.testNature = testNature;
	}

	/**
	 * Gets the status id.
	 * 
	 * @return the status id
	 */
	public long getStatusId() {
		return statusId;
	}

	/**
	 * Sets the status id.
	 * 
	 * @param statusId the new status id
	 */
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	/**
	 * Gets the test nature id.
	 * 
	 * @return the test nature id
	 */
	public long getTestNatureId() {
		return testNatureId;
	}

	/**
	 * Sets the test nature id.
	 * 
	 * @param testNatureId the new test nature id
	 */
	public void setTestNatureId(long testNatureId) {
		this.testNatureId = testNatureId;
	}

	/**
	 * Checks if is flag.
	 * 
	 * @return true, if is flag
	 */
	public boolean isFlag() {
		return flag;
	}

	/**
	 * Sets the flag.
	 * 
	 * @param flag the new flag
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
