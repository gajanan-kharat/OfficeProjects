/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.jobtypes.up;

import com.psa.plm.te.jobtypes.PSAGenericJob;

public class PSADetModifJob extends PSAGenericJob {

    public PSADetModifJob(String strjobID) {
        super(strjobID);
        strJobType = "plm_tf_detmodif";
    }

    @Override
    public void initJobDetails(String strExaPath, StringBuilder rapportFileContent) {
        // 1. Get the details of the job
        getjobReportDetails(rapportFileContent);
    }

    @Override
    public void processIndexedData() {
        // Nothing to process....NOP
    }

}
