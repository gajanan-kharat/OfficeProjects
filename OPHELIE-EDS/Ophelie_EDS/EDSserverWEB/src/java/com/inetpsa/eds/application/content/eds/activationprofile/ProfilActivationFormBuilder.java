package com.inetpsa.eds.application.content.eds.activationprofile;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.CaptionPath;
import com.inetpsa.eds.application.content.eds.EdsFormFactory;
import com.inetpsa.eds.application.content.eds.I_FormBuilder;
import com.inetpsa.eds.application.menu.edsnode.EN_FormNode;
import com.inetpsa.eds.application.menu.edsnode.EN_ConsolidatedStageFormNode;
import com.inetpsa.eds.dao.model.EdsConsolidateCurentFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsMissionActivationFormData;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;
import java.util.Collection;
import java.util.Collections;

/**
 * Form Builder "activation Profile"
 * 
 * @author Geometric Ltd.
 */
public class ProfilActivationFormBuilder implements I_FormBuilder {
    // PUBLIC
    /**
     * Variable to store a unique identifier.
     */
    public static final String ID = "eds-profil-activation";

    /**
     * Default constructor.
     */
    public ProfilActivationFormBuilder() {
        init();
    }

    /**
     * Implement Method to return a unique identifier for the form constructor. As explained, this string should be unique. Otherwise the data would
     * prevent collisions Forms with the same identifier run.
     * 
     * @return A unique identifier.
     * @see com.inetpsa.eds.application.content.eds.I_FormBuilder#getUniqueId()
     */
    public String getUniqueId() {
        return ID;
    }

    /**
     * Implement Method to return a display name for this builder.
     * 
     * @return The display name for this builder
     * @see com.inetpsa.eds.application.content.eds.I_FormBuilder#getCaption()
     */
    public String getCaption() {
        return "Activ-profil-title";
    }

    /**
     * Implement Method to return a name of stade for this builder.
     * 
     * @return The display stade for this builder.
     * @see com.inetpsa.eds.application.content.eds.I_FormBuilder#getStade()
     */
    public String getStade() {
        return "Profil activation";
    }

    /**
     * Implement Method to define the resource to this form builder.
     * 
     * @return resource to display.
     * @see com.inetpsa.eds.application.content.eds.I_FormBuilder#getIcon()
     */
    public Resource getIcon() {
        return ResourceManager.getInstance().getThemeIconResource("icons/activation-profile.png");
    }

    /**
     * Implement Method to define a path for the builder. . It is best to define a path in EdsFormFactory the class to centralize the various existing
     * path and thus shared among different builders.
     * 
     * @return The path for this builder
     * @see com.inetpsa.eds.application.content.eds.I_FormBuilder#getCaptionPath()
     */
    public CaptionPath getCaptionPath() {
        return EdsFormFactory.CONSOLIDATED_CAPTION_PATH;
    }

    /**
     * Implement Method to define the necessary right of access to this form builder.
     * 
     * @return The law required to use this builder.
     * @see com.inetpsa.eds.application.content.eds.I_FormBuilder#getReadingRightId()
     */
    public String getReadingRightId() {
        return EdsRight.FORM_READ_ACTIVATION_PROFILE;
    }

    /**
     * Implement Method to return the class used as a data model for the form of the builder
     * 
     * @return The class representing the data model.
     * @see com.inetpsa.eds.application.content.eds.I_FormBuilder#getFormDataClasses()
     */
    public Collection<Class<?>> getFormDataClasses() {
        return Collections.<Class<?>> singleton(EdsMissionActivationFormData.class);
    }

    /**
     * Implement Method to build a node to access the form on this builder.
     * 
     * @param controller Controller of application data
     * @param eds EDS file for which you want to build a node
     * @return The access node to the form
     * @see com.inetpsa.eds.application.content.eds.I_FormBuilder#buildNode(com.inetpsa.eds.application.EdsApplicationController,
     *      com.inetpsa.eds.dao.model.EdsEds)
     */
    public EN_FormNode buildNode(EdsApplicationController controller, EdsEds eds) {
        EdsMissionActivationFormData missionActivationFormData = (EdsMissionActivationFormData) controller.getFormDataModel(eds,
                EdsMissionActivationFormData.class);
        EdsConsolidateCurentFormData consolidateFormData = (EdsConsolidateCurentFormData) controller.getFormDataModel(eds,
                EdsConsolidateCurentFormData.class);

        return new EN_ConsolidatedStageFormNode(controller, new ProfilActivationFormReadView(eds, missionActivationFormData, controller),
                new ProfilActivationFormEditView(eds, missionActivationFormData, consolidateFormData, controller),
                EdsRight.FORM_WRITE_ACTIVATION_PROFILE);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialization method.
     */
    private void init() {
    }

}
