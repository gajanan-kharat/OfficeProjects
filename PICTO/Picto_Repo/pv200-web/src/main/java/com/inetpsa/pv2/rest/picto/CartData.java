package com.inetpsa.pv2.rest.picto;

import java.util.List;

/**
 * The Class CartData.
 */
public class CartData {
	
	/** The login. */
	private String login;
	
	/** The picto id. */
	private List<Long> pictoId;

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
	 * @param login the new login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Gets the picto id.
	 *
	 * @return the picto id
	 */
	public List<Long> getPictoId() {
		return pictoId;
	}

	/**
	 * Sets the picto id.
	 *
	 * @param pictoId the new picto id
	 */
	public void setPictoId(List<Long> pictoId) {
		this.pictoId = pictoId;
	}

}
