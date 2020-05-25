/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedpgl;

import org.seedstack.business.domain.BaseFactory;

public class TvvValuedEsDepPGLFactoryDefault extends BaseFactory<TvvValuedEsDepPGL> implements TvvValuedEsDepPGLFactory {

	/**
	 * Factory create method.
	 * 
	 * @param name
	 * @return the PollutantGasLimitList
	 */
	@Override
	public TvvValuedEsDepPGL createTvvValuedEsDepPGL() {
		return new TvvValuedEsDepPGL();
	}

	@Override
	public TvvValuedEsDepPGL createTvvValuedEsDepPGL(String label, String description, String version) {
		return new TvvValuedEsDepPGL(label, description, version);
	}

	@Override
	public TvvValuedEsDepPGL createTvvValuedEsDepPGL(TvvValuedEsDepPGL tvvValuedEsDepPGL) {
		return new TvvValuedEsDepPGL(tvvValuedEsDepPGL);
	}
}
