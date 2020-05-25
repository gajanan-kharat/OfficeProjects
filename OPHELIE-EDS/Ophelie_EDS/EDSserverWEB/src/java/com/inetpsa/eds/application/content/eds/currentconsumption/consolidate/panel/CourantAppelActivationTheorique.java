package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel;

import java.util.ArrayList;
import java.util.UUID;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantAppelActivationFormEditView;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyTheoritic;
import com.inetpsa.eds.dao.model.EdsCourantAppelleActivation;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This class provide Activation inrush current theoretical panel
 * 
 * @author Geometric Ltd.
 */
public class CourantAppelActivationTheorique extends Panel {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param ecst object of EdsConsolidateSupplyTheoritic
     * @param qcfConsolide object of EdsQcf
     * @param controller Controller of EDS application
     */
    public CourantAppelActivationTheorique(EdsConsolidateSupplyTheoritic ecst, EdsQcf qcfConsolide, EdsApplicationController controller) {

        this.ecst = ecst;
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
    private VerticalLayout vLCourantAppelActivation;
    /**
     * Variable to hold value of EdsConsolidateSupplyTheoritic
     */
    private EdsConsolidateSupplyTheoritic ecst;
    /**
     * Variable to hold value of EdsQcf
     */
    private EdsQcf qcfConsolide;
    /**
     * Variable to hold list of CourantAppelActivationFormEditView
     */
    private ArrayList<CourantAppelActivationFormEditView> caafevs;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize Activation inrush current theoretical panel
     */
    private void init() {
        caafevs = new ArrayList<CourantAppelActivationFormEditView>();

        vLCourantAppelActivation = new VerticalLayout();
        vLCourantAppelActivation.setSpacing(true);
        vLCourantAppelActivation.setWidth("100%");
        addComponent(vLCourantAppelActivation);

        vBAdd = new Button();
        vBAdd.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png "));
        vBAdd.setStyleName(BaseTheme.BUTTON_LINK);
        vBAdd.setDescription(controller.getBundle().getString("eds-add-tab"));
        vBAdd.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {

                final EdsCourantAppelleActivation ecaa = new EdsCourantAppelleActivation(UUID.randomUUID().toString());
                ecaa.setCaaedsRemove(true);
                CourantAppelActivationFormEditView mmfev = new CourantAppelActivationFormEditView(qcfConsolide, ecaa, ecaa.isCaaedsRemove(),
                        controller, ecst.getCsmedsConsolidateSupply().getCsedsTbtBt().equals("BT")) {
                    public void buttonClick(ClickEvent event) {
                        vLCourantAppelActivation.removeComponent(this);
                        caafevs.remove(this);
                        ecst.getEdsCourantAppelleActivations().remove(ecaa);
                    }
                };

                addCourantAppelleActivation(mmfev, ecaa.isCaaedsRemove());

                ecst.getEdsCourantAppelleActivations().add(ecaa);
            }
        });
        addComponent(vBAdd);
    }

    /**
     * This method add Activation inrush current measure
     * 
     * @param mmfev Object of CourantAppelActivationFormEditView
     * @param b Boolean to check of CourantAppelActivationFormEditView already exist
     */
    public void addCourantAppelleActivation(CourantAppelActivationFormEditView mmfev, boolean b) {
        if (b) {
            vLCourantAppelActivation.addComponent(mmfev);
        } else {
            vLCourantAppelActivation.addComponent(mmfev, 0);
        }

        caafevs.add(mmfev);
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
        vLCourantAppelActivation.removeAllComponents();
        caafevs.clear();
    }

    /**
     * This method saves the changes to Databases
     */
    public void commitChanges() {
        for (CourantAppelActivationFormEditView caafev : caafevs) {
            caafev.commitChanges();
        }
    }

    /**
     * This method removes Activation inrush current theoretical
     * 
     * @param caafev Object of CourantAppelActivationFormEditView
     */
    public void removeCourantAppelleActivation(CourantAppelActivationFormEditView caafev) {
        vLCourantAppelActivation.removeComponent(caafev);
        caafevs.remove(caafev);
    }

    /**
     * This method checks if Activation inrush current theoretical is valid
     * 
     * @return Check if Activation inrush current theoretical is valid
     */
    boolean isValid() {
        for (CourantAppelActivationFormEditView caafev : caafevs) {
            if (!caafev.isValid()) {
                return false;
            }
        }
        return true;
    }
}
