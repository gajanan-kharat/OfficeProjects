package com.inetpsa.eds.application.content.eds.connectivity.cavity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.inetpsa.eds.application.ChsController;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.application.content.eds.connectivity.cavity.panel.ComplexProfileTableOfPointsReadView;
import com.inetpsa.eds.application.content.eds.connectivity.cavity.panel.SimpleProfileReadView;
import com.inetpsa.eds.application.content.eds.connectivity.cavity.panel.WITableOfPointsReadView;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsBsx;
import com.inetpsa.eds.dao.model.EdsBsxPin;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsPinConnect;
import com.inetpsa.eds.dao.model.EdsPinTypeComment;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * This class represents 'Cavity Definition' in read view.
 * 
 * @author Joao Costa @ Alter Frame
 */
public class CavityDefFormReadView extends A_EdsReadForm {
    private Map<String, BeanItemContainer<EdsPinConnect>> containerMap;

    private final List<String> rMapTypes = new ArrayList<String>(Arrays.asList(new String[] { "GND", "IN", "OUT", "IxO" }));

    private Map<String, List<EdsPinConnect>> pinMap;

    private Map<String, Table> tables;

    private Map<String, TextArea> typeComments;

    private EdsPinConnect selectedItem;

    private Layout detailsTab;

    private Accordion accordion;

    private TextField tMax;

    private TextField tMin;

    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param eds EDS details.
     * @param controller EDS application controller object.
     */
    public CavityDefFormReadView(EdsApplicationController controller, EdsEds eds) {
        super(controller);
        this.controller = controller;
        this.bundle = controller.getBundle();
        this.eds = eds;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormName()
     */
    @Override
    public String getFormName() {
        return "connectivity-cavitydef-title";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormTitle()
     */
    @Override
    public String getFormTitle() {
        return "connectivity-cavitydef-title";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        return CavityDefFormBuilder.ID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEdsRef()
     */
    @Override
    public String getEdsRef() {
        return eds.getEdsRef();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEds()
     */
    @Override
    public EdsEds getEds() {
        return eds;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshViewData()
     */
    @Override
    public void refreshViewData() {
        initPinMapping(eds.getEdsPinConnect());
        // initPinComments(eds.getEdsPinConnect());
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

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        eds = EDSdao.getInstance().getEdsByRef(eds.getEdsRef());

    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;

    /**
     * EDS details.
     */
    private EdsEds eds;

    /**
     * Resource bundle to read configuration file.
     */
    private ResourceBundle bundle;

    // record info

    /**
     * Initialization method.
     */
    private void init() {
        tMax = new TextField(bundle.getString("chs-cavdef-pin-tmax"));
        tMax.setNullRepresentation("");
        tMin = new TextField(bundle.getString("chs-cavdef-pin-tmin"));
        tMin.setNullRepresentation("");
        initPinMapping(eds.getEdsPinConnect());
        tables = new HashMap<String, Table>();
        containerMap = new HashMap<String, BeanItemContainer<EdsPinConnect>>();
        TabSheet main = new TabSheet();
        main.addListener(new SelectedTabChangeListener() {
            @Override
            public void selectedTabChange(SelectedTabChangeEvent event) {
                accordion.addStyleName("hideAccordion");
                Table table;
                for (String key : tables.keySet()) {
                    table = tables.get(key);
                    table.select(table.getNullSelectionItemId());
                }
            }
        });
        initTypeComments(eds.getEdsTypeComment());
        initDetails();
        addComponent(main);
        for (String key : ChsController.pinTypes) {
            main.addTab(initTab(key)).setCaption(key);
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
            commentArea.setReadOnly(false);
            commentArea.setValue(i.getMessage());
            commentArea.setReadOnly(true);
            if (i.getType().equals("OUT")) {
                tMax.setReadOnly(false);
                tMin.setReadOnly(false);
                tMax.setValue(i.gettMax());
                tMin.setValue(i.gettMin());
                tMax.setReadOnly(true);
                tMin.setReadOnly(true);
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

    private Accordion initDetails() {
        accordion = new Accordion();
        accordion.addStyleName("hideAccordion");
        accordion.addTab(getDetailsTab());
        return accordion;
    }

    private Layout getDetailsTab() {
        final GridLayout layout = new GridLayout(3, 2);
        detailsTab = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        layout.addComponent(detailsTab, 0, 0, 2, 0);
        return layout;
    }

    private Layout getNewProfileTab() {
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

        layout.addComponent(typeField, 1, 1);
        layout.addComponent(panel, 0, 2, 2, 2);
        if (selectedItem.getSimpleProfile() != null) {
            VerticalLayout simpleProfile = new VerticalLayout();
            simpleProfile.setSizeFull();
            simpleProfile.setMargin(true);
            simpleProfile.setSpacing(true);
            final SimpleProfileReadView view = new SimpleProfileReadView(controller, selectedItem);
            simpleProfile.addComponent(view);
            layout.replaceComponent(layout.getComponent(0, 2), simpleProfile);
            typeField.select(bundle.getString("chs-newprofile-simple"));
            nameField.setValue(selectedItem.getSimpleProfile());
        } else if (selectedItem.getComplexProfile() != null) {
            VerticalLayout complexProfile = new VerticalLayout();
            final ComplexProfileTableOfPointsReadView view = new ComplexProfileTableOfPointsReadView(controller, selectedItem);
            complexProfile.addComponent(view);
            layout.replaceComponent(layout.getComponent(0, 2), complexProfile);
            typeField.select(bundle.getString("chs-newprofile-complex"));
            nameField.setValue(selectedItem.getComplexProfile());
        }
        typeField.setReadOnly(true);
        nameField.setReadOnly(true);
        return layout;
    }

    private Layout getAssociateIFTab() {
        final GridLayout layout = new GridLayout(3, 4);
        layout.setSizeFull();
        layout.setMargin(true);
        Label tf1 = new Label(bundle.getString("chs-cavdef-wi-choice"));

        layout.addComponent(tf1, 0, 0);
        layout.setComponentAlignment(tf1, Alignment.MIDDLE_RIGHT);

        WITableOfPointsReadView points = new WITableOfPointsReadView(controller, selectedItem.getWiProperty());
        layout.addComponent(points, 0, 2, 2, 2);
        Label tf2 = new Label(bundle.getString("chs-cavdef-wi-details"));
        layout.addComponent(tf2, 0, 1, 2, 1);
        layout.setComponentAlignment(tf2, Alignment.MIDDLE_LEFT);
        Label tf3 = new Label(selectedItem.getWiredInterface());
        layout.addComponent(tf3, 1, 0);
        layout.setComponentAlignment(tf3, Alignment.MIDDLE_LEFT);

        return layout;
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

    private Table buildTable(String type, final Layout pinDetails) {
        final Table table = new Table();
        List<EdsPinConnect> pins = pinMap.get(type);

        table.setSelectable(true);
        table.addGeneratedColumn("chosenElement", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(Table source, final Object itemId, Object columnId) {
                final EdsPinConnect item = (EdsPinConnect) itemId;
                HorizontalLayout layout = new HorizontalLayout();
                layout.setSizeFull();

                Label title = new Label();
                title.setSizeFull();
                String caption = "-";
                if (item.isCrossed()) {
                    caption = bundle.getString("chs-cavdef-crossed");
                } else if (item.getSupply() != null) {
                    caption = item.getSupply().getSedsSupplyName();
                } else if (item.getWiredInterface() != null) {
                    caption = item.getWiredInterface();
                } else if (item.getSimpleProfile() != null) {
                    caption = item.getSimpleProfile();
                } else if (item.getComplexProfile() != null) {
                    caption = item.getComplexProfile();
                }

                title.setCaption(caption);
                layout.addComponent(title);
                layout.addListener(new LayoutClickListener() {
                    @Override
                    public void layoutClick(LayoutClickEvent event) {
                        table.select(itemId);
                    }
                });
                layout.setComponentAlignment(title, Alignment.MIDDLE_CENTER);
                layout.setExpandRatio(title, 1.0f);
                return layout;
            }
        });

        table.setSizeFull();
        tables.put(type, table);

        table.removeAllItems();
        BeanItemContainer<EdsPinConnect> container = new BeanItemContainer<EdsPinConnect>(EdsPinConnect.class);
        containerMap.put(type, container);
        table.setImmediate(true);
        table.addListener(new ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                EdsPinConnect item = (EdsPinConnect) table.getValue();
                pinDetails.removeAllComponents();
                pinDetails.addComponent(accordion);
                selectedItem = item;
                accordion.addStyleName("hideAccordion");
                if (selectedItem != null) {
                    accordion.getTab(0).setCaption("Cavity : " + selectedItem.getCavity());
                    accordion.removeStyleName("hideAccordion");
                    detailsTab.removeAllComponents();
                    if (selectedItem.getWiredInterface() != null) {
                        detailsTab.addComponent(getAssociateIFTab());
                    } else if (selectedItem.getSimpleProfile() != null) {
                        detailsTab.addComponent(getNewProfileTab());
                    } else if (selectedItem.getComplexProfile() != null) {
                        detailsTab.addComponent(getNewProfileTab());
                    } else {
                        accordion.addStyleName("hideAccordion");
                    }

                }

            }

        });
        table.setContainerDataSource(container);

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

}
