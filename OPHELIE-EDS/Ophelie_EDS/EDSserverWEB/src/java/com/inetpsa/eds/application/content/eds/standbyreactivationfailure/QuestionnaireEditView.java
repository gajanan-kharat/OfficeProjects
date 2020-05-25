package com.inetpsa.eds.application.content.eds.standbyreactivationfailure;

import com.inetpsa.eds.application.EdsApplicationController;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.validator.DoubleValidator;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provide Asleep/ awake failure Questions edit view
 * 
 * @author Geometric Ltd.
 */
public class QuestionnaireEditView extends GridLayout implements Property.ValueChangeListener {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public QuestionnaireEditView(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold list of String with yes/ no option
     */
    private static final List<String> yesNo = Arrays.asList(new String[] { "OUI", "NON" });
    /**
     * Variable to hold value of GridLayout
     */
    GridLayout mainLayout;
    /**
     * Variable to hold value of OptionGroup
     */
    private OptionGroup vOGYesNo;
    /**
     * Variable to hold value of Label for 'The total device failure occurrence value for the event'
     */
    private Label vLTotOcurence;
    /**
     * Variable to hold value of Label for 'Current consumption associated'
     */
    private Label vLCourant;
    /**
     * Variable to hold value of Label for 'Notes'
     */
    private Label vLCom;
    /**
     * Variable to hold value of Label for 'mA'
     */
    private Label unitA;
    /**
     * Variable to hold value of TextField for 'The total device failure occurrence value for the event'
     */
    private TextField vTXTTotOcurence;
    /**
     * Variable to hold value of TextField for 'Current consumption associated'
     */
    private TextField vTXTCourant;
    /**
     * Variable to hold value of TextArea for 'Notes'
     */
    private TextArea vTACom;
    /**
     * Variable to hold value of Map of String to save the value
     */
    private Map<String, String> savedValue;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize Asleep/ awake failure Questions edit view
     */
    private void init() {

        savedValue = new HashMap<String, String>();

        setColumns(1);
        setRows(2);
        setSpacing(true);
        setMargin(true);
        setWidth("100% ");

        vOGYesNo = new OptionGroup("", yesNo);
        vOGYesNo.setImmediate(true);
        vOGYesNo.select("NON");
        vOGYesNo.addStyleName("horizontal");
        vOGYesNo.addListener(this);

        addComponent(vOGYesNo, 0, 0);

        mainLayout = new GridLayout(2, 6);
        mainLayout.setSpacing(true);
        mainLayout.setColumnExpandRatio(0, 0f);
        mainLayout.setColumnExpandRatio(1, 1f);

        vLTotOcurence = new Label(controller.getBundle().getString("Def-V-R-Occ") + " : ");
        mainLayout.addComponent(vLTotOcurence, 0, 0, 1, 0);

        vTXTTotOcurence = new TextField();
        vTXTTotOcurence.setWidth("250px");
        vTXTTotOcurence.addValidator(new DoubleValidator(controller.getBundle().getString("eds-number-error")));
        mainLayout.addComponent(vTXTTotOcurence, 0, 1);
        Label unitPPM = new Label(controller.getBundle().getString("Def-V-R-ppm"));
        unitPPM.addStyleName("h3");
        mainLayout.addComponent(unitPPM, 1, 1);
        vLCourant = new Label(controller.getBundle().getString("Def-V-R-current") + " : ");
        mainLayout.addComponent(vLCourant, 0, 2, 1, 2);

        vTXTCourant = new TextField();
        vTXTCourant.addValidator(new DoubleValidator(controller.getBundle().getString("eds-number-error")));
        vTXTCourant.setWidth("250px");
        mainLayout.addComponent(vTXTCourant, 0, 3);
        unitA = new Label("mA");
        unitA.addStyleName("h3");
        mainLayout.addComponent(unitA, 1, 3);

        vLCom = new Label(controller.getBundle().getString("eds-comnent") + " :   ");
        mainLayout.addComponent(vLCom, 0, 4, 1, 4);

        vTACom = new TextArea();
        vTACom.setWidth("600px");
        mainLayout.addComponent(vTACom, 0, 5, 1, 5);
        setActive(false);
        addComponent(mainLayout, 0, 1);
    }

    /**
     * This class set the question view active based on specified boolean value
     * 
     * @param b Boolean to set question edit view active
     */
    public void setActive(boolean b) {
        if (b) {
            mainLayout.setEnabled(true);
            vOGYesNo.select("OUI");
            vTACom.setValue(savedValue.get("comentaire") == null ? "" : savedValue.get("comentaire"));
            vTXTCourant.setValue(savedValue.get("courant") == null ? "" : savedValue.get("courant"));
            vTXTTotOcurence.setValue(savedValue.get("ocurence") == null ? "" : savedValue.get("ocurence"));
        } else {
            mainLayout.setEnabled(false);
            vTACom.setValue("");
            vTXTCourant.setValue("");
            vTXTTotOcurence.setValue("");
            vOGYesNo.select("NON");
        }
    }

    /**
     * Notifies this listener that the Property's value has changed.
     * 
     * @param event value change event object
     * @see com.vaadin.data.Property.ValueChangeListener#valueChange(com.vaadin.data.Property.ValueChangeEvent)
     */
    public void valueChange(ValueChangeEvent event) {
        String selected = "" + event.getProperty();
        if (selected.equals("NON")) {
            setActive(false);
        } else {
            setActive(true);
        }
    }

    /**
     * This method returns the comment
     * 
     * @return comment
     */
    public String getCom() {
        return vTACom.getValue() + "";
    }

    /**
     * This method set the comment
     * 
     * @param com Comment to set
     */
    public void setCom(String com) {
        if (com == null) {
            com = "";
        }
        this.vTACom.setValue(com);
        savedValue.put("comentaire", com);
    }

    /**
     * This method returns 'The total device failure occurrence value for the event' value
     * 
     * @return 'The total device failure occurrence value for the event' value
     */
    public String getTotOcurence() {
        return vTXTTotOcurence.getValue() + "";
    }

    /**
     * This method sets 'The total device failure occurrence value for the event' value
     * 
     * @param TotOcurence 'The total device failure occurrence value for the event' value to set
     */
    public void setTotOcurence(String TotOcurence) {
        if (TotOcurence == null) {
            TotOcurence = "";
        }
        this.vTXTTotOcurence.setValue(TotOcurence);
        savedValue.put("ocurence", TotOcurence);
    }

    /**
     * This method returns 'Current consumption associated' value
     * 
     * @return 'Current consumption associated' value
     */
    public String getCourant() {
        return vTXTCourant.getValue() + "";
    }

    /**
     * This method sets 'Current consumption associated' value
     * 
     * @param courant 'Current consumption associated' value
     */
    public void setCourant(String courant) {
        if (courant == null) {
            courant = "";
        }
        this.vTXTCourant.setValue(courant);
        savedValue.put("courant", courant);
    }

    /**
     * This method validates the value entered for 'The total device failure occurrence value for the event'
     * 
     * @return check if value is valid
     */
    public Boolean validate() {

        if (mainLayout.isEnabled()) {
            if (!vTXTTotOcurence.isValid()) {
                return false;
            }
            if (!vTXTCourant.isValid() && vTXTCourant.isVisible()) {
                return false;
            }
        }

        return true;
    }

    /**
     * This method set the value for Questions of asleep and awake failure
     * 
     * @param ocurence Occurrence
     * @param courant current
     * @param commentaire comment
     */
    void setValue(String ocurence, String courant, String commentaire) {
        try {
            setTotOcurence(ocurence);
            setCourant(courant);
            setCom(commentaire);
        } catch (NullPointerException e) {
        }

    }

    /**
     * This method checks if Main layout is active
     * 
     * @return Check if main layout is active
     */
    public Integer isActive() {

        if (mainLayout.isEnabled()) {
            return 1;
        }
        return 0;

    }

    /**
     * This method shows the question edit view with current based on boolean value specified
     * 
     * @param b check for show current
     */
    public void withCourant(Boolean b) {
        vTXTCourant.setVisible(b);
        vLCourant.setVisible(b);
        unitA.setVisible(b);
    }
}
