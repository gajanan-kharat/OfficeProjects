/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.generictechdatamandatory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.generictd.GenericTechnicalData;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTTDM")
public class GenericTechDataMandatory extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long entityId;

    /** The value. */
    @Column(name = "VALUE")
    private Boolean value = false;

    /** The generic technical data. */
    @ManyToOne
    @JoinColumn(name = "DATA_ID")
    private GenericTechnicalData genericTechnicalData;

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public GenericTechDataMandatory() {
    	super();
    }

    /*
     * (non-Javadoc)
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
    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public Boolean getValue() {
        return value;
    }

    /**
     * Sets the value.
     * 
     * @param value the new value
     */
    public void setValue(Boolean value) {
        this.value = value;
    }

    /**
     * Gets the generic technical data.
     * 
     * @return the generic technical data
     */
    public GenericTechnicalData getGenericTechnicalData() {
        return genericTechnicalData;
    }

    /**
     * Sets the generic technical data.
     * 
     * @param genericTechnicalData the new generic technical data
     */
    public void setGenericTechnicalData(GenericTechnicalData genericTechnicalData) {
        this.genericTechnicalData = genericTechnicalData;
    }

}
