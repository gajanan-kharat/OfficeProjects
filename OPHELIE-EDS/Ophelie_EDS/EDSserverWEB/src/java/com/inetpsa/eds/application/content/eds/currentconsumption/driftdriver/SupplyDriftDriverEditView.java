package com.inetpsa.eds.application.content.eds.currentconsumption.driftdriver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.model.EdsDriftInfo;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsRight;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;

/**
 * This class provide edit view for Drift in Eds supply data
 * 
 * @author Geometric Ltd.
 */
public class SupplyDriftDriverEditView extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param eds Eds details
     * @param controller Controller of supply drift
     * @param edsController Controller of EDS application
     */
    public SupplyDriftDriverEditView(EdsEds eds, SupplyDriftController controller, EdsApplicationController edsController) {
        this.eds = eds;
        this.controller = controller;
        this.edsController = edsController;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {

        if (!getAllValidatedDriftInfo().isEmpty() && commentTextArea.getValue().equals("")) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        drifts.clear();
        validatedDrifts.clear();
        for (EdsDriftInfo drift : controller.getAllDriftInfos()) {
            if (drift.isAlert() || drift.isWarning()) {
                drifts.add(drift);
                validatedDrifts.put(drift, eds.getEdsDriftInfos().contains(drift));
            }

        }
        if (drifts.isEmpty()) {
            showEmptyView();
        } else {
            removeAllComponents();
            getDriftTable().removeAllItems();
            Collections.sort(drifts);
            for (EdsDriftInfo drift : drifts) {
                getDriftTable().addItem(drift);
            }
            addComponent(getDriftTable());

            // Add comment text area to supply tab
            commentTextArea.setWidth("100%");
            commentTextArea.setHeight("150");
            final String driftComment = controller.getSupply().getSedsDriftComment();
            if (driftComment != null) {
                commentTextArea.setValue(driftComment);
            }
            addComponent(commentTextArea);

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.EMPTY_LIST;
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

    /**
     * This method returns set of Eds drift information
     * 
     * @return Set of EdsDriftInfo
     */
    public Set<EdsDriftInfo> getAllValidatedDriftInfo() {
        Set<EdsDriftInfo> validatedDriftInfos = new HashSet<EdsDriftInfo>();
        for (EdsDriftInfo drift : validatedDrifts.keySet()) {
            if (validatedDrifts.get(drift)) {
                validatedDriftInfos.add(drift);
            }
        }
        return validatedDriftInfos;
    }

    /**
     * This method returns set of validated Eds drift information
     * 
     * @return Set of EdsDriftInfo
     */
    public Set<EdsDriftInfo> getAllUnvalidatedDriftInfo() {
        Set<EdsDriftInfo> unvalidatedDriftInfos = new HashSet<EdsDriftInfo>();
        for (EdsDriftInfo drift : validatedDrifts.keySet()) {
            if (!validatedDrifts.get(drift)) {
                unvalidatedDriftInfos.add(drift);
            }
        }
        return unvalidatedDriftInfos;
    }

    // Set the comment in the EdsSupply model object.
    public void persistEdsSupplyDriftComment() {
        final String comment = (String) commentTextArea.getValue();
        controller.getSupply().setSedsDriftComment(comment);
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
     * Variable to hold value of EdsEDs
     */
    private EdsEds eds;
    /**
     * Variable to hold list of EdsDriftInfo
     */
    private List<EdsDriftInfo> drifts;
    /**
     * Variable to hold Map of EdsDriftInfo and Boolean for validate check
     */
    private HashMap<EdsDriftInfo, Boolean> validatedDrifts;
    /**
     * Notes on the current EDS Supply Drifts
     */
    TextArea commentTextArea;
    /**
     * Variable to hod value of Table for drifts
     */
    private Table vTBdrifts;

    /**
     * Initialize Edit view for Supply drift
     */
    private void init() {
        setSpacing(true);
        setMargin(true);

        this.drifts = new ArrayList<EdsDriftInfo>();
        this.validatedDrifts = new HashMap<EdsDriftInfo, Boolean>();
        this.commentTextArea = new TextArea(edsController.getBundle().getString("eds-drift-comment"));

        discardChanges();
    }

    /**
     * This method returns Table for drifts in supply
     * 
     * @return Table for drifts in supply
     */
    private Table getDriftTable() {
        Object[] properties = { SupplyDriftController.DATATYPE_CAPTION };
        boolean[] ordering = { false };
        if (vTBdrifts == null) {
            vTBdrifts = new Table();
            vTBdrifts.setWidth("100%");

            Table.ColumnGenerator columnGenerator = new Table.ColumnGenerator() {
                public Object generateCell(Table source, Object itemId, Object columnId) {
                    Label vLBLcaption = new Label();
                    vLBLcaption.setContentMode(Label.CONTENT_XHTML);
                    final EdsDriftInfo drift = (EdsDriftInfo) itemId;
                    switch ((Integer) columnId) {
                    case SupplyDriftController.DATATYPE_CAPTION: {
                        vLBLcaption.setValue(SupplyDriftController.getDataTypeTextValue(drift.getDiDataType(), edsController));
                        break;
                    }
                    case SupplyDriftController.SRC_STAGE_CAPTION: {
                        vLBLcaption.setValue(SupplyDriftController.getCaptionTextValue(drift.getDiRefStage(), edsController));
                        break;
                    }
                    case SupplyDriftController.SRC_STAGE_VALUE_CAPTION: {
                        vLBLcaption.setValue(drift.getDiRefValue());
                        break;
                    }
                    case SupplyDriftController.TARGET_STAGE_CAPTION: {
                        vLBLcaption.setValue(SupplyDriftController.getCaptionTextValue(drift.getDiTargetStage(), edsController));
                        break;
                    }
                    case SupplyDriftController.TARGET_STAGE_VALUE_CAPTION: {
                        vLBLcaption.setValue(drift.getDiTargetValue());
                        break;
                    }
                    case SupplyDriftController.DELTA_I_CAPTION: {
                        vLBLcaption.setValue(drift.getDeltaI());
                        break;
                    }
                    case SupplyDriftController.VALID_DRIFT_CAPTION: {
                        CheckBox vCBvalidValue = new CheckBox();

                        vCBvalidValue.addListener(new ValueChangeListener() {
                            public void valueChange(ValueChangeEvent event) {
                                validatedDrifts.put(drift, (Boolean) event.getProperty().getValue());
                            }
                        });
                        vCBvalidValue.setValue(eds.getEdsDriftInfos().contains(drift));
                        if (!controller.getController().userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_DRIFT)) {
                            vCBvalidValue.setEnabled(false);
                        }
                        return vCBvalidValue;
                    }
                    }
                    return vLBLcaption;
                }
            };

            // Creating columns
            vTBdrifts.addGeneratedColumn(SupplyDriftController.DATATYPE_CAPTION, columnGenerator);
            vTBdrifts.addGeneratedColumn(SupplyDriftController.SRC_STAGE_CAPTION, columnGenerator);
            vTBdrifts.addGeneratedColumn(SupplyDriftController.SRC_STAGE_VALUE_CAPTION, columnGenerator);
            vTBdrifts.addGeneratedColumn(SupplyDriftController.TARGET_STAGE_CAPTION, columnGenerator);
            vTBdrifts.addGeneratedColumn(SupplyDriftController.TARGET_STAGE_VALUE_CAPTION, columnGenerator);
            vTBdrifts.addGeneratedColumn(SupplyDriftController.DELTA_I_CAPTION, columnGenerator);
            vTBdrifts.addGeneratedColumn(SupplyDriftController.VALID_DRIFT_CAPTION, columnGenerator);

            // Setting headers
            vTBdrifts.setColumnWidth(SupplyDriftController.DATATYPE_CAPTION, 220);
            vTBdrifts.setColumnHeader(SupplyDriftController.DATATYPE_CAPTION,
                    SupplyDriftController.getCaptionTextValue(SupplyDriftController.DATATYPE_CAPTION, edsController));
            vTBdrifts.setColumnHeader(SupplyDriftController.SRC_STAGE_CAPTION,
                    SupplyDriftController.getCaptionTextValue(SupplyDriftController.SRC_STAGE_CAPTION, edsController));
            vTBdrifts.setColumnHeader(SupplyDriftController.SRC_STAGE_VALUE_CAPTION,
                    SupplyDriftController.getCaptionTextValue(SupplyDriftController.SRC_STAGE_VALUE_CAPTION, edsController));
            vTBdrifts.setColumnHeader(SupplyDriftController.TARGET_STAGE_CAPTION,
                    SupplyDriftController.getCaptionTextValue(SupplyDriftController.TARGET_STAGE_CAPTION, edsController));
            vTBdrifts.setColumnHeader(SupplyDriftController.TARGET_STAGE_VALUE_CAPTION,
                    SupplyDriftController.getCaptionTextValue(SupplyDriftController.TARGET_STAGE_VALUE_CAPTION, edsController));
            vTBdrifts.setColumnHeader(SupplyDriftController.DELTA_I_CAPTION,
                    SupplyDriftController.getCaptionTextValue(SupplyDriftController.DELTA_I_CAPTION, edsController));
            vTBdrifts.setColumnHeader(SupplyDriftController.VALID_DRIFT_CAPTION,
                    SupplyDriftController.getCaptionTextValue(SupplyDriftController.VALID_DRIFT_CAPTION, edsController));
            vTBdrifts.sort(properties, ordering);
        }
        return vTBdrifts;
    }

    /**
     * This method shows view when no drift in EDS
     */
    private void showEmptyView() {
        removeAllComponents();
        addComponent(new Label(edsController.getBundle().getString("Pilote-data-mess-no-deriv")));
    }
}
