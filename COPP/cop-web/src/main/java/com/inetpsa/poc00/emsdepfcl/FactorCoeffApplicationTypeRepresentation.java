/*
 * Creation : Apr 15, 2016
 */
package com.inetpsa.poc00.emsdepfcl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class FactorCoeffApplicationTypeRepresentation.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FactorCoeffApplicationTypeRepresentation {

	/** The entity id. */
	protected long entityId;

	/** The label. */
	private String label;

	/**
	 * Instantiates a new factor coeff application type representation.
	 */
	public FactorCoeffApplicationTypeRepresentation() {
		super();
	}

	/**
	 * Instantiates a new factor coeff application type representation.
	 * 
	 * @param entityId the entity id
	 * @param label the label
	 */
	public FactorCoeffApplicationTypeRepresentation(long entityId, String label) {
		this.entityId = entityId;
		this.label = label;
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

}
