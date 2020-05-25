package com.inetpsa.poc00.domain.tvv;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating TVV objects.
 */
public interface TVVFactory extends GenericFactory<TVV> {

	/**
	 * Creates a new TVV object.
	 * 
	 * @return the tvv
	 */
	TVV createTVV();

}
