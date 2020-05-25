/*
 * Creation : Jan 10, 2017
 */
package com.inetpsa.poc00.codecisionparameters;

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
import com.inetpsa.poc00.application.codecisionparameters.CODecisionParametersService;
import com.inetpsa.poc00.domain.codecisionparameters.CODecisionParameters;
import com.inetpsa.poc00.domain.codecisionparameters.CODecisionParametersFactory;
import com.inetpsa.poc00.domain.codecisionparameters.CODecisionParametersRepository;
import com.inetpsa.poc00.domain.fueltype.FuelType;
import com.inetpsa.poc00.domain.fueltype.FuelTypeFactory;
import com.inetpsa.poc00.domain.fueltype.FuelTypeRepository;

/**
 * The Class CODecisionParametersIT.
 */
@RunWith(SeedITRunner.class)
public class CODecisionParametersIT {

	/** The cdpfactory. */
	@Inject
	CODecisionParametersFactory cdpfactory;

	/** The fuel typefactory. */
	@Inject
	FuelTypeFactory fuelTypefactory;

	/** The cdp repo. */
	@Inject
	CODecisionParametersRepository cdpRepo;

	/** The cdp service. */
	@Inject
	CODecisionParametersService cdpService;

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
		Assertions.assertThat(cdpService).isNotNull();
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
		CODecisionParameters newCDP = cdpfactory.createCODecisionParameters();
		String higherSymbol = "TestHigherSymbol1";
		newCDP.setHigherSymbol(higherSymbol);
		newCDP.setFuelType(fuelType);
		assertNotNull(cdpService);
		List<CODecisionParameters> cdpDataList = cdpRepo.getAllCODecisionParameters();
		logger.info("********** before save tested successfully*********");

		CODecisionParameters savedCDP = cdpService.saveCODecisionParameters(cdpDataList, newCDP, null);
		logger.info("**********after save tested successfully*********");

		assertNotNull(savedCDP);
		/*assertTrue(cdpDataList.contains(savedCDP));
		assertFalse(!cdpDataList.contains(savedCDP));
		assertEquals(savedCDP.getHigherSymbol(), higherSymbol);*/
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
		CODecisionParameters newCDP = cdpfactory.createCODecisionParameters();
		String higherSymbol = "TestHigherSymbol1";
		newCDP.setHigherSymbol(higherSymbol);
		newCDP.setFuelType(fuelType);
		assertNotNull(cdpService);
		List<CODecisionParameters> cdpDataList = cdpRepo.getAllCODecisionParameters();
		logger.info("********** before save tested successfully*********");

		CODecisionParameters savedCDP = cdpService.saveCODecisionParameters(cdpDataList, newCDP, null);
		logger.info("**********after save tested successfully*********");
		cdpDataList = cdpRepo.getAllCODecisionParameters();
		assertNotNull(cdpService);
		savedCDP = cdpDataList.get(cdpDataList.size() - 1);
		// String beforeUpdateSymbol = savedCDP.getHigherSymbol();
		String afterUpdateSymbol = "AfterUpdateSymbol";
		savedCDP.setHigherSymbol(afterUpdateSymbol);
		CODecisionParameters updatedCDP = cdpService.saveCODecisionParameters(cdpDataList, savedCDP, savedCDP.getEntityId());
		assertNotNull(updatedCDP);
		assertEquals(updatedCDP.getHigherSymbol(), afterUpdateSymbol);

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
		/*	CODecisionParameters newCDP = cdpfactory.createCODecisionParameters();
			String higherSymbol = "TestHigherSymbol1";
			newCDP.setHigherSymbol(higherSymbol);*/
		List<CODecisionParameters> cdpDataList = cdpRepo.getAllCODecisionParameters();
		assertNotNull(cdpService);
		CODecisionParameters codToBeDeleted = cdpDataList.get(cdpDataList.size() - 1);
		cdpService.deleteCODecisionParameters(codToBeDeleted.getEntityId());
		List<CODecisionParameters> cdpDataList2 = cdpRepo.getAllCODecisionParameters();
		assertTrue(!cdpDataList2.contains(codToBeDeleted));
		assertFalse(cdpDataList2.contains(codToBeDeleted));

	}

}
