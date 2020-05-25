/*
 * Creation : Jan 16, 2017
 */
package com.inetpsa.poc00.carfactory;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.domain.factory.CarFactory;
import com.inetpsa.poc00.domain.factory.CarFactoryObjectCreation;
import com.inetpsa.poc00.domain.factory.CarFactoryRepository;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvv.TVVFactory;
import com.inetpsa.poc00.domain.tvv.TVVRepository;
import com.inetpsa.poc00.rest.carfactory.CarFactoryFinder;
import com.inetpsa.poc00.rest.carfactory.CarFactoryRepresentation;

/**
 * The Class CarFactoryFinderIT.
 */
@RunWith(SeedITRunner.class)
public class CarFactoryFinderIT {

	/** The car factory repository. */
	@Inject
	CarFactoryRepository carFactoryRepository;

	/** The Car factory object creation. */
	@Inject
	CarFactoryObjectCreation CarFactoryObjectCreation;

	/** The car factory finder. */
	@Inject
	CarFactoryFinder carFactoryFinder;

	/** The tvv repository. */
	@Inject
	TVVRepository tvvRepository;

	/** The t vv factory. */
	@Inject
	TVVFactory tVVFactory;

	/**
	 * Test find all usine data.
	 */
	@Test
	public final void testFindAllUsineData() {
		CarFactory carFactory = CarFactoryObjectCreation.createCarFactoryObject();
		carFactory.setLabel("CarFactoryLabel" + Calendar.getInstance().getTime());
		carFactoryRepository.saveFactoryObject(carFactory);
		List<CarFactoryRepresentation> list = carFactoryFinder.findAllUsineData();
		assertNotNull(list);

	}

	/**
	 * Test find car factory data by label.
	 */
	@Test
	public final void testFindCarFactoryDataByLabel() {
		CarFactory carFactory = CarFactoryObjectCreation.createCarFactoryObject();
		carFactory.setLabel("CarFactoryLabel" + Calendar.getInstance().getTime());
		CarFactory carFactorySaved = carFactoryRepository.saveFactoryObject(carFactory);
		List<CarFactoryRepresentation> list = carFactoryFinder.findCarFactoryDataByLabel(carFactorySaved.getLabel());
		assertNotNull(list);

	}

	/**
	 * Test get factories for tvv.
	 */
	@Test
	public final void testGetFactoriesForTVV() {
		TVV tvv = tVVFactory.createTVV();
		tvv.setLabel("TVVLabel" + Calendar.getInstance().getTime());
		TVV tvvSaved = tvvRepository.saveTVV(tvv);
		Set<TVV> tvvSet = new HashSet<>();
		tvvSet.add(tvvSaved);
		CarFactory carFactory = CarFactoryObjectCreation.createCarFactoryObject();
		carFactory.setLabel("CarFactoryLabel" + Calendar.getInstance().getTime());
		carFactory.setTvvSet(tvvSet);
		carFactoryRepository.saveFactoryObject(carFactory);
		List<CarFactoryRepresentation> list = carFactoryFinder.getFactoriesForTVV(tvvSaved.getEntityId());
		assertNotNull(list);
	}

}
