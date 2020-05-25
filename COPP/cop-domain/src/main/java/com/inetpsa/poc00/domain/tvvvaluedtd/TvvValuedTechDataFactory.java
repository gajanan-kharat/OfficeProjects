package com.inetpsa.poc00.domain.tvvvaluedtd;

import org.seedstack.business.domain.GenericFactory;

/**
 * Category Factory interface.
 */

public interface TvvValuedTechDataFactory extends GenericFactory<TvvValuedTechData> {
	/**
	 * Factory create method.
	 * 
	 * @param name
	 * @return the TvvValuedTechData
	 */
	TvvValuedTechData createTvvValuedTechData();

	TvvValuedTechData createValuedGenericTechData(TvvValuedTechData genericData);

}
