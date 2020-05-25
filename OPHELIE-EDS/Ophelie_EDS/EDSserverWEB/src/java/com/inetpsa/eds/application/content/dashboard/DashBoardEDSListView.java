package com.inetpsa.eds.application.content.dashboard;

import com.inetpsa.eds.application.EdsApplicationController;

/**
 * Component display standard tables sheet EDS. It contains a table and a filter component.
 * 
 * @author Geometric Ltd.
 * @see EdsTable, EdsFilterPanel
 */
public class DashBoardEDSListView extends DefaultEDSListView {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public DashBoardEDSListView(EdsApplicationController controller) {
        super(controller);
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize Standard table sheet EDS
     */
    private void init() {
        this.table.setVisibleColumns(new Object[] { "edsRef", "edsMajorVersion", "edsName", "edsComponentType", "edsStage", "edsSupplier",
                "edsProject", "edsProjectEdses", "edsUserByEdsOfficerId", "edsUserByEdsAffectationUserId", "edsModifDate", "edsDescription" });
        this.table.setColumnHeaders(new String[] { controller.getBundle().getString("hist-ref"), controller.getBundle().getString("hist-vers"),
                controller.getBundle().getString("Admin-user-name"), controller.getBundle().getString("menu-project-tab-model"),
                controller.getBundle().getString("menu-project-tab-step"), controller.getBundle().getString("generic-data-represent-FNR"),
                controller.getBundle().getString("filter-project-launcher"), controller.getBundle().getString("generic-data-link-follow"),
                controller.getBundle().getString("menu-EDS-charge-dev"), controller.getBundle().getString("eds-affected-user"),
                controller.getBundle().getString("eds-modif-date"), controller.getBundle().getString("eds-description") });
    }
}
