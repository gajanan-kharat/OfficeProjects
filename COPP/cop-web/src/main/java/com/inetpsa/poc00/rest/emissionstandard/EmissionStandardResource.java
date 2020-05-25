/*
 * Creation : Apr 7, 2016
 */
package com.inetpsa.poc00.rest.emissionstandard;

import java.net.URISyntaxException;
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
import com.inetpsa.poc00.application.emissionstandard.EmissionStandardService;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.es.EmissionStandardRepository;
import com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoeffLabelFactory;
import com.inetpsa.poc00.emsdepfcl.FactorCoefficentsFinder;
import com.inetpsa.poc00.pollutantorgas.PollutantGasLimitsFinder;
import com.inetpsa.poc00.rest.clasz.ClaszFinder;
import com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionFinder;
import com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataFinder;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class EmissionStandardResource.
 */
@Path("/emissionStandard")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class EmissionStandardResource {

	/** The emission standard factory. */
	@Inject
	private EmissionStandardFactory emissionStandardFactory;

	/** The emission standard assembler. */
	@Inject
	private EmissionStandardAssembler emissionStandardAssembler;

	/** The emission standard repository. */
	@Inject
	private EmissionStandardRepository emissionStandardRepository;

	/** The emission standard service. */
	@Inject
	EmissionStandardService emissionStandardService;

	/** The emission standard finder. */
	@Inject
	private EmissionStandardFinder emissionStandardFinder;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;

	/** The logger. */
	@Logging
	private Logger logger;

	/** The factor coeff label factory. */
	@Inject
	FactorCoeffLabelFactory factorCoeffLabelFactory;

	/** The generic tech data finder. */
	@Inject
	GenericTechnicalDataFinder genericTechDataFinder;

	/** The generic condition finder. */
	@Inject
	GenericTestConditionFinder genericConditionFinder;

	/** The pollutant gas limit finder. */
	@Inject
	PollutantGasLimitsFinder pollutantGasLimitFinder;

	/** The factor coefficents finder. */
	@Inject
	FactorCoefficentsFinder factorCoefficentsFinder;

	/** The clasz finder. */
	@Inject
	ClaszFinder claszFinder;

	/**
	 * Creates the emission standard.
	 * 
	 * @param emissionStandardRepresentation the emission standard representation
	 * @return the response
	 * @throws URISyntaxException the uRI syntax exception
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/EmissionStandard")
	public Response createEmissionStandard(EmissionStandardRepresentation emissionStandardRepresentation) throws URISyntaxException {

		EmissionStandard emissionStandard = emissionStandardFactory.createEmissonStandard();
		emissionStandardAssembler.doMergeAggregateWithDto(emissionStandard, emissionStandardRepresentation);
		EmissionStandard newObject = emissionStandardService.createEmissionStandard(emissionStandard);
		if (newObject != null) {
			EmissionStandardRepresentation newEmissionStandardRepresentation = new EmissionStandardRepresentation();
			emissionStandardAssembler.doAssembleDtoFromAggregate(newEmissionStandardRepresentation, newObject);
			return Response.ok(newEmissionStandardRepresentation).build();
		}
		return Response.status(javax.ws.rs.core.Response.Status.PRECONDITION_FAILED).build();
	}

	/**
	 * Gets the all emission standards.
	 * 
	 * @return the all emission standards
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/AllEmissionStandards")
	public Response getAllEmissionStandards() {

		List<EmissionStandardRepresentation> emissionStandardRepresentationList = emissionStandardFinder.getAllEmissionStandards();

		return Response.ok(emissionStandardRepresentationList).build();

	}

	/**
	 * Gets the valid emission standards.
	 * 
	 * @return the valid emission standards
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/emissionStandardsValid")
	public Response getValidEmissionStandards() {

		List<EmissionStandardRepresentation> emissionStandardRepresentationList = emissionStandardFinder.getAllEmissionStandardsForTVV();

		return Response.ok(emissionStandardRepresentationList).build();

	}

	/**
	 * Creates the new ems version.
	 * 
	 * @param emissionStandard the emission standard
	 * @return the emission standard
	 */
	public EmissionStandard createNewEMSVersion(EmissionStandard emissionStandard) {

		Double version = emissionStandardFinder.getMaxVersionForLabel(emissionStandard.getEsLabel()) + 1;
		emissionStandard.setVersion(version.toString());
		EmissionStandard newOBJ = emissionStandardFactory.createEmissonStandard(emissionStandard.getEsLabel(), emissionStandard.getDescription(), emissionStandard.getVersion());
		newOBJ.setCategories(emissionStandard.getCategories());
		newOBJ.setFuelInjectionTypes(emissionStandard.getFuelInjectionTypes());
		newOBJ.setStatus(emissionStandard.getStatus());
		newOBJ.setFuels(emissionStandard.getFuels());
		newOBJ.setVehicleTechnologySet(emissionStandard.getVehicleTechnologySet());

		return newOBJ;
	}

	/**
	 * Gets the emission standards with label.
	 * 
	 * @param label the label
	 * @return the emission standards with label
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/EmissionStandards/{label}")
	public Response getEmissionStandardsWithLabel(@PathParam("label") String label) {

		List<EmissionStandardRepresentation> esList = emissionStandardFinder.getEmissionStandardsWithLabel(label);

		return Response.ok(esList).build();
	}

	/**
	 * Copy emisssion standard.
	 * 
	 * @param emissionStandard the emission standard
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/copy")
	public Response copyEmisssionStandard(EmissionStandardRepresentation emissionStandard) {

		logger.info("Copy Started for Emission Standard");

		EmissionStandard entityObj = emissionStandardFactory.createEmissonStandard();
		emissionStandardAssembler.doMergeAggregateWithDto(entityObj, emissionStandard);
		EmissionStandard newOBJ = emissionStandardService.copyEmissionStandard(entityObj);

		logger.info("Saved Copied Emission Standard");

		EmissionStandardRepresentation emissionStandardRepresentation = new EmissionStandardRepresentation();
		emissionStandardAssembler.doAssembleDtoFromAggregate(emissionStandardRepresentation, newOBJ);

		return Response.ok(emissionStandardRepresentation).build();
	}

	/**
	 * Change emission standard status.
	 * 
	 * @param emissionStandardRepresentation the emission standard representation
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/EmissionStandardStatus")
	public Response changeEmissionStandardStatus(EmissionStandardRepresentation emissionStandardRepresentation) {
		EmissionStandard emissionStandard = emissionStandardService.changeEmissionStandardVersion(emissionStandardRepresentation.getEntityId());
		EmissionStandardRepresentation newEmissionStandardRepresentation = new EmissionStandardRepresentation();
		emissionStandardAssembler.doAssembleDtoFromAggregate(newEmissionStandardRepresentation, emissionStandard);
		return Response.ok(newEmissionStandardRepresentation).build();
	}

	/**
	 * Copy emisssion standard.
	 * 
	 * @param emissionStandard the emission standard
	 * @return the emission standard
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete")
	public Response deleteEmisssionStandard(EmissionStandardRepresentation emissionStandard) {
		EmissionStandard emissionStandardObj = emissionStandardRepository.load(emissionStandard.getEntityId());

		if (emissionStandardObj.getStatus().getLabel().equalsIgnoreCase(Constants.VALID))
			return Response.status(javax.ws.rs.core.Response.Status.PRECONDITION_FAILED).build();

		emissionStandardRepository.delete(emissionStandard.getEntityId());
		return Response.ok().build();
	}

	/**
	 * Gets the reglementation data.
	 * 
	 * @param hsr the hsr
	 * @param info the info
	 * @return the reglementation data
	 */
	@GET
	@Path("/ReglementationList")
	public Response getReglementationData() {

		List<EmissionStandardRepresentation> emsList;

		logger.info("To get value from EmissionStandard");
		emsList = emissionStandardFinder.findAllReglementationData();

		return Response.ok(emsList).build();
	}

	/**
	 * Save reglementation data.
	 * 
	 * @param emissionStandardRequestDto the emission standard request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/ReglementationList")
	public Response saveReglementationData(ManageEmissionStandardRequestDto emissionStandardRequestDto) {

		logger.info("trying to save data in EmissionStandard table");

		EmissionStandardRepresentation emissionStandardRepresentationResponse = emissionStandardRequestDto.getEmisssionStandardRepresentationList().get(0);
		for (EmissionStandardRepresentation emissionStandardRepresentation : emissionStandardRequestDto.getEmisssionStandardRepresentationList()) {

			EmissionStandard emissionStandard = emissionStandardFactory.createEmissonStandard();
			emissionStandardAssembler.doMergeAggregateWithDto(emissionStandard, emissionStandardRepresentation);
			EmissionStandard emsResponse = emissionStandardService.saveEmissionStandard(emissionStandard);

			if (emsResponse == null) {
				emissionStandardRepresentation.setReglementationDKA(null);
				return Response.ok(emissionStandardRepresentation).build();
			}
			emissionStandardRepresentationResponse = emissionStandardRepresentation;
		}
		logger.info("Sucessfully saved EmissionStandard data");
		return Response.ok(emissionStandardRepresentationResponse).build();

	}

	/**
	 * Delete reglementation data.
	 * 
	 * @param emissionStandardRequestDto the emission standard request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Reglementation")
	public Response deleteReglementationData(ManageEmissionStandardRequestDto emissionStandardRequestDto) {

		logger.info("trying to delete data from EmissionStandard table");
		for (EmissionStandardRepresentation emissionStandardRepresentation : emissionStandardRequestDto.getEmisssionStandardRepresentationList()) {
			boolean deleted = emissionStandardService.deleteEmissionStandard(emissionStandardRepresentation.getEntityId());
			if (!deleted) {
				return Response.status(Response.Status.PRECONDITION_FAILED).build();
			}
		}

		return Response.ok().build();

	}

	/**
	 * Gets the all emission standard names.
	 * 
	 * @return the all emission standard names
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/emissionStandardNames")
	public Response getAllEmissionStandardNames() {

		List<String> emissionStandardRepresentationList = emissionStandardFinder.getAllEmissionStandardNames();

		return Response.ok(emissionStandardRepresentationList).build();

	}

	/**
	 * Emission standard data.
	 * 
	 * @param hsr the hsr
	 * @param info the info
	 * @return the response
	 */
	@GET
	@Path("/emissionStandardData")
	public Response emissionStandardData() {

		List<EmissionStandardRepresentation> emissionStandardList;

		logger.info("To get list_emissionStandardData");
		emissionStandardList = emissionStandardFinder.getAllEmissionStandards();

		logger.info("sending EmissionStandard value to UI");
		return Response.ok(emissionStandardList).build();
	}

	/**
	 * Gets the all emission standards for rg.
	 * 
	 * @param hsr the hsr
	 * @param info the info
	 * @return the all emission standards for rg
	 */
	@GET
	@Path("/EmissionStandardsForRG")
	public Response getAllEmissionStandardsForRG() {

		List<EmissionStandardRepresentation> emissionStandardList;

		emissionStandardList = emissionStandardFinder.getEmissionStandardForRG();

		return Response.ok(emissionStandardList).build();
	}

}
