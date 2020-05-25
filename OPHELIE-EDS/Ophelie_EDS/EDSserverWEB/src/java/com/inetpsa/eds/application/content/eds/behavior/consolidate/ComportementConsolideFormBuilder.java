package com.inetpsa.eds.application.content.eds.behavior.consolidate;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.CaptionPath;
import com.inetpsa.eds.application.content.eds.EdsFormFactory;
import com.inetpsa.eds.application.content.eds.I_FormBuilder;
import com.inetpsa.eds.application.menu.edsnode.EN_ConsolidatedStageFormNode;
import com.inetpsa.eds.application.menu.edsnode.EN_FormNode;
import com.inetpsa.eds.dao.model.EdsComportementConsolideFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;
import java.util.Collection;
import java.util.Collections;

/**
 * Builder of "Behavior of consolidate stage" form
 * 
 * @author Geometric Ltd.
 */
public class ComportementConsolideFormBuilder implements I_FormBuilder {
    // PUBLIC
    /**
     * Constant to hold value of unique identifier
     */
    public static final String ID = "eds-comportement-consolide";

    /**
     * Default constructor
     */
    public ComportementConsolideFormBuilder() {
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
        return "Admin-droit-form-comp-cons";
    }

    /**
     * Method returns stage of the form
     * 
     * @return stage of the form
     */
    public String getStade() {
        return "Consolidé";
    }

    /**
     * Method define Resource
     * 
     * @return resource to display
     */
    public Resource getIcon() {
        return ResourceManager.getInstance().getThemeIconResource("icons/behaviour.png");
    }

    /**
     * Method returns Caption path
     * 
     * @return caption path
     */
    public CaptionPath getCaptionPath() {
        return EdsFormFactory.CONSOLIDATED_CAPTION_PATH;
    }

    /**
     * Define the necessary right of access
     * 
     * @return right of access
     */
    public String getReadingRightId() {
        return EdsRight.FORM_READ_CONSOLIDATED_BEHAVIOR;
    }

    /**
     * Return the class used as a data model for the form of the builder
     * 
     * @return The class representing the data model
     */
    public Collection<Class<?>> getFormDataClasses() {
        return Collections.<Class<?>> singleton(EdsComportementConsolideFormData.class);
    }

    /**
     * Build a node to access the form on builder.
     * 
     * @param controller Controller of application data
     * @param eds EDS file for which you want to build a node
     * @return The access node to the form
     */
    public EN_FormNode buildNode(EdsApplicationController controller, EdsEds eds) {
        EdsComportementConsolideFormData comportementconsolidedata = (EdsComportementConsolideFormData) controller.getFormDataModel(eds,
                EdsComportementConsolideFormData.class);

        return new EN_ConsolidatedStageFormNode(controller, new ComportementConsolideReadView(eds, comportementconsolidedata, controller),
                new ComportementConsolideEditView(eds, comportementconsolidedata, controller), EdsRight.FORM_WRITE_CONSOLIDATED_BEHAVIOR);
    }

    // PROTECTED
    // PRIVATE

    /**
     * Initialize builder for Behavior of consolidated stage form
     */
    private void init() {
    }

}
