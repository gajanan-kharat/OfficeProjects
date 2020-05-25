package com.inetpsa.poc00.rest.country;

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
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.country.CountryService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.country.Country;
import com.inetpsa.poc00.domain.country.CountryFactory;
import com.inetpsa.poc00.domain.country.CountryRepository;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class CountryResource.
 */
@Path("/PaysReference")

public class CountryResource {

    /** The country finder. */
    @Inject
    CountryFinder countryFinder;

    /** The country factory. */
    @Inject
    CountryFactory countryFactory;

    /** The country assembler. */
    @Inject
    CountryAssembler countryAssembler;

    /** The country repository. */
    @Inject
    CountryRepository countryRepository;

    /** The trace resource. */
    @Inject
    TraceabilityResource traceResource;
    /** The CountryService. */
    @Inject
    CountryService countryService;
    /** The logger. */
    @Logging
    private Logger logger;

    /**
     * Gets the pays data.
     * 
     * @param hsr the hsr
     * @param info the info
     * @return the pays data
     */
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    @GET
    @Path("/PaysList")
    public Response getPaysData() {
        List<CountryRepresentation> countryList;

        logger.info("To get value from Country");
        countryList = countryFinder.findAllPaysData();

        logger.info("sending Country value to UI");
        return Response.ok(countryList).build();
    }

    /**
     * Save pays data.
     * 
     * @param countryRequestDto the country request dto
     * @return the response
     */
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/PaysList")
    public Response savePaysData(ManageCountryRequestDto countryRequestDto) {

        logger.info("trying to save data in Country table");
        for (CountryRepresentation countryRepresentation : countryRequestDto.getCountryRepresentationList()) {
            Country countryObj = assembleFromRepresentation(countryRepresentation);
            String response = countryService.saveCountry(countryObj);
            if (response != ConstantsApp.SUCCESS) {
                countryAssembler.doAssembleDtoFromAggregate(countryRepresentation, countryObj);
                countryRepresentation.setDuplicate(true);
                return Response.ok(countryRepresentation).build();
            }
        }
        logger.info("Sucessfully saved Country data");
        return Response.ok(countryRequestDto.getCountryRepresentationList().get(0)).build();

    }

    /**
     * Assemble from representation.
     * 
     * @param countryRepresentation the country representation
     * @return the country
     */
    private Country assembleFromRepresentation(CountryRepresentation countryRepresentation) {
        Country country = countryFactory.createCountry();
        countryAssembler.mergeAggregateWithDto(country, countryRepresentation);
        return country;

    }

    /**
     * Delete pays data.
     * 
     * @param countryRequestDto the country request dto
     * @return the response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/Pays")
    public Response deletePaysData(ManageCountryRequestDto countryRequestDto) {
        try {
            logger.info("trying to delete data from Country table");
            for (CountryRepresentation countryRepresentation : countryRequestDto.getCountryRepresentationList()) {

                String result = countryService.deleteCountry(countryRepresentation.getEntityId());
                if (result != ConstantsApp.SUCCESS) {
                    return Response.status(Response.Status.PRECONDITION_FAILED).build();
                }

            }
            return Response.ok().build();
        } catch (Exception e) {
            logger.error(" Error occured while deleting data ", e);
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }
    }

    /**
     * Gets the all country names.
     * 
     * @return the all country names
     */
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    @GET
    @Path("/countryNames")
    public Response getAllCountryNames() {

        List<String> countryList;

        logger.info("To get value from Country");
        countryList = countryFinder.getAllCountryNames();

        logger.info("sending Country value to UI");
        return Response.ok(countryList).build();
    }

    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    @GET
    @Path("/countryList/{technicalCaseId}")
    public Response getCountryList(@PathParam("technicalCaseId") Long technicalCaseId) {

        List<Country> countryList = countryFinder.countryByTechnicalCase(technicalCaseId);
        List<CountryRepresentation> countryRepList = new ArrayList<>();
        for (Country country : countryList) {
            CountryRepresentation countryRep = new CountryRepresentation();
            countryAssembler.doAssembleDtoFromAggregate(countryRep, country);

            countryRepList.add(countryRep);
        }
        return Response.ok(countryRepList).build();
    }

}
