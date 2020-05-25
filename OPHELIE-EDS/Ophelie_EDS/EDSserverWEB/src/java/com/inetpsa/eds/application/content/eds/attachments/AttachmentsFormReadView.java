package com.inetpsa.eds.application.content.eds.attachments;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.EdsFormFactory;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.dao.model.EdsAttachment;
import com.inetpsa.eds.dao.model.EdsAttachmentFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.tools.docinfo.LienDocInfo;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Component for reading the form of a Attaching files to EDS record.
 * 
 * @author Geometric Ltd.
 */
public class AttachmentsFormReadView extends A_EdsReadForm {
    // PUBLIC
    /**
     * Parameterized Controller
     * 
     * @param formData EDS attachment form data.
     * @param controller EDS application controller object.
     * @param eds EDS details
     */
    public AttachmentsFormReadView(EdsAttachmentFormData formData, EdsApplicationController controller, EdsEds eds) {
        super(controller);
        this.formData = formData;
        this.controller = controller;
        this.eds = eds;
        init();
    }

    /**
     * This method returns attachment form data
     * 
     * @return Attachment form data
     */
    public EdsAttachmentFormData getFormData() {
        return formData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormName()
     */
    @Override
    public String getFormName() {
        return "file-j-title";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormTitle()
     */
    @Override
    public String getFormTitle() {
        return "file-j-title";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        return AttachmentsFormBuilder.ID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEdsRef()
     */
    @Override
    public String getEdsRef() {
        return eds.getEdsRef();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEds()
     */
    @Override
    public EdsEds getEds() {
        return eds;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshViewData()
     */
    @Override
    public void refreshViewData() {
        removeAllComponents();

        if (formData.getEdsAttachments().isEmpty()) {
            addComponent(new Label(controller.getBundle().getString("file-j-no-file")));
        } else {
            Iterator<EdsAttachment> it = formData.getEdsAttachments().iterator();

            EdsAttachment currentAttachment = it.next();
            addComponent(createAttachmentLayout(currentAttachment));

            while (it.hasNext()) {
                addComponent(new Label("<hr />", Label.CONTENT_XHTML));

                addComponent(createAttachmentLayout(it.next()));
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        formData = controller.getFormDataModel(eds, formData.getClass());
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EdsAttachmentFormData
     */
    private EdsAttachmentFormData formData;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;

    /**
     * Initialize AttachmentsFormReadView
     */
    private void init() {
        this.setSpacing(true);
    }

    /**
     * This method provide read view for attachments
     * 
     * @param attachment Attachments associated with EDS
     * @return Layout for attachments
     */
    private Layout createAttachmentLayout(EdsAttachment attachment) {
        VerticalLayout attachmentLayout = new VerticalLayout();
        attachmentLayout.setWidth("100%");
        attachmentLayout.setSpacing(true);
        try {
            attachmentLayout.addComponent(new LienDocInfo(new URL(attachment.getALink()), controller));
        } catch (MalformedURLException ex) {
            Logger.getLogger(AttachmentsFormReadView.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (attachment.getAComment() != null) {
            TextArea ta = new TextArea(controller.getBundle().getString("eds-comnent") + " :", attachment.getAComment());
            ta.setWidth("100%");
            ta.setNullRepresentation("");
            ta.setNullSettingAllowed(true);
            ta.setReadOnly(true);
            attachmentLayout.addComponent(ta);
        }

        return attachmentLayout;
    }
}
