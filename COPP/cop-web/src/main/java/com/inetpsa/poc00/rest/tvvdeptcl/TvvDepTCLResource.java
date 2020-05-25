/*
 * Creation : Mar 21, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptcl;

import java.util.ArrayList;
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
import javax.ws.rs.core.Response.Status;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.tvvdeptcl.TvvDepTCLService;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTCL;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTCLFactory;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class TvvDepTCLResource.
 */
@Path("/tvvDepTCL")

public class TvvDepTCLResource {

	/** The tvv dep tcl factory. */
	@Inject
	private TvvDepTCLFactory tvvDepTCLFactory;

	/** The tvv dep tcl assembler. */
	@Inject
	private TvvDepTCLAssembler tvvDepTCLAssembler;

	/** The tvv dep tcl finder. */
	@Inject
	private TvvDepTCLFinder tvvDepTCLFinder;

	/** The generic test finder. */
	@Inject
	private GenericTestConditionFinder genericTestFinder;

	/** The generic test condition assembler. */
	@Inject
	GenericTestConditionAssembler genericTestConditionAssembler;

	/** The generic condition mandatory finder. */
	@Inject
	private GenericConditionMandatoryFinder genericConditionMandatoryFinder;

	/** The tracabilty resource. */
	@Inject
	TraceabilityResource tracabiltyResource;
	@Inject
	private TvvDepTCLService tvvDepTclService;
	private static final Logger LOGGER = LoggerFactory.getLogger(TvvDepTCLResource.class);

	/**
	 * Save tvv dep tcl changes.
	 * 
	 * @param tvvDepTCLRequestDto the tvv dep tcl request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/TvvDependentTCLists")
	public Response saveTvvDepTCLChanges(ManageTvDepTCLRequestDto tvvDepTCLRequestDto) {

		boolean deleteFailed = false;
		for (TvvDepTCLRepresentation tvvDepTCLRepresentation : tvvDepTCLRequestDto.getTvvDepTCLRepresentationList()) {
			TvvDepTCL newOBJ = null;

			TvvDepTCL tvvDepTCL = tvvDepTCLFactory.createTvvDepTCL();
			tvvDepTCLAssembler.doMergeAggregateWithDto(tvvDepTCL, tvvDepTCLRepresentation);
			if ("INSERT".equalsIgnoreCase(tvvDepTCLRepresentation.getModifiedFlag())) {
				newOBJ = tvvDepTclService.insertTvvDepTCL(tvvDepTCL);
			} else if ("UPDATE".equalsIgnoreCase(tvvDepTCLRepresentation.getModifiedFlag())) {
				newOBJ = tvvDepTclService.updateTvvDepTCL(tvvDepTCL);
			} else if ("DELETE".equalsIgnoreCase(tvvDepTCLRepresentation.getModifiedFlag())) {
				try {
					tvvDepTclService.deleteTvvDepTCL(tvvDepTCL);
				} catch (Exception e) {
					LOGGER.error("Error in deleting TvvDepTCL", e);
					deleteFailed = true;
				}
			}
			if (newOBJ != null) {
				TvvDepTCLRepresentation newTvvDepTCLRepresentation = new TvvDepTCLRepresentation();

				tvvDepTCLAssembler.doAssembleDtoFromAggregate(newTvvDepTCLRepresentation, newOBJ);

			}

		}
		if (deleteFailed)
			return Response.status(Status.NOT_MODIFIED).build();
		return Response.ok().build();

	}

	/**
	 * Gets the tvv dependent tcl.
	 * 
	 * @return the tvv dependent tcl
	 */
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/TvvDependentTCLists")
	public Response getTvvDependentTCL() {

		List<TvvDepTCLRepresentation> listToReturn = tvvDepTCLFinder.getAllTvvDepTCL();
		for (TvvDepTCLRepresentation obj : listToReturn) {
			obj.setGenericTestCondition(new ArrayList<GenericTestConditionRepresentation>());

			List<GenericTestConditionRepresentation> dataList = genericTestFinder.getAllConditionsForList(obj.getEntityId());
			for (GenericTestConditionRepresentation tempObj : dataList) {
				List<GenericConditionMandatoryRepresentation> mandatoryList = genericConditionMandatoryFinder.getManadatoryListForCondition(tempObj.getEntityId());
				tempObj.setGenericConditionMandatory(mandatoryList);
			}
			obj.setGenericTestCondition(dataList);

		}
		return Response.ok(listToReturn).build();

	}

	/**
	 * Tvv dep tcl label.
	 * 
	 * @param entityId the entity id
	 * @return the response
	 */
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/TvvDepTCLLabel/{entityId}")
	public Response checkTvvDepTCLLabel(@PathParam("entityId") String entityId) {

		List<TvvDepTCLRepresentation> tvvDepTCLObj = tvvDepTCLFinder.getTvvDepTCLLabel(entityId);

		if (tvvDepTCLObj.isEmpty()) {
			return Response.ok(tvvDepTCLObj).build();

		}
		return Response.status(Status.PRECONDITION_FAILED).build();

	}

	/**
	 * Tvv dep tcl data.
	 * 
	 * @param entityId the entity id
	 * @param label the label
	 * @return the response
	 */
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/TvvDepTCLData/{entityId}/{label}")
	public Response checkTvvDepTCLData(@PathParam("entityId") long entityId, @PathParam("label") String label) {

		List<GenericTestConditionRepresentation> genericObj = genericTestFinder.getGenericTestLabel(label, entityId);

		if (genericObj.isEmpty()) {
			return Response.ok(genericObj).build();

		}
		return Response.status(Status.PRECONDITION_FAILED).build();

	}

}
