package com.inetpsa.eds.application.content.eds.connectivity.cavity.panel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.connectivity.cavity.util.ProfileType;
import com.inetpsa.eds.application.content.eds.connectivity.cavity.validator.TFDoubleValidator;
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
public class SimpleProfileEditView extends VerticalLayout {

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

    private boolean newItem;
    private boolean initialization;

    private List<TextField> fields;

    private Table table;

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public SimpleProfileEditView(final EdsApplicationController controller, final EdsPinConnect selected, final boolean newItem) {
        this.controller = controller;
        this.bundle = controller.getBundle();
        this.selectedItem = selected;
        this.newItem = newItem;
        EdsUser user = controller.getAuthenticatedUser();
        fields = new ArrayList<TextField>();
        username = user.getUFirstname() + " " + user.getULastname() + " " + user.getUPsaId();
        init();
    }

    /**
     * Initialize the panel components with data, or empty if no profile exists.
     */
    private void init() {
        initialization = true;
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

                return "white";
            }
        });

        table.setTableFieldFactory(new DefaultFieldFactory() {
            @Override
            public Field createField(Container container, final Object itemId, Object propertyId, Component uiContext) {
                Class<?> cls = container.getType(propertyId);

                if (cls.equals(String.class) || cls.equals(Double.class)) {
                    TextField cav = new TextField();
                    cav.setNullRepresentation("");
                    cav.setImmediate(true);

                    if (propertyId.equals("user")) {
                        cav.setReadOnly(true);
                    }
                    if (cls.equals(Double.class)) {
                        cav.addValidator(new TFDoubleValidator(false));
                        fields.add(cav);
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
        table.setEditable(true);
        table.setVisibleColumns(new Object[] { "typeName", "value", "user", "comment" });
        table.setColumnHeaders(new String[] { bundle.getString("chs-newprofile-type"), bundle.getString("chs-newprofile-value"),
                bundle.getString("chs-newprofile-user"), bundle.getString("chs-newprofile-comment") });

        if (newItem) {
            EdsSPProperty item;
            for (int x = 0; x < 4; x++) {
                item = new EdsSPProperty();
                item.setWi(selectedItem);
                item.setType(x);
                container.addItem(item);
            }
        } else {
            container.addAll(selectedItem.getSpProperty());
        }

        this.addComponent(table);
        initialization = false;
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
        boolean valid = true;

        for (TextField field : fields) {
            field.removeStyleName("validate-chs-error");
            if (!field.isValid()) {
                valid = false;
                field.addStyleName("validate-chs-error");
            }
        }

        return valid;
    }
}
