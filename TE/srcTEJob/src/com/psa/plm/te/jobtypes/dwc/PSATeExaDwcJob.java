/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.jobtypes.dwc;

import com.psa.plm.te.jobtypes.PSAGenericJob;

public class PSATeExaDwcJob extends PSAGenericJob {

    public PSATeExaDwcJob(String strjobID) {
        super(strjobID);
        strJobType = "plm_te_exalead_downward";
    }

    @Override
    public void initJobDetails(String strExaPath, StringBuilder rapportFileContent) {
        // 1. Set the Report Path to empty as this data is present the job plm_te_exalead_PSAApplicationEffectivite
        strReportPath = "";

        // 2. Get the details of the job
        getjobReportDetails(rapportFileContent);
    }

    @Override
    public void processIndexedData() {
        // Nothing to process...NOP
    }

}
