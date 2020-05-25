package com.inetpsa.eds.application.content.eds.versionhistory;

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
import java.util.Collections;

/**
 * This class is used to retrieve the form data and build Version history form.
 * 
 * @author Geometric Ltd.
 */
public class VersionHistoryFormBuilder implements I_FormBuilder {
    // PUBLIC
    /**
     * Variable to store version history ID.
     */
    public static final String ID = "eds-version-history";

    /**
     * Default constructor.
     */
    public VersionHistoryFormBuilder() {
        init();
    }

    /**
     * This method retrieves Unique ID.
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
     * @return The display name for this builder.
     * @see com.inetpsa.eds.application.content.eds.I_FormBuilder#getCaption()
     */
    public String getCaption() {
        return "hist-title";
    }

    /**
     * Implement Method to return a name of stade for this builder.
     * 
     * @return The display state for this builder.
     * @see com.inetpsa.eds.application.content.eds.I_FormBuilder#getStade()
     */
    public String getStade() {
        return "Version";
    }

    /**
     * Implement Method to define a path for the builder. It is best to define a path in EdsFormFactory the class to centralize the various existing
     * path and thus shared among different builders.
     * 
     * @return The path for this builder.
     * @see com.inetpsa.eds.application.content.eds.I_FormBuilder#getCaptionPath()
     */
    public CaptionPath getCaptionPath() {
        return EdsFormFactory.NO_PATH;
    }

    /**
     * Implement Method to define the necessary right of access to this form builder.
     * 
     * @return The law required to use this builder.
     * @see com.inetpsa.eds.application.content.eds.I_FormBuilder#getReadingRightId()
     */
    public String getReadingRightId() {
        return EdsRight.FORM_READ_VERSION_HISTORY;
    }

    /**
     * Implement Method to define the resource to this form builder.
     * 
     * @return resource to display.
     * @see com.inetpsa.eds.application.content.eds.I_FormBuilder#getIcon()
     */
    public Resource getIcon() {
        return ICON;
    }

    /**
     * Implement Method to return the class used as a data model for the form of the builder.
     * 
     * @return The class representing the data model.
     * @see com.inetpsa.eds.application.content.eds.I_FormBuilder#getFormDataClasses()
     */
    public Collection<Class<?>> getFormDataClasses() {
        return Collections.EMPTY_LIST;
    }

    /**
     * Implement Method to build a node to access the form on this builder.
     * 
     * @param controller Controller of application data.
     * @param eds EDS file for which you want to build a node
     * @return The access node to the form.
     * @see com.inetpsa.eds.application.content.eds.I_FormBuilder#buildNode(com.inetpsa.eds.application.EdsApplicationController,
     *      com.inetpsa.eds.dao.model.EdsEds)
     */
    public EN_FormNode buildNode(EdsApplicationController controller, EdsEds eds) {
        return new EN_ClosedStageFormNode(controller, new VersionHistoryFormReadView(controller, eds), null, null);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store the EDS icon.
     */
    private static final Resource ICON = ResourceManager.getInstance().getThemeIconResource("icons/eds.png");

    /**
     * Initialization method.
     */
    private void init() {
    }
}
