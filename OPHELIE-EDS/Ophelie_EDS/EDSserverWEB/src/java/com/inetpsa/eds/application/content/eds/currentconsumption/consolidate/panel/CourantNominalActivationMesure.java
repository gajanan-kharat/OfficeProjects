package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel;

import java.util.ArrayList;
import java.util.UUID;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantNominaleActivationFormEditView;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyMesure;
import com.inetpsa.eds.dao.model.EdsCourantNominaleActivation;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This class provide Activation nominal current measure panel
 * 
 * @author Geometric Ltd.
 */
public class CourantNominalActivationMesure extends Panel {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param ecsm object of EdsConsolidateSupplyMesure
     * @param qcfConsolide object of EdsQcf
     * @param controller Controller of EDS application
     */
    public CourantNominalActivationMesure(EdsConsolidateSupplyMesure ecsm, EdsQcf qcfConsolide, EdsApplicationController controller) {

        this.ecsm = ecsm;
        this.qcfConsolide = qcfConsolide;
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of Button
     */
    private Button vBAdd;
    /**
     * Variable to hold value of VerticalLayout
     */
    private VerticalLayout vLCourantNominalActivation;
    /**
     * Variable to hold value of EdsConsolidateSupplyMesure
     */
    private EdsConsolidateSupplyMesure ecsm;
    /**
     * Variable to hold value of EdsQcf
     */
    private EdsQcf qcfConsolide;
    /**
     * Variable to hold list of CourantNominaleActivationFormEditView
     */
    private ArrayList<CourantNominaleActivationFormEditView> cnafevs;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize Activation nominal current measure panel
     */
    private void init() {
        cnafevs = new ArrayList<CourantNominaleActivationFormEditView>();

        vLCourantNominalActivation = new VerticalLayout();
        vLCourantNominalActivation.setSpacing(true);
        vLCourantNominalActivation.setWidth("100%");
        addComponent(vLCourantNominalActivation);

        vBAdd = new Button();
        vBAdd.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png "));
        vBAdd.setStyleName(BaseTheme.BUTTON_LINK);
        vBAdd.setDescription(controller.getBundle().getString("eds-add-tab"));
        vBAdd.addListener(new Button.ClickListener() {
            /*
             * (non-Javadoc)
             * 
             * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
             */
            public void buttonClick(ClickEvent event) {

                final EdsCourantNominaleActivation ecaa = new EdsCourantNominaleActivation(UUID.randomUUID().toString());
                ecaa.setCnaedsRemove(true);
                CourantNominaleActivationFormEditView mmfev = new CourantNominaleActivationFormEditView(qcfConsolide, ecaa, ecaa.isCnaedsRemove(),
                        ecaa.isCnaedsIEfficace(), controller, ecsm.getCsmedsConsolidateSupply().getCsedsTbtBt().equals("BT")) {
                    /*
                     * (non-Javadoc)
                     * 
                     * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
                     */
                    public void buttonClick(ClickEvent event) {
                        vLCourantNominalActivation.removeComponent(this);
                        cnafevs.remove(this);
                        ecsm.getEdsCourantNominaleActivationMesures().remove(ecaa);
                    }
                };

                addCourantNominalActivation(mmfev, ecaa.isCnaedsRemove());

                ecsm.getEdsCourantNominaleActivationMesures().add(ecaa);
            }
        });
        addComponent(vBAdd);
    }

    /**
     * This method add Activation nominal current measure
     * 
     * @param cnafev Object of CourantNominaleActivationFormEditView
     * @param b Boolean to check of CourantNominaleActivationFormEditView already exist
     */
    public void addCourantNominalActivation(CourantNominaleActivationFormEditView cnafev, boolean b) {
        if (b) {
            vLCourantNominalActivation.addComponent(cnafev);
        } else {
            vLCourantNominalActivation.addComponent(cnafev, 0);
        }

        cnafevs.add(cnafev);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.Panel#setCaption(java.lang.String)
     */
    @Override
    public void setCaption(String caption) {
        super.setCaption(caption);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.Panel#removeAllComponents()
     */
    @Override
    public void removeAllComponents() {
        vLCourantNominalActivation.removeAllComponents();
        cnafevs.clear();
    }

    /**
     * This method save changes to database
     */
    public void commitChanges() {
        for (CourantNominaleActivationFormEditView cnafev : cnafevs) {
            cnafev.commitChanges();
        }
    }

    /**
     * This method removes Activation nominal current measure
     * 
     * @param caafev Object of CourantNominaleActivationFormEditView
     */
    public void removeCourantNominalActivation(CourantNominaleActivationFormEditView caafev) {
        vLCourantNominalActivation.removeComponent(caafev);
        cnafevs.remove(caafev);
    }

    /**
     * This method checks if Activation nominal current measure is valid
     * 
     * @return Check if Activation nominal current measure is valid
     */
    boolean isValid() {
        for (CourantNominaleActivationFormEditView cnafev : cnafevs) {
            if (!cnafev.isValid()) {
                return false;
            }
        }
        return true;
    }
}