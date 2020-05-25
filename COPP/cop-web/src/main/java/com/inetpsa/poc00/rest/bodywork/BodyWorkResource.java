package com.inetpsa.poc00.rest.bodywork;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.bodywork.BodyWorkService;
import com.inetpsa.poc00.domain.bodywork.Bodywork;
import com.inetpsa.poc00.domain.bodywork.BodyworkFactory;
import com.inetpsa.poc00.domain.bodywork.BodyworkRepository;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class BodyWorkResource.
 */
@Path("/SilhouetteReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class BodyWorkResource {

	/** The body work finder. */
	@Inject
	BodyWorkFinder bodyWorkFinder;

	/** The bodywork factory. */
	@Inject
	BodyworkFactory bodyworkFactory;

	/** The body work assembler. */
	@Inject
	BodyWorkAssembler bodyWorkAssembler;

	/** The bodywork repository. */
	@Inject
	BodyworkRepository bodyworkRepository;

	@Inject
	BodyWorkService bodyWorkService;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * Gets the silhouette data.
	 * 
	 * @param hsr the hsr
	 * @param info the info
	 * @return the silhouette data
	 */
	@GET
	@Path("/Silhouettes")
	public Response getSilhouetteData() {

		List<BodyWorkRepresentation> bodyworkList;

		logger.info("Loading Silhouette Data");
		
		bodyworkList = bodyWorkFinder.findAllSilhouetteData();

		return Response.ok(bodyworkList).build();
	}

	/**
	 * Save silhouette data.
	 * 
	 * @param bodyWorkRequestDto the body work request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Silhouettes")
	public Response saveSilhouetteData(ManageBodyWorkRequestDto bodyWorkRequestDto) {

		logger.info("trying to save data in projectCodeFamily table");

		BodyWorkRepresentation bodyWorkRepresentationResponse = bodyWorkRequestDto.getBodyWorkRepresentationList().get(0);
		for (BodyWorkRepresentation bodyWorkRepresentation : bodyWorkRequestDto.getBodyWorkRepresentationList()) {

			Bodywork bodyWork = bodyworkFactory.createBodywork();
			bodyWorkAssembler.doMergeAggregateWithDto(bodyWork, bodyWorkRepresentation);
			Bodywork bodyWorkRespose = bodyWorkService.saveBodyWork(bodyWork);
			if (bodyWorkRespose == null) {
				bodyWorkRepresentation.setSilhouetteBOD(null);
				return Response.ok(bodyWorkRepresentation).build();
			}

			bodyWorkRepresentationResponse = bodyWorkRepresentation;

		}
		logger.info("Sucessfully saved BodyWork data");
		return Response.ok(bodyWorkRepresentationResponse).build();

	}

	/**
	 * Delete silhouette data.
	 * 
	 * @param bodyWorkRequestDto the body work request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Silhouette")
	public Response deleteSilhouetteData(ManageBodyWorkRequestDto bodyWorkRequestDto) {

		logger.info("trying to delete data from BodyWork table");

		for (BodyWorkRepresentation bodyWorkRepresentation : bodyWorkRequestDto.getBodyWorkRepresentationList()) {

			boolean deleted = bodyWorkService.delelteBodyWork(bodyWorkRepresentation.getEntityId());
			if (!deleted)
				return Response.status(Response.Status.PRECONDITION_FAILED).build();
		}
		return Response.ok().build();

	}

	/**
	 * Gets the all body work data.
	 * 
	 * @return the all body work data
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/bodyWorks")
	public Response getAllBodyWorkData() {

		List<BodyWorkRepresentation> bodyWorkRepresentationList;

		logger.info("To get value from Bodywork");
		bodyWorkRepresentationList = bodyWorkFinder.findAllBodyWorkData();

		logger.info("sending Bodywork value to UI");
		return Response.ok(bodyWorkRepresentationList).build();
	}

	/**
	 * Gets the all body work names.
	 * 
	 * @return the all body work names
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/bodyWorkNames")
	public Response getAllBodyWorkNames() {

		List<String> bodyWorkRepresentationList;

		logger.info("To get value from Bodywork");
		bodyWorkRepresentationList = bodyWorkFinder.getAllBodyWorkNames();

		logger.info("sending Bodywork value to UI");
		return Response.ok(bodyWorkRepresentationList).build();
	}
}
