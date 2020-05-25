/*
 * Creation : Sep 22, 2016
 */
package com.inetpsa.poc00.rest.typeoftest;

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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.typeoftest.TypeOfTestService;
import com.inetpsa.poc00.domain.testnature.TestNature;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTest;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTestFactory;
import com.inetpsa.poc00.rest.testnature.TestNatureFinder;

/**
 * TypeOfTestResource class
 * 
 * @author mehaj
 */

@Path("/typeoftest")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class TypeOfTestResource {

    /** The typeOfTestFinder. */
    @Inject
    TypeOfTestFinder typeOfTestFinder;

    /** The logger. */
    @Logging
    private Logger logger;

    /** The test nature finder. */
    @Inject
    private TestNatureFinder testnaturefinder;

    /** The typeOfTestFactory. */
    @Inject
    private TypeOfTestFactory typeOfTestFactory;

    /** The TypeOfTest assembler. */
    @Inject
    private TypeOfTestReprAssembler typeOfTestAssembler;

    @Inject
    private TypeOfTestService typeOfTestService;

    /**
     * @return the typeOfTest data
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/typeoftestlist")
    public Response getTypeOfTestData(@Context HttpServletResponse hsr, @Context UriInfo info) {
        List<TypeOfTestRepresentation> typeOfTestList;

        logger.info("To get data from TypeOfTest");
        typeOfTestList = typeOfTestFinder.findAllTypeTestData();
        logger.info("sending TypeOfTest data to UI");
        return Response.ok(typeOfTestList).build();
    }

    /**
     * Save status.
     * 
     * @param ManageTypeOfTestRequestDTO the TypeOfTestRequest request dto
     * @return the response
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/savetypeoftestlist")
    public Response saveTypeOfTestList(ManageTypeOfTestRequestDTO manageTypeOfTestRequestDTO) {
        for (TypeOfTestRepresentation typeOfTestRepresentation : manageTypeOfTestRequestDTO.getTypeOfTestRepresentationList()) {
            TestNature testNatureNew = testnaturefinder.findTestNature(typeOfTestRepresentation.getTestNatureType());

            TypeOfTest typeOfTest = typeOfTestFinder.findTypeOfTestForLabel(typeOfTestRepresentation.getLabel());

            if (typeOfTestRepresentation.getTypeOfTestId() == null || typeOfTestRepresentation.getTypeOfTestId() == 0) {
                if (typeOfTest == null) {
                    typeOfTest = typeOfTestFactory.createTypeOfTest();
                    typeOfTestRepresentation.setTypeOfTestId(typeOfTest.getEntityId());
                    typeOfTestAssembler.doMergeAggregateWithDto(typeOfTest, typeOfTestRepresentation);
                }
                typeOfTest.setTestNature(testNatureNew);
                typeOfTestService.saveTypeOfTest(typeOfTest);

            }
        }
        List<TypeOfTestRepresentation> typeOfTestRepresentationForClient = getTypeTestList();
        return Response.ok(typeOfTestRepresentationForClient).build();
    }

    /**
     * Delete type of test.
     * 
     * @param TypeOfTest id to delete
     * @return the response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/TypeTestDelete/{id}")
    public Response deleteTypeTest(@PathParam("id") long typeId) {

        try {
            if (!typeOfTestFinder.isTypeOfTestUsed(typeId)) {
                typeOfTestService.delete(typeId);
                logger.info("Type Of Test is deleted Successfully, id : {}", typeId);

                return Response.status(Status.OK).build();

            }
            return Response.status(Status.PRECONDITION_FAILED).build();

        } catch (Exception e) {
            logger.error(" **** End: Exception occured while running deleteTypeOfTest method ", e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    /**
     * Method to return type of test list after save
     */
    private List<TypeOfTestRepresentation> getTypeTestList() {

        List<TypeOfTestRepresentation> typeOfTestList;

        logger.info("To get data from TypeOfTest");
        typeOfTestList = typeOfTestFinder.findAllTypeTestData();

        logger.info("sending TypeOfTest data to UI");
        return typeOfTestList;

    }
}
