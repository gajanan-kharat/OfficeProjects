/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PSATEPatternMatcher {

    public PSATEPatternMatcher() {

    }

    public static String execPattern(String strpattern, String strData, int ngroup) {
        String strResult = "";
        Pattern pattern = Pattern.compile(strpattern);
        Matcher mresult = pattern.matcher(strData);
        if (mresult != null) {
            while (mresult.find()) {
                if (ngroup == -1)
                    strResult = mresult.group() != null ? mresult.group() : "";
                else
                    strResult = mresult.group(ngroup) != null ? mresult.group(ngroup) : "";
                break;
            }
        }
        return strResult;
    }

    public static String[] execPattern(String strPattern, String strData) {
        ArrayList<String> listTmpData = new ArrayList<String>();
        String[] strResult = null;
        Pattern pattern = Pattern.compile(strPattern);
        Matcher mresult = pattern.matcher(strData);
        if (mresult != null) {
            if (mresult.find()) {
                int ngroups = mresult.groupCount();
                strResult = new String[ngroups];
                for (int i = 1; i <= ngroups; i++)
                    strResult[i - 1] = mresult.group(i) != null ? mresult.group(i) : "";
            }
        }
        return strResult;
    }

    public static boolean isMatching(String strPattern, String strData) {
        return Pattern.matches(strPattern, strData);
    }

    public static ArrayList<String> getMatchList(String strPattern, String strData, int ngroupID) {
        ArrayList<String> listValues = new ArrayList<String>();
        Pattern pattern = Pattern.compile(strPattern);
        Matcher mresult = pattern.matcher(strData);
        if (mresult != null) {
            while (mresult.find()) {
                listValues.add(mresult.group(ngroupID) != null ? mresult.group(ngroupID) : "");
            }
        }
        return listValues;
    }

    public static HashMap<String, ArrayList<String>> execPatternForMap(String strJobDetailsPattern, StringBuilder rapportFileContent, int i) {
        HashMap<String, ArrayList<String>> mapDetails = null;
        Pattern pattern = Pattern.compile(strJobDetailsPattern);
        Matcher mresult = pattern.matcher(rapportFileContent);
        // System.out.println();
        // System.out.println(strJobDetailsPattern);
        if (mresult != null) {
            mapDetails = new HashMap<String, ArrayList<String>>();
            while (mresult.find()) {
                // System.out.println("Value --> " + mresult.group());
                String key = "";
                String value = "";
                if (i == 1) {
                    key = mresult.group(1) != null ? mresult.group(1) : "";
                    value = mresult.group(2) != null ? mresult.group(2) : "";
                } else {
                    key = mresult.group(2) != null ? mresult.group(2) : "";
                    value = mresult.group(1) != null ? mresult.group(1) : "";
                }
                key = key.trim();
                value = value.trim();
                if (key.length() > 0) {
                    // System.out.println("Key-->"+key+"    value-->"+value);
                    if (mapDetails.containsKey(key)) {
                        mapDetails.get(key).add(value);
                    } else {
                        ArrayList<String> listValue = new ArrayList<String>();
                        listValue.add(value);
                        mapDetails.put(key, listValue);
                    }
                }
            }
        }
        // System.out.println(mapDetails);
        // System.out.println();
        return mapDetails;
    }

    public static HashMap<String, ArrayList<String>> execPatternForGroupMap(String strJobDetailsPattern, String rapportFileContent) {
        HashMap<String, ArrayList<String>> mapDetails = null;
        Pattern pattern = Pattern.compile(strJobDetailsPattern);
        Matcher mresult = pattern.matcher(rapportFileContent);
        if (mresult != null) {
            mapDetails = new HashMap<String, ArrayList<String>>();
            int nGroups = mresult.groupCount();
            while (mresult.find()) {
                for (int i = 1; i <= nGroups; i++) {
                    String strGrpKey = "Group_" + i;
                    if (mapDetails.containsKey(strGrpKey)) {
                        mapDetails.get(strGrpKey).add(mresult.group(i) != null ? mresult.group(i) : "");
                    } else {
                        ArrayList<String> listValue = new ArrayList<String>();
                        listValue.add(mresult.group(i) != null ? mresult.group(i) : "");
                        mapDetails.put(strGrpKey, listValue);
                    }
                }// for
            }// while
        }
        return mapDetails;
    }

    public static ArrayList<String> getTransactionReportError(String strData) {
        ArrayList<String> listMessage = new ArrayList<String>();
        Pattern pattern = Pattern.compile(PSATEMufoConfig.STR_ERROR_MESSASE_TRANSACTIONXML);
        Matcher m = pattern.matcher(strData);
        while (m != null && m.find()) {
            String strMessage = (m.group(3) != null ? m.group(3) : "") + ": " + (m.group(1) != null ? m.group(1) : "");
            if (!listMessage.contains(strMessage))
                listMessage.add(strMessage);
        }
        return listMessage;
    }
}
