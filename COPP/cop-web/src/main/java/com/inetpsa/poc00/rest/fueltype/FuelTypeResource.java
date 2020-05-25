package com.inetpsa.poc00.rest.fueltype;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.seedstack.business.assembler.FluentAssembler;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.fueltype.FuelTypeService;
import com.inetpsa.poc00.domain.fueltype.FuelType;
import com.inetpsa.poc00.domain.fueltype.FuelTypeFactory;
import com.inetpsa.poc00.domain.fueltype.FuelTypeRepository;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class FuelTypeResource.
 */
@Path("/FuelTypeReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class FuelTypeResource {

	/** The fuel type finder. */
	@Inject
	FuelTypeFinder fuelTypeFinder;

	/** The fluent assembler. */
	@Inject
	FluentAssembler fluentAssembler;

	/** The fuel type repository. */
	@Inject
	FuelTypeRepository fuelTypeRepository;

	/** The fuel type assembler. */
	@Inject
	FuelTypeAssembler fuelTypeAssembler;

	/** The fuel type factory. */
	@Inject
	FuelTypeFactory fuelTypeFactory;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;

	@Inject
	private FuelTypeService fuleTypeService;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * Gets the carburant data.
	 * 
	 * @param hsr the hsr
	 * @param info the info
	 * @return the carburant data
	 */
	@GET
	@Path("/Carburants")
	public Response getCarburantData() {

		logger.info("To get value for FuelType");
		List<FuelTypeRepresentation> fuelTypeList = fuelTypeFinder.findAllCarburantData();

		logger.info("sending FuelType value to UI");
		return Response.ok(fuelTypeList).build();

	}

	/**
	 * Save carburant data.
	 * 
	 * @param fuelTypeRequestDto the fuel type request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Carburants")
	public Response saveCarburantData(ManageFuelTypeRequestDto fuelTypeRequestDto) {

		logger.info("trying to save data in FuelType table");

		FuelTypeRepresentation fuelTypeRepresentationResponse = fuelTypeRequestDto.getFuelTypeRepresentationList().get(0);
		for (FuelTypeRepresentation fuelTypeRepresentation : fuelTypeRequestDto.getFuelTypeRepresentationList()) {
			FuelType fuelType = fuelTypeFactory.createFuelType();
			fuelTypeAssembler.mergeAggregateWithDto(fuelType, fuelTypeRepresentation);

			FuelType fuelTypeResponse = fuleTypeService.saveFuelType(fuelType);

			if (fuelTypeResponse == null) {
				fuelTypeRepresentation.setDcd(null);
				return Response.ok(fuelTypeRepresentation).build();
			}

			fuelTypeRepresentationResponse = fuelTypeRepresentation;

		}
		logger.info("Sucessfully saved Engine data");
		return Response.ok(fuelTypeRepresentationResponse).build();

	}

	/**
	 * Delete carburant data.
	 * 
	 * @param fuelTypeRequestDto the fuel type request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Carburant")
	public Response deleteCarburantData(ManageFuelTypeRequestDto fuelTypeRequestDto) {

		logger.info("trying to delete data from FuelType table");
		for (FuelTypeRepresentation fuelTypeRepresentation : fuelTypeRequestDto.getFuelTypeRepresentationList()) {
			boolean response = fuleTypeService.deleteFuelType(fuelTypeRepresentation.getEntityId());
			if (!response)
				return Response.status(Response.Status.PRECONDITION_FAILED).build();

		}

		return Response.ok().build();

	}

	/**
	 * Gets the all fuel type data.
	 * 
	 * @return the all fuel type data
	 */
	@GET
	@Path("/fuelTypes")
	public Response getAllFuelTypeData() {

		logger.info("To get value for FuelType");
		List<FuelTypeRepresentation> fuelTypeList = fuelTypeFinder.findAllFuelTypeData();

		logger.info("sending FuelType value to UI");
		return Response.ok(fuelTypeList).build();

	}
}
