/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inetpsa.eds.application.content.eds.attachments;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.CaptionPath;
import com.inetpsa.eds.application.content.eds.EdsFormFactory;
import com.inetpsa.eds.application.content.eds.I_FormBuilder;
import com.inetpsa.eds.application.menu.edsnode.EN_ClosedStageFormNode;
import com.inetpsa.eds.application.menu.edsnode.EN_FormNode;
import com.inetpsa.eds.dao.model.EdsAttachmentFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;
import java.util.Collection;
import java.util.Collections;

/**
 * Form Builder for attachments
 * 
 * @author Geometric Ltd.
 */
public class AttachmentsFormBuilder implements I_FormBuilder {
    // PUBLIC
    /**
     * Constant to hold value for ID
     */
    public static final String ID = "eds-attachments";

    /**
     * Default Constructor
     */
    public AttachmentsFormBuilder() {
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
        return "file-j-title";
    }

    /**
     * Method returns stage of the form
     * 
     * @return stage of the form
     */
    public String getStade() {
        return "Fichiers joints";
    }

    /**
     * Method define Resource
     * 
     * @return resource to display
     */
    public Resource getIcon() {
        return ICON;
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
        return EdsRight.FORM_READ_ATTACHMENTS;
    }

    /**
     * Return the class used as a data model for the form of the builder
     * 
     * @return The class representing the data model
     */
    public Collection<Class<?>> getFormDataClasses() {
        return Collections.<Class<?>> singleton(EdsAttachmentFormData.class);
    }

    /**
     * Build a node to access the form on builder.
     * 
     * @param controller Controller of application data
     * @param eds EDS file for which you want to build a node
     * @return The access node to the form
     */
    public EN_FormNode buildNode(EdsApplicationController controller, EdsEds eds) {
        EdsAttachmentFormData formData = controller.getFormDataModel(eds, EdsAttachmentFormData.class);

        return new EN_ClosedStageFormNode(controller, new AttachmentsFormReadView(formData, controller, eds), new AttachmentsFormEditView(formData,
                controller, eds), EdsRight.FORM_WRITE_ATTACHMENTS);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Constant to hold value of Resource
     */
    private static final Resource ICON = ResourceManager.getInstance().getThemeIconResource("icons/attachments.png");

    /**
     * Initialize AttachementsFormBuilder
     */
    private void init() {
    }
}
