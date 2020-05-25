/*
 * Creation : May 24, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedtdl;

import org.seedstack.business.domain.BaseFactory;

public class TvvValuedTvvDepTDLFactoryDefault extends BaseFactory<TvvValuedTvvDepTDL> implements TvvValuedTvvDepTDLFactory {

	@Override
	public TvvValuedTvvDepTDL createTvvValuedTDL() {
		return new TvvValuedTvvDepTDL();
	}

	@Override
	public TvvValuedTvvDepTDL createTvvValuedTDL(String label, String description, String version) {

		return new TvvValuedTvvDepTDL(label, description, version);
	}

	@Override
	public TvvValuedTvvDepTDL createTvvValuedTvvDepTDL(TvvValuedTvvDepTDL tvvValuedTvvDepTDL) {
		return new TvvValuedTvvDepTDL(tvvValuedTvvDepTDL);
	}

}