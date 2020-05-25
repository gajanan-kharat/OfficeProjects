/*
 * Creation : May 27, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedesdeptcl;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating TvvValuedEsDepTCL objects.
 */
public interface TvvValuedEsDepTCLFactory extends GenericFactory<TvvValuedEsDepTCL> {

	/**
	 * Creates a new TvvValuedEsDepTCL object.
	 * 
	 * @return the tvv valued es dep tcl
	 */
	TvvValuedEsDepTCL createTvvValuedEsDepTCL();

	/**
	 * Creates a new TvvValuedEsDepTCL object.
	 * 
	 * @param label the label
	 * @param description the description
	 * @param version the version
	 * @return the tvv valued es dep tcl
	 */
	TvvValuedEsDepTCL createTvvValuedEsDepTCL(String label, String description, String version);

	TvvValuedEsDepTCL createTvvValuedEsDepTCL(TvvValuedEsDepTCL oldTCL);
}
