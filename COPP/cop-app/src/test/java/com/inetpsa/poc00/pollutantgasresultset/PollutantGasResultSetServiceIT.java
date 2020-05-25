/*
 * Creation : Dec 9, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.pollutantgasresultset;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.application.pollgasresultset.PollutantGasResultSetService;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSetFactory;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSetRepository;
import com.inetpsa.poc00.domain.technicalcase.TechCaseFactory;
import com.inetpsa.poc00.domain.technicalcase.TechCaseRepository;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvv.TVVFactory;
import com.inetpsa.poc00.domain.tvv.TVVRepository;
import com.inetpsa.poc00.domain.vehicle.Vehicle;
import com.inetpsa.poc00.domain.vehicle.VehicleRepository;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;

/**
 * TODO : Description
 * 
 * @author mehaj
 */
@RunWith(SeedITRunner.class)
public class PollutantGasResultSetServiceIT {
	/** The vehicle repo. */
	@Inject
	private VehicleRepository vehicleRepo;
	/** The vehicle file repo. */
	@Inject
	private VehicleFileRepository vehicleFileRepo;
	@Inject
	PollutantGasResultSetFactory pollGasResultSetFactory;
	@Inject
	PollutantGasResultSetRepository pollutantGasResultSetRepository;
	@Inject
	PollutantGasResultSetService pollutantGasResultSetService;
	/** The t vv repository. */
	@Inject
	private TVVRepository tVVRepository;

	/** The tech case repository. */
	@Inject
	private TechCaseRepository techCaseRepository;

	/** The t vv factory. */
	@Inject
	private TVVFactory tVVFactory;
	/** The tech case factory. */
	@Inject
	private TechCaseFactory techCaseFactory;

	@Test
	public void savePollutantGasResultSetTest() {
		Vehicle vehicleObj = new Vehicle();
		vehicleObj.setDescription("test Dsc1");
		Vehicle newVehicle = vehicleRepo.saveVehicle(vehicleObj);
		assertNotNull(newVehicle);
		VehicleFile vehicleFileObj = new VehicleFile();
		vehicleFileObj.setVehicle(newVehicle);
		VehicleFile vehFileSaved = vehicleFileRepo.saveVehicle(vehicleFileObj);
		PollutantGasResultSet pollGasResultSet = pollGasResultSetFactory.createPollutantGasResultSet();
		pollGasResultSet.setVehicleFile(vehFileSaved);
		PollutantGasResultSet pgResultSetSaved = pollutantGasResultSetService.savePollutantGasResultSet(pollGasResultSet);
		assertNotNull(pgResultSetSaved.getEntityId());
	};

	@Test
	public void createPollutantGasResultSetTest() {
		TVV tvvObj = tVVFactory.createTVV();
		tvvObj.setLabel("UnitTestTVV");
		// tvvObj.setEntityId(65454L);
		TVV tvvSaved = tVVRepository.saveTVV(tvvObj);
		TechnicalCase tc = techCaseFactory.createTechCase();
		tc.setTvv(tvvSaved);
		TechnicalCase tcSaved = techCaseRepository.saveTechCase(tc);
		Vehicle vehicleObj = new Vehicle();
		vehicleObj.setCounterMark("testCountTVV" + Calendar.getInstance().getTime());
		vehicleObj.setChasisNumber("testChassisTVV" + Calendar.getInstance().getTime());
		vehicleObj.setRegistrationNumber("testRegTVV" + Calendar.getInstance().getTime());
		vehicleObj.setTechnicalCase(tcSaved);
		Vehicle newVehicle = vehicleRepo.saveVehicle(vehicleObj);
		VehicleFile vehicleFileObj = new VehicleFile();
		vehicleFileObj.setVehicle(newVehicle);
		VehicleFile vehFileSaved = vehicleFileRepo.saveVehicle(vehicleFileObj);

		List<PollutantGasResultSet> pollutantGasResultSetList = pollutantGasResultSetService.createPollutantGasResultSet(vehFileSaved, tvvSaved, 2);
		Assert.assertNotEquals(0, pollutantGasResultSetList.size());
	}
}
