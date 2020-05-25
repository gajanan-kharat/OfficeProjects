package com.inetpsa.poc00.rest.pollutantgaslabel;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
import com.inetpsa.poc00.application.pollgaslabel.PollGasLabelService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelFactory;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelRepository;
import com.inetpsa.poc00.pollutantorgas.PollutantGasLimitAssembler;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class PollutantGasLabelResource.
 */
@Path("/pollutantReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class PollutantGasLabelResource {

	/** The pollutant finder. */
	@Inject
	PollutantGasLabelFinder pollutantFinder;

	/** The pgl factory. */
	@Inject
	PollutantGasLabelFactory pglFactory;

	/** The pgl assembler. */
	@Inject
	PollutantGasLabelAssembler pglAssembler;

	/** The pgl repo. */
	@Inject
	PollutantGasLabelRepository pglRepo;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;

	/** The pollutant gas limit assembler. */
	@Inject
	PollutantGasLimitAssembler pollutantGasLimitAssembler;

	/** The poll gas label service. */
	@Inject
	PollGasLabelService pollGasLabelService;

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(PollutantGasLabelResource.class);

	/**
	 * Gets the all pollutant data.
	 *
	 * @param hsr the hsr
	 * @param info the info
	 * @return the all pollutant data
	 */
	@GET
	@Path("/AllPollutantLables")
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Response getAllPollutantData(@Context HttpServletResponse hsr, @Context UriInfo info) {

		List<PollutantGasLabelRepresentation> pgLabelList;

		pgLabelList = pollutantFinder.getAllPollutantData();

		return Response.ok(pgLabelList).build();
	}

	/**
	 * Save pollutant.
	 *
	 * @param pglDto the pgl dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/PollutantLables")
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Response savePollutant(PollutantGasLabelDto pglDto) {

		logger.info("trying to save data in Country table");
		for (PollutantGasLabelRepresentation pollutantGasLabelRepresentation : pglDto.getPollutantRepresentationList()) {
			PollutantGasLabel pollutantGasLabelObj = assembleFromRepresentation(pollutantGasLabelRepresentation);
			String response = pollGasLabelService.savePollGasLabel(pollutantGasLabelObj);
			if (response != ConstantsApp.SUCCESS) {
				pglAssembler.doAssembleDtoFromAggregate(pollutantGasLabelRepresentation, pollutantGasLabelObj);
				pollutantGasLabelRepresentation.setLabel(null);
				return Response.ok(pollutantGasLabelRepresentation).build();
			}
		}
		logger.info("Sucessfully saved Country data");
		return Response.ok(pglDto.getPollutantRepresentationList().get(0)).build();

	}

	/**
	 * Assemble from representation.
	 *
	 * @param pollutantGasLabelRepresentation the pollutant gas label representation
	 * @return the pollutant gas label
	 */
	private PollutantGasLabel assembleFromRepresentation(PollutantGasLabelRepresentation pollutantGasLabelRepresentation) {
		PollutantGasLabel pgl = pglFactory.createPollutantGasLabel();
		pglAssembler.mergeAggregateWithDto(pgl, pollutantGasLabelRepresentation);

		return pgl;

	}

	/**
	 * Delete pollutant.
	 *
	 * @param entityId the entity id
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/PollutantLabel/{entityId}")

	public Response deletePollutant(@PathParam("entityId") String entityId) {
		try {

			try {

				Long pollGasId = Long.parseLong(entityId);
				String result = pollGasLabelService.deletePollGasLabel(pollGasId);
				if (result != ConstantsApp.SUCCESS) {
					return Response.status(Response.Status.PRECONDITION_FAILED).build();
				}
			} catch (Exception e) {
				logger.error("exception ,{}", e);
				return Response.status(Response.Status.PRECONDITION_FAILED).build();

			}
			return Response.ok().build();
		} catch (Exception e) {
			logger.error("exception ,{}", e);
			return Response.status(Response.Status.PRECONDITION_FAILED).build();
		}

	}

	/**
	 * Gets the all PG lables.
	 *
	 * @return the all PG lables
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/PollutantGasLables")
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Response getAllPGLables() {
		List<PollutantGasLabelRepresentation> listToReturn = pollutantFinder.getAllPGLables();

		return Response.ok(listToReturn).build();

	}

	/**
	 * Gets the all un used pg labels.
	 *
	 * @param emissionStdId the emission std id
	 * @return the all un used pg labels
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/UnUsedPollutantGasLabels/{entityId}")
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
		for (PollutantGasLabel p : nullGuard(pollGasLabelService.getAllUsedPgLabels(emissionStdId))) {
			usedList.add(p.getEntityId());
		}
		Set<Long> result = new LinkedHashSet<>();

		for (Long el : nullGuard(allList)) {
			if (!usedList.contains(el)) {
				result.add(el);
			}
		}
		for (Long el : usedList) {
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
