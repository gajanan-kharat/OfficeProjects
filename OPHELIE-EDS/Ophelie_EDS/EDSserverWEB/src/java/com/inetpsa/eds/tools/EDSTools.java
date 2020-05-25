package com.inetpsa.eds.tools;

import java.util.Locale;

import com.inetpsa.eds.dao.model.EdsWording;

/**
 * This class provide tool for combining several useful static methods for the application.
 * 
 * @author Geometric Ltd.
 */
public class EDSTools {
    // PUBLIC
    /**
     * This method convert empty string to null.
     * 
     * @param string String value
     * @return null if string is empty
     */
    public static String convertEmptyStringToNull(String string) {
        if (string == null) {
            return null;
        }

        String convertedString = string.trim();

        if (convertedString.length() == 0) {
            return null;
        }
        return convertedString;

    }

    /**
     * This method returns String of EDSWording in given locale
     * 
     * @param wording Eds wording details
     * @param locale Locale value
     * @return String value in specified locale
     */
    public static String getWordingValueByLocale(EdsWording wording, Locale locale) {
        String value = null;
        if (wording != null) {
            value = wording.getValueByLocale(locale);
        }
        return value;
    }

    /**
     * This method returns String for EDS wording value or null value
     * 
     * @param wording Eds wording details
     * @return String for wording value
     */
    public static String getWordingValueOrNullRepresentation(EdsWording wording) {
        if (wording == null) {
            return "--";
        }
        return wording.getWValue();
    }

    /**
     * This method replace characters by XHTML
     * 
     * @param str String to be convert
     * @return XHTML representation of String
     */
    public static String replaceFormatCharsByXHTML(String str) {
        int indexSubReplaced = -1;
        int indexSupReplaced = -1;
        int index = 0;

        StringBuilder strBuilder = new StringBuilder();

        for (char character : str.toCharArray()) {
            if (character == '_') {
                if (indexSubReplaced < 0) {
                    strBuilder.append("<sub>");
                    indexSubReplaced = index;
                } else {
                    if (indexSupReplaced > indexSubReplaced) {
                        strBuilder.append("</sup>");
                        indexSupReplaced = -1;
                    }
                    strBuilder.append("</sub>");
                    indexSubReplaced = -1;
                }
            } else if (character == '^') {
                if (indexSupReplaced < 0) {
                    strBuilder.append("<sup>");
                    indexSupReplaced = index;
                } else {
                    if (indexSubReplaced > indexSupReplaced) {
                        strBuilder.append("</sub>");
                        indexSubReplaced = -1;
                    }
                    strBuilder.append("</sup>");
                    indexSupReplaced = -1;
                }
            } else {
                strBuilder.append(character);
            }

            ++index;
        }

        if (indexSubReplaced > -1) {
            if (indexSupReplaced > -1) {
                if (indexSubReplaced > indexSupReplaced) {
                    strBuilder.append("</sub>");
                    strBuilder.append("</sup>");
                } else {
                    strBuilder.append("</sup>");
                    strBuilder.append("</sub>");
                }
            } else {
                strBuilder.append("</sub>");
            }
        } else if (indexSupReplaced > -1) {
            strBuilder.append("</sup>");
        }

        return strBuilder.toString();
    }

    // PROTECTED
    // PRIVATE

    /**
     * This method convert String to float
     * 
     * @param val String value
     * @return Float value
     */
    public static Float convertStringToFloat(String val) {
        if (val == null || val.equals("") || val.equals("NaN")) {
            return null;
        }
        try {
            return Float.parseFloat(val);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static String convertFloatToString(Float val) {
        if (val == null || val == Float.NaN) {
            return "--";
        }
        return val.toString();

    }

    public static String convertFloatToVide(Float val) {
        if (val == null || val == Float.NaN) {
            return "";
        }
        return val.toString();

    }

    /**
     * This method perform sum of float values
     * 
     * @param val1 first float Value
     * @param val2 second float value
     * @return Addition of float values
     */
    public static Float Concat(Float val1, Float val2) {
        Float result = val1;
        if (val2 != null && !val2.isNaN()) {
            if (result == null) {
                result = 0f;
            }
            result += val2;
        }
        return result;

    }
}
