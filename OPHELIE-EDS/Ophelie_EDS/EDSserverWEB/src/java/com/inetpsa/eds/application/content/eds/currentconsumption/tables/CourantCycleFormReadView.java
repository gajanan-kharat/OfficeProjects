package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import org.apache.commons.lang.StringUtils;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.composants.MyTable;
import com.inetpsa.eds.dao.model.EdsCourantCycle;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

/**
 * This class provide Read form view for cycle current consumption
 * 
 * @author Geometric Ltd.
 */
public class CourantCycleFormReadView extends GridLayout {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param ecc Object of EdsCourantCycles
     * @param controller Controller of EDS application
     */
    public CourantCycleFormReadView(EdsCourantCycle ecc, EdsApplicationController controller) {
        this.edsCourantCycle = ecc;
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EdsCourantCycle
     */
    private EdsCourantCycle edsCourantCycle;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of MyTable
     */
    private MyTable vTETable;
    /**
     * Variable to hold value of Label for title
     */
    private Label vLtitle;
    /**
     * Variable to hold value of Label for Cycle
     */
    private Label vLTcycle;
    /**
     * Variable to hold value of Label for Notes
     */
    private Label vLComent;

    /**
     * Initialize Read form view for Cycle current consumption
     */
    private void init() {

        vTETable = new MyTable();
        vLtitle = new Label();
        vLTcycle = new Label();
        vLComent = new Label();

        HorizontalLayout hl = new HorizontalLayout();
        hl.setSpacing(true);

        hl.addComponent(vLtitle);

        GridLayout layout = new GridLayout(1, 2);
        layout.setSpacing(true);
        layout.setWidth("100%");
        layout.addComponent(hl, 0, 0);

        layout.addComponent(getTable(), 0, 1);

        addComponent(layout);
        discardChanges();

        setSpacing(true);
        setWidth("100%");

        // separator
        addComponent(new Label("<br />", Label.CONTENT_XHTML));

    }

    /**
     * This method roll backs data to original data
     */
    public void discardChanges() {
        vLTcycle.setValue(edsCourantCycle.getCcedsTcycle());
        vLComent.setValue(edsCourantCycle.getCcedsComent());
        vLtitle.setValue(edsCourantCycle.getCcedsName() + " (" + StringUtils.join(edsCourantCycle.getEdsProjects(), ",") + ")");
    }

    /**
     * This method returns Component for Cycle current consumption
     * 
     * @return Component
     */
    private Component getTable() {

        vTETable.setPageLength(1);
        vTETable.setWidth("100%");
        vTETable.setSelectable(true);

        vTETable.addContainerProperty(controller.getBundle().getString("eds-courant-label"), Label.class, null);

        vTETable.addContainerProperty(controller.getBundle().getString("current-conso-tab-data-TCycle"), Label.class, null);

        vTETable.addContainerProperty(controller.getBundle().getString("eds-comnent"), Label.class, null);

        vTETable.addItem(new Object[] { controller.getBundle().getString("current-conso-tab-data-ICycle"), vLTcycle, vLComent }, new Integer(0));

        return vTETable;
    }
}
