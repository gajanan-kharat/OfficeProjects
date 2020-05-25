package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.vaadin.dialogs.ConfirmDialog;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.composants.InfosButton;
import com.inetpsa.eds.application.content.eds.composants.Qcf;
import com.inetpsa.eds.application.content.eds.composants.TypeAlimentation;
import com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel.DonneesMesureesEditView;
import com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel.DonneesTheoriquesEditView;
import com.inetpsa.eds.dao.model.EdsConsolidateSupply;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyMesure;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyTheoritic;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.inetpsa.eds.dao.model.EdsSupply;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TabSheet;

/**
 * This class provide component for editing view of Current consumption supply tab of consolidate stage
 * 
 * @author Geometric Ltd.
 */
public class ConsolidateSupplyEditView extends A_EdsEditForm implements Serializable {
    // PRIVE
    /**
     * Variable to hold value of EdsConsolidateSupply
     */
    private EdsConsolidateSupply edsConsolidateSupply;
    /**
     * Variable to hold value of EdsConsolidateSupplyTheoritic
     */
    private EdsConsolidateSupplyTheoritic edsConsolidateSupplyTheoritic;
    /**
     * Variable to hold value of EdsConsolidateSupplyMesure
     */
    private EdsConsolidateSupplyMesure edsConsolidateSupplyMesure;
    /**
     * Variable to hold value of EdsSupply
     */
    private EdsSupply edsSupply;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of GridLayout
     */
    private GridLayout mainLayout;
    /**
     * Variable to hold value of TypeAlimentation
     */
    private TypeAlimentation typeAlimentation;
    /**
     * Variable to hold value of TabSheet
     */
    private TabSheet vAConsolide;
    /**
     * Variable to hold value of DonneesTheoriquesEditView
     */
    private DonneesTheoriquesEditView theoriques;
    /**
     * Variable to hold value of DonneesMesureesEditView
     */
    private DonneesMesureesEditView mesurees;
    /**
     * Variable to hold value of Qcf
     */
    private Qcf vPQcfConsolide;
    /**
     * Variable to hold value of EdsQcf
     */
    private EdsQcf qcfConsolide;
    /**
     * Variable to hold value of InfosButton
     */
    private InfosButton infosButton;

    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     * @param edsConsolidateSupply Object of EdsConsolidateSupply
     * @param edsConsolidateSupplyTheoritic object of EdsConsolidateSupplyTheoritic
     * @param edsConsolidateSupplyMesure object of EdsConsolidateSupplyMesure
     * @param edsSupply Object of EdsSupply
     */
    public ConsolidateSupplyEditView(EdsApplicationController controller, EdsConsolidateSupply edsConsolidateSupply,
            EdsConsolidateSupplyTheoritic edsConsolidateSupplyTheoritic, EdsConsolidateSupplyMesure edsConsolidateSupplyMesure, EdsSupply edsSupply) {
        this.controller = controller;
        this.edsConsolidateSupply = edsConsolidateSupply;
        this.edsConsolidateSupplyTheoritic = edsConsolidateSupplyTheoritic;
        this.edsConsolidateSupplyMesure = edsConsolidateSupplyMesure;
        this.edsSupply = edsSupply;
        init();
    }

    /**
     * Initialize editing view of Current consumption supply tab of consolidate stage
     */
    private void init() {
        mainLayout = new GridLayout(2, 3);
        mainLayout.setWidth("100%");
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        typeAlimentation = new TypeAlimentation(controller);
        typeAlimentation.viewComent(false);
        typeAlimentation.viewUser(false);
        mainLayout.addComponent(typeAlimentation, 0, 0);

        infosButton = new InfosButton(controller);
        mainLayout.addComponent(infosButton, 1, 0);
        mainLayout.setComponentAlignment(infosButton, Alignment.MIDDLE_RIGHT);

        qcfConsolide = edsConsolidateSupply.getEdsQcf();
        vPQcfConsolide = new Qcf(controller, qcfConsolide, new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                ConfirmDialog.show(getApplication().getMainWindow(), controller.getBundle().getString("pop-warning-title"), controller.getBundle()
                        .getString("eds-qcf-message"), controller.getBundle().getString("consolid-qcf-oui"),
                        controller.getBundle().getString("consolid-qcf-non"), new ConfirmDialog.Listener() {
                            public void onClose(ConfirmDialog cd) {
                                if (cd.isConfirmed()) {
                                    updateQcf();
                                } else {
                                    vPQcfConsolide.discardChanges();
                                }
                            }
                        });

            }
        });

        mainLayout.addComponent(vPQcfConsolide, 0, 1, 1, 1);

        vAConsolide = new TabSheet();
        vAConsolide.setStyleName("gras");

        theoriques = new DonneesTheoriquesEditView(edsConsolidateSupplyTheoritic, controller, qcfConsolide, edsConsolidateSupply.getCsedsTbtBt());
        vAConsolide.addTab(theoriques, controller.getBundle().getString("consolid-theoric-data"));

        mesurees = new DonneesMesureesEditView(edsConsolidateSupplyMesure, controller, qcfConsolide, edsConsolidateSupply.getCsedsTbtBt());

        vAConsolide.addTab(mesurees, controller.getBundle().getString("consolid-mesured-data"));

        mainLayout.addComponent(vAConsolide, 0, 2, 1, 2);
        addComponent(mainLayout);
        discardChanges();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        vPQcfConsolide.discardChanges();
        typeAlimentation.setValue(edsConsolidateSupply.getWording());

        updateQcf();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        if (isValid()) {

            vPQcfConsolide.commit();
            mesurees.commit();
            theoriques.commit();
            edsConsolidateSupply.setEdsQcf(qcfConsolide);
            return true;
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {

        if (mesurees.isValid() && theoriques.isValid()) {
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {

        Collection<Object> itemsToSave = new ArrayList<Object>();
        itemsToSave.add(edsConsolidateSupplyTheoritic);
        itemsToSave.add(edsConsolidateSupplyMesure);

        return itemsToSave;
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
    }

    /**
     * This method update Question complementary forms
     */
    private void updateQcf() {
        vPQcfConsolide.update(qcfConsolide);
        theoriques.update();
        mesurees.update();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return edsConsolidateSupply.getCsedsSupplyName();
    }

    /**
     * This method returns EdsSupply
     * 
     * @return EdsSupply
     */
    public EdsSupply getEdsSupply() {
        return edsSupply;
    }

    /**
     * This method set EdsSupply
     * 
     * @param edsSupply EdsSupply to be set
     */
    public void setEdsSupply(EdsSupply edsSupply) {
        this.edsSupply = edsSupply;
    }
}
