package com.inetpsa.eds.tools.filter;

import java.util.TreeSet;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.chs.description.FF_ChsDescription;
import com.inetpsa.eds.tools.filter.chs.nbcavities.FF_ChsNbCavities;
import com.inetpsa.eds.tools.filter.chs.nbembases.FF_ChsNbEmbases;
import com.inetpsa.eds.tools.filter.chs.number96.FF_ChsNumber96;
import com.inetpsa.eds.tools.filter.chs.partnumber.FF_ChsPartNumber;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

/**
 * Composition component filters. It contains a ComboBox with different factories filter and display filters when publishers are Selected. It must be
 * linked to a view of a filter manager.
 * 
 * @author Joao Costa @ Alter Frame
 * @see ChsFilterManagerView
 */
public class ChsFilterComposerView extends VerticalLayout {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param filterManager EdsFilterManagerView object
     * @param controller Controller of EDS application
     */
    public ChsFilterComposerView(ChsFilterManagerView filterManager, EdsApplicationController controller) {
        this.filterManager = filterManager;
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EdsFilterMangerView object
     */
    private ChsFilterManagerView filterManager;
    /**
     * Variable to hold value of combo box for selected filter
     */
    private ComboBox vCMBfilterSelector;
    /**
     * Variable to hold value of HorizontalLayout details
     */
    private HorizontalLayout filterDisplayLayout;
    /**
     * Variable to hold value of current Filter factory
     */
    private A_FilterFactory currentFactory;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize Eds filter composer view
     */
    private void init() {
        this.setMargin(true);
        this.setSpacing(true);
        this.setSizeFull();

        HorizontalLayout contentLayout = new HorizontalLayout();
        contentLayout.setSpacing(true);
        contentLayout.setSizeFull();

        TreeSet<A_FilterFactory> factorySet = new TreeSet<A_FilterFactory>();
        factorySet.add(new FF_ChsPartNumber(controller));
        factorySet.add(new FF_ChsDescription(controller));
        factorySet.add(new FF_ChsNumber96(controller));
        factorySet.add(new FF_ChsNbCavities(controller));
        factorySet.add(new FF_ChsNbEmbases(controller));

        this.vCMBfilterSelector = new ComboBox(controller.getBundle().getString("eds-filter-name") + " :", factorySet);
        this.vCMBfilterSelector.setTextInputAllowed(false);
        this.vCMBfilterSelector.addListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                showCurrentFactory();
            }
        });

        filterDisplayLayout = new HorizontalLayout();
        filterDisplayLayout.setSizeFull();

        contentLayout.addComponent(this.vCMBfilterSelector);
        contentLayout.addComponent(filterDisplayLayout);
        contentLayout.setExpandRatio(filterDisplayLayout, 1f);

        this.addComponent(contentLayout);

        Button vBTadd = new Button(controller.getBundle().getString("filter-button-add-filter"), new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                createFilter();
            }
        });
        vBTadd.setImmediate(true);

        this.addComponent(vBTadd);
        this.setComponentAlignment(vBTadd, Alignment.BOTTOM_RIGHT);
        this.setExpandRatio(contentLayout, 1f);

        this.vCMBfilterSelector.setImmediate(true);
    }

    /**
     * This method display current factory
     */
    private void showCurrentFactory() {
        currentFactory = (A_FilterFactory) vCMBfilterSelector.getValue();

        filterDisplayLayout.removeAllComponents();
        if (currentFactory != null) {
            filterDisplayLayout.addComponent(currentFactory.createEditor());
        }
    }

    /**
     * This method create filter
     */
    private void createFilter() {
        try {
            A_EdsFilter filter = currentFactory.createFilter();
            filterManager.addFilter(filter);

            vCMBfilterSelector.select(null);
        } catch (E_FilterNotReady e) {
            getWindow()
                    .showNotification(controller.getBundle().getString("eds-pop-warning-title"), e.getMessage(), Notification.TYPE_WARNING_MESSAGE);
        } catch (NullPointerException e) {
            getWindow().showNotification(controller.getBundle().getString("eds-pop-warning-title"),
                    controller.getBundle().getString("eds-filter-error-message"), Notification.TYPE_WARNING_MESSAGE);
        }
    }
}
