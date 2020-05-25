package com.inetpsa.poc00.rest.finalreduction;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.finalreduction.FinalReductionService;
import com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatio;
import com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatioFactory;
import com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatioRepository;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleRepository;
import com.inetpsa.poc00.rest.gnomedictionary.GenomeLCDVDictionaryFinder;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class FinalReductionResource.
 */
@Path("/FinalReductionReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class FinalReductionResource {

	/** The tvv rule repository. */
	@Inject
	private GenomeTVVRuleRepository tvvRuleRepository;

	/** The final reduction finder. */
	@Inject
	FinalReductionFinder finalReductionFinder;

	/** The final reduction ratio factory. */
	@Inject
	FinalReductionRatioFactory finalReductionRatioFactory;

	/** The final reduction assembler. */
	@Inject
	FinalReductionAssembler finalReductionAssembler;

	/** The final reduction ratio repository. */
	@Inject
	FinalReductionRatioRepository finalReductionRatioRepository;

	@Inject
	FinalReductionService finalReductionService;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;

	/** The logger. */
	@Logging
	private Logger logger;

	/** The genome lcdv dictionary finder. */
	@Inject
	private GenomeLCDVDictionaryFinder genomeLCDVDictionaryFinder;

	/**
	 * Gets the final reduction data.
	 * 
	 * @param hsr the hsr
	 * @param info the info
	 * @return the final reduction data
	 */
	@GET
	@Path("/FinalReductionData")
	public Response getFinalReductionData(@Context HttpServletResponse hsr, @Context UriInfo info) {

		List<FinalReductionRepresentation> finalReductionRatioList;

		logger.info("To get value from FinalReduction");
		finalReductionRatioList = finalReductionFinder.findAllFinalReductionData();

		logger.info("sending FinalReduction value to UI");
		return Response.ok(finalReductionRatioList).build();
	}

	/**
	 * Save final reduction data.
	 * 
	 * @param finalReductionRequestDto the final reduction request dto
	 * @return the response
	 */

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/FinalReductionData")
	public Response saveFinalReductionData(ManageFinalReductionRequestDto finalReductionRequestDto) {

		logger.info("trying to save data in FinalReductionRatio table");

		FinalReductionRepresentation finalReductionRatioResponse = finalReductionRequestDto.getFinalReductionRepresentationList().get(0);
		for (FinalReductionRepresentation finalReductionRepresentation : finalReductionRequestDto.getFinalReductionRepresentationList()) {
			FinalReductionRatio finalReductionRatio = finalReductionRatioFactory.createReductionRatio();
			finalReductionAssembler.mergeAggregateWithDto(finalReductionRatio, finalReductionRepresentation);
			FinalReductionRatio finalReductionResponse = finalReductionService.saveFinalReduction(finalReductionRatio);
			if (finalReductionResponse == null) {
				finalReductionRepresentation.setFinalReductionDCW(null);
				return Response.ok(finalReductionRepresentation).build();
			}

			finalReductionRatioResponse = finalReductionRepresentation;
		}
		logger.info("Sucessfully saved FinalReductionRatio data");
		return Response.ok(finalReductionRatioResponse).build();

	}

	/**
	 * Delete final reduction data.
	 * 
	 * @param finalReductionRequestDto the final reduction request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/FinalReduction")
	public Response deleteFinalReductionData(ManageFinalReductionRequestDto finalReductionRequestDto) {

		logger.info("trying to delete data from FinalReduction table");
		for (FinalReductionRepresentation finalReductionRepresentation : finalReductionRequestDto.getFinalReductionRepresentationList()) {
			boolean deleted = finalReductionService.deleteFinalReduction(finalReductionRepresentation.getEntityId());

			if (!deleted) {
				return Response.status(Response.Status.PRECONDITION_FAILED).build();
			}
		}
		return Response.ok().build();
	}

	/**
	 * Gets the final reduction ratio.
	 * 
	 * @param tvvLabel the tvv label
	 * @return the final reduction ratio
	 */
	@GET
	@Path("/FinalReductionRatio/{tvvLabel}")
	public Response getFinalReductionRatio(@PathParam("tvvLabel") String tvvLabel) {

		List<FinalReductionRepresentation> finalReductionRatioList = null;

		logger.info("To get value from FinalReduction for TVV");
		List<String> kmatList = new ArrayList<String>();

		if (tvvLabel != null && tvvLabel.length() >= 6)

		{
			String tvvlabeltoSend = tvvLabel.substring(0, 5);
			kmatList = genomeLCDVDictionaryFinder.getKmatDictionaryForTVV(tvvlabeltoSend);
		}
		if (!kmatList.isEmpty())
			finalReductionRatioList = finalReductionFinder.getAllFinalReductionForTvv(kmatList);

		if (finalReductionRatioList == null || finalReductionRatioList.isEmpty()) {
			finalReductionRatioList = finalReductionFinder.getAllFinalReductionData();
			logger.info("No data found from GENOME for TVV label,fetching all Final Reduction Ratio data");
		} else
			logger.info("Returning data based on TVV label");
		logger.info("sending FinalReduction value to UI");
		return Response.ok(finalReductionRatioList).build();
	}
}
