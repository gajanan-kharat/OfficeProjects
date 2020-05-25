/*
 * Author: U224106
 * Creation: 18 déc. 2014
 */
package com.inetpsa.oaz00.ws.checker.formula.services;

import java.math.BigInteger;

import org.apache.log4j.Logger;

/**
 * The Class EquationException.
 * 
 * @author U224106
 */
public class EquationException extends Exception {

    /** Error status messsage. */
    private BigInteger errorStatus;
    /** Error description message. */
    private String errorDescription;
    /** Error position */
    private BigInteger errorPosition;

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7782180642169649543L;

    /** The logger. */
    private static Logger logger = Logger.getLogger(EquationException.class);

    /**
     * Instantiates a new equation exception.
     * 
     * @param s The exception message
     */
    public EquationException(String s) {
        super(s);
        logger.error(s);
    }

    /**
     * Instantiates a new equation exception.
     * 
     * @param errorStatus The error status message.
     * @param errorDescription The error description message.
     * @param position The error position.
     */
    public EquationException(BigInteger errorStatus, String errorDescription, BigInteger position) {
        super(errorDescription);
        this.errorStatus = errorStatus;
        this.errorDescription = errorDescription;
        this.errorPosition = position;
    }

    /**
     * Instantiates a new equation exception.
     * 
     * @param errorStatus The error status message.
     * @param errorDescription The error description message.
     */
    public EquationException(BigInteger errorStatus, String errorDescription) {
        super();
        this.errorStatus = errorStatus;
        this.errorDescription = errorDescription;
    }

    /**
     * @return The error status message.
     */
    public BigInteger getErrorStatus() {
        return errorStatus;
    }

    /**
     * @return The error description message.
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * @return The error position.
     */
    public BigInteger getPosition() {
        return errorPosition;
    }

    /**
     * Set the error position.
     * 
     * @param position
     */
    public void setPosition(BigInteger position) {
        this.errorPosition = position;
    }

    /**
     * @return Syntax error message.
     */
    public String toString() {
        return "Syntax error : " + getMessage();
    }

}
