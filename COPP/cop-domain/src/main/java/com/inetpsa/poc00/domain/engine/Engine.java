/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.engine;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjectionType;
import com.inetpsa.poc00.domain.fueltype.FuelType;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRule;
import com.inetpsa.poc00.domain.tvv.TVV;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTENG")
public class Engine extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ENGINE_ID")
    protected Long entityId;

    /** The label derogation. */
    @Column(name = "LABEL_DEROGATION")
    private String labelDerogation;

    /** The power cv. */
    @Column(name = "POWER_CV")
    private String powerCv;

    /** The engine label. */
    @Column(name = "LABEL")
    private String engineLabel;

    /** The power kw. */
    @Column(name = "POWER_KW")
    private String powerKw;

    /** The torque. */
    @Column(name = "TORQUE")
    private String torque;

    /** The genome tvv rule. */
    @OneToOne
    @JoinColumn(name = "TVV_RULE_ID_B0C")
    private GenomeTVVRule genomeTvvRule;

    /** The genome tvv rule DOC. */
    @ManyToOne
    @JoinColumn(name = "TVV_RULE_ID_DOC")
    private GenomeTVVRule genomeTvvRuleDOC;

    /** The fuel injection type. */
    @ManyToOne
    @JoinColumn(name = "FUELINJECTION_ID")
    private FuelInjectionType fuelInjectionType;

    /** The fuel type. */
    @ManyToOne
    @JoinColumn(name = "FUELTYPE_ID")
    private FuelType fuelType;

    /** The tvv list. */
    @OneToMany(mappedBy = "engine")
    private Set<TVV> tvvList = new HashSet<TVV>();

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public Engine() {
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
     * Gets the label derogation.
     *
     * @return the label derogation
     */
    public String getLabelDerogation() {
        return labelDerogation;
    }

    /**
     * Sets the label derogation.
     *
     * @param labelDerogation the new label derogation
     */
    public void setLabelDerogation(String labelDerogation) {
        this.labelDerogation = labelDerogation;
    }

    /**
     * Gets the power cv.
     *
     * @return the power cv
     */
    public String getPowerCv() {
        return powerCv;
    }

    /**
     * Sets the power cv.
     *
     * @param powerCv the new power cv
     */
    public void setPowerCv(String powerCv) {
        this.powerCv = powerCv;
    }

    /**
     * Gets the engine label.
     *
     * @return the engine label
     */
    public String getEngineLabel() {
        return engineLabel;
    }

    /**
     * Sets the engine label.
     *
     * @param engineLabel the new engine label
     */
    public void setEngineLabel(String engineLabel) {
        this.engineLabel = engineLabel;
    }

    /**
     * Gets the power kw.
     *
     * @return the power kw
     */
    public String getPowerKw() {
        return powerKw;
    }

    /**
     * Sets the power kw.
     *
     * @param powerKw the new power kw
     */
    public void setPowerKw(String powerKw) {
        this.powerKw = powerKw;
    }

    /**
     * Gets the torque.
     *
     * @return the torque
     */
    public String getTorque() {
        return torque;
    }

    /**
     * Sets the torque.
     *
     * @param torque the new torque
     */
    public void setTorque(String torque) {
        this.torque = torque;
    }

    /**
     * Gets the genome tvv rule.
     *
     * @return the genome tvv rule
     */
    public GenomeTVVRule getGenomeTvvRule() {
        return genomeTvvRule;
    }

    /**
     * Sets the genome tvv rule.
     *
     * @param genomeTvvRule the new genome tvv rule
     */
    public void setGenomeTvvRule(GenomeTVVRule genomeTvvRule) {
        this.genomeTvvRule = genomeTvvRule;
    }

    /**
     * Gets the fuel injection type.
     *
     * @return the fuel injection type
     */
    public FuelInjectionType getFuelInjectionType() {
        return fuelInjectionType;
    }

    /**
     * Sets the fuel injection type.
     *
     * @param fuelInjectionType the new fuel injection type
     */
    public void setFuelInjectionType(FuelInjectionType fuelInjectionType) {
        this.fuelInjectionType = fuelInjectionType;
    }

    /**
     * Gets the fuel type.
     *
     * @return the fuel type
     */
    public FuelType getFuelType() {
        return fuelType;
    }

    /**
     * Sets the fuel type.
     *
     * @param fuelType the new fuel type
     */
    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    /**
     * Gets the genome tvv rule DOC.
     *
     * @return the genome tvv rule DOC
     */
    public GenomeTVVRule getGenomeTvvRuleDOC() {
        return genomeTvvRuleDOC;
    }

    /**
     * Sets the genome tvv rule DOC.
     *
     * @param genomeTvvRuleDOC the new genome tvv rule DOC
     */
    public void setGenomeTvvRuleDOC(GenomeTVVRule genomeTvvRuleDOC) {
        this.genomeTvvRuleDOC = genomeTvvRuleDOC;
    }

    /**
     * Gets the tvv list.
     *
     * @return the tvv list
     */
    public Set<TVV> getTvvList() {
        return tvvList;
    }

    /**
     * Sets the tvv list.
     *
     * @param tvvList the new tvv list
     */
    public void setTvvList(Set<TVV> tvvList) {
        this.tvvList = tvvList;
    }

    /**
     * to String implementation.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return engineLabel;
    }
}
