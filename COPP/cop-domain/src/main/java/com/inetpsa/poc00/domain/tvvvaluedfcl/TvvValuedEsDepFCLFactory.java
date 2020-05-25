/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedfcl;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating TvvValuedEsDepFCL objects.
 */
public interface TvvValuedEsDepFCLFactory extends GenericFactory<TvvValuedEsDepFCL> {

	/**
	 * Creates a new TvvValuedEsDepFCL object.
	 * 
	 * @return the tvv valued es dep fcl
	 */
	TvvValuedEsDepFCL createTvvValuedEsDepFCL();

	/**
	 * Creates a new TvvValuedEsDepFCL object.
	 * 
	 * @param label the label
	 * @param description the description
	 * @param version the version
	 * @return the tvv valued es dep fcl
	 */
	TvvValuedEsDepFCL createTvvValuedEsDepFCL(String label, String description, String version);

	/**
	 * Creates a new TvvValuedEsDepFCL object.
	 * 
	 * @param oldFCL the old fcl
	 * @return the tvv valued es dep fcl
	 */
	TvvValuedEsDepFCL createTvvValuedEsDepFCL(TvvValuedEsDepFCL oldFCL);

}
