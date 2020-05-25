package com.psa.tools;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Class used to set a limit to the input text on a JTextField
 * 
 */
public class PSAJTextFieldLimit extends PlainDocument {

	private int txtFieldLimit;

	/**
	 * Constructor of the class.
	 */
	public PSAJTextFieldLimit(int txtFieldLimit) {
		super();
		this.txtFieldLimit = txtFieldLimit;
	}

	/**
	 * Super class method overriding
	 */
	public void insertString(int offset, String txtValue, AttributeSet attribute)
			throws BadLocationException {
		if (txtValue == null)
			return;

		if ((getLength() + txtValue.length()) <= txtFieldLimit) {
			super.insertString(offset, txtValue, attribute);
		}
	}

}
