package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import java.util.Collection;
import java.util.UUID;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.composants.MyTable;
import com.inetpsa.eds.application.content.eds.cse.FloatField;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyTheoritic;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextField;

/**
 * This class is an array of pre-formatted (Vaadin component) for display CSE lines. All "renderer" fields of Particular type (non-primitive or not
 * supported by Vaadin components) are shown in this table.
 * 
 * @author Geometric Ltd.
 */
public class TheoriqueDataEditableTable extends MyTable {
    // PUBLIC
    /**
     * Constant to hold value of PROPERTY_TMIN
     */
    public static final String PROPERTY_TMIN = "current-conso-tab-data-Tmin";
    /**
     * Constant to hold value of PROPERTY_TMIN_COMMENT
     */
    public static final String PROPERTY_TMIN_COMMENT = "current-conso-tab-data-Tmin-comment";

    /**
     * Constant to hold value of PROPERTY_TMOY
     */
    public static final String PROPERTY_TMOY = "current-conso-tab-data-Tmoy";

    /**
     * Constant to hold value of PROPERTY_TMOY_COMMENT
     */
    public static final String PROPERTY_TMOY_COMMENT = "current-conso-tab-data-Tmoy-comment";
    /**
     * Constant to hold value of PROPERTY_TMAX
     */
    public static final String PROPERTY_TMAX = "current-conso-tab-data-Tmax";
    /**
     * Constant to hold value of PROPERTY_TMAX_COMMENT
     */
    public static final String PROPERTY_TMAX_COMMENT = "current-conso-tab-data-Tmax-comment";

    /**
     * Show all the temperature, or only tmoy
     */
    private boolean showAllTemp;

    /**
     * Parameterized Constructor
     * 
     * @param controller Controller of EDS application
     */
    public TheoriqueDataEditableTable(EdsApplicationController controller, boolean showAllTemp) {
        this.controller = controller;
        this.showAllTemp = showAllTemp;
        init();
    }

    /**
     * This method returns controller of EDS application
     * 
     * @return controller of EDS application
     */
    public EdsApplicationController getController() {
        return controller;
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
     * This method return Validation error message
     * 
     * @return Validation error message
     */
    public String getValidationErrorMessage() {
        return errorMessage;
    }

    /**
     * This method create new Theoretical supply
     * 
     * @return EdsConsolidateSupplyTheoritic
     */
    public EdsConsolidateSupplyTheoritic createNewTheoritic() {
        EdsConsolidateSupplyTheoritic newLine = new EdsConsolidateSupplyTheoritic(UUID.randomUUID().toString());

        addDonneeTheorique(newLine);

        return newLine;
    }

    /**
     * This method add EdsConsolidateSupplyTheoritic in table
     * 
     * @param ecst Object of EdsConsolidateSupplyTheoritic
     */
    public void addDonneeTheorique(EdsConsolidateSupplyTheoritic ecst) {
        if (showAllTemp) {
            addItem(new Object[] { ecst.getCsedsTmin(), ecst.getCsedsTminComment(), ecst.getCsedsTmoy() != null ? ecst.getCsedsTmoy() : 23F,
                    ecst.getCsedsTmoyComment(), ecst.getCsedsTmax(), ecst.getCsedsTmaxComment() }, ecst.getCstedsId());
        } else {
            addItem(new Object[] { ecst.getCsedsTmoy() != null ? ecst.getCsedsTmoy() : 23F, ecst.getCsedsTmoyComment() }, ecst.getCstedsId());
        }
    }

    /**
     * This method remove specified EdsConsolidateSupplyTheoritic
     * 
     * @param ecst Object of EdsConsolidateSupplyTheoritic
     */
    public void removeTheorique(EdsConsolidateSupplyTheoritic ecst) {
        this.removeItem(ecst.getCstedsId());
    }

    /**
     * This method update the changes in specified EdsConsolidateSupplyTheoritic
     * 
     * @param ecst Object of EdsConsolidateSupplyTheoritic
     */
    public void updateCseTheorique(EdsConsolidateSupplyTheoritic ecst) {
        if (getContainerProperty(ecst.getCstedsId(), PROPERTY_TMIN) != null) {
            if (showAllTemp) {
                ecst.setCsedsTmin((Float) getContainerProperty(ecst.getCstedsId(), PROPERTY_TMIN).getValue());
                ecst.setCsedsTminComment((String) getContainerProperty(ecst.getCstedsId(), PROPERTY_TMIN_COMMENT).getValue());
            }
            ecst.setCsedsTmoy((Float) getContainerProperty(ecst.getCstedsId(), PROPERTY_TMOY).getValue());
            ecst.setCsedsTmoyComment((String) getContainerProperty(ecst.getCstedsId(), PROPERTY_TMOY_COMMENT).getValue());
            if (showAllTemp) {
                ecst.setCsedsTmax((Float) getContainerProperty(ecst.getCstedsId(), PROPERTY_TMAX).getValue());
                ecst.setCsedsTmaxComment((String) getContainerProperty(ecst.getCstedsId(), PROPERTY_TMAX_COMMENT).getValue());
            }
        }
    }

    // PRIVATE
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of String for error message
     */
    private String errorMessage;
    /**
     * Variable to hold value of Validator
     */
    private Validator lineValidator;

    /**
     * Initialize Theoretical data editable table
     */
    private void init() {
        this.errorMessage = null;
        // model settings
        if (showAllTemp) {
            addContainerProperty(PROPERTY_TMIN, Float.class, null, controller.getBundle().getString(PROPERTY_TMIN), null, null);
            addContainerProperty(PROPERTY_TMIN_COMMENT, String.class, null, controller.getBundle().getString(PROPERTY_TMIN_COMMENT), null, null);
        }
        addContainerProperty(PROPERTY_TMOY, Float.class, null, controller.getBundle().getString(PROPERTY_TMOY), null, null);
        addContainerProperty(PROPERTY_TMOY_COMMENT, String.class, null, controller.getBundle().getString(PROPERTY_TMOY_COMMENT), null, null);
        if (showAllTemp) {
            addContainerProperty(PROPERTY_TMAX, Float.class, null, controller.getBundle().getString(PROPERTY_TMAX), null, null);
            addContainerProperty(PROPERTY_TMAX_COMMENT, String.class, null, controller.getBundle().getString(PROPERTY_TMAX_COMMENT), null, null);
        }

        this.lineValidator = new Validator() {
            /*
             * (non-Javadoc)
             * 
             * @see com.vaadin.data.Validator#validate(java.lang.Object)
             */
            public void validate(Object value) throws InvalidValueException {
                Item lineItem = (Item) value;

                // if ( lineItem.getItemProperty( PROPERTY_TMIN ).getValue() == null )
                // {
                // if ( lineItem.getItemProperty( PROPERTY_TMIN ).getValue() != null
                // || lineItem.getItemProperty( PROPERTY_TMIN_COMMENT ).getValue() != null
                // || lineItem.getItemProperty( PROPERTY_TMAX ).getValue() != null
                // || lineItem.getItemProperty( PROPERTY_TMAX_COMMENT).getValue() != null )
                // {
                // throw new InvalidValueException(
                // "Erreur CSE n°" + (Integer) lineItem.getItemProperty( PROPERTY_NUMBER ).getValue() +
                // " : Le nom de la donnée de la CSE ne peut pas être vide." );
                // }
                // }
                // else if ( lineItem.getItemProperty( PROPERTY_LOWER_BOUND ).getValue() != null
                // && lineItem.getItemProperty( PROPERTY_UPPER_BOUND ).getValue() != null
                // && (Float) lineItem.getItemProperty( PROPERTY_LOWER_BOUND ).getValue() > (Float) lineItem.
                // getItemProperty( PROPERTY_UPPER_BOUND ).getValue() )
                // {
                // throw new InvalidValueException(
                // "Erreur CSE n°" + (Integer) lineItem.getItemProperty( PROPERTY_NUMBER ).getValue() + " : Les bornes de la CSE ne sont pas valides."
                // );
                // }
            }

            /*
             * (non-Javadoc)
             * 
             * @see com.vaadin.data.Validator#isValid(java.lang.Object)
             */
            public boolean isValid(Object value) {
                Item lineItem = (Item) value;

                // if ( lineItem.getItemProperty( PROPERTY_TMAX ).getValue() == null )
                // {
                // return false;
                // }
                // else if ( lineItem.getItemProperty( PROPERTY_LOWER_BOUND ).getValue() != null
                // && lineItem.getItemProperty( PROPERTY_UPPER_BOUND ).getValue() != null
                // && (Float) lineItem.getItemProperty( PROPERTY_LOWER_BOUND ).getValue() > (Float) lineItem.
                // getItemProperty( PROPERTY_UPPER_BOUND ).getValue() )
                // {
                // return false;
                // }

                return true;
            }
        };

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

                if (propertyId.equals(PROPERTY_TMAX)) {
                    FloatField ff = (FloatField) createdField;
                    ff.setNullRepresentation("");
                    ff.setNullSettingAllowed(true);
                    ff.setMaxLength(64);
                } else if (propertyId.equals(PROPERTY_TMOY)) {
                    FloatField ff = (FloatField) createdField;
                    ff.setNullRepresentation("");
                    ff.setNullSettingAllowed(true);
                    ff.setMaxLength(64);
                } else if (propertyId.equals(PROPERTY_TMIN)) {
                    FloatField ff = (FloatField) createdField;
                    ff.setNullRepresentation("");
                    ff.setNullSettingAllowed(true);
                    ff.setMaxLength(64);
                } else if (propertyId.equals(PROPERTY_TMIN_COMMENT)) {
                    TextField tf = (TextField) createdField;
                    tf.setNullRepresentation("");
                    tf.setNullSettingAllowed(true);
                    tf.setMaxLength(256);
                    tf.addListener(new TextChangeListener() {
                        public void textChange(TextChangeEvent event) {
                            if (event.getText().length() > 255) {
                                getController().showWarning("Le champ de texte ne peut contenir plus de 256 caractères.");
                            }
                        }
                    });
                } else if (propertyId.equals(PROPERTY_TMAX_COMMENT)) {
                    TextField tf = (TextField) createdField;
                    tf.setNullRepresentation("");
                    tf.setNullSettingAllowed(true);
                    tf.setMaxLength(256);
                    tf.addListener(new TextChangeListener() {
                        public void textChange(TextChangeEvent event) {
                            if (event.getText().length() > 255) {
                                getController().showWarning("Le champ de texte ne peut contenir plus de 256 caractères.");
                            }
                        }
                    });
                } else if (propertyId.equals(PROPERTY_TMOY_COMMENT)) {
                    TextField tf = (TextField) createdField;
                    tf.setNullRepresentation("");
                    tf.setNullSettingAllowed(true);
                    tf.setMaxLength(256);
                    tf.addListener(new TextChangeListener() {
                        public void textChange(TextChangeEvent event) {
                            if (event.getText().length() > 255) {
                                getController().showWarning("Le champ de texte ne peut contenir plus de 256 caractères.");
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
        this.setPageLength(1);

    }
}
