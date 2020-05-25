/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.vehicletechnology;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTAVT")
public class VehicleTechnology extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    protected Long entityId;

    /** The description. */
    @Column(name = "DESCRIPTION")
    private String description;

    /** The label. */
    @Column(name = "LABEL")
    private String label;

    /** The emission standards. */
    @ManyToMany(mappedBy = "vehicleTechnologySet")
    private Set<EmissionStandard> emissionStandards;

    /** The factor coefficent list. */
    @ManyToMany(mappedBy = "vehicleTechnologySet")
    private Set<FactorCoefficentList> factorCoefficentList;

    /** The pollutant gas limit list. */
    @ManyToMany(mappedBy = "vehicleTechnologySet")
    private Set<PollutantGasLimitList> pollutantGasLimitList;

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */

    public VehicleTechnology() {
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
     * Gets the emission standards.
     * 
     * @return the emission standards
     */
    public Set<EmissionStandard> getEmissionStandards() {
        return emissionStandards;
    }

    /**
     * Sets the emission standards.
     * 
     * @param emissionStandards the new emission standards
     */
    public void setEmissionStandards(Set<EmissionStandard> emissionStandards) {
        this.emissionStandards = emissionStandards;
    }

    /**
     * Gets the factor coefficent list.
     * 
     * @return the factor coefficent list
     */
    public Set<FactorCoefficentList> getFactorCoefficentList() {
        return factorCoefficentList;
    }

    /**
     * Sets the factor coefficent list.
     * 
     * @param factorCoefficentList the new factor coefficent list
     */
    public void setFactorCoefficentList(Set<FactorCoefficentList> factorCoefficentList) {
        this.factorCoefficentList = factorCoefficentList;
    }

    /**
     * Gets the pollutant gas limit list.
     * 
     * @return the pollutant gas limit list
     */
    public Set<PollutantGasLimitList> getPollutantGasLimitList() {
        return pollutantGasLimitList;
    }

    /**
     * Sets the pollutant gas limit list.
     * 
     * @param pollutantGasLimitList the new pollutant gas limit list
     */
    public void setPollutantGasLimitList(Set<PollutantGasLimitList> pollutantGasLimitList) {
        this.pollutantGasLimitList = pollutantGasLimitList;
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
