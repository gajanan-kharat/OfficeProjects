/*
 * Creation : 4 Jan 2017
 */
package com.psa.plm.te.jobtypes.dwc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.psa.plm.te.jobtypes.PSAGenericJob;
import com.psa.plm.te.tools.PSATEGenericfunctions;
import com.psa.plm.te.tools.PSATEPatternMatcher;

public class PSATeDwcInstanceJob extends PSAGenericJob {

    public final String strExaSubfolder = "Instance";
    private String[] arrReportFile = null;

    public PSATeDwcInstanceJob(String strjobID) {
        super(strjobID);
        strJobType = "plm_te_dwc_inst";
    }

    @Override
    public void initJobDetails(String strExaPath, StringBuilder rapportFileContent) {
        // Set the report path
        strReportPath = strExaPath + File.separator + strExaSubfolder + File.separator + strJobID + File.separator + "exalead";

        // Get the details of the Job
        getjobReportDetails(rapportFileContent);

    }

    @Override
    public void processIndexedData() {
        String strPath = strReportPath + File.separator + "TransitionEngine";
        String strPathPattern = "Report_\\d+-\\d+-\\d+_\\d+\\.\\d+\\.\\d+";
        String strFilePatttern = "Transaction\\s?Report_\\d+\\.xml";
        String[] arrReportFolder = PSATEGenericfunctions.getDirectoryChild(strPath, strPathPattern, false);
        if (arrReportFolder != null && arrReportFolder.length > 0) {
            String strReportFile = arrReportFolder[0];
            String[] arrTractFiles = PSATEGenericfunctions.getDirectoryChild(strPath + File.separator + strReportFile, strFilePatttern, true);
            if (arrTractFiles != null && arrTractFiles.length > 0) {
                arrReportFile = new String[arrTractFiles.length];
                int i = 0;
                for (String fileName : arrTractFiles) {
                    arrReportFile[i] = "TransitionEngine" + File.separator + strReportFile + File.separator + fileName;
                    i++;
                }

            }
        }

        if (arrReportFile != null && arrReportFile.length > 0) {
            populateTransactionError();
        }

        // Get the details from the Downward-compatibility Report
        PSATEDownwardCompatibilityParser objparser = new PSATEDownwardCompatibilityParser(strReportPath);
        objparser.processHTML();
        String strHtmlStatus = objparser.getStatus();
        String strHtmlRootName = objparser.getDataInput();
        String strHtmlErrorMesString = objparser.getStatusMessage();
        if (strHtmlStatus != null && strHtmlStatus.equals("KO")) {
            if (strHtmlErrorMesString != null && strHtmlErrorMesString.length() > 0) {
                ArrayList<String> listRootPart = new ArrayList<String>();
                listRootPart.add(strHtmlRootName);
                mapErrorDeta.put(strHtmlErrorMesString, listRootPart);
            }
        }
    }

    private void populateTransactionError() {

        for (String fileName : arrReportFile) {
            String strData = readfileContent(strReportPath + File.separator + fileName, false);
            ArrayList<String> listErrors = PSATEPatternMatcher.getTransactionReportError(strData);
            if (listErrors != null && listErrors.size() > 0) {
                for (String errMsg : listErrors) {
                    if (mapErrorDeta.containsKey(errMsg))
                        mapErrorDeta.get(errMsg).add("");
                    else {
                        ArrayList<String> listTemp = new ArrayList<String>();
                        listTemp.add("");
                        mapErrorDeta.put(errMsg, listTemp);
                    }

                }
            }
        }
    }

    private String readfileContent(String strFile, boolean bLine) {
        StringBuilder strContent = null;
        try {
            File rptfile = new File(strFile);
            strContent = new StringBuilder();
            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(new FileReader(rptfile));
            while (reader.ready()) {
                // Read Line-by-line and remove all the starting white-spaces
                String strLine = reader.readLine().replaceFirst("^\\s+", "");
                if (strLine.length() > 0) {
                    if (bLine)
                        strContent.append(strLine + "\n");
                    else
                        strContent.append(strLine);
                }
            }
        } catch (IOException e) {

        }

        return strContent.toString();
    }

}
