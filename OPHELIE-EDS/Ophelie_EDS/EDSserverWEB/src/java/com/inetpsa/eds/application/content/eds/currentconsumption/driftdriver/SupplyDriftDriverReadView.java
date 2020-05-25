package com.inetpsa.eds.application.content.eds.currentconsumption.driftdriver;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.application.content.eds.composants.Comment;
import com.inetpsa.eds.dao.model.EdsEds;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;

/**
 * This class provide read view for Drift in Eds supply data
 * 
 * @author Geometric Ltd.
 */
public class SupplyDriftDriverReadView extends A_EdsReadForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of supply drift
     * @param edsController Controller of EDS application
     */
    public SupplyDriftDriverReadView(SupplyDriftController controller, EdsApplicationController edsController) {
        super(null);
        this.controller = controller;
        this.edsController = edsController;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormName()
     */
    @Override
    public String getFormName() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormTitle()
     */
    @Override
    public String getFormTitle() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEdsRef()
     */
    @Override
    public String getEdsRef() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEds()
     */
    @Override
    public EdsEds getEds() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshViewData()
     */
    @Override
    public void refreshViewData() {
        removeAllComponents();
        addComponent(getCustomProfileView());

        // Display the Drift Comments section when the comments exist.
        final String comment = controller.getSupply().getSedsDriftComment();
        if (comment != null) {
            Panel commentPanel = new Panel(edsController.getBundle().getString("eds-drift-comment"));
            Comment driftComment = new Comment(edsController);
            commentPanel.setWidth("100%");
            commentPanel.setHeight("150");

            driftComment.setValue(comment);
            commentPanel.addComponent(driftComment);
            addComponent(commentPanel);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        // DO NOTHING
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of controller of drift in Supply
     */
    private SupplyDriftController controller;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController edsController;
    /**
     * Variable to hold value of Table
     */
    private Table vTBLcontent;

    /**
     * Initialize read view for Drift in Eds supply data
     */
    private void init() {
        setMargin(true);
        setSpacing(true);
        refreshViewData();
    }

    /**
     * This method returns Table for drift control
     * 
     * @return Table for drift control
     */
    private Table getCustomProfileView() {

        if (vTBLcontent == null) {
            vTBLcontent = new Table();
            vTBLcontent.setPageLength(8);
            vTBLcontent.setWidth("100%");
            vTBLcontent.setSortDisabled(false);

            // column data
            vTBLcontent.addGeneratedColumn(SupplyDriftController.DATATYPE_CAPTION, new Table.ColumnGenerator() {
                public Object generateCell(Table source, Object itemId, Object columnId) {
                    Label vLBLcaption = new Label();
                    vLBLcaption.setContentMode(Label.CONTENT_XHTML);
                    vLBLcaption.setValue(SupplyDriftController.getDataTypeTextValue((Integer) itemId, edsController));
                    return vLBLcaption;
                }
            });
            vTBLcontent.setColumnHeader(SupplyDriftController.DATATYPE_CAPTION,
                    SupplyDriftController.getCaptionTextValue(SupplyDriftController.DATATYPE_CAPTION, edsController));
            vTBLcontent.setColumnWidth(SupplyDriftController.DATATYPE_CAPTION, 220);

            // Stages column data
            Table.ColumnGenerator columnGenerator = new Table.ColumnGenerator() {
                public Object generateCell(Table source, Object itemId, Object columnId) {
                    Label vLBLcaption = new Label();
                    Float value = controller.getSupplyData((Integer) columnId, (Integer) itemId);
                    if (value != null) {
                        vLBLcaption.setValue(value);
                    } else {
                        vLBLcaption.setValue("N/A");
                    }
                    // Adding a style depending on the status of the case
                    switch (controller.getSupplyDataStatus((Integer) columnId, (Integer) itemId)) {
                    case SupplyDriftController.STATE_VALIDATED:
                        vLBLcaption.setStyleName("drift-validated");
                        break;

                    case SupplyDriftController.STATE_ALERT:
                        vLBLcaption.setStyleName("drift-alert");
                        break;

                    case SupplyDriftController.STATE_WARNING:
                        vLBLcaption.setStyleName("drift-warning");
                        break;
                    }

                    return vLBLcaption;
                }
            };
            vTBLcontent.addGeneratedColumn(SupplyDriftController.PRELIM_STAGE, columnGenerator);
            vTBLcontent.addGeneratedColumn(SupplyDriftController.ROBUST_STAGE, columnGenerator);
            vTBLcontent.addGeneratedColumn(SupplyDriftController.THEORETICAL_CONSOLIDATE_STAGE, columnGenerator);
            vTBLcontent.addGeneratedColumn(SupplyDriftController.MEASURED_CONSOLIDATE_STAGE, columnGenerator);
            vTBLcontent.addGeneratedColumn(SupplyDriftController.MEASURE_STAGE, columnGenerator);

            // Column Delta I
            vTBLcontent.addGeneratedColumn(SupplyDriftController.DELTA_I_CAPTION, new Table.ColumnGenerator() {
                public Object generateCell(Table source, Object itemId, Object columnId) {
                    Label vLBLcaption = new Label();
                    Float value = controller.getSupplyDataDriftValue((Integer) itemId);
                    if (value != null) {
                        vLBLcaption.setValue(value);
                    } else {
                        vLBLcaption.setValue("OK");
                    }
                    return vLBLcaption;
                }
            });

            vTBLcontent.setColumnHeader(SupplyDriftController.PRELIM_STAGE,
                    SupplyDriftController.getCaptionTextValue(SupplyDriftController.PRELIM_STAGE, edsController));
            vTBLcontent.setColumnHeader(SupplyDriftController.ROBUST_STAGE,
                    SupplyDriftController.getCaptionTextValue(SupplyDriftController.ROBUST_STAGE, edsController));
            vTBLcontent.setColumnHeader(SupplyDriftController.THEORETICAL_CONSOLIDATE_STAGE,
                    SupplyDriftController.getCaptionTextValue(SupplyDriftController.THEORETICAL_CONSOLIDATE_STAGE, edsController));
            vTBLcontent.setColumnHeader(SupplyDriftController.MEASURED_CONSOLIDATE_STAGE,
                    SupplyDriftController.getCaptionTextValue(SupplyDriftController.MEASURED_CONSOLIDATE_STAGE, edsController));
            vTBLcontent.setColumnHeader(SupplyDriftController.MEASURE_STAGE,
                    SupplyDriftController.getCaptionTextValue(SupplyDriftController.MEASURE_STAGE, edsController));
            vTBLcontent.setColumnHeader(SupplyDriftController.DELTA_I_CAPTION,
                    SupplyDriftController.getCaptionTextValue(SupplyDriftController.DELTA_I_CAPTION, edsController));

            // Adding different lines.
            vTBLcontent.addItem(SupplyDriftController.I_SLEEP_CURRENT);
            vTBLcontent.addItem(SupplyDriftController.I_AWAKE_INACTIVE_12_5);
            vTBLcontent.addItem(SupplyDriftController.I_AWAKE_INACTIVE_13_5);
            vTBLcontent.addItem(SupplyDriftController.I_AWAKE_INACTIVE_13_5);
            vTBLcontent.addItem(SupplyDriftController.I_NOM_STAB_12_5);
            vTBLcontent.addItem(SupplyDriftController.I_NOM_STAB_13_5);
            vTBLcontent.addItem(SupplyDriftController.I_WORST_STAB_12_5);
            vTBLcontent.addItem(SupplyDriftController.I_WORST_STAB_13_5);

        }

        return vTBLcontent;

    }
}
