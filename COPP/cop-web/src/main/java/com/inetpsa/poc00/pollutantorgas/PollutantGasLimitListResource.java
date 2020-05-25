/*
 * Creation : Apr 20, 2016
 */
package com.inetpsa.poc00.pollutantorgas;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.pollutantgaslimitlist.PollutantGasLimitListService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.es.EmissionStandardRepository;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListFactory;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListRepository;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardAssembler;

import ch.qos.logback.core.status.Status;

/**
 * The Class PollutantGasLimitListResource.
 */
@Path("/pollutantGas")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class PollutantGasLimitListResource {

	/** The pollutant gas list finder. */
	@Inject
	private PollutantGasListFinder pollutantGasListFinder;

	/** The pollutant gas finder. */
	@Inject
	PollutantGasLimitsFinder pollutantGasFinder;

	/** The emission standard repository. */
	@Inject
	private EmissionStandardRepository emissionStandardRepository;

	/** The pollutant gas list assembler. */
	@Inject
	private PollutantGasLimitListAssembler pollutantGasListAssembler;

	/** The pollutant gas list factory. */
	@Inject
	private PollutantGasLimitListFactory pollutantGasListFactory;

	/** The pollutant gas list repository. */
	@Inject
	private PollutantGasLimitListRepository pollutantGasListRepository;

	/** The emission standard factory. */
	@Inject
	private EmissionStandardFactory emissionStandardFactory;

	/** The emission standard assembler. */
	@Inject
	private EmissionStandardAssembler emissionStandardAssembler;

	/** The tracabilty resource. */
	@Inject
	private TraceabilityService historyService;

	/** The poll gas lim list service. */
	@Inject
	PollutantGasLimitListService pollGasLimListService;

	/** The Constant LOGGER. */
	private static final Logger logger = LoggerFactory.getLogger(PollutantGasLimitListResource.class);

	/**
	 * 
	 * @param entityId
	 * @param emsId
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/EmsDependentPGLists/{entityId}/{emsId}")
	public Response checkEmsDependentPGLists(@PathParam("entityId") long entityId, @PathParam("emsId") String emsId) {

		List<PollutantGasLimitListRepresentation> pgListObj = pollutantGasListFinder.getPGListForLabel(entityId, emsId);
		if (pgListObj.isEmpty()) {
			return Response.ok(pgListObj).build();
		}
		return Response.status(Status.ERROR).build();

	}

	/**
	 * Gets the es dependent lists.
	 * 
	 * @param emsId the ems id
	 * @return the es dependent lists
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/pollutantGasList/{emsId}")
	public Response getEsDependentLists(@PathParam("emsId") String emsId) {

		List<PollutantGasLimitListRepresentation> templist;
		List<PollutantGasLimitListRepresentation> listToReturn = new ArrayList<>();

		if (emsId != null) {

			long id = Long.parseLong(emsId);

			templist = pollutantGasListFinder.getEMSDepLists(id);

			for (PollutantGasLimitListRepresentation obj : templist) {

				PollutantGasLimitList fcObject = pollutantGasListRepository.load(obj.getEntityId());
				List<PollutantGasLimitRepresentation> dataList = pollutantGasFinder.getAllDataForEMSDepList(obj.getEntityId());

				PollutantGasLimitListRepresentation objectToSend = new PollutantGasLimitListRepresentation();
				pollutantGasListAssembler.doAssembleDtoFromAggregate(objectToSend, fcObject);
				objectToSend.setPollutantGasLimit(dataList);
				listToReturn.add(objectToSend);

			}

		}

		return Response.ok(listToReturn).build();

	}

	/**
	 * Savepollutant gas list changes.
	 * 
	 * @param pollutantGasListRequestDto the pollutant gas list request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/pollutantGasList")
	public Response savePollutantGasListChanges(ManagePollutantGasLimitListRequestDto pollutantGasListRequestDto) {

		boolean changeEmsVersion = pollutantGasListRequestDto.isChangeEmsVersion();

		Set<PollutantGasLimitListRepresentation> listToReturn = new LinkedHashSet<>();

		Set<PollutantGasLimitListRepresentation> requestList = pollutantGasListRequestDto.getPgList();
		if (requestList == null || requestList.isEmpty()) {
			return Response.ok().build();
		}

		try {

			EmissionStandard emissionStandard = emissionStandardFactory.createEmissonStandard();
			emissionStandardAssembler.doMergeAggregateWithDto(emissionStandard, requestList.iterator().next().getEmissionStandard());
			EmissionStandard newVersionObject = pollGasLimListService.getEmissionStandardObject(requestList.iterator().next().getEmissionStandard().getEntityId(), changeEmsVersion, emissionStandard);

			for (PollutantGasLimitListRepresentation pollutantGasListRepresentation : pollutantGasListRequestDto.getPgList()) {

				PollutantGasLimitList pollutantGasList = pollutantGasListFactory.createPollutantGasLimitList();
				pollutantGasListAssembler.doMergeAggregateWithDto(pollutantGasList, pollutantGasListRepresentation);
				PollutantGasLimitListRepresentation newpollutantGasListRepresentation = new PollutantGasLimitListRepresentation();

				if ("UPDATE".equalsIgnoreCase(pollutantGasListRepresentation.getModifiedFlag())) {

					PollutantGasLimitList newpGList = pollGasLimListService.updateEsDepPgl(pollutantGasList, newVersionObject);

					pollutantGasListAssembler.doAssembleDtoFromAggregate(newpollutantGasListRepresentation, newpGList);

					listToReturn.add(newpollutantGasListRepresentation);

				} else if ("INSERT".equalsIgnoreCase(pollutantGasListRepresentation.getModifiedFlag())) {

					pollGasLimListService.createEsDepPgl(newVersionObject, pollutantGasList);
					pollutantGasListAssembler.doAssembleDtoFromAggregate(newpollutantGasListRepresentation, pollutantGasList);
					listToReturn.add(newpollutantGasListRepresentation);

				} else if ("DELETE".equalsIgnoreCase(pollutantGasListRepresentation.getModifiedFlag())) {

					if (pollutantGasListRepresentation.getEntityId() != 0) {
						pollGasLimListService.deleteEmsDepPGL(pollutantGasListRepresentation.getEntityId());
					}
				} else {
					if (changeEmsVersion) {

						pollGasLimListService.copySinglePGL(pollutantGasList, newVersionObject);
						pollutantGasListAssembler.doAssembleDtoFromAggregate(newpollutantGasListRepresentation, pollutantGasList);
						listToReturn.add(newpollutantGasListRepresentation);

					} else {
						listToReturn.add(pollutantGasListRepresentation);
					}
				}

			}

			return Response.ok(listToReturn).build();
		}

		catch (Exception e) {
			logger.error(" **** End: Exception occured while running savepollutantGasListChanges Method", e);
			return Response.status(Status.ERROR).build();
		}
	}

}
