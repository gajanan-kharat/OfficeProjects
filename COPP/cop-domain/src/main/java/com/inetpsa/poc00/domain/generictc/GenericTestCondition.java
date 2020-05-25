/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.generictc;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL;
import com.inetpsa.poc00.domain.generictestconditionmandatory.GenericTestConditionMandatory;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTCL;
import com.inetpsa.poc00.domain.unit.Unit;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTGTC")
public class GenericTestCondition extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long entityId;

	/** The data type. */
	@Column(name = "DATATYPE")
	private String dataType;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The default value. */
	@Column(name = "DEFAULTVALUE")
	private String defaultValue;

	/** The tvv dep tcl. */
	@ManyToOne
	@JoinColumn(name = "TESTCONDITIONLIST_ID")
	private TvvDepTCL tvvDepTCL;

	/** The ems dep tcl. */
	@ManyToOne
	@JoinColumn(name = "EMSDEPTCL_ID")
	private EmissionStdDepTCL emsDepTCL;

	/** The gneric test condition mandatory. */
	@OneToMany(mappedBy = "genericTestCondition", cascade = CascadeType.ALL)
	private List<GenericTestConditionMandatory> gnericTestConditionMandatory;

	/** The default value. */
	@Column(name = "MANDATORY_STRING")
	private String mandatoryIdValues;

	/** The unit. */
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "UNIT_ID")
	private Unit unit;

	/** The is deleted. */
	@Transient
	private String isDeleted;

	/**
	 * 
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public GenericTestCondition() {
		super();
	}

	/**
	 * Instantiates a new generic test condition.
	 * 
	 * @param dataType the data type
	 * @param defaultValue the default value
	 * @param label the label
	 */
	public GenericTestCondition(String dataType, String defaultValue, String label) {
		this.dataType = dataType;
		this.defaultValue = defaultValue;
		this.label = label;
	}

	/**
	 * Instantiates a new generic test condition.
	 * 
	 * @param dataType the data type
	 * @param defaultValue the default value
	 * @param label the label
	 * @param unitValue the unit value
	 */
	public GenericTestCondition(String dataType, String defaultValue, String label, String unitValue) {
		this.dataType = dataType;
		this.defaultValue = defaultValue;
		this.label = label;
		this.unit = new Unit(unitValue);
	}

	/**
	 * Instantiates a new generic test condition.
	 * 
	 * @param genericCondition the generic condition
	 */
	public GenericTestCondition(GenericTestCondition genericCondition) {
		this.entityId = genericCondition.entityId;
		this.label = genericCondition.label;
		this.dataType = genericCondition.dataType;
		this.defaultValue = genericCondition.defaultValue;
		this.unit = new Unit(genericCondition.getUnit());
		this.mandatoryIdValues = genericCondition.mandatoryIdValues;

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
	 * Gets the tvv dep tcl.
	 * 
	 * @return the tvv dep tcl
	 */
	public TvvDepTCL getTvvDepTCL() {
		return tvvDepTCL;
	}

	/**
	 * Sets the tvv dep tcl.
	 * 
	 * @param tvvDepTCL the new tvv dep tcl
	 */
	public void setTvvDepTCL(TvvDepTCL tvvDepTCL) {
		this.tvvDepTCL = tvvDepTCL;
	}

	/**
	 * Gets the ems dep tcl.
	 * 
	 * @return the ems dep tcl
	 */
	public EmissionStdDepTCL getEmsDepTCL() {
		return emsDepTCL;
	}

	/**
	 * Sets the ems dep tcl.
	 * 
	 * @param targetEntity the new ems dep tcl
	 */
	public void setEmsDepTCL(EmissionStdDepTCL targetEntity) {
		this.emsDepTCL = targetEntity;
	}

	/**
	 * Gets the gneric test condition mandatory.
	 * 
	 * @return the gneric test condition mandatory
	 */
	public List<GenericTestConditionMandatory> getGnericTestConditionMandatory() {
		return gnericTestConditionMandatory;
	}

	/**
	 * Sets the gneric test condition mandatory.
	 * 
	 * @param gnericTestConditionMandatory the new gneric test condition mandatory
	 */
	public void setGnericTestConditionMandatory(List<GenericTestConditionMandatory> gnericTestConditionMandatory) {
		this.gnericTestConditionMandatory = gnericTestConditionMandatory;
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
	 * Gets the mandatory id values.
	 * 
	 * @return the mandatoryIdValues
	 */
	public String getMandatoryIdValues() {
		return mandatoryIdValues;
	}

	/**
	 * Sets the mandatory id values.
	 * 
	 * @param mandatoryIdValues the mandatoryIdValues to set
	 */
	public void setMandatoryIdValues(String mandatoryIdValues) {
		this.mandatoryIdValues = mandatoryIdValues;
	}

	/**
	 * Gets the checks if is deleted.
	 * 
	 * @return the checks if is deleted
	 */
	public String getIsDeleted() {
		return isDeleted;
	}

	/**
	 * Sets the checks if is deleted.
	 * 
	 * @param isDeleted the new checks if is deleted
	 */
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
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
