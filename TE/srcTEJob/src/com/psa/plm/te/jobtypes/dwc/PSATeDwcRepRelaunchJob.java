/*
 * Creation : 12 Dec 2016
 */
package com.psa.plm.te.jobtypes.dwc;

import java.io.File;
import java.util.ArrayList;

import com.psa.plm.te.jobtypes.PSAGenericJob;
import com.psa.plm.te.tools.PSATEMufoConfig;
import com.psa.plm.te.tools.PSATEPatternMatcher;

public class PSATeDwcRepRelaunchJob extends PSAGenericJob {

    private final String strExaSubfolder = "relaunchrep";
    protected String strParentJobID = "";

    public PSATeDwcRepRelaunchJob(String strjobID) {
        super(strjobID);
        strJobType = "plm_te_dwc_relaunchrep";
    }

    @Override
    public void initJobDetails(String strExaPath, StringBuilder rapportFileContent) {

        getjobReportDetails(rapportFileContent);

        strReportPath = strExaPath + File.separator + strExaSubfolder + File.separator + strParentJobID + File.separator + strJobID + File.separator
                + "exalead";

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
        // nothing to process here....
    }

    protected void getjobReportDetails(StringBuilder rapportFileContent) {

        super.getjobReportDetails(rapportFileContent);

        String strParentIDPattern = PSATEMufoConfig.STR_PARENT_JOBID;
        strParentIDPattern = strParentIDPattern.replace("%JOBTYPE%", "plm_te_dwc_relaunchrep");
        strParentIDPattern = strParentIDPattern.replace("%JOBID%", strJobID);
        strParentJobID = PSATEPatternMatcher.execPattern(strParentIDPattern, rapportFileContent.toString(), 1);
    }
}
