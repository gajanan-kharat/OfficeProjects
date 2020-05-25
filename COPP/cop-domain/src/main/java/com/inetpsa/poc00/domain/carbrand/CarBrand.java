/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.carbrand;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRule;
import com.inetpsa.poc00.domain.tvv.TVV;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTCMB")
public class CarBrand extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long entityId;

    /** The brand label. */
    @Column(name = "CAR_BRAND_LABEL")
    private String brandLabel;

    /** The maker label. */
    @Column(name = "CAR_MAKER_LABEL")
    private String makerLabel;

    /** The genome tvv rule. */
    @OneToOne
    @JoinColumn(name = "TVV_RULE_ID")
    private GenomeTVVRule genomeTvvRule;

    /** The tvv set. */
    @ManyToMany(mappedBy = "carBrandSet")
    private Set<TVV> tvvSet = new HashSet<TVV>();

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
	public CarBrand() {
		super();
    }

	/* 
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
     * Gets the brand label.
     * 
     * @return the brand label
     */
    public String getBrandLabel() {
        return brandLabel;
    }

    /**
     * Sets the brand label.
     * 
     * @param brandLabel the new brand label
     */
    public void setBrandLabel(String brandLabel) {
        this.brandLabel = brandLabel;
    }

    /**
     * Gets the maker label.
     * 
     * @return the maker label
     */
    public String getMakerLabel() {
        return makerLabel;
    }

    /**
     * Sets the maker label.
     * 
     * @param makerLabel the new maker label
     */
    public void setMakerLabel(String makerLabel) {
        this.makerLabel = makerLabel;
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
     * Gets the tvv set.
     * 
     * @return the tvv set
     */
    public Set<TVV> getTvvSet() {
        return tvvSet;
    }

    /**
     * Sets the tvv set.
     * 
     * @param tvvSet the new tvv set
     */
    public void setTvvSet(Set<TVV> tvvSet) {
        this.tvvSet = tvvSet;
    }

    /**
     * to String implementation.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return makerLabel + " " + brandLabel;
    }
}
