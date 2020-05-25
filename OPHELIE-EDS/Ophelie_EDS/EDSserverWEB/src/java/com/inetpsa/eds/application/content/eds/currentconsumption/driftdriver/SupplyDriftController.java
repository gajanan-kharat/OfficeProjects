package com.inetpsa.eds.application.content.eds.currentconsumption.driftdriver;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.I_DriftableSupply;
import com.inetpsa.eds.dao.model.EdsDriftInfo;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsSupply;
import com.inetpsa.eds.tools.EDSTools;

/**
 * This class acts as a controller for Drift in supply
 * 
 * @author Geometric Ltd.
 */
public class SupplyDriftController {
    // PUBLIC
    // Identifiers for columns of driver drifts. Constants must be different per stage
    /**
     * Constant to hold value of DATATYPE_CAPTION
     */
    public final static int DATATYPE_CAPTION = -1;
    /**
     * Constant to hold value of DELTA_I_CAPTION
     */
    public final static int DELTA_I_CAPTION = 5;
    /**
     * Constant to hold value of SRC_STAGE_CAPTION
     */
    public final static int SRC_STAGE_CAPTION = 6;
    /**
     * Constant to hold value of TARGET_STAGE_CAPTION
     */
    public final static int TARGET_STAGE_CAPTION = 7;
    /**
     * Constant to hold value of SRC_STAGE_VALUE_CAPTION
     */
    public final static int SRC_STAGE_VALUE_CAPTION = 8;
    /**
     * Constant to hold value of TARGET_STAGE_VALUE_CAPTION
     */
    public final static int TARGET_STAGE_VALUE_CAPTION = 9;
    /**
     * Constant to hold value of VALID_DRIFT_CAPTION
     */
    public final static int VALID_DRIFT_CAPTION = 10;
    // Constant Stages
    /**
     * Constant to hold value of PRELIM_STAGE
     */
    public final static int PRELIM_STAGE = 0;
    /**
     * Constant to hold value of ROBUST_STAGE
     */
    public final static int ROBUST_STAGE = 1;
    /**
     * Constant to hold value of CONSOLIDATE_STAGE
     */
    public final static int THEORETICAL_CONSOLIDATE_STAGE = 2;
    /**
     * Constant to hold value of MEASURED_CONSOLIDATE_STAGE
     */
    public final static int MEASURED_CONSOLIDATE_STAGE = 3;
    /**
     * Constant to hold value of MEASURE_STAGE
     */
    public final static int MEASURE_STAGE = 4;
    // Constant Data
    /**
     * Constant to hold value of I_SLEEP_CURRENT
     */
    public final static int I_SLEEP_CURRENT = 0;
    /**
     * Constant to hold value of I_AWAKE_INACTIVE_12_5
     */
    public final static int I_AWAKE_INACTIVE_12_5 = 1;
    /**
     * Constant to hold value of I_THEORICAL_AWAKE_INACTIVE_13_5
     */
    public final static int I_AWAKE_INACTIVE_13_5 = 2;

    /**
     * Constant to hold value of I_NOM_STAB_12_5
     */
    public final static int I_NOM_STAB_12_5 = 4;
    /**
     * Constant to hold value of I_NOM_STAB_13_5
     */
    public final static int I_NOM_STAB_13_5 = 5;
    /**
     * Constant to hold value of I_WORST_STAB_12_5
     */
    public final static int I_WORST_STAB_12_5 = 6;
    /**
     * Constant to hold value of I_WORST_STAB_13_5
     */
    public final static int I_WORST_STAB_13_5 = 7;
    // Constant status of a case
    /**
     * Constant to hold value of STATE_DEFAULT
     */
    public final static int STATE_DEFAULT = 0;
    /**
     * Constant to hold value of STATE_VALIDATED
     */
    public final static int STATE_VALIDATED = 1;
    /**
     * Constant to hold value of STATE_WARNING
     */
    public final static int STATE_WARNING = 2;
    /**
     * Constant to hold value of STATE_ALERT
     */
    public final static int STATE_ALERT = 3;

    /**
     * This method returns Text value for given data type
     * 
     * @param dataType Data type to be converted to text
     * @param controller Controller of EDS application
     * @return Text value for given data type
     */
    public static String getDataTypeTextValue(int dataType, EdsApplicationController controller) {
        String value = null;
        switch (dataType) {
        case I_SLEEP_CURRENT:
            value = EDSTools.replaceFormatCharsByXHTML(controller.getBundle().getString("Pilote-data-current-1"));
            break;
        case I_AWAKE_INACTIVE_12_5:
            value = EDSTools.replaceFormatCharsByXHTML(controller.getBundle().getString("Pilote-data-current-2"));
            break;
        case I_AWAKE_INACTIVE_13_5:
            value = EDSTools.replaceFormatCharsByXHTML(controller.getBundle().getString("Pilote-data-current-3"));
            break;
        case I_NOM_STAB_12_5:
            value = EDSTools.replaceFormatCharsByXHTML(controller.getBundle().getString("Pilote-data-current-4"));
            break;
        case I_NOM_STAB_13_5:
            value = EDSTools.replaceFormatCharsByXHTML(controller.getBundle().getString("Pilote-data-current-5"));
            break;
        case I_WORST_STAB_12_5:
            value = EDSTools.replaceFormatCharsByXHTML(controller.getBundle().getString("Pilote-data-current-6"));
            break;
        case I_WORST_STAB_13_5:
            value = EDSTools.replaceFormatCharsByXHTML(controller.getBundle().getString("Pilote-data-current-7"));
            break;
        default:
            value = "";
            break;
        }
        return value;
    }

    /**
     * This method provide Caption for Text value
     * 
     * @param stage Text value for which caption to be set
     * @param controller Controller of EDS application
     * @return Caption for Text value
     */
    public static String getCaptionTextValue(int stage, EdsApplicationController controller) {
        String value = null;
        switch (stage) {
        case PRELIM_STAGE:
            value = controller.getBundle().getString("Pilote-data-tab-title-col-1");
            break;
        case ROBUST_STAGE:
            value = controller.getBundle().getString("Pilote-data-tab-title-col-2");
            break;
        case THEORETICAL_CONSOLIDATE_STAGE:
            value = controller.getBundle().getString("Pilote-data-tab-title-col-31");
            break;
        case MEASURED_CONSOLIDATE_STAGE:
            value = controller.getBundle().getString("Pilote-data-tab-title-col-32");
            break;
        case MEASURE_STAGE:
            value = controller.getBundle().getString("Pilote-data-tab-title-col-4");
            break;
        case DELTA_I_CAPTION:
            value = "ΔI";
            break;
        case DATATYPE_CAPTION:
            value = controller.getBundle().getString("Pilote-data-tab-title-data");
            break;
        case SRC_STAGE_CAPTION:
            value = controller.getBundle().getString("Pilote-data-tab-title-col-5");
            break;
        case SRC_STAGE_VALUE_CAPTION:
            value = controller.getBundle().getString("Pilote-data-tab-title-col-6");
            break;
        case TARGET_STAGE_CAPTION:
            value = controller.getBundle().getString("Pilote-data-tab-title-col-7");
            break;
        case TARGET_STAGE_VALUE_CAPTION:
            value = controller.getBundle().getString("Pilote-data-tab-title-col-8");
            break;
        case VALID_DRIFT_CAPTION:
            value = "OK";
            break;
        default:
            value = "";
            break;
        }
        return value;
    }

    /**
     * Parameterized constructor
     * 
     * @param supply Eds supply
     * @param eds Eds details
     * @param controller Controller of EDS application
     */
    public SupplyDriftController(EdsSupply supply, EdsEds eds, EdsApplicationController controller) {
        this.supply = supply;
        this.eds = eds;
        this.controller = controller;
        init();
    }

    /**
     * This method returns Controller of EDS application
     * 
     * @return Controller of EDS application
     */
    public EdsApplicationController getController() {
        return controller;
    }

    /**
     * This method returns EDS supply
     * 
     * @return EdsSupply
     */
    public EdsSupply getSupply() {
        return supply;
    }

    /**
     * This method returns status for supply data at given stage with value
     * 
     * @param stage Stage
     * @param valueType Value type
     * @return status for supply data at given stage with value
     */
    public int getSupplyDataStatus(int stage, int valueType) {
        return driftStatus[stage][valueType];
    }

    /**
     * This method returns the difference between supply data
     * 
     * @param valueType Value type
     * @return The difference between supply data
     */
    public Float getSupplyDataDriftValue(int valueType) {
        if (driftValues[valueType] != null) {
            return driftValues[valueType].getDeltaI();
        }
        return null;
    }

    /**
     * This method returns the supply data at a given stage with value
     * 
     * @param stage Stage
     * @param valueType Value type
     * @return The supply data at a given stage with value
     */
    public Float getSupplyData(int stage, int valueType) {
        Float value = null;
        I_DriftableSupply stageSupply = null;

        switch (stage) {
        case PRELIM_STAGE: {
            stageSupply = supply.getEdsPrimarySupply();
            break;
        }
        case ROBUST_STAGE: {
            stageSupply = supply.getEdsRobustSupply();
            break;
        }
        case THEORETICAL_CONSOLIDATE_STAGE: {
            stageSupply = supply.getEdsConsolidateSupply();
            break;
        }
        case MEASURED_CONSOLIDATE_STAGE: {
            if (supply.getEdsConsolidateSupply() != null) {
                stageSupply = supply.getEdsConsolidateSupply().getEdsConsolidateSupplyMesure();
            }

            break;
        }
        case MEASURE_STAGE: {
            stageSupply = supply.getEdsPsaMesureSupply();
            break;
        }
        }
        if (stageSupply != null) {
            switch (valueType) {
            case I_AWAKE_INACTIVE_12_5: {
                value = stageSupply.getIAwake12_5();
                break;
            }
            case I_AWAKE_INACTIVE_13_5: {
                value = stageSupply.getIAwake13_5();
                break;
            }
            case I_NOM_STAB_12_5: {
                value = stageSupply.getINomStab12_5();
                break;
            }
            case I_NOM_STAB_13_5: {
                value = stageSupply.getINomStab13_5();
                break;
            }
            case I_SLEEP_CURRENT: {
                value = stageSupply.getISleepCurrent();
                break;
            }
            case I_WORST_STAB_12_5: {
                value = stageSupply.getIWorstStab12_5();
                break;
            }
            case I_WORST_STAB_13_5: {
                value = stageSupply.getIWorstStab13_5();
                break;
            }
            }
        }
        return value;
    }

    /**
     * This method returns list of EDS drift information
     * 
     * @return List of EdsDriftInfo
     */
    public List<EdsDriftInfo> getAllDriftInfos() {
        List<EdsDriftInfo> driftInfos = new ArrayList<EdsDriftInfo>();
        // Info drifts by data type
        for (int dataType = I_SLEEP_CURRENT; dataType <= I_WORST_STAB_13_5; dataType++) {
            // Comparisons with preliminary stage
            driftInfos.add(new EdsDriftInfo(UUID.randomUUID().toString(), supply.getSedsSupplyName(), dataType, PRELIM_STAGE, getSupplyData(
                    PRELIM_STAGE, dataType), ROBUST_STAGE, getSupplyData(ROBUST_STAGE, dataType), eds));
            driftInfos.add(new EdsDriftInfo(UUID.randomUUID().toString(), supply.getSedsSupplyName(), dataType, PRELIM_STAGE, getSupplyData(
                    PRELIM_STAGE, dataType), THEORETICAL_CONSOLIDATE_STAGE, getSupplyData(THEORETICAL_CONSOLIDATE_STAGE, dataType), eds));
            driftInfos.add(new EdsDriftInfo(UUID.randomUUID().toString(), supply.getSedsSupplyName(), dataType, PRELIM_STAGE, getSupplyData(
                    PRELIM_STAGE, dataType), MEASURED_CONSOLIDATE_STAGE, getSupplyData(MEASURED_CONSOLIDATE_STAGE, dataType), eds));
            driftInfos.add(new EdsDriftInfo(UUID.randomUUID().toString(), supply.getSedsSupplyName(), dataType, PRELIM_STAGE, getSupplyData(
                    PRELIM_STAGE, dataType), MEASURE_STAGE, getSupplyData(MEASURE_STAGE, dataType), eds));

            // Comparisons with robust stage
            driftInfos.add(new EdsDriftInfo(UUID.randomUUID().toString(), supply.getSedsSupplyName(), dataType, ROBUST_STAGE, getSupplyData(
                    ROBUST_STAGE, dataType), THEORETICAL_CONSOLIDATE_STAGE, getSupplyData(THEORETICAL_CONSOLIDATE_STAGE, dataType), eds));
            driftInfos.add(new EdsDriftInfo(UUID.randomUUID().toString(), supply.getSedsSupplyName(), dataType, ROBUST_STAGE, getSupplyData(
                    ROBUST_STAGE, dataType), MEASURED_CONSOLIDATE_STAGE, getSupplyData(MEASURED_CONSOLIDATE_STAGE, dataType), eds));
            driftInfos.add(new EdsDriftInfo(UUID.randomUUID().toString(), supply.getSedsSupplyName(), dataType, ROBUST_STAGE, getSupplyData(
                    ROBUST_STAGE, dataType), MEASURE_STAGE, getSupplyData(MEASURE_STAGE, dataType), eds));

            // Comparisons with consolidate stage
            driftInfos.add(new EdsDriftInfo(UUID.randomUUID().toString(), supply.getSedsSupplyName(), dataType, THEORETICAL_CONSOLIDATE_STAGE,
                    getSupplyData(THEORETICAL_CONSOLIDATE_STAGE, dataType), MEASURE_STAGE, getSupplyData(MEASURE_STAGE, dataType), eds));
            driftInfos.add(new EdsDriftInfo(UUID.randomUUID().toString(), supply.getSedsSupplyName(), dataType, MEASURED_CONSOLIDATE_STAGE,
                    getSupplyData(MEASURED_CONSOLIDATE_STAGE, dataType), MEASURE_STAGE, getSupplyData(MEASURE_STAGE, dataType), eds));
        }
        return driftInfos;
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EdsSupply
     */
    private EdsSupply supply;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of two dimensional array of integer for drift status
     */
    private int[][] driftStatus;
    /**
     * Variable to hold value of array of EdsDriftInfo
     */
    private EdsDriftInfo[] driftValues;
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;

    /**
     * Initialize controller for Drift in supply
     */
    private void init() {
        computeData();
    }

    /**
     * This method compute supply data with drift status
     */
    private void computeData() {
        // Filling information about the status
        this.driftStatus = new int[5][8];
        this.driftValues = new EdsDriftInfo[8];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                driftStatus[i][j] = STATE_DEFAULT;
            }
        }
        for (EdsDriftInfo info : getAllDriftInfos()) {
            if (!eds.getEdsDriftInfos().contains(info)) {
                if (info.isAlert()) {
                    driftStatus[info.getDiTargetStage()][info.getDiDataType()] = STATE_ALERT;
                    driftStatus[info.getDiRefStage()][info.getDiDataType()] = STATE_ALERT;
                    if (driftValues[info.getDiDataType()] == null) {
                        driftValues[info.getDiDataType()] = info;
                    }
                } else if (info.isWarning()) {
                    driftStatus[info.getDiTargetStage()][info.getDiDataType()] = STATE_WARNING;
                    driftStatus[info.getDiRefStage()][info.getDiDataType()] = STATE_WARNING;
                    if (driftValues[info.getDiDataType()] == null) {
                        driftValues[info.getDiDataType()] = info;
                    }
                }
            } else {
                if (info.isAlert() || info.isWarning()) {
                    driftStatus[info.getDiTargetStage()][info.getDiDataType()] = STATE_VALIDATED;
                    driftStatus[info.getDiRefStage()][info.getDiDataType()] = STATE_VALIDATED;
                    if (driftValues[info.getDiDataType()] == null) {
                        driftValues[info.getDiDataType()] = info;
                    }
                }
            }

        }

    }
}
