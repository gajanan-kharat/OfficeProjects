package com.inetpsa.eds.application.content.eds.missionprofile;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsMissionProfil;
import com.inetpsa.eds.dao.model.EdsMissionProfilFormData;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.themes.BaseTheme;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.TreeSet;
import java.util.UUID;
import org.vaadin.dialogs.ConfirmDialog;

/**
 * This class represents 'Mission Profile' edit view.
 * 
 * @author Geometric Ltd.
 */
public class ProfilMissionFormEditView extends A_EdsEditForm implements TabSheet.CloseHandler {
    /**
     * Tab sheet to add profile sheets.
     */
    private TabSheet vTSProfil;
    /**
     * EDS details.
     */
    private EdsEds eds;
    /**
     * Variable to store EDS mission profile data.
     */
    private EdsMissionProfilFormData formData;
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;
    /**
     * Button to add new profile.
     */
    private Button vBAddProfil;
    /**
     * List of all the Profiles data.
     */
    private ArrayList<ProfilMissionEditView> missionEditViews;
    /**
     * Pop-up window for adding new profile.
     */
    private Window subwindow;

    /**
     * Parameterized constructor.
     * 
     * @param eds EDS details.
     * @param formData EDS mission profile data.
     * @param controller EDS application controller object.
     */
    public ProfilMissionFormEditView(EdsEds eds, EdsMissionProfilFormData formData, EdsApplicationController controller) {
        this.eds = eds;
        this.formData = formData;
        this.controller = controller;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        for (ProfilMissionEditView view : missionEditViews) {
            if (!view.isValid()) {
                return false;
            }
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

        if (!isValid()) {
            return false;
        }
        int i = 0;
        for (ProfilMissionEditView view : missionEditViews) {
            view.getProfil().setMpRank(i);
            view.commitChanges();
            formData.getEdsMissionProfils().add(view.getProfil());
            i++;
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

        missionEditViews = new ArrayList<ProfilMissionEditView>();

        vTSProfil.removeAllComponents();

        for (EdsMissionProfil profil : new TreeSet<EdsMissionProfil>(formData.getEdsMissionProfils())) {
            ProfilMissionEditView view = new ProfilMissionEditView(profil, controller);

            Tab tabNew = vTSProfil.addTab(view, (String) profil.getMpNomProfil(), null, profil.getMpRank());

            missionEditViews.add(view);
            tabNew.setClosable(true);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.singleton((Object) formData);
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

    /**
     * Initialization method. This method is used to add all the tab-sheets and add profile button in edit view.
     */
    private void init() {
        setSpacing(true);

        vBAddProfil = new Button(controller.getBundle().getString("profil-form-add-form"));
        vBAddProfil.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png "));
        // vBAddProfil.setStyleName( BaseTheme.BUTTON_LINK );
        vBAddProfil.setDescription(controller.getBundle().getString("profil-form-add-form"));
        vBAddProfil.addStyleName("gras");
        vBAddProfil.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                queryNewProfil();
            }
        });

        addComponent(vBAddProfil);
        setComponentAlignment(vBAddProfil, Alignment.MIDDLE_RIGHT);

        vTSProfil = new TabSheet();
        vTSProfil.setCloseHandler(this);
        addComponent(vTSProfil);
    }

    /**
     * This method is used to add new profile to the tab-sheet.
     * 
     * @param pName profile name to be added.
     */
    private void addNewProfil(String pName) {

        EdsMissionProfil profil = new EdsMissionProfil(UUID.randomUUID().toString());
        profil.setMpUnit(0);
        profil.setMpNomProfil(pName);
        ProfilMissionEditView view = new ProfilMissionEditView(profil, controller);

        Tab t = vTSProfil.addTab(view, pName);

        t.setClosable(true);
        vTSProfil.setSelectedTab(view);
        missionEditViews.add(view);

    }

    /**
     * This method is used to validate if the profile already exists.
     * 
     * @param toString profile name to be added.
     * @return true if profile already exists, else false.
     */
    private boolean isExist(String toString) {
        for (ProfilMissionEditView pmev : missionEditViews) {
            if (toString.equals(pmev.toString())) {
                getWindow().showNotification(controller.getBundle().getString("profil-form-profil-exist"));
                return true;
            }
        }
        return false;
    }

    /**
     * This method is used to show a pop-up window which will retrieve the profile name from user.
     */
    private void queryNewProfil() {
        subwindow = new Window(controller.getBundle().getString("profil-form-new-profil"));
        // subwindow.setClosable( false );
        subwindow.setWidth("350");
        GridLayout contentLayout = new GridLayout(2, 2);

        final TextField pName = new TextField();
        pName.setWidth("200");
        contentLayout.addComponent(pName, 1, 0);

        Label l = new Label(controller.getBundle().getString("profil-form-profil-name") + " :");
        l.setWidth("100");
        contentLayout.addComponent(l, 0, 0);

        Button button = new Button(controller.getBundle().getString("Admin-proj-validate-button"), new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (pName.getValue() != null && pName.getValue() != "") {
                    if (!isExist(pName.getValue().toString())) {
                        addNewProfil(pName.getValue().toString());
                        subwindow.setVisible(false);
                    }

                } else {
                    getWindow().showNotification(controller.getBundle().getString("profil-form-profil-error"));
                }

            }
        });

        contentLayout.addComponent(button, 0, 1, 1, 1);

        contentLayout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);

        contentLayout.setSpacing(true);

        subwindow.setModal(true);
        subwindow.center();
        subwindow.addComponent(contentLayout);
        getApplication().getMainWindow().addWindow(subwindow);
    }

    /**
     * This method is used to delete the profile data whenever the tab is closed.
     * 
     * @param tabsheet Tab-sheet from which tab needs to be removed.
     * @param tabContent Tab to be removed.
     * @see com.vaadin.ui.TabSheet.CloseHandler#onTabClose(com.vaadin.ui.TabSheet, com.vaadin.ui.Component)
     */
    public void onTabClose(final TabSheet tabsheet, final Component tabContent) {
        final ProfilMissionEditView pmev = (ProfilMissionEditView) tabContent;
        ConfirmDialog.show(getApplication().getMainWindow(), controller.getBundle().getString("pop-warning-title"), MessageFormat.format(controller
                .getBundle().getString("profil-form-erorr-message"), pmev.getProfilName()), controller.getBundle().getString("consolid-qcf-oui"),
                controller.getBundle().getString("consolid-qcf-non"), new ConfirmDialog.Listener() {
                    public void onClose(ConfirmDialog cd) {
                        if (cd.isConfirmed()) {
                            missionEditViews.remove(pmev);
                            tabsheet.removeComponent(tabContent);
                            formData.getEdsMissionProfils().remove(pmev.getProfil());
                        }
                    }
                });
    }

    /**
     * This method is used to retrieve the profile name.
     * 
     * @return Profile name.
     */
    String getProfilName() {
        return controller.getBundle().getString("profil-form-title");
    }
}
