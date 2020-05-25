/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.coastdown;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.DomainConstants;
import com.inetpsa.poc00.domain.inertia.Inertia;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTCTD")
public class CoastDown extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	protected Long entityId;

	/** The p sa reference. */
	@Column(name = "PSA_REFERENCE")
	private String pSAReference;

	/** The road law. */
	@Column(name = "ROAD_LAW")
	private String roadLaw;

	/** The inertia. */
	@ManyToOne
	@JoinColumn(name = "INERTIA_ID")
	private Inertia inertia;

	/** The computed bench f0. */
	@Column(name = "COMPUTED_BENCH_F0")
	private Double computedBenchF0;

	/** The computed bench f1. */
	@Column(name = "COMPUTED_BENCH_F1")
	private Double computedBenchF1;

	/** The computed bench f2. */
	@Column(name = "COMPUTED_BENCH_F2")
	private Double computedBenchF2;

	/** The theorical bench f0. */
	@Column(name = "THEORICAL_BENCH_F0")
	private Double theoricalBenchF0;

	/** The theorical bench f1. */
	@Column(name = "THEORICAL_BENCH_F1")
	private Double theoricalBenchF1;

	/** The theorical bench f2. */
	@Column(name = "THEORICAL_BENCH_F2")
	private Double theoricalBenchF2;

	/** The version. */
	@Column(name = "VERSION")
	private String version;

	/** The user bench f0. */
	@Column(name = "USER_BENCH_F0")
	private Double userBenchF0;

	/** The user bench f1. */
	@Column(name = "USER_BENCH_F1")
	private Double userBenchF1;

	/** The user bench f2. */
	@Column(name = "USER_BENCH_F2")
	private Double userBenchF2;

	/** The latestversion. */
	@Column(name = "LATESTVERSION")
	private Boolean latestversion;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public CoastDown() {
		super();
	}

	/**
	 * Instantiates a new coast down.
	 * 
	 * @param version the version
	 * @param entityId the entity id
	 */
	public CoastDown(String version, Long entityId) {

		this.version = version;
		this.entityId = entityId;
	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.domain.BaseEntity#getEntityId()
	 */
	@Override
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
	 * Gets the user bench f0.
	 * 
	 * @return the user bench f0
	 */
	public Double getUserBenchF0() {
		return userBenchF0;
	}

	/**
	 * Sets the user bench f0.
	 * 
	 * @param userBenchF0 the new user bench f0
	 */
	public void setUserBenchF0(Double userBenchF0) {
		this.userBenchF0 = userBenchF0;
	}

	/**
	 * Gets the user bench f1.
	 * 
	 * @return the user bench f1
	 */
	public Double getUserBenchF1() {
		return userBenchF1;
	}

	/**
	 * Sets the user bench f1.
	 * 
	 * @param userBenchF1 the new user bench f1
	 */
	public void setUserBenchF1(Double userBenchF1) {
		this.userBenchF1 = userBenchF1;
	}

	/**
	 * Gets the user bench f2.
	 * 
	 * @return the user bench f2
	 */
	public Double getUserBenchF2() {
		return userBenchF2;
	}

	/**
	 * Sets the user bench f2.
	 * 
	 * @param userBenchF2 the new user bench f2
	 */
	public void setUserBenchF2(Double userBenchF2) {
		this.userBenchF2 = userBenchF2;
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
	 * @param inertia the new inertia
	 */
	public void setInertia(Inertia inertia) {
		this.inertia = inertia;
	}

	/**
	 * Checks if is latestversion.
	 * 
	 * @return the boolean
	 */
	public Boolean isLatestversion() {
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
	 * Gets the latestversion.
	 * 
	 * @return the latestversion
	 */
	public Boolean getLatestversion() {
		return latestversion;
	}
	
	/**
	 * to String implementation.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return pSAReference + DomainConstants.VERSION_SEPARATOR + version + DomainConstants.VERSION_SEPARATOR + inertia;
	}
	
}
