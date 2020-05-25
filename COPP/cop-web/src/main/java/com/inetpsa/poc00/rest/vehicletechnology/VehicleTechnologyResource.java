package com.inetpsa.poc00.rest.vehicletechnology;

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
import com.inetpsa.poc00.application.vehicletechnology.VehicleTechnologyService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechFactory;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechRepository;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class VehicleTechnologyResource.
 */
@Path("/vehicleTechnologyReference")

public class VehicleTechnologyResource {

	/** The vehicle technology finder. */
	@Inject
	VehicleTechnologyFinder vehicleTechnologyFinder;

	/** The veh tech factory. */
	@Inject
	VehicleTechFactory vehTechFactory;

	/** The veh tech assembler. */
	@Inject
	VehicleTechnologyAssembler vehTechAssembler;

	/** The veh tech repo. */
	@Inject
	VehicleTechRepository vehTechRepo;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;

	/** The vehicle technology service. */
	@Inject
	VehicleTechnologyService vehicleTechnologyService;
	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * Gets the all vehicle technology.
	 *
	 * @return the all vehicle technology
	 */
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	@GET
	@Path("/AllVehicleTechnologies")
	public Response getAllVehicleTechnology() {

		List<VehicleTechnologyRepresentation> vehicleTechnologyList;

		vehicleTechnologyList = vehicleTechnologyFinder.getAllVehicleTechnologies();

		return Response.ok(vehicleTechnologyList).build();
	}

	/**
	 * Save vehicle technology.
	 * 
	 * @param vehTechDto the veh tech dto
	 * @return the response
	 */
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/VehicleTechnologies")
	public Response saveVehicleTechnology(VehicleTechnologyDto vehTechDto) {

		logger.info("trying to save data in TypeApprovalArea table");
		for (VehicleTechnologyRepresentation vehicleTechnologyRepresentation : vehTechDto.getVechicleTechnologyRepresentationList()) {
			VehicleTechnology vehicleTechnology = assembleFromRepresentation(vehicleTechnologyRepresentation);
			String response = vehicleTechnologyService.saveVehicleTechnology(vehicleTechnology);
			if (response != ConstantsApp.SUCCESS) {
				vehTechAssembler.doAssembleDtoFromAggregate(vehicleTechnologyRepresentation, vehicleTechnology);
				vehicleTechnologyRepresentation.setLabel(null);
				return Response.ok(vehicleTechnologyRepresentation).build();
			}
		}
		logger.info("Sucessfully saved TypeApprovalArea data");
		return Response.ok(vehTechDto.getVechicleTechnologyRepresentationList().get(0)).build();

	}

	/**
	 * Assemble from representation.
	 * 
	 * @param vehicleTechnologyRepresentation the vehicle technology representation
	 * @return the vehicle technology
	 */
	private VehicleTechnology assembleFromRepresentation(VehicleTechnologyRepresentation vehicleTechnologyRepresentation) {
		VehicleTechnology vehTech = vehTechFactory.createVehTechnology();

		vehTechAssembler.mergeAggregateWithDto(vehTech, vehicleTechnologyRepresentation);

		return vehTech;

	}

	/**
	 * Delete vehicle technology.
	 * 
	 * @param entityId the entity id
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/VehicleTechnology/{entityId}")
	public Response deleteVehicleTechnology(@PathParam("entityId") String entityId) {

		logger.info("trying to delete data from VehicleTechnology table");
		try {
			String result = vehicleTechnologyService.deleteVehicleTechnology(Long.parseLong(entityId));
			if (result != ConstantsApp.SUCCESS) {
				return Response.status(Response.Status.PRECONDITION_FAILED).build();
			}
		} catch (Exception e) {

			logger.error(" Error occured while deleting data ", e);
			return Response.status(Response.Status.PRECONDITION_FAILED).build();
		}
		return Response.ok().build();

	}

	/**
	 * Gets the vehicle technologies.
	 * 
	 * @return the vehicle technologies
	 */
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/AllVehicleTechnologies")
	public Response getVehicleTechnologies() {
		List<VehicleTechnologyRepresentation> listToReturn = vehicleTechnologyFinder.getAllVehicleTechnologies();

		return Response.ok(listToReturn).build();

	}

	/**
	 * Gets the all v technologies for es.
	 * 
	 * @param emsId the ems id
	 * @return the all v technologies for es
	 */
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/AllVehicleTechnologies/{emsId}")
	public Response getAllVTechnologiesForES(@PathParam("emsId") String emsId) {
		List<VehicleTechnologyRepresentation> listToReturn = null;
		if (emsId != null) {
			long id = Long.parseLong(emsId);
			listToReturn = vehicleTechnologyFinder.getAllVTechnologiesForES(id);
		}

		return Response.ok(listToReturn).build();

	}

}
