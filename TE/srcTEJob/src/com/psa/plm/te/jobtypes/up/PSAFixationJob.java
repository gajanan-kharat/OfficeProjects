/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.jobtypes.up;

import com.psa.plm.te.jobtypes.PSAGenericJob;

public class PSAFixationJob extends PSAGenericJob {

    public PSAFixationJob(String strjobID) {
        super(strjobID);
        strJobType = "plm_te_fixation_fixation";
    }

    @Override
    public void initJobDetails(String strExaPath, StringBuilder rapportFileContent) {
        // 1.Set the link to the file where the csv file is copied
        strReportPath = "";

        // 2. Get the details of the job
        getjobReportDetails(rapportFileContent);
    }

    @Override
    public void processIndexedData() {
        // Nothing to process...NOP
    }

}
