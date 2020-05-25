/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.inertia;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.DomainConstants;
import com.inetpsa.poc00.domain.coastdown.CoastDown;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTINA")
public class Inertia extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long entityId;

    /** The max. */
    @Column(name = "MAX")
    private int max;

    /** The min. */
    @Column(name = "MIN")
    private int min;

    /** The value. */
    @Column(name = "VALUE")
    private int value;

    /** The costdown. */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "inertia")
    private Set<CoastDown> costdown;

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public Inertia() {
    	super();
    }
	
    /**
     * Instantiates a new inertia.
     *
     * @param value the value
     */
    public Inertia(int value) {
        this.value = value;
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
     * Gets the max.
     *
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the max.
     *
     * @param max the new max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Gets the min.
     *
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets the min.
     *
     * @param min the new min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * to String implementation.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return DomainConstants.EMPTY + value;
    }
	

}
