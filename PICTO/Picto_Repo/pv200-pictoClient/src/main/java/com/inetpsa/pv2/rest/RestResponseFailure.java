/*
 * Creation : May 11, 2016
 */
package com.inetpsa.pv2.rest;

public class RestResponseFailure extends Exception {

    private String message = null;

    public RestResponseFailure() {
        super();
    }

    public RestResponseFailure(String message) {
        super(message);
        this.message = message;
    }

    public RestResponseFailure(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
