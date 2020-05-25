package com.inetpsa.poc00.domain.receptionfile;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating ReceptionFile objects.
 */
public interface ReceptionFileFactory extends GenericFactory<ReceptionFile> {

	/**
	 * Creates a new ReceptionFile object.
	 *
	 * @return the reception file
	 */
	public ReceptionFile createReceptionFile();
}
