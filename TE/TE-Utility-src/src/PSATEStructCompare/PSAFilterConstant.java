/*
 * Creation : Jun 12, 2017
 */
package com.psa.PSATEStructCompare;

import java.util.logging.Logger;

public class PSAFilterConstant {
    private static final Logger logger = Logger.getLogger("PSAFilterConstant");
    public static final boolean TRUE_VAL = true;
    public static final boolean FALSE_VAL = false;
    public static final String EMPTY_STRING = "";
    public static final String TAHOMA = "Tahoma";
    public static final String DATE_SEARCH_PATTERN = "*-*-*";
    public static final String STAR_SYMBOL = "*";
    public static final String VPM_USER_LANGUAGE = "VPM_USER_LANGUAGE";

    private PSAFilterConstant() {
        logger.info("START Constructor");
        logger.info("END Constructor");
    }

}
