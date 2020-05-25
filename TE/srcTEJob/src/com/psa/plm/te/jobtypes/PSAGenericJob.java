/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.jobtypes;

import java.util.ArrayList;
import java.util.HashMap;

import com.psa.plm.te.tools.PSATEGenericfunctions;
import com.psa.plm.te.tools.PSATEMufoConfig;
import com.psa.plm.te.tools.PSATEPatternMatcher;

public abstract class PSAGenericJob {

    protected String strJobID = "";
    protected String strJobType = "";

    protected String strReportPath = "";

    protected HashMap<String, ArrayList<String>> mapErrorDeta = new HashMap<String, ArrayList<String>>();

    protected HashMap<String, ArrayList<String>> mapJobDetails = null;

    protected String[] arrMapJobDetails = null;

    public PSAGenericJob(String strjobID) {
        strJobID = strjobID;
    }

    public abstract void initJobDetails(String strExaPath, StringBuilder rapportFileContent);

    public abstract void processIndexedData();

    // Get the unique JobIDs
    public ArrayList<String> getReturnValues() {
        ArrayList<String> listValues = null;
        if (mapJobDetails != null && mapJobDetails.size() > 0) {
            ArrayList<String> tmpListVal = mapJobDetails.get("Code retour");
            if (tmpListVal != null) {
                listValues = PSATEGenericfunctions.getUniqueList(tmpListVal);
            }
        }
        return listValues;
    }

    // Map of ReturnCode with the jobID
    public void populateReturnStatus(HashMap<String, ArrayList<String>> mapReturnValue) {
        ArrayList<String> listValues = null;
        if (mapJobDetails != null && mapJobDetails.size() > 0) {
            ArrayList<String> tmpListVal = mapJobDetails.get("Code retour");

            // The case of "Code Retour" was found for DivImp service
            if (tmpListVal == null)
                tmpListVal = mapJobDetails.get("Code Retour");

            if (tmpListVal != null) {
                for (String retVal : tmpListVal) {
                    if (!mapReturnValue.containsKey(retVal)) {
                        ArrayList<String> listIds = new ArrayList<String>();
                        listIds.add(strJobID);
                        mapReturnValue.put(retVal, listIds);
                    } else {
                        mapReturnValue.get(retVal).add(strJobID);
                    }
                }
            }
        }
    }

    protected void getjobReportDetails(StringBuilder rapportFileContent) {
        // 1. Read the job details
        String strJobDetailsPattern = PSATEMufoConfig.STR_PATTERN_JOBDETAILS;
        strJobDetailsPattern = strJobDetailsPattern.replace("%JOBTYPE%", strJobType);
        strJobDetailsPattern = strJobDetailsPattern.replace("%JOBID%", strJobID);
        mapJobDetails = PSATEPatternMatcher.execPatternForMap(strJobDetailsPattern, rapportFileContent, 1);
        // 2. Read the LAME/VM details
        String strJobLameDetails = PSATEMufoConfig.STR_LAME_JOBDETAILS;
        strJobLameDetails = strJobLameDetails.replace("%JOBID%", strJobID);
        arrMapJobDetails = PSATEPatternMatcher.execPattern(strJobLameDetails, rapportFileContent.toString());
        /*
         * if(arrMapJobDetails != null) System.out.println("VM Details --> " + arrMapJobDetails[0]);
         */
    }

    public void populateErrorDetails(HashMap<String, HashMap<String, ArrayList<String>>> mapErrorDetails) {
        mapErrorDetails.put(strJobID, mapErrorDeta);
    }
}
