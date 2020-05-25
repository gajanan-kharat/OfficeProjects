package com.inetpsa.eds.application.content.home;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.uri.FragmentManager;
import com.inetpsa.eds.dao.model.EdsComponentType;
import com.inetpsa.eds.dao.model.EdsEds;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import java.util.Collection;

/**
 * Lighter version of EdsTable used to display the home page tables.
 * 
 * @author Geometric Ltd.
 * @see EdsTable
 */
public class EdsShortTable extends Table {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public EdsShortTable(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /**
     * This method is used to set all the EDS to the container.
     * 
     * @param edses collection of EDS.
     */
    public void setEdsList(Collection<EdsEds> edses) {
        container.removeAllItems();
        container.addAll(edses);
    }

    // PROTECTED

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.Table#formatPropertyValue(java.lang.Object, java.lang.Object, com.vaadin.data.Property)
     */
    @Override
    protected String formatPropertyValue(Object rowId, Object colId, Property property) {
        if (property.getValue() == null) {
            return "-";
        }
        if (property.getType().equals(EdsComponentType.class)) {
            return ((EdsComponentType) property.getValue()).getLocaleCtName(controller.getApplication().getLocale());
        }

        return super.formatPropertyValue(rowId, colId, property);
    }

    // PRIVATE
    /**
     * Variable to store the EDS application controller object.
     */
    private EdsApplicationController controller;
    /**
     * Bean item container to display the list of EDS.
     */
    private BeanItemContainer container;

    /**
     * Initialization method.
     */
    private void init() {
        // General settings
        this.setSelectable(false);

        // Model settings
        this.container = new BeanItemContainer(EdsEds.class);
        this.setContainerDataSource(container);
        this.setVisibleColumns(new Object[] { "edsRef", "edsName", "edsComponentType" });
        this.setColumnHeaders(new String[] { controller.getBundle().getString("hist-ref"), controller.getBundle().getString("Admin-user-name"),
                controller.getBundle().getString("menu-project-tab-model") });

        // Renderers settings
        this.addGeneratedColumn("edsRef", new Table.ColumnGenerator() {
            public Object generateCell(Table source, Object itemId, Object columnId) {
                Property prop = source.getItem(itemId).getItemProperty(columnId);
                if (prop.getValue() != null) {
                    return new Link(((EdsEds) itemId).toImplicitRef(), new ExternalResource("#"
                            + FragmentManager.formatURLFragmentForEDS((EdsEds) itemId)));
                }
                return null;
            }
        });
    }
}
