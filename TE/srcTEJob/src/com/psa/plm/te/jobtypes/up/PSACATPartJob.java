/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.jobtypes.up;

import java.io.File;

public class PSACATPartJob extends PSAUpExchngAutomation {

    private static final String strExaSubfolder = "catpart";

    public PSACATPartJob(String strJobID) {
        super(strJobID);
        strJobType = "plm_te_upward_catpart";
    }

    @Override
    public void initJobDetails(String strExaPath, StringBuilder rapportFileContent) {
        // 1. Build the EXALEAD PATH
        strReportPath = strExaPath + File.separator + strExaSubfolder + File.separator + strJobID + File.separator + "exalead";

        // 2. Get the details of the job
        getjobReportDetails(rapportFileContent);
    }

}
