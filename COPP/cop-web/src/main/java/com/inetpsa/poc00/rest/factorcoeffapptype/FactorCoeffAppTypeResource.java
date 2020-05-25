package com.inetpsa.poc00.rest.factorcoeffapptype;

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
import com.inetpsa.poc00.application.factorcoeffapptype.FactorCoeffAppTypeService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffAppFactory;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffAppRepository;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffApplicationType;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class FactorCoeffAppTypeResource.
 */
@Path("/factorCoeffApplicationTypeReference")

public class FactorCoeffAppTypeResource {

	/** The factor coeff app type finder. */
	@Inject
	FactorCoeffAppTypeFinder factorCoeffAppTypeFinder;

	/** The fca factory. */
	@Inject
	FactorCoeffAppFactory fcaFactory;

	/** The fca assembler. */
	@Inject
	FactorCoeffAppTypeAssembler fcaAssembler;

	/** The fca repo. */
	@Inject
	FactorCoeffAppRepository fcaRepo;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;
	@Inject
	FactorCoeffAppTypeService factorCoeffAppTypeService;
	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * Gets the all factor coeff application type.
	 *
	 * @param hsr the hsr
	 * @param info the info
	 * @return the all factor coeff application type
	 */
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	@GET
	@Path("/AllFactorCoeffApplicationTypes")
	public Response getAllFactorCoeffApplicationType() {

		List<FactorCoeffAppTypeRepresentation> fcAppTypeList;

		fcAppTypeList = factorCoeffAppTypeFinder.getAllFactorCoeffApplicationType();

		return Response.ok(fcAppTypeList).build();
	}

	/**
	 * Save factor coeff app type.
	 *
	 * @param fcaDto the fca dto
	 * @return the response
	 */
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/FactorCoeffAppTypes")
	public Response saveFactorCoeffAppType(FactorCoeffAppTypeDto fcaDto) {

		logger.info("trying to save data in FactorCoeffAppTypeDto table");
		for (FactorCoeffAppTypeRepresentation factorCoeffApplicationTypeRepresentation : fcaDto.getFactorCoeffAppTypeRepresentationList()) {
			FactorCoeffApplicationType updatedFCA = assembleFromRepresentation(factorCoeffApplicationTypeRepresentation);
			String response = factorCoeffAppTypeService.saveFactorCoeffApplicationType(updatedFCA);
			if (response != ConstantsApp.SUCCESS) {
				fcaAssembler.doAssembleDtoFromAggregate(factorCoeffApplicationTypeRepresentation, updatedFCA);
				factorCoeffApplicationTypeRepresentation.setLabel(null);
				return Response.ok(factorCoeffApplicationTypeRepresentation).build();
			}
		}
		logger.info("Sucessfully saved FactorCoeffAppTypeDto data");
		return Response.ok(fcaDto.getFactorCoeffAppTypeRepresentationList().get(0)).build();

	}

	/**
	 * Assemble from representation.
	 *
	 * @param factorCoeffApplicationTypeRepresentation the factor coeff application type representation
	 * @return the factor coeff application type
	 */
	private FactorCoeffApplicationType assembleFromRepresentation(FactorCoeffAppTypeRepresentation factorCoeffApplicationTypeRepresentation) {
		FactorCoeffApplicationType fca = fcaFactory.createFactorCoefficents();

		fcaAssembler.doMergeAggregateWithDto(fca, factorCoeffApplicationTypeRepresentation);

		return fca;

	}

	/**
	 * Delete factor coeff app type.
	 *
	 * @param entityId the entity id
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/FactorCoeffAppType/{entityId}")
	public Response deleteFactorCoeffAppType(@PathParam("entityId") String entityId) {

		logger.info("trying to delete data from Country table");
		try {
			String result = factorCoeffAppTypeService.deleteFactorCoeffApplicationType(Long.parseLong(entityId));
			if (result != ConstantsApp.SUCCESS) {
				return Response.status(Response.Status.PRECONDITION_FAILED).build();
			}
		} catch (Exception e) {
			logger.error(" Error occured while deleting data ", e);
			return Response.status(Response.Status.PRECONDITION_FAILED).build();
		}
		return Response.ok().build();

	}

}
