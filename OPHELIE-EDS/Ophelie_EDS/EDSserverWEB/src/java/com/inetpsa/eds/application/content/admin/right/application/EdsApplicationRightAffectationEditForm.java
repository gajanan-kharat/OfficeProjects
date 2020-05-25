package com.inetpsa.eds.application.content.admin.right.application;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.dao.model.EdsRole;
import com.inetpsa.eds.tools.localisation.BundleManager;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.OptionGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class provide Component for editing application rights.
 * 
 * @author Geometric Ltd.
 */
public class EdsApplicationRightAffectationEditForm extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param role EdsRole details
     * @param controller Controller of EDS application
     */
    public EdsApplicationRightAffectationEditForm(EdsRole role, EdsApplicationController controller) {
        this.role = role;
        this.controller = controller;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        TreeSet<String> userNewRights = new TreeSet<String>();
        for (OptionGroup vOGrights : options) {
            userNewRights.addAll((Collection<String>) vOGrights.getValue());
        }

        StringBuilder newRightValue = new StringBuilder();
        for (String value : userNewRights) {
            newRightValue.append(value).append(';');
        }
        role.setRoRights(newRightValue.toString());
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        List<String> userRightsList = Arrays.asList(role.getRoRights().split(";"));
        for (OptionGroup vOGrights : options) {
            HashSet<String> userRights = new HashSet<String>(userRightsList);
            userRights.retainAll((Collection<String>) vOGrights.getItemIds());
            vOGrights.setValue(userRights);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.singleton((Object) role);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        return true;
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
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsRole
     */
    private EdsRole role;
    /**
     * Variable to hold list of OptionGroup
     */
    private ArrayList<OptionGroup> options;
    /**
     * Variable to hold value of OptionGroup for general rights
     */
    private OptionGroup vOGgeneralRights;
    /**
     * Variable to hold value of OptionGroup for project menu rights
     */
    private OptionGroup vOGprojectMenuRights;
    /**
     * Variable to hold value of OptionGroup for eds menu rights
     */
    private OptionGroup vOGedsMenuRights;
    /**
     * Variable to hold value of OptionGroup for dashboard menu rights
     */
    private OptionGroup vOGdashboardMenuRights;
    /**
     * Variable to hold value of OptionGroup for eds rights
     */
    private OptionGroup vOGedsRights;
    /**
     * Set variable to hold value of string representing rights
     */
    private Set<String> rights;

    /**
     * Initialize Component for editing application rights.
     */
    private void init() {
        setMargin(true);
        setSpacing(true);
        setCaption(controller.getBundle().getString("Admin-droit-ss-title"));

        this.rights = new HashSet<String>();
        this.options = new ArrayList<OptionGroup>();
        Accordion vTABcontent = new Accordion();

        this.vOGgeneralRights = new OptionGroup(controller.getBundle().getString("Admin-droit-gene"));
        this.vOGgeneralRights.setMultiSelect(true);
        this.vOGgeneralRights.setImmediate(true);
        for (String right : EdsRight.getApplicationGeneralRights()) {
            vOGgeneralRights.addItem(right);
            vOGgeneralRights.setItemCaption(right,
                    BundleManager.getInstance().getResourceBundle(controller.getApplication().getLocale()).getString(right));
        }
        vTABcontent.addTab(vOGgeneralRights, controller.getBundle().getString("Admin-droit-gene"));

        this.vOGprojectMenuRights = new OptionGroup(controller.getBundle().getString("Admin-droit-project"));
        this.vOGprojectMenuRights.setMultiSelect(true);
        this.vOGprojectMenuRights.setImmediate(true);

        for (String right : EdsRight.getApplicationProjectMenuRights()) {
            vOGprojectMenuRights.addItem(right);
            vOGprojectMenuRights.setItemCaption(right, BundleManager.getInstance().getResourceBundle(controller.getApplication().getLocale())
                    .getString(right));
        }
        vOGprojectMenuRights.setItemEnabled(EdsRight.APP_PROJECT_MENU_SUBSCRIBE_EDS, false);
        vOGprojectMenuRights.setItemEnabled(EdsRight.APP_PROJECT_MENU_EXPORT_EDS, false);
        vOGprojectMenuRights.setItemEnabled(EdsRight.APP_PROJECT_MENU_EXPORT_SIMULATION, false);
        vTABcontent.addTab(vOGprojectMenuRights, controller.getBundle().getString("Admin-droit-project"));

        this.vOGedsMenuRights = new OptionGroup(controller.getBundle().getString("Admin-droit-EDS"));
        this.vOGedsMenuRights.setMultiSelect(true);
        this.vOGedsMenuRights.setImmediate(true);
        for (String right : EdsRight.getApplicationEdsMenuRights()) {
            vOGedsMenuRights.addItem(right);
            vOGedsMenuRights.setItemCaption(right,
                    BundleManager.getInstance().getResourceBundle(controller.getApplication().getLocale()).getString(right));
        }
        vTABcontent.addTab(vOGedsMenuRights, controller.getBundle().getString("Admin-droit-EDS"));

        this.vOGdashboardMenuRights = new OptionGroup(controller.getBundle().getString("Admin-droit-TDB"));
        this.vOGdashboardMenuRights.setMultiSelect(true);
        this.vOGdashboardMenuRights.setImmediate(true);
        for (String right : EdsRight.getApplicationDashboardMenuRights()) {
            vOGdashboardMenuRights.addItem(right);
            vOGdashboardMenuRights.setItemCaption(right, BundleManager.getInstance().getResourceBundle(controller.getApplication().getLocale())
                    .getString(right));
        }
        vOGdashboardMenuRights.setItemEnabled(EdsRight.APP_DASHBOARD_MENU_EXPORT_TAB_VIEW, false);
        vTABcontent.addTab(vOGdashboardMenuRights, controller.getBundle().getString("Admin-droit-TDB"));

        this.vOGedsRights = new OptionGroup(controller.getBundle().getString("Admin-droit-rens"));
        this.vOGedsRights.setMultiSelect(true);
        this.vOGedsRights.setImmediate(true);
        for (String right : EdsRight.getApplicationEdsRights()) {
            vOGedsRights.addItem(right);
            vOGedsRights.setItemCaption(right, BundleManager.getInstance().getResourceBundle(controller.getApplication().getLocale())
                    .getString(right));
        }

        vOGedsRights.setItemEnabled(EdsRight.APP_EDS_MENU_EXPORT_TAB_VIEW, false);
        vOGedsRights.setItemEnabled(EdsRight.APP_EDS_MODIFY_SETTER_PROJECT, false);
        vTABcontent.addTab(vOGedsRights, controller.getBundle().getString("Admin-droit-rens"));
        vTABcontent.setWidth("500px");

        addComponent(vTABcontent);
        this.options.add(vOGgeneralRights);
        this.options.add(vOGprojectMenuRights);
        this.options.add(vOGedsMenuRights);
        this.options.add(vOGdashboardMenuRights);
        this.options.add(vOGedsRights);
        discardChanges();
    }
}
