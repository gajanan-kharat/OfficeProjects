package com.inetpsa.poc00.rest.lambdadecisionparameters;

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
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.lambdadecisionparameters.LambdaDecisionParametersService;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParameters;
import com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParametersFactory;
import com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParametersRepository;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class LambdaDecisionParametersResource.
 */
@Path("/LambdaDecisionParametersReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class LambdaDecisionParametersResource {

	/** The ldp finder. */
	@Inject
	LambdaDecisionParametersFinder ldpFinder;

	/** The ldp factory. */
	@Inject
	LambdaDecisionParametersFactory ldpFactory;

	/** The ldp assembler. */
	@Inject
	LambdaDecisionParametersAssembler ldpAssembler;

	/** The ldp repository. */
	@Inject
	LambdaDecisionParametersRepository ldpRepository;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;
	@Inject
	LambdaDecisionParametersService ldpService;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * Gets the all lambda decision parameter.
	 *
	 * @return the all lambda decision parameter
	 */
	@GET
	@Path("/AllLambdaDecisionParameters")
	public Response getAllLambdaDecisionParameter() {

		List<LambdaDecisionParametersRepresentation> ldpRepresntationList;

		logger.info("To get value from LambdaDecisionParameters Representation");
		List<LambdaDecisionParameters> ldpEntityList = ldpFinder.getAllLambdaDecisionParameter();
		logger.info("ldpEntityList--------->" + ldpEntityList);
		ldpRepresntationList = ldpAssembler.doAssembleDtoFromAggregate(ldpEntityList);
		logger.info("ldpRepresntationList--------->" + ldpRepresntationList);
		logger.info("sending LambdaDecisionParametersRepresentation value to UI");
		return Response.ok(ldpRepresntationList).build();
	}

	/**
	 * Save lambda decision parameters.
	 *
	 * @param manageLambdaDecisionParametersRequestDto the manage lambda decision parameters request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/LambdaDecisionParameters")
	public Response saveLambdaDecisionParameters(ManageLambdaDecisionParametersRequestDto manageLambdaDecisionParametersRequestDto) {

		LambdaDecisionParametersRepresentation ldpRepresentationResponse = new LambdaDecisionParametersRepresentation();
		logger.info("********************************************" + manageLambdaDecisionParametersRequestDto);
		for (LambdaDecisionParametersRepresentation ldpRepresentation : manageLambdaDecisionParametersRequestDto.getLambdaDecisionParametersRepresentationsList()) {

			List<LambdaDecisionParameters> ldpDataList = ldpFinder.getLambdaDecisionParameterByLabel(ldpRepresentation.getFuelTypeLabel());
			LambdaDecisionParameters updatedLDP = ldpAssembler.assembleFromRepresentation(ldpRepresentation);
			LambdaDecisionParameters savedLDP = ldpService.saveLambdaDecisionParameters(ldpDataList, updatedLDP, ldpRepresentation.getEntityId());
			if (savedLDP != null)
				ldpAssembler.assembleDtoFromAggregate(ldpRepresentationResponse, savedLDP);
			else
				ldpRepresentationResponse.setFuelTypeLabel(null);

		}
		logger.info("Sucessfully saved LambdaDecisionParameters data");
		return Response.ok(ldpRepresentationResponse).build();

	}

	/**
	 * Delete lambda decision parameters.
	 *
	 * @param entityId the entity id
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/LambdaDecisionParameter/{entityId}")
	public Response deleteLambdaDecisionParameters(@PathParam("entityId") String entityId) {

		Long id = Long.parseLong(entityId);
		LambdaDecisionParameters objToDelete = ldpRepository.load(id);
		int deletedRow = ldpService.deleteLambdaDecisionParameters(id);
		if (deletedRow > 0) {
			traceResource.historyForDelete(objToDelete, Constants.SPECIFICCOP_SCREEN_ID);
			return Response.ok().build();
		}
		logger.error("Error occured while deleting data ");
		return Response.status(Response.Status.PRECONDITION_FAILED).build();

	}

}
