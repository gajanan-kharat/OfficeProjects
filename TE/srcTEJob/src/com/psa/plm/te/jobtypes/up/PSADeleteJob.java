/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.jobtypes.up;

import java.io.File;

import com.psa.plm.te.jobtypes.PSAGenericJob;

public class PSADeleteJob extends PSAGenericJob {
    private static final String strExaSubfolder = "delete";

    public PSADeleteJob(String strjobID) {
        super(strjobID);
        strJobType = "plm_te_deletepsa";
    }

    @Override
    public void initJobDetails(String strExaPath, StringBuilder rapportFileContent) {
        // 1. Build the EXALEAD PATH
        strReportPath = strExaPath + File.separator + strExaSubfolder + File.separator + strJobType + File.separator + strJobID + File.separator
                + "exalead";

        // 2. Get the details of the job
        getjobReportDetails(rapportFileContent);

    }

    @Override
    public void processIndexedData() {
        // Nothing to process....NOP
    }

}
