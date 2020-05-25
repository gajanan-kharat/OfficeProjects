/*
 * Creation : 5 juil. 2016
 */
package com.psa.plm.te.processReport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import com.psa.plm.te.jobReport.PSATEJobAnalysis;
import com.psa.plm.te.jobtypes.PSATETaskDetails;
import com.psa.plm.te.tools.PSATEGenericfunctions;
import com.psa.plm.te.tools.PSATEMufoConfig;

public class PSATETechnicalObject {
    // Map of return code per JobID
    public HashMap<String, ArrayList<String>> mapReturncode = new HashMap<String, ArrayList<String>>();

    // Map of JobID, with (Map of Message per data)
    public HashMap<String, HashMap<String, ArrayList<String>>> mapErrorDetails = new HashMap<String, HashMap<String, ArrayList<String>>>();

    public String strRequired = "";
    public String strExecuted = "";

    public static HashMap<String, PSATETechnicalObject> createTechnicalObject(PSATEJobAnalysis mufoJob) {
        HashMap<String, PSATETechnicalObject> mapTechObject = new HashMap<String, PSATETechnicalObject>();
        Set<String> keys = mufoJob.getSubTaskId();
        for (String key : keys) {
            if (PSATEMufoConfig.listToSkipInCSV.contains(key))
                continue;
            PSATETaskDetails jobDetails = mufoJob.getsubTask(key);
            PSATETechnicalObject techObject = new PSATETechnicalObject();
            techObject.strRequired = Integer.toString(jobDetails.nTotalRequired);
            techObject.strExecuted = Integer.toString(jobDetails.listExaJobDetails.size());
            int nJobEntry = jobDetails.listExaJobDetails.size();
            for (int i = 0; i < nJobEntry; i++) {
                jobDetails.listExaJobDetails.get(i).populateReturnStatus(techObject.mapReturncode);
                jobDetails.listExaJobDetails.get(i).populateErrorDetails(techObject.mapErrorDetails);
            }
            mapTechObject.put(key, techObject);
        }
        return mapTechObject;
    }

    public static String GetExecutionStatus(HashMap<String, PSATETechnicalObject> mapTechObject) {
        String strStatus = "OK";
        Set<String> kyes = mapTechObject.keySet();
        for (String key : kyes) {
            PSATETechnicalObject techObj = mapTechObject.get(key);
            if (!techObj.strExecuted.equals(techObj.strRequired))
                strStatus = "KO";
        }
        return strStatus;
    }

    public static String GetReturnCodeStatus(HashMap<String, PSATETechnicalObject> mapTechObject) {
        String strStatus = "OK";
        ArrayList<String> listcodes = new ArrayList<String>();
        ArrayList<String> keys = PSATEGenericfunctions.getCSVKeyInOrder(mapTechObject.keySet());
        for (String key : keys) {
            Set<String> errCode = mapTechObject.get(key).mapReturncode.keySet();
            for (String code : errCode) {
                if (!listcodes.contains(code))
                    listcodes.add(code);
            }
        }
        if (listcodes.size() > 0)
            Collections.sort(listcodes);
        listcodes.removeAll(PSATEMufoConfig.listCSVRetCodeToIgnore);

        if (listcodes.size() > 1)
            strStatus = "KO";

        return strStatus;
    }
}
