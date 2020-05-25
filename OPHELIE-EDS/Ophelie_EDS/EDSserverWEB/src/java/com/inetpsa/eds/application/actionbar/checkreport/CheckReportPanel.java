/*
 * Creation : 20 mai 2015
 */
package com.inetpsa.eds.application.actionbar.checkreport;

import java.util.ResourceBundle;
import java.util.Set;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyMesure;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyTheoritic;
import com.inetpsa.eds.dao.model.EdsCourantAppelleActivation;
import com.inetpsa.eds.dao.model.EdsCourantBloqueCourantDysfonctionnement;
import com.inetpsa.eds.dao.model.EdsCourantMiseSousTension;
import com.inetpsa.eds.dao.model.EdsCourantNominaleActivation;
import com.inetpsa.eds.dao.model.EdsModeMontage;
import com.inetpsa.eds.dao.model.EdsModeParc;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.inetpsa.eds.dao.model.EdsReseauVeilleReveilleOrganeInactif;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class CheckReportPanel extends Panel {

    /**
     * UID
     */
    private static final long serialVersionUID = -6348862470280820687L;

    // PRIVATE
    private boolean valid = true;
    private final EdsConsolidateSupplyTheoritic supplyTheoritic;
    private final EdsApplicationController controller;
    private EdsQcf qcfConsolide;

    private ResourceBundle bundle;

    private Accordion acc;
    private Panel vPModeParc;
    private Panel vPModeMontage;
    private Panel vPCourantAppelActivation;
    private Panel vPCourantCouple;
    private Panel vPReseauVeilleReveille;
    private Panel vPCourantMiseSousTension;
    private Panel vPCourantNominalActivationPanel;

    private EdsConsolidateSupplyMesure supplyMesure;

    // PUBLIC
    /**
     * Constructor
     * 
     * @param edsSupply The supply
     */
    public CheckReportPanel(EdsConsolidateSupplyTheoritic supplyTheoritic, EdsApplicationController controller, EdsQcf qcfConsolide) {
        this.supplyMesure = null;
        this.supplyTheoritic = supplyTheoritic;
        this.controller = controller;
        this.qcfConsolide = qcfConsolide;

        this.bundle = controller.getBundle();

        init();
    }

    /**
     * Constructor
     * 
     * @param edsSupply The supply
     */
    public CheckReportPanel(EdsConsolidateSupplyMesure supplyMesure, EdsApplicationController controller, EdsQcf qcfConsolide) {
        this.supplyMesure = supplyMesure;
        this.supplyTheoritic = null;
        this.controller = controller;
        this.qcfConsolide = qcfConsolide;

        this.bundle = controller.getBundle();

        init();
    }

    /**
     * Check if the report is valid. A report is valid if there is no blank field in every data imported
     * 
     * @return True if valid, false otherwise
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Check if theorical values are present
     * 
     * @return
     */
    public boolean isTheoric() {
        return supplyTheoritic != null;
    }

    private Resource getIcon(boolean valid) {
        return valid ? ResourceManager.getInstance().getThemeIconResource("icons/tick.png") : ResourceManager.getInstance().getThemeIconResource(
                "icons/cross.png");
    }

    /**
     * Initialize the panel, and validate its data
     */
    private void init() {

        this.acc = new Accordion();

        if (this.supplyTheoritic != null) {
            // Reseau Veille Reveille Panel
            boolean valid = initReseauVeilleReveillePanel(supplyTheoritic.getEdsReseauVeilleReveilleOrganeInactifs());
            acc.addTab(vPReseauVeilleReveille, bundle.getString("consolid-veille-reveille-curent"), getIcon(valid));
            this.valid = valid && this.valid;

            // Parc panel
            if (qcfConsolide.getQcf2() == 1) {
                valid = initModeParcPanel(supplyTheoritic.getEdsModeParcs());
                acc.addTab(vPModeParc, bundle.getString("consolid-mode-parc-curent"), getIcon(valid));
                this.valid = valid && this.valid;
            }

            // Mode Montage
            if (qcfConsolide.getQcf3() == 1) {
                valid = initModeMontagePanel(supplyTheoritic.getEdsModeMontages());
                acc.addTab(vPModeMontage, bundle.getString("consolid-mode-montage-curent"), getIcon(valid));
                this.valid = valid && this.valid;
            }

            // Courant appel activation
            valid = initCourantAppelActivationPanel(supplyTheoritic.getEdsCourantAppelleActivations());
            acc.addTab(vPCourantAppelActivation, bundle.getString("consolid-appel-active-curent"), getIcon(valid));
            this.valid = valid && this.valid;

            // Courant couple
            valid = initCourantCouplePanel(supplyTheoritic.getEdsCourantBloqueCourantDysfonctionnements());
            acc.addTab(vPCourantCouple, bundle.getString("consolid-bloqued-curent"), getIcon(valid));
            this.valid = valid && this.valid;

            // Courant mise sous tension
            valid = initCourantMiseSousTensionPanel(supplyTheoritic.getEdsCourantMiseSousTensions());
            acc.addTab(vPCourantMiseSousTension, bundle.getString("consolid-mise-s-tension-curent"), getIcon(valid));
            this.valid = valid && this.valid;

            // Courant nominal activation
            valid = initCourantNominalActivationPanel(supplyTheoritic.getEdsCourantNominaleActivations());
            acc.addTab(vPCourantNominalActivationPanel, bundle.getString("consolid-veille-reveille-curent"), getIcon(valid));
            this.valid = valid && this.valid;
        } else {
            // Reseau Veille Reveille Panel
            boolean valid = initReseauVeilleReveillePanel(supplyMesure.getEdsReseauVeilleReveilleOrganeInactifMesures());
            acc.addTab(vPReseauVeilleReveille, bundle.getString("consolid-veille-reveille-curent"), getIcon(valid));
            this.valid = valid && this.valid;

            // Parc panel
            if (qcfConsolide.getQcf2() == 1) {
                valid = initModeParcPanel(supplyMesure.getEdsModeParcMesures());
                acc.addTab(vPModeParc, bundle.getString("consolid-mode-parc-curent"), getIcon(valid));
                this.valid = valid && this.valid;
            }

            // Mode Montage
            if (qcfConsolide.getQcf3() == 1) {
                valid = initModeMontagePanel(supplyMesure.getEdsModeMontageMesures());
                acc.addTab(vPModeMontage, bundle.getString("consolid-mode-montage-curent"), getIcon(valid));
                this.valid = valid && this.valid;
            }

            // Courant appel activation
            valid = initCourantAppelActivationPanel(supplyMesure.getEdsCourantAppelleActivationMesures());
            acc.addTab(vPCourantAppelActivation, bundle.getString("consolid-appel-active-curent"), getIcon(valid));
            this.valid = valid && this.valid;

            // Courant couple
            valid = initCourantCouplePanel(supplyMesure.getEdsCourantBloqueCourantDysfonctionnementMesures());
            acc.addTab(vPCourantCouple, bundle.getString("consolid-bloqued-curent"), getIcon(valid));
            this.valid = valid && this.valid;

            // Courant mise sous tension
            valid = initCourantMiseSousTensionPanel(supplyMesure.getEdsCourantMiseSousTensionMesures());
            acc.addTab(vPCourantMiseSousTension, bundle.getString("consolid-mise-s-tension-curent"), getIcon(valid));
            this.valid = valid && this.valid;

            // Courant nominal activation
            valid = initCourantNominalActivationPanel(supplyMesure.getEdsCourantNominaleActivationMesures());
            acc.addTab(vPCourantNominalActivationPanel, bundle.getString("consolid-veille-reveille-curent"), getIcon(valid));
            this.valid = valid && this.valid;
        }

        this.setStyleName("no-empty");
        this.addComponent(acc);
    }

    /**
     * Initialize the "Courant Nominal Activation" panel.
     * 
     * @return
     */
    private boolean initCourantNominalActivationPanel(Set<EdsCourantNominaleActivation> values) {
        boolean valid = true;
        vPCourantNominalActivationPanel = new Panel(controller.getBundle().getString("consolid-appel-active-curent"));

        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);

        for (EdsCourantNominaleActivation ecna : values) {
            CheckTableFormReadView cnafrv = new CheckTableFormReadView(controller);

            int i = 31;

            cnafrv.setCaption(ecna.getCnaedsName());

            if (qcfConsolide.getQcf1() == 1)
                cnafrv.addOrderToContainer(bundle.getString("check-report-Inomstab-Tmin"), ecna.getCnaedsIminStabMnt());
            cnafrv.addOrderToContainer(bundle.getString("check-report-Inomstab-Tmoy"), ecna.getCnaedsImoyStabMnt());
            if (qcfConsolide.getQcf1() == 1)
                cnafrv.addOrderToContainer(bundle.getString("check-report-Inomstab-Tmax"), ecna.getCnaedsImaxStabMnt());

            if (qcfConsolide.getQcf1() == 1)
                cnafrv.addOrderToContainer(bundle.getString("check-report-Tnomstab-Tmin"), ecna.getCnaedsTminStabMnt());
            cnafrv.addOrderToContainer(bundle.getString("check-report-Tnomstab-Tmoy"), ecna.getCnaedsTmoyStabMnt());
            if (qcfConsolide.getQcf1() == 1)
                cnafrv.addOrderToContainer(bundle.getString("check-report-Tnomstab-Tmax"), ecna.getCnaedsTmaxStabMnt());

            if (qcfConsolide.getQcf1() == 1)
                cnafrv.addOrderToContainer(bundle.getString("check-report-Ipirecasstab-Tmin"), ecna.getCnaedsIminPireCasMnt());
            cnafrv.addOrderToContainer(bundle.getString("check-report-Ipirecasstab-Tmoy"), ecna.getCnaedsImoyPireCasMnt());
            if (qcfConsolide.getQcf1() == 1)
                cnafrv.addOrderToContainer(bundle.getString("check-report-Ipirecasstab-Tmax"), ecna.getCnaedsImaxPireCasMnt());

            if (qcfConsolide.getQcf1() == 1)
                cnafrv.addOrderToContainer(bundle.getString("check-report-Tpirecasstab-Tmin"), ecna.getCnaedsTminPireCasMnt());
            cnafrv.addOrderToContainer(bundle.getString("check-report-Tpirecasstab-Tmoy"), ecna.getCnaedsTmoyPireCasMnt());
            if (qcfConsolide.getQcf1() == 1)
                cnafrv.addOrderToContainer(bundle.getString("check-report-Tpirecasstab-Tmax"), ecna.getCnaedsTmaxPireCasMnt());

            if (qcfConsolide.getQcfC3() == 1) {
                i += 6;

                if (qcfConsolide.getQcf1() == 1)
                    cnafrv.addOrderToContainer(bundle.getString("check-report-Inomstab-Tmin-10Mt"), ecna.getCnaedsIminStab10Mt());
                cnafrv.addOrderToContainer(bundle.getString("check-report-Inomstab-Tmoy-10Mt"), ecna.getCnaedsImoyStab10Mt());
                if (qcfConsolide.getQcf1() == 1)
                    cnafrv.addOrderToContainer(bundle.getString("check-report-Inomstab-Tmax-10Mt"), ecna.getCnaedsImaxStab10Mt());

                if (qcfConsolide.getQcf1() == 1)
                    cnafrv.addOrderToContainer(bundle.getString("check-report-Tnomstab-Tmin-10Mt"), ecna.getCnaedsTminStab10Mt());
                cnafrv.addOrderToContainer(bundle.getString("check-report-Tnomstab-Tmoy-10Mt"), ecna.getCnaedsTmoyStab10Mt());
                if (qcfConsolide.getQcf1() == 1)
                    cnafrv.addOrderToContainer(bundle.getString("check-report-Tnomstab-Tmax-10Mt"), ecna.getCnaedsTmaxStab10Mt());
            }

            if (qcfConsolide.getQcf1() == 1)
                cnafrv.addOrderToContainer(bundle.getString("check-report-Inomstab-Tmin-13Mt"), ecna.getCnaedsIminStab13Mt());
            cnafrv.addOrderToContainer(bundle.getString("check-report-Inomstab-Tmoy-13Mt"), ecna.getCnaedsImoyStab13Mt());
            if (qcfConsolide.getQcf1() == 1)
                cnafrv.addOrderToContainer(bundle.getString("check-report-Inomstab-Tmax-13Mt"), ecna.getCnaedsImaxStab13Mt());

            if (qcfConsolide.getQcf1() == 1)
                cnafrv.addOrderToContainer(bundle.getString("check-report-Tnomstab-Tmin-13Mt"), ecna.getCnaedsTminStab13Mt());
            cnafrv.addOrderToContainer(bundle.getString("check-report-Tnomstab-Tmoy-13Mt"), ecna.getCnaedsTmoyStab13Mt());
            if (qcfConsolide.getQcf1() == 1)
                cnafrv.addOrderToContainer(bundle.getString("check-report-Tnomstab-Tmax-13Mt"), ecna.getCnaedsTmaxStab13Mt());

            if (qcfConsolide.getQcfC2() == 1) {
                i += 6;

                if (qcfConsolide.getQcf1() == 1)
                    cnafrv.addOrderToContainer(bundle.getString("check-report-Inomstab-Tmin-15Mt"), ecna.getCnaedsIminStab15Mt());
                cnafrv.addOrderToContainer(bundle.getString("check-report-Inomstab-Tmoy-15Mt"), ecna.getCnaedsImoyStab15Mt());
                if (qcfConsolide.getQcf1() == 1)
                    cnafrv.addOrderToContainer(bundle.getString("check-report-Inomstab-Tmax-15Mt"), ecna.getCnaedsImaxStab15Mt());

                if (qcfConsolide.getQcf1() == 1)
                    cnafrv.addOrderToContainer(bundle.getString("check-report-Tnomstab-Tmin-15Mt"), ecna.getCnaedsTminStab15Mt());
                cnafrv.addOrderToContainer(bundle.getString("check-report-Tnomstab-Tmoy-15Mt"), ecna.getCnaedsTmoyStab15Mt());
                if (qcfConsolide.getQcf1() == 1)
                    cnafrv.addOrderToContainer(bundle.getString("check-report-Tnomstab-Tmax-15Mt"), ecna.getCnaedsTmaxStab15Mt());
            }

            if (qcfConsolide.getQcfC3() == 1) {
                i += 6;

                if (qcfConsolide.getQcf1() == 1)
                    cnafrv.addOrderToContainer(bundle.getString("check-report-Ipirecasstab-Tmin-10Mt"), ecna.getCnaedsIminPireCas10Mt());
                cnafrv.addOrderToContainer(bundle.getString("check-report-Ipirecasstab-Tmoy-10Mt"), ecna.getCnaedsImoyPireCas10Mt());
                if (qcfConsolide.getQcf1() == 1)
                    cnafrv.addOrderToContainer(bundle.getString("check-report-Ipirecasstab-Tmax-10Mt"), ecna.getCnaedsImaxPireCas10Mt());

                if (qcfConsolide.getQcf1() == 1)
                    cnafrv.addOrderToContainer(bundle.getString("check-report-Tpirecasstab-Tmin-10Mt"), ecna.getCnaedsTminPireCas10Mt());
                cnafrv.addOrderToContainer(bundle.getString("check-report-Tpirecasstab-Tmoy-10Mt"), ecna.getCnaedsTmoyPireCas10Mt());
                if (qcfConsolide.getQcf1() == 1)
                    cnafrv.addOrderToContainer(bundle.getString("check-report-Tpirecasstab-Tmax-10Mt"), ecna.getCnaedsTmaxPireCas10Mt());
            }

            if (qcfConsolide.getQcf1() == 1)
                cnafrv.addOrderToContainer(bundle.getString("check-report-Ipirecasstab-Tmin-13Mt"), ecna.getCnaedsIminPireCas13Mt());
            cnafrv.addOrderToContainer(bundle.getString("check-report-Ipirecasstab-Tmin-13Mt"), ecna.getCnaedsImoyPireCas13Mt());
            if (qcfConsolide.getQcf1() == 1)
                cnafrv.addOrderToContainer(bundle.getString("check-report-Ipirecasstab-Tmin-13Mt"), ecna.getCnaedsImaxPireCas13Mt());

            if (qcfConsolide.getQcf1() == 1)
                cnafrv.addOrderToContainer(bundle.getString("check-report-Tpirecasstab-Tmin-13Mt"), ecna.getCnaedsTminPireCas13Mt());
            cnafrv.addOrderToContainer(bundle.getString("check-report-Tpirecasstab-Tmoy-13Mt"), ecna.getCnaedsTmoyPireCas13Mt());
            if (qcfConsolide.getQcf1() == 1)
                cnafrv.addOrderToContainer(bundle.getString("check-report-Tpirecasstab-Tmax-13Mt"), ecna.getCnaedsTmaxPireCas13Mt());

            if (qcfConsolide.getQcfC2() == 1) {
                if (qcfConsolide.getQcf1() == 1)
                    cnafrv.addOrderToContainer(bundle.getString("check-report-Ipirecasstab-Tmin-15Mt"), ecna.getCnaedsIminPireCas15Mt());
                cnafrv.addOrderToContainer(bundle.getString("check-report-Ipirecasstab-Tmoy-15Mt"), ecna.getCnaedsImoyPireCas15Mt());
                if (qcfConsolide.getQcf1() == 1)
                    cnafrv.addOrderToContainer(bundle.getString("check-report-Ipirecasstab-Tmax-15Mt"), ecna.getCnaedsImaxPireCas15Mt());

                if (qcfConsolide.getQcf1() == 1)
                    cnafrv.addOrderToContainer(bundle.getString("check-report-Tpirecasstab-Tmin-15Mt"), ecna.getCnaedsTminPireCas15Mt());
                cnafrv.addOrderToContainer(bundle.getString("check-report-Tpirecasstab-Tmoy-15Mt"), ecna.getCnaedsTmoyPireCas15Mt());
                if (qcfConsolide.getQcf1() == 1)
                    cnafrv.addOrderToContainer(bundle.getString("check-report-Tpirecasstab-Tmax-15Mt"), ecna.getCnaedsTmaxPireCas15Mt());
            }

            if (qcfConsolide.getQcfC4() == 1) {
                cnafrv.addOrderToContainer(bundle.getString("check-report-Inom-dem"), ecna.getCnaedsImoyDem());
                cnafrv.addOrderToContainer(bundle.getString("check-report-Tnom-dem"), ecna.getCnaedsTmoyDem());
            }

            cnafrv.setPageLength(i);
            vl.addComponent(cnafrv);

            valid = valid && cnafrv.isValid();
        }
        vPCourantNominalActivationPanel.addComponent(vl);

        return valid;
    }

    /**
     * Initialize the "Courant Mise Sous Tension" panel.
     * 
     * @return
     */
    private boolean initCourantMiseSousTensionPanel(Set<EdsCourantMiseSousTension> values) {
        boolean valid = true;
        vPCourantMiseSousTension = new Panel(controller.getBundle().getString("consolid-mise-s-tension-curent"));

        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);

        for (EdsCourantMiseSousTension ecmst : values) {
            CheckTableFormReadView cmstrvt = new CheckTableFormReadView(controller);
            cmstrvt.setPageLength(2);

            cmstrvt.setCaption(ecmst.getCmstedsName());

            cmstrvt.addOrderToContainer(bundle.getString("check-report-Imst-Tpirecas"), ecmst.getCmstedsTpirecasImst());
            cmstrvt.addOrderToContainer(bundle.getString("check-report-ImstDelta-Tpirecas"), ecmst.getCmstedsTpirecasDt());

            vl.addComponent(cmstrvt);

            valid = valid && cmstrvt.isValid();
        }
        vPCourantMiseSousTension.addComponent(vl);

        return valid;
    }

    /**
     * Initialize the "Courant Couple" panel.
     * 
     * @return
     */
    private boolean initCourantCouplePanel(Set<EdsCourantBloqueCourantDysfonctionnement> values) {
        boolean valid = true;
        vPCourantCouple = new Panel(controller.getBundle().getString("consolid-bloqued-curent"));

        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);

        for (EdsCourantBloqueCourantDysfonctionnement ecbcd : values) {
            CheckTableFormReadView ccrvt = new CheckTableFormReadView(controller);
            ccrvt.setPageLength(7);

            ccrvt.setCaption(ecbcd.getCbcdedsTitre());

            if (qcfConsolide.getQcf1() == 1)
                ccrvt.addOrderToContainer(bundle.getString("check-report-Idys-Tmin"), ecbcd.getCbcdedsTminIdys());
            ccrvt.addOrderToContainer(bundle.getString("check-report-Idys-Tmoy"), ecbcd.getCbcdedsTmoyIdys());
            if (qcfConsolide.getQcf1() == 1)
                ccrvt.addOrderToContainer(bundle.getString("check-report-Idys-Tmax"), ecbcd.getCbcdedsTmaxIdys());

            if (qcfConsolide.getQcf1() == 1)
                ccrvt.addOrderToContainer(bundle.getString("check-report-Tdys-Tmin"), ecbcd.getCbcdedsTminTdys());
            ccrvt.addOrderToContainer(bundle.getString("check-report-Tdys-Tmoy"), ecbcd.getCbcdedsTmoyTdys());
            if (qcfConsolide.getQcf1() == 1)
                ccrvt.addOrderToContainer(bundle.getString("check-report-Tdys-Tmax"), ecbcd.getCbcdedsTmaxTdys());

            vl.addComponent(ccrvt);

            valid = valid && ccrvt.isValid();
        }
        vPCourantCouple.addComponent(vl);

        return valid;
    }

    /**
     * Initialize the "Courant Appel Activation" panel
     * 
     * @return
     */
    private boolean initCourantAppelActivationPanel(Set<EdsCourantAppelleActivation> values) {
        boolean valid = true;
        vPCourantAppelActivation = new Panel(bundle.getString("consolid-appel-active-curent"));

        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);

        for (EdsCourantAppelleActivation ecaa : values) {
            CheckTableFormReadView caarvt = new CheckTableFormReadView(controller);
            caarvt.setPageLength(6);

            caarvt.setCaption(ecaa.getCaaedsTitre());

            if (qcfConsolide.getQcf1() == 1)
                caarvt.addOrderToContainer(bundle.getString("check-report-Imax-Tmin"), ecaa.getCaaedsTminImaxPulse());
            caarvt.addOrderToContainer(bundle.getString("check-report-Imax-Tmoy"), ecaa.getCaaedsTmoyImaxPulse());
            if (qcfConsolide.getQcf1() == 1)
                caarvt.addOrderToContainer(bundle.getString("check-report-Imax-Tmax"), ecaa.getCaaedsTmaxImaxPulse());

            if (qcfConsolide.getQcf1() == 1)
                caarvt.addOrderToContainer(bundle.getString("check-report-ImaxDelta-Tmin"), ecaa.getCaaedsTminDtPulse());
            caarvt.addOrderToContainer(bundle.getString("check-report-ImaxDelta-Tmoy"), ecaa.getCaaedsTmoyDtPulse());
            if (qcfConsolide.getQcf1() == 1)
                caarvt.addOrderToContainer(bundle.getString("check-report-ImaxDelta-Tmax"), ecaa.getCaaedsTmaxDtPulse());

            vl.addComponent(caarvt);

            valid = valid && caarvt.isValid();
        }
        vPCourantAppelActivation.addComponent(vl);

        return valid;
    }

    /**
     * Initialize the "Mode montage" panel.
     * 
     * @return
     */
    private boolean initModeMontagePanel(Set<EdsModeMontage> values) {
        boolean valid = true;
        vPModeMontage = new Panel(bundle.getString("consolid-mode-montage-curent"));

        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);

        for (EdsModeMontage modeMontage : values) {
            CheckTableFormReadView mmrvt = new CheckTableFormReadView(controller);
            mmrvt.setPageLength(1);

            mmrvt.setCaption(modeMontage.getMmedsModeMontageTitre());

            mmrvt.addOrderToContainer(bundle.getString("check-report-montage-Tmoy"), modeMontage.getMmedsTmoyModeMontage());

            vl.addComponent(mmrvt);

            valid = valid && mmrvt.isValid();
        }
        vPModeMontage.addComponent(vl);

        return valid;
    }

    /**
     * Initialize the "Mode Parc" panel.
     */
    private boolean initModeParcPanel(Set<EdsModeParc> values) {
        vPModeParc = new Panel(controller.getBundle().getString("consolid-mode-parc-curent"));
        boolean valid = true;

        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);

        for (EdsModeParc parcMode : values) {
            CheckTableFormReadView mprvt = new CheckTableFormReadView(controller);
            mprvt.setPageLength(1);

            mprvt.setCaption(parcMode.getMpedsName());

            if (qcfConsolide.getQcf1() == 1) {
                mprvt.addOrderToContainer(bundle.getString("check-report-Iveil-Tmoy"), parcMode.getMpedsTmoyModeParc());
                mprvt.addOrderToContainer(bundle.getString("check-report-Iveil-Tmax"), parcMode.getMpedsTmaxModeParc());
            } else {
                mprvt.addOrderToContainer(bundle.getString("check-report-Iveil-Tmoy"), parcMode.getMpedsTmoyModeParc());
            }

            vl.addComponent(mprvt);

            valid = valid && mprvt.isValid();
        }
        vPModeParc.addComponent(vl);

        return valid;
    }

    private boolean initReseauVeilleReveillePanel(Set<EdsReseauVeilleReveilleOrganeInactif> values) {
        vPReseauVeilleReveille = new Panel(bundle.getString("consolid-veille-reveille-curent"));
        boolean valid = true;
        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);

        for (EdsReseauVeilleReveilleOrganeInactif ervroi : values) {
            CheckTableFormReadView rvroifrv = new CheckTableFormReadView(controller);

            rvroifrv.setCaption(ervroi.getRvroiedsName());

            if (qcfConsolide.getQcf1() == 1)
                rvroifrv.addOrderToContainer(bundle.getString("check-report-Iveille-moteur-non-tournant-Tmin"),
                        ervroi.getRvroiedsTminIveilleMoteurNonTourant());
            rvroifrv.addOrderToContainer(bundle.getString("check-report-Iveille-moteur-non-tournant-Tmoy"),
                    ervroi.getRvroiedsTmoyIveilleMoteurNonTourant());
            if (qcfConsolide.getQcf1() == 1)
                rvroifrv.addOrderToContainer(bundle.getString("check-report-Iveille-moteur-non-tournant-Tmax"),
                        ervroi.getRvroiedsTmaxIveilleMoteurNonTourant());

            if (qcfConsolide.getQcfC1() == 1) {
                if (qcfConsolide.getQcf1() == 1)
                    rvroifrv.addOrderToContainer(bundle.getString("check-report-Iveille8h-moteur-non-tournant-Tmin"),
                            ervroi.getRvroiedsTminIveille8hMoteurNonTourant());
                rvroifrv.addOrderToContainer(bundle.getString("check-report-Iveille8h-moteur-non-tournant-Tmoy"),
                        ervroi.getRvroiedsTmoyIveille8hMoteurNonTourant());
                if (qcfConsolide.getQcf1() == 1)
                    rvroifrv.addOrderToContainer(bundle.getString("check-report-Iveille8h-moteur-non-tournant-Tmax"),
                            ervroi.getRvroiedsTmaxIveille8hMoteurNonTourant());

                if (qcfConsolide.getQcf1() == 1)
                    rvroifrv.addOrderToContainer(bundle.getString("check-report-Iveille21j-moteur-non-tournant-Tmin"),
                            ervroi.getRvroiedsTminIveille21hMoteurNonTourant());
                rvroifrv.addOrderToContainer(bundle.getString("check-report-Iveille21j-moteur-non-tournant-Tmoy"),
                        ervroi.getRvroiedsTmoyIveille21hMoteurNonTourant());
                if (qcfConsolide.getQcf1() == 1)
                    rvroifrv.addOrderToContainer(bundle.getString("check-report-Iveille21j-moteur-non-tournant-Tmax"),
                            ervroi.getRvroiedsTmaxIveille21hMoteurNonTourant());

                if (qcfConsolide.getQcf1() == 1)
                    rvroifrv.addOrderToContainer(bundle.getString("check-report-Iveille30j-moteur-non-tournant-Tmin"),
                            ervroi.getRvroiedsTminIveille30hMoteurNonTourant());
                rvroifrv.addOrderToContainer(bundle.getString("check-report-Iveille30j-moteur-non-tournant-Tmoy"),
                        ervroi.getRvroiedsTmoyIveille30hMoteurNonTourant());
                if (qcfConsolide.getQcf1() == 1)
                    rvroifrv.addOrderToContainer(bundle.getString("check-report-Iveille30j-moteur-non-tournant-Tmax"),
                            ervroi.getRvroiedsTmaxIveille30hMoteurNonTourant());
            }

            if (qcfConsolide.getQcf1() == 1)
                rvroifrv.addOrderToContainer(bundle.getString("check-report-Ireveille-moteur-non-tournant-Tmin"),
                        ervroi.getRvroiedsTminIreveilleInactifMoteurNonTourant());
            rvroifrv.addOrderToContainer(bundle.getString("check-report-Ireveille-moteur-non-tournant-Tmoy"),
                    ervroi.getRvroiedsTmoyIreveilleInactifMoteurNonTourant());
            if (qcfConsolide.getQcf1() == 1)
                rvroifrv.addOrderToContainer(bundle.getString("check-report-Ireveille-moteur-non-tournant-Tmax"),
                        ervroi.getRvroiedsTmaxIreveilleInactifMoteurNonTourant());

            if (qcfConsolide.getQcfC3() == 1) {
                if (qcfConsolide.getQcf1() == 1)
                    rvroifrv.addOrderToContainer(bundle.getString("check-report-Ireveille-moteur-tournant-Tmin-10Mt"),
                            ervroi.getRvroiedsTminIreveilleInactif10vMoteurTourant());
                rvroifrv.addOrderToContainer(bundle.getString("check-report-Ireveille-moteur-tournant-Tmoy-10Mt"),
                        ervroi.getRvroiedsTmoyIreveilleInactif10vMoteurTourant());
                if (qcfConsolide.getQcf1() == 1)
                    rvroifrv.addOrderToContainer(bundle.getString("check-report-Ireveille-moteur-tournant-Tmax-10Mt"),
                            ervroi.getRvroiedsTmaxIreveilleInactif10vMoteurTourant());
            }

            if (qcfConsolide.getQcf1() == 1)
                rvroifrv.addOrderToContainer(bundle.getString("check-report-Ireveille-moteur-tournant-Tmin-13Mt"),
                        ervroi.getRvroiedsTminIreveilleInactif13vMoteurTourant());
            rvroifrv.addOrderToContainer(bundle.getString("check-report-Ireveille-moteur-tournant-Tmoy-13Mt"),
                    ervroi.getRvroiedsTmoyIreveilleInactif13vMoteurTourant());
            if (qcfConsolide.getQcf1() == 1)
                rvroifrv.addOrderToContainer(bundle.getString("check-report-Ireveille-moteur-tournant-Tmax-13Mt"),
                        ervroi.getRvroiedsTmaxIreveilleInactif13vMoteurTourant());

            if (qcfConsolide.getQcfC2() == 1) {
                if (qcfConsolide.getQcf1() == 1)
                    rvroifrv.addOrderToContainer(bundle.getString("check-report-Ireveille-moteur-tournant-Tmin-15Mt"),
                            ervroi.getRvroiedsTminIreveilleInactif15vMoteurTourant());
                rvroifrv.addOrderToContainer(bundle.getString("check-report-Ireveille-moteur-tournant-Tmoy-15Mt"),
                        ervroi.getRvroiedsTmoyIreveilleInactif15vMoteurTourant());
                if (qcfConsolide.getQcf1() == 1)
                    rvroifrv.addOrderToContainer(bundle.getString("check-report-Ireveille-moteur-tournant-Tmax-15Mt"),
                            ervroi.getRvroiedsTmaxIreveilleInactif15vMoteurTourant());
            }

            vl.addComponent(rvroifrv);

            valid = valid && rvroifrv.isValid();
        }

        vPReseauVeilleReveille.addComponent(vl);

        return valid;
    }
}
