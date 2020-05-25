/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.esdependent.tcl;

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

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.DomainConstants;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.generictc.GenericTestCondition;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTECL")
public class EmissionStdDepTCL extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long entityId;

    /** The description. */
    @Column(name = "DESCRIPTION")
    private String description;

    /** The version. */
    @Column(name = "VERSION")
    private String version;

    /** The label. */
    @Column(name = "LABEL")
    private String label;

    /** The generic test condition. */
    @OneToMany(mappedBy = "emsDepTCL", cascade = CascadeType.ALL)
    private List<GenericTestCondition> genericTestCondition;
    
    /** The emission standard. */
    @ManyToOne
    @JoinColumn(name = "EMS_ID")
    private EmissionStandard emissionStandard;
    
    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public EmissionStdDepTCL() {
    	super();
    }
    
    /**
     * Instantiates a new emission std dep TCL.
     *
     * @param label the label
     * @param description the description
     * @param version the version
     */
   public EmissionStdDepTCL(String label, String description, String version) {
        this.label = label;
        this.description = description;
        this.version = version;
    }

    /**
     * Gets the emission standard.
     *
     * @return the emission standard
     */
    public EmissionStandard getEmissionStandard() {
        return emissionStandard;
    }

    /**
     * Sets the emission standard.
     *
     * @param emissionStandard the new emission standard
     */
    public void setEmissionStandard(EmissionStandard emissionStandard) {
        this.emissionStandard = emissionStandard;
    }

    /**
     * Sets the entity id.
     *
     * @param entityId the new entity id
     */
    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

	/* (non-Javadoc)
     * @see org.seedstack.business.domain.BaseEntity#getEntityId()
     */
    @Override
    public Long getEntityId() {
        return entityId;
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
     * Gets the version.
     *
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version the new version
     */
    public void setVersion(String version) {
        this.version = version;
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
     * Gets the generic test condition.
     *
     * @return the generic test condition
     */
    public List<GenericTestCondition> getGenericTestCondition() {
        return genericTestCondition;
    }

    /**
     * Sets the generic test condition.
     *
     * @param genericTestCondition the new generic test condition
     */
    public void setGenericTestCondition(List<GenericTestCondition> genericTestCondition) {
        this.genericTestCondition = genericTestCondition;
    }

    /**
     * to String implementation.
     *
     * @return the string
     */
    @Override
    public String toString() {
		return label+DomainConstants.VERSION_SEPARATOR+version;
    }

}
