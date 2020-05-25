/*
 * Creation : Apr 26, 2016
 */
package com.inetpsa.poc00.emsdepfcl;

/**
 * The Class FactorCoefficentsLabelRepresntation.
 */
public class FactorCoefficentsLabelRepresntation {

	/** The entity id. */
	protected long entityId;

	/** The label. */
	private String label;

	/**
	 * Instantiates a new factor coefficents label represntation.
	 * 
	 * @param fcLabel_id the fc label id
	 */
	public FactorCoefficentsLabelRepresntation(long fcLabelId) {
		this.entityId = fcLabelId;
	}

	/**
	 * Instantiates a new factor coefficents label represntation.
	 */
	public FactorCoefficentsLabelRepresntation() {
		// Default Constructor
	}

	/**
	 * Instantiates a new factor coefficents label represntation.
	 * 
	 * @param fcLabel_id the fc label id
	 * @param factorLabel the factor label
	 */
	public FactorCoefficentsLabelRepresntation(long fcLabelId, String factorLabel) {
		this.entityId = fcLabelId;
		this.label = factorLabel;
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
