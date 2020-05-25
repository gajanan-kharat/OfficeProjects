/*
 * Creation : Apr 13, 2016
 */
package com.inetpsa.poc00.emsdeptcl;

import java.util.List;

/**
 * The Class ManageEmsDepTCLRequestDto.This class is used to send request data for saving Emission Stndard Dependent TCL
 */
public class ManageEmsDepTCLRequestDto {

	/** The ems dep tcl representation list. */
	private List<EmsDepTCLRepresentation> emsDepTCLRepresentationList;

	/** The change ems version. */
	private boolean changeEmsVersion;

	/**
	 * Gets the ems dep tcl representation list.
	 * 
	 * @return the ems dep tcl representation list
	 */
	public List<EmsDepTCLRepresentation> getEmsDepTCLRepresentationList() {
		return emsDepTCLRepresentationList;
	}

	/**
	 * Sets the ems dep tcl representation list.
	 * 
	 * @param emsDepTCLRepresentationList the new ems dep tcl representation list
	 */
	public void setEmsDepTCLRepresentationList(List<EmsDepTCLRepresentation> emsDepTCLRepresentationList) {
		this.emsDepTCLRepresentationList = emsDepTCLRepresentationList;
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
