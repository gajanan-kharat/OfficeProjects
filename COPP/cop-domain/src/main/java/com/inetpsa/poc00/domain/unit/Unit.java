/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.unit;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.generictc.GenericTestCondition;
import com.inetpsa.poc00.domain.generictd.GenericTechnicalData;
import com.inetpsa.poc00.domain.specialtestcondition.SpecialTestCondition;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTUNT")
public class Unit extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long entityId;

	/** The value. */
	@Column(name = "VALUE")
	private String value;

	/** The generic technical data. */
	@OneToMany(mappedBy = "unit")
	private List<GenericTechnicalData> genericTechnicalData;

	/** The generic test condition. */
	@OneToMany(mappedBy = "unit")
	private List<GenericTestCondition> genericTestCondition;

	/** The generic test condition. */
	@OneToMany(mappedBy = "unit")
	private List<SpecialTestCondition> specialTestCondition;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public Unit() {
		super();
	}

	/**
	 * Instantiates a new unit.
	 *
	 * @param unitValue the unit value
	 */
	public Unit(String unitValue) {
		this.value = unitValue;
	}

	/**
	 * Instantiates a new unit.
	 *
	 * @param unit the unit
	 */
	public Unit(Unit unit) {
		this.entityId = 0;
		this.value = unit.value;
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
	public List<GenericTechnicalData> getGenericTechnicalData() {
		return genericTechnicalData;
	}

	/**
	 * Sets the generic technical data.
	 *
	 * @param genericTechnicalData the new generic technical data
	 */
	public void setGenericTechnicalData(List<GenericTechnicalData> genericTechnicalData) {
		this.genericTechnicalData = genericTechnicalData;
	}

	/**
	 * Gets the generic test condition.
	 *
	 * @return the generic test condition
	 */
	public List<GenericTestCondition> getGenericTestCondition() {
		return genericTestCondition;
	}

	/**
	 * Sets the generic test condition.
	 *
	 * @param genericTestCondition the new generic test condition
	 */
	public void setGenericTestCondition(List<GenericTestCondition> genericTestCondition) {
		this.genericTestCondition = genericTestCondition;
	}

	/**
	 * to String implementation.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return value;
	}

	/**
	 * Getter specialTestCondition.
	 *
	 * @return the specialTestCondition
	 */
	public List<SpecialTestCondition> getSpecialTestCondition() {
		return specialTestCondition;
	}

	/**
	 * Setter specialTestCondition.
	 *
	 * @param specialTestCondition the specialTestCondition to set
	 */
	public void setSpecialTestCondition(List<SpecialTestCondition> specialTestCondition) {
		this.specialTestCondition = specialTestCondition;
	}
}
