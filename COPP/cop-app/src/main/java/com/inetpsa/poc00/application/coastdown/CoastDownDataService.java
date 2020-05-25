/*
 * Creation : Jan 3, 2017
 */
package com.inetpsa.poc00.application.coastdown;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.coastdown.CoastDown;

/**
 * The Interface CoastDownDataService.
 */
@Service
public interface CoastDownDataService {

	/**
	 * Save coast down.
	 *
	 * @param coastDown the coast down
	 * @param inertiaValue the inertia value
	 * @param coastDownId the coast down id
	 * @return the coast down
	 */
	public CoastDown saveCoastDown(CoastDown coastDown, int inertiaValue, Long coastDownId);

}
