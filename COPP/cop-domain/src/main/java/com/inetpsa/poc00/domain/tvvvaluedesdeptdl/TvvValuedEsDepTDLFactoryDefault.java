/*
 * Creation : May 27, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedesdeptdl;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class TvvValuedEsDepTDLFactoryDefault.
 */
public class TvvValuedEsDepTDLFactoryDefault extends BaseFactory<TvvValuedEsDepTDL> implements TvvValuedEsDepTDLFactory {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedesdeptdl.TvvValuedEsDepTDLFactory#createTvvValuedEsDepTDL()
	 */
	@Override
	public TvvValuedEsDepTDL createTvvValuedEsDepTDL() {
		return new TvvValuedEsDepTDL();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedesdeptdl.TvvValuedEsDepTDLFactory#createTvvValuedEsDepTDL(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public TvvValuedEsDepTDL createTvvValuedEsDepTDL(String label, String description, String version) {
		return new TvvValuedEsDepTDL(label, description, version);
	}

	@Override
	public TvvValuedEsDepTDL createTvvValuedEsDepTDL(TvvValuedEsDepTDL tvvValuedEsDepTDL) {
		return new TvvValuedEsDepTDL(tvvValuedEsDepTDL);
	}

}
