/*
 * Creation : May 2, 2016
 */
package com.inetpsa.poc00.pollutantorgas;

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
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimitFactory;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimitRepository;

/**
 * The Class PollutantGasLimitResource.
 */

@Path("/pollutantGasLimit")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class PollutantGasLimitResource {

	/** The pollutant gas limit factory. */
	@Inject
	private PollutantGasLimitFactory pollutantGasLimitFactory;

	/** The pollutant gas limit finder. */
	@Inject
	private PollutantGasLimitsFinder pollutantGasLimitFinder;

	/** The pollutant gas limit assembler. */

	@Inject
	private PollutantGasLimitAssembler pollutantGasLimitAssembler;

	/** The pollutant gas limit repository. */

	@Inject
	PollutantGasLimitRepository pollutantGasLimitRepository;

	/**
	 * Update pollutant limit mandatory.
	 * 
	 * @param requestDto the request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updatePollutantLimitMandatory")
	public Response updatePollutantLimitMandatory(ManagePollutantGasLimitRequestDto requestDto) {

		List<PollutantGasLimitRepresentation> listToReturn = new ArrayList<PollutantGasLimitRepresentation>();
		List<PollutantGasLimitRepresentation> genericDataList = requestDto.getPollutantLimitList();
		for (PollutantGasLimitRepresentation dataRepresentation : genericDataList) {
			PollutantGasLimit entity = pollutantGasLimitFactory.createPollutantGasLimit();
			pollutantGasLimitAssembler.doMergeAggregateWithDto(entity, dataRepresentation);
			entity = pollutantGasLimitRepository.savePollutantGasLimit(entity);
			PollutantGasLimitRepresentation data = new PollutantGasLimitRepresentation();
			pollutantGasLimitAssembler.doAssembleDtoFromAggregate(data, entity);
			listToReturn.add(data);

		}
		return Response.ok(listToReturn).build();

	}

	/**
	 * Gets the all PG limits for label.
	 * 
	 * @param label the label
	 * @param limit the limit
	 * @return the all PG limits for label
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/PGLimit/{label}/{limit}")
	public Response getAllPGLimitsForLabel(@PathParam("label") String label, @PathParam("limit") String limit) {
		List<Double> listToReturn = null;
		if ("MIN".equalsIgnoreCase(limit)) {
			listToReturn = pollutantGasLimitFinder.getAllMinPGLimitsForLabel(label);
		} else {
			listToReturn = pollutantGasLimitFinder.getAllMaxPGLimitsForLabel(label);
		}
		return Response.ok(listToReturn).build();

	}
}
