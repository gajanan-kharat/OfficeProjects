package com.inetpsa.eds.application.content.eds.genericdata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.currentconsumption.driftdriver.SupplyDriftController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsDriftInfo;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsSupply;

/**
 * This class is used to calculate and retrieve Drift for the given EDS.
 * 
 * @author Geometric Ltd.
 */
public class EdsDriftController implements Serializable {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param eds EDS details.
     * @param controller EDS application controller object.
     */
    public EdsDriftController(EdsEds eds, EdsApplicationController controller) {
        this.eds = eds;
        this.controller = controller;
        init();
    }

    /**
     * This method is used to check if the EDS is drifted.
     * 
     * @return true if is drifted, else false.
     */
    public boolean isDrifted() {
        return drifted;
    }

    /**
     * The method is used to check if the drift is validated.
     * 
     * @return true id drift is validated, else false.
     */
    public boolean isDriftValidated() {
        return driftValidated;
    }

    /**
     * This method is used to compute the drift.
     */
    public void computeDrift() {
        supplies.clear();

        supplies.addAll(EDSdao.getInstance().getAllEdsSuppliesByEdsId(eds.getEdsId()));

        List<EdsDriftInfo> drifts = getAllDrifts();
        driftValidated = false;
        if (drifts.isEmpty()) {
            drifted = false;
        } else {
            drifted = true;
            if (eds.getEdsDriftInfos().containsAll(drifts)) {
                driftValidated = true;
            }
        }
    }

    // PROTECTED
    // PRIVATE
    /**
     * EDS details.
     */
    private EdsEds eds;
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;
    /**
     * Flag to validate if EDS is drifted.
     */
    private boolean drifted;
    /**
     * Flag to validate if drifted EDS is validated.
     */
    private boolean driftValidated;
    /**
     * List of Supply details.
     */
    private List<EdsSupply> supplies;

    /**
     * Initialization method.
     */
    private void init() {
        this.drifted = false;
        this.driftValidated = false;
        this.supplies = new ArrayList<EdsSupply>();
    }

    /**
     * This method is used to retrieve list of all the drifts.
     * 
     * @return the list of drifts.
     */
    private List<EdsDriftInfo> getAllDrifts() {
        List<EdsDriftInfo> drifts = new ArrayList<EdsDriftInfo>();
        for (EdsSupply supply : supplies) {
            SupplyDriftController supplyDriftController = new SupplyDriftController(supply, eds, controller);
            for (EdsDriftInfo drift : supplyDriftController.getAllDriftInfos()) {
                if (drift.isAlert() || drift.isWarning()) {
                    drifts.add(drift);
                }
            }
        }
        return drifts;
    }
}
