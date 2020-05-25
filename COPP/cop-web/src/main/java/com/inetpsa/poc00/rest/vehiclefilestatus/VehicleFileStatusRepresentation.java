/*
 * Creation : Oct 10, 2016
 */
package com.inetpsa.poc00.rest.vehiclefilestatus;

/**
 * The Class VehicleFileStatusRepresentation.
 */
public class VehicleFileStatusRepresentation {

	/** The entity id. */
	private Long entityId;

	/** The label. */
	private String label;

	/** The gui label. */
	private String guiLabel;

	/**
	 * Instantiates a new vehicle file status representation.
	 */
	public VehicleFileStatusRepresentation() {
		// Default Constructor
	}

	/**
	 * Instantiates a new vehicle file status representation.
	 *
	 * @param entityId the entity id
	 * @param label the label
	 * @param guiLabel the gui label
	 */
	public VehicleFileStatusRepresentation(Long entityId, String label, String guiLabel) {
		this.entityId = entityId;
		this.label = label;
		this.guiLabel = guiLabel;
	}

	/**
	 * Getter entityId.
	 *
	 * @return the entityId
	 */
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Setter entityId.
	 *
	 * @param entityId the entityId to set
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Getter label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Setter label.
	 *
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Getter guiLabel.
	 *
	 * @return the guiLabel
	 */
	public String getGuiLabel() {
		return guiLabel;
	}

	/**
	 * Setter guiLabel.
	 *
	 * @param guiLabel the guiLabel to set
	 */
	public void setGuiLabel(String guiLabel) {
		this.guiLabel = guiLabel;
	}
}
