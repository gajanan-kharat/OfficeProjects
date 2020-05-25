package com.inetpsa.eds.tools.chs;

import com.inetpsa.eds.application.ChsController;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.EdsChsConnectView;
import com.inetpsa.eds.tools.filter.EdsFilterComposerView;
import com.inetpsa.eds.tools.filter.EdsFilterManagerView;
import com.inetpsa.eds.tools.table.EmbaseCHSTable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * A panel display component for creating and managing filters. It shows a view of the filter manager as well as view of filter composer. It is linked
 * to a EdsTable to be updated at each change of filter manager.
 * 
 * @author Joao Costa @ Alter Frame
 * @see EdsFilterManagerView, EdsFilterComposerView
 */
public class ConnectPartPanel extends VerticalLayout {
    // PUBLIC

    /**
     * Parameterized constructor
     * 
     * @param controller controller of Eds application
     */
    public ConnectPartPanel(EdsApplicationController controller, ChsController chsController) {
        this.controller = controller;
        this.chsController = chsController;
        init();
    }

    /**
     * This method returns filter manager view
     * 
     * @return Filter manager view
     */
    public EdsFilterManagerView getFilterManagerView() {
        return filterManagerView;
    }

    public EdsChsConnectView getSelectedWireView() {
        return selectedWire;
    }

    // PROTECTED
    // PRIVATE
    /**
     * Constant to hold value of FILTER_PANEL_WIDTH
     */
    private static final String FILTER_PANEL_WIDTH = "350px";
    /**
     * Constant to hold value of PANEL_HEIGHT
     */
    private static final String PANEL_HEIGHT = "250px";
    /**
     * Variable to hold value of Eds table
     */
    private EmbaseCHSTable table;

    private ChsController chsController;

    public EmbaseCHSTable getTable() {
        return table;
    }

    public void setTable(EmbaseCHSTable table) {
        this.table = table;
    }

    /**
     * Variable to hold value of component
     */
    private Component additionalComponent;
    /**
     * Variable to hold value of Eds filter manager view
     */
    private EdsFilterManagerView filterManagerView;
    /**
     * Variable to hold value of Filter composer view
     */
    private EdsFilterComposerView filterComposerView;
    /**
     * Variable to hold value of Controller of Eds application
     */
    private EdsApplicationController controller;

    private EdsChsConnectView selectedWire;

    /**
     * Initialize Eds filter panel
     */
    private void init() {

        this.setSpacing(true);
        this.setMargin(false, false, true, true);

        table = new EmbaseCHSTable(controller, chsController);

        table.setWidth("100%");
        table.setHeight("70%");
        selectedWire = new EdsChsConnectView(controller, chsController);
        Panel topPanel = new Panel(controller.getBundle().getString("con-selected-wire"));

        topPanel.getContent().addComponent(selectedWire);
        topPanel.setHeight(PANEL_HEIGHT);
        addComponent(topPanel);
        addComponent(table);
        setComponentAlignment(table, Alignment.TOP_CENTER);

    }
}
