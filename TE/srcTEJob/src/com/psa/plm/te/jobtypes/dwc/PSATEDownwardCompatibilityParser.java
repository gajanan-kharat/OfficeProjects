package com.psa.plm.te.jobtypes.dwc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.psa.plm.te.tools.PSATEPatternMatcher;

public class PSATEDownwardCompatibilityParser {
    public static final String STR_HTMLFILE_NAME = "DownwardCompatibility.htm";
    private String strExchangeFilePath = "";
    private String strDataInput = "";
    private String strStatus = "";
    private String strStatusMessage = null;

    public PSATEDownwardCompatibilityParser(String strFilePath) {
        strExchangeFilePath = strFilePath;
    }

    public void processHTML() {
        String strFileContent = readfileContent(strExchangeFilePath + File.separator + STR_HTMLFILE_NAME, true);
        getHTMLStatusData(strFileContent);
    }

    public String getStatus() {
        return strStatus;
    }

    public String getDataInput() {
        return strDataInput;
    }

    public String getStatusMessage() {
        return strStatusMessage;
    }

    private String readfileContent(String strFile, boolean bLine) {
        StringBuilder strContent = null;
        try {
            File rptfile = new File(strFile);
            strContent = new StringBuilder();
            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(new FileReader(rptfile));
            while (reader.ready()) {
                // Read Line-by-line and remove all the starting white-spaces
                String strLine = reader.readLine().replaceFirst("^\\s+", "");
                if (strLine.length() > 0) {
                    if (bLine)
                        strContent.append(strLine + "\n");
                    else
                        strContent.append(strLine);
                }
            }
        } catch (IOException e) {

        }

        return strContent.toString();
    }

    private void getHTMLStatusData(String strHtmlData) {
        // <TR><TD>P000180194-DWC2</TD><TD><A ".\P000180194-DWC2.dwc_convert_traces.htm"><font color=red> KO</font></A></TD></TR>
        String dwcStatusPattern = "(?m)^\\<TR\\>\\<TD\\>(.*?)\\</TD\\>.*\\<font.*\\>\\s?(\\w+?)\\</font.*";
        String dwcSuccessDetailPattern = "(?m)(.*Code\\sde\\sRetour.*)";
        String dwcErrorDetailsPattern = "(?m)(^Process ends with.*)";
        // Get the Error Block from the HTML data read
        String[] strData = PSATEPatternMatcher.execPattern(dwcStatusPattern, strHtmlData);
        if (strData != null) {
            strStatus = strData[1];
            strDataInput = strData[0];
            if (strStatus.equals("OK"))
                strStatusMessage = PSATEPatternMatcher.execPattern(dwcSuccessDetailPattern, strHtmlData, 1);
            else
                strStatusMessage = PSATEPatternMatcher.execPattern(dwcErrorDetailsPattern, strHtmlData, 1);
        }

    }
}
