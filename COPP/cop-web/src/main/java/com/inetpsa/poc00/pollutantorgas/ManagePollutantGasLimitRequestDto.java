/*
 * Creation : May 3, 2016
 */
package com.inetpsa.poc00.pollutantorgas;

import java.util.List;


/**
 * The Class ManagePollutantGasLimitRequestDto.
 */
public class ManagePollutantGasLimitRequestDto {
	
	/** The pollutant limit list. */
	List<PollutantGasLimitRepresentation> pollutantLimitList;

	/**
	 * Gets the pollutant limit list.
	 *
	 * @return the pollutant limit list
	 */
	public List<PollutantGasLimitRepresentation> getPollutantLimitList() {
		return pollutantLimitList;
	}

	/**
	 * Sets the pollutant limit list.
	 *
	 * @param pollutantLimitList the new pollutant limit list
	 */
	public void setPollutantLimitList(List<PollutantGasLimitRepresentation> pollutantLimitList) {
		this.pollutantLimitList = pollutantLimitList;
	}
}
