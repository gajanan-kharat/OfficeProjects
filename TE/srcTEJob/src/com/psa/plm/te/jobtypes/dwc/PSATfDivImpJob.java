/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.jobtypes.dwc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.psa.plm.te.jobtypes.PSAGenericJob;
import com.psa.plm.te.tools.PSATEPatternMatcher;

public class PSATfDivImpJob extends PSAGenericJob {

    PSAExaAppEffJob exaJob = null;

    String strGlobalStatus = "";

    public PSATfDivImpJob(String strjobID) {
        super(strjobID);
        strJobType = "plm_tf_divimp";
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
        if (exaJob != null) {
            setErrorStatus();
        }
    }

    public void setExaLeadJob(HashMap<String, PSAExaAppEffJob> mapExaJob) {
        exaJob = mapExaJob.get(strJobID);
    }

    private void setErrorStatus() {
        // <GlobalStatus Status="WARNING"/>
        String strGlobalStatusPattern = "(?m)\\<GlobalStatus\\sStatus=\"(\\w+)\"/\\>";

        // <StatusDetailInfo Type="Global" Level="Error">Error processing input effectivity</StatusDetailInfo>
        // <StatusDetailInfo Type="Global" Level="Error">Error getting V4 child</StatusDetailInfo>
        String strErrorDetailsPattern = "(?m)\\<StatusDetailInfo\\sLevel=\"Error\"\\sType=\"Global\"\\>(.*?)\\</StatusDetailInfo\\>";

        String strfiledata = readfileContent(exaJob.getReportPath());

        strGlobalStatus = PSATEPatternMatcher.execPattern(strGlobalStatusPattern, strfiledata, 1);

        ArrayList<String> listErrorMessages = PSATEPatternMatcher.getMatchList(strErrorDetailsPattern, strfiledata, 1);
        if (listErrorMessages != null && listErrorMessages.size() > 0) {
            for (String errMsg : listErrorMessages) {
                if (mapErrorDeta.containsKey(errMsg)) {
                    mapErrorDeta.get(errMsg).add("");
                } else {
                    ArrayList<String> listTemp = new ArrayList<String>();
                    listTemp.add("");
                    mapErrorDeta.put(errMsg, listTemp);
                }
            }
        }
    }

    private String readfileContent(String strFile) {
        StringBuilder strContent = null;
        try {
            File rptfile = new File(strFile);
            strContent = new StringBuilder();
            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(new FileReader(rptfile));
            while (reader.ready()) {
                // Read Line-by-line and remove all the starting white-spaces
                String strLine = reader.readLine().replaceFirst("^\\s+", "");
                if (strLine.length() > 0)
                    strContent.append(strLine + "\n");
            }
        } catch (IOException e) {

        }

        return strContent.toString();
    }
}
