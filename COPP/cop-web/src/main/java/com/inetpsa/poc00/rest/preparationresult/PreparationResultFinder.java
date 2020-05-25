package com.inetpsa.poc00.rest.preparationresult;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.preparationresult.PreparationResult;

/**
 * The Interface PreparationResultFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface PreparationResultFinder {

	/**
	 * Find preparation result by id.
	 * 
	 * @param entityId the entity id
	 * @return the preparation result
	 */
	public PreparationResult findPreparationResultById(Long entityId);

}
