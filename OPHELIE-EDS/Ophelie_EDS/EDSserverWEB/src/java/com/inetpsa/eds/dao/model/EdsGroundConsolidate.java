package com.inetpsa.eds.dao.model;

// Generated 23 avr. 2013 16:19:05 by Hibernate Tools 3.2.1.GA

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * EdsGroundConsolidate generated by hbm2java
 */
public class EdsGroundConsolidate implements java.io.Serializable {
    /**
     * String Variable to hold the value for Column GEDS_ID of Table OPLQTGRC
     */
    private String gedsId;
    /**
     * String Variable to hold the value for Column GEDS_TITLE of Table OPLQTGRC
     */
    private String gedsTitle;
    // private String gcGrcedsId;
    /**
     * Set Collection to hold the values for Table OPLQTMST
     */
    private Set<EdsCourantMiseSousTension> edsCourantMiseSousTensions = new HashSet<EdsCourantMiseSousTension>(0);
    /**
     * Set Collection to hold the values for Table EDS_COURANT_APPELLE_ACTIVATION
     */
    private Set<EdsCourantAppelleActivation> edsCourantAppelleActivations = new HashSet<EdsCourantAppelleActivation>(0);
    /**
     * Set Collection to hold the values for Table EDS_COURANT_BLOQUE_COURANT_DYSFONCTIONNEMENT
     */
    private Set<EdsCourantBloqueCourantDysfonctionnement> edsCourantBloqueCourantDysfonctionnements = new HashSet<EdsCourantBloqueCourantDysfonctionnement>(
            0);
    /**
     * Set Collection to hold the values for Table EDS_COURANT_NOMINALE_ACTIVATION
     */
    private Set<EdsCourantNominaleActivation> edsCourantNominaleActivations = new HashSet<EdsCourantNominaleActivation>(0);

    /**
     * Default Constructor
     */
    public EdsGroundConsolidate() {
    }

    /**
     * Parameterized Constructor
     * 
     * @param gedsId
     */
    public EdsGroundConsolidate(String gedsId) {
        this.gedsId = gedsId;
    }

    /**
     * Parameterized Constructor
     * 
     * @param edsGroundConsolidate
     */
    public EdsGroundConsolidate(EdsGroundConsolidate edsGroundConsolidate) {
        this.gedsId = UUID.randomUUID().toString();
        this.gedsTitle = edsGroundConsolidate.gedsTitle;
        for (EdsCourantAppelleActivation source : edsGroundConsolidate.edsCourantAppelleActivations) {
            this.edsCourantAppelleActivations.add(new EdsCourantAppelleActivation(source));
        }
        for (EdsCourantNominaleActivation source : edsGroundConsolidate.edsCourantNominaleActivations) {
            this.edsCourantNominaleActivations.add(new EdsCourantNominaleActivation(source));
        }
        for (EdsCourantBloqueCourantDysfonctionnement source : edsGroundConsolidate.edsCourantBloqueCourantDysfonctionnements) {
            this.edsCourantBloqueCourantDysfonctionnements.add(new EdsCourantBloqueCourantDysfonctionnement(source));
        }
    }

    /**
     * Function to get gedsId
     * 
     * @return the Value of gedsId
     */
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
     * Function to get gedsTitle
     * 
     * @return the Value of gedsTitle
     */
    public String getGedsTitle() {
        return this.gedsTitle;
    }

    /**
     * Function to set gedsTitle
     * 
     * @param gedsTitle
     */
    public void setGedsTitle(String gedsTitle) {
        this.gedsTitle = gedsTitle;
    }

    // public String getGcGrcedsId() {
    // return this.gcGrcedsId;
    // }
    //
    // public void setGcGrcedsId(String gcGrcedsId) {
    // this.gcGrcedsId = gcGrcedsId;
    // }

    /**
     * Function to get edsCourantMiseSousTensions
     * 
     * @return the Value of edsCourantMiseSousTensions
     */
    public Set<EdsCourantMiseSousTension> getEdsCourantMiseSousTensions() {
        return this.edsCourantMiseSousTensions;
    }

    /**
     * Function to set edsCourantMiseSousTensions
     * 
     * @param edsCourantMiseSousTensions
     */
    public void setEdsCourantMiseSousTensions(Set<EdsCourantMiseSousTension> edsCourantMiseSousTensions) {
        this.edsCourantMiseSousTensions = edsCourantMiseSousTensions;
    }

    /**
     * Function to get edsCourantAppelleActivations
     * 
     * @return the Value of edsCourantAppelleActivations
     */
    public Set<EdsCourantAppelleActivation> getEdsCourantAppelleActivations() {
        return this.edsCourantAppelleActivations;
    }

    /**
     * Function to set edsCourantAppelleActivations
     * 
     * @param edsCourantAppelleActivations
     */
    public void setEdsCourantAppelleActivations(Set<EdsCourantAppelleActivation> edsCourantAppelleActivations) {
        this.edsCourantAppelleActivations = edsCourantAppelleActivations;
    }

    /**
     * Function to get edsCourantBloqueCourantDysfonctionnements
     * 
     * @return the Value of edsCourantBloqueCourantDysfonctionnements
     */
    public Set<EdsCourantBloqueCourantDysfonctionnement> getEdsCourantBloqueCourantDysfonctionnements() {
        return this.edsCourantBloqueCourantDysfonctionnements;
    }

    /**
     * Function to set edsCourantBloqueCourantDysfonctionnements
     * 
     * @param edsCourantBloqueCourantDysfonctionnements
     */
    public void setEdsCourantBloqueCourantDysfonctionnements(Set<EdsCourantBloqueCourantDysfonctionnement> edsCourantBloqueCourantDysfonctionnements) {
        this.edsCourantBloqueCourantDysfonctionnements = edsCourantBloqueCourantDysfonctionnements;
    }

    /**
     * Function to get edsCourantNominaleActivations
     * 
     * @return the Value of edsCourantNominaleActivations
     */
    public Set<EdsCourantNominaleActivation> getEdsCourantNominaleActivations() {
        return this.edsCourantNominaleActivations;
    }

    /**
     * Function to set
     * 
     * @param edsCourantNominaleActivations
     */
    public void setEdsCourantNominaleActivations(Set<EdsCourantNominaleActivation> edsCourantNominaleActivations) {
        this.edsCourantNominaleActivations = edsCourantNominaleActivations;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return gedsTitle; // To change body of generated methods, choose Tools | Templates.
    }

}
