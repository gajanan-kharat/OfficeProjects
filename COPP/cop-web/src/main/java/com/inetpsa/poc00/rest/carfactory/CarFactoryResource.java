package com.inetpsa.poc00.rest.carfactory;

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
import com.inetpsa.poc00.application.carfactory.CarFactoryService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.factory.CarFactory;
import com.inetpsa.poc00.domain.factory.CarFactoryObjectCreation;
import com.inetpsa.poc00.domain.factory.CarFactoryRepository;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class CarFactoryResource.
 */
@Path("/UsineReference")

public class CarFactoryResource {

	/** The car factory finder. */
	@Inject
	CarFactoryFinder carFactoryFinder;

	/** The car factory object. */
	@Inject
	CarFactoryObjectCreation carFactoryObject;

	/** The car factory assembler. */
	@Inject
	CarFactoryAssembler carFactoryAssembler;

	/** The car factory repository. */
	@Inject
	CarFactoryRepository carFactoryRepository;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;
	/** The CarFactoryService. */
	@Inject
	CarFactoryService carFactoryService;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * Gets the usine data.
	 * 
	 * @param hsr the hsr
	 * @param info the info
	 * @return the usine data
	 */
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	@GET

	@Path("/Factories")
	public Response getUsineData() {
		List<CarFactoryRepresentation> carFactoryList;
		logger.info("To get value from CarFactory");
		carFactoryList = carFactoryFinder.findAllUsineData();
		logger.info("sending CarFactory value to UI");
		return Response.ok(carFactoryList).build();
	}

	/**
	 * Save usine data.
	 * 
	 * @param carFactoryRequestDto the car factory request dto
	 * @return the response
	 */
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	@POST

	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Factories")
	public Response saveUsineData(ManageCarFactoryRequestDto carFactoryRequestDto) {

		logger.info("trying to save data in Carfactory table");
		for (CarFactoryRepresentation carFactoryRepresentation : carFactoryRequestDto.getCarFactoryRepresentationList()) {
			CarFactory carFactory = assembleFromRepresentation(carFactoryRepresentation);
			String response = carFactoryService.saveFactoryObject(carFactory);
			if (response != ConstantsApp.SUCCESS) {
				carFactoryAssembler.doAssembleDtoFromAggregate(carFactoryRepresentation, carFactory);
				carFactoryRepresentation.setDuplicate(true);
				return Response.ok(carFactoryRepresentation).build();
			}
		}
		logger.info("Sucessfully saved Carfactory data");
		return Response.ok(carFactoryRequestDto.getCarFactoryRepresentationList().get(0)).build();

	}

	/**
	 * Update car factory.
	 * 
	 * @param carFactoryRepresentation the car factory representation
	 * @return the car factory
	 */
	private CarFactory assembleFromRepresentation(CarFactoryRepresentation carFactoryRepresentation) {
		CarFactory carFactory = carFactoryObject.createCarFactoryObject();
		carFactoryAssembler.mergeAggregateWithDto(carFactory, carFactoryRepresentation);
		return carFactory;

	}

	/**
	 * Delete usine data.
	 * 
	 * @param carFactoryRequestDto the car factory request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Factory")
	public Response deleteUsineData(ManageCarFactoryRequestDto carFactoryRequestDto) {

		try {
			logger.info("trying to delete data from CarFactory table");
			for (CarFactoryRepresentation carFactoryRepresentation : carFactoryRequestDto.getCarFactoryRepresentationList()) {

				String result = carFactoryService.deleteCarFactory(carFactoryRepresentation.getEntityId());
				if (result != ConstantsApp.SUCCESS) {
					return Response.status(Response.Status.PRECONDITION_FAILED).build();
				}

			}

			return Response.ok().build();
		} catch (Exception e) {
			logger.error(" Error occured while deleting data ", e);
			return Response.status(Response.Status.PRECONDITION_FAILED).build();
		}

	}
}
