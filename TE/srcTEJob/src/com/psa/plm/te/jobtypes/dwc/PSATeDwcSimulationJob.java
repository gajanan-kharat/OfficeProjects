/*
 * Creation : 6 Jan 2017
 */
package com.psa.plm.te.jobtypes.dwc;

import java.io.File;
import java.util.ArrayList;

import com.psa.plm.te.jobtypes.PSAGenericJob;

public class PSATeDwcSimulationJob extends PSAGenericJob {

    private final String strExaSubfolder = "dwcsimu";

    public PSATeDwcSimulationJob(String strjobID) {
        super(strjobID);
        strJobType = "plm_te_dwc_simulation";
    }

    @Override
    public void initJobDetails(String strExaPath, StringBuilder rapportFileContent) {

        // Get the details of the Job
        getjobReportDetails(rapportFileContent);

        // Set the report path
        strReportPath = strExaPath + File.separator + strExaSubfolder + File.separator + strJobID + File.separator + "exalead";

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

    @Override
    public void processIndexedData() {
        // nothing to process here

    }

}
