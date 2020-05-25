/*
 * Creation : Aug 22, 2016
 */
package com.inetpsa.poc00.rest.vehicle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

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
import com.inetpsa.poc00.application.vehicle.VehicleService;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.factory.CarFactoryRepository;
import com.inetpsa.poc00.domain.technicalcase.TechCaseRepository;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTestRepository;
import com.inetpsa.poc00.domain.vehicle.Vehicle;
import com.inetpsa.poc00.domain.vehicle.VehicleFactory;
import com.inetpsa.poc00.domain.vehicle.VehicleRepository;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatus;
import com.inetpsa.poc00.rest.carfactory.CarFactoryFinder;
import com.inetpsa.poc00.rest.carfactory.CarFactoryRepresentation;
import com.inetpsa.poc00.rest.testnature.TestNatureFinder;
import com.inetpsa.poc00.rest.tvv.TvvFinder;
import com.inetpsa.poc00.rest.tvv.TvvRepresentation;
import com.inetpsa.poc00.rest.typeoftest.TypeOfTestFinder;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileFinder;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileRepresentation;
import com.inetpsa.poc00.rest.vehiclefilestatus.VehicleFileStatusFinder;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class VehicleResource.
 */
@Path("/vehicle")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class VehicleResource {

    /** The tvv finder. */
    @Inject
    private TvvFinder tvvFinder;

    /** The vehicle finder. */
    @Inject
    private VehicleFinder vehicleFinder;

    /** The vehicle assembler. */
    @Inject
    private VehicleAssembler vehicleAssembler;

    /** The vehicle repo. */
    @Inject
    private VehicleRepository vehicleRepo;

    /** The vehicle factory. */
    @Inject
    private VehicleFactory vehicleFactory;

    /** The type of test repo. */
    @Inject
    private TypeOfTestRepository typeOfTestRepo;

    /** The type of test finder. */
    @Inject
    private TypeOfTestFinder typeOFTestFinder;

    /** The vehicle file repo. */
    @Inject
    private VehicleFileRepository vehicleFileRepo;

    /** The vehicle file finder. */
    @Inject
    private VehicleFileFinder vehicleFileFinder;

    /** The vehicle file status finder. */
    @Inject
    private VehicleFileStatusFinder vehicleFileStatusFinder;

    /** The car factory repo. */
    @Inject
    private CarFactoryRepository carFactoryRepo;

    /** The tech case repo. */
    @Inject
    private TechCaseRepository techCaseRepo;

    /** The test nature finder. */
    @Inject
    private TestNatureFinder testNatureFinder;

    /** The car factory finder. */
    @Inject
    private CarFactoryFinder carFactoryFinder;

    /** The trace resource. */
    @Inject
    TraceabilityResource traceResource;

    /** The vehicle service. */
    @Inject
    private VehicleService vehicleService;

    /** The traceability service. */
    @Inject
    private TraceabilityResource traceabilityService;

    /** The logger. */
    @Logging
    private final Logger logger = LoggerFactory.getLogger(VehicleResource.class);

    /**
     * Gets the tvv list.
     * 
     * @param typeOfTestId the type of test id
     * @param tvvLabel the tvv label
     * @return the tvv list
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/tvvList/{tvvLabel}/{typeOfTestId}")
    public Response getTvvList(@PathParam("typeOfTestId") Long typeOfTestId, @PathParam("tvvLabel") String tvvLabel) {

        List<Long> testNatureList = testNatureFinder.testnatureList(typeOfTestId);

        StringBuilder testNatureEntity = new StringBuilder("");
        for (Long testNature : testNatureList) {
            testNatureEntity = testNatureEntity.append(",").append(testNature);

        }

        testNatureEntity = new StringBuilder(testNatureEntity.substring(1));
        logger.info("Trying to get TVV from tvvLabel : " + tvvLabel + " and typeOfTestId : " + typeOfTestId);
        List<TvvRepresentation> tvvList = tvvFinder.getTvvforVehicle(testNatureEntity.toString(), tvvLabel);

        List<TvvRepresentation> tvvListToResponse = new ArrayList<>();
        Map<Double, String> versionLimitMap = new HashMap<>();

        for (TvvRepresentation tvvObj : tvvList) {

            if (!tvvListToResponse.isEmpty()) {

                ListIterator<TvvRepresentation> itr = tvvListToResponse.listIterator();
                boolean versionCheck = true;

                while (itr.hasNext()) {

                    TvvRepresentation tvvObjToResponse = itr.next();
                    if (tvvObj.getEntityId() != tvvObjToResponse.getEntityId()) {
                        if (tvvObj.getEmissionStandard().getEsLabel().equals(tvvObjToResponse.getEmissionStandard().getEsLabel())
                                && (!tvvObj.getVersion().equals(tvvObjToResponse.getVersion()))
                                && (tvvObj.getTestNature().getEntityId() == tvvObjToResponse.getTestNature().getEntityId())) {
                            if (Double.compare(tvvObj.getMaxCo2LimitTvv(), tvvObjToResponse.getMaxCo2LimitTvv()) == 0
                                    && Double.compare(tvvObj.getMinCo2LimitTvv(), tvvObjToResponse.getMinCo2LimitTvv()) == 0) {

                                Double tvvObjVersion = Double.parseDouble(tvvObj.getVersion());
                                Double tvvObjToResponseVersion = Double.parseDouble(versionLimitMap.get(tvvObj.getMaxCo2LimitTvv()));
                                if (tvvObjVersion > tvvObjToResponseVersion) {
                                    itr.remove();
                                } else {
                                    versionCheck = false;
                                }

                            }

                        }
                    } else {
                        if (!(tvvObj.getPgLabel().toUpperCase()).equals(Constants.PGLABEL_CO2)) {
                            versionCheck = false;
                        } else {
                            itr.remove();
                            break;
                        }
                    }

                }

                if (versionCheck) {
                    versionLimitMap.put(tvvObj.getMaxCo2LimitTvv(), tvvObj.getVersion());

                    tvvListToResponse.add(tvvObj);
                }

            } else {
                versionLimitMap.put(tvvObj.getMaxCo2LimitTvv(), tvvObj.getVersion());
                tvvListToResponse.add(tvvObj);
            }
        }
        logger.info("--------Sending TVVList to UI-------");
        return Response.ok(tvvListToResponse).build();

    }

    /**
     * Creates the vehicle.
     * 
     * @param vehicleRep the vehicle rep
     * @return the response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/vehiclelist")
    public Response createVehicle(VehicleRepresentation vehicleRep) {

        Vehicle vehicleObj = vehicleFactory.createVehicle();

        vehicleAssembler.doMergeAggregateWithDto(vehicleObj, vehicleRep);

        VehicleFile vehicleFileObj = vehicleService.createVehicle(vehicleObj, vehicleRep.getTypeOfTestId());

        if (vehicleFileObj != null && vehicleFileObj.getEntityId() != null) {
            String newValue = "UP: " + vehicleFileObj.getVehicle().getCarfactory() + ", Année modèle: " + vehicleFileObj.getVehicle().getModelYear()
                    + ", CO2 individuel véhicule (WLTP):" + vehicleFileObj.getVehicle().getmCO2I();
            traceabilityService.saveVehicleFileHistory(vehicleFileObj, newValue, ConstantsApp.VEHICLEFILE_HISTORY_DESCRIPTION);

            return Response.ok().build();
        }
        return Response.status(Status.PRECONDITION_FAILED).build();

    }

    /**
     * Search vehicle.
     * 
     * @param counterMark the counter mark
     * @param chassis the chassis
     * @param registration the registration
     * @param typeOfTestId the type of test id
     * @return the response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/vehicledetail/{countermark}/{chassis}/{registration}/{typeOfTestId}")
    public Response searchVehicle(@PathParam("countermark") String counterMark, @PathParam("chassis") String chassis,
            @PathParam("registration") String registration, @PathParam("typeOfTestId") Long typeOfTestId) {

        String getVehicleCondition = "";
        if (!"null".equals(counterMark)) {
            getVehicleCondition = getVehicleCondition + Constants.VEHICLE_COUNTERMARK + "='" + counterMark + "' OR ";
        }
        if (!"null".equals(chassis)) {
            getVehicleCondition = getVehicleCondition + Constants.VEHICLE_CHASISNUMBER + "='" + chassis + "' OR ";
        }

        if (!"null".equals(registration)) {
            getVehicleCondition = getVehicleCondition + Constants.VEHICLE_REGISTRATIONNUMBER + "='" + registration + "' OR ";
        }
        if (getVehicleCondition.trim().endsWith("OR"))
            getVehicleCondition = getVehicleCondition.substring(0, getVehicleCondition.lastIndexOf("OR"));
        logger.info("Trying to get Vehicle with " + getVehicleCondition);
        Vehicle vehicleObj = vehicleFinder.getVehicleId(getVehicleCondition);
        List<VehicleFileRepresentation> vehicleFileList = null;
        if (vehicleObj != null) {
            logger.info("Trying to get VehicleFile");
            VehicleFile vehicleFileRepObj = vehicleFileFinder.getVehicleFile(vehicleObj.getEntityId(), typeOfTestId);
            if (vehicleFileRepObj != null)
                return Response.status(Status.PRECONDITION_FAILED).build();

            vehicleFileList = createVehicleFile(typeOfTestId, vehicleObj);
            if (vehicleFileList == null)
                return Response.status(Status.PRECONDITION_FAILED).build();
        }

        return Response.ok(vehicleFileList).build();

    }

    /**
     * Creates the vehicle file.
     * 
     * @param typeOfTest the type of test
     * @param vehicleObj the vehicle obj
     * @return the list
     */
    private List<VehicleFileRepresentation> createVehicleFile(Long typeOfTest, Vehicle vehicleObj) {

        VehicleFile vehicleFileObj = new VehicleFile();
        vehicleFileObj.setVehicle(vehicleObj);
        vehicleFileObj.setTypeOfTest(typeOfTestRepo.load(typeOfTest));
        VehicleFileStatus vehicleFileStatus = vehicleFileStatusFinder.getVehicleFileStatusbyLabel(Constants.VEHICLEFILESTATUS_LABEL);
        if (vehicleFileStatus == null) {
            return null;
        }
        vehicleFileObj.setVehicleFileStatus(vehicleFileStatus);
        vehicleFileObj.setCreationDate(Calendar.getInstance().getTime());
        VehicleFile newVwhicleFile = vehicleFileRepo.saveVehicle(vehicleFileObj);

        String newValue = "UP: " + newVwhicleFile.getVehicle().getCarfactory() + ", Année modèle: " + newVwhicleFile.getVehicle().getModelYear()
                + ", CO2 individuel véhicule (WLTP):" + newVwhicleFile.getVehicle().getmCO2I();

        traceResource.saveVehicleFileHistory(newVwhicleFile, newValue, Constants.VEHICLEFILE_HISTORY_DESCRIPTION);
        return vehicleFileFinder.getVehicleFileList(vehicleObj.getEntityId());

    }

    /**
     * Gets the tvv factory.
     * 
     * @param tvvId the tvv id
     * @return the tvv factory
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/tvvfactory/{tvvId}")
    public Response getTvvFactory(@PathParam("tvvId") Long tvvId) {

        List<CarFactoryRepresentation> factoryList = carFactoryFinder.getFactoriesForTVV(tvvId);

        return Response.ok(factoryList).build();

    }

    /**
     * Gets the tvv factory by tech case.
     *
     * @param tcId the tc id
     * @return the tvv factory by tech case
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/tvvfactorybytc/{tcId}")
    public Response getTvvFactoryByTechCase(@PathParam("tcId") Long tcId) {
        TechnicalCase technicalCase = techCaseRepo.loadTechnicalCase(tcId);
        List<CarFactoryRepresentation> factoryList = carFactoryFinder.getFactoriesForTVV(technicalCase.getTvv().getEntityId());

        return Response.ok(factoryList).build();

    }

    /**
     * Gets the vehicle.
     *
     * @param vehicleFileId the vehicle file id
     * @return the vehicle
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/vehicledetail/{vehicleFileId}")
    public Response getVehicle(@PathParam("vehicleFileId") Long vehicleFileId) {

        VehicleFileRepresentation vehicleFile = vehicleFileFinder.getVehicleFileById(vehicleFileId);
        return Response.ok(vehicleFile).build();
    }

    /**
     * Creates the vehicle.
     * 
     * @param vehicleRep the vehicle rep
     * @return the response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/vehicleobject")
    public Response updateVehicle(VehicleRepresentation vehicleRep) {

        Vehicle vehicleObj = vehicleRepo.loadVehicle(vehicleRep.getEntityId());

        vehicleAssembler.doMergeAggregateWithDto(vehicleObj, vehicleRep);

        Vehicle vehicleObjSaved = vehicleService.saveVehicle(vehicleObj);
        VehicleRepresentation vehicleRepresentation = new VehicleRepresentation();

        if (vehicleObjSaved != null) {
            vehicleAssembler.doAssembleDtoFromAggregate(vehicleRepresentation, vehicleObjSaved);
            return Response.ok(vehicleRepresentation).build();
        }

        return Response.status(Status.PRECONDITION_FAILED).build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/modelyear")
    public Response getModelYear() {

        List<String> modelYearList = vehicleFinder.getModelYear();
        return Response.ok(modelYearList).build();
    }
}
