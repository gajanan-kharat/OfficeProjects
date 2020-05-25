/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.jobtypes.up;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.psa.plm.te.jobtypes.PSAGenericJob;
import com.psa.plm.te.tools.PSATEGenericfunctions;
import com.psa.plm.te.tools.PSATEPatternMatcher;

public class PSAInstantiationJob extends PSAGenericJob {

    private static final String strExaSubfolder = "INSTANCIATION";

    private String strOutputXML = "";

    private String strGlobalStatus = "";

    private String strDiagonalInputXML = "";

    public PSAInstantiationJob(String strjobID) {
        super(strjobID);
        strJobType = "plm_te_posttraitement_INSTANCIATION";
    }

    @Override
    public void initJobDetails(String strExaPath, StringBuilder rapportFileContent) {
        // 1. Build the EXALEAD PATH
        strReportPath = strExaPath + File.separator + strExaSubfolder + File.separator + strJobID + File.separator + "exalead";

        // 2. Get the details of the job
        getjobReportDetails(rapportFileContent);
    }

    @Override
    public void processIndexedData() {
        // 1. Get the Diagonal Input xml for association with correct diagonal job.
        getDiagonalInputXML();

        // 2. Get the error details from the output xml
        getErrorDetails();
    }

    public final String getDiagonalInputName() {
        return strDiagonalInputXML;
    }

    private void getDiagonalInputXML() {
        String strPattern = "^PSAVpmInterfaceInsUpdateBatch_\\d+_\\d+\\.xml";
        String[] listfiles = PSATEGenericfunctions.getDirectoryChild(strReportPath, strPattern, true);
        if (listfiles != null && listfiles.length > 0) {
            strOutputXML = listfiles[0];
        }
    }

    private void getErrorDetails() {
        String strDiagonalInputPattern = "(?:\"DiagonalIn\")\\>.*?(diagonal.*?xml)<[/]";
        String strGlobalProcessStatus = "(?:\\<GlobalStatus\\s\\w+\\s=\\s\"(\\w+)\"[/]\\>)";
        String strErrorDetailsPattern = "(?:Object(?:.*?)([\\w\\-]+)\\|([\\w\\-]{3})?\"\\sStatus\\s=\\s\"KO\"(?:.*?)Global\"\\>([\\w\\s[^<]]+))";

        StringBuilder strBufferData = PSATEGenericfunctions.getFileContent(strReportPath + File.separator + strOutputXML);

        strDiagonalInputXML = PSATEPatternMatcher.execPattern(strDiagonalInputPattern, strBufferData.toString(), 1);

        strGlobalStatus = PSATEPatternMatcher.execPattern(strGlobalProcessStatus, strBufferData.toString(), 1);

        HashMap<String, ArrayList<String>> mapKODetails = PSATEPatternMatcher
                .execPatternForGroupMap(strErrorDetailsPattern, strBufferData.toString());
        // Group_1 --> Name //Group_2 --> Version //Group_3 --> Global Error message
        final ArrayList<String> listObjName = mapKODetails.get("Group_1");
        final ArrayList<String> listObjVersion = mapKODetails.get("Group_2");
        final ArrayList<String> listErrorDetails = mapKODetails.get("Group_3");
        int i = 0;
        // Keep the error as per the common messages found
        if ((listObjName != null) && (listObjVersion != null) && (listErrorDetails != null)) {

            for (String errMsg : listErrorDetails) {
                String strObject = listObjName.get(i) + "|" + (listObjVersion.get(i) == null ? "" : listObjVersion.get(i));
                if (!mapErrorDeta.containsKey(errMsg)) {
                    ArrayList<String> listErrObj = new ArrayList<String>();
                    listErrObj.add(strObject);
                    mapErrorDeta.put(errMsg, listErrObj);
                } else {
                    mapErrorDeta.get(errMsg).add(strObject);
                }
                i++;
            }
        }
    }

}
