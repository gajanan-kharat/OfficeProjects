/*
 * Creation : 19 mai 2015
 */
package com.inetpsa.eds.application.actionbar.checkreport;

import com.inetpsa.eds.application.A_EdsWindow;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsComportementConsolideFormData;
import com.inetpsa.eds.dao.model.EdsConsolidateCurentFormData;
import com.inetpsa.eds.dao.model.EdsSupply;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;

public class CheckReportWindow extends A_EdsWindow {

    private EdsComportementConsolideFormData edsComportementConsolideFormData;

    // PUBLIC
    public CheckReportWindow(EdsApplicationController controller, EdsConsolidateCurentFormData edsConsolidateCurentFormData,
            EdsComportementConsolideFormData edsComportementConsolideFormData) {
        super(controller.getBundle().getString("check-report"));
        this.controller = controller;
        this.edsConsolidateCurentFormData = edsConsolidateCurentFormData;
        this.edsComportementConsolideFormData = edsComportementConsolideFormData;
        init();
    }

    // PRIVATE
    /**
     * UID
     */
    private static final long serialVersionUID = 404395020946361087L;

    /**
     * Variable to store the window title.
     */
    private String windowTitle;

    /**
     * Variable to store the EDS application controller.
     */
    private EdsApplicationController controller;

    /**
     * Variable to hold value of EdsEds
     */
    private EdsConsolidateCurentFormData edsConsolidateCurentFormData;

    /**
     * Tabbed pane.
     */
    private TabSheet tabSheet;

    /**
     * Initialize the window.
     */
    private void init() {
        ResourceManager.getInstance().getThemeIconResource("icons/check-report.png");

        tabSheet = new TabSheet();

        for (EdsSupply supply : edsConsolidateCurentFormData.getEdsSupplies()) {
            Label label = new Label("Supply : " + supply.getSedsSupplyName());

            TabSheet internalTabSheet = new TabSheet();

            // Theoric
            CheckReportPanel panelTheoric = new CheckReportPanel(supply.getEdsConsolidateSupply().getEdsConsolidateSupplyTheoritic(), controller,
                    supply.getEdsConsolidateSupply().getEdsQcf());

            // Mesure
            CheckReportPanel panelMesure = new CheckReportPanel(supply.getEdsConsolidateSupply().getEdsConsolidateSupplyMesure(), controller, supply
                    .getEdsConsolidateSupply().getEdsQcf());

            // Model
            Panel panel = new Panel();
            panel.setStyleName("no-empty");
            CheckTableFormReadView panelModel = new CheckTableFormReadView(controller);
            panelModel.setCaption(controller.getBundle().getString("Admin-droit-form-comp-cons"));

            panelModel.addOrderToContainer(controller.getBundle().getString("comp-consolide-type-pilotage"),
                    edsComportementConsolideFormData.getCocofdTypePilotage());
            panelModel.addOrderToContainer(controller.getBundle().getString("comp-consolide-freq-plage"),
                    edsComportementConsolideFormData.getCocofdPlageFrequence());
            panelModel.addOrderToContainer(controller.getBundle().getString("comp-consolide-rlc-model"),
                    edsComportementConsolideFormData.getCocofdModeleRlcImage());
            panelModel.addOrderToContainer(controller.getBundle().getString("comp-consolide-saber-shema"),
                    edsComportementConsolideFormData.getCocofdModeleSaberImage());
            panel.addComponent(panelModel);

            internalTabSheet.addTab(panelTheoric, controller.getBundle().getString("consolid-theoric-data"), ResourceManager.getInstance()
                    .getThemeIconResource(panelTheoric.isValid() ? "icons/tick.png" : "icons/cross.png"));
            internalTabSheet.addTab(panelMesure, controller.getBundle().getString("consolid-mesured-data"), ResourceManager.getInstance()
                    .getThemeIconResource(panelMesure.isValid() ? "icons/tick.png" : "icons/cross.png"));
            internalTabSheet.addTab(panel, "Model",
                    ResourceManager.getInstance().getThemeIconResource(panelModel.isValid() ? "icons/tick.png" : "icons/cross.png"));

            if (panelTheoric.isValid() && panelMesure.isValid() && panelModel.isValid())
                tabSheet.addTab(internalTabSheet, supply.getSedsSupplyName(), ResourceManager.getInstance().getThemeIconResource("icons/tick.png"));
            else
                tabSheet.addTab(internalTabSheet, supply.getSedsSupplyName(), ResourceManager.getInstance().getThemeIconResource("icons/cross.png"));
        }

        this.addComponent(tabSheet);
    }
}
