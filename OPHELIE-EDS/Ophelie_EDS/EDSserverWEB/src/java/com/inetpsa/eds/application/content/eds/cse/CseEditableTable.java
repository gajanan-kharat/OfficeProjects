package com.inetpsa.eds.application.content.eds.cse;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsCseFormData;
import com.inetpsa.eds.dao.model.EdsCseLine;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Table;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextField;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.UUID;

/**
 * This class is an array of pre-formatted (Vaadin component) for display CSE lines. All "renderer" fields of a particular type (non-primitive or not
 * supported by Vaadin components) are Entered in this table.
 * 
 * @author Geometric Ltd.
 */
public class CseEditableTable extends Table {
    // PUBLIC
    /**
     * Constant to hold value of PROPERTY_NUMBER
     */
    public static final String PROPERTY_NUMBER = "CSE-num";
    /**
     * Constant to hold value of PROPERTY_DATANAME
     */
    public static final String PROPERTY_DATANAME = "CSE-data";
    /**
     * Constant to hold value of PROPERTY_LOWER_BOUND
     */
    public static final String PROPERTY_LOWER_BOUND = "CSE-inf";
    /**
     * Constant to hold value of PROPERTY_UPPER_BOUND
     */
    public static final String PROPERTY_UPPER_BOUND = "CSE-sup";
    /**
     * Constant to hold value of PROPERTY_UNIT
     */
    public static final String PROPERTY_UNIT = "CSE-Unit";
    /**
     * Constant to hold value of PROPERTY_COMMENT
     */
    public static final String PROPERTY_COMMENT = "eds-comnent";

    /**
     * Parameterized constructor
     * 
     * @param controller controller of EDS application
     */
    public CseEditableTable(EdsApplicationController controller) {
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
     * Method Clears any data that already exist in the table and populates it with data from the parameter list.
     * 
     * @param cseLines data to display in the table.
     */
    public void setCseList(Collection<EdsCseLine> cseLines) {
        removeAllItems();

        TreeSet<EdsCseLine> sortedSet = new TreeSet<EdsCseLine>(new Comparator<EdsCseLine>() {
            public int compare(EdsCseLine o1, EdsCseLine o2) {
                return o1.getCselNumber() - o2.getCselNumber();
            }
        });
        sortedSet.addAll(cseLines);
        nextCseNumber = sortedSet.last().getCselNumber() + 1;

        for (EdsCseLine edsCseLine : sortedSet) {
            addCseLine(edsCseLine);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.AbstractField#isValid()
     */
    @Override
    public boolean isValid() {
        try {
            for (String itemId : (Collection<String>) getItemIds()) {
                lineValidator.validate(getItem(itemId));
            }

            super.validate();
        } catch (InvalidValueException ex) {
            errorMessage = ex.getMessage();
            return false;
        }
        return true;
    }

    /**
     * This method returns Validation Error Message
     * 
     * @return Validation Error Message
     */
    public String getValidationErrorMessage() {
        return errorMessage;
    }

    /**
     * This method creates new Cse
     * 
     * @param formData Object of EdsCseFormData
     * @return Object of EdsCseLine
     */
    public EdsCseLine createNewCse(EdsCseFormData formData) {
        EdsCseLine newLine = new EdsCseLine(UUID.randomUUID().toString(), formData, nextCseNumber++, null);

        addCseLine(newLine);

        return newLine;
    }

    /**
     * This method add Cse line
     * 
     * @param line EdsCseLine to be added
     */
    public void addCseLine(EdsCseLine line) {
        addItem(new Object[] { line.getCselNumber(), line.getCselDataname(), line.getCselLowerBound(), line.getCselUpperBound(), line.getCselUnit(),
                line.getCselComment() }, line.getCselId());
    }

    /**
     * This method remove Cse
     * 
     * @param line EdsCseLine to be removed
     */
    public void removeCse(EdsCseLine line) {
        this.removeItem(line.getCselId());
    }

    /**
     * This method update Cse Line
     * 
     * @param line EdsCseLine to be updated
     */
    public void updateCseLine(EdsCseLine line) {
        if (getContainerProperty(line.getCselId(), PROPERTY_DATANAME) != null) {
            line.setCselDataname((String) getContainerProperty(line.getCselId(), PROPERTY_DATANAME).getValue());
            line.setCselLowerBound((Float) getContainerProperty(line.getCselId(), PROPERTY_LOWER_BOUND).getValue());
            line.setCselUpperBound((Float) getContainerProperty(line.getCselId(), PROPERTY_UPPER_BOUND).getValue());
            line.setCselUnit((String) getContainerProperty(line.getCselId(), PROPERTY_UNIT).getValue());
            line.setCselComment((String) getContainerProperty(line.getCselId(), PROPERTY_COMMENT).getValue());
        }
    }

    // PRIVATE
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold error message
     */
    private String errorMessage;
    /**
     * Variable to hold value of nextCseNumber
     */
    private int nextCseNumber;
    /**
     * Variable to hold value of Validator
     */
    private Validator lineValidator;

    /**
     * Initialize CseEditableTable
     */
    private void init() {
        this.errorMessage = null;

        // Model Settings
        addContainerProperty(PROPERTY_NUMBER, Integer.class, null, controller.getBundle().getString(PROPERTY_NUMBER), null, null);
        addContainerProperty(PROPERTY_DATANAME, String.class, null, controller.getBundle().getString(PROPERTY_DATANAME), null, null);
        addContainerProperty(PROPERTY_LOWER_BOUND, Float.class, null, controller.getBundle().getString(PROPERTY_LOWER_BOUND), null, null);
        addContainerProperty(PROPERTY_UPPER_BOUND, Float.class, null, controller.getBundle().getString(PROPERTY_UPPER_BOUND), null, null);
        addContainerProperty(PROPERTY_UNIT, String.class, null, controller.getBundle().getString(PROPERTY_UNIT), null, null);
        addContainerProperty(PROPERTY_COMMENT, String.class, null, controller.getBundle().getString(PROPERTY_COMMENT), null, null);

        this.lineValidator = new Validator() {
            public void validate(Object value) throws InvalidValueException {
                Item lineItem = (Item) value;

                if (lineItem.getItemProperty(PROPERTY_DATANAME).getValue() == null) {
                    if (lineItem.getItemProperty(PROPERTY_LOWER_BOUND).getValue() != null
                            || lineItem.getItemProperty(PROPERTY_UPPER_BOUND).getValue() != null
                            || lineItem.getItemProperty(PROPERTY_UNIT).getValue() != null
                            || lineItem.getItemProperty(PROPERTY_COMMENT).getValue() != null) {
                        throw new InvalidValueException(MessageFormat.format(controller.getBundle().getString("cse-name-error"), (Integer) lineItem
                                .getItemProperty(PROPERTY_NUMBER).getValue()));
                    }
                } else if (lineItem.getItemProperty(PROPERTY_LOWER_BOUND).getValue() != null
                        && lineItem.getItemProperty(PROPERTY_UPPER_BOUND).getValue() != null
                        && (Float) lineItem.getItemProperty(PROPERTY_LOWER_BOUND).getValue() > (Float) lineItem.getItemProperty(PROPERTY_UPPER_BOUND)
                                .getValue()) {
                    throw new InvalidValueException(MessageFormat.format(controller.getBundle().getString("cse-borne-error"), (Integer) lineItem
                            .getItemProperty(PROPERTY_NUMBER).getValue()));
                }
            }

            /*
             * (non-Javadoc)
             * 
             * @see com.vaadin.data.Validator#isValid(java.lang.Object)
             */
            public boolean isValid(Object value) {
                Item lineItem = (Item) value;

                if (lineItem.getItemProperty(PROPERTY_DATANAME).getValue() == null) {
                    return false;
                } else if (lineItem.getItemProperty(PROPERTY_LOWER_BOUND).getValue() != null
                        && lineItem.getItemProperty(PROPERTY_UPPER_BOUND).getValue() != null
                        && (Float) lineItem.getItemProperty(PROPERTY_LOWER_BOUND).getValue() > (Float) lineItem.getItemProperty(PROPERTY_UPPER_BOUND)
                                .getValue()) {
                    return false;
                }

                return true;
            }
        };

        this.setColumnAlignment(PROPERTY_NUMBER, Table.ALIGN_CENTER);
        this.setColumnExpandRatio(PROPERTY_COMMENT, 1f);

        this.setTableFieldFactory(new TableFieldFactory() {
            public Field createField(final Container container, final Object itemId, Object propertyId, Component uiContext) {
                TextField createdField = null;
                if (container.getItem(itemId).getItemProperty(propertyId).getType().equals(Float.class)) {
                    createdField = new FloatField();
                } else {
                    createdField = (TextField) DefaultFieldFactory.createFieldByPropertyType(container.getItem(itemId).getItemProperty(propertyId)
                            .getType());
                }
                createdField.setImmediate(true);
                createdField.setWriteThrough(true);

                Item item = container.getItem(itemId);
                Integer lineNumber = (Integer) item.getItemProperty(PROPERTY_NUMBER).getValue();

                if (propertyId.equals(PROPERTY_NUMBER)) {
                    createdField.setReadOnly(true);
                } else if (lineNumber != null && (lineNumber == 1 || lineNumber == 2)
                        && (propertyId.equals(PROPERTY_DATANAME) || propertyId.equals(PROPERTY_UNIT))) {
                    createdField.setEnabled(false);
                } else if (propertyId.equals(PROPERTY_DATANAME)) {
                    TextField tf = (TextField) createdField;
                    tf.setNullRepresentation("");
                    tf.setNullSettingAllowed(true);
                    tf.setMaxLength(128);
                } else if (propertyId.equals(PROPERTY_LOWER_BOUND)) {
                    FloatField ff = (FloatField) createdField;
                    ff.setNullRepresentation("");
                    ff.setNullSettingAllowed(true);
                    ff.setMaxLength(64);
                } else if (propertyId.equals(PROPERTY_UPPER_BOUND)) {
                    FloatField ff = (FloatField) createdField;
                    ff.setNullRepresentation("");
                    ff.setNullSettingAllowed(true);
                    ff.setMaxLength(64);
                } else if (propertyId.equals(PROPERTY_UNIT)) {
                    TextField tf = (TextField) createdField;
                    tf.setNullRepresentation("");
                    tf.setNullSettingAllowed(true);
                    tf.setMaxLength(32);
                } else if (propertyId.equals(PROPERTY_COMMENT)) {
                    TextField tf = (TextField) createdField;
                    tf.setNullRepresentation("");
                    tf.setNullSettingAllowed(true);
                    tf.setMaxLength(256);
                    tf.addListener(new TextChangeListener() {
                        public void textChange(TextChangeEvent event) {
                            if (event.getText().length() > 255) {
                                getController().showWarning(controller.getBundle().getString("eds-coment-too-long"));
                            }
                        }
                    });
                }

                return createdField;
            }
        });

        this.setEditable(true);
        this.setSelectable(true);
        this.setMultiSelect(true);
    }
}
