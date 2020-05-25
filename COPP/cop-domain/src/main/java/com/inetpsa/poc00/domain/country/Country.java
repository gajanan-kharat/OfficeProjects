/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.country;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.vehicle.Vehicle;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTCTY")
public class Country extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	protected Long entityId;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The tvv set. */
	@ManyToMany(mappedBy = "countrySet")
	private Set<TVV> tvvSet = new HashSet<>();

	/** The vehicle. */
	@OneToMany
	private List<Vehicle> vehicle = new ArrayList<>();

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public Country() {
		super();
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
	 * to String implementation.
	 * 
	 * @return the string
	 */
	@Override
	public String toString() {
		return label;
	}

	/**
	 * Gets the tvv set.
	 *
	 * @return the tvv set
	 */
	public Set<TVV> getTvvSet() {
		return tvvSet;
	}

	/**
	 * Sets the tvv set.
	 *
	 * @param tvvSet the new tvv set
	 */
	public void setTvvSet(Set<TVV> tvvSet) {
		this.tvvSet = tvvSet;
	}

	/**
	 * Gets the vehicle.
	 *
	 * @return the vehicle
	 */
	public List<Vehicle> getVehicle() {
		return vehicle;
	}

	/**
	 * Sets the vehicle.
	 *
	 * @param vehicle the new vehicle
	 */
	public void setVehicle(List<Vehicle> vehicle) {
		this.vehicle = vehicle;
	}

}
