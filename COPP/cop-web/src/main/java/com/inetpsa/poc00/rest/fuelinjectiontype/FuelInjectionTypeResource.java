package com.inetpsa.poc00.rest.fuelinjectiontype;

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
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjctnTypeFactory;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjctnTypeRepository;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjectionType;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class FuelInjectionTypeResource.
 */
@Path("/fuelInjectTypeReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class FuelInjectionTypeResource {

    /** The fuel inject type finder. */
    @Inject
    FuelInjectionTypeFinder fuelInjectTypeFinder;

    /** The fuel injection type assembler. */
    @Inject
    FuelInjectionTypeAssembler fuelInjectionTypeAssembler;

    /** The repo. */
    @Inject
    FuelInjctnTypeRepository repo;

    /** The fuel injection typefactory. */
    @Inject
    FuelInjctnTypeFactory fuelInjectionTypefactory;

    /** The trace resource. */
    @Inject
    TraceabilityResource traceResource;

    /** The fuel injection type finder. */
    @Inject
    FuelInjectionTypeFinder fuelInjectionTypeFinder;

    /** The logger. */
    @Logging
    private Logger logger;

    /**
     * Gets the all fuel injection type.
     * 
     * @param hsr the hsr
     * @param info the info
     * @return the all fuel injection type
     */
    @GET
    @Path("/AllFuelInjectionTypes")
    public Response getAllFuelInjectionType(@Context HttpServletResponse hsr, @Context UriInfo info) {

        List<FuelInjectionTypeRepresentation> fuelInjectionTypeList;

        fuelInjectionTypeList = fuelInjectTypeFinder.getAllFuelInjectionTypes();

        return Response.ok(fuelInjectionTypeList).build();
    }

    /**
     * Save fuel injection type.
     * 
     * @param fuelInjectionTypeDto the fuel injection type dto
     * @return the response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/FuelInjectionTypes")
    public Response saveFuelInjectionType(FuelInjectionTypeDto fuelInjectionTypeDto) {

        FuelInjectionTypeRepresentation fuelInjectionTypeRepresentationResponse = fuelInjectionTypeDto.getFuelInjectionTypeRepresentationList()
                .get(0);
        for (FuelInjectionTypeRepresentation fuelInjectionTypeRepresentation : fuelInjectionTypeDto.getFuelInjectionTypeRepresentationList()) {

            List<FuelInjectionTypeRepresentation> FuelInjectionTypeData = fuelInjectionTypeFinder
                    .findFuelInjectionTypeDataByLabel(fuelInjectionTypeRepresentation.getLabel());
            if (FuelInjectionTypeData.size() > 0) {

                if (FuelInjectionTypeData.get(0).getEntityId() == fuelInjectionTypeRepresentation.getEntityId()) {

                    // update
                    FuelInjectionType oldFuelInjectionTyoe = repo.load(fuelInjectionTypeRepresentation.getEntityId());
                    FuelInjectionType updatedFuelInjectionType = assembleFromRepresentation(fuelInjectionTypeRepresentation);

                    traceResource.historyForUpdate(oldFuelInjectionTyoe, updatedFuelInjectionType, Constants.SPECIFICCOP_SCREEN_ID);
                    repo.saveFuelInjctnType(updatedFuelInjectionType);
                    fuelInjectionTypeRepresentationResponse = fuelInjectionTypeRepresentation;

                } else {
                    // error
                    fuelInjectionTypeRepresentation.setLabel(null);
                    return Response.ok(fuelInjectionTypeRepresentation).build();
                }
            } else if (fuelInjectionTypeRepresentation.getEntityId() == null) {

                // save

                FuelInjectionType updatedFuelInjectionType = assembleFromRepresentation(fuelInjectionTypeRepresentation);
                updatedFuelInjectionType = repo.saveFuelInjctnType(updatedFuelInjectionType);
                logger.info("******************************************** Saving History for FuelInjectionType, Id : ",
                        updatedFuelInjectionType.getEntityId());
                traceResource.historyForSave(updatedFuelInjectionType, Constants.SPECIFICCOP_SCREEN_ID);
                fuelInjectionTypeRepresentationResponse = fuelInjectionTypeRepresentation;

            } else {
                // update

                FuelInjectionType oldFuelInjectionTyoe = repo.load(fuelInjectionTypeRepresentation.getEntityId());
                FuelInjectionType updatedFuelInjectionType = assembleFromRepresentation(fuelInjectionTypeRepresentation);

                traceResource.historyForUpdate(oldFuelInjectionTyoe, updatedFuelInjectionType, Constants.SPECIFICCOP_SCREEN_ID);
                repo.saveFuelInjctnType(updatedFuelInjectionType);
                fuelInjectionTypeRepresentationResponse = fuelInjectionTypeRepresentation;

            }

        }
        logger.info("Sucessfully saved FuelInjectionType data");
        return Response.ok(fuelInjectionTypeRepresentationResponse).build();

    }

    /**
     * Update fuel injection type.
     * 
     * @param fuelInjectionTypeRepresentation the fuel injection type representation
     * @return the fuel injection type
     */
    private FuelInjectionType assembleFromRepresentation(FuelInjectionTypeRepresentation fuelInjectionTypeRepresentation) {
        FuelInjectionType fuelObj = fuelInjectionTypefactory.createFuelInjctnType();
        fuelInjectionTypeAssembler.mergeAggregateWithDto(fuelObj, fuelInjectionTypeRepresentation);

        return fuelObj;

    }

    /**
     * Delete fuel injection type.
     * 
     * @param entityId the entity id
     * @return the response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/FuelInjectionType/{entityId}")
    public Response deleteFuelInjectionType(@PathParam("entityId") String entityId) {

        Long id = Long.parseLong(entityId);
        FuelInjectionType ObjToDelete = repo.load(id);
        if (ObjToDelete.getEmissionStandards().isEmpty() && ObjToDelete.getFactorCoefficentList().isEmpty()
                && ObjToDelete.getPollutantGasLimitList().isEmpty() && ObjToDelete.getTvvList().isEmpty()) {
            int deletedRow = repo.deleteFuelInjctnType(id);
            if (deletedRow > 0) {
                logger.info("History For Deleting FuelInjectionType Object, Id : ", id);

                traceResource.historyForDelete(ObjToDelete, Constants.SPECIFICCOP_SCREEN_ID);

                return Response.ok().build();
            }
            logger.error("Error occured while deleting data");
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }
        logger.warn("Can't delete FuelInjection as used in other table : foreign key constraint");
        return Response.status(Response.Status.PRECONDITION_FAILED).build();

    }

    /**
     * Gets the fuel injection types.
     * 
     * @return the fuel injection types
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/AllFuelInjectionTypes")
    public Response getFuelInjectionTypes() {
        List<FuelInjectionTypeRepresentation> listToReturn = fuelInjectionTypeFinder.getAllFuelInjectionTypes();

        return Response.ok(listToReturn).build();

    }

    /**
     * Gets the all fi types for es.
     * 
     * @param emsId the ems id
     * @return the all fi types for es
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/AllFuelInjectionTypes/{emsId}")
    public Response getAllFITypesForES(@PathParam("emsId") String emsId) {
        List<FuelInjectionTypeRepresentation> listToReturn = null;
        if (emsId != null) {
            long id = Long.parseLong(emsId);
            listToReturn = fuelInjectionTypeFinder.getAllFITypesForES(id);
        }

        return Response.ok(listToReturn).build();

    }

    /**
     * Gets the fuel injection data.
     * 
     * @param hsr the hsr
     * @param info the info
     * @return the fuel injection data
     */
    @GET
    @Path("/getFuelInjectionData")
    public Response getFuelInjectionData(@Context HttpServletResponse hsr, @Context UriInfo info) {
        logger.info("Trying to get FuelInjectiontype data to show in Motour drop down");
        List<FuelInjectionTypeRepresentation> fuelInjectionTypeList = fuelInjectionTypeFinder.findAllFuelInjectionTypeData();

        return Response.ok(fuelInjectionTypeList).build();

    }
}
