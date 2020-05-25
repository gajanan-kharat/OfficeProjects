/*
 * Creation : May 27, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedesdeptdl;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating TvvValuedEsDepTDL objects.
 */
public interface TvvValuedEsDepTDLFactory extends GenericFactory<TvvValuedEsDepTDL> {

	/**
	 * Creates a new TvvValuedEsDepTDL object.
	 * 
	 * @return the tvv valued es dep tdl
	 */
	TvvValuedEsDepTDL createTvvValuedEsDepTDL();

	/**
	 * Creates a new TvvValuedEsDepTDL object.
	 * 
	 * @param label the label
	 * @param description the description
	 * @param version the version
	 * @return the tvv valued es dep tdl
	 */
	TvvValuedEsDepTDL createTvvValuedEsDepTDL(String label, String description, String version);

	/**
	 * Creates a new TvvValuedEsDepTDL object.
	 * 
	 * @param oldTDL the old tdl
	 * @return the tvv valued es dep tdl
	 */
	TvvValuedEsDepTDL createTvvValuedEsDepTDL(TvvValuedEsDepTDL oldTDL);

}
