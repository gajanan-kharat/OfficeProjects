package com.inetpsa.poc00.rest.technicalgroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
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
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.technicalgroup.TechnicalGroupService;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroupRepository;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.status.StatusFactory;
import com.inetpsa.poc00.domain.technicalcase.TechCaseRepository;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.technicalgroup.TechGroupFactory;
import com.inetpsa.poc00.domain.technicalgroup.TechGroupRepository;
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;
import com.inetpsa.poc00.domain.tvv.TVVRepository;
import com.inetpsa.poc00.rest.regulationgroup.RegulationGroupAssembler;
import com.inetpsa.poc00.rest.regulationgroup.RegulationGroupRepresentation;
import com.inetpsa.poc00.rest.sampling.SamplingRuleFinder;
import com.inetpsa.poc00.rest.sampling.SamplingRuleRepresentation;
import com.inetpsa.poc00.rest.status.StatusFinder;
import com.inetpsa.poc00.rest.status.StatusRepresentation;
import com.inetpsa.poc00.rest.status.StatusRepresentationAssembler;
import com.inetpsa.poc00.rest.technicalcase.TechnicalCaseFinder;
import com.inetpsa.poc00.rest.tvv.ManageTVVRequestDto;
import com.inetpsa.poc00.rest.tvv.TvvRepresentation;
import com.inetpsa.poc00.rest.tvv.TvvSearchRepresentation;

/**
 * The Class TechnicalGroupResource.
 */
@Path("/TechnicalGroup")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class TechnicalGroupResource {

    /** The technical group service. */
    @Inject
    TechnicalGroupService technicalGroupService;
    /** The technical group assembler. */
    @Inject
    private TechnicalGroupAssembler technicalGroupAssembler;

    /** The technical group finder. */
    @Inject
    private TechnicalGroupFinder technicalGroupFinder;

    /** The technical group repository. */
    @Inject
    private TechGroupRepository technicalGroupRepository;

    /** The entity manager. */
    @Inject
    EntityManager entityManager;

    /** The technical gruop factory. */
    @Inject
    private TechGroupFactory technicalGruopFactory;

    /** The status finder. */
    @Inject
    private StatusFinder statusFinder;

    /** The status assembler. */
    @Inject
    private StatusRepresentationAssembler statusAssembler;

    /** The status factory. */
    @Inject
    private StatusFactory statusFactory;

    /** The sampling rule finder. */
    @Inject
    private SamplingRuleFinder samplingRuleFinder;

    /** The tvv repository. */
    @Inject
    private TVVRepository tvvRepository;

    /** The technical case repository. */
    @Inject
    private TechCaseRepository technicalCaseRepository;

    /** The technical case finder. */
    @Inject
    private TechnicalCaseFinder technicalCaseFinder;

    /** The regulation group assembler. */
    @Inject
    private RegulationGroupAssembler regulationGroupAssembler;

    /** The regulation group repository. */
    @Inject
    private RegulationGroupRepository regulationGroupRepository;

    /** The logger. */
    @Logging
    private static Logger logger = LoggerFactory.getLogger(TechnicalGroupResource.class);

    /**
     * Save technical group.
     * 
     * @param technicalRepresentationDto the technical representation dto
     * @return the response
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/TechnicalGroupNew")
    public Response saveTechnicalGroup(TechnicalGroupRepresentation technicalRepresentationDto) {
        TechnicalGroup technicalGroup = technicalGruopFactory.createTechGroup();
        TechnicalGroupRepresentation technicalGroupRepresentobj = new TechnicalGroupRepresentation();
        boolean isTechGroupAvailable = technicalGroupService.isTechnicalGroupExist(technicalRepresentationDto.getLabel());
        technicalGroupAssembler.doMergeAggregateWithDto(technicalGroup, technicalRepresentationDto);
        // Check if status is present
        Status status = statusFinder.getStatusForEmissionStandard(Constants.DRAFT);
        if (status == null)
            return Response.status(javax.ws.rs.core.Response.Status.PRECONDITION_FAILED).build();
        TechnicalGroup technicalGroupobj = technicalGroupService.saveTechGroup(technicalGroup);
        technicalGroupAssembler.doAssembleDtoFromAggregate(technicalGroupRepresentobj, technicalGroupobj);
        technicalGroupRepresentobj.setAvailable(isTechGroupAvailable);
        return Response.ok(technicalGroupRepresentobj).build();

    }

    /**
     * Delete technical group.
     * 
     * @param technicalGroupId the technical group id
     * @return the response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response deleteTechnicalGroup(@PathParam("id") long technicalGroupId) {
        try {
            List<TechnicalCase> techCase = technicalCaseFinder.getTechnicalCasetoDelete(technicalGroupId);
            technicalGroupService.deleteTechnicalGroup(techCase, technicalGroupId);

            return Response.ok().build();
        } catch (Exception e) {
            logger.error("Error in deleting TechnicalGroup ", e);
            return Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    /**
     * Delete tvv.
     * 
     * @param tvvObject the tvv object
     * @return the response
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/TvvDelete")
    public Response deleteTVV(TvvRepresentation tvvObject) {
        try {
            long tvvEntityId = tvvObject.getEntityId();
            technicalGroupService.deleteTvvFromTG(tvvEntityId);
            return Response.ok().build();
        } catch (Exception e) {
            logger.error("Error in deleting tvv ", e);
            return Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Gets the max tg version.
     * 
     * @param technicalGroupDto the technical group dto
     * @return the max tg version
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MaxVersion")
    public Response getMaxTgVersion(TechnicalGroupRepresentation technicalGroupDto) {
        // Get maximum version for technicalGroup
        Double version = technicalGroupFinder.getMaxVersionForTGLabel(technicalGroupDto.getLabel()) + 1.0;
        TechnicalGroupRepresentation techgroupobj = new TechnicalGroupRepresentation();
        techgroupobj.setVersion(version.toString());

        return Response.ok(techgroupobj).build();
    }

    /**
     * Save edited technical group.
     * 
     * @param technicalRepresentationDto the technical representation dto
     * @return the response
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/TechnicalGroup")
    public Response saveEditedTechnicalGroup(TechnicalGroupRepresentation technicalRepresentationDto) {

        TechnicalGroup technicalGroup = technicalGruopFactory.createTechGroup();
        TechnicalGroupRepresentation technicalGroupRepresentobj = new TechnicalGroupRepresentation();
        technicalGroupAssembler.doMergeAggregateWithDto(technicalGroup, technicalRepresentationDto);
        TechnicalGroup technicalGroupobj = technicalGroupService.saveEditedTechnicalGroup(technicalGroup, technicalGroup.getTechgroupstatus(),
                technicalRepresentationDto.isNewVersion(), technicalRepresentationDto.getSamplingLabel(),
                technicalRepresentationDto.getCreationDate(), technicalGroup.getRegulationGroup());
        technicalGroupAssembler.doAssembleDtoFromAggregate(technicalGroupRepresentobj, technicalGroupobj);

        /* Add tvv to technical group */
        if (!technicalRepresentationDto.isNewVersion()) {
            if (technicalRepresentationDto.getTvvRepresentations() != null) {
                boolean worstCaseSelected = addTvvsToTechnicalGroup(technicalRepresentationDto.getTvvRepresentations(), technicalGroupobj);
                technicalGroupRepresentobj.setWorstCaseset(worstCaseSelected);
                technicalRepresentationDto.setWorstCaseset(worstCaseSelected);

            }
            if (technicalRepresentationDto.getTechgroupstatus().getLabel().equalsIgnoreCase(Constants.VALID)
                    && !technicalRepresentationDto.isWorstCaseset()) {

                return Response.status(javax.ws.rs.core.Response.Status.PRECONDITION_FAILED).build();

            }

        }
        technicalGroupRepresentobj = getTechnicalGroupForEdit(technicalGroupobj.getEntityId());
        if (technicalRepresentationDto.isWorstCaseset())
            technicalGroupRepresentobj.setWorstCaseset(true);
        return Response.ok(technicalGroupRepresentobj).build();

    }

    /**
     * Gets the status for technical group.
     * 
     * @return the status for technical group
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/TechnicalGroupStatus")
    public Response getStatusForTechnicalGroup() {
        // Initialy load status
        List<StatusRepresentation> statusList = statusFinder.getAllStatus();
        List<StatusRepresentation> statusForTGList = new ArrayList<>();
        for (StatusRepresentation status : statusList) {
            if (status.getLabel().equalsIgnoreCase(Constants.DRAFT) || status.getLabel().equalsIgnoreCase(Constants.VALID)) {

                statusForTGList.add(status);

            }
        }

        return Response.ok(statusForTGList).build();
    }

    /**
     * Gets the searched technical groups.
     * 
     * @param searchTgRepresentation the search tg representation
     * @return the searched technical groups
     */
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/TechnicalGroupsByCriteria")
    public Response getSearchedTechnicalGroups(ManageSearchTGRequestDto searchTgRepresentation) {
        List<TechnicalGroupRepresentation> searchedGroup;
        String searchLabel = searchTgRepresentation.getSearchLable();
        if (searchLabel != null) {

            while (searchLabel.indexOf(Constants.ASTERIC) != -1) {
                searchLabel = searchLabel.replace(Constants.ASTERIC, Constants.WILDCARD_PERCENTAGE);
            }
        }
        TvvSearchRepresentation tvvSearchRepresentation = searchTgRepresentation.getTvvSearchRepresentation();

        searchedGroup = technicalGroupFinder.getSearchedTechnicalGroup(tvvSearchRepresentation, searchLabel, searchTgRepresentation);
        for (TechnicalGroupRepresentation searchedItem : searchedGroup) {
            if (searchedItem.getTechgroupstatus().getLabel().equalsIgnoreCase(Constants.DRAFT)) {
                searchedItem.setValid(0);
            }
        }

        return Response.ok(searchedGroup).build();

    }

    /**
     * Gets the technical group.
     * 
     * @param technicalGroupId the technical group id
     * @return the technical group
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/TechnicalGroup/{id}")
    public Response getTechnicalGroup(@PathParam("id") long technicalGroupId) {

        TechnicalGroupRepresentation technicalGroup = getTechnicalGroupForEdit(technicalGroupId);

        return Response.ok(technicalGroup).build();

    }

    /**
     * Gets the technical group for edit.
     * 
     * @param technicalGroupId the technical group id
     * @return the technical group for edit
     */
    private TechnicalGroupRepresentation getTechnicalGroupForEdit(long technicalGroupId) {
        TechnicalGroupRepresentation technicalGroup = technicalGroupFinder.findTechnicalGroupById(technicalGroupId);
        if (technicalGroup != null) {
            List<TvvRepresentation> tvvList = getTVVsForTechnicalGroup(technicalGroupId);
            for (TvvRepresentation tvvrepresent : tvvList) {
                Date modificationDate = tvvrepresent.getModificationDate();
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String formatteddDate = df.format(modificationDate);
                tvvrepresent.setDisplayDate(formatteddDate);
            }
            technicalGroup.setTvvRepresentations(tvvList);

            if (technicalGroup.getRegulationGroupId() != null) {
                RegulationGroup regulationGroup = regulationGroupRepository.load(technicalGroup.getRegulationGroupId());
                RegulationGroupRepresentation regulationRepresentation = new RegulationGroupRepresentation();
                regulationGroupAssembler.doAssembleDtoFromAggregate(regulationRepresentation, regulationGroup);
                technicalGroup.setRegulationGroupRepresent(regulationRepresentation);
            }
        }
        return technicalGroup;
    }

    /**
     * Load sampling rule.
     * 
     * @return the response
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/SamplingRule")
    public Response loadSamplingRule() {
        Set<SamplingRuleRepresentation> samplingRuleSet = new HashSet<>();
        List<SamplingRuleRepresentation> samplingRuleList = samplingRuleFinder.getAllSamplingRule();
        boolean isDuplicate;

        for (SamplingRuleRepresentation samplingRule : samplingRuleList) {
            isDuplicate = false;
            for (SamplingRuleRepresentation TgsamplingRule : samplingRuleSet) {
                if (TgsamplingRule.getLabel().equalsIgnoreCase(samplingRule.getLabel())) {
                    TgsamplingRule.setDescription(TgsamplingRule.getDescription() + '\n' + samplingRule.getDescription());
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate)

                samplingRuleSet.add(samplingRule);
        }
        return Response.ok(samplingRuleSet).build();
    }

    /**
     * Load selected tvv.
     * 
     * @param technicalGroupId the technical group id
     * @return the response
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/TVVs/{id}")
    public Response loadSelectedTvv(@PathParam("id") long technicalGroupId) {

        return Response.ok(getTVVsForTechnicalGroup(technicalGroupId)).build();
    }

    /**
     * Gets the TV vs for technical group.
     * 
     * @param technicalGroupId the technical group id
     * @return the TV vs for technical group
     */
    private List<TvvRepresentation> getTVVsForTechnicalGroup(long technicalGroupId) {
        return technicalGroupFinder.getTvvsForTechnicalGroup(technicalGroupId);

    }

    /**
     * Save technica group for tg.
     * 
     * @param tvvRepresentationDto the tvv representation dto
     * @return the response
     */
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/TechnicalGroupForTVV")
    public Response saveTechnicaGroupForTG(ManageTVVRequestDto tvvRepresentationDto) {
        List<TvvRepresentation> tvvRepresentations = tvvRepresentationDto.getTvvRepresentationList();

        TechnicalGroup technicalGroup = new TechnicalGroup();
        technicalGroupAssembler.doMergeAggregateWithDto(technicalGroup, tvvRepresentationDto.getTechnicalGroupRepresentation());

        addTvvsToTechnicalGroup(tvvRepresentations, technicalGroup);
        return Response.ok().build();
    }

    /**
     * Adds the tvvs to technical group.
     * 
     * @param tvvRepresentations the tvv representations
     * @param technicalGroup the technical group
     * @return true, if successful
     */
    private boolean addTvvsToTechnicalGroup(List<TvvRepresentation> tvvRepresentations, TechnicalGroup technicalGroup) {
        boolean worstCaseSelected = false;
        for (TvvRepresentation tvvRepresentation : tvvRepresentations) {

            TechnicalCase technicalCase = technicalGroupService.saveTechnicalCase(tvvRepresentation.getEntityId(), technicalGroup,
                    tvvRepresentation.isWorstcaseSelected());
            if (technicalCase.getTvvWorstCase()) {
                tvvRepresentation.setShowWorstCaseicon(1);
                worstCaseSelected = true;
            } else
                tvvRepresentation.setShowWorstCaseicon(0);

            Date modificationDate = tvvRepresentation.getModificationDate();
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String formatteddDate = df.format(modificationDate);
            tvvRepresentation.setDisplayDate(formatteddDate);
            logger.info("TechnicalCase Saved Successfully");

        }
        return worstCaseSelected;
    }

    /**
     * Sets the worst case.
     * 
     * @param tvvRepresentationDto the tvv representation dto
     * @return the response
     */
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/TvvWorstCase")
    public Response setWorstCase(ManageTVVRequestDto tvvRepresentationDto)

    {
        TechnicalGroup technicalGroup = null;
        List<TvvRepresentation> tvvRepresentList = new ArrayList<>();
        for (TvvRepresentation tvvRepresentation : tvvRepresentationDto.getTvvRepresentationList()) {
            TechnicalCase technicalCase = technicalGroupService.saveTechnicalCase(tvvRepresentation.getEntityId(), technicalGroup,
                    tvvRepresentation.isWorstcaseSelected());
            if (technicalCase.getTvvWorstCase()) {
                tvvRepresentation.setShowWorstCaseicon(1);
            } else {
                tvvRepresentation.setShowWorstCaseicon(0);
            }
            tvvRepresentList.add(tvvRepresentation);
            logger.info("WorstCase set successfully");

        }
        return Response.ok(tvvRepresentList).build();
    }

    /**
     * Gets the regulation group.
     * 
     * @param technicalGroupId the technical group id
     * @return the regulation group
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/RegulationGroup/{id}")
    public Response getRegulationGroup(@PathParam("id") long technicalGroupId) {
        List<RegulationGroup> regulationGroupList = technicalGroupFinder.getRegulationGroup(technicalGroupId);
        List<RegulationGroupRepresentation> regulationGrpRepresentList = new ArrayList<>();
        if (regulationGroupList != null) {

            for (RegulationGroup regulationgrp : regulationGroupList) {
                RegulationGroupRepresentation regulationGroupRepresent = new RegulationGroupRepresentation();
                regulationGroupAssembler.doAssembleDtoFromAggregate(regulationGroupRepresent, regulationgrp);
                regulationGrpRepresentList.add(regulationGroupRepresent);
            }

        }
        return Response.ok(regulationGrpRepresentList).build();
    }

}
