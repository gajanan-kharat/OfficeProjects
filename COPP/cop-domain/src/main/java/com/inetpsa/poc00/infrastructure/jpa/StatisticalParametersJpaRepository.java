/*
 * Creation : Dec 2, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.statisticalparameters.StatisticalCalculationParameters;
import com.inetpsa.poc00.domain.statisticalparameters.StatisticalParametersRepository;

/**
 * The Class StatisticalParametersJpaRepository.
 */
public class StatisticalParametersJpaRepository extends BaseJpaRepository<StatisticalCalculationParameters, Long> implements StatisticalParametersRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalparameters.StatisticalParametersRepository#saveStatisticalPara(com.inetpsa.poc00.domain.statisticalparameters.StatisticalCalculationParameters)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public StatisticalCalculationParameters saveStatisticalPara(StatisticalCalculationParameters scpObj) {

		return super.save(scpObj);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalparameters.StatisticalParametersRepository#persistStatisticalPara(com.inetpsa.poc00.domain.statisticalparameters.StatisticalCalculationParameters)
	 */
	@Override
	public StatisticalCalculationParameters persistStatisticalPara(StatisticalCalculationParameters scpObj) {

		return entityManager.merge(scpObj);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalparameters.StatisticalParametersRepository#getParamtersForRule(java.lang.String)
	 */
	@Override
	public List<StatisticalCalculationParameters> getParamtersForRule(String ruleName) {

		TypedQuery<StatisticalCalculationParameters> query = entityManager.createQuery("SELECT params FROM StatisticalCalculationParameters params where params.statisticalCalculationRule.labelKey ='" + ruleName + "'", StatisticalCalculationParameters.class);

		return query.getResultList();

	}

}
