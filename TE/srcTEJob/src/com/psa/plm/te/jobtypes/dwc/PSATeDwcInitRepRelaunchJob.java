/*
 * Creation : 12 Dec 2016
 */
package com.psa.plm.te.jobtypes.dwc;

import com.psa.plm.te.jobtypes.PSAGenericJob;

public class PSATeDwcInitRepRelaunchJob extends PSAGenericJob {

    private long totalRepRelaunch = 0;

    public PSATeDwcInitRepRelaunchJob(String strjobID) {
        super(strjobID);
        strJobType = "plm_te_dwc_init_reprelaunch";
    }

    @Override
    public void initJobDetails(String strExaPath, StringBuilder rapportFileContent) {
        strReportPath = "";
        getjobReportDetails(rapportFileContent);
    }

    @Override
    public void processIndexedData() {
        // nothing to process here....
    }

}
