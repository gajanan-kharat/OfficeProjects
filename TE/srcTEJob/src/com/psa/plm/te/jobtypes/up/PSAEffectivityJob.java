/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.jobtypes.up;

import com.psa.plm.te.jobtypes.PSAGenericJob;

public class PSAEffectivityJob extends PSAGenericJob {

    public PSAEffectivityJob(String strjobID) {
        super(strjobID);
        strJobType = "plm_tf_effectivity";
    }

    @Override
    public void initJobDetails(String strExaPath, StringBuilder rapportFileContent) {
        // 1. Nothing is copied in EXALEAD PATH
        strReportPath = "";

        // 2. Get the details of the job
        getjobReportDetails(rapportFileContent);
    }

    @Override
    public void processIndexedData() {
        // Nothing to process....NOP
    }

}
