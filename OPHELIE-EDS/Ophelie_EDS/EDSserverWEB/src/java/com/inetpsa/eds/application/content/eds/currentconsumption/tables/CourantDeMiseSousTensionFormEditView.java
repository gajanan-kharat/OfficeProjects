package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import java.util.Collection;
import java.util.Collections;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.composants.MyTable;
import com.inetpsa.eds.application.content.eds.composants.MyTextArea;
import com.inetpsa.eds.application.content.eds.composants.MyTextField;
import com.inetpsa.eds.dao.model.ConsolidateSupplyEdsTension;
import com.inetpsa.eds.dao.model.EdsCourantMiseSousTension;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

/**
 * This class provide Edit form view for Powered on current
 * 
 * @author Geometric Ltd.
 */
public class CourantDeMiseSousTensionFormEditView extends A_EdsEditForm {
    // PUBLIC
    /**
     * Variable to hold Value of MyTextField for current on TworstCase of Ipowered On (A)
     */
    private MyTextField vTXTTpireCasMst;
    /**
     * Variable to hold Value of MyTextArea for comment of Ipowered On (A)
     */
    private MyTextArea vTACommentaireMst;
    /**
     * Variable to hold Value of MyTextField for current on TworstCase of Dt
     */
    private MyTextField vTXTTpireCasDt;
    /**
     * Variable to hold Value of MyTextArea for comment of Dt
     */
    private MyTextArea vTACommentaireDt;
    /**
     * Variable to hold Value of EdsCourantMiseSousTension
     */
    private EdsCourantMiseSousTension ecmst;
    /**
     * Variable to hold Value of controller of EDS application
     */
    private EdsApplicationController controller;
    private ConsolidateSupplyEdsTension tension;
    /**
     * Variable to hold value of BT_TBT
     */
    private Boolean bt = false;

    /**
     * Parameterized Constructor
     * 
     * @param ecmst Object of EdsCourantMiseSousTension
     * @param controller Controller of EDS application
     */
    public CourantDeMiseSousTensionFormEditView(EdsCourantMiseSousTension ecmst, EdsApplicationController controller,
            ConsolidateSupplyEdsTension tension, Boolean bt) {
        this.tension = tension;
        this.ecmst = ecmst;
        this.controller = controller;
        init();
    }

    public CourantDeMiseSousTensionFormEditView(EdsCourantMiseSousTension ecmst, EdsApplicationController controller, Boolean bt) {
        this(ecmst, controller, new ConsolidateSupplyEdsTension(), bt);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {

        ecmst.setCmstedsTpirecasDt(vTXTTpireCasDt.getFloat());
        ecmst.setCmstedsTpirecasDtComment(vTACommentaireDt.getText());
        ecmst.setCmstedsTpirecasImst(vTXTTpireCasMst.getFloat());
        ecmst.setCmstedsTpirecasImstComment(vTACommentaireMst.getText());

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {

        vTXTTpireCasDt.setValue(ecmst.getCmstedsTpirecasDt());
        vTACommentaireDt.setValue(ecmst.getCmstedsTpirecasDtComment());
        vTXTTpireCasMst.setValue(ecmst.getCmstedsTpirecasImst());
        vTACommentaireMst.setValue(ecmst.getCmstedsTpirecasImstComment());

    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize Edit form view for Powered on current
     */
    private void init() {
        Label useCaseName = new Label(ecmst.getCmstedsName() == null ? "-" : ecmst.getCmstedsName());
        addComponent(useCaseName);

        vTXTTpireCasMst = new MyTextField();
        vTACommentaireMst = new MyTextArea();

        vTXTTpireCasDt = new MyTextField();
        vTACommentaireDt = new MyTextArea();

        addComponent(getTable());
        addComponent(new Label(controller.getBundle().getString("courant-i-pircas-advice")));
        discardChanges();

        setSpacing(true);
        setWidth("100%");

        // separator
        addComponent(new Label("<br />", Label.CONTENT_XHTML));

    }

    /**
     * This method returns Table for Powered on current
     * 
     * @return Table Powered on current
     */
    public Table getTable() {
        MyTable table = new MyTable(null);
        table.setPageLength(2);
        table.addContainerProperty(controller.getBundle().getString("table-header-label-data-to-edit"), Label.class, null);
        table.addContainerProperty(controller.getBundle().getString("table-label-sous-tension"), Label.class, null);
        table.addContainerProperty(controller.getBundle().getString("current-conso-tab-data-TPireCas"), TextField.class, null);
        table.addContainerProperty(controller.getBundle().getString("eds-comnent"), TextArea.class, null);

        table.addItem(new Object[] { controller.getBundle().getString("current-conso-tab-data-Imst"), super.getUmoy12_5(tension.getCsEdsUmoy(), bt),
                vTXTTpireCasMst, vTACommentaireMst }, new Integer(0));

        table.addItem(new Object[] { "∆t (66% Imst) (s)", super.getUmoy12_5(tension.getCsEdsUmoy(), bt), vTXTTpireCasDt, vTACommentaireDt },
                new Integer(1));

        return table;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.singleton((Object) ecmst);
    }

    /**
     * This method returns Collection of EdsCourantMiseSousTension
     * 
     * @return Collection of EdsCourantMiseSousTension
     */
    public Collection<EdsCourantMiseSousTension> getAllEdsCourantMiseSousTension() {
        return Collections.singleton(ecmst);
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
        // DO NOTHING
    }
}
