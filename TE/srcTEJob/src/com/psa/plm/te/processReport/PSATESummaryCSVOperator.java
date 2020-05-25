/*
 * Creation : 4 juil. 2016
 */
package com.psa.plm.te.processReport;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import com.psa.plm.te.tools.PSATECSVFileBuilder;
import com.psa.plm.te.tools.PSATEGenericfunctions;
import com.psa.plm.te.tools.PSATEMufoConfig;

public class PSATESummaryCSVOperator {
    PSATECSVFileBuilder csvObj = null;
    String strPath = "";

    public PSATESummaryCSVOperator(String strName) {
        strPath = System.getenv("MU2_WSHARE") + File.separator + "out" + File.separator + strName;
    }

    public int InitCSVfile(Set<String> JobTypeKeys) {
        int nRet = 0;
        System.out.println("Createing summary CSV at the path: " + strPath);
        csvObj = new PSATECSVFileBuilder(strPath);
        nRet = csvObj.PrepareCSVFile();
        if (nRet == 0) {
            String header = generateHeader(JobTypeKeys);
            csvObj.writeHeader(header);
        }
        return nRet;
    }

    public void storeReturnStatus(HashMap<String, PSATETechnicalObject> mapJobDetails) {
        setExecutionStatus(mapJobDetails);
        ArrayList<String> listErrorVal = getUniqueValues(mapJobDetails);
        for (String retCode : listErrorVal) {
            ArrayList<String> strLines = getLineForCode(retCode, mapJobDetails);
            if (strLines != null) {
                for (String line : strLines) {
                    csvObj.writeLine(line);
                }
            }
        }
    }

    public String getCSVPath() {
        return strPath;
    }

    private ArrayList<String> getLineForCode(String retCode, HashMap<String, PSATETechnicalObject> mapJobDetails) {
        ArrayList<String> listLine = new ArrayList<String>();
        int nLine = 0;
        boolean bfound = true;
        ArrayList<String> keys = PSATEGenericfunctions.getCSVKeyInOrder(mapJobDetails.keySet());
        while (bfound) {
            String strLine = PSATEMufoConfig.CSV_RESULT + PSATEMufoConfig.CSV_SEPARATER + PSATEMufoConfig.CSV_CODERETURN + retCode
                    + PSATEMufoConfig.CSV_SEPARATER;
            bfound = false;

            for (String job : keys) {
                PSATETechnicalObject obj = mapJobDetails.get(job);
                if (obj.mapReturncode.containsKey(retCode)) {
                    ArrayList<String> listJobID = obj.mapReturncode.get(retCode);
                    if (listJobID.size() > nLine) {
                        strLine += listJobID.get(nLine) + PSATEMufoConfig.CSV_SEPARATER;
                        bfound |= true;
                    } else {
                        strLine += "-" + PSATEMufoConfig.CSV_SEPARATER;
                        bfound |= false;
                    }
                } else {
                    bfound |= false;
                    strLine += "-" + PSATEMufoConfig.CSV_SEPARATER;
                }
            }

            if (bfound) {
                listLine.add(strLine);
                nLine++;
            }
        }
        return listLine;
    }

    private ArrayList<String> getUniqueValues(HashMap<String, PSATETechnicalObject> mapJobDetails) {
        ArrayList<String> listcodes = new ArrayList<String>();
        ArrayList<String> keys = PSATEGenericfunctions.getCSVKeyInOrder(mapJobDetails.keySet());
        for (String key : keys) {
            Set<String> errCode = mapJobDetails.get(key).mapReturncode.keySet();
            for (String code : errCode) {
                if (!listcodes.contains(code))
                    listcodes.add(code);
            }
        }
        if (listcodes.size() > 0)
            Collections.sort(listcodes);
        listcodes.removeAll(PSATEMufoConfig.listCSVRetCodeToIgnore);
        // from 99 to 0
        Collections.reverse(listcodes);

        return listcodes;
    }

    private void setExecutionStatus(HashMap<String, PSATETechnicalObject> mapJobDetails) {

        ArrayList<String> keys = PSATEGenericfunctions.getCSVKeyInOrder(mapJobDetails.keySet());

        String strLine1 = PSATEMufoConfig.CSV_TOTALNEEDED + PSATEMufoConfig.CSV_SEPARATER;
        String strLine2 = PSATEMufoConfig.CSV_TOTALEXECUTED + PSATEMufoConfig.CSV_SEPARATER;

        for (String key : keys) {
            strLine1 += mapJobDetails.get(key).strRequired + PSATEMufoConfig.CSV_SEPARATER;
            strLine2 += mapJobDetails.get(key).strExecuted + PSATEMufoConfig.CSV_SEPARATER;
        }
        csvObj.writeLine(strLine1);
        csvObj.writeLine(strLine2);
    }

    private String generateHeader(Set<String> jobTypeKeys) {
        ArrayList<String> listKeys = PSATEGenericfunctions.getCSVKeyInOrder(jobTypeKeys);
        String strHeader = PSATEMufoConfig.CSV_SEPARATER + PSATEMufoConfig.CSV_SEPARATER;
        for (String key : listKeys) {
            strHeader += PSATEMufoConfig.mapCSVHeader.get(key) + PSATEMufoConfig.CSV_SEPARATER;
        }
        return strHeader;
    }

    public void closefile() {
        csvObj.closeFile();
        csvObj = null;
    }

}
