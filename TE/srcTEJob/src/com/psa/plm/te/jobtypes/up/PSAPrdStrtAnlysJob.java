/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.jobtypes.up;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.psa.plm.te.jobtypes.PSAGenericJob;
import com.psa.plm.te.tools.PSATEGenericfunctions;
import com.psa.plm.te.tools.PSATEMufoConfig;
import com.psa.plm.te.tools.PSATEPatternMatcher;

public class PSAPrdStrtAnlysJob extends PSAGenericJob {
    private static final String strExaSubfolder = "PSAProductStructureAnalysis";

    private String strReportFileName = "";
    private String strGlobalStatus = "";

    public PSAPrdStrtAnlysJob(String strjobID) {
        super(strjobID);
        strJobType = "plm_te_psa";
    }

    @Override
    public void initJobDetails(String strExaPath, StringBuilder rapportFileContent) {
        // 1. Build the EXALEAD PATH (using the ID of the plm_te_exalead_PSAProductStructureAnalysis
        String strPattern = PSATEMufoConfig.getJobTypePattern("plm_te_exalead_PSAProductStructureAnalysis");
        ArrayList<String> arrJobID = PSATEPatternMatcher.getMatchList(strPattern, rapportFileContent.toString(), 1);
        if (arrJobID != null && arrJobID.size() == 1) {
            strReportPath = strExaPath + File.separator + strExaSubfolder + File.separator + strJobID + File.separator + "exalead";
        } else {
            strReportPath = "";
        }
        // 2. Get the details of the job
        if (strReportPath.length() > 0)
            getjobReportDetails(rapportFileContent);
    }

    @Override
    public void processIndexedData() {
        // 1. Get the Report file of the processing...
        String[] arrReportFile = PSATEGenericfunctions.getDirectoryChild(strReportPath, "^Report_XFLOWXML.*xml", true);
        if (arrReportFile != null && arrReportFile.length > 0) {
            strReportFileName = arrReportFile[0];
        }

        // 2. Parse the Report file to get all the needed details
        if (strReportFileName.length() > 0) {
            String strData = getFileContent(strReportFileName);
            getGlobalStatusDetails(strData);
            getPartStatusDetails(strData);
        }
    }

    private void getPartStatusDetails(String strData) {
        strGlobalStatus = PSATEPatternMatcher.execPattern(PSATEMufoConfig.STR_GLOBAL_STATUS_XFLOW_XML, strData, 1);
    }

    private void getGlobalStatusDetails(String strData) {
        HashMap<String, ArrayList<String>> mapKODetails = PSATEPatternMatcher.execPatternForGroupMap(PSATEMufoConfig.STR_ERROR_DETAILS_PSA_XFLOW_XML,
                strData);
        // Group_1 --> Type of object //Group_2 --> Object ID //Group_3 --> Global Error message
        final ArrayList<String> listErrorObjects = mapKODetails.get("Group_2");
        final ArrayList<String> listErrorDetails = mapKODetails.get("Group_3");
        int i = 0;
        // Keep the error as per the common messages found
        for (String errMsg : listErrorDetails) {
            if (mapErrorDeta.containsKey(errMsg)) {
                ArrayList<String> listErrObj = new ArrayList<String>();
                listErrObj.add(listErrorObjects.get(i));
                mapErrorDeta.put(errMsg, listErrObj);
            } else {
                mapErrorDeta.get(errMsg).add(listErrorObjects.get(i));
            }
            i++;
        }

    }

    private String getFileContent(String strFile) {
        StringBuilder strContent = null;
        try {
            File rptfile = new File(strFile);
            strContent = new StringBuilder();
            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(new FileReader(rptfile));
            while (reader.ready()) {
                strContent.append(reader.readLine().replaceFirst("^\\s+", ""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strContent.toString();
    }

}
