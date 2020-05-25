package com.inetpsa.eds.application.content.home;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.uri.FragmentManager;
import com.inetpsa.eds.dao.model.EdsComponentType;
import com.inetpsa.eds.dao.model.EdsProject;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import java.util.Collection;

/**
 * This class is an array (Vaadin component) pre-formatted for display lists of projects. All "renderer" fields (not original or not supported by
 * Vaadin components) particular type are shown in this table.
 * 
 * @author Geometric Ltd.
 * @see EdsTable
 */
public class ProjectShortTable extends Table {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public ProjectShortTable(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /**
     * This method is used to set the project list.
     * 
     * @param projects collection of project details.
     */
    public void setProjectList(Collection<EdsProject> projects) {
        container.removeAllItems();
        container.addAll(projects);
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
     * Bean item container to display the list of projects.
     */
    private BeanItemContainer container;

    /**
     * Initialization method.
     */
    private void init() {
        // General settings
        this.setSelectable(false);

        // Model settings
        this.container = new BeanItemContainer(EdsProject.class);
        this.setContainerDataSource(container);
        this.setVisibleColumns(new Object[] { "PName" });
        this.setColumnHeaders(new String[] { controller.getBundle().getString("Admin-user-name") });

        // Renderers settings
        this.addGeneratedColumn("PName", new Table.ColumnGenerator() {
            public Object generateCell(Table source, Object itemId, Object columnId) {
                Item item = source.getItem(itemId);
                Property prop = item.getItemProperty(columnId);
                if (prop.getValue() != null) {
                    return new Link((String) prop.getValue(), new ExternalResource("#"
                            + FragmentManager.formatURLFragmentForProject((String) item.getItemProperty("PId").getValue())));
                }
                return null;
            }
        });
    }
}
