/*
 * Creation : May 31, 2017
 */
package com.psa.PSATEStructCompare;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class PSAUtilityFunctions {
    private PSAUtilityFunctions() {
    }

    public static Point getCenterPosition(Component component) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        // Determine the new location of the window
        int width = component.getSize().width;
        int height = component.getSize().height;
        int x = (dim.width) / 2 - (width) / 2;
        int y = (dim.height) / 2 - (height) / 2;
        return new Point(x, y);
    }

}
