package com.inetpsa.poc00.rest.pollutantgaslabel;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inetpsa.poc00.emsdepfcl.FactorCoefficentsRepresentation;
import com.inetpsa.poc00.pollutantorgas.PollutantGasLimitRepresentation;

/**
 * The Class PollutantGasLabelRepresentation.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PollutantGasLabelRepresentation {

	/** The entity id. */
	Long entityId;

	/** The label. */
	String label;

	/** The edited. */
	private boolean edited;
	/** The factor or coeffiecients. */
	private Set<FactorCoefficentsRepresentation> factorOrCoeffiecients;

	/** The pollutant gas limits. */
	private Set<PollutantGasLimitRepresentation> pollutantGasLimits;

	/**
	 * Instantiates a new pollutant gas label representation.
	 */
	public PollutantGasLabelRepresentation() {
		// Default Constructor
	}

	/**
	 * Instantiates a new pollutant gas label representation.
	 * 
	 * @param entityId the entity id
	 * @param label the label
	 */
	public PollutantGasLabelRepresentation(Long entityId, String label) {
		this.entityId = entityId;
		this.label = label;
	}

	/**
	 * Instantiates a new pollutant gas label representation.
	 * 
	 * @param pgLabel_id the pg label_id
	 */
	public PollutantGasLabelRepresentation(Long pgLabelId) {
		this.entityId = pgLabelId;
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
	 * Gets the factor or coeffiecients.
	 * 
	 * @return the factor or coeffiecients
	 */
	public Set<FactorCoefficentsRepresentation> getFactorOrCoeffiecients() {
		return factorOrCoeffiecients;
	}

	/**
	 * Sets the factor or coeffiecients.
	 * 
	 * @param targetDto the new factor or coeffiecients
	 */
	public void setFactorOrCoeffiecients(Set<FactorCoefficentsRepresentation> targetDto) {
		this.factorOrCoeffiecients = targetDto;
	}

	/**
	 * Gets the pollutant gas limits.
	 * 
	 * @return the pollutant gas limits
	 */
	public Set<PollutantGasLimitRepresentation> getPollutantGasLimits() {
		return pollutantGasLimits;
	}

	/**
	 * Sets the pollutant gas limits.
	 * 
	 * @param pollutantGasLimits the new pollutant gas limits
	 */
	public void setPollutantGasLimits(Set<PollutantGasLimitRepresentation> pollutantGasLimits) {
		this.pollutantGasLimits = pollutantGasLimits;
	}

	/**
	 * Checks if is edited.
	 * 
	 * @return true, if is edited
	 */
	public boolean isEdited() {
		return edited;
	}

	/**
	 * Sets the edited.
	 * 
	 * @param edited the new edited
	 */
	public void setEdited(boolean edited) {
		this.edited = edited;
	}



}
