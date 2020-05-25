package com.inetpsa.eds.application.content.eds.behavior.robust;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.CaptionPath;
import com.inetpsa.eds.application.content.eds.EdsFormFactory;
import com.inetpsa.eds.application.content.eds.I_FormBuilder;
import com.inetpsa.eds.application.menu.edsnode.EN_FormNode;
import com.inetpsa.eds.application.menu.edsnode.EN_RobustStageFormNode;
import com.inetpsa.eds.dao.model.EdsComportementRobusteFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;
import java.util.Collection;
import java.util.Collections;

/**
 * Builder of "Behavior of robust stage" form
 * 
 * @author Geometric Ltd.
 */
public class ComportementRobusteFormBuilder implements I_FormBuilder {
    // PUBLIC
    /**
     * Constant to hold value of unique identifier
     */
    public static final String ID = "eds-comportement-mission";

    /**
     * Default constructor
     */
    public ComportementRobusteFormBuilder() {
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
        return "Admin-droit-form-comp-rob";
    }

    /**
     * Method returns stage of the form
     * 
     * @return stage of the form
     */
    public String getStade() {
        return "Robuste";
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
        return EdsFormFactory.ROBUST_CAPTION_PATH;
    }

    /**
     * Define the necessary right of access
     * 
     * @return right of access
     */
    public String getReadingRightId() {
        return EdsRight.FORM_READ_ROBUST_BEHAVIOR;
    }

    /**
     * Return the class used as a data model for the form of the builder
     * 
     * @return The class representing the data model
     */
    public Collection<Class<?>> getFormDataClasses() {
        return Collections.<Class<?>> singleton(EdsComportementRobusteFormData.class);
    }

    /**
     * Build a node to access the form on builder.
     * 
     * @param controller Controller of application data
     * @param eds EDS file for which you want to build a node
     * @return The access node to the form
     */
    public EN_FormNode buildNode(EdsApplicationController controller, EdsEds eds) {
        EdsComportementRobusteFormData comportementrobusteData = (EdsComportementRobusteFormData) controller.getFormDataModel(eds,
                EdsComportementRobusteFormData.class);

        return new EN_RobustStageFormNode(controller, new ComportementRobusteReadView(eds, comportementrobusteData, controller),
                new ComportementRobusteEditView(eds, comportementrobusteData, controller), EdsRight.FORM_WRITE_ROBUST_BEHAVIOR);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize builder for Behavior of robust stage form
     */
    private void init() {
    }
}
