package com.inetpsa.poc00.rest.status;

import java.util.ArrayList;
import java.util.Iterator;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.status.StatusService;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.generictd.GenericTechDataRepository;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.status.StatusFactory;
import com.inetpsa.poc00.domain.status.StatusRepository;
import com.inetpsa.poc00.domain.testnature.TestNature;
import com.inetpsa.poc00.rest.testnature.TestNatureFinder;
import com.inetpsa.poc00.rest.testnature.TestNatureRepresentation;

/**
 * The Class StatusResource.
 */
@Path("/Status")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class StatusResource {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusResource.class);

    /** The status assembler. */
    @Inject
    private StatusRepresentationAssembler statusAssembler;

    /** The status finder. */
    @Inject
    private StatusFinder statusFinder;

    /** The statusfactory. */
    @Inject
    private StatusFactory statusfactory;

    /** The testnaturefinder. */
    @Inject
    private TestNatureFinder testnaturefinder;

    /** The generic technical data repository. */
    @Inject
    GenericTechDataRepository genericTechnicalDataRepository;

    @Inject
    StatusService statusService;

    /** The status repo. */
    @Inject
    private StatusRepository statusRepo;

    /**
     * Save status.
     * 
     * @param statusRequestDto the status request dto
     * @return the response
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/StatusList")
    public Response saveStatus(ManageStatusRequestDto statusRequestDto) {
        for (StatusRepresentation statusRepresentation : statusRequestDto.getStatusRepresentationList()) {
            TestNature testNatureNew = testnaturefinder.findTestNature(statusRepresentation.getTestNatureLabel());

            Status status = statusFinder.findStatusByLabel(statusRepresentation.getLabel());

            if (statusRepresentation.getEntityId() == null || statusRepresentation.getEntityId() == 0) {
                if (status == null) {
                    status = statusfactory.createStatus();
                    statusAssembler.doMergeAggregateWithDto(status, statusRepresentation);
                }
                status.getTestNatures().add(testNatureNew);
                statusService.saveStatus(status);

            } else if (!statusRepresentation.getTestNatureLabel().equals(statusRepresentation.getTestNatureLabelOld())) {

                Status statusUpdate = statusRepo.load(statusRepresentation.getEntityId());

                for (Iterator<TestNature> iterator = statusUpdate.getTestNatures().iterator(); iterator.hasNext();) {
                    TestNature testNature = iterator.next();
                    if (testNature.getType().equals(statusRepresentation.getTestNatureLabelOld())) {
                        statusUpdate.getTestNatures().remove(testNature);
                        break;
                    }
                }

                status.getTestNatures().add(testNatureNew);
                statusService.saveStatus(status);

            }
        }
        List<StatusRepresentation> statusRepresentationForClient = getStatusNatureData();
        return Response.ok(statusRepresentationForClient).build();
    }

    /**
     * Gets the status.
     * 
     * @return the status
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/StatusList")
    public Response getStatus() {

        List<StatusRepresentation> statusList = getStatusNatureData();

        return Response.ok(statusList).build();
    }

    /**
     * Gets the status nature data.
     * 
     * @return the status nature data
     */
    private List<StatusRepresentation> getStatusNatureData() {
        List<Status> statusList = null;
        List<StatusRepresentation> resultList = new ArrayList<StatusRepresentation>();

        statusList = statusFinder.getAllStatusNaturesForTvv();
        for (Status status : statusList) {
            StatusRepresentation statusRep = new StatusRepresentation();

            statusAssembler.doAssembleDtoFromAggregate(statusRep, status);
            if (statusRep.getTestrepresentationdata() != null && !statusRep.getTestrepresentationdata().isEmpty()) {
                for (TestNatureRepresentation testNature : statusRep.getTestrepresentationdata()) {
                    StatusRepresentation statusToAdd = new StatusRepresentation(statusRep.getEntityId(), statusRep.getLabel(),
                            statusRep.getGuiLabel(), testNature.getEntityId(), testNature.getLabel(), testNature.getType());
                    resultList.add(statusToAdd);
                }
            } else
                resultList.add(statusRep);

        }

        return resultList;

    }

    /**
     * Status delete.
     * 
     * @param statusid the statusid
     * @param natureLabel the nature label
     * @return the response
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/StatusDelete/{id}/{natureLabel}")
    public Response statusDelete(@PathParam("id") Long statusid, @PathParam("natureLabel") String natureLabel) {

        try {
            Status status = statusRepo.load(statusid);
            boolean deleteCheck = true;
            String statusLabel = status.getLabel();
            if ((status.getTestNatures().size() == 1
                    && ((statusLabel.toUpperCase()).equals(Constants.VALID) || (statusLabel.toUpperCase()).equals(Constants.DRAFT)))) {

                deleteCheck = false;

            }
            if (deleteCheck && (status.getTestNatures().size() > 1 || status.getTvv().isEmpty() && status.getRegulationGroupList().isEmpty()
                    && status.getTechnicalGroupList().isEmpty() && status.getEsList().isEmpty())) {

                TestNature testNatureToDelete = null;
                for (Iterator<TestNature> iterator = status.getTestNatures().iterator(); iterator.hasNext();) {
                    TestNature testNature = iterator.next();
                    if (testNature.getType().equals(natureLabel)) {
                        testNatureToDelete = testNature;
                        break;
                    }
                }

                status.getTestNatures().remove(testNatureToDelete);

                if (status.getTestNatures().isEmpty()) {
                    statusService.deleteStatus(statusid);
                }

                return Response.ok().build();
            }
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        } catch (Exception e) {
            LOGGER.error(" **** End: Exception occured while running deleteStatus method ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    /**
     * Gets the status for label.
     * 
     * @param label the label
     * @return the status for label
     */
    /*
     * @GET
     * 
     * @Produces(MediaType.APPLICATION_JSON)
     * 
     * @Consumes(MediaType.APPLICATION_JSON)
     * 
     * @Path("/saveStatusTest") public Response saveStatusTest(StatusRepresentation statusTestRepresentation) {
     * 
     * Status status = statusfactory.createStatus();
     * 
     * statusAssembler.doMergeAggregateWithDto(status, statusTestRepresentation);
     * 
     * statusRepo.saveStatus(status);
     * 
     * return Response.ok().build(); }
     */

    /**
     * Gets the status for label.
     * 
     * @param label the label
     * @return the status for label
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/statusForLabel/{label}")
    public Response getStatusForLabel(@PathParam("label") String label) {

        StatusRepresentation status = null;

        status = statusFinder.getStatusForLabel(label);
        if (status == null)
            return Response.status(javax.ws.rs.core.Response.Status.PRECONDITION_FAILED).build();

        return Response.ok(status).build();
    }

    /**
     * Gets the all status natures.
     * 
     * @return the all status natures
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/statusTestNatures")
    public Response getAllStatusNatures() {

        List<Status> statusList;
        List<StatusRepresentation> resultList = new ArrayList<StatusRepresentation>();

        statusList = statusFinder.getAllStatusNaturesForTvv();
        for (Status status : statusList) {
            StatusRepresentation statusRep = new StatusRepresentation();

            statusAssembler.doAssembleDtoFromAggregate(statusRep, status);
            for (TestNatureRepresentation testNature : statusRep.getTestrepresentationdata()) {
                StatusRepresentation statusToAdd = new StatusRepresentation(statusRep.getEntityId(), statusRep.getLabel(), statusRep.getGuiLabel(),
                        testNature.getEntityId(), testNature.getLabel(), testNature.getType());
                resultList.add(statusToAdd);
            }

        }

        return Response.ok(resultList).build();
    }
}
