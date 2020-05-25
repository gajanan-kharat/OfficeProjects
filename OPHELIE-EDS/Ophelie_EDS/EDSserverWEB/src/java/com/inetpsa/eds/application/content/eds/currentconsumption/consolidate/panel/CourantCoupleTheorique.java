/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantCoupleBloqueCourantDysfonctionnelFormEditView;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyTheoritic;
import com.inetpsa.eds.dao.model.EdsCourantBloqueCourantDysfonctionnement;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import java.util.ArrayList;
import java.util.UUID;

/**
 * This class provide Blocked couple current /dysfunctional current Theoretical panel
 * 
 * @author Geometric Ltd.
 */
public class CourantCoupleTheorique extends Panel {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param ecst object of EdsConsolidateSupplyTheoritic
     * @param qcfConsolide object of EdsQcf
     * @param controller Controller of EDS application
     */
    public CourantCoupleTheorique(EdsConsolidateSupplyTheoritic ecst, EdsQcf qcfConsolide, EdsApplicationController controller) {
        this.controller = controller;
        this.ecst = ecst;
        this.qcfConsolide = qcfConsolide;
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
    private VerticalLayout vLCourantCouple;
    /**
     * Variable to hold value of EdsConsolidateSupplyTheoritic
     */
    private EdsConsolidateSupplyTheoritic ecst;
    /**
     * Variable to hold value of EdsQcf
     */
    private EdsQcf qcfConsolide;
    /**
     * Variable to hold list of CourantCoupleBloqueCourantDysfonctionnelFormEditView
     */
    private ArrayList<CourantCoupleBloqueCourantDysfonctionnelFormEditView> ccbcdfevs;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize Blocked couple current /dysfunctional current Theoretical panel
     */
    private void init() {
        ccbcdfevs = new ArrayList<CourantCoupleBloqueCourantDysfonctionnelFormEditView>();

        vLCourantCouple = new VerticalLayout();
        vLCourantCouple.setSpacing(true);
        vLCourantCouple.setWidth("100%");
        addComponent(vLCourantCouple);

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

                final EdsCourantBloqueCourantDysfonctionnement ecbcd = new EdsCourantBloqueCourantDysfonctionnement(UUID.randomUUID().toString());
                ecbcd.setCbcdedsRemove(true);
                CourantCoupleBloqueCourantDysfonctionnelFormEditView ccbcdfev = new CourantCoupleBloqueCourantDysfonctionnelFormEditView(
                        qcfConsolide, ecbcd, ecbcd.isCbcdedsRemove(), controller) {
                    /*
                     * (non-Javadoc)
                     * 
                     * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
                     */
                    public void buttonClick(ClickEvent event) {
                        vLCourantCouple.removeComponent(this);
                        ccbcdfevs.remove(this);
                        ecst.getEdsCourantBloqueCourantDysfonctionnements().remove(ecbcd);
                    }
                };

                addCourantCouple(ccbcdfev, ecbcd.isCbcdedsRemove());

                ecst.getEdsCourantBloqueCourantDysfonctionnements().add(ecbcd);
            }
        });
        addComponent(vBAdd);
    }

    /**
     * This method add Blocked couple current /dysfunctional current Theoretical
     * 
     * @param ccbcdfev Object of CourantCoupleBloqueCourantDysfonctionnelFormEditView
     * @param b Boolean to check of CourantCoupleBloqueCourantDysfonctionnelFormEditView already exist
     */
    public void addCourantCouple(CourantCoupleBloqueCourantDysfonctionnelFormEditView ccbcdfev, boolean b) {
        if (b) {
            vLCourantCouple.addComponent(ccbcdfev);
        } else {
            vLCourantCouple.addComponent(ccbcdfev, 0);
        }

        ccbcdfevs.add(ccbcdfev);
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
        vLCourantCouple.removeAllComponents();
        ccbcdfevs.clear();
    }

    /**
     * This method saves the changes to Database
     */
    public void commitChanges() {
        for (CourantCoupleBloqueCourantDysfonctionnelFormEditView ccbcdfev : ccbcdfevs) {
            ccbcdfev.commitChanges();
        }
    }

    /**
     * This method removes Blocked couple current /dysfunctional current Theoretical
     * 
     * @param mmfev Object of CourantCoupleBloqueCourantDysfonctionnelFormEditView
     */
    public void removeCourantCouple(CourantCoupleBloqueCourantDysfonctionnelFormEditView mmfev) {
        vLCourantCouple.removeComponent(mmfev);
        ccbcdfevs.remove(mmfev);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.AbstractComponent#setVisible(boolean)
     */
    @Override
    public void setVisible(boolean b) {
        vBAdd.setVisible(b);
    }

    /**
     * This method checks if Blocked couple current /dysfunctional current Theoretical is valid
     * 
     * @return checks if Blocked couple current /dysfunctional current Theoretical is valid
     */
    boolean isValid() {
        for (CourantCoupleBloqueCourantDysfonctionnelFormEditView ccbcdfev : ccbcdfevs) {
            if (!ccbcdfev.isValid()) {
                return false;
            }
        }
        return true;
    }
}
