/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.generictestconditionmandatory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.generictc.GenericTestCondition;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTTCM")
public class GenericTestConditionMandatory extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long entityId;

	/** The value. */
	@Column(name = "VALUE")
	private boolean value;

	/** The generic test condition. */
	@ManyToOne
	@JoinColumn(name = "CONDITION_ID")
	private GenericTestCondition genericTestCondition;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public GenericTestConditionMandatory() {
		super();
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
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public boolean getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value the new value
	 */
	public void setValue(boolean value) {
		this.value = value;
	}

	/**
	 * Gets the generic test condition.
	 * 
	 * @return the generic test condition
	 */
	public GenericTestCondition getGenericTestCondition() {
		return genericTestCondition;
	}

	/**
	 * Sets the generic test condition.
	 * 
	 * @param genericTestCondition the new generic test condition
	 */
	public void setGenericTestCondition(GenericTestCondition genericTestCondition) {
		this.genericTestCondition = genericTestCondition;
	}

}
