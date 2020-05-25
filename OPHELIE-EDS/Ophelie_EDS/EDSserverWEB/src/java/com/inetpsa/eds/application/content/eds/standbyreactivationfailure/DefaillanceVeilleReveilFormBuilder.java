package com.inetpsa.eds.application.content.eds.standbyreactivationfailure;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.CaptionPath;
import com.inetpsa.eds.application.content.eds.EdsFormFactory;
import com.inetpsa.eds.application.content.eds.I_FormBuilder;
import com.inetpsa.eds.application.menu.edsnode.EN_ClosedStageFormNode;
import com.inetpsa.eds.application.menu.edsnode.EN_ConsolidatedStageFormNode;
import com.inetpsa.eds.application.menu.edsnode.EN_FormNode;
import com.inetpsa.eds.dao.model.EdsDefaillanceVeilleReveilFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;
import java.util.Collection;
import java.util.Collections;

/**
 * Builder of "asleep / awake Failure " form
 * 
 * @author Geometric Ltd.
 */
public class DefaillanceVeilleReveilFormBuilder implements I_FormBuilder {
    // PUBLIC
    /**
     * Constant to hold value of Unique identifier
     */
    public static final String ID = "eds-defaillance-veille-reveil";

    /**
     * Default constructor
     */
    public DefaillanceVeilleReveilFormBuilder() {
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
        return "Def-V-R-title";
    }

    /**
     * Method returns stage of the form
     * 
     * @return stage of the form
     */
    public String getStade() {
        return "Défaillance veille/réveil";
    }

    /**
     * Method define Resource
     * 
     * @return resource to display
     */
    public Resource getIcon() {
        return ResourceManager.getInstance().getThemeIconResource("icons/sleep.png");
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
        return EdsRight.FORM_READ_STANDBY_WAKEUP_FAILURE;
    }

    /**
     * Return the class used as a data model for the form of the builder
     * 
     * @return The class representing the data model
     */
    public Collection<Class<?>> getFormDataClasses() {
        return Collections.<Class<?>> singleton(EdsDefaillanceVeilleReveilFormData.class);
    }

    /**
     * Build a node to access the form on builder.
     * 
     * @param controller Controller of application data
     * @param eds EDS file for which you want to build a node
     * @return The access node to the form
     */
    public EN_FormNode buildNode(EdsApplicationController controller, EdsEds eds) {
        EdsDefaillanceVeilleReveilFormData defaillanceveillereveilFormData = (EdsDefaillanceVeilleReveilFormData) controller.getFormDataModel(eds,
                EdsDefaillanceVeilleReveilFormData.class);
        return new EN_ConsolidatedStageFormNode(controller,
                new DefaillanceVeilleReveilFormReadView(eds, defaillanceveillereveilFormData, controller), new DefaillanceVeilleReveilFormEditView(
                        eds, defaillanceveillereveilFormData, controller), EdsRight.FORM_WRITE_STANDBY_WAKEUP_FAILURE);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize Builder of sleep / wake Failure form
     */
    private void init() {
    }
}
