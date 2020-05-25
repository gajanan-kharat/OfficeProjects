package com.inetpsa.eds.tools.table;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;

import com.inetpsa.eds.application.ChsController;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.Chs;
import com.inetpsa.eds.dao.model.ChsPin;
import com.inetpsa.eds.tools.chs.observer.EdsChsChangeType;
import com.inetpsa.eds.tools.chs.observer.Observable;
import com.inetpsa.eds.tools.chs.observer.Observer;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This class is pre-formatted array (Vaadin component) to display lists of CHS. All the "renderer" of Fields (non-primitive or not supported by
 * Vaadin components) of particular types are shown in this table.
 * 
 * @author Joao Costa @ Alter Frame
 */
public class CHSTable extends Table implements Observer<EdsChsChangeType> {
    // PUBLIC
    private ChsController chsController;
    private final static int CAV_TEXT_SIZE = 6;

    public CHSTable(EdsApplicationController controller, ChsController chsController) {
        this.chsController = chsController;
        this.controller = controller;
        chsController.addObserver(this);
        init();
    }

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of the EDS application.
     */
    public CHSTable(EdsApplicationController controller) {
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
    public void setChsList() {
        container.removeAllItems();
        container.addAll(chsController.getFilterChs());
        setSelected();
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
    // PRIVATE
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
    private BeanItemContainer<Chs> container;
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
        this.setMultiSelect(false);
        this.setPageLength(15);
        this.setNullPropertyRepresentation("-");
        this.setNullSelectionAllowed(false);

        // model settings
        this.container = new BeanItemContainer<Chs>(Chs.class);
        this.setContainerDataSource(container);

        this.addGeneratedColumn("nbPin", new Table.ColumnGenerator() {

            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                Set<ChsPin> cavities = (Set<ChsPin>) source.getItem(itemId).getItemProperty("cavities").getValue();
                return cavities.size();
            }
        });

        this.addGeneratedColumn("nbCav", new Table.ColumnGenerator() {

            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                Map<String, Integer> pinDetails = (Map<String, Integer>) source.getItem(itemId).getItemProperty("pinDetails").getValue();
                return pinDetails.keySet().size();
            }
        });
        this.addGeneratedColumn("chsCav", new Table.ColumnGenerator() {

            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                Map<String, Integer> pinDetails = (Map<String, Integer>) source.getItem(itemId).getItemProperty("pinDetails").getValue();

                StringBuilder chsCav = new StringBuilder(pinDetails.keySet().size() * CAV_TEXT_SIZE);
                for (String key : pinDetails.keySet()) {
                    chsCav.append("[");
                    chsCav.append(key);
                    chsCav.append(',');
                    chsCav.append(pinDetails.get(key));
                    chsCav.append("]");
                }
                return chsCav.toString();
            }
        });
        this.addGeneratedColumn("associationCreate", new Table.ColumnGenerator() {

            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                final Chs item = (Chs) itemId;
                Button create = new Button();
                create.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png"));
                create.setImmediate(true);
                create.setStyleName(BaseTheme.BUTTON_LINK);
                create.addListener(new ClickListener() {

                    @Override
                    public void buttonClick(ClickEvent event) {
                        chsController.addConnectedChs(item);
                    }

                });
                return create;
            }
        });

        this.setVisibleColumns(new Object[] { "partNumber", "description", "userRef2", "nbPin", "nbCav", "chsCav", "associationCreate" });
        this.setColumnHeaders(new String[] { controller.getBundle().getString("con-association-partnum"),
                controller.getBundle().getString("con-association-desc"), controller.getBundle().getString("con-association-96xxx"),
                controller.getBundle().getString("con-association-cavnum"), controller.getBundle().getString("con-association-embnum"),
                controller.getBundle().getString("con-association-chscav"), controller.getBundle().getString("con-association-create") });

        this.setColumnAlignment("associationCreate", Table.ALIGN_CENTER);

        this.setItemDescriptionGenerator(new ItemDescriptionGenerator() {
            public String generateDescription(Component source, Object itemId, Object propertyId) {
                Chs item = (Chs) itemId;
                if (propertyId == null) {
                    return "";
                }
                StringBuilder builder = new StringBuilder("<center><b><ins>ORGANE:</ins></b></center><br>");
                builder.append("<b>Ref 96xxx:</b> ");
                builder.append(item.getUserRef2());
                builder.append("<br>");
                builder.append("<b>Description:</b> ");
                builder.append(item.getDescription());
                builder.append("<br><br>");
                builder.append("<center><b><ins>FILTRAGE:</ins></b></center> <br> ");
                builder.append("<b>");
                builder.append(controller.getBundle().getString("chs-filter-cavnum-desc"));
                builder.append("</b> ");
                builder.append(item.getCavities().size());
                builder.append("<br>");
                builder.append("<b>");
                builder.append(controller.getBundle().getString("chs-filter-embnum-desc"));
                builder.append("</b> ");
                builder.append(item.getPinDetails().keySet().size());
                builder.append("<br>");
                builder.append("<b>[embase, nb of pins]:</b> ");
                Map<String, Integer> pinDetails = item.getPinDetails();
                StringBuilder chsCav = new StringBuilder(pinDetails.keySet().size() * CAV_TEXT_SIZE);
                for (String key : pinDetails.keySet()) {
                    chsCav.append("[");
                    chsCav.append(key);
                    chsCav.append(',');
                    chsCav.append(pinDetails.get(key));
                    chsCav.append("]");
                }
                builder.append(chsCav.toString());

                return builder.toString();
            }

        });
        this.setImmediate(true);
        this.addListener(new ItemClickEvent.ItemClickListener() {

            @Override
            public void itemClick(ItemClickEvent event) {
                Set<ChsPin> cavities = (Set<ChsPin>) event.getItem().getItemProperty("cavities").getValue();
                chsController.setSelectedChs(cavities);
            }
        });

    }

    private void setSelected() {
        if (container.size() > 0) {
            this.select(container.getIdByIndex(0));
            chsController.setSelectedChs(container.getIdByIndex(0).getCavities());
        } else {
            chsController.setSelectedChs(null);
        }
    }

    @Override
    public void update(Observable<EdsChsChangeType> o, EdsChsChangeType updated) {
        if (updated.equals(EdsChsChangeType.FILTER)) {
            setChsList();
        }
    }
}
