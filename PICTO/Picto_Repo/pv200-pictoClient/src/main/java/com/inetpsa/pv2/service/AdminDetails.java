/*
 * Creation : Apr 12, 2016
 */
package com.inetpsa.pv2.service;

public class AdminDetails {
    private static AdminDetails adminDetails = new AdminDetails();
    private long usrId;
    private String authStringEnc;
    private String userName;
    private String language;

    /**
     * Getter userName
     * 
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter userName
     * 
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String password;

    /**
     * Getter password
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter password
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter authStringEnc
     * 
     * @return the authStringEnc
     */
    public String getAuthStringEnc() {
        return authStringEnc;
    }

    /**
     * Setter authStringEnc
     * 
     * @param authStringEnc the authStringEnc to set
     */
    public void setAuthStringEnc(String authStringEnc) {
        this.authStringEnc = authStringEnc;
    }

    /**
     * A private Constructor prevents any other class from instantiating.
     */
    private AdminDetails() {
    }

    /**
     * Static 'instance' method
     * 
     * @return : singleton instance
     */
    public static AdminDetails getInstance() {
        return adminDetails;
    }

    public long getLoggedInAdmin() {
        return usrId;
    }

    public long setLoggedInAdmin(String adminId) {
        // adminId = 2L;
        usrId = Long.parseLong(adminId);
        return usrId;
    }

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

}
