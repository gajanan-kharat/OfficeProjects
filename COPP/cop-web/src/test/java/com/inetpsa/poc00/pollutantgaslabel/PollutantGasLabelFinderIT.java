/*
 * Creation : Jan 16, 2017
 */
package com.inetpsa.poc00.pollutantgaslabel;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelFactory;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelRepository;
import com.inetpsa.poc00.domain.statisticalcalculationrule.StatisticalCalculationRule;
import com.inetpsa.poc00.domain.statisticalcalculationrule.StatsCalcltnRuleFactory;
import com.inetpsa.poc00.domain.statisticalcalculationrule.StatsCalcltnRuleRepository;
import com.inetpsa.poc00.domain.statisticalparameters.StatisticalCalculationParameters;
import com.inetpsa.poc00.domain.statisticalparameters.StatisticalParametersFactory;
import com.inetpsa.poc00.domain.statisticalparameters.StatisticalParametersRepository;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelFinder;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelRepresentation;

/**
 * The Class PollutantGasLabelFinderIT.
 */
@RunWith(SeedITRunner.class)
public class PollutantGasLabelFinderIT {

	/** The pollutant gas label finder. */
	@Inject
	PollutantGasLabelFinder pollutantGasLabelFinder;

	/** The pollutant gas label factory. */
	@Inject
	PollutantGasLabelFactory pollutantGasLabelFactory;

	/** The pollutant gas label repository. */
	@Inject
	PollutantGasLabelRepository pollutantGasLabelRepository;

	/** The statistical parameters factory. */
	@Inject
	StatisticalParametersFactory statisticalParametersFactory;

	/** The statistical parameters repository. */
	@Inject
	StatisticalParametersRepository statisticalParametersRepository;

	/** The stats calcltn rule repository. */
	@Inject
	StatsCalcltnRuleRepository statsCalcltnRuleRepository;

	/** The stats calcltn rule factory. */
	@Inject
	StatsCalcltnRuleFactory statsCalcltnRuleFactory;

	/**
	 * Test get all pg lables.
	 */
	@Test
	public final void testGetAllPGLables() {
		PollutantGasLabel pgLabel = pollutantGasLabelFactory.createPollutantGasLabel();
		pgLabel.setLabel("PollGasLabel" + Calendar.getInstance().getTime());
		pollutantGasLabelRepository.savePollutantGasLabel(pgLabel);
		List<PollutantGasLabelRepresentation> pgList = pollutantGasLabelFinder.getAllPGLables();
		assertNotNull(pgList);
	}

	/**
	 * Test find pollutant gas label data by label.
	 */
	@Test
	public final void testFindPollutantGasLabelDataByLabel() {
		PollutantGasLabel pgLabel = pollutantGasLabelFactory.createPollutantGasLabel();
		pgLabel.setLabel("PollGasLabel" + Calendar.getInstance().getTime());
		PollutantGasLabel pollutantGasLabelSaved = pollutantGasLabelRepository.savePollutantGasLabel(pgLabel);
		List<PollutantGasLabelRepresentation> pgList = pollutantGasLabelFinder.findPollutantGasLabelDataByLabel(pollutantGasLabelSaved.getLabel());
		assertNotNull(pgList);
	}

	/**
	 * Test get all pollutant data.
	 */
	@Test
	public final void testGetAllPollutantData() {
		PollutantGasLabel pgLabel = pollutantGasLabelFactory.createPollutantGasLabel();
		pgLabel.setLabel("PollGasLabel" + Calendar.getInstance().getTime());
		pollutantGasLabelRepository.savePollutantGasLabel(pgLabel);
		List<PollutantGasLabelRepresentation> pgList = pollutantGasLabelFinder.getAllPollutantData();
		assertNotNull(pgList);
	}

	/**
	 * Test get pgl for statistical parameter.
	 */
	@Test
	public final void testGetPGLforStatisticalParameter() {
		StatisticalCalculationRule statisticalCalculationRule = statsCalcltnRuleFactory.createStatCalculationRule();
		StatisticalCalculationRule statisticalCalculationRuleSaved = statsCalcltnRuleRepository.saveStatCalRule(statisticalCalculationRule);
		StatisticalCalculationParameters statisticalCalculationParameters = statisticalParametersFactory.createSCP();
		statisticalCalculationParameters.setStatisticalCalculationRule(statisticalCalculationRuleSaved);
		StatisticalCalculationParameters statisticalCalculationParametersSaved = statisticalParametersRepository.saveStatisticalPara(statisticalCalculationParameters);
		PollutantGasLabel pgLabel = pollutantGasLabelFactory.createPollutantGasLabel();
		List<StatisticalCalculationParameters> statCalculationParametersList = new ArrayList<>();
		statCalculationParametersList.add(statisticalCalculationParametersSaved);
		pgLabel.setLabel("PollGasLabel" + Calendar.getInstance().getTime());
		pgLabel.setStatisticalCalculationParameters(statCalculationParametersList);
		pollutantGasLabelRepository.savePollutantGasLabel(pgLabel);
		List<PollutantGasLabelRepresentation> pgList = pollutantGasLabelFinder.getPGLforStatisticalParameter(statisticalCalculationRuleSaved.getEntityId());
		assertNotNull(pgList);
	}

}
