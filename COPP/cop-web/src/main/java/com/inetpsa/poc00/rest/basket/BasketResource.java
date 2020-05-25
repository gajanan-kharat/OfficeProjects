package com.inetpsa.poc00.rest.basket;

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
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.basket.BasketService;
import com.inetpsa.poc00.domain.basket.Basket;
import com.inetpsa.poc00.domain.preparationfile.PreparationFile;
import com.inetpsa.poc00.domain.preparationresult.PreparationResult;
import com.inetpsa.poc00.domain.preparationresult.PreparationResultRepository;
import com.inetpsa.poc00.rest.preparationchecklist.PreparationCheckListRepresentation;
import com.inetpsa.poc00.rest.preparationfile.PreparationFileAssembler;
import com.inetpsa.poc00.rest.preparationfile.PreparationFileFinder;
import com.inetpsa.poc00.rest.preparationfile.PreparationFileRepresentation;
import com.inetpsa.poc00.rest.preparationfile.PreparationFileUtil;
import com.inetpsa.poc00.rest.preparationresult.PreparationResultRepresentation;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileFinder;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileRepresentation;
import com.inetpsa.poc00.traceability.TraceabilityFinder;
import com.inetpsa.poc00.traceability.TraceabilityRepresentation;

/**
 * The Class BasketResource.
 */
@Path("/basket")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class BasketResource {

    /** The vehicle finder. */
    @Inject
    private VehicleFileFinder vehicleFileFinder;

    /** The basket service. */
    @Inject
    private BasketService basketService;

    /** The trace finder. */
    @Inject
    private TraceabilityFinder traceFinder;

    /** The prep file finder. */
    @Inject
    private PreparationFileFinder prepFileFinder;

    /** The prep file assembler. */
    @Inject
    private PreparationFileAssembler prepFileAssembler;

    /** The prep result repo. */
    @Inject
    private PreparationResultRepository prepResultRepo;

    /** The logger. */
    @Logging
    private final Logger logger = LoggerFactory.getLogger(BasketResource.class);

    /**
     * Gets the basket.
     * 
     * @param userId the user id
     * @return the basket
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/basketdetails/{userId}")
    public Response getBasket(@PathParam("userId") String userId) {

        logger.info("Loading Basket Details for User Id : {}", userId);
        Basket basketObj = basketService.getBasketForUser(userId);
        List<VehicleFileRepresentation> vehicleFileList = null;
        if (basketObj != null)
            vehicleFileList = vehicleFileFinder.getVehicleFileByBaket(basketObj.getEntityId());

        return Response.ok(vehicleFileList).build();
    }

    /**
     * Removes the vehicle from basket.
     * 
     * @param vehicleFileId the vehicle file id
     * @return the response
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/baskets/{vehicleFileId}")
    public Response removeVehicleFromBasket(@PathParam("vehicleFileId") Long vehicleFileId) {

        basketService.deleteBasket(vehicleFileId);
        return Response.ok(Status.OK).build();

    }

    /**
     * Gets the basketcount.
     * 
     * @param userId the user id
     * @return the basketcount
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/BasketCount/{userId}")
    public Response getBasketcount(@PathParam("userId") String userId) {

        Long count = basketService.getVehicleFilesCountForBasket(userId);
        BasketRepresentation br = new BasketRepresentation();
        br.setVehicleCount(count);
        return Response.ok(br).build();
    }

    /**
     * Gets the basket.
     * 
     * @param userId the user id
     * @return the basket
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/offlineData/{userId}")
    public BasketRepresentation getOfflineBasket(@PathParam("userId") String userId) {

        logger.info("Loading Basket Details for User Id : {}", userId);

        Basket basketObj = basketService.getBasketForUser(userId);

        BasketRepresentation offlineObj = new BasketRepresentation();

        List<VehicleFileRepresentation> vfRList = new ArrayList<>();

        if (basketObj != null) {

            offlineObj.setEntityId(basketObj.getEntityId());
            offlineObj.setUserId(userId);
            offlineObj.setVehicleCount(basketService.getVehicleFilesCountForBasket(userId));

            List<VehicleFileRepresentation> vehicleFileList = vehicleFileFinder.getVehicleFileByBaket(basketObj.getEntityId());

            for (VehicleFileRepresentation vehicleFile : vehicleFileList) {

                // Vehicle File History
                List<TraceabilityRepresentation> historyList = traceFinder.getVehicleFileHistory(vehicleFile.getEntityId());
                vehicleFile.setVehicleFileHistory(historyList);

                // Preparation File List for the Vehicle File
                PreparationFile preparationFile = prepFileFinder.findPrepFileByVehicleFileId(vehicleFile.getEntityId());
                PreparationFileRepresentation prepFileRepresentation = new PreparationFileRepresentation();
                if (preparationFile != null) {
                    prepFileAssembler.doAssembleDtoFromAggregate(prepFileRepresentation, preparationFile);
                    PreparationFileUtil.filterCheckListByFuelType(prepFileRepresentation);
                }

                vehicleFile.setPrepFileRepresentation(prepFileRepresentation);

                vfRList.add(vehicleFile);

            }

            offlineObj.setVehicleFileRepList(vfRList);
        }

        return offlineObj;
    }

    /**
     * Sync offline data.
     * 
     * @param offlineData the offline data
     * @return the response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/syncOfflineData")
    public Response syncOfflineData(BasketRepresentation offlineData) {

        logger.info("Saving Offline Data");

        if (offlineData != null && offlineData.getVehicleFileRepList() != null && !offlineData.getVehicleFileRepList().isEmpty()) {

            for (VehicleFileRepresentation vfr : offlineData.getVehicleFileRepList()) {
                saveOfflinePreparationResult(vfr);
            }
        }

        logger.info("Oflfine Data Successfully Saved");

        return Response.ok().build();

    }

    /**
     * Save offline preparation result.
     *
     * @param vfr the vfr
     */
    private void saveOfflinePreparationResult(VehicleFileRepresentation vfr) {

        logger.info("Vehicle File id : {}", vfr.getEntityId());

        if (vfr.getPrepFileRepresentation() != null && vfr.getPrepFileRepresentation().getPrepCheckListRepresentation() != null) {
            for (PreparationCheckListRepresentation pfr : vfr.getPrepFileRepresentation().getPrepCheckListRepresentation()) {

                logger.info("Preparation File Check List id : {}", pfr.getEntityId());

                for (PreparationResultRepresentation prp : pfr.getPreparationResultList()) {

                    PreparationResult pr = prepResultRepo.load(prp.getEntityId());

                    logger.info("Old Value : {}", pr.getValue());

                    pr.setValue(prp.getValue());
                    prepResultRepo.savePrepResultList(pr);

                    logger.info("New Value : {}", prp.getValue());
                }
            }
        }
    }
}
