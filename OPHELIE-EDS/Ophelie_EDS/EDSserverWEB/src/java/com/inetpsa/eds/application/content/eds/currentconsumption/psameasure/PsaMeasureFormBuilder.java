package com.inetpsa.eds.application.content.eds.currentconsumption.psameasure;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.CaptionPath;
import com.inetpsa.eds.application.content.eds.EdsFormFactory;
import com.inetpsa.eds.application.content.eds.I_FormBuilder;
import com.inetpsa.eds.application.menu.edsnode.EN_ClosedStageFormNode;
import com.inetpsa.eds.application.menu.edsnode.EN_FormNode;
import com.inetpsa.eds.dao.model.EdsConsolidateCurentFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsPsaMesureSupply;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;
import java.util.Collection;
import java.util.Collections;

/**
 * Builder of the Current consumption PSA measurement form
 * 
 * @author Geometric Ltd.
 */
// TODO
public class PsaMeasureFormBuilder implements I_FormBuilder {
    // PUBLIC
    /**
     * Constant to hold value of unique id
     */
    public static final String ID = "eds-psa-measure-cc";

    /**
     * Default constructor
     */
    public PsaMeasureFormBuilder() {
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
        return "conso-psa-mesure-title";
    }

    /**
     * Method returns stage of the form
     * 
     * @return stage of the form
     */
    public String getStade() {
        return "Mesure PSA";
    }

    /**
     * Method define Resource
     * 
     * @return resource to display
     */
    public Resource getIcon() {
        return ResourceManager.getInstance().getThemeIconResource("icons/measure.png");
    }

    /**
     * Method returns Caption path
     * 
     * @return caption path
     */
    public CaptionPath getCaptionPath() {
        return EdsFormFactory.CLOSED_CAPTION_PATH;
    }

    /**
     * Define the necessary right of access
     * 
     * @return right of access
     */
    public String getReadingRightId() {
        return EdsRight.FORM_READ_MEASURED_CURRENT_CUNSUMPTION;
    }

    /**
     * Return the class used as a data model for the form of the builder
     * 
     * @return The class representing the data model
     */
    public Collection<Class<?>> getFormDataClasses() {
        return null;// Collections.<Class<?>>singleton( EdsPsaMesureSupply.class );
    }

    /**
     * Build a node to access the form on builder.
     * 
     * @param controller Controller of application data
     * @param eds EDS file for which you want to build a node
     * @return The access node to the form
     */
    public EN_FormNode buildNode(EdsApplicationController controller, EdsEds eds) {
        // The form measures PSA corresponds to adding information on power
        // A data model for this form is not worth
        EdsConsolidateCurentFormData edsConsolidateCurentFormData = (EdsConsolidateCurentFormData) controller.getFormDataModel(eds,
                EdsConsolidateCurentFormData.class);
        return new EN_ClosedStageFormNode(controller, new PsaMeasureFormReadView(eds, edsConsolidateCurentFormData, controller),
                new PsaMeasureFormEditView(eds, edsConsolidateCurentFormData, controller), EdsRight.FORM_WRITE_MEASURED_CURRENT_CUNSUMPTION);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Constant to hold value of String array
     */
    private static final String[] CAPTION_PATH = new String[] { "Consommations de courant" };

    /**
     * Initialize builder for PSA measurement
     */
    private void init() {
    }
}
