package com.inetpsa.poc00.infrastructure.jpa;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.seed.DataSet;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.preparationchecklist.PreparationCheckList;
import com.inetpsa.poc00.domain.preparationchecklist.PreparationCheckListRepository;

/**
 * The Class PreparationCheckListJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "PreparationCheckList")
public class PreparationCheckListJpaRepository extends BaseJpaRepository<PreparationCheckList, Long> implements PreparationCheckListRepository {

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.preparationchecklist.PreparationCheckListRepository#savePrepCheckList(com.inetpsa.poc00.domain.preparationchecklist.PreparationCheckList)
	 */
	@Override
	public PreparationCheckList savePrepCheckList(PreparationCheckList prepCheckList) {
		return super.save(prepCheckList);
	}

}
