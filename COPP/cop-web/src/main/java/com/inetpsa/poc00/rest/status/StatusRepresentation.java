package com.inetpsa.poc00.rest.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.inetpsa.poc00.rest.testnature.TestNatureRepresentation;

/**
 * The Class StatusRepresentation.
 */
public class StatusRepresentation {

	/** The entity id. */
	protected Long entityId;

	/** The gui label. */
	private String guiLabel;

	/** The label. */
	private String label;

	/** The test nature label.This is actually used for TestNatureType */

	private String testNatureLabel;

	/** The test nature label old. */
	private String testNatureLabelOld;

	/** The display label. */
	private String displayLabel;

	/** The status nature. */
	private List<StatusNatureRepresentation> statusNature = new ArrayList<>();

	/** The testrepresentationdata. */
	private Set<TestNatureRepresentation> testrepresentationdata = new HashSet<>();

	/** The test nature id. */
	private long testNatureId;

	/** The test label. */
	// This represents Test Nature Label
	private String testLabel;

	/**
	 * Instantiates a new status representation.
	 */
	public StatusRepresentation() {
		super();

	}

	/**
	 * Instantiates a new status representation.
	 * 
	 * @param label the label
	 */
	public StatusRepresentation(String label) {
		this.label = label;
	}

	/**
	 * Instantiates a new status representation.
	 * 
	 * @param entityId the entity id
	 * @param guiLabel the gui label
	 * @param label the label
	 */
	public StatusRepresentation(Long entityId, String guiLabel, String label) {
		super();
		this.entityId = entityId;
		this.guiLabel = guiLabel;
		this.label = label;

	}

	/**
	 * Instantiates a new status representation.
	 * 
	 * @param entityId the entity id
	 * @param guiLabel the gui label
	 * @param label the label
	 * @param testNatureLabel the test nature label
	 */
	public StatusRepresentation(Long entityId, String guiLabel, String label, String testNatureLabel) {
		this.entityId = entityId;
		this.guiLabel = guiLabel;
		this.label = label;
		this.testNatureLabel = testNatureLabel;
	}

	/**
	 * Instantiates a new status representation.
	 * 
	 * @param statusId the status id
	 * @param statuslabel the statuslabel
	 * @param statusGuiLabel the status gui label
	 * @param testId the test id
	 * @param testlabel the testlabel
	 * @param testtype the testtype
	 */
	public StatusRepresentation(Long statusId, String statuslabel, String statusGuiLabel, Long testId, String testlabel, String testtype) {
		this.entityId = statusId;
		this.guiLabel = statusGuiLabel;
		this.label = statuslabel;
		this.setTestNatureId(testId);
		this.setTestLabel(testlabel);
		this.testNatureLabel = testtype;
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
	 * Sets the entity id.
	 * 
	 * @param entityId the new entity id
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Gets the gui label.
	 * 
	 * @return the gui label
	 */
	public String getGuiLabel() {
		return guiLabel;
	}

	/**
	 * Sets the gui label.
	 * 
	 * @param guiLabel the new gui label
	 */
	public void setGuiLabel(String guiLabel) {
		this.guiLabel = guiLabel;
	}

	/**
	 * Gets the label.
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 * 
	 * @param label the new label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets the testrepresentationdata.
	 * 
	 * @return the testrepresentationdata
	 */
	public Set<TestNatureRepresentation> getTestrepresentationdata() {
		return testrepresentationdata;
	}

	/**
	 * Sets the testrepresentationdata.
	 * 
	 * @param testrepresentationdata the new testrepresentationdata
	 */
	public void setTestrepresentationdata(Set<TestNatureRepresentation> testrepresentationdata) {
		this.testrepresentationdata = testrepresentationdata;
	}

	/**
	 * Gets the status nature.
	 * 
	 * @return the status nature
	 */
	public List<StatusNatureRepresentation> getStatusNature() {
		return statusNature;
	}

	/**
	 * Sets the status nature.
	 * 
	 * @param statusNature the new status nature
	 */
	public void setStatusNature(List<StatusNatureRepresentation> statusNature) {
		this.statusNature = statusNature;
	}

	/**
	 * Gets the test nature label.
	 * 
	 * @return the testNatureLabel
	 */
	public String getTestNatureLabel() {
		return testNatureLabel;
	}

	/**
	 * Sets the test nature label.
	 * 
	 * @param testNatureLabel the testNatureLabel to set
	 */
	public void setTestNatureLabel(String testNatureLabel) {
		this.testNatureLabel = testNatureLabel;
	}

	/**
	 * Gets the display label.
	 * 
	 * @return the display label
	 */
	public String getDisplayLabel() {
		return displayLabel;
	}

	/**
	 * Sets the display label.
	 * 
	 * @param displayLabel the new display label
	 */
	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
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
	 * Gets the test label.
	 * 
	 * @return the test label
	 */
	public String getTestLabel() {
		return testLabel;
	}

	/**
	 * Sets the test label.
	 * 
	 * @param testLabel the new test label
	 */
	public void setTestLabel(String testLabel) {
		this.testLabel = testLabel;
	}

	/**
	 * Gets the test nature label old.
	 * 
	 * @return the test nature label old
	 */
	public String getTestNatureLabelOld() {
		return testNatureLabelOld;
	}

	/**
	 * Sets the test nature label old.
	 * 
	 * @param testNatureLabelOld the new test nature label old
	 */
	public void setTestNatureLabelOld(String testNatureLabelOld) {
		this.testNatureLabelOld = testNatureLabelOld;
	}

}
