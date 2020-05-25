package com.inetpsa.poc00.rest.preparationchecklist;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.preparationchecklist.PreparationCheckList;

/**
 * The Interface PreparationCheckListFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface PreparationCheckListFinder {

	/**
	 * Find prep check list by id.
	 * 
	 * @param entityId the entity id
	 * @return the preparation check list
	 */
	public PreparationCheckList findPrepCheckListById(Long entityId);

}
