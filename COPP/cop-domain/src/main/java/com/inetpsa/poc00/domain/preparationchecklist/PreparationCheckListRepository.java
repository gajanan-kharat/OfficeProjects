package com.inetpsa.poc00.domain.preparationchecklist;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface PreparationCheckListRepository.
 */
public interface PreparationCheckListRepository extends GenericRepository<PreparationCheckList, Long> {

	/**
	 * Save prep check list.
	 * 
	 * @param prepCheckList the prep check list
	 * @return the preparation check list
	 */
	public PreparationCheckList savePrepCheckList(PreparationCheckList prepCheckList);
}
