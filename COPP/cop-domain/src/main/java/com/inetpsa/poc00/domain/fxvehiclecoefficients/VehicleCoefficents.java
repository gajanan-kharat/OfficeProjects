/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.fxvehiclecoefficients;

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
@Table(name = "COPQTFVC")
public class VehicleCoefficents extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    protected Long entityId;

    /** The label. */
    @Column(name = "LABEL")
    private String label;

    /** The description. */
    @Column(name = "DESCRIPTION")
    private String description;

    /** The f0 coeffiecient. */
    @Column(name = "F0_COEFFICIENT")
    private double f0Coeffiecient;

    /** The f1 coeffiecient. */
    @Column(name = "F1_COEFFICIENT")
    private double f1Coeffiecient;

    /** The f2 coeffiecient. */
    @Column(name = "F2_COEFFICIENT")
    private double f2Coeffiecient;

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public VehicleCoefficents() {
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
     * Gets the f0 coeffiecient.
     * 
     * @return the f0 coeffiecient
     */
    public double getF0Coeffiecient() {
        return f0Coeffiecient;
    }

    /**
     * Sets the f0 coeffiecient.
     * 
     * @param f0Coeffiecient the new f0 coeffiecient
     */
    public void setF0Coeffiecient(double f0Coeffiecient) {
        this.f0Coeffiecient = f0Coeffiecient;
    }

    /**
     * Gets the f1 coeffiecient.
     * 
     * @return the f1 coeffiecient
     */
    public double getF1Coeffiecient() {
        return f1Coeffiecient;
    }

    /**
     * Sets the f1 coeffiecient.
     * 
     * @param f1Coeffiecient the new f1 coeffiecient
     */
    public void setF1Coeffiecient(double f1Coeffiecient) {
        this.f1Coeffiecient = f1Coeffiecient;
    }

    /**
     * Gets the f2 coeffiecient.
     * 
     * @return the f2 coeffiecient
     */
    public double getF2Coeffiecient() {
        return f2Coeffiecient;
    }

    /**
     * Sets the f2 coeffiecient.
     * 
     * @param f2Coeffiecient the new f2 coeffiecient
     */
    public void setF2Coeffiecient(double f2Coeffiecient) {
        this.f2Coeffiecient = f2Coeffiecient;
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
