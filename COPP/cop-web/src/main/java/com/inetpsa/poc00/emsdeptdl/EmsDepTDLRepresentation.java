/*
 * Creation : Apr 12, 2016
 */
package com.inetpsa.poc00.emsdeptdl;

import java.util.List;

import com.inetpsa.poc00.common.BaseRepresentation;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataRepresentation;

/**
 * The Class EmsDepTDLRepresentation.
 */
public class EmsDepTDLRepresentation extends BaseRepresentation {

	/** The entity id. */
	protected Long entityId;

	/** The label. */
	private String label;

	/** The description. */
	private String description;

	/** The version. */
	private String version;

	/** The generic technical data. */
	private List<GenericTechnicalDataRepresentation> genericTechnicalData;

	/** The emission standard. */
	private EmissionStandardRepresentation emissionStandard;

	/**
	 * Instantiates a new ems dep tdl representation.
	 */
	public EmsDepTDLRepresentation() {
		// Default Constructor
	}

	/**
	 * Instantiates a new ems dep tdl representation.
	 * 
	 * @param entityId the entity id
	 * @param description the description
	 * @param version the version
	 * @param label the label
	 */
	public EmsDepTDLRepresentation(long entityId, String description, String version, String label) {
		this.entityId = entityId;
		this.description = description;
		this.version = version;
		this.label = label;

	}

	/**
	 * Instantiates a new ems dep tdl representation.
	 * 
	 * @param entityId the entity id
	 * @param description the description
	 * @param version the version
	 * @param label the label
	 * @param emsId the ems id
	 */
	public EmsDepTDLRepresentation(long entityId, String description, String version, String label, long emsId) {
		this.entityId = entityId;
		this.description = description;
		this.version = version;
		this.label = label;
		this.emissionStandard = new EmissionStandardRepresentation(emsId);

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
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * Gets the generic technical data.
	 * 
	 * @return the generic technical data
	 */
	public List<GenericTechnicalDataRepresentation> getGenericTechnicalData() {
		return genericTechnicalData;
	}

	/**
	 * Sets the generic technical data.
	 * 
	 * @param dataList the new generic technical data
	 */
	public void setGenericTechnicalData(List<GenericTechnicalDataRepresentation> dataList) {
		this.genericTechnicalData = dataList;
	}

	/**
	 * Gets the emission standard.
	 * 
	 * @return the emission standard
	 */
	public EmissionStandardRepresentation getEmissionStandard() {
		return emissionStandard;
	}

	/**
	 * Sets the emission standard.
	 * 
	 * @param emissionStandard the new emission standard
	 */
	public void setEmissionStandard(EmissionStandardRepresentation emissionStandard) {
		this.emissionStandard = emissionStandard;
	}

}
