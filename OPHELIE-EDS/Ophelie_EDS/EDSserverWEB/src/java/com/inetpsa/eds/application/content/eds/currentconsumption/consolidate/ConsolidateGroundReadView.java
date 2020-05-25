package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsGroundConsolidate;
import com.inetpsa.eds.dao.model.EdsGroundConsolidateCurent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;

/**
 * This class provide component for reading view of Current consumption ground tab of consolidate stage
 * 
 * @author Geometric Ltd.
 */
public class ConsolidateGroundReadView extends A_EdsReadForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     * @param edsGroundConsolidateCurent object of EdsGroundConsolidateCurent
     */
    public ConsolidateGroundReadView(EdsApplicationController controller, EdsGroundConsolidateCurent edsGroundConsolidateCurent) {
        super(controller);
        this.controller = controller;
        this.edsGroundConsolidateCurent = edsGroundConsolidateCurent;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of Controller of Eds application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsGroundConsolidateCurent
     */
    private EdsGroundConsolidateCurent edsGroundConsolidateCurent;
    /**
     * Variable to hold value of TabSheet
     */
    private TabSheet vTSMasse;

    /**
     * Initialize Read view for Current consumption ground tab of consolidate stage
     */
    private void init() {
        setCaption(controller.getBundle().getString("current-conso-tab-mass-rob-title"));
        setSpacing(true);
        setMargin(true);
        vTSMasse = new TabSheet();

        refreshViewData();
    }

    /**
     * This method refreshes view data
     */
    public void refreshViewData() {
        if (edsGroundConsolidateCurent.getGccChoixMasse() == null) {
            Label noMasse = new Label(controller.getBundle().getString("eds-no-ground"));
            noMasse.setStyleName("gras");
            addComponent(noMasse);
            return;
        }

        int i = edsGroundConsolidateCurent.getGccChoixMasse();

        HorizontalLayout hl1 = new HorizontalLayout();
        hl1.setSpacing(true);
        Label l1 = new Label("Umax : ");
        hl1.addComponent(l1);
        l1.setStyleName("gras");
        hl1.addComponent(new Label(edsGroundConsolidateCurent.getGccUmax() + " V"));
        addComponent(hl1);

        HorizontalLayout hl2 = new HorizontalLayout();
        hl2.setSpacing(true);
        Label choix = new Label();
        switch (i) {
        case 0:
            choix.setCaption(controller.getBundle().getString("current-conso-tab-mass-rob-choose1"));
            break;
        case 1:
            choix.setCaption(controller.getBundle().getString("current-conso-tab-mass-rob-choose2"));
            break;
        case 2:
            choix.setCaption(controller.getBundle().getString("current-conso-tab-mass-rob-choose3"));
            break;
        }

        Label l2 = new Label(controller.getBundle().getString("eds-ground-choose") + " :");
        hl2.addComponent(l2);
        l2.setStyleName("gras");
        hl2.addComponent(choix);
        addComponent(hl2);
        addComponent(vTSMasse);
        for (EdsGroundConsolidate groundConsolidate : edsGroundConsolidateCurent.getEdsGroundConsolidates()) {
            GroundConsolidateReadView editView = new GroundConsolidateReadView(controller, groundConsolidate);
            vTSMasse.addTab(editView, editView.toString());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormName()
     */
    @Override
    public String getFormName() {
        return "comp-cons-masse-title";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormTitle()
     */
    @Override
    public String getFormTitle() {
        return "comp-cons-masse-title";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEdsRef()
     */
    @Override
    public String getEdsRef() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEds()
     */
    @Override
    public EdsEds getEds() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }
}
