/*
 * Creation : Sep 12, 2016
 */
package com.inetpsa.poc00.domain.tirebrand;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class TireBrandFactoryDefault.
 */
// public interface TireBrandFactory extends GenericFactory<TireBrand>
public class TireBrandFactory extends BaseFactory<TireBrand> {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tirebrand.TireBrandFactory#createTireBrand()
	 */

	public static TireBrand createTireBrand() {
		return new TireBrand();
	}

}
