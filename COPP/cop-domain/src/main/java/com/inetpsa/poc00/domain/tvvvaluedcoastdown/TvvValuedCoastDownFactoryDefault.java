/*
 * Creation : Jun 17, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedcoastdown;

import org.seedstack.business.domain.BaseFactory;

public class TvvValuedCoastDownFactoryDefault extends BaseFactory<TvvValuedCoastDown> implements TvvValuedCoastDownFactory {

	@Override
	public TvvValuedCoastDown createValuedCoastDown() {

		return new TvvValuedCoastDown();
	}

	@Override
	public TvvValuedCoastDown createValuedCoastDown(TvvValuedCoastDown tvvValuedCoastDown) {
		return new TvvValuedCoastDown(tvvValuedCoastDown);
	}

}
