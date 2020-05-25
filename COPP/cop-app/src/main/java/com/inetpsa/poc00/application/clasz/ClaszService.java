/*
 * Creation : Jan 4, 2017
 */
package com.inetpsa.poc00.application.clasz;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.clasz.Clasz;

/**
 * The Interface ClaszService.
 */
@Service
public interface ClaszService {

	/**
	 * Save clasz.
	 *
	 * @param clasz the clasz
	 * @return the clasz
	 */
	public Clasz saveClasz(Clasz clasz);

	/**
	 * Delete clasz.
	 *
	 * @param claszId the clasz id
	 * @return true, if successful
	 */
	public boolean deleteClasz(Long claszId);
}
