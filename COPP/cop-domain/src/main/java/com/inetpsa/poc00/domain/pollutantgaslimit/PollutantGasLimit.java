/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.pollutantgaslimit;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.DomainConstants;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;
import com.inetpsa.poc00.domain.pollutantgaslimitmandatory.PollutantGasLmtMandatory;

/**
 * The PollutantGasLimit aggregate root.
 */

@Entity
@Table(name = "COPQTPLT")
public class PollutantGasLimit extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long entityId;

    /** The max d value. */
    @Column(name = "MAXDVALUE")
    private Double maxDValue;

    /** The max d val rule. */
    @Column(name = "MAXDVALUERULE")
    private String maxDValRule;

    /** The min d value. */
    @Column(name = "MINDVALUE")
    private Double minDValue;

    /** The min d val rule. */
    @Column(name = "MINDVALUERULE")
    private String minDValRule;

    /** The pg label. */
    @ManyToOne(cascade = { CascadeType.MERGE })
    @JoinColumn(name = "LABEL_ID", nullable = false, updatable = false, insertable = true)
    private PollutantGasLabel pgLabel;

    /** The pollutant gas limit list. */
    @ManyToOne
    @JoinColumn(name = "PGLIST_ID", nullable = false, updatable = false, insertable = true)
    private PollutantGasLimitList pollutantGasLimitList;

    /** The pollutant gas lmt mandatory. */
    @OneToMany(mappedBy = "pollutantGasLimit", cascade = CascadeType.ALL)
    private List<PollutantGasLmtMandatory> pollutantGasLmtMandatory;

    /** The default value. */
    @Column(name = "MANDATORY_STRING")
    private String mandatoryIdValues;
    
    /** The is deleted. */
   	@Transient
   	private String isDeleted;

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public PollutantGasLimit() {
    	super();
    }

    /**
     * Instantiates a new pollutant gas limit.
     * 
     * @param pollutantGasLabel the pollutant gas label
     * @param maxDValRule the max d val rule
     * @param maxDValue the max d value
     * @param minDValRule the min d val rule
     * @param minDValue the min d value
     */
    public PollutantGasLimit(PollutantGasLabel pollutantGasLabel, String maxDValRule, Double maxDValue, String minDValRule, Double minDValue) {

        this.maxDValRule = maxDValRule;
        this.maxDValue = maxDValue;
        this.minDValRule = minDValRule;
        this.minDValue = minDValue;
        this.pgLabel = pollutantGasLabel;

    }

    public PollutantGasLimit(PollutantGasLimit pollutantGasLimit) {
        this.entityId = pollutantGasLimit.entityId;
        this.maxDValRule = pollutantGasLimit.maxDValRule;
        this.maxDValue = pollutantGasLimit.maxDValue;
        this.minDValRule = pollutantGasLimit.minDValRule;
        this.minDValue = pollutantGasLimit.minDValue;
        this.pgLabel = pollutantGasLimit.pgLabel;
        this.mandatoryIdValues = pollutantGasLimit.mandatoryIdValues;
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
     * Gets the max d value.
     * 
     * @return the max d value
     */
    public Double getMaxDValue() {
        return maxDValue;
    }

    /**
     * Sets the max d value.
     * 
     * @param maxDValue the new max d value
     */
    public void setMaxDValue(Double maxDValue) {
        this.maxDValue = maxDValue;
    }

    /**
     * Gets the max d val rule.
     * 
     * @return the max d val rule
     */
    public String getMaxDValRule() {
        return maxDValRule;
    }

    /**
     * Sets the max d val rule.
     * 
     * @param maxDValRule the new max d val rule
     */
    public void setMaxDValRule(String maxDValRule) {
        this.maxDValRule = maxDValRule;
    }

    /**
     * Gets the min d value.
     * 
     * @return the min d value
     */
    public Double getMinDValue() {
        return minDValue;
    }

    /**
     * Sets the min d value.
     * 
     * @param minDValue the new min d value
     */
    public void setMinDValue(Double minDValue) {
        this.minDValue = minDValue;
    }

    /**
     * Gets the min d val rule.
     * 
     * @return the min d val rule
     */
    public String getMinDValRule() {
        return minDValRule;
    }

    /**
     * Sets the min d val rule.
     * 
     * @param minDValRule the new min d val rule
     */
    public void setMinDValRule(String minDValRule) {
        this.minDValRule = minDValRule;
    }

    /**
     * Gets the pollutant gas limit list.
     * 
     * @return the pollutant gas limit list
     */
    public PollutantGasLimitList getPollutantGasLimitList() {
        return pollutantGasLimitList;
    }

    /**
     * Sets the pollutant gas limit list.
     * 
     * @param pollutantGasLimitList the new pollutant gas limit list
     */
    public void setPollutantGasLimitList(PollutantGasLimitList pollutantGasLimitList) {
        this.pollutantGasLimitList = pollutantGasLimitList;
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
     * Gets the pollutant gas lmt mandatory.
     * 
     * @return the pollutant gas lmt mandatory
     */
    public List<PollutantGasLmtMandatory> getPollutantGasLmtMandatory() {
        return pollutantGasLmtMandatory;
    }

    /**
     * Sets the pollutant gas lmt mandatory.
     * 
     * @param dataList the new pollutant gas lmt mandatory
     */
    public void setPollutantGasLmtMandatory(List<PollutantGasLmtMandatory> dataList) {
        this.pollutantGasLmtMandatory = dataList;
    }

    /**
     * @return the mandatoryIdValues
     */
    public String getMandatoryIdValues() {
        return mandatoryIdValues;
    }

    /**
     * @param mandatoryIdValues the mandatoryIdValues to set
     */
    public void setMandatoryIdValues(String mandatoryIdValues) {
        this.mandatoryIdValues = mandatoryIdValues;
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
        if (pgLabel != null) {
            return pgLabel.getLabel();
        } else {
            return DomainConstants.EMPTY;
        }
    }

}
