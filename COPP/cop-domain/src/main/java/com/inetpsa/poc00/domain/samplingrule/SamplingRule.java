/**
 * samplingRule * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.samplingrule;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTSRL")
public class SamplingRule extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	protected Long entityId;

	/** The amt or percent. */
	@Column(name = "AMOUNT_OR_PERCENTAGE")
	private String amtOrPercent;

	/** The amt type. */
	@Column(name = "AMOUNT_TYPE")
	private String amtType;

	/** The description. */
	@Column(name = "DESCRIPTION")
	private String description;

	/** The frequency. */
	@Column(name = "FREQUENCY")
	private Integer frequency;

	/** The higher limit. */
	@Column(name = "HIGHER_LIMIT")
	private Integer higherLimit;

	/** The higher symbol. */
	@Column(name = "HIGHER_SYMBOL")
	private String higherSymbol;

	/** The lower limit. */
	@Column(name = "LOWER_LIMIT")
	private Integer lowerLimit;

	/** The lower symbol. */
	@Column(name = "LOWER_SYMBOL")
	private String lowerSymbol;

	/** The needed amt. */
	@Column(name = "NEEDED_AMOUNT")
	private Double neededAmt;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The version. */
	@Column(name = "VERSION")
	private String version;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public SamplingRule() {
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
	 * Gets the amt or percent.
	 * 
	 * @return the amt or percent
	 */
	public String getAmtOrPercent() {
		return amtOrPercent;
	}

	/**
	 * Sets the amt or percent.
	 * 
	 * @param amtOrPercent the new amt or percent
	 */
	public void setAmtOrPercent(String amtOrPercent) {
		this.amtOrPercent = amtOrPercent;
	}

	/**
	 * Gets the amt type.
	 * 
	 * @return the amt type
	 */
	public String getAmtType() {
		return amtType;
	}

	/**
	 * Sets the amt type.
	 * 
	 * @param amtType the new amt type
	 */
	public void setAmtType(String amtType) {
		this.amtType = amtType;
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
	 * Gets the frequency.
	 * 
	 * @return the frequency
	 */
	public Integer getFrequency() {
		return frequency;
	}

	/**
	 * Sets the frequency.
	 * 
	 * @param frequency the new frequency
	 */
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	/**
	 * Gets the higher symbol.
	 * 
	 * @return the higher symbol
	 */
	public String getHigherSymbol() {
		return higherSymbol;
	}

	/**
	 * Sets the higher symbol.
	 * 
	 * @param higherSymbol the new higher symbol
	 */
	public void setHigherSymbol(String higherSymbol) {
		this.higherSymbol = higherSymbol;
	}

	/**
	 * Gets the higher limit.
	 * 
	 * @return the higher limit
	 */
	public Integer getHigherLimit() {
		return higherLimit;
	}

	/**
	 * Sets the higher limit.
	 * 
	 * @param higherLimit the new higher limit
	 */
	public void setHigherLimit(Integer higherLimit) {
		this.higherLimit = higherLimit;
	}

	/**
	 * Gets the lower limit.
	 * 
	 * @return the lower limit
	 */
	public Integer getLowerLimit() {
		return lowerLimit;
	}

	/**
	 * Sets the lower limit.
	 * 
	 * @param lowerLimit the new lower limit
	 */
	public void setLowerLimit(Integer lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	/**
	 * Gets the lower symbol.
	 * 
	 * @return the lower symbol
	 */
	public String getLowerSymbol() {
		return lowerSymbol;
	}

	/**
	 * Sets the lower symbol.
	 * 
	 * @param lowerSymbol the new lower symbol
	 */
	public void setLowerSymbol(String lowerSymbol) {
		this.lowerSymbol = lowerSymbol;
	}

	/**
	 * Gets the needed amt.
	 * 
	 * @return the needed amt
	 */
	public Double getNeededAmt() {
		return neededAmt;
	}

	/**
	 * Sets the needed amt.
	 * 
	 * @param neededAmt the new needed amt
	 */
	public void setNeededAmt(Double neededAmt) {
		this.neededAmt = neededAmt;
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
	 * to String implementation.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return label ;
	}
}
