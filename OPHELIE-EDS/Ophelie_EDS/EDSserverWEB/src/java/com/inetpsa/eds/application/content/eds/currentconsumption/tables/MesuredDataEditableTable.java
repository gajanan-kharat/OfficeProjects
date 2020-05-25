package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.composants.MyTable;
import com.inetpsa.eds.application.content.eds.cse.FloatField;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyMesure;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextField;

/**
 * This class is an array of pre-formatted (Vaadin component) for display CSE lines. All "renderer" fields of a particular type (non-primitive or not
 * supported by Vaadin components) are entered in this table.
 * 
 * @author Geometric Ltd.
 */
public class MesuredDataEditableTable extends MyTable {
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
     * Constant to hold value of PROPERTY_NBR_PIECE
     */
    public static final String PROPERTY_NBR_PIECE = "current-conso-tab-data-nbr-pieces";
    /**
     * Constant to hold value of PROPERTY_NBR_PIECE_COMMENT
     */
    public static final String PROPERTY_NBR_PIECE_COMMENT = "current-conso-tab-data-nbr-pieces-comment";
    /**
     * Constant to hold value of PROPERTY_ECHANTILLON
     */
    public static final String PROPERTY_ECHANTILLON = "current-conso-tab-data-echantillon";
    /**
     * Constant to hold value of PROPERTY_ECHANTILLON_COMMENT
     */
    public static final String PROPERTY_ECHANTILLON_COMMENT = "current-conso-tab-data-echantillon-comment";
    /**
     * Constant to hold list of String for sample
     */
    public static final List<String> ECHANTILLON = Arrays.asList("IOD", "Pré-série", "Série");

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public MesuredDataEditableTable(EdsApplicationController controller) {
        this.controller = controller;
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
     * This method create new supply measure
     * 
     * @return EdsConsolidateSupplyMesure
     */
    public EdsConsolidateSupplyMesure createNewMesure() {
        EdsConsolidateSupplyMesure newLine = new EdsConsolidateSupplyMesure(UUID.randomUUID().toString());

        addEcsm(newLine);

        return newLine;
    }

    /**
     * This method add EdsConsolidateSupplyMesure in table
     * 
     * @param ecsm Object of EdsConsolidateSupplyMesure
     */
    public void addEcsm(EdsConsolidateSupplyMesure ecsm) {
        addItem(new Object[] { ecsm.getCsmedsTmin(), ecsm.getCsmedsTminComment(), ecsm.getCsmedsTmoy(), ecsm.getCsmedsTmoyComment(),
                ecsm.getCsmedsTmax(), ecsm.getCsmedsTmaxComment(), ecsm.getCsmedsNombrePieceTester(), ecsm.getCsmedsNombrePieceTesterComment(),
                ecsm.getCsmedsEchantillonDeTest(), ecsm.getCsmedsEchantillonDeTestComment() }, ecsm.getCsmedsId());
    }

    /**
     * This method update the changes in specified EdsConsolidateSupplyMesure
     * 
     * @param ecsm Object of EdsConsolidateSupplyMesure
     */
    public void updateEcsm(EdsConsolidateSupplyMesure ecsm) {
        if (getContainerProperty(ecsm.getCsmedsId(), PROPERTY_TMIN) != null) {
            ecsm.setCsmedsTmin((Float) getContainerProperty(ecsm.getCsmedsId(), PROPERTY_TMIN).getValue());
            ecsm.setCsmedsTminComment((String) getContainerProperty(ecsm.getCsmedsId(), PROPERTY_TMIN_COMMENT).getValue());
            ecsm.setCsmedsTmoy((Float) getContainerProperty(ecsm.getCsmedsId(), PROPERTY_TMOY).getValue());
            ecsm.setCsmedsTmoyComment((String) getContainerProperty(ecsm.getCsmedsId(), PROPERTY_TMOY_COMMENT).getValue());
            ecsm.setCsmedsTmax((Float) getContainerProperty(ecsm.getCsmedsId(), PROPERTY_TMAX).getValue());
            ecsm.setCsmedsTmaxComment((String) getContainerProperty(ecsm.getCsmedsId(), PROPERTY_TMAX_COMMENT).getValue());
            ecsm.setCsmedsNombrePieceTester((Float) getContainerProperty(ecsm.getCsmedsId(), PROPERTY_NBR_PIECE).getValue());
            ecsm.setCsmedsNombrePieceTesterComment((String) getContainerProperty(ecsm.getCsmedsId(), PROPERTY_NBR_PIECE_COMMENT).getValue());
            ecsm.setCsmedsEchantillonDeTest((String) getContainerProperty(ecsm.getCsmedsId(), PROPERTY_ECHANTILLON).getValue());
            ecsm.setCsmedsEchantillonDeTestComment((String) getContainerProperty(ecsm.getCsmedsId(), PROPERTY_ECHANTILLON_COMMENT).getValue());
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
     * Initialize Measured data editable table
     */
    private void init() {
        this.errorMessage = null;
        // Model settings
        addContainerProperty(PROPERTY_TMIN, Float.class, null, controller.getBundle().getString(PROPERTY_TMIN), null, null);
        addContainerProperty(PROPERTY_TMIN_COMMENT, String.class, null, controller.getBundle().getString(PROPERTY_TMIN_COMMENT), null, null);
        addContainerProperty(PROPERTY_TMOY, Float.class, null, controller.getBundle().getString(PROPERTY_TMOY), null, null);
        addContainerProperty(PROPERTY_TMOY_COMMENT, String.class, null, controller.getBundle().getString(PROPERTY_TMOY_COMMENT), null, null);
        addContainerProperty(PROPERTY_TMAX, Float.class, null, controller.getBundle().getString(PROPERTY_TMAX), null, null);
        addContainerProperty(PROPERTY_TMAX_COMMENT, String.class, null, controller.getBundle().getString(PROPERTY_TMAX_COMMENT), null, null);
        addContainerProperty(PROPERTY_NBR_PIECE, Float.class, null, controller.getBundle().getString(PROPERTY_NBR_PIECE), null, null);
        addContainerProperty(PROPERTY_NBR_PIECE_COMMENT, String.class, null, controller.getBundle().getString(PROPERTY_NBR_PIECE_COMMENT), null, null);
        addContainerProperty(PROPERTY_ECHANTILLON, String.class, null, controller.getBundle().getString(PROPERTY_ECHANTILLON), null, null);
        addContainerProperty(PROPERTY_ECHANTILLON_COMMENT, String.class, null, controller.getBundle().getString(PROPERTY_ECHANTILLON_COMMENT), null,
                null);

        this.lineValidator = new Validator() {
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
                // && (Float) lineItem.getItemProperty( PROPERTY_LOWER_BOUND ).getValue() > (Float)
                // lineItem.
                // getItemProperty( PROPERTY_UPPER_BOUND ).getValue() )
                // {
                // throw new InvalidValueException(
                // "Erreur CSE n°" + (Integer) lineItem.getItemProperty( PROPERTY_NUMBER ).getValue() +
                // " : Les bornes de la CSE ne sont pas valides." );
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
                // && (Float) lineItem.getItemProperty( PROPERTY_LOWER_BOUND ).getValue() > (Float)
                // lineItem.
                // getItemProperty( PROPERTY_UPPER_BOUND ).getValue() )
                // {
                // return false;
                // }

                return true;
            }
        };

        this.setTableFieldFactory(new TableFieldFactory() {
            /*
             * (non-Javadoc)
             * 
             * @see com.vaadin.ui.TableFieldFactory#createField(com.vaadin.data.Container, java.lang.Object, java.lang.Object,
             * com.vaadin.ui.Component)
             */
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
                } else if (propertyId.equals(PROPERTY_TMIN)) {
                    FloatField ff = (FloatField) createdField;
                    ff.setNullRepresentation("");
                    ff.setNullSettingAllowed(true);
                    ff.setMaxLength(64);
                } else if (propertyId.equals(PROPERTY_TMOY)) {
                    FloatField ff = (FloatField) createdField;
                    ff.setNullRepresentation("");
                    ff.setNullSettingAllowed(true);
                    ff.setMaxLength(64);
                } else if (propertyId.equals(PROPERTY_NBR_PIECE)) {
                    FloatField ff = (FloatField) createdField;
                    ff.setNullRepresentation("");
                    ff.setNullSettingAllowed(true);
                    ff.setMaxLength(64);
                } else if (propertyId.equals(PROPERTY_ECHANTILLON)) {
                    ComboBox tf = new ComboBox("", ECHANTILLON);

                    return tf;
                } else if (propertyId.equals(PROPERTY_TMIN_COMMENT)) {
                    TextField tf = (TextField) createdField;
                    tf.setNullRepresentation("");
                    tf.setNullSettingAllowed(true);
                    tf.setMaxLength(256);
                    tf.addListener(new TextChangeListener() {
                        public void textChange(TextChangeEvent event) {
                            if (event.getText().length() > 255) {
                                getController().showWarning(controller.getBundle().getString("message-comment-error"));
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
                                getController().showWarning(controller.getBundle().getString("message-comment-error"));
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
                                getController().showWarning(controller.getBundle().getString("message-comment-error"));
                            }
                        }
                    });
                } else if (propertyId.equals(PROPERTY_NBR_PIECE_COMMENT)) {
                    TextField tf = (TextField) createdField;
                    tf.setNullRepresentation("");
                    tf.setNullSettingAllowed(true);
                    tf.setMaxLength(256);
                    tf.addListener(new TextChangeListener() {
                        public void textChange(TextChangeEvent event) {
                            if (event.getText().length() > 255) {
                                getController().showWarning(controller.getBundle().getString("message-comment-error"));
                            }
                        }
                    });
                } else if (propertyId.equals(PROPERTY_ECHANTILLON_COMMENT)) {
                    TextField tf = (TextField) createdField;
                    tf.setNullRepresentation("");
                    tf.setNullSettingAllowed(true);
                    tf.setMaxLength(256);
                    tf.addListener(new TextChangeListener() {
                        public void textChange(TextChangeEvent event) {
                            if (event.getText().length() > 255) {
                                getController().showWarning(controller.getBundle().getString("message-comment-error"));
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

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.Table#setVisible(boolean)
     */
    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            setVisibleColumns(new Object[] { PROPERTY_TMIN, PROPERTY_TMIN_COMMENT, PROPERTY_TMOY, PROPERTY_TMOY_COMMENT, PROPERTY_TMAX,
                    PROPERTY_TMAX_COMMENT, PROPERTY_NBR_PIECE, PROPERTY_NBR_PIECE_COMMENT, PROPERTY_ECHANTILLON, PROPERTY_ECHANTILLON_COMMENT });
        } else {
            setVisibleColumns(new Object[] { PROPERTY_NBR_PIECE, PROPERTY_NBR_PIECE_COMMENT, PROPERTY_ECHANTILLON, PROPERTY_ECHANTILLON_COMMENT });
        }
    }

    /**
     * This method reset values of EdsConsolidateSupplyMesure
     * 
     * @param ecsm Object of EdsConsolidateSupplyMesure
     */
    public void reset(EdsConsolidateSupplyMesure ecsm) {
        getContainerProperty(ecsm.getCsmedsId(), PROPERTY_TMIN).setValue(null);
        getContainerProperty(ecsm.getCsmedsId(), PROPERTY_TMIN_COMMENT).setValue("");
        getContainerProperty(ecsm.getCsmedsId(), PROPERTY_TMOY).setValue(null);
        getContainerProperty(ecsm.getCsmedsId(), PROPERTY_TMOY_COMMENT).setValue("");
        getContainerProperty(ecsm.getCsmedsId(), PROPERTY_TMAX).setValue(null);
        getContainerProperty(ecsm.getCsmedsId(), PROPERTY_TMAX_COMMENT).setValue("");
    }
}
