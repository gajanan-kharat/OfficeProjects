/*
 * Creation : Apr 15, 2016
 */
package com.inetpsa.pv2.constants;

/**
 * Enum class
 * 
 * @author mehaj
 */
public enum PictoClientConstants {
    TRUE(true), FALSE(false);

    private boolean returnVal;

    PictoClientConstants(boolean returnVal) {
        this.returnVal = returnVal;
    }

    /**
     * To return value
     * 
     * @return
     */
    public boolean returnVal() {
        return returnVal;
    }
}
