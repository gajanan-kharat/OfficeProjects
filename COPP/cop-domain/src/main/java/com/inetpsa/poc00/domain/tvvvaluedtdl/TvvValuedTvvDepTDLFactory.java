/*
 * Creation : May 24, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedtdl;

import org.seedstack.business.domain.GenericFactory;

/**
 * Category Factory interface.
 */

public interface TvvValuedTvvDepTDLFactory extends GenericFactory<TvvValuedTvvDepTDL> {
	/**
	 * Factory create method.
	 * 
	 * @param name the dictionary name
	 * @return the dictionary
	 */
	TvvValuedTvvDepTDL createTvvValuedTDL();

	TvvValuedTvvDepTDL createTvvValuedTDL(String label, String description, String version);

	TvvValuedTvvDepTDL createTvvValuedTvvDepTDL(TvvValuedTvvDepTDL oldTDL);

}
