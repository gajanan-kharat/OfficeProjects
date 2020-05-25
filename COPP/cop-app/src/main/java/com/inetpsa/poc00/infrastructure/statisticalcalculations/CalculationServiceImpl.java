/*
 * Creation : Nov 28, 2016
 */
package com.inetpsa.poc00.infrastructure.statisticalcalculations;

import java.util.List;

import javax.inject.Inject;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.application.statisticalcalculations.CalculationService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSetRepository;
import com.inetpsa.poc00.domain.statisticalcalculationrule.StatisticalCalculationRule;
import com.inetpsa.poc00.domain.statisticalparameters.StatisticalParametersRepository;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSample;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleFactory;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;
import com.inetpsa.poc00.infrastructure.vehicle.VehicleServiceImpl;
import com.inetpsa.poc00.util.DecisionCalculator;
import com.inetpsa.poc00.util.JAPANRule1Calculator;
import com.inetpsa.poc00.util.MVEGRule1Calculator;
import com.inetpsa.poc00.util.MVEGRule2Calculator;
import com.inetpsa.poc00.util.WLTPRule1Calculator;

/**
 * The Class CalculationServiceImpl.
 */
public class CalculationServiceImpl implements CalculationService {

	/** The vehicle file repository. */
	@Inject
	private VehicleFileRepository vehicleFileRepository;

	/** The statistical sample repository. */
	@Inject
	private StatisticalSampleRepository statisticalSampleRepository;

	/** The statistical samplefactory. */
	@Inject
	private StatisticalSampleFactory statisticalSamplefactory;

	/** The pg result set repository. */
	@Inject
	PollutantGasResultSetRepository pgResultSetRepository;

	/** The parameter repository. */
	@Inject
	StatisticalParametersRepository parameterRepository;

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.statisticalcalculations.CalculationService#calculateDecision(com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet)
	 */
	@Override
	public PollutantGasResultSet calculateDecision(PollutantGasResultSet pgResultSet) {
		/**
		 * Input will be PGResultSet object only Fetch TechnicalCase,factory,SSrule from it
		 */
		VehicleFile vehicleFile = vehicleFileRepository.load(pgResultSet.getVehicleFile().getEntityId());
		TechnicalCase technicalCase = vehicleFile.getVehicle().getTechnicalCase();

		StatisticalCalculationRule rule;
		if (technicalCase.getTechnicalGroup() != null && technicalCase.getTechnicalGroup().getRegulationGroup() != null && technicalCase.getTechnicalGroup().getRegulationGroup().getStatisticalCalculationRule() != null) {
			rule = technicalCase.getTechnicalGroup().getRegulationGroup().getStatisticalCalculationRule();
		} else {
			pgResultSet.getVehicleFile().setStatisticalDecision(ConstantsApp.ERROR_NO_STATISTICAL_RULE);
			return pgResultSet;
		}
		StatisticalSample currentSample = getSampleForCalculation(pgResultSet, rule);

		return sendForCalculation(currentSample, pgResultSet, rule);

	}

	/**
	 * @param pgResultSet
	 * @param rule
	 * @return
	 */
	private StatisticalSample getSampleForCalculation(PollutantGasResultSet pgResultSet, StatisticalCalculationRule rule) {
		VehicleFile vehicleFile = vehicleFileRepository.load(pgResultSet.getVehicleFile().getEntityId());
		TechnicalCase technicalCase = vehicleFile.getVehicle().getTechnicalCase();
		long factoryId = vehicleFile.getVehicle().getCarfactory().getEntityId();
		long typeOftestId = vehicleFile.getTypeOfTest().getEntityId();
		List<StatisticalSample> openSamples = statisticalSampleRepository.getSampleForVehicleFile(technicalCase.getEntityId(), factoryId, typeOftestId);
		StatisticalSample currentSample;
		if (openSamples.isEmpty()) {

			// If there is already OPEN sample available for same TechnicalCase,Factory and TypeOfTest
			currentSample = createNewSample(pgResultSet, vehicleFile);

		} else {
			// If there is already OPEN sample available for same TechnicalCase,Factory and TypeOfTest associate current
			// PollutantGasResultSet to same sample
			currentSample = openSamples.get(0);
			boolean closeSample = false;
			if ((ConstantsApp.MVEG_1.equalsIgnoreCase(rule.getLabelKey()) || ConstantsApp.MVEG_2.equalsIgnoreCase(rule.getLabelKey())) && currentSample.getNoOfElements() == ConstantsApp.MAX_ELEMENTS_MVEG)
				closeSample = true;
			else if (ConstantsApp.WLTP_1.equalsIgnoreCase(rule.getLabelKey()) && currentSample.getNoOfElements() == ConstantsApp.MAX_ELEMENTS_WLTP)
				closeSample = true;
			if (!closeSample) {
				currentSample = addResultSetToSample(pgResultSet, vehicleFile, currentSample);
			} else {// Sample maximum size has reached so close current sample and create New one
				currentSample.setClosed(true);
				currentSample = createNewSample(pgResultSet, vehicleFile);

			}
		}
		return currentSample;
	}

	/**
	 * @param pgResultSet
	 * @param vehicleFile
	 * @param currentSample
	 * @return
	 */
	private StatisticalSample addResultSetToSample(PollutantGasResultSet pgResultSet, VehicleFile vehicleFile, StatisticalSample currentSample) {
		boolean increment = checkSampleCounter(pgResultSet.getEntityId(), vehicleFile, currentSample);
		if (increment)// this vehicleFile is first time used for Statistical Calculations,so increment counter
			currentSample.setNoOfElements(currentSample.getNoOfElements() + 1);
		pgResultSet.setStatisticalSample(currentSample);
		pgResultSet.setStatisticalOrder(pgResultSetRepository.getMaxOrder(currentSample) + 1);
		pgResultSetRepository.savePollutantGasResultSet(pgResultSet);

		return statisticalSampleRepository.saveStatisticalSample(currentSample);
	}

	/**
	 * Send for calculation.
	 *
	 * @param currentSample the current sample
	 * @param technicalCase the technical case
	 * @param pgResultSet the pg result set
	 * @return the pollutant gas result set
	 */
	private PollutantGasResultSet sendForCalculation(StatisticalSample currentSample, PollutantGasResultSet pgResultSet, StatisticalCalculationRule rule) {

		DecisionCalculator calculator = null;
		if (ConstantsApp.MVEG_1.equalsIgnoreCase(rule.getLabelKey())) {
			calculator = new MVEGRule1Calculator(pgResultSetRepository);

		} else if (ConstantsApp.MVEG_2.equalsIgnoreCase(rule.getLabelKey())) {
			calculator = new MVEGRule2Calculator(pgResultSetRepository);

		} else if (ConstantsApp.JAPAN_1.equalsIgnoreCase(rule.getLabelKey())) {
			calculator = new JAPANRule1Calculator(parameterRepository, statisticalSampleRepository);

		} else if (ConstantsApp.WLTP_1.equalsIgnoreCase(rule.getLabelKey())) {
			calculator = new WLTPRule1Calculator(parameterRepository, pgResultSetRepository);

		}
		if (calculator != null)
			return calculator.calculateDecision(currentSample, pgResultSet);

		return pgResultSet;

	}

	/**
	 * This method checks if any PGResultSet of current Vehicle file is already used for calculations.If yes, then
	 * Sample counter is not incremented
	 *
	 * @param vehicleFile the vehicle file
	 * @return true, if successful
	 */
	@Override
	public boolean checkSampleCounter(long entityId, VehicleFile vehicleFile, StatisticalSample sample) {
		List<PollutantGasResultSet> resultSetlist = vehicleFile.getPgResultSet();

		boolean incrementFlag = true;
		for (PollutantGasResultSet resultSet : resultSetlist) {
			if (resultSet.getStatisticalSample() != null && resultSet.getStatisticalSample().getEntityId() == sample.getEntityId() && resultSet.getEntityId() != entityId)
				incrementFlag = false;
		}

		return incrementFlag;

	}

	/**
	 * This method creates new Statistical Sample for current PGResultSet and TechnicalCase,Factory and TypeOfTest for
	 * current VehicleFile.
	 *
	 * @param pgResultSet the pg result set
	 * @param vehicleFile the vehicle file
	 * @return the statistical sample
	 */
	private StatisticalSample createNewSample(PollutantGasResultSet pgResultSet, VehicleFile vehicleFile) {
		StatisticalSample newSample = statisticalSamplefactory.createStatisticalSample();
		newSample.setTechnicalCase(vehicleFile.getVehicle().getTechnicalCase());
		newSample.setCarFactory(vehicleFile.getVehicle().getCarfactory());
		newSample.setClosed(false);
		newSample.setTestType(vehicleFile.getTypeOfTest());
		newSample.setNoOfElements(1);
		newSample = statisticalSampleRepository.saveStatisticalSample(newSample);
		pgResultSet.setStatisticalSample(newSample);
		pgResultSet.setStatisticalOrder(1);
		pgResultSetRepository.savePollutantGasResultSet(pgResultSet);
		newSample = statisticalSampleRepository.saveStatisticalSample(newSample);
		return newSample;
	}

}
