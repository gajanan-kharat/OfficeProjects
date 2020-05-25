/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.jobtypes.dwc;

import com.psa.plm.te.jobtypes.PSAGenericJob;

public class PSATfDivExpJob extends PSAGenericJob {

    public PSATfDivExpJob(String strjobID) {
        super(strjobID);
        strJobType = "plm_tf_divexp";
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
