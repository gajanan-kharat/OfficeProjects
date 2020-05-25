/*
 * Creation : 30 juin 2016
 */
package com.psa.plm.te.jobtypes.up;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.psa.plm.te.tools.PSATEGenericfunctions;
import com.psa.plm.te.tools.PSATEMufoConfig;
import com.psa.plm.te.tools.PSATEPatternMatcher;

class PSATEVPMExchangeAutomationParser {
    public static final String STR_HTMLFILE_NAME = "VPMExchangeAutomation.htm";
    public static final String STR_XFLOWFILE_NAME = "outputXFlowXML.xml";
    private String strExchangeFile = "";
    private String strXFlowFile = "";
    private String strStatus = "";
    private String[] strKOStatus = null;

    public PSATEVPMExchangeAutomationParser(String strFile) {
        strExchangeFile = strFile;
    }

    public void processHTML() {
        String strFileContent = readfileContent(strExchangeFile);
        getHTMLStatusData(strFileContent);
    }

    public String getStatus() {
        return strStatus;
    }

    public String getMessage() {
        String strMessage = "";
        if (strKOStatus != null) {
            for (String value : strKOStatus) {
                if (!value.contains(" ") && !value.startsWith("&")) {
                    strMessage += "_" + value;
                } else if (value.contains(" ") && !value.startsWith("&")) {
                    strMessage += "\n" + value;
                }
            }
        }
        return strMessage;
    }

    public String getHTMLMessage() {
        String strMessage = "";
        if (strKOStatus != null) {
            strMessage = "<TABLE BORDERCOLOR=GRAY CELLPADDING=5 CELLSPACING=0 WIDTH=1400 BORDER=1 BGCOLOR=RED>";
            for (String value : strKOStatus) {
                if (!value.contains(" ") && !value.startsWith("&")) {
                    strMessage += "<TR><TD>";
                    strMessage += "_" + value;
                    strMessage += "</TD></TR>";
                } else if (value.contains(" ") && !value.startsWith("&")) {
                    strMessage += "\n" + value;
                }
            }
            strMessage = "</TABLE>";
        }
        return strMessage;
    }

    public ArrayList<String> parseXFlowErrorReport(String strReportPath) {
        ArrayList<String> listDetails = null;
        String strXFloxXML = strReportPath + File.separator + PSATEVPMExchangeAutomationParser.STR_XFLOWFILE_NAME;
        if (PSATEGenericfunctions.isFileExist(strXFloxXML)) {
            String strData = readfileContent(strXFloxXML);
            String[] arrError = PSATEPatternMatcher.execPattern(PSATEMufoConfig.STR_ERROR_MESSAGE_PATTERN, strData);
            if (arrError != null) {
                listDetails = new ArrayList<String>();
                for (String value : arrError) {
                    //if (!listDetails.contains(value))
                        listDetails.add(value);
                }
            }
        }
        return listDetails;
    }

    public ArrayList<String> parseXFlowReport(String transReport) {
        ArrayList<String> listMessage = null;
        String strData = readfileContent(transReport);
        if (strData != null) {
            listMessage = PSATEPatternMatcher.getTransactionReportError(strData);
        }
        return listMessage;
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
                    strContent.append(strLine);
            }
        } catch (IOException e) {

        }

        return strContent.toString();
    }

    private void getHTMLStatusData(String strHtmlData) {
        // Get the Error Block from the HTML data read
        String strStatusBlock = PSATEPatternMatcher.execPattern(PSATEMufoConfig.STR_STATUS_BLOCK, strHtmlData, 1);
        if (strStatusBlock.length() > 1) {
            strKOStatus = PSATEPatternMatcher.execPattern(PSATEMufoConfig.STR_HTML_ERROR_DETAILS, strStatusBlock);
            if (strKOStatus != null && strKOStatus.length > 0)
                // If the data is found then this is a part of KO message..
                strStatus = "KO";
        } else {
            // No data found in the status block, hence it is marked as OK.
            strStatus = "OK";
        }
    }

}
