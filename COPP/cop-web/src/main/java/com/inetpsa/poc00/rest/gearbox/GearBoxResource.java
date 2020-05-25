package com.inetpsa.poc00.rest.gearbox;

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
import com.inetpsa.poc00.application.gearbox.GearBoxService;
import com.inetpsa.poc00.domain.gearbox.GearBox;
import com.inetpsa.poc00.domain.gearbox.GearBoxFactory;
import com.inetpsa.poc00.domain.gearbox.GearBoxRepository;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class GearBoxResource.
 */
@Path("/BolteReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class GearBoxResource {

	/** The gear box finder. */
	@Inject
	GearBoxFinder gearBoxFinder;

	/** The gear box factory. */
	@Inject
	GearBoxFactory gearBoxFactory;

	/** The gear box assembler. */
	@Inject
	GearBoxAssembler gearBoxAssembler;

	/** The gear box repository. */
	@Inject
	GearBoxRepository gearBoxRepository;

	@Inject
	GearBoxService gearBoxService;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * Gets the bolte data.
	 *
	 * @param hsr the hsr
	 * @param info the info
	 * @return the bolte data
	 */
	@GET
	@Path("/BolteList")
	public Response getBolteData() {

		List<GearBoxRepresentation> gearBoxDataList;

		gearBoxDataList = gearBoxFinder.findAllGearBoxData();

		return Response.ok(gearBoxDataList).build();
	}

	/**
	 * Save bolte data.
	 *
	 * @param gearBoxRequestDto the gear box request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/BolteList")
	public Response saveBolteData(ManageGearBoxRequestDto gearBoxRequestDto) {

		logger.info("trying to save data in GearBox table");
		GearBoxRepresentation gearBoxRepresentationResponse = gearBoxRequestDto.getGearBoxRepresentationList().get(0);
		for (GearBoxRepresentation gearBoxRepresentation : gearBoxRequestDto.getGearBoxRepresentationList()) {

			GearBox gearBox = gearBoxFactory.createGearBox();
			gearBoxAssembler.mergeAggregateWithDto(gearBox, gearBoxRepresentation);
			GearBox gearBoxResponse = gearBoxService.saveGearBox(gearBox);
			if (gearBoxResponse == null) {
				gearBoxRepresentation.setB0gValue(null);
				return Response.ok(gearBoxRepresentation).build();
			}
			gearBoxRepresentationResponse = gearBoxRepresentation;
		}
		logger.info("Sucessfully saved GearBox data");
		return Response.ok(gearBoxRepresentationResponse).build();
	}

	/**
	 * Delete bolte data.
	 *
	 * @param gearBoxRequestDto the gear box request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Bolte")
	public Response deleteBolteData(ManageGearBoxRequestDto gearBoxRequestDto) {

		logger.info("trying to delete data from GearBox table");

		for (GearBoxRepresentation gearBoxRepresentation : gearBoxRequestDto.getGearBoxRepresentationList()) {

			boolean deleted = gearBoxService.deleteGearBox(gearBoxRepresentation.getGearboxEntity());
			if (!deleted)
				return Response.status(Response.Status.PRECONDITION_FAILED).build();
		}

		return Response.ok().build();

	}

	/**
	 * Gets the all gear box data.
	 *
	 * @return the all gear box data
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/gearBoxes")
	public Response getAllGearBoxData() {

		List<GearBoxRepresentation> gearBoxList;

		gearBoxList = gearBoxFinder.getAllGearBoxData();

		return Response.ok(gearBoxList).build();
	}

	/**
	 * Gets the all gear box names.
	 *
	 * @return the all gear box names
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/gearBoxNames")
	public Response getAllGearBoxNames() {

		List<String> gearBoxList;

		gearBoxList = gearBoxFinder.getAllGearBoxNames();

		return Response.ok(gearBoxList).build();
	}

}
