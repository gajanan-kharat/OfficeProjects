/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.pollutantgaslimitmandatory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTPLM")
public class PollutantGasLmtMandatory extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long entityId;

	/** The value. */
	@Column(name = "VALUE")
	private boolean value;

	/** The pollutant gas limit. */
	@ManyToOne
	@JoinColumn(name = "PORGASLIMIT_ID")
	PollutantGasLimit pollutantGasLimit;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public PollutantGasLmtMandatory() {
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
	 * Gets the pollutant gas limit.
	 * 
	 * @return the pollutant gas limit
	 */
	public PollutantGasLimit getPollutantGasLimit() {
		return pollutantGasLimit;
	}

	/**
	 * Sets the pollutant gas limit.
	 * 
	 * @param pollutantGasLimit the new pollutant gas limit
	 */
	public void setPollutantGasLimit(PollutantGasLimit pollutantGasLimit) {
		this.pollutantGasLimit = pollutantGasLimit;
	}

}
