package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel;

import java.util.Iterator;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantAppelleActivationFormReadView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantCoupleBloqueCourantDysfonctionnelFormReadView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantDeMiseSousTensionFormReadView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantNominaleActivationFormReadView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.MesuredDataEditableTable;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.ModeMontageFormReadView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.ModeParcFormReadView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.ReseauVeilleReveilleOrganeInactifFormReadView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.TensionsTableView;
import com.inetpsa.eds.dao.model.ConsolidateSupplyEdsTension;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyMesure;
import com.inetpsa.eds.dao.model.EdsCourantAppelleActivation;
import com.inetpsa.eds.dao.model.EdsCourantBloqueCourantDysfonctionnement;
import com.inetpsa.eds.dao.model.EdsCourantMiseSousTension;
import com.inetpsa.eds.dao.model.EdsCourantNominaleActivation;
import com.inetpsa.eds.dao.model.EdsModeMontage;
import com.inetpsa.eds.dao.model.EdsModeParc;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.inetpsa.eds.dao.model.EdsReseauVeilleReveilleOrganeInactif;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * This class provide Measured data Read view
 * 
 * @author Geometric Ltd.
 */
public class DonneesMesureesReadView extends VerticalLayout implements I_Tensions {

    private static final long serialVersionUID = 1664300481892527859L;

    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param supplyMesure object of EdsConsolidateSupplyMesure
     * @param controller Controller of EDS application
     * @param qcfConsolide object of EdsQcf
     */
    public DonneesMesureesReadView(EdsConsolidateSupplyMesure supplyMesure, EdsApplicationController controller, EdsQcf qcfConsolide, String BT_TBT) {
        this.supplyMesure = supplyMesure;
        this.controller = controller;
        this.qcfConsolide = qcfConsolide;
        this.BT_TBT = BT_TBT;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EdsConsolidateSupplyMesure
     */
    private EdsConsolidateSupplyMesure supplyMesure;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsQcf
     */
    private EdsQcf qcfConsolide;
    /**
     * Variable to hold value of panel for generic data
     */
    private Panel vPGenericData;
    /**
     * Variable to hold value of panel for Power current
     */
    private Panel vPCourantMiseSousTension;
    /**
     * Variable to hold value of panel for Park mode
     */
    private Panel vPModeParc;
    /**
     * Variable to hold value of panel for Assembled mode
     */
    private Panel vPModeMontage;
    /**
     * Variable to hold value of panel for Activation inrush current
     */
    private Panel vPCourantAppelActivation;
    /**
     * Variable to hold value of panel for Blocked couple current
     */
    private Panel vPCourantCouple;
    /**
     * Variable to hold value of panel for Awake or asleep network or inactive device
     */
    private Panel vPReseauVeilleReveille;
    /**
     * Variable to hold value of panel for Activation nominal current
     */
    private Panel vPCourantNominalActivation;
    /**
     * Variable to hold value of Supply type
     */
    private String BT_TBT;

    /**
     * Initialize Measured data Read view
     */
    private void init() {

        setSpacing(true);

        // We only add profiles in the read view when at least one exists.
        if (!supplyMesure.getEdsConsolidateSupplyProfile().isEmpty()) {
            addComponent(new CurrentProfilesReadView(controller, supplyMesure.getEdsConsolidateSupplyProfile()));
        }

        vPGenericData = new Panel(controller.getBundle().getString("generic-data-title"));
        vPGenericData.setWidth("100%");

        MesuredDataEditableTable mdet = new MesuredDataEditableTable(controller);
        mdet.setEditable(false);
        mdet.setSizeFull();

        // Set Default value of Tmoy
        if (supplyMesure.getCsmedsTmoy() == null) {
            supplyMesure.setCsmedsTmoy(23f);
        }
        mdet.addEcsm(supplyMesure);

        vPGenericData.addComponent(mdet);

        // Tensions Table
        if (BT_TBT.equals("BT")) {
            vPGenericData.addComponent(getConsolidateTensionsTable());
        }

        addComponent(vPGenericData);

        addComponent(getReseauVeilleReveille());

        if (qcfConsolide.getQcf2() == 1) {
            addComponent(getModeParc());
        }

        if (qcfConsolide.getQcf3() == 1) {
            addComponent(getModeMontage());
        }

        addComponent(getCourantAppelActivation());
        addComponent(getCourantCouple());
        vPCourantMiseSousTension = new Panel(controller.getBundle().getString("consolid-mise-s-tension-curent"));

        int index = 0;
        for (EdsCourantMiseSousTension ecmst : supplyMesure.getEdsCourantMiseSousTensionMesures()) {
            CourantDeMiseSousTensionFormReadView cmstrvt = new CourantDeMiseSousTensionFormReadView(controller);
            cmstrvt.setPageLength(2);
            cmstrvt.addOrderToContainer("Imst (A)", "U=" + getUmoy12_5(getMesuredTensionsData(index++).getCsEdsUmoy()),
                    ecmst.getCmstedsTpirecasImst(), ecmst.getCmstedsTpirecasImstComment());
            cmstrvt.addOrderToContainer("Dt (66% Imst) (S) ", "", ecmst.getCmstedsTpirecasDt(), ecmst.getCmstedsTpirecasDtComment());
            vPCourantMiseSousTension.addComponent(cmstrvt);
        }
        addComponent(vPCourantMiseSousTension);

        vPCourantNominalActivation = new Panel(controller.getBundle().getString("consolid-active-curent"));
        vPCourantNominalActivation.addComponent(getCourantNominalActivation());
        addComponent(vPCourantNominalActivation);

    }

    private Component getConsolidateTensionsTable() {
        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);
        vl.setStyleName("margin-top");
        for (ConsolidateSupplyEdsTension tension : supplyMesure.getConsolidateSupplyEdsTensions()) {
            TensionsTableView tensionsTable = new TensionsTableView(tension, controller, true);
            vl.addComponent(tensionsTable);

        }
        return vl;
    }

    /**
     * This method returns Park mode panel
     * 
     * @return Park mode panel
     */
    private Component getModeParc() {
        vPModeParc = new Panel(controller.getBundle().getString("consolid-mode-parc-curent"));

        int index = 0;
        for (EdsModeParc parcMode : supplyMesure.getEdsModeParcMesures()) {
            ModeParcFormReadView mprvt = new ModeParcFormReadView(qcfConsolide, controller);
            mprvt.setPageLength(1);
            if (qcfConsolide.getQcf1() == 1) {
                mprvt.addOrderToContainer(controller.getBundle().getString("CSE-Iveil"), getUmoy12_5(getMesuredTensionsData(index++).getCsEdsUmoy()),
                        parcMode.getMpedsTmoyModeParc(), parcMode.getMpedsTmaxModeParc(), parcMode.getMpedsTmoyModeParcComment());
            } else {
                mprvt.addOrderToContainer(controller.getBundle().getString("CSE-Iveil"), getUmoy12_5(getMesuredTensionsData(index++).getCsEdsUmoy()),
                        parcMode.getMpedsTmoyModeParc(), parcMode.getMpedsTmoyModeParcComment());
            }
            vPModeParc.addComponent(mprvt);

        }
        return vPModeParc;
    }

    /**
     * This method returns Assembled mode panel
     * 
     * @return Assembled mode panel
     */
    private Component getModeMontage() {
        vPModeMontage = new Panel(controller.getBundle().getString("consolid-mode-montage-curent"));
        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);

        int index = 0;
        for (EdsModeMontage modeMontage : supplyMesure.getEdsModeMontageMesures()) {
            ModeMontageFormReadView mmrvt = new ModeMontageFormReadView(controller);
            mmrvt.setPageLength(1);
            mmrvt.setCaption(modeMontage.getMmedsModeMontageTitre());
            mmrvt.addOrderToContainer(controller.getBundle().getString("consolid-mode-montage-curent") + " (A)",
                    getUmoy12_5(getMesuredTensionsData(index++).getCsEdsUmoy()), modeMontage.getMmedsTmoyModeMontage(),
                    modeMontage.getMmedsTmoyModeMontageComment());
            vl.addComponent(mmrvt);
            mmrvt.setStyleName("separator");
        }

        vPModeMontage.addComponent(vl);
        return vPModeMontage;
    }

    /**
     * This method returns Activation inrush current panel
     * 
     * @return Activation inrush current panel
     */
    private Component getCourantAppelActivation() {
        vPCourantAppelActivation = new Panel(controller.getBundle().getString("consolid-appel-active-curent"));
        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);

        vPCourantAppelActivation.addComponent(vl);

        int index = 0;
        for (EdsCourantAppelleActivation ecaa : supplyMesure.getEdsCourantAppelleActivationMesures()) {
            CourantAppelleActivationFormReadView caarvt = new CourantAppelleActivationFormReadView(qcfConsolide, controller);
            caarvt.setPageLength(2);
            caarvt.setCaption(ecaa.getCaaedsTitre());
            caarvt.addOrderToContainer("Imax pulse (A)", "u=" + getUmoy12_5(getMesuredTensionsData(index++).getCsEdsUmoy()),
                    ecaa.getCaaedsTminImaxPulse(), ecaa.getCaaedsTmoyImaxPulse(), ecaa.getCaaedsTmaxImaxPulse(), ecaa.getCaaedsTmoyImaxPulseComment());
            caarvt.addOrderToContainer("Dt (66% Imax pulse) (S)", " ", ecaa.getCaaedsTminDtPulse(), ecaa.getCaaedsTmoyDtPulse(),
                    ecaa.getCaaedsTmaxDtPulse(), ecaa.getCaaedsTmoyDtPulseComment());
            caarvt.setStyleName("separator");
            vl.addComponent(caarvt);
        }

        return vPCourantAppelActivation;
    }

    /**
     * This method returns Blocked couple current panel
     * 
     * @return Blocked couple current panel
     */
    private Component getCourantCouple() {
        vPCourantCouple = new Panel(controller.getBundle().getString("consolid-bloqued-curent"));
        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);
        vPCourantCouple.addComponent(vl);

        for (EdsCourantBloqueCourantDysfonctionnement ecbcd : supplyMesure.getEdsCourantBloqueCourantDysfonctionnementMesures()) {

            CourantCoupleBloqueCourantDysfonctionnelFormReadView ccrvt = new CourantCoupleBloqueCourantDysfonctionnelFormReadView(qcfConsolide,
                    controller);
            ccrvt.setPageLength(1);
            ccrvt.addOrderToContainer("Idys (A)<br>Tdys (S)", "Udys=?", ecbcd.getCbcdedsTminIdys(), ecbcd.getCbcdedsTminTdys(),
                    ecbcd.getCbcdedsTmoyIdys(), ecbcd.getCbcdedsTmoyTdys(), ecbcd.getCbcdedsTmaxIdys(), ecbcd.getCbcdedsTmaxTdys(),
                    ecbcd.getCbcdedsOccurenceDefaillenceTdys(), ecbcd.getCbcdedsComment());

            ccrvt.setStyleName("separator");
            vl.addComponent(ccrvt);
        }

        return vPCourantCouple;
    }

    /**
     * This method returns Awake or asleep network or inactive device panel
     * 
     * @return Awake or asleep network or inactive device panel
     */
    private Component getReseauVeilleReveille() {
        vPReseauVeilleReveille = new Panel(controller.getBundle().getString("consolid-veille-reveille-curent"));
        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);
        vPReseauVeilleReveille.addComponent(vl);

        int index = 0;
        for (EdsReseauVeilleReveilleOrganeInactif ervroi : supplyMesure.getEdsReseauVeilleReveilleOrganeInactifMesures()) {
            ReseauVeilleReveilleOrganeInactifFormReadView rvroifrv = new ReseauVeilleReveilleOrganeInactifFormReadView(ervroi, qcfConsolide,
                    controller, false, getMesuredTensionsData(index++), BT_TBT.equals("BT"));

            vl.addComponent(rvroifrv);

        }

        return vPReseauVeilleReveille;
    }

    /**
     * This method returns Activation nominal current panel
     * 
     * @return Activation nominal current panel
     */
    private Component getCourantNominalActivation() {
        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);

        int index = 0;
        for (EdsCourantNominaleActivation ecna : supplyMesure.getEdsCourantNominaleActivationMesures()) {
            CourantNominaleActivationFormReadView cnafrv = new CourantNominaleActivationFormReadView(ecna, qcfConsolide, controller, false,
                    getMesuredTensionsData(index++), BT_TBT.equals("BT"));
            vl.addComponent(cnafrv);
        }
        return vl;
    }

    /**
     * Get the supply tension at the provided index. If there is no supply tension at the provided index, an empty one will be returned.
     * 
     * @param index The index.
     * @return A supply tension.
     */
    private ConsolidateSupplyEdsTension getMesuredTensionsData(int index) {

        if (index >= supplyMesure.getConsolidateSupplyEdsTensions().size())
            return new ConsolidateSupplyEdsTension();

        Iterator<ConsolidateSupplyEdsTension> tensionIt = supplyMesure.getConsolidateSupplyEdsTensions().iterator();

        while (index > 0) {
            index--;
            tensionIt.next();
        }

        return tensionIt.next();
    }

    @Override
    public String getUmin(Float uMin) {
        if (uMin != null) {
            return uMin.toString() + "V";
        }
        if (BT_TBT.equals("BT"))
            return U_MIN_BT;

        return U_MIN_TBT;
    }

    @Override
    public String getUmoy12_5(Float uMoy12_5) {
        if (uMoy12_5 != null) {
            return uMoy12_5.toString() + "V";
        }
        if (BT_TBT.equals("BT"))
            return U_MOY12_5_BT;

        return U_MOY12_5_TBT;
    }

    @Override
    public String getUmoy13_5(Float uMoy13_5) {
        if (uMoy13_5 != null) {
            return uMoy13_5.toString() + "V";
        }
        if (BT_TBT.equals("BT"))
            return U_MOY13_5_BT;

        return U_MOY13_5_TBT;
    }

    @Override
    public String getUmax(Float uMax) {
        if (uMax != null) {
            return uMax.toString() + "V";
        }
        if (BT_TBT.equals("BT"))
            return U_MAX_BT;

        return U_MAX_TBT;
    }

}
