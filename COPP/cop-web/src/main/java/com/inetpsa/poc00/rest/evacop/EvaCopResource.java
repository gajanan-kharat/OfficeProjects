package com.inetpsa.poc00.rest.evacop;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
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
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileFactory;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatus;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatusRepository;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class ClaszResource.
 */
@Path("/EvaCopFile")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class EvaCopResource {

	/** The trace resource. */
	@Inject
	private TraceabilityResource traceResource;
	@Inject
	private VehicleFileFactory vFileFactory;

	/** The vehicle file status repo. */
	@Inject
	private VehicleFileStatusRepository vehicleFileStatusRepo;

	/** The vehicle file repo. */
	@Inject
	VehicleFileRepository vehicleFileRepo;

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(EvaCopResource.class);

	/**
	 * Save clasz.
	 *
	 * @param claszDto the clasz dto
	 * @return the response
	 */

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/EvaCopVehicleFile/{entityId}/{comment}/{status}")
	public Response setEvaCopVehicleFileHistory(@PathParam("entityId") String vehicleFileId, @PathParam("comment") String comment, @PathParam("status") String vehicleFileStatus) {
		if (vehicleFileId != null && vehicleFileId.length() > 0) {
			long vFileId = Long.parseLong(vehicleFileId);
			VehicleFile vehicleFile = vehicleFileRepo.load(vFileId);
			traceResource.saveVehicleFileHistory(vehicleFile, comment, vehicleFileStatus);
		}
		return Response.ok().build();

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/EvaCopVehicleFileStatusUpdate/{entityId}/{status}")
	public Response updateEvaCopVehiclefile(@PathParam("entityId") String vehicleFileId, @PathParam("status") String vehicleFileStatus) {
				if (vehicleFileId != null && vehicleFileId.length() > 0) {
			long vFileId = Long.parseLong(vehicleFileId);
			logger.info("Changing vehicle File Status, Vehicle File Id : {}, VehicleFileStatus : {}", vehicleFileId, vehicleFileStatus);

			VehicleFile vehicleFile = vehicleFileRepo.load(vFileId);
			VehicleFileStatus vfStatus = vehicleFileStatusRepo.getVehicleFileStatusbyLabel(vehicleFileStatus);
			VehicleFileStatus oldStatus = vehicleFile.getVehicleFileStatus();
			
			vehicleFile.setVehicleFileStatus(vfStatus);
			vehicleFile.setPreviousVFStatus(oldStatus);
			traceResource.saveVehicleFileHistory(vehicleFile, null, vfStatus.getLabel());

			logger.info("Vehicle File status has been changed : {}", vehicleFileStatus);

		
		}
		return Response.ok().build();

	}

}
