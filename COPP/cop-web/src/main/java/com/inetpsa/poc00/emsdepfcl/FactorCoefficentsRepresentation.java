package com.inetpsa.poc00.emsdepfcl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelRepresentation;

/**
 * The Class FactorCoefficentsRepresentation.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FactorCoefficentsRepresentation {

	/** The entity id. */
	protected long entityId;

	/** The default value. */
	private double defaultValue;

	/** The fc list. */
	@JsonIgnore
	private FactorCoefficentListRepresentation fcList;

	/** The fc label. */
	private FactorCoefficentsLabelRepresntation fcLabel;

	/** The pg label. */
	private PollutantGasLabelRepresentation pgLabel;

	/** The is deleted. */
	private String isDeleted;

	/**
	 * Instantiates a new factor coefficents representation.
	 * 
	 * @param entityId the entity id
	 * @param defaultValue the default value
	 * @param fcLabelId the fc label id
	 * @param pglabelId the pg label id
	 */
	public FactorCoefficentsRepresentation(long entityId, double defaultValue, long fcLabelId, long pglabelId) {
		this.entityId = entityId;
		this.defaultValue = defaultValue;
		fcLabel = new FactorCoefficentsLabelRepresntation(fcLabelId);
		pgLabel = new PollutantGasLabelRepresentation(pglabelId);
	}

	/**
	 * Instantiates a new factor coefficents representation.
	 * 
	 * @param entityId the entity id
	 * @param defaultValue the default value
	 * @param fcLabelId the fc label id
	 * @param factorLabel the factor label
	 * @param pgLabelId the pg label id
	 * @param pollutantLabel the pollutant label
	 */
	public FactorCoefficentsRepresentation(long entityId, double defaultValue, long fcLabelId, String factorLabel, long pgLabelId, String pollutantLabel) {
		this.entityId = entityId;
		this.defaultValue = defaultValue;
		fcLabel = new FactorCoefficentsLabelRepresntation(fcLabelId, factorLabel);
		pgLabel = new PollutantGasLabelRepresentation(pgLabelId, pollutantLabel);
	}

	/**
	 * Instantiates a new factor coefficents representation.
	 */
	public FactorCoefficentsRepresentation() {
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
	 * Gets the default value.
	 * 
	 * @return the default value
	 */
	public double getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Sets the default value.
	 * 
	 * @param defaultValue the new default value
	 */
	public void setDefaultValue(double defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Gets the fc list.
	 * 
	 * @return the fc list
	 */
	public FactorCoefficentListRepresentation getFcList() {
		return fcList;
	}

	/**
	 * Sets the fc list.
	 * 
	 * @param fcList the new fc list
	 */
	public void setFcList(FactorCoefficentListRepresentation fcList) {
		this.fcList = fcList;
	}

	/**
	 * Gets the fc label.
	 * 
	 * @return the fc label
	 */
	public FactorCoefficentsLabelRepresntation getFcLabel() {
		return fcLabel;
	}

	/**
	 * Sets the fc label.
	 * 
	 * @param fcLabel the new fc label
	 */
	public void setFcLabel(FactorCoefficentsLabelRepresntation fcLabel) {
		this.fcLabel = fcLabel;
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

}
