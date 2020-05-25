/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedpgl;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating TvvValuedEsDepPGL objects.
 */
public interface TvvValuedEsDepPGLFactory extends GenericFactory<TvvValuedEsDepPGL> {

	/**
	 * Factory create method.
	 * 
	 * @return the PollutantGasLimitList
	 */
	TvvValuedEsDepPGL createTvvValuedEsDepPGL();

	/**
	 * Creates a new TvvValuedEsDepPGL object.
	 * 
	 * @param label the label
	 * @param description the description
	 * @param version the version
	 * @return the tvv valued es dep pgl
	 */
	TvvValuedEsDepPGL createTvvValuedEsDepPGL(String label, String description, String version);

	/**
	 * Creates a new TvvValuedEsDepPGL object.
	 * 
	 * @param oldPGL the old pgl
	 * @return the tvv valued es dep pgl
	 */
	TvvValuedEsDepPGL createTvvValuedEsDepPGL(TvvValuedEsDepPGL oldPGL);

}
