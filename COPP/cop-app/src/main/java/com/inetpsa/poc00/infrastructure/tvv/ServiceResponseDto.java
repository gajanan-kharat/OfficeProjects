/*
 * Creation : Jan 2, 2017
 */
package com.inetpsa.poc00.infrastructure.tvv;

import com.inetpsa.poc00.domain.tvv.TVV;

/**
 * The Class ServiceResponseDto.
 */
public class ServiceResponseDto {

	/** The tvv. */
	private TVV tvv;

	/** The change version. */
	private boolean changeVersion;

	/**
	 * Gets the tvv.
	 *
	 * @return the tvv
	 */
	public TVV getTvv() {
		return tvv;
	}

	/**
	 * Sets the tvv.
	 *
	 * @param tvv the new tvv
	 */
	public void setTvv(TVV tvv) {
		this.tvv = tvv;
	}

	/**
	 * Checks if is change version.
	 *
	 * @return true, if is change version
	 */
	public boolean isChangeVersion() {
		return changeVersion;
	}

	/**
	 * Sets the change version.
	 *
	 * @param changeVersion the new change version
	 */
	public void setChangeVersion(boolean changeVersion) {
		this.changeVersion = changeVersion;
	}
}
