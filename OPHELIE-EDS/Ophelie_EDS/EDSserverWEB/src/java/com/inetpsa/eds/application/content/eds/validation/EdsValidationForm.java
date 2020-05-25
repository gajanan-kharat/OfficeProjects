package com.inetpsa.eds.application.content.eds.validation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.dao.model.EdsValidation;
import com.inetpsa.eds.dao.model.EdsWording;
import com.inetpsa.eds.tools.EDSTools;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.TextArea;

/**
 * GUI component (Vaadin) for editing a validation record.
 * 
 * @author Geometric Ltd.
 */
public class EdsValidationForm extends Form {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param validation EDSValidation form.
     * @param locale Locale object.
     * @param controller EDS application controller object.
     */
    public EdsValidationForm(EdsValidation validation, Locale locale, EdsApplicationController controller) {
        this(validation, locale, null, controller);
    }

    /**
     * Parameterized constructor.
     * 
     * @param validation EDSValidation form.
     * @param locale Locale object.
     * @param listener value change listener.
     * @param controller EDS application controller object.
     */
    public EdsValidationForm(EdsValidation validation, Locale locale, ValueChangeListener listener, EdsApplicationController controller) {
        this.validation = validation;
        this.locale = locale;
        this.listener = listener;
        this.controller = controller;
        init();
    }

    /**
     * This method is used to commit the changes made by the user.
     * 
     * @param commitingUser User detail who committed.
     * @param commitDate Commit date.
     */
    public void commitFor(EdsUser commitingUser, Date commitDate) {
        commit();

        validation.setEdsUser(commitingUser);
        validation.setVValidationDate(commitDate);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Constant variable to store value of 'edsWording'.
     */
    private static final String PROPERTY_COMMENT1 = "edsWording";
    /**
     * Constant variable to store value of 'VComment2'.
     */
    private static final String PROPERTY_COMMENT2 = "VComment2";
    /**
     * Constant variable to store value of 'VStatus'.
     */
    private static final String PROPERTY_STATUS = "VStatus";
    /**
     * Variable to store EDS validation details.
     */
    private EdsValidation validation;
    /**
     * Variable to store Locale information.
     */
    private Locale locale;
    /**
     * Variable to store value change listener.
     */
    private ValueChangeListener listener;
    /**
     * Variable to store value of EDS application controller object.
     */
    private EdsApplicationController controller;
    /**
     * Variable to store the list of EDS validation details.
     */
    private BeanItem<EdsValidation> beanValidation;

    /**
     * Initialization method.
     */
    private void init() {
        ValidationFieldFactory fieldFactory = new ValidationFieldFactory(locale);
        this.setFormFieldFactory(fieldFactory);
        beanValidation = new BeanItem<EdsValidation>(validation, new String[] { PROPERTY_COMMENT1, PROPERTY_COMMENT2, PROPERTY_STATUS });

        this.setFormDataSource(validation, Arrays.asList(PROPERTY_COMMENT1, PROPERTY_COMMENT2, PROPERTY_STATUS));

        this.setInvalidCommitted(false);

        if (listener != null) {
            fieldFactory.setValidationValueListener(listener);
        }
        this.setWriteThrough(false);
        this.setImmediate(true);
    }

    /**
     * This class is used to create a custom validation field factory.
     * 
     * @author Geometric Ltd.
     */
    private class ValidationFieldFactory extends DefaultFieldFactory {
        /**
         * Parameterized constructor.
         * 
         * @param locale Locale object.
         */
        public ValidationFieldFactory(Locale locale) {
            this.locale = locale;
            init();
        }

        /**
         * This method is used to set value change listener.
         * 
         * @param listener Listener to be added.
         */
        public void setValidationValueListener(ValueChangeListener listener) {
            vCMBvalidation.addListener(listener);
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.vaadin.ui.DefaultFieldFactory#createField(com.vaadin.data.Item, java.lang.Object, com.vaadin.ui.Component)
         */
        @Override
        public Field createField(Item item, Object propertyId, Component uiContext) {
            if (PROPERTY_COMMENT1.equals(propertyId)) {
                return vCMBprefilledComment;
            }
            if (PROPERTY_COMMENT2.equals(propertyId)) {
                return vTXTcommentArea;
            }
            if (PROPERTY_STATUS.equals(propertyId)) {
                return vCMBvalidation;
            }
            return super.createField(item, propertyId, uiContext);
        }

        /**
         * Variable to store Locale information.
         */
        private Locale locale;
        /**
         * Variable to store value change listener.
         */
        private ValueChangeListener listener;
        /**
         * Combo-box for pre-filled comment.
         */
        private ComboBox vCMBprefilledComment;
        /**
         * Text area for comment.
         */
        private TextArea vTXTcommentArea;
        /**
         * Combo-box for validation.
         */
        private ComboBox vCMBvalidation;

        /**
         * Initialization method.
         */
        private void init() {
            EDSdao dao = EDSdao.getInstance();

            List<EdsWording> wordings = dao.getAllActiveWordingsByType(EdsWording.COMMENT);

            this.vCMBprefilledComment = createCommentsComboBox(wordings);
            this.vTXTcommentArea = createCommentArea();
            this.vCMBvalidation = createValidationComboBox();

            if (listener != null) {
                vCMBvalidation.addListener(listener);
            }
        }

        /**
         * This method is used to create the combo-box for 'Pre-filled note' label.
         * 
         * @param comments comments to be added to the combo-box.
         * @return Combo-box component for Pre-filled note.
         */
        private ComboBox createCommentsComboBox(Collection<EdsWording> comments) {
            ComboBox vCMBcomments = new ComboBox(controller.getBundle().getString("Validation-com-pre") + " :", comments);

            for (EdsWording wording : comments) {
                vCMBcomments.setItemCaption(wording, EDSTools.getWordingValueByLocale(wording, locale));
            }

            vCMBcomments.setNullSelectionAllowed(false);
            vCMBcomments.setTextInputAllowed(false);
            vCMBcomments.setInvalidAllowed(false);
            vCMBcomments.setInvalidCommitted(false);
            vCMBcomments.setRequired(true);
            return vCMBcomments;
        }

        /**
         * This method is used to create the comment area for validation.
         * 
         * @return Text area component for adding comments.
         */
        private TextArea createCommentArea() {
            TextArea vTXTcomment = new TextArea(controller.getBundle().getString("Validation-com-free") + " :");

            vTXTcomment.setNullRepresentation("");
            vTXTcomment.setRows(5);
            vTXTcomment.setMaxLength(1024);
            vTXTcomment.setNullSettingAllowed(true);
            vTXTcomment.setWidth("100%");

            return vTXTcomment;
        }

        /**
         * This method is used to create Validation combo-box.
         * 
         * @return Combo-box for validation.
         */
        private ComboBox createValidationComboBox() {
            ComboBox vCMBvalidations = new ComboBox(controller.getBundle().getString("Validation-cb-label") + " :", Arrays.asList(
                    EdsValidation.VALIDATION_NONE, EdsValidation.VALIDATION_STRICT_NOK, EdsValidation.VALIDATION_WEAK_NOK,
                    EdsValidation.VALIDATION_OK));
            vCMBvalidations.setItemCaption(EdsValidation.VALIDATION_NONE, EdsValidation.getValidationText(EdsValidation.VALIDATION_NONE));
            vCMBvalidations.setItemCaption(EdsValidation.VALIDATION_STRICT_NOK, EdsValidation.getValidationText(EdsValidation.VALIDATION_STRICT_NOK));
            vCMBvalidations.setItemCaption(EdsValidation.VALIDATION_WEAK_NOK, EdsValidation.getValidationText(EdsValidation.VALIDATION_WEAK_NOK));
            vCMBvalidations.setItemCaption(EdsValidation.VALIDATION_OK, EdsValidation.getValidationText(EdsValidation.VALIDATION_OK));
            vCMBvalidations.setTextInputAllowed(false);
            vCMBvalidations.setNullSelectionAllowed(false);
            vCMBvalidations.setInvalidAllowed(false);
            vCMBvalidations.setInvalidCommitted(false);

            return vCMBvalidations;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.Form#isValid()
     */
    @Override
    public boolean isValid() {

        return true;
    }
}
