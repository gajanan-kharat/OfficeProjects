/*
 * Creation : Sep 29, 2016
 */
package com.inetpsa.poc00.rest.vehiclefile;

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
import javax.ws.rs.core.Response.Status;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.security.SecuritySupport;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.vehiclefile.VehicleFileService;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;

/**
 * The Class VehicleFileResource.
 */
@Path("/return")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class ReturnVehicleResource {

	/** The vehicle file repo. */
	@Inject
	private VehicleFileRepository vehicleFileRepo;

	/** The vehicle file finder. */
	@Inject
	private VehicleFileFinder vehicleFileFinder;

	/** The vf service. */
	@Inject
	private VehicleFileService vfService;

	/** The security support. */
	@Inject
	SecuritySupport securitySupport;

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(ReturnVehicleResource.class);

	/*
	 * To return Filter values to view
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/returnVehicles")
	public Response getFilterValuesForVF() {

		List<VehicleFileRepresentation> vehicleFiles = vehicleFileFinder.getAllReturedVehicleFiles();

		if (vehicleFiles != null)
			logger.info("Number of Vehicle Files with Returned Status : {}", vehicleFiles.size());

		return Response.ok(vehicleFiles).build();
	}

	/**
	 * Removes the vehicle from basket.
	 * 
	 * @param vehicleFileId the vehicle file id
	 * @return the response
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/returnVehicle/{vehicleFileId}")
	public Response removeVehicleFromBasket(@PathParam("vehicleFileId") long vehicleFileId) {

		VehicleFile vehicleFile = vfService.returnVehicleFile(vehicleFileId);

		if (vehicleFile != null) {
			return Response.ok(Status.OK).build();
		}

		return Response.status(Status.PRECONDITION_FAILED).build();

	}

}
