package com.inetpsa.eds.application.content.admin.componenttype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.popup.EditWordingWindow;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsComponentType;
import com.inetpsa.eds.dao.model.EdsWording;
import com.inetpsa.eds.tools.doublelist.DoubleList;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.event.MouseEvents.DoubleClickEvent;
import com.vaadin.event.MouseEvents.DoubleClickListener;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This class provide Eds Model list edit form
 * 
 * @author Geometric Ltd.
 */
public class EdsComponentTypeListEditForm extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public EdsComponentTypeListEditForm(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /**
     * This method returns all available EDS models
     * 
     * @return Collection of EdsComponentType
     */
    public Collection<EdsComponentType> getAllAvailableComponentTypes() {
        return vDLSTcomponentTypes.getAllSelections();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        // Check that all component types have a different name
        HashSet<String> differentNameCount = new HashSet<String>();
        int i = 0;
        for (Object o : getAllItemsToSave()) {
            if (o instanceof EdsWording) {
                differentNameCount.add(((EdsWording) o).getCaption());
                i++;
            }
        }
        boolean isValid = (i == differentNameCount.size());
        if (!isValid) {
            getWindow().showNotification(controller.getBundle().getString("pop-error-title"),
                    controller.getBundle().getString("Admin-model-pop-error"), Notification.TYPE_ERROR_MESSAGE);
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
        for (Object inactiveComponentType : vDLSTcomponentTypes.getAllOptions()) {
            ((EdsComponentType) inactiveComponentType).setCtIndex(EdsComponentType.INACTIVE);
        }
        for (Object activeComponentType : vDLSTcomponentTypes.getAllSelections()) {
            ((EdsComponentType) activeComponentType).setCtIndex(vDLSTcomponentTypes.indexOfItem(activeComponentType) + 1);
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
        componentTypesDeleted.clear();
        vDLSTcomponentTypes.removeAllItems();

        for (EdsComponentType componentType : EDSdao.getInstance().getAllComponentTypes(true)) {
            if (componentType.getCtIndex() > EdsComponentType.INACTIVE) {
                vDLSTcomponentTypes.addSelection(componentType);
            } else {
                vDLSTcomponentTypes.addOption(componentType);
            }
            vDLSTcomponentTypes.setItemCaption(componentType, componentType.getCtName().getCaption());
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        Collection itemsToSave = new ArrayList();
        for (Object object : vDLSTcomponentTypes.getAllOptions()) {
            EdsComponentType ct = (EdsComponentType) object;
            itemsToSave.add(ct.getCtName());
            itemsToSave.add(ct);
        }
        for (Object object : vDLSTcomponentTypes.getAllSelections()) {
            EdsComponentType ct = (EdsComponentType) object;
            itemsToSave.add(ct.getCtName());
            itemsToSave.add(ct);
        }
        itemsToSave.addAll(componentTypesDeleted);
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
     * Variable to hold value of DoubleList for Eds models
     */
    private DoubleList vDLSTcomponentTypes;
    /**
     * Variable to hold set of EdsComponentType
     */
    private HashSet<EdsComponentType> componentTypesDeleted;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize EdsComponentTypeListEditForm
     */
    private void init() {
        this.componentTypesDeleted = new HashSet<EdsComponentType>();

        addComponent(getLayout());
    }

    /**
     * This method returns layout for Eds model
     * 
     * @return Layout for Eds model
     */
    private Layout getLayout() {
        HorizontalLayout componentTypeLayout = new HorizontalLayout();
        componentTypeLayout.setSpacing(true);
        componentTypeLayout.setMargin(true);

        this.vDLSTcomponentTypes = new DoubleList();
        vDLSTcomponentTypes.setLeftColumnCaption(controller.getBundle().getString("Admin-model-model-inact"));
        vDLSTcomponentTypes.setRightColumnCaption(controller.getBundle().getString("Admin-model-model-act"));
        vDLSTcomponentTypes.setRows(20);
        vDLSTcomponentTypes.setMultiSelect(true);
        vDLSTcomponentTypes.setNullSelectionAllowed(true);
        vDLSTcomponentTypes.setImmediate(true);
        vDLSTcomponentTypes.setWidth("500px");
        componentTypeLayout.addComponent(vDLSTcomponentTypes);

        // Add listener double click for option list-> Rename window opens
        vDLSTcomponentTypes.addOptionDoubleClickListener(new DoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                if (!((Set<EdsComponentType>) vDLSTcomponentTypes.getOptionValue()).isEmpty()) {
                    final EdsComponentType selectedComponentType = ((Set<EdsComponentType>) vDLSTcomponentTypes.getOptionValue()).iterator().next();
                    EdsWording selectedWording = selectedComponentType.getCtName();
                    EditWordingWindow window = new EditWordingWindow(selectedWording, (AbstractSelect) event.getComponent(), controller.getBundle()
                            .getString("Admin-model-edit-name"), new EditWordingWindow.ValidateListener() {
                        public void onValidate(EdsWording wording) {
                            vDLSTcomponentTypes.setItemCaption(selectedComponentType, wording.getCaption());
                        }
                    }, controller);
                    window.setModal(true);
                    getApplication().getMainWindow().addWindow(window);
                    window.center();
                }
            }
        });

        // Add listener double click for selected list-> Rename window opens
        vDLSTcomponentTypes.addSelectionDoubleClickListener(new DoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                if (!((Set<EdsComponentType>) vDLSTcomponentTypes.getSelectedValue()).isEmpty()) {
                    final EdsComponentType selectedComponentType = ((Set<EdsComponentType>) vDLSTcomponentTypes.getSelectedValue()).iterator().next();
                    EdsWording selectedWording = selectedComponentType.getCtName();
                    EditWordingWindow window = new EditWordingWindow(selectedWording, (AbstractSelect) event.getComponent(), controller.getBundle()
                            .getString("Admin-model-edit-name"), new EditWordingWindow.ValidateListener() {
                        public void onValidate(EdsWording wording) {
                            vDLSTcomponentTypes.setItemCaption(selectedComponentType, wording.getCaption());
                        }
                    }, controller);
                    window.setModal(true);
                    getApplication().getMainWindow().addWindow(window);
                    window.center();
                }
            }
        });

        VerticalLayout componentTypeControlLayout = new VerticalLayout();
        componentTypeControlLayout.setSpacing(true);

        // Button to bring up the list
        Button upButton = new Button(null, new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                Set<EdsComponentType> valueToMove = new HashSet<EdsComponentType>((Set<EdsComponentType>) vDLSTcomponentTypes.getSelectedValue());
                if (valueToMove.size() == 1) {
                    vDLSTcomponentTypes.moveUp(valueToMove.iterator().next());
                }
            }
        });
        upButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/up.png "));
        upButton.setStyleName(BaseTheme.BUTTON_LINK);
        upButton.setDescription(controller.getBundle().getString("Admin-model-upp-model"));
        componentTypeControlLayout.addComponent(upButton);

        // Button to scroll down the list
        Button downButton = new Button("", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                Set<EdsComponentType> valueToMove = new HashSet<EdsComponentType>((Set<EdsComponentType>) vDLSTcomponentTypes.getSelectedValue());
                if (valueToMove.size() == 1) {
                    vDLSTcomponentTypes.moveDown(valueToMove.iterator().next());
                }
            }
        });
        downButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/down.png "));
        downButton.setStyleName(BaseTheme.BUTTON_LINK);
        downButton.setDescription(controller.getBundle().getString("Admin-model-down-model"));
        componentTypeControlLayout.addComponent(downButton);

        // Button to add a new EDS model
        Button addButton = new Button("", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                EdsComponentType newComponentType = new EdsComponentType(UUID.randomUUID().toString(), new EdsWording(UUID.randomUUID().toString(),
                        "fra:Nouveau modèle de fiche;eng:New record model", EdsWording.COMPONENT_TYPE, 1), EdsComponentType.INACTIVE);
                vDLSTcomponentTypes.addOption(newComponentType);
                vDLSTcomponentTypes.setItemCaption(newComponentType, newComponentType.getCtName().getCaption());
            }
        });
        addButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png"));
        addButton.setStyleName(BaseTheme.BUTTON_LINK);
        addButton.setDescription(controller.getBundle().getString("Admin-model-add-nex-model"));
        vDLSTcomponentTypes.addButton(addButton);

        componentTypeLayout.addComponent(componentTypeControlLayout);
        componentTypeLayout.setComponentAlignment(componentTypeControlLayout, Alignment.MIDDLE_LEFT);
        componentTypeLayout.setExpandRatio(vDLSTcomponentTypes, 1);

        return componentTypeLayout;
    }
}
