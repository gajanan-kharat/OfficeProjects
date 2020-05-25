//COPYRIGHT PSA Peugeot Citroen 2012
/**********************************************************************************************************
 *
 * FILE NAME      : PSAStructUITools.java
 *
 * SOCIETE        : PSA
 * PROJET         : Structure compare
 * VERSION        : V1.0
 * DATE           : 26/05/2017
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Interface contain enum to declare operation type.
 *                   
 **********************************************************************************************************/
package com.psa.PSATEStructCompare;

import java.awt.Cursor;
import java.util.Calendar;

public interface PSAStructUITools {

    // Cursors defined for default and wait type.
    public static final Cursor DEFAULT_CURSOR = Cursor.getDefaultCursor();
    public static final Cursor WAIT_CURSOR = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);

    // enum for MODE

    public enum MODE {
        DAILY, WEEKLY, MONTHLY
    }

    // enum for MODE_PARAM

    public enum MODE_PARAM {
        MODE_PARAM_WEEKLY, MODE_PARAM_MONTHLY
    }

    // enum for MODE_PARAM_WEEKLY

    public enum MODE_PARAM_WEEKLY {
        EVERY_MONDAY, EVERY_TUESDAY, EVERY_WEDNESDAY, EVERY_THURDAY, EVERY_FRIDAY, EVERY_SATURDAY, EVERY_SUNDAY
    }

    // Enum for user Access rights
    public enum USER_ACCESS {
        ALL, READ, WRITE, INVALID
    }

    // enum for MODE_PARAM_MONTHLY

    public enum MODE_PARAM_MONTHLY {
        ;
        void setNthDayOfWeek(Calendar cal, int DayOfWeek, int n) {
            int week = 0;
            int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            int startDay = n > 0 ? 1 : lastDay;
            int endDay = n > 0 ? lastDay : 1;
            int incrementValue = n > 0 ? 1 : -1;
            for (int Day = startDay; Day != endDay; Day += incrementValue) {
                cal.set(Calendar.DAY_OF_MONTH, Day);
                if (cal.get(Calendar.DAY_OF_WEEK) == DayOfWeek) {
                    week += incrementValue;
                    if (week == n) {
                        return;
                    }
                }
            }
        }

        private void DayOfMonth(int month, int day) {

        }
    }
}
