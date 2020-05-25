//COPYRIGHT PSA Peugeot Citroen 2012
/**********************************************************************************************************
 *
 * FILE NAME	  : FixedLengthDocument.java
 *
 * SOCIETE        : PSA
 * PROJET         : 171BO : Filtrage Maquette Num�rique configur�e
 * VERSION        : V1.0
 * DATE           : 12/01/2012
 *
 * AUTEUR         : Pankaj Pardhi (GEOMETRIC GLOBAL)
 *
 * DESCRIPTION    : Class used to fixed length for textfield.
 *					
**********************************************************************************************************/
package com.psa.PSATEStructCompare;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Class used to fixed length for text field.
 */
@SuppressWarnings("serial")
public class FixedLengthDocument extends PlainDocument {

    public static final int ALPHA_NUMERIC = 0;
    public static final int NUMERIC = 1;
    public static final int NUMERIC_AND_ASTRIC = 2;
    private int maxlength;
    private int allowedType;
    private String strNumeric = "0123456789";
    private String strNumericAstric = "*-" + strNumeric;

    /**
     * Constructor of the class
     * 
     * @param iMaxlength : int
     */
    public FixedLengthDocument(int iMaxlength) {
        maxlength = iMaxlength;
        allowedType = ALPHA_NUMERIC;
    }

    /**
     * Constructor of the class
     * 
     * @param iMaxlength : int
     * @param iType : int
     */
    public FixedLengthDocument(int iMaxlength, int iType) {
        maxlength = iMaxlength;
        allowedType = iType;
    }

    /**
     * This is the method used to insert a string to a Plain Document.
     * 
     * @param iOffset : int
     * @param iStrInput : String
     * @param iAttributeSet : AttributeSet
     */
    @Override
    public void insertString(int iOffset, String iStrInput, AttributeSet iAttributeSet) throws BadLocationException {

        boolean allowedFlag = false;
        if (iStrInput.length() == 0)
            iStrInput = "*";
        if (allowedType == NUMERIC_AND_ASTRIC) {
            if (strNumericAstric.contains(iStrInput)) {
                allowedFlag = true;
            }
        } else if (allowedType == NUMERIC) {
            if (strNumeric.contains(iStrInput)) {
                allowedFlag = true;
            }
        } else if (allowedType == ALPHA_NUMERIC) {
            allowedFlag = true;
        }

        if (allowedFlag && !((getLength() + iStrInput.length()) > maxlength)) {
            super.insertString(iOffset, iStrInput, iAttributeSet);
        }
    }

}
