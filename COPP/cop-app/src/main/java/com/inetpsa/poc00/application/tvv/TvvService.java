/*
 * Creation : Dec 30, 2016
 */
package com.inetpsa.poc00.application.tvv;

import java.util.Set;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.carbrand.CarBrand;
import com.inetpsa.poc00.domain.factory.CarFactory;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvvvaluedcoastdown.TvvValuedCoastDown;
import com.inetpsa.poc00.infrastructure.tvv.ServiceResponseDto;

/**
 * The Interface TvvService.
 */
@Service
public interface TvvService {

	/**
	 * Creates the version.
	 *
	 * @param tvvId the tvv id
	 * @return the tvv
	 */
	TVV createVersion(Long tvvId);

	/**
	 * Update tvv.
	 *
	 * @param tvvId the tvv id
	 * @param updatedTvv the updated tvv
	 * @param valuedCoastDown the valued coast down
	 * @return the service response dto
	 */
	ServiceResponseDto updateTvv(long tvvId, TVV updatedTvv, TvvValuedCoastDown valuedCoastDown);

	/**
	 * Copy tvv.
	 *
	 * @param label the label
	 * @param tvv the tvv
	 * @param carFactorySet the car factory set
	 * @param carBrandSet the car brand set
	 * @param valuedCoastDown the valued coast down
	 * @return the tvv
	 */
	TVV copyTvv(String label, TVV tvv, Set<CarFactory> carFactorySet, Set<CarBrand> carBrandSet, TvvValuedCoastDown valuedCoastDown);

	/**
	 * Save tvv object.
	 *
	 * @param isChangeVersion the is change version
	 * @param tvvObjectToSave the tvv object to save
	 * @param oldtvv the oldtvv
	 * @return the tvv
	 */
	TVV saveTvvObject(boolean isChangeVersion, TVV tvvObjectToSave, TVV oldtvv);

	/**
	 * Delete tvv.
	 *
	 * @param entityId the entity id
	 * @return the int
	 */
	int deleteTvv(Long entityId);

}
