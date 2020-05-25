/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.genomelcdvdictionary;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.genomelcdvcode.GenomeLCDVCode;

/**
 * Entity Class : GenomeLCDVDictionary table Name : COPQTGDY.
 */
@Entity
@Table(name = "COPQTGDY")
public class GenomeLCDVDictionary extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LCDV_DICTIONARY_ID")
    protected Long entityId;

    /** The genome lcdv code list. */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "genomeLCDVDictionary")
    private List<GenomeLCDVCode> genomeLcdvCodeList = new ArrayList<GenomeLCDVCode>();

    /** The fr label. */
    @Column(name = "FR_LABEL")
    private String frLabel;

    /** The dictionary value. */
    @Column(name = "DICTIONARY_VALUE")
    private String dictionaryValue;

    /** The kmat. */
    @Column(name = "KMAT")
    private String kmat;

    /** The creation date. */
    @Column(name = "CREATION_DATE")
    private Date creationDate = new Date();

    /** The update date. */
    @Column(name = "UPDATE_DATE")
    private Date updateDate = new Date();

    /**
     * Modify the last Update Date for all existing Row while updating.
     */
    @PreUpdate
    public void setLastUpdate() {
        this.updateDate = new Date();
    }

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities. Factories are in the same package so he can
     * access package visibility.
     */
    public GenomeLCDVDictionary() {
    	super();
    }

    /*
     * (non-Javadoc)
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
     * Gets the creation date.
     *
     * @return the creation date
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Gets the update date.
     *
     * @return the update date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * Gets the genome lcdv code list.
     *
     * @return the genome lcdv code list
     */
    public List<GenomeLCDVCode> getGenomeLcdvCodeList() {
        return genomeLcdvCodeList;
    }

    /**
     * Sets the genome lcdv code list.
     *
     * @param genomeLcdvCodeList the new genome lcdv code list
     */
    public void setGenomeLcdvCodeList(List<GenomeLCDVCode> genomeLcdvCodeList) {
        this.genomeLcdvCodeList = genomeLcdvCodeList;
    }

    /**
     * Gets the fr label.
     *
     * @return the fr label
     */
    public String getFrLabel() {
        return frLabel;
    }

    /**
     * Sets the fr label.
     *
     * @param frLabel the new fr label
     */
    public void setFrLabel(String frLabel) {
        this.frLabel = frLabel;
    }

    /**
     * Gets the dictionary value.
     *
     * @return the dictionary value
     */
    public String getDictionaryValue() {
        return dictionaryValue;
    }

    /**
     * Sets the dictionary value.
     *
     * @param dictionaryValue the new dictionary value
     */
    public void setDictionaryValue(String dictionaryValue) {
        this.dictionaryValue = dictionaryValue;
    }

    /**
     * Gets the kmat.
     *
     * @return the kmat
     */
    public String getKmat() {
        return kmat;
    }

    /**
     * Sets the kmat.
     *
     * @param kmat the new kmat
     */
    public void setKmat(String kmat) {
        this.kmat = kmat;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.domain.BaseEntity#toString()
     */
    @Override
    public String toString() {
		String result = "KMAT : " + this.getKmat() + ", Dictionary Value : " + this.getDictionaryValue() + ", Fr Label " + this.getFrLabel() + ", Lcdv Code Size : " + (this.getGenomeLcdvCodeList() == null ? "empty" : (this.getGenomeLcdvCodeList().size() + ""));
        return result;
    }

}
