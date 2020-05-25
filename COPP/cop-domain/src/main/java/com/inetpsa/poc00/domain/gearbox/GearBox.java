/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.gearbox;

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

import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRule;
import com.inetpsa.poc00.domain.tvv.TVV;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTGBX")
public class GearBox extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GEAR_BOX_ID")
    protected Long entityId;

    /** The label. */
    @Column(name = "LABEL")
    private String label;

    /** The type. */
    @Column(name = "TYPE")
    private String type;

    /** The genome tvv rule dbm. */
    @OneToOne
    @JoinColumn(name = "TVV_RULE_ID_DBM")
    private GenomeTVVRule genomeTvvRuleDBM;
    
    /** The genome tvv rule b0 g. */
    @ManyToOne
    @JoinColumn(name = "TVV_RULE_ID_B0G")
    private GenomeTVVRule genomeTvvRuleB0G;

    /** The tvv list. */
    @OneToMany(mappedBy = "gearBox")
    private Set<TVV> tvvList = new HashSet<TVV>();

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public GearBox() {
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
     * Gets the type.
     * 
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     * 
     * @param type the new type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the genome tvv rule dbm.
     * 
     * @return the genome tvv rule dbm
     */
    public GenomeTVVRule getGenomeTvvRuleDBM() {
        return genomeTvvRuleDBM;
    }

    /**
     * Sets the genome tvv rule dbm.
     * 
     * @param genomeTvvRuleDBM the new genome tvv rule dbm
     */
    public void setGenomeTvvRuleDBM(GenomeTVVRule genomeTvvRuleDBM) {
        this.genomeTvvRuleDBM = genomeTvvRuleDBM;
    }

    /**
     * Gets the genome tvv rule b0 g.
     * 
     * @return the genome tvv rule b0 g
     */
    public GenomeTVVRule getGenomeTvvRuleB0G() {
        return genomeTvvRuleB0G;
    }

    /**
     * Sets the genome tvv rule b0 g.
     * 
     * @param genomeTvvRuleB0G the new genome tvv rule b0 g
     */
    public void setGenomeTvvRuleB0G(GenomeTVVRule genomeTvvRuleB0G) {
        this.genomeTvvRuleB0G = genomeTvvRuleB0G;
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
