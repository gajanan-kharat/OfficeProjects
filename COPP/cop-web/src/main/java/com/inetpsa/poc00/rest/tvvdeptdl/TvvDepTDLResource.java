/*
 * Creation : Mar 21, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptdl;

import java.net.URISyntaxException;
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
import com.inetpsa.poc00.application.tvvdeptdl.TvvDepTDLService;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTDL;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTDLFactory;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class TvvDepTDLResource.
 */
@Path("/tvvDepTDL")

public class TvvDepTDLResource {

	/** The tvv dep tdl factory. */
	@Inject
	private TvvDepTDLFactory tvvDepTDLFactory;

	/** The tvv dep tdl assembler. */
	@Inject
	private TvvDepTDLAssembler tvvDepTDLAssembler;

	/** The tvv dep tdl finder. */
	@Inject
	private TvvDepTDLFinder tvvDepTDLFinder;

	/** The generic data finder. */
	@Inject
	private GenericTechnicalDataFinder genericDataFinder;

	/** The Generic tech data repository. */
	@Inject
	com.inetpsa.poc00.domain.generictd.GenericTechDataRepository genericTechDataRepository;

	/** The generic technical data assembler. */
	@Inject
	GenericTechnicalDataAssembler genericTechnicalDataAssembler;

	/** The generic data mandatory finder. */
	@Inject
	GenericDataMandatoryFinder genericDataMandatoryFinder;
	@Inject
	TraceabilityResource tracabiltyResource;
	@Inject
	TvvDepTDLService tvvDepTdlService;
	private static final Logger LOGGER = LoggerFactory.getLogger(TvvDepTDLResource.class);

	/**
	 * Save tvv dep tdl changes.
	 * 
	 * @param tvvDepTDLRequestDto the tvv dep tdl request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/TvvDependentTDLists")
	public Response saveTvvDepTDLChanges(ManageTvvDepTDLRequestDto tvvDepTDLRequestDto) {

		boolean deleteFailed = false;
		for (TvvDepTDLRepresentation tvvDepTDLRepresentation : tvvDepTDLRequestDto.getTvvDepTDLRepresentationList()) {
			TvvDepTDL newOBJ = null;

			TvvDepTDL tvvDepTDL = tvvDepTDLFactory.createTvvDepTDL();
			tvvDepTDLAssembler.doMergeAggregateWithDto(tvvDepTDL, tvvDepTDLRepresentation);
			if ("INSERT".equalsIgnoreCase(tvvDepTDLRepresentation.getModifiedFlag())) {
				newOBJ = tvvDepTdlService.insertTvvDepTDL(tvvDepTDL);
			} else if ("UPDATE".equalsIgnoreCase(tvvDepTDLRepresentation.getModifiedFlag())) {
				newOBJ = tvvDepTdlService.updateTvvDepTDL(tvvDepTDL);
			} else if ("DELETE".equalsIgnoreCase(tvvDepTDLRepresentation.getModifiedFlag())) {
				try {
					tvvDepTdlService.deleteTvvDepTDL(tvvDepTDL);
				} catch (Exception e) {
					LOGGER.error("Error in deleting TvvDepTDL", e);
					deleteFailed = true;
				}
			}
			if (newOBJ != null) {
				TvvDepTDLRepresentation newTvvDepTDLRepresentation = new TvvDepTDLRepresentation();

				tvvDepTDLAssembler.doAssembleDtoFromAggregate(newTvvDepTDLRepresentation, newOBJ);

			}

		}
		if (deleteFailed)
			return Response.status(Status.NOT_MODIFIED).build();
		return Response.ok().build();

	}

	/**
	 * Gets the tvv dependent tdl.
	 * 
	 * @return the tvv dependent tdl
	 */
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/TvvDependentTDLists")
	public Response getTvvDependentTDL() {
		List<TvvDepTDLRepresentation> listToReturn = tvvDepTDLFinder.getAllTvvDepTDL();

		for (TvvDepTDLRepresentation obj : listToReturn) {
			obj.setGenericTechnicalData(new ArrayList<GenericTechnicalDataRepresentation>());

			List<GenericTechnicalDataRepresentation> dataList = genericDataFinder.getAllDataForList(obj.getEntityId());
			for (GenericTechnicalDataRepresentation tempData : dataList) {
				List<GenericTechDataMandatoryRepresentation> mandatoryList = genericDataMandatoryFinder.getMandatoryListForData(tempData.getEntityId());
				tempData.setGenericTechnicalDataManadatory(mandatoryList);
			}
			obj.setGenericTechnicalData(dataList);
		}
		return Response.ok(listToReturn).build();

	}

	/**
	 * Update tvv dependent tdl.
	 * 
	 * @param tvvDepTDLRepresentation the tvv dep tdl representation
	 * @return the response
	 * @throws URISyntaxException the uRI syntax exception
	 */
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/TvvDepTDLLabel/{entityId}")
	public Response checkTvvDepTDLLabel(@PathParam("entityId") String entityId) {

		List<TvvDepTDLRepresentation> tvvDepTDLObj = tvvDepTDLFinder.getTvvDepTDLLabel(entityId);

		if (tvvDepTDLObj.isEmpty()) {
			return Response.ok(tvvDepTDLObj).build();

		}
		return Response.status(Status.PRECONDITION_FAILED).build();

	}

	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/TvvDepTDLData/{entityId}/{label}")
	public Response checkTvvDepTDLData(@PathParam("entityId") long entityId, @PathParam("label") String label) {

		List<GenericTechnicalDataRepresentation> genericObj = genericDataFinder.getGenericDataLabel(label, entityId);

		if (genericObj.isEmpty()) {
			return Response.ok(genericObj).build();

		}
		return Response.status(Status.PRECONDITION_FAILED).build();

	}

}
