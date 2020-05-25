/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.inetpsa.eds.application.popup;

import java.text.MessageFormat;
import java.util.UUID;

import com.inetpsa.eds.application.A_EdsWindow;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsRole;
import com.inetpsa.eds.dao.model.EdsRoleType;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Alter SOLUTIONS - Rabah OULD TAHAR - e360527 <rabah.ouldtahar@ext.mpsa.com>
 */
public class EditRoleWindow extends A_EdsWindow {
    // PUBLIC
    public EditRoleWindow(EdsRole edsRole, EdsApplicationController controller, ValidateListener listener) {
        super(controller.getBundle().getString("Admin-acc-role-rename"));
        this.controller = controller;
        this.listener = listener;
        this.edsRole = edsRole;
        init();
    }

    public static abstract class ValidateListener {
        public abstract void onValidate(String newValue, int roType);
    }

    // PROTECTED
    // PRIVATE
    private ValidateListener listener;
    private EdsApplicationController controller;
    private EdsRole edsRole;

    private void init() {
        VerticalLayout mainLayout = (VerticalLayout) getContent();
        mainLayout.setMargin(true);
        GridLayout contentLayout = new GridLayout(2, 3);
        contentLayout.setSpacing(true);

        final TextField vTFstringValue = new TextField();
        vTFstringValue.setValue(edsRole.getRoName());
        vTFstringValue.setRequired(true);
        vTFstringValue.setRequiredError(MessageFormat.format(controller.getBundle().getString("eds-field-not-empty"), controller.getBundle()
                .getString("Admin-acc-role-new")));
        contentLayout.addComponent(new Label(controller.getBundle().getString("Admin-acc-role-new") + " :"));

        contentLayout.addComponent(vTFstringValue, 1, 0);

        final ComboBox vCMType = new ComboBox();
        EdsRoleType partenaire = new EdsRoleType(UUID.randomUUID().toString(), "Partenaire", EdsRole.TYPE_PERIMETER);
        vCMType.addItem(partenaire);

        EdsRoleType fournisseur = new EdsRoleType(UUID.randomUUID().toString(), "Fourniseur", EdsRole.TYPE_SUPPLIER);
        vCMType.addItem(fournisseur);

        switch (edsRole.getRoType()) {
        case EdsRole.TYPE_SUPPLIER:
            vCMType.setValue(fournisseur);
            break;
        case EdsRole.TYPE_PERIMETER:
            vCMType.setValue(partenaire);
            break;
        }

        contentLayout.addComponent(new Label(controller.getBundle().getString("Admin-acc-role-type") + " :"), 0, 1);
        contentLayout.addComponent(vCMType, 1, 1);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setSpacing(true);

        Button okButton = new Button(controller.getBundle().getString("Admin-proj-validate-button"));
        okButton.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                if (vTFstringValue.isValid()) {
                    int roType = ((EdsRoleType) vCMType.getValue()) == null ? 0 : ((EdsRoleType) vCMType.getValue()).getType();
                    listener.onValidate(vTFstringValue.getValue().toString(), roType);
                    close();
                } else {
                    showNotification(controller.getBundle().getString("eds-field-error"));
                }
            }
        });
        buttonsLayout.addComponent(okButton);

        Button cancelButton = new Button(controller.getBundle().getString("button-cancel"));
        cancelButton.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                close();
            }
        });
        buttonsLayout.addComponent(cancelButton);

        contentLayout.addComponent(buttonsLayout, 1, 2);
        contentLayout.setComponentAlignment(okButton, Alignment.TOP_RIGHT);

        mainLayout.addComponent(contentLayout);
        mainLayout.setExpandRatio(contentLayout, 1);
        mainLayout.setComponentAlignment(contentLayout, Alignment.MIDDLE_CENTER);
        mainLayout.setSizeUndefined();

        setResizable(false);
    }
}
