package com.inetpsa.pv2.rest.pictofamily;

/**
 * The Enum EnumarationInfo.
 */
public enum EnumarationInfo {
	NOTHING(0), INFO(1), WARN(2);

	private final int info;

	EnumarationInfo(int info) {
		this.info = info;
	}

	public int getInformationCode() {
		return this.info;
	}

}
