package com.inetpsa.eds.application.content.eds.connectivity.cavity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.inetpsa.dbelec.model.conductor.Conductor;
import com.inetpsa.dbelec.model.wiredinterface.Profil;
import com.inetpsa.dbelec.model.wiredinterface.WiredInterface;
import com.inetpsa.eds.application.ChsController;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.connectivity.cavity.panel.ComplexProfileTableOfPointsEditView;
import com.inetpsa.eds.application.content.eds.connectivity.cavity.panel.SimpleProfileEditView;
import com.inetpsa.eds.application.content.eds.connectivity.cavity.panel.WITableOfPointsReadView;
import com.inetpsa.eds.application.content.eds.connectivity.cavity.validator.TFDoubleValidator;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsBsx;
import com.inetpsa.eds.dao.model.EdsBsxPin;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsPinConnect;
import com.inetpsa.eds.dao.model.EdsPinTypeComment;
import com.inetpsa.eds.dao.model.EdsSPProperty;
import com.inetpsa.eds.dao.model.EdsSupply;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.dao.model.EdsWIProperty;
import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import edu.emory.mathcs.backport.java.util.Arrays;
import edu.emory.mathcs.backport.java.util.Collections;

/**
 * This class represents 'Cavity Definition' edit view.
 * 
 * @author Joao Costa @ Alter Frame
 */
public class CavityDefFormEditView extends A_EdsEditForm {

    private Map<String, Table> tables;

    private Map<String, BeanItemContainer<EdsPinConnect>> containerMap;

    private Map<String, List<EdsPinConnect>> pinMap;

    private final List<String> rMapTypes = new ArrayList<String>(Arrays.asList(new String[] { "GND", "IN", "OUT", "IxO" }));

    private Map<String, TextField> pinComments;

    private Map<String, TextArea> typeComments;

    private TextField tMax;

    private TextField tMin;

    private Collection<WiredInterface> WIlist;

    private Set<String> COlist;

    private EdsPinConnect selectedItem;

    private Layout detailsTab;

    private ComboBox combo;

    private final String username;

    private List<EdsSPProperty> modifyList;

    /**
     * Parameterized constructor.
     * 
     * @param eds EDS details.
     * @param controller EDS application controller object.
     */
    public CavityDefFormEditView(EdsEds eds, EdsApplicationController controller) {
        this.eds = eds;
        this.controller = controller;
        this.bundle = controller.getBundle();
        EdsUser user = controller.getAuthenticatedUser();
        this.username = user.getUFirstname() + " " + user.getULastname() + " " + user.getUPsaId();
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        boolean isValid = true;
        if (!tMax.isValid()) {
            isValid = false;
        }
        if (!tMin.isValid()) {
            isValid = false;
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
        if (isValid()) {
            eds.setEdsTypeComment(getEdsTypeComments());

            return true;
        }
        return false;
    }

    public Set<EdsPinTypeComment> getEdsTypeComments() {
        Set<EdsPinTypeComment> comments = eds.getEdsTypeComment();

        for (EdsPinTypeComment type : comments) {
            Object value = typeComments.get(type.getType()).getValue();
            type.setMessage(value != null ? value.toString() : "");
            if (type.getType().equals("OUT")) {
                type.settMax(null);
                type.settMin(null);
                String convert;
                convert = (String) tMax.getValue();
                if (convert != null && !convert.isEmpty()) {
                    type.settMax(Double.valueOf(convert));
                }
                convert = (String) tMin.getValue();
                if (convert != null && !convert.isEmpty()) {
                    type.settMin(Double.valueOf(convert));
                }

            }
        }

        return comments;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        refreshItems();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        List<Object> itemsToSave = new ArrayList<Object>();
        itemsToSave.add(eds);
        return itemsToSave;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete()
     */
    @Override
    public Collection getAllItemsToDelete() {

        List<Object> itemsToDelete = new ArrayList<Object>();
        return itemsToDelete;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        eds = EDSdao.getInstance().getEdsByRef(eds.getEdsRef());

        initGestionData();
        initPinMapping(eds.getEdsPinConnect());
        initPinComments(eds.getEdsPinConnect());
        initTypeComments(eds.getEdsTypeComment());
        BeanItemContainer<EdsPinConnect> container;
        List<EdsPinConnect> pins;
        for (String key : ChsController.pinTypes) {
            pins = pinMap.get(key);
            container = containerMap.get(key);
            container.removeAllItems();
            if (pins != null) {
                container.addAll(pins);
            }
        }
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;
    /**
     * Accordion panel for main layout.
     */
    private Accordion accordion;

    /**
     * EDS details.
     */
    private EdsEds eds;

    private ResourceBundle bundle;

    /**
     * Initialization method.
     */
    private void init() {
        tMax = new TextField(bundle.getString("chs-cavdef-pin-tmax"));
        tMax.setNullRepresentation("");
        tMax.addValidator(new TFDoubleValidator(true));
        tMax.setImmediate(true);
        tMin = new TextField(bundle.getString("chs-cavdef-pin-tmin"));
        tMin.setNullRepresentation("");
        tMin.addValidator(new TFDoubleValidator(true));
        tMin.setImmediate(true);
        initDetails();
        initGestionData();
        tables = new HashMap<String, Table>();
        containerMap = new HashMap<String, BeanItemContainer<EdsPinConnect>>();

        TabSheet main = new TabSheet();
        main.addListener(new SelectedTabChangeListener() {

            @Override
            public void selectedTabChange(SelectedTabChangeEvent event) {
                accordion.addStyleName("hideAccordion");
            }

        });

        main.setSizeFull();
        addComponent(main);

        initPinMapping(eds.getEdsPinConnect());
        initPinComments(eds.getEdsPinConnect());
        initTypeComments(eds.getEdsTypeComment());

        for (String key : ChsController.pinTypes) {
            main.addTab(initTab(key)).setCaption(key);
        }
    }

    private void initGestionData() {
        COlist = new HashSet<String>();
        if (controller.getDbelec() != null) {
            WIlist = controller.getDbelec().getWiredInterfaces().getAll();
            for (Conductor c : controller.getDbelec().getConductors().getAll()) {
                COlist.add(c.getID_2());
            }
        } else {
            WIlist = new ArrayList<WiredInterface>();
        }
        String minSection;
        for (EdsPinConnect pin : eds.getEdsPinConnect()) {
            minSection = pin.getMinSection();
            if (minSection != null && !minSection.isEmpty()) {
                COlist.add(minSection);
            }
        }
    }

    private void initPinComments(Set<EdsPinConnect> comment) {
        TextField commentArea;
        pinComments = new HashMap<String, TextField>();
        Iterator<EdsPinConnect> it = comment.iterator();
        EdsPinConnect i;
        while (it.hasNext()) {
            i = it.next();
            commentArea = new TextField();
            commentArea.setNullRepresentation("");
            commentArea.setSizeFull();
            commentArea.setValue(i.getMessage());
            pinComments.put(i.getPinType() + "#" + i.getCavity(), commentArea);
        }
    }

    private void initTypeComments(Set<EdsPinTypeComment> comment) {
        TextArea commentArea;
        if (typeComments == null) {
            typeComments = new HashMap<String, TextArea>();
            for (String key : ChsController.pinTypes) {
                commentArea = new TextArea(bundle.getString("chs-cavdef-pintype-comment"));
                commentArea.setNullRepresentation("");
                commentArea.setSizeFull();
                typeComments.put(key, commentArea);

            }
        }

        for (String key : ChsController.pinTypes) {
            typeComments.get(key).setEnabled(false);
        }
        Iterator<EdsPinTypeComment> it = comment.iterator();
        EdsPinTypeComment i;
        while (it.hasNext()) {
            i = it.next();
            commentArea = typeComments.get(i.getType());
            commentArea.setEnabled(true);
            commentArea.setValue(i.getMessage());
            if (i.getType().equals("OUT")) {
                tMax.setValue(i.gettMax() != null ? i.gettMax().toString() : null);
                tMin.setValue(i.gettMin() != null ? i.gettMin().toString() : null);
            }
        }
    }

    private void initPinMapping(Set<EdsPinConnect> edsChs) {
        List<EdsPinConnect> list;
        EdsPinConnect edsPin;
        pinMap = new HashMap<String, List<EdsPinConnect>>();
        Iterator<EdsPinConnect> it = edsChs.iterator();
        Iterator<EdsBsx> itBsx = eds.getEdsBsx().iterator();
        EdsBsx bsx = itBsx.hasNext() ? itBsx.next() : null;
        Set<EdsBsxPin> bsxPins;
        while (it.hasNext()) {
            edsPin = it.next();
            String pinType = edsPin.getPinType();
            if (!pinMap.containsKey(pinType)) {
                pinMap.put(pinType, new ArrayList<EdsPinConnect>());
            }
            list = pinMap.get(pinType);
            if (bsx != null) {
                bsxPins = bsx.getPin();
                for (EdsBsxPin p : bsxPins) {
                    if (p.isSamePinAs(edsPin.getCavity())) {
                        edsPin.setCrossed(true);
                    }
                }
            }
            list.add(edsPin);
        }

    }

    private Layout initTab(String type) {

        HorizontalLayout tab = new HorizontalLayout();
        tab.setSizeFull();
        tab.setMargin(true);

        VerticalLayout content = new VerticalLayout();
        VerticalLayout details = new VerticalLayout();
        details.setSizeFull();
        content.setMargin(true);
        content.setSpacing(true);
        Table pins = buildTable(type, details);
        content.addComponent(pins);

        content.addComponent(details);
        tab.addComponent(content);
        tab.setExpandRatio(content, 3.0f);

        VerticalLayout commentArea = new VerticalLayout();
        commentArea.setSizeFull();
        commentArea.addComponent(typeComments.get(type));
        if (type.equals("OUT")) {
            commentArea.addComponent(tMax);
            commentArea.addComponent(tMin);
        }
        tab.addComponent(commentArea);
        tab.setExpandRatio(commentArea, 1.0f);
        return tab;
    }

    private ComboBox getConsumption() {
        combo = new ComboBox();

        combo.addItem(bundle.getString("chs-cavdef-pin-newprofile"));
        combo.addItem(bundle.getString("chs-cavdef-pin-readexisting"));
        combo.addItem(bundle.getString("chs-cavdef-pin-associateif"));
        combo.setImmediate(true);
        combo.addListener(new ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {

                String selected = (String) event.getProperty().getValue();
                detailsTab.removeAllComponents();
                if (selected != null) {
                    if (selected.equals(bundle.getString("chs-cavdef-pin-newprofile"))) {
                        detailsTab.addComponent(getNewProfileTab(true));
                    } else if (selected.equals(bundle.getString("chs-cavdef-pin-readexisting"))) {
                        detailsTab.addComponent(getReadExistingTab());
                    } else if (selected.equals(bundle.getString("chs-cavdef-pin-associateif"))) {
                        detailsTab.addComponent(getAssociateIFTab());
                    }
                }
            }

        });
        return combo;
    }

    private Accordion initDetails() {
        accordion = new Accordion();
        accordion.setSizeFull();
        accordion.addStyleName("hideAccordion");
        accordion.addTab(getDetailsTab());

        return accordion;
    }

    private Layout getDetailsTab() {
        final GridLayout layout = new GridLayout(3, 2);
        detailsTab = new VerticalLayout();
        detailsTab.setSizeFull();
        layout.setSizeFull();
        layout.setMargin(true);
        Label nameLabel = new Label(bundle.getString("chs-cavdef-details"));

        layout.addComponent(nameLabel, 0, 0);
        layout.addComponent(getConsumption(), 1, 0);
        layout.addComponent(detailsTab, 0, 1, 2, 1);
        layout.setComponentAlignment(nameLabel, Alignment.MIDDLE_RIGHT);
        return layout;
    }

    private Layout getNewProfileTab(final boolean newItem) {
        final GridLayout layout = new GridLayout(3, 3);
        final VerticalLayout panel = new VerticalLayout();
        layout.setSizeFull();
        panel.setSizeFull();
        layout.setMargin(true);
        Label nameLabel = new Label(bundle.getString("chs-newprofile-name"));
        layout.addComponent(nameLabel, 0, 0);
        layout.setComponentAlignment(nameLabel, Alignment.MIDDLE_RIGHT);

        final TextField nameField = new TextField();
        layout.addComponent(nameField, 1, 0);

        Label typeLabel = new Label(bundle.getString("chs-newprofile-choice"));

        layout.addComponent(typeLabel, 0, 1);
        layout.setComponentAlignment(typeLabel, Alignment.MIDDLE_RIGHT);
        final OptionGroup typeField = new OptionGroup();
        typeField.setImmediate(true);
        typeField.addItem(bundle.getString("chs-newprofile-simple"));
        typeField.addItem(bundle.getString("chs-newprofile-complex"));
        typeField.setStyleName("horizontal");

        typeField.addListener(new ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                Layout profile;
                if (typeField.getValue().equals(bundle.getString("chs-newprofile-simple"))) {
                    profile = getSimpleLayout(nameField, newItem);
                } else {
                    profile = getComplexLayout(nameField, newItem);
                }
                layout.replaceComponent(layout.getComponent(0, 2), profile);

            }

        });
        layout.addComponent(typeField, 1, 1);
        layout.addComponent(panel, 0, 2, 2, 2);
        if (!newItem) {
            Layout profile;
            if (selectedItem.getSimpleProfile() != null) {
                profile = getSimpleLayout(nameField, newItem);
                nameField.setValue(selectedItem.getSimpleProfile());
                typeField.select(bundle.getString("chs-newprofile-simple"));
            } else {
                profile = getComplexLayout(nameField, newItem);
                nameField.setValue(selectedItem.getComplexProfile());
                typeField.select(bundle.getString("chs-newprofile-complex"));
            }
            typeField.setReadOnly(true);
            layout.replaceComponent(layout.getComponent(0, 2), profile);
        }
        return layout;
    }

    private Layout getReadExistingTab() {
        GridLayout layout = new GridLayout(3, 3);
        layout.setSizeFull();
        layout.setMargin(true);

        Label tf1 = new Label(bundle.getString("chs-cavdef-accordion-readexisting-combo"));
        layout.addComponent(tf1, 0, 0);
        layout.setComponentAlignment(tf1, Alignment.MIDDLE_RIGHT);

        final ComboBox combo = new ComboBox();
        BeanItemContainer<EdsSupply> container = new BeanItemContainer<EdsSupply>(EdsSupply.class);
        combo.setContainerDataSource(container);
        combo.setItemCaptionPropertyId("sedsSupplyName");
        combo.setImmediate(true);

        List<EdsSupply> supplies = new ArrayList<EdsSupply>();

        supplies.addAll(EDSdao.getInstance().getAllEdsSuppliesByEdsId(eds.getEdsId()));
        for (EdsSupply supply : supplies) {
            combo.addItem(supply);

        }

        layout.addComponent(combo, 1, 0);
        layout.setComponentAlignment(combo, Alignment.MIDDLE_LEFT);

        Button button = new Button(bundle.getString("chs-cavdef-accordion-validate"));
        button.addListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                clearGeneratedCell();
                selectedItem.setSupply((EdsSupply) combo.getValue());
                Table table;
                for (String key : tables.keySet()) {
                    table = tables.get(key);
                    table.refreshRowCache();
                }
                accordion.addStyleName("hideAccordion");
            }

        });
        layout.addComponent(button, 2, 2);
        layout.setComponentAlignment(button, Alignment.BOTTOM_RIGHT);
        return layout;
    }

    private Layout getAssociateIFTab() {
        final GridLayout layout = new GridLayout(3, 4);
        layout.setSizeFull();
        layout.setMargin(true);
        Label tf1 = new Label(bundle.getString("chs-cavdef-wi-choice"));

        layout.addComponent(tf1, 0, 0);
        layout.setComponentAlignment(tf1, Alignment.MIDDLE_RIGHT);

        final ComboBox combo = new ComboBox();
        combo.setImmediate(true);
        BeanItemContainer<WiredInterface> container = new BeanItemContainer<WiredInterface>(WiredInterface.class);
        combo.setItemCaptionPropertyId("ID_2");

        combo.setContainerDataSource(container);
        container.addAll(WIlist);
        combo.addListener(new ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                layout.removeComponent(0, 1);
                layout.removeComponent(0, 2);
                if (combo.getValue() != null) {
                    WITableOfPointsReadView points = new WITableOfPointsReadView(controller, (WiredInterface) combo.getValue());
                    points.setSizeFull();
                    layout.addComponent(points, 0, 2, 2, 2);
                    Label tf2 = new Label(bundle.getString("chs-cavdef-wi-details"));
                    layout.addComponent(tf2, 0, 1, 2, 1);
                    layout.setComponentAlignment(tf2, Alignment.MIDDLE_LEFT);
                }

            }

        });
        layout.addComponent(combo, 1, 0);
        layout.setComponentAlignment(combo, Alignment.MIDDLE_LEFT);

        Button button = new Button(bundle.getString("chs-cavdef-accordion-validate"));
        button.addListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                clearGeneratedCell();
                WiredInterface wi = (WiredInterface) combo.getValue();
                selectedItem.setWiredInterface(wi.getID_2());
                selectedItem.setIfImpedance(wi.getProperties().getValueDouble("impedance", true));
                Set<EdsWIProperty> properties = getPropertiesWI(wi);
                selectedItem.setWiProperty(properties);
                Table table;
                for (String key : tables.keySet()) {
                    table = tables.get(key);
                    table.refreshRowCache();
                }
                accordion.addStyleName("hideAccordion");
            }

        });

        layout.addComponent(button, 0, 3, 2, 3);
        layout.setComponentAlignment(button, Alignment.BOTTOM_RIGHT);

        return layout;
    }

    private Table buildTable(String type, final Layout pinDetails) {
        Table table;
        List<EdsPinConnect> pins = pinMap.get(type);

        table = new Table();
        table.addGeneratedColumn("chosenElement", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                final EdsPinConnect item = (EdsPinConnect) itemId;
                HorizontalLayout layout = new HorizontalLayout();
                NativeButton edit = new NativeButton();
                layout.setSizeFull();

                Label title = new Label();
                title.setSizeFull();
                String caption = "-";
                if (item.isCrossed()) {
                    caption = bundle.getString("chs-cavdef-crossed");
                    edit.setEnabled(false);
                } else {
                    if (item.getSupply() != null) {
                        caption = item.getSupply().getSedsSupplyName();

                    } else if (item.getWiredInterface() != null) {
                        caption = item.getWiredInterface();
                    } else if (item.getSimpleProfile() != null) {
                        caption = item.getSimpleProfile();
                    } else if (item.getComplexProfile() != null) {
                        caption = item.getComplexProfile();
                    }

                    edit.setStyleName("edit-field");
                    edit.setWidth("16px");
                    edit.setHeight("16px");
                    edit.addListener(new ClickListener() {

                        @Override
                        public void buttonClick(ClickEvent event) {
                            pinDetails.removeAllComponents();
                            pinDetails.addComponent(accordion);
                            selectedItem = item;
                            combo.select(combo.getNullSelectionItemId());
                            accordion.addStyleName("hideAccordion");
                            if (selectedItem != null) {
                                accordion.getTab(0).setCaption("Cavity : " + selectedItem.getCavity());
                                accordion.removeStyleName("hideAccordion");
                                detailsTab.removeAllComponents();
                                if (selectedItem.getComplexProfile() != null || selectedItem.getSimpleProfile() != null) {
                                    detailsTab.addComponent(getNewProfileTab(false));
                                }

                            }

                        }

                    });

                }
                title.setCaption(caption);
                layout.addComponent(title);
                layout.addComponent(edit);
                layout.setComponentAlignment(edit, Alignment.MIDDLE_RIGHT);
                layout.setComponentAlignment(title, Alignment.MIDDLE_CENTER);
                layout.setExpandRatio(title, 1.0f);
                return layout;
            }
        });

        table.setSizeFull();
        tables.put(type, table);

        table = tables.get(type);
        table.removeAllItems();
        BeanItemContainer<EdsPinConnect> container = new BeanItemContainer<EdsPinConnect>(EdsPinConnect.class);
        containerMap.put(type, container);
        table.setTableFieldFactory(new DefaultFieldFactory() {

            @Override
            public Field createField(Container container, Object itemId, Object propertyId, Component uiContext) {
                Class<?> cls = container.getType(propertyId);
                if (propertyId.equals("cavity")) {
                    TextField cav = new TextField();
                    cav.setReadOnly(true);
                    return cav;
                }
                if (propertyId.equals("minSection")) {
                    ComboBox combo = new ComboBox();
                    for (String c : COlist) {
                        combo.addItem(c);
                    }
                    return combo;

                }
                if (cls.equals(String.class) || cls.equals(Double.class)) {
                    TextField tf = new TextField();
                    tf.setNullRepresentation("");
                    return tf;
                }

                return super.createField(container, itemId, propertyId, uiContext);
            }
        });
        table.setContainerDataSource(container);
        table.setEditable(true);
        List<String> visibleColumns = new ArrayList<String>();
        List<String> headerVisibleColumns = new ArrayList<String>();
        visibleColumns.add("cavity");
        headerVisibleColumns.add(bundle.getString("chs-cavdef-pin-cavity"));
        visibleColumns.add("chosenElement");
        headerVisibleColumns.add(bundle.getString("chs-cavdef-pin-element"));

        if (rMapTypes.contains(type)) {
            visibleColumns.add("rMax");
            headerVisibleColumns.add(bundle.getString("chs-cavdef-pin-rmax"));
        }
        boolean typeOUT = type.equals("OUT");
        if (typeOUT) {
            visibleColumns.add("minSection");
            headerVisibleColumns.add(bundle.getString("chs-cavdef-pin-msection"));
        }
        visibleColumns.add("message");
        headerVisibleColumns.add(bundle.getString("chs-cavdef-pin-comment"));
        table.setVisibleColumns(visibleColumns.toArray());
        table.setColumnHeaders(headerVisibleColumns.toArray(new String[headerVisibleColumns.size()]));

        if (pins != null) {
            container.addAll(pins);
        }

        table.setHeight("350px");
        return table;
    }

    private Set<EdsWIProperty> getPropertiesWI(WiredInterface wi) {
        Set<EdsWIProperty> properties = new HashSet<EdsWIProperty>();
        List<Profil> profils = new ArrayList<Profil>(wi.getAll());
        Collections.sort(profils, new Comparator<Profil>() {
            @Override
            public int compare(Profil o1, Profil o2) {
                return o1.getID_2().compareTo(o2.getID_2());
            }

        });
        EdsWIProperty item;
        for (Profil profil : profils) {
            item = new EdsWIProperty();
            Double current = profil.getProperties().getValueDouble("current", true);
            if (current != null) {
                item.setTime(profil.getID_2());
                item.setCurrent(current);
                item.setWi(selectedItem);
                properties.add(item);
            }
        }
        return properties;
    }

    private void clearGeneratedCell() {
        if (selectedItem.getSpProperty() != null) {
            selectedItem.getSpProperty().clear();
        }
        selectedItem.setSimpleProfile(null);
        selectedItem.setSupply(null);
        if (selectedItem.getWiProperty() != null) {
            selectedItem.getWiProperty().clear();
        }
        selectedItem.setWiredInterface(null);

        if (selectedItem.getCpProfile() != null) {
            selectedItem.getCpProfile().clear();
        }
        if (selectedItem.getCpProperty() != null) {
            selectedItem.getCpProperty().clear();
        }
        selectedItem.setComplexProfile(null);
        selectedItem.setIfImpedance(null);

    }

    private Layout getComplexLayout(final TextField nameField, final boolean newItem) {
        VerticalLayout complexProfile = new VerticalLayout();
        final ComplexProfileTableOfPointsEditView view = new ComplexProfileTableOfPointsEditView(controller, selectedItem, newItem);

        complexProfile.addComponent(view);
        Button button = new Button(bundle.getString("chs-cavdef-accordion-validate"));
        button.addListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {

                if (isValid(nameField, view.isValid())) {
                    selectedItem.setComplexProfile((String) nameField.getValue());
                    selectedItem.setComplexComment(view.getComment());
                    selectedItem.setCpProfile(view.getProfile());
                    selectedItem.setCpProperty(view.getProperties());
                    accordion.addStyleName("hideAccordion");
                    Table table;
                    for (String key : tables.keySet()) {
                        table = tables.get(key);
                        table.refreshRowCache();
                    }
                } else {
                    getWindow().showNotification(bundle.getString("chs-cavdef-error"));
                }
            }

        });
        complexProfile.addComponent(button);
        complexProfile.setComponentAlignment(button, Alignment.MIDDLE_RIGHT);
        return complexProfile;
    }

    private Layout getSimpleLayout(final TextField nameField, final boolean newItem) {
        VerticalLayout simpleProfile = new VerticalLayout();
        simpleProfile.setSizeFull();
        simpleProfile.setMargin(true);
        simpleProfile.setSpacing(true);
        modifyList = new ArrayList<EdsSPProperty>();
        for (EdsSPProperty item : selectedItem.getSpProperty()) {
            if (item != null) {
                EdsSPProperty clone = new EdsSPProperty();
                clone.setType(item.getType());
                clone.setUser(item.getUser());
                clone.setValue(item.getValue());
                clone.setComment(item.getComment());
                modifyList.add(clone);
            }

        }
        final SimpleProfileEditView view = new SimpleProfileEditView(controller, selectedItem, newItem);

        simpleProfile.addComponent(view);

        Button button = new Button(bundle.getString("chs-cavdef-accordion-validate"));
        button.addListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {

                if (isValid(nameField, view.isValid())) {
                    List<EdsSPProperty> list = view.getProperties();
                    Iterator<EdsSPProperty> it = list.iterator();
                    EdsSPProperty item;

                    while (it.hasNext()) {
                        item = it.next();
                        checkUpdatedSPUser(item);
                    }
                    clearGeneratedCell();
                    selectedItem.setSimpleProfile((String) nameField.getValue());
                    selectedItem.setSpProperty(list);
                    accordion.addStyleName("hideAccordion");
                    Table table;
                    for (String key : tables.keySet()) {
                        table = tables.get(key);
                        table.refreshRowCache();
                    }

                } else {

                    getWindow().showNotification(bundle.getString("chs-cavdef-error"));
                }
            }

        });
        simpleProfile.addComponent(button);
        simpleProfile.setComponentAlignment(button, Alignment.MIDDLE_RIGHT);
        return simpleProfile;
    }

    private void checkUpdatedSPUser(EdsSPProperty newItem) {
        if (newItem != null) {
            if ((newItem.getValue() != null || newItem.getComment() != null) && newItem.getUser() == null) {
                newItem.setUser(username);
            } else {
                for (EdsSPProperty item : modifyList) {

                    if (item != null && item.getType() == newItem.getType()) {
                        if ((newItem.getValue() != null && !newItem.getValue().equals(item.getValue()))
                                || (newItem.getComment() != null && !newItem.getComment().equals(item.getComment())))

                            newItem.setUser(username);
                    }
                }
            }
        }
    }

    private boolean isValid(TextField nameField, boolean viewValid) {
        String profileName = (String) nameField.getValue();
        nameField.removeStyleName("validate-chs-error");
        if (profileName == null || profileName.isEmpty()) {
            nameField.addStyleName("validate-chs-error");
            return false;
        }
        return viewValid;
    }

}
