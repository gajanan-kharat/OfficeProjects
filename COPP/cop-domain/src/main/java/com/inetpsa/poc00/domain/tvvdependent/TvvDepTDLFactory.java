package com.inetpsa.poc00.domain.tvvdependent;

import org.seedstack.business.domain.GenericFactory;

/**
 * Category Factory interface.
 */

public interface TvvDepTDLFactory extends GenericFactory<TvvDepTDL> {
	/**
	 * Factory create method.
	 * 
	 * @param name the dictionary name
	 * @return the dictionary
	 */
	TvvDepTDL createTvvDepTDL();

	TvvDepTDL createTvvDepTDL(String label, String description, String version);

	TvvDepTDL createTvvDepTDL(TvvDepTDL oldTDL);

}
