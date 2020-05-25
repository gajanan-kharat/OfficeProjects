package com.inetpsa.eds.dao.model;

// Generated 24 sept. 2012 15:50:47 by Hibernate Tools 3.2.1.GA

import com.inetpsa.eds.dao.I_FormData;
import com.inetpsa.eds.dao.I_ReconsultableFormData;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * EdsPreliminarySupplyVoltageFormData generated by hbm2java
 */
@XmlRootElement(name = "preliminary-supply-voltage-form")
public class EdsPreliminarySupplyVoltageFormData implements java.io.Serializable, I_FormData, I_ReconsultableFormData {
    /**
     * String Variable to hold the value for Column PSV_ID of Table EDS_PRELIMINARY_SUPPLY_VOLTAGE_FORM_DATA
     */
    private String psvId;
    /**
     * Variable to hold the value for Column PSV_ORIGINE_DONNEE_TENSION_MAXIMALE_FONCTIONNEMENT_NOMINALE of Table
     * EDS_PRELIMINARY_SUPPLY_VOLTAGE_FORM_DATA
     */
    private EdsWording edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale;
    /**
     * Variable to hold the value for Column PSV_ORIGINE_DONNEE_TENSION_MINIMALE_FONCTIONNEMENT_NOMINALE of Table
     * EDS_PRELIMINARY_SUPPLY_VOLTAGE_FORM_DATA
     */
    private EdsWording edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale;
    /**
     * Variable to hold the value for Column PSV_ORIGINE_DONNEE_TENSION_MINIMALE_FONCTIONNEMENT_INITIALISATION of Table
     * EDS_PRELIMINARY_SUPPLY_VOLTAGE_FORM_DATA
     */
    private EdsWording edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation;
    /**
     * Variable to hold the value for Column PSV_EDS_ID of Table EDS_PRELIMINARY_SUPPLY_VOLTAGE_FORM_DATA
     */
    private EdsEds edsEds;
    /**
     * Variable to hold the value for Column PSV_ORIGINE_DONNEE_TENSION_MAXIMALE_FONCTIONNEMENT_REHABILISATION of Table
     * EDS_PRELIMINARY_SUPPLY_VOLTAGE_FORM_DATA
     */
    private EdsWording edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation;
    /**
     * String Variable to hold the value for Column PSV_VALEUR_TENSION_MINIMALE_FONCTIONNEMENT_INITIALISATION of Table
     * EDS_PRELIMINARY_SUPPLY_VOLTAGE_FORM_DATA
     */
    private String psvValeurTensionMinimaleFonctionnementInitialisation;
    /**
     * String Variable to hold the value for Column PSV_VALEUR_TENSION_MINIMALE_FONCTIONNEMENT_NOMINALE of Table
     * EDS_PRELIMINARY_SUPPLY_VOLTAGE_FORM_DATA
     */
    private String psvValeurTensionMinimaleFonctionnementNominale;
    /**
     * String Variable to hold the value for Column PSV_VALEUR_TENSION_MAXIMALE_FONCTIONNEMENT_NOMINALE of Table
     * EDS_PRELIMINARY_SUPPLY_VOLTAGE_FORM_DATA
     */
    private String psvValeurTensionMaximaleFonctionnementNominale;
    /**
     * String Variable to hold the value for Column PSV_VALEUR_TENSION_MAXIMALE_FONCTIONNEMENT_REHABILISATION of Table
     * EDS_PRELIMINARY_SUPPLY_VOLTAGE_FORM_DATA
     */
    private String psvValeurTensionMaximaleFonctionnementRehabilisation;

    /**
     * Default Constructor
     */
    public EdsPreliminarySupplyVoltageFormData() {
    }

    /**
     * Parameterized Constructor
     * 
     * @param psvId
     * @param edsEds
     */
    public EdsPreliminarySupplyVoltageFormData(String psvId, EdsEds edsEds) {
        this.psvId = psvId;
        this.edsEds = edsEds;
    }

    /**
     * Parameterized Constructor
     * 
     * @param psvId Id
     * @param edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale Maximum voltage allowed for a normal
     * @param edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale Minimal voltage of nominal operation (Umin)
     * @param edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation Minimal voltage of downgraded operation (Initialisation)
     * @param edsEds EdsEds
     * @param edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation Maximum voltage of downgraded operation (Restoration)
     * @param psvValeurTensionMinimaleFonctionnementInitialisation Minimal voltage of downgraded operation (Initialisation) value
     * @param psvValeurTensionMinimaleFonctionnementNominale Minimal voltage of nominal operation (Umin) value
     * @param psvValeurTensionMaximaleFonctionnementNominale Maximum voltage allowed for a normal value
     * @param psvValeurTensionMaximaleFonctionnementRehabilisation Maximum voltage of downgraded operation (Restoration) value
     */
    public EdsPreliminarySupplyVoltageFormData(String psvId, EdsWording edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale,
            EdsWording edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale,
            EdsWording edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation, EdsEds edsEds,
            EdsWording edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation,
            String psvValeurTensionMinimaleFonctionnementInitialisation, String psvValeurTensionMinimaleFonctionnementNominale,
            String psvValeurTensionMaximaleFonctionnementNominale, String psvValeurTensionMaximaleFonctionnementRehabilisation) {
        this.psvId = psvId;
        this.edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale = edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale;
        this.edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale = edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale;
        this.edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation = edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation;
        this.edsEds = edsEds;
        this.edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation = edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation;
        this.psvValeurTensionMinimaleFonctionnementInitialisation = psvValeurTensionMinimaleFonctionnementInitialisation;
        this.psvValeurTensionMinimaleFonctionnementNominale = psvValeurTensionMinimaleFonctionnementNominale;
        this.psvValeurTensionMaximaleFonctionnementNominale = psvValeurTensionMaximaleFonctionnementNominale;
        this.psvValeurTensionMaximaleFonctionnementRehabilisation = psvValeurTensionMaximaleFonctionnementRehabilisation;
    }

    /**
     * Function to get psvId
     * 
     * @return the Value of psvId
     */
    @XmlTransient
    public String getPsvId() {
        return this.psvId;
    }

    /**
     * Function to set psvId
     * 
     * @param psvId
     */
    public void setPsvId(String psvId) {
        this.psvId = psvId;
    }

    /**
     * Function to get edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale
     * 
     * @return the Value of edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale
     */
    @XmlElement(name = "nominal-func-max-voltage-data-origin")
    public EdsWording getEdsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale() {
        return this.edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale;
    }

    /**
     * Function to set edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale
     * 
     * @param edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale
     */
    public void setEdsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale(
            EdsWording edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale) {
        this.edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale = edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale;
    }

    /**
     * Function to get edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale
     * 
     * @return the Value of edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale
     */
    @XmlElement(name = "nominal-func-min-voltage-data-origin")
    public EdsWording getEdsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale() {
        return this.edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale;
    }

    /**
     * Function to set edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale
     * 
     * @param edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale
     */
    public void setEdsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale(
            EdsWording edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale) {
        this.edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale = edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale;
    }

    /**
     * Function to get edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation
     * 
     * @return the Value of edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation
     */
    @XmlElement(name = "initialisation-min-voltage-data-origin")
    public EdsWording getEdsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation() {
        return this.edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation;
    }

    /**
     * Function to set edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation
     * 
     * @param edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation
     */
    public void setEdsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation(
            EdsWording edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation) {
        this.edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation = edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation;
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
     * @param edsEds
     */
    public void setEdsEds(EdsEds edsEds) {
        this.edsEds = edsEds;
    }

    /**
     * Function to get edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation
     * 
     * @return the Value of edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation
     */
    @XmlElement(name = "rehabilitation-max-voltage-data-origin")
    public EdsWording getEdsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation() {
        return this.edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation;
    }

    /**
     * Function to set Function to set edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation
     * 
     * @param edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation
     */
    public void setEdsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation(
            EdsWording edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation) {
        this.edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation = edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation;
    }

    /**
     * Function to get psvValeurTensionMinimaleFonctionnementInitialisation
     * 
     * @return the Value of psvValeurTensionMinimaleFonctionnementInitialisation
     */
    @XmlElement(name = "initialisation-min-voltage-value")
    public String getPsvValeurTensionMinimaleFonctionnementInitialisation() {
        return this.psvValeurTensionMinimaleFonctionnementInitialisation;
    }

    /**
     * Function to set psvValeurTensionMinimaleFonctionnementInitialisation
     * 
     * @param psvValeurTensionMinimaleFonctionnementInitialisation
     */
    public void setPsvValeurTensionMinimaleFonctionnementInitialisation(String psvValeurTensionMinimaleFonctionnementInitialisation) {
        this.psvValeurTensionMinimaleFonctionnementInitialisation = psvValeurTensionMinimaleFonctionnementInitialisation;
    }

    /**
     * Function to get psvValeurTensionMinimaleFonctionnementNominale
     * 
     * @return the Value of psvValeurTensionMinimaleFonctionnementNominale
     */
    @XmlElement(name = "nominal-func-min-voltage-value")
    public String getPsvValeurTensionMinimaleFonctionnementNominale() {
        return this.psvValeurTensionMinimaleFonctionnementNominale;
    }

    /**
     * Function to set psvValeurTensionMinimaleFonctionnementNominale
     * 
     * @param psvValeurTensionMinimaleFonctionnementNominale
     */
    public void setPsvValeurTensionMinimaleFonctionnementNominale(String psvValeurTensionMinimaleFonctionnementNominale) {
        this.psvValeurTensionMinimaleFonctionnementNominale = psvValeurTensionMinimaleFonctionnementNominale;
    }

    /**
     * Function to get psvValeurTensionMaximaleFonctionnementNominale
     * 
     * @return the Value of psvValeurTensionMaximaleFonctionnementNominale
     */
    @XmlElement(name = "nominal-func-max-voltage-value")
    public String getPsvValeurTensionMaximaleFonctionnementNominale() {
        return this.psvValeurTensionMaximaleFonctionnementNominale;
    }

    /**
     * Function to set psvValeurTensionMaximaleFonctionnementNominale
     * 
     * @param psvValeurTensionMaximaleFonctionnementNominale
     */
    public void setPsvValeurTensionMaximaleFonctionnementNominale(String psvValeurTensionMaximaleFonctionnementNominale) {
        this.psvValeurTensionMaximaleFonctionnementNominale = psvValeurTensionMaximaleFonctionnementNominale;
    }

    /**
     * Function to get psvValeurTensionMaximaleFonctionnementRehabilisation
     * 
     * @return the Value of psvValeurTensionMaximaleFonctionnementRehabilisation
     */
    @XmlElement(name = "rehabilitation-max-voltage-value")
    public String getPsvValeurTensionMaximaleFonctionnementRehabilisation() {
        return this.psvValeurTensionMaximaleFonctionnementRehabilisation;
    }

    /**
     * Function to set psvValeurTensionMaximaleFonctionnementRehabilisation
     * 
     * @param psvValeurTensionMaximaleFonctionnementRehabilisation
     */
    public void setPsvValeurTensionMaximaleFonctionnementRehabilisation(String psvValeurTensionMaximaleFonctionnementRehabilisation) {
        this.psvValeurTensionMaximaleFonctionnementRehabilisation = psvValeurTensionMaximaleFonctionnementRehabilisation;
    }

    /**
     * Function to get
     * 
     * @param eds Eds details
     * @param copiesMap Map of Id and ProjectSpecificData objects
     * @return the Value of EdsPreliminarySupplyVoltageFormData
     */
    public I_FormData getCopy(EdsEds eds, HashMap<String, Object> copiesMap) {
        EdsPreliminarySupplyVoltageFormData copy = new EdsPreliminarySupplyVoltageFormData(UUID.randomUUID().toString(), eds);

        copy.edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale = this.edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale;
        copy.edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation = this.edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation;
        copy.edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation = this.edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation;
        copy.edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale = this.edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale;

        copy.psvValeurTensionMaximaleFonctionnementNominale = this.psvValeurTensionMaximaleFonctionnementNominale;
        copy.psvValeurTensionMaximaleFonctionnementRehabilisation = this.psvValeurTensionMaximaleFonctionnementRehabilisation;
        copy.psvValeurTensionMinimaleFonctionnementInitialisation = this.psvValeurTensionMinimaleFonctionnementInitialisation;
        copy.psvValeurTensionMinimaleFonctionnementNominale = this.psvValeurTensionMinimaleFonctionnementNominale;
        return copy;
    }

    /**
     * Function to get all saved items
     * 
     * @return all saved items
     */
    public List<Object> getAllItemsToSave() {
        return Collections.singletonList((Object) this);
    }

    /**
     * Function to get
     * 
     * @param eds Eds Details
     * @param copiesMap Map of Id and ProjectSpecificData objects
     * @return the Value of EdsPreliminarySupplyVoltageFormData
     */
    public I_FormData getReconsultableCopy(EdsEds eds, HashMap<String, Object> copiesMap) {
        EdsPreliminarySupplyVoltageFormData copy = new EdsPreliminarySupplyVoltageFormData(UUID.randomUUID().toString(), eds);

        copy.edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale = this.edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale;
        copy.edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation = this.edsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation;
        copy.edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation = this.edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation;
        copy.edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale = this.edsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale;

        copy.psvValeurTensionMaximaleFonctionnementNominale = this.psvValeurTensionMaximaleFonctionnementNominale;
        copy.psvValeurTensionMaximaleFonctionnementRehabilisation = this.psvValeurTensionMaximaleFonctionnementRehabilisation;
        copy.psvValeurTensionMinimaleFonctionnementInitialisation = this.psvValeurTensionMinimaleFonctionnementInitialisation;
        copy.psvValeurTensionMinimaleFonctionnementNominale = this.psvValeurTensionMinimaleFonctionnementNominale;
        return copy;
    }
}
