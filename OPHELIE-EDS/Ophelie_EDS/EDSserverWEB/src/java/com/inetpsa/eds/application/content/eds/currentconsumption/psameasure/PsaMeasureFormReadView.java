package com.inetpsa.eds.application.content.eds.currentconsumption.psameasure;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsConsolidateCurentFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsPsaMesureSupply;
import com.inetpsa.eds.dao.model.EdsSupply;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * This class provide Component for reading the PSA measurement form
 * 
 * @author Geometric Ltd.
 */
public class PsaMeasureFormReadView extends A_EdsReadForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param eds Object of EdsEDs
     * @param formData Object of EdsConsolidateCurentFormData
     * @param controller Controller of EDS application
     */
    public PsaMeasureFormReadView(EdsEds eds, EdsConsolidateCurentFormData formData, EdsApplicationController controller) {
        super(controller);
        this.eds = eds;
        this.controller = controller;
        this.formData = formData;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEdsRef()
     */
    @Override
    public String getEdsRef() {
        return eds.getEdsRef();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormName()
     */
    @Override
    public String getFormName() {
        return "menu-app-mesure";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormTitle()
     */
    @Override
    public String getFormTitle() {
        return "conso-psa-mesure-title";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        return PsaMeasureFormBuilder.ID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshViewData()
     */
    @Override
    public void refreshViewData() {
        vTSalims.removeAllComponents();
        supplyViews.clear();
        for (EdsSupply supply : (Set<EdsSupply>) formData.getEdsSupplies()) {
            EdsPsaMesureSupply psaMesureSupply = supply.getEdsPsaMesureSupply();
            if (psaMesureSupply != null) {
                PsaMeasureSupplyReadView supplyReadView = new PsaMeasureSupplyReadView(supply, eds, psaMesureSupply, controller);
                vTSalims.addTab(supplyReadView, supply.getSedsSupplyName());
                supplyViews.add(supplyReadView);
            }
        }
        if (supplyViews.isEmpty()) {
            showEmptyView();
        }
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
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        formData = controller.getFormDataModel(eds, formData.getClass());

        eds = EDSdao.getInstance().getEdsByRef(eds.getEdsRef());

    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsConsolidateCurentFormData
     */
    private EdsConsolidateCurentFormData formData;
    /**
     * Variable to hold list of PsaMeasureSupplyReadView
     */
    private List<PsaMeasureSupplyReadView> supplyViews;
    /**
     * Variable to hold value of TabSheet
     */
    private TabSheet vTSalims;

    /**
     * Initialize Read view for PSA measurement form
     */
    private void init() {
        vTSalims = new TabSheet();
        vTSalims.setSizeFull();
        addComponent(vTSalims);
        supplyViews = new ArrayList<PsaMeasureSupplyReadView>();
    }

    /**
     * This method show View for Empty PSA measurement
     */
    private void showEmptyView() {
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setMargin(true);
        contentLayout.addComponent(new Label(controller.getBundle().getString("psa-mesure-no-mesure-infos")));
        vTSalims.addTab(contentLayout, controller.getBundle().getString("psa-mesure-title"));
    }
}
