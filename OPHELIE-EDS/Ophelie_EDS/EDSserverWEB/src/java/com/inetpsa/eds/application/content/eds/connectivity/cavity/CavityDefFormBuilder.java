package com.inetpsa.eds.application.content.eds.connectivity.cavity;

import java.util.Collection;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.CaptionPath;
import com.inetpsa.eds.application.content.eds.EdsFormFactory;
import com.inetpsa.eds.application.content.eds.I_FormBuilder;
import com.inetpsa.eds.application.menu.edsnode.EN_ConnectivityFormNode;
import com.inetpsa.eds.application.menu.edsnode.EN_FormNode;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsRight;
import com.vaadin.terminal.Resource;

/**
 * Form Builder "Cavity Definition"
 * 
 * @author Joao Costa @ Alter Frame
 */
public class CavityDefFormBuilder implements I_FormBuilder {
    // PUBLIC
    /**
     * Variable to store a unique identifier.
     */
    // TODO:Check id create
    public static final String ID = "eds-connectivity-cavitydef";

    /**
     * Default constructor.
     */
    public CavityDefFormBuilder() {
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
        return "connectivity-cavitydef-title";
    }

    /**
     * Implement Method to return a name of stade for this builder.
     * 
     * @return The display stade for this builder.
     * @see com.inetpsa.eds.application.content.eds.I_FormBuilder#getStade()
     */
    public String getStade() {
        return "Connectivité";
    }

    /**
     * Implement Method to define the resource to this form builder.
     * 
     * @return resource to display.
     * @see com.inetpsa.eds.application.content.eds.I_FormBuilder#getIcon()
     */
    public Resource getIcon() {
        return null;
    }

    /**
     * Implement Method to define a path for the builder. . It is best to define a path in EdsFormFactory the class to centralize the various existing
     * path and thus shared among different builders.
     * 
     * @return The path for this builder
     * @see com.inetpsa.eds.application.content.eds.I_FormBuilder#getCaptionPath()
     */
    public CaptionPath getCaptionPath() {
        return EdsFormFactory.CONNECTIVITY_CAPTION_PATH;
    }

    /**
     * Implement Method to define the necessary right of access to this form builder.
     * 
     * @return The law required to use this builder.
     * @see com.inetpsa.eds.application.content.eds.I_FormBuilder#getReadingRightId()
     */
    public String getReadingRightId() {
        return EdsRight.FORM_READ_ASSOCIATION_FC;
    }

    /**
     * Implement Method to return the class used as a data model for the form of the builder
     * 
     * @return The class representing the data model.
     * @see com.inetpsa.eds.application.content.eds.I_FormBuilder#getFormDataClasses()
     */
    public Collection<Class<?>> getFormDataClasses() {
        // Cas spécial EdsEds
        return null;
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
        return new EN_ConnectivityFormNode(controller, eds, new CavityDefFormReadView(controller, eds), new CavityDefFormEditView(eds, controller),
                EdsRight.FORM_WRITE_ASSOCIATION_FC);

    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialization method.
     */
    private void init() {
    }
}
