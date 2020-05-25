/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.factorcoeffLabel;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.factorcoefficient.FactorCoefficents;
import com.inetpsa.poc00.domain.tvvvaluedfactorcoeff.TvvValuedFactorCoefficents;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTFCL")
public class FactorCoefficentsLabel extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long entityId;

    /** The label. */
    @Column(name = "LABEL")
    private String label;

    /** The factor or coeffiecients. */
    @OneToMany
    @JoinColumn(name = "FACTORORCO_ID")
    private List<FactorCoefficents> factorOrCoeffiecients;

    /** The Tvv valued factor or coeffiecients. */
    @OneToMany
    @JoinColumn(name = "FCLABEL_ID")
    private List<TvvValuedFactorCoefficents> tvvValuedFactorOrCoeffiecients;

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public FactorCoefficentsLabel() {
    	super();
    }
    
    /**
     * Instantiates a new factor coefficents label.
     *
     * @param fcLabel the fc label
     */
    public FactorCoefficentsLabel(FactorCoefficentsLabel fcLabel) {
        this.entityId = fcLabel.entityId;
        this.label = fcLabel.label;
    }

    /**
     * Instantiates a new factor coefficents label.
     * 
     * @param label the label
     */
    public FactorCoefficentsLabel(String label) {
        this.label = label;
    }

    /**
     * Instantiates a new factor coefficents label.
     * 
     * @param fcLabelId the fc label_id
     */
    public FactorCoefficentsLabel(long fcLabelId) {
        this.entityId = fcLabelId;
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
     * Gets the factor or coeffiecients.
     * 
     * @return the factor or coeffiecients
     */
    public List<FactorCoefficents> getFactorOrCoeffiecients() {
        return factorOrCoeffiecients;
    }

    /**
     * Sets the factor or coeffiecients.
     * 
     * @param factorOrCoeffiecients the new factor or coeffiecients
     */
    public void setFactorOrCoeffiecients(List<FactorCoefficents> factorOrCoeffiecients) {
        this.factorOrCoeffiecients = factorOrCoeffiecients;
    }

    /**
     * Gets the tvv valued factor or coeffiecients.
     * 
     * @return the tvv valued factor or coeffiecients
     */
    public List<TvvValuedFactorCoefficents> getTvvValuedFactorOrCoeffiecients() {
        return tvvValuedFactorOrCoeffiecients;
    }

    /**
     * Sets the tvv valued factor or coeffiecients.
     * 
     * @param tvvValuedFactorOrCoeffiecients the new tvv valued factor or coeffiecients
     */
    public void setTvvValuedFactorOrCoeffiecients(List<TvvValuedFactorCoefficents> tvvValuedFactorOrCoeffiecients) {
        this.tvvValuedFactorOrCoeffiecients = tvvValuedFactorOrCoeffiecients;
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
