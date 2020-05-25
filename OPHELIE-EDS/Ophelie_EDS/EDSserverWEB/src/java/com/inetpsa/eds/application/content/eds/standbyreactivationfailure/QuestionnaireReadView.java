package com.inetpsa.eds.application.content.eds.standbyreactivationfailure;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.composants.Comment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

/**
 * This class provide Asleep/ awake failure Questions read view
 * 
 * @author Geometric Ltd.
 */
public class QuestionnaireReadView extends GridLayout {
    // PUBLIC
    /**
     * Default constructor
     */
    public QuestionnaireReadView() {
        init();
    }

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
     * Variable to hold value of Label for 'The total device failure occurrence value for the event' value
     */
    private Label vLValTotOcurence;
    /**
     * Variable to hold value of Label for 'Current consumption associated' value
     */
    private Label vLValCourant;
    /**
     * Variable to hold value of Label for 'Notes' value
     */
    private Comment vLValCom;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Parameterized constructor
     * 
     * @param occurence Occurrence
     * @param courant Current
     * @param commentaire comment
     * @param controller Controller of EDS application
     */
    QuestionnaireReadView(String occurence, String courant, String commentaire, EdsApplicationController controller) {
        this.controller = controller;
        init();
        setTotOcurence(occurence);
        setCourant(courant);
        setCom(commentaire);
    }

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    QuestionnaireReadView(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /**
     * Initialize Asleep/ awake failure Questions read view
     */
    private void init() {

        setColumns(2);
        setRows(6);
        setMargin(true);
        setWidth(" 100% ");

        setColumnExpandRatio(0, 0.3f);
        setColumnExpandRatio(1, 0.7f);

        vLTotOcurence = new Label(controller.getBundle().getString("Def-V-R-Occ") + "  : ");
        vLTotOcurence.addStyleName("h2");
        addComponent(vLTotOcurence, 0, 0, 1, 0);

        vLValTotOcurence = new Label();
        addComponent(vLValTotOcurence, 0, 1);

        vLCourant = new Label(controller.getBundle().getString("Def-V-R-current") + "  : ");
        vLCourant.addStyleName("h2");
        addComponent(vLCourant, 0, 2, 1, 2);

        vLValCourant = new Label();
        addComponent(vLValCourant, 0, 3);

        vLCom = new Label(controller.getBundle().getString("eds-comnent") + " :   ");
        vLCom.addStyleName("h2");
        addComponent(vLCom, 0, 4, 1, 4);

        vLValCom = new Comment(controller);

        addComponent(vLValCom, 0, 5, 1, 5);

    }

    /**
     * This method set the comment
     * 
     * @param com Comment to set
     */
    private void setCom(String com) {
        if (com == null) {
            com = "--";
        }
        this.vLValCom.setValue(com);
    }

    /**
     * This method sets 'The total device failure occurrence value for the event' value
     * 
     * @param TotOcurence 'The total device failure occurrence value for the event' value to set
     */
    private void setTotOcurence(String TotOcurence) {
        if (TotOcurence == null) {
            TotOcurence = "--";
        }
        this.vLValTotOcurence.setValue(TotOcurence + controller.getBundle().getString("Def-V-R-ppm"));
    }

    /**
     * This method sets 'Current consumption associated' value
     * 
     * @param courant 'Current consumption associated' value
     */
    private void setCourant(String courant) {
        if (courant == null) {
            courant = "--";
        }
        this.vLValCourant.setValue(courant + " mA");
    }

    /**
     * This method set the value for Questions of asleep and awake failure
     * 
     * @param ocurence Occurrence
     * @param courant current
     * @param commentaire comment
     */
    public void setValue(String ocurence, String courant, String commentaire) {
        try {
            setTotOcurence(ocurence);
            setCourant(courant);
            setCom(commentaire);
        } catch (NullPointerException e) {
        }

    }

    /**
     * This method shows the question edit view with current based on boolean value specified
     * 
     * @param b check for show current
     */
    public void withCourant(Boolean b) {
        vLValCourant.setVisible(b);
        vLCourant.setVisible(b);
    }
}
