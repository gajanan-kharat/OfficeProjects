package com.inetpsa.eds.tools.filter;

import java.util.ArrayList;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.inetpsa.eds.tools.table.EdsTable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.BaseTheme;

/**
 * Visual Component Manager filters. It displays selected filters by users on a column filters. The "deletable" filters have a delete button. It
 * contains a model manager filters.
 * 
 * @author Geometric Ltd.
 * @see EdsFilterManager
 */
public class EdsFilterManagerView extends Table {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param table Eds Table details
     * @param controller Controller of EDS application
     */
    public EdsFilterManagerView(EdsTable table, EdsApplicationController controller) {
        this.table = table;
        this.controller = controller;
        init();
    }

    /**
     * This method returns Eds table
     * 
     * @return Eds table
     */
    public EdsTable getTable() {
        return table;
    }

    /**
     * This method return filter manager
     * 
     * @return filter manager
     */
    public EdsFilterManager getFilterManager() {
        return filterManager;
    }

    /**
     * This method add filter in Eds filter manager
     * 
     * @param filter Eds filter to be added
     */
    public void addFilter(A_EdsFilter filter) {
        if (filterManager.addFilter(filter)) {
            this.addItem(filter);
        } else if (filterManager.getFilter(filter).isRemoveable) {
            filterManager.removeFilter(filter);
            filterManager.addFilter(filter);
            this.removeItem(filter);
            this.addItem(filter);
        } else {
            return;
        }

        updateEdsList();
    }

    /**
     * This method removes Eds filter from Eds Filter Manager
     * 
     * @param filter Filter to be removed
     */
    public void removeFilter(A_EdsFilter filter) {
        if (filterManager.removeFilter(filter)) {
            this.removeItem(filter);

            updateEdsList();
        }
    }

    /**
     * This method clears the filters from Eds Filter Manager
     */
    public void clearFilters() {
        ArrayList<A_EdsFilter> filtersToDelete = new ArrayList<A_EdsFilter>();

        for (A_EdsFilter filter : filterManager.getAllFilters()) {
            if (filter.isRemoveable()) {
                filtersToDelete.add(filter);
            }
        }

        for (A_EdsFilter filter : filtersToDelete) {
            filterManager.removeFilter(filter);
            this.removeItem(filter);
        }
    }

    /**
     * This method update Eds list
     */
    public void updateEdsList() {
        EDSdao dao = EDSdao.getInstance();

        table.setEdsList(dao.executeEdsCriteria(filterManager.buildQuery()));

    }

    // PROTECTED
    // PRIVATE
    /**
     * constant to represent PROPERTY_FILTER value
     */
    private static final String PROPERTY_FILTER = "filter";
    /**
     * Variable to hold value of Eds table
     */
    private EdsTable table;
    /**
     * Variable to hold value of Controller of Eds application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of Eds filter manager
     */
    private EdsFilterManager filterManager;

    /**
     * Initialize Eds filter Manager view
     */
    private void init() {
        this.setWidth("100%");

        this.filterManager = new EdsFilterManager(table.getController());

        this.addContainerProperty(PROPERTY_FILTER, A_EdsFilter.class, null);

        this.addGeneratedColumn(PROPERTY_FILTER, new ColumnGenerator() {
            public Object generateCell(Table source, Object itemId, Object columnId) {
                final A_EdsFilter filter = (A_EdsFilter) itemId;
                if (filter == null) {
                    return null;
                }

                HorizontalLayout cellLayout = new HorizontalLayout();
                cellLayout.setWidth("100%");

                Label vLBLdescription = new Label(filter.describe());
                cellLayout.addComponent(vLBLdescription);
                cellLayout.setComponentAlignment(vLBLdescription, Alignment.MIDDLE_LEFT);
                cellLayout.setExpandRatio(vLBLdescription, 1f);

                if (filter.isRemoveable()) {
                    Button vBTdelete = new Button(null, new Button.ClickListener() {
                        public void buttonClick(ClickEvent event) {
                            EdsFilterManagerView.this.removeFilter(filter);
                        }
                    });
                    vBTdelete.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/delete-filter.png"));
                    vBTdelete.setWidth("16px");
                    vBTdelete.setHeight("16px");
                    vBTdelete.setStyleName(BaseTheme.BUTTON_LINK);

                    cellLayout.addComponent(vBTdelete);
                    cellLayout.setComponentAlignment(vBTdelete, Alignment.MIDDLE_RIGHT);
                }

                return cellLayout;
            }
        });

        this.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
        this.setSelectable(false);
        this.setEditable(false);
    }
}
