package com.inetpsa.eds.application.content.eds.missionprofile;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.composants.MyTextArea;
import com.inetpsa.eds.application.content.eds.composants.MyTextField;
import com.inetpsa.eds.dao.model.EdsMissionProfil;
import com.inetpsa.eds.tools.docinfo.ComposantDocInfos;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window.Notification;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * This class represents 'Mission Profile' edit view after adding a profile.
 * 
 * @author Geometric Ltd.
 */
public class ProfilMissionEditView extends A_EdsEditForm {
    /**
     * Parameterized constructor.
     * 
     * @param profil EDS mission profile data data.
     * @param controller EDS application controller object.
     */
    public ProfilMissionEditView(EdsMissionProfil profil, EdsApplicationController controller) {
        this.profil = profil;
        this.controller = controller;
        init();
    }

    /**
     * Variable to store EDS mission profile data.
     */
    private EdsMissionProfil profil;
    /**
     * Layout of the main panel.
     */
    private GridLayout mainLayout;
    /**
     * Label for 'Activation number'.
     */
    private Label vLActivation;
    /**
     * Label for 'Notes'.
     */
    private Label vLCommentaire;
    /**
     * Text field for 'Activation number'.
     */
    private MyTextField vTFNbrActivation;
    /**
     * Custom text area for 'Notes'.
     */
    private MyTextArea vTACommentaire;
    /**
     * Combo-box for measure.
     */
    private ComboBox vCBFrec;
    /**
     * List for activation measure.
     */
    private ArrayList<Integer> item;
    /**
     * Component for attaching documents and information.
     */
    private ComposantDocInfos docInfos;
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;

    /**
     * The method retrieves Mission profile data.
     * 
     * @return Mission profile data.
     */
    public EdsMissionProfil getProfil() {
        return profil;
    }

    /**
     * Initialization method. This method is used to create the view to add the profile details.
     */
    private void init() {
        mainLayout = new GridLayout(3, 4);
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);
        mainLayout.setSizeFull();

        mainLayout.setColumnExpandRatio(0, 0f);
        mainLayout.setColumnExpandRatio(1, 1f);
        mainLayout.setColumnExpandRatio(2, 0f);

        vLActivation = new Label(controller.getBundle().getString("Activ-profil-nombre") + " :");
        vLActivation.setWidth("190px");
        vLActivation.addStyleName("h2");
        mainLayout.addComponent(vLActivation, 0, 0);
        mainLayout.setComponentAlignment(vLActivation, Alignment.BOTTOM_LEFT);

        HorizontalLayout vHL = new HorizontalLayout();
        vHL.setSpacing(true);

        vTFNbrActivation = new MyTextField();
        vTFNbrActivation.setWidth("150px");
        vHL.addComponent(vTFNbrActivation);

        vHL.setComponentAlignment(vTFNbrActivation, Alignment.BOTTOM_LEFT);

        item = new ArrayList<Integer>();
        item.add(0);
        item.add(1);
        item.add(2);

        vCBFrec = new ComboBox("", item);
        vCBFrec.setItemCaption(0, controller.getBundle().getString("Activ-profil-hour"));
        vCBFrec.setItemCaption(1, controller.getBundle().getString("Activ-profil-day"));
        vCBFrec.setItemCaption(2, controller.getBundle().getString("Activ-profil-year"));
        vHL.addComponent(vCBFrec);
        mainLayout.addComponent(vHL, 1, 0);
        vHL.setComponentAlignment(vCBFrec, Alignment.BOTTOM_LEFT);

        vLCommentaire = new Label(controller.getBundle().getString("eds-comnent"));
        vLCommentaire.addStyleName("h2");
        mainLayout.addComponent(vLCommentaire, 0, 1, 2, 1);

        vTACommentaire = new MyTextArea();
        vTACommentaire.setWidth("650");
        vTACommentaire.setMaxLength(2048);
        vTACommentaire.setRows(8);
        vTACommentaire.setImmediate(true);
        mainLayout.addComponent(vTACommentaire, 0, 2, 2, 2);

        docInfos = new ComposantDocInfos(controller);
        mainLayout.addComponent(docInfos, 0, 3, 2, 3);

        addComponent(mainLayout);
        discardChanges();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        try {
            Float.parseFloat("" + vTFNbrActivation.getValue());
        } catch (NumberFormatException e) {
            getWindow().showNotification(controller.getBundle().getString("Activ-profil-nombre-error"), Notification.TYPE_ERROR_MESSAGE);
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

            profil.setMpDocsInfosUrls(docInfos.getValue());
            profil.setMpNombreActivation(vTFNbrActivation.getFloat());
            profil.setMpUnit(Integer.parseInt("" + vCBFrec.getValue()));
            profil.setMpCommentaire(vTACommentaire.getText());

            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        vTFNbrActivation.setValue(profil.getMpNombreActivation());
        vCBFrec.setValue(profil.getMpUnit());
        vTACommentaire.setValue(profil.getMpCommentaire());
        docInfos.setValue(profil.getMpDocsInfosUrls());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.singleton((Object) profil);
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
        // DO NOTHING
    }

    /**
     * This method is used to set the profile name.
     * 
     * @param name Name of profile to be set.
     */
    public void setProfilName(String name) {
        profil.setMpNomProfil(name);
    }

    /**
     * This method is used to retrieve profile name.
     * 
     * @return Profile name.
     */
    public String getProfilName() {
        return controller.getBundle().getString("menu-app-mission-profil");
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return profil.getMpNomProfil();
    }
}
