/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.factorcoefficient;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.DomainConstants;
import com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoefficentsLabel;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;

/**
 * The FactorCoefficents aggregate root.
 */

@Entity
@Table(name = "COPQTFCT")
public class FactorCoefficents extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long entityId;

    /** The default value. */
    @Column(name = "DEFAULTVALUE")
    private double defaultValue;

    /** The fc list. */
    @ManyToOne
    @JoinColumn(name = "FCLIST_ID")
    private FactorCoefficentList fcList;

    /** The fc label. */
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinColumn(name = "FACTORORCO_ID")
    private FactorCoefficentsLabel fcLabel;

    /** The pg label. */
    @ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinColumn(name = "PORGASLABEL_ID")
    private PollutantGasLabel pgLabel;
    
	/** The is deleted. */
	@Transient
	private String isDeleted;

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public FactorCoefficents() {
    	super();
    }

    /**
     * Instantiates a new factor coefficents.
     * 
     * @param defaultValue the default value
     * @param fcLabel the fc label
     * @param pollutantGasLabel the pollutant gas label
     */
    public FactorCoefficents(double defaultValue, FactorCoefficentsLabel fcLabel, PollutantGasLabel pollutantGasLabel) {
        this.defaultValue = defaultValue;
        this.fcLabel = new FactorCoefficentsLabel(fcLabel.getLabel());
        this.pgLabel = pollutantGasLabel;
    }

    /**
     * Instantiates a new factor coefficents.
     * 
     * @param defaultValue the default value
     * @param pgLabel the pg label
     */
    public FactorCoefficents(double defaultValue, PollutantGasLabel pgLabel) {
        this.defaultValue = defaultValue;

        this.pgLabel = pgLabel;
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
     * Gets the default value.
     * 
     * @return the default value
     */
    public double getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the default value.
     * 
     * @param defaultValue the new default value
     */
    public void setDefaultValue(double defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Gets the fc list.
     * 
     * @return the fc list
     */
    public FactorCoefficentList getFcList() {
        return fcList;
    }

    /**
     * Sets the fc list.
     * 
     * @param fcList the new fc list
     */
    public void setFcList(FactorCoefficentList fcList) {
        this.fcList = fcList;
    }

    /**
     * Gets the fclabel.
     * 
     * @return the fclabel
     */
    public FactorCoefficentsLabel getFclabel() {
        return fcLabel;
    }

    /**
     * Sets the fclabel.
     * 
     * @param fclabel the new fclabel
     */
    public void setFclabel(FactorCoefficentsLabel fclabel) {
        this.fcLabel = fclabel;
    }

    /**
     * Gets the pg label.
     * 
     * @return the pg label
     */
    public PollutantGasLabel getPgLabel() {
        return pgLabel;
    }

    /**
     * Sets the pg label.
     * 
     * @param pgLabel the new pg label
     */
    public void setPgLabel(PollutantGasLabel pgLabel) {
        this.pgLabel = pgLabel;
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
	
	/**
     * to String implementation.
     *
     * @return the string
     */
    @Override
    public String toString() {
		if(fcLabel != null){
			return fcLabel.getLabel();	
		}else{
			return DomainConstants.EMPTY;	
        }
		
    }
}
