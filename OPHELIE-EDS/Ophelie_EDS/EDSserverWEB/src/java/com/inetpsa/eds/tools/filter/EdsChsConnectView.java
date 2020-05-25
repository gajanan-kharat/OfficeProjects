package com.inetpsa.eds.tools.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.vaadin.dialogs.ConfirmDialog;

import com.inetpsa.eds.application.ChsController;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.Chs;
import com.inetpsa.eds.tools.chs.observer.EdsChsChangeType;
import com.inetpsa.eds.tools.chs.observer.Observable;
import com.inetpsa.eds.tools.chs.observer.Observer;
import com.inetpsa.eds.tools.filter.chs.connect.ConnectEdsChsFilter;
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
 * @see EdsFilterManager
 */
public class EdsChsConnectView extends Table implements Observer<EdsChsChangeType> {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public EdsChsConnectView(EdsApplicationController controller, ChsController chsController) {
        this.controller = controller;
        this.chsController = chsController;
        chsController.addObserver(this);
        init();
    }

    /**
     * This method returns Eds table
     * 
     * @return Eds table
     */

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
    public void addFilter(ConnectEdsChsFilter filter) {
        Set<Chs> items = chsController.getConnectedChs();
        if (items == null) {
            items = new HashSet<Chs>();
        }
        if (filterManager.addFilter(filter)) {
            this.addItem(filter);
            items.add(filter.getChs());
        } else if (filterManager.getFilter(filter).isRemoveable) {
            filterManager.removeFilter(filter);
            filterManager.addFilter(filter);
            this.removeItem(filter);
            this.addItem(filter);
        } else {
            return;
        }
    }

    /**
     * This method removes Eds filter from Eds Filter Manager
     * 
     * @param filter Filter to be removed
     */
    public void removeFilter(final ConnectEdsChsFilter filter) {
        if (filterManager.getAllFilters().size() == 1) {
            StringBuilder build = new StringBuilder();
            build.append(controller.getBundle().getString("chs-filter-remove-top"));
            build.append("<br>");
            build.append(controller.getBundle().getString("chs-filter-remove-middle"));
            build.append("<br>");
            build.append(controller.getBundle().getString("chs-filter-remove-bot"));

            ConfirmDialog dialog = ConfirmDialog.show(controller.getUserWindow().getWindow(),
                    controller.getBundle().getString("chs-filter-remove-title"), build.toString(),
                    controller.getBundle().getString("chs-filter-remove-confirm"), controller.getBundle().getString("chs-filter-remove-cancel"),
                    new ConfirmDialog.Listener() {

                        public void onClose(ConfirmDialog dialog) {
                            if (dialog.isConfirmed()) {
                                removeItem(filter);
                                chsController.removeConnectedChs(filter.getChs());
                            }
                        }
                    });
            dialog.setContentMode(ConfirmDialog.CONTENT_HTML);
        } else if (filterManager.removeFilter(filter)) {
            this.removeItem(filter);
            chsController.removeConnectedChs(filter.getChs());
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

    // PROTECTED
    // PRIVATE
    private EdsApplicationController controller;

    private ChsController chsController;
    /**
     * constant to represent PROPERTY_FILTER value
     */
    private static final String PROPERTY_FILTER = "filter";

    /**
     * Variable to hold value of Eds filter manager
     */
    private EdsFilterManager filterManager;

    /**
     * Initialize Eds filter Manager view
     */
    private void init() {
        this.setWidth("100%");
        this.filterManager = new EdsFilterManager(null);

        this.addContainerProperty(PROPERTY_FILTER, ConnectEdsChsFilter.class, null);

        this.addGeneratedColumn(PROPERTY_FILTER, new ColumnGenerator() {
            public Object generateCell(Table source, Object itemId, Object columnId) {
                final ConnectEdsChsFilter filter = (ConnectEdsChsFilter) itemId;
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
                            removeFilter(filter);
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

    @Override
    public void update(Observable<EdsChsChangeType> o, EdsChsChangeType updated) {
        switch (updated) {
        case CONNECTIVITY:
            addFilter(new ConnectEdsChsFilter(chsController.getLastConnectedChs(), null));
            break;
        case INIT:
            Set<Chs> items = chsController.getConnectedChs();
            for (Chs item : items) {
                addFilter(new ConnectEdsChsFilter(item, null));
            }
            break;
        default:
            break;
        }

    }
}
