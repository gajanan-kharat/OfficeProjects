/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;
import com.inetpsa.poc00.domain.testnature.TestNature;
import com.inetpsa.poc00.domain.tvv.TVV;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTAST")
public class Status extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATUS_ID")
    protected Long entityId;

    /** The gui label. */
    @Column(name = "GUI_LABEL")
    private String guiLabel;

    /** The label. */
    @Column(name = "LABEL")
    private String label;

    /** The tvv. */
    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    private List<TVV> tvv = new ArrayList<TVV>();

    /** The regulation group list. */
    @OneToMany(mappedBy = "regulationgroupstatus", cascade = CascadeType.ALL)
    private List<RegulationGroup> regulationGroupList = new ArrayList<RegulationGroup>();

    /** The technical group list. */
    @OneToMany(mappedBy = "techgroupstatus", cascade = CascadeType.ALL)
    private List<TechnicalGroup> technicalGroupList = new ArrayList<TechnicalGroup>();

    /** The es list. */
    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    private List<EmissionStandard> esList = new ArrayList<EmissionStandard>();
    
    /** The test natures. */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "COPQTMSN", joinColumns = { @JoinColumn(name = "STATUS_ID") }, inverseJoinColumns = { @JoinColumn(name = "TEST_ID") })
    private Set<TestNature> testNatures = new HashSet<TestNature>();

    /**
     * Instantiates a new status.
     */
    public Status() {
    	super();
    }

    /**
     * Instantiates a new status.
     * 
     * @param entityId the entity id
     * @param guiLabel the gui label
     * @param label the label
     */
    public Status(Long entityId, String guiLabel, String label) {
        this.entityId = entityId;
        this.guiLabel = guiLabel;
        this.label = label;
    }

    /**
     * Instantiates a new status.
     * 
     * @param statusId the status id
     */
    Status(long statusId) {
        this.entityId = statusId;
    }

    /**
     * Instantiates a new status.
     * 
     * @param label the label
     */
    public Status(String label) {
        this.label = label;
    }

	/* (non-Javadoc)
     * @see org.seedstack.business.domain.BaseEntity#getEntityId()
     */
    @Override
    public Long getEntityId() {
        return entityId;
    }

    /**
     * Gets the test natures.
     * 
     * @return the testNatures
     */
    public Set<TestNature> getTestNatures() {
        return testNatures;
    }

    /**
     * Sets the test natures.
     * 
     * @param testNatures the testNatures to set
     */
    public void setTestNatures(Set<TestNature> testNatures) {
        this.testNatures = testNatures;
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
     * Gets the gui label.
     * 
     * @return the gui label
     */
    public String getGuiLabel() {
        return guiLabel;
    }

    /**
     * Sets the gui label.
     * 
     * @param guiLabel the new gui label
     */
    public void setGuiLabel(String guiLabel) {
        this.guiLabel = guiLabel;
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

    /*	*//**
           * Gets the regulation group.
           * 
           * @return the regulationGroup
           */

    @Override
    public String toString() {
        return label;
    }

    /**
     * Gets the tvv.
     * 
     * @return the tvv
     */
    public List<TVV> getTvv() {
        return tvv;
    }

    /**
     * Sets the tvv.
     * 
     * @param tvv the new tvv
     */
    public void setTvv(List<TVV> tvv) {
        this.tvv = tvv;
    }

    /**
     * Gets the regulation group list.
     * 
     * @return the regulation group list
     */
    public List<RegulationGroup> getRegulationGroupList() {
        return regulationGroupList;
    }

    /**
     * Sets the regulation group list.
     * 
     * @param regulationGroupList the new regulation group list
     */
    public void setRegulationGroupList(List<RegulationGroup> regulationGroupList) {
        this.regulationGroupList = regulationGroupList;
    }

    /**
     * Gets the technical group list.
     * 
     * @return the technical group list
     */
    public List<TechnicalGroup> getTechnicalGroupList() {
        return technicalGroupList;
    }

    /**
     * Sets the technical group list.
     * 
     * @param technicalGroupList the new technical group list
     */
    public void setTechnicalGroupList(List<TechnicalGroup> technicalGroupList) {
        this.technicalGroupList = technicalGroupList;
    }

    /**
     * Gets the es list.
     * 
     * @return the es list
     */
    public List<EmissionStandard> getEsList() {
        return esList;
    }

    /**
     * Sets the es list.
     * 
     * @param esList the new es list
     */
    public void setEsList(List<EmissionStandard> esList) {
        this.esList = esList;
    }

}
