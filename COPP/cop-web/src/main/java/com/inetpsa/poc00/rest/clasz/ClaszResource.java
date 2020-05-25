package com.inetpsa.poc00.rest.clasz;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.clasz.ClaszService;
import com.inetpsa.poc00.domain.clasz.Clasz;
import com.inetpsa.poc00.domain.clasz.ClaszFactory;
import com.inetpsa.poc00.domain.clasz.ClaszRepository;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class ClaszResource.
 */
@Path("/ClaszReference")
@JpaUnit(Config.JPA_UNIT)
public class ClaszResource {

	/** The clasz finder. */
	@Inject
	ClaszFinder claszFinder;

	/** The clasz factory. */
	@Inject
	ClaszFactory claszFactory;

	/** The clasz assembler. */
	@Inject
	ClaszAssembler claszAssembler;

	/** The clasz repo. */
	@Inject
	ClaszRepository claszRepo;

	/** The clasz service. */
	@Inject
	ClaszService claszService;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(ClaszResource.class);

	/**
	 * Gets the all clasz data.
	 *
	 * @return the all clasz data
	 */
	@GET
	@Path("/AllClasz")
	@Transactional
	public Response getAllClaszData() {

		List<ClaszRepresentation> classList;

		classList = claszFinder.getAllClasz();

		return Response.ok(classList).build();
	}

	/**
	 * Save clasz.
	 *
	 * @param claszDto the clasz dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Classes")
	@Transactional
	public Response saveClasz(ClaszDto claszDto) {

		ClaszRepresentation claszRepresentationResponse = claszDto.getClaszRepresentationList().get(0);
		for (ClaszRepresentation claszRepresentation : claszDto.getClaszRepresentationList()) {

			Clasz clasz = claszFactory.createClasz();
			claszAssembler.doMergeAggregateWithDto(clasz, claszRepresentation);
			Clasz claszResponse = claszService.saveClasz(clasz);
			if (claszResponse == null) {
				claszRepresentation.setLabel(null);
				return Response.ok(claszRepresentation).build();
			}
			claszRepresentationResponse = claszRepresentation;
		}

		logger.info("Sucessfully saved Clasz data");

		return Response.ok(claszRepresentationResponse).build();

	}

	/**
	 * Delete clasz.
	 *
	 * @param entityId the entity id
	 * @return the response
	 */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/Clasz/{entityId}")
	@Transactional
	public Response deleteClasz(@PathParam("entityId") Long entityId) {

		boolean deleted = claszService.deleteClasz(entityId);
		if (!deleted)
			return Response.status(Response.Status.PRECONDITION_FAILED).build();

		return Response.ok().build();
	}
}
