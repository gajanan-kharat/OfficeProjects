/*
 * Creation : 4 juil. 2016
 */
package com.psa.plm.te.processReport;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.psa.plm.te.tools.PSATECSVFileBuilder;
import com.psa.plm.te.tools.PSATEMufoConfig;

public class PSATEErrorCSVOperator {
    PSATECSVFileBuilder csvObj = null;
    String strPath = "";

    public PSATEErrorCSVOperator(String strName) {
        strPath = System.getenv("MU2_WSHARE") + File.separator + "out" + File.separator + strName;
    }

    public int InitCSVfile() {
        int nRet = 0;
        System.out.println("Createing error CSV at the path: " + strPath);
        csvObj = new PSATECSVFileBuilder(strPath);
        nRet = csvObj.PrepareCSVFile();
        if (nRet == 0) {
            String header = generateHeader();
            csvObj.writeHeader(header);
        }
        return nRet;
    }

    public void storeErrorDetails(HashMap<String, PSATETechnicalObject> mapJobDetails) {
        Set<String> techkeys = mapJobDetails.keySet();
        for (String jobType : PSATEMufoConfig.listOrderforCSV) {
            if (techkeys.contains(jobType)) {
                setErrorDetailsToCSV(mapJobDetails.get(jobType));
            }
        }
    }

    public String getCSVPath() {
        return strPath;
    }

    private void setErrorDetailsToCSV(PSATETechnicalObject psateTechnicalObject) {
        Set<String> listJobId = psateTechnicalObject.mapErrorDetails.keySet();
        for (String jobId : listJobId) {
            HashMap<String, ArrayList<String>> errMap = psateTechnicalObject.mapErrorDetails.get(jobId);
            if (errMap != null && errMap.size() > 0) {
                Set<String> listErrMsg = errMap.keySet();
                for (String errMsgTemp : listErrMsg) {
                    ArrayList<String> listData = errMap.get(errMsgTemp);
                    String strPartData = getPartData(listData);
                    String errMsg = "";
                    try {
                        errMsg = new String(errMsgTemp.getBytes("ISO-8859-1"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String strLine = jobId + PSATEMufoConfig.CSV_SEPARATER + listData.size() + PSATEMufoConfig.CSV_SEPARATER + "\"" + errMsg + "\""
                            + PSATEMufoConfig.CSV_SEPARATER + strPartData;
                    csvObj.writeLine(strLine);
                }
            }
        }
    }

    private String getPartData(ArrayList<String> listData) {
        String strData = "";
        if (listData != null) {
            for (String strPart : listData) {
                if (strPart.length() == 0 || strPart.equals("|") || strPart.equals(" "))
                    continue;
                if (strData.length() > 0)
                    strData += "\n" + strPart;
                else
                    strData += strPart;
            }
            if (strData.length() > 0)
                strData = "\"" + strData + "\"";
        }
        return strData;
    }

    private String generateHeader() {
        String strHeader = PSATEMufoConfig.CSV_Error_NumJob + PSATEMufoConfig.CSV_SEPARATER + PSATEMufoConfig.CSV_Error_JOBID
                + PSATEMufoConfig.CSV_SEPARATER + PSATEMufoConfig.CSV_Error_Message + PSATEMufoConfig.CSV_SEPARATER + PSATEMufoConfig.CSV_Error_Parts;

        return strHeader;
    }

    public void closefile() {
        csvObj.closeFile();
        csvObj = null;
    }
}
