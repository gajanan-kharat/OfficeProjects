package com.inetpsa.eds.application.content.admin.access.perimeter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.popup.EditStringWindow;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsPerimeter;
import com.inetpsa.eds.tools.doublelist.DoubleList;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.event.MouseEvents.DoubleClickEvent;
import com.vaadin.event.MouseEvents.DoubleClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This class provide partners access Management
 * 
 * @author Geometric Ltd.
 */
public class EdsPerimeterManagementForm extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized Constructor
     * 
     * @param controller Controller of EDS application
     */
    public EdsPerimeterManagementForm(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        // Check that all perimeters have a different name
        HashSet<String> differentNameCount = new HashSet<String>();
        Collection perimeters = new HashSet(vDLSTperimeters.getAllOptions());
        perimeters.addAll(vDLSTperimeters.getAllSelections());
        int i = 0;
        for (Object o : perimeters) {
            differentNameCount.add(((EdsPerimeter) o).getPeName());
            i++;
        }
        boolean isValid = (i == differentNameCount.size());
        if (!isValid) {
            getWindow().showNotification(controller.getBundle().getString("pop-error-title"),
                    controller.getBundle().getString("Admin-partner-error"), Notification.TYPE_ERROR_MESSAGE);
        }
        return isValid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        for (Object inactivePerimeter : vDLSTperimeters.getAllOptions()) {
            ((EdsPerimeter) inactivePerimeter).setPeActive(EdsPerimeter.INACTIVE);
        }
        for (Object activePerimeter : vDLSTperimeters.getAllSelections()) {
            ((EdsPerimeter) activePerimeter).setPeActive(EdsPerimeter.ACTIVE);
        }
        return true;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        perimetersDeleted.clear();
        vDLSTperimeters.removeAllItems();

        for (EdsPerimeter perimeter : EDSdao.getInstance().getAllPerimeters(true)) {
            if (perimeter.getPeActive() == EdsPerimeter.ACTIVE) {
                vDLSTperimeters.addSelection(perimeter);
            } else {
                vDLSTperimeters.addOption(perimeter);
            }
            vDLSTperimeters.setItemCaption(perimeter, perimeter.getPeName());
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        Collection itemsToSave = new HashSet(vDLSTperimeters.getAllOptions());
        itemsToSave.addAll(vDLSTperimeters.getAllSelections());
        itemsToSave.addAll(perimetersDeleted);
        return itemsToSave;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete()
     */
    @Override
    public Collection getAllItemsToDelete() {
        return Collections.EMPTY_SET;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        // DO NOTHING
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of DoubleList for partners
     */
    private DoubleList vDLSTperimeters;
    /**
     * Variable to hold set of EdsPerimeter
     */
    private HashSet<EdsPerimeter> perimetersDeleted;

    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize EdsPerimeterManagementForm
     */
    private void init() {
        this.perimetersDeleted = new HashSet<EdsPerimeter>();
        TabSheet vTSmain = new TabSheet();
        vTSmain.setSizeFull();

        vTSmain.addTab(getPerimeterLayout(), controller.getBundle().getString("Admin-acc-partn-ss-title"));

        addComponent(vTSmain);
    }

    /**
     * This method returns Partners access Management layout
     * 
     * @return Layout for partners access Management
     */
    private Layout getPerimeterLayout() {
        HorizontalLayout perimeterLayout = new HorizontalLayout();
        perimeterLayout.setSpacing(true);
        perimeterLayout.setMargin(true);

        this.vDLSTperimeters = new DoubleList();
        vDLSTperimeters.setLeftColumnCaption(controller.getBundle().getString("Admin-acc-partn-inact"));
        vDLSTperimeters.setRightColumnCaption(controller.getBundle().getString("Admin-acc-partn-act"));
        vDLSTperimeters.setRows(20);
        vDLSTperimeters.setMultiSelect(true);
        vDLSTperimeters.setNullSelectionAllowed(true);
        vDLSTperimeters.setImmediate(true);
        vDLSTperimeters.setWidth("500px");
        perimeterLayout.addComponent(vDLSTperimeters);

        // Adding listener double click for option list -> Rename window opens
        vDLSTperimeters.addOptionDoubleClickListener(new DoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                if (!((Set<EdsPerimeter>) vDLSTperimeters.getOptionValue()).isEmpty()) {
                    final EdsPerimeter selectedPerimeter = ((Set<EdsPerimeter>) vDLSTperimeters.getOptionValue()).iterator().next();
                    EditStringWindow window = new EditStringWindow(selectedPerimeter.getPeName(), controller.getBundle().getString(
                            "Admin-acc-renom-partn"), controller.getBundle().getString("Admin-new-partn-name"), controller,
                            new EditStringWindow.ValidateListener() {
                                /*
                                 * (non-Javadoc)
                                 * 
                                 * @see com.inetpsa.eds.application.popup. EditStringWindow .ValidateListener#onValidate (java.lang.String)
                                 */
                                @Override
                                public void onValidate(String newValue) {
                                    selectedPerimeter.setPeName(newValue);
                                    vDLSTperimeters.setItemCaption(selectedPerimeter, newValue);
                                }
                            });
                    window.setModal(true);
                    getApplication().getMainWindow().addWindow(window);
                    window.center();
                }
            }
        });

        // Adding listener double click for selected list -> Rename window opens
        vDLSTperimeters.addSelectionDoubleClickListener(new DoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                if (!((Set<EdsPerimeter>) vDLSTperimeters.getSelectedValue()).isEmpty()) {

                    final EdsPerimeter selectedPerimeter = ((Set<EdsPerimeter>) vDLSTperimeters.getSelectedValue()).iterator().next();
                    EditStringWindow window = new EditStringWindow(selectedPerimeter.getPeName(), controller.getBundle().getString(
                            "Admin-acc-renom-partn"), controller.getBundle().getString("Admin-new-partn-name"), controller,
                            new EditStringWindow.ValidateListener() {
                                /*
                                 * (non-Javadoc)
                                 * 
                                 * @see com.inetpsa.eds.application.popup. EditStringWindow .ValidateListener#onValidate (java.lang.String)
                                 */
                                @Override
                                public void onValidate(String newValue) {
                                    selectedPerimeter.setPeName(newValue);
                                    vDLSTperimeters.setItemCaption(selectedPerimeter, newValue);
                                }
                            });
                    window.setModal(true);
                    getApplication().getMainWindow().addWindow(window);
                    window.center();
                }
            }
        });

        // Button to add a partner
        Button addButton = new Button("", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                EdsPerimeter newPerimeter = new EdsPerimeter(UUID.randomUUID().toString(), controller.getBundle().getString("Admin-new-partner"),
                        EdsPerimeter.INACTIVE);
                vDLSTperimeters.addOption(newPerimeter);
                vDLSTperimeters.setItemCaption(newPerimeter, newPerimeter.getPeName());
            }
        });
        addButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png"));
        addButton.setStyleName(BaseTheme.BUTTON_LINK);
        addButton.setDescription(controller.getBundle().getString("Admin-acc-partn-add-tt"));
        vDLSTperimeters.addButton(addButton);

        perimeterLayout.setExpandRatio(vDLSTperimeters, 1);

        return perimeterLayout;
    }
}
