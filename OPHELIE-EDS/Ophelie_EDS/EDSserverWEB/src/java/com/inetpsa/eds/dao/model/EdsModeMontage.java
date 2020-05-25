package com.inetpsa.eds.dao.model;

// Generated 23 sept. 2012 01:07:01 by Hibernate Tools 3.2.1.GA

import java.util.UUID;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * EdsModeMontage generated by hbm2java
 */
public class EdsModeMontage implements java.io.Serializable {
    /**
     * String Variable to hold the value for Column MMEDS_ID of Table OPLQTMOM
     */
    private String mmedsId;
    /**
     * Float Variable to hold the value for Column MMEDS_TMOY_MODE_MONTAGE of Table OPLQTMOM
     */
    private Float mmedsTmoyModeMontage;
    /**
     * String Variable to hold the value for Column MMEDS_TMOY_MODE_MONTAGE_COMMENT of Table OPLQTMOM
     */
    private String mmedsTmoyModeMontageComment;
    /**
     * String Variable to hold the value for Column MMEDS_MODE_MONTAGE_TITRE of Table OPLQTMOM
     */
    private String mmedsModeMontageTitre;
    /**
     * Integer Variable to hold the value for Column MMEDS_REMOVE of Table OPLQTMOM
     */
    private Integer mmedsRemove;

    /**
     * Function to get mmedsRemove
     * 
     * @return the Value of mmedsRemove
     */
    @XmlTransient
    public Integer getMmedsRemove() {
        return mmedsRemove;
    }

    /**
     * Function to set mmedsRemove
     * 
     * @param mmedsRemove Check to remove
     */
    public void setMmedsRemove(Integer mmedsRemove) {
        this.mmedsRemove = mmedsRemove;
    }

    /**
     * Function to get mmedsModeMontageTitre
     * 
     * @return the Value ofmmedsModeMontageTitre
     */
    @XmlTransient
    public String getMmedsModeMontageTitre() {
        return mmedsModeMontageTitre;
    }

    /**
     * Function to set mmedsModeMontageTitre
     * 
     * @param mmedsTmoyModeMontageTitre Tmed
     */
    public void setMmedsModeMontageTitre(String mmedsTmoyModeMontageTitre) {
        this.mmedsModeMontageTitre = mmedsTmoyModeMontageTitre;
    }

    /**
     * Default Constructor
     */
    public EdsModeMontage() {
    }

    /**
     * Parameterized Constructor
     * 
     * @param mmedsId Id
     */
    public EdsModeMontage(String mmedsId) {
        this.mmedsId = mmedsId;
    }

    /**
     * Parameterized Constructor
     * 
     * @param mmedsId Id
     * @param mmedsRemove Check to remove
     */
    public EdsModeMontage(String mmedsId, Integer mmedsRemove) {
        this.mmedsId = mmedsId;
        this.mmedsRemove = mmedsRemove;
    }

    /**
     * Parameterized Constructor
     * 
     * @param mmedsId Id
     * @param mmedsTmoyModeMontage Tmoy
     * @param mmedsTmoyModeMontageComment comment
     */
    public EdsModeMontage(String mmedsId, Float mmedsTmoyModeMontage, String mmedsTmoyModeMontageComment) {
        this.mmedsId = mmedsId;
        this.mmedsTmoyModeMontage = mmedsTmoyModeMontage;
        this.mmedsTmoyModeMontageComment = mmedsTmoyModeMontageComment;
    }

    /**
     * Parameterized Constructor
     * 
     * @param srcModeMontage
     */
    public EdsModeMontage(EdsModeMontage srcModeMontage) {
        this.mmedsId = UUID.randomUUID().toString();
        this.mmedsTmoyModeMontage = srcModeMontage.mmedsTmoyModeMontage;
        this.mmedsTmoyModeMontageComment = srcModeMontage.mmedsTmoyModeMontageComment;
        this.mmedsRemove = srcModeMontage.mmedsRemove;
        this.mmedsModeMontageTitre = srcModeMontage.mmedsModeMontageTitre;
    }

    /**
     * Function to get mmedsId
     * 
     * @return the Value of mmedsId
     */
    @XmlAttribute(name = "id")
    public String getMmedsId() {
        return this.mmedsId;
    }

    /**
     * Function to set mmedsId
     * 
     * @param mmedsId
     */
    public void setMmedsId(String mmedsId) {
        this.mmedsId = mmedsId;
    }

    /**
     * Function to get mmedsTmoyModeMontage
     * 
     * @return the Value of mmedsTmoyModeMontage
     */
    @XmlElement(name = "Tmoy")
    public Float getMmedsTmoyModeMontage() {
        return this.mmedsTmoyModeMontage;
    }

    /**
     * Function to set mmedsTmoyModeMontage
     * 
     * @param mmedsTmoyModeMontage
     */
    public void setMmedsTmoyModeMontage(Float mmedsTmoyModeMontage) {
        this.mmedsTmoyModeMontage = mmedsTmoyModeMontage;
    }

    /**
     * Function to get mmedsTmoyModeMontageComment
     * 
     * @return the Value of mmedsTmoyModeMontageComment
     */
    @XmlTransient
    public String getMmedsTmoyModeMontageComment() {
        return this.mmedsTmoyModeMontageComment;
    }

    /**
     * Function to set mmedsTmoyModeMontageComment
     * 
     * @param mmedsTmoyModeMontageComment
     */
    public void setMmedsTmoyModeMontageComment(String mmedsTmoyModeMontageComment) {
        this.mmedsTmoyModeMontageComment = mmedsTmoyModeMontageComment;
    }
}