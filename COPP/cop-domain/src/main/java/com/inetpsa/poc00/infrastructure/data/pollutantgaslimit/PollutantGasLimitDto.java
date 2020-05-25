/*
 * Creation : Mar 1, 2016
 */
package com.inetpsa.poc00.infrastructure.data.pollutantgaslimit;

/**
 * The Class PollutantGasLimitDto.
 */
public class PollutantGasLimitDto {

	/** The entity id. */
	protected Long entityId;

	/** The max d value. */
	private Double maxDValue;

	/** The max d val rule. */
	private String maxDValRule;

	/** The min d value. */
	private Double minDValue;

	/** The min d val rule. */
	private String minDValRule;

	/** The pg label. */
	private String pgLabel;

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
	public void setMaxDValue(Double maxDValue) {
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
	public void setMinDValue(Double minDValue) {
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
	 * Gets the pg label.
	 * 
	 * @return the pg label
	 */
	public String getPgLabel() {
		return pgLabel;
	}

	/**
	 * Sets the pg label.
	 * 
	 * @param pgLabel the new pg label
	 */
	public void setPgLabel(String pgLabel) {
		this.pgLabel = pgLabel;
	}

}
