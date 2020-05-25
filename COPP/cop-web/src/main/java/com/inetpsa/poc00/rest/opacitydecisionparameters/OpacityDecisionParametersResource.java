package com.inetpsa.poc00.rest.opacitydecisionparameters;

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
import com.inetpsa.poc00.application.opacitydecisionparameters.OpacityDecisionParametersService;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParameters;
import com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParametersRepository;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class OpacityDecisionParametersResource.
 */
@Path("/OpacityDecisionParametersReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class OpacityDecisionParametersResource {

	/** The odp service. */
	@Inject
	OpacityDecisionParametersService odpService;
	/** The odp finder. */
	@Inject
	OpacityDecisionParametersFinder odpFinder;

	/** The odp factory. */

	/** The odp assembler. */
	@Inject
	OpacityDecisionParametersAssembler odpAssembler;

	/** The odp repository. */
	@Inject
	OpacityDecisionParametersRepository odpRepository;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * Gets the all opacity decision parameter.
	 *
	 * @return the all opacity decision parameter
	 */
	@GET
	@Path("/AllOpacityDecisionParameters")
	public Response getAllOpacityDecisionParameter() {

		List<OpacityDecisionParametersRepresentation> odpRepresntationList;

		logger.info("To get value from OpacityDecisionParameters Representation");
		List<OpacityDecisionParameters> odpEntityList = odpFinder.getAllOpacityDecisionParameter();
		logger.info("odpEntityList--------->" + odpEntityList);
		odpRepresntationList = odpAssembler.doAssembleDtoFromAggregate(odpEntityList);
		logger.info("odpRepresntationList--------->" + odpRepresntationList);
		logger.info("sending OpacityDecisionParametersRepresentation value to UI");
		return Response.ok(odpRepresntationList).build();
	}

	/**
	 * Save lambda decision parameters.
	 *
	 * @param manageOpacityDecisionParametersRequestDto the manage lambda decision parameters request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/OpacityDecisionParameters")
	public Response saveOpacityDecisionParameters(ManageOpacityDecisionParametersRequestDto manageOpacityDecisionParametersRequestDto) {

		OpacityDecisionParametersRepresentation odpRepresentationResponse = new OpacityDecisionParametersRepresentation();
		logger.info("********************************************" + manageOpacityDecisionParametersRequestDto);
		for (OpacityDecisionParametersRepresentation odpRepresentation : manageOpacityDecisionParametersRequestDto.getOpacityDecisionParametersRepresentationsList()) {

			List<OpacityDecisionParameters> odpDataList = odpFinder.getOpacityDecisionParameterByLabel(odpRepresentation.getFuelTypeLabel());
			OpacityDecisionParameters updatedODP = odpAssembler.assembleFromRepresentation(odpRepresentation);
			OpacityDecisionParameters savedODP = odpService.saveOpacityDecisionParameters(odpDataList, updatedODP, odpRepresentation.getEntityId());
			if (savedODP != null)
				odpAssembler.assembleDtoFromAggregate(odpRepresentationResponse, savedODP);
			else
				odpRepresentationResponse.setFuelTypeLabel(null);

		}
		logger.info("Sucessfully saved OpacityDecisionParameters data");
		return Response.ok(odpRepresentationResponse).build();

	}

	/**
	 * Delete opacity decision parameters.
	 *
	 * @param entityId the entity id
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/OpacityDecisionParameter/{entityId}")
	public Response deleteOpacityDecisionParameters(@PathParam("entityId") String entityId) {

		Long id = Long.parseLong(entityId);
		OpacityDecisionParameters objToDelete = odpRepository.load(id);
		int deletedRow = odpService.deleteOpacityDecisionParameters(id);
		if (deletedRow > 0) {
			traceResource.historyForDelete(objToDelete, Constants.SPECIFICCOP_SCREEN_ID);
			return Response.ok().build();
		}
		logger.error("Error occured while deleting data ");
		return Response.status(Response.Status.PRECONDITION_FAILED).build();

	}

}
