package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantAppelActivationFormEditView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantCoupleBloqueCourantDysfonctionnelFormEditView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantDeMiseSousTensionFormEditView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantNominaleActivationFormEditView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.MesuredDataEditableTable;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.ModeMontageFormEditView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.ModeParcFormEditView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.ReseauVeilleReveilleOrganeInactifFormEditView;
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
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * This class provide Measured data Edit view
 * 
 * @author Geometric Ltd.
 */
public class DonneesMesureesEditView extends GridLayout {
    /**
     * UID
     */
    private static final long serialVersionUID = -3276876988225378916L;

    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param supplyMesure object of EdsConsolidateSupplyMesure
     * @param controller Controller of EDS application
     * @param qcfConsolide object of EdsQcf
     */
    public DonneesMesureesEditView(EdsConsolidateSupplyMesure supplyMesure, EdsApplicationController controller, EdsQcf qcfConsolide, String BT_TBT) {
        this.supplyMesure = supplyMesure;
        this.controller = controller;
        this.qcfConsolide = qcfConsolide;
        this.BT_TBT = BT_TBT;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of panel for generic data
     */
    private Panel vPGenericData;
    /**
     * Variable to hold value of panel for awake or asleep network
     */
    private Panel vPVeilleReveille;
    /**
     * Variable to hold value of panel for Park mode
     */
    private Panel vPModeParc;
    /**
     * Variable to hold value of ModeMontageMesure
     */
    private ModeMontageMesure vPModeMontage;
    /**
     * Variable to hold value of panel for Powered on current
     */
    private Panel vPCourantMiseSousTension;
    /**
     * Variable to hold value of CourantAppelActivationMesure
     */
    private CourantAppelActivationMesure vPCourantAppelActivation;
    /**
     * Variable to hold value of CourantNominalActivationMesure
     */
    private CourantNominalActivationMesure vPCourantNominalActivation;
    /**
     * Variable to hold value of CourantCoupleMesure
     */
    private CourantCoupleMesure vPCourantCouple;
    /**
     * Variable to hold value of MesuredDataEditableTable
     */
    private MesuredDataEditableTable mdet;
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
     * Variable to hold list of ReseauVeilleReveilleOrganeInactifFormEditView
     */
    private ArrayList<ReseauVeilleReveilleOrganeInactifFormEditView> rvroifevs;
    /**
     * Variable to hold list of ModeParcFormEditView
     */
    private ArrayList<ModeParcFormEditView> mpfevs;
    /**
     * Variable to hold list of CourantDeMiseSousTensionFormEditView
     */
    private ArrayList<CourantDeMiseSousTensionFormEditView> cdmstfevs;
    /**
     * Current profile edit view.
     */
    private CurrentProfilesEditView currentProfilesEditView;

    /**
     * Tensions Table View (Umin, Umoy, Umax).
     */
    private ArrayList<TensionsTableView> tensionsTableViews;

    private String BT_TBT;

    private VerticalLayout vLTensionsTable;

    /**
     * Initialize Measured data Edit view
     */
    private void init() {

        rvroifevs = new ArrayList<ReseauVeilleReveilleOrganeInactifFormEditView>();
        mpfevs = new ArrayList<ModeParcFormEditView>();
        cdmstfevs = new ArrayList<CourantDeMiseSousTensionFormEditView>();
        tensionsTableViews = new ArrayList<TensionsTableView>();
        vLTensionsTable = new VerticalLayout();

        currentProfilesEditView = new CurrentProfilesEditView(controller, supplyMesure.getEdsConsolidateSupplyProfile());

        setWidth("100%");
        setMargin(true);
        setSpacing(true);

        addComponent(currentProfilesEditView);

        vPGenericData = new Panel(controller.getBundle().getString("generic-data-title"));
        vPGenericData.setWidth("100%");

        mdet = new MesuredDataEditableTable(controller);

        mdet.setSizeFull();
        mdet.addEcsm(supplyMesure);

        vPGenericData.addComponent(mdet);

        vPVeilleReveille = new Panel(controller.getBundle().getString("consolid-veille-reveille-curent"));
        vPVeilleReveille.setWidth("100%");

        vPCourantCouple = new CourantCoupleMesure(supplyMesure, qcfConsolide, controller);
        vPCourantCouple.setCaption(controller.getBundle().getString("consolid-bloqued-curent"));
        vPCourantCouple.setWidth("100%");

        vPCourantMiseSousTension = new Panel(controller.getBundle().getString("consolid-mise-s-tension-curent"));
        vPCourantMiseSousTension.setWidth("100%");

        vPCourantNominalActivation = new CourantNominalActivationMesure(supplyMesure, qcfConsolide, controller);
        vPCourantNominalActivation.setCaption(controller.getBundle().getString("consolid-active-curent"));

        vPCourantNominalActivation.setWidth("100%");

        vPCourantAppelActivation = new CourantAppelActivationMesure(supplyMesure, qcfConsolide, controller);
        vPCourantAppelActivation.setCaption(controller.getBundle().getString("consolid-appel-active-curent"));
        vPCourantAppelActivation.setWidth("100%");

        vPModeParc = new Panel(controller.getBundle().getString("consolid-mode-parc-curent"));
        vPModeParc.setWidth("100%");

        vPModeMontage = new ModeMontageMesure(supplyMesure, qcfConsolide, controller);
        vPModeMontage.setCaption(controller.getBundle().getString("consolid-mode-montage-curent"));
        vPModeMontage.setWidth("100%");

        addComponent(vPGenericData);
        addComponent(vPVeilleReveille);
        addComponent(vPModeParc);
        addComponent(vPModeMontage);
        addComponent(vPCourantMiseSousTension);
        addComponent(vPCourantAppelActivation);
        addComponent(vPCourantNominalActivation);
        addComponent(vPCourantCouple);
        discardChanges();

    }

    /**
     * This method save the data into respective database tables
     */
    public void commit() {
        if (qcfConsolide.getQcf1() == 0) {
            mdet.reset(supplyMesure);
        }
        if (isValid()) {
            mdet.commit();
            mdet.updateEcsm(supplyMesure);
            for (TensionsTableView tensionsTableView : tensionsTableViews) {
                tensionsTableView.commitTensions();
            }
            supplyMesure.getEdsModeParcMesures().clear();

            for (ReseauVeilleReveilleOrganeInactifFormEditView rvroifev : rvroifevs) {
                rvroifev.commitChanges();
            }
            for (CourantDeMiseSousTensionFormEditView cdmstfev : cdmstfevs) {
                cdmstfev.commitChanges();
            }

            for (ModeParcFormEditView emp : mpfevs) {
                emp.commitChanges();
                supplyMesure.getEdsModeParcMesures().add(emp.getEdsModeParc());
            }

            vPCourantAppelActivation.commitChanges();
            vPModeMontage.commitChanges();
            vPCourantCouple.commitChanges();
            vPCourantNominalActivation.commitChanges();

            currentProfilesEditView.commit();
        }

    }

    /**
     * This method roll back changes to original data
     */
    public void discardChanges() {
        Set<EdsCourantMiseSousTension> ecmsts = supplyMesure.getEdsCourantMiseSousTensionMesures();
        if (ecmsts.isEmpty()) {
            EdsCourantMiseSousTension ecmst = new EdsCourantMiseSousTension(UUID.randomUUID().toString());
            CourantDeMiseSousTensionFormEditView cdmstfev = new CourantDeMiseSousTensionFormEditView(ecmst, controller, BT_TBT.equals("BT"));
            addCourantDeMiseSousTension(cdmstfev);
            supplyMesure.getEdsCourantMiseSousTensionMesures().add(ecmst);
        } else {
            for (EdsCourantMiseSousTension ecmst : ecmsts) {
                CourantDeMiseSousTensionFormEditView cdmstfev = new CourantDeMiseSousTensionFormEditView(ecmst, controller, BT_TBT.equals("BT"));
                addCourantDeMiseSousTension(cdmstfev);
            }
        }

        currentProfilesEditView.discard();
    }

    /**
     * This method update Asleep or awake network and inactive device
     * 
     * @param rvroifevb Object of ReseauVeilleReveilleOrganeInactifFormEditView
     */
    void UpdateVeilleReveilleEdit(ReseauVeilleReveilleOrganeInactifFormEditView rvroifevb) {
        vPVeilleReveille.removeAllComponents();
        vPVeilleReveille.addComponent(rvroifevb);
    }

    /**
     * This method update the changes into database table
     */
    public void update() {
        // Generic DATA
        if (qcfConsolide.getQcf1() == 1) {
            mdet.setVisible(true);
        } else {
            mdet.setVisible(false);
        }

        removeAllComponents();

        // -- Tensions Table
        Set<ConsolidateSupplyEdsTension> csEdsUs = supplyMesure.getConsolidateSupplyEdsTensions();
        if (csEdsUs.isEmpty()) {
            ConsolidateSupplyEdsTension csEdsU = new ConsolidateSupplyEdsTension();
            supplyMesure.getConsolidateSupplyEdsTensions().add(csEdsU);
            TensionsTableView uTable = new TensionsTableView(csEdsU, controller, false);
            uTable.setEditable(true);
            vLTensionsTable.addComponent(uTable);
            tensionsTableViews.add(uTable);
        } else {
            for (ConsolidateSupplyEdsTension csEdsU : csEdsUs) {
                TensionsTableView uTable = new TensionsTableView(csEdsU, controller, false);
                uTable.setEditable(true);
                vLTensionsTable.addComponent(uTable);
                tensionsTableViews.add(uTable);
            }
        }
        if (BT_TBT.equals("BT")) {
            vPGenericData.addComponent(vLTensionsTable);
        }

        // ---Sleep / Wake-

        Set<EdsReseauVeilleReveilleOrganeInactif> ervrois = supplyMesure.getEdsReseauVeilleReveilleOrganeInactifMesures();
        if (ervrois.isEmpty()) {
            EdsReseauVeilleReveilleOrganeInactif edsReseauVeilleReveilleOrganeInactif = new EdsReseauVeilleReveilleOrganeInactif(UUID.randomUUID()
                    .toString());

            ReseauVeilleReveilleOrganeInactifFormEditView reseauVeilleReveilleOrganeInactif = new ReseauVeilleReveilleOrganeInactifFormEditView(
                    qcfConsolide, edsReseauVeilleReveilleOrganeInactif, controller, getMesuredTensionsData(0), BT_TBT.equals("BT"));
            addReseauVeilleReveille(reseauVeilleReveilleOrganeInactif);
            supplyMesure.getEdsReseauVeilleReveilleOrganeInactifMesures().add(edsReseauVeilleReveilleOrganeInactif);
        } else {
            int index = 0;
            for (EdsReseauVeilleReveilleOrganeInactif ervroi : ervrois) {
                ReseauVeilleReveilleOrganeInactifFormEditView reseauVeilleReveilleOrganeInactif = new ReseauVeilleReveilleOrganeInactifFormEditView(
                        qcfConsolide, ervroi, controller, getMesuredTensionsData(index++), BT_TBT.equals("BT"));
                addReseauVeilleReveille(reseauVeilleReveilleOrganeInactif);
            }
        }
        //
        // //---Park mode
        if (qcfConsolide.getQcf2() == 1) {
            vPModeParc.setVisible(true);
            Set<EdsModeParc> emps = supplyMesure.getEdsModeParcMesures();
            if (emps.isEmpty()) {
                EdsModeParc modeParc = new EdsModeParc(UUID.randomUUID().toString());
                ModeParcFormEditView modeParcFormEditView = new ModeParcFormEditView(qcfConsolide, modeParc, controller, getMesuredTensionsData(0),
                        BT_TBT.equals("BT"));
                addModeParc(modeParcFormEditView);
            } else {
                int index = 0;
                for (EdsModeParc emp : emps) {
                    ModeParcFormEditView modeParcFormEditView = new ModeParcFormEditView(qcfConsolide, emp, controller,
                            getMesuredTensionsData(index++), BT_TBT.equals("BT"));
                    addModeParc(modeParcFormEditView);
                }
            }
        } else {
            vPModeParc.setVisible(false);
            mpfevs.clear();
        }

        // ---Assembled mode
        if (qcfConsolide.getQcf3() == 1) {
            vPModeMontage.setVisible(true);
            Set<EdsModeMontage> emms = supplyMesure.getEdsModeMontageMesures();
            if (emms.isEmpty()) {
                final EdsModeMontage modeMontage = new EdsModeMontage(UUID.randomUUID().toString());
                modeMontage.setMmedsModeMontageTitre(controller.getBundle().getString("conso-cons-mod-Montage"));
                modeMontage.setMmedsRemove(0);
                ModeMontageFormEditView mmfev = new ModeMontageFormEditView(modeMontage, modeMontage.getMmedsRemove(), qcfConsolide, controller,
                        BT_TBT.equals("BT")) {

                    private static final long serialVersionUID = -2986607239527221219L;

                    public void buttonClick(ClickEvent event) {
                        if (modeMontage.getMmedsRemove() == 1) {
                            supplyMesure.getEdsModeMontageMesures().remove(modeMontage);
                            vPModeMontage.removeModeMontage(this);
                        }
                    }
                };
                vPModeMontage.addModeMontage(mmfev, modeMontage.getMmedsRemove());
                supplyMesure.getEdsModeMontageMesures().add(modeMontage);
            } else {

                for (final EdsModeMontage edsModeMontage : emms) {
                    ModeMontageFormEditView mmfev = new ModeMontageFormEditView(edsModeMontage, edsModeMontage.getMmedsRemove(), qcfConsolide,
                            controller, BT_TBT.equals("BT")) {

                        private static final long serialVersionUID = 6045916894702848294L;

                        public void buttonClick(ClickEvent event) {
                            if (edsModeMontage.getMmedsRemove() == 1) {
                                supplyMesure.getEdsModeMontageMesures().remove(edsModeMontage);
                                vPModeMontage.removeModeMontage(this);
                            }
                        }
                    };
                    vPModeMontage.addModeMontage(mmfev, edsModeMontage.getMmedsRemove());

                }
            }
            vPModeMontage.setVisible(true);
        } else {
            vPModeMontage.setVisible(false);

            supplyMesure.getEdsModeMontageMesures().clear();
        }

        // --Activation inrush current
        Set<EdsCourantAppelleActivation> ecaas = supplyMesure.getEdsCourantAppelleActivationMesures();
        if (ecaas.isEmpty()) {
            final EdsCourantAppelleActivation ecaa = new EdsCourantAppelleActivation(UUID.randomUUID().toString());
            ecaa.setCaaedsRemove(false);
            ecaa.setCaaedsTitre(controller.getBundle().getString("consolid-appel-active-curent"));
            CourantAppelActivationFormEditView caafev = new CourantAppelActivationFormEditView(qcfConsolide, ecaa, ecaa.isCaaedsRemove(), controller,
                    BT_TBT.equals("BT")) {

                private static final long serialVersionUID = -4757644920639745205L;

                public void buttonClick(ClickEvent event) {
                    supplyMesure.getEdsCourantAppelleActivationMesures().remove(ecaa);
                    vPCourantAppelActivation.removeCourantAppelleActivation(this);
                }
            };
            vPCourantAppelActivation.addCourantAppelleActivation(caafev, ecaa.isCaaedsRemove());
            supplyMesure.getEdsCourantAppelleActivationMesures().add(ecaa);

        } else {
            int index = 0;
            for (final EdsCourantAppelleActivation ecaa : ecaas) {
                CourantAppelActivationFormEditView caafev = new CourantAppelActivationFormEditView(qcfConsolide, ecaa, ecaa.isCaaedsRemove(),
                        controller, getMesuredTensionsData(index++), BT_TBT.equals("BT")) {

                    private static final long serialVersionUID = 9091269776510117326L;

                    public void buttonClick(ClickEvent event) {
                        supplyMesure.getEdsCourantAppelleActivationMesures().remove(ecaa);
                        vPCourantAppelActivation.removeCourantAppelleActivation(this);
                    }
                };
                vPCourantAppelActivation.addCourantAppelleActivation(caafev, ecaa.isCaaedsRemove());
            }
        }

        // ---Blocked couple
        Set<EdsCourantBloqueCourantDysfonctionnement> ecbcds = supplyMesure.getEdsCourantBloqueCourantDysfonctionnementMesures();
        if (ecbcds.isEmpty()) {
            final EdsCourantBloqueCourantDysfonctionnement ecbcd = new EdsCourantBloqueCourantDysfonctionnement(UUID.randomUUID().toString());
            ecbcd.setCbcdedsRemove(false);
            ecbcd.setCbcdedsTitre(controller.getBundle().getString("consolid-bloqued-curent"));
            CourantCoupleBloqueCourantDysfonctionnelFormEditView ccbcdfev = new CourantCoupleBloqueCourantDysfonctionnelFormEditView(qcfConsolide,
                    ecbcd, ecbcd.isCbcdedsRemove(), controller, getMesuredTensionsData(0)) {

                private static final long serialVersionUID = 2475381827788041638L;

                public void buttonClick(ClickEvent event) {
                    supplyMesure.getEdsCourantBloqueCourantDysfonctionnementMesures().remove(ecbcd);
                    vPCourantCouple.removeCourantCouple(this);
                }
            };
            vPCourantCouple.addCourantCouple(ccbcdfev, ecbcd.isCbcdedsRemove());
            supplyMesure.getEdsCourantBloqueCourantDysfonctionnementMesures().add(ecbcd);
        } else {
            int index = 0;
            for (final EdsCourantBloqueCourantDysfonctionnement ecbcd : ecbcds) {
                CourantCoupleBloqueCourantDysfonctionnelFormEditView ccbcdfev = new CourantCoupleBloqueCourantDysfonctionnelFormEditView(
                        qcfConsolide, ecbcd, ecbcd.isCbcdedsRemove(), controller, getMesuredTensionsData(index++)) {

                    private static final long serialVersionUID = -6648814578528679362L;

                    public void buttonClick(ClickEvent event) {
                        supplyMesure.getEdsCourantBloqueCourantDysfonctionnementMesures().remove(ecbcd);
                        vPCourantCouple.removeCourantCouple(this);
                    }
                };
                vPCourantCouple.addCourantCouple(ccbcdfev, ecbcd.isCbcdedsRemove());
                supplyMesure.getEdsCourantBloqueCourantDysfonctionnementMesures().add(ecbcd);
            }
        }
        // ---Activation nominal current
        Set<EdsCourantNominaleActivation> ecnas = supplyMesure.getEdsCourantNominaleActivationMesures();
        if (ecnas.isEmpty()) {
            EdsCourantNominaleActivation ecna = new EdsCourantNominaleActivation(UUID.randomUUID().toString());
            ecna.setCnaedsRemove(false);
            ecna.setCnaedsIEfficace(false);
            ecna.setCnaedsName(controller.getBundle().getString("consolid-active-curent"));
            CourantNominaleActivationFormEditView activationFormReadView = new CourantNominaleActivationFormEditView(qcfConsolide, ecna,
                    ecna.isCnaedsRemove(), ecna.isCnaedsIEfficace(), controller, getMesuredTensionsData(0), BT_TBT.equals("BT")) {

                private static final long serialVersionUID = -6242573166739699605L;

                public void buttonClick(ClickEvent event) {
                }
            };
            vPCourantNominalActivation.addCourantNominalActivation(activationFormReadView, ecna.isCnaedsRemove());
            supplyMesure.getEdsCourantNominaleActivationMesures().add(ecna);
        } else {
            int index = 0;
            for (final EdsCourantNominaleActivation ecna : ecnas) {
                CourantNominaleActivationFormEditView activationFormReadView = new CourantNominaleActivationFormEditView(qcfConsolide, ecna,
                        ecna.isCnaedsRemove(), ecna.isCnaedsIEfficace(), controller, getMesuredTensionsData(index++), BT_TBT.equals("BT")) {

                    private static final long serialVersionUID = -1739386620473907727L;

                    public void buttonClick(ClickEvent event) {
                        vPCourantNominalActivation.removeCourantNominalActivation(this);
                        supplyMesure.getEdsCourantNominaleActivationMesures().remove(ecna);
                    }
                };
                vPCourantNominalActivation.addCourantNominalActivation(activationFormReadView, ecna.isCnaedsRemove());
                supplyMesure.getEdsCourantNominaleActivationMesures().add(ecna);
            }
        }
    }

    /**
     * This method add Asleep or awake network and inactive device
     * 
     * @param c Object of ReseauVeilleReveilleOrganeInactifFormEditView
     */
    private void addReseauVeilleReveille(ReseauVeilleReveilleOrganeInactifFormEditView c) {
        vPVeilleReveille.addComponent(c);
        rvroifevs.add(c);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.GridLayout#removeAllComponents()
     */
    @Override
    public void removeAllComponents() {

        rvroifevs.clear();
        mpfevs.clear();
        tensionsTableViews.clear();
        vPVeilleReveille.removeAllComponents();
        vPModeParc.removeAllComponents();
        vPModeMontage.removeAllComponents();
        vPCourantAppelActivation.removeAllComponents();
        vPCourantNominalActivation.removeAllComponents();
        vPCourantCouple.removeAllComponents();
        vLTensionsTable.removeAllComponents();
    }

    /**
     * This method add Park mode
     * 
     * @param c Object of ModeParcFormEditView
     */
    private void addModeParc(ModeParcFormEditView c) {
        vPModeParc.addComponent(c);
        mpfevs.add(c);
    }

    /**
     * This method add Power current
     * 
     * @param c Object of CourantDeMiseSousTensionFormEditView
     */
    private void addCourantDeMiseSousTension(CourantDeMiseSousTensionFormEditView c) {
        vPCourantMiseSousTension.addComponent(c);
        cdmstfevs.add(c);
    }

    /**
     * This method check if panels added are valid
     * 
     * @return check if panels added are valid
     */
    public boolean isValid() {
        if (!vPCourantAppelActivation.isValid()) {
            return false;
        }
        if (!vPModeMontage.isValid()) {
            return false;
        }
        if (!vPCourantCouple.isValid()) {
            return false;
        }
        if (!vPCourantNominalActivation.isValid()) {
            return false;
        }

        for (ModeParcFormEditView mpfev : mpfevs) {
            if (!mpfev.isValid()) {
                return false;
            }

        }
        for (ReseauVeilleReveilleOrganeInactifFormEditView rvroifev : rvroifevs) {

            if (!rvroifev.isValid()) {
                return false;
            }
        }
        for (CourantDeMiseSousTensionFormEditView cdmstfev : cdmstfevs) {

            if (!cdmstfev.isValid()) {
                return false;
            }
        }
        if (!currentProfilesEditView.isValid()) {
            return false;
        }

        return true;
    }

    /**
     * Return a supply tension by use case name
     * 
     * @param operatingMode The use case value
     * @return
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
}
