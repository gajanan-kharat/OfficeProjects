package com.inetpsa.eds.application.content.eds.currentconsumption.driftdriver;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.CaptionPath;
import com.inetpsa.eds.application.content.eds.EdsFormFactory;
import com.inetpsa.eds.application.content.eds.I_FormBuilder;
import com.inetpsa.eds.application.menu.edsnode.EN_ClosedStageFormNode;
import com.inetpsa.eds.application.menu.edsnode.EN_FormNode;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;
import java.util.Collection;

/**
 * Builder of " current Consumption Drift control" form
 * 
 * @author Geometric Ltd.
 */
public class DriverDriftsFormBuilder implements I_FormBuilder {
    // PUBLIC
    /**
     * Constant to hold value of unique identifier
     */
    public static final String ID = "eds-driver-drifts-cc";

    /**
     * Default constructor
     */
    public DriverDriftsFormBuilder() {
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
        return "Pilote-title";
    }

    /**
     * Method returns stage of the form
     * 
     * @return stage of the form
     */
    public String getStade() {
        return "Pilote des dérives";
    }

    /**
     * Method define Resource
     * 
     * @return resource to display
     */
    public Resource getIcon() {
        return ResourceManager.getInstance().getThemeIconResource("icons/driftdriver.png");
    }

    /**
     * Method returns Caption path
     * 
     * @return caption path
     */
    public CaptionPath getCaptionPath() {
        return EdsFormFactory.NO_PATH;
    }

    /**
     * Define the necessary right of access
     * 
     * @return right of access
     */
    public String getReadingRightId() {
        return EdsRight.FORM_READ_DRIFT_DRIVER_CURRENT_CUNSUMPTION;
    }

    /**
     * Return the class used as a data model for the form of the builder
     * 
     * @return The class representing the data model
     */
    public Collection<Class<?>> getFormDataClasses() {
        return null;
    }

    /**
     * Build a node to access the form on builder.
     * 
     * @param controller Controller of application data
     * @param eds EDS file for which you want to build a node
     * @return The access node to the form
     */
    public EN_FormNode buildNode(EdsApplicationController controller, EdsEds eds) {
        return new EN_ClosedStageFormNode(controller, new EdsDriftDriverFormReadView(eds, controller),
                new EdsDriftDriverFormEditView(eds, controller), EdsRight.FORM_WRITE_DRIFT_DRIVER_CURRENT_CUNSUMPTION);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize Builder of "Current consumption Drift control " form
     */
    private void init() {
    }
}
