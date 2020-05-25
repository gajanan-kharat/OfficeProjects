package com.inetpsa.poc00.rest.tirebrand;

import java.util.ArrayList;
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
import com.inetpsa.poc00.application.tirebrand.TireBrandService;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.tirebrand.TireBrand;
import com.inetpsa.poc00.domain.tirebrand.TireBrandFactory;
import com.inetpsa.poc00.domain.tirebrand.TireBrandRepository;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class tireBrandResource.
 */
@Path("/tireBrandReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class TireBrandResource {

	/** The tire brand finder. */
	@Inject
	TireBrandFinder tireBrandFinder;

	/** The tire brand assembler. */
	@Inject
	TireBrandAssembler tireBrandAssembler;

	/** The tire brand repository. */
	@Inject
	TireBrandRepository tireBrandRepository;
	@Inject
	TireBrandService tireBrandService;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * Gets the all tire brands.
	 *
	 * @param httpServletResponse the http servlet response
	 * @param uriInfo the uri info
	 * @return the all tire brands
	 */
	@GET
	@Path("/AllTireBrands")
	public Response getAllTireBrands() {

		List<TireBrandRepresentation> tireBrandRepresentationsList;
		logger.info("To get value from tireBrand");
		List<TireBrand> tireBrandEntityList = tireBrandFinder.getAllTireBrands();
		tireBrandRepresentationsList = doAssembleDtoFromAggregate(tireBrandEntityList);
		logger.info("sending TireBrands value to UI");
		return Response.ok(tireBrandRepresentationsList).build();

	}

	/**
	 * Save tire brand.
	 *
	 * @param manageTireBrandRequestDto the manage tire brand request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/TireBrands")
	public Response saveTireBrand(ManageTireBrandRequestDto manageTireBrandRequestDto) {

		TireBrandRepresentation tireBrandRepresentationResponse = null;
		for (TireBrandRepresentation tireBrandRepresentation : manageTireBrandRequestDto.getTireBrandRepresentationsList()) {

			List<TireBrandRepresentation> tireBrandDataList = doAssembleDtoFromAggregate(tireBrandFinder.getTireBrandByLabel(tireBrandRepresentation.getLabel()));

			if (!tireBrandDataList.isEmpty()) {

				if (tireBrandDataList.get(0).getEntityId().equals(tireBrandRepresentation.getEntityId())) {
					// update
					TireBrand oldTBD = tireBrandRepository.load(tireBrandRepresentation.getEntityId());
					TireBrand updatedTBD = assembleFromRepresentation(tireBrandRepresentation);
					traceResource.historyForUpdate(oldTBD, updatedTBD, Constants.SPECIFICCOP_SCREEN_ID);
					updatedTBD = tireBrandService.saveTireBrand(updatedTBD);
					if (updatedTBD != null)
						tireBrandRepresentationResponse = tireBrandRepresentation;
					else
						logger.info("unablr to updated TireBrand data");
				} else {
					// error
					logger.info("duplicate TireBrand data");
					tireBrandRepresentation.setLabel(null);
					return Response.ok(tireBrandRepresentation).build();
				}
			} else if (tireBrandRepresentation.getEntityId() == null) {

				// save

				TireBrand updatedTBD = assembleFromRepresentation(tireBrandRepresentation);
				updatedTBD = tireBrandService.saveTireBrand(updatedTBD);
				if (updatedTBD != null) {
					logger.info("******************************************** Saving History for TireBrand, Id : " + updatedTBD.getEntityId());
					traceResource.historyForSave(updatedTBD, Constants.SPECIFICCOP_SCREEN_ID);
					tireBrandRepresentationResponse = tireBrandRepresentation;
				} else
					logger.info("unable to saved TireBrand data");

			} else {
				// update

				TireBrand oldTBD = tireBrandRepository.load(tireBrandRepresentation.getEntityId());
				TireBrand updatedTBD = assembleFromRepresentation(tireBrandRepresentation);
				updatedTBD = tireBrandService.saveTireBrand(updatedTBD);
				if (updatedTBD != null) {

					traceResource.historyForUpdate(oldTBD, updatedTBD, Constants.SPECIFICCOP_SCREEN_ID);
					tireBrandRepresentationResponse = tireBrandRepresentation;
				} else
					logger.info("unable to update TireBrand data");

			}

		}
		logger.info("Sucessfully saved TireBrand data");
		return Response.ok(tireBrandRepresentationResponse).build();

	}

	/**
	 * Delete tire brand.
	 *
	 * @param entityId the entity id
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/TireBrand/{entityId}")
	public Response deleteTireBrand(@PathParam("entityId") String entityId) {

		Long id = Long.parseLong(entityId);
		TireBrand objToDelete = tireBrandRepository.load(id);
		int response = tireBrandService.deleteTireBrand(id);
		if (response != -1) {
			traceResource.historyForDelete(objToDelete, Constants.SPECIFICCOP_SCREEN_ID);
			return Response.ok().build();
		}

		logger.error("Error occured while deleting data ");
		return Response.status(Response.Status.PRECONDITION_FAILED).build();

	}

	/**
	 * Assemble from representation.
	 *
	 * @param tireBrandRepresentation the tire brand representation
	 * @return the tire brand
	 */

	private TireBrand assembleFromRepresentation(TireBrandRepresentation tireBrandRepresentation) {
		TireBrand targetTireBrand = TireBrandFactory.createTireBrand();
		tireBrandAssembler.doMergeAggregateWithDto(targetTireBrand, tireBrandRepresentation);
		return targetTireBrand;

	}

	private List<TireBrandRepresentation> doAssembleDtoFromAggregate(List<TireBrand> sourceList) {
		List<TireBrandRepresentation> targetList = new ArrayList<TireBrandRepresentation>();

		for (TireBrand tb : sourceList) {
			targetList.add(tireBrandAssembler.assembleDtoFromAggregate(tb));
		}
		return targetList;

	}

}
