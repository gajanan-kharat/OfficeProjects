package com.inetpsa.poc00.domain.receptionfile;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class ReceptionFileFactoryImpl.
 */
public class ReceptionFileFactoryImpl extends BaseFactory<ReceptionFile> implements ReceptionFileFactory {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.receptionfile.ReceptionFileFactory#createReceptionFile()
	 */
	@Override
	public ReceptionFile createReceptionFile() {

		return new ReceptionFile();
	}

}
