/*
 * Creation : Jan 10, 2017
 */
package com.inetpsa.poc00.lambdadecisionparameters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.lambdadecisionparameters.LambdaDecisionParametersService;
import com.inetpsa.poc00.domain.fueltype.FuelType;
import com.inetpsa.poc00.domain.fueltype.FuelTypeFactory;
import com.inetpsa.poc00.domain.fueltype.FuelTypeRepository;
import com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParameters;
import com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParametersFactory;
import com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParametersRepository;

/**
 * The Class LambdaDecisionParametersIT.
 */
@RunWith(SeedITRunner.class)
public class LambdaDecisionParametersIT {

	/** The ldpfactory. */
	@Inject
	LambdaDecisionParametersFactory ldpfactory;

	/** The fuel typefactory. */
	@Inject
	FuelTypeFactory fuelTypefactory;

	/** The ldp repo. */
	@Inject
	LambdaDecisionParametersRepository ldpRepo;

	/** The ldp service. */
	@Inject
	LambdaDecisionParametersService ldpService;

	/** The fuel type repo. */
	@Inject
	FuelTypeRepository fuelTypeRepo;

	/** The logger. */
	@Logging
	private Logger logger;

	/** The label. */
	String label = "testTeamlabel" + System.currentTimeMillis();

	/**
	 * My_service_is_injectable.
	 */

	@Test
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void my_service_is_injectable() {
		Assertions.assertThat(ldpService).isNotNull();
		logger.info("*********Service Injected Successfully**********");
	}

	/**
	 * Test save.
	 */
	@Test
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	@WithUser(id = "poch1", password = "poch1")

	public void testSave() {
		FuelType fuelType = fuelTypefactory.createFuelType();
		String fuelTypeLable = "TestFuel1";
		fuelType.setFuelTypeLable(fuelTypeLable);
		fuelType = fuelTypeRepo.saveFuelType(fuelType);
		LambdaDecisionParameters newLDP = ldpfactory.createLambdaDecisionParameters();
		String higherSymbol = "TestHigherSymbol1";
		newLDP.setHigherSymbol(higherSymbol);
		newLDP.setFuelType(fuelType);
		assertNotNull(ldpService);
		List<LambdaDecisionParameters> ldpDataList = ldpRepo.getAllLambdaDecisionParameters();
		logger.info("********** before save tested successfully*********");

		LambdaDecisionParameters savedLDP = ldpService.saveLambdaDecisionParameters(ldpDataList, newLDP, null);
		logger.info("**********after save tested successfully*********");

		assertNotNull(savedLDP);
		/*assertTrue(ldpDataList.contains(savedLDP));
		assertFalse(!ldpDataList.contains(savedLDP));
		assertEquals(savedLDP.getHigherSymbol(), higherSymbol);*/
		/*List<FuelType> fuelList = fuelTypeRepo.getFuelTypeByLabel(fuelLabel);
		if (!fuelList.isEmpty()) {
		
			for (FuelType fuelToBedeleted : fuelList) {
		
				fuelTypeRepo.delete(fuelToBedeleted);
			}
		}*/
		logger.info("**********save tested successfully*********");

	}

	/**
	 * Test update.
	 */
	@Test
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	@WithUser(id = "poch1", password = "poch1")
	public void testUpdate() {
		FuelType fuelType = fuelTypefactory.createFuelType();
		String fuelTypeLable = "TestFuel1";
		fuelType.setFuelTypeLable(fuelTypeLable);
		fuelType = fuelTypeRepo.saveFuelType(fuelType);
		LambdaDecisionParameters newLDP = ldpfactory.createLambdaDecisionParameters();
		String higherSymbol = "TestHigherSymbol1";
		newLDP.setHigherSymbol(higherSymbol);
		newLDP.setFuelType(fuelType);
		assertNotNull(ldpService);
		List<LambdaDecisionParameters> ldpDataList = ldpRepo.getAllLambdaDecisionParameters();
		logger.info("********** before save tested successfully*********");

		LambdaDecisionParameters savedLDP = ldpService.saveLambdaDecisionParameters(ldpDataList, newLDP, null);
		logger.info("**********after save tested successfully*********");
		ldpDataList = ldpRepo.getAllLambdaDecisionParameters();
		assertNotNull(ldpService);
		savedLDP = ldpDataList.get(ldpDataList.size() - 1);
		// String beforeUpdateSymbol = savedLDP.getHigherSymbol();
		String afterUpdateSymbol = "AfterUpdateSymbol";
		savedLDP.setHigherSymbol(afterUpdateSymbol);
		LambdaDecisionParameters updatedLDP = ldpService.saveLambdaDecisionParameters(ldpDataList, savedLDP, savedLDP.getEntityId());
		assertNotNull(updatedLDP);
		assertEquals(updatedLDP.getHigherSymbol(), afterUpdateSymbol);

		logger.info("**********Update tested successfully*********");

	}

	/**
	 * Test delete.
	 */
	@Test
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	@WithUser(id = "poch1", password = "poch1")
	public void testDelete() {
		/*	LambdaDecisionParameters newLDP = ldpfactory.createLambdaDecisionParameters();
			String higherSymbol = "TestHigherSymbol1";
			newLDP.setHigherSymbol(higherSymbol);*/
		List<LambdaDecisionParameters> ldpDataList = ldpRepo.getAllLambdaDecisionParameters();
		assertNotNull(ldpService);
		LambdaDecisionParameters codToBeDeleted = ldpDataList.get(ldpDataList.size() - 1);
		ldpService.deleteLambdaDecisionParameters(codToBeDeleted.getEntityId());
		List<LambdaDecisionParameters> ldpDataList2 = ldpRepo.getAllLambdaDecisionParameters();
		assertTrue(!ldpDataList2.contains(codToBeDeleted));
		assertFalse(ldpDataList2.contains(codToBeDeleted));

	}

}
