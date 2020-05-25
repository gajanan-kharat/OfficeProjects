/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.jobReport;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.psa.plm.te.jobtypes.PSAGenericJob;
import com.psa.plm.te.jobtypes.PSATETaskDetails;
import com.psa.plm.te.jobtypes.dwc.PSAExaAppEffJob;
import com.psa.plm.te.jobtypes.dwc.PSAPrepareRepDwcJob;
import com.psa.plm.te.jobtypes.dwc.PSATeDwcInitRepRelaunchJob;
import com.psa.plm.te.jobtypes.dwc.PSATeDwcInstanceJob;
import com.psa.plm.te.jobtypes.dwc.PSATeDwcJob;
import com.psa.plm.te.jobtypes.dwc.PSATeDwcNonTerminalJob;
import com.psa.plm.te.jobtypes.dwc.PSATeDwcRepRelaunchJob;
import com.psa.plm.te.jobtypes.dwc.PSATeDwcSimulationJob;
import com.psa.plm.te.jobtypes.dwc.PSATeDwcTerminalJob;
import com.psa.plm.te.jobtypes.dwc.PSATeExaDwcJob;
import com.psa.plm.te.jobtypes.dwc.PSATfDivExpJob;
import com.psa.plm.te.jobtypes.dwc.PSATfDivImpJob;
import com.psa.plm.te.jobtypes.dwc.PSATfDwcJob;
import com.psa.plm.te.tools.PSATEMufoConfig;
import com.psa.plm.te.tools.PSATEPatternMatcher;

public class PSATEDwcJobAnalysis extends PSATEJobAnalysis {

    // Map of the PSAApplicationEffectivity jobs executed w.r.t to the div_Imp
    private HashMap<String, PSAExaAppEffJob> mapExaJob = new HashMap<String, PSAExaAppEffJob>();

    PSATEDwcJobAnalysis(String strJobID) {
        super(strJobID);
        strSpecialKey = "<DWCINPUT>";
        bIsUpward = false;
    }

    public void Init() {
        String strpatternExaID = "(?<ProjectName>^[\\w\\-\\.]+)";
        strExaProjectName = PSATEPatternMatcher.execPattern(strpatternExaID, strMufoIDFolder, 1);

        strExaPath += File.separator + strExaProjectName;

        // Update the location keys to be used.
        Set<String> keys = PSATEMufoConfig.mapDwnJobPath.keySet();
        for (String key : keys) {
            String strConfig = PSATEMufoConfig.mapDwnJobPath.get(key);
            strConfig = strConfig.replace("<JOBPATH>", strMufoJobPath);
            mapTaskConfig.put(key, strConfig);
        }
        // 2. Set Reconciliation folder list
        populateSpecialFolder();
    }

    @Override
    public void populateJobTaskDetails() {
        StringBuilder rapportFileContent = getRapportFlow();
        if (rapportFileContent == null)
            return;
        // 1. Process for tf_dwc
        processFoTfDwcJob(rapportFileContent);
        // 2. Process for te_dwc
        processForTeDwcJob(rapportFileContent);
        // 3. Process for te_exalead
        processForTeExaDwcJob(rapportFileContent);
        // 4. Process for tf_divExp
        processForTfDivExpJob(rapportFileContent);
        // 5. Process for te_exalead_PSAApplicationEffecttivity
        processForExaAppEffJob(rapportFileContent);
        // 6. Process for tf_divimp
        processForTfDivImpJob(rapportFileContent);
        // 7. Process for te_prepareRep
        processForTePrepareDwcRepJob(rapportFileContent);
        // 8. Process for plm_te_dwc_init_reprelaunch
        processForTeDwcInitReprelaunch(rapportFileContent);
        // 9. Process for plm_te_dwc_relaunchrep
        processForTeDwcRelaunchRep(rapportFileContent);
        // 10. Process for plm_te_dwc_simulation
        processForTeDwcSimulation(rapportFileContent);
        // 11. Process for plm_te_dwc_tref
        processForTeDwcTerminal(rapportFileContent);
        // 12. Process for plm_te_dwc_ntref
        processForTeDwcNonTerminal(rapportFileContent);
        // 13. Process for plm_te_dwc_inst
        processForTeDwcInstance(rapportFileContent);

        // 8. Get the Timeout jobs
        processTimeOutJobs(rapportFileContent);

    }

    private void processFoTfDwcJob(StringBuilder rapportFileContent) {
        String strClassName = PSATfDwcJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSATfDwcJob objtfdwc = new PSATfDwcJob(arrJobID.get(i));
                objtfdwc.initJobDetails(strExaPath, rapportFileContent);
                objtfdwc.processIndexedData();
                jobDetails.listExaJobDetails.add(objtfdwc);
            }
            mapJobTasks.put(PSATEMufoConfig.mapDwcJobs.get(strClassName), jobDetails);
        }
    }

    private void processForTeDwcJob(StringBuilder rapportFileContent) {
        String strClassName = PSATeDwcJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSATeDwcJob objtedwc = new PSATeDwcJob(arrJobID.get(i));
                objtedwc.initJobDetails(strExaPath, rapportFileContent);
                objtedwc.processIndexedData();
                jobDetails.listExaJobDetails.add(objtedwc);
            }
            mapJobTasks.put(PSATEMufoConfig.mapDwcJobs.get(strClassName), jobDetails);
        }
    }

    private void processForTeExaDwcJob(StringBuilder rapportFileContent) {
        String strClassName = PSATeExaDwcJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSATeExaDwcJob objteexadwc = new PSATeExaDwcJob(arrJobID.get(i));
                objteexadwc.initJobDetails(strExaPath, rapportFileContent);
                objteexadwc.processIndexedData();
                jobDetails.listExaJobDetails.add(objteexadwc);
            }
            mapJobTasks.put(PSATEMufoConfig.mapDwcJobs.get(strClassName), jobDetails);
        }
    }

    private void processForTfDivExpJob(StringBuilder rapportFileContent) {
        String strClassName = PSATfDivExpJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSATfDivExpJob objtfdivexp = new PSATfDivExpJob(arrJobID.get(i));
                objtfdivexp.initJobDetails(strExaPath, rapportFileContent);
                objtfdivexp.processIndexedData();
                jobDetails.listExaJobDetails.add(objtfdivexp);
            }
            if (!strExaProjectName.startsWith("DWCPAR"))
                mapJobTasks.put(PSATEMufoConfig.mapDwcJobs.get(strClassName), jobDetails);
        }
    }

    private void processForTfDivImpJob(StringBuilder rapportFileContent) {
        String strClassName = PSATfDivImpJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSATfDivImpJob objtfdivimp = new PSATfDivImpJob(arrJobID.get(i));
                objtfdivimp.initJobDetails(strExaPath, rapportFileContent);
                objtfdivimp.setExaLeadJob(mapExaJob);
                objtfdivimp.processIndexedData();
                jobDetails.listExaJobDetails.add(objtfdivimp);
            }
            mapJobTasks.put(PSATEMufoConfig.mapDwcJobs.get(strClassName), jobDetails);
        }
    }

    private void processForExaAppEffJob(StringBuilder rapportFileContent) {
        String strClassName = PSAExaAppEffJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSAExaAppEffJob objexaappeff = new PSAExaAppEffJob(arrJobID.get(i));
                objexaappeff.initJobDetails(strExaPath, rapportFileContent);
                objexaappeff.processIndexedData();

                String strKey = objexaappeff.getParentJobId();
                if (strKey != null && strKey.length() > 0)
                    mapExaJob.put(strKey, objexaappeff);

                jobDetails.listExaJobDetails.add(objexaappeff);
            }
            mapJobTasks.put(PSATEMufoConfig.mapDwcJobs.get(strClassName), jobDetails);
        }
    }

    private void processForTePrepareDwcRepJob(StringBuilder rapportFileContent) {
        String strClassName = PSAPrepareRepDwcJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSAPrepareRepDwcJob objpreprep = new PSAPrepareRepDwcJob(arrJobID.get(i));
                objpreprep.initJobDetails(strExaPath, rapportFileContent);
                objpreprep.processIndexedData();
                jobDetails.listExaJobDetails.add(objpreprep);
            }
            mapJobTasks.put(PSATEMufoConfig.mapDwcJobs.get(strClassName), jobDetails);
        }
    }

    private void processForTeDwcInitReprelaunch(StringBuilder rapportFileContent) {
        String strClassName = PSATeDwcInitRepRelaunchJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0)
            jobDetails.nTotalRequired = 1;
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSATeDwcInitRepRelaunchJob objInitRelaunch = new PSATeDwcInitRepRelaunchJob(arrJobID.get(i));
                objInitRelaunch.initJobDetails(strExaPath, rapportFileContent);
                objInitRelaunch.processIndexedData();
                jobDetails.listExaJobDetails.add(objInitRelaunch);
            }
            mapJobTasks.put(PSATEMufoConfig.mapDwcJobs.get(strClassName), jobDetails);
        }

    }

    private void processForTeDwcRelaunchRep(StringBuilder rapportFileContent) {
        String strClassName = PSATeDwcRepRelaunchJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSATeDwcRepRelaunchJob objRepRelaunch = new PSATeDwcRepRelaunchJob(arrJobID.get(i));
                objRepRelaunch.initJobDetails(strExaPath, rapportFileContent);
                objRepRelaunch.processIndexedData();
                jobDetails.listExaJobDetails.add(objRepRelaunch);
            }
            mapJobTasks.put(PSATEMufoConfig.mapDwcJobs.get(strClassName), jobDetails);
        }

    }

    private void processForTeDwcSimulation(StringBuilder rapportFileContent) {
        String strClassName = PSATeDwcSimulationJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSATeDwcSimulationJob objSimulation = new PSATeDwcSimulationJob(arrJobID.get(i));
                objSimulation.initJobDetails(strExaPath, rapportFileContent);
                objSimulation.processIndexedData();
                jobDetails.listExaJobDetails.add(objSimulation);
            }
            if (strExaProjectName.startsWith("DWCPAR"))
                mapJobTasks.put(PSATEMufoConfig.mapDwcJobs.get(strClassName), jobDetails);
        }
    }

    private void processForTeDwcTerminal(StringBuilder rapportFileContent) {
        String strClassName = PSATeDwcTerminalJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSATeDwcTerminalJob objTerminal = new PSATeDwcTerminalJob(arrJobID.get(i));
                objTerminal.initJobDetails(strExaPath, rapportFileContent);
                objTerminal.processIndexedData();
                jobDetails.listExaJobDetails.add(objTerminal);
            }
            mapJobTasks.put(PSATEMufoConfig.mapDwcJobs.get(strClassName), jobDetails);
        }

    }

    private void processForTeDwcNonTerminal(StringBuilder rapportFileContent) {
        String strClassName = PSATeDwcNonTerminalJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSATeDwcNonTerminalJob objNonTerminal = new PSATeDwcNonTerminalJob(arrJobID.get(i));
                objNonTerminal.initJobDetails(strExaPath, rapportFileContent);
                objNonTerminal.processIndexedData();
                jobDetails.listExaJobDetails.add(objNonTerminal);
            }
            mapJobTasks.put(PSATEMufoConfig.mapDwcJobs.get(strClassName), jobDetails);
        }

    }

    private void processForTeDwcInstance(StringBuilder rapportFileContent) {
        String strClassName = PSATeDwcInstanceJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSATeDwcInstanceJob objInstance = new PSATeDwcInstanceJob(arrJobID.get(i));
                objInstance.initJobDetails(strExaPath, rapportFileContent);
                objInstance.processIndexedData();
                jobDetails.listExaJobDetails.add(objInstance);
            }
            mapJobTasks.put(PSATEMufoConfig.mapDwcJobs.get(strClassName), jobDetails);
        }

    }

    private void populateSpecialFolder() {
        String strJobTypePattern = "(\\w+)";
        String strJobType = PSATEPatternMatcher.execPattern(strJobTypePattern, strExaProjectName, 1);
        Set<String> keys = PSATEMufoConfig.mapDwnSpecialInput.keySet();
        for (String key : keys) {
            if (key.startsWith(strJobType)) {
                String strValue = PSATEMufoConfig.mapDwnSpecialInput.get(key);
                listSpecialValue.add(strValue);
            }
        }

    }

}
