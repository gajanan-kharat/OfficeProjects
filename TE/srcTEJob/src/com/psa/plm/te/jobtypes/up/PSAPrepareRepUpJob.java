/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.jobtypes.up;

import com.psa.plm.te.jobtypes.PSAGenericJob;

public class PSAPrepareRepUpJob extends PSAGenericJob {

    public PSAPrepareRepUpJob(String strjobID) {
        super(strjobID);
        strJobType = "plm_te_prepare_rep";
    }

    @Override
    public void initJobDetails(String strExaPath, StringBuilder rapportFileContent) {
        // 1. Nothing is indexed...
        strReportPath = "";

        // 2. Get the details of the job
        getjobReportDetails(rapportFileContent);
    }

    @Override
    public void processIndexedData() {
        // Nothing to process...NOP
    }

}
