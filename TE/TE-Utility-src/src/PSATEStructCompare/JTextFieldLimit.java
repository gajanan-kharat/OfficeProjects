/*
 * Creation : Jun 6, 2017
 */
package com.psa.PSATEStructCompare;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldLimit extends PlainDocument {
    private static final long serialVersionUID = 1L;
    private int limit;

    JTextFieldLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet set) throws BadLocationException {
        if (str == null) {
            return;
        } else if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, set);
        }
    }
}
