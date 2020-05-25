package com.inetpsa.eds.application.content.connectivity;

import com.inetpsa.eds.application.ChsController;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.ChsFilterPanel;
import com.inetpsa.eds.tools.table.CHSTable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

/**
 * Component display standard tables sheet CHS. It contains a table and a filter component.
 * 
 * @author Joao Costa @ Alter Frame
 * @see EdsTable, EdsFilterPanel
 */
public class DefaultCHSListView extends VerticalLayout {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    private ChsController chsController;

    public DefaultCHSListView(EdsApplicationController controller, ChsController chsController) {
        this.chsController = chsController;
        this.controller = controller;
        init();
    }

    /**
     * This method returns EdsTable
     * 
     * @return EdsTable
     */
    public CHSTable getEdsTable() {
        return table;
    }

    /**
     * This method returns Filer panel
     * 
     * @return EdsFilerPanel
     */
    public ChsFilterPanel getFilterPanel() {
        return filterPanel;
    }

    // PROTECTED
    /**
     * Variable to hold value of controller of EDS application
     */
    protected EdsApplicationController controller;
    /**
     * Variable to hold value of EdsTable
     */
    protected CHSTable table;
    /**
     * Variable to hold value of EdsFilterPanel
     */
    protected ChsFilterPanel filterPanel;

    // PRIVATE

    /**
     * Initialize Default Eds list view
     */
    private void init() {
        this.setSpacing(true);

        table = new CHSTable(controller, chsController);

        table.setWidth("100%");
        table.setHeight("70%");

        this.filterPanel = new ChsFilterPanel(chsController, controller);
        addComponent(filterPanel);
        addComponent(table);
        setComponentAlignment(table, Alignment.TOP_CENTER);
    }
}
