/*
 * Creation : Dec 29, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.infrastructure.carfactory;

import java.util.List;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.carfactory.CarFactoryService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.factory.CarFactory;
import com.inetpsa.poc00.domain.factory.CarFactoryRepository;

/**
 * The CarFactoryServiceImpl
 * 
 * @author mehaj
 */
public class CarFactoryServiceImpl implements CarFactoryService {
	@Inject
	CarFactoryRepository carFactoryRepository;

	@Inject
	TraceabilityService traceabilityService;
	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(CarFactoryServiceImpl.class);

	@Override
	public CarFactory persistFactoryObject(CarFactory carFactory) {
		return carFactoryRepository.persistFactoryObject(carFactory);
	}

	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public String saveFactoryObject(CarFactory carFactoryObj) {

		List<CarFactory> carFactoryData = carFactoryRepository.getCarFactoryDataByLabel(carFactoryObj.getLabel());
		if (!carFactoryData.isEmpty()) {

			if (carFactoryData.get(0).getEntityId() != carFactoryObj.getEntityId()) {

				return carFactoryData.get(0).getLabel();

			}
		} else if (carFactoryObj.getEntityId() == null) {
			if (carFactoryObj.getLabel() != null && carFactoryObj.getLabel() != "") {
				// save

				CarFactory carFactorySaved = carFactoryRepository.saveFactoryObject(carFactoryObj);

				traceabilityService.historyForSave(carFactorySaved, ConstantsApp.SPECIFICCOP_SCREEN_ID);
			}
		} else {
			// update
			CarFactory carFactoryOld = carFactoryRepository.load(carFactoryObj.getEntityId());
			traceabilityService.historyForUpdate(carFactoryOld, carFactoryObj, ConstantsApp.SPECIFICCOP_SCREEN_ID);
			carFactoryRepository.persistFactoryObject(carFactoryObj);

		}

		return ConstantsApp.SUCCESS;
	}

	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public String deleteCarFactory(long carFactoryId) {

		CarFactory objToDelete = carFactoryRepository.load(carFactoryId);
		try {
			carFactoryRepository.deleteCarFactory(carFactoryId);

			logger.info("Sucessfully deleted CarFactory data");
			traceabilityService.historyForDelete(objToDelete, ConstantsApp.SPECIFICCOP_SCREEN_ID);
			return ConstantsApp.SUCCESS;

		} catch (Exception e) {
			logger.error(" Error occured while deleting data  :", e);
			return ConstantsApp.FAILURE;
		}

	}

}
