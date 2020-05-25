package com.inetpsa.eds.tools.filter;

/**
 * This exception is thrown when trying to create a filter if the prerequisites are not met.
 * 
 * @author Geometric Ltd.
 */
public class E_FilterNotReady extends Exception {
    // PUBLIC
    /**
     * Default constructor
     */
    public E_FilterNotReady() {
        init();
    }

    /**
     * Parameterized constructor
     * 
     * @param cause object of Throwable class
     */
    public E_FilterNotReady(Throwable cause) {
        super(cause);
        init();
    }

    /**
     * Parameterized constructor
     * 
     * @param message Error message
     * @param cause object of Throwable class
     */
    public E_FilterNotReady(String message, Throwable cause) {
        super(message, cause);
        init();
    }

    /**
     * Parameterized constructor
     * 
     * @param message Error message
     */
    public E_FilterNotReady(String message) {
        super(message);
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize E_FilterNotReady exception
     */
    private void init() {
    }
}
