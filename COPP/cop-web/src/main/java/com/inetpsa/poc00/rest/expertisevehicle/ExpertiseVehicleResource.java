package com.inetpsa.poc00.rest.expertisevehicle;

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
import com.inetpsa.poc00.domain.expertiseresult.ExpertiseResult;
import com.inetpsa.poc00.domain.expertiseresult.ExpertiseResultFactory;
import com.inetpsa.poc00.domain.expertiseresult.ExpertiseResultRepository;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileFactory;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatus;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatusRepository;
import com.inetpsa.poc00.traceability.TraceabilityResource;

// TODO: Auto-generated Javadoc
/**
 * The Class ClaszResource.
 */
@Path("/VehicleExpertise")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class ExpertiseVehicleResource {

	/** The trace resource. */
	@Inject
	private TraceabilityResource traceResource;

	/** The v file factory. */
	@Inject
	private VehicleFileFactory vFileFactory;

	/** The vehicle file status repo. */
	@Inject
	private VehicleFileStatusRepository vehicleFileStatusRepo;

	/** The vehicle file repo. */
	@Inject
	VehicleFileRepository vehicleFileRepo;

	/** The expertise result repository. */
	@Inject
	ExpertiseResultRepository expertiseResultRepository;

	/** The expertise result factory. */
	@Inject
	ExpertiseResultFactory expertiseResultFactory;

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(ExpertiseVehicleResource.class);

	/**
	 * Save clasz.
	 *
	 * @param comment the comment
	 * @return the response
	 */

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/vehicleComplete/{entityId}/{comment}")
	public Response setVehicleComplete(@PathParam("entityId") String vehicleFileId, @PathParam("comment") String comment) {
		logger.debug("in setVehicleComplete ");
		if (vehicleFileId != null && vehicleFileId.length() > 0) {
			long vFileId = Long.parseLong(vehicleFileId);
			VehicleFile vehicleFile = vehicleFileRepo.load(vFileId);
			VehicleFileStatus oldStatus = vehicleFile.getPreviousVFStatus();
			vehicleFile.setVehicleFileStatus(oldStatus);
			// traceResource.saveVehicleFileHistory(vehicleFile, comment, vehicleFileStatus);
			ExpertiseResult expertiseResult = expertiseResultFactory.createExpertiseResult();
			expertiseResult.setComment(comment);
			expertiseResultRepository.save(expertiseResult);
		}
		return Response.ok().build();

	}

	/*@POST
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
	
	}*/

}
