/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.finalreductionratio;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRule;
import com.inetpsa.poc00.domain.tvv.TVV;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTFRR")
public class FinalReductionRatio extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long entityId;

    /** The label. */
    @Column(name = "LABEL")
    private String label;

    /** The genome tvv rule. */
    @OneToOne
    @JoinColumn(name = "TVV_RULE_ID")
    private GenomeTVVRule genomeTvvRule;

    /** The tvv list. */
    @OneToMany(mappedBy = "finalReductionRatio")
    private Set<TVV> tvvList = new HashSet<TVV>();

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public FinalReductionRatio() {
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
        return label;
    }
}
