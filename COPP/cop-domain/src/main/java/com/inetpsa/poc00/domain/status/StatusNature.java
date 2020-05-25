/*
 * Creation : Apr 28, 2016
 */
package com.inetpsa.poc00.domain.status;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.inetpsa.poc00.domain.testnature.TestNature;

/**
 * The Class StatusNature.
 */
@Entity
@Table(name = "COPQTMSN")
public class StatusNature {

	/** The entity id. */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long entityId;

	/** The status. */
	@ManyToOne
	@JoinColumn(name = "STATUS_ID")
	private Status status;

	/** The test nature. */
	@ManyToOne
	@JoinColumn(name = "TEST_ID")
	private TestNature testNature;

	/**
	 * Instantiates a new status nature.
	 *
	 * @param entityId the entity id
	 * @param statusId the status id
	 * @param testNatureId the test nature id
	 */
	public StatusNature(long entityId, long statusId, long testNatureId) {
		this.entityId = entityId;
		this.status = new Status(statusId);
		this.testNature = new TestNature(testNatureId);
	}

	/**
	 * Instantiates a new status nature.
	 */
	public StatusNature() {
		// Default Constructor
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Gets the test nature.
	 *
	 * @return the test nature
	 */
	public TestNature getTestNature() {
		return testNature;
	}

	/**
	 * Sets the test nature.
	 *
	 * @param testNature the new test nature
	 */
	public void setTestNature(TestNature testNature) {
		this.testNature = testNature;
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

}
