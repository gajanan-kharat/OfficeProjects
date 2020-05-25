/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedfcl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.emsdepfcl.FactorCoefficentsLabelRepresntation;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelRepresentation;

/**
 * The Class TvvValuedFactorCoefficentsRepresentation.
 */
public class TvvValuedFactorCoefficentsRepresentation {

	/** The entity id. */
	protected long entityId;

	/** The default value. */
	private double defaultValue;

	/** The fc list. */
	@JsonIgnore
	private TvvValuedEsDepFCLRepresentation fcList;

	/** The fc label. */
	private FactorCoefficentsLabelRepresntation fcLabel;

	/** The pg label. */
	private PollutantGasLabelRepresentation pgLabel;

	/** The is deleted. */
	private String isDeleted;

	/**
	 * Instantiates a new tvv valued factor coefficents representation.
	 * 
	 * @param entityId the entity id
	 * @param defaultValue the default value
	 * @param fcLabelId the fc label id
	 * @param pgLabelId the pg label id
	 */
	public TvvValuedFactorCoefficentsRepresentation(long entityId, double defaultValue, long fcLabelId, long pgLabelId) {
		this.entityId = entityId;
		this.defaultValue = defaultValue;
		fcLabel = new FactorCoefficentsLabelRepresntation(fcLabelId);
		pgLabel = new PollutantGasLabelRepresentation(pgLabelId);
	}

	/**
	 * Instantiates a new tvv valued factor coefficents representation.
	 */
	public TvvValuedFactorCoefficentsRepresentation() {
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
	public TvvValuedEsDepFCLRepresentation getFcList() {
		return fcList;
	}

	/**
	 * Sets the fc list.
	 * 
	 * @param fcList the new fc list
	 */
	public void setFcList(TvvValuedEsDepFCLRepresentation fcList) {
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
