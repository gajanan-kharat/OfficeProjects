package com.inetpsa.eds.dao.model;

// Generated 30 juil. 2012 09:33:50 by Hibernate Tools 3.2.1.GA

import com.inetpsa.eds.dao.I_FormData;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * EdsMissionActivationFormData generated by hbm2java
 */
@XmlRootElement(name = "activation-profile-form")
public class EdsMissionActivationFormData implements java.io.Serializable, I_FormData {
    /**
     * String Variable to hold the value for Column MAFD_ID of Table EDS_MISSION_ACTIVATION_FORM_DATA
     */
    private String mafdId;
    /**
     * String Variable to hold the value for Column MAFD_EDS_ID of Table EDS_MISSION_ACTIVATION_FORM_DATA
     */
    private EdsEds edsEds;
    /**
     * String Variable to hold the value for Column MAFD_COMMENTAIRE of Table EDS_MISSION_ACTIVATION_FORM_DATA
     */
    private String mafdCommentaire;
    /**
     * Set Collection to hold the values for Table OPLQTCUS
     */
    private Set<EdsCurrentStatement> edsCurrentStatements = new HashSet<EdsCurrentStatement>(0);

    /**
     * Default Constructor
     */
    public EdsMissionActivationFormData() {
    }

    /**
     * Parameterized Constructor
     * 
     * @param mafdId Id
     * @param edsEds Eds details
     */
    public EdsMissionActivationFormData(String mafdId, EdsEds edsEds) {
        this.mafdId = mafdId;
        this.edsEds = edsEds;
    }

    /**
     * Parameterized Constructor
     * 
     * @param mafdId Id
     * @param edsEds Eds details
     * @param mafdCommentaire Comment
     * @param edsCurrentSupplies set of EdsSupply
     */
    public EdsMissionActivationFormData(String mafdId, EdsEds edsEds, String mafdCommentaire, Set edsCurrentSupplies) {
        this.mafdId = mafdId;
        this.edsEds = edsEds;
        this.mafdCommentaire = mafdCommentaire;
        this.edsCurrentStatements = edsCurrentSupplies;
    }

    /**
     * Function to get mafdId
     * 
     * @return the Value of mafdId
     */
    @XmlTransient
    public String getMafdId() {
        return this.mafdId;
    }

    /**
     * Function to set mafdId
     * 
     * @param mafdId Id
     */
    public void setMafdId(String mafdId) {
        this.mafdId = mafdId;
    }

    /**
     * Function to get edsEds
     * 
     * @return the Value of edsEds
     */
    @XmlTransient
    public EdsEds getEdsEds() {
        return this.edsEds;
    }

    /**
     * Function to set edsEds
     * 
     * @param edsEds Eds details
     */
    public void setEdsEds(EdsEds edsEds) {
        this.edsEds = edsEds;
    }

    /**
     * Function to get mafdCommentaire
     * 
     * @return the Value of mafdCommentaire
     */
    @XmlElement(name = "comment")
    public String getMafdCommentaire() {
        return this.mafdCommentaire;
    }

    /**
     * Function to set mafdCommentaire
     * 
     * @param mafdCommentaire comment
     */
    public void setMafdCommentaire(String mafdCommentaire) {
        this.mafdCommentaire = mafdCommentaire;
    }

    /**
     * Function to get edsCurrentStatements
     * 
     * @return the Value of edsCurrentStatements
     */
    @XmlElementWrapper(name = "current-statements")
    @XmlElement(name = "current-statement")
    public Set<EdsCurrentStatement> getEdsCurrentStatements() {
        return this.edsCurrentStatements;
    }

    /**
     * Function to set edsCurrentStatements
     * 
     * @param edsCurrentStatements set of EdsCurrentStatement
     */
    public void setEdsCurrentStatements(Set edsCurrentStatements) {
        this.edsCurrentStatements = edsCurrentStatements;
    }

    /**
     * Function to get EdsMissionActivationFormData
     * 
     * @param eds Eds details
     * @param copiesMap Map of Id and EdsCurrentStatement objects
     * @return the Value of EdsMissionActivationFormData
     */
    public I_FormData getCopy(EdsEds eds, HashMap<String, Object> copiesMap) {
        EdsMissionActivationFormData copy = new EdsMissionActivationFormData(UUID.randomUUID().toString(), eds);
        copy.mafdCommentaire = this.mafdCommentaire;
        copy.edsCurrentStatements = new HashSet();
        for (EdsCurrentStatement statement : edsCurrentStatements) {
            EdsCurrentStatement statementCopy = statement.getCopy(eds, copiesMap);
            statementCopy.setEdsMissionActivationFormData(copy);
            copy.edsCurrentStatements.add(statementCopy);
        }
        return copy;
    }

    /**
     * Function to get all saved data
     * 
     * @return all saved data
     */
    public List<Object> getAllItemsToSave() {
        return Collections.singletonList((Object) this);
    }
}
