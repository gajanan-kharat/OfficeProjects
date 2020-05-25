/*
 * Creation : Apr 20, 2016
 */
package com.inetpsa.poc00.pollutantorgas;

import java.util.Set;

/**
 * The Class ManagePollutantGasLimitListRequestDto.
 */
public class ManagePollutantGasLimitListRequestDto {

	/** The pg list. */
	Set<PollutantGasLimitListRepresentation> pgList;

	/** The change ems version. */
	private boolean changeEmsVersion;

	/**
	 * Gets the pg list.
	 * 
	 * @return the pg list
	 */
	public Set<PollutantGasLimitListRepresentation> getPgList() {
		return pgList;
	}

	/**
	 * Sets the pg list.
	 * 
	 * @param pgList the new pg list
	 */
	public void setPgList(Set<PollutantGasLimitListRepresentation> pgList) {
		this.pgList = pgList;
	}

	/**
	 * Checks if is change ems version.
	 * 
	 * @return true, if is change ems version
	 */
	public boolean isChangeEmsVersion() {
		return changeEmsVersion;
	}

	/**
	 * Sets the change ems version.
	 * 
	 * @param changeEmsVersion the new change ems version
	 */
	public void setChangeEmsVersion(boolean changeEmsVersion) {
		this.changeEmsVersion = changeEmsVersion;
	}


}
