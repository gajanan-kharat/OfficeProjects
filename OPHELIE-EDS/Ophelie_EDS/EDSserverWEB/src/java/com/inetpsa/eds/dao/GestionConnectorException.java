/*
 * Creation : 31 juil. 2015
 */
package com.inetpsa.eds.dao;

public class GestionConnectorException extends Exception {

    private String message;

    public GestionConnectorException(String type, String messsage, Exception source) {
        super(type, source);
        this.message = messsage;
    }

    public String getMessage() {
        return message;
    }
}