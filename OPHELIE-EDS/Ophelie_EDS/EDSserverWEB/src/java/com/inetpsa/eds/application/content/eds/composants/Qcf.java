package com.inetpsa.eds.application.content.eds.composants;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

/**
 * This class provide Question complementary forms component
 * 
 * @author Geometric Ltd.
 */
public class Qcf extends Panel {
    // PUBLIC
    /**
     * Parameterized Constructor
     * 
     * @param controller Controller of EDS application
     */
    public Qcf(EdsApplicationController controller) {
        this.controller = controller;
        setCaption((controller.getBundle().getString("consolid-qcf-titre")));
        init();
    }

    /**
     * Parameterized Constructor
     * 
     * @param controller Controller of EDS application
     * @param qcf Eds Question complementary forms
     */
    public Qcf(EdsApplicationController controller, EdsQcf qcf) {
        this.controller = controller;
        this.qcf = qcf;
        setCaption((controller.getBundle().getString("consolid-qcf-titre")));
        init();
    }

    /**
     * Parameterized Constructor
     * 
     * @param controller Controller of EDS application
     * @param qcf Eds Question complementary forms
     * @param listener Button click listener
     */
    public Qcf(EdsApplicationController controller, EdsQcf qcf, Button.ClickListener listener) {
        this.controller = controller;
        this.listener = listener;
        this.qcf = qcf;
        setCaption((controller.getBundle().getString("consolid-qcf-titre")));
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of Controller of Eds application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of GridLayout for Question complementary forms
     */
    private GridLayout qcfLayout;
    /**
     * Variable to hold value of QcfQuestion for Question complementary forms 1
     */
    private QcfQuestion qCf1;
    /**
     * Variable to hold value of QcfQuestion for Question complementary forms2
     */
    private QcfQuestion qCf2;
    /**
     * Variable to hold value of QcfQuestion for Question complementary forms3
     */
    private QcfQuestion qCf3;
    /**
     * Variable to hold value of QcfQuestion for Complementary Question complementary forms1
     */
    private QcfQuestion qCf1Comp;
    /**
     * Variable to hold value of QcfQuestion for Complementary Question complementary forms2
     */
    private QcfQuestion qCf2Comp;
    /**
     * Variable to hold value of QcfQuestion for Complementary Question complementary forms3
     */
    private QcfQuestion qCf3Comp;
    /**
     * Variable to hold value of QcfQuestion for Complementary Question complementary forms4
     */
    private QcfQuestion qCf4Comp;
    /**
     * Variable to hold value for Button to set question complementary forms
     */
    private Button vBSetQCF;
    /**
     * Variable to hold value for EdsQcf
     */
    private EdsQcf qcf;
    /**
     * Variable to hold value for Button click listener
     */
    private Button.ClickListener listener;

    /**
     * Initialize Question complementary forms
     */
    private void init() {

        qcfLayout = new GridLayout(1, 10);

        qcfLayout.setSpacing(true);
        qcfLayout.setWidth("100%");

        Label vLGeneral = new Label(controller.getBundle().getString("consolid-qcf-general"));
        vLGeneral.addStyleName("h2");
        qcfLayout.addComponent(vLGeneral, 0, 0);

        qCf1 = new QcfQuestion(controller.getBundle().getString("consolid-qcf1"), controller);
        qCf1.setDescription(controller.getBundle().getString("consolid-qcf1-tooltip"));
        qcfLayout.addComponent(qCf1, 0, 1);

        qCf2 = new QcfQuestion(controller.getBundle().getString("consolid-qcf2"), controller);
        qCf2.setDescription(controller.getBundle().getString("consolid-qcf2-tooltip"));
        qcfLayout.addComponent(qCf2, 0, 2);

        qCf3 = new QcfQuestion(controller.getBundle().getString("consolid-qcf3"), controller);

        qCf3.setDescription(controller.getBundle().getString("consolid-qcf3-tooltip"));
        qcfLayout.addComponent(qCf3, 0, 3);

        Label vLCompl = new Label(controller.getBundle().getString("consolid-qcf-complementaire"));
        vLCompl.addStyleName("h2");
        qcfLayout.addComponent(vLCompl, 0, 4);

        qCf1Comp = new QcfQuestion(controller.getBundle().getString("consolid-qcf4"), controller);
        qCf1Comp.setDescription(controller.getBundle().getString("consolid-qcf4-tooltip"));
        qcfLayout.addComponent(qCf1Comp, 0, 5);

        qCf2Comp = new QcfQuestion(controller.getBundle().getString("consolid-qcf5"), controller);
        qCf2Comp.setDescription(controller.getBundle().getString("consolid-qcf5-tooltip"));
        qcfLayout.addComponent(qCf2Comp, 0, 6);

        qCf3Comp = new QcfQuestion(controller.getBundle().getString("consolid-qcf6"), controller);
        qCf3Comp.setDescription(controller.getBundle().getString("consolid-qcf6-tooltip"));
        qcfLayout.addComponent(qCf3Comp, 0, 7);

        qCf4Comp = new QcfQuestion(controller.getBundle().getString("consolid-qcf7"), controller);
        qCf4Comp.setDescription(controller.getBundle().getString("consolid-qcf7-tooltip"));
        qcfLayout.addComponent(qCf4Comp, 0, 8);

        vBSetQCF = new Button("Valider");
        if (listener != null) {
            vBSetQCF.addListener(listener);
        }

        qcfLayout.addComponent(vBSetQCF, 0, 9);
        qcfLayout.setComponentAlignment(vBSetQCF, Alignment.MIDDLE_RIGHT);
        addComponent(qcfLayout);
    }

    /**
     * This method set Question complementary forms editable based on boolean value
     * 
     * @param b value to set editable or not
     */
    public void setEditable(boolean b) {
        vBSetQCF.setVisible(b);
        qCf1.setEnabled(b);
        qCf2.setEnabled(b);
        qCf3.setEnabled(b);
        qCf1Comp.setEnabled(b);
        qCf2Comp.setEnabled(b);
        qCf3Comp.setEnabled(b);
        qCf4Comp.setEnabled(b);

    }

    /**
     * This method set Question complementary forms selected based on boolean value
     * 
     * @param b value to set selected or not
     */
    public void setSelected(Boolean b) {
        if (b) {
            qCf1.setValue(1);
            qCf2.setValue(1);
            qCf3.setValue(1);
            qCf1Comp.setValue(1);
            qCf2Comp.setValue(1);
            qCf3Comp.setValue(1);
            qCf4Comp.setValue(1);
        }
    }

    /**
     * This method set Question complementary forms value
     * 
     * @param qcf Eds Question complementary forms details
     */
    public void setValue(EdsQcf qcf) {
        qCf1.setValue(qcf.getQcf1());
        qCf2.setValue(qcf.getQcf2());
        qCf3.setValue(qcf.getQcf3());
        qCf1Comp.setValue(qcf.getQcfC1());
        qCf2Comp.setValue(qcf.getQcfC2());
        qCf3Comp.setValue(qcf.getQcfC3());
        qCf4Comp.setValue(qcf.getQcfC4());
    }

    /**
     * This method save values of Question complementary forms
     */
    public void commit() {
        qcf.setQcf1(qCf1.getVal());
        qcf.setQcf2(qCf2.getVal());
        qcf.setQcf3(qCf3.getVal());
        qcf.setQcfC1(qCf1Comp.getVal());
        qcf.setQcfC2(qCf2Comp.getVal());
        qcf.setQcfC3(qCf3Comp.getVal());
        qcf.setQcfC4(qCf4Comp.getVal());

    }

    /**
     * This method revert back original values of Question complementary forms
     */
    public void discardChanges() {

        qCf1.setValue(qcf.getQcf1());
        qCf2.setValue(qcf.getQcf2());
        qCf3.setValue(qcf.getQcf3());
        qCf1Comp.setValue(qcf.getQcfC1());
        qCf2Comp.setValue(qcf.getQcfC2());
        qCf3Comp.setValue(qcf.getQcfC3());
        qCf4Comp.setValue(qcf.getQcfC4());
    }

    /**
     * This method update the values of specified Eds Question complementary forms
     * 
     * @param qcfConsolide Eds Question complementary forms to be updated
     */
    public void update(EdsQcf qcfConsolide) {
        qcfConsolide.setQcf1(qCf1.getVal());
        qcfConsolide.setQcf2(qCf2.getVal());
        qcfConsolide.setQcf3(qCf3.getVal());
        qcfConsolide.setQcfC1(qCf1Comp.getVal());
        qcfConsolide.setQcfC2(qCf2Comp.getVal());
        qcfConsolide.setQcfC3(qCf3Comp.getVal());
        qcfConsolide.setQcfC4(qCf4Comp.getVal());
    }
}
