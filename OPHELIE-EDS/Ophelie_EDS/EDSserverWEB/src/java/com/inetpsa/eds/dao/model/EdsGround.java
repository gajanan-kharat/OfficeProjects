package com.inetpsa.eds.dao.model;

// Generated 22 ao�t 2012 11:05:51 by Hibernate Tools 3.2.1.GA

import java.util.UUID;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * EdsGround generated by hbm2java
 */
@XmlType
public class EdsGround implements java.io.Serializable {
    /**
     * String Variable to hold the value for Column GEDS_ID of Table OPLQTGRO
     */
    private String gedsId;
    /**
     * String Variable to hold the value for Column G_RCEDS_ID of Table OPLQTGRO
     */
    private EdsRobustCurentFormData edsRobustCurentFormData;
    /**
     * String Variable to hold the value for Column G_GRCEDS_ID of Table OPLQTGRO
     */
    private EdsGroundRobustCurent edsGroundRobustCurent;

    /**
     * Default Constructor
     */
    public EdsGround() {
    }

    /**
     * Parameterized Constructor
     * 
     * @param gedsId
     */
    public EdsGround(String gedsId) {
        this.gedsId = gedsId;
    }

    /**
     * Parameterized Constructor
     * 
     * @param gedsId Id
     * @param edsRobustCurentFormData EdsRobustCurentFormData
     * @param edsGroundRobustCurent EdsGroundRobustCurent
     */
    public EdsGround(String gedsId, EdsRobustCurentFormData edsRobustCurentFormData, EdsGroundRobustCurent edsGroundRobustCurent) {
        this.gedsId = gedsId;
        this.edsRobustCurentFormData = edsRobustCurentFormData;
        this.edsGroundRobustCurent = edsGroundRobustCurent;
    }

    /**
     * Parameterized Constructor
     * 
     * @param otherGround EdsGround
     */
    public EdsGround(EdsGround otherGround) {

        this.gedsId = UUID.randomUUID().toString();
        if (otherGround.edsGroundRobustCurent != null) {
            this.edsGroundRobustCurent = new EdsGroundRobustCurent(otherGround.edsGroundRobustCurent);
        }
    }

    /**
     * Function to get gedsId
     * 
     * @return the Value of gedsId
     */
    @XmlTransient
    public String getGedsId() {
        return this.gedsId;
    }

    /**
     * Function to set gedsId
     * 
     * @param gedsId
     */
    public void setGedsId(String gedsId) {
        this.gedsId = gedsId;
    }

    /**
     * Function to get edsRobustCurentFormData
     * 
     * @return the Value of edsRobustCurentFormData
     */
    @XmlTransient
    public EdsRobustCurentFormData getEdsRobustCurentFormData() {
        return this.edsRobustCurentFormData;
    }

    /**
     * Function to set edsRobustCurentFormData
     * 
     * @param edsRobustCurentFormData
     */
    public void setEdsRobustCurentFormData(EdsRobustCurentFormData edsRobustCurentFormData) {
        this.edsRobustCurentFormData = edsRobustCurentFormData;
    }

    /**
     * Function to get edsGroundRobustCurent
     * 
     * @return the Value of edsGroundRobustCurent
     */
    @XmlElement(name = "robust-ground")
    public EdsGroundRobustCurent getEdsGroundRobustCurent() {
        return this.edsGroundRobustCurent;
    }

    /**
     * Function to set edsGroundRobustCurent
     * 
     * @param edsGroundRobustCurent
     */
    public void setEdsGroundRobustCurent(EdsGroundRobustCurent edsGroundRobustCurent) {
        this.edsGroundRobustCurent = edsGroundRobustCurent;
    }
}