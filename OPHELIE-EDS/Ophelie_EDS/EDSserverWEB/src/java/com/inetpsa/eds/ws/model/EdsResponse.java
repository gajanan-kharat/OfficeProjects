package com.inetpsa.eds.ws.model;

import com.inetpsa.eds.dao.I_FormData;
import com.inetpsa.eds.dao.model.EdsAttachmentFormData;
import com.inetpsa.eds.dao.model.EdsComportementConsolideFormData;
import com.inetpsa.eds.dao.model.EdsComportementRobusteFormData;
import com.inetpsa.eds.dao.model.EdsConsolidateCurentFormData;
import com.inetpsa.eds.dao.model.EdsConsolidatedSupplyVoltageFormData;
import com.inetpsa.eds.dao.model.EdsCseFormData;
import com.inetpsa.eds.dao.model.EdsDefaillanceVeilleReveilFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsHighValidationFormData;
import com.inetpsa.eds.dao.model.EdsLowValidationFormData;
import com.inetpsa.eds.dao.model.EdsMissionActivationFormData;
import com.inetpsa.eds.dao.model.EdsMissionProfilFormData;
import com.inetpsa.eds.dao.model.EdsPreliminarySupplyVoltageFormData;
import com.inetpsa.eds.dao.model.EdsPrimaryCurent;
import com.inetpsa.eds.dao.model.EdsPsaMesureSupply;
import com.inetpsa.eds.dao.model.EdsRobustCurentFormData;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class bind EdsResponse to XML
 * 
 * @author Geometric Ltd.
 */
@XmlRootElement(name = "eds")
@XmlSeeAlso({ EdsPrimaryCurent.class, EdsRobustCurentFormData.class, EdsConsolidateCurentFormData.class, EdsDefaillanceVeilleReveilFormData.class,
        EdsMissionActivationFormData.class, EdsMissionProfilFormData.class, EdsComportementConsolideFormData.class,
        EdsComportementRobusteFormData.class, EdsPreliminarySupplyVoltageFormData.class, EdsConsolidatedSupplyVoltageFormData.class,
        EdsLowValidationFormData.class, EdsHighValidationFormData.class, EdsAttachmentFormData.class, EdsCseFormData.class, EdsPsaMesureSupply.class })
public class EdsResponse {
    // PUBLIC
    /**
     * Default Constructor
     */
    public EdsResponse() {
        init();
    }

    /**
     * Function to get formDatas
     * 
     * @return the Value of formDatas
     */
    @XmlElementWrapper
    @XmlAnyElement
    public List<I_FormData> getFormDatas() {
        return formDatas;
    }

    /**
     * Function to set formDatas
     * 
     * @param formDatas List of Form data
     */
    public void setFormDatas(List<I_FormData> formDatas) {
        this.formDatas = formDatas;
    }

    /**
     * Function to get eds
     * 
     * @return the Value of eds
     */
    @XmlElement(name = "generic-data")
    public EdsEds getEds() {
        return eds;
    }

    /**
     * Function to set eds
     * 
     * @param eds Eds details
     */
    public void setEds(EdsEds eds) {
        this.eds = eds;
    }

    // PROTECTED
    // PRIVATE
    /**
     * Method to initialize arraylist of Form data
     */
    private void init() {
        this.formDatas = new ArrayList<I_FormData>();
    }

    /**
     * EdsEds variable to hold value of eds details
     */
    private EdsEds eds;
    /**
     * List to store form data
     */
    private List<I_FormData> formDatas;
}
