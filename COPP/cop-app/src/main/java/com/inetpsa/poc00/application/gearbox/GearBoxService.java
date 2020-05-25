/*
 * Creation : Jan 2, 2017
 */
package com.inetpsa.poc00.application.gearbox;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.gearbox.GearBox;

/**
 * The Interface GearBoxService.
 */
@Service
public interface GearBoxService {

	/**
	 * Save gear box.
	 *
	 * @param gearBox the gear box
	 * @return the gear box
	 */
	public GearBox saveGearBox(GearBox gearBox);

	/**
	 * Delete gear box.
	 *
	 * @param gearBoxId the gear box id
	 * @return true, if successful
	 */
	public boolean deleteGearBox(Long gearBoxId);

}
