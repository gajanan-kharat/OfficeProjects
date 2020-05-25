/*
 * Creation : 4 juil. 2016
 */
package com.psa.plm.te.mail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import com.psa.plm.te.processReport.PSATETechnicalObject;
import com.psa.plm.te.tools.PSATEGenericfunctions;
import com.psa.plm.te.tools.PSATEMufoConfig;

public class PSAJobStatusHtmlTable {

    String strTableHtml = "";

    private final String tagOpenTABLE = "<table class=\"tg\">";
    private final String tagCloseTABLE = "</table>";

    private final String tagOpenTH = "<th class=\"tg-2ktp\">";
    private final String tagCloseTH = "</th>";

    private final String tagOpenTR = "<tr>";
    private final String tagCloseTR = "</tr>";

    private final String tagOpenEvenTD = "<td class=\"tg-b7b8\">";
    private final String tagOpenOddTD = "<td class=\"tg-yw4l\">";
    private final String tagOpenEvenRSTD = "<td rowspan=\"<ROWS>\" class=\"tg-dzk6\">";
    private final String tagOpenOddRSTD = "<td rowspan=\"<ROWS>\"  class=\"tg-ya56\">";
    private final String tagCloseTD = "</td>";

    public PSAJobStatusHtmlTable() {
        // Nothing to Do
    }

    public String getTableData() {
        return strTableHtml;
    }

    public void createRetCodeTable(HashMap<String, PSATETechnicalObject> mapTechObject) {
        String strCreateTableHeader = CreateTableHeader(mapTechObject.keySet());
        String strCreateExecutionStatus = CreateStatusRows(mapTechObject);
        String strCrateRetcodeRows = getReturnCodeDetails(mapTechObject);
        strTableHtml = tagOpenTABLE + strCreateTableHeader + strCreateExecutionStatus + strCrateRetcodeRows + tagCloseTABLE;

        // System.out.println(strTableHtml);
    }

    private String CreateTableHeader(Set<String> keySet) {
        String strTableHeader = tagOpenTR;
        ArrayList<String> keys = PSATEGenericfunctions.getCSVKeyInOrder(keySet);
        // 1. Create the first 2 empty headers
        strTableHeader += tagOpenTH + tagCloseTH + "\n" + tagOpenTH + tagCloseTH + "\n";

        // 2. Create the rest of the header
        for (String key : keys) {
            strTableHeader += tagOpenTH + PSATEMufoConfig.mapCSVHeader.get(key) + tagCloseTH + "\n";
        }

        // Add the row tags to complete row
        strTableHeader = tagOpenTR + strTableHeader + tagCloseTR + "\n";

        return strTableHeader;
    }

    private String CreateStatusRows(HashMap<String, PSATETechnicalObject> mapTechObject) {
        String strExecRow = "";
        ArrayList<String> keys = PSATEGenericfunctions.getCSVKeyInOrder(mapTechObject.keySet());

        String strLine1 = tagOpenOddRSTD.replace("<ROWS>", "2") + "Complétude" + tagCloseTD + tagOpenOddTD + PSATEMufoConfig.CSV_TOTALNEEDED
                + tagCloseTD + "\n";
        String strLine2 = tagOpenEvenTD + PSATEMufoConfig.CSV_TOTALEXECUTED + tagCloseTD + "\n";

        for (String key : keys) {
            strLine1 += tagOpenOddTD + mapTechObject.get(key).strRequired + tagCloseTD + "\n";
            strLine2 += tagOpenEvenTD + mapTechObject.get(key).strExecuted + tagCloseTD + "\n";
        }

        strExecRow = tagOpenTR + strLine1 + tagCloseTR + "\n" + tagOpenTR + strLine2 + tagCloseTR + "\n";

        return strExecRow;
    }

    private String getReturnCodeDetails(HashMap<String, PSATETechnicalObject> mapTechObject) {
        String strData = "";

        // 1. Get the list of unique Return code to consider for display
        ArrayList<String> listErrorVal = getUniqueValues(mapTechObject);

        // 2. Create a Map with the per code the list of rows
        HashMap<String, ArrayList<String>> mapRetCodeRows = new HashMap<String, ArrayList<String>>();

        for (String retCode : listErrorVal) {
            ArrayList<String> strLines = getLineForCode(retCode, mapTechObject);
            if (strLines != null) {
                mapRetCodeRows.put(retCode, strLines);
            }
        }

        // 3. Create the overall table rows from the data obtained.
        Set<String> retcodeKeys = mapRetCodeRows.keySet();
        boolean bfirstRowdone = false;
        int nResultSpan = 0;
        // Get the span needed of the first data in the named Résultat
        for (String retcode : retcodeKeys) {
            nResultSpan += mapRetCodeRows.get(retcode).size();
        }
        // Start creating the rows:
        boolean bOdd = true;
        for (String retcode : retcodeKeys) {
            ArrayList<String> listColumns = mapRetCodeRows.get(retcode);
            String strRetcodeCol = "";
            if (bOdd) {
                strRetcodeCol = tagOpenOddRSTD.replace("<ROWS>", Integer.toString(listColumns.size())) + retcode + tagCloseTD;
                bOdd = false;
            } else {
                strRetcodeCol = tagOpenEvenRSTD.replace("<ROWS>", Integer.toString(listColumns.size())) + retcode + tagCloseTD;
                bOdd = true;
            }
            for (String colData : listColumns) {
                if (bfirstRowdone == false) {
                    if (bOdd) {
                        strData = tagOpenTR + tagOpenOddRSTD.replace("<ROWS>", Integer.toString(nResultSpan)) + "Résultat" + tagCloseTD + "\n"
                                + strRetcodeCol + colData + "\n" + tagCloseTR + "\n";
                        bOdd = false;
                    } else {
                        strData = tagOpenTR + tagOpenEvenRSTD.replace("<ROWS>", Integer.toString(nResultSpan)) + "Résultat" + tagCloseTD + "\n"
                                + strRetcodeCol + colData + "\n" + tagCloseTR + "\n";
                        bOdd = true;
                    }
                    bfirstRowdone = true;
                } else {
                    strData += tagOpenTR + strRetcodeCol + colData + "\n" + tagCloseTR + "\n";
                }
                strRetcodeCol = "";
            }
        }

        return strData;
    }

    private ArrayList<String> getLineForCode(String retCode, HashMap<String, PSATETechnicalObject> mapJobDetails) {
        ArrayList<String> listLine = new ArrayList<String>();
        int nLine = 0;
        boolean bfound = true;
        boolean bOdd = true;
        ArrayList<String> keys = PSATEGenericfunctions.getCSVKeyInOrder(mapJobDetails.keySet());
        while (bfound) {
            String strLine = "";
            bfound = false;
            bOdd = !bOdd;

            for (String job : keys) {
                PSATETechnicalObject obj = mapJobDetails.get(job);
                if (obj.mapReturncode.containsKey(retCode)) {
                    ArrayList<String> listJobID = obj.mapReturncode.get(retCode);
                    if (listJobID.size() > nLine) {
                        if (bOdd)
                            strLine += tagOpenOddTD + listJobID.get(nLine) + tagCloseTD;
                        else
                            strLine += tagOpenEvenTD + listJobID.get(nLine) + tagCloseTD;
                        bfound |= true;
                    } else {
                        if (bOdd)
                            strLine += tagOpenOddTD + "-" + tagCloseTD;
                        else
                            strLine += tagOpenEvenTD + "-" + tagCloseTD;
                        bfound |= false;
                    }
                } else {
                    bfound |= false;
                    if (bOdd)
                        strLine += tagOpenOddTD + "-" + tagCloseTD;
                    else
                        strLine += tagOpenEvenTD + "-" + tagCloseTD;
                }
            }

            if (bfound) {
                listLine.add(strLine);
                nLine++;
            }
        }
        return listLine;
    }

    private ArrayList<String> getUniqueValues(HashMap<String, PSATETechnicalObject> mapJobDetails) {
        ArrayList<String> listcodes = new ArrayList<String>();
        Set<String> keys = mapJobDetails.keySet();
        for (String key : keys) {
            Set<String> errCode = mapJobDetails.get(key).mapReturncode.keySet();
            for (String code : errCode) {
                if (!listcodes.contains(code))
                    listcodes.add(code);
            }
        }
        if (listcodes.size() > 0)
            Collections.sort(listcodes);
        listcodes.removeAll(PSATEMufoConfig.listCSVRetCodeToIgnore);
        // from 99 to 0
        Collections.reverse(listcodes);

        return listcodes;
    }
}
