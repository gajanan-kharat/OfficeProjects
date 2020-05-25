package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel;

import java.util.ArrayList;
import java.util.UUID;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.ModeMontageFormEditView;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyMesure;
import com.inetpsa.eds.dao.model.EdsModeMontage;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This class provide Assembled mode measure panel
 * 
 * @author Geometric Ltd.
 */
public class ModeMontageMesure extends Panel {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param ecsm object of EdsConsolidateSupplyMesure
     * @param qcfConsolide object of EdsQcf
     * @param controller Controller of EDS application
     */
    public ModeMontageMesure(EdsConsolidateSupplyMesure ecsm, EdsQcf qcfConsolide, EdsApplicationController controller) {

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
    private VerticalLayout vLCourantAppelActivation;
    /**
     * Variable to hold value of EdsConsolidateSupplyMesure
     */
    private EdsConsolidateSupplyMesure ecsm;
    /**
     * Variable to hold value of EdsQcf
     */
    private EdsQcf qcfConsolide;
    /**
     * Variable to hold list of ModeMontageFormEditView
     */
    private ArrayList<ModeMontageFormEditView> mmfevs;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize Assembled mode measure panel
     */
    private void init() {
        mmfevs = new ArrayList<ModeMontageFormEditView>();

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

                final EdsModeMontage emm = new EdsModeMontage(UUID.randomUUID().toString());
                emm.setMmedsRemove(1);
                ModeMontageFormEditView mmfev = new ModeMontageFormEditView(emm, emm.getMmedsRemove(), qcfConsolide, controller, ecsm
                        .getCsmedsConsolidateSupply().getCsedsTbtBt().equals("BT")) {
                    public void buttonClick(ClickEvent event) {
                        vLCourantAppelActivation.removeComponent(this);
                        mmfevs.remove(this);
                        ecsm.getEdsModeMontageMesures().remove(emm);
                    }
                };

                addModeMontage(mmfev, emm.getMmedsRemove());

                ecsm.getEdsModeMontageMesures().add(emm);
            }
        });
        addComponent(vBAdd);
    }

    /**
     * This method add Assembled mode measure
     * 
     * @param mmfev Object of ModeMontageFormEditView
     * @param b Boolean to check of ModeMontageFormEditView already exist
     */
    public void addModeMontage(ModeMontageFormEditView mmfev, Integer b) {
        if (b == 1) {
            vLCourantAppelActivation.addComponent(mmfev);
        } else {
            vLCourantAppelActivation.addComponent(mmfev, 0);
        }

        mmfevs.add(mmfev);
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
        mmfevs.clear();
    }

    /**
     * This method saves the changes to Databases
     */
    public void commitChanges() {
        for (ModeMontageFormEditView mmfev : mmfevs) {
            mmfev.commitChanges();
        }
    }

    /**
     * This method removes Assembled mode measure
     * 
     * @param mmfev Object of ModeMontageFormEditView
     */
    public void removeModeMontage(ModeMontageFormEditView mmfev) {
        vLCourantAppelActivation.removeComponent(mmfev);
        mmfevs.remove(mmfev);

    }

    // @Override
    // public void setVisible(boolean b){
    // vBAdd.setVisible( b );
    // }

    /**
     * This method checks if Assembled mode measure is valid
     * 
     * @return Check if Assembled mode measure is valid
     */
    boolean isValid() {
        for (ModeMontageFormEditView mmfev : mmfevs) {
            if (!mmfev.isValid()) {
                return false;
            }
        }
        return true;
    }
}
