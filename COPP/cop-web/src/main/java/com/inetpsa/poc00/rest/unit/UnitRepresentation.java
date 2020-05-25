/*
 * Creation : May 3, 2016
 */
package com.inetpsa.poc00.rest.unit;

import java.util.List;

import com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataRepresentation;

/**
 * The Class UnitRepresentation.
 */
public class UnitRepresentation {

	/** The entity id. */
	private long entityId;

	/** The value. */
	private String value;

	/** The generic technical data. */
	private List<GenericTechnicalDataRepresentation> genericTechnicalData;

	/**
	 * Instantiates a new unit representation.
	 *
	 * @param unitId the unit id
	 * @param unitValue the unit value
	 */
	public UnitRepresentation(long unitId, String unitValue) {
		this.entityId = unitId;
		this.value = unitValue;
	}

	/**
	 * Instantiates a new unit representation.
	 */
	public UnitRepresentation() {
		// Default Constructor
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
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
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
	 * @param genericTechnicalData the new generic technical data
	 */
	public void setGenericTechnicalData(List<GenericTechnicalDataRepresentation> genericTechnicalData) {
		this.genericTechnicalData = genericTechnicalData;
	}
}
