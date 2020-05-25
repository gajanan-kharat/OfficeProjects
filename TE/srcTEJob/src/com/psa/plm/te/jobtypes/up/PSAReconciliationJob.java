/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.jobtypes.up;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.psa.plm.te.jobtypes.PSAGenericJob;
import com.psa.plm.te.tools.PSATEGenericfunctions;
import com.psa.plm.te.tools.PSATEMufoConfig;
import com.psa.plm.te.tools.PSATEPatternMatcher;

public class PSAReconciliationJob extends PSAGenericJob {

    private static final String strExaSubfolder = "reconciliation";

    private String strInputFileName = "";

    private String strXFlowReport = "";

    private String strGlobalStatus = "";

    private int nInstantationNeeded = 0;

    private int nMatrixPosNeeded = 0;

    private ArrayList<String> listMatPosHash = null;
    private ArrayList<PSAMatrixPosJob> listMatPos = null;

    private ArrayList<PSAInstantiationJob> listINSTJob = null;

    public PSAReconciliationJob(String strjobID) {
        super(strjobID);
        strJobType = "plm_te_reconciliation";
    }

    @Override
    public void initJobDetails(String strExaPath, StringBuilder rapportFileContent) {
        // 1. Build the EXALEAD PATH
        strReportPath = strExaPath + File.separator + strExaSubfolder + File.separator + strJobID + File.separator + "exalead";

        // 2. Get the details of the job
        getjobReportDetails(rapportFileContent);
    }

    @Override
    public void processIndexedData() {
        // 1. Get the input file name as present in the XFlow*.xml present in the exalead path
        getReconInputFile();
        if (strXFlowReport.equals("")) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add("NO OUTPUT FILE FOUND !!!");
            mapErrorDeta.put("ALERT", listError);
        } else {
            // 2. Get the objects in error with the global status.
            getRconStatusDetails();

            getBAErrorDetails();

            // 3. get the number of INST and MATPOS needed
            getPostTraitementDetails();
        }
    }

    public void SetInstObjects(ArrayList<PSAGenericJob> listInstObj) {
        for (PSAGenericJob psageneric : listInstObj) {
            PSAInstantiationJob psaInstantiationJob = (PSAInstantiationJob) psageneric;
            if (psaInstantiationJob.getDiagonalInputName().equals(strInputFileName)) {
                if (listINSTJob == null)
                    listINSTJob = new ArrayList<PSAInstantiationJob>();
                listINSTJob.add(psaInstantiationJob);
            }
        }
    }

    public void SetMatrixPosObjects(ArrayList<PSAGenericJob> listMatPosObj) {
        if (listMatPosHash != null) {
            for (PSAGenericJob psageneric : listMatPosObj) {
                PSAMatrixPosJob psaMatrixPosJob = (PSAMatrixPosJob) psageneric;
                if (listMatPosHash.contains(psaMatrixPosJob.getMatrixHash())) {
                    if (listMatPos == null)
                        listMatPos = new ArrayList<PSAMatrixPosJob>();
                    listMatPos.add(psaMatrixPosJob);
                }
            }
        }
    }

    private void getRconStatusDetails() {
        String strGlobalStatusPattern = "(?:\\<GlobalStatus\\s\\w+=\"(\\w+)\"[/]?\\>)";
        String strGlobalErrorPattern = "(?:GlobalStatusDetailInfo\\>(.*?)\\<[/]GlobalStatusDetailInfo\\>)";
        String strObjectErrorPattern = "(?:Object\\sIdentifier=\"(?:([\\w\\-]+?);([\\w\\-]{0,3}?))?\"\\sStatus=\"KO\"(?:.*?)Level=\"Error\"\\sType=\"Global\"\\>([^<]+))";

        StringBuilder strBufferData = PSATEGenericfunctions.getFileContent(strReportPath + File.separator + strXFlowReport);

        strGlobalStatus = PSATEPatternMatcher.execPattern(strGlobalStatusPattern, strBufferData.toString(), 1);

        // KO then get the global error messages
        if (strGlobalStatus.equals("KO")) {
            ArrayList<String> MsgErr = PSATEPatternMatcher.getMatchList(strGlobalErrorPattern, strBufferData.toString(), 1);
            if (MsgErr != null && MsgErr.size() > 0)
                mapErrorDeta.put(strGlobalStatus, MsgErr);
        }

        // Get the list of objects in error..
        HashMap<String, ArrayList<String>> mapKODetails = PSATEPatternMatcher.execPatternForGroupMap(strObjectErrorPattern, strBufferData.toString());

        // Group_1 --> Name //Group_2 --> Version //Group_3 --> Error message
        final ArrayList<String> listObjName = mapKODetails.get("Group_1");
        final ArrayList<String> listObjVersion = mapKODetails.get("Group_2");
        final ArrayList<String> listErrorDetails = mapKODetails.get("Group_3");
        int i = 0;
        // Keep the error as per the common messages found
        if ((listObjName != null) && (listObjVersion != null) && (listErrorDetails != null)) {
            for (String errMsg : listErrorDetails) {
                String strObject = listObjName.get(i) + "|" + (listObjVersion.get(i) == null ? "" : listObjVersion.get(i));
                if (!mapErrorDeta.containsKey(errMsg)) {
                    ArrayList<String> listErrObj = new ArrayList<String>();
                    listErrObj.add(strObject);
                    mapErrorDeta.put(errMsg, listErrObj);
                } else {
                    mapErrorDeta.get(errMsg).add(strObject);
                }
                i++;
            }
        }
    }

    private void getBAErrorDetails() {
        String strBAErrorFile = strReportPath + File.separator + "BA_error.log";
        File fileBAError = new File(strBAErrorFile);
        if (fileBAError.exists() && fileBAError.length() > 10) {
            StringBuilder strBufferData = PSATEGenericfunctions.getFileContent(strBAErrorFile);
            if (strBufferData.length() > 1) {
                System.out.println("The BA file details are : " + strBufferData);
                ArrayList<String> listError = PSATEPatternMatcher.getMatchList(PSATEMufoConfig.STR_BA_ERROR_LOG, strBufferData.toString(), 1);
                if (listError != null && listError.size() > 0) {
                    System.out.println("\n\nThe RegEx details are : ");
                    for (String errMsg : listError) {
                        ArrayList<String> MsgErr = null;
                        if (mapErrorDeta.containsKey(errMsg)) {
                            MsgErr = mapErrorDeta.get(errMsg);
                        } else {
                            MsgErr = new ArrayList<String>();
                            mapErrorDeta.put(errMsg, MsgErr);
                        }
                        setPSARefInError(errMsg, MsgErr);
                    }
                    // mapErrorDeta.put(strBAError, MsgErr);
                }
            }
        }
    }

    private void setPSARefInError(String errMsg, ArrayList<String> msgErr) {
        String strPattern = ".*\\[(\\d+\\-\\w+)\\].*";
        ArrayList<String> listRefs = PSATEPatternMatcher.getMatchList(strPattern, errMsg, 1);
        if (listRefs != null && listRefs.size() == 1) {
            msgErr.add(listRefs.get(0));
        }

    }

    private void getReconInputFile() {
        String[] strFiles = PSATEGenericfunctions.getDirectoryChild(strReportPath, "XFlow\\d+\\.xml", true);
        if (strFiles != null && strFiles.length > 0) {
            strXFlowReport = strFiles[0];
            StringBuilder strBufferData = PSATEGenericfunctions.getFileContent(strReportPath + File.separator + strXFlowReport);
            if (strBufferData != null) {
                String strPattern = "\\<ConfigurationItem\\sname=\"Input XML\"\\>(?:.*?(diagonal.*xml))\\<[/]ConfigurationItem\\>";
                strInputFileName = PSATEPatternMatcher.execPattern(strPattern, strBufferData.toString(), 1);
            }
        }
    }

    private void getPostTraitementDetails() {
        String[] arrInstFile = PSATEGenericfunctions.getDirectoryChild(strReportPath, "^INSTANCIATION.*xml", true);
        String[] arrMatPosFile = PSATEGenericfunctions.getDirectoryChild(strReportPath, "^MATRIXPOS_output_\\d+\\.xml", true);
        if (arrInstFile != null && arrInstFile.length > 0) {
            nInstantationNeeded = arrInstFile.length;
        }
        if (arrMatPosFile != null && arrMatPosFile.length > 0) {
            nMatrixPosNeeded = arrMatPosFile.length;
            for (String strMatPosFile : arrMatPosFile) {
                generateHash(strMatPosFile);
            }
        }
    }

    private void generateHash(String strMatPosFile) {
        StringBuilder strBufferData = PSATEGenericfunctions.getFileContent(strReportPath + File.separator + strMatPosFile);
        if (strBufferData != null) {
            String strPattern = "PA\\s.*?Name=\"(\\w+)\".*?\\>";
            ArrayList<String> listNP = PSATEPatternMatcher.getMatchList(strPattern, strBufferData.toString(), 1);
            storeHashValue(listNP);
        }
    }

    private void storeHashValue(ArrayList<String> listNP) {
        String strInputToHash = "";
        Collections.sort(listNP);
        for (String value : listNP) {
            if (!strInputToHash.contains(value))
                strInputToHash += value;
        }
        String strHashValue = PSATEGenericfunctions.getHashValue(strInputToHash);
        if (listMatPosHash == null)
            listMatPosHash = new ArrayList<String>();
        listMatPosHash.add(strHashValue);
    }
}
