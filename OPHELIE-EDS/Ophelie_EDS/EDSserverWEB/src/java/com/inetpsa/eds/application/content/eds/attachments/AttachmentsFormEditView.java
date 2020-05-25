package com.inetpsa.eds.application.content.eds.attachments;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.model.EdsAttachment;
import com.inetpsa.eds.dao.model.EdsAttachmentFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.tools.EDSTools;
import com.inetpsa.eds.tools.docinfo.LienDocInfo;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Component editing the form of a Attaching files to EDS record.
 * 
 * @author Geometric Ltd.
 */
public class AttachmentsFormEditView extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param formData Eds attachment form data
     * @param controller Controller of EDS application
     * @param eds Eds details
     */
    public AttachmentsFormEditView(EdsAttachmentFormData formData, EdsApplicationController controller, EdsEds eds) {
        this.formData = formData;
        this.controller = controller;
        this.eds = eds;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        for (EdsAttachment attachment : attachmentsToRemove) {
            formData.removeAttachment(attachment);
        }

        for (EdsAttachment attachment : attachmentsToAdd) {
            formData.addAttachment(attachment);
        }

        for (EdsAttachment attachment : formData.getEdsAttachments()) {
            attachment.setAComment((String) textAreasMap.get(attachment.getAId()).getValue());
        }
        eds.setEdsModifDate(new Date());
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        textAreasMap.clear();
        attachmentsToAdd.clear();
        attachmentsToRemove.clear();

        addedFilesLayout.removeAllComponents();

        for (EdsAttachment attachment : formData.getEdsAttachments()) {
            addedFilesLayout.addComponent(createPreviewLayout(attachment));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<EdsAttachmentFormData> getAllItemsToSave() {
        return Collections.singletonList(formData);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete()
     */
    @Override
    public Collection getAllItemsToDelete() {
        return Collections.EMPTY_SET;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        formData = controller.getFormDataModel(eds, formData.getClass());
    }

    // PROTECTED
    // PRIVATE
    /**
     * Constant to hold value of Resource of Add_ICON
     */
    private static final Resource ADD_ICON = ResourceManager.getInstance().getThemeIconResource("icons/new.png");
    /**
     * Constant to hold value of Resource of REMOVE_ICON
     */
    private static final Resource REMOVE_ICON = ResourceManager.getInstance().getThemeIconResource("icons/delete.png");
    /**
     * Constant to hold value of Resource of PREVIEW_ICON
     */
    private static final Resource PREVIEW_ICON = ResourceManager.getInstance().getThemeIconResource("icons/preview.png");
    /**
     * Variable to hold value of EdsAttachmentFormData
     */
    private EdsAttachmentFormData formData;
    /**
     * Variable to hold value of Controller of Eds application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of Eds details
     */
    private EdsEds eds;
    /**
     * Variable to hold value of TextField for link
     */
    private TextField vTXTlinkValue;
    /**
     * Variable to hold value of TextArea for comment
     */
    private TextArea vTXTcommentValue;
    /**
     * Variable to hold value of Button to add file
     */
    private Button vBTaddFile;
    /**
     * Variable to hold value of VerticalLayout
     */
    private VerticalLayout addedFilesLayout;
    /**
     * Variable to hold value of map of String and TextArea
     */
    private HashMap<String, TextArea> textAreasMap;
    /**
     * Variable to hold set of EdsAttachment to add
     */
    private HashSet<EdsAttachment> attachmentsToAdd;
    /**
     * Variable to hold set of EdsAttachment to remove
     */
    private HashSet<EdsAttachment> attachmentsToRemove;

    /**
     * Initialize AttachmentFormEditView
     */
    private void init() {
        textAreasMap = new HashMap<String, TextArea>();
        attachmentsToAdd = new HashSet<EdsAttachment>();
        attachmentsToRemove = new HashSet<EdsAttachment>();

        setSpacing(true);

        Panel addLinkPanel = new Panel(controller.getBundle().getString("file-j-add-file"));
        addLinkPanel.setWidth("100%");
        VerticalLayout addLinkLayout = new VerticalLayout();
        addLinkLayout.setWidth("100%");
        addLinkLayout.setMargin(true);
        addLinkLayout.setSpacing(true);

        vTXTlinkValue = new TextField(controller.getBundle().getString("file-j-lync") + " :");
        vTXTlinkValue.setNullRepresentation("");
        vTXTlinkValue.setNullSettingAllowed(true);
        vTXTlinkValue.setRequired(true);
        vTXTlinkValue.setColumns(35);
        vTXTlinkValue.setMaxLength(512);
        vTXTcommentValue = new TextArea(controller.getBundle().getString("eds-comnent") + " :");
        vTXTcommentValue.setWidth("100%");
        vTXTcommentValue.setNullRepresentation("");
        vTXTcommentValue.setNullSettingAllowed(true);
        vTXTcommentValue.setMaxLength(1024);
        vBTaddFile = new Button(controller.getBundle().getString("file-j-add"), new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                String link = EDSTools.convertEmptyStringToNull((String) vTXTlinkValue.getValue());
                String comment = EDSTools.convertEmptyStringToNull((String) vTXTcommentValue.getValue());

                if (link != null) {
                    if (Character.isDigit(link.charAt(0))) {
                        link = "http://docinfogroupe.inetpsa.com/ead/doc/ref." + link;
                    }

                    EdsAttachment newAttachment = new EdsAttachment(UUID.randomUUID().toString(), formData, link, comment);

                    if (formData.getEdsAttachments().contains(newAttachment)) {
                        controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle().getString("file-existe"));
                    } else if (attachmentsToAdd.add(newAttachment)) {
                        attachmentsToRemove.remove(newAttachment);
                        addedFilesLayout.addComponent(createPreviewLayout(newAttachment));
                    } else {
                        controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle().getString("file-existe"));
                    }
                } else {
                    controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle().getString("url-empted"));
                }

                vTXTlinkValue.setValue(null);
                vTXTcommentValue.setValue(null);
            }
        });
        vBTaddFile.setIcon(ADD_ICON);

        addLinkPanel.setContent(addLinkLayout);

        Panel addedFilesPanel = new Panel(controller.getBundle().getString("file-j-title"));
        addedFilesPanel.setWidth("100%");
        addedFilesLayout = new VerticalLayout();
        addedFilesLayout.setWidth("100%");
        addedFilesLayout.setSpacing(true);
        addedFilesLayout.setMargin(true);
        addedFilesPanel.setContent(addedFilesLayout);

        addLinkLayout.addComponent(vTXTlinkValue);
        addLinkLayout.addComponent(vTXTcommentValue);
        addLinkLayout.addComponent(vBTaddFile);
        addLinkLayout.setComponentAlignment(vBTaddFile, Alignment.MIDDLE_RIGHT);

        addComponent(addLinkPanel);
        addComponent(addedFilesPanel);
    }

    /**
     * This method returns layout for previewing attachment
     * 
     * @param attachment Eds attachment
     * @return Preview layout
     */
    private Layout createPreviewLayout(final EdsAttachment attachment) {
        final VerticalLayout fileLayout = new VerticalLayout();
        fileLayout.setWidth("100%");
        fileLayout.setSpacing(true);

        try {
            LienDocInfo docInfo = new LienDocInfo(new URL(attachment.getALink()), controller);
            docInfo.setWidth("100%");
            fileLayout.addComponent(docInfo);
            fileLayout.setComponentAlignment(fileLayout.getComponent(0), Alignment.MIDDLE_LEFT);
        } catch (MalformedURLException ex) {
            Logger.getLogger(AttachmentsFormEditView.class.getName()).log(Level.SEVERE, null, ex);
        }

        TextArea vTXTcomment = new TextArea(controller.getBundle().getString("eds-comnent") + " :");
        vTXTcomment.setWidth("100%");
        vTXTcomment.setMaxLength(1024);
        vTXTcomment.setNullRepresentation("");
        vTXTcomment.setNullSettingAllowed(true);
        vTXTcomment.setValue(attachment.getAComment());
        textAreasMap.put(attachment.getAId(), vTXTcomment);

        Button vBTdelete = new Button(controller.getBundle().getString("eds-delete-btn"), new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                attachmentsToAdd.remove(attachment);
                attachmentsToRemove.add(attachment);
                textAreasMap.remove(attachment.getAId());
                addedFilesLayout.removeComponent(fileLayout);
            }
        });
        vBTdelete.setIcon(REMOVE_ICON);

        fileLayout.addComponent(vTXTcomment);
        fileLayout.addComponent(vBTdelete);
        fileLayout.addComponent(new Label("<hr/>", Label.CONTENT_XHTML));
        fileLayout.setComponentAlignment(vBTdelete, Alignment.MIDDLE_RIGHT);

        return fileLayout;
    }

    /**
     * This method shows link preview
     * 
     * @param link Link to preview
     */
    private void popLinkPreview(String link) {
        Window previewWindow = new Window(controller.getBundle().getString("eds-view"));
        previewWindow.setWidth("100%");

        HorizontalLayout previewLayout = new HorizontalLayout();
        previewLayout.setWidth("100%");
        previewLayout.setHeight("400px");
        Embedded preview = new Embedded(null, new ExternalResource(link));
        preview.setType(Embedded.TYPE_BROWSER);
        preview.setSizeFull();
        previewLayout.addComponent(preview);

        previewWindow.setContent(previewLayout);
        previewWindow.setModal(true);
        previewWindow.setResizable(false);

        controller.getApplication().getMainWindow().addWindow(previewWindow);
        previewWindow.center();
    }
}
