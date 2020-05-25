/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inetpsa.eds.application.content.eds.cse;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsCseLine;
import com.inetpsa.eds.tools.EDSTools;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * This class is an array of pre-formatted (Vaadin component) for display CSE lines. All "renderer" fields of a particular type (non-primitive or not
 * supported by Vaadin components) are Entered in this table.
 * 
 * @author Geometric Ltd.
 */
public class CseTable extends Table {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller controller of EDS application
     */
    public CseTable(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /**
     * Method returns Controller of EDS application
     * 
     * @return Controller of EDS application
     */
    public EdsApplicationController getController() {
        return controller;
    }

    /**
     * Clears any data that already exist in the table and populates it with data from the parameter list.
     * 
     * @param cseLines data to display in the table.
     */
    public void setCseList(Collection<EdsCseLine> cseLines) {
        container.removeAllItems();
        TreeSet<EdsCseLine> sortedSet = new TreeSet<EdsCseLine>(new Comparator<EdsCseLine>() {
            public int compare(EdsCseLine o1, EdsCseLine o2) {
                return o1.getCselNumber() - o2.getCselNumber();
            }
        });
        sortedSet.addAll(cseLines);
        container.addAll(sortedSet);
        this.setSortContainerPropertyId("cselNumber");
        this.setSortAscending(true);
    }

    /**
     * Change the display string representation for an empty field.
     * 
     * @param nullRepresentation string representing the empty fields.
     */
    public void setNullPropertyRepresentation(String nullRepresentation) {
        this.nullRepresentation = nullRepresentation;
    }

    // PROTECTED/
    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.Table#formatPropertyValue(java.lang.Object, java.lang.Object, com.vaadin.data.Property)
     */
    @Override
    protected String formatPropertyValue(Object rowId, Object colId, Property property) {
        if (property.getValue() == null) {
            return nullRepresentation;
        }

        return super.formatPropertyValue(rowId, colId, property);
    }

    // PRIVATE
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold BeanItemContainer of EdsCseLine
     */
    private BeanItemContainer<EdsCseLine> container;

    /**
     * Variable to hold value of String for nullRepresentaion
     */
    private String nullRepresentation;

    /**
     * Initialize CseTable
     */
    private void init() {
        // General Settings
        this.setNullPropertyRepresentation("--");

        // Model Settings
        this.container = new BeanItemContainer<EdsCseLine>(EdsCseLine.class);
        this.container.removeContainerProperty("edsCseFormData");
        this.setContainerDataSource(container);

        // Renderer Settings
        Table.ColumnGenerator xhtmlColumnGenerator = new Table.ColumnGenerator() {
            public Object generateCell(Table source, Object itemId, Object columnId) {
                Property prop = source.getItem(itemId).getItemProperty(columnId);
                if (prop.getValue() != null) {
                    return new Label(EDSTools.replaceFormatCharsByXHTML(prop.getValue().toString()), Label.CONTENT_XHTML);
                }
                return nullRepresentation;
            }
        };
        this.addGeneratedColumn("cselDataname", xhtmlColumnGenerator);
        this.addGeneratedColumn("cselUnit", xhtmlColumnGenerator);
        this.addGeneratedColumn("cselComment", xhtmlColumnGenerator);

        this.setVisibleColumns(new Object[] { "cselNumber", "cselDataname", "cselLowerBound", "cselUpperBound", "cselUnit", "cselComment" });
        this.setColumnHeaders(new String[] { controller.getBundle().getString("CSE-num"), controller.getBundle().getString("CSE-data"),
                controller.getBundle().getString("CSE-inf"), controller.getBundle().getString("CSE-sup"),
                controller.getBundle().getString("CSE-Unit"), controller.getBundle().getString("eds-comnent") });

        this.setColumnAlignment("cselNumber", Table.ALIGN_CENTER);
        this.setColumnAlignment("cselLowerBound", Table.ALIGN_CENTER);
        this.setColumnAlignment("cselUpperBound", Table.ALIGN_CENTER);
        this.setColumnAlignment("cselUnit", Table.ALIGN_CENTER);
        this.setColumnExpandRatio("cselComment", 1f);
    }
}
