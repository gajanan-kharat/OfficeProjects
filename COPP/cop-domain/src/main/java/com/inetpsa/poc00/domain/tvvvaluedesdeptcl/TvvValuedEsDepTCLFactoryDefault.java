/*
 * Creation : May 27, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedesdeptcl;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class TvvValuedEsDepTCLFactoryDefault.
 */
public class TvvValuedEsDepTCLFactoryDefault extends BaseFactory<TvvValuedEsDepTCL> implements TvvValuedEsDepTCLFactory {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCLFactory#createTvvValuedEsDepTCL()
	 */
	@Override
	public TvvValuedEsDepTCL createTvvValuedEsDepTCL() {
		return new TvvValuedEsDepTCL();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCLFactory#createTvvValuedEsDepTCL(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public TvvValuedEsDepTCL createTvvValuedEsDepTCL(String label, String description, String version) {

		return new TvvValuedEsDepTCL(label, description, version);
	}

	@Override
	public TvvValuedEsDepTCL createTvvValuedEsDepTCL(TvvValuedEsDepTCL tvvValuedEsDepTCL) {
		return new TvvValuedEsDepTCL(tvvValuedEsDepTCL);
	}
}