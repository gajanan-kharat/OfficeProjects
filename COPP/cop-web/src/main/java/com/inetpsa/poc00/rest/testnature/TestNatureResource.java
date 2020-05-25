package com.inetpsa.poc00.rest.testnature;

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
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.testnature.TestNatureService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.testnature.TestNature;
import com.inetpsa.poc00.domain.testnature.TestNatureFactory;
import com.inetpsa.poc00.domain.testnature.TestNatureRepository;

/**
 * The Class TestNatureResource.
 */
@Path("/TestNature")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class TestNatureResource {

	/** The Constant LOGGER. */
	private static final Logger logger = LoggerFactory.getLogger(TestNatureResource.class);

	/** The test nature assembler. */
	@Inject
	private TestNatureRepresentationAssembler testNatureAssembler;

	/** The test nature finder. */
	@Inject
	private TestNatureFinder testNatureFinder;

	/** The test nature factory. */
	@Inject
	private TestNatureFactory testNatureFactory;

	/** The test nature service. */
	@Inject
	private TestNatureService testNatureService;

	/** The test nature repository. */
	@Inject
	private TestNatureRepository testNatureRepository;

	/**
	 * Save test nature.
	 * 
	 * @param testRepresentation the test representation
	 * @return the response
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/TestNatures")
	public Response saveTestNature(TestNatureRepresentation testRepresentation) {

		TestNature testnature = testNatureFactory.createTestNature();

		testNatureAssembler.doMergeAggregateWithDto(testnature, testRepresentation);
		TestNature newObj = testNatureService.saveTestNature(testnature);

		TestNatureRepresentation testrepresent = new TestNatureRepresentation();

		testNatureAssembler.doAssembleDtoFromAggregate(testrepresent, newObj);

		return Response.ok(testrepresent).build();

	}

	/**
	 * Update test nature.
	 *
	 * @param testRepresentationList the test representation list
	 * @return the response
	 */
	@POST
	@Transactional
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updatetestnatures")
	public Response updateTestNature(List<TestNatureRepresentation> testRepresentationList) {

		for (TestNatureRepresentation testRepresentation : testRepresentationList) {

			TestNature testnature = testNatureRepository.load(testRepresentation.getEntityId());
			int oldHierarchy = testnature.getHierarchy();
			testNatureAssembler.doMergeAggregateWithDto(testnature, testRepresentation);

			String result = testNatureService.updateTestNature(testnature);
			if (result != ConstantsApp.SUCCESS) {
				testnature.setHierarchy(oldHierarchy);
				return Response.status(Response.Status.PRECONDITION_FAILED).build();
			}
		}

		List<TestNatureRepresentation> testnaturelist = testNatureFinder.getAllTestNature();
		return Response.ok(testnaturelist).build();
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/TestNatures")
	public Response getStatus() {

		List<TestNatureRepresentation> testnaturelist;

		testnaturelist = testNatureFinder.getAllTestNature();

		return Response.ok(testnaturelist).build();
	}

	/**
	 * Delete test nature.
	 * 
	 * @param testId the test id
	 * @return the response
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/TestNatureDelete/{id}")
	public Response deleteTestNature(@PathParam("id") long testId) {

		try {

			/*
			 * Checking if TestNature "testId" is being used
			 * 
			 * True : testId is being used in along with Status False : testId is not being used anywhere
			 * 
			 */
			if (testNatureFinder.isTestNatureUsedInStatus(testId)) {

				logger.info("Test Nature is being used, Id : {} ", testId);

				return Response.status(Status.PRECONDITION_FAILED).build();
			}
			testNatureService.deleteTestNature(testId);
			logger.info("Test Nature is deleted Successfully, id : {}", testId);

			return Response.status(Status.OK).build();

		} catch (Exception e) {
			logger.error(" **** End: Exception occured while running deleteTestNature method ", e);

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

	}

}