package com.inetpsa.eds.ws.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents user information
 * 
 * @author Geometric Ltd.
 */
@XmlRootElement
public class UserInfo {
    // PUBLIC
    /**
     * Default Constructor
     */
    public UserInfo() {
    }

    /**
     * Parameterized Constructor
     * 
     * @param login User name
     * @param password Password
     */
    public UserInfo(String login, String password) {
        this.login = login;
        this.password = password;
        init();
    }

    /**
     * Function to get login
     * 
     * @return the Value of login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Function to set login
     * 
     * @param login User Name
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Function to get password
     * 
     * @return the Value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Function to set password
     * 
     * @param password Password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize User inforamation
     */
    private void init() {
    }

    /**
     * String variable to hold value of login
     */
    private String login;
    /**
     * String variable to hold value of password
     */
    private String password;
}
