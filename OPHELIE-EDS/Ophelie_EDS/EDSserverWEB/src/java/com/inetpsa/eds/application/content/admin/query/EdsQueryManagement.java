/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.inetpsa.eds.application.content.admin.query;

import java.util.Collection;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.EDSdao;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextArea;

/**
 * @author VAUSHELL - Rabah OULD TAHAR - e360527 <rabah.ouldtahar@ext.mpsa.com>
 */
public class EdsQueryManagement extends A_EdsEditForm {
    // PUBLIC
    public EdsQueryManagement(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    private EdsApplicationController controller;
    private TextArea queryArea;
    private Button export;
    private Button execute;

    private void init() {
        export = new Button("Exporter", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
            }
        });

        export.setImmediate(true);

        addComponent(export);
        setComponentAlignment(export, Alignment.BOTTOM_RIGHT);

        queryArea = new TextArea();
        queryArea.setWidth("100%");
        queryArea.setHeight("300px");
        addComponent(queryArea);

        execute = new Button("Executer", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                EDSdao.getInstance().excuteQuery(queryArea.getValue().toString());
            }
        });

        addComponent(execute);
        setComponentAlignment(execute, Alignment.BOTTOM_LEFT);

    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean commitChanges() {
        return true;
    }

    @Override
    public void discardChanges() {
    }

    @Override
    public Collection getAllItemsToSave() {
        return null;
    }

    @Override
    public Collection getAllItemsToDelete() {
        return null;
    }

    @Override
    public void refreshItems() {
    }
}
