package com.inetpsa.pv2.rest.pictofamily;

import java.util.List;


/**
 * The Class FavoriteData.
 */
public class FavoriteData {

	/** The login. */
	private String login;
	
	/** The picto family. */
	private List<String> pictoFamily;

	/**
	 * Gets the login.
	 *
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the login.
	 *
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Gets the picto family.
	 *
	 * @return the pictoFamily
	 */
	public List<String> getPictoFamily() {
		return pictoFamily;
	}

	/**
	 * Sets the picto family.
	 *
	 * @param pictoFamily the pictoFamily to set
	 */
	public void setPictoFamily(List<String> pictoFamily) {
		this.pictoFamily = pictoFamily;
	}

}
