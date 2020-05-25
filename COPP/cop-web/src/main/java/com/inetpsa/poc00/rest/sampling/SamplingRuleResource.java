package com.inetpsa.poc00.rest.sampling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.samplingrule.SamplingRuleService;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.samplingrule.SamplingRule;
import com.inetpsa.poc00.domain.samplingrule.SamplingRuleRepository;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class SamplingRuleResource.
 */
@Path("/Sampling")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class SamplingRuleResource {

    /** The sampling assembler. */

    @Inject
    private SamplingRuleAssembler samplingAssembler;

    @Inject
    private SamplingRuleService samplingRuleService;

    /** The samplingrule finder. */
    @Inject
    private SamplingRuleFinder samplingruleFinder;

    /** The samplingrepository. */
    @Inject
    private SamplingRuleRepository samplingrRepository;

    /** The e_manager. */
    @Inject
    EntityManager entityManager;

    /** The trace resource. */
    @Inject
    TraceabilityResource traceResource;

    /**
     * Edits the sampling.
     * 
     * @param samplingRepresentationDto the samplingrepresentation dto
     * @return the response
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/SamplingRules")
    public Response editSampling(ManageSamplingRuleRequestDto samplingRepresentationDto) {
        for (SamplingRuleRepresentation samplingrepresentation : samplingRepresentationDto.getSamplingRuleRepresentation()) {
            SamplingRule oldSamplingRule = null;
            SamplingRule samplingrule = new SamplingRule();
            samplingAssembler.doMergeAggregateWithDto(samplingrule, samplingrepresentation);

            SamplingRule newSamplingRule = samplingRuleService.saveSamplingRule(samplingrule);
            if (samplingrepresentation.getEntityId() != null) {
                oldSamplingRule = samplingrRepository.load(samplingrepresentation.getEntityId());
            }

            if (oldSamplingRule != null) {
                traceResource.historyForUpdate(oldSamplingRule, newSamplingRule, Constants.SAMPLINGRULE_SCREEN_ID);
            } else {
                traceResource.historyForSave(newSamplingRule, Constants.SAMPLINGRULE_SCREEN_ID);
            }
        }
        return Response.status(Status.OK).build();

    }

    /**
     * Gets the sampling.
     * 
     * @return the response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/labels")
    public Response getUniqueSamplingLabels() {

        List<SamplingRuleRepresentation> ruleList;
        List<SamplingRuleRepresentation> samplingListToReturn = new ArrayList<>();

        ruleList = samplingruleFinder.getAllSamplingRule();
        Map<String, List<String>> testMap = new HashMap<>();

        for (Iterator iterator = ruleList.iterator(); iterator.hasNext();) {
            SamplingRuleRepresentation samplingRule = (SamplingRuleRepresentation) iterator.next();

            SamplingRuleRepresentation samplingRuleToReturn = new SamplingRuleRepresentation();
            samplingRuleToReturn.setLabel(samplingRule.getLabel());

            List<String> currentLabels = testMap.get(samplingRule.getLabel());
            if (currentLabels == null) {
                currentLabels = new ArrayList<>();
                testMap.put(samplingRule.getLabel(), currentLabels);
            }
            currentLabels.add(samplingRule.getDescription());
        }

        for (Iterator<String> iterator = testMap.keySet().iterator(); iterator.hasNext();) {
            String label = iterator.next();

            SamplingRuleRepresentation samplingRuleToReturn = new SamplingRuleRepresentation();
            List<String> descriptions = testMap.get(label);

            samplingRuleToReturn.setDescriptions(descriptions);
            samplingRuleToReturn.setLabel(label);
            samplingListToReturn.add(samplingRuleToReturn);

        }

        return Response.ok(samplingListToReturn).build();
    }

    /**
     * Gets the sampling.
     * 
     * @return the response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/SamplingGet")
    public Response getSampling() {

        List<SamplingRuleRepresentation> ruleList;

        ruleList = samplingruleFinder.getAllSamplingRule();

        return Response.ok(ruleList).build();
    }

}