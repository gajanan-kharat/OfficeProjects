/*
 * Creation : Dec 1, 2016
 */
package com.inetpsa.poc00.rest.statisticalrulesparameters;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.statisticalparameter.StatisticalParameterService;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.statisticalparameters.StatisticalCalculationParameters;
import com.inetpsa.poc00.domain.statisticalparameters.StatisticalParametersFactory;
import com.inetpsa.poc00.domain.statisticalparameters.StatisticalParametersRepository;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelFinder;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelRepresentation;
import com.inetpsa.poc00.rest.statisticalcalculationrule.StatisticalCalculationRuleRepresentation;
import com.inetpsa.poc00.rest.statisticalcalculationrule.StatisticalcalFinder;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class StatisticalParametersResource.
 */
@Path("/statisticalruleparameter")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class StatisticalParametersResource {

	/** The statisticalcal finder. */
	@Inject
	private StatisticalcalFinder statisticalcalFinder;

	/** The pollutant gas finder. */
	@Inject
	private PollutantGasLabelFinder pollutantGasFinder;

	/** The statistical parameters finder. */
	@Inject
	private StatisticalParametersFinder statisticalParametersFinder;

	/** The statistical parameter assembler. */
	@Inject
	private StatisticalParameterAssembler statisticalParameterAssembler;

	/** The statistical parameter repo. */
	@Inject
	private StatisticalParametersRepository statisticalParameterRepo;

	/** The scp factory. */
	@Inject
	private StatisticalParametersFactory scpFactory;

	/** The scp service. */
	@Inject
	private StatisticalParameterService scpService;

	/** The trace resource. */
	@Inject
	private TraceabilityResource traceResource;

	/**
	 * Gets the statistical rule.
	 * 
	 * @return the statistical rule
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/statisticalrulelist")
	public Response getStatisticalRule() {

		List<StatisticalCalculationRuleRepresentation> statisticalCalObj = statisticalcalFinder.getAllstatisticalRule();
		return Response.ok(statisticalCalObj).build();
	}

	/**
	 * Gets the pollutant gas.
	 * 
	 * @return the pollutant gas
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/polluntantgas")
	public Response getPollutantGas() {

		List<PollutantGasLabelRepresentation> pollutantGasObj = pollutantGasFinder.getAllPGLables();
		return Response.ok(pollutantGasObj).build();
	}

	/**
	 * Gets the statistical param.
	 * 
	 * @param statisticalRuleId the statistical rule id
	 * @return the statistical param
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/statisticalparameter/{statisticalRuleId}")
	public Response getstatisticalParam(@PathParam("statisticalRuleId") Long statisticalRuleId) {

		List<StatisticalParameterRepresentation> pollutantGasObj = statisticalParametersFinder.getStatisticalParameterList(statisticalRuleId);
		return Response.ok(pollutantGasObj).build();
	}

	/**
	 * Save statistical parameters.
	 * 
	 * @param statisticalParametersDto the statistical parameters dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/statisticalparameter")
	public Response saveStatisticalParameters(StatisticalParametersDto statisticalParametersDto) {

		List<StatisticalParameterRepresentation> scpRepList = statisticalParametersDto.getStatisticalParameterList();

		for (StatisticalParameterRepresentation scpRep : scpRepList) {
			StatisticalCalculationParameters scpObj = scpFactory.createSCP();

			statisticalParameterAssembler.doMergeAggregateWithDto(scpObj, scpRep);
			if (scpObj.getEntityId() == null || scpObj.getEntityId() == 0) {

				StatisticalCalculationParameters newScpObj = scpService.saveStatisticalParam(scpObj);
				traceResource.historyForSave(newScpObj, Constants.STATISTICAL_PARAM_SCREEN_ID);
			} else {
				StatisticalCalculationParameters oldScpObj = statisticalParameterRepo.load(scpObj.getEntityId());
				traceResource.historyForUpdate(oldScpObj, scpObj, Constants.STATISTICAL_PARAM_SCREEN_ID);
				scpService.updateStatisticalParam(scpObj);

			}
		}
		return Response.ok().build();
	}

}
