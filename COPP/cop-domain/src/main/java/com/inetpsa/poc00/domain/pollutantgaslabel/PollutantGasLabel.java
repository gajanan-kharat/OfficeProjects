/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.pollutantgaslabel;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.factorcoefficient.FactorCoefficents;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit;
import com.inetpsa.poc00.domain.statisticalparameters.StatisticalCalculationParameters;
import com.inetpsa.poc00.domain.tvvvaluedfactorcoeff.TvvValuedFactorCoefficents;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTPGL")
public class PollutantGasLabel extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long entityId;

    /** The label. */
    @Column(name = "LABEL")
    private String label;

    /** The factor or coeffiecients. */
    @OneToMany(mappedBy = "pgLabel")
    private List<FactorCoefficents> factorOrCoeffiecients;

    /** The tvv valued factor coefficents. */
    @OneToMany(mappedBy = "pgLabel")
    private List<TvvValuedFactorCoefficents> tvvValuedFactorCoefficents;

    /** The pollutant gas limits. */
    @OneToMany(mappedBy = "pgLabel")
    private List<PollutantGasLimit> pollutantGasLimits;

    /** The pollutant gas limits. */
    @OneToMany(mappedBy = "pollutantGasLabel")
    private List<StatisticalCalculationParameters> statisticalCalculationParameters;

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public PollutantGasLabel() {
    	super();
    }

    /**
     * Instantiates a new pollutant gas label.
     *
     * @param pgLabelId the pg label id
     */
    public PollutantGasLabel(long pgLabelId) {
        this.entityId = pgLabelId;
    }

    /**
     * Instantiates a new pollutant gas label.
     * 
     * @param label the label
     */
    public PollutantGasLabel(String label) {
        this.label = label;
    }

    /**
     * Instantiates a new pollutant gas label.
     *
     * @param pgLabel the pg label
     */
    public PollutantGasLabel(PollutantGasLabel pgLabel) {
        this.entityId = pgLabel.entityId;
        this.label = pgLabel.label;
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
     * Gets the pollutant gas limits.
     * 
     * @return the pollutant gas limits
     */
    public List<PollutantGasLimit> getPollutantGasLimits() {
        return pollutantGasLimits;
    }

    /**
     * Sets the pollutant gas limits.
     * 
     * @param pollutantGasLimits the new pollutant gas limits
     */
    public void setPollutantGasLimits(List<PollutantGasLimit> pollutantGasLimits) {
        this.pollutantGasLimits = pollutantGasLimits;
    }

    /**
     * Gets the tvv valued factor coefficents.
     * 
     * @return the tvv valued factor coefficents
     */
    public List<TvvValuedFactorCoefficents> getTvvValuedFactorCoefficents() {
        return tvvValuedFactorCoefficents;
    }

    /**
     * Sets the tvv valued factor coefficents.
     * 
     * @param tvvValuedFactorCoefficents the new tvv valued factor coefficents
     */
    public void setTvvValuedFactorCoefficents(List<TvvValuedFactorCoefficents> tvvValuedFactorCoefficents) {
        this.tvvValuedFactorCoefficents = tvvValuedFactorCoefficents;
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
     * Gets the statistical calculation parameters.
     *
     * @return the statistical calculation parameters
     */
    public List<StatisticalCalculationParameters> getStatisticalCalculationParameters() {
        return statisticalCalculationParameters;
    }

    /**
     * Sets the statistical calculation parameters.
     *
     * @param statisticalCalculationParameters the new statistical calculation parameters
     */
    public void setStatisticalCalculationParameters(List<StatisticalCalculationParameters> statisticalCalculationParameters) {
        this.statisticalCalculationParameters = statisticalCalculationParameters;
    }

}
