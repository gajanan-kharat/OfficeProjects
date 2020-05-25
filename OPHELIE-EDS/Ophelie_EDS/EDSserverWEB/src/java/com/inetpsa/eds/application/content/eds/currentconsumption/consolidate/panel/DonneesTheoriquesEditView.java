/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.ModeMontageFormEditView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.ModeParcFormEditView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.ReseauVeilleReveilleOrganeInactifFormEditView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.TensionsTableView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.TheoriqueDataEditableTable;
import com.inetpsa.eds.dao.model.ConsolidateSupplyEdsTension;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyTheoritic;
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
 * This class provide Theoretical data Edit View
 * 
 * @author Geometric Ltd.
 */
public class DonneesTheoriquesEditView extends GridLayout {
    // PUBLIC

    /**
     * Parameterized constructor
     * 
     * @param edsConsolidateSupplyTheoritic object of EdsConsolidateSupplyTheoritic
     * @param controller Controller of EDS application
     * @param qcfConsolide object of EdsQcf
     */
    public DonneesTheoriquesEditView(EdsConsolidateSupplyTheoritic edsConsolidateSupplyTheoritic, EdsApplicationController controller,
            EdsQcf qcfConsolide, String BT_TBT) {
        this.supplyTheoritic = edsConsolidateSupplyTheoritic;
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
    private Panel vPGeneriqueData;

    private VerticalLayout vLTensionsTable;
    /**
     * Variable to hold value of panel for awake or asleep network
     */
    private Panel vPVeilleReveille;
    /**
     * Variable to hold value of panel for Park mode
     */
    private Panel vPModeParc;
    /**
     * Variable to hold value of ModeMontageTheorique
     */
    private ModeMontageTheorique vPModeMontage;
    /**
     * Variable to hold value of panel for Powered on current
     */
    private Panel vPCourantMiseSousTension;
    /**
     * Variable to hold value of CourantAppelActivationTheorique
     */
    private CourantAppelActivationTheorique vPCourantAppelActivation;
    /**
     * Variable to hold value of CourantNominalActivationTheorique
     */
    private CourantNominalActivationTheorique vPCourantNominalActivation;
    /**
     * Variable to hold value of CourantCoupleTheorique
     */
    private CourantCoupleTheorique vPCourantCouple;
    /**
     * Variable to hold value of TheoriqueDataEditableTable
     */
    private TheoriqueDataEditableTable tdet;

    /**
     * Tensions Table View (Umin, Umoy, Umax).
     */
    private ArrayList<TensionsTableView> tensionsTableViews;
    /**
     * Variable to hold value of EdsConsolidateSupplyTheoritic
     */
    private EdsConsolidateSupplyTheoritic supplyTheoritic;
    /**
     * Variable to hold value of Controller of Eds application
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

    private String BT_TBT;

    /**
     * Initialize Theoretical data Edit view
     */
    private void init() {

        rvroifevs = new ArrayList<ReseauVeilleReveilleOrganeInactifFormEditView>();
        mpfevs = new ArrayList<ModeParcFormEditView>();
        cdmstfevs = new ArrayList<CourantDeMiseSousTensionFormEditView>();
        tensionsTableViews = new ArrayList<TensionsTableView>();
        vLTensionsTable = new VerticalLayout();

        currentProfilesEditView = new CurrentProfilesEditView(controller, supplyTheoritic.getEdsConsolidateSupplyProfile());

        setWidth("100%");
        setMargin(true);
        setSpacing(true);

        addComponent(currentProfilesEditView);

        vPGeneriqueData = new Panel(controller.getBundle().getString("generic-data-title"));
        vPGeneriqueData.setWidth("100%");

        tdet = new TheoriqueDataEditableTable(controller, qcfConsolide.getQcf1() == 1);
        tdet.setSizeFull();
        tdet.addDonneeTheorique(supplyTheoritic);

        vPGeneriqueData.addComponent(tdet);

        vPVeilleReveille = new Panel(controller.getBundle().getString("consolid-veille-reveille-curent"));
        vPVeilleReveille.setWidth("100%");

        vPCourantCouple = new CourantCoupleTheorique(supplyTheoritic, qcfConsolide, controller);
        vPCourantCouple.setCaption(controller.getBundle().getString("consolid-bloqued-curent"));
        vPCourantCouple.setWidth("100%");

        vPCourantMiseSousTension = new Panel(controller.getBundle().getString("consolid-mise-s-tension-curent"));
        vPCourantMiseSousTension.setWidth("100%");

        vPCourantNominalActivation = new CourantNominalActivationTheorique(supplyTheoritic, qcfConsolide, controller);
        vPCourantNominalActivation.setCaption(controller.getBundle().getString("consolid-active-curent"));
        vPCourantNominalActivation.setWidth("100%");

        vPCourantAppelActivation = new CourantAppelActivationTheorique(supplyTheoritic, qcfConsolide, controller);
        vPCourantAppelActivation.setCaption(controller.getBundle().getString("consolid-appel-active-curent"));
        vPCourantAppelActivation.setWidth("100%");

        vPModeParc = new Panel(controller.getBundle().getString("consolid-mode-parc-curent"));
        vPModeParc.setWidth("100%");

        vPModeMontage = new ModeMontageTheorique(supplyTheoritic, qcfConsolide, controller);
        vPModeMontage.setCaption(controller.getBundle().getString("conso-cons-mod-Montage"));
        vPModeMontage.setWidth("100%");

        addComponent(vPGeneriqueData);
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

        tdet.commit();
        tdet.updateCseTheorique(supplyTheoritic);
        for (TensionsTableView tensionsTableView : tensionsTableViews) {
            tensionsTableView.commitTensions();
        }

        supplyTheoritic.getEdsModeParcs().clear();

        for (ReseauVeilleReveilleOrganeInactifFormEditView rvroifev : rvroifevs) {
            rvroifev.commitChanges();
        }

        for (ModeParcFormEditView mpfev : mpfevs) {
            mpfev.commitChanges();
            supplyTheoritic.getEdsModeParcs().add(mpfev.getEdsModeParc());
        }

        for (CourantDeMiseSousTensionFormEditView cdmstfev : cdmstfevs) {
            cdmstfev.commitChanges();
        }
        vPCourantAppelActivation.commitChanges();
        vPModeMontage.commitChanges();
        vPCourantCouple.commitChanges();
        vPCourantNominalActivation.commitChanges();

        currentProfilesEditView.commit();
    }

    /**
     * This method roll back changes to original data
     */
    public void discardChanges() {

        Set<EdsCourantMiseSousTension> ecmsts = supplyTheoritic.getEdsCourantMiseSousTensions();
        if (ecmsts.isEmpty()) {
            EdsCourantMiseSousTension ecmst = new EdsCourantMiseSousTension(UUID.randomUUID().toString());
            CourantDeMiseSousTensionFormEditView cdmstfev = new CourantDeMiseSousTensionFormEditView(ecmst, controller, BT_TBT.equals("BT"));
            addCourantDeMiseSousTension(cdmstfev);
            supplyTheoritic.getEdsCourantMiseSousTensions().add(ecmst);
        } else {
            for (EdsCourantMiseSousTension ecmst : ecmsts) {
                CourantDeMiseSousTensionFormEditView cdmstfev = new CourantDeMiseSousTensionFormEditView(ecmst, controller, BT_TBT.equals("BT"));
                addCourantDeMiseSousTension(cdmstfev);
            }
        }

        currentProfilesEditView.discard();
    }

    /**
     * This method update the changes into database table
     */
    public void update() {

        // Generic DATA
        vPGeneriqueData.removeAllComponents();
        tdet = new TheoriqueDataEditableTable(controller, qcfConsolide.getQcf1() == 1);
        tdet.setSizeFull();
        tdet.addDonneeTheorique(supplyTheoritic);

        vPGeneriqueData.addComponent(tdet);

        removeAllComponents();

        // -- Tensions Table
        Set<ConsolidateSupplyEdsTension> csEdsUs = supplyTheoritic.getConsolidateSupplyEdsTensions();
        if (csEdsUs.isEmpty()) {
            ConsolidateSupplyEdsTension csEdsU = new ConsolidateSupplyEdsTension();
            supplyTheoritic.getConsolidateSupplyEdsTensions().add(csEdsU);
            TensionsTableView uTable = new TensionsTableView(csEdsU, controller, true);
            uTable.setEditable(true);
            vLTensionsTable.addComponent(uTable);
            tensionsTableViews.add(uTable);
        } else {
            for (ConsolidateSupplyEdsTension csEdsU : csEdsUs) {
                TensionsTableView uTable = new TensionsTableView(csEdsU, controller, true);
                uTable.setEditable(true);
                vLTensionsTable.addComponent(uTable);
                tensionsTableViews.add(uTable);
            }
        }
        if (BT_TBT.equals("BT")) {
            vPGeneriqueData.addComponent(vLTensionsTable);
        }

        // ---sleep / Wake-
        Set<EdsReseauVeilleReveilleOrganeInactif> ervrois = supplyTheoritic.getEdsReseauVeilleReveilleOrganeInactifs();
        if (ervrois.isEmpty()) {
            EdsReseauVeilleReveilleOrganeInactif edsReseauVeilleReveilleOrganeInactif = new EdsReseauVeilleReveilleOrganeInactif(UUID.randomUUID()
                    .toString());

            ReseauVeilleReveilleOrganeInactifFormEditView reseauVeilleReveilleOrganeInactif = new ReseauVeilleReveilleOrganeInactifFormEditView(
                    qcfConsolide, edsReseauVeilleReveilleOrganeInactif, controller, getTehoriticTensionsData(0), BT_TBT.equals("BT"));

            addReseauVeilleReveille(reseauVeilleReveilleOrganeInactif);
            supplyTheoritic.getEdsReseauVeilleReveilleOrganeInactifs().add(edsReseauVeilleReveilleOrganeInactif);
        } else {
            int index = 0;
            for (EdsReseauVeilleReveilleOrganeInactif ervroi : ervrois) {
                ReseauVeilleReveilleOrganeInactifFormEditView reseauVeilleReveilleOrganeInactif = new ReseauVeilleReveilleOrganeInactifFormEditView(
                        qcfConsolide, ervroi, controller, getTehoriticTensionsData(index++), BT_TBT.equals("BT"));

                addReseauVeilleReveille(reseauVeilleReveilleOrganeInactif);
            }
        }

        // ---Park mode
        if (qcfConsolide.getQcf2() == 1) {
            vPModeParc.setVisible(true);
            Set<EdsModeParc> emps = supplyTheoritic.getEdsModeParcs();
            if (emps.isEmpty()) {
                EdsModeParc modeParc = new EdsModeParc(UUID.randomUUID().toString());
                ModeParcFormEditView modeParcFormEditView = new ModeParcFormEditView(qcfConsolide, modeParc, controller, getTehoriticTensionsData(0),
                        BT_TBT.equals("BT"));
                addModeParc(modeParcFormEditView);
                supplyTheoritic.getEdsModeParcs().add(modeParc);

            } else {
                int index = 0;
                for (EdsModeParc emp : emps) {
                    ModeParcFormEditView modeParcFormEditView = new ModeParcFormEditView(qcfConsolide, emp, controller,
                            getTehoriticTensionsData(index++), BT_TBT.equals("BT"));
                    addModeParc(modeParcFormEditView);
                }
            }
        } else {
            vPModeParc.setVisible(false);
        }

        // ---Assembled mode
        if (qcfConsolide.getQcf3() == 1) {
            vPModeMontage.setVisible(true);
            Set<EdsModeMontage> emms = supplyTheoritic.getEdsModeMontages();
            if (emms.isEmpty()) {
                final EdsModeMontage modeMontage = new EdsModeMontage(UUID.randomUUID().toString());
                modeMontage.setMmedsModeMontageTitre(controller.getBundle().getString("conso-cons-mod-Montage"));
                modeMontage.setMmedsRemove(0);
                ModeMontageFormEditView mmfev = new ModeMontageFormEditView(modeMontage, modeMontage.getMmedsRemove(), qcfConsolide, controller,
                        BT_TBT.equals("BT")) {
                    /**
                             * 
                             */
                    private static final long serialVersionUID = 1L;

                    public void buttonClick(ClickEvent event) {
                        if (modeMontage.getMmedsRemove() == 1) {
                            supplyTheoritic.getEdsModeMontages().remove(modeMontage);
                            removeComponent(this);
                        }
                    }
                };
                vPModeMontage.addModeMontage(mmfev, modeMontage.getMmedsRemove());
                supplyTheoritic.getEdsModeMontages().add(modeMontage);
            } else {
                for (final EdsModeMontage edsModeMontage : emms) {
                    ModeMontageFormEditView mmfev = new ModeMontageFormEditView(edsModeMontage, edsModeMontage.getMmedsRemove(), qcfConsolide,
                            controller, BT_TBT.equals("BT")) {

                        private static final long serialVersionUID = -5790348181414067441L;

                        public void buttonClick(ClickEvent event) {
                            if (edsModeMontage.getMmedsRemove() == 1) {
                                supplyTheoritic.getEdsModeMontages().remove(edsModeMontage);
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
            supplyTheoritic.getEdsModeMontages().clear();
            vPModeMontage.setVisible(false);
        }

        // --Activation inrush current
        Set<EdsCourantAppelleActivation> ecaas = supplyTheoritic.getEdsCourantAppelleActivations();
        vPCourantAppelActivation.removeAllComponents();
        if (ecaas.isEmpty()) {
            final EdsCourantAppelleActivation ecaa = new EdsCourantAppelleActivation(UUID.randomUUID().toString());
            ecaa.setCaaedsRemove(false);
            ecaa.setCaaedsTitre(controller.getBundle().getString("consolid-appel-active-curent"));
            CourantAppelActivationFormEditView caafev = new CourantAppelActivationFormEditView(qcfConsolide, ecaa, ecaa.isCaaedsRemove(), controller,
                    getTehoriticTensionsData(0), BT_TBT.equals("BT")) {

                private static final long serialVersionUID = -1319326223527686085L;

                public void buttonClick(ClickEvent event) {
                    supplyTheoritic.getEdsCourantAppelleActivations().remove(ecaa);
                    vPCourantAppelActivation.removeCourantAppelleActivation(this);
                }
            };
            vPCourantAppelActivation.addCourantAppelleActivation(caafev, ecaa.isCaaedsRemove());
            supplyTheoritic.getEdsCourantAppelleActivations().add(ecaa);

        } else {
            int index = 0;
            for (final EdsCourantAppelleActivation ecaa : ecaas) {
                CourantAppelActivationFormEditView caafev = new CourantAppelActivationFormEditView(qcfConsolide, ecaa, ecaa.isCaaedsRemove(),
                        controller, getTehoriticTensionsData(index++), BT_TBT.equals("BT")) {

                    private static final long serialVersionUID = -6372380052044177008L;

                    public void buttonClick(ClickEvent event) {
                        supplyTheoritic.getEdsCourantAppelleActivations().remove(ecaa);
                        vPCourantAppelActivation.removeCourantAppelleActivation(this);
                    }
                };
                vPCourantAppelActivation.addCourantAppelleActivation(caafev, ecaa.isCaaedsRemove());
                caafev.discardChanges();
            }
        }

        // ---Blocked couple
        Set<EdsCourantBloqueCourantDysfonctionnement> ecbcds = supplyTheoritic.getEdsCourantBloqueCourantDysfonctionnements();
        if (ecbcds.isEmpty()) {
            final EdsCourantBloqueCourantDysfonctionnement ecbcd = new EdsCourantBloqueCourantDysfonctionnement(UUID.randomUUID().toString());
            ecbcd.setCbcdedsRemove(false);
            ecbcd.setCbcdedsTitre(controller.getBundle().getString("consolid-bloqued-curent"));
            CourantCoupleBloqueCourantDysfonctionnelFormEditView ccbcdfev = new CourantCoupleBloqueCourantDysfonctionnelFormEditView(qcfConsolide,
                    ecbcd, ecbcd.isCbcdedsRemove(), controller) {

                private static final long serialVersionUID = 3942847926073318358L;

                public void buttonClick(ClickEvent event) {
                    supplyTheoritic.getEdsCourantBloqueCourantDysfonctionnements().remove(ecbcd);
                    vPCourantCouple.removeCourantCouple(this);
                }
            };
            vPCourantCouple.addCourantCouple(ccbcdfev, ecbcd.isCbcdedsRemove());
            supplyTheoritic.getEdsCourantBloqueCourantDysfonctionnements().add(ecbcd);
        } else {
            for (final EdsCourantBloqueCourantDysfonctionnement ecbcd : ecbcds) {
                CourantCoupleBloqueCourantDysfonctionnelFormEditView ccbcdfev = new CourantCoupleBloqueCourantDysfonctionnelFormEditView(
                        qcfConsolide, ecbcd, ecbcd.isCbcdedsRemove(), controller) {

                    private static final long serialVersionUID = 1951118948361915872L;

                    public void buttonClick(ClickEvent event) {
                        supplyTheoritic.getEdsCourantBloqueCourantDysfonctionnements().remove(ecbcd);
                        vPCourantCouple.removeCourantCouple(this);
                    }
                };
                vPCourantCouple.addCourantCouple(ccbcdfev, ecbcd.isCbcdedsRemove());
                supplyTheoritic.getEdsCourantBloqueCourantDysfonctionnements().add(ecbcd);
            }
        }

        // ---Activation nominal current
        Set<EdsCourantNominaleActivation> ecnas = supplyTheoritic.getEdsCourantNominaleActivations();
        if (ecnas.isEmpty()) {
            EdsCourantNominaleActivation ecna = new EdsCourantNominaleActivation(UUID.randomUUID().toString());
            ecna.setCnaedsRemove(false);
            ecna.setCnaedsIEfficace(false);
            ecna.setCnaedsName(controller.getBundle().getString("consolid-active-curent"));
            CourantNominaleActivationFormEditView activationFormReadView = new CourantNominaleActivationFormEditView(qcfConsolide, ecna,
                    ecna.isCnaedsRemove(), ecna.isCnaedsIEfficace(), controller, getTehoriticTensionsData(0), BT_TBT.equals("BT")) {

                private static final long serialVersionUID = 1L;

                public void buttonClick(ClickEvent event) {
                }
            };
            vPCourantNominalActivation.addCourantNominalActivation(activationFormReadView, ecna.isCnaedsRemove());
            supplyTheoritic.getEdsCourantNominaleActivations().add(ecna);
        } else {
            int index = 0;
            for (final EdsCourantNominaleActivation ecna : ecnas) {
                CourantNominaleActivationFormEditView activationFormReadView = new CourantNominaleActivationFormEditView(qcfConsolide, ecna,
                        ecna.isCnaedsRemove(), ecna.isCnaedsIEfficace(), controller, getTehoriticTensionsData(index++), BT_TBT.equals("BT")) {

                    private static final long serialVersionUID = 1L;

                    public void buttonClick(ClickEvent event) {
                        vPCourantNominalActivation.removeCourantNominalActivation(this);
                        supplyTheoritic.getEdsCourantNominaleActivations().remove(ecna);
                    }
                };
                vPCourantNominalActivation.addCourantNominalActivation(activationFormReadView, ecna.isCnaedsRemove());
                supplyTheoritic.getEdsCourantNominaleActivations().add(ecna);
            }
        }

        // Courant mise sous tension
        Set<EdsCourantMiseSousTension> ecmsts = supplyTheoritic.getEdsCourantMiseSousTensions();
        cdmstfevs.clear();

        vPCourantMiseSousTension.removeAllComponents();
        if (ecmsts.isEmpty()) {
            EdsCourantMiseSousTension ecmst = new EdsCourantMiseSousTension(UUID.randomUUID().toString());
            CourantDeMiseSousTensionFormEditView miseSousTensionFormEditView = new CourantDeMiseSousTensionFormEditView(ecmst, controller,
                    getTehoriticTensionsData(0), BT_TBT.equals("BT"));

            cdmstfevs.add(miseSousTensionFormEditView);

            vPCourantMiseSousTension.addComponent(miseSousTensionFormEditView);
            supplyTheoritic.getEdsCourantMiseSousTensions().add(ecmst);
        } else {
            int index = 0;
            for (final EdsCourantMiseSousTension ecmst : ecmsts) {
                CourantDeMiseSousTensionFormEditView miseSousTensionFormEditView = new CourantDeMiseSousTensionFormEditView(ecmst, controller,
                        getTehoriticTensionsData(index++), BT_TBT.equals("BT"));

                cdmstfevs.add(miseSousTensionFormEditView);

                cdmstfevs.add(miseSousTensionFormEditView);
                vPCourantMiseSousTension.addComponent(miseSousTensionFormEditView);
                supplyTheoritic.getEdsCourantMiseSousTensions().add(ecmst);
            }
        }
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
     * This method add Asleep or awake network and inactive device
     * 
     * @param rvroifevb Object of ReseauVeilleReveilleOrganeInactifFormEditView
     */
    private void addReseauVeilleReveille(ReseauVeilleReveilleOrganeInactifFormEditView rvroifevb) {
        vPVeilleReveille.addComponent(rvroifevb);
        rvroifevs.add(rvroifevb);
    }

    /**
     * This method add Park mode
     * 
     * @param modeParcFormEditView Object of ModeParcFormEditView
     */
    private void addModeParc(ModeParcFormEditView modeParcFormEditView) {
        vPModeParc.addComponent(modeParcFormEditView);
        mpfevs.add(modeParcFormEditView);
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
        if (!currentProfilesEditView.isValid())
            return false;

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

        return true;
    }

    private ConsolidateSupplyEdsTension getTehoriticTensionsData(int index) {

        if (index >= supplyTheoritic.getConsolidateSupplyEdsTensions().size())
            return new ConsolidateSupplyEdsTension();

        Iterator<ConsolidateSupplyEdsTension> tensionIt = supplyTheoritic.getConsolidateSupplyEdsTensions().iterator();

        while (index > 0) {
            index--;
            tensionIt.next();
        }

        return tensionIt.next();
    }
}
