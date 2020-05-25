package com.inetpsa.eds.tools.table;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import com.inetpsa.eds.application.ChsController;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.ChsPin;
import com.inetpsa.eds.dao.model.EdsComponentType;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsSupplier;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.tools.chs.observer.EdsChsChangeType;
import com.inetpsa.eds.tools.chs.observer.Observable;
import com.inetpsa.eds.tools.chs.observer.Observer;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

/**
 * This class is pre-formatted array (Vaadin component) to display lists of CHS from a device ('Connectivity Node'). All the "renderer" of Fields
 * (non-primitive or not supported by Vaadin components) of particular types are shown in this table.
 * 
 * @author Joao Costa @ Alter Frame
 */
public class EmbaseCHSTable extends Table implements Observer<EdsChsChangeType> {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of the EDS application.
     */
    public EmbaseCHSTable(EdsApplicationController controller, ChsController chsController) {
        this.controller = controller;
        this.chsController = chsController;
        chsController.addObserver(this);
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
    public void setEdsList(Collection<ChsPin> pins) {
        // TODO: Stop being EdsEds
        container.removeAllItems();
        container.addAll(pins);
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

    private ChsController chsController;

    /**
     * Initialize Eds table.
     */
    private void init() {
        // general Settings
        this.setSelectable(true);
        this.setMultiSelect(true);
        this.setPageLength(15);
        this.setNullPropertyRepresentation("-");

        this.container = new BeanItemContainer(ChsPin.class);
        this.setContainerDataSource(container);

        this.setVisibleColumns(new Object[] { "cavity", "pinType" });
        this.setColumnHeaders(new String[] { controller.getBundle().getString("con-association-way"),
                controller.getBundle().getString("con-association-type") });

    }

    private void setEmbaseChs() {
        container.removeAllItems();
        container.addAll(chsController.getSelectedPins());
    }

    @Override
    public void update(Observable<EdsChsChangeType> o, EdsChsChangeType updated) {
        if (updated.equals(EdsChsChangeType.SELECTED)) {
            setEmbaseChs();
        }
    }
}
