package com.inetpsa.eds.tools.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import com.inetpsa.eds.application.ChsController;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.Chs;
import com.inetpsa.eds.tools.chs.observer.EdsChsChangeType;
import com.inetpsa.eds.tools.chs.observer.Observable;
import com.inetpsa.eds.tools.chs.observer.Observer;
import com.inetpsa.eds.tools.filter.chs.connectivity.ChsConnectivityFilter;
import com.inetpsa.eds.tools.filter.chs.nbcavities.ChsNbCavitiesFilter;
import com.inetpsa.eds.tools.filter.chs.nbembases.ChsNbEmbasesFilter;
import com.inetpsa.eds.tools.resource.ResourceManager;
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
 * @author Joao Costa @ Alter Frame
 * @see ChsFilterManager
 */
public class ChsFilterManagerView extends Table implements Observer<EdsChsChangeType> {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param table Eds Table details
     * @param controller Controller of EDS application
     */
    public ChsFilterManagerView(ChsController chsController, EdsApplicationController controller) {
        this.chsController = chsController;
        this.controller = controller;
        this.chsController.addObserver(this);
        init();
    }

    /**
     * This method return filter manager
     * 
     * @return filter manager
     */
    public ChsFilterManager getFilterManager() {
        return filterManager;
    }

    /**
     * This method add filter in Eds filter manager
     * 
     * @param filter Eds filter to be added
     */
    public void addFilter(A_EdsFilter filter) {
        if (filterManager.addFilter(filter) && filter.isVisible) {
            this.addItem(filter);
        } else if (filterManager.getFilter(filter).isRemoveable && filter.isVisible) {
            filterManager.removeFilter(filter);
            filterManager.addFilter(filter);
            this.removeItem(filter);
            this.addItem(filter);
        } else {
            return;
        }
        chsController.setFilterChs(filterManager.buildQuery());
    }

    /**
     * This method add multiple filters in Eds filter manager
     * 
     * @param filter Eds filter to be added
     */
    public void addFilter(A_EdsFilter[] filters) {
        for (A_EdsFilter filter : filters) {
            if (filterManager.addFilter(filter) && filter.isVisible) {
                this.addItem(filter);
            } else if (filterManager.getFilter(filter).isRemoveable && filter.isVisible) {
                filterManager.removeFilter(filter);
                filterManager.addFilter(filter);
                this.removeItem(filter);
                this.addItem(filter);
            }
        }
        chsController.setFilterChs(filterManager.buildQuery());
    }

    /**
     * This method removes Eds filter from Eds Filter Manager
     * 
     * @param filter Filter to be removed
     */
    public void removeFilter(A_EdsFilter filter) {
        if (filterManager.removeFilter(filter)) {
            if (filter.isVisible) {
                this.removeItem(filter);
            }
            chsController.setFilterChs(filterManager.buildQuery());
        }
    }

    /**
     * This method clears the filters from Eds Filter Manager
     */
    public void clearFilters() {
        ArrayList<A_EdsFilter> filtersToDelete = new ArrayList<A_EdsFilter>();
        Iterator<A_EdsFilter> it = filterManager.getAllFilters().iterator();
        A_EdsFilter filter;
        while (it.hasNext()) {
            filter = it.next();
            this.removeItem(filter);
            it.remove();
        }

        chsController.setFilterChs(filterManager.buildQuery());
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
    private ChsController chsController;
    /**
     * Variable to hold value of Controller of Eds application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of Eds filter manager
     */
    private ChsFilterManager filterManager;

    /**
     * Initialize Eds filter Manager view
     */
    private void init() {
        this.setWidth("100%");

        this.filterManager = new ChsFilterManager(controller);

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
                            ChsFilterManagerView.this.removeFilter(filter);
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

    public void updateConnectFilters() {
        Set<Chs> connect = chsController.getConnectedChs();
        A_EdsFilter[] filters;
        if (connect.size() > 0) {
            Chs base = connect.iterator().next();
            ChsNbCavitiesFilter nbCavities = new ChsNbCavitiesFilter(String.valueOf(base.getCavities().size()), EdsFilterManager.FILTER_EQUALS,
                    controller);
            nbCavities.setRemoveable(false);
            nbCavities.setVisible(false);
            ChsNbEmbasesFilter nbEmbases = new ChsNbEmbasesFilter(String.valueOf(base.getPinDetails().keySet().size()),
                    EdsFilterManager.FILTER_EQUALS, controller);
            nbEmbases.setRemoveable(false);
            nbEmbases.setVisible(false);
            ChsConnectivityFilter connected = new ChsConnectivityFilter(base, EdsFilterManager.FILTER_EQUALS, controller);
            connected.setRemoveable(false);
            connected.setVisible(false);
            filters = new A_EdsFilter[3];
            filters[0] = connected;
            filters[1] = nbCavities;
            filters[2] = nbEmbases;

            addFilter(filters);
        } else {
            clearFilters();
        }
    }

    @Override
    public void update(Observable<EdsChsChangeType> o, EdsChsChangeType updated) {
        switch (updated) {
        case CONNECTIVITY_FILTER:
            updateConnectFilters();
            break;
        case INIT:
            updateConnectFilters();
            break;
        default:
            break;
        }

    }
}
