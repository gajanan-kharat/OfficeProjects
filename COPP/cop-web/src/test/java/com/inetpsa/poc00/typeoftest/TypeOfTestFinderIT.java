/*
 * Creation : Nov 9, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.typeoftest;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.application.testnature.TestNatureService;
import com.inetpsa.poc00.application.vehicle.VehicleService;
import com.inetpsa.poc00.domain.status.StatusFactory;
import com.inetpsa.poc00.domain.status.StatusRepository;
import com.inetpsa.poc00.domain.testnature.TestNature;
import com.inetpsa.poc00.domain.testnature.TestNatureFactory;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTest;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTestRepository;
import com.inetpsa.poc00.domain.vehicle.Vehicle;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.rest.testnature.TestNatureFinder;
import com.inetpsa.poc00.rest.typeoftest.TypeOfTestFinder;
import com.inetpsa.poc00.rest.typeoftest.TypeOfTestRepresentation;

/**
 * TypeOfTestFinderIT
 *
 * @author mehaj
 */
@RunWith(SeedITRunner.class)
public class TypeOfTestFinderIT {

	/** The test nature service. */
	@Inject
	TestNatureService testNatureService;

	/** The test nature finder. */
	@Inject
	TestNatureFinder testNatureFinder;

	/** The test nature factory. */
	@Inject
	TestNatureFactory testNatureFactory;

	/** The status factory. */
	@Inject
	StatusFactory statusFactory;

	/** The status repository. */
	@Inject
	StatusRepository statusRepository;

	/** The type of test repository. */
	@Inject
	TypeOfTestRepository typeOfTestRepository;

	/** The type of test finder. */
	@Inject
	TypeOfTestFinder typeOfTestFinder;
	/** The type test repo. */
	@Inject
	private TypeOfTestRepository typeTestRepo;

	/** The vehicle service. */
	@Inject
	private VehicleService vehicleService;

	/**
	 * Find all type test data test.
	 */
	@Test
	public void findAllTypeTestDataTest() {
		TestNature tn = testNatureFactory.createTestNature();
		tn.setHierarchy(122);
		tn.setLabel("TOTJUnit" + Calendar.getInstance().getTime());
		tn.setType("TestNatureType" + Calendar.getInstance().getTime());
		TestNature newTn = testNatureService.saveTestNature(tn);
		TypeOfTest tot = new TypeOfTest();
		tot.setLabel("TOTUnitTesting" + Calendar.getInstance().getTime());
		tot.setTestNature(newTn);
		TypeOfTest totSaved = typeOfTestRepository.saveTypeOfTest(tot);
		List<TypeOfTestRepresentation> totList = typeOfTestFinder.findAllTypeTestData();
		Assert.assertNotEquals(0, totList.size());
		typeOfTestRepository.deleteTypeOfTest(totSaved.getEntityId());
		testNatureService.deleteTestNature(newTn.getEntityId());
	}

	/**
	 * Find type of test for label.
	 */
	@Test
	public void findTypeOfTestForLabel() {
		TestNature tn = testNatureFactory.createTestNature();
		tn.setHierarchy(12222);
		tn.setLabel("TOTLabelJUnit" + Calendar.getInstance().getTime());
		tn.setType("TestNatureType" + Calendar.getInstance().getTime());
		TestNature newTn = testNatureService.saveTestNature(tn);
		TypeOfTest tot = new TypeOfTest();
		tot.setLabel("TOTUnitTesting" + Calendar.getInstance().getTime());
		tot.setTestNature(newTn);
		TypeOfTest totsaved = typeOfTestRepository.saveTypeOfTest(tot);
		TypeOfTest typeOfTest = typeOfTestFinder.findTypeOfTestForLabel(totsaved.getLabel());
		assertNotNull(typeOfTest);
		typeOfTestRepository.deleteTypeOfTest(totsaved.getEntityId());
		testNatureService.deleteTestNature(newTn.getEntityId());
	}

	@Test
	public void testIsTypeOfTestUsed() {

		Vehicle vehicleObj = new Vehicle();
		vehicleObj.setCounterMark("testCountTVV" + Calendar.getInstance().getTime());
		vehicleObj.setChasisNumber("testChassisTVV" + Calendar.getInstance().getTime());
		vehicleObj.setRegistrationNumber("testRegTVV" + Calendar.getInstance().getTime());
		TypeOfTest typeTestObj = new TypeOfTest();
		typeTestObj.setLabel("TypeTestJunit" + Calendar.getInstance().getTime());
		TypeOfTest newTypeTest = typeTestRepo.saveTypeOfTest(typeTestObj);
		assertNotNull(newTypeTest);
		VehicleFile vfSaved = vehicleService.createVehicle(vehicleObj, newTypeTest.getEntityId());
		boolean result = typeOfTestFinder.isTypeOfTestUsed(newTypeTest.getEntityId());
		Assert.assertEquals(true, result);
		// vehicleFileRepo.deleteVehicleFile(vfSaved);

	}
}
