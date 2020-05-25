/*
 * Creation : May 24, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedtcl;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class TvvValuedTvvDepTCLFactoryDefault.
 */
public class TvvValuedTvvDepTCLFactoryDefault extends BaseFactory<TvvValuedTvvDepTCL> implements TvvValuedTvvDepTCLFactory {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTvvDepTCLFactory#createTvvValuedTvvDepTCL()
	 */
	@Override
	public TvvValuedTvvDepTCL createTvvValuedTvvDepTCL() {
		return new TvvValuedTvvDepTCL();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTvvDepTCLFactory#createTvvValuedTvvDepTCL(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public TvvValuedTvvDepTCL createTvvValuedTvvDepTCL(String label, String description, String version) {

		return new TvvValuedTvvDepTCL(label, description, version);
	}

	@Override
	public TvvValuedTvvDepTCL createTvvValuedTvvDepTCL(TvvValuedTvvDepTCL tvvValuedTvvDepTCL) {
		return new TvvValuedTvvDepTCL(tvvValuedTvvDepTCL);
	}
}
