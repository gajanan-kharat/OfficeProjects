/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.fuelinjectiontype;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.engine.Engine;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;
import com.inetpsa.poc00.domain.tvv.TVV;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTFIT")
public class FuelInjectionType extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	protected Long entityId;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The description. */
	@Column(name = "DESCRIPTION")
	private String description;

	/** The emission standards. */
	@ManyToMany(mappedBy = "fuelInjectionTypes")
	private Set<EmissionStandard> emissionStandards;

	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "fuelInjectionType")
	private Set<Engine> engine;

	/** The factor coefficent list. */
	@ManyToMany(mappedBy = "fuelInjectionTypes")
	private Set<FactorCoefficentList> factorCoefficentList;

	@ManyToMany(mappedBy = "fuelInjectionTypes")
	private Set<PollutantGasLimitList> pollutantGasLimitList;

	@OneToMany(mappedBy = "fuelInjectionType")
	private Set<TVV> tvvList;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public FuelInjectionType() {
		// Default Constructor
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
	 * Gets the emission standards.
	 * 
	 * @return the emission standards
	 */
	public Set<EmissionStandard> getEmissionStandards() {
		return emissionStandards;
	}

	/**
	 * Sets the emission standards.
	 * 
	 * @param emissionStandards the new emission standards
	 */
	public void setEmissionStandards(Set<EmissionStandard> emissionStandards) {
		this.emissionStandards = emissionStandards;
	}

	public Set<Engine> getEngine() {
		return engine;
	}

	public void setEngine(Set<Engine> engine) {
		this.engine = engine;
	}

	/**
	 * @return the factorCoefficentList
	 */
	public Set<FactorCoefficentList> getFactorCoefficentList() {
		return factorCoefficentList;
	}

	/**
	 * @param factorCoefficentList the factorCoefficentList to set
	 */
	public void setFactorCoefficentList(Set<FactorCoefficentList> factorCoefficentList) {
		this.factorCoefficentList = factorCoefficentList;
	}

	/**
	 * @return the pollutantGasLimitList
	 */
	public Set<PollutantGasLimitList> getPollutantGasLimitList() {
		return pollutantGasLimitList;
	}

	/**
	 * @param pollutantGasLimitList the pollutantGasLimitList to set
	 */
	public void setPollutantGasLimitList(Set<PollutantGasLimitList> pollutantGasLimitList) {
		this.pollutantGasLimitList = pollutantGasLimitList;
	}

	/**
	 * to String implementation.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return label;
	}

	public Set<TVV> getTvvList() {
		return tvvList;
	}

	public void setTvvList(Set<TVV> tvvList) {
		this.tvvList = tvvList;
	}

}
