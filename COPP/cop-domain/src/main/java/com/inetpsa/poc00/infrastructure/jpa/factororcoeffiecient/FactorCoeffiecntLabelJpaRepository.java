/*
 * Creation : Jun 16, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa.factororcoeffiecient;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoeffLabelRepository;
import com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoefficentsLabel;

/**
 * The Class FactorCoeffiecntLabelJpaRepository.
 */
public class FactorCoeffiecntLabelJpaRepository extends BaseJpaRepository<FactorCoefficentsLabel, Long> implements FactorCoeffLabelRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoeffLabelRepository#saveFactorCoeffLabel(com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoefficentsLabel)
	 */
	@Override
	public FactorCoefficentsLabel saveFactorCoeffLabel(FactorCoefficentsLabel labelObj) {
		if (labelObj.getEntityId() != null && labelObj.getEntityId() != 0)
			return super.save(labelObj);
		super.persist(labelObj);
		entityManager.flush();

		return this.load(labelObj.getEntityId());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoeffLabelRepository#persistFactorCoeffLabel(com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoefficentsLabel)
	 */
	@Override
	public void persistFactorCoeffLabel(FactorCoefficentsLabel object) {
		super.persist(object);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoeffLabelRepository#deleteAll()
	 */
	@Override
	public long deleteAll() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoeffLabelRepository#count()
	 */
	@Override
	public long count() {

		return 0;
	}

}
