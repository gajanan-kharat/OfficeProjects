/*
 * @(#)Constants.java 1.6 Apr 24, 2015
 * CopyRight : The PSA Company
 * 
 * Creation : Apr 24, 2015
 */
package com.inetpsa.oaz00.ws.scilab.utils;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

/**
 * Utility class for truncating the Number.
 * 
 * @author amitbo
 */
public class NumberUtil {

    /** The logger. */
    private static Logger logger = Logger.getLogger(NumberUtil.class);

    /**
     * Truncate a Double Value to 3 Decimals
     * 
     * @param doubleValue input Value
     * @return the Truncated Value
     */
    public static double getTruncatedNumber(double doubleValue) {
        // a is the double value to be rounded
        double doubleValueTmp = doubleValue;
        try {
            BigDecimal bd = new BigDecimal(doubleValue);
            if (doubleValue > 0) {
                bd = bd.setScale(3, BigDecimal.ROUND_HALF_UP);
            } else {
                bd = bd.setScale(3, BigDecimal.ROUND_HALF_DOWN);
            }
            doubleValue = bd.doubleValue();
        } catch (NumberFormatException e) {
            // NumberFormatException Raised when double is Infinite or NaN : We Keep This Value.
            logger.debug("Trying to Round an Infinite or NaN Value, Initial Value is Kept", e);
            doubleValue = doubleValueTmp;
        }

        return doubleValue;
    }
}
