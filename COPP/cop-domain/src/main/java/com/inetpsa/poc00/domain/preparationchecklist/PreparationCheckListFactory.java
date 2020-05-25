package com.inetpsa.poc00.domain.preparationchecklist;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating PreparationCheckList objects.
 */
public interface PreparationCheckListFactory extends GenericFactory<PreparationCheckList> {

	/**
	 * Creates a new PreparationCheckList object.
	 * 
	 * @return the preparation check list
	 */
	PreparationCheckList createPreparationCheckList();
}
