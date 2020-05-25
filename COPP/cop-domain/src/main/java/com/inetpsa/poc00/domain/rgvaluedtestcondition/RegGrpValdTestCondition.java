/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.rgvaluedtestcondition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCL;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTRTC")
public class RegGrpValdTestCondition extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	protected Long entityId;

	/** The default value. */
	@Column(name = "DEFAULT_VALUE")
	private String defaultValue;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The data type. */
	@Column(name = "DATA_TYPE")
	private String dataType;
	
	/** The rg valued es dep TCL. */
	@ManyToOne
	@JoinColumn(name = "RGVALUEDTCL_ID")
	private RGValuedESDependentTCL rgValuedEsDepTCL;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public RegGrpValdTestCondition() {
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
	 * Gets the rg valued es dep TCL.
	 *
	 * @return the rg valued es dep TCL
	 */
	public RGValuedESDependentTCL getRgValuedEsDepTCL() {
		return rgValuedEsDepTCL;
	}

	/**
	 * Sets the rg valued es dep TCL.
	 *
	 * @param rgValuedEsDepTCL the new rg valued es dep TCL
	 */
	public void setRgValuedEsDepTCL(RGValuedESDependentTCL rgValuedEsDepTCL) {
		this.rgValuedEsDepTCL = rgValuedEsDepTCL;
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
