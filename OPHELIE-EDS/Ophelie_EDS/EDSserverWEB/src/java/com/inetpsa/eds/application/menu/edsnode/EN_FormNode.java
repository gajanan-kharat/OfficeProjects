package com.inetpsa.eds.application.menu.edsnode;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.text.Format;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.vaadin.dialogs.ConfirmDialog;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.actionbar.abort.AbortEdsButton;
import com.inetpsa.eds.application.actionbar.abort.I_Abortable;
import com.inetpsa.eds.application.actionbar.abort.UnabortEdsButton;
import com.inetpsa.eds.application.actionbar.affectation.EdsAffectationButton;
import com.inetpsa.eds.application.actionbar.affectation.I_Affectable;
import com.inetpsa.eds.application.actionbar.cancelchange.CancelChangeButton;
import com.inetpsa.eds.application.actionbar.cancelchange.I_Cancelable;
import com.inetpsa.eds.application.actionbar.checkreport.CheckReportWindow;
import com.inetpsa.eds.application.actionbar.checkreport.GenerateEdsCheckReportButton;
import com.inetpsa.eds.application.actionbar.checkreport.I_CheckReport;
import com.inetpsa.eds.application.actionbar.chs.ExportEdsChsButton;
import com.inetpsa.eds.application.actionbar.chs.ExportExcelCHS;
import com.inetpsa.eds.application.actionbar.chs.I_EdsChsExport;
import com.inetpsa.eds.application.actionbar.chs.I_EdsChsSavable;
import com.inetpsa.eds.application.actionbar.chs.SaveEdsChsButton;
import com.inetpsa.eds.application.actionbar.close.CloseEdsButton;
import com.inetpsa.eds.application.actionbar.close.I_Closable;
import com.inetpsa.eds.application.actionbar.discardchange.DiscardChangeButton;
import com.inetpsa.eds.application.actionbar.discardchange.I_Discardable;
import com.inetpsa.eds.application.actionbar.editform.EditFormButton;
import com.inetpsa.eds.application.actionbar.editform.I_Editable;
import com.inetpsa.eds.application.actionbar.export.ExportButton;
import com.inetpsa.eds.application.actionbar.export.I_Exportable;
import com.inetpsa.eds.application.actionbar.exportadminxml.ExportXmlAdminButton;
import com.inetpsa.eds.application.actionbar.exportadminxml.I_XmlAdminExportable;
import com.inetpsa.eds.application.actionbar.exportxml.A_ExportExcel.ExcelExportException;
import com.inetpsa.eds.application.actionbar.exportxml.ExportExcelEDS;
import com.inetpsa.eds.application.actionbar.exportxml.I_XmlExportable;
import com.inetpsa.eds.application.actionbar.freeze.FreezeEdsButton;
import com.inetpsa.eds.application.actionbar.freeze.I_Freezable;
import com.inetpsa.eds.application.actionbar.reconduct.I_Reconductable;
import com.inetpsa.eds.application.actionbar.reconduct.ReconductWithModifButton;
import com.inetpsa.eds.application.actionbar.reconduct.ReconductWithoutModifButton;
import com.inetpsa.eds.application.actionbar.reconsult.I_Reconsultable;
import com.inetpsa.eds.application.actionbar.reconsult.ReconsultButton;
import com.inetpsa.eds.application.actionbar.resetvalidation.I_Resettable;
import com.inetpsa.eds.application.actionbar.resetvalidation.ResetEdsValidationsButton;
import com.inetpsa.eds.application.actionbar.savechange.I_Savable;
import com.inetpsa.eds.application.actionbar.savechange.SaveChangeButton;
import com.inetpsa.eds.application.actionbar.subscribe.I_Subscribable;
import com.inetpsa.eds.application.actionbar.subscribe.SubscribeEdsButton;
import com.inetpsa.eds.application.actionbar.subscribe.UnsubscribeEdsButton;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.application.content.eds.validation.HighValidationFormEditView;
import com.inetpsa.eds.application.content.eds.validation.HighValidationFormReadView;
import com.inetpsa.eds.application.content.eds.validation.LowValidationFormEditView;
import com.inetpsa.eds.application.content.eds.validation.LowValidationFormReadView;
import com.inetpsa.eds.application.popup.ExportWarningWindow;
import com.inetpsa.eds.application.popup.WsTestWindow;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.E_AccessLocked;
import com.inetpsa.eds.dao.model.EdsComportementConsolideFormData;
import com.inetpsa.eds.dao.model.EdsConsolidateCurentFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsHighValidationFormData;
import com.inetpsa.eds.dao.model.EdsLowValidationFormData;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.tools.mail.EdsMailMessageBuilder;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.inetpsa.eds.tools.uri.A_FragmentHandler;
import com.inetpsa.eds.tools.uri.FragmentManager;
import com.inetpsa.eds.ws.model.UserInfo;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.StreamResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

/**
 * Navigation node associated with a form of listing EDS. Has a form of reading and writing.
 * 
 * @author Geometric Ltd.
 */
public class EN_FormNode extends A_EdsNavigationNode implements I_Abortable, I_Cancelable, I_Closable, I_Discardable, I_Editable, I_XmlExportable,
        I_Reconductable, I_Reconsultable, I_Savable, I_Subscribable, I_Freezable, I_Affectable, I_Resettable, I_CheckReport, I_Exportable,
        I_XmlAdminExportable, I_EdsChsSavable, I_EdsChsExport {
    /**
     * constant hold value of Warning logo
     */
    private static final Resource WARNING_LOGO = ResourceManager.getInstance().getThemeIconResource("icons/warning.png");

    /** Resources of the XSLT file to transform EDS xml to HTML */
    private static final String XSLT_EDS_TO_HTML = "/EDS.xsl";

    /** Resources of the XSLT file to transform EDS xml to simplified xml */
    private static final String XSLT_EDS_TO_SIMPLIFIED_XML = "/EDS_SIMPLIFY.xsl";

    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     * @param readForm Read form of the EDS.
     * @param editForm Edit form of the EDS.
     * @param editRight EDS write access.
     */
    public EN_FormNode(EdsApplicationController controller, A_EdsReadForm readForm, A_EdsEditForm editForm, String editRight) {
        super(controller);
        this.readForm = readForm;
        this.editForm = editForm;
        this.editRight = editRight;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#areChildrenAllowed()
     */
    @Override
    public boolean areChildrenAllowed() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#getCaption()
     */
    @Override
    public String getCaption() {
        return controller.getBundle().getString(readForm.getFormName());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#getURIfragment()
     */
    @Override
    public String getURIfragment() {
        return A_FragmentHandler.EDS_FRAGMENT_KEY + "&ref=" + readForm.getEdsRef();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#isSelectable()
     */
    @Override
    public boolean isSelectable() {
        return true;
    }

    /**
     * This method is used to retrieve the EDS details.
     * 
     * @return EDS details.
     */
    public EdsEds getEds() {
        return readForm.getEds();
    }

    /**
     * This method gets called whenever the node is visited. This method displays the EDS details based on the state of the EDS in read mode.
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#onEnter()
     */
    @Override
    public void onEnter() {
        controller.getUriFragmentUtility().setFragment(FragmentManager.formatURLFragmentForEDS(getEds()), false);

        switch (readForm.getEds().getEdsState()) {
        case EdsEds.DEFAULT_STATE:
            rebuildIdleActionBar();
            getController().setContent(readForm);
            controller.setReadForm(readForm);
            refreshModel();
            readForm.refreshViewData();
            break;
        case EdsEds.FROZEN_STATE:
            controller.getActionBar().clear();
            getController().getActionBar().addRightButton(getCloseEdsButton());
            getController().setContent(readForm);
            controller.setReadForm(readForm);
            // refreshModel();
            readForm.refreshViewData();
            break;
        case EdsEds.ABORTED_STATE:
            controller.getActionBar().clear();
            getController().getActionBar().addRightButton(getCloseEdsButton());
            if (controller.userHasSufficientRights(EdsRight.APP_PROJECT_MENU_RECOVER_EDS)) {
                getController().getActionBar().addLeftButton(getUnabortEdsButton());
            }
            getController().setContent(readForm);
            controller.setReadForm(readForm);
            // refreshModel();
            readForm.refreshViewData();
            break;
        default:
            throw new IllegalStateException("Forbidden value for eds.edsState: " + readForm.getEds().getEdsState());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#onExit()
     */
    @Override
    public void onExit() {
        EDSdao.getInstance().freeFormAccess(getController().getAuthenticatedUser(), getEds().getEdsId(), readForm.getID());

        getController().getActionBar().clear();
        getController().getContent().removeAllComponents();
    }

    /**
     * This method should allow to switch between editing mode to read-only mode without saving changes.
     * 
     * @see com.inetpsa.eds.application.actionbar.cancelchange.I_Cancelable#cancel()
     */
    public void cancel() {
        EDSdao.getInstance().freeFormAccess(getController().getAuthenticatedUser(), getEds().getEdsId(), readForm.getID());

        refreshModel();
        this.editForm.discardChanges();
        getController().setContent(readForm);
        readForm.refreshViewData();
        getController().getActionBar().clear();

        rebuildIdleActionBar();
    }

    /**
     * This method should reset an edit form with the initial values​​
     * 
     * @see com.inetpsa.eds.application.actionbar.discardchange.I_Discardable#discard()
     */
    public void discard() {
        refreshModel();
        this.editForm.discardChanges();
    }

    /**
     * This method must changes the read-only mode to edit mode.
     * 
     * @see com.inetpsa.eds.application.actionbar.editform.I_Editable#edit()
     */
    public void edit() {
        try {
            EDSdao.getInstance().lockFormAccess(getController().getAuthenticatedUser(), getEds().getEdsId(), readForm.getID());

            getController().setContent(editForm);
            editForm.prepare();
            refreshModel();
            editForm.discardChanges();
            getController().getActionBar().clear();
            getController().getActionBar().addLeftButton(getSaveButton());
            getController().getActionBar().addLeftButton(getDiscardButton());
            getController().getActionBar().addLeftButton(getCancelButton());

        } catch (E_AccessLocked ex) {
            getController().showError(controller.getBundle().getString("form-verouille"),
                    MessageFormat.format(controller.getBundle().getString("form-fiche-verouille-message"), ex.getUser().toPSAIdentity()));
        }
    }

    /**
     * This method commits edit forms changes.
     * 
     * @see com.inetpsa.eds.application.actionbar.savechange.I_Savable#save()
     */
    public void save() {

        if (editForm instanceof LowValidationFormEditView || editForm instanceof HighValidationFormEditView) {
            if (editForm instanceof HighValidationFormEditView) {
                this.oldStage = ((HighValidationFormReadView) readForm).getFormData().getValidationStage();
            } else if (editForm instanceof LowValidationFormEditView) {
                this.oldStage = ((LowValidationFormReadView) readForm).getFormData().getValidationStage();
            }
            int isValid = editForm.isValidEDS();
            if (isValid == 0) {
                if (editForm.commitChanges()) {
                    rebuildReadform();
                }
            } else if (isValid == -1) {
                final Window warningWindow = new Window(controller.getBundle().getString("pop-warning-title"));
                VerticalLayout waringLayout = new VerticalLayout();
                warningWindow.setWidth("35%");
                // warningWindow.setHeight("25%");
                // waringLayout.setSpacing(true);
                HorizontalLayout messageLayout = new HorizontalLayout();
                Embedded vIMGtitle = new Embedded(null, WARNING_LOGO);
                messageLayout.addComponent(vIMGtitle);
                messageLayout.setComponentAlignment(vIMGtitle, Alignment.MIDDLE_LEFT);
                Label message = new Label();
                message.setCaption(controller.getBundle().getString("Validation-message-8"));
                messageLayout.addComponent(message);
                messageLayout.setComponentAlignment(message, Alignment.MIDDLE_CENTER);
                waringLayout.addComponent(messageLayout);

                HorizontalLayout buttonLayout = new HorizontalLayout();
                buttonLayout.setSpacing(true);

                Button cancelButton = new Button(controller.getBundle().getString("button-cancel"), new Button.ClickListener() {
                    public void buttonClick(ClickEvent event) {
                        controller.getApplication().getMainWindow().removeWindow(warningWindow);
                    }
                });

                Button continueButton = new Button(controller.getBundle().getString("Button-Continue"), new Button.ClickListener() {
                    public void buttonClick(ClickEvent event) {
                        if (editForm.commitChanges()) {

                            rebuildReadform();
                            if (readForm.getEds().getEdsValidLvl() == EdsEds.VALIDATION_LVL_HIGH) {
                                int newStage = ((HighValidationFormReadView) readForm).getFormData().getValidationStage();
                                if (newStage > oldStage) {
                                    validateStageAndVersion(((HighValidationFormReadView) readForm).getFormData(), newStage);
                                }
                            } else if (readForm.getEds().getEdsValidLvl() == EdsEds.VALIDATION_LVL_LOW) {
                                int newStage = ((LowValidationFormReadView) readForm).getFormData().getValidationStage();
                                if (newStage > oldStage) {
                                    validateStageAndVersion(((LowValidationFormReadView) readForm).getFormData(), newStage);
                                }
                            } else {
                                throw new RuntimeException(controller.getBundle().getString("app-eds-validate-inconnu")
                                        + readForm.getEds().getEdsValidLvl());
                            }
                        }
                        controller.getApplication().getMainWindow().removeWindow(warningWindow);
                    }
                });

                buttonLayout.addComponent(cancelButton);

                buttonLayout.addComponent(continueButton);

                waringLayout.addComponent(buttonLayout);
                waringLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
                warningWindow.addComponent(waringLayout);
                warningWindow.setModal(true);
                controller.getApplication().getMainWindow().addWindow(warningWindow);
                warningWindow.center();
            }
        } else {
            if (editForm.isValid()) {
                if (editForm.commitChanges()) {
                    rebuildReadform();
                }
            }
        }
    }

    /**
     * This method changes edit mode to display/read mode after recording changes .
     */
    private void rebuildReadform() {
        EDSdao.getInstance().freeFormAccess(getController().getAuthenticatedUser(), getEds().getEdsId(), readForm.getID());

        for (Object object : editForm.getAllItemsToSave()) {
            EDSdao.getInstance().mergeDetachedDBObject(object);
        }

        for (Object object : editForm.getAllItemsToDelete()) {
            EDSdao.getInstance().deleteDetachedDBObject(object);
        }

        getController().setContent(readForm);
        refreshModel();
        readForm.refreshViewData();

        rebuildIdleActionBar();

        // //Pas d'envoi si formulaire de validation
        // if ( !readForm.getID().equals( HighValidationFormBuilder.ID )
        // )
        // {
        controller.sendNotificationMail(readForm.getID(), readForm.getEds(), EdsMailMessageBuilder.EDS_UPDATED);
        // }
    }

    /**
     * This method is used to abort EDS without saving changes.
     * 
     * @see com.inetpsa.eds.application.actionbar.abort.I_Abortable#abort()
     */
    public void abort() {

        try {
            EDSdao.getInstance().lockEdsAccess(getController().getAuthenticatedUser(), getEds().getEdsId());

            ConfirmDialog.show(getController().getUserWindow(), controller.getBundle().getString("current-conso-tab-mass-rob-Alert-title"),
                    controller.getBundle().getString("form-message-abandon"), controller.getBundle().getString("consolid-qcf-oui"), controller
                            .getBundle().getString("consolid-qcf-non"), new ConfirmDialog.Listener() {
                        public void onClose(ConfirmDialog cd) {
                            if (cd.isConfirmed()) {
                                getController().abortEds(getEds());
                                getController().getApplication().getMainWindow()
                                        .showNotification(controller.getBundle().getString("eds-version-fiche-abandonnee"));
                            }

                            EDSdao.getInstance().freeEdsAccess(getController().getAuthenticatedUser(), getEds().getEdsId());
                        }
                    });
        } catch (E_AccessLocked ex) {
            getController().showError(controller.getBundle().getString("form-fiche-verouille"),
                    MessageFormat.format(controller.getBundle().getString("form-fiche-verouille-message"), ex.getUser().toPSAIdentity()));
        }
    }

    /**
     * This method is used to abort EDS and is restored.
     * 
     * @see com.inetpsa.eds.application.actionbar.abort.I_Abortable#unabort()
     */
    public void unabort() {
        ConfirmDialog.show(getController().getUserWindow(), controller.getBundle().getString("current-conso-tab-mass-rob-Alert-title"), controller
                .getBundle().getString("form-message-retablir"), controller.getBundle().getString("consolid-qcf-oui"), controller.getBundle()
                .getString("consolid-qcf-non"), new ConfirmDialog.Listener() {
            public void onClose(ConfirmDialog cd) {
                if (cd.isConfirmed()) {
                    getController().unabortEds(getEds());
                    getController().getApplication().getMainWindow().showNotification(controller.getBundle().getString("form-fiche-retablie"));
                }
            }
        });
    }

    /**
     * This method is used to close the EDS.
     * 
     * @see com.inetpsa.eds.application.actionbar.close.I_Closable#close()
     */
    public void close() {
        getController().closeEds(readForm.getEds());
    }

    /**
     * This method is responsible for making a renewal with modification
     * 
     * @see com.inetpsa.eds.application.actionbar.reconduct.I_Reconductable#reconductWithModif()
     */
    public void reconductWithModif() {
        try {
            EDSdao.getInstance().lockEdsAccess(getController().getAuthenticatedUser(), getEds().getEdsId());

            getController().queryReconductionWithModif(readForm, editForm);
        } catch (E_AccessLocked ex) {
            getController().showError(controller.getBundle().getString("form-fiche-verouille"),
                    MessageFormat.format(controller.getBundle().getString("form-fiche-verouille-message"), ex.getUser().toPSAIdentity()));
        }
    }

    /**
     * This method is responsible for making a renewal without modification
     * 
     * @see com.inetpsa.eds.application.actionbar.reconduct.I_Reconductable#reconductWithoutModif()
     */
    public void reconductWithoutModif() {
        try {
            EDSdao.getInstance().lockEdsAccess(getController().getAuthenticatedUser(), getEds().getEdsId());

            getController().queryReconductionWithoutModif(readForm, editForm);
        } catch (E_AccessLocked ex) {
            getController().showError(controller.getBundle().getString("form-fiche-verouille"),
                    MessageFormat.format(controller.getBundle().getString("form-fiche-verouille-message"), ex.getUser().toPSAIdentity()));
        }
    }

    /**
     * This method is responsible for initiating a re-consultation.
     * 
     * @see com.inetpsa.eds.application.actionbar.reconsult.I_Reconsultable#reconsult()
     */
    public void reconsult() {
        try {
            EDSdao.getInstance().lockEdsAccess(getController().getAuthenticatedUser(), getEds().getEdsId());

            getController().queryReconsultation(readForm.getEds());
        } catch (E_AccessLocked ex) {
            getController().showError(controller.getBundle().getString("form-fiche-verouille"),
                    MessageFormat.format(controller.getBundle().getString("form-fiche-verouille-message"), ex.getUser().toPSAIdentity()));
        }
    }

    /**
     * This method is used to subscribe to a EDS form.
     * 
     * @see com.inetpsa.eds.application.actionbar.subscribe.I_Subscribable#subscribe()
     */
    public void subscribe() {
        // Check if the user has a valid mail address
        if (null == controller.getAuthenticatedUser().getConfig().getMailAddress()) {
            controller.showError(controller.getBundle().getString("form-abonner-error-title"),
                    controller.getBundle().getString("form-abonner-error-message"));
        } else {
            ConfirmDialog.show(controller.getApplication().getMainWindow(), controller.getBundle()
                    .getString("current-conso-tab-mass-rob-Alert-title"), controller.getBundle().getString("form-message-abonner"), controller
                    .getBundle().getString("consolid-qcf-oui"), controller.getBundle().getString("consolid-qcf-non"), new ConfirmDialog.Listener() {
                public void onClose(ConfirmDialog cd) {
                    if (cd.isConfirmed()) {
                        controller.subscribeToEds(readForm.getEds().getEdsRef());
                        rebuildIdleActionBar();
                    }
                }
            });
        }
    }

    /**
     * This method is used to un-subscribe to a EDS form.
     * 
     * @see com.inetpsa.eds.application.actionbar.subscribe.I_Subscribable#unsubscribe()
     */
    public void unsubscribe() {
        ConfirmDialog.show(controller.getApplication().getMainWindow(), controller.getBundle().getString("current-conso-tab-mass-rob-Alert-title"),
                controller.getBundle().getString("form-message-desabonner"), controller.getBundle().getString("consolid-qcf-oui"), controller
                        .getBundle().getString("consolid-qcf-non"), new ConfirmDialog.Listener() {
                    public void onClose(ConfirmDialog cd) {
                        if (cd.isConfirmed()) {
                            controller.unsubscribeFromEds(readForm.getEds().getEdsRef());
                            rebuildIdleActionBar();
                        }
                    }
                });
    }

    /**
     * This method is used to assign the EDS to a specific user.
     * 
     * @see com.inetpsa.eds.application.actionbar.affectation.I_Affectable#affect()
     */
    public void affect() {
        getController().queryAffectation(readForm.getEds());
    }

    @Override
    public void showExport() {

        final Window subWindow = new Window("Export");
        Label label = new Label("Export type : ");
        Button buttonXml = new Button("XML");
        Button buttonExcel = new Button("Excel");

        buttonXml.addListener(new ClickListener() {

            private static final long serialVersionUID = 4316184801919825007L;

            @Override
            public void buttonClick(ClickEvent event) {
                exportXml();
                subWindow.getParent().removeWindow(subWindow);
            }
        });

        buttonExcel.addListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                subWindow.getParent().removeWindow(subWindow);

                try {
                    // Lock the EDS dao
                    EDSdao.getInstance().lockEdsAccess(getController().getAuthenticatedUser(), getEds().getEdsId());

                    // File template
                    File template = new File(EN_FormNode.class.getClassLoader().getResource("/templates/template.xlsm").getFile());

                    if (!template.exists()) {
                        getController().showError("Export error", "Impossible de trouver le fichier de template");
                        return;
                    }

                    final ByteArrayOutputStream dest = new ByteArrayOutputStream();

                    // Export
                    new ExportExcelEDS(template, dest, controller, getEds()).write();

                    // Response stream
                    Format formatter = new SimpleDateFormat(" dd-MM-yyyy HH-mm-ss");
                    StreamResource sr = new StreamResource(new StreamResource.StreamSource() {

                        private static final long serialVersionUID = 5070826190728599139L;

                        @Override
                        public InputStream getStream() {
                            return new ByteArrayInputStream(dest.toByteArray());
                        }
                    }, getEds().getEdsName() + formatter.format(new Date()) + ".xlsm", controller.getApplication());

                    sr.setCacheTime(5000);
                    sr.setMIMEType("application/octet-stream");
                    getController().getApplication().getMainWindow().open(sr, "_top");

                } catch (IOException e) {
                    getController().showError("Export error", "Erreur d'écriture à la génération du fichier");
                    e.printStackTrace();
                } catch (ExcelExportException e) {
                    getController().showError("Export error", "Erreur d'export à la génération du fichier");
                    e.printStackTrace();
                } catch (E_AccessLocked e1) {
                    getController().showError("Export error", "Erreur d'accès à la ressource EDS à la génération du fichier");
                    e1.printStackTrace();
                } catch (InvalidFormatException e) {
                    getController().showError("Export error", "Format du template invalide");
                    e.printStackTrace();
                } finally {
                    EDSdao.getInstance().freeEdsAccess(controller.getAuthenticatedUser(), getEds().getEdsId());
                }
            }
        });

        HorizontalLayout layout = new HorizontalLayout();
        layout.addComponent(buttonXml);
        layout.addComponent(buttonExcel);

        subWindow.addComponent(label);
        subWindow.addComponent(layout);

        subWindow.center();
        subWindow.setModal(true);
        controller.getApplication().getMainWindow().addWindow(subWindow);

        // window.setModal(true);
        // controller.getApplication().getMainWindow().addWindow(window);
        // window.center();
        //
        // EDSdao.getInstance().freeEdsAccess(controller.getAuthenticatedUser(), getEds().getEdsId());
        // } catch (E_AccessLocked ex) {
        // getController().showError(controller.getBundle().getString("form-fiche-verouille"),
        // MessageFormat.format(controller.getBundle().getString("form-fiche-verouille-message"), ex.getUser().toPSAIdentity()));
    }

    /**
     * This method is responsible for exporting data in XML format
     * 
     * @see com.inetpsa.eds.application.actionbar.exportxml.I_XmlExportable#exportXml()
     */
    public void exportXml() {

        try {
            EDSdao.getInstance().lockEdsAccess(getController().getAuthenticatedUser(), getEds().getEdsId());

            WsTestWindow window = new WsTestWindow(controller, WsTestWindow.EXPORT_EDS, getEds().getEdsRef());
            window.setModal(true);
            controller.getApplication().getMainWindow().addWindow(window);
            window.center();

            EDSdao.getInstance().freeEdsAccess(controller.getAuthenticatedUser(), getEds().getEdsId());
        } catch (E_AccessLocked ex) {
            getController().showError(controller.getBundle().getString("form-fiche-verouille"),
                    MessageFormat.format(controller.getBundle().getString("form-fiche-verouille-message"), ex.getUser().toPSAIdentity()));
        }

        // Code pour proposer de télécharger un fichier xml contenant "response"
        // final PipedInputStream in = new PipedInputStream();
        // SwingUtilities.invokeLater( new Runnable()
        // {
        // public void run()
        // {
        // OutputStreamWriter writer = null;
        // try
        // {
        // writer = new OutputStreamWriter( new PipedOutputStream( in ) ,
        // "UTF-8" );
        // writer.write( response );
        // }
        // catch( IOException ex )
        // {
        // }
        // finally
        // {
        // try
        // {
        // writer.close();
        // }
        // catch( IOException ex )
        // {
        // Logger.getLogger( EN_FormNode.class.getName() ).log( Level.SEVERE ,
        // null ,
        // ex );
        // }
        // }
        //
        // }
        // } );
        //
        // StreamResource.StreamSource streamSource = new StreamResource.StreamSource()
        // {
        // public InputStream getStream()
        // {
        // return in;
        // }
        // };
        // StreamResource streamResource = new StreamResource( streamSource ,
        // getEds().getEdsRef() + ".xml" ,
        // getController().getApplication() );
        //
        // streamResource.setCacheTime(
        // 5000 );
        // streamResource.setMIMEType(
        // "application/octet-stream" );
        // getController().getApplication().getMainWindow().open( streamResource ,
        // "_top" );
    }

    /**
     * This method is responsible for exporting data in XML format for admin user
     * 
     * @see com.inetpsa.eds.application.actionbar.exportadminxml.I_XmlAdminExportable#exportAdminXml()
     */
    public void exportAdminXml() {
        try {
            EDSdao.getInstance().lockEdsAccess(getController().getAuthenticatedUser(), getEds().getEdsId());

            ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
            Client client = Client.create(config);

            String userName = controller.getCookieByID(EdsApplicationController.USERNAME_COOKIE);
            String password = controller.getCookieByID(EdsApplicationController.PASSWORD_COOKIE);

            String tokenID = login(new UserInfo(userName, password));

            String resourceUrl = controller.getApplication().getURL() + "resources/";
            WebResource webResource = client.resource(resourceUrl).path("eds");
            webResource = webResource.path("admin");

            String response = webResource.queryParam("token-id", tokenID).queryParam("eds-ref", getEds().getEdsRef()).get(String.class);
            // Add the stylesheet information to the xml
            int xmTagEndIndex = response.indexOf(">");
            String xmlTagString = response.substring(0, xmTagEndIndex + 1);
            String xmlDataString = response.substring(xmTagEndIndex + 1);
            response = xmlTagString + "<?xml-stylesheet type=\"text/xsl\" href=\"EDS.xsl\"?>" + xmlDataString;
            int stage = controller.retrieveEdsStage(getEds(), getEds().getEdsProject());
            int stageData = stage - 1;
            String stageText = controller.getStageTextEn(stageData);
            BufferedWriter bw = null;

            String fileName = controller.getEdsConfRessourceBundle().getString("server_xml_report_path") + "RefEDS_" + stage + getEds().getEdsRef()
                    + "_" + stageText;

            // write html and simplified xml files
            String exportHTMLFilePath = fileName + ".html";
            transform(response, XSLT_EDS_TO_HTML, exportHTMLFilePath);
            String exportXMLFilePath = fileName + ".xml";
            transform(response, XSLT_EDS_TO_SIMPLIFIED_XML, exportXMLFilePath);

            logout(tokenID);

            ExportWarningWindow window = new ExportWarningWindow(controller);
            window.setModal(true);
            controller.getApplication().getMainWindow().addWindow(window);
            window.center();

            EDSdao.getInstance().freeEdsAccess(controller.getAuthenticatedUser(), getEds().getEdsId());

        } catch (E_AccessLocked ex) {
            getController().showError(controller.getBundle().getString("form-fiche-verouille"),
                    MessageFormat.format(controller.getBundle().getString("form-fiche-verouille-message"), ex.getUser().toPSAIdentity()));
        }

    }

    /**
     * Transforms the input xml file to the output file using a XSLT transformation with the specified xslt file
     * 
     * @param inputFilePath the xml content in a string
     * @param xsltResources the xslt file resources in the classpath
     * @param outputFilePath the output file
     * @throws TransformerException If an unrecoverable error occurs during the course of the transformation.
     */
    private void transform(String inputXMLString, String xsltResources, String outputFilePath) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            StreamSource xslStream = new StreamSource(EN_FormNode.class.getResourceAsStream(xsltResources));
            Transformer transformer = factory.newTransformer(xslStream);
            StreamSource in = new StreamSource(new StringReader(inputXMLString));
            StreamResult out = new StreamResult(new File(outputFilePath));
            transformer.transform(in, out);
        } catch (TransformerException e) {
            Logger.getLogger(EN_FormNode.class.getName()).log(Level.SEVERE, "Unable to generate PLM export admin file : " + outputFilePath, e);
            controller.getApplication().getMainWindow()
                    .showNotification(controller.getBundle().getString("file-download-error"), Notification.TYPE_ERROR_MESSAGE);
        }
    }

    /**
     * This method used to Freeze the EDS and do major revision
     * 
     * @see com.inetpsa.eds.application.actionbar.freeze.I_Freezable#freeze()
     */
    public void freeze() {
        try {
            EDSdao.getInstance().lockEdsAccess(getController().getAuthenticatedUser(), getEds().getEdsId());

            controller.queryFreezeEds(readForm.getEds());

        } catch (E_AccessLocked ex) {
            getController().showError(controller.getBundle().getString("form-fiche-verouille"),
                    MessageFormat.format(controller.getBundle().getString("form-fiche-verouille-message"), ex.getUser().toPSAIdentity()));
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.eds.application.actionbar.resetvalidation.I_Resettable#reset()
     */
    @Override
    public void reset() {
        ConfirmDialog.show(controller.getApplication().getMainWindow(), controller.getBundle().getString("confirm-reset-pop"), controller.getBundle()
                .getString("confirm-reset-pop-msg"), controller.getBundle().getString("consolid-qcf-oui"),
                controller.getBundle().getString("consolid-qcf-non"), new ConfirmDialog.Listener() {
                    public void onClose(ConfirmDialog cd) {
                        if (cd.isConfirmed()) {

                            EDSdao dao = EDSdao.getInstance();
                            EdsEds oldVersion = readForm.getEds();
                            EdsEds newVersionEds = dao.copyEdsForMajorVersion(oldVersion, controller.getAuthenticatedUser());

                            controller.closeEds(oldVersion);
                            controller.openEds(newVersionEds);

                            if (readForm.getEds().getEdsValidLvl() == EdsEds.VALIDATION_LVL_LOW) {

                                dao.deleteDetachedDBObject(dao.getFormData(newVersionEds.getEdsId(), EdsLowValidationFormData.class));

                            } else if (readForm.getEds().getEdsValidLvl() == EdsEds.VALIDATION_LVL_HIGH) {

                                dao.deleteDetachedDBObject(dao.getFormData(newVersionEds.getEdsId(), EdsHighValidationFormData.class));
                            }

                            controller.openEds(newVersionEds);
                            getController().setContent(readForm);
                            refreshModel();
                            readForm.refreshViewData();

                        }
                    }
                });

    }

    @Override
    public void generateCheckReport() {

        Window subWindow = new CheckReportWindow(controller, (EdsConsolidateCurentFormData) controller.getFormDataModel(readForm.getEds(),
                EdsConsolidateCurentFormData.class), (EdsComportementConsolideFormData) controller.getFormDataModel(readForm.getEds(),
                EdsComportementConsolideFormData.class));
        subWindow.center();
        subWindow.setModal(true);
        subWindow.setHeight("90%");
        subWindow.setWidth("70%");
        controller.getApplication().getMainWindow().addWindow(subWindow);
    }

    // PROTECTED
    /**
     * Variable to store the read form of EDS.
     */
    protected A_EdsReadForm readForm;
    /**
     * Variable to store the edit form of EDS.
     */
    protected A_EdsEditForm editForm;
    /**
     * EDS write access.
     */
    protected String editRight;

    /**
     * This method is used to re-build the action bar, based on the edit right of the user.
     */
    protected void rebuildIdleActionBar() {
        getController().getActionBar().clear();

        if (controller.userHasSufficientRights(editRight)) {
            getController().getActionBar().addLeftButton(getEditButton());
        }

        if (controller.userHasSufficientRights(EdsRight.APP_DASHBOARD_MENU_ABORT_EDS)) {
            getController().getActionBar().addLeftButton(getAbortEdsButton());
        }

        if (controller.userHasSufficientRights(EdsRight.APP_EDS_FREEZE_EDS)) {
            getController().getActionBar().addLeftButton(getvBTversion());
        }
        if (controller.userHasSufficientRights(EdsRight.APP_EDS_SUBSCRIBE_EDS)) {
            if (!controller.userHasSubscribed(readForm.getEds())) {
                getController().getActionBar().addLeftButton(getSubscribeEdsButton());
            } else {
                getController().getActionBar().addLeftButton(getUnsubscribeEdsButton());
            }
        }
        if (controller.userHasSufficientRights(EdsRight.APP_EDS_RECONDUCT_EDS_WITHOUT_MODIF)) {
            getController().getActionBar().addLeftButton(getReconductWithoutModifButton());
        }
        if (controller.userHasSufficientRights(EdsRight.APP_EDS_RECONDUCT_EDS_WITH_MODIF)) {
            getController().getActionBar().addLeftButton(getReconductWithModifButton());
        }
        if (controller.userHasSufficientRights(EdsRight.APP_EDS_RECONSULT_EDS)) {
            getController().getActionBar().addLeftButton(getReconsultButton());
        }
        if (controller.userHasSufficientRights(EdsRight.APP_DASHBOARD_MENU_EXPORT_EDS)) {
            getController().getActionBar().addLeftButton(getvBTtestWS());
        }
        if (controller.userHasSufficientRights(EdsRight.APP_DASHBOARD_MENU_EXPORT_EDS)) {
            getController().getActionBar().addLeftButton(getvBTexportfc());
        }
        if (controller.userHasSufficientRights(EdsRight.APP_GENERAL_BE_EDS_AFFECTATION)) {
            getController().getActionBar().addLeftButton(getAffectationButton());
        }
        if (controller.userHasSufficientRights(EdsRight.APP_EDS_RESET_EDS)) {
            getController().getActionBar().addLeftButton(getvBTreset());
        }
        if (controller.userHasSufficientRights(EdsRight.APP_EDS_CHECK_REPORT) && this instanceof EN_ValidationNode) {
            getController().getActionBar().addLeftButton(getCheckReportButton());
        }
        int stage = controller.retrieveEdsStage(getEds(), getEds().getEdsProject());
        if (controller.userHasSufficientRights(EdsRight.APP_EDS_MENU_EXPORT_PLM_TAB_VIEW) && stage >= 1) {
            getController().getActionBar().addLeftButton(getvBTAdmintestWS());
        }

        getController().getActionBar().addRightButton(getCloseEdsButton());
    }

    /**
     * This method is used to retrieve the save button.
     * 
     * @return Save button.
     */
    protected SaveChangeButton getSaveButton() {
        if (vBTsave == null) {
            vBTsave = new SaveChangeButton(this, controller);
        }
        return vBTsave;
    }

    /**
     * This method is used to retrieve the cancel button.
     * 
     * @return Cancel button.
     */
    protected CancelChangeButton getCancelButton() {
        if (vBTcancel == null) {
            vBTcancel = new CancelChangeButton(this, controller);
        }
        return vBTcancel;
    }

    /**
     * This method is used to retrieve the edit button.
     * 
     * @return Edit button.
     */
    protected EditFormButton getEditButton() {
        if (vBTedit == null) {
            vBTedit = new EditFormButton(this, controller);
        }
        return vBTedit;
    }

    /**
     * This method is used to retrieve the discard button.
     * 
     * @return Discard button.
     */
    protected DiscardChangeButton getDiscardButton() {
        if (vBTdiscard == null) {
            vBTdiscard = new DiscardChangeButton(this, controller);
        }
        return vBTdiscard;
    }

    /**
     * This method is used to retrieve the abort button.
     * 
     * @return Abort button.
     */
    protected AbortEdsButton getAbortEdsButton() {
        if (vBTabortEDS == null) {
            vBTabortEDS = new AbortEdsButton(this, controller);
        }
        return vBTabortEDS;
    }

    /**
     * This method is used to retrieve the un-abort button.
     * 
     * @return Un-abort button.
     */
    protected UnabortEdsButton getUnabortEdsButton() {
        if (vBTunabortEDS == null) {
            vBTunabortEDS = new UnabortEdsButton(this, controller);
        }
        return vBTunabortEDS;
    }

    /**
     * This method is used to retrieve the 'Re-conduct without modification' button.
     * 
     * @return 'Re-conduct without modification' button.
     */
    protected ReconductWithoutModifButton getReconductWithoutModifButton() {
        if (vBTreconductWithoutModif == null) {
            vBTreconductWithoutModif = new ReconductWithoutModifButton(this, controller);
        }
        return vBTreconductWithoutModif;
    }

    /**
     * This method is used to retrieve the 'Re-conduct with modification' button.
     * 
     * @return 'Re-conduct with modification' button.
     */
    protected ReconductWithModifButton getReconductWithModifButton() {
        if (vBTreconductWithModif == null) {
            vBTreconductWithModif = new ReconductWithModifButton(this, controller);
        }
        return vBTreconductWithModif;
    }

    /**
     * This method is used to retrieve the Re-consult button.
     * 
     * @return Re-consult button.
     */
    protected ReconsultButton getReconsultButton() {
        if (vBTreconsult == null) {
            vBTreconsult = new ReconsultButton(this, controller);
        }
        return vBTreconsult;
    }

    /**
     * This method is used to retrieve the Close button.
     * 
     * @return Close button.
     */
    protected CloseEdsButton getCloseEdsButton() {
        if (vBTclose == null) {
            vBTclose = new CloseEdsButton(this, controller);
        }
        return vBTclose;
    }

    /**
     * This method is used to retrieve the Subscribe button.
     * 
     * @return Subscribe button.
     */
    protected SubscribeEdsButton getSubscribeEdsButton() {
        if (vBTsubscribe == null) {
            vBTsubscribe = new SubscribeEdsButton(this, controller);
        }
        return vBTsubscribe;
    }

    /**
     * This method is used to retrieve the Unsubscribe button.
     * 
     * @return Unsubscribe button.
     */
    protected UnsubscribeEdsButton getUnsubscribeEdsButton() {
        if (vBTunsubscribe == null) {
            vBTunsubscribe = new UnsubscribeEdsButton(this, controller);
        }
        return vBTunsubscribe;
    }

    /**
     * This method is used to retrieve the Export button.
     * 
     * @return Export button.
     */
    protected ExportButton getvBTtestWS() {
        if (vBTtestWS == null) {
            vBTtestWS = new ExportButton(this, controller);
        }
        return vBTtestWS;
    }

    protected ExportEdsChsButton getvBTexportfc() {
        if (vBTexportfc == null) {
            vBTexportfc = new ExportEdsChsButton(this, controller);
        }
        return vBTexportfc;
    }

    /**
     * This method is used to retrieve the ExportXmlAdmin button.
     * 
     * @return ExportXmlAdmin button.
     */
    protected ExportXmlAdminButton getvBTAdmintestWS() {
        if (vBTAdmintestWS == null) {
            vBTAdmintestWS = new ExportXmlAdminButton(this, controller);
        }
        return vBTAdmintestWS;
    }

    /**
     * This method is used to retrieve the Freeze button.
     * 
     * @return Freeze button.
     */
    protected FreezeEdsButton getvBTversion() {
        if (vBTversion == null) {
            vBTversion = new FreezeEdsButton(this, controller);
        }
        return vBTversion;
    }

    protected ResetEdsValidationsButton getvBTreset() {
        if (vBTreset == null) {
            vBTreset = new ResetEdsValidationsButton(this, controller);
        }

        return vBTreset;
    }

    protected GenerateEdsCheckReportButton getCheckReportButton() {
        if (vBTcheckReport == null) {
            vBTcheckReport = new GenerateEdsCheckReportButton(this, controller);
        }

        return vBTcheckReport;
    }

    /**
     * This method is used to retrieve the Assign button.
     * 
     * @return Assign button.
     */
    protected EdsAffectationButton getAffectationButton() {
        if (vBTaffectation == null) {
            vBTaffectation = new EdsAffectationButton(this, controller);
        }
        return vBTaffectation;
    }

    /**
     * This method is used to retrieve a button to save the Association FC
     * 
     * @return Save button.
     */
    protected SaveEdsChsButton getEdsChsSaveButton() {
        if (vBTsaveEdsChs == null) {
            vBTsaveEdsChs = new SaveEdsChsButton(this, controller);
        }
        return vBTsaveEdsChs;
    }

    /**
     * This method is used by web-service. This method is used for login.
     * 
     * @param userInfo User details of the user to login.
     * @return Unique token id for logged in user.
     */
    protected String login(UserInfo userInfo) {
        ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        Client client = Client.create(config);
        WebResource webResource = client.resource(getController().getApplication().getURL() + "resources").path("authenticate");

        WebResource loginResource = webResource.path("login");

        String token = loginResource.type(MediaType.APPLICATION_JSON).post(String.class, userInfo);
        client.destroy();

        return token;
    }

    /**
     * This method is used by web-service. This method is used for logout.
     * 
     * @param tokenID Token ID of logged in user.
     */
    protected void logout(String tokenID) {
        ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        Client client = Client.create(config);
        WebResource webResource = client.resource(getController().getApplication().getURL() + "resources").path("authenticate");

        WebResource logoutResource = webResource.path("logout");
        client.destroy();
    }

    /**
     * This method is used to refresh both read and edit form.
     */
    protected void refreshModel() {
        readForm.refreshItems();
        if (editForm != null) {
            editForm.refreshItems();
        }
    }

    // PRIVATE
    /**
     * Variable to store Save button.
     */
    private SaveEdsChsButton vBTsaveEdsChs;
    /**
     * Variable to store Save button.
     */
    private SaveChangeButton vBTsave;
    /**
     * Variable to store Edit button.
     */
    private EditFormButton vBTedit;
    /**
     * Variable to store Discard button.
     */
    private DiscardChangeButton vBTdiscard;
    /**
     * Variable to store Cancel button.
     */
    private CancelChangeButton vBTcancel;
    /**
     * Variable to store Abort button.
     */
    private AbortEdsButton vBTabortEDS;
    /**
     * Variable to store Un abort button.
     */
    private UnabortEdsButton vBTunabortEDS;
    /**
     * Variable to store 'Re-conduct with modification' button.
     */
    private ReconductWithoutModifButton vBTreconductWithoutModif;
    /**
     * Variable to store 'Re-conduct without modification' button.
     */
    private ReconductWithModifButton vBTreconductWithModif;
    /**
     * Variable to store Re-consult button.
     */
    private ReconsultButton vBTreconsult;
    /**
     * Variable to store Close button.
     */
    private CloseEdsButton vBTclose;
    /**
     * Variable to store Subscribe button.
     */
    private SubscribeEdsButton vBTsubscribe;
    /**
     * Variable to store Unsubscribe button.
     */
    private UnsubscribeEdsButton vBTunsubscribe;
    /**
     * Variable to store Export button.
     */
    private ExportButton vBTtestWS;
    /**
     * Variable to store ExportXmlAdmin button.
     */
    private ExportXmlAdminButton vBTAdmintestWS;
    /**
     * Variable to store Freeze button.
     */
    private FreezeEdsButton vBTversion;

    /**
     * Variable to store reset validations button.
     */
    private ResetEdsValidationsButton vBTreset;
    /**
     * Variable to store generate check report button.
     */
    private GenerateEdsCheckReportButton vBTcheckReport;

    /**
     * Variable to store Assign button.
     */
    private EdsAffectationButton vBTaffectation;

    /**
     * Variable to store Export FC button.
     */
    private ExportEdsChsButton vBTexportfc;

    /**
     * Initialization method.
     */
    private void init() {
    }

    private int oldStage = controller.PRELIM_STAGE;

    /**
     * This method is used to validate stage and version of EDS.
     * 
     * @param formData EDS High validation form data.
     * @param newStage EDS validation Stage
     */
    public void validateStageAndVersion(EdsHighValidationFormData formData, int newStage) {
        EDSdao dao = EDSdao.getInstance();

        EdsEds oldVersion = dao.getEdsByID(formData.getEdsEds().getEdsId());
        EdsEds newVersionEds = dao.copyEdsForMajorVersion(oldVersion, controller.getAuthenticatedUser());

        controller.closeEds(oldVersion);

        controller.openEds(newVersionEds);

        int stageDiff = newStage - oldStage;
        if (controller.userHasSufficientRights(EdsRight.APP_EDS_MENU_EXPORT_PLM_TAB_VIEW)) {
            if (stageDiff > 1 && !newVersionEds.getEdsNumber96fcts().isEmpty()) {

                exportRobustStageAdminXml();
            }

            if (newStage == EdsApplicationController.SOLID_STAGE && !newVersionEds.getEdsNumber96fcts().isEmpty()) {
                exportAdminXml();
            }
            if (newStage > EdsApplicationController.SOLID_STAGE) {
                exportAdminXml();
            }
        }
        controller.sendNotificationMail(readForm.getID(), newVersionEds, EdsMailMessageBuilder.EDS_VALIDATED);
    }

    /**
     * This method is used to validate stage and version of EDS.
     * 
     * @param formData EDS Low validation form data.
     * @param newStage EDS validation stage
     */
    public void validateStageAndVersion(EdsLowValidationFormData formData, int newStage) {
        EDSdao dao = EDSdao.getInstance();

        EdsEds oldVersion = dao.getEdsByID(formData.getEdsEds().getEdsId());
        EdsEds newVersionEds = dao.copyEdsForMajorVersion(oldVersion, controller.getAuthenticatedUser());

        controller.closeEds(oldVersion);

        controller.openEds(newVersionEds);

        int stageDiff = newStage - oldStage;
        if (controller.userHasSufficientRights(EdsRight.APP_EDS_MENU_EXPORT_PLM_TAB_VIEW)) {
            if (stageDiff > 1 && !newVersionEds.getEdsNumber96fcts().isEmpty()) {

                exportRobustStageAdminXml();
            }

            if (newStage == EdsApplicationController.SOLID_STAGE && !newVersionEds.getEdsNumber96fcts().isEmpty()) {
                exportAdminXml();
            }
            if (newStage > EdsApplicationController.SOLID_STAGE) {
                exportAdminXml();
            }
        }

        controller.sendNotificationMail(readForm.getID(), newVersionEds, EdsMailMessageBuilder.EDS_VALIDATED);
    }

    /**
     * This method is used to generate XML at robust stage for admin when multiple stages are validated.
     */
    public void exportRobustStageAdminXml() {
        // TODO Auto-generated method stub
        try {
            EDSdao.getInstance().lockEdsAccess(getController().getAuthenticatedUser(), getEds().getEdsId());

            ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
            Client client = Client.create(config);

            String userName = controller.getCookieByID(EdsApplicationController.USERNAME_COOKIE);
            String password = controller.getCookieByID(EdsApplicationController.PASSWORD_COOKIE);

            String tokenID = login(new UserInfo(userName, password));

            String resourceUrl = controller.getApplication().getURL() + "resources/";
            WebResource webResource = client.resource(resourceUrl).path("eds");
            webResource = webResource.path("robustStageValidation");

            String response = webResource.queryParam("token-id", tokenID).queryParam("eds-ref", getEds().getEdsRef()).get(String.class);
            int xmTagEndIndex = response.indexOf(">");
            String xmlTagString = response.substring(0, xmTagEndIndex + 1);
            String xmlDataString = response.substring(xmTagEndIndex + 1);
            response = xmlTagString + "<?xml-stylesheet type=\"text/xsl\" href=\"EDS.xsl\"?>" + xmlDataString;
            int stage = controller.SOLID_STAGE;
            BufferedWriter bw = null;

            String fileName = controller.getEdsConfRessourceBundle().getString("server_xml_report_path") + "RefEDS_" + stage + getEds().getEdsRef()
                    + "_" + controller.getStageTextEn(controller.ROBUST_STAGE);

            // write html and simplified xml files
            String exportHTMLFilePath = fileName + ".html";
            transform(response, XSLT_EDS_TO_HTML, exportHTMLFilePath);
            String exportXMLFilePath = fileName + ".xml";
            transform(response, XSLT_EDS_TO_SIMPLIFIED_XML, exportXMLFilePath);

            logout(tokenID);

            ExportWarningWindow window = new ExportWarningWindow(controller);
            window.setModal(true);
            controller.getApplication().getMainWindow().addWindow(window);
            window.center();

            EDSdao.getInstance().freeEdsAccess(controller.getAuthenticatedUser(), getEds().getEdsId());

        } catch (E_AccessLocked ex) {
            getController().showError(controller.getBundle().getString("form-fiche-verouille"),
                    MessageFormat.format(controller.getBundle().getString("form-fiche-verouille-message"), ex.getUser().toPSAIdentity()));
        }

    }

    @Override
    public void saveEdsChs() {
        getController().setContent(readForm);
        refreshModel();
        readForm.refreshViewData();
    }

    @Override
    public void exportEdsChs() {
        try {
            EDSdao.getInstance().lockEdsAccess(getController().getAuthenticatedUser(), getEds().getEdsId());

            // File template
            File template = new File(EN_FormNode.class.getClassLoader().getResource("/templates/template_fc.xlsm").getFile());

            if (!template.exists()) {
                getController().showError("Export error", "Impossible de trouver le fichier de template");
                return;
            }

            final ByteArrayOutputStream dest = new ByteArrayOutputStream();

            // Export
            new ExportExcelCHS(template, dest, controller, getEds()).write();

            // Response stream
            Format formatter = new SimpleDateFormat(" dd-MM-yyyy HH-mm-ss");
            StreamResource sr = new StreamResource(new StreamResource.StreamSource() {

                private static final long serialVersionUID = 5070826190728599139L;

                @Override
                public InputStream getStream() {
                    return new ByteArrayInputStream(dest.toByteArray());
                }
            }, getEds().getEdsName() + formatter.format(new Date()) + ".xlsm", controller.getApplication());

            sr.setCacheTime(5000);
            sr.setMIMEType("application/octet-stream");
            getController().getApplication().getMainWindow().open(sr, "_top");

        } catch (IOException e) {
            getController().showError("Export error", "Erreur d'écriture à la génération du fichier");
            e.printStackTrace();
        } catch (ExcelExportException e) {
            getController().showError("Export error", "Erreur d'export à la génération du fichier");
            e.printStackTrace();
        } catch (E_AccessLocked e1) {
            getController().showError("Export error", "Erreur d'accès à la ressource EDS à la génération du fichier");
            e1.printStackTrace();
        } catch (InvalidFormatException e) {
            getController().showError("Export error", "Format du template invalide");
            e.printStackTrace();
        } finally {
            EDSdao.getInstance().freeEdsAccess(controller.getAuthenticatedUser(), getEds().getEdsId());
        }

    }

}
