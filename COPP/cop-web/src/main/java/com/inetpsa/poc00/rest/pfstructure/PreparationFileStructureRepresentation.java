/*
 * Creation : Oct 14, 2016
 */
package com.inetpsa.poc00.rest.pfstructure;

import java.util.Date;
import java.util.List;

import com.inetpsa.poc00.rest.genericpreparationchecklist.GenericPreparationCheckListRepresentation;

/**
 * The Class PreparationFileStructureRepresentation.
 */
public class PreparationFileStructureRepresentation {

	/** The entity id. */
	private Long entityId;

	/** The version. */
	private String version;

	/** The modification date. */
	private Date modificationDate;

	/** The preparation checklists. */
	private List<GenericPreparationCheckListRepresentation> preparationChecklists;

	/**
	 * Instantiates a new preparation file structure representation.
	 */
	public PreparationFileStructureRepresentation() {
		// Default Constructor
	}

	/**
	 * Instantiates a new preparation file structure representation.
	 *
	 * @param entityId the entity id
	 * @param version the version
	 * @param modificationDate the modification date
	 */
	public PreparationFileStructureRepresentation(Long entityId, String version, Date modificationDate) {

		this.entityId = entityId;
		this.version = version;
		this.modificationDate = modificationDate;
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
	 * Gets the version.
	 *
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the modification date.
	 *
	 * @return the modification date
	 */
	public Date getModificationDate() {
		return modificationDate;
	}

	/**
	 * Sets the modification date.
	 *
	 * @param modificationDate the new modification date
	 */
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	/**
	 * Gets the preparation checklists.
	 *
	 * @return the preparation checklists
	 */
	public List<GenericPreparationCheckListRepresentation> getPreparationChecklists() {
		return preparationChecklists;
	}

	/**
	 * Sets the preparation checklists.
	 *
	 * @param preparationChecklists the new preparation checklists
	 */
	public void setPreparationChecklists(List<GenericPreparationCheckListRepresentation> preparationChecklists) {
		this.preparationChecklists = preparationChecklists;
	}

}
