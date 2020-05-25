/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inetpsa.eds.application.content.dashboard;

import com.inetpsa.eds.application.EdsApplicationController;

/**
 * Component display standard tables sheet EDS. It contains a table and a filter component.
 * 
 * @author Geometric Ltd.
 * @see EdsTable, EdsFilterPanel
 */
public class OverallEDSListView extends DefaultEDSListView {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public OverallEDSListView(EdsApplicationController controller) {
        super(controller);
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize Over all Eds list view
     */
    private void init() {
        if (!controller.getAuthenticatedUser().getEdsRole().isPerimeter() && !controller.getAuthenticatedUser().getEdsRole().isSupplier()) {
            this.table.setVisibleColumns(new Object[] { "edsRef", "edsMajorVersion", "edsName", "edsComponentType", "edsStage",
                    "edsUserByEdsAdminId", "edsUserByEdsOfficerId", "edsUserByEdsManagerId", "edsSupplier", "edsUserByEdsAffectationUserId",
                    "edsModifDate", "edsProject", "edsProjectEdses", "edsDescription" });
            this.table.setColumnHeaders(new String[] { controller.getBundle().getString("hist-ref"), controller.getBundle().getString("hist-vers"),
                    controller.getBundle().getString("Admin-user-name"), controller.getBundle().getString("menu-project-tab-model"),
                    controller.getBundle().getString("menu-project-tab-step"), controller.getBundle().getString("menu-EDS-admin"),
                    controller.getBundle().getString("menu-EDS-charge-dev"), controller.getBundle().getString("menu-project-tab-respo"),
                    controller.getBundle().getString("generic-data-represent-FNR"), controller.getBundle().getString("eds-affected-user"),
                    controller.getBundle().getString("eds-modif-date"), controller.getBundle().getString("filter-project-launcher"),
                    controller.getBundle().getString("generic-data-link-follow"), controller.getBundle().getString("eds-description") });
        } else {
            this.table.setVisibleColumns(new Object[] { "edsRef", "edsMajorVersion", "edsName", "edsComponentType", "edsStage",
                    "edsUserByEdsAdminId", "edsUserByEdsOfficerId", "edsUserByEdsManagerId", "edsSupplier", "edsUserByEdsAffectationUserId",
                    "edsModifDate", "edsDescription" });
            this.table.setColumnHeaders(new String[] { controller.getBundle().getString("hist-ref"), controller.getBundle().getString("hist-vers"),
                    controller.getBundle().getString("Admin-user-name"), controller.getBundle().getString("menu-project-tab-model"),
                    controller.getBundle().getString("menu-project-tab-step"), controller.getBundle().getString("menu-EDS-admin"),
                    controller.getBundle().getString("menu-EDS-charge-dev"), controller.getBundle().getString("menu-project-tab-respo"),
                    controller.getBundle().getString("generic-data-represent-FNR"), controller.getBundle().getString("eds-affected-user"),
                    controller.getBundle().getString("eds-modif-date"), controller.getBundle().getString("eds-description") });
        }
    }
}
