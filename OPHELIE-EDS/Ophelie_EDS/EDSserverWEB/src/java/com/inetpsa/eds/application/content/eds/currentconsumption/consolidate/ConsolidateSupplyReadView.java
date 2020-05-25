/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.application.content.eds.composants.Qcf;
import com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel.DonneesMesureesReadView;
import com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel.DonneesTheoriqueReadView;
import com.inetpsa.eds.dao.model.EdsConsolidateSupply;
import com.inetpsa.eds.dao.model.EdsEds;
import com.vaadin.ui.TabSheet;

/**
 * This class provide component for reading view of Current consumption supply tab of consolidate stage
 * 
 * @author Geometric Ltd.
 */
public class ConsolidateSupplyReadView extends A_EdsReadForm {
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Parameterized constructor
     * 
     * @param edsConsolidateSupply Object of EdsConsolidateSupply
     * @param controller Controller of EDS application
     */
    public ConsolidateSupplyReadView(EdsConsolidateSupply edsConsolidateSupply, EdsApplicationController controller) {
        super(controller);
        this.controller = controller;
        this.edsConsolidateSupply = edsConsolidateSupply;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormName()
     */
    @Override
    public String getFormName() {
        return "";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormTitle()
     */
    @Override
    public String getFormTitle() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEdsRef()
     */
    @Override
    public String getEdsRef() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEds()
     */
    @Override
    public EdsEds getEds() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshViewData()
     */
    @Override
    public void refreshViewData() {
        qcf.discardChanges();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        // DO NOTHING
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EdsConsolidateSupply
     */
    private EdsConsolidateSupply edsConsolidateSupply;
    /**
     * Variable to hold value of Qcf
     */
    private Qcf qcf;
    /**
     * Variable to hold value of Tabsheet
     */
    private TabSheet vAConsolide;

    /**
     * Initialize reading view of Current consumption supply tab of consolidate stage
     */
    private void init() {
        setSpacing(true);
        setMargin(true);
        qcf = new Qcf(controller, edsConsolidateSupply.getEdsQcf());
        qcf.setEditable(false);
        addComponent(qcf);

        refreshViewData();
        vAConsolide = new TabSheet();
        addComponent(vAConsolide);

        DonneesTheoriqueReadView dtrv = new DonneesTheoriqueReadView(edsConsolidateSupply.getEdsConsolidateSupplyTheoritic(), controller,
                edsConsolidateSupply.getEdsQcf(), edsConsolidateSupply.getCsedsTbtBt());
        vAConsolide.addTab(dtrv, controller.getBundle().getString("consolid-theoric-data"));

        DonneesMesureesReadView dmrv = new DonneesMesureesReadView(edsConsolidateSupply.getEdsConsolidateSupplyMesure(), controller,
                edsConsolidateSupply.getEdsQcf(), edsConsolidateSupply.getCsedsTbtBt());
        vAConsolide.addTab(dmrv, controller.getBundle().getString("consolid-mesured-data"));

    }
}
