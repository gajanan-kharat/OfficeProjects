package com.inetpsa.eds.application.content.eds.currentconsumption.driftdriver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsDriftInfo;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsSupply;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

/**
 * This class provide Edit view for drift control form
 * 
 * @author Geometric Ltd.
 */
public class EdsDriftDriverFormEditView extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param eds Eds details
     * @param controller Controller of EDS application
     */
    public EdsDriftDriverFormEditView(EdsEds eds, EdsApplicationController controller) {
        this.eds = eds;
        this.controller = controller;
        init();
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
     * Variable to hold value of Tabsheet
     */
    private TabSheet vTScontainer;
    /**
     * Variable to hold list of EdsSupply
     */
    private List<EdsSupply> supplies;

    /**
     * Variable to hold set of validated EdsDriftInfo
     */
    private Set<EdsDriftInfo> validatedDrifts;
    /**
     * Variable to hold set of invalidated EdsSupply
     */
    private Set<EdsDriftInfo> unvalidatedDrifts;
    /**
     * Variable to hold list of SupplyDriftDriverEditView
     */
    private List<SupplyDriftDriverEditView> driftViews;

    /**
     * Initialize Edit view of drift control form
     */
    private void init() {
        setMargin(true);
        this.validatedDrifts = new HashSet<EdsDriftInfo>();
        this.unvalidatedDrifts = new HashSet<EdsDriftInfo>();
        this.vTScontainer = new TabSheet();
        this.supplies = new ArrayList<EdsSupply>();
        this.driftViews = new ArrayList<SupplyDriftDriverEditView>();
        addComponent(vTScontainer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        for (SupplyDriftDriverEditView view : driftViews) {
            if (!view.isValid()) {
                getWindow().showNotification("", controller.getBundle().getString("eds-driftcontrol-save-error"), Notification.TYPE_ERROR_MESSAGE);

                return false;
            }
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        validatedDrifts.clear();
        unvalidatedDrifts.clear();
        for (SupplyDriftDriverEditView view : driftViews) {
            validatedDrifts.addAll(view.getAllValidatedDriftInfo());
            unvalidatedDrifts.addAll(view.getAllUnvalidatedDriftInfo());
            view.persistEdsSupplyDriftComment();
        }
        eds.setEdsDriftInfos(new HashSet(validatedDrifts));

        // All deviations are validated ..one informs the validator and the validation date.
        if (unvalidatedDrifts.isEmpty()) {
            eds.setEdsUserByEdsDriftValidatorId(controller.getAuthenticatedUser());
            eds.setEdsDriftValidationDate(new Date());
            eds.setEdsHasDrift(0);
        } else {
            eds.setEdsHasDrift(1);
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
        supplies.clear();
        driftViews.clear();

        supplies.addAll(EDSdao.getInstance().getAllEdsSuppliesByEdsId(eds.getEdsId()));

        vTScontainer.removeAllComponents();
        if (supplies.isEmpty()) {
            showEmptyView();
        } else {
            for (EdsSupply supply : supplies) {
                SupplyDriftDriverEditView editView = new SupplyDriftDriverEditView(eds, new SupplyDriftController(supply, eds, controller),
                        controller);
                vTScontainer.addTab(editView, supply.getSedsSupplyName());
                driftViews.add(editView);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        List<Object> list = new ArrayList<Object>();
        list.add(eds);
        list.addAll(supplies); // Supplies must be saved, in case they contain new comments.
        return list;
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
        eds = EDSdao.getInstance().getEdsByRef(eds.getEdsRef());

    }

    /**
     * This method shows view when no supplies defined in EDS sheet
     */
    private void showEmptyView() {
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setMargin(true);
        contentLayout.addComponent(new Label(controller.getBundle().getString("drift-no-alim")));
        vTScontainer.addTab(contentLayout, controller.getBundle().getString("eds-alimentation"));
    }
}
