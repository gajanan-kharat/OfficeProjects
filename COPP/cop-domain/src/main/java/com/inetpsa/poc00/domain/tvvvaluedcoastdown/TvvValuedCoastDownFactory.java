/*
 * Creation : Jun 17, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedcoastdown;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating TvvValuedCoastDown objects.
 */
public interface TvvValuedCoastDownFactory extends GenericFactory<TvvValuedCoastDown> {

	/**
	 * Creates a new TvvValuedCoastDown object.
	 * 
	 * @return the tvv valued coast down
	 */
	TvvValuedCoastDown createValuedCoastDown();

	/**
	 * Creates a new TvvValuedCoastDown object.
	 * 
	 * @param tvvValuedCoastDown the tvv valued coast down
	 * @return the tvv valued coast down
	 */
	TvvValuedCoastDown createValuedCoastDown(TvvValuedCoastDown tvvValuedCoastDown);

}
