/*
 * Creation : Dec 6, 2016
 */
package com.inetpsa.poc00.application.statisticalparameter;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.statisticalparameters.StatisticalCalculationParameters;

/**
 * The Interface StatisticalParameterService.
 */
@Service
public interface StatisticalParameterService {

	/**
	 * Save statistical param.
	 *
	 * @param scpObj the scp obj
	 * @return the statistical calculation parameters
	 */
	public StatisticalCalculationParameters saveStatisticalParam(StatisticalCalculationParameters scpObj);

	/**
	 * Update statistical param.
	 *
	 * @param scpObj the scp obj
	 * @return the statistical calculation parameters
	 */
	public StatisticalCalculationParameters updateStatisticalParam(StatisticalCalculationParameters scpObj);
}
