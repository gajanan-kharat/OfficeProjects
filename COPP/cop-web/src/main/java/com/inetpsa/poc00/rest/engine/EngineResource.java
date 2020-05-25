package com.inetpsa.poc00.rest.engine;

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
import com.inetpsa.poc00.application.engine.EngineService;
import com.inetpsa.poc00.domain.engine.Engine;
import com.inetpsa.poc00.domain.engine.EngineFactory;
import com.inetpsa.poc00.domain.engine.EngineRepository;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjctnTypeRepository;
import com.inetpsa.poc00.domain.fueltype.FuelTypeRepository;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class EngineResource.
 */
@Path("/MoteurReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class EngineResource {

	/** The engine finder. */
	@Inject
	EngineFinder engineFinder;

	/** The fit repository. */
	@Inject
	FuelInjctnTypeRepository fitRepository;

	/** The fuel type repository. */
	@Inject
	FuelTypeRepository fuelTypeRepository;

	/** The engine factory. */
	@Inject
	EngineFactory engineFactory;

	/** The engine assembler. */
	@Inject
	EngineAssembler engineAssembler;

	/** The engine repository. */
	@Inject
	EngineRepository engineRepository;

	/** The engine service. */
	@Inject
	EngineService engineService;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * Gets the moteur data.
	 *
	 * @return the moteur data
	 */
	@GET
	@Path("/Moteurs")
	public Response getMoteurData() {

		List<EngineRepresentation> engineList;

		logger.info("Loading All Moteur Data");

		engineList = engineFinder.findAllMotureData();

		return Response.ok(engineList).build();
	}

	/**
	 * Save moteur data.
	 * 
	 * @param engineRequestDto the engine request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Moteurs")
	public Response saveMoteurData(ManageEngineRequestDto engineRequestDto) {

		logger.info("trying to save data in Engine table");
		EngineRepresentation engineRepresentationResponse = engineRequestDto.getEngineRepresentation().get(0);

		for (EngineRepresentation engineRepresentation : engineRequestDto.getEngineRepresentation()) {

			Engine engine = engineFactory.createEngine();
			engineAssembler.mergeAggregateWithDto(engine, engineRepresentation);
			engineService.saveEngine(engine);
		}

		logger.info("Sucessfully saved Engine data");
		return Response.ok(engineRepresentationResponse).build();
	}

	/**
	 * Delete moteur data.
	 * 
	 * @param engineRequestDto the engine request dto
	 * @return the response
	 */

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Moteur")
	public Response deleteMoteurData(ManageEngineRequestDto engineRequestDto) {

		logger.info("trying to delete data from Engine table");
		for (EngineRepresentation engineRepresentation : engineRequestDto.getEngineRepresentation()) {
			boolean deleted = engineService.deleteEngine(engineRepresentation.getEngineEntityID());
			if (!deleted)
				return Response.status(Response.Status.PRECONDITION_FAILED).build();

		}

		return Response.ok().build();

	}

	/**
	 * Gets the all engine data.
	 * 
	 * @return the all engine data
	 */
	@GET
	@Path("/engines")
	public Response getAllEngineData() {
		List<EngineRepresentation> engineList;

		logger.info("To get value from Engine");
		engineList = engineFinder.findAllEngineData();

		logger.info("sending Engine value to UI");
		return Response.ok(engineList).build();
	}

	/**
	 * Gets the all engine names.
	 * 
	 * @return the all engine names
	 */
	@GET
	@Path("/engineNames")
	public Response getAllEngineNames() {
		List<String> engineList;

		logger.info("Getting All Engine Name");
		engineList = engineFinder.findAllEngineNames();

		return Response.ok(engineList).build();
	}

	/**
	 * Engine data.
	 *
	 * @return the response
	 */
	@GET
	@Path("/engineData")
	public Response engineData() {
		List<EngineRepresentation> engineList;

		logger.info("All Engine Data");

		engineList = engineFinder.getAllEngineData();

		return Response.ok(engineList).build();
	}

}
