package com.inetpsa.eds.application.content.eds.composants;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsWording;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * This class provide TypeAlimentation component
 * 
 * @author Geometric Ltd.
 */
public class TypeAlimentation extends VerticalLayout {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public TypeAlimentation(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of combo box for type of supply
     */
    private ComboBox vTFType;
    /**
     * Variable to hold value of TextFiled for type of supply edited by
     */
    private TextField vLTypeRenseigner;
    /**
     * Variable to hold value of TextFiled for comment
     */
    private TextField vTFTypeCom;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize TypeAlimentation
     */
    private void init() {
        vTFType = new ComboBox(controller.getBundle().getString("current-conso-list-alim-modif-type"));
        vTFType.setNullSelectionAllowed(false);
        vTFTypeCom = new TextField(controller.getBundle().getString("eds-comnent") + " :");
        vTFTypeCom.setWidth("350");
        vLTypeRenseigner = new TextField(controller.getBundle().getString("eds-renseigned-by") + " :");
        vLTypeRenseigner.setEnabled(false);

        HorizontalLayout hlType = new HorizontalLayout();
        hlType.setSpacing(true);
        hlType.addComponent(vTFType);
        hlType.addComponent(vLTypeRenseigner);
        hlType.addComponent(vTFTypeCom);

        for (EdsWording wording : EDSdao.getInstance().getAllActiveWordingsByType(EdsWording.ALIM)) {
            vTFType.addItem(wording);
            vTFType.setItemCaption(wording, wording.getValueByLocale(controller.getApplication().getLocale()));

        }

        addComponent(hlType);
    }

    /**
     * Method set comment
     * 
     * @param com Comment to be set
     */
    public void setCommentaire(String com) {
        vTFTypeCom.setValue(com);
    }

    /**
     * Method set the EdsWording
     * 
     * @param wording Object of EdsWording
     */
    public void setValue(EdsWording wording) {
        if (wording == null) {
            vTFType.setValue("");
        } else {
            vTFType.setValue(wording);
        }
    }

    /**
     * Method to set User name
     * 
     * @param user User Value
     */
    public void setUser(String user) {
        vLTypeRenseigner.setValue(user);
    }

    /**
     * Method returns Comment
     * 
     * @return Comment
     */
    public String getCommentaire() {
        if (vTFTypeCom.getValue() == null)
            return "";
        return vTFTypeCom.getValue().toString();
    }

    /**
     * Method returns EdsWording Value
     * 
     * @return EdsWording Value
     */
    public EdsWording getValue() {
        return (EdsWording) vTFType.getValue();
    }

    /**
     * This method checks if comment is visible
     * 
     * @param b Boolean value to view comment
     */
    public void viewComent(boolean b) {
        vTFTypeCom.setVisible(b);
    }

    /**
     * This method checks if User is visible
     * 
     * @param b Boolean value to view User
     */
    public void viewUser(boolean b) {
        vLTypeRenseigner.setVisible(b);
    }
}
