/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedfcl;

import org.seedstack.business.domain.BaseFactory;

public class TvvValuedEsDepFCLFactoryDefault extends BaseFactory<TvvValuedEsDepFCL> implements TvvValuedEsDepFCLFactory {

	@Override
	public TvvValuedEsDepFCL createTvvValuedEsDepFCL() {
		return new TvvValuedEsDepFCL();
	}

	@Override
	public TvvValuedEsDepFCL createTvvValuedEsDepFCL(String label, String description, String version) {
		return new TvvValuedEsDepFCL(label, description, version);
	}

	@Override
	public TvvValuedEsDepFCL createTvvValuedEsDepFCL(TvvValuedEsDepFCL tvvValuedEsDepFCL) {
		return new TvvValuedEsDepFCL(tvvValuedEsDepFCL);
	}
}
