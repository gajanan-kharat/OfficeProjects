package com.inetpsa.eds.application.content.eds.behavior.robust;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.model.EdsComportementRobusteFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.application.content.eds.behavior.ComportementComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window.Notification;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * This class provide component for editing behavior in robust stage
 * 
 * @author Geometric Ltd.
 */
public class ComportementRobusteEditView extends A_EdsEditForm {
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Parameterized constructor
     * 
     * @param eds Eds Details
     * @param data form data for Eds robust stage behavior
     * @param controller Controller of EDS application
     */
    public ComportementRobusteEditView(EdsEds eds, EdsComportementRobusteFormData data, EdsApplicationController controller) {
        this.eds = eds;
        this.formData = data;
        this.controller = controller;
        init();
    }

    /**
     * Variable to hold value of TabSheet
     */
    private TabSheet vTS;
    /**
     * Variable to hold value of Label for title
     */
    private Label vLTitre;
    /**
     * Variable to hold value of EdsComportementRobusteFormData
     */
    private EdsComportementRobusteFormData formData;
    /**
     * Variable to hold value of ComportementComponent for electric synoptic
     */
    private ComportementComponent electrique;
    /**
     * Variable to hold value of ComportementComponent for functional synoptic
     */
    private ComportementComponent fonctionnel;

    /**
     * Initialize component for editing behavior in robust stage
     */
    private void init() {

        vLTitre = new Label(controller.getBundle().getString("Admin-droit-form-comp-rob"));
        vLTitre.addStyleName("h1");

        vTS = new TabSheet();

        fonctionnel = new ComportementComponent(eds, controller.getBundle().getString("comp-rob-fonct"), controller);
        vTS.addTab(fonctionnel, controller.getBundle().getString("comp-rob-syn-fonct"));

        electrique = new ComportementComponent(eds, controller.getBundle().getString("comp-rob-syn-elec"), controller);
        vTS.addTab(electrique, controller.getBundle().getString("comp-rob-syn-elec"));
        addComponent(vLTitre);
        addComponent(vTS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {

        if (electrique.getCom().length() > 1025) {
            getWindow().showNotification(controller.getBundle().getString("eds-coment-too-long"), Notification.TYPE_ERROR_MESSAGE);
            return false;
        }

        if (fonctionnel.getCom().length() > 1025) {
            getWindow().showNotification(controller.getBundle().getString("eds-coment-too-long"), Notification.TYPE_ERROR_MESSAGE);
            return false;
        }
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
            formData.setCrfdSynoptiqueElectriqueCommentaire(electrique.getCom());
            formData.setCrfdSynoptiqueFonctionnelCommentaire(fonctionnel.getCom());

            formData.setCrfdSynoptiqueFonctionnelUrls(fonctionnel.getURLs());
            formData.setCrfdSynoptiqueElectriqueUrls(electrique.getURLs());

            formData.setCrfdSynoptiqueElectriqueImage(electrique.getImage());
            formData.setCrfdSynoptiqueFonctionnelImage(fonctionnel.getImage());
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

        electrique.setCom(formData.getCrfdSynoptiqueElectriqueCommentaire());
        electrique.setImage(formData.getCrfdSynoptiqueElectriqueImage());
        electrique.setURL(formData.getCrfdSynoptiqueElectriqueUrls());

        fonctionnel.setCom(formData.getCrfdSynoptiqueFonctionnelCommentaire());
        fonctionnel.setImage(formData.getCrfdSynoptiqueFonctionnelImage());
        fonctionnel.setURL(formData.getCrfdSynoptiqueFonctionnelUrls());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.singletonList((Object) formData);
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
}
