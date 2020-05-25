/*
 * Creation : Jun 8, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedcoastdown;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.DomainConstants;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.valuedinertia.ValuedInertia;

/**
 * The Class TvvValuedCoastDown.
 */

@Entity
@Table(name = "COPQTVCD")
public class TvvValuedCoastDown extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	protected long entityId;

	/** The computed bench f0. */
	@Column(name = "COMPUTEDBENCHF0")
	private Double computedBenchF0;

	/** The computed bench f1. */
	@Column(name = "COMPUTEDBENCHF1")
	private Double computedBenchF1;

	/** The computed bench f2. */
	@Column(name = "COMPUTEDBENCHF2")
	private Double computedBenchF2;

	/** The theorical bench f0. */
	@Column(name = "THEORICALF0")
	private Double theoricalBenchF0;

	/** The theorical bench f1. */
	@Column(name = "THEORICALF1")
	private Double theoricalBenchF1;

	/** The theorical bench f2. */
	@Column(name = "THEORICALF2")
	private Double theoricalBenchF2;

	/** The road law. */
	@Column(name = "ROADLAW")
	private String roadLaw;
	/** The user bench f0. */
	@Column(name = "USERBENCHF0")
	private Double userBenchF0;

	/** The user bench f1. */
	@Column(name = "USERBENCHF1")
	private Double userBenchF1;

	/** The user bench f2. */
	@Column(name = "USERBENCHF2")
	private Double userBenchF2;

	/** The p sa reference. */
	@Column(name = "PSA_REFERENCE")
	private String pSAReference;

	/** The version. */
	@Column(name = "VERSION")
	private String version;

	/** The inertia. */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "VINERTIA_ID")
	private ValuedInertia inertia;

	/** The tvv obj. */
	@OneToOne(mappedBy = "tvvValuedCoastDown")
	private TVV tvvObj;

	/**
	 * Instantiates a new tvv valued coast down.
	 */
	public TvvValuedCoastDown() {
		super();
	}

	/**
	 * Instantiates a new tvv valued coast down.
	 *
	 * @param tvvValuedCoastDown the tvv valued coast down
	 */
	public TvvValuedCoastDown(TvvValuedCoastDown tvvValuedCoastDown) {
		this.entityId = 0;
		this.version = "1.0";
		this.computedBenchF0 = tvvValuedCoastDown.computedBenchF0;
		this.computedBenchF1 = tvvValuedCoastDown.computedBenchF1;
		this.computedBenchF2 = tvvValuedCoastDown.computedBenchF2;
		this.pSAReference = tvvValuedCoastDown.pSAReference;
		this.roadLaw = tvvValuedCoastDown.roadLaw;
		this.theoricalBenchF0 = tvvValuedCoastDown.theoricalBenchF0;
		this.theoricalBenchF1 = tvvValuedCoastDown.theoricalBenchF1;
		this.theoricalBenchF2 = tvvValuedCoastDown.theoricalBenchF2;
		this.inertia = new ValuedInertia(tvvValuedCoastDown.getInertia());
	}

	/**
	 * {@inheritDoc}
	 * 
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
	public void setEntityId(long entityId) {
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
	public ValuedInertia getInertia() {
		return inertia;
	}

	/**
	 * Sets the inertia.
	 * 
	 * @param inertia the new inertia
	 */
	public void setInertia(ValuedInertia inertia) {
		this.inertia = inertia;
	}

	/**
	 * Gets the tvv obj.
	 *
	 * @return the tvv obj
	 */
	public TVV getTvvObj() {
		return tvvObj;
	}

	/**
	 * Sets the tvv obj.
	 *
	 * @param tvvObj the new tvv obj
	 */
	public void setTvvObj(TVV tvvObj) {
		this.tvvObj = tvvObj;
	}

	/**
	 * to String implementation.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return pSAReference + DomainConstants.VERSION_SEPARATOR + version + DomainConstants.VERSION_SEPARATOR + inertia + DomainConstants.VERSION_SEPARATOR + roadLaw + DomainConstants.VERSION_SEPARATOR + version + DomainConstants.VERSION_SEPARATOR + computedBenchF0 + DomainConstants.VERSION_SEPARATOR + computedBenchF1 + DomainConstants.VERSION_SEPARATOR + computedBenchF2
				+ DomainConstants.VERSION_SEPARATOR + theoricalBenchF0 + DomainConstants.VERSION_SEPARATOR + theoricalBenchF1 + DomainConstants.VERSION_SEPARATOR + theoricalBenchF2 + DomainConstants.VERSION_SEPARATOR + userBenchF0 + DomainConstants.VERSION_SEPARATOR + userBenchF1 + DomainConstants.VERSION_SEPARATOR + userBenchF2;

	}
}
