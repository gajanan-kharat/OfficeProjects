/*
 * Creation : Sep 29, 2016
 */
package com.inetpsa.poc00.rest.vehiclefile;

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
import javax.ws.rs.core.Response.Status;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.security.SecuritySupport;
import org.seedstack.seed.security.principals.Principals;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.vehiclefile.VehicleFileService;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.basket.Basket;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvv.TVVFactory;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;
import com.inetpsa.poc00.rest.basket.BasketFinder;
import com.inetpsa.poc00.rest.regulationgroup.RegulationGroupFinder;
import com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupFinder;
import com.inetpsa.poc00.rest.tvv.TvvAssembler;
import com.inetpsa.poc00.rest.tvv.TvvFinder;
import com.inetpsa.poc00.rest.tvv.TvvRepresentation;
import com.inetpsa.poc00.rest.vehicle.VehicleModelRepresentation;
import com.inetpsa.poc00.rest.vehicle.VehicleSearchRepresentation;

/**
 * The Class VehicleFileResource.
 */
@Path("/vehiclefile")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class VehicleFileResource {

    /** The basket finder. */
    @Inject
    private BasketFinder basketFinder;

    /** The vehicle file repo. */
    @Inject
    private VehicleFileRepository vehicleFileRepo;

    /** The vehicle file finder. */
    @Inject
    private VehicleFileFinder vehicleFileFinder;

    /** The security support. */
    @Inject
    SecuritySupport securitySupport;

    /** The technical group finder. */
    @Inject
    private TechnicalGroupFinder technicalGroupFinder;

    /** The regulation group finder. */
    @Inject
    private RegulationGroupFinder regulationGroupFinder;
    /** The tvv assembler. */
    @Inject
    TvvAssembler tvvAssembler;

    /** The tvv factory. */
    @Inject
    TVVFactory tvvFactory;

    /** The tvv repository. */
    @Inject
    TvvFinder tvvFinder;

    /** The vehicle file service. */
    @Inject
    VehicleFileService vehicleFileService;

    /** The vehicle file assembler. */
    @Inject
    VehicleFileAssembler vehicleFileAssembler;

    /** The Constant logger. */
    @Logging
    private final Logger logger = LoggerFactory.getLogger(VehicleFileResource.class);

    /**
     * Adds the basket for multiple vehicleFile.
     *
     * @param vehicleFileModel the vehicle file model
     * @return the response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/basket")
    public Response addBasketMultipleVF(VehicleFileModel vehicleFileModel) {
        List<Long> vehicleFileIdList = vehicleFileModel.getVehicleFileIds();
        String userId = securitySupport.getSimplePrincipalByName(Principals.IDENTITY).getPrincipal();
        for (Long vehiclefileId : vehicleFileIdList) {
            VehicleFile vehicleFile = vehicleFileRepo.load(vehiclefileId);
            if (vehicleFile.getBasket() != null) {
                return Response.status(Status.PRECONDITION_FAILED).build();
            }
        }
        for (Long vehiclefileId : vehicleFileIdList) {
            VehicleFile vehicleFile = vehicleFileRepo.load(vehiclefileId);
            if (vehicleFile.getBasket() == null) {
                Basket basketObj = basketFinder.getBasketByUserId(userId);
                vehicleFile.setBasket(basketObj);
                vehicleFileRepo.saveVehicle(vehicleFile);
            }
        }
        return Response.ok().build();
    }

    /**
     * Gets the filtered vf.
     *
     * @param vehicleSearchRepresentation the vehicle search representation
     * @return the filtered vf
     */
    /*
     * To return Filtered vehicle files
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/seachvehiclefile")
    public Response getFilteredVF(VehicleSearchRepresentation vehicleSearchRepresentation) {
        List<VehicleFileRepresentation> vehicleRepresentation = vehicleFileFinder.searchVehicleFile(vehicleSearchRepresentation);
        return Response.ok(vehicleRepresentation).build();
    }

    /**
     * Gets the filter values for vf.
     *
     * @return the filter values for vf
     */
    /*
     * To return Filter values to view
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/seachvehiclefile")
    public Response getFilterValuesForVF() {
        VehicleModelRepresentation vehicleSearchRepresentation = vehicleFileFinder.getvehicleSearchRep();
        return Response.ok(vehicleSearchRepresentation).build();
    }

    /**
     * Gets the tvv for vehicle file.
     *
     * @param vehicleFileId the vehicle file id
     * @return the tvv for vehicle file
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/tvv/{vehicleFileId}")
    public Response getTvvForVehicleFile(@PathParam("vehicleFileId") String vehicleFileId) {
        try {
            TvvRepresentation tvvObj = new TvvRepresentation();
            if (vehicleFileId != null && vehicleFileId.length() > 0) {
                long vFileId = Long.parseLong(vehicleFileId);
                TVV tvv = tvvFinder.getTvvForVehicleFile(vFileId);
                if (tvv != null) {
                    TechnicalGroup tGroup = technicalGroupFinder.getTechnicalGroupForTechnicalCase(tvv.getTechnicalCase().getEntityId());
                    if (tGroup != null) {
                        RegulationGroup rg = regulationGroupFinder.getRegulationGroupForTG(tGroup.getEntityId());
                        if (rg != null)
                            tGroup.setRegulationGroup(rg);
                        tvv.getTechnicalCase().setTechnicalGroup(tGroup);
                    }
                    tvvAssembler.doAssembleDtoFromAggregate(tvvObj, tvv);
                } else
                    return Response.status(javax.ws.rs.core.Response.Status.PRECONDITION_FAILED).build();

            }
            return Response.ok(tvvObj).build();
        } catch (Exception e) {
            logger.error("Error in retriving TVV Data", e);
            return Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Gets the vehicle file for archive.
     *
     * @param chassis the chassis
     * @param counterMark the counter mark
     * @param registration the registration
     * @param typeOfTestId the type of test id
     * @param archiveBoxId the archive box id
     * @return the vehicle file for archive
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/vehicleforarchive/{chassis}/{counterMark}/{registration}/{typeOfTestId}/{archiveBoxId}")
    public Response getVehicleFileForArchive(@PathParam("chassis") String chassis, @PathParam("counterMark") String counterMark,
            @PathParam("registration") String registration, @PathParam("typeOfTestId") Long typeOfTestId,
            @PathParam("archiveBoxId") Long archiveBoxId) {

        VehicleFile vehicleFile = vehicleFileService.vehicleFileArchive(chassis, counterMark, registration, typeOfTestId, archiveBoxId);

        if (vehicleFile == null)
            return Response.status(Status.PRECONDITION_FAILED).build();

        VehicleFileRepresentation vehicleFileRepr = new VehicleFileRepresentation();

        vehicleFileAssembler.doAssembleDtoFromAggregate(vehicleFileRepr, vehicleFile);

        return Response.ok(vehicleFileRepr).build();
    }

    /**
     * Removes the vehicle file archive.
     *
     * @param vehicleFileId the vehicle file id
     * @return the response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/vehicleforarchive/{vehicleFileId}")
    public Response removeVehicleFileArchive(@PathParam("vehicleFileId") Long vehicleFileId) {

        vehicleFileService.removeVehicleFile(vehicleFileId);
        return Response.ok().build();
    }

    /**
     * Search vehicle.
     *
     * @param chassis the chassis
     * @param counterMark the counter mark
     * @param registration the registration
     * @return the response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/vehiclefilelist/{chassis}/{countermark}/{registration}")
    public Response searchVehicle(@PathParam("chassis") String chassis, @PathParam("countermark") String counterMark,
            @PathParam("registration") String registration) {

        String getVehicleCondition = "";
        if (!"null".equals(counterMark)) {
            getVehicleCondition = getVehicleCondition + Constants.VEHICLE_COUNTERMARK_ARCHIVE + "='" + counterMark + "' OR ";
        }
        if (!"null".equals(chassis)) {
            getVehicleCondition = getVehicleCondition + Constants.VEHICLE_CHASISNUMBER_ARCHIVE + "='" + chassis + "' OR ";
        }

        if (!"null".equals(registration)) {
            getVehicleCondition = getVehicleCondition + Constants.VEHICLE_REGISTRATIONNUMBER_ARCHIVE + "='" + registration + "' OR ";
        }
        if (getVehicleCondition.trim().endsWith("OR"))
            getVehicleCondition = getVehicleCondition.substring(0, getVehicleCondition.lastIndexOf("OR"));
        logger.info("Trying to get VehicleFiles with " + getVehicleCondition);
        List<VehicleFile> vehicleFileList = vehicleFileFinder.getVehiclefileList(getVehicleCondition);

        if (vehicleFileList == null)
            return Response.status(Status.PRECONDITION_FAILED).build();

        List<VehicleFileRepresentation> vehicleFileReprList = new ArrayList<>();
        for (VehicleFile vehicleFile : vehicleFileList) {

            VehicleFileRepresentation vehicleFileRepr = new VehicleFileRepresentation();
            vehicleFileAssembler.doAssembleDtoFromAggregate(vehicleFileRepr, vehicleFile);
            vehicleFileReprList.add(vehicleFileRepr);

        }

        return Response.ok(vehicleFileReprList).build();

    }

}
