package com.inetpsa.eds.application.content.eds.currentconsumption.psameasure;

import java.util.Set;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel.CurrentProfilesReadView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantAppelleActivationFormReadView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantCoupleBloqueCourantDysfonctionnelFormReadView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantCycleFormReadView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantDeMiseSousTensionFormReadView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantNominaleActivationFormReadView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.ModeMontageFormReadView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.ModeParcFormReadView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.ReseauVeilleReveilleOrganeInactifFormReadView;
import com.inetpsa.eds.dao.model.EdsCourantAppelleActivation;
import com.inetpsa.eds.dao.model.EdsCourantBloqueCourantDysfonctionnement;
import com.inetpsa.eds.dao.model.EdsCourantCycle;
import com.inetpsa.eds.dao.model.EdsCourantMiseSousTension;
import com.inetpsa.eds.dao.model.EdsCourantNominaleActivation;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsModeMontage;
import com.inetpsa.eds.dao.model.EdsModeParc;
import com.inetpsa.eds.dao.model.EdsPsaMesureSupply;
import com.inetpsa.eds.dao.model.EdsReseauVeilleReveilleOrganeInactif;
import com.inetpsa.eds.dao.model.EdsSupply;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

/**
 * This class provide Component for reading the PSA measurement Supply
 * 
 * @author Geometric Ltd.
 */
public class PsaMeasureSupplyReadView extends A_EdsReadForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param supply Object of EdsSupply
     * @param eds Object of EdsEds
     * @param psaMesureSupply Object of PsaMeasureSupply
     * @param controller Controller of EDS application
     */
    public PsaMeasureSupplyReadView(EdsSupply supply, EdsEds eds, EdsPsaMesureSupply psaMesureSupply, EdsApplicationController controller) {
        super(controller);
        this.supply = supply;
        this.psaMesureSupply = psaMesureSupply;
        this.eds = eds;
        this.controller = controller;
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
        return "";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEdsRef()
     */
    @Override
    public String getEdsRef() {
        return "";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEds()
     */
    @Override
    public EdsEds getEds() {
        return eds;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshViewData()
     */
    @Override
    public void refreshViewData() {
        refreshStandbyView();
        refreshParcModeView();
        refreshMountingModeView();
        refreshSwitchOnVoltageView();
        refreshInrushCurrentView();
        refreshNominalCurrentView();
        refreshBlockedCurrentView();
        refreshCycleCurrentView();
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
     * Variable to hold value of EdsSupply
     */
    private EdsSupply supply;
    /**
     * Variable to hold value of EdsPsaMeasureSupply
     */
    private EdsPsaMesureSupply psaMesureSupply;
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;
    /**
     * Variable to hold value of Panel for stand by view
     */
    private Panel standbyView;
    /**
     * Variable to hold value of panel for park mode view
     */
    private Panel parcmodeView;
    /**
     * Variable to hold value of panel for Mounting mode view
     */
    private Panel mountingView;
    /**
     * Variable to hold value of panel for switch on voltage view
     */
    private Panel switchOnVoltageView;
    /**
     * Variable to hold value of panel for inrush view
     */
    private Panel inrushView;
    /**
     * Variable to hold value of panel for nominal current view
     */
    private Panel nominalCurrentView;
    /**
     * Variable to hold value of panel for blocked current view
     */
    private Panel blockedCurrentView;
    /**
     * Variable to hold value of panel for cycle current view
     */
    private Panel CycleCurrentView;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize Read view for Psa Measure supply
     */
    private void init() {
        setMargin(true);
        setSpacing(true);

        // We only add profiles in the read view when at least one exists.
        if (supply.getEdsPsaMesureSupply() != null && !supply.getEdsPsaMesureSupply().getEdsConsolidateSupplyProfile().isEmpty()) {
            addComponent(new CurrentProfilesReadView(controller, supply.getEdsPsaMesureSupply().getEdsConsolidateSupplyProfile()));
        }

        addComponent(getStandbyView());
        addComponent(getParcModeView());
        addComponent(getMountingModeView());
        addComponent(getSwitchOnVoltageView());
        addComponent(getInrushCurrentView());
        addComponent(getNominalCurrentView());
        addComponent(getBlockedCurrentView());
        addComponent(getCycleCurrentView());
        refreshViewData();

    }

    /**
     * This method refreshes Asleep or awake network and inactive device (Customer mode) view
     */
    private void refreshStandbyView() {
        getStandbyView().removeAllComponents();
        for (EdsReseauVeilleReveilleOrganeInactif data : (Set<EdsReseauVeilleReveilleOrganeInactif>) supply.getEdsPsaMesureSupply()
                .getEdsPmReseauVeilleReveilleOrganeInactifs()) {
            getStandbyView().addComponent(
                    new ReseauVeilleReveilleOrganeInactifFormReadView(data, psaMesureSupply.getEdsQcf(), controller, false, supply
                            .getEdsConsolidateSupply().getCsedsTbtBt().equals("BT")));
        }
    }

    /**
     * This method return Asleep or awake network and inactive device (Customer mode) view
     * 
     * @return Stand by view
     */
    private Panel getStandbyView() {
        if (standbyView == null) {
            standbyView = new Panel();
            standbyView.setStyleName("psa-measure-table");
            standbyView.setCaption(controller.getBundle().getString("consolid-veille-reveille-curent"));
        }
        return standbyView;
    }

    /**
     * This method refreshes Park mode view
     */
    private void refreshParcModeView() {
        getParcModeView().removeAllComponents();
        for (EdsModeParc data : (Set<EdsModeParc>) supply.getEdsPsaMesureSupply().getEdsPmModeParcs()) {

            ModeParcFormReadView mprvt = new ModeParcFormReadView(psaMesureSupply.getEdsQcf(), controller);
            mprvt.setPageLength(1);
            if (psaMesureSupply.getEdsQcf().getQcf1() == 1) {
                mprvt.addOrderToContainer(controller.getBundle().getString("CSE-Iveil"), "12,5V", data.getMpedsTmoyModeParc(),
                        data.getMpedsTmaxModeParc(), data.getMpedsTmoyModeParcComment());
            } else {
                mprvt.addOrderToContainer(controller.getBundle().getString("CSE-Iveil"), "12,5V", data.getMpedsTmoyModeParc(),
                        data.getMpedsTmoyModeParcComment());
            }
            getParcModeView().addComponent(mprvt);
        }
    }

    /**
     * This method returns panel for park mode view
     * 
     * @return park mode view
     */
    private Panel getParcModeView() {
        if (parcmodeView == null) {
            parcmodeView = new Panel();
            parcmodeView.setStyleName("psa-measure-table");
            parcmodeView.setCaption(controller.getBundle().getString("consolid-mode-parc-curent"));
        }
        return parcmodeView;
    }

    /**
     * This method refreshes assembled mode view
     */
    private void refreshMountingModeView() {
        getMountingModeView().removeAllComponents();
        for (EdsModeMontage data : (Set<EdsModeMontage>) supply.getEdsPsaMesureSupply().getEdsPmModeMontages()) {
            ModeMontageFormReadView mmrvt = new ModeMontageFormReadView(controller);
            mmrvt.setPageLength(1);
            mmrvt.setCaption(data.getMmedsModeMontageTitre());
            mmrvt.addOrderToContainer(controller.getBundle().getString("consolid-mode-montage-curent") + " (A)", "12.5",
                    data.getMmedsTmoyModeMontage(), data.getMmedsTmoyModeMontageComment());
            mmrvt.setStyleName("separator");
            getMountingModeView().addComponent(mmrvt);
        }
    }

    /**
     * This method returns panel for park mode view
     * 
     * @return park mode view
     */
    private Panel getMountingModeView() {
        if (mountingView == null) {
            mountingView = new Panel();
            mountingView.setStyleName("psa-measure-table");
            mountingView.setCaption(controller.getBundle().getString("consolid-mode-montage-curent"));
        }
        return mountingView;
    }

    /**
     * This method refreshes Powered on current view
     */
    private void refreshSwitchOnVoltageView() {
        getSwitchOnVoltageView().removeAllComponents();
        for (EdsCourantMiseSousTension data : (Set<EdsCourantMiseSousTension>) supply.getEdsPsaMesureSupply().getEdsPmCourantMiseSousTensions()) {
            CourantDeMiseSousTensionFormReadView cmstrvt = new CourantDeMiseSousTensionFormReadView(controller);
            cmstrvt.setPageLength(2);
            cmstrvt.addOrderToContainer("Imst (A)", "U=12,5V", data.getCmstedsTpirecasImst(), data.getCmstedsTpirecasImstComment());
            cmstrvt.addOrderToContainer("Dt (66% Imst) (S) ", "", data.getCmstedsTpirecasDt(), data.getCmstedsTpirecasDtComment());
            getSwitchOnVoltageView().addComponent(cmstrvt);
        }
    }

    /**
     * This method returns panel for powered on current view
     * 
     * @return switch on voltage view
     */
    private Panel getSwitchOnVoltageView() {
        if (switchOnVoltageView == null) {
            switchOnVoltageView = new Panel();
            switchOnVoltageView.setStyleName("psa-measure-table");
            switchOnVoltageView.setCaption(controller.getBundle().getString("consolid-mise-s-tension-curent"));
        }
        return switchOnVoltageView;
    }

    /**
     * This method refreshes Activation inrush current view
     */
    private void refreshInrushCurrentView() {
        getInrushCurrentView().removeAllComponents();
        for (EdsCourantAppelleActivation data : (Set<EdsCourantAppelleActivation>) supply.getEdsPsaMesureSupply().getEdsPmCourantAppelleActivations()) {

            CourantAppelleActivationFormReadView caarvt = new CourantAppelleActivationFormReadView(psaMesureSupply.getEdsQcf(), controller);
            caarvt.setPageLength(2);
            caarvt.setCaption(data.getCaaedsTitre());
            caarvt.addOrderToContainer("Imax pulse (A)", "u=12,5V", data.getCaaedsTminImaxPulse(), data.getCaaedsTmoyImaxPulse(),
                    data.getCaaedsTmaxImaxPulse(), data.getCaaedsTmoyImaxPulseComment());
            caarvt.addOrderToContainer("Dt (66% Imax pulse) (S)", " ", data.getCaaedsTminDtPulse(), data.getCaaedsTmoyDtPulse(),
                    data.getCaaedsTmaxDtPulse(), data.getCaaedsTmoyDtPulseComment());
            caarvt.setStyleName("separator");

            getInrushCurrentView().addComponent(caarvt);
        }
    }

    /**
     * This method returns panel for Activation inrush current view
     * 
     * @return Inrush current view
     */
    private Panel getInrushCurrentView() {
        if (inrushView == null) {
            inrushView = new Panel();
            inrushView.setStyleName("psa-measure-table");
            inrushView.setCaption(controller.getBundle().getString("consolid-appel-active-curent"));
        }
        return inrushView;
    }

    /**
     * This method refreshes Activation nominal current view
     */
    private void refreshNominalCurrentView() {

        getNominalCurrentView().removeAllComponents();
        for (EdsCourantNominaleActivation data : (Set<EdsCourantNominaleActivation>) supply.getEdsPsaMesureSupply()
                .getEdsPmCourantNominaleActivations()) {

            // Permet d'afficher les projets reconduits sans modification
            getNominalCurrentView().addComponent(
                    new CourantNominaleActivationFormReadView(data, psaMesureSupply.getEdsQcf(), controller, false, supply.getEdsConsolidateSupply()
                            .getCsedsTbtBt().equals("BT")));

        }
    }

    /**
     * This method returns panel for Activation nominal current view
     * 
     * @return nominal current view
     */
    private Panel getNominalCurrentView() {
        if (nominalCurrentView == null) {
            nominalCurrentView = new Panel();
            nominalCurrentView.setStyleName("psa-measure-table");
            nominalCurrentView.setCaption(controller.getBundle().getString("consolid-conso-cycle"));
        }
        return nominalCurrentView;
    }

    /**
     * This method refreshes Blocked couple current view
     */
    private void refreshBlockedCurrentView() {
        getBlockedCurrentView().removeAllComponents();
        for (EdsCourantBloqueCourantDysfonctionnement data : (Set<EdsCourantBloqueCourantDysfonctionnement>) supply.getEdsPsaMesureSupply()
                .getEdsPmCourantBloqueCourantDysfonctionnements()) {
            CourantCoupleBloqueCourantDysfonctionnelFormReadView ccrvt = new CourantCoupleBloqueCourantDysfonctionnelFormReadView(
                    psaMesureSupply.getEdsQcf(), controller);
            ccrvt.setPageLength(1);
            String caption = data.getCbcdedsTitre();
            if (data.getCbcdedsOccurenceDefaillence()) {
                caption += " " + controller.getBundle().getString("occurence-sup");
            }
            Label cLabel = new Label(caption, Label.CONTENT_XHTML);
            cLabel.setStyleName("gras");
            ccrvt.addOrderToContainer("Idys (A)<br>Tdys (S)", "Udys=?", data.getCbcdedsTminIdys(), data.getCbcdedsTminTdys(),
                    data.getCbcdedsTmoyIdys(), data.getCbcdedsTmoyTdys(), data.getCbcdedsTmaxIdys(), data.getCbcdedsTmaxTdys(),
                    data.getCbcdedsOccurenceDefaillenceTdys(), data.getCbcdedsComment());
            getBlockedCurrentView().addComponent(cLabel);
            getBlockedCurrentView().addComponent(ccrvt);
        }
    }

    /**
     * This method returns panel for Blocked couple current view
     * 
     * @return blocked current view
     */
    private Panel getBlockedCurrentView() {
        if (blockedCurrentView == null) {
            blockedCurrentView = new Panel();
            blockedCurrentView.setCaption(controller.getBundle().getString("consolid-bloqued-curent"));
        }
        return blockedCurrentView;
    }

    /**
     * This method refreshes Cycle consumptions current view
     */
    private void refreshCycleCurrentView() {
        getCycleCurrentView().removeAllComponents();
        for (EdsCourantCycle data : (Set<EdsCourantCycle>) supply.getEdsPsaMesureSupply().getEdsCourantCycles()) {
            CourantCycleFormReadView ccrvt = new CourantCycleFormReadView(data, controller);
            getCycleCurrentView().addComponent(ccrvt);
        }
    }

    /**
     * This method returns panel for cycle consumptions view
     * 
     * @return cycle current view
     */
    private Panel getCycleCurrentView() {
        if (CycleCurrentView == null) {
            CycleCurrentView = new Panel();
            CycleCurrentView.setCaption(controller.getBundle().getString("consolid-conso-cycle"));
        }
        return CycleCurrentView;
    }
}
