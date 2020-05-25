
package com.inetpsa.poc00.application.tirebrand;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.tirebrand.TireBrand;

/**
 * The Interface TireBrandService.
 */
@Service
public interface TireBrandService {

	/**
	 * Delete tire brand.
	 *
	 * @param entityId the entity id
	 * @return the int
	 */
	int deleteTireBrand(Long entityId);

	/**
	 * Save tire brand.
	 *
	 * @param updatedTBD the updated tbd
	 * @return the tire brand
	 */
	TireBrand saveTireBrand(TireBrand updatedTBD);

}
