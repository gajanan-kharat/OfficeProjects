/*
 * Creation : Feb 2, 2017
 */
package com.inetpsa.poc00.domain.corvertdata;

import org.seedstack.business.domain.GenericFactory;

public interface CorvetDataFactory extends GenericFactory<CorvetData> {

	/**
	 * Creates a new ArchiveBox object.
	 * 
	 * @return the archive box
	 */
	CorvetData createCorvetData();

}
