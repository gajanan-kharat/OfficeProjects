/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inetpsa.eds.dao;

import com.inetpsa.eds.dao.model.EdsUser;

/**
 * This is an Exception class. This Exception is thrown whenever user's access is locked in the application.
 * 
 * @author Geometric Ltd.
 */
public class E_AccessLocked extends Exception {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param user Eds user details
     */
    public E_AccessLocked(EdsUser user) {
        super("Access locked by user " + user);
        this.user = user;
        init();
    }

    /**
     * Parameterized constructor
     * 
     * @param user Eds User details
     * @param message Message
     */
    public E_AccessLocked(EdsUser user, String message) {
        super(message);
        this.user = user;
        init();
    }

    /**
     * This method returns the use whose access is locked
     * 
     * @return Eds user details
     */
    public EdsUser getUser() {
        return user;
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EdsUser
     */
    private EdsUser user;

    /**
     * Initialize E_AccessLocked
     */
    private void init() {
    }
}
