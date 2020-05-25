package com.inetpsa.poc00.rest.typeapprovalarea;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.typeapprovalarea.TypeApprovalAreaService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalArea;
import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalAreaFactory;
import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalAreaRepository;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class TypeApprovalAreaResource.
 */
@Path("/typeApprovalAreaReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class TypeApprovalAreaResource {

	/** The type app area finder. */
	@Inject
	TypeApprovalAreaFinder typeAppAreaFinder;

	/** The type app area factory. */
	@Inject
	TypeApprovalAreaFactory typeAppAreaFactory;

	/** The type app area assembler. */
	@Inject
	TypeApprovalAreaAssembler typeAppAreaAssembler;

	/** The type app area repo. */
	@Inject
	TypeApprovalAreaRepository typeAppAreaRepo;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;
	@Inject
	TypeApprovalAreaService typeApprovalAreaService;
	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(TypeApprovalAreaResource.class);

	/**
	 * Gets the all type approval area.
	 * 
	 * @param hsr the hsr
	 * @param info the info
	 * @return the all type approval area
	 */
	@GET
	@Path("/AllTypeApprovalAreas")
	public Response getAllTypeApprovalArea(@Context HttpServletResponse hsr, @Context UriInfo info) {

		List<TypeApprovalAreaRepresentation> typeApprovalAreaList;

		typeApprovalAreaList = typeAppAreaFinder.getAllTypeApprovalArea();

		return Response.ok(typeApprovalAreaList).build();
	}

	@GET
	@Path("/TypeApprovalAreasForRG")
	public Response getAllTypeApprovalAreaForRG() {

		List<TypeApprovalAreaRepresentation> typeApprovalAreaList;

		typeApprovalAreaList = typeAppAreaFinder.getAllTypeApprovalAreaForRg();

		return Response.ok(typeApprovalAreaList).build();
	}

	/**
	 * Save type approval area.
	 * 
	 * @param typeAppAreaDto the type app area dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/TypeApprovalAreas")
	public Response saveTypeApprovalArea(TypeApprovalAreaDto typeAppAreaDto) {

		logger.info("trying to save data in TypeApprovalArea table");
		for (TypeApprovalAreaRepresentation typeApprovalAreaRepresentation : typeAppAreaDto.getTypeApprovalAreaRepresentationList()) {
			TypeApprovalArea typeAppAreaObj = assembleFromRepresentation(typeApprovalAreaRepresentation);
			String response = typeApprovalAreaService.saveTypeApprovalArea(typeAppAreaObj);
			if (response != ConstantsApp.SUCCESS) {
				typeAppAreaAssembler.doAssembleDtoFromAggregate(typeApprovalAreaRepresentation, typeAppAreaObj);
				typeApprovalAreaRepresentation.setLabel(null);
				return Response.ok(typeApprovalAreaRepresentation).build();
			}
		}
		logger.info("Sucessfully saved TypeApprovalArea data");
		return Response.ok(typeAppAreaDto.getTypeApprovalAreaRepresentationList().get(0)).build();

	}

	/**
	 * Update type approval area.
	 * 
	 * @param typeApprovalAreaRepresentation the type approval area representation
	 */
	private TypeApprovalArea assembleFromRepresentation(TypeApprovalAreaRepresentation typeApprovalAreaRepresentation) {
		TypeApprovalArea typeAppArea = typeAppAreaFactory.createTypeApprovalArea();

		typeAppAreaAssembler.mergeAggregateWithDto(typeAppArea, typeApprovalAreaRepresentation);

		return typeAppArea;

	}

	/**
	 * Delete type approval area.
	 * 
	 * @param entityId the entity id
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/TypeApprovalArea/{entityId}")
	public Response deleteTypeApprovalArea(@PathParam("entityId") String entityId) {

		logger.info("trying to delete data from Country table");

		String result = typeApprovalAreaService.deleteTypeApprovalArea(Long.parseLong(entityId));
		if (result != ConstantsApp.SUCCESS) {
			return Response.status(Response.Status.PRECONDITION_FAILED).build();
		}

		return Response.ok().build();

	}

	/**
	 * Gets the all area names.
	 * 
	 * @return the all area names
	 */
	@GET
	@Path("/areaNames")
	public Response getAllAreaNames() {

		List<String> areaList;

		areaList = typeAppAreaFinder.getAllAreaNames();

		return Response.ok(areaList).build();
	}

	/**
	 * Type approval area data.
	 * 
	 * @param hsr the hsr
	 * @param info the info
	 * @return the response
	 */
	@GET
	@Path("/typeApprovalAreaData")
	public Response typeApprovalAreaData() {

		List<TypeApprovalAreaRepresentation> typeApprovalAreaList;

		typeApprovalAreaList = typeAppAreaFinder.getTypeApprovalAreaData();

		return Response.ok(typeApprovalAreaList).build();
	}

}
