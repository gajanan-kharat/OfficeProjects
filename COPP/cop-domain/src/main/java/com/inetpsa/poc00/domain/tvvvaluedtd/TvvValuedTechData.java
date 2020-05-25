/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.tvvvaluedtd;

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

import com.inetpsa.poc00.domain.generictd.GenericTechnicalData;
import com.inetpsa.poc00.domain.tvvvaluedesdeptdl.TvvValuedEsDepTDL;
import com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTvvDepTDL;
import com.inetpsa.poc00.domain.unit.Unit;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTVTD")
public class TvvValuedTechData extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long entityId;

    /** The data type. */
    @Column(name = "DATATYPE")
    private String dataType;

    /** The value. */
    @Column(name = "VALUE")
    private String value;

    /** The label. */
    @Column(name = "LABEL")
    private String label;

    /** The tvv valued TDL. */
    @ManyToOne
    @JoinColumn(name = "VALUED_DATALIST_ID")
    private TvvValuedTvvDepTDL tvvValuedTDL;

    /** The tvv valued es TDL. */
    @ManyToOne
    @JoinColumn(name = "ESDEPENDENT_DATALIST_ID")
    private TvvValuedEsDepTDL tvvValuedEsTDL;

    /** The unit. */
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "UNIT_ID")
    private Unit unit;
    
    /** The generic data. */
    @OneToOne
    @JoinColumn(name = "GENERIC_DATA_ID")
    private GenericTechnicalData genericData;

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public TvvValuedTechData() {
    	super();
    }

    /**
     * Instantiates a new tvv valued tech data.
     * 
     * @param dataObject the data object
     */
    TvvValuedTechData(TvvValuedTechData dataObject) {
        this.entityId = 0;
        this.label = dataObject.label;
        this.value = dataObject.value;
        this.dataType = dataObject.dataType;
        this.unit = new Unit(dataObject.getUnit());
        if (dataObject.getGenericData() != null)
            this.genericData = new GenericTechnicalData(dataObject.getGenericData());
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
     * Gets the tvv dep TDL.
     * 
     * @return the tvv dep TDL
     */
    public TvvValuedTvvDepTDL getTvvDepTDL() {
        return tvvValuedTDL;
    }

    /**
     * Sets the tvv dep TDL.
     * 
     * @param tvvDepTDL the new tvv dep TDL
     */
    public void setTvvDepTDL(TvvValuedTvvDepTDL tvvDepTDL) {
        this.tvvValuedTDL = tvvDepTDL;
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
     * Gets the tvv valued es TDL.
     * 
     * @return the tvv valued es TDL
     */
    public TvvValuedEsDepTDL getTvvValuedEsTDL() {
        return tvvValuedEsTDL;
    }

    /**
     * Sets the tvv valued es TDL.
     * 
     * @param tvvValuedEsTDL the new tvv valued es TDL
     */
    public void setTvvValuedEsTDL(TvvValuedEsDepTDL tvvValuedEsTDL) {
        this.tvvValuedEsTDL = tvvValuedEsTDL;
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
     * Gets the generic data.
     *
     * @return the generic data
     */
    public GenericTechnicalData getGenericData() {
        return genericData;
    }

    /**
     * Sets the generic data.
     *
     * @param genericData the new generic data
     */
    public void setGenericData(GenericTechnicalData genericData) {
        this.genericData = genericData;
    }
}
