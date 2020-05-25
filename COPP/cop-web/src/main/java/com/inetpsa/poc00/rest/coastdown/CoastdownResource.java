package com.inetpsa.poc00.rest.coastdown;

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
import com.inetpsa.poc00.application.coastdown.CoastDownDataService;
import com.inetpsa.poc00.domain.coastdown.CoastDown;
import com.inetpsa.poc00.domain.coastdown.CoastDownFactory;
import com.inetpsa.poc00.domain.coastdown.CoastDownRepository;
import com.inetpsa.poc00.domain.inertia.InertiaFactory;
import com.inetpsa.poc00.domain.inertia.InertiaRepository;
import com.inetpsa.poc00.rest.inertia.InertiaFinder;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class CoastdownResource.
 */
@Path("/CoastdownReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class CoastdownResource {

	/** The coast down finder. */
	@Inject
	CoastdownFinder coastDownFinder;

	/** The cost down factory. */
	@Inject
	CoastDownFactory costDownFactory;

	/** The coast down assembler. */
	@Inject
	CoastdownAssembler coastDownAssembler;

	/** The coast down repository. */
	@Inject
	CoastDownRepository coastDownRepository;

	/** The inertia finder. */
	@Inject
	InertiaFinder inertiaFinder;

	/** The inertia factory. */
	@Inject
	InertiaFactory inertiaFactory;

	/** The inertia repository. */
	@Inject
	InertiaRepository inertiaRepository;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;

	/** The coast down data service. */
	@Inject
	CoastDownDataService coastDownDataService;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * Gets the coastdown data.
	 *
	 * @param hsr the hsr
	 * @param info the info
	 * @return the coastdown data
	 */
	@GET
	@Path("/Coastdowns")
	public Response getCoastdownData() {

		List<CoastdownRepresentation> coastDownList;

		coastDownList = coastDownFinder.getAllCoastdownRepresentation();
		return Response.ok(coastDownList).build();
	}

	/**
	 * Save coastdown data.
	 *
	 * @param costdownDto the costdown dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Coastdowns")
	public Response saveCoastdownData(CoastdownDto costdownDto) {

		CoastdownRepresentation coastdownResponse = new CoastdownRepresentation();
		for (CoastdownRepresentation objectToSave : costdownDto.getCostdownRepresentationList()) {

			CoastDown coastDown = costDownFactory.createCoastDown();
			coastDownAssembler.doMergeAggregateWithDto(coastDown, objectToSave);
			CoastDown coastDownSaved = coastDownDataService.saveCoastDown(coastDown, objectToSave.getInertia_value(), objectToSave.getEntityId());
			if (coastDownSaved == null) {
				coastdownResponse.setpSAReference(objectToSave.getpSAReference());
				coastdownResponse.setRoadLaw(null);
				break;
			}
			coastdownResponse = objectToSave;

		}

		return Response.ok(coastdownResponse).build();
	}

	/**
	 * Coastdown value.
	 *
	 * @param inertia the inertia
	 * @return the response
	 */
	@GET
	@Path("/coastdownValue/{inertia}")
	public Response coastdownValue(@PathParam("inertia") Long inertia) {

		List<CoastdownRepresentation> coastDownList;

		coastDownList = coastDownFinder.getAllCoastdownRepresentation();

		return Response.ok(coastDownList).build();
	}

}
