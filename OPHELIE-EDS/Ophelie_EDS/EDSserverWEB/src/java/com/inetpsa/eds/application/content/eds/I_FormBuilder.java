package com.inetpsa.eds.application.content.eds;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.menu.edsnode.EN_FormNode;
import com.inetpsa.eds.dao.model.EdsEds;
import com.vaadin.terminal.Resource;
import java.util.Collection;

/**
 * Interface for implementing a mechanism for building form at the HMI and the data level. All classes implementing this interface must be listed in
 * the EdsFormfactory class.
 * 
 * @author Geometric Ltd.
 * @see EdsFormfactory
 */
public interface I_FormBuilder {
    // PUBLIC
    /**
     * Implement Method to return a unique identifier for the form constructor. As explained, this string should be unique. Otherwise the data would
     * prevent collisions Forms with the same identifier run.
     * 
     * @return A unique identifier
     */
    public String getUniqueId();

    /**
     * Implement Method to return a display name for this builder.
     * 
     * @return The display name for this builder
     */
    public String getCaption();

    /**
     * Implement Method to return a name of stade for this builder.
     * 
     * @return The display stade for this builder
     */
    public String getStade();

    /**
     * Implement Method to define a path for the builder. It is best to define a path in EdsFormFactory the class to centralize the various existing
     * path and thus shared among different builders.
     * 
     * @return The path for this builder
     */
    public CaptionPath getCaptionPath();

    /**
     * Implement Method to define the necessary right of access to this form builder.
     * 
     * @return The law required to use this builder
     */
    public String getReadingRightId();

    /**
     * Implement Method to define the resource to this form builder
     * 
     * @return resource to display
     */
    public Resource getIcon();

    /**
     * Implement Method to return the class used as a data model for the form of the builder
     * 
     * @return The class representing the data model
     */
    public Collection<Class<?>> getFormDataClasses();

    /**
     * Implement Method to build a node to access the form on this builder.
     * 
     * @param controller Controller of application data
     * @param eds EDS file for which you want to build a node
     * @return The access node to the form
     */
    public EN_FormNode buildNode(EdsApplicationController controller, EdsEds eds);
}
