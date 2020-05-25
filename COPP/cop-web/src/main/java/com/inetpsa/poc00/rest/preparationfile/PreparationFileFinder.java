package com.inetpsa.poc00.rest.preparationfile;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.preparationfile.PreparationFile;

/**
 * The Interface PreparationFileFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface PreparationFileFinder {

	/**
	 * Find preparation file by id.
	 * 
	 * @param entityId the entity id
	 * @return the preparation file
	 */
	public PreparationFile findPreparationFileById(Long entityId);
	
	/**
	 * Find prep file by vehicle id.
	 *
	 * @param vehicleFileId the vehicle file id
	 * @return the preparation file
	 */
	public PreparationFile findPrepFileByVehicleFileId(Long vehicleFileId);

}
