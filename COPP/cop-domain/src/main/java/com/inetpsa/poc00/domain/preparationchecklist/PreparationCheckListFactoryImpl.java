package com.inetpsa.poc00.domain.preparationchecklist;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class PreparationCheckListFactoryImpl.
 */
public class PreparationCheckListFactoryImpl extends BaseFactory<PreparationCheckList> implements PreparationCheckListFactory {

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.preparationchecklist.PreparationCheckListFactory#createPreparationCheckList()
	 */
	@Override
	public PreparationCheckList createPreparationCheckList() {
		return new PreparationCheckList();

	}

}
