/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate;

import java.util.Set;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantAppelleActivationFormReadView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantCoupleBloqueCourantDysfonctionnelFormReadView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantNominaleActivationFormReadView;
import com.inetpsa.eds.dao.model.EdsCourantAppelleActivation;
import com.inetpsa.eds.dao.model.EdsCourantBloqueCourantDysfonctionnement;
import com.inetpsa.eds.dao.model.EdsCourantNominaleActivation;
import com.inetpsa.eds.dao.model.EdsGroundConsolidate;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * This class provide Ground consolidate Read view. This View is opened when we select Ground monitor of device as other and add new ground
 * 
 * @author Geometric Ltd.
 */
public class GroundConsolidateReadView extends VerticalLayout {
    // PUBLIC
    /**
     * Default Constructor
     */
    public GroundConsolidateReadView() {
        init();
    }

    /**
     * Parameterized Constructor
     * 
     * @param controller Controller of EDS application
     * @param edsGround Object of EdsGroundConsolidate
     */
    public GroundConsolidateReadView(EdsApplicationController controller, EdsGroundConsolidate edsGround) {

        this.edsGround = edsGround;
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsGroundConsolidate
     */
    private EdsGroundConsolidate edsGround;
    /**
     * Variable to hold value of EdsQcf
     */
    private EdsQcf qcf;

    /**
     * Initialize Ground consolidate Read view
     */
    private void init() {
        qcf = new EdsQcf();
        qcf.selectAll(true);

        setSpacing(true);
        setMargin(true);
        setStyleName("gras");

        addComponent(getInrushCurrentView());
        addComponent(getNominalCurrentView());
        addComponent(getBlockedCurrentView());

    }

    /**
     * This method return Activation inrush current view
     * 
     * @return Activation inrush current view
     */
    private Component getInrushCurrentView() {
        Panel container = new Panel(controller.getBundle().getString("consolid-appel-active-curent"));
        Set<EdsCourantAppelleActivation> ecaas = edsGround.getEdsCourantAppelleActivations();
        if (!ecaas.isEmpty()) {
            for (EdsCourantAppelleActivation ecaa : ecaas) {
                CourantAppelleActivationFormReadView caarvt = new CourantAppelleActivationFormReadView(qcf, controller);
                caarvt.setPageLength(2);
                caarvt.setCaption(ecaa.getCaaedsTitre());
                caarvt.addOrderToContainer("Imax pulse (A)", "u=12,5V", ecaa.getCaaedsTminImaxPulse(), ecaa.getCaaedsTmoyImaxPulse(),
                        ecaa.getCaaedsTmaxImaxPulse(), ecaa.getCaaedsTmoyImaxPulseComment());
                caarvt.addOrderToContainer("Dt (66% Imax pulse) (S)", " ", ecaa.getCaaedsTminDtPulse(), ecaa.getCaaedsTmoyDtPulse(),
                        ecaa.getCaaedsTmaxDtPulse(), ecaa.getCaaedsTmoyDtPulseComment());

                container.addComponent(caarvt);

            }
        }

        return container;
    }

    /**
     * This method returnActivation nominal current view
     * 
     * @return Activation nominal current view
     */
    private Component getNominalCurrentView() {
        Panel container = new Panel(controller.getBundle().getString("consolid-active-curent"));
        Set<EdsCourantNominaleActivation> ecnas = edsGround.getEdsCourantNominaleActivations();
        if (!ecnas.isEmpty()) {
            for (EdsCourantNominaleActivation ecna : ecnas) {
                CourantNominaleActivationFormReadView cnafrv = new CourantNominaleActivationFormReadView(ecna, qcf, controller, false, false);
                container.addComponent(cnafrv);
            }
        }
        return container;
    }

    /**
     * This method return Blocked couple current /dysfunctional current view
     * 
     * @return Blocked couple current /dysfunctional current view
     */
    private Component getBlockedCurrentView() {
        Panel container = new Panel(controller.getBundle().getString("consolid-bloqued-curent"));
        Set<EdsCourantBloqueCourantDysfonctionnement> ecbcds = edsGround.getEdsCourantBloqueCourantDysfonctionnements();
        if (!ecbcds.isEmpty()) {
            for (EdsCourantBloqueCourantDysfonctionnement ecbcd : ecbcds) {
                CourantCoupleBloqueCourantDysfonctionnelFormReadView ccrvt = new CourantCoupleBloqueCourantDysfonctionnelFormReadView(qcf, controller);
                ccrvt.setPageLength(1);
                String caption = ecbcd.getCbcdedsTitre();
                if (ecbcd.getCbcdedsOccurenceDefaillence()) {
                    caption += controller.getBundle().getString("occurence-sup");
                }
                Label cLabel = new Label(caption);
                cLabel.setStyleName("gras");
                container.addComponent(cLabel);
                ccrvt.addOrderToContainer("Idys (A)<br>Tdys (S)", "Udysf", ecbcd.getCbcdedsTminIdys(), ecbcd.getCbcdedsTminTdys(),
                        ecbcd.getCbcdedsTmoyIdys(), ecbcd.getCbcdedsTmoyTdys(), ecbcd.getCbcdedsTmaxIdys(), ecbcd.getCbcdedsTmaxTdys(),
                        ecbcd.getCbcdedsOccurenceDefaillenceTdys(), ecbcd.getCbcdedsComment());

                container.addComponent(ccrvt);
            }

        }
        return container;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return edsGround.getGedsTitle();
    }
}
