package com.inetpsa.poc00.rest.carbrand;

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
import com.inetpsa.poc00.application.carbrand.CarBrandService;
import com.inetpsa.poc00.domain.carbrand.CarBrand;
import com.inetpsa.poc00.domain.carbrand.CarBrandFactory;
import com.inetpsa.poc00.domain.carbrand.CarBrandRepository;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleRepository;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class CarBrandResource.
 */
@Path("/ConstructeurReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class CarBrandResource {

	/** The tvv rule repository. */
	@Inject
	private GenomeTVVRuleRepository tvvRuleRepository;

	/** The car brand finder. */
	@Inject
	CarBrandFinder carBrandFinder;

	/** The car bran factory. */
	@Inject
	CarBrandFactory carBranFactory;

	/** The car brand assembler. */
	@Inject
	CarBrandAssembler carBrandAssembler;

	/** The car brand repository. */
	@Inject
	CarBrandRepository carBrandRepository;

	@Inject
	CarBrandService carBrandService;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * Gets the constructeur data.
	 *
	 * @return the constructeur data
	 */
	@GET
	@Path("/Constructeurs")
	public Response getConstructeurData() {

		List<CarBrandRepresentation> carBrandList;

		logger.info("To get value from CarBrand");
		carBrandList = carBrandFinder.findAllConstructeurData();

		logger.info("sending CarBrand value to UI");
		return Response.ok(carBrandList).build();
	}

	/**
	 * Save constructeur data.
	 * 
	 * @param carBrandRequestDto the car brand request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Constructeurs")
	public Response saveConstructeurData(ManageCarBrandRequestDto carBrandRequestDto) {

		logger.info("trying to save data in CarBrand table");

		CarBrandRepresentation carBrandRepresentationResponse = carBrandRequestDto.getCarBrandRepresentationList().get(0);
		for (CarBrandRepresentation carBrandRepresentation : carBrandRequestDto.getCarBrandRepresentationList()) {

			CarBrand carBrand = carBranFactory.createCarBrand();
			carBrandAssembler.mergeAggregateWithDto(carBrand, carBrandRepresentation);

			CarBrand carBrandRespose = carBrandService.saveCarBrand(carBrand);
			if (carBrandRespose == null) {
				carBrandRepresentation.setConstructeurBOB(null);
				return Response.ok(carBrandRepresentation).build();
			}
			carBrandRepresentationResponse = carBrandRepresentation;

		}
		logger.info("Sucessfully saved CarBrand data");
		return Response.ok(carBrandRepresentationResponse).build();

	}

	/**
	 * Delete constructeur data.
	 * 
	 * @param carBrandRequestDto the car brand request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Constructeur")
	public Response deleteConstructeurData(ManageCarBrandRequestDto carBrandRequestDto) {

		logger.info("trying to delete data from CarBrand table");

		for (CarBrandRepresentation carBrandRepresentation : carBrandRequestDto.getCarBrandRepresentationList()) {
			boolean deleted = carBrandService.deleteCarBrand(carBrandRepresentation.getEntityId());
			if (!deleted)
				return Response.status(Response.Status.PRECONDITION_FAILED).build();
		}
		return Response.ok().build();

	}

	/**
	 * Gets the all car brand data.
	 * 
	 * @return the all car brand data
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/carBrands")
	public Response getAllCarBrandData() {

		List<CarBrandRepresentation> carBrandList;

		logger.info("To get value from CarBrand");
		carBrandList = carBrandFinder.findAllCarBrandData();

		logger.info("sending CarBrand value to UI");
		return Response.ok(carBrandList).build();
	}

}
