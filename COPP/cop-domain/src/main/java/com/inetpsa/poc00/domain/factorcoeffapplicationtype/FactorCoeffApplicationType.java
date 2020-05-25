/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.factorcoeffapplicationtype;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

/**
 * The Class FactorCoeffApplicationType.
 */
@Entity
@Table(name = "COPQTFAT")
public class FactorCoeffApplicationType extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    protected Long entityId;

    /** The label. */
    @Column(name = "LABEL")
    private String label;

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public FactorCoeffApplicationType() {
    	super();
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
     * to String implementation.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return label;
    }

}
