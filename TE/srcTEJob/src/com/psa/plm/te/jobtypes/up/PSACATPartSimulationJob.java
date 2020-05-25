/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.jobtypes.up;

import com.psa.plm.te.jobtypes.PSAGenericJob;

public class PSACATPartSimulationJob extends PSAGenericJob {

    public PSACATPartSimulationJob(String strjobID) {
        super(strjobID);
        strJobType = "plm_te_upward_catpart_Simulation";
    }

    @Override
    public void initJobDetails(String strExaPath, StringBuilder rapportFileContent) {
        // 1.Nothing of this JOB is indexed in EXQLEAD
        strReportPath = "";

        // 2. Get the details of the job
        getjobReportDetails(rapportFileContent);

    }

    @Override
    public void processIndexedData() {
        // Nothing to process..NOP
    }

}
