/*
 * Creation : Dec 2, 2016
 */
package com.inetpsa.poc00.domain.statisticalparameters;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface StatisticalParametersRepository.
 */
public interface StatisticalParametersRepository extends GenericRepository<StatisticalCalculationParameters, Long> {

	/**
	 * Save statistical para.
	 *
	 * @param scpObj the scp obj
	 * @return the statistical calculation parameters
	 */
	public StatisticalCalculationParameters saveStatisticalPara(StatisticalCalculationParameters scpObj);

	/**
	 * Gets the paramters for rule.
	 *
	 * @param japan1 the japan1
	 * @return the paramters for rule
	 */
	public List<StatisticalCalculationParameters> getParamtersForRule(String japan1);

	/**
	 * Persist statistical para.
	 *
	 * @param scpObj the scp obj
	 * @return the statistical calculation parameters
	 */
	public StatisticalCalculationParameters persistStatisticalPara(StatisticalCalculationParameters scpObj);
}
