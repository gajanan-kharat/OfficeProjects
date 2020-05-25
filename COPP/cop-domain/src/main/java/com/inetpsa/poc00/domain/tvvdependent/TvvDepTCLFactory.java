package com.inetpsa.poc00.domain.tvvdependent;

import org.seedstack.business.domain.GenericFactory;

/**
 * Category Factory interface.
 */

public interface TvvDepTCLFactory extends GenericFactory<TvvDepTCL> {
	/**
	 * Factory create method.
	 * 
	 * @param name
	 *            the dictionary name
	 * @return the dictionary
	 */
	TvvDepTCL createTvvDepTCL();

	TvvDepTCL createTvvDepTCL(String label, String description, String version);

}
