/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.generictd;

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

import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDL;
import com.inetpsa.poc00.domain.generictechdatamandatory.GenericTechDataMandatory;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTDL;
import com.inetpsa.poc00.domain.unit.Unit;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTGTD")
public class GenericTechnicalData extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long entityId;

    /** The label. */
    @Column(name = "LABEL")
    private String label;

    /** The data type. */
    @Column(name = "DATATYPE")
    private String dataType;

    /** The default value. */
    @Column(name = "DEFAULTVALUE")
    private String defaultValue;
    
    /** The default value. */
    @Column(name = "MANDATORY_STRING")
    private String mandatoryIdValues;
    
    /** The generic technical data manadatory. */
    @OneToMany(mappedBy = "genericTechnicalData", cascade = CascadeType.ALL)
    private List<GenericTechDataMandatory> genericTechnicalDataManadatory;

    /** The tvv dep tdl. */
    @ManyToOne
    @JoinColumn(name = "TECHNICALDATALIST_ID")
    private TvvDepTDL tvvDepTDL;

    /** The ems dep tdl. */
    @ManyToOne
    @JoinColumn(name = "EMSDEPTDL_ID")
    private EmissionStdDepTDL emsDepTDL;

    /** The unit. */
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "UNIT_ID")
    private Unit unit;
    
    /** The is deleted. */
	@Transient
	private String isDeleted;

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public GenericTechnicalData() {
    	super();
    }

    /**
     * Instantiates a new generic technical data.
     * 
     * @param genericTechnicalDataToCopy the generic technical data to copy
     */
    public GenericTechnicalData(GenericTechnicalData genericTechnicalDataToCopy) {
        this.entityId = genericTechnicalDataToCopy.entityId;
        this.label = genericTechnicalDataToCopy.label;
        this.dataType = genericTechnicalDataToCopy.dataType;
        this.defaultValue = genericTechnicalDataToCopy.defaultValue;
        this.unit = new Unit(genericTechnicalDataToCopy.getUnit());
        this.mandatoryIdValues = genericTechnicalDataToCopy.mandatoryIdValues;
    }

    /**
     * Instantiates a new generic technical data.
     * 
     * @param dataType the data type
     * @param defaultValue the default value
     * @param label the label
     * @param unitValue the unit value
     */
    public GenericTechnicalData(String dataType, String defaultValue, String label, String unitValue) {
        this.dataType = dataType;
        this.defaultValue = defaultValue;
        this.label = label;
        this.unit = new Unit(unitValue);

    }

    /**
     * Instantiates a new generic technical data.
     * 
     * @param dataType the data type
     * @param defaultValue the default value
     * @param label the label
     */
    public GenericTechnicalData(String dataType, String defaultValue, String label) {
        this.dataType = dataType;
        this.defaultValue = defaultValue;
        this.label = label;

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
     * Gets the tvv dep tdl.
     * 
     * @return the tvv dep tdl
     */
    public TvvDepTDL getTvvDepTDL() {
        return tvvDepTDL;
    }

    /**
     * Sets the tvv dep tdl.
     * 
     * @param tvvDepTDL the new tvv dep tdl
     */
    public void setTvvDepTDL(TvvDepTDL tvvDepTDL) {
        this.tvvDepTDL = tvvDepTDL;
    }

    /**
     * Gets the ems dep tdl.
     * 
     * @return the ems dep tdl
     */
    public EmissionStdDepTDL getEmsDepTDL() {
        return emsDepTDL;
    }

    /**
     * Sets the ems dep tdl.
     * 
     * @param targetEntity the new ems dep tdl
     */
    public void setEmsDepTDL(EmissionStdDepTDL targetEntity) {
        this.emsDepTDL = targetEntity;
    }

    /**
     * Gets the generic technical data manadatory.
     * 
     * @return the generic technical data manadatory
     */
    public List<GenericTechDataMandatory> getGenericTechnicalDataManadatory() {
        return genericTechnicalDataManadatory;
    }

    /**
     * Sets the generic technical data manadatory.
     * 
     * @param genericTechnicalDataManadatory the new generic technical data manadatory
     */
    public void setGenericTechnicalDataManadatory(List<GenericTechDataMandatory> genericTechnicalDataManadatory) {
        this.genericTechnicalDataManadatory = genericTechnicalDataManadatory;
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
     * @return the mandatoryIdValues
     */
    public String getMandatoryIdValues() {
        return mandatoryIdValues;
    }

    /**
     * @param mandatoryIdValues the mandatoryIdValues to set
     */
    public void setMandatoryIdValues(String mandatoryIdValues) {
        this.mandatoryIdValues = mandatoryIdValues;
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
}
