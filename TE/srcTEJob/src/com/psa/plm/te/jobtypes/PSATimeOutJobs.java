/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.jobtypes;

public class PSATimeOutJobs extends PSAGenericJob {

    public PSATimeOutJobs(String strjobID) {
        super(strjobID);
        strJobType = "TimeOutJob";
    }

    @Override
    public void initJobDetails(String strExaPath, StringBuilder rapportFileContent) {
        strReportPath = "";
        getjobReportDetails(rapportFileContent);
    }

    @Override
    public void processIndexedData() {
        // Nothing to process...NOP
    }

}
