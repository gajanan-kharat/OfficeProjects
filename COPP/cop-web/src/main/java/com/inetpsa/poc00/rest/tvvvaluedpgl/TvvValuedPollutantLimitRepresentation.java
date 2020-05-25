/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedpgl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.common.BaseRepresentation;
import com.inetpsa.poc00.pollutantorgas.PollutantGasLimitRepresentation;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelRepresentation;

/**
 * The Class TvvValuedPollutantLimitRepresentation.
 */
public class TvvValuedPollutantLimitRepresentation extends BaseRepresentation {

	/** The entity id. */
	protected long entityId;

	/** The max d value. */
	private double maxDValue;

	/** The max d val rule. */
	private String maxDValRule;

	/** The min d value. */
	private double minDValue;

	/** The min d val rule. */
	private String minDValRule;

	/** The is deleted. */
	private String isDeleted;

	/** The pollutant gas limit list. */
	@JsonIgnore
	private TvvValuedEsDepPGLRepresentation pollutantGasLimitList;

	/** The pg label. */
	private PollutantGasLabelRepresentation pgLabel;

	/** The tvv display value. */
	private String tvvDisplayValue;

	/** The pg limit. */
	private PollutantGasLimitRepresentation pgLimit;

	/**
	 * Instantiates a new tvv valued pollutant limit representation.
	 * 
	 * @param entityId the entity id
	 * @param maxDValue the max d value
	 * @param maxDValRule the max d val rule
	 * @param minDValue the min d value
	 * @param minDValRule the min d val rule
	 * @param pgLabelId the pg label_ id
	 * @param pollutantLabel the pollutant label
	 */
	public TvvValuedPollutantLimitRepresentation(long entityId, Double maxDValue, String maxDValRule, Double minDValue, String minDValRule, long pgLabelId, String pollutantLabel) {
		this.entityId = entityId;
		this.maxDValRule = maxDValRule;
		this.maxDValue = maxDValue;
		this.minDValRule = minDValRule;
		this.minDValue = minDValue;
		this.pgLabel = new PollutantGasLabelRepresentation(pgLabelId, pollutantLabel);
		this.tvvDisplayValue = this.maxDValue + this.maxDValRule + this.minDValue + this.minDValRule;

	}

	/**
	 * Instantiates a new tvv valued pollutant limit representation.
	 */
	public TvvValuedPollutantLimitRepresentation() {
		super();
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
	 * Gets the max d value.
	 * 
	 * @return the max d value
	 */
	public Double getMaxDValue() {
		return maxDValue;
	}

	/**
	 * Sets the max d value.
	 * 
	 * @param maxDValue the new max d value
	 */
	public void setMaxDValue(double maxDValue) {
		this.maxDValue = maxDValue;
	}

	/**
	 * Gets the max d val rule.
	 * 
	 * @return the max d val rule
	 */
	public String getMaxDValRule() {
		return maxDValRule;
	}

	/**
	 * Sets the max d val rule.
	 * 
	 * @param maxDValRule the new max d val rule
	 */
	public void setMaxDValRule(String maxDValRule) {
		this.maxDValRule = maxDValRule;
	}

	/**
	 * Gets the min d value.
	 * 
	 * @return the min d value
	 */
	public Double getMinDValue() {
		return minDValue;
	}

	/**
	 * Sets the min d value.
	 * 
	 * @param minDValue the new min d value
	 */
	public void setMinDValue(double minDValue) {
		this.minDValue = minDValue;
	}

	/**
	 * Gets the min d val rule.
	 * 
	 * @return the min d val rule
	 */
	public String getMinDValRule() {
		return minDValRule;
	}

	/**
	 * Sets the min d val rule.
	 * 
	 * @param minDValRule the new min d val rule
	 */
	public void setMinDValRule(String minDValRule) {
		this.minDValRule = minDValRule;
	}

	/**
	 * Gets the pollutant gas limit list.
	 * 
	 * @return the pollutant gas limit list
	 */
	public TvvValuedEsDepPGLRepresentation getPollutantGasLimitList() {
		return pollutantGasLimitList;
	}

	/**
	 * Sets the pollutant gas limit list.
	 * 
	 * @param pollutantGasLimitListRepresentation the new pollutant gas limit list
	 */
	public void setPollutantGasLimitList(TvvValuedEsDepPGLRepresentation pollutantGasLimitListRepresentation) {
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

	/**
	 * Gets the tvv display value.
	 * 
	 * @return the tvv display value
	 */
	public String getTvvDisplayValue() {
		return tvvDisplayValue;
	}

	/**
	 * Sets the tvv display value.
	 * 
	 * @param tvvDisplayValue the new tvv display value
	 */
	public void setTvvDisplayValue(String tvvDisplayValue) {
		this.tvvDisplayValue = tvvDisplayValue;
	}

	/**
	 * Gets the pg limit.
	 * 
	 * @return the pg limit
	 */
	public PollutantGasLimitRepresentation getPgLimit() {
		return pgLimit;
	}

	/**
	 * Sets the pg limit.
	 * 
	 * @param pgLimit the new pg limit
	 */
	public void setPgLimit(PollutantGasLimitRepresentation pgLimit) {
		this.pgLimit = pgLimit;
	}

}
