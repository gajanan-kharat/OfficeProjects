package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import java.util.Collection;
import java.util.Collections;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.composants.MyTable;
import com.inetpsa.eds.application.content.eds.composants.MyTextArea;
import com.inetpsa.eds.application.content.eds.composants.MyTextField;
import com.inetpsa.eds.dao.model.ConsolidateSupplyEdsTension;
import com.inetpsa.eds.dao.model.EdsModeParc;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

/**
 * This class provide Read form view for Park Mode
 * 
 * @author Geometric Ltd.
 */
public class ModeParcFormEditView extends A_EdsEditForm {

    /**
     * Parameterized constructor
     * 
     * @param qcf Object of EdsQcf
     * @param edsModeParc Object of EdsModeParc
     * @param controller Controller of EDS application
     */
    public ModeParcFormEditView(EdsQcf qcf, EdsModeParc edsModeParc, EdsApplicationController controller, ConsolidateSupplyEdsTension tension,
            Boolean bt) {
        this.tension = tension;
        this.edsModeParc = edsModeParc;
        this.qcf = qcf;
        this.controller = controller;
        this.bt = bt;
        init();
    }

    public ModeParcFormEditView(EdsQcf qcf, EdsModeParc edsModeParc, EdsApplicationController controller, Boolean bt) {
        this(qcf, edsModeParc, controller, new ConsolidateSupplyEdsTension(), bt);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        if (vTXTTmoy.isFloat() && vTXTTmax.isFloat()) {
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        // edsModeParc.setMpedsQcf1( ConditionTemperature );
        resetQcf();
        edsModeParc.setMpedsTmoyModeParc(vTXTTmoy.getFloat());
        edsModeParc.setMpedsTmaxModeParc(vTXTTmax.getFloat());
        edsModeParc.setMpedsTmoyModeParcComment(vTACommentaire.getText());

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {

        vTXTTmoy.setValue(edsModeParc.getMpedsTmoyModeParc());
        vTXTTmax.setValue(edsModeParc.getMpedsTmaxModeParc());
        vTACommentaire.setValue(edsModeParc.getMpedsTmoyModeParcComment());

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.singleton((Object) edsModeParc);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Tenstion
     */
    private ConsolidateSupplyEdsTension tension;
    /**
     * Variable to hold value of BT_TBT
     */
    private Boolean bt = false;

    /**
     * Variable to hold value of EdsModeParc
     */
    private EdsModeParc edsModeParc;
    /**
     * Variable to hold value of MyTextField for Tmed
     */
    private MyTextField vTXTTmoy;
    /**
     * Variable to hold value of MyTextField for Tmax
     */
    private MyTextField vTXTTmax;
    /**
     * Variable to hold value of MyTextArea for comment
     */
    private MyTextArea vTACommentaire;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsQcf
     */
    private EdsQcf qcf;

    /**
     * Initialize Read form view for Park mode
     */
    private void init() {
        Label useCaseName = new Label(edsModeParc.getMpedsName() == null ? "-" : edsModeParc.getMpedsName());
        addComponent(useCaseName);

        vTXTTmoy = new MyTextField();
        vTXTTmax = new MyTextField();
        vTACommentaire = new MyTextArea();

        addComponent(getModeParc());
        discardChanges();

        setSpacing(true);
        setWidth("100%");

        // separator
        addComponent(new Label("<br />", Label.CONTENT_XHTML));

    }

    /**
     * This method returns Table for Park Mode
     * 
     * @return Table for Park Mode
     */
    public Table getModeParc() {
        MyTable table = new MyTable();
        table.setPageLength(2);
        table.setWidth("100%");

        table.addContainerProperty("0", Label.class, null, controller.getBundle().getString("table-header-label-data-to-edit"), null, null);

        table.addContainerProperty("1", Label.class, null, controller.getBundle().getString("table-header-label-tension"), null, null);

        table.addContainerProperty("2", TextField.class, null, controller.getBundle().getString("current-conso-tab-data-at-Tmoy"), null, null);

        table.addContainerProperty("3", TextField.class, null, controller.getBundle().getString("current-conso-tab-data-at-Tmax"), null, null);

        table.addContainerProperty("4", TextArea.class, null, controller.getBundle().getString("eds-comnent"), null, null);

        table.addItem(
                new Object[] { controller.getBundle().getString("current-conso-tab-data-v-mode-parc"), super.getUmoy12_5(tension.getCsEdsUmoy(), bt),
                        vTXTTmoy, vTXTTmax, vTACommentaire }, new Integer(0));

        if (qcf.getQcf1() != 1) {
            table.setVisibleColumns(new Object[] { "0", "1", "2", "4" });
        }

        return table;
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

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete()
     */
    @Override
    public Collection getAllItemsToDelete() {
        return Collections.EMPTY_SET;
    }

    /**
     * This method reset value of EdsQf for 1st question
     */
    private void resetQcf1() {
        vTXTTmax.setValue(null);
    }

    /**
     * This method reset value of EdsQf for 2nd question
     */
    private void resetQcf2() {
        vTXTTmax.setValue(null);
        vTXTTmoy.setValue(null);
        vTACommentaire.setValue("");
    }

    /**
     * This method reset value of EdsQf for all question
     */
    private void resetQcf() {
        if (qcf.getQcf1() == 0) {
            resetQcf1();
        }
        if (qcf.getQcf2() == 0) {
            resetQcf2();
        }
    }

    /**
     * This method returns EdsModeParc
     * 
     * @return EdsModeParc
     */
    public EdsModeParc getEdsModeParc() {
        return edsModeParc;
    }

    /**
     * This method set EdsModeParc
     * 
     * @param edsModeParc Object of EdsModeParc
     */
    public void setEdsModeParc(EdsModeParc edsModeParc) {
        this.edsModeParc = edsModeParc;
    }
}
