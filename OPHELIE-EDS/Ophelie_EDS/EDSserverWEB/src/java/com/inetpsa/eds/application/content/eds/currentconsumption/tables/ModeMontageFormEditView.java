package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import java.util.Collection;
import java.util.Collections;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.composants.MyTable;
import com.inetpsa.eds.dao.model.ConsolidateSupplyEdsTension;
import com.inetpsa.eds.dao.model.EdsModeMontage;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.inetpsa.eds.tools.EDSTools;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This abstract class provide Edit form view for Assembled Mode
 * 
 * @author Geometric Ltd.
 */
public abstract class ModeMontageFormEditView extends A_EdsEditForm implements Button.ClickListener {

    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param edsModeMontage Object of EdsModeMontage
     * @param isRemove Integer to remove table
     * @param qcf Object of EdsQcf
     * @param controller Controller of EDS application
     */
    public ModeMontageFormEditView(EdsModeMontage edsModeMontage, Integer isRemove, EdsQcf qcf, EdsApplicationController controller,
            ConsolidateSupplyEdsTension tension, Boolean bt) {
        this.tension = tension;
        this.edsModeMontage = edsModeMontage;
        this.isRemove = isRemove;
        this.qcf = qcf;
        this.controller = controller;
        this.bt = bt;
        init();
    }

    public ModeMontageFormEditView(EdsModeMontage edsModeMontage, Integer isRemove, EdsQcf qcf, EdsApplicationController controller, Boolean bt) {
        this(edsModeMontage, isRemove, qcf, controller, new ConsolidateSupplyEdsTension(), bt);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        return true;
        // throw new UnsupportedOperationException( "Not yet implemented" );
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {

        edsModeMontage.setMmedsTmoyModeMontage(EDSTools.convertStringToFloat(vTXTdonneeTmoy.getValue().toString()));
        edsModeMontage.setMmedsTmoyModeMontageComment(vTAdonneeCommentaire.getValue().toString());
        edsModeMontage.setMmedsModeMontageTitre(vTXTTitle.getValue().toString());
        edsModeMontage.setMmedsRemove(isRemove);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {

        vTXTTitle.setValue(edsModeMontage.getMmedsModeMontageTitre() == null ? "" : edsModeMontage.getMmedsModeMontageTitre());

        vTAdonneeCommentaire.setValue(edsModeMontage.getMmedsTmoyModeMontageComment() == null ? "" : edsModeMontage.getMmedsTmoyModeMontageComment());

        vTXTdonneeTmoy.setValue(edsModeMontage.getMmedsTmoyModeMontage() == null ? "" : edsModeMontage.getMmedsTmoyModeMontage());

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.singleton((Object) edsModeMontage);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete()
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
    }

    // PROTECTED
    // PRIVATE
    /**
     * Tension.
     */
    private ConsolidateSupplyEdsTension tension;
    /**
     * Variable to hold value of BT_TBT
     */
    private Boolean bt = false;
    /**
     * Variable to hold value of EdsModeMontage
     */
    private EdsModeMontage edsModeMontage;
    /**
     * Variable to hold value of Label for assembled mode
     */
    private Label vLBLmodeMontage;
    /**
     * Variable to hold value of Label for voltage
     */
    private Label vLBLdonneeTension;
    /**
     * Variable to hold value of TextField for Tmoy
     */
    private TextField vTXTdonneeTmoy;
    /**
     * Variable to hold value of TextField for title
     */
    private TextField vTXTTitle;
    /**
     * Variable to hold value of TextArea for Comment
     */
    private TextArea vTAdonneeCommentaire;
    /**
     * Variable to hold value of Remove Button
     */
    private Button vBRemove;
    /**
     * Constant to hold value of Integer to remove table
     */
    private final Integer isRemove;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsQcf
     */
    private EdsQcf qcf;

    /**
     * Initialize Edit form view for Assembled Mode
     */
    private void init() {
        vTXTTitle = new TextField();
        vTAdonneeCommentaire = new TextArea();
        vTXTdonneeTmoy = new TextField();

        setSpacing(false);
        vTXTTitle.setWidth("300");
        addComponent(vTXTTitle);

        HorizontalLayout vHlLayout = new HorizontalLayout();

        vHlLayout.addComponent(tableauModeMontage());

        if (isRemove == 1) {
            vBRemove = new Button();
            vBRemove.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/erase.png"));
            vBRemove.setStyleName(BaseTheme.BUTTON_LINK);
            vBRemove.setDescription(controller.getBundle().getString("btn-delete-tab"));
            vBRemove.addListener(this);
            vHlLayout.addComponent(vBRemove);
            vHlLayout.setComponentAlignment(vBRemove, Alignment.MIDDLE_LEFT);
            vHlLayout.setSpacing(true);
        }
        addComponent(vHlLayout);
        discardChanges();

        setSpacing(true);
        setWidth("100%");

        // separator
        addComponent(new Label("<br />", Label.CONTENT_XHTML));

    }

    /**
     * This method return Table for assembled mode
     * 
     * @return Table for assembled mode
     */
    public Table tableauModeMontage() {
        MyTable table = new MyTable();
        table.setPageLength(1);
        table.addContainerProperty("Données à renseigner", Label.class, null, controller.getBundle().getString("table-header-label-data-to-edit"),
                null, null);
        table.addContainerProperty("Sous la Tension de", Label.class, null, controller.getBundle().getString("table-label-sous-tension"), null, null);
        table.addContainerProperty("Courant sous la température Tmoy", TextField.class, null,
                controller.getBundle().getString("current-conso-tab-data-at-Tmoy"), null, null);

        vLBLmodeMontage = new Label(controller.getBundle().getString("conso-cons-mod-Montage") + " (A)");
        vLBLdonneeTension = new Label(super.getUmoy12_5(tension.getCsEdsUmoy(), bt));

        vTAdonneeCommentaire.setWidth("200px");
        vTAdonneeCommentaire.setHeight("25px");

        table.addContainerProperty(controller.getBundle().getString("eds-comnent"), TextArea.class, null);
        table.addItem(new Object[] { vLBLmodeMontage, vLBLdonneeTension, vTXTdonneeTmoy, vTAdonneeCommentaire }, new Integer(0));
        return table;
    }

    /**
     * This method returns Collection of EdsModeMontage
     * 
     * @return Collection of EdsModeMontage
     */
    public Collection<EdsModeMontage> getAllEdsModeMontage() {
        return Collections.singleton(edsModeMontage);
    }
}
