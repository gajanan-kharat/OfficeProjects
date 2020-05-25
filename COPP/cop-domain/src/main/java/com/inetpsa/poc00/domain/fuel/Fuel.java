/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.fuel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.archivebox.ArchiveBox;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.fueltype.FuelType;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;
import com.inetpsa.poc00.domain.tvv.TVV;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTAFL")
public class Fuel extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	protected Long entityId;

	/** The default fuel. */
	@Column(name = "DEFAULT_FUEL")
	private boolean defaultFuel;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The description. */
	@Column(name = "DESCRIPTION")
	private String description;

	/** The emission standards. */
	@ManyToMany(mappedBy = "fuels")
	private Set<EmissionStandard> emissionStandards;

	/** The fuel type. */
	@ManyToOne
	@JoinColumn(name = "FUELTYPE_ID")
	private FuelType fuelType;

	/** The factor coefficent list. */
	@ManyToMany(mappedBy = "fuels")
	private Set<FactorCoefficentList> factorCoefficentList;

	/** The pollutant gas limit list. */
	@ManyToMany(mappedBy = "fuels")
	private Set<PollutantGasLimitList> pollutantGasLimitList;

	/** The tvv list. */
	@OneToMany(mappedBy = "fuel")
	private Set<TVV> tvvList = new HashSet<>();

	/** The archive box list. */
	@OneToMany(mappedBy = "fuel")
	private List<ArchiveBox> archiveBoxList = new ArrayList<>();

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public Fuel() {
		// Default Constructor
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.domain.BaseEntity#getEntityId()
	 */
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
	 * Gets the default fuel.
	 * 
	 * @return the default fuel
	 */
	public boolean getDefaultFuel() {
		return defaultFuel;
	}

	/**
	 * Sets the default fuel.
	 * 
	 * @param defaultFuel the new default fuel
	 */
	public void setDefaultFuel(boolean defaultFuel) {
		this.defaultFuel = defaultFuel;
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
	 * @param factorCoefficentList the new factor coefficent list
	 */
	public void setFactorCoefficentList(Set<FactorCoefficentList> factorCoefficentList) {
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
	 * @param pollutantGasLimitList the new pollutant gas limit list
	 */
	public void setPollutantGasLimitList(Set<PollutantGasLimitList> pollutantGasLimitList) {
		this.pollutantGasLimitList = pollutantGasLimitList;
	}

	/**
	 * Gets the fuel type.
	 * 
	 * @return the fuel type
	 */
	public FuelType getFuelType() {
		return fuelType;
	}

	/**
	 * Sets the fuel type.
	 * 
	 * @param fuelType the new fuel type
	 */
	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
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
	 * to String implementation.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return label;
	}

	/**
	 * Getter archiveBoxList.
	 *
	 * @return the archiveBoxList
	 */
	public List<ArchiveBox> getArchiveBoxList() {
		return archiveBoxList;
	}

	/**
	 * Setter archiveBoxList.
	 *
	 * @param archiveBoxList the archiveBoxList to set
	 */
	public void setArchiveBoxList(List<ArchiveBox> archiveBoxList) {
		this.archiveBoxList = archiveBoxList;
	}
}
