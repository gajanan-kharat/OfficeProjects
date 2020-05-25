package com.inetpsa.poc00.infrastructure.jpa;

import com.inetpsa.poc00.domain.preparationchecklist.PreparationCheckList;
import com.inetpsa.poc00.rest.preparationchecklist.PreparationCheckListFinder;

/**
 * The Class JpaPreparationCheckListFinder.
 */
public class JpaPreparationCheckListFinder implements PreparationCheckListFinder {

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.preparationchecklist.PreparationCheckListFinder#findPrepCheckListById(java.lang.Long)
	 */
	@Override
	public PreparationCheckList findPrepCheckListById(Long entityId) {
		return null;
	}

}
