/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.tvvvaluedtc;

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

import com.inetpsa.poc00.domain.generictc.GenericTestCondition;
import com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCL;
import com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTvvDepTCL;
import com.inetpsa.poc00.domain.unit.Unit;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTVTC")
public class TvvValuedTestCondition extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long entityId;

	/** The data type. */
	@Column(name = "DATATYPE")
	private String dataType;

	/** The default value. */
	@Column(name = "DEFAULTVALUE")
	private String defaultValue;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The tvv valued TCL. */
	@ManyToOne
	@JoinColumn(name = "VALUED_CONDITIONLIST_ID")
	private TvvValuedTvvDepTCL tvvValuedTCL;

	/** The tvv valued es TCL. */
	@ManyToOne
	@JoinColumn(name = "ESDEPENDENT_CONDITIONLIST_ID")
	private TvvValuedEsDepTCL tvvValuedEsTCL;

	/** The unit. */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "UNIT_ID")
	private Unit unit;

	/** The generic condition. */
	@OneToOne
	@JoinColumn(name = "GENERIC_CONDITION_ID")
	private GenericTestCondition genericCondition;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public TvvValuedTestCondition() {
		super();
	}

	/**
	 * Instantiates a new tvv valued test condition.
	 * 
	 * @param tvvValuedTestCondition the tvv valued test condition
	 */
	public TvvValuedTestCondition(TvvValuedTestCondition tvvValuedTestCondition) {
		this.entityId = 0;
		this.label = tvvValuedTestCondition.label;
		this.dataType = tvvValuedTestCondition.dataType;
		this.defaultValue = tvvValuedTestCondition.defaultValue;
		this.unit = new Unit(tvvValuedTestCondition.getUnit());
		if (tvvValuedTestCondition.getGenericCondition() != null) {
			this.genericCondition = new GenericTestCondition(tvvValuedTestCondition.getGenericCondition());
		}

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
	 * Gets the data type.
	 * 
	 * @return the data type
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * Sets the data type.
	 * 
	 * @param dataType the new data type
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * Gets the default value.
	 * 
	 * @return the default value
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Sets the default value.
	 * 
	 * @param defaultValue the new default value
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
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
	 * Gets the tvv valued TCL.
	 * 
	 * @return the tvv valued TCL
	 */
	public TvvValuedTvvDepTCL getTvvValuedTCL() {
		return tvvValuedTCL;
	}

	/**
	 * Sets the tvv valued TCL.
	 * 
	 * @param tvvValuedTCL the new tvv valued TCL
	 */
	public void setTvvValuedTCL(TvvValuedTvvDepTCL tvvValuedTCL) {
		this.tvvValuedTCL = tvvValuedTCL;
	}

	/**
	 * Gets the unit.
	 * 
	 * @return the unit
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * Sets the unit.
	 * 
	 * @param unit the new unit
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * Gets the tvv valued es TCL.
	 * 
	 * @return the tvv valued es TCL
	 */
	public TvvValuedEsDepTCL getTvvValuedEsTCL() {
		return tvvValuedEsTCL;
	}

	/**
	 * Sets the tvv valued es TCL.
	 * 
	 * @param tvvValuedEsTCL the new tvv valued es TCL
	 */
	public void setTvvValuedEsTCL(TvvValuedEsDepTCL tvvValuedEsTCL) {
		this.tvvValuedEsTCL = tvvValuedEsTCL;
	}

	/**
	 * Gets the generic condition.
	 *
	 * @return the generic condition
	 */
	public GenericTestCondition getGenericCondition() {
		return genericCondition;
	}

	/**
	 * Sets the generic condition.
	 *
	 * @param genericCondition the new generic condition
	 */
	public void setGenericCondition(GenericTestCondition genericCondition) {
		this.genericCondition = genericCondition;
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
