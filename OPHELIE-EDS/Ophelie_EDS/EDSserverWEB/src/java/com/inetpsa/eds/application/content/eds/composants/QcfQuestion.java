package com.inetpsa.eds.application.content.eds.composants;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.themes.BaseTheme;
import java.util.Arrays;
import java.util.List;

/**
 * This class provide Question complementary forms question component
 * 
 * @author Geometric Ltd.
 */
public class QcfQuestion extends GridLayout implements Property.ValueChangeListener {
    /**
     * Variable to hold Label for question
     */
    private Label question;
    /**
     * Variable to hold question text
     */
    private String questiontxt;
    /**
     * Variable to hold combo box for question response
     */
    private ComboBox reponce;
    /**
     * Variable to hold value of yes/no selection
     */
    private int val;
    /**
     * Variable to hold list of for Yes/no selection
     */
    private List<String> YesNo;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of NativeBotton
     */
    private NativeButton infos;

    // PUBLIC

    /**
     * Default Constructor
     */
    public QcfQuestion() {
        init();
    }

    /**
     * parameterized constructor
     * 
     * @param question Question
     * @param controller Controller of EDS application
     */
    public QcfQuestion(String question, EdsApplicationController controller) {
        this.questiontxt = question;
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize Question complementary forms question component
     */
    private void init() {
        YesNo = Arrays.asList(controller.getBundle().getString("consolid-qcf-oui"), controller.getBundle().getString("consolid-qcf-non"));

        setColumns(3);
        setRows(1);
        setColumnExpandRatio(0, 0.8f);
        setColumnExpandRatio(1, 0.1f);
        setColumnExpandRatio(2, 0.1f);
        setWidth("100%");
        setSpacing(true);
        question = new Label(questiontxt);
        addComponent(question, 0, 0);

        reponce = new ComboBox(null, YesNo);
        reponce.addListener(this);
        reponce.setImmediate(true);
        reponce.setNullSelectionAllowed(false);
        reponce.setValue(controller.getBundle().getString("consolid-qcf-non"));
        addComponent(reponce, 1, 0);

        infos = new NativeButton();
        infos.setStyleName(BaseTheme.BUTTON_LINK);
        infos.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/help.png"));
        addComponent(infos, 2, 0);
        setComponentAlignment(infos, Alignment.TOP_LEFT);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.AbstractComponent#setDescription(java.lang.String)
     */
    @Override
    public void setDescription(String description) {
        infos.setDescription(description);
    }

    /**
     * Method to set value of Yes/No selection
     * 
     * @param val Value
     */
    public void setValue(int val) {
        setVal(val);
        if (val == 0) {
            reponce.setValue(controller.getBundle().getString("consolid-qcf-non"));
        } else {
            reponce.setValue(controller.getBundle().getString("consolid-qcf-oui"));
        }
    }

    /**
     * Method to reset combo box value
     */
    public void reset() {
        reponce.setValue(null);
    }

    /**
     * method returns value
     * 
     * @return Value
     */
    public int getVal() {
        return val;
    }

    /**
     * Method set value
     * 
     * @param value Value to set
     */
    public void setVal(int value) {
        this.val = value;
    }

    /**
     * Notifies this listener that the Property's value has changed.
     * 
     * @param event Value change event
     * @see com.vaadin.data.Property.ValueChangeListener#valueChange(com.vaadin.data.Property.ValueChangeEvent)
     */
    public void valueChange(ValueChangeEvent event) {
        if (controller.getBundle().getString("consolid-qcf-oui").equals(event.getProperty().toString())) {
            setVal(1);
        } else {
            setVal(0);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.AbstractComponentContainer#setEnabled(boolean)
     */
    @Override
    public void setEnabled(boolean enabled) {
        reponce.setEnabled(enabled);
    }
}
