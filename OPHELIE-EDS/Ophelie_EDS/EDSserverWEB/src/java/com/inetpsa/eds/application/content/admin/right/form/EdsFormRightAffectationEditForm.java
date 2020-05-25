/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inetpsa.eds.application.content.admin.right.form;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.I_FormBuilder;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.versionhistory.VersionHistoryFormBuilder;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.dao.model.EdsRole;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class provide Component for editing form rights.
 * 
 * @author Geometric Ltd.
 */
public class EdsFormRightAffectationEditForm extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param role EdsRole details
     * @param controller Controller of EDS application
     */
    public EdsFormRightAffectationEditForm(EdsRole role, EdsApplicationController controller) {
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
        for (String formId : (Set<String>) vOGreadingRights.getValue()) {
            userNewRights.add(formIdToReadingRightsMap.get(formId));
        }
        for (String formId : (Set<String>) vOGwritingRights.getValue()) {
            userNewRights.add(formIdToWritingRightsMap.get(formId));
        }

        StringBuilder newRightValue = new StringBuilder();
        for (String value : userNewRights) {
            newRightValue.append(value).append(";");
        }
        role.setRoFormRights(newRightValue.toString());
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        List<String> userRightsList = Arrays.asList(role.getRoFormRights().split(";"));
        Set<String> readingRightValue = new HashSet<String>();
        Set<String> writingRightValue = new HashSet<String>();
        // Determining the rights associated to the user
        for (String right : userRightsList) {
            if (readingRightsToFormIdMap.containsKey(right)) {
                readingRightValue.add(readingRightsToFormIdMap.get(right));
            } else if (writingRightsToFormIdMap.containsKey(right)) {
                writingRightValue.add(writingRightsToFormIdMap.get(right));
            }
        }
        vOGreadingRights.setValue(readingRightValue);
        vOGwritingRights.setValue(writingRightValue);
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
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete()
     */
    @Override
    public Collection getAllItemsToDelete() {
        return Collections.EMPTY_SET;
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
     * Variable to hold value of OptionGroup for general reading rights
     */
    private OptionGroup vOGreadingRights;
    /**
     * Variable to hold value of OptionGroup for general writing rights
     */
    private OptionGroup vOGwritingRights;
    /**
     * Variable to hold value of check box of for all reading rights
     */
    private CheckBox vCBallReadingRights;
    /**
     * Variable to hold value of check box of for all writing rights
     */
    private CheckBox vCBallWritingRights;
    /**
     * Variable to hold value of map of String related to form id and string related to riding rights
     */
    private Map<String, String> formIdToReadingRightsMap;
    /**
     * Variable to hold value of map of String related to form id and string related to writing rights
     */
    private Map<String, String> formIdToWritingRightsMap;
    /**
     * Variable to hold value of map of String related to riding rights and string related to form id
     */
    private Map<String, String> readingRightsToFormIdMap;
    /**
     * Variable to hold value of map of String related to writing rights and string related to form id
     */
    private Map<String, String> writingRightsToFormIdMap;

    /**
     * Initialize Component for editing form rights.
     */
    private void init() {
        HorizontalLayout contentLayout = new HorizontalLayout();
        contentLayout.setMargin(true);
        contentLayout.setSpacing(true);
        contentLayout.setCaption(controller.getBundle().getString("Admin-droit-ss-title"));

        this.formIdToReadingRightsMap = new HashMap<String, String>();
        this.formIdToWritingRightsMap = new HashMap<String, String>();
        this.readingRightsToFormIdMap = new HashMap<String, String>();
        this.writingRightsToFormIdMap = new HashMap<String, String>();

        this.vOGreadingRights = new OptionGroup();
        this.vOGreadingRights.setMultiSelect(true);
        this.vOGreadingRights.setImmediate(true);
        this.vOGwritingRights = new OptionGroup();
        this.vOGwritingRights.setMultiSelect(true);
        this.vOGwritingRights.setImmediate(true);

        this.vCBallReadingRights = new CheckBox();
        this.vCBallReadingRights.setCaption(controller.getBundle().getString("Admin-droit-form-all-coch"));
        this.vCBallReadingRights.setImmediate(true);
        this.vCBallWritingRights = new CheckBox();
        this.vCBallWritingRights.setCaption(controller.getBundle().getString("Admin-droit-form-all-coch"));
        this.vCBallWritingRights.setImmediate(true);

        VerticalLayout readingRightsLayout = new VerticalLayout();
        readingRightsLayout.setCaption(controller.getBundle().getString("Admin-droit-form-read"));
        readingRightsLayout.addComponent(vCBallReadingRights);
        readingRightsLayout.addComponent(vOGreadingRights);
        VerticalLayout writingRightsLayout = new VerticalLayout();
        writingRightsLayout.setCaption(controller.getBundle().getString("Admin-droit-form-write"));
        writingRightsLayout.addComponent(vCBallWritingRights);
        writingRightsLayout.addComponent(vOGwritingRights);

        for (I_FormBuilder builder : EdsRight.getFormReadRights().keySet()) {
            // Filling options
            vOGreadingRights.addItem(builder.getUniqueId());
            vOGreadingRights.setItemCaption(builder.getUniqueId(), controller.getBundle().getString(builder.getCaption()));

            vOGwritingRights.addItem(builder.getUniqueId());
            vOGwritingRights.setItemCaption(builder.getUniqueId(), controller.getBundle().getString(builder.getCaption()));

            // Filling maps of correspondence between ids
            formIdToReadingRightsMap.put(builder.getUniqueId(), EdsRight.getFormReadRights().get(builder));
            readingRightsToFormIdMap.put(EdsRight.getFormReadRights().get(builder), builder.getUniqueId());
            formIdToWritingRightsMap.put(builder.getUniqueId(), EdsRight.getFormWriteRights().get(builder));
            writingRightsToFormIdMap.put(EdsRight.getFormWriteRights().get(builder), builder.getUniqueId());
        }

        // No write access to the historical form of conversations
        vOGwritingRights.removeItem(new VersionHistoryFormBuilder().getUniqueId());

        vOGreadingRights.addListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                Set<String> readingValues = new HashSet<String>((Set<String>) vOGreadingRights.getValue());
                Set<String> writingValues = new HashSet<String>((Set<String>) vOGwritingRights.getValue());

                if (!readingValues.containsAll(writingValues)) {
                    writingValues.retainAll(readingValues);
                    vOGwritingRights.setValue(writingValues);
                }
            }
        });

        vOGwritingRights.addListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                Set<String> readingValues = new HashSet<String>((Set<String>) vOGreadingRights.getValue());
                Set<String> writingValues = new HashSet<String>((Set<String>) vOGwritingRights.getValue());

                if (!readingValues.containsAll(writingValues)) {
                    readingValues.addAll(writingValues);
                    vOGreadingRights.setValue(readingValues);
                }
            }
        });

        // Check / Uncheck All
        vCBallReadingRights.addListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                if ((Boolean) vCBallReadingRights.getValue()) {
                    vOGreadingRights.setValue(vOGreadingRights.getItemIds());
                } else {
                    vOGreadingRights.setValue(Collections.EMPTY_SET);
                }
            }
        });
        vCBallWritingRights.addListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                if ((Boolean) vCBallWritingRights.getValue()) {
                    vOGwritingRights.setValue(vOGwritingRights.getItemIds());
                } else {
                    vOGwritingRights.setValue(Collections.EMPTY_SET);
                }
            }
        });

        contentLayout.addComponent(readingRightsLayout);
        contentLayout.addComponent(writingRightsLayout);

        addComponent(contentLayout);
        discardChanges();
    }
}
