package com.inetpsa.eds.application.content.eds.currentconsumption.preliminary;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.CaptionPath;
import com.inetpsa.eds.application.content.eds.EdsFormFactory;
import com.inetpsa.eds.application.content.eds.I_FormBuilder;
import com.inetpsa.eds.application.menu.edsnode.EN_FormNode;
import com.inetpsa.eds.application.menu.edsnode.EN_PreliminaryStageFormNode;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsPrimaryCurent;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.dao.model.EdsRobustCurentFormData;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;
import java.util.Collection;
import java.util.Collections;

/**
 * Builder of the "Current consumption of preliminary stage" form
 * 
 * @author Geometric Ltd.
 */
public class PrimaryFormBuilder implements I_FormBuilder {
    // PUBLIC
    /**
     * Constant to hold value of ID
     */
    public static final String ID = "eds-primary-cc";
    /**
     * Variable to hold value of EdsPrimaryCurent
     */
    private EdsPrimaryCurent edsSupplyData;
    /**
     * Variable to hold value of EdsRobustCurentFormData
     */
    private EdsRobustCurentFormData edsRobustCurentFormData;

    /**
     * This method set the EdsSupplyData
     * 
     * @param edsSupplyData Object of EdsPrimaryCurent
     */
    public void setEdsSupplyData(EdsPrimaryCurent edsSupplyData) {
        this.edsSupplyData = edsSupplyData;
    }

    /**
     * This method return the EdsSupplyData
     * 
     * @return EdsPrimaryCurent
     */
    public EdsPrimaryCurent getEdsSupplyData() {
        return edsSupplyData;
    }

    /**
     * Default constructor
     */
    public PrimaryFormBuilder() {
        init();
    }

    /**
     * Method returns unique id of the form
     * 
     * @return unique id
     */
    public String getUniqueId() {
        return ID;
    }

    /**
     * Method returns caption of the form
     * 
     * @return caption of the form
     */
    public String getCaption() {
        return "conso-preliminaire-title";
    }

    /**
     * Method returns stage of the form
     * 
     * @return stage of the form
     */
    public String getStade() {
        return "Préliminaire-2";
    }

    /**
     * Method define Resource
     * 
     * @return resource to display
     */
    public Resource getIcon() {
        return ResourceManager.getInstance().getThemeIconResource("icons/current.ico");
    }

    /**
     * Method returns Caption path
     * 
     * @return caption path
     */
    public CaptionPath getCaptionPath() {
        return EdsFormFactory.PRELIMINARY_CAPTION_PATH;
    }

    /**
     * Define the necessary right of access
     * 
     * @return right of access
     */
    public String getReadingRightId() {
        return EdsRight.FORM_READ_PRELIM_CURRENT_CUNSUMPTION;
    }

    /**
     * Return the class used as a data model for the form of the builder
     * 
     * @return The class representing the data model
     */
    public Collection<Class<?>> getFormDataClasses() {
        return Collections.<Class<?>> singleton(EdsPrimaryCurent.class);
    }

    /**
     * Build a node to access the form on builder.
     * 
     * @param controller Controller of application data
     * @param eds EDS file for which you want to build a node
     * @return The access node to the form
     */
    public EN_FormNode buildNode(EdsApplicationController controller, EdsEds eds) {
        edsSupplyData = (EdsPrimaryCurent) controller.getFormDataModel(eds, EdsPrimaryCurent.class);
        edsRobustCurentFormData = (EdsRobustCurentFormData) controller.getFormDataModel(eds, EdsRobustCurentFormData.class);

        return new EN_PreliminaryStageFormNode(controller, new PrimaryFormReadView(eds, edsSupplyData, controller), new PrimaryFormEditView(
                controller, eds, edsSupplyData, edsRobustCurentFormData), EdsRight.FORM_WRITE_PRELIM_CURRENT_CUNSUMPTION);
    }

    // PROTECTED
    // PRIVATE

    /**
     * Initialize Builder of the "Current consumption of preliminary stage" form
     */
    private void init() {
    }
}
