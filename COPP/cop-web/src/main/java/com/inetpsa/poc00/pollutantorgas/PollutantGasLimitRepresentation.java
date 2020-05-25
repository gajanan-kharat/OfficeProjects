/*
 * Creation : Apr 20, 2016
 */
package com.inetpsa.poc00.pollutantorgas;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelRepresentation;

/**
 * The Class PollutantGasLimitRepresentation.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PollutantGasLimitRepresentation {

	/** The entity id. */
	protected long entityId;

	/** The max D value. */
	private double maxDValue;

	/** The max D val rule. */
	private String maxDValRule;

	/** The min D value. */
	private double minDValue;

	/** The min D val rule. */
	private String minDValRule;

	/** The is deleted. */
	private String isDeleted;

	/** The pollutant gas limit list. */
	@JsonIgnore
	private PollutantGasLimitListRepresentation pollutantGasLimitList;

	/** The pg label. */
	private PollutantGasLabelRepresentation pgLabel;

	/** The pollutant limit mandatory. */
	private List<PollutantLimitMandatoryRepresentation> pollutantLimitMandatory;

	/**
	 * Instantiates a new pollutant gas limit representation.
	 *
	 * @param entityId the entity id
	 * @param maxDValue the max D value
	 * @param maxDValRule the max D val rule
	 * @param minDValue the min D value
	 * @param minDValRule the min D val rule
	 * @param pgLabel_Id the pg label id
	 * @param pollutantLabel the pollutant label
	 */
	public PollutantGasLimitRepresentation(long entityId, Double maxDValue, String maxDValRule, Double minDValue, String minDValRule, long pgLabel_Id, String pollutantLabel) {
		this.entityId = entityId;
		this.maxDValRule = maxDValRule;
		this.maxDValue = maxDValue;
		this.minDValRule = minDValRule;
		this.minDValue = minDValue;
		this.pgLabel = new PollutantGasLabelRepresentation(pgLabel_Id, pollutantLabel);

	}

	/**
	 * Instantiates a new pollutant gas limit representation.
	 */
	public PollutantGasLimitRepresentation() {
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
	 * Gets the max D value.
	 *
	 * @return the max D value
	 */
	public Double getMaxDValue() {
		return maxDValue;
	}

	/**
	 * Sets the max D value.
	 *
	 * @param maxDValue the new max D value
	 */
	public void setMaxDValue(double maxDValue) {
		this.maxDValue = maxDValue;
	}

	/**
	 * Gets the max D val rule.
	 *
	 * @return the max D val rule
	 */
	public String getMaxDValRule() {
		return maxDValRule;
	}

	/**
	 * Sets the max D val rule.
	 *
	 * @param maxDValRule the new max D val rule
	 */
	public void setMaxDValRule(String maxDValRule) {
		this.maxDValRule = maxDValRule;
	}

	/**
	 * Gets the min D value.
	 *
	 * @return the min D value
	 */
	public Double getMinDValue() {
		return minDValue;
	}

	/**
	 * Sets the min D value.
	 *
	 * @param minDValue the new min D value
	 */
	public void setMinDValue(double minDValue) {
		this.minDValue = minDValue;
	}

	/**
	 * Gets the min D val rule.
	 *
	 * @return the min D val rule
	 */
	public String getMinDValRule() {
		return minDValRule;
	}

	/**
	 * Sets the min D val rule.
	 *
	 * @param minDValRule the new min D val rule
	 */
	public void setMinDValRule(String minDValRule) {
		this.minDValRule = minDValRule;
	}

	/**
	 * Gets the pollutant gas limit list.
	 *
	 * @return the pollutant gas limit list
	 */
	/*	public String getLabel() {
			return label;
		}
	
		public void setLabel(String label) {
			this.label = label;
		}
	*/
	public PollutantGasLimitListRepresentation getPollutantGasLimitList() {
		return pollutantGasLimitList;
	}

	/**
	 * Sets the pollutant gas limit list.
	 *
	 * @param pollutantGasLimitListRepresentation the new pollutant gas limit list
	 */
	public void setPollutantGasLimitList(PollutantGasLimitListRepresentation pollutantGasLimitListRepresentation) {
		this.pollutantGasLimitList = pollutantGasLimitListRepresentation;
	}

	/**
	 * Gets the pg label.
	 *
	 * @return the pg label
	 */
	public PollutantGasLabelRepresentation getPgLabel() {
		return pgLabel;
	}

	/**
	 * Sets the pg label.
	 *
	 * @param pgLabel the new pg label
	 */
	public void setPgLabel(PollutantGasLabelRepresentation pgLabel) {
		this.pgLabel = pgLabel;
	}

	/**
	 * Gets the pollutant limit mandatory.
	 *
	 * @return the pollutant limit mandatory
	 */
	public List<PollutantLimitMandatoryRepresentation> getPollutantLimitMandatory() {
		return pollutantLimitMandatory;
	}

	/**
	 * Sets the pollutant limit mandatory.
	 *
	 * @param pollutantLimitMandatory the new pollutant limit mandatory
	 */
	public void setPollutantLimitMandatory(List<PollutantLimitMandatoryRepresentation> pollutantLimitMandatory) {
		this.pollutantLimitMandatory = pollutantLimitMandatory;
	}

	/**
	 * Gets the checks if is deleted.
	 *
	 * @return the checks if is deleted
	 */
	public String getIsDeleted() {
		return isDeleted;
	}

	/**
	 * Sets the checks if is deleted.
	 *
	 * @param isDeleted the new checks if is deleted
	 */
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "PollutantGasLimitRepresentation [entityId=" + entityId + ", maxDValue=" + maxDValue + ", maxDValRule=" + maxDValRule + ", minDValue=" + minDValue + ", minDValRule=" + minDValRule + ", isDeleted=" + isDeleted + ", pollutantGasLimitList=" + pollutantGasLimitList + ", pgLabel=" + pgLabel + ", pollutantLimitMandatory=" + pollutantLimitMandatory + "]";
	}

}
