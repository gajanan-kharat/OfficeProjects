/*
 * Creation : Nov 23, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.infrastructure.pollgasresultset;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.pollgasresultset.PollutantGasResultSetService;
import com.inetpsa.poc00.application.statisticalcalculations.CalculationService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSetFactory;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSetRepository;
import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResult;
import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResultFactory;
import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResultRepository;
import com.inetpsa.poc00.domain.statisticalcalculationrule.StatisticalCalculationRule;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSample;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvvvaluedpgl.TvvValuedEsDepPGL;
import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimit;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;

/**
 * the implementation class for TvvValuedPollGasLimitService.
 *
 * @author mehaj
 */
public class PollutantGasResultSetServiceImpl implements PollutantGasResultSetService {

	/** The pollutant gas test result factory. */
	@Inject
	PollutantGasTestResultFactory pollutantGasTestResultFactory;

	/** The pollutant gas test result repository. */
	@Inject
	PollutantGasTestResultRepository pollutantGasTestResultRepository;

	/** The pollutant gas result set factory. */
	@Inject
	PollutantGasResultSetFactory pollutantGasResultSetFactory;

	/** The pollutant gas result set repository. */
	@Inject
	PollutantGasResultSetRepository pollutantGasResultSetRepository;

	/** The vehicle file repository. */
	@Inject
	VehicleFileRepository vehicleFileRepository;

	/** The statistical sample repository. */
	@Inject
	private StatisticalSampleRepository statisticalSampleRepository;

	/** The traceability service. */
	@Inject
	private TraceabilityService traceabilityService;
	@Inject
	private CalculationService calculationService;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.pollgasresultset.PollutantGasResultSetService#createPollutantGasResultSet(com.inetpsa.poc00.domain.vehiclefile.VehicleFile,
	 *      com.inetpsa.poc00.domain.tvv.TVV, int)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<PollutantGasResultSet> createPollutantGasResultSet(VehicleFile vehicleFileObj, TVV tvvObj, int resultSetListSize) {
		List<TvvValuedEsDepPGL> tvvValuedEsDepPGLList = tvvObj.getTvvValuedEsDepPGL();
		List<PollutantGasResultSet> listPollutantGasResultSetList = new ArrayList<>();
		List<TvvValuedPollutantGasLimit> listTvvValuedPollutantGasLimit = new ArrayList<>();
		for (TvvValuedEsDepPGL tvvValuedEsDepPGL : tvvValuedEsDepPGLList) {
			listTvvValuedPollutantGasLimit.addAll(tvvValuedEsDepPGL.getPollutantGasLimit());
		}
		for (int i = 0; i < ConstantsApp.INTERGER_VAL_THREE - resultSetListSize; i++) {
			PollutantGasResultSet pollutantGasResultSet = pollutantGasResultSetFactory.createPollutantGasResultSet();
			List<PollutantGasTestResult> listPollutantGasTestResult = new ArrayList<>();
			for (TvvValuedPollutantGasLimit tvvValuedPollutantGasLimit : listTvvValuedPollutantGasLimit) {
				PollutantGasTestResult pollutantGasTestResultObj = pollutantGasTestResultFactory.createPollutantGasTestResult();
				pollutantGasTestResultObj.setTvvValuedPollGasLimit(tvvValuedPollutantGasLimit);
				pollutantGasTestResultObj.setPollutantGasResultSet(pollutantGasResultSet);
				listPollutantGasTestResult.add(pollutantGasTestResultObj);
			}
			pollutantGasResultSet.setInQuarantine(false);
			pollutantGasResultSet.setKeptInStatSample(false);
			pollutantGasResultSet.setVehicleFile(vehicleFileObj);
			pollutantGasResultSet.setSetOrder(i + resultSetListSize + 1);
			pollutantGasResultSet.setPollutantGasTestResult(listPollutantGasTestResult);

			listPollutantGasResultSetList.add(pollutantGasResultSet);
		}

		return listPollutantGasResultSetList;
	}

	/**
	 * Save pollutant gas result set old.
	 *
	 * @param pollutantGasResultSet the pollutant gas result set
	 * @return the pollutant gas result set
	 */
	public PollutantGasResultSet savePollutantGasResultSetOld(PollutantGasResultSet pollutantGasResultSet) {
		return pollutantGasResultSetRepository.save(pollutantGasResultSet);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.pollgasresultset.PollutantGasResultSetService#savePollutantGasResultSetValues(com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public PollutantGasResultSet savePollutantGasResultSetValues(PollutantGasResultSet pollGasResultSetToSave) {
		if (pollGasResultSetToSave.getEntityId() != null) {
			pollGasResultSetToSave.setUpdateDate(new Date());
			return pollutantGasResultSetRepository.savePollutantGasResultSet(pollGasResultSetToSave);
		}
		boolean createNewResult = false;
		for (PollutantGasTestResult pollutantGasTestResult : pollGasResultSetToSave.getPollutantGasTestResult()) {
			if (pollutantGasTestResult.getValue() != null) {
				createNewResult = true;
			}
		}
		if (createNewResult) {
			pollGasResultSetToSave.setUpdateDate(new Date());
			return pollutantGasResultSetRepository.savePollutantGasResultSet(pollGasResultSetToSave);
		}
		return new PollutantGasResultSet();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.pollgasresultset.PollutantGasResultSetService#getPollGasResultSet(com.inetpsa.poc00.domain.vehiclefile.VehicleFile)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<PollutantGasResultSet> getPollGasResultSet(VehicleFile vehicleFileObj) {
		List<PollutantGasResultSet> pollGasResultSetList = pollutantGasResultSetRepository.getPollutantGasResultSets(vehicleFileObj.getEntityId());
		List<PollutantGasResultSet> listResultsetObj = new ArrayList<>();
		if (pollGasResultSetList.isEmpty() || pollGasResultSetList.size() < ConstantsApp.INTERGER_VAL_THREE) {
			listResultsetObj = createPollutantGasResultSet(vehicleFileObj, vehicleFileObj.getVehicle().getTechnicalCase().getTvv(), pollGasResultSetList.size());
		}
		pollGasResultSetList.addAll(listResultsetObj);

		return pollGasResultSetList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.pollgasresultset.PollutantGasResultSetService#savePollutantGasResultSet(com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet)
	 */
	@Override
	public PollutantGasResultSet savePollutantGasResultSet(PollutantGasResultSet pollutantGasResultSet) {
		pollutantGasResultSet.setUpdateDate(new Date());
		return pollutantGasResultSetRepository.savePollutantGasResultSet(pollutantGasResultSet);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.pollgasresultset.PollutantGasResultSetService#changeQurantineFlag(java.lang.Long,
	 *      boolean)
	 */
	@Override
	public void changeQurantineFlag(Long pgResultSetId, boolean inQurantineStatus) {
		PollutantGasResultSet resultSetObj = pollutantGasResultSetRepository.load(pgResultSetId);
		if (resultSetObj.getInQuarantine() == null || resultSetObj.getInQuarantine() != inQurantineStatus) {
			resultSetObj.setInQuarantine(inQurantineStatus);

			savePollutantGasResultSet(resultSetObj);

			StatisticalSample sample = resultSetObj.getStatisticalSample();

			if (sample != null && !sample.isClosed()) {
				// If resultSet is associated to open sample then now it will not be part of sample so decrement sample
				// count
				if (resultSetObj.getInQuarantine() && sample.getNoOfElements() > 0) {

					sample.setNoOfElements(sample.getNoOfElements() - 1);

				} else // removing inQurantine, resultSet can be part of sample now

				if (!resultSetObj.getInQuarantine() && (checkSampleCount(resultSetObj, sample))) {

					// If any other result set of same vehicleFile is already in use then dont add this flag

					sample.setNoOfElements(sample.getNoOfElements() + 1);

				}
				statisticalSampleRepository.save(sample);
			}

			String desc;
			if (inQurantineStatus) {
				desc = ConstantsApp.ENQUARANTAINE_TRUE_DESC;
			} else {
				desc = ConstantsApp.ENQUARANTAINE_FALSE_DESC;
			}
			traceabilityService.saveVehicleFileHistory(resultSetObj.getVehicleFile(), null, desc);
		}

	}

	/**
	 * 
	 * @param resultSet
	 * @param currentSample
	 * @return
	 */
	boolean checkSampleCount(PollutantGasResultSet resultSet, StatisticalSample currentSample) {
		boolean maxSizeReached = false;
		VehicleFile vehicleFile = vehicleFileRepository.load(resultSet.getVehicleFile().getEntityId());
		TechnicalCase technicalCase = vehicleFile.getVehicle().getTechnicalCase();

		StatisticalCalculationRule rule = technicalCase.getTechnicalGroup().getRegulationGroup().getStatisticalCalculationRule();

		if ((ConstantsApp.MVEG_1.equalsIgnoreCase(rule.getLabelKey()) || ConstantsApp.MVEG_2.equalsIgnoreCase(rule.getLabelKey())) && currentSample.getNoOfElements() == ConstantsApp.MAX_ELEMENTS_MVEG)
			maxSizeReached = true;
		else if (ConstantsApp.WLTP_1.equalsIgnoreCase(rule.getLabelKey()) && currentSample.getNoOfElements() == ConstantsApp.MAX_ELEMENTS_WLTP)
			maxSizeReached = true;

		if (!maxSizeReached)// sample Max size has not reached so check if any other ResultSet of same VehicelFile is
							// already associated to sample sample
			return calculationService.checkSampleCounter(resultSet.getEntityId(), resultSet.getVehicleFile(), currentSample);
		return !maxSizeReached;// don't increment counter as sample maxSize has reached
	}

	
}
