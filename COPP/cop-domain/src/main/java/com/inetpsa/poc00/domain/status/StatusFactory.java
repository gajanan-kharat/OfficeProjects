package com.inetpsa.poc00.domain.status;

import org.seedstack.business.domain.GenericFactory;

/**
 * Category Factory interface.
 */

public interface StatusFactory extends GenericFactory<Status> {
	/**
	 * Factory create method.
	 * 
	 * @param name
	 * @return the Status
	 */
	Status createStatus();

	Status createStatus(String draft);

}
