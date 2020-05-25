package com.inetpsa.eds.application.content.eds.currentconsumption.psameasure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel.CurrentProfilesEditView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantAppelActivationFormEditView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantCoupleBloqueCourantDysfonctionnelFormEditView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantCycleFormEditView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantDeMiseSousTensionFormEditView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.CourantNominaleActivationFormEditView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.ModeMontageFormEditView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.ModeParcFormEditView;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.ReseauVeilleReveilleOrganeInactifFormEditView;
import com.inetpsa.eds.dao.model.EdsCourantAppelleActivation;
import com.inetpsa.eds.dao.model.EdsCourantBloqueCourantDysfonctionnement;
import com.inetpsa.eds.dao.model.EdsCourantCycle;
import com.inetpsa.eds.dao.model.EdsCourantMiseSousTension;
import com.inetpsa.eds.dao.model.EdsCourantNominaleActivation;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsModeMontage;
import com.inetpsa.eds.dao.model.EdsModeParc;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.dao.model.EdsProjectEds;
import com.inetpsa.eds.dao.model.EdsPsaMesureSupply;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.inetpsa.eds.dao.model.EdsReseauVeilleReveilleOrganeInactif;
import com.inetpsa.eds.dao.model.EdsSupply;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This class provide Edit view for PSA Measure supply
 * 
 * @author Geometric Ltd.
 */
public class PsaMeasureSupplyEditView extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param supply Object of EdsSupply
     * @param eds Object of EdsEds
     * @param controller Controller of EDS application
     */
    public PsaMeasureSupplyEditView(EdsSupply supply, EdsEds eds, EdsApplicationController controller) {
        this.supply = supply;
        this.eds = eds;
        this.controller = controller;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        if (!currentProfilesEditView.isValid())
            return false;

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        supply.setEdsPsaMesureSupply(psaSupply);
        commitStandbyChanges();
        commitBlockedCurrentChanges();
        commitInrushChanges();
        commitMountingChanges();
        commitNominalCurrentChanges();
        commitCycleCurrentChanges();
        commitParcmodeChanges();
        commitSwitchingVoltageChanges();
        currentProfilesEditView.commit();
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        discardStandbyChanges();
        discardBlockedCurrentChanges();
        discardInrushChanges();
        discardMountingChanges();
        discardCycleCurrentChanges();
        discardNominalCurrentChanges();
        discardParcModeChanges();
        discardSwitchingOnVoltageChanges();
        currentProfilesEditView.discard();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection getAllItemsToSave() {
        return Collections.EMPTY_LIST;
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
        // DO NOTHING
    }

    /**
     * This method add Nominal current Tab for specified project
     * 
     * @param name Name for Tab
     * @param project EdsProject
     */
    public void addNominalCurrentTab(String name, EdsProject project) {
        EdsCourantCycle data = new EdsCourantCycle(UUID.randomUUID().toString());
        data.getEdsProjects().add(project);
        data.setCcedsName(name);
        data.setCcedsRemove(true);
        data.setCcedsWithModif(EdsCourantCycle.RECONDUCTED_WITH_MODIF);

        CourantCycleFormEditView form = new CourantCycleFormEditView(data, true, controller) {
            public void buttonClick(ClickEvent event) {
                cycleCurrentForms.remove(this);
                CycleCurrentView.removeComponent(this);
            }
        };

        cycleCurrentForms.add(form);
        CycleCurrentView.addComponent(form, CycleCurrentView.getComponentCount() - 1);
        form.discardChanges();
    }

    /**
     * This method add nominal current for all projects
     * 
     * @param name Name of tab
     * @param projects List of EdsProject
     */
    public void addNominalCurrentTab(String name, List<EdsProject> projects) {
        EdsCourantCycle data = new EdsCourantCycle(UUID.randomUUID().toString());
        for (EdsProject ep : projects) {
            data.getEdsProjects().add(ep);
        }
        data.setCcedsName(name);
        data.setCcedsRemove(true);
        data.setCcedsWithModif(EdsCourantCycle.RECONDUCTED_WITHOUT_MODIF);

        CourantCycleFormEditView form = new CourantCycleFormEditView(data, true, controller) {
            public void buttonClick(ClickEvent event) {
                cycleCurrentForms.remove(this);
                CycleCurrentView.removeComponent(this);
            }
        };

        cycleCurrentForms.add(form);
        CycleCurrentView.addComponent(form, CycleCurrentView.getComponentCount() - 1);
        form.discardChanges();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;
    /**
     * Variable to hold value of EdsSupply
     */
    private EdsSupply supply;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsPsaMeasureSupply
     */
    private EdsPsaMesureSupply psaSupply;
    /**
     * Variable to hold value of VertivalLayout for Stand by view
     */
    private VerticalLayout standbyView;
    /**
     * Variable to hold value of VertivalLayout for park mode view
     */
    private VerticalLayout parcmodeView;
    /**
     * Variable to hold value of VertivalLayout for assembled mode view
     */
    private VerticalLayout mountingView;
    /**
     * Variable to hold value of VertivalLayout for powered on current view
     */
    private VerticalLayout switchOnVoltageView;
    /**
     * Variable to hold value of VertivalLayout for Activation Inrush current view
     */
    private VerticalLayout inrushView;
    /**
     * Variable to hold value of VertivalLayout for Activation nominal current view
     */
    private VerticalLayout nominalCurrentView;
    /**
     * Variable to hold value of VertivalLayout for cycle consumption current view
     */
    private VerticalLayout CycleCurrentView;
    /**
     * Variable to hold value of VertivalLayout for Blocked couple current view
     */
    private VerticalLayout blockedCurrentView;
    /**
     * Variable to hold List of ReseauVeilleReveilleOrganeInactifFormEditView
     */
    private List<ReseauVeilleReveilleOrganeInactifFormEditView> standbyForms;
    /**
     * Variable to hold List of ModeParcFormEditView
     */
    private List<ModeParcFormEditView> parcmodeForms;
    /**
     * Variable to hold List of ModeMontageFormEditView
     */
    private List<ModeMontageFormEditView> mountingForms;
    /**
     * Variable to hold List of CourantDeMiseSousTensionFormEditView
     */
    private List<CourantDeMiseSousTensionFormEditView> switchingOnVoltageForms;
    /**
     * Variable to hold List of CourantAppelActivationFormEditView
     */
    private List<CourantAppelActivationFormEditView> inrushForms;
    /**
     * Variable to hold List of CourantNominaleActivationFormEditView
     */
    private List<CourantNominaleActivationFormEditView> nominalCurrentForms;
    /**
     * Variable to hold List of CourantCycleFormEditView
     */
    private List<CourantCycleFormEditView> cycleCurrentForms;
    /**
     * Variable to hold List of CourantCoupleBloqueCourantDysfonctionnelFormEditView
     */
    private List<CourantCoupleBloqueCourantDysfonctionnelFormEditView> blockedCurrentForms;
    /**
     * Variable to hold List of EdsProject
     */
    private List<EdsProject> relatedProjects = new ArrayList<EdsProject>();
    /**
     * Variable to hold value of EdsQcf of consolidate
     */
    private EdsQcf qcfConsolide;
    /**
     * Variable to hold value of EdsQcf of measure
     */
    private EdsQcf qcfMesuree;
    /**
     * Variable to hold value of Button for update
     */
    private Button vBUpdate;

    /**
     * Profile edit view.
     */
    private CurrentProfilesEditView currentProfilesEditView;

    /**
     * Initialize Edit view for PSA measurement supply
     */
    private void init() {
        setMargin(true);
        setSpacing(true);
        setWidth("100%");

        qcfConsolide = supply.getEdsConsolidateSupply().getEdsQcf();

        psaSupply = supply.getEdsPsaMesureSupply();

        if (psaSupply == null) {
            initPsaSupply();
        }
        qcfMesuree = psaSupply.getEdsQcf();
        if (!qcfConsolide.equals(qcfMesuree)) {
            vBUpdate = new Button(controller.getBundle().getString("psa-mesure-apply-qcf"), new Button.ClickListener() {
                public void buttonClick(ClickEvent event) {
                    update();
                }
            });
            addComponent(vBUpdate);
            setComponentAlignment(vBUpdate, Alignment.MIDDLE_RIGHT);
        }

        /*
         * currentProfilesEditView = new CurrentProfilesEditView(controller, psaSupply.get .getEdsConsolidateSupplyMesure());
         * addComponent(currentProfilesEditView);
         */

        this.standbyForms = new ArrayList<ReseauVeilleReveilleOrganeInactifFormEditView>();
        this.parcmodeForms = new ArrayList<ModeParcFormEditView>();
        this.mountingForms = new ArrayList<ModeMontageFormEditView>();
        this.switchingOnVoltageForms = new ArrayList<CourantDeMiseSousTensionFormEditView>();
        this.inrushForms = new ArrayList<CourantAppelActivationFormEditView>();
        this.nominalCurrentForms = new ArrayList<CourantNominaleActivationFormEditView>();
        this.blockedCurrentForms = new ArrayList<CourantCoupleBloqueCourantDysfonctionnelFormEditView>();
        this.cycleCurrentForms = new ArrayList<CourantCycleFormEditView>();

        currentProfilesEditView = new CurrentProfilesEditView(controller, psaSupply.getEdsConsolidateSupplyProfile());

        addComponent(currentProfilesEditView);
        addComponent(getStandbyView());
        addComponent(getParcModeView());
        addComponent(getMountingModeView());
        addComponent(getSwitchOnVoltageView());
        addComponent(getInrushCurrentView());
        addComponent(getNominalCurrentView());
        addComponent(getCycleCurrentView());
        addComponent(getBlockedCurrentView());
        discardChanges();
    }

    /**
     * This method initialize Eds PSA supply
     */
    private void initPsaSupply() {
        psaSupply = new EdsPsaMesureSupply(UUID.randomUUID().toString());
        // psaSupply.setEdsEds( eds );
        // Below code for the renewal data (not activated)
        if (supply.getEdsConsolidateSupply() != null) {
            Set<EdsProject> specificProjects = new HashSet<EdsProject>();
            specificProjects.add(eds.getEdsProject());
            for (EdsProjectEds follower : eds.getEdsProjectEdses()) {
                if (follower.getPedsReconductWithModif() == EdsProjectEds.RECONDUCTED_WITHOUT_MODIF) {
                    specificProjects.add(follower.getEdsProject());
                }
            }
            psaSupply.setPmsedsRef(supply.getEdsConsolidateSupply().getCsedsRef());
            psaSupply.setPmsedsSupplyName(supply.getEdsConsolidateSupply().getCsedsSupplyName());
            controller.reconductSupply(psaSupply, supply.getEdsConsolidateSupply(), specificProjects);
        }
    }

    /**
     * This method discard the changes of Asleep or awake network and inactive device (Customer mode)
     */
    private void discardStandbyChanges() {
        standbyForms.clear();
        standbyView.removeAllComponents();
        for (EdsReseauVeilleReveilleOrganeInactif data : (Set<EdsReseauVeilleReveilleOrganeInactif>) psaSupply
                .getEdsPmReseauVeilleReveilleOrganeInactifs()) {
            ReseauVeilleReveilleOrganeInactifFormEditView form = new ReseauVeilleReveilleOrganeInactifFormEditView(qcfMesuree, // Réponse QCF4
                    data, controller, false);
            standbyForms.add(form);
            standbyView.addComponent(form);
            form.discardChanges();
        }
    }

    /**
     * This method discard the changes of park mode
     */
    private void discardParcModeChanges() {
        parcmodeForms.clear();
        parcmodeView.removeAllComponents();
        if (qcfMesuree.getQcf2() == 0) {
            psaSupply.getEdsPmModeParcs().clear();
        } else {
            if (psaSupply.getEdsPmModeParcs().isEmpty()) {
                psaSupply.getEdsPmModeParcs().add(new EdsModeParc(UUID.randomUUID().toString()));
            }
        }
        for (EdsModeParc data : (Set<EdsModeParc>) psaSupply.getEdsPmModeParcs()) {
            ModeParcFormEditView form = new ModeParcFormEditView(qcfMesuree, // Réponse QCF1 prédéfini.
                    data, controller, false);
            parcmodeForms.add(form);
            parcmodeView.addComponent(form);
            form.discardChanges();
        }
    }

    //

    /**
     * This method discard the changes of assembled mode
     */
    private void discardMountingChanges() {
        mountingForms.clear();
        mountingView.removeAllComponents();

        final Button vBTaddTab = new Button("", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                EdsModeMontage data = new EdsModeMontage(UUID.randomUUID().toString());
                ModeMontageFormEditView form = new ModeMontageFormEditView(data, 1, qcfMesuree, controller, false) {
                    public void buttonClick(ClickEvent event) {
                        mountingForms.remove(this);
                        mountingView.removeComponent(this);
                    }
                };
                mountingForms.add(form);
                mountingView.addComponent(form, mountingView.getComponentCount() - 1);
                form.discardChanges();
            }
        });
        vBTaddTab.setImmediate(true);
        vBTaddTab.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png "));
        vBTaddTab.setStyleName(BaseTheme.BUTTON_LINK);
        if (qcfMesuree.getQcf3() == 0) {
            psaSupply.getEdsPmModeMontages().clear();
            vBTaddTab.setVisible(false);
        }

        for (EdsModeMontage data : (Set<EdsModeMontage>) psaSupply.getEdsPmModeMontages()) {
            ModeMontageFormEditView form = new ModeMontageFormEditView(data, data.getMmedsRemove(), qcfMesuree, controller, false) {
                public void buttonClick(ClickEvent event) {
                    mountingForms.remove(this);
                    mountingView.removeComponent(this);
                }
            };
            mountingForms.add(form);
            mountingView.addComponent(form);
            form.discardChanges();
        }
        mountingView.addComponent(vBTaddTab);
    }

    /**
     * This method discard the changes of Powered on current
     */
    private void discardSwitchingOnVoltageChanges() {
        switchingOnVoltageForms.clear();
        switchOnVoltageView.removeAllComponents();

        for (EdsCourantMiseSousTension data : (Set<EdsCourantMiseSousTension>) psaSupply.getEdsPmCourantMiseSousTensions()) {
            CourantDeMiseSousTensionFormEditView form = new CourantDeMiseSousTensionFormEditView(data, controller, false);
            switchingOnVoltageForms.add(form);
            switchOnVoltageView.addComponent(form);
            form.discardChanges();
        }

    }

    /**
     * This method discard the changes of Activation inrush current
     */
    private void discardInrushChanges() {
        inrushForms.clear();
        inrushView.removeAllComponents();

        final Button vBTaddTab = new Button("", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                EdsCourantAppelleActivation data = new EdsCourantAppelleActivation(UUID.randomUUID().toString());
                CourantAppelActivationFormEditView form = new CourantAppelActivationFormEditView(qcfMesuree, // Réponse QCF1 prédéfini.
                        data, true, controller, false) {
                    public void buttonClick(ClickEvent event) {
                        inrushForms.remove(this);
                        inrushView.removeComponent(this);
                    }
                };
                inrushForms.add(form);
                inrushView.addComponent(form, inrushView.getComponentCount() - 1);
                form.discardChanges();
            }
        });
        vBTaddTab.setImmediate(true);
        vBTaddTab.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png "));
        vBTaddTab.setStyleName(BaseTheme.BUTTON_LINK);

        for (EdsCourantAppelleActivation data : (Set<EdsCourantAppelleActivation>) psaSupply.getEdsPmCourantAppelleActivations()) {
            CourantAppelActivationFormEditView form = new CourantAppelActivationFormEditView(qcfMesuree, // Réponse QCF1 prédéfini.
                    data, true, controller, false) {
                public void buttonClick(ClickEvent event) {
                    inrushForms.remove(this);
                    inrushView.removeComponent(this);
                }
            };
            inrushForms.add(form);
            inrushView.addComponent(form);
            form.discardChanges();
        }
        inrushView.addComponent(vBTaddTab);
    }

    /**
     * This method discard the changes of cycle consumption current
     */
    private void discardCycleCurrentChanges() {
        cycleCurrentForms.clear();
        CycleCurrentView.removeAllComponents();

        // List of projects renewed without changes
        relatedProjects.clear();
        relatedProjects.add(eds.getEdsProject());
        for (EdsProjectEds epe : eds.getEdsProjectEdses()) {
            if (epe.getPedsReconductWithModif() == EdsProjectEds.RECONDUCTED_WITHOUT_MODIF) {
                relatedProjects.add(epe.getEdsProject());
            }
        }
        final Button vBTaddTab = new Button("", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                // TODO add choice button for projects.
                List<EdsProject> projects = new ArrayList<EdsProject>();

                for (EdsProjectEds epe : eds.getEdsProjectEdses()) {
                    if (epe.getPedsReconductWithModif() == EdsProjectEds.RECONDUCTED_WITH_MODIF) {
                        projects.add(epe.getEdsProject());
                    }
                }

                AddCycleCurrentWindow popup = new AddCycleCurrentWindow(PsaMeasureSupplyEditView.this, projects, relatedProjects, controller);
                popup.center();
                popup.setModal(true);
                getWindow().addWindow(popup);

            }
        });
        vBTaddTab.setImmediate(true);
        vBTaddTab.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png "));
        vBTaddTab.setStyleName(BaseTheme.BUTTON_LINK);

        for (EdsCourantCycle data : (Set<EdsCourantCycle>) psaSupply.getEdsCourantCycles()) {
            CourantCycleFormEditView form = null;

            form = new CourantCycleFormEditView(data, data.getCcedsRemove(), controller) {
                /*
                 * (non-Javadoc)
                 * 
                 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
                 */
                @Override
                public void buttonClick(ClickEvent event) {
                    cycleCurrentForms.remove(this);
                    CycleCurrentView.removeComponent(this);
                }
            };

            cycleCurrentForms.add(form);
            CycleCurrentView.addComponent(form);
            if (data.getCcedsWithModif() == EdsCourantCycle.RECONDUCTED_WITHOUT_MODIF) {
                updateProjects(data, relatedProjects);
            }
            form.discardChanges();
        }
        CycleCurrentView.addComponent(vBTaddTab);
    }

    /**
     * This method discard the changes of Activation nominal current
     */
    private void discardNominalCurrentChanges() {
        nominalCurrentForms.clear();
        nominalCurrentView.removeAllComponents();
        final Button vBTaddTab = new Button("", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                EdsCourantNominaleActivation data = new EdsCourantNominaleActivation(UUID.randomUUID().toString());
                CourantNominaleActivationFormEditView form = new CourantNominaleActivationFormEditView(qcfMesuree, data, true, false, controller,
                        false) {
                    public void buttonClick(ClickEvent event) {
                        nominalCurrentForms.remove(this);
                        nominalCurrentView.removeComponent(this);
                    }
                };

                nominalCurrentForms.add(form);
                nominalCurrentView.addComponent(form);
                form.discardChanges();

            }
        });
        vBTaddTab.setImmediate(true);
        vBTaddTab.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png "));
        vBTaddTab.setStyleName(BaseTheme.BUTTON_LINK);

        for (EdsCourantNominaleActivation data : (Set<EdsCourantNominaleActivation>) psaSupply.getEdsPmCourantNominaleActivations()) {
            CourantNominaleActivationFormEditView form = null;

            form = new CourantNominaleActivationFormEditView(qcfMesuree, data, data.isCnaedsRemove(), data.isCnaedsIEfficace(), controller, false) {
                public void buttonClick(ClickEvent event) {
                    nominalCurrentForms.remove(this);
                    nominalCurrentView.removeComponent(this);
                }
            };

            nominalCurrentForms.add(form);
            nominalCurrentView.addComponent(form);
            form.discardChanges();
        }
        nominalCurrentView.addComponent(vBTaddTab);
    }

    /**
     * This method discard the changes of Blocked couple current
     */
    private void discardBlockedCurrentChanges() {
        blockedCurrentForms.clear();
        blockedCurrentView.removeAllComponents();

        final Button vBTaddTab = new Button("", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                EdsCourantBloqueCourantDysfonctionnement data = new EdsCourantBloqueCourantDysfonctionnement(UUID.randomUUID().toString());
                CourantCoupleBloqueCourantDysfonctionnelFormEditView form = new CourantCoupleBloqueCourantDysfonctionnelFormEditView(qcfMesuree,
                        data, true, controller) {
                    public void buttonClick(ClickEvent event) {
                        blockedCurrentForms.remove(this);
                        blockedCurrentView.removeComponent(this);
                    }
                }; // QCF1 predefined response
                blockedCurrentForms.add(form);
                blockedCurrentView.addComponent(form, blockedCurrentView.getComponentCount() - 1);
                form.discardChanges();
            }
        });
        vBTaddTab.setImmediate(true);
        vBTaddTab.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png "));
        vBTaddTab.setStyleName(BaseTheme.BUTTON_LINK);

        for (EdsCourantBloqueCourantDysfonctionnement data : (Set<EdsCourantBloqueCourantDysfonctionnement>) psaSupply
                .getEdsPmCourantBloqueCourantDysfonctionnements()) {
            CourantCoupleBloqueCourantDysfonctionnelFormEditView form = new CourantCoupleBloqueCourantDysfonctionnelFormEditView(qcfMesuree, data,
                    false, controller) {
                public void buttonClick(ClickEvent event) {
                    blockedCurrentForms.remove(this);
                    blockedCurrentView.removeComponent(this);
                }
            }; // QCF1 predefined response
            blockedCurrentForms.add(form);
            blockedCurrentView.addComponent(form);
            form.discardChanges();
        }
        blockedCurrentView.addComponent(vBTaddTab);
    }

    /**
     * This method commit the changes of Asleep or awake network and inactive device (Customer mode)
     */
    private void commitStandbyChanges() {
        for (ReseauVeilleReveilleOrganeInactifFormEditView editForm : standbyForms) {
            editForm.commitChanges();
        }
    }

    /**
     * This method commit the changes of Park mode
     */
    private void commitParcmodeChanges() {
        for (ModeParcFormEditView editForm : parcmodeForms) {
            editForm.commitChanges();
        }
    }

    //

    /**
     * This method commit the changes of Assembled mode
     */
    private void commitMountingChanges() {
        psaSupply.getEdsPmModeMontages().clear();
        for (ModeMontageFormEditView editForm : mountingForms) {
            editForm.commitChanges();
            psaSupply.getEdsPmModeMontages().addAll(editForm.getAllEdsModeMontage());
        }
    }

    /**
     * This method commit the changes of Powered on current
     */
    private void commitSwitchingVoltageChanges() {
        psaSupply.getEdsPmCourantMiseSousTensions().clear();
        for (CourantDeMiseSousTensionFormEditView editForm : switchingOnVoltageForms) {
            editForm.commitChanges();
            psaSupply.getEdsPmCourantMiseSousTensions().addAll(editForm.getAllEdsCourantMiseSousTension());
        }
    }

    /**
     * This method commit the changes of Activation Inrush current
     */
    private void commitInrushChanges() {
        psaSupply.getEdsPmCourantAppelleActivations().clear();
        for (CourantAppelActivationFormEditView editForm : inrushForms) {
            editForm.commitChanges();
            psaSupply.getEdsPmCourantAppelleActivations().addAll(editForm.getAllIEdsCourantAppelleActivation());
        }
    }

    /**
     * This method commit the changes of Activation nominal current
     */
    private void commitNominalCurrentChanges() {
        psaSupply.getEdsPmCourantNominaleActivations().clear();
        for (CourantNominaleActivationFormEditView editForm : nominalCurrentForms) {
            editForm.commitChanges();
            psaSupply.getEdsPmCourantNominaleActivations().addAll(editForm.getAllEdsCourantNominaleActivation());
        }
    }

    /**
     * This method commit the changes of cycle consumption current
     */
    private void commitCycleCurrentChanges() {
        psaSupply.getEdsCourantCycles().clear();
        for (CourantCycleFormEditView editForm : cycleCurrentForms) {
            editForm.commitChanges();
            psaSupply.getEdsCourantCycles().addAll(editForm.getAllItemsToSave());
        }
    }

    /**
     * This method commit the changes of Blocked current couple
     */
    private void commitBlockedCurrentChanges() {
        psaSupply.getEdsPmCourantBloqueCourantDysfonctionnements().clear();
        for (CourantCoupleBloqueCourantDysfonctionnelFormEditView editForm : blockedCurrentForms) {
            editForm.commitChanges();
            psaSupply.getEdsPmCourantBloqueCourantDysfonctionnements().addAll(editForm.getAllEdsCourantBloqueCourantDysfonctionnement());
        }
    }

    /**
     * This method return panel for Asleep or awake network and inactive device (Customer mode)
     * 
     * @return Standby view
     */
    private Panel getStandbyView() {
        Panel container = new Panel(controller.getBundle().getString("consolid-veille-reveille-curent"));
        if (standbyView == null) {
            standbyView = new VerticalLayout();
            standbyView.setMargin(true);
            standbyView.setSpacing(true);
        }
        container.addComponent(standbyView);
        return container;
    }

    /**
     * This method return panel for Park mode
     * 
     * @return park mode view
     */
    private Panel getParcModeView() {
        Panel container = new Panel(controller.getBundle().getString("consolid-mode-parc-curent"));
        if (parcmodeView == null) {
            parcmodeView = new VerticalLayout();
            parcmodeView.setMargin(true);
            parcmodeView.setSpacing(true);
        }
        container.addComponent(parcmodeView);
        return container;
    }

    /**
     * This method return panel for Assembled mode
     * 
     * @return mounting mode view
     */
    private Panel getMountingModeView() {
        Panel container = new Panel(controller.getBundle().getString("consolid-mode-montage-curent"));
        if (mountingView == null) {
            mountingView = new VerticalLayout();
            mountingView.setMargin(true);
            mountingView.setSpacing(true);
        }
        container.addComponent(mountingView);
        return container;
    }

    /**
     * This method return panel for Powered on current
     * 
     * @return Switch On Voltage view
     */
    private Panel getSwitchOnVoltageView() {
        Panel container = new Panel(controller.getBundle().getString("consolid-mise-s-tension-curent"));
        if (switchOnVoltageView == null) {
            switchOnVoltageView = new VerticalLayout();
            switchOnVoltageView.setMargin(true);
            switchOnVoltageView.setSpacing(true);
        }
        container.addComponent(switchOnVoltageView);
        return container;
    }

    /**
     * This method return panel for Activation inrush current
     * 
     * @return Inrush current view
     */
    private Panel getInrushCurrentView() {
        Panel container = new Panel(controller.getBundle().getString("consolid-appel-active-curent"));
        if (inrushView == null) {
            inrushView = new VerticalLayout();
            inrushView.setMargin(true);
            inrushView.setSpacing(true);
        }
        container.addComponent(inrushView);
        return container;
    }

    /**
     * This method return panel for Activation nominal current
     * 
     * @return Nominal current view
     */
    private Panel getNominalCurrentView() {
        Panel container = new Panel(controller.getBundle().getString("consolid-active-curent"));
        if (nominalCurrentView == null) {
            nominalCurrentView = new VerticalLayout();
            nominalCurrentView.setMargin(true);
            nominalCurrentView.setSpacing(true);
        }
        container.addComponent(nominalCurrentView);
        return container;
    }

    /**
     * This method return panel for cycle consumptions
     * 
     * @return Cycle current view
     */
    private Panel getCycleCurrentView() {
        Panel container = new Panel(controller.getBundle().getString("consolid-conso-cycle"));
        if (CycleCurrentView == null) {
            CycleCurrentView = new VerticalLayout();
            CycleCurrentView.setMargin(true);
            CycleCurrentView.setSpacing(true);
        }
        container.addComponent(CycleCurrentView);
        return container;
    }

    /**
     * This method return panel for Blocked couplecurrent
     * 
     * @return Blocked current view
     */
    private Panel getBlockedCurrentView() {
        Panel container = new Panel(controller.getBundle().getString("consolid-bloqued-curent"));
        if (blockedCurrentView == null) {
            blockedCurrentView = new VerticalLayout();
            blockedCurrentView.setMargin(true);
            blockedCurrentView.setSpacing(true);
        }
        container.addComponent(blockedCurrentView);
        return container;
    }

    /**
     * This method update Eds PSA supply measuer
     */
    private void update() {
        psaSupply.setEdsQcf(new EdsQcf(supply.getEdsConsolidateSupply().getEdsQcf()));
        qcfMesuree = psaSupply.getEdsQcf();
        discardChanges();
    }

    /**
     * This method update projects with Eds current cycle
     * 
     * @param data Eds current cycle
     * @param relatedProjects List of EdsProjects
     */
    private void updateProjects(EdsCourantCycle data, List<EdsProject> relatedProjects) {
        data.getEdsProjects().clear();
        data.getEdsProjects().add(eds.getEdsProject());
        for (EdsProject ep : relatedProjects) {
            data.getEdsProjects().add(ep);
        }
    }
}
