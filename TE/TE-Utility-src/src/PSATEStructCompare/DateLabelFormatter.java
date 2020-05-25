/*
 * Creation : May 30, 2017
 */
package com.psa.PSATEStructCompare;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.text.DateFormatter;

public class DateLabelFormatter extends DateFormatter {
    private static final long serialVersionUID = 1L;
    private String datePattern = "yyyy/MM/dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
        return "";
    }

}
