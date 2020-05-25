/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.category;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.clasz.Clasz;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;
import com.inetpsa.poc00.domain.tvv.TVV;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTACT")
public class Category extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	protected Long entityId;

	/** The description. */
	@Column(name = "DESCRIPTION")
	private String description;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The emission standards. */
	@ManyToMany(mappedBy = "categories")
	private Set<EmissionStandard> emissionStandards;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "COPQTMCC", joinColumns = { @JoinColumn(name = "CATEGORY_ID") }, inverseJoinColumns = { @JoinColumn(name = "CLASZ_ID") })
	private Set<Clasz> clasz = new HashSet<Clasz>();

	/** The factor coefficent list. */
	@ManyToMany(mappedBy = "categories")
	private Set<FactorCoefficentList> factorCoefficentList;

	/** The pollutant gas limit list. */
	@ManyToMany(mappedBy = "categories")
	private Set<PollutantGasLimitList> pollutantGasLimitList;

	@OneToMany(mappedBy = "category")
	private Set<TVV> tvvList = new HashSet<TVV>();

	/**
	 * Constructor are in visibility package because only Factories can create
	 * aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public Category() {
		super();
	}

	/**
	 * Instantiates a new category.
	 * 
	 * @param entityId
	 *            the entity id
	 * @param claszId
	 *            the clasz_id
	 * @param label
	 *            the label
	 * @param description
	 *            the description
	 */

	Category(Long entityId, Long claszId, String label, String description) {
		this.entityId = entityId;
		this.label = label;
		this.description = description;
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
	 * @param entityId
	 *            the new entity id
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
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
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @param label
	 *            the new label
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
	 * @param emissionStandards
	 *            the new emission standards
	 */
	public void setEmissionStandards(Set<EmissionStandard> emissionStandards) {
		this.emissionStandards = emissionStandards;
	}

	/**
	 * Gets the tvv list.
	 *
	 * @return the tvv list
	 */
	public Set<TVV> getTvvList() {
		return tvvList;
	}

	/**
	 * Sets the tvv list.
	 *
	 * @param tvvList the new tvv list
	 */
	public void setTvvList(Set<TVV> tvvList) {
		this.tvvList = tvvList;
	}

	/**
	 * Gets the factor coefficent list.
	 * 
	 * @return the factor coefficent list
	 */
	public Set<FactorCoefficentList> getFactorCoefficentList() {
		return factorCoefficentList;
	}

	/**
	 * Sets the factor coefficent list.
	 * 
	 * @param factorCoefficentList
	 *            the new factor coefficent list
	 */
	public void setFactorCoefficentList(
			Set<FactorCoefficentList> factorCoefficentList) {
		this.factorCoefficentList = factorCoefficentList;
	}

	/**
	 * Gets the pollutant gas limit list.
	 * 
	 * @return the pollutant gas limit list
	 */
	public Set<PollutantGasLimitList> getPollutantGasLimitList() {
		return pollutantGasLimitList;
	}

	/**
	 * Sets the pollutant gas limit list.
	 * 
	 * @param pollutantGasLimitList
	 *            the new pollutant gas limit list
	 */
	public void setPollutantGasLimitList(
			Set<PollutantGasLimitList> pollutantGasLimitList) {
		this.pollutantGasLimitList = pollutantGasLimitList;
	}

	/**
	 * Gets the clasz.
	 *
	 * @return the clasz
	 */
	public Set<Clasz> getClasz() {
		return clasz;
	}

	/**
	 * Sets the clasz.
	 *
	 * @param clasz the new clasz
	 */
	public void setClasz(Set<Clasz> clasz) {
		this.clasz = clasz;
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

}
