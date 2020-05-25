/*
 * Creation : Feb 2, 2017
 */
package com.inetpsa.poc00.domain.corvertdata;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class CorvetDataFactoryDefault.
 */
public class CorvetDataFactoryDefault extends BaseFactory<CorvetData> implements CorvetDataFactory {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.corvertdata.CorvetDataFactory#createCorvetData()
	 */
	@Override
	public CorvetData createCorvetData() {
		return new CorvetData();
	}

}
