package com.inetpsa.eds.application.content.eds.connectivity.cavity.panel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.connectivity.cavity.util.ProfileType;
import com.inetpsa.eds.dao.model.EdsPinConnect;
import com.inetpsa.eds.dao.model.EdsSPProperty;
import com.inetpsa.eds.dao.model.EdsUser;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.CellStyleGenerator;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * This class defines a Simple Profile Table of Points panel for editing.
 * 
 * @author jcosta @ Alter Frame
 */
public class SimpleProfileReadView extends VerticalLayout {

    private static final long serialVersionUID = 5053781617093670213L;

    /** Variable to hold value of Controller of EDS application. */
    private EdsApplicationController controller;

    /** Resources **/
    private ResourceBundle bundle;

    /** Selected Pin **/
    private EdsPinConnect selectedItem;

    /** Constant variable to store 'time'. **/
    private static final String TIME_ID = "type";

    /** Constant variable to store 'intensity'. **/
    private static final String CURRENT_ID = "value";

    private BeanItemContainer<EdsSPProperty> container;

    private TextArea commentArea;

    private final String username;

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public SimpleProfileReadView(final EdsApplicationController controller, final EdsPinConnect selected) {
        this.controller = controller;
        this.bundle = controller.getBundle();
        this.selectedItem = selected;
        EdsUser user = controller.getAuthenticatedUser();
        username = user.getUFirstname() + " " + user.getULastname() + " " + user.getUPsaId();
        init();
    }

    /**
     * Initialize the panel components with data, or empty if no profile exists.
     */
    private void init() {
        this.setSizeFull();
        Table table = new Table();
        container = new BeanItemContainer<EdsSPProperty>(EdsSPProperty.class);
        table.setContainerDataSource(container);
        table.setSizeFull();
        table.setPageLength(0);
        table.setSortDisabled(true);
        table.setCellStyleGenerator(new CellStyleGenerator() {

            @Override
            public String getStyle(Object itemId, Object propertyId) {
                if (propertyId == null) {
                    return "rowheader";
                }
                String property = (String) propertyId;

                // for (int x = 1; x < 5; x++) {
                // if (ProfileType..Integer.toString(x).equals(property)) {
                // return "rowheader";
                // }
                // }

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
                    if (propertyId.equals("user")) {
                        cav.setReadOnly(true);
                    }
                    return cav;
                }
                return super.createField(container, itemId, propertyId, uiContext);
            }
        });

        table.addGeneratedColumn("typeName", new ColumnGenerator() {

            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                EdsSPProperty item = (EdsSPProperty) itemId;
                String name = ProfileType.getProfileFromType(item.getType()).getName();
                return bundle.getString(name);
            }

        });
        table.setEditable(false);
        table.setVisibleColumns(new Object[] { "typeName", "value", "user", "comment" });
        table.setColumnHeaders(new String[] { bundle.getString("chs-newprofile-type"), bundle.getString("chs-newprofile-value"),
                bundle.getString("chs-newprofile-user"), bundle.getString("chs-newprofile-comment") });
        List<EdsSPProperty> list = selectedItem.getSpProperty();
        container.addAll(list);
        this.addComponent(table);
    }

    public List<EdsSPProperty> getProperties() {
        Collection<EdsSPProperty> items = container.getItemIds();
        List<EdsSPProperty> list = new ArrayList<EdsSPProperty>(items);
        return list;
    }

    /**
     * This method checks if the mandatory fields have data.
     * 
     * @return boolean true if all mandatory fields have data, false otherwise.
     */
    public boolean isValid() {
        return true;
    }
}
