/*
 * Author: U224106
 * Creation: 3 déc. 2014
 */
package com.inetpsa.oaz00.ws.report.utils;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

/**
 * The Enum ReportStyles.
 * 
 * @author U224106
 */
public enum ReportStyles {

    /** The title. */
    TITLE(255, 255, 255, 24, true, true, 148, 138, 84, CellStyle.BORDER_MEDIUM),
    /** The section. */
    SECTION(238, 236, 225, 20, true, true, 148, 138, 84, CellStyle.BORDER_MEDIUM),
    /** The row header. */
    ROW_HEADER(149, 179, 215, 14, false, true, 191, 191, 191, CellStyle.BORDER_THIN),
    /** The column header. */
    COL_HEADER(197, 217, 241, 14, true, false, 191, 191, 191, CellStyle.BORDER_THIN),
    /** The reference. */
    REFERENCE(242, 220, 219, 14, false, true, 191, 191, 191, CellStyle.BORDER_THIN),
    /** The contributor. */
    CONTRIBUTOR(197, 217, 241, 14, false, false, 191, 191, 191, CellStyle.BORDER_THIN),
    /** The soft grey. */
    SOFT_GREY(238, 236, 225, 14, false, true, 191, 191, 191, CellStyle.BORDER_THIN),
    /** The default. */
    HIGHLIGHT(242, 242, 242, 14, false, true, 191, 191, 191, CellStyle.BORDER_THIN),
    /** The bold default. */
    BOLD(255, 255, 255, 14, false, true, 191, 191, 191, CellStyle.BORDER_THIN),
    /** The default. */
    DEFAULT(255, 255, 255, 14, false, false, 191, 191, 191, CellStyle.BORDER_THIN);

    /** The red component. */
    private final int red;

    /** The green component. */
    private final int green;

    /** The blue component. */
    private final int blue;

    /** The font size. */
    private final int fontSize;

    /** The italic. */
    private final boolean italic;

    /** The bold. */
    private final boolean bold;

    /** The border red component. */
    private final int borderRed;

    /** The border green component. */
    private final int borderGreen;

    /** The border blue component. */
    private final int borderBlue;

    /** The border size. */
    private final short borderSize;

    /**
     * Instantiates a new report styles.
     * 
     * @param r the Red
     * @param g the Green
     * @param b the Blue
     * @param fSize the font size
     * @param it the italic
     * @param w the font weight
     * @param br the border Red
     * @param bg the border Green
     * @param bb the border Blue
     * @param bSize the border size
     */
    ReportStyles(int r, int g, int b, int fSize, boolean it, boolean w, int br, int bg, int bb, int bSize) {
        red = r;
        green = g;
        blue = b;
        fontSize = fSize;
        italic = it;
        bold = w;
        borderRed = br;
        borderGreen = bg;
        borderBlue = bb;
        borderSize = (short) bSize;
    }

    /**
     * Gets the color.
     * 
     * @return the color
     */
    public XSSFColor getColor() {
        return new XSSFColor(new java.awt.Color(red, green, blue));
    }

    /**
     * Gets the border color.
     * 
     * @return the border color
     */
    public XSSFColor getBorderColor() {
        return new XSSFColor(new java.awt.Color(borderRed, borderGreen, borderBlue));
    }

    /**
     * Gets the border size.
     * 
     * @return the border size
     */
    public short getBorderSize() {
        return borderSize;
    }

    /**
     * Gets the font size.
     * 
     * @return the font size
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * Checks if is italic.
     * 
     * @return true, if is italic
     */
    public boolean isItalic() {
        return italic;
    }

    /**
     * Checks if is bold.
     * 
     * @return true, if is bold
     */
    public boolean isBold() {
        return bold;
    }

}
