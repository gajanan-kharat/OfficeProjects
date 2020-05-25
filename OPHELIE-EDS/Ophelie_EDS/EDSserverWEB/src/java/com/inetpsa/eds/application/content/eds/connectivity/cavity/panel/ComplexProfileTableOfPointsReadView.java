package com.inetpsa.eds.application.content.eds.connectivity.cavity.panel;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.jfree.data.xy.XYSeries;
import org.vaadin.addon.JFreeChartWrapper;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.activationprofile.JFreeChartBuilder;
import com.inetpsa.eds.application.content.eds.connectivity.cavity.util.ProfileType;
import com.inetpsa.eds.dao.model.EdsCPProfile;
import com.inetpsa.eds.dao.model.EdsCPProperty;
import com.inetpsa.eds.dao.model.EdsPinConnect;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.CellStyleGenerator;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * This class defines a Complex Profile Table of Points panel for editing.
 * 
 * @author jcosta @ Alter Frame
 */
public class ComplexProfileTableOfPointsReadView extends VerticalLayout {

    private static final long serialVersionUID = 5053781617093670213L;

    /** Variable to hold value of Controller of EDS application. */
    private EdsApplicationController controller;

    /** Point table **/
    private Table vTBxyDataValues;

    /** Resources **/
    private ResourceBundle bundle;

    /** Selected Pin **/
    private EdsPinConnect selectedItem;

    /** Constant variable to store 'time'. **/
    private static final String TIME_ID = "type";

    /** Constant variable to store 'intensity'. **/
    private static final String CURRENT_ID = "value";

    private BeanItemContainer<EdsCPProperty> containerProperty;

    private BeanItemContainer<EdsCPProfile> containerProfile;

    private TextArea commentArea;

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public ComplexProfileTableOfPointsReadView(final EdsApplicationController controller, final EdsPinConnect selected) {
        this.controller = controller;
        this.bundle = controller.getBundle();
        this.selectedItem = selected;
        init();
    }

    /**
     * Initialize the panel components with data, or empty if no profile exists.
     */
    private void init() {

        setWidth(100, UNITS_PERCENTAGE);
        setCaption(controller.getBundle().getString("profile-table-of-points"));

        HorizontalLayout customProfilView = new HorizontalLayout();
        customProfilView.setMargin(true);
        customProfilView.setSpacing(true);

        vTBxyDataValues = new Table();
        containerProfile = new BeanItemContainer<EdsCPProfile>(EdsCPProfile.class);
        vTBxyDataValues.setContainerDataSource(containerProfile);
        vTBxyDataValues.setVisibleColumns(new Object[] { TIME_ID, CURRENT_ID });
        vTBxyDataValues.setColumnHeaders(new String[] { controller.getBundle().getString("eds-time-label"),
                controller.getBundle().getString("eds-courant-label") });
        vTBxyDataValues.setEditable(true);
        vTBxyDataValues.setSelectable(true);
        vTBxyDataValues.setMultiSelect(true);
        vTBxyDataValues.setImmediate(true);

        vTBxyDataValues.setTableFieldFactory(new DefaultFieldFactory() {

            @Override
            public Field createField(Container container, Object itemId, Object propertyId, Component uiContext) {
                Class<?> cls = container.getType(propertyId);
                if (cls.equals(Double.class)) {
                    TextField tf = new TextField();
                    tf.setNullRepresentation("");
                    tf.setReadOnly(true);
                    return tf;
                }

                return super.createField(container, itemId, propertyId, uiContext);
            }
        });
        containerProfile.addAll(selectedItem.getCpProfile());

        VerticalLayout tableLayout = new VerticalLayout();
        tableLayout.setSpacing(true);
        tableLayout.addComponent(vTBxyDataValues);

        customProfilView.addComponent(tableLayout);

        VerticalLayout contentLayout = new VerticalLayout();
        HorizontalLayout subLayout = new HorizontalLayout();
        subLayout.setMargin(true);
        subLayout.setSpacing(true);

        XYSeries serie = new XYSeries(controller.getBundle().getString("Activ-profil-courant-title"), false);
        Double current;
        List<EdsCPProfile> properties = selectedItem.getCpProfile();
        Iterator<EdsCPProfile> it = properties.iterator();
        EdsCPProfile profile;
        // TODO: null may happen.
        while (it.hasNext()) {
            profile = it.next();
            if (profile == null || profile.getType() == null) {
                it.remove();
            }
        }
        Collections.sort(properties, new Comparator<EdsCPProfile>() {
            @Override
            public int compare(EdsCPProfile o1, EdsCPProfile o2) {
                return Double.compare(o1.getType(), o2.getType());
            }

        });

        for (EdsCPProfile property : properties) {

            current = property.getValue();
            if (current != null) {
                serie.add(Double.valueOf(current), Double.valueOf(property.getType()));
            }
        }

        subLayout.addComponent(new JFreeChartWrapper(JFreeChartBuilder.buildCustomNoLegendCurrentProfile(serie, controller)));
        contentLayout.addComponent(subLayout);
        customProfilView.addComponent(contentLayout);

        commentArea = new TextArea(bundle.getString("chs-cavdef-pintype-comment"));
        commentArea.setNullRepresentation("");
        commentArea.setWidth("400px");
        commentArea.setValue(selectedItem.getComplexComment());
        commentArea.setReadOnly(true);

        tableLayout.setSizeUndefined();
        this.setSizeFull();

        this.addComponent(getInomVmin());
        this.addComponent(customProfilView);
        this.addComponent(commentArea);
    }

    private Table getInomVmin() {
        Table table = new Table();
        containerProperty = new BeanItemContainer<EdsCPProperty>(EdsCPProperty.class);
        table.setContainerDataSource(containerProperty);
        table.setPageLength(0);
        table.setSortDisabled(true);
        table.setCellStyleGenerator(new CellStyleGenerator() {

            @Override
            public String getStyle(Object itemId, Object propertyId) {
                if (propertyId == null) {
                    return "rowheader";
                }

                // int row = ((Integer) itemId).intValue();
                // if (row == 0) {
                // return "rowheader";
                // }
                // int col = Integer.parseInt((String) propertyId);
                String property = (String) propertyId;

                for (int x = 1; x < 5; x++) {
                    if (Integer.toString(x).equals(property)) {
                        return "rowheader";
                    }
                }

                return "white";
            }
        });

        table.setTableFieldFactory(new DefaultFieldFactory() {
            @Override
            public Field createField(Container container, Object itemId, Object propertyId, Component uiContext) {
                Class<?> cls = container.getType(propertyId);

                if (cls.equals(String.class) || cls.equals(Double.class)) {
                    TextField cav = new TextField();
                    cav.setNullRepresentation("");
                    cav.setReadOnly(true);

                    return cav;
                }
                return super.createField(container, itemId, propertyId, uiContext);
            }
        });

        table.addGeneratedColumn("typeName", new ColumnGenerator() {

            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                EdsCPProperty item = (EdsCPProperty) itemId;
                String name = ProfileType.getProfileFromType(item.getType()).getName();
                return bundle.getString(name);
            }

        });
        table.setEditable(true);
        table.setVisibleColumns(new Object[] { "typeName", "value", "comment" });
        table.setColumnHeaders(new String[] { "", bundle.getString("chs-newprofile-value"), bundle.getString("chs-newprofile-comment") });
        containerProperty.addAll(selectedItem.getCpProperty());
        table.setWidth("50%");
        table.setColumnExpandRatio(0, 1.0f);
        table.setColumnExpandRatio(1, 2.0f);
        table.setColumnExpandRatio(2, 4.0f);

        return table;

    }

    private EdsCPProperty getCPProperty(ProfileType profile) {
        EdsCPProperty item = new EdsCPProperty();
        item.setWi(selectedItem);
        item.setType(profile.getIndex());
        return item;
    }

}
