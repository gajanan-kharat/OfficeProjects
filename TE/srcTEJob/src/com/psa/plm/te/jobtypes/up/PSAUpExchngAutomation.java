/*
 * Creation : 30 juin 2016
 */
package com.psa.plm.te.jobtypes.up;

import java.io.File;
import java.util.ArrayList;

import com.psa.plm.te.jobtypes.PSAGenericJob;
import com.psa.plm.te.tools.PSATEGenericfunctions;

public abstract class PSAUpExchngAutomation extends PSAGenericJob {

    protected String strVPMExngfolderName = "";
    protected PSATEVPMExchangeAutomationParser htmlParser = null;

    public PSAUpExchngAutomation(String strJobID) {
        super(strJobID);
    }

    @Override
    public void processIndexedData() {

        // 1. check the VPMExchangeAutomation.htm for the process status
        getstatusVPMExchangeAutomation();

        // 2. Get the VPMExchangeAutomation folder
        getVPMExchangeAutomationfolder();

        // 3. Get the details of the error present in the Transaction Report_*.xml
        if (strVPMExngfolderName.length() > 0)
            analyzeTransactionReport();
    }

    protected void getVPMExchangeAutomationfolder() {
        int nCount = PSATEGenericfunctions.getfileCount(strReportPath, "^VPMExchangeAutomation.*", false);
        if (nCount >= 1) {
            String[] arrfolderName = PSATEGenericfunctions.getDirectoryChild(strReportPath, "^VPMExchangeAutomation.*", false);
            if (arrfolderName != null) {
                for (String folderName : arrfolderName) {
                    String strProcessReportPath = strReportPath + File.separator + folderName + File.separator + "ProcessReport.xml";
                    String strTransationReportPath = strReportPath + File.separator + folderName + File.separator + "Transaction Report_1.xml";
                    File fileprocRpt = new File(strProcessReportPath);
                    File filetransactRpt = new File(strTransationReportPath);
                    if (fileprocRpt.exists() && filetransactRpt.exists()) {
                        strVPMExngfolderName = folderName;
                    }
                }
            }
        }
    }

    protected void analyzeTransactionReport() {
        if (!strVPMExngfolderName.equals("")) {
            String strUpReportPath = strReportPath + File.separator + strVPMExngfolderName;
            String[] listFiles = PSATEGenericfunctions.getDirectoryChild(strUpReportPath, "Transaction\\s?Report_\\d{1,}\\.xml", true);
            if (listFiles != null && listFiles.length > 0) {
                for (String transReport : listFiles) {
                    processTransactionReport(transReport, strUpReportPath + File.separator + transReport);
                }
            }
        }
    }

    protected void getstatusVPMExchangeAutomation() {
        String strVPMExchangeReport = strReportPath + File.separator + PSATEVPMExchangeAutomationParser.STR_HTMLFILE_NAME;
        if (PSATEGenericfunctions.isFileExist(strVPMExchangeReport)) {
            htmlParser = new PSATEVPMExchangeAutomationParser(strVPMExchangeReport);
            htmlParser.processHTML();
            if (htmlParser.getStatus().equals("KO")) {
                processErrorXFlowXML();
            }
        }
    }

    private void processErrorXFlowXML() {
        String strXFloxXML = strReportPath + File.separator + PSATEVPMExchangeAutomationParser.STR_XFLOWFILE_NAME;
        if (PSATEGenericfunctions.isFileExist(strXFloxXML)) {
            ArrayList<String> listErrorMessage = htmlParser.parseXFlowErrorReport(strXFloxXML);
            if (listErrorMessage != null) {
                for (String errorMessage : listErrorMessage) {
                    if (mapErrorDeta.containsKey(errorMessage))
                        mapErrorDeta.get(errorMessage).add("");
                    else {
                        ArrayList<String> listTmp = new ArrayList<String>();
                        listTmp.add("");
                        mapErrorDeta.put(errorMessage, listTmp);
                    }
                }
            }
        }
    }

    private void processTransactionReport(String strReportName, String transReport) {
        ArrayList<String> listErrorMessage = htmlParser.parseXFlowReport(transReport);
        if (listErrorMessage != null) {
            for (String errorMessage : listErrorMessage) {
                if (mapErrorDeta.containsKey(errorMessage))
                    mapErrorDeta.get(errorMessage).add("");
                else {
                    ArrayList<String> listTmp = new ArrayList<String>();
                    listTmp.add("");
                    mapErrorDeta.put(errorMessage, listTmp);
                }
            }
        }
    }

}
