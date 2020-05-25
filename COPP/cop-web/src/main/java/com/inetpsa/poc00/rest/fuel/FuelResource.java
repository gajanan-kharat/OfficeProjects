package com.inetpsa.poc00.rest.fuel;

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
import com.inetpsa.poc00.application.fuel.FuelService;
import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.fuel.FuelFactory;
import com.inetpsa.poc00.domain.fuel.FuelRepository;
import com.inetpsa.poc00.rest.referential.GenomeDataFinder;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class FuelResource.
 */
@Path("/fuelReference")
@JpaUnit(Config.JPA_UNIT)
public class FuelResource {

	/** The genome data finder. */
	@Inject
	GenomeDataFinder genomeDataFinder;

	/** The fuel finder. */
	@Inject
	FuelFinder fuelFinder;

	/** The fuel factory. */
	@Inject
	FuelFactory fuelFactory;

	/** The fuel assembler. */
	@Inject
	FuelAssembler fuelAssembler;

	/** The fuel repo. */
	@Inject
	FuelRepository fuelRepo;

	/** The fuel service. */
	@Inject
	FuelService fuelService;

	/** The trace resource. */
	@Inject
	private TraceabilityResource traceResource;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * Gets the all fuel.
	 *
	 * @return the all fuel
	 */
	@GET
	@Path("/AllFuels")
	@Transactional
	public Response getAllFuel() {

		List<FuelRepresentation> fuelList;

		fuelList = fuelFinder.getAllFuel();

		return Response.ok(fuelList).build();
	}

	/**
	 * Save fuel.
	 * 
	 * @param fuelDto the fuel dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Fuels")
	@Transactional
	public Response saveFuel(FuelDto fuelDto) {

		FuelRepresentation fuelRepresentationResponse = fuelDto.getFuelRepresentationList().get(0);
		for (FuelRepresentation fuelRepresentation : fuelDto.getFuelRepresentationList()) {
			Fuel fuel = fuelFactory.createFuel();
			fuelAssembler.doMergeAggregateWithDto(fuel, fuelRepresentation);
			Fuel fuelResponse = fuelService.saveFuel(fuel);
			if (fuelResponse == null) {
				fuelRepresentation.setLabel(null);
				return Response.ok(fuelRepresentation).build();
			}
			fuelRepresentationResponse = fuelRepresentation;

		}
		logger.info("Sucessfully saved Fuel data");
		return Response.ok(fuelRepresentationResponse).build();

	}

	/**
	 * Delete fuel.
	 * 
	 * @param entityId the entity id
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteFuel/{entityId}")
	@Transactional
	public Response deleteFuel(@PathParam("entityId") Long entityId) {

		boolean deleted = fuelService.deleteFuel(entityId);
		if (!deleted)
			return Response.status(Response.Status.PRECONDITION_FAILED).build();

		return Response.ok().build();
	}

	/**
	 * Gets the fuels.
	 * 
	 * @return the fuels
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Fuels")
	@Transactional
	public Response getFuels() {
		List<FuelRepresentation> listToReturn = fuelFinder.getAllFuels();

		return Response.ok(listToReturn).build();

	}

	/**
	 * Gets the all fuels for ES.
	 * 
	 * @param emsId the ems id
	 * @return the all fuels for ES
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Fuels/{emsId}")
	@Transactional
	public Response getAllFuelsForES(@PathParam("emsId") String emsId) {
		List<FuelRepresentation> listToReturn = null;
		if (emsId != null) {
			long id = Long.parseLong(emsId);
			listToReturn = fuelFinder.getAllFuelsForES(id);
		}

		return Response.ok(listToReturn).build();

	}

	/**
	 * Fuel names.
	 * 
	 * @return the response
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/fuelNames")
	@Transactional
	public Response fuelNames() {
		List<String> listToReturn = fuelFinder.getAllFuelNames();

		return Response.ok(listToReturn).build();

	}

	/**
	 * Fuel data.
	 *
	 * @return the response
	 */
	@GET
	@Path("/fuelData")
	@Transactional
	public Response fuelData() {

		List<FuelRepresentation> fuelList;

		fuelList = fuelFinder.getFuelData();

		return Response.ok(fuelList).build();
	}
}
