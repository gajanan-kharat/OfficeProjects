package com.inetpsa.poc00.infrastructure.jpa;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.seed.DataSet;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.preparationfile.PreparationFile;
import com.inetpsa.poc00.domain.preparationfile.PreparationFileRepository;

/**
 * The Class PreparationFileJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "PreparationFile")
public class PreparationFileJpaRepository extends BaseJpaRepository<PreparationFile, Long> implements PreparationFileRepository {

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.preparationfile.PreparationFileRepository#savePreparationFile(com.inetpsa.poc00.domain.preparationfile.PreparationFile)
	 */
	@Override
	public PreparationFile savePreparationFile(PreparationFile preparationFile) {

		if (preparationFile.getEntityId() != null)
			return entityManager.merge(preparationFile);

		return super.save(preparationFile);

	}

}
