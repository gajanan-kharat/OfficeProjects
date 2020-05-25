/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.fueltype;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.engine.Engine;
import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRule;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTFTP")
public class FuelType extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	protected Long entityId;

	/** The fuel type lable. */
	@Column(name = "LABEL")
	private String fuelTypeLable;

	/** The genome tvv rule. */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TVV_RULE_ID")
	private GenomeTVVRule genomeTvvRule;

	/** The engine. */
	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "fuelType", targetEntity = Engine.class)
	private Set<Engine> engine;

	/** The fuel. */
	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "fuelType", targetEntity = Fuel.class)
	private Set<Fuel> fuel;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public FuelType() {
		super();
	}

	/*
	 * (non-Javadoc)
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
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Gets the fuel type lable.
	 * 
	 * @return the fuel type lable
	 */
	public String getFuelTypeLable() {
		return fuelTypeLable;
	}

	/**
	 * Sets the fuel type lable.
	 * 
	 * @param fuelTypeLable the new fuel type lable
	 */
	public void setFuelTypeLable(String fuelTypeLable) {
		this.fuelTypeLable = fuelTypeLable;
	}

	/**
	 * Gets the genome tvv rule.
	 * 
	 * @return the genome tvv rule
	 */
	public GenomeTVVRule getGenomeTvvRule() {
		return genomeTvvRule;
	}

	/**
	 * Sets the genome tvv rule.
	 * 
	 * @param genomeTvvRule the new genome tvv rule
	 */
	public void setGenomeTvvRule(GenomeTVVRule genomeTvvRule) {
		this.genomeTvvRule = genomeTvvRule;
	}

	/**
	 * Gets the engine.
	 * 
	 * @return the engine
	 */
	public Set<Engine> getEngine() {
		return engine;
	}

	/**
	 * Sets the engine.
	 * 
	 * @param engine the new engine
	 */
	public void setEngine(Set<Engine> engine) {
		this.engine = engine;
	}

	/**
	 * Gets the fuel.
	 * 
	 * @return the fuel
	 */
	public Set<Fuel> getFuel() {
		return fuel;
	}

	/**
	 * Sets the fuel.
	 * 
	 * @param fuel the new fuel
	 */
	public void setFuel(Set<Fuel> fuel) {
		this.fuel = fuel;
	}

	/**
	 * to String implementation.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return fuelTypeLable;
	}

}
