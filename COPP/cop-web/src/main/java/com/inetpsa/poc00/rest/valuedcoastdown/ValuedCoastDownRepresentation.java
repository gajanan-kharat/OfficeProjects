/*
 * Creation : Jun 8, 2016
 */
package com.inetpsa.poc00.rest.valuedcoastdown;

import com.inetpsa.poc00.rest.valuedinertia.ValuedInertiaRepresentation;

/**
 * The Class ValuedCoastDownRepresentation.
 */
public class ValuedCoastDownRepresentation {

	/** The p sa reference. */
	private String pSAReference;

	/** The road law. */
	private String roadLaw;

	/** The theorical bench f0. */
	private Double theoricalBenchF0;

	/** The theorical bench f1. */
	private Double theoricalBenchF1;

	/** The theorical bench f2. */
	private Double theoricalBenchF2;

	/** The computed bench f0. */
	private Double computedBenchF0;

	/** The computed bench f1. */
	private Double computedBenchF1;

	/** The computed bench f2. */
	private Double computedBenchF2;

	/** The user bench f0. */
	private Double userBenchF0;

	/** The user bench f1. */
	private Double userBenchF1;

	/** The user bench f2. */
	private Double userBenchF2;

	/** The version. */
	private String version;

	/** The inertia_id. */
	private int inertia_value;

	/** The inertia. */
	private ValuedInertiaRepresentation inertia;

	/** The entity id. */
	private Long entityId;

	/**
	 * Gets the p sa reference.
	 *
	 * @return the p sa reference
	 */
	public String getpSAReference() {
		return pSAReference;
	}

	/**
	 * Sets the p sa reference.
	 *
	 * @param pSAReference the new p sa reference
	 */
	public void setpSAReference(String pSAReference) {
		this.pSAReference = pSAReference;
	}

	/**
	 * Gets the road law.
	 *
	 * @return the road law
	 */
	public String getRoadLaw() {
		return roadLaw;
	}

	/**
	 * Sets the road law.
	 *
	 * @param roadLaw the new road law
	 */
	public void setRoadLaw(String roadLaw) {
		this.roadLaw = roadLaw;
	}

	/**
	 * Gets the theorical bench f0.
	 *
	 * @return the theorical bench f0
	 */
	public Double getTheoricalBenchF0() {
		return theoricalBenchF0;
	}

	/**
	 * Sets the theorical bench f0.
	 *
	 * @param theoricalBenchF0 the new theorical bench f0
	 */
	public void setTheoricalBenchF0(Double theoricalBenchF0) {
		this.theoricalBenchF0 = theoricalBenchF0;
	}

	/**
	 * Gets the theorical bench f1.
	 *
	 * @return the theorical bench f1
	 */
	public Double getTheoricalBenchF1() {
		return theoricalBenchF1;
	}

	/**
	 * Sets the theorical bench f1.
	 *
	 * @param theoricalBenchF1 the new theorical bench f1
	 */
	public void setTheoricalBenchF1(Double theoricalBenchF1) {
		this.theoricalBenchF1 = theoricalBenchF1;
	}

	/**
	 * Gets the theorical bench f2.
	 *
	 * @return the theorical bench f2
	 */
	public Double getTheoricalBenchF2() {
		return theoricalBenchF2;
	}

	/**
	 * Sets the theorical bench f2.
	 *
	 * @param theoricalBenchF2 the new theorical bench f2
	 */
	public void setTheoricalBenchF2(Double theoricalBenchF2) {
		this.theoricalBenchF2 = theoricalBenchF2;
	}

	/**
	 * Gets the computed bench f0.
	 *
	 * @return the computed bench f0
	 */
	public Double getComputedBenchF0() {
		return computedBenchF0;
	}

	/**
	 * Sets the computed bench f0.
	 *
	 * @param computedBenchF0 the new computed bench f0
	 */
	public void setComputedBenchF0(Double computedBenchF0) {
		this.computedBenchF0 = computedBenchF0;
	}

	/**
	 * Gets the computed bench f1.
	 *
	 * @return the computed bench f1
	 */
	public Double getComputedBenchF1() {
		return computedBenchF1;
	}

	/**
	 * Sets the computed bench f1.
	 *
	 * @param computedBenchF1 the new computed bench f1
	 */
	public void setComputedBenchF1(Double computedBenchF1) {
		this.computedBenchF1 = computedBenchF1;
	}

	/**
	 * Gets the computed bench f2.
	 *
	 * @return the computed bench f2
	 */
	public Double getComputedBenchF2() {
		return computedBenchF2;
	}

	/**
	 * Sets the computed bench f2.
	 *
	 * @param computedBenchF2 the new computed bench f2
	 */
	public void setComputedBenchF2(Double computedBenchF2) {
		this.computedBenchF2 = computedBenchF2;
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
	 * Gets the inertia_value.
	 *
	 * @return the inertia_value
	 */
	public int getInertia_value() {
		return inertia_value;
	}

	/**
	 * Sets the inertia_value.
	 *
	 * @param inertia_value the new inertia_value
	 */
	public void setInertia_value(int inertia_value) {
		this.inertia_value = inertia_value;
	}

	/**
	 * Gets the inertia.
	 *
	 * @return the inertia
	 */
	public ValuedInertiaRepresentation getInertia() {
		return inertia;
	}

	/**
	 * Sets the inertia.
	 *
	 * @param valuedInertia the new inertia
	 */
	public void setInertia(ValuedInertiaRepresentation valuedInertia) {
		this.inertia = valuedInertia;
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
	 * Getter userBenchF0
	 * 
	 * @return the userBenchF0
	 */
	public Double getUserBenchF0() {
		return userBenchF0;
	}

	/**
	 * Setter userBenchF0
	 * 
	 * @param userBenchF0 the userBenchF0 to set
	 */
	public void setUserBenchF0(Double userBenchF0) {
		this.userBenchF0 = userBenchF0;
	}

	/**
	 * Getter userBenchF1
	 * 
	 * @return the userBenchF1
	 */
	public Double getUserBenchF1() {
		return userBenchF1;
	}

	/**
	 * Setter userBenchF1
	 * 
	 * @param userBenchF1 the userBenchF1 to set
	 */
	public void setUserBenchF1(Double userBenchF1) {
		this.userBenchF1 = userBenchF1;
	}

	/**
	 * Getter userBenchF2
	 * 
	 * @return the userBenchF2
	 */
	public Double getUserBenchF2() {
		return userBenchF2;
	}

	/**
	 * Setter userBenchF2
	 * 
	 * @param userBenchF2 the userBenchF2 to set
	 */
	public void setUserBenchF2(Double userBenchF2) {
		this.userBenchF2 = userBenchF2;
	}

}
