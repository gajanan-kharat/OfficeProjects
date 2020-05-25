package com.inetpsa.eds.tools.filter;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.table.EdsTable;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * A panel display component for creating and managing filters. It shows a view of the filter manager as well as view of filter composer. It is linked
 * to a EdsTable to be updated at each change of filter manager.
 * 
 * @author Geometric Ltd.
 * @see EdsFilterManagerView, EdsFilterComposerView
 */
public class EdsFilterPanel extends HorizontalLayout {
    // PUBLIC
    /**
     * Parameterized Constructor
     * 
     * @param table Eds table details
     * @param controller Controller of EDs application
     */
    public EdsFilterPanel(EdsTable table, EdsApplicationController controller) {
        this(table, null, controller);

    }

    /**
     * Parameterized constructor
     * 
     * @param table Eds table details
     * @param additionalComponent additional component to be added
     * @param controller controller of Eds application
     */
    public EdsFilterPanel(EdsTable table, Component additionalComponent, EdsApplicationController controller) {
        this.table = table;
        this.additionalComponent = additionalComponent;
        this.controller = controller;
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
    private EdsTable table;
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

    /**
     * Initialize Eds filter panel
     */
    private void init() {
        this.setWidth("100%");
        this.setHeight(PANEL_HEIGHT);
        this.filterManagerView = new EdsFilterManagerView(this.table, controller);
        this.filterComposerView = new EdsFilterComposerView(this.filterManagerView, controller);
        this.filterComposerView.setSizeFull();

        HorizontalLayout contentLayout = new HorizontalLayout();
        contentLayout.setSizeFull();

        Panel leftPanel = new Panel(controller.getBundle().getString("filter-title"));
        leftPanel.setWidth(FILTER_PANEL_WIDTH);
        leftPanel.setHeight("100%");
        leftPanel.getContent().addComponent(filterManagerView);

        Panel rightPanel = new Panel(controller.getBundle().getString("filter-select"));
        rightPanel.setSizeFull();
        if (additionalComponent == null) {
            rightPanel.setContent(filterComposerView);
        } else {
            VerticalLayout splitLayout = new VerticalLayout();
            splitLayout.setSizeFull();
            splitLayout.addComponent(additionalComponent);
            splitLayout.addComponent(filterComposerView);
            splitLayout.setExpandRatio(filterComposerView, 1f);
            rightPanel.setContent(splitLayout);
        }

        contentLayout.addComponent(leftPanel);
        contentLayout.addComponent(rightPanel);
        contentLayout.setExpandRatio(rightPanel, 1f);

        this.addComponent(contentLayout);
    }
}
