package com.inetpsa.eds.application.content.admin.access.supplier;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.popup.EditStringWindow;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsSupplier;
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
 * This class provide Suppliers access Management
 * 
 * @author Geometric Ltd.
 */
public class EdsSupplierManagementForm extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public EdsSupplierManagementForm(EdsApplicationController controller) {
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
        // Check that all suppliers have a different name
        HashSet<String> differentNameCount = new HashSet<String>();
        Collection suppliers = new HashSet(vDLSTsuppliers.getAllOptions());
        suppliers.addAll(vDLSTsuppliers.getAllSelections());
        int i = 0;
        for (Object o : suppliers) {
            differentNameCount.add(((EdsSupplier) o).getSName());
            i++;
        }
        boolean isValid = (i == differentNameCount.size());
        if (!isValid) {
            getWindow().showNotification(controller.getBundle().getString("pop-error-title"),
                    controller.getBundle().getString("Admin-acc-fnr-ss-pop-err"), Notification.TYPE_ERROR_MESSAGE);
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
        for (Object inactivesupplier : vDLSTsuppliers.getAllOptions()) {
            ((EdsSupplier) inactivesupplier).setSActive(EdsSupplier.INACTIVE);
        }
        for (Object activesupplier : vDLSTsuppliers.getAllSelections()) {
            ((EdsSupplier) activesupplier).setSActive(EdsSupplier.ACTIVE);
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
        suppliersDeleted.clear();
        vDLSTsuppliers.removeAllItems();

        for (EdsSupplier supplier : EDSdao.getInstance().getAllSuppliers(true)) {
            if (supplier.getSActive() == EdsSupplier.ACTIVE) {
                vDLSTsuppliers.addSelection(supplier);
            } else {
                vDLSTsuppliers.addOption(supplier);
            }
            vDLSTsuppliers.setItemCaption(supplier, supplier.getSName());
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        Collection itemsToSave = new HashSet(vDLSTsuppliers.getAllOptions());
        itemsToSave.addAll(vDLSTsuppliers.getAllSelections());
        itemsToSave.addAll(suppliersDeleted);
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
     * Variable to hold value of DoubleList for suppliers
     */
    private DoubleList vDLSTsuppliers;
    /**
     * Variable to hold set of EdsSupplier
     */
    private HashSet<EdsSupplier> suppliersDeleted;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize EdsSupplierManagementForm
     */
    private void init() {
        this.suppliersDeleted = new HashSet<EdsSupplier>();
        TabSheet vTSmain = new TabSheet();
        vTSmain.setSizeFull();

        vTSmain.addTab(getSupplierLayout(), controller.getBundle().getString("Admin-acc-fnr"));

        addComponent(vTSmain);
    }

    /**
     * This method returns suppliers access Management layout
     * 
     * @return Layout for suppliers access Management
     */
    private Layout getSupplierLayout() {
        HorizontalLayout supplierLayout = new HorizontalLayout();
        supplierLayout.setSpacing(true);
        supplierLayout.setMargin(true);

        this.vDLSTsuppliers = new DoubleList();
        vDLSTsuppliers.setLeftColumnCaption(controller.getBundle().getString("Admin-acc-fnr-inact"));
        vDLSTsuppliers.setRightColumnCaption(controller.getBundle().getString("Admin-acc-fnr-act"));
        vDLSTsuppliers.setRows(20);
        vDLSTsuppliers.setMultiSelect(true);
        vDLSTsuppliers.setNullSelectionAllowed(true);
        vDLSTsuppliers.setImmediate(true);
        vDLSTsuppliers.setWidth("500px");
        supplierLayout.addComponent(vDLSTsuppliers);

        // Adding listener double click for option list -> Rename window opens
        vDLSTsuppliers.addOptionDoubleClickListener(new DoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                if (!((Set<EdsSupplier>) vDLSTsuppliers.getOptionValue()).isEmpty()) {
                    final EdsSupplier selectedSupplier = ((Set<EdsSupplier>) vDLSTsuppliers.getOptionValue()).iterator().next();
                    EditStringWindow window = new EditStringWindow(selectedSupplier.getSName(), controller.getBundle().getString(
                            "Admin-acc-renom-fnr"), controller.getBundle().getString("Admin-acc-fnr-new-name"), controller,
                            new EditStringWindow.ValidateListener() {
                                /*
                                 * (non-Javadoc)
                                 * 
                                 * @see com.inetpsa.eds.application.popup. EditStringWindow .ValidateListener#onValidate (java.lang.String)
                                 */
                                @Override
                                public void onValidate(String newValue) {
                                    selectedSupplier.setSName(newValue);
                                    vDLSTsuppliers.setItemCaption(selectedSupplier, newValue);
                                }
                            });
                    window.setModal(true);
                    getApplication().getMainWindow().addWindow(window);
                    window.center();
                }
            }
        });

        // Adding listener double click for selected list -> Rename window opens
        vDLSTsuppliers.addSelectionDoubleClickListener(new DoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                if (!((Set<EdsSupplier>) vDLSTsuppliers.getSelectedValue()).isEmpty()) {

                    final EdsSupplier selectedSupplier = ((Set<EdsSupplier>) vDLSTsuppliers.getSelectedValue()).iterator().next();
                    EditStringWindow window = new EditStringWindow(selectedSupplier.getSName(), controller.getBundle().getString(
                            "Admin-acc-renom-fnr"), controller.getBundle().getString("Admin-acc-fnr-new-name"), controller,
                            new EditStringWindow.ValidateListener() {
                                /*
                                 * (non-Javadoc)
                                 * 
                                 * @see com.inetpsa.eds.application.popup. EditStringWindow .ValidateListener#onValidate (java.lang.String)
                                 */
                                @Override
                                public void onValidate(String newValue) {
                                    selectedSupplier.setSName(newValue);
                                    vDLSTsuppliers.setItemCaption(selectedSupplier, newValue);
                                }
                            });
                    window.setModal(true);
                    getApplication().getMainWindow().addWindow(window);
                    window.center();
                }
            }
        });

        // Button to add a supplier
        Button addButton = new Button("", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                EdsSupplier newSupplier = new EdsSupplier(UUID.randomUUID().toString(), controller.getBundle().getString("Admin-acc-fnr-new-fnr-tt"),
                        EdsSupplier.INACTIVE);
                vDLSTsuppliers.addOption(newSupplier);
                vDLSTsuppliers.setItemCaption(newSupplier, newSupplier.getSName());
            }
        });
        addButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png"));
        addButton.setStyleName(BaseTheme.BUTTON_LINK);
        addButton.setDescription(controller.getBundle().getString("Admin-acc-fnr-add-fnr-tt"));
        vDLSTsuppliers.addButton(addButton);

        supplierLayout.setExpandRatio(vDLSTsuppliers, 1);

        return supplierLayout;
    }
}
