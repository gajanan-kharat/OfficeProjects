/*
 * Creation : May 24, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedtcl;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating TvvValuedTvvDepTCL objects.
 */
public interface TvvValuedTvvDepTCLFactory extends GenericFactory<TvvValuedTvvDepTCL> {

	/**
	 * Creates a new TvvValuedTvvDepTCL object.
	 * 
	 * @return the tvv valued tvv dep tcl
	 */
	TvvValuedTvvDepTCL createTvvValuedTvvDepTCL();

	/**
	 * Creates a new TvvValuedTvvDepTCL object.
	 * 
	 * @param label the label
	 * @param description the description
	 * @param version the version
	 * @return the tvv valued tvv dep tcl
	 */
	TvvValuedTvvDepTCL createTvvValuedTvvDepTCL(String label, String description, String version);

	TvvValuedTvvDepTCL createTvvValuedTvvDepTCL(TvvValuedTvvDepTCL oldTCL);

}
