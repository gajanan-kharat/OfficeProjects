/*
 * Author: U224106
 * Creation: 3 déc. 2014
 */
package com.inetpsa.oaz00.ws.report.utils;

/**
 * The Enum ReportRowHeights.
 * 
 * @author U224106
 */
public enum ReportRowHeights {

    /** The PSA logo row: <b>30</b>. */
    LOGO(30),
    /** The title row: <b>50</b>. */
    TITLE(50),
    /** The section row: <b>30</b>. */
    SECTION(30),
    /** The table header row: <b>45</b>. */
    TABLE_HEADER(45),
    /** The table value row: <b>40</b>. */
    TABLE_VALUE(40),
    /** The formula row: <b>100</b>. */
    FORMULA(100),
    /** The standard row: <b>20</b>. */
    STANDARD(20),
    /** The default row (empty one): <b>15</b>. */
    DEFAULT(15);

    /** The height. */
    private final int height;

    /**
     * Instantiates a new report row heights.
     * 
     * @param h the h
     */
    ReportRowHeights(int h) {
        height = h;
    }

    /**
     * Gets the height.
     * 
     * @return the height
     */
    public int getHeight() {
        return height;
    }
}
