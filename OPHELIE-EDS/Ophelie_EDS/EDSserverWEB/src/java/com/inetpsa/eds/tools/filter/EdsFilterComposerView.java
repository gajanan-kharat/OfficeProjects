package com.inetpsa.eds.tools.filter;

import java.util.TreeSet;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.administrator.FF_EdsAdministrator;
import com.inetpsa.eds.tools.filter.affectation.FF_EdsAffected;
import com.inetpsa.eds.tools.filter.bttbt.FF_EdsBtTbt;
import com.inetpsa.eds.tools.filter.componenttype.FF_EdsComponentType;
import com.inetpsa.eds.tools.filter.connectivity.FF_ChsConnectivity;
import com.inetpsa.eds.tools.filter.description.FF_EdsDescription;
import com.inetpsa.eds.tools.filter.followerproject.FF_EdsFollowerProject;
import com.inetpsa.eds.tools.filter.launcherproject.FF_EdsLauncherProject;
import com.inetpsa.eds.tools.filter.manager.FF_EdsManager;
import com.inetpsa.eds.tools.filter.name.FF_EdsName;
import com.inetpsa.eds.tools.filter.number96k.FF_Eds96K;
import com.inetpsa.eds.tools.filter.officer.FF_EdsOfficer;
import com.inetpsa.eds.tools.filter.organe.FF_EdsOrgane;
import com.inetpsa.eds.tools.filter.perimeter.FF_EdsPerimeter;
import com.inetpsa.eds.tools.filter.project.FF_EdsProject;
import com.inetpsa.eds.tools.filter.reconducted.FF_EdsReconducted;
import com.inetpsa.eds.tools.filter.ref.FF_EdsRef;
import com.inetpsa.eds.tools.filter.stage.FF_EdsStage;
import com.inetpsa.eds.tools.filter.subtype1.FF_EdsSubtype1;
import com.inetpsa.eds.tools.filter.subtype2.FF_EdsSubtype2;
import com.inetpsa.eds.tools.filter.supplier.FF_EdsSupplier;
import com.inetpsa.eds.tools.filter.validationlvl.FF_EdsValidationLevel;
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
 * @author Geometric Ltd.
 * @see EdsFilterManagerView
 */
public class EdsFilterComposerView extends VerticalLayout {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param filterManager EdsFilterManagerView object
     * @param controller Controller of EDS application
     */
    public EdsFilterComposerView(EdsFilterManagerView filterManager, EdsApplicationController controller) {
        this.filterManager = filterManager;
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EdsFilterMangerView object
     */
    private EdsFilterManagerView filterManager;
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
        factorySet.add(new FF_Eds96K(controller));
        factorySet.add(new FF_EdsAdministrator(controller));
        factorySet.add(new FF_EdsBtTbt(controller));
        factorySet.add(new FF_EdsComponentType(filterManager.getTable().getController()));
        factorySet.add(new FF_EdsFollowerProject(controller));
        factorySet.add(new FF_EdsLauncherProject(controller));
        factorySet.add(new FF_EdsManager(controller));
        factorySet.add(new FF_EdsName(controller));
        factorySet.add(new FF_EdsOfficer(controller));
        if (!filterManager.getTable().getController().getAuthenticatedUser().getEdsRole().isPerimeter()) {
            factorySet.add(new FF_EdsPerimeter(controller));
        }
        factorySet.add(new FF_EdsReconducted(controller));
        factorySet.add(new FF_EdsRef(controller));
        factorySet.add(new FF_EdsStage(controller));
        factorySet.add(new FF_EdsSubtype1(controller));
        factorySet.add(new FF_EdsSubtype2(controller));
        if (!filterManager.getTable().getController().getAuthenticatedUser().getEdsRole().isSupplier()) {
            factorySet.add(new FF_EdsSupplier(controller));
        }
        factorySet.add(new FF_EdsValidationLevel(controller));
        factorySet.add(new FF_EdsProject(controller));
        factorySet.add(new FF_EdsOrgane(controller));
        factorySet.add(new FF_EdsAffected(controller));
        // Ex_conn_98 - Filter by description
        factorySet.add(new FF_EdsDescription(controller));
        // End of Ex_conn_98
        factorySet.add(new FF_ChsConnectivity(controller));
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
