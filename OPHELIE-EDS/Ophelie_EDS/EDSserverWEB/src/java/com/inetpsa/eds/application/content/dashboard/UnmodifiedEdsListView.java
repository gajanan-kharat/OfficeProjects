package com.inetpsa.eds.application.content.dashboard;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.EdsFilterPanel;
import com.inetpsa.eds.tools.filter.fixedunmodifieddate.EdsFixedUnmodifDateFilter;
import com.inetpsa.eds.tools.filter.unmodifdate.EdsUnmodifDateFilter;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.inetpsa.eds.tools.table.EdsTable;
import com.inetpsa.eds.tools.timepicker.WeekTimePicker;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This view shows a table, lists EDS sheets and a date picker to filter the list of records displayed as a function of their modification date
 * outside a time interval
 * 
 * @author Geometric Ltd.
 * @see WeekTimePicker, UnmodifiedEdsList
 */
public class UnmodifiedEdsListView extends VerticalLayout {
    // PUBLIC
    /**
     * Parameterized Constructor
     * 
     * @param controller Controller of EDs application
     */
    public UnmodifiedEdsListView(EdsApplicationController controller) {
        this.controller = controller;
        init();
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
    // PRIVATE
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsFilterPanel
     */
    private EdsFilterPanel filterPanel;
    /**
     * Variable to hold value of EdsTable
     */
    private EdsTable table;
    /**
     * Variable to hold value of WeekTimePicker
     */
    private WeekTimePicker timePicker;
    /**
     * Variable to hold value of EdsUnmodifDateFilter
     */
    private EdsUnmodifDateFilter unmodifFilter;

    /**
     * Initialize Unmodified Eds list view
     */
    private void init() {
        this.setSpacing(true);

        HorizontalLayout timePickLayout = new HorizontalLayout();
        timePickLayout.setMargin(true);
        timePickLayout.setSpacing(true);
        timePickLayout.setWidth("100%");
        timePicker = new WeekTimePicker();

        timePickLayout.addComponent(timePicker);
        timePickLayout.setComponentAlignment(timePicker, Alignment.MIDDLE_LEFT);
        Button vBTrefresh = new Button(null, new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                unmodifFilter.setInterval(timePicker.getSelectedStartDate(), timePicker.getSelectedEndDate());
                filterPanel.getFilterManagerView().refreshRowCache();
                filterPanel.getFilterManagerView().updateEdsList();
            }
        });
        vBTrefresh.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/refresh.png"));
        vBTrefresh.setDescription("Rafraîchir");
        vBTrefresh.setStyleName(BaseTheme.BUTTON_LINK);

        timePickLayout.addComponent(vBTrefresh);
        timePickLayout.setExpandRatio(vBTrefresh, 1f);
        timePickLayout.setComponentAlignment(vBTrefresh, Alignment.MIDDLE_LEFT);

        table = new EdsTable(controller);

        table.setVisibleColumns(new Object[] { "edsRef", "edsMajorVersion", "edsName", "edsComponentType", "edsStage", "edsSupplier", "edsProject",
                "edsProjectEdses", "edsUserByEdsOfficerId", "edsUserByEdsAffectationUserId", "edsModifDate", "edsDescription" });
        table.setColumnHeaders(new String[] { controller.getBundle().getString("hist-ref"), controller.getBundle().getString("hist-vers"),
                controller.getBundle().getString("Admin-user-name"), controller.getBundle().getString("menu-project-tab-model"),
                controller.getBundle().getString("menu-project-tab-step"), controller.getBundle().getString("generic-data-represent-FNR"),
                controller.getBundle().getString("filter-project-launcher"), controller.getBundle().getString("generic-data-link-follow"),
                controller.getBundle().getString("menu-EDS-charge-dev"), controller.getBundle().getString("eds-affected-user"),
                controller.getBundle().getString("eds-modif-date"), controller.getBundle().getString("eds-description") });

        table.setSizeFull();

        this.filterPanel = new EdsFilterPanel(table, timePickLayout, controller);

        addComponent(this.filterPanel);
        addComponent(table);
        setComponentAlignment(table, Alignment.TOP_CENTER);

        unmodifFilter = new EdsFixedUnmodifDateFilter(timePicker.getSelectedStartDate(), timePicker.getSelectedEndDate(), controller);

        this.filterPanel.getFilterManagerView().addFilter(unmodifFilter);
    }
}
