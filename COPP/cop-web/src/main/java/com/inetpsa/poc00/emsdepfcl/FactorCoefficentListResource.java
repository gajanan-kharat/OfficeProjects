/*
 * Creation : Apr 15, 2016
 */
package com.inetpsa.poc00.emsdepfcl;

import java.util.ArrayList;
import java.util.Collections;
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
import com.inetpsa.poc00.application.factorcoefficentlist.FactorCoefficentListService;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListFactory;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListRepository;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardAssembler;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelFinder;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelRepresentation;

import ch.qos.logback.core.status.Status;


/**
 * The Class FactorCoefficentListResource.
 */
@Path("/factorOrCoeff")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class FactorCoefficentListResource {

	/** The pollutant finder. */
	@Inject
	PollutantGasLabelFinder pollutantFinder;

	/** The factor coefficent list finder. */
	@Inject
	private FactorCoefficentListFinder factorCoefficentListFinder;

	/** The factor coefficient list assembler. */
	@Inject
	private FactorCoefficentListAssembler factorCoefficientListAssembler;

	/** The factor coefficient list factory. */
	@Inject
	private FactorCoeffListFactory factorCoefficientListFactory;

	/** The factor coefficient list repository. */
	@Inject
	private FactorCoeffListRepository factorCoefficientListRepository;

	/** The emission standard assembler. */
	@Inject
	private EmissionStandardAssembler emissionStandardAssembler;

	/** The emission standard factory. */
	@Inject
	private EmissionStandardFactory emissionStandardFactory;

	/** The factor coeff list service. */
	@Inject
	private FactorCoefficentListService factorCoeffListService;

	/** The Constant LOGGER. */
	private static final Logger logger = LoggerFactory.getLogger(FactorCoefficentListResource.class);

	/**
	 * Gets the es dependent lists.
	 * 
	 * @param emsId the ems id
	 * @return the es dependent lists
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/FactorCoefficentLists/{emsId}")
	public Response getEsDependentLists(@PathParam("emsId") String emsId) {
		List<FactorCoefficentListRepresentation> templist;

		List<FactorCoefficentListRepresentation> listToReturn = new ArrayList<>();

		if (emsId != null) {

			long id = Long.parseLong(emsId);
			templist = factorCoefficentListFinder.getEMSDepLists(id);

			for (FactorCoefficentListRepresentation obj : templist) {

				FactorCoefficentList fcObject = factorCoefficientListRepository.load(obj.getEntityId());
				FactorCoefficentListRepresentation objectToSend = new FactorCoefficentListRepresentation();
				factorCoefficientListAssembler.doAssembleDtoFromAggregate(objectToSend, fcObject);
				listToReturn.add(objectToSend);
			}
		}

		return Response.ok(listToReturn).build();
	}

	/**
	 * Savefactor coefficient list changes.
	 * 
	 * @param factorCoefficientListRequestDto the factor coefficient list request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/FactorCoefficentLists")
	public Response savefactorCoefficientListChanges(ManageFactorCoefficentListRequestDto factorCoefficientListRequestDto) {

		boolean changeEmsVersion = factorCoefficientListRequestDto.isChangeEmsVersion();

		List<FactorCoefficentListRepresentation> listToReturn = new ArrayList<>();

		List<FactorCoefficentListRepresentation> requestList = factorCoefficientListRequestDto.getFcListRepresentation();

		if (requestList == null || requestList.isEmpty()) {
			return Response.ok().build();
		}

		try {

			EmissionStandard emissionStandard = emissionStandardFactory.createEmissonStandard();
			emissionStandardAssembler.doMergeAggregateWithDto(emissionStandard, requestList.get(0).getEmissionStandard());
			EmissionStandard newVersionObject = factorCoeffListService.getEmissionStandardObject(requestList.get(0).getEmissionStandard().getEntityId(), changeEmsVersion, emissionStandard);

			for (FactorCoefficentListRepresentation factorCoefficientListRepresentation : factorCoefficientListRequestDto.getFcListRepresentation()) {

				FactorCoefficentList factorCoefficientList = factorCoefficientListFactory.createFactorCoeffList();
				factorCoefficientListAssembler.doMergeAggregateWithDto(factorCoefficientList, factorCoefficientListRepresentation);
				FactorCoefficentListRepresentation newfactorCoefficientListRepresentation = new FactorCoefficentListRepresentation();

				if ("UPDATE".equalsIgnoreCase(factorCoefficientListRepresentation.getModifiedFlag())) {

					FactorCoefficentList newFCList = factorCoeffListService.updateEmsDepFCL(factorCoefficientList, newVersionObject);
					factorCoefficientListAssembler.doAssembleDtoFromAggregate(newfactorCoefficientListRepresentation, newFCList);

					listToReturn.add(newfactorCoefficientListRepresentation);

				} else if ("INSERT".equalsIgnoreCase(factorCoefficientListRepresentation.getModifiedFlag())) {

					factorCoeffListService.createEmsDepFCL(newVersionObject, factorCoefficientList);
					factorCoefficientListAssembler.doAssembleDtoFromAggregate(newfactorCoefficientListRepresentation, factorCoefficientList);

					listToReturn.add(newfactorCoefficientListRepresentation);

				} else if ("DELETE".equalsIgnoreCase(factorCoefficientListRepresentation.getModifiedFlag())) {

					if (factorCoefficientListRepresentation.getEntityId() != 0) {
						factorCoeffListService.deleteEmsDepFCL(factorCoefficientListRepresentation.getEntityId());
					}

				} else {
					if (changeEmsVersion) {

						factorCoefficientList = factorCoeffListService.copySingleFCL(factorCoefficientList, newVersionObject);
						factorCoefficientListAssembler.doAssembleDtoFromAggregate(newfactorCoefficientListRepresentation, factorCoefficientList);

						listToReturn.add(newfactorCoefficientListRepresentation);

					} else {
						listToReturn.add(factorCoefficientListRepresentation);
					}
				}

			}

			return Response.ok(listToReturn).build();

		} catch (Exception e) {

			logger.error(" **** End: Exception occured while running savefactorCoefficientListChanges Method", e);
			return Response.status(Status.ERROR).build();
		}

	}

	/**
	 * F c application types.
	 * 
	 * @param entityId the entity id
	 * @param label the label
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/FCApplicationTypes/{entityId}/{emsId}")
	public Response fCApplicationTypes(@PathParam("entityId") long entityId, @PathParam("emsId") String label) {

		List<FactorCoefficentListRepresentation> fcAppObject = factorCoefficentListFinder.getFCAppTypeForLabel(entityId, label);

		if (fcAppObject.isEmpty()) {
			return Response.ok(fcAppObject).build();
		}

		return Response.status(Status.ERROR).build();

	}

	/**
	 * Gets the all un used pg labels.
	 *
	 * @param emissionStdId the emission std id
	 * @return the all un used pg labels
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/UnUsedPGLabelsForfactorOrCoeff/{entityId}")
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Response getAllUnUsedPgLabels(@PathParam("entityId") Long emissionStdId) {

		logger.info("===getAllUnUsedPgLabels==========================================>");
		List<PollutantGasLabelRepresentation> allPgLabelSet = pollutantFinder.getAllPGLables();
		Set<PollutantGasLabelRepresentation> listToReturn = new LinkedHashSet<>();

		Set<Long> allList = new LinkedHashSet<>();
		for (PollutantGasLabelRepresentation p : nullGuard(allPgLabelSet)) {
			allList.add(p.getEntityId());
		}

		Set<Long> usedList = new LinkedHashSet<>();
		for (PollutantGasLabel p : nullGuard(factorCoeffListService.getAllUsedPgLabels(emissionStdId))) {
			usedList.add(p.getEntityId());
		}
		Set<Long> result = new LinkedHashSet<>();

		for (Long el : nullGuard(allList)) {
			if (!usedList.contains(el)) {
				result.add(el);
			}
		}
		for (Long el : nullGuard(usedList)) {
			if (!allList.contains(el)) {
				result.add(el);
			}
		}

		for (PollutantGasLabelRepresentation p : nullGuard(allPgLabelSet)) {
			for (Long e1ement : result) {

				if (p.getEntityId().equals(e1ement))
					listToReturn.add(p);
			}
		}

		return Response.ok(listToReturn).build();

	}

	/**
	 * Null guard.
	 *
	 * @param <T> the generic type
	 * @param iterable the iterable
	 * @return the iterable
	 */
	private static <T> Iterable<T> nullGuard(Iterable<T> iterable) {

		if (iterable == null) {
			return Collections.<T> emptyList();
		}

		return iterable;
	}

}
