/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.jobReport;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import com.psa.plm.te.jobtypes.PSAGenericJob;
import com.psa.plm.te.jobtypes.PSATETaskDetails;
import com.psa.plm.te.jobtypes.up.PSACATDrawingJob;
import com.psa.plm.te.jobtypes.up.PSACATPartJob;
import com.psa.plm.te.jobtypes.up.PSACATPartSimulationJob;
import com.psa.plm.te.jobtypes.up.PSADeleteJob;
import com.psa.plm.te.jobtypes.up.PSADetModifJob;
import com.psa.plm.te.jobtypes.up.PSAEffectivityJob;
import com.psa.plm.te.jobtypes.up.PSAFixationJob;
import com.psa.plm.te.jobtypes.up.PSAFullJob;
import com.psa.plm.te.jobtypes.up.PSAInstanceJob;
import com.psa.plm.te.jobtypes.up.PSAInstantiationJob;
import com.psa.plm.te.jobtypes.up.PSALaunchReconciliationJob;
import com.psa.plm.te.jobtypes.up.PSAMastershipJob;
import com.psa.plm.te.jobtypes.up.PSAMatrixPosJob;
import com.psa.plm.te.jobtypes.up.PSAModelJob;
import com.psa.plm.te.jobtypes.up.PSAPrdStrtAnlysJob;
import com.psa.plm.te.jobtypes.up.PSAPrepareRepUpJob;
import com.psa.plm.te.jobtypes.up.PSAReconciliationJob;
import com.psa.plm.te.jobtypes.up.PSAReferenceJob;
import com.psa.plm.te.tools.PSATEGenericfunctions;
import com.psa.plm.te.tools.PSATEMufoConfig;
import com.psa.plm.te.tools.PSATEPatternMatcher;

public class PSATEUpJobAnalysis extends PSATEJobAnalysis {

    private String strPSAFolder = "";

    PSATEUpJobAnalysis(String strJobID) {
        super(strJobID);
        bIsUpward = true;
    }

    public void Init() {

        String strpatternExaID = "(?<ProjectName>^[\\w\\-]+)\\..*";
        strExaProjectName = PSATEPatternMatcher.execPattern(strpatternExaID, strMufoIDFolder, 1);
        strExaPath += File.separator + strExaProjectName;
        // 1. Get PSA Folder Name
        getPSAFolderName();
        if (strPSAFolder.equals("")) {
            listErrors.add("GLOBAL ERROR: No ProductStructureAnalysis data found !!!");
        } else {
            // Update the location keys to be used.
            Set<String> keys = PSATEMufoConfig.mapUpJobPath.keySet();
            for (String key : keys) {
                String strConfig = PSATEMufoConfig.mapUpJobPath.get(key);
                strConfig = strConfig.replace("<JOBPATH>", strMufoJobPath);
                strConfig = strConfig.replace("<PSAFOLDER>", strPSAFolder);
                mapTaskConfig.put(key, strConfig);
            }
        }
        // 2. Set Reconciliation folder list
        populateReconciliationFolder();
    }

    /**
     * Get the details of the sub-task of the job by checking the associated input files per specific type. Get the details of the sub-task from the
     * indexed EXALEAD folder.
     */
    public void populateJobTaskDetails() {
        StringBuilder rapportFileContent = getRapportFlow();
        if (rapportFileContent == null)
            return;
        // 1. Get the CATDrawing job (if any)
        processForCATDrawing(rapportFileContent);
        // 2. Get the CATPart job.
        processForCATPart(rapportFileContent);
        // 3. Get the CATPart simulation job.
        processForCATPartSimu(rapportFileContent);
        // 4. Get the Delete job.
        processForDelete(rapportFileContent);
        // 5. Get the effectivity job.
        processForEffectivity(rapportFileContent);
        // 6. Get the fixation job.
        processForFixation(rapportFileContent);
        // 7. Get the full job.
        processForFull(rapportFileContent);
        // 8. Get the instance job.
        processForInstance(rapportFileContent);
        // 9. Get the mastership job.
        processForMasterShip(rapportFileContent);
        // 10. Get the model job.
        processForModel(rapportFileContent);
        // 11. Get the PreparRep job
        processForPrepareRep(rapportFileContent);
        // 12. Get the Reference Job.
        processForReference(rapportFileContent);
        // 13. Get the Reconciliation job.
        processForReconciliation(rapportFileContent);
        // 14. Get the Instantiation job.
        processForInstantiation(rapportFileContent);
        // 15. Get the MatrixPos job.
        processForMatrixPos(rapportFileContent);
        // 16. Get the PSA Job
        processProdStructAnalysis(rapportFileContent);
        // 17. Get the launch Reconciliation job
        processLaunchReconciliation(rapportFileContent);
        // 18. Get the DetModif Job
        processForDetModif(rapportFileContent);
        // 19. Get the Timeout jobs
        processTimeOutJobs(rapportFileContent);
        // 20. Associate the INST and MATPOS to the proper reconciliation
        associateDiagonalJobs();

    }

    private void processLaunchReconciliation(StringBuilder rapportFileContent) {
        String strClassName = PSALaunchReconciliationJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
        int nFound = arrJobID.size();
        if (nFound > 0) {
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            for (int i = 0; i < nFound; i++) {
                PSALaunchReconciliationJob objlaunRecon = new PSALaunchReconciliationJob(arrJobID.get(i));
                objlaunRecon.initJobDetails(strExaPath, rapportFileContent);
                objlaunRecon.processIndexedData();
                jobDetails.listExaJobDetails.add(objlaunRecon);
            }
            mapJobTasks.put(PSATEMufoConfig.mapUpJobs.get(strClassName), jobDetails);
        }
    }

    private void processProdStructAnalysis(StringBuilder rapportFileContent) {
        String strClassName = PSAPrdStrtAnlysJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
        int nFound = arrJobID.size();
        if (nFound > 0) {
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            for (int i = 0; i < nFound; i++) {
                PSAPrdStrtAnlysJob objprdAna = new PSAPrdStrtAnlysJob(arrJobID.get(i));
                objprdAna.initJobDetails(strExaPath, rapportFileContent);
                objprdAna.processIndexedData();
                jobDetails.listExaJobDetails.add(objprdAna);
            }
            mapJobTasks.put(PSATEMufoConfig.mapUpJobs.get(strClassName), jobDetails);
        }
    }

    private void populateReconciliationFolder() {
        strSpecialKey = "<RECONFOLDER>";
        String strReconPath = strMufoJobPath + File.separator + "data" + File.separator + "reconciliation";
        String strFolderPattern = "\\d+_\\d.*";
        String[] strChildFolders = PSATEGenericfunctions.getDirectoryChild(strReconPath, strFolderPattern, false);
        if (strChildFolders != null) {
            for (String folder : strChildFolders) {
                String strFullPath = strReconPath + File.separator + folder + File.separator + "BA_debug.log";
                File fileCheck = new File(strFullPath);
                if (fileCheck.exists())
                    listSpecialValue.add(folder);
            }
        }
    }

    private void processForCATDrawing(StringBuilder rapportFileContent) {
        String strClassName = PSACATDrawingJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSACATDrawingJob objcatdrw = new PSACATDrawingJob(arrJobID.get(i));
                objcatdrw.initJobDetails(strExaPath, rapportFileContent);
                objcatdrw.processIndexedData();
                jobDetails.listExaJobDetails.add(objcatdrw);
            }
            mapJobTasks.put(PSATEMufoConfig.mapUpJobs.get(strClassName), jobDetails);
        }
    }

    private void processForCATPart(StringBuilder rapportFileContent) {
        String strClassName = PSACATPartJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSACATPartJob objcat = new PSACATPartJob(arrJobID.get(i));
                objcat.initJobDetails(strExaPath, rapportFileContent);
                objcat.processIndexedData();
                jobDetails.listExaJobDetails.add(objcat);
            }
            mapJobTasks.put(PSATEMufoConfig.mapUpJobs.get(strClassName), jobDetails);
        }
    }

    private void processForCATPartSimu(StringBuilder rapportFileContent) {
        String strClassName = PSACATPartSimulationJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSACATPartSimulationJob objcat = new PSACATPartSimulationJob(arrJobID.get(i));
                objcat.initJobDetails(strExaPath, rapportFileContent);
                objcat.processIndexedData();
                jobDetails.listExaJobDetails.add(objcat);
            }
            mapJobTasks.put(PSATEMufoConfig.mapUpJobs.get(strClassName), jobDetails);
        }
    }

    private void processForDelete(StringBuilder rapportFileContent) {
        String strClassName = PSADeleteJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSADeleteJob objcat = new PSADeleteJob(arrJobID.get(i));
                objcat.initJobDetails(strExaPath, rapportFileContent);
                objcat.processIndexedData();
                jobDetails.listExaJobDetails.add(objcat);
            }
            mapJobTasks.put(PSATEMufoConfig.mapUpJobs.get(strClassName), jobDetails);
        }
    }

    private void processForEffectivity(StringBuilder rapportFileContent) {
        String strClassName = PSAEffectivityJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSAEffectivityJob objcat = new PSAEffectivityJob(arrJobID.get(i));
                objcat.initJobDetails(strExaPath, rapportFileContent);
                objcat.processIndexedData();
                jobDetails.listExaJobDetails.add(objcat);
            }
            mapJobTasks.put(PSATEMufoConfig.mapUpJobs.get(strClassName), jobDetails);
        }
    }

    private void processForFixation(StringBuilder rapportFileContent) {
        String strClassName = PSAFixationJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSAFixationJob objcat = new PSAFixationJob(arrJobID.get(i));
                objcat.initJobDetails(strExaPath, rapportFileContent);
                objcat.processIndexedData();
                jobDetails.listExaJobDetails.add(objcat);
            }
            mapJobTasks.put(PSATEMufoConfig.mapUpJobs.get(strClassName), jobDetails);
        }
    }

    private void processForFull(StringBuilder rapportFileContent) {
        String strClassName = PSAFullJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSAFullJob objcat = new PSAFullJob(arrJobID.get(i));
                objcat.initJobDetails(strExaPath, rapportFileContent);
                objcat.processIndexedData();
                jobDetails.listExaJobDetails.add(objcat);
            }
            mapJobTasks.put(PSATEMufoConfig.mapUpJobs.get(strClassName), jobDetails);
        }
    }

    private void processForInstance(StringBuilder rapportFileContent) {
        String strClassName = PSAInstanceJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSAInstanceJob objcat = new PSAInstanceJob(arrJobID.get(i));
                objcat.initJobDetails(strExaPath, rapportFileContent);
                objcat.processIndexedData();
                jobDetails.listExaJobDetails.add(objcat);
            }
            mapJobTasks.put(PSATEMufoConfig.mapUpJobs.get(strClassName), jobDetails);
        }
    }

    private void processForMasterShip(StringBuilder rapportFileContent) {
        String strClassName = PSAMastershipJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSAMastershipJob objMship = new PSAMastershipJob(arrJobID.get(i));
                objMship.initJobDetails(strExaPath, rapportFileContent);
                objMship.processIndexedData();
                jobDetails.listExaJobDetails.add(objMship);
            }
            mapJobTasks.put(PSATEMufoConfig.mapUpJobs.get(strClassName), jobDetails);
        }
    }

    private void processForModel(StringBuilder rapportFileContent) {
        String strClassName = PSAModelJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSAModelJob objModel = new PSAModelJob(arrJobID.get(i));
                objModel.initJobDetails(strExaPath, rapportFileContent);
                objModel.processIndexedData();
                jobDetails.listExaJobDetails.add(objModel);
            }
            mapJobTasks.put(PSATEMufoConfig.mapUpJobs.get(strClassName), jobDetails);
        }
    }

    private void processForPrepareRep(StringBuilder rapportFileContent) {
        String strClassName = PSAPrepareRepUpJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSAPrepareRepUpJob objprepRep = new PSAPrepareRepUpJob(arrJobID.get(i));
                objprepRep.initJobDetails(strExaPath, rapportFileContent);
                objprepRep.processIndexedData();
                jobDetails.listExaJobDetails.add(objprepRep);
            }
            mapJobTasks.put(PSATEMufoConfig.mapUpJobs.get(strClassName), jobDetails);
        }
    }

    private void processForReference(StringBuilder rapportFileContent) {
        String strClassName = PSAReferenceJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSAReferenceJob objRef = new PSAReferenceJob(arrJobID.get(i));
                objRef.initJobDetails(strExaPath, rapportFileContent);
                objRef.processIndexedData();
                jobDetails.listExaJobDetails.add(objRef);
            }
            mapJobTasks.put(PSATEMufoConfig.mapUpJobs.get(strClassName), jobDetails);
        }
    }

    private void processForReconciliation(StringBuilder rapportFileContent) {
        String strClassName = PSAReconciliationJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSAReconciliationJob objRecon = new PSAReconciliationJob(arrJobID.get(i));
                objRecon.initJobDetails(strExaPath, rapportFileContent);
                objRecon.processIndexedData();
                jobDetails.listExaJobDetails.add(objRecon);
            }
            mapJobTasks.put(PSATEMufoConfig.mapUpJobs.get(strClassName), jobDetails);
        }
    }

    private void processForInstantiation(StringBuilder rapportFileContent) {
        String strClassName = PSAInstantiationJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSAInstantiationJob objInst = new PSAInstantiationJob(arrJobID.get(i));
                objInst.initJobDetails(strExaPath, rapportFileContent);
                objInst.processIndexedData();
                jobDetails.listExaJobDetails.add(objInst);
            }
            mapJobTasks.put(PSATEMufoConfig.mapUpJobs.get(strClassName), jobDetails);
        }
    }

    private void processForMatrixPos(StringBuilder rapportFileContent) {
        String strClassName = PSAMatrixPosJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSAMatrixPosJob objMatPos = new PSAMatrixPosJob(arrJobID.get(i));
                objMatPos.initJobDetails(strExaPath, rapportFileContent);
                objMatPos.processIndexedData();
                jobDetails.listExaJobDetails.add(objMatPos);
            }
            mapJobTasks.put(PSATEMufoConfig.mapUpJobs.get(strClassName), jobDetails);
        }
    }

    private void processForDetModif(StringBuilder rapportFileContent) {
        String strClassName = PSADetModifJob.class.getSimpleName();
        PSATETaskDetails jobDetails = createnSetJobType(strClassName);
        if (jobDetails.nTotalRequired > 0) {
            ArrayList<String> arrJobID = getMufoJobID(rapportFileContent, strClassName);
            jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
            int nFound = arrJobID.size();
            for (int i = 0; i < nFound; i++) {
                PSADetModifJob objDetModif = new PSADetModifJob(arrJobID.get(i));
                objDetModif.initJobDetails(strExaPath, rapportFileContent);
                objDetModif.processIndexedData();
                jobDetails.listExaJobDetails.add(objDetModif);
            }
            mapJobTasks.put(PSATEMufoConfig.mapUpJobs.get(strClassName), jobDetails);
        }
    }

    private void associateDiagonalJobs() {
        String strReconName = PSATEMufoConfig.mapUpJobs.get(PSAReconciliationJob.class.getSimpleName());
        String strINSTName = PSATEMufoConfig.mapUpJobs.get(PSAInstantiationJob.class.getSimpleName());
        String strMatPosName = PSATEMufoConfig.mapUpJobs.get(PSAMatrixPosJob.class.getSimpleName());
        PSATETaskDetails jobDetailsRecon = mapJobTasks.get(strReconName);
        PSATETaskDetails jobDetailsInst = mapJobTasks.get(strINSTName);
        PSATETaskDetails jobDetailsMatPos = mapJobTasks.get(strMatPosName);

        if (jobDetailsRecon != null && jobDetailsRecon.listExaJobDetails.size() > 0) {
            // Set the details for the Reconciliation and Instantiation
            if (jobDetailsInst != null && jobDetailsInst.listExaJobDetails.size() > 0) {
                for (PSAGenericJob genjob : jobDetailsRecon.listExaJobDetails) {
                    PSAReconciliationJob recon = (PSAReconciliationJob) genjob;
                    recon.SetInstObjects(jobDetailsInst.listExaJobDetails);
                }
            }
            // Set the details for the Reconciliation and MatPos
            if (jobDetailsMatPos != null && jobDetailsMatPos.listExaJobDetails.size() > 0) {
                for (PSAGenericJob genjob : jobDetailsRecon.listExaJobDetails) {
                    PSAReconciliationJob recon = (PSAReconciliationJob) genjob;
                    recon.SetMatrixPosObjects(jobDetailsMatPos.listExaJobDetails);
                }
            }
        }
    }

    private void getPSAFolderName() {
        String strDataFolder = strMufoJobPath + "\\data";
        String[] childFolders = PSATEGenericfunctions.getChildDirectory(strDataFolder);

        if (childFolders.length >= 1) {
            for (String chlddir : childFolders) {
                if (chlddir.equals("simulation"))
                    continue;
                String strChildDir = strDataFolder + File.separator + chlddir;
                if (PSATEGenericfunctions.hasChildFolder(strChildDir, PSATEMufoConfig.listPSASubFolders)) {
                    strPSAFolder = chlddir;
                }
            }
        }
    }

}
