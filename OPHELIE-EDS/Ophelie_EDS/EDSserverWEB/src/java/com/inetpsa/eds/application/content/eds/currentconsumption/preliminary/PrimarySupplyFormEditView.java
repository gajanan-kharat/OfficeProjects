package com.inetpsa.eds.application.content.eds.currentconsumption.preliminary;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.composants.TypeAlimentation;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.ConsumptionTableEdit;
import com.inetpsa.eds.dao.model.EdsPrimarySupply;
import com.inetpsa.eds.dao.model.EdsSupply;
import com.inetpsa.eds.tools.EDSTools;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window.Notification;

/**
 * This class provide component for editing view of Current consumption supply form of preliminary stage
 * 
 * @author Geometric Ltd.
 */
public class PrimarySupplyFormEditView extends A_EdsEditForm {
    // PUBLIC
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     * @param edsPrimarySupply Object of EdsPrimarySupply
     */
    public PrimarySupplyFormEditView(EdsApplicationController controller, EdsPrimarySupply edsPrimarySupply) {
        this.edsPrimarySupply = edsPrimarySupply;
        this.controller = controller;
        init();
    }

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     * @param edsSupply Object of EdsSupply
     * @param edsPrimarySupply Object of EdsPrimarySupply
     */
    public PrimarySupplyFormEditView(EdsApplicationController controller, EdsSupply edsSupply, EdsPrimarySupply edsPrimarySupply) {
        this.edsSupply = edsSupply;
        this.edsPrimarySupply = edsPrimarySupply;
        this.controller = controller;
        init();

    }

    /**
     * This method returns EdsSupply
     * 
     * @return EdsSupply
     */
    public EdsSupply getEdsSupply() {
        return edsSupply;
    }

    /**
     * This method returns EdsPrimarySupply
     * 
     * @return EdsPrimarySupply
     */
    public EdsPrimarySupply getEdsPrimarySupply() {
        return edsPrimarySupply;
    }

    // PRIVATE
    /**
     * Variable to hold value of EdsPrimarySupply
     */
    private EdsPrimarySupply edsPrimarySupply;
    /**
     * Variable to hold value of EdsSupply
     */
    private EdsSupply edsSupply;
    /**
     * Variable to hold value of TextField for power supply name
     */
    private TextField vTFName;
    /**
     * Variable to hold value of Label for power supply name
     */
    private Label vLNameRenseigner;
    /**
     * Variable to hold value of TextField for comment
     */
    private TextField vTFNameCom;
    /**
     * Variable to hold value of TypeAlimentation
     */
    private TypeAlimentation typeAlimentation;
    // private TextField vTFType;
    // private Label vLTypeRenseigner;
    // private TextField vTFTypeCom;
    /**
     * Variable to hold value of TextField for Isleep(A)
     */
    private TextField vTFIveille;
    /**
     * Variable to hold value of Lable for Isleep(A)
     */
    private Label vLIveilleRenseigner;
    /**
     * Variable to hold value of TextField for Isleep(A) comment
     */
    private TextField vTFIveilleCom;
    /**
     * Variable to hold value of TextField for Iawake mode non functioning mode(A)
     */
    private TextField vTFIreveille;
    /**
     * Variable to hold value of Lable for Iawake mode non functioning mode(A)
     */
    private Label vLIreveilleRenseigner;
    /**
     * Variable to hold value of TextField for Iawake mode non functioning mode(A) comment
     */
    private TextField vTFIreveilleCom;
    /**
     * Variable to hold value of TextField for Inom Stab (A)
     */
    private TextField vTFInomStab;
    /**
     * Variable to hold value of Label for Inom Stab (A)
     */
    private Label vLInomStabRenseigner;
    /**
     * Variable to hold value of TextField for Inom Stab (A) comment
     */
    private TextField vTFInomStabCom;
    /**
     * Variable to hold value of TextField for Iworst stab (A)
     */
    private TextField vTFIpirecas;
    /**
     * Variable to hold value of Label for Iworst stab (A)
     */
    private Label vLIpirecasRenseigner;
    /**
     * Variable to hold value of TextField for Iworst stab (A) comment
     */
    private TextField vTFIpirecasCom;
    /**
     * Variable to hold value of ConsumptionTableEdit
     */
    private ConsumptionTableEdit table;
    /**
     * Variable to hold value of String specifying User
     */
    private String user;

    /**
     * Initialize component for editing view of Current consumption supply form of preliminary stage
     */
    private void init() {

        this.user = controller.getAuthenticatedUser().getUFirstname() + " " + controller.getAuthenticatedUser().getULastname();

        setSpacing(true);
        setMargin(true);

        table = new ConsumptionTableEdit(controller);
        table.setPageLength(6);
        table.setWidth("100%");

        vTFName = new TextField();
        vTFName.setEnabled(false);
        vTFNameCom = new TextField();
        vLNameRenseigner = new Label();

        // vTFType = new TextField();
        // vTFTypeCom = new TextField();
        // vLTypeRenseigner = new Label();

        vTFIveille = new TextField();
        vTFIveilleCom = new TextField();
        vLIveilleRenseigner = new Label();

        vTFIreveille = new TextField();
        vTFIreveilleCom = new TextField();
        vLIreveilleRenseigner = new Label();

        vTFInomStab = new TextField();
        vTFInomStabCom = new TextField();
        vLInomStabRenseigner = new Label();

        vTFIpirecas = new TextField();
        vTFIpirecasCom = new TextField();
        vLIpirecasRenseigner = new Label();

        vTFNameCom.setMaxLength(128);
        // vTFTypeCom.setMaxLength( 128 );
        vTFIveilleCom.setMaxLength(128);
        vTFIreveilleCom.setMaxLength(128);
        vTFInomStabCom.setMaxLength(128);
        vTFIpirecasCom.setMaxLength(128);

        typeAlimentation = new TypeAlimentation(controller);
        addComponent(typeAlimentation);

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-list-alim-modif-name"), vTFName, vLNameRenseigner,
                vTFNameCom);

        // table.addOrderToContainer( table ,
        // "Type d'alimentation" ,
        // vTFType ,
        // vLTypeRenseigner ,
        // vTFTypeCom );

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-prem-veil"), vTFIveille, vLIveilleRenseigner,
                vTFIveilleCom);

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-prem-rev"), vTFIreveille, vLIreveilleRenseigner,
                vTFIreveilleCom);

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-prem-nom"), vTFInomStab, vLInomStabRenseigner,
                vTFInomStabCom);

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-prem-pire"), vTFIpirecas, vLIpirecasRenseigner,
                vTFIpirecasCom);

        discardChanges();

        addComponent(table);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {

        if (isValid()) {

            edsPrimarySupply.setPsedsSupplyName(vTFName.getValue().toString());
            edsPrimarySupply.setPsedsSupplyNameComment(vTFNameCom.getValue().toString());
            edsPrimarySupply.setPsedsSupplyTypeSupplyNameComment(typeAlimentation.getCommentaire());
            edsPrimarySupply.setPsedsIVeilleComment(vTFIveilleCom.getValue().toString());
            edsPrimarySupply.setPsedsReveilleComment(vTFIreveilleCom.getValue().toString());
            edsPrimarySupply.setPsedsINomStabComment(vTFInomStabCom.getValue().toString());
            edsPrimarySupply.setPsedsIPirecasComment(vTFIpirecasCom.getValue().toString());

            edsPrimarySupply.setWording(typeAlimentation.getValue());
            edsPrimarySupply.setPsedsIVeille(EDSTools.convertStringToFloat(vTFIveille.getValue().toString()));
            edsPrimarySupply.setPsedsIReveilleInactif(EDSTools.convertStringToFloat(vTFIreveille.getValue().toString()));
            edsPrimarySupply.setPsedsINomStab(EDSTools.convertStringToFloat(vTFInomStab.getValue().toString()));
            edsPrimarySupply.setPsedsIPirecasStab(EDSTools.convertStringToFloat(vTFIpirecas.getValue().toString()));

            return true;
        } else {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {

        edsPrimarySupply.setCurentUser(this.user);
        vTFName.setValue(edsPrimarySupply.getPsedsSupplyName() == null ? "" : edsPrimarySupply.getPsedsSupplyName());
        vTFNameCom.setValue(edsPrimarySupply.getPsedsSupplyNameComment() == null ? "" : edsPrimarySupply.getPsedsSupplyNameComment());
        vLNameRenseigner.setValue(edsPrimarySupply.getPsedsSupplyNameIformBy() == null ? "" : edsPrimarySupply.getPsedsSupplyNameIformBy());

        typeAlimentation.setValue(edsPrimarySupply.getWording());
        typeAlimentation.setCommentaire(edsPrimarySupply.getPsedsSupplyTypeSupplyNameComment() == null ? "" : edsPrimarySupply
                .getPsedsSupplyTypeSupplyNameComment());
        typeAlimentation.setUser(edsPrimarySupply.getPsedsSupplyTypeSupplyNameIformBy() == null ? "" : edsPrimarySupply
                .getPsedsSupplyTypeSupplyNameIformBy());

        vTFIveille.setValue(edsPrimarySupply.getPsedsIVeille() == null ? "" : edsPrimarySupply.getPsedsIVeille());
        vTFIveilleCom.setValue(edsPrimarySupply.getPsedsIVeilleComment() == null ? "" : edsPrimarySupply.getPsedsIVeilleComment());
        vLIveilleRenseigner.setValue(edsPrimarySupply.getPsedsIVeilleIformBy() == null ? "" : edsPrimarySupply.getPsedsIVeilleIformBy());

        vTFIreveille.setValue(edsPrimarySupply.getPsedsIReveilleInactif() == null ? "" : edsPrimarySupply.getPsedsIReveilleInactif());
        vTFIreveilleCom.setValue(edsPrimarySupply.getPsedsReveilleComment() == null ? "" : edsPrimarySupply.getPsedsReveilleComment());
        vLIreveilleRenseigner.setValue(edsPrimarySupply.getPsedsReveilleIformBy() == null ? "" : edsPrimarySupply.getPsedsReveilleIformBy());

        vTFInomStab.setValue(edsPrimarySupply.getPsedsINomStab() == null ? "" : edsPrimarySupply.getPsedsINomStab());
        vTFInomStabCom.setValue(edsPrimarySupply.getPsedsINomStabComment() == null ? "" : edsPrimarySupply.getPsedsINomStabComment());
        vLInomStabRenseigner.setValue(edsPrimarySupply.getPsedsINomStabIformBy() == null ? "" : edsPrimarySupply.getPsedsINomStabIformBy());

        vTFIpirecas.setValue(edsPrimarySupply.getPsedsIPirecasStab() == null ? "" : edsPrimarySupply.getPsedsIPirecasStab());
        vTFIpirecasCom.setValue(edsPrimarySupply.getPsedsIPirecasComment() == null ? "" : edsPrimarySupply.getPsedsIPirecasComment());
        vLIpirecasRenseigner.setValue(edsPrimarySupply.getPsedsIPirecasStabIformBy() == null ? "" : edsPrimarySupply.getPsedsIPirecasStabIformBy());

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        ArrayList<Object> list = new ArrayList<Object>();
        list.add(edsPrimarySupply);

        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        try {
            Float.parseFloat(vTFIveille.getValue().toString().equals("") ? "0" : vTFIveille.getValue().toString());
            Float.parseFloat(vTFIreveille.getValue().toString().equals("") ? "0" : vTFIreveille.getValue().toString());
            Float.parseFloat(vTFInomStab.getValue().toString().equals("") ? "0" : vTFInomStab.getValue().toString());
            Float.parseFloat(vTFIpirecas.getValue().toString().equals("") ? "0" : vTFIpirecas.getValue().toString());
        } catch (NumberFormatException e) {
            showNotification(controller.getBundle().getString("eds-format-nombre-title"),
                    MessageFormat.format(controller.getBundle().getString("alim-format-nombre-msg"), this.toString()));
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete ()
     */
    @Override
    public Collection getAllItemsToDelete() {
        return Collections.EMPTY_SET;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        // DO NOTHING
    }

    /**
     * This method shows notification
     * 
     * @param title Title of notification
     * @param message Message of notification
     */
    private void showNotification(String title, String message) {
        getWindow().showNotification(title, message, Notification.TYPE_ERROR_MESSAGE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return edsPrimarySupply.toString();
    }
}
