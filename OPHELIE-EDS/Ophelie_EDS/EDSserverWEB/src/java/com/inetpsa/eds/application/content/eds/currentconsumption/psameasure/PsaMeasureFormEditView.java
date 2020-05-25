package com.inetpsa.eds.application.content.eds.currentconsumption.psameasure;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.model.EdsConsolidateCurentFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsSupply;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * This class provide Component for editing the PSA measurement form
 * 
 * @author Geometric Ltd.
 */
public class PsaMeasureFormEditView extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param eds Object of EdsEds
     * @param formData Object of EdsConsolidateCurentFormData
     * @param controller Controller of EDS application
     */
    public PsaMeasureFormEditView(EdsEds eds, EdsConsolidateCurentFormData formData, EdsApplicationController controller) {
        this.eds = eds;
        this.formData = formData;
        this.controller = controller;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        for (PsaMeasureSupplyEditView editView : supplyViews) {
            editView.commitChanges();
        }
        eds.setEdsModifDate(new Date());
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        vTSalims.removeAllComponents();
        supplyViews.clear();
        for (EdsSupply supply : (Set<EdsSupply>) formData.getEdsSupplies()) {
            PsaMeasureSupplyEditView supplyEditView = new PsaMeasureSupplyEditView(supply, eds, controller);
            vTSalims.addTab(supplyEditView, supply.getSedsSupplyName());
            supplyViews.add(supplyEditView);

        }
        if (supplyViews.isEmpty()) {
            showEmptyView();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.singleton((Object) formData);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        for (PsaMeasureSupplyEditView editView : supplyViews) {
            if (!editView.isValid()) {
                return false;
            }
        }
        return true;
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
        formData = controller.getFormDataModel(eds, formData.getClass());
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;
    /**
     * Variable to hold value of Controller of Eds application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsConsolidateCurentFormData
     */
    private EdsConsolidateCurentFormData formData;
    /**
     * Variable to hold list of PsaMeasureSupplyEditView
     */
    private List<PsaMeasureSupplyEditView> supplyViews;
    /**
     * Variable to hold value of TabSheet
     */
    private TabSheet vTSalims;

    /**
     * Initialize Edit view for PSA Measurement form
     */
    private void init() {
        vTSalims = new TabSheet();
        vTSalims.setSizeFull();
        addComponent(vTSalims);
        supplyViews = new ArrayList<PsaMeasureSupplyEditView>();
    }

    /**
     * This method show View for Empty PSA measurement
     */
    private void showEmptyView() {
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setMargin(true);
        contentLayout.addComponent(new Label(controller.getBundle().getString("psa-mesure-no-alim")));
        vTSalims.addTab(contentLayout, controller.getBundle().getString("psa-mesure-title"));
    }
}
