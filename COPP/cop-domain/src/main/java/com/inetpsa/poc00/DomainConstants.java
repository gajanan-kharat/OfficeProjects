/*
 * Creation : July 5, 2016
 */
package com.inetpsa.poc00;

/**
 * The Class DomainConstants.
 */
public class DomainConstants {
	/** The Constant TVV_TABLE. */
	public static final String TVV_TABLE = "COPQTTVV";
	/** The Constant EMPTY. */
	public static final String EMPTY = "";

	/** The Constant HYPHEN. */
	public static final char HYPHEN = '-';

	/** The Constant VERSION_SEPARATOR. */
	public static final char VERSION_SEPARATOR = HYPHEN;
	/** The Constant VEHICLE_TVVSTATUS_RECEIVED. */
	public static final String VEHICLEFILESTATUS_ARCHIVED = "Archived";
	/** The Constant MAX_TVV_VERSION_QUERY. */
	public static final String MAX_TVV_VERSION_QUERY = "SELECT MAX(CAST(T.VERSION AS DECIMAL(10,1))) FROM " + TVV_TABLE + " T WHERE T.LABEL = ?";

	/**
	 * Instantiates a new constants.
	 */
	private DomainConstants() {
	}

}
