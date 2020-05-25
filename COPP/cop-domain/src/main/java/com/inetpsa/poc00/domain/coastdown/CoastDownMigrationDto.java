package com.inetpsa.poc00.domain.coastdown;

import com.inetpsa.poc00.domain.inertia.Inertia;

/**
 * The Class CoastdownRepresentation.
 */
public class CoastDownMigrationDto {

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

	/** The version. */
	private String version;

	/** The latestversion. */
	private Boolean latestversion;

	/** The entity id. */
	private Long entityId;

	/** The inertia. */
	private Long inertiaId;

	/** The inertia. */
	private Integer inertiaValue;

	/** The inertia. */
	private Inertia inertia;

	/**
	 * Instantiates a new coastdown representation.
	 */
	public CoastDownMigrationDto() {
		super();
	}

	/**
	 * Instantiates a new coast down migration dto.
	 * 
	 * @param pSAReference the SA reference
	 * @param roadLaw the road law
	 * @param inertiaValue the inertia value
	 * @param theoricalBenchF0 the theorical bench f0
	 * @param theoricalBenchF1 the theorical bench f1
	 * @param theoricalBenchF2 the theorical bench f2
	 * @param computedBenchF0 the computed bench f0
	 * @param computedBenchF1 the computed bench f1
	 * @param computedBenchF2 the computed bench f2
	 * @param version the version
	 */
	public CoastDownMigrationDto(String pSAReference, String roadLaw, Integer inertiaValue, Double theoricalBenchF0, Double theoricalBenchF1, Double theoricalBenchF2, Double computedBenchF0, Double computedBenchF1, Double computedBenchF2, String version) {
		this.pSAReference = pSAReference;
		this.roadLaw = roadLaw;
		this.theoricalBenchF0 = theoricalBenchF0;
		this.theoricalBenchF1 = theoricalBenchF1;
		this.theoricalBenchF2 = theoricalBenchF2;
		this.computedBenchF0 = computedBenchF0;
		this.computedBenchF1 = computedBenchF1;
		this.computedBenchF2 = computedBenchF2;
		this.version = version;
		this.inertiaValue = inertiaValue;
	}

	/**
	 * Instantiates a new coastdown representation.
	 * 
	 * @param version the version
	 * @param entityId the entity id
	 */
	public CoastDownMigrationDto(String version, Long entityId) {
		this.entityId = entityId;
		this.version = version;
	}

	/**
	 * Instantiates a new coastdown representation.
	 * 
	 * @param entityId the entity id
	 * @param pSAReference the SA reference
	 */
	public CoastDownMigrationDto(Long entityId, String pSAReference) {
		this.entityId = entityId;
		this.pSAReference = pSAReference;
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
	 * Gets the latestversion.
	 * 
	 * @return the latestversion
	 */
	public Boolean getLatestversion() {
		return latestversion;
	}

	/**
	 * Sets the latestversion.
	 * 
	 * @param latestversion the new latestversion
	 */
	public void setLatestversion(Boolean latestversion) {
		this.latestversion = latestversion;
	}

	/**
	 * Gets the inertia.
	 * 
	 * @return the inertia
	 */
	public Inertia getInertia() {
		return inertia;
	}

	/**
	 * Sets the inertia.
	 * 
	 * @param inertia the inertia to set
	 */
	public void setInertia(Inertia inertia) {
		this.inertia = inertia;
	}

	/**
	 * Gets the inertia id.
	 * 
	 * @return the inertiaId
	 */
	public Long getInertiaId() {
		return inertiaId;
	}

	/**
	 * Sets the inertia id.
	 * 
	 * @param inertiaId the inertiaId to set
	 */
	public void setInertiaId(Long inertiaId) {
		this.inertiaId = inertiaId;
	}

	/**
	 * Gets the inertia value.
	 * 
	 * @return the inertiaValue
	 */
	public Integer getInertiaValue() {
		return inertiaValue;
	}

	/**
	 * Sets the inertia value.
	 * 
	 * @param inertiaValue the inertiaValue to set
	 */
	public void setInertiaValue(Integer inertiaValue) {
		this.inertiaValue = inertiaValue;
	}

}
