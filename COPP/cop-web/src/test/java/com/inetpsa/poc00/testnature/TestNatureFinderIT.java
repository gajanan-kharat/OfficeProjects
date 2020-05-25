/*
 * Creation : Nov 7, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.testnature;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.application.testnature.TestNatureService;
import com.inetpsa.poc00.domain.status.StatusFactory;
import com.inetpsa.poc00.domain.status.StatusRepository;
import com.inetpsa.poc00.domain.testnature.TestNature;
import com.inetpsa.poc00.domain.testnature.TestNatureFactory;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTest;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTestRepository;
import com.inetpsa.poc00.rest.testnature.TestNatureFinder;
import com.inetpsa.poc00.rest.testnature.TestNatureRepresentation;

/**
 * TODO : Description.
 *
 * @author mehaj
 */
@RunWith(SeedITRunner.class)
public class TestNatureFinderIT {
	
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

	/**
	 * Gets the test nature by type.
	 *
	 * @return the test nature by type
	 */
	@Test
	public void getTestNatureByType() {
		TestNature tn = testNatureFactory.createTestNature();
		tn.setHierarchy(889);
		tn.setLabel("TestITTestNature" + Calendar.getInstance().getTime());
		tn.setType("TestNatureType" + Calendar.getInstance().getTime());
		TestNature newTn = testNatureService.saveTestNature(tn);
		TestNature savedTn = testNatureFinder.findTestNature(newTn.getType());
		assertNotNull(savedTn);
	}

	/**
	 * Checks if is test nature used in type test.
	 */
	@Test
	public void isTestNatureUsedInTypeTest() {

		TestNature tn = testNatureFactory.createTestNature();
		tn.setHierarchy(12);
		tn.setLabel("TestITTestNature1" + Calendar.getInstance().getTime());
		tn.setType("TestNatureType2" + Calendar.getInstance().getTime());
		TestNature newTn = testNatureService.saveTestNature(tn);
		TypeOfTest tot = new TypeOfTest();
		tot.setLabel("TOTUnitTesting" + Calendar.getInstance().getTime());
		tot.setTestNature(newTn);
		typeOfTestRepository.saveTypeOfTest(tot);
		boolean tnusedintot = testNatureFinder.isTestNatureUsedInTypeTest(newTn.getEntityId());
		assertTrue(tnusedintot);
	}

	/**
	 * Gets the all test nature test.
	 *
	 * @return the all test nature test
	 */
	@Test
	public void getAllTestNatureTest() {
		TestNature tn = testNatureFactory.createTestNature();
		tn.setHierarchy(9);
		tn.setLabel("TestNatureForJunit" + Calendar.getInstance().getTime());
		tn.setType("TestNatureTypeJUnit" + Calendar.getInstance().getTime());
		TestNature newTn = testNatureService.saveTestNature(tn);
		List<TestNatureRepresentation> savedTnList = testNatureFinder.getAllTestNature();
		Assert.assertNotEquals(0, savedTnList.size());
	}

	/**
	 * Testnature list.
	 */
	@Test
	public void testnatureList() {

		TestNature tn = testNatureFactory.createTestNature();
		tn.setHierarchy(90);
		tn.setLabel("TestITTestNature2" + Calendar.getInstance().getTime());
		tn.setType("TestNatureType3" + Calendar.getInstance().getTime());
		TestNature newTn = testNatureService.saveTestNature(tn);
		TestNature tn1 = testNatureFactory.createTestNature();
		tn.setHierarchy(91);
		tn.setLabel("TestITTestNatureJunit" + Calendar.getInstance().getTime());
		tn.setType("TestNatureTypeJunit" + Calendar.getInstance().getTime());
		TestNature newTn1 = testNatureService.saveTestNature(tn);
		TypeOfTest tot = new TypeOfTest();
		tot.setLabel("TOTUnitTesting" + Calendar.getInstance().getTime());
		tot.setTestNature(newTn1);
		TypeOfTest totSaved = typeOfTestRepository.saveTypeOfTest(tot);
		List<Long> tnListLowerHierarchy = testNatureFinder.testnatureList(totSaved.getEntityId());
		Assert.assertNotEquals(0, tnListLowerHierarchy.size());
	}
}
