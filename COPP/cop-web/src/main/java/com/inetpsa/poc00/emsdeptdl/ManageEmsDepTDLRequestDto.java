/*
 * Creation : Apr 12, 2016
 */
package com.inetpsa.poc00.emsdeptdl;

import java.util.List;

import com.inetpsa.poc00.common.BaseRepresentation;

/**
 * The Class ManageEmsDepTDLRequestDto.This class is used to send request data for saving Emission Stndard Dependent TDL
 */
public class ManageEmsDepTDLRequestDto extends BaseRepresentation {

	/** The ems dep tdl representation list. */
	private List<EmsDepTDLRepresentation> emsDepTDLRepresentationList;

	/** The change ems version. */
	private boolean changeEmsVersion;

	/**
	 * Gets the ems dep tdl representation list.
	 * 
	 * @return the ems dep tdl representation list
	 */
	public List<EmsDepTDLRepresentation> getEmsDepTDLRepresentationList() {
		return emsDepTDLRepresentationList;
	}

	/**
	 * Sets the ems dep tdl representation list.
	 * 
	 * @param emsDepTDLRepresentationList the new ems dep tdl representation list
	 */
	public void setEmsDepTDLRepresentationList(List<EmsDepTDLRepresentation> emsDepTDLRepresentationList) {
		this.emsDepTDLRepresentationList = emsDepTDLRepresentationList;
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
