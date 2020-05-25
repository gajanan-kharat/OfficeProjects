package com.inetpsa.eds.ws.eds;

/**
 * The Class EdsWsException.
 */
public class EdsWsException extends Exception {

    /** The code. */
    private int code;

    /**
     * Instantiates a new eds ws exception.
     * 
     * @param code the code
     * @param message the message
     */
    public EdsWsException(int code, String message) {
        super(message);

        this.code = code;
    }

    /**
     * Gets the code.
     * 
     * @return the code
     */
    public int getCode() {
        return code;
    }

}
