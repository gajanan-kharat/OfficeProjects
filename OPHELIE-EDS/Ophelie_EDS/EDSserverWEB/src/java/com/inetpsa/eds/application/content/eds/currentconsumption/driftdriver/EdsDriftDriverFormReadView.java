package com.inetpsa.eds.application.content.eds.currentconsumption.driftdriver;

import java.util.ArrayList;
import java.util.List;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsSupply;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * This class provide Read view for drift control form
 * 
 * @author Geometric Ltd.
 */
public class EdsDriftDriverFormReadView extends A_EdsReadForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param eds Eds details
     * @param controller Controller of EDS application
     */
    public EdsDriftDriverFormReadView(EdsEds eds, EdsApplicationController controller) {
        super(controller);
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
        return "menu-app-pilote";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormTitle()
     */
    @Override
    public String getFormTitle() {
        return "Pilote-title";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        return DriverDriftsFormBuilder.ID;
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
        supplies.clear();

        supplies.addAll(EDSdao.getInstance().getAllEdsSuppliesByEdsId(getEds().getEdsId()));

        mainTab.removeAllComponents();
        if (supplies.isEmpty()) {
            showEmptyView();
        } else {
            for (EdsSupply supply : supplies) {
                SupplyDriftDriverReadView readView = new SupplyDriftDriverReadView(new SupplyDriftController(supply, eds, controller), controller);
                mainTab.addTab(readView, supply.getSedsSupplyName());
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshItems()
     */
    @Override
    public void refreshItems() {

        eds = EDSdao.getInstance().getEdsByRef(eds.getEdsRef());

    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;
    /**
     * Variable to hold list of EdsSupply
     */
    private List<EdsSupply> supplies;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of Tabsheet
     */
    private TabSheet mainTab;

    /**
     * Initialize Read view for drift control form
     */
    private void init() {
        this.mainTab = new TabSheet();
        this.supplies = new ArrayList<EdsSupply>();
        addComponent(mainTab);
    }

    /**
     * This method shows view when no supplies defined in EDS sheet
     */
    private void showEmptyView() {
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setMargin(true);
        contentLayout.addComponent(new Label(controller.getBundle().getString("drift-no-alim")));
        mainTab.addTab(contentLayout, controller.getBundle().getString("eds-alimentation"));
    }
}
