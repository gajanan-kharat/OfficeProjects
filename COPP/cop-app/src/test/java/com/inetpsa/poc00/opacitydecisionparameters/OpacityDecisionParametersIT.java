/*
 * Creation : Jan 10, 2017
 */
package com.inetpsa.poc00.opacitydecisionparameters;

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
import com.inetpsa.poc00.application.opacitydecisionparameters.OpacityDecisionParametersService;
import com.inetpsa.poc00.domain.fueltype.FuelType;
import com.inetpsa.poc00.domain.fueltype.FuelTypeFactory;
import com.inetpsa.poc00.domain.fueltype.FuelTypeRepository;
import com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParameters;
import com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParametersFactory;
import com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParametersRepository;

/**
 * The Class OpacityDecisionParametersIT.
 */
@RunWith(SeedITRunner.class)
public class OpacityDecisionParametersIT {

	/** The odpfactory. */
	@Inject
	OpacityDecisionParametersFactory odpfactory;

	/** The fuel typefactory. */
	@Inject
	FuelTypeFactory fuelTypefactory;

	/** The odp repo. */
	@Inject
	OpacityDecisionParametersRepository odpRepo;

	/** The odp service. */
	@Inject
	OpacityDecisionParametersService odpService;

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
		Assertions.assertThat(odpService).isNotNull();
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
		OpacityDecisionParameters newODP = odpfactory.createOpacityDecisionParameters();
		String higherSymbol = "TestHigherSymbol1";
		newODP.setHigherSymbol(higherSymbol);
		newODP.setFuelType(fuelType);
		assertNotNull(odpService);
		List<OpacityDecisionParameters> odpDataList = odpRepo.getAllOpacityDecisionParameters();
		logger.info("********** before save tested successfully*********");

		OpacityDecisionParameters savedODP = odpService.saveOpacityDecisionParameters(odpDataList, newODP, null);
		logger.info("**********after save tested successfully*********");

		assertNotNull(savedODP);
		/*assertTrue(odpDataList.contains(savedODP));
		assertFalse(!odpDataList.contains(savedODP));
		assertEquals(savedODP.getHigherSymbol(), higherSymbol);*/
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
		OpacityDecisionParameters newODP = odpfactory.createOpacityDecisionParameters();
		String higherSymbol = "TestHigherSymbol1";
		newODP.setHigherSymbol(higherSymbol);
		newODP.setFuelType(fuelType);
		assertNotNull(odpService);
		List<OpacityDecisionParameters> odpDataList = odpRepo.getAllOpacityDecisionParameters();
		logger.info("********** before save tested successfully*********");

		OpacityDecisionParameters savedODP = odpService.saveOpacityDecisionParameters(odpDataList, newODP, null);
		logger.info("**********after save tested successfully*********");
		odpDataList = odpRepo.getAllOpacityDecisionParameters();
		assertNotNull(odpService);
		savedODP = odpDataList.get(odpDataList.size() - 1);
		// String beforeUpdateSymbol = savedODP.getHigherSymbol();
		String afterUpdateSymbol = "AfterUpdateSymbol";
		savedODP.setHigherSymbol(afterUpdateSymbol);
		OpacityDecisionParameters updatedODP = odpService.saveOpacityDecisionParameters(odpDataList, savedODP, savedODP.getEntityId());
		assertNotNull(updatedODP);
		assertEquals(updatedODP.getHigherSymbol(), afterUpdateSymbol);

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
		/*	OpacityDecisionParameters newODP = odpfactory.createOpacityDecisionParameters();
			String higherSymbol = "TestHigherSymbol1";
			newODP.setHigherSymbol(higherSymbol);*/
		List<OpacityDecisionParameters> odpDataList = odpRepo.getAllOpacityDecisionParameters();
		assertNotNull(odpService);
		OpacityDecisionParameters codToBeDeleted = odpDataList.get(odpDataList.size() - 1);
		odpService.deleteOpacityDecisionParameters(codToBeDeleted.getEntityId());
		List<OpacityDecisionParameters> odpDataList2 = odpRepo.getAllOpacityDecisionParameters();
		assertTrue(!odpDataList2.contains(codToBeDeleted));
		assertFalse(odpDataList2.contains(codToBeDeleted));

	}

}
