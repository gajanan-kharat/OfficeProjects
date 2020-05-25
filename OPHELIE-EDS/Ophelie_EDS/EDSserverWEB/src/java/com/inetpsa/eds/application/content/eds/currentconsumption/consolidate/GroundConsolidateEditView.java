package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.composants.MyTextField;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantAppelActivationFormEditView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantCoupleBloqueCourantDysfonctionnelFormEditView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantNominaleActivationFormEditView;
import com.inetpsa.eds.dao.model.EdsCourantAppelleActivation;
import com.inetpsa.eds.dao.model.EdsCourantBloqueCourantDysfonctionnement;
import com.inetpsa.eds.dao.model.EdsCourantNominaleActivation;
import com.inetpsa.eds.dao.model.EdsGroundConsolidate;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

/**
 * This class provide Ground consolidate Edit view. This View is opened when we select Ground monitor of device as other and add new ground
 * 
 * @author Geometric Ltd.
 */
public class GroundConsolidateEditView extends A_EdsEditForm {
    // PUBLIC
    /**
     * Default Constructor
     */
    public GroundConsolidateEditView() {
        init();
    }

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     * @param edsGround object of EdsGroundConsolidate
     * @param enabled Check if enabled view
     */
    public GroundConsolidateEditView(EdsApplicationController controller, EdsGroundConsolidate edsGround, boolean enabled) {

        this.edsGround = edsGround;
        this.controller = controller;
        this.enabled = enabled;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsGroundConsolidate
     */
    private EdsGroundConsolidate edsGround;
    /**
     * Variable to hold list of CourantAppelActivationFormEditView
     */
    private ArrayList<CourantAppelActivationFormEditView> caafevs;

    /**
     * Variable to hold list of CourantNominaleActivationFormEditView
     */
    private ArrayList<CourantNominaleActivationFormEditView> cnafevs;
    /**
     * Variable to hold list of CourantCoupleBloqueCourantDysfonctionnelFormEditView
     */
    private ArrayList<CourantCoupleBloqueCourantDysfonctionnelFormEditView> ccbcdfevs;
    /**
     * Variable to hold value of Button
     */
    private Button vBDelete;
    /**
     * Variable to hold value of MyTextField
     */
    private MyTextField vTXTTitle;
    /**
     * Variable to hold value of EdsQcf
     */
    private EdsQcf qcf;
    /**
     * Variable to hold value boolean to check view is enabled
     */
    private boolean enabled;

    /**
     * Initialize Ground consolidate Edit view
     */
    private void init() {

        caafevs = new ArrayList<CourantAppelActivationFormEditView>();
        cnafevs = new ArrayList<CourantNominaleActivationFormEditView>();
        ccbcdfevs = new ArrayList<CourantCoupleBloqueCourantDysfonctionnelFormEditView>();

        qcf = new EdsQcf(UUID.randomUUID().toString());
        qcf.selectAll(true);

        setSpacing(true);
        setMargin(true);

        HorizontalLayout hl = new HorizontalLayout();
        hl.addComponent(new Label(controller.getBundle().getString("eds-title-label") + " :"));
        vTXTTitle = new MyTextField();
        vTXTTitle.setWidth("350");
        hl.addComponent(vTXTTitle);
        addComponent(hl);

        addComponent(getInrushCurrentView());
        addComponent(getNominalCurrentView());
        addComponent(getBlockedCurrentView());

        discardChanges();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.AbstractComponentContainer#setEnabled(boolean)
     */
    @Override
    public void setEnabled(boolean enabled) {
        // vTFIveille.setEnabled( enabled );
        // vTFIrveilleInactif.setEnabled( enabled );
        // vTFInomStab.setEnabled( enabled );
        // vTFIpireCas.setEnabled( enabled );
        // vTFIpic.setEnabled( enabled );
        // vTFTIpic.setEnabled( enabled );
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        edsGround.setGedsTitle(vTXTTitle.getString());
        for (CourantAppelActivationFormEditView caafev : caafevs) {
            caafev.commitChanges();
        }
        for (CourantNominaleActivationFormEditView cnafev : cnafevs) {
            cnafev.commitChanges();
        }
        for (CourantCoupleBloqueCourantDysfonctionnelFormEditView ccbcdfev : ccbcdfevs) {
            ccbcdfev.commitChanges();
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        vTXTTitle.setValue(edsGround.getGedsTitle());
        // cnafevs.clear();
        // caafevs.clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection getAllItemsToSave() {
        return Collections.EMPTY_SET;
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
     * This method return Activation inrush current view
     * 
     * @return Activation inrush current view
     */
    private Component getInrushCurrentView() {
        Panel container = new Panel(controller.getBundle().getString("consolid-appel-active-curent"));
        Set<EdsCourantAppelleActivation> ecaas = edsGround.getEdsCourantAppelleActivations();
        if (!ecaas.isEmpty()) {
            for (EdsCourantAppelleActivation ecaa : ecaas) {
                CourantAppelActivationFormEditView activationFormEditView = new CourantAppelActivationFormEditView(qcf, ecaa, false, controller,
                        false) {
                    public void buttonClick(Button.ClickEvent event) {
                    }
                };
                activationFormEditView.setEditable(enabled);
                caafevs.add(activationFormEditView);
                container.addComponent(activationFormEditView);

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
                CourantNominaleActivationFormEditView activationFormEditView = new CourantNominaleActivationFormEditView(qcf, ecna, false, false,
                        controller, false) {
                    public void buttonClick(Button.ClickEvent event) {
                        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools |
                                                                                       // Templates.
                    }
                };
                activationFormEditView.setEditable(enabled);
                container.addComponent(activationFormEditView);
                cnafevs.add(activationFormEditView);
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
                CourantCoupleBloqueCourantDysfonctionnelFormEditView ccbcdfev = new CourantCoupleBloqueCourantDysfonctionnelFormEditView(qcf, ecbcd,
                        false, controller) {
                    public void buttonClick(Button.ClickEvent event) {
                        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools |
                                                                                       // Templates.
                    }
                };
                ccbcdfev.setEditable(enabled);
                container.addComponent(ccbcdfev);
                ccbcdfevs.add(ccbcdfev);
            }

        }
        return container;
    }

    /**
     * This method return EdsGroundConsolidate
     * 
     * @return EdsGroundConsolidate
     */
    public EdsGroundConsolidate getEdsGround() {
        return edsGround;
    }

    /**
     * This method set EdsGroundConsolidate
     * 
     * @param edsGround EdsGroundConsolidate object to set
     */
    public void setEdsGround(EdsGroundConsolidate edsGround) {
        this.edsGround = edsGround;
    }

    /**
     * This method check if view is enabled
     * 
     * @param b Boolean value to enable view
     */
    public void setEditable(boolean b) {
        this.setEnabled(b);
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
