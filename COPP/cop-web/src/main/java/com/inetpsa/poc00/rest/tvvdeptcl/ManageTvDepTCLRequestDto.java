/*
 * Creation : Apr 1, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptcl;

import java.util.List;

/**
 * The Class ManageTvDepTCLRequestDto.
 */
public class ManageTvDepTCLRequestDto {
	
	/** The tvv dep TCL representation list. */
	private List<TvvDepTCLRepresentation> tvvDepTCLRepresentationList;

	/**
	 * Gets the tvv dep TCL representation list.
	 *
	 * @return the tvv dep TCL representation list
	 */
	public List<TvvDepTCLRepresentation> getTvvDepTCLRepresentationList() {
		return tvvDepTCLRepresentationList;
	}

	/**
	 * Sets the tvv dep TCL representation list.
	 *
	 * @param tvvDepTCLRepresentationList the new tvv dep TCL representation list
	 */
	public void setTvvDepTCLRepresentationList(
			List<TvvDepTCLRepresentation> tvvDepTCLRepresentationList) {
		this.tvvDepTCLRepresentationList = tvvDepTCLRepresentationList;
	}
}
