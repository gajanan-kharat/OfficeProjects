package com.inetpsa.eds.dao.model;

// Generated 13 mai 2013 16:55:53 by Hibernate Tools 3.2.1.GA

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * EdsCourantCycle generated by hbm2java
 */
public class EdsCourantCycle implements java.io.Serializable {
    /**
     * Constant to hold value of RECONDUCTED_WITHOUT_MODIF
     */
    public final static int RECONDUCTED_WITHOUT_MODIF = 0;
    /**
     * Constant to hold value of RECONDUCTED_WITH_MODIF
     */
    public final static int RECONDUCTED_WITH_MODIF = 1;
    /**
     * String Variable to hold the value for Column CCEDS_ID of Table OPLQTCOC
     */
    private String ccedsId;
    /**
     * String Variable to hold the value for Column CCEDS_NAME of Table OPLQTCOC
     */
    private String ccedsName;
    /**
     * Float Variable to hold the value for Column CCEDS_TCYCLE of Table OPLQTCOC
     */
    private Float ccedsTcycle;
    /**
     * String Variable to hold the value for Column CCEDS_COMENT of Table OPLQTCOC
     */
    private String ccedsComent;
    /**
     * Boolean Variable to hold the value for Column CCEDS_REMOVE of Table OPLQTCOC
     */
    private Boolean ccedsRemove;
    /**
     * Integer Variable to hold the value for Column CCEDS_WITH_MODIF of Table OPLQTCOC
     */
    private Integer ccedsWithModif;
    /**
     * Set Collection to hold the values for Table OPLQTPRO
     */
    private Set<EdsProject> edsProjects = new HashSet<EdsProject>(0);

    /**
     * Default Constructor
     */
    public EdsCourantCycle() {
    }

    /**
     * Parameterized Constructor
     * 
     * @param ccedsId
     */
    public EdsCourantCycle(String ccedsId) {
        this.ccedsId = ccedsId;
    }

    /**
     * Parameterized Constructor
     * 
     * @param ccedsId Id
     * @param ccedsName Name
     * @param ccedsTcycle T cycle
     * @param ccedsComent comment
     * @param ccedsRemove Check if removable
     * @param edsProjects set of EdsProject
     */
    public EdsCourantCycle(String ccedsId, String ccedsName, Float ccedsTcycle, String ccedsComent, Boolean ccedsRemove, Set<EdsProject> edsProjects) {
        this.ccedsId = ccedsId;
        this.ccedsName = ccedsName;
        this.ccedsTcycle = ccedsTcycle;
        this.ccedsComent = ccedsComent;
        this.ccedsRemove = ccedsRemove;
        this.edsProjects = edsProjects;
    }

    /**
     * Parameterized Constructor
     * 
     * @param cycle EdsCourantCycle
     */
    public EdsCourantCycle(EdsCourantCycle cycle) {
        this.ccedsId = UUID.randomUUID().toString();
        this.ccedsName = cycle.ccedsName;
        this.ccedsTcycle = cycle.ccedsTcycle;
        this.ccedsComent = cycle.ccedsComent;
        this.ccedsRemove = cycle.ccedsRemove;
        this.ccedsWithModif = cycle.ccedsWithModif;
        for (EdsProject edsProject : cycle.edsProjects) {
            this.edsProjects.add(edsProject);
        }

    }

    /**
     * Function to get ccedsId
     * 
     * @return Id
     */
    public String getCcedsId() {
        return this.ccedsId;
    }

    /**
     * Function to set ccedsId
     * 
     * @param ccedsId Id
     */
    public void setCcedsId(String ccedsId) {
        this.ccedsId = ccedsId;
    }

    /**
     * Function to get ccedsName
     * 
     * @return Name
     */
    public String getCcedsName() {
        return this.ccedsName;
    }

    /**
     * Function to set ccedsName
     * 
     * @param ccedsName Name
     */
    public void setCcedsName(String ccedsName) {
        this.ccedsName = ccedsName;
    }

    /**
     * Function to get ccedsTcycle
     * 
     * @return T cycle
     */
    public Float getCcedsTcycle() {
        return this.ccedsTcycle;
    }

    /**
     * Function to set ccedsTcycle
     * 
     * @param ccedsTcycle T cycle
     */
    public void setCcedsTcycle(Float ccedsTcycle) {
        this.ccedsTcycle = ccedsTcycle;
    }

    /**
     * Function to get ccedsComent
     * 
     * @return Comment
     */
    public String getCcedsComent() {
        return this.ccedsComent;
    }

    /**
     * Function to set ccedsComent
     * 
     * @param ccedsComent comment
     */
    public void setCcedsComent(String ccedsComent) {
        this.ccedsComent = ccedsComent;
    }

    /**
     * Function to get ccedsRemove
     * 
     * @return Check if remoable
     */
    public Boolean getCcedsRemove() {
        return this.ccedsRemove;
    }

    /**
     * Function to set ccedsRemove
     * 
     * @param ccedsRemove Boolean specifying removable
     */
    public void setCcedsRemove(Boolean ccedsRemove) {
        this.ccedsRemove = ccedsRemove;
    }

    /**
     * Function to get ccedsWithModif
     * 
     * @return Current cycle with modification
     */
    public Integer getCcedsWithModif() {
        return ccedsWithModif;
    }

    /**
     * Function to set ccedsWithModif
     * 
     * @param ccedsWithModif Current cycle with modification
     */
    public void setCcedsWithModif(Integer ccedsWithModif) {
        this.ccedsWithModif = ccedsWithModif;
    }

    /**
     * Function to get edsProjects
     * 
     * @return set of EdsProject
     */
    public Set<EdsProject> getEdsProjects() {
        return this.edsProjects;
    }

    /**
     * Function to set edsProjects
     * 
     * @param edsProjects Set of EdsProject
     */
    public void setEdsProjects(Set<EdsProject> edsProjects) {
        this.edsProjects = edsProjects;
    }
}