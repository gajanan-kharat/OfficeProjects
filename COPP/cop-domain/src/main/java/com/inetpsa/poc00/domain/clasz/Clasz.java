/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.clasz;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.category.Category;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;
import com.inetpsa.poc00.domain.tvv.TVV;

/**
 * The Class Clasz.
 */
@Entity
@Table(name = "COPQTACS")
public class Clasz extends BaseAggregateRoot<Long> {

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

	/** The category set. */
    @ManyToMany(mappedBy = "clasz")
    private Set<Category> categorySet;

    /** The pollutant gas limit list. */
    @ManyToMany(mappedBy = "classes")
    private Set<PollutantGasLimitList> pollutantGasLimitList;

    /** The factor coefficent list. */
    @ManyToMany(mappedBy = "classes")
    private Set<FactorCoefficentList> factorCoefficentList;

    @OneToMany(mappedBy = "clasz")
    private Set<TVV> tvvList;

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public Clasz() {
		super();
    }

    /**
     * Instantiates a new clasz.
     * 
     * @param entityId the entity id
     * @param label the label
     */
    public Clasz(Long entityId, String label) {
        this.entityId = entityId;
        this.label = label;

    }

    /**
     * Instantiates a new clasz.
     * 
     * @param entityId the entity id
     * @param label the label
     * @param description the description
     */
    public Clasz(Long entityId, String label, String description) {
        this.entityId = entityId;
        this.label = label;
        this.description = description;

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
     * Gets the category set.
     * 
     * @return the category set
     */

    /**
     * Gets the pollutant gas limit list.
     * 
     * @return the pollutant gas limit list
     */
    public Set<PollutantGasLimitList> getPollutantGasLimitList() {
        return pollutantGasLimitList;
    }

    public Set<Category> getCategorySet() {
        return categorySet;
    }

    public void setCategorySet(Set<Category> categorySet) {
        this.categorySet = categorySet;
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
     * to String implementation.
     * 
     * @return the string
     */
    @Override
    public String toString() {
        return label;
    }

    public Set<TVV> getTvvList() {
        return tvvList;
    }

    public void setTvvList(Set<TVV> tvvList) {
        this.tvvList = tvvList;
    }

}
