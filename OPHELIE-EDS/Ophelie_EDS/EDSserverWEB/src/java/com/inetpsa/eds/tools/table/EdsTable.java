package com.inetpsa.eds.tools.table;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsComponentType;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsNumber96k;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.dao.model.EdsProjectEds;
import com.inetpsa.eds.dao.model.EdsSupplier;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.tools.uri.FragmentManager;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;

/**
 * This class is pre-formatted array (Vaadin component) to display lists of EDS files. All the "renderer" of Fields (non-primitive or not supported by
 * Vaadin components) of particular type are shown in this table.
 * 
 * @author Geometric Ltd.
 */
public class EdsTable extends Table {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of the EDS application.
     */
    public EdsTable(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /**
     * This method returns the controller of EDS application.
     * 
     * @return Controller of EDS application
     */
    public EdsApplicationController getController() {
        return controller;
    }

    /**
     * This method clears any data that already exist in the table and fills it with data from the parameter list.
     * 
     * @param edses Data to display in the table.
     */
    public void setEdsList(Collection<EdsEds> edses) {
        container.removeAllItems();
        container.addAll(edses);
    }

    /**
     * This method Change the display string representation for an empty field.
     * 
     * @param nullRepresentation The string representing the empty fields.
     */
    public void setNullPropertyRepresentation(String nullRepresentation) {
        this.nullRepresentation = nullRepresentation;
    }

    // PROTECTED
    /**
     * This is overloaded method to customize the display of the fields of a particular type
     * 
     * @param rowId Line identifier in commonly treated item.
     * @param colId ID column in the currently processed element.
     * @param property Property of the commonly treated element .
     * @return string representation of fluently processed box.
     */
    @Override
    protected String formatPropertyValue(Object rowId, Object colId, Property property) {
        if (property.getValue() == null) {
            return nullRepresentation;
        }
        if (property.getType().equals(EdsUser.class)) {
            return ((EdsUser) property.getValue()).toFullName();
        }
        if (property.getType().equals(EdsSupplier.class)) {
            return ((EdsSupplier) property.getValue()).getSName();
        }
        if (property.getType().equals(Date.class)) {
            return df.format((Date) property.getValue());
        }
        if (property.getType().equals(EdsComponentType.class)) {
            return ((EdsComponentType) property.getValue()).getLocaleCtName(controller.getApplication().getLocale());
        }
        if (colId.equals("edsStage")) {
            return EdsApplicationController.getStageText((Integer) property.getValue());
        }
        if (colId.equals("edsMajorVersion")) {
            return ((EdsEds) rowId).getVersionShort();
        }

        return super.formatPropertyValue(rowId, colId, property);
    }

    // PRIVATE
    // STATIC FINAL
    /**
     * Constant variable that holds simple date format value
     */
    private static final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
    // STANDARD
    /**
     * Variable that holds value of Eds controller
     */
    private EdsApplicationController controller;
    /**
     * Variable that holds value of bean container
     */
    private BeanItemContainer container;
    /**
     * variable that holds empty string
     */
    private String nullRepresentation;

    /**
     * Initialize Eds table.
     */
    private void init() {
        // general Settings
        this.setSelectable(true);
        this.setMultiSelect(true);
        this.setPageLength(15);
        this.setNullPropertyRepresentation("-");

        // model settings
        this.container = new BeanItemContainer(EdsEds.class);
        this.setContainerDataSource(container);

        // Renderer settings
        this.addGeneratedColumn("edsRef", new Table.ColumnGenerator() {
            public Object generateCell(Table source, Object itemId, Object columnId) {
                Property prop = source.getItem(itemId).getItemProperty(columnId);
                if (prop.getValue() != null) {
                    return new Link(prop.getValue().toString(), new ExternalResource("#" + FragmentManager.formatURLFragmentForEDS((EdsEds) itemId)));
                }
                return nullRepresentation;
            }
        });
        this.addGeneratedColumn("edsProject", new Table.ColumnGenerator() {
            public Object generateCell(Table source, Object itemId, Object columnId) {
                Property prop = source.getItem(itemId).getItemProperty(columnId);
                if (prop.getType().equals(EdsProject.class)) {
                    EdsProject project = (EdsProject) prop.getValue();
                    return new Link(project.getPName(), new ExternalResource("#" + FragmentManager.formatURLFragmentForProject(project.getPId())));
                }
                return nullRepresentation;
            }
        });
        this.addGeneratedColumn("edsProjectEdses", new Table.ColumnGenerator() {
            public Object generateCell(Table source, Object itemId, Object columnId) {
                Property prop = source.getItem(itemId).getItemProperty(columnId);
                if (prop.getType().equals(Set.class)) {
                    GridLayout followersLayout = new GridLayout();
                    followersLayout.setColumns(4);
                    followersLayout.setSpacing(true);
                    Set projectEdses = (Set) prop.getValue();
                    if (!projectEdses.isEmpty()) {
                        Iterator<EdsProjectEds> it = projectEdses.iterator();

                        EdsProject currentProject = it.next().getEdsProject();
                        followersLayout.addComponent(new Link(currentProject.getPName(), new ExternalResource("#"
                                + FragmentManager.formatURLFragmentForProject(currentProject.getPId()))));

                        while (it.hasNext()) {
                            currentProject = it.next().getEdsProject();

                            followersLayout.addComponent(new Link(currentProject.getPName(), new ExternalResource("#"
                                    + FragmentManager.formatURLFragmentForProject(currentProject.getPId()))));
                        }
                        return followersLayout;
                    }
                }
                return nullRepresentation;
            }
        });

        this.addGeneratedColumn("edsStage", new Table.ColumnGenerator() {
            public Object generateCell(Table source, Object itemId, Object columnId) {
                return new Label(
                        EdsApplicationController.getStageText(controller.retrieveEdsStage((EdsEds) itemId, ((EdsEds) itemId).getEdsProject())));
            }
        });

        this.setVisibleColumns(new Object[] { "edsRef", "edsMajorVersion", "edsName", "edsComponentType", "edsStage", "edsSupplier", "edsProject",
                "edsProjectEdses", "edsUserByEdsOfficerId", "edsModifDate", "edsDescription" });
        this.setColumnHeaders(new String[] { controller.getBundle().getString("hist-ref"), controller.getBundle().getString("hist-vers"),
                controller.getBundle().getString("Admin-user-name"), controller.getBundle().getString("menu-project-tab-model"),
                controller.getBundle().getString("menu-project-tab-step"), controller.getBundle().getString("generic-data-represent-FNR"),
                controller.getBundle().getString("filter-project-launcher"), controller.getBundle().getString("generic-data-link-follow"),
                controller.getBundle().getString("menu-EDS-charge-dev"), controller.getBundle().getString("eds-modif-date"),
                controller.getBundle().getString("eds-description") });

        this.setItemDescriptionGenerator(new ItemDescriptionGenerator() {
            public String generateDescription(Component source, Object itemId, Object propertyId) {
                if (propertyId == null) {
                    Item edsItem = EdsTable.this.getItem(itemId);
                    StringBuilder builder = new StringBuilder("<b>" + controller.getBundle().getString("hist-ref") + "</b> : ");
                    builder.append(edsItem.getItemProperty("edsRef").getValue());
                    builder.append("<br/>");
                    builder.append("<b>" + controller.getBundle().getString("Admin-user-name") + "</b> : ");
                    builder.append(edsItem.getItemProperty("edsName").getValue());
                    builder.append("<br/>");
                    builder.append("<b>" + controller.getBundle().getString("menu-project-tab-model") + "</b> : ");
                    builder.append(((EdsComponentType) edsItem.getItemProperty("edsComponentType").getValue()).getLocaleCtName(controller
                            .getApplication().getLocale()));
                    builder.append("<br/>");
                    builder.append("<b>" + controller.getBundle().getString("eds-pop-new-reseau-bt-tbt") + "</b> : ");
                    if (edsItem.getItemProperty("edsIsBttbt").getValue().equals(0)) {
                        builder.append(controller.getBundle().getString("consolid-qcf-non"));
                    } else {
                        builder.append(controller.getBundle().getString("consolid-qcf-oui"));
                    }
                    builder.append("<br/>");
                    builder.append("<b>" + controller.getBundle().getString("mail-content-eds-stade") + "</b> : ");
                    builder.append(EdsApplicationController.getStageText(controller.retrieveEdsStage((EdsEds) itemId,
                            ((EdsEds) itemId).getEdsProject())));
                    builder.append("<br/>");
                    builder.append("<b>" + controller.getBundle().getString("generic-data-represent-FNR") + "</b> : ");

                    EdsSupplier supplier = (EdsSupplier) edsItem.getItemProperty("edsSupplier").getValue();
                    String supplierName = "-";
                    if (supplier != null) {
                        supplierName = supplier.getSName();
                    }
                    builder.append(supplierName);
                    builder.append("<br/>");
                    builder.append("<b>" + controller.getBundle().getString("generic-data-represent-responsible") + "</b> : ");
                    String managerName = "-";
                    String officerDptName = "-";
                    EdsUser manager = (EdsUser) edsItem.getItemProperty("edsUserByEdsManagerId").getValue();
                    if (manager != null) {
                        managerName = manager.getULastname();
                    }
                    EdsUser officer = (EdsUser) edsItem.getItemProperty("edsUserByEdsOfficerId").getValue();
                    if (officer != null) {
                        officerDptName = officer.getUService();
                    }
                    builder.append(managerName);
                    builder.append("<br/>");
                    builder.append("<b>" + controller.getBundle().getString("Admin-user-service") + "</b> : ");
                    builder.append(officerDptName);
                    builder.append("<br/>");
                    if (!controller.getAuthenticatedUser().getEdsRole().isPerimeter() && !controller.getAuthenticatedUser().getEdsRole().isSupplier()) {
                        builder.append("<b>" + controller.getBundle().getString("filter-project-launcher") + "</b> : ");
                        builder.append(((EdsProject) edsItem.getItemProperty("edsProject").getValue()).getPName());
                        builder.append("<br/>");

                        Set projectEdses = (Set) edsItem.getItemProperty("edsProjectEdses").getValue();
                        if (projectEdses.size() > 1) {
                            builder.append("<b>" + controller.getBundle().getString("generic-data-link-follow") + "</b> : ");
                            Iterator<EdsProjectEds> it = projectEdses.iterator();
                            builder.append(it.next().getEdsProject().getPName());
                            while (it.hasNext()) {
                                builder.append(", ");
                                builder.append(it.next().getEdsProject().getPName());
                            }
                        } else if (projectEdses.isEmpty()) {
                            builder.append("<b>" + controller.getBundle().getString("generic-data-link-follow") + "</b> : aucun");
                        } else {
                            builder.append("<b>" + controller.getBundle().getString("generic-data-link-follow") + "</b> : ");
                            builder.append(((EdsProjectEds) projectEdses.iterator().next()).getEdsProject().getPName());
                        }
                        builder.append("<br/>");
                    }
                    builder.append("<b>" + controller.getBundle().getString("filter-96") + "</b> : ");
                    builder.append(getSAPRefs(edsItem));
                    builder.append("<br/>");

                    return builder.toString();
                }
                return null;
            }

            private String getSAPRefs(Item item) {
                String refs = "-";
                Set<EdsNumber96k> numbers = (Set<EdsNumber96k>) item.getItemProperty("edsNumber96ks").getValue();
                if (!numbers.isEmpty()) {
                    refs = "";
                    for (EdsNumber96k number : numbers) {
                        refs += number.getNValue() + ", ";
                    }
                    refs = refs.substring(0, refs.length() - 2);
                }
                return refs;
            }
        });
    }
}
