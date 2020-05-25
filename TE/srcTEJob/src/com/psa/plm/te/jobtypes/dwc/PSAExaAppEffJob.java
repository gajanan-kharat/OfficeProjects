/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.jobtypes.dwc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.psa.plm.te.jobtypes.PSAGenericJob;
import com.psa.plm.te.tools.PSATEGenericfunctions;
import com.psa.plm.te.tools.PSATEPatternMatcher;

public class PSAExaAppEffJob extends PSAGenericJob {

    private static final String strExaSubfolder = "PSAApplicationEffectivite";

    private String strXFlowXml = "";

    private String strRootPart = "";

    private String strDivImpJobID = "";

    public PSAExaAppEffJob(String strjobID) {
        super(strjobID);
        strJobType = "plm_te_exalead_PSAApplicationEffectivite";
    }

    @Override
    public void initJobDetails(String strExaPath, StringBuilder rapportFileContent) {
        // 1. Set the Report Path
        strReportPath = strExaPath + File.separator + strExaSubfolder + File.separator + strJobID + File.separator + "exalead";

        // 2. Get the details of the job
        getjobReportDetails(rapportFileContent);
    }

    @Override
    public void processIndexedData() {
        // 1. Get the XFlow XML file.
        String strNamePattern = "Report_XFLOWXML_\\d+_\\d+\\.xml";
        String[] arrFile = PSATEGenericfunctions.getDirectoryChild(strReportPath, strNamePattern, true);
        if (arrFile != null && arrFile.length > 0) {
            strXFlowXml = arrFile[0];
            getParentJobDetails();
        }
    }

    public String getParentJobId() {
        return strDivImpJobID;
    }

    public String getRootPart() {
        return strRootPart;
    }

    public String getReportPath() {
        return strReportPath + File.separator + strXFlowXml;
    }

    private void getParentJobDetails() {
        String strRootDetailPattern = "(?m).*Input_File\"\\>/scratch/lsftmp/lsf_\\w+_(\\d+)/([\\w\\-]+)\\.csv";
        String strData = readfileContent(strReportPath + File.separator + strXFlowXml);
        String[] arrDetails = PSATEPatternMatcher.execPattern(strRootDetailPattern, strData);
        if (arrDetails != null && arrDetails.length == 2) {
            strDivImpJobID = arrDetails[0] + "_1";
            strRootPart = arrDetails[1];
        }

    }

    private String readfileContent(String strFile) {
        StringBuilder strContent = null;
        try {
            File rptfile = new File(strFile);
            strContent = new StringBuilder();
            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(new FileReader(rptfile));
            while (reader.ready()) {
                // Read Line-by-line and remove all the starting white-spaces
                String strLine = reader.readLine().replaceFirst("^\\s+", "");
                if (strLine.length() > 0)
                    strContent.append(strLine + "\n");
            }
        } catch (IOException e) {

        }

        return strContent.toString();
    }
}
