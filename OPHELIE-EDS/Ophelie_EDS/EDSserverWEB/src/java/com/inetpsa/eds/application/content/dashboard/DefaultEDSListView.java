package com.inetpsa.eds.application.content.dashboard;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.table.EdsTable;
import com.inetpsa.eds.tools.filter.EdsFilterPanel;
import com.inetpsa.eds.dao.model.EdsEds;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;
import java.util.List;

/**
 * Component display standard tables sheet EDS. It contains a table and a filter component.
 * 
 * @author Geometric Ltd.
 * @see EdsTable, EdsFilterPanel
 */
public class DefaultEDSListView extends VerticalLayout {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public DefaultEDSListView(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /**
     * This method returns EdsTable
     * 
     * @return EdsTable
     */
    public EdsTable getEdsTable() {
        return table;
    }

    /**
     * This method set the set of EdsEds
     * 
     * @param edsList EdsEds list
     */
    public void setEdsList(List<EdsEds> edsList) {
        table.setEdsList(edsList);
    }

    /**
     * This method returns Filer panel
     * 
     * @return EdsFilerPanel
     */
    public EdsFilterPanel getFilterPanel() {
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
    protected EdsTable table;
    /**
     * Variable to hold value of EdsFilterPanel
     */
    protected EdsFilterPanel filterPanel;

    // PRIVATE

    /**
     * Initialize Default Eds list view
     */
    private void init() {
        this.setSpacing(true);

        table = new EdsTable(controller);

        table.setWidth("100%");
        table.setHeight("70%");

        this.filterPanel = new EdsFilterPanel(table, controller);
        addComponent(filterPanel);
        addComponent(table);
        setComponentAlignment(table, Alignment.TOP_CENTER);
    }
}
