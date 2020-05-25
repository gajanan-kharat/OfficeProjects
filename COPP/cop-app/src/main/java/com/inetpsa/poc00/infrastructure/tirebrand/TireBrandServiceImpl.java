
package com.inetpsa.poc00.infrastructure.tirebrand;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.tirebrand.TireBrandService;
import com.inetpsa.poc00.domain.tirebrand.TireBrand;
import com.inetpsa.poc00.domain.tirebrand.TireBrandRepository;

/**
 * The Class TireBrandServiceImpl.
 */
public class TireBrandServiceImpl implements TireBrandService {

	/** The tire brand repository. */
	@Inject
	TireBrandRepository tireBrandRepository;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.tirebrand.TireBrandService#deleteTireBrand(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public int deleteTireBrand(Long id) {

		int deletedRows = tireBrandRepository.deleteTireBrand(id);
		if (deletedRows > 0) {

			return deletedRows;
		}

		return -1;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.tirebrand.TireBrandService#saveTireBrand(com.inetpsa.poc00.domain.tirebrand.TireBrand)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public TireBrand saveTireBrand(TireBrand updatedTBD) {

		TireBrand newTireBrand = null;
		if (updatedTBD != null) {
			newTireBrand = tireBrandRepository.saveTireBrand(updatedTBD);
		}
		return newTireBrand;

	}

}
