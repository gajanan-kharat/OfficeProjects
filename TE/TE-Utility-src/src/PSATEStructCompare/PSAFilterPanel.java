/*
 * Creation : Jun 1, 2017
 */
package com.psa.PSATEStructCompare;

import java.awt.Cursor;

public interface PSAFilterPanel {
    public static final Cursor DEFAULT_CURSOR = Cursor.getDefaultCursor(); // Default cursor
    public static final Cursor WAIT_CURSOR = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR); // Wait cursor
    // Enum for user type

    public enum USER_TYPE {
        INVALID_USER, ADMIN_USER, PILOT_USER, ORDINARY_USER
    }

    public enum ACTION {
        CREATE_FILTER, MODIFY_FILTER, VIEW_FILTER, DELETE_FILTER
    }

    enum MODE {
        ALL, DAILY, WEEKLY, MONTHLY
    }
    
    enum FILTER_TYPE {
        ALL, ISO, CIBLE, ISO_CIBLE
    }
}
