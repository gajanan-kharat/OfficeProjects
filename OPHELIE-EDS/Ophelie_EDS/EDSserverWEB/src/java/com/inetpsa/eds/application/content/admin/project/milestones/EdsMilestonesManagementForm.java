package com.inetpsa.eds.application.content.admin.project.milestones;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.popup.EditStringWindow;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsWording;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Indexed;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This class provide milestone management form
 * 
 * @author Geometric Ltd.
 */
public class EdsMilestonesManagementForm extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public EdsMilestonesManagementForm(EdsApplicationController controller) {
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
        // Check that all milestones have a different name
        HashSet<String> differentNameCount = new HashSet<String>();
        int i = 0;
        for (EdsWording wording : (Collection<EdsWording>) vLSTmilestones.getItemIds()) {
            differentNameCount.add(wording.getWValue());
            i++;
        }
        boolean isValid = (i == differentNameCount.size());
        if (!isValid) {
            getWindow().showNotification(controller.getBundle().getString("pop-error-title"),
                    controller.getBundle().getString("Admin-proj-jal-pop-error"), Notification.TYPE_ERROR_MESSAGE);
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
        milestonesToSave.clear();

        // Updated index labels
        Container.Indexed container = (Indexed) vLSTmilestones.getContainerDataSource();
        for (Object o : vLSTmilestones.getItemIds()) {
            EdsWording milestone = (EdsWording) o;
            int milestoneIndex = container.indexOfId(o);
            if (milestoneIndex < 4) {
                milestone.setWIndex(milestoneIndex + 1);
            } else {
                milestone.setWIndex(EdsWording.INACTIVE);
            }
        }
        milestonesToSave.addAll((Collection<EdsWording>) vLSTmilestones.getItemIds());
        milestonesToSave.addAll(milestonesDeleted);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        vLSTmilestones.removeAllItems();
        milestonesDeleted.clear();

        for (EdsWording milestone : EDSdao.getInstance().getAllActiveWordingsByType(EdsWording.MILESTONE)) {
            vLSTmilestones.addItem(milestone);
        }
        for (EdsWording milestone : EDSdao.getInstance().getAllInactiveWordingsByType(EdsWording.MILESTONE)) {
            vLSTmilestones.addItem(milestone);
        }
        refreshAllMilestoneCaptions();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return new ArrayList<Object>(milestonesToSave);
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
     * Variable to hold ListSelect of milestones
     */
    private ListSelect vLSTmilestones;
    /**
     * Variable to hold list of EdsWording to be saved
     */
    private ArrayList<EdsWording> milestonesToSave;
    /**
     * Variable to hold list of EdsWording to be deleted
     */
    private ArrayList<EdsWording> milestonesDeleted;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize milestone management form
     */
    private void init() {
        this.milestonesToSave = new ArrayList<EdsWording>();
        this.milestonesDeleted = new ArrayList<EdsWording>();
        TabSheet vTSmain = new TabSheet();
        vTSmain.setSizeFull();

        vTSmain.addTab(getMilestoneLayout(), controller.getBundle().getString("Admin-proj-ss-title-jal"));

        addComponent(vTSmain);
    }

    /**
     * This method returns layout for milestone management form
     * 
     * @return Layout for milestone management form
     */
    private Layout getMilestoneLayout() {
        HorizontalLayout mileStoneLayout = new HorizontalLayout();
        mileStoneLayout.setSpacing(true);
        mileStoneLayout.setMargin(true);

        this.vLSTmilestones = new ListSelect(controller.getBundle().getString("Admin-proj-jal"));
        vLSTmilestones.setRows(20);
        vLSTmilestones.setNullSelectionAllowed(false);
        vLSTmilestones.setImmediate(true);
        vLSTmilestones.setWidth("300px");
        mileStoneLayout.addComponent(vLSTmilestones);

        // Adding listener double click -> Rename window opens
        mileStoneLayout.addListener(new LayoutClickListener() {
            public void layoutClick(LayoutClickEvent event) {
                if (event.getChildComponent() == vLSTmilestones && event.isDoubleClick()) {
                    final EdsWording selectedMilestone = (EdsWording) vLSTmilestones.getValue();
                    if (selectedMilestone != null) {
                        EditStringWindow window = new EditStringWindow(selectedMilestone.getWValue(), controller.getBundle().getString(
                                "Admin-proj-renom-jal"), controller.getBundle().getString("Admin-proj-renom-jal-new-name"), controller,
                                new EditStringWindow.ValidateListener() {
                                    /*
                                     * (non-Javadoc)
                                     * 
                                     * @see com.inetpsa.eds.application.popup .EditStringWindow .ValidateListener#onValidate (java.lang.String)
                                     */
                                    @Override
                                    public void onValidate(String newValue) {
                                        selectedMilestone.setWValue(newValue);
                                        refreshAllMilestoneCaptions();
                                    }
                                });
                        window.setModal(true);
                        getApplication().getMainWindow().addWindow(window);
                        window.center();
                    }
                }
            }
        });

        VerticalLayout milestoneControlLayout = new VerticalLayout();
        milestoneControlLayout.setSpacing(true);

        // Button to bring up the list
        Button upButton = new Button(null, new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                moveUp((EdsWording) vLSTmilestones.getValue());

            }
        });
        upButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/up.png "));
        upButton.setStyleName(BaseTheme.BUTTON_LINK);
        upButton.setDescription(controller.getBundle().getString("Admin-proj-up-jal-tt"));
        milestoneControlLayout.addComponent(upButton);

        // Button to scroll down the list
        Button downButton = new Button("", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {

                moveDown((EdsWording) vLSTmilestones.getValue());
            }
        });
        downButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/down.png "));
        downButton.setStyleName(BaseTheme.BUTTON_LINK);
        downButton.setDescription(controller.getBundle().getString("Admin-proj-down-jal-tt"));
        milestoneControlLayout.addComponent(downButton);

        // Button to add a milestone
        Button addButton = new Button("", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                EdsWording newMilestone = new EdsWording(UUID.randomUUID().toString(), controller.getBundle().getString("Admin-proj-new-jal"),
                        EdsWording.MILESTONE, EdsWording.INACTIVE);
                vLSTmilestones.addItem(newMilestone);
                refreshMilestoneCaption(newMilestone);
            }
        });
        addButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png"));
        addButton.setStyleName(BaseTheme.BUTTON_LINK);
        addButton.setDescription(controller.getBundle().getString("Admin-proj-add-new-jal"));
        milestoneControlLayout.addComponent(addButton);

        mileStoneLayout.addComponent(milestoneControlLayout);
        mileStoneLayout.setComponentAlignment(milestoneControlLayout, Alignment.MIDDLE_LEFT);
        mileStoneLayout.setExpandRatio(vLSTmilestones, 1);
        milestoneControlLayout.setSizeUndefined();

        return mileStoneLayout;
    }

    /**
     * This method refreshes all milestone captions
     */
    private void refreshAllMilestoneCaptions() {
        for (Object milestone : vLSTmilestones.getItemIds()) {
            refreshMilestoneCaption((EdsWording) milestone);
        }
    }

    /**
     * This method refreshes milestone caption of specified EdsWording
     * 
     * @param milestone EdsWording
     */
    private void refreshMilestoneCaption(EdsWording milestone) {
        if (milestone != null) {
            switch (((Indexed) vLSTmilestones.getContainerDataSource()).indexOfId(milestone)) {
            case -1:
                break;
            case 0: {
                vLSTmilestones.setItemCaption(milestone, milestone.getWValue() + " (" + controller.getBundle().getString("menu-app-step-prelim")
                        + ")");
                break;
            }
            case 1: {
                vLSTmilestones.setItemCaption(milestone, milestone.getWValue() + " (" + controller.getBundle().getString("menu-app-step-rob") + ")");
                break;
            }
            case 2: {
                vLSTmilestones.setItemCaption(milestone, milestone.getWValue() + "( " + controller.getBundle().getString("Validation-step-cons")
                        + ")");
                break;
            }
            case 3: {
                vLSTmilestones.setItemCaption(milestone, milestone.getWValue() + " (" + controller.getBundle().getString("menu-app-step-clot") + ")");
                break;
            }
            default: {
                vLSTmilestones.setItemCaption(milestone, milestone.getWValue() + " (" + controller.getBundle().getString("stade-inactif") + ")");
                break;
            }
            }
        }
    }

    /**
     * This method Moves up the specified EdsWording(milestone)
     * 
     * @param milestone EdsWording
     */
    private void moveUp(EdsWording milestone) {
        if (vLSTmilestones.containsId(milestone)) {
            Container.Indexed container = (Indexed) vLSTmilestones.getContainerDataSource();
            int itemIndex = container.indexOfId(milestone);
            if (itemIndex > 0) {
                container.removeItem(milestone);
                container.addItemAt(itemIndex - 1, milestone);
                refreshMilestoneCaption(milestone);
                refreshMilestoneCaption((EdsWording) container.nextItemId(milestone));
            }
        }
    }

    /**
     * This method Moves down specified EdsWording(milestone)
     * 
     * @param milestone EdsWording
     */
    private void moveDown(EdsWording milestone) {
        if (vLSTmilestones.containsId(milestone)) {
            Container.Indexed container = (Indexed) vLSTmilestones.getContainerDataSource();
            int itemIndex = container.indexOfId(milestone);
            if (itemIndex < container.size() - 1) {
                container.removeItem(milestone);
                container.addItemAt(itemIndex + 1, milestone);
                refreshMilestoneCaption(milestone);
                refreshMilestoneCaption((EdsWording) container.prevItemId(milestone));
            }
        }
    }
}
