package com.inetpsa.poc00.infrastructure.jpa;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.seed.DataSet;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.preparationresult.PreparationResult;
import com.inetpsa.poc00.domain.preparationresult.PreparationResultRepository;

/**
 * The Class PreparationResultJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "PreparationResult")
public class PreparationResultJpaRepository extends BaseJpaRepository<PreparationResult, Long> implements PreparationResultRepository {

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.preparationresult.PreparationResultRepository#savePrepCheckList(com.inetpsa.poc00.domain.preparationresult.PreparationResult)
	 */
	@Override
	public PreparationResult savePrepResultList(PreparationResult preparationResult) {
		return super.save(preparationResult);
	}

}
