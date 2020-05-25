package com.inetpsa.poc00.rest.vehiclecoefficent;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
import com.inetpsa.poc00.application.vehiclecoefficent.VehicleCoeffService;
import com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoeffFactory;
import com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoeffRepository;
import com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoefficents;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class VehicleCoeffResource.
 */
@Path("/vehicleCoefficentsReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class VehicleCoeffResource {

	/** The vehicle coeff finder. */
	@Inject
	VehicleCoeffFinder vehicleCoeffFinder;

	/** The v co factory. */
	@Inject
	VehicleCoeffFactory vCoFactory;

	/** The v co assembler. */
	@Inject
	VehicleCoeffAssembler vCoAssembler;

	/** The v co repo. */
	@Inject
	VehicleCoeffRepository vCoRepo;

	/** The vehicle coeff service. */
	@Inject
	VehicleCoeffService vehicleCoeffService;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(VehicleCoeffResource.class);

	/**
	 * Gets the all vehicle coefficents.
	 * 
	 * @param hsr the hsr
	 * @param info the info
	 * @return the all vehicle coefficents
	 */
	@GET
	@Path("/AllVehicleCoefficents")
	public Response getAllVehicleCoefficents(@Context HttpServletResponse hsr, @Context UriInfo info) {

		List<VehicleCoeffRepresentation> vehicleCoefficentList;

		vehicleCoefficentList = vehicleCoeffFinder.getAllVehicleCoefficents();

		return Response.ok(vehicleCoefficentList).build();
	}

	/**
	 * Save vehicle coefficents.
	 * 
	 * @param vCoDto the v co dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/VehicleCoefficents")
	public Response saveVehicleCoefficents(VehicleCoeffDto vCoDto) {

		for (VehicleCoeffRepresentation objectToSave : vCoDto.getVehicleCoeffRepresentationList()) {
			VehicleCoefficents vCo = vCoFactory.createVehicleCoefficents();
			vCoAssembler.doMergeAggregateWithDto(vCo, objectToSave);
			vehicleCoeffService.saveVehicleCoefficents(vCo);
		}
		return Response.ok().build();
	}

}
