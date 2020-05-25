package com.inetpsa.eds.application.content.eds.behavior.consolidate;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.behavior.ComportementComponent;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.model.EdsComportementConsolideFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window.Notification;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * This class provide component for editing behavior in consolidate stage
 * 
 * @author Geometric Ltd.
 */
public class ComportementConsolideEditView extends A_EdsEditForm {
    /**
     * Parameterized constructor
     * 
     * @param eds Eds Details
     * @param data form data for Eds Consolidate stage behavior
     * @param controller Controller of EDS application
     */
    public ComportementConsolideEditView(EdsEds eds, EdsComportementConsolideFormData data, EdsApplicationController controller) {
        this.data = data;
        this.eds = eds;
        this.controller = controller;
        init();
    }

    /**
     * Variable to hold value of TabSheet
     */
    private TabSheet vTS;

    /**
     * Variable to hold value of Label for control type
     */
    private Label vLABTypePilotage;
    /**
     * Variable to hold value of Label for Frequency control
     */
    private Label vLABPlagePilotage;
    /**
     * Variable to hold value of TextField for control type
     */
    private TextField vTXTTypePilotage;
    /**
     * Variable to hold value of TextField for Frequency control
     */
    private TextField vTXTPlagePilotage;
    /**
     * Variable to hold value of ComportementComponent for RLC tab
     */
    private ComportementComponent consolideRLC;
    /**
     * Variable to hold value of ComportementComponent for Saber tab
     */
    private ComportementComponent consolideSaber;
    /**
     * Variable to hold value of EdsComportementConsolideFormData
     */
    private EdsComportementConsolideFormData data;
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize component for editing behavior in consolidate stage
     */
    private void init() {
        vTS = new TabSheet();
        Panel vPConsolide = new Panel(controller.getBundle().getString("Admin-droit-form-comp-cons"));

        GridLayout vGLEntete = new GridLayout(2, 2);
        vGLEntete.setSpacing(true);

        vLABTypePilotage = new Label(controller.getBundle().getString("comp-consolide-type-pilotage"));
        vGLEntete.addComponent(vLABTypePilotage, 0, 0);

        vTXTTypePilotage = new TextField();
        vTXTTypePilotage.setWidth("590");
        vGLEntete.addComponent(vTXTTypePilotage, 1, 0);

        vLABPlagePilotage = new Label(controller.getBundle().getString("comp-consolide-freq-plage") + ":");
        vGLEntete.addComponent(vLABPlagePilotage, 0, 1);

        vTXTPlagePilotage = new TextField();
        vTXTPlagePilotage.setWidth("590");
        vGLEntete.addComponent(vTXTPlagePilotage, 1, 1);

        GridLayout vGLConsolide = new GridLayout(1, 2);
        vGLConsolide.setSpacing(true);
        vGLConsolide.setWidth("100%");
        vGLConsolide.addComponent(vGLEntete, 0, 0);

        // robust RCL
        consolideRLC = new ComportementComponent(eds, "robusteRLC", controller);
        consolideRLC.setPopupView(controller.getBundle().getString("comp-consolide-rlc-shema"),
                controller.getBundle().getString("comp-consolide-rlc-shema-desc"));

        vTS.addTab(consolideRLC, controller.getBundle().getString("comp-consolide-rlc-model"));

        // robust Saber
        consolideSaber = new ComportementComponent(eds, "robusteSaber", controller);

        vTS.addTab(consolideSaber, controller.getBundle().getString("comp-consolide-saber-model"));

        vGLConsolide.addComponent(vTS, 0, 1);

        vPConsolide.addComponent(vGLConsolide);
        addComponent(vPConsolide);
    }

    /**
     * This method shows notification
     * 
     * @param message Notification message
     * @param detail Details of notification
     */
    protected void showNotification(String message, String detail) {
        getWindow().showNotification(message, detail, Notification.TYPE_ERROR_MESSAGE);
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
        if (isValid()) {

            data.setCocofdModeleRclCommentaire(consolideRLC.getCom());
            data.setCocofdModeleRlcImage(consolideRLC.getImage());
            data.setCocofdModeleRclUrls(consolideRLC.getURLs());

            data.setCocofdModeleSaberCommentaire(consolideSaber.getCom());
            data.setCocofdModeleSaberImage(consolideSaber.getImage());
            data.setCocofdModeleSaberUrls(consolideSaber.getURLs());

            data.setCocofdPlageFrequence("" + vTXTPlagePilotage.getValue());
            data.setCocofdTypePilotage("" + vTXTTypePilotage.getValue());

            eds.setEdsModifDate(new Date());
            return true;
        } else {
            return false;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        consolideRLC.setCom(data.getCocofdModeleRclCommentaire() == null ? "" : data.getCocofdModeleRclCommentaire());
        consolideRLC.setImage(data.getCocofdModeleRlcImage());
        consolideRLC.setURL(data.getCocofdModeleRclUrls());

        consolideSaber.setCom(data.getCocofdModeleSaberCommentaire() == null ? "" : data.getCocofdModeleSaberCommentaire());
        consolideSaber.setImage(data.getCocofdModeleSaberImage());
        consolideSaber.setURL(data.getCocofdModeleSaberUrls());

        vTXTPlagePilotage.setValue(data.getCocofdTypePilotage() == null ? "" : data.getCocofdTypePilotage());
        vTXTPlagePilotage.setValue(data.getCocofdPlageFrequence() == null ? "" : data.getCocofdPlageFrequence());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.singleton((Object) data);
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
        data = controller.getFormDataModel(eds, data.getClass());
    }
}
