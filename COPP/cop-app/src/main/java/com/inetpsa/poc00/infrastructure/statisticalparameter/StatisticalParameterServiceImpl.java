/*
 * Creation : Dec 6, 2016
 */
package com.inetpsa.poc00.infrastructure.statisticalparameter;

import java.util.List;

import javax.inject.Inject;

import com.inetpsa.poc00.application.statisticalparameter.StatisticalParameterService;
import com.inetpsa.poc00.domain.statisticalparameters.StatisticalCalculationParameters;
import com.inetpsa.poc00.domain.statisticalparameters.StatisticalParametersRepository;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSample;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository;

/**
 * The Class StatisticalParameterServiceImpl.
 */
public class StatisticalParameterServiceImpl implements StatisticalParameterService {

	/** The statistical parameter repo. */
	@Inject
	private StatisticalParametersRepository statisticalParameterRepo;

	@Inject
	private StatisticalSampleRepository statisticalSampleRepo;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.statisticalparameter.StatisticalParameterService#saveStatisticalParam(com.inetpsa.poc00.domain.statisticalparameters.StatisticalCalculationParameters)
	 */
	@Override
	public StatisticalCalculationParameters saveStatisticalParam(StatisticalCalculationParameters scpObj) {

		return statisticalParameterRepo.saveStatisticalPara(scpObj);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.statisticalparameter.StatisticalParameterService#updateStatisticalParam(com.inetpsa.poc00.domain.statisticalparameters.StatisticalCalculationParameters)
	 */
	@Override
	public StatisticalCalculationParameters updateStatisticalParam(StatisticalCalculationParameters scpObj) {

		List<StatisticalSample> statisticalSampleList = statisticalSampleRepo.getSamplebySampleParameter(scpObj.getEntityId());

		for (StatisticalSample statisticalSample : statisticalSampleList) {

			statisticalSample.setClosed(true);
			statisticalSampleRepo.saveStatisticalSample(statisticalSample);
		}
		return statisticalParameterRepo.persistStatisticalPara(scpObj);
	}

}
