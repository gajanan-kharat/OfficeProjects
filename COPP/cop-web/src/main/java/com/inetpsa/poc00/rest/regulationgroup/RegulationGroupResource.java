/*
 * 
 */
package com.inetpsa.poc00.rest.regulationgroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.google.inject.Inject;
import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.regulationgroup.RegulationGroupService;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL;
import com.inetpsa.poc00.domain.generictc.GenericTestCondition;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroupFactory;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroupRepository;
import com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCL;
import com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCLFactory;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;
import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalAreaFactory;
import com.inetpsa.poc00.emsdeptcl.EmsDepTCLFinder;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardAssembler;
import com.inetpsa.poc00.rest.regulationgroupvaluedes.EmsDepTCtoRGValuedTCAssembler;
import com.inetpsa.poc00.rest.regulationgroupvaluedes.RGValuedESDependantAssembler;
import com.inetpsa.poc00.rest.regulationgroupvaluedes.RGValuedESDependentTCLRepresentation;
import com.inetpsa.poc00.rest.regulationgroupvaluedes.RGValuedTCLFinder;
import com.inetpsa.poc00.rest.regulationgroupvaluedtc.GenericTCtoRGVAluedTC;
import com.inetpsa.poc00.rest.regulationgroupvaluedtc.RegGrpValdTestConditionRepresentation;
import com.inetpsa.poc00.rest.regulationgroupvaluedtc.RegGrpValuedTCAssembler;
import com.inetpsa.poc00.rest.status.StatusFinder;
import com.inetpsa.poc00.rest.technicalgroup.ManageTGRequestDto;
import com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupRepresentation;
import com.inetpsa.poc00.rest.tvv.TvvAssembler;
import com.inetpsa.poc00.rest.tvv.TvvFinder;
import com.inetpsa.poc00.rest.tvv.TvvRepresentation;
import com.inetpsa.poc00.rest.tvv.TvvSearchRepresentation;
import com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionFinder;

/**
 * The Class RegulationGroupResource.
 */
@Path("/RegulationGroup")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class RegulationGroupResource {

    /** The regulation group finder. */

    @Inject
    private RegulationGroupFinder regulationGroupFinder;

    /** The regulation factory. */
    @Inject
    private RegulationGroupFactory regulationFactory;

    /** The status factory. */
    @Inject
    private RegulationGroupService regulationGroupService;

    /** The regulation assembler. */
    @Inject
    private RegulationGroupAssembler regulationAssembler;

    /** The status finder. */
    @Inject
    private StatusFinder statusFinder;

    /** The regulation grp repository. */
    @Inject
    private RegulationGroupRepository regulationGrpRepository;

    /** The emissionfactory. */
    @Inject
    EmissionStandardFactory emissionfactory;

    /** The typeapproval factory. */
    @Inject
    TypeApprovalAreaFactory typeapprovalFactory;

    /** The emission assembler. */
    @Inject
    EmissionStandardAssembler emissionAssembler;

    /** The tvv finder. */
    @Inject
    private TvvFinder tvvFinder;

    /** The ems dep tcl finder. */
    @Inject
    EmsDepTCLFinder emsDepTclFinder;

    /** The ems to rgvld assembler. */
    @Inject
    EmsDepTCtoRGValuedTCAssembler emsToRgvldAssembler;

    /** The generic test condition finder. */
    @Inject
    GenericTestConditionFinder genericTestConditionFinder;

    /** The generic tcto rg valued tc. */
    @Inject
    GenericTCtoRGVAluedTC genericTctoRgValuedTc;

    /** The reg grp valued tc assembler. */
    @Inject
    RegGrpValuedTCAssembler regGrpValuedTcAssembler;

    /** The rg valued esd dependant assembler. */
    @Inject
    RGValuedESDependantAssembler rgValuedEsdDependantAssembler;

    /** The tvv assembler. */
    @Inject
    TvvAssembler tvvAssembler;

    /** The RG valued ES dependent TCL factory. */
    @Inject
    RGValuedESDependentTCLFactory rgValuedESDependentTCLFactory;

    /** The rg valued TCL finder. */
    @Inject
    RGValuedTCLFinder rgValuedTCLFinder;

    /** The logger. */
    @Logging
    private Logger logger;

    /**
     * Save regulation group.
     * 
     * @param regulationGroupDto the regulation group dto
     * @return the response
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/RegulationGroupNew")
    public Response saveRegulationGroup(RegulationGroupRepresentation regulationGroupDto) {
        regulationGroupFinder.getRegulationGroup(regulationGroupDto.getLabel());

        RegulationGroup regulationGroup = regulationFactory.createRegulationGroup();
        RegulationGroupRepresentation regulationGroupRepresentobj = new RegulationGroupRepresentation();
        boolean isRegulationGrpAvailable = regulationGroupService.isRegulationGroupExist(regulationGroupDto.getLabel());
        regulationAssembler.doMergeAggregateWithDto(regulationGroup, regulationGroupDto);
        Status status = statusFinder.getStatusForEmissionStandard(Constants.DRAFT);
        if (status == null)
            return Response.status(javax.ws.rs.core.Response.Status.PRECONDITION_FAILED).build();
        RegulationGroup regulationGroupObj = regulationGroupService.saveRegulationGroup(regulationGroup);
        regulationAssembler.doAssembleDtoFromAggregate(regulationGroupRepresentobj, regulationGroupObj);
        regulationGroupRepresentobj.setAvailable(isRegulationGrpAvailable);
        return Response.ok(regulationGroupRepresentobj).build();

    }

    /**
     * Gets the searched technical groups.
     * 
     * @param searchRGRepresentation the search RG representation
     * @return the searched technical groups
     */
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/RegulationGroupsByCriteria")
    public Response getSearchedTechnicalGroups(ManageSearchRGRequestDto searchRGRepresentation) {

        List<RegulationGroupRepresentation> searchRgGroupRepresentList;
        String searchLabel = searchRGRepresentation.getSearchLable();
        if (searchLabel != null) {

            while (searchLabel.indexOf(Constants.ASTERIC) != -1) {
                searchLabel = searchLabel.replace(Constants.ASTERIC, Constants.WILDCARD_PERCENTAGE);
            }
        }
        TvvSearchRepresentation tvvSearchRepresentation = searchRGRepresentation.getTvvSearchRepresentation();

        List<TvvRepresentation> tvvRepresentation = tvvFinder.findByCriteria(tvvSearchRepresentation);
        List<String> tvvLableList = new ArrayList<>();
        for (TvvRepresentation tvvrepresent : tvvRepresentation) {
            tvvLableList.add(tvvrepresent.getLabel());
        }

        searchRgGroupRepresentList = regulationGroupFinder.getSearchedRegulationGroup(searchLabel, searchRGRepresentation);

        for (RegulationGroupRepresentation searchedItem : searchRgGroupRepresentList) {
            if (searchedItem.getRegulationgroupstatus().getGuiLabel().equalsIgnoreCase(Constants.DRAFT)) {
                searchedItem.setValid(0);
            }
        }

        return Response.ok(searchRgGroupRepresentList).build();

    }

    /**
     * Delete technical group.
     * 
     * @param regulationGrpId the regulation grp id
     * @return the response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/RegulationGrp/{id}")
    public Response deleteRegulationGroup(@PathParam("id") Long regulationGrpId) {
        try {
            RegulationGroup regulationGroup = regulationGrpRepository.loadRegulationGroup(regulationGrpId);
            List<TechnicalGroup> technicalGroupList = new ArrayList<>();
            technicalGroupList.addAll(regulationGroup.getTechnicalGroups());

            regulationGroupService.deleteRegulationGroup(technicalGroupList, regulationGrpId);
            return Response.ok().build();
        } catch (Exception e) {
            logger.error("Error in deleting Regulation group ", e);
            return Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/RegulationGrp")
    public Response deleteTechnicalGroup(TechnicalGroupRepresentation technicalGroup) {
        try {
            regulationGroupService.deleteTechnicalGroup(technicalGroup.getEntityId());

            return Response.ok().build();
        } catch (Exception e) {
            logger.error("Error in deleting Technical group ", e);
            return Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    /**
     * Save regulation group for tg.
     * 
     * @param technicalGrpDto the technical grp dto
     * @return the response
     */
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/RegulationGroupForTG")
    public Response saveRegulationGroupForTg(ManageTGRequestDto technicalGrpDto) {

        for (TechnicalGroupRepresentation technicalGroupRepresentation : technicalGrpDto.getTgRepresentationList()) {
            TechnicalCase technicalCaseObj = regulationGroupService.saveRegulationGroupForTg(
                    technicalGrpDto.getRegulationGroupRepresentation().getEntityId(), technicalGroupRepresentation.getEntityId(),
                    technicalGrpDto.isValidateForEmissionStandard());

            if (technicalGrpDto.isValidateForEmissionStandard() && technicalCaseObj != null) {
                TvvRepresentation tvvObject = new TvvRepresentation();
                tvvAssembler.doAssembleDtoFromAggregate(tvvObject, technicalCaseObj.getTvv());
                technicalGrpDto.setTvvObject(tvvObject);
                return Response.ok(technicalGrpDto).build();
            }

        }
        technicalGrpDto.setTvvObject(null);
        return Response.ok(technicalGrpDto).build();
    }

    /**
     * Save edited regulation group.
     * 
     * @param regulationGroupDto the regulation group dto
     * @return the response
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/RegulationGroup")
    public Response saveEditedRegulationGroup(RegulationGroupRepresentation regulationGroupDto) {

        RegulationGroup regulationGroup = regulationFactory.createRegulationGroup();
        regulationAssembler.doMergeAggregateWithDto(regulationGroup, regulationGroupDto);
        Date modificationDate = new Date();
        regulationGroup.setModificationDate(modificationDate);
        RegulationGroup oldObject = regulationGrpRepository.load(regulationGroupDto.getEntityId());

        if (regulationGroupDto.getStatisticalRuleRepresentation() == null
                && regulationGroupDto.getRegulationgroupstatus().getLabel().equalsIgnoreCase(Constants.VALID)) {
            return Response.status(javax.ws.rs.core.Response.Status.PRECONDITION_FAILED).build();
        }

        if (oldObject.getEmissionStandardforRg() != null && regulationGroup.getEmissionStandardforRg() != null) {
            EmissionStandard oldEs = oldObject.getEmissionStandardforRg();
            EmissionStandard newEs = regulationGroup.getEmissionStandardforRg();
            String oldEsLabel = oldEs.getEsLabel() + oldEs.getVersion();
            String newEsLabel = newEs.getEsLabel() + newEs.getVersion();

            if (!oldEsLabel.equalsIgnoreCase(newEsLabel)) {
                deleteOldRgValuedTCL(oldObject);
                oldObject.setRgValuedEsDepTCL(null);
                List<RGValuedESDependentTCL> rgValuedTCL = getRegulationGroupValuedTCL(newEs, regulationGroup);
                regulationGroup.setRgValuedEsDepTCL(rgValuedTCL);
            }
        } else if (oldObject.getEmissionStandardforRg() == null) {
            List<RGValuedESDependentTCL> rgValuedTCL = getRegulationGroupValuedTCL(regulationGroup.getEmissionStandardforRg(), regulationGroup);
            regulationGroup.setRgValuedEsDepTCL(rgValuedTCL);
        }

        if (regulationGroupDto.isNewRgVersion()) {
            regulationGroup.setEntityId(null);
            Double version = regulationGroupFinder.getMaxVersionForRGLabel(regulationGroup.getLabel()) + 1.0;
            regulationGroup.setVersion(version.toString());
            Status status = statusFinder.findStatusByLabel(Constants.DRAFT);
            regulationGroup.setRegulationgroupstatus(status);
            regulationGroup.setTechnicalGroups(null);
        }
        RegulationGroup regulationgrp = regulationGrpRepository.saveRegulationGroup(regulationGroup);
        RegulationGroupRepresentation regulationgrpRepresent = new RegulationGroupRepresentation();
        regulationAssembler.doAssembleDtoFromAggregate(regulationgrpRepresent, regulationgrp);

        return Response.ok(regulationgrpRepresent).build();
    }

    /**
     * Delete old rg valued TCL.
     * 
     * @param oldObject the old object
     */
    private void deleteOldRgValuedTCL(RegulationGroup oldObject) {

        List<RGValuedESDependentTCL> rgValuedTcl = rgValuedTCLFinder.getRGValuedTestConditionList(oldObject.getEntityId());
        regulationGroupService.deleteOldRgValuedTCL(rgValuedTcl);
    }

    /**
     * Gets the regulation group valued TCL.
     * 
     * @param emissionStandard the emission standard
     * @param rGroup the r group
     * @return the regulation group valued TCL
     */
    private List<RGValuedESDependentTCL> getRegulationGroupValuedTCL(EmissionStandard emissionStandard, RegulationGroup rGroup) {
        List<RGValuedESDependentTCL> rgValuedTcl = new ArrayList<>();
        if (emissionStandard != null) {
            List<EmissionStdDepTCL> emsDepTCLList = emsDepTclFinder.getLatestEmissionStandardDepLists(emissionStandard.getEntityId());

            for (EmissionStdDepTCL emsDeptcl : emsDepTCLList) {
                List<RegGrpValdTestConditionRepresentation> rgvaluedTCRepresentList = new ArrayList<>();
                RGValuedESDependentTCLRepresentation rgvaluedesRepresent = new RGValuedESDependentTCLRepresentation();
                emsToRgvldAssembler.doAssembleDtoFromAggregate(rgvaluedesRepresent, emsDeptcl);
                List<GenericTestCondition> genericTestConditionList = genericTestConditionFinder
                        .getAllGenericConditionsForEmsList(emsDeptcl.getEntityId());

                for (GenericTestCondition genericTC : genericTestConditionList) {
                    RegGrpValdTestConditionRepresentation rgValuedTCRepresent = new RegGrpValdTestConditionRepresentation();
                    genericTctoRgValuedTc.doAssembleDtoFromAggregate(rgValuedTCRepresent, genericTC);
                    rgvaluedTCRepresentList.add(rgValuedTCRepresent);
                }
                rgvaluedesRepresent.setRgValuedGenericTestConditionrepresent(rgvaluedTCRepresentList);
                RGValuedESDependentTCL valuedTCL = rgValuedESDependentTCLFactory.createRegGrpValuedESDTCL();
                rgValuedEsdDependantAssembler.doMergeAggregateWithDto(valuedTCL, rgvaluedesRepresent);
                valuedTCL.setRegulationGroup(rGroup);

                rgValuedTcl.add(valuedTCL);

            }
        }
        return rgValuedTcl;
    }

    /**
     * Load selected technical groups.
     * 
     * @param regulationGrpId the regulation grp id
     * @return the response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/TechnicalGroups/{id}")
    public Response loadSelectedTechnicalGroups(@PathParam("id") Long regulationGrpId) {
        List<TechnicalGroupRepresentation> technicalGroupList = regulationGroupFinder.getSelectedTechnicalGroupsForRg(regulationGrpId);
        return Response.ok(technicalGroupList).build();
    }

    /**
     * Gets the all regulation groups.
     * 
     * @return the all regulation groups
     */
    @GET
    @Path("/AllRegulationGroups")
    public Response getAllRegulationGroups() {
        List<RegulationGroupRepresentation> regulationGroupRepresentations;

        logger.info("To get all Regulation Groups");
        regulationGroupRepresentations = regulationGroupFinder.findAllRegulationGroups();

        logger.info("sending all Regulation Groups to UI");
        return Response.ok(regulationGroupRepresentations).build();

    }

    /**
     * Load regulation group.
     * 
     * @param regulationGroupId the regulation group id
     * @return the response
     */
    @GET
    @Path("/RegulationGroupData/{id}")
    public Response loadRegulationGroup(@PathParam("id") long regulationGroupId) {
        RegulationGroup regulationGroupObj = regulationGrpRepository.load(regulationGroupId);
        RegulationGroupRepresentation regulationRepresentationObj = new RegulationGroupRepresentation();
        regulationAssembler.doAssembleDtoFromAggregate(regulationRepresentationObj, regulationGroupObj);
        return Response.ok(regulationRepresentationObj).build();
    }

    /**
     * Gets the max tg version.
     * 
     * @return the max tg version
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/MaxVersion")
    public Response getMaxTgVersion(RegulationGroupRepresentation regulationGroup) {
        Double version = regulationGroupFinder.getMaxVersionForRGLabel(regulationGroup.getLabel()) + 1.0;
        RegulationGroupRepresentation regulationObj = new RegulationGroupRepresentation();
        regulationObj.setVersion(version.toString());
        logger.info("setting max version for regulationGroup");
        return Response.ok(regulationObj).build();
    }

}
