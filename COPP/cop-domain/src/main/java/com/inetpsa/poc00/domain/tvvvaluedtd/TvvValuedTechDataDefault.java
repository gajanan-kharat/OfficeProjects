package com.inetpsa.poc00.domain.tvvvaluedtd;

import org.seedstack.business.domain.BaseFactory;

public class TvvValuedTechDataDefault extends BaseFactory<TvvValuedTechData> implements TvvValuedTechDataFactory {

	/**
	 * Factory create method.
	 * 
	 * @param name
	 * @return the TvvValuedTechData
	 */
	@Override
	public TvvValuedTechData createTvvValuedTechData() {
		return new TvvValuedTechData();
	}

	@Override
	public TvvValuedTechData createValuedGenericTechData(TvvValuedTechData tvvValuedTechData) {
		return new TvvValuedTechData(tvvValuedTechData);
	}
}
