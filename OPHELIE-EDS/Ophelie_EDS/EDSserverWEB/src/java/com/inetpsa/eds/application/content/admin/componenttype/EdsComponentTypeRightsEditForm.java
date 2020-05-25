package com.inetpsa.eds.application.content.admin.componenttype;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.EdsFormFactory;
import com.inetpsa.eds.dao.model.EdsComponentType;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * This class provide Eds Model rights edit form
 * 
 * @author Geometric Ltd.
 */
public class EdsComponentTypeRightsEditForm extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public EdsComponentTypeRightsEditForm(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /**
     * This method sets componentsType list
     * 
     * @param ctList Collection of EdsComponentType
     */
    public void setComponentTypesList(Collection<EdsComponentType> ctList) {
        initComponant(ctList);
        edsComponentTypes = ctList;
        vCMBcomponentTypes.removeListener(cmbListener);

        vCMBcomponentTypes.removeAllItems();
        for (EdsComponentType componentType : ctList) {

            vCMBcomponentTypes.addItem(componentType);
            vCMBcomponentTypes.setItemCaption(componentType, componentType.getLocaleCtName(getApplication().getLocale()));
        }

        vCMBcomponentTypes.addListener(cmbListener);
        vCMBcomponentTypes.select(ctList.iterator().next());
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
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        for (Entry<EdsComponentType, LinkedHashMap<String, LinkedHashMap<String, CheckBox>>> entry : map.entrySet()) {
            EdsComponentType edsComponentType = entry.getKey();

            bttbtRights = entry.getValue().get("bttbt");
            LinkedHashSet<String> selectedBttbtFormIds = new LinkedHashSet<String>();
            for (String formId : bttbtRights.keySet()) {
                if (bttbtRights.get(formId).booleanValue()) {
                    selectedBttbtFormIds.add(formId);
                }
            }
            edsComponentType.setAllBttbtbForms(selectedBttbtFormIds);

            nonBttbtRights = entry.getValue().get("nonbttbt");
            LinkedHashSet<String> selectedNonBttbtFormIds = new LinkedHashSet<String>();
            for (String formId : nonBttbtRights.keySet()) {
                if (nonBttbtRights.get(formId).booleanValue()) {
                    selectedNonBttbtFormIds.add(formId);
                }
            }
            edsComponentType.setAllNonBttbtbForms(selectedNonBttbtFormIds);
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
        if (vCMBcomponentTypes.getValue() != null) {
            this.select((EdsComponentType) vCMBcomponentTypes.getValue());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection getAllItemsToSave() {
        return edsComponentTypes;
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
     * Variable to hold value of Combo box
     */
    private ComboBox vCMBcomponentTypes;
    /**
     * Variable to hold value of Vertical layout
     */
    private VerticalLayout bttbtLayout;
    /**
     * Variable to hold value of Vertical layout
     */
    private VerticalLayout nonBttbtLayout;

    /**
     * Variable to hold value of Linked hash map of string and check box
     */
    private LinkedHashMap<String, CheckBox> bttbtRights;
    /**
     * Variable to hold value of Linked hash map of string and check box
     */
    private LinkedHashMap<String, CheckBox> nonBttbtRights;
    /**
     * Variable to hold value of listener when combo box value changes
     */
    private ComboBox.ValueChangeListener cmbListener;
    /**
     * Variable to hold value of collection of EdsComponentType
     */
    private Collection<EdsComponentType> edsComponentTypes;
    /**
     * Variable to hold value of Linked hash map of EdsComponenet type and Linked hash map of string and Linked hash map of string and check box
     */
    private LinkedHashMap<EdsComponentType, LinkedHashMap<String, LinkedHashMap<String, CheckBox>>> map;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize EdsComponentTyprRightsEditForm
     */
    private void init() {
        bttbtRights = new LinkedHashMap<String, CheckBox>();
        nonBttbtRights = new LinkedHashMap<String, CheckBox>();
        map = new LinkedHashMap<EdsComponentType, LinkedHashMap<String, LinkedHashMap<String, CheckBox>>>();

        this.setMargin(true);
        this.setSpacing(true);
        this.setSizeFull();

        this.vCMBcomponentTypes = new ComboBox(controller.getBundle().getString("menu-project-tab-model") + " :");
        this.vCMBcomponentTypes.setNullSelectionAllowed(false);
        this.vCMBcomponentTypes.setTextInputAllowed(false);
        this.vCMBcomponentTypes.setImmediate(true);

        Panel bttbtPanel = new Panel(controller.getBundle().getString("Admin-model-form-BT-TBT"));
        bttbtLayout = new VerticalLayout();
        bttbtLayout.setMargin(true);
        // bttbtLayout.setSpacing( true );
        bttbtPanel.setContent(bttbtLayout);

        Panel nonBttbtPanel = new Panel(controller.getBundle().getString("Admin-model-no-BT-TBT"));
        nonBttbtLayout = new VerticalLayout();
        nonBttbtLayout.setMargin(true);
        nonBttbtPanel.setContent(nonBttbtLayout);

        Button vBTcopyRights = new Button(">>", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                for (String formId : bttbtRights.keySet()) {
                    if (bttbtRights.get(formId).booleanValue()) {
                        nonBttbtRights.get(formId).setValue(true);
                    }
                }
            }
        });
        vBTcopyRights.setDescription(controller.getBundle().getString("Admin-model-copy-BT-TBT"));

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setWidth("900px");
        // mainLayout.setHeight( "100%" );
        mainLayout.setSpacing(true);
        mainLayout.addComponent(bttbtPanel);
        mainLayout.addComponent(vBTcopyRights);
        mainLayout.addComponent(nonBttbtPanel);
        mainLayout.setExpandRatio(bttbtPanel, 1f);
        mainLayout.setExpandRatio(nonBttbtPanel, 1f);
        mainLayout.setComponentAlignment(vBTcopyRights, Alignment.MIDDLE_CENTER);

        this.addComponent(vCMBcomponentTypes);
        this.addComponent(mainLayout);

        this.cmbListener = new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                select((EdsComponentType) event.getProperty().getValue());
            }
        };
    }

    /**
     * This method provide all details of selected Eds model
     * 
     * @param ct EdsComponentType
     */
    private void select(EdsComponentType ct) {
        bttbtLayout.removeAllComponents();
        nonBttbtLayout.removeAllComponents();

        LinkedHashMap<String, LinkedHashMap<String, CheckBox>> linkedHashMap = map.get(ct);
        bttbtRights = linkedHashMap.get("bttbt");
        nonBttbtRights = linkedHashMap.get("nonbttbt");

        for (String formId : bttbtRights.keySet()) {
            bttbtRights.get(formId).setValue(bttbtRights.get(formId).booleanValue());
            bttbtLayout.addComponent(bttbtRights.get(formId));
        }
        for (String formId : nonBttbtRights.keySet()) {
            nonBttbtRights.get(formId).setValue(nonBttbtRights.get(formId).booleanValue());
            nonBttbtLayout.addComponent(nonBttbtRights.get(formId));
        }
    }

    /**
     * This method initialize EDS components
     * 
     * @param ctList Collection of EdsComponentType
     */
    private void initComponant(Collection<EdsComponentType> ctList) {

        for (EdsComponentType componentType : ctList) {
            bttbtRights = new LinkedHashMap<String, CheckBox>();
            for (String formId : EdsFormFactory.getFormUniqueIds()) {

                CheckBox vCBright = new CheckBox(controller.getBundle().getString(EdsFormFactory.getBuilder(formId).getCaption()));
                if (componentType.getAllBttbtbForms() != null)
                    vCBright.setValue(componentType.getAllBttbtbForms().contains(formId));
                bttbtRights.put(formId, vCBright);
            }

            nonBttbtRights = new LinkedHashMap<String, CheckBox>();
            for (String formId : EdsFormFactory.getFormUniqueIds()) {
                CheckBox vCBright = new CheckBox(controller.getBundle().getString(EdsFormFactory.getBuilder(formId).getCaption()));
                if (componentType.getAllNonBttbtbForms() != null)
                    vCBright.setValue(componentType.getAllNonBttbtbForms().contains(formId));
                nonBttbtRights.put(formId, vCBright);
            }
            LinkedHashMap<String, LinkedHashMap<String, CheckBox>> linkedHashMap = new LinkedHashMap<String, LinkedHashMap<String, CheckBox>>();
            linkedHashMap.put("nonbttbt", nonBttbtRights);
            linkedHashMap.put("bttbt", bttbtRights);
            map.put(componentType, linkedHashMap);
        }
        map.size();
    }
}
