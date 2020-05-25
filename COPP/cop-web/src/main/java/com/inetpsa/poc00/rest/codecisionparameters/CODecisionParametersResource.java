package com.inetpsa.poc00.rest.codecisionparameters;

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
import com.inetpsa.poc00.application.codecisionparameters.CODecisionParametersService;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.codecisionparameters.CODecisionParameters;
import com.inetpsa.poc00.domain.codecisionparameters.CODecisionParametersFactory;
import com.inetpsa.poc00.domain.codecisionparameters.CODecisionParametersRepository;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class CODecisionParametersResource.
 */
@Path("/CODecisionParametersReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class CODecisionParametersResource {

	/** The codp finder. */
	@Inject
	CODecisionParametersFinder cdpFinder;

	/** The codp factory. */
	@Inject
	CODecisionParametersFactory cdpFactory;

	/** The codp assembler. */
	@Inject
	CODecisionParametersAssembler cdpAssembler;

	/** The codp service. */
	@Inject
	CODecisionParametersService cdpService;

	/** The codp repository. */
	@Inject
	CODecisionParametersRepository cdpRepository;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * Gets the all co decision parameter.
	 *
	 * @return the all co decision parameter
	 */
	@GET
	@Path("/AllCODecisionParameters")
	public Response getAllCODecisionParameter() {

		List<CODecisionParametersRepresentation> codpRepresntationList;

		logger.info("To get value from CODecisionParameterRepresentation");
		List<CODecisionParameters> codpEntityList = cdpFinder.getAllCODecisionParameter();
		logger.info("codpEntityList--------->" + codpEntityList);
		codpRepresntationList = cdpAssembler.doAssembleDtoFromAggregate(codpEntityList);
		logger.info("codpRepresntationList--------->" + codpRepresntationList);
		logger.info("sending CODecisionParameterRepresentation value to UI");
		return Response.ok(codpRepresntationList).build();
	}

	/**
	 * Save co decision parameters.
	 *
	 * @param manageCODecisionParametersRequestDto the manage co decision parameters request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/CODecisionParameters")
	public Response saveCODecisionParameters(ManageCODecisionParametersRequestDto manageCODecisionParametersRequestDto) {
		CODecisionParametersRepresentation cdpRepresentationResponse = new CODecisionParametersRepresentation();
		logger.info("********************************************" + manageCODecisionParametersRequestDto);
		for (CODecisionParametersRepresentation cdpRepresentation : manageCODecisionParametersRequestDto.getCoDecisionParametersRepresentationsList()) {

			List<CODecisionParameters> cdpDataList = cdpFinder.getCODecParamByFuelTypeLabel(cdpRepresentation.getFuelTypeLabel());
			CODecisionParameters updatedCDP = cdpAssembler.assembleFromRepresentation(cdpRepresentation);
			CODecisionParameters savedCDP = cdpService.saveCODecisionParameters(cdpDataList, updatedCDP, cdpRepresentation.getEntityId());
			if (savedCDP != null)
				cdpAssembler.assembleDtoFromAggregate(cdpRepresentationResponse, savedCDP);
			else
				cdpRepresentationResponse.setFuelTypeLabel(null);

		}
		logger.info("Sucessfully saved CODecisionParameters data");
		return Response.ok(cdpRepresentationResponse).build();

	}

	/**
	 * Delete co decision parameters.
	 *
	 * @param entityId the entity id
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/CODecisionParameter/{entityId}")
	public Response deleteCODecisionParameters(@PathParam("entityId") String entityId) {

		Long id = Long.parseLong(entityId);
		CODecisionParameters objToDelete = cdpRepository.load(id);
		int deletedRow = cdpService.deleteCODecisionParameters(id);
		if (deletedRow > 0) {
			traceResource.historyForDelete(objToDelete, Constants.SPECIFICCOP_SCREEN_ID);
			return Response.ok().build();
		}
		logger.error("Error occured while deleting data ");
		return Response.status(Response.Status.PRECONDITION_FAILED).build();

	}

}
