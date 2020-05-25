package com.inetpsa.eds.application.content.eds.composants;

import com.vaadin.ui.Table;

/**
 * This class provide MyTable Component
 * 
 * @author Geometric Ltd.
 */
public class MyTable extends Table {
    // PUBLIC
    /**
     * Default Constructor
     */
    public MyTable() {
        init();
    }

    /**
     * Parameterized Constructor
     * 
     * @param caption Caption of Table
     */
    public MyTable(String caption) {

        super(caption);
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize MyTable component
     */
    private void init() {
        setWidth("100%");
        setSortDisabled(true);
    }

    /**
     * This method add property to table and show it as a visible column
     * 
     * @param propertyId Unique property identifier
     * @param type class of property
     * @param columnHeader Column header
     * @return check If property is added
     * @throws UnsupportedOperationException if the operation is not supported.
     */
    public boolean addContainer(Object propertyId, Class<?> type, String columnHeader) throws UnsupportedOperationException {
        return super.addContainerProperty(propertyId, type, null, columnHeader, null, null);

    }
}
