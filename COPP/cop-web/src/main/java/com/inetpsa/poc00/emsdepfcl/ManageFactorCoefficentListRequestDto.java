/*
 * Creation : Apr 15, 2016
 */
package com.inetpsa.poc00.emsdepfcl;

import java.util.List;


/**
 * The Class ManageFactorCoefficentListRequestDto.
 */
public class ManageFactorCoefficentListRequestDto {

	/** The fc list representation. */
	private List<FactorCoefficentListRepresentation> fcListRepresentation;
	
	/** The change ems version. */
	private boolean changeEmsVersion;

	/**
	 * Gets the fc list representation.
	 *
	 * @return the fc list representation
	 */
	public List<FactorCoefficentListRepresentation> getFcListRepresentation() {
		return fcListRepresentation;
	}

	/**
	 * Sets the fc list representation.
	 *
	 * @param fcListRepresentation the new fc list representation
	 */
	public void setFcListRepresentation(List<FactorCoefficentListRepresentation> fcListRepresentation) {
		this.fcListRepresentation = fcListRepresentation;
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
