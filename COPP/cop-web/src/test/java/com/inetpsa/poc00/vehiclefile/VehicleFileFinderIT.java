/*
 * Creation : Oct 27, 2016
 */
package com.inetpsa.poc00.vehiclefile;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.Logging;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.application.vehicle.VehicleService;
import com.inetpsa.poc00.domain.basket.Basket;
import com.inetpsa.poc00.domain.basket.BasketRepository;
import com.inetpsa.poc00.domain.bodywork.Bodywork;
import com.inetpsa.poc00.domain.bodywork.BodyworkFactory;
import com.inetpsa.poc00.domain.bodywork.BodyworkRepository;
import com.inetpsa.poc00.domain.carbrand.CarBrand;
import com.inetpsa.poc00.domain.carbrand.CarBrandFactory;
import com.inetpsa.poc00.domain.carbrand.CarBrandRepository;
import com.inetpsa.poc00.domain.engine.Engine;
import com.inetpsa.poc00.domain.engine.EngineFactory;
import com.inetpsa.poc00.domain.engine.EngineRepository;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.es.EmissionStandardRepository;
import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.fuel.FuelFactory;
import com.inetpsa.poc00.domain.fuel.FuelRepository;
import com.inetpsa.poc00.domain.fueltype.FuelType;
import com.inetpsa.poc00.domain.fueltype.FuelTypeFactory;
import com.inetpsa.poc00.domain.fueltype.FuelTypeRepository;
import com.inetpsa.poc00.domain.gearbox.GearBox;
import com.inetpsa.poc00.domain.gearbox.GearBoxFactory;
import com.inetpsa.poc00.domain.gearbox.GearBoxRepository;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamily;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamilyFactory;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamilyRepository;
import com.inetpsa.poc00.domain.technicalcase.TechCaseFactory;
import com.inetpsa.poc00.domain.technicalcase.TechCaseRepository;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvv.TVVFactory;
import com.inetpsa.poc00.domain.tvv.TVVRepository;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTest;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTestRepository;
import com.inetpsa.poc00.domain.vehicle.Vehicle;
import com.inetpsa.poc00.domain.vehicle.VehicleRepository;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatus;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatusFactory;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatusRepository;
import com.inetpsa.poc00.rest.vehicle.VehicleSearchRepresentation;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileFinder;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileRepresentation;

/**
 * The Class VehicleFileFinderIT.
 */
@RunWith(SeedITRunner.class)
public class VehicleFileFinderIT {

	/** The vehicle file finder. */
	@Inject
	private VehicleFileFinder vehicleFileFinder;
	/** The vehicle file repo. */
	@Inject
	private VehicleFileRepository vehicleFileRepo;
	/** The vehicle repo. */
	@Inject
	private VehicleRepository vehicleRepo;
	/** The type test repo. */
	@Inject
	private TypeOfTestRepository typeTestRepo;
	/** The vehicle file status repo. */
	@Inject
	private VehicleFileStatusRepository vehicleFileStatusRepo;
	/** The basket repo. */
	@Inject
	private BasketRepository basketRepo;

	/** The vehicle service. */
	@Inject
	private VehicleService vehicleService;

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

	/** The EMS factory. */
	@Inject
	private EmissionStandardFactory EMSFactory;

	/** The emission standard repository. */
	@Inject
	private EmissionStandardRepository emissionStandardRepository;

	/** The project code family factory. */
	@Inject
	private ProjectCodeFamilyFactory projectCodeFamilyFactory;

	/** The project code family repository. */
	@Inject
	private ProjectCodeFamilyRepository projectCodeFamilyRepository;

	/** The bodywork factory. */
	@Inject
	private BodyworkFactory bodyworkFactory;

	/** The bodywork repository. */
	@Inject
	private BodyworkRepository bodyworkRepository;

	/** The fuel factory. */
	@Inject
	private FuelFactory fuelFactory;

	/** The fuel repository. */
	@Inject
	private FuelRepository fuelRepository;

	/** The fuel type factory. */
	@Inject
	private FuelTypeFactory fuelTypeFactory;

	/** The fuel type repository. */
	@Inject
	private FuelTypeRepository fuelTypeRepository;

	/** The car brand factory. */
	@Inject
	private CarBrandFactory carBrandFactory;

	/** The car brand repository. */
	@Inject
	private CarBrandRepository carBrandRepository;

	/** The engine factory. */
	@Inject
	private EngineFactory engineFactory;

	/** The engine repository. */
	@Inject
	private EngineRepository engineRepository;

	/** The gear box factory. */
	@Inject
	private GearBoxFactory gearBoxFactory;

	/** The gear box repository. */
	@Inject
	private GearBoxRepository gearBoxRepository;

	/** The vehicle file status factory. */
	@Inject
	private VehicleFileStatusFactory vehicleFileStatusFactory;

	/** The vehicle file status repository. */
	@Inject
	private VehicleFileStatusRepository vehicleFileStatusRepository;

	/** The logger. */
	@Logging
	private final Logger logger = Logger.getLogger(VehicleFileFinderIT.class);

	/**
	 * Gets the vehicle file list.
	 *
	 * @return the vehicle file list
	 */
	@Test
	public void getVehicleFileList() {

		Vehicle vehicleObj = new Vehicle();

		vehicleObj.setDescription("test Dsc1");

		Vehicle newVehicle = vehicleRepo.saveVehicle(vehicleObj);
		assertNotNull(newVehicle);
		VehicleFile vehicleFileObj = new VehicleFile();

		vehicleFileObj.setVehicle(newVehicle);
		vehicleFileObj.setVehicleFileStatus(vehicleFileStatusRepo.loadVehicleFileStatus(1L));

		vehicleFileRepo.saveVehicle(vehicleFileObj);

		List<VehicleFileRepresentation> vehicleFileRep = vehicleFileFinder.getVehicleFileList(newVehicle.getEntityId());
		Assert.assertEquals(vehicleFileRep.size(), 0);

		logger.info("********************getVehicleFileList tested successfully****************");
		// vehicleFileRepo.delete(vehicleFileObj);
	}

	/**
	 * Gets the vehicle file by baket.
	 *
	 * @return the vehicle file by baket
	 */
	@Test
	public void getVehicleFileByBaket() {

		Basket basketObj = new Basket();
		Basket newBasket = basketRepo.saveBasket(basketObj);

		assertNotNull(newBasket);

		VehicleFile vehicleFileObj = new VehicleFile();
		vehicleFileObj.setBasket(newBasket);

		vehicleFileRepo.saveVehicle(vehicleFileObj);

		List<VehicleFileRepresentation> vehicleFileRep = vehicleFileFinder.getVehicleFileByBaket(newBasket.getEntityId());

		Assert.assertEquals(0, vehicleFileRep.size());

		logger.info("********************getVehicleFileByBaket tested successfully****************");
		// vehicleFileRepo.delete(vehicleFileObj);

	}

	/**
	 * Search vehicle file by tvv.
	 */
	@Test
	public void searchVehicleFileByTVV() {
		VehicleFileStatus vfs = new VehicleFileStatus();
		vfs.setLabel("VehiclefileStatusLabel" + Calendar.getInstance().getTime());
		VehicleFileStatus vfsSaved = vehicleFileStatusRepo.saveVehicleFileStatus(vfs);
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
		assertNotNull(vehicleService);
		TypeOfTest typeTestObj = new TypeOfTest();
		typeTestObj.setLabel("TypeTestJunit" + Calendar.getInstance().getTime());
		TypeOfTest newTypeTest = typeTestRepo.saveTypeOfTest(typeTestObj);
		assertNotNull(newTypeTest);
		VehicleFile vfSaved = vehicleService.createVehicle(vehicleObj, newTypeTest.getEntityId());
		vfSaved.setVehicleFileStatus(vfsSaved);
		vehicleFileRepo.saveVehicle(vfSaved);
		VehicleSearchRepresentation vehicleSearchRepresentation = new VehicleSearchRepresentation();
		vehicleSearchRepresentation.setTvvLabel("UnitTestTVV");
		List<VehicleFileRepresentation> result = vehicleFileFinder.searchVehicleFile(vehicleSearchRepresentation);
		Assert.assertNotEquals(0, result.size());
		// vehicleFileRepo.deleteVehicleFile(vfSaved);
	}

	/**
	 * Search vehicle file by ems.
	 */
	@Test
	public void searchVehicleFileByEMS() {
		VehicleFileStatus vfs = new VehicleFileStatus();
		vfs.setLabel("VehiclefileStatusLabel" + Calendar.getInstance().getTime());
		VehicleFileStatus vfsSaved = vehicleFileStatusRepo.saveVehicleFileStatus(vfs);
		EmissionStandard ems = EMSFactory.createEmissonStandard();
		ems.setEsLabel("EMSForUnitTest");
		EmissionStandard emsSaved = emissionStandardRepository.saveEmissionStandard(ems);
		TechnicalCase tc = techCaseFactory.createTechCase();
		tc.setEmissionStandard(emsSaved);
		TechnicalCase tcSaved = techCaseRepository.saveTechCase(tc);
		Vehicle vehicleObj = new Vehicle();
		vehicleObj.setCounterMark("testCountEMS" + Calendar.getInstance().getTime());
		vehicleObj.setChasisNumber("testChassisEMS" + Calendar.getInstance().getTime());
		vehicleObj.setRegistrationNumber("testRegEMS" + Calendar.getInstance().getTime());
		vehicleObj.setTechnicalCase(tcSaved);
		assertNotNull(vehicleService);
		TypeOfTest typeTestObj = new TypeOfTest();
		typeTestObj.setLabel("TypeTestJunit");
		TypeOfTest newTypeTest = typeTestRepo.saveTypeOfTest(typeTestObj);
		assertNotNull(newTypeTest);
		VehicleFile vfSaved = vehicleService.createVehicle(vehicleObj, newTypeTest.getEntityId());
		vfSaved.setVehicleFileStatus(vfsSaved);
		vehicleFileRepo.saveVehicle(vfSaved);
		VehicleSearchRepresentation vehicleSearchRepresentation = new VehicleSearchRepresentation();
		List<Long> emsList = new ArrayList<Long>();
		emsList.add(emsSaved.getEntityId());
		vehicleSearchRepresentation.setEmsList(emsList);
		List<VehicleFileRepresentation> result = vehicleFileFinder.searchVehicleFile(vehicleSearchRepresentation);
		Assert.assertNotEquals(0, result.size());
		// vehicleFileRepo.deleteVehicleFile(vfSaved);
	}

	/**
	 * Search vehicle file by countermark.
	 */
	@Test
	public void searchVehicleFileByCountermark() {
		VehicleFileStatus vfs = new VehicleFileStatus();
		vfs.setLabel("VehiclefileStatusLabel" + Calendar.getInstance().getTime());
		VehicleFileStatus vfsSaved = vehicleFileStatusRepo.saveVehicleFileStatus(vfs);
		TVV tvvObj = tVVFactory.createTVV();
		tvvObj.setLabel("UnitTestTVV");

		TVV tvvSaved = tVVRepository.saveTVV(tvvObj);
		TechnicalCase tc = techCaseFactory.createTechCase();
		tc.setTvv(tvvSaved);
		TechnicalCase tcSaved = techCaseRepository.saveTechCase(tc);

		Vehicle vehicleObj = new Vehicle();
		vehicleObj.setTechnicalCase(tcSaved);
		vehicleObj.setCounterMark("testCountCM" + Calendar.getInstance().getTime());
		vehicleObj.setChasisNumber("testChassisCM" + Calendar.getInstance().getTime());
		vehicleObj.setRegistrationNumber("testRegCM" + Calendar.getInstance().getTime());

		assertNotNull(vehicleService);
		TypeOfTest typeTestObj = new TypeOfTest();
		typeTestObj.setLabel("TypeTestJunit");
		TypeOfTest newTypeTest = typeTestRepo.saveTypeOfTest(typeTestObj);
		assertNotNull(newTypeTest);
		VehicleFile vfSaved = vehicleService.createVehicle(vehicleObj, newTypeTest.getEntityId());
		vfSaved.setVehicleFileStatus(vfsSaved);
		VehicleFile vfSavedNew = vehicleFileRepo.saveVehicle(vfSaved);
		VehicleSearchRepresentation vehicleSearchRepresentation = new VehicleSearchRepresentation();

		vehicleSearchRepresentation.setCounterMark(vfSavedNew.getVehicle().getCounterMark());
		List<VehicleFileRepresentation> result = vehicleFileFinder.searchVehicleFile(vehicleSearchRepresentation);
		Assert.assertNotEquals(0, result.size());
		// vehicleFileRepo.deleteVehicleFile(vfSaved);
	}

	/**
	 * Search vehicle file by reg num.
	 */
	@Test
	public void searchVehicleFileByRegNum() {
		VehicleFileStatus vfs = new VehicleFileStatus();
		vfs.setLabel("VehiclefileStatusLabel" + Calendar.getInstance().getTime());
		VehicleFileStatus vfsSaved = vehicleFileStatusRepo.saveVehicleFileStatus(vfs);
		TVV tvvObj = tVVFactory.createTVV();
		tvvObj.setLabel("UnitTestTVV");

		TVV tvvSaved = tVVRepository.saveTVV(tvvObj);
		TechnicalCase tc = techCaseFactory.createTechCase();
		tc.setTvv(tvvSaved);
		TechnicalCase tcSaved = techCaseRepository.saveTechCase(tc);

		Vehicle vehicleObj = new Vehicle();
		vehicleObj.setTechnicalCase(tcSaved);
		vehicleObj.setCounterMark("testCountRegNum1" + Calendar.getInstance().getTime());
		vehicleObj.setChasisNumber("testChassisRegNum1" + Calendar.getInstance().getTime());
		vehicleObj.setRegistrationNumber("testRegRegNum1" + Calendar.getInstance().getTime());
		assertNotNull(vehicleService);
		TypeOfTest typeTestObj = new TypeOfTest();
		typeTestObj.setLabel("TypeTestJunit");
		TypeOfTest newTypeTest = typeTestRepo.saveTypeOfTest(typeTestObj);
		assertNotNull(newTypeTest);
		VehicleFile vfSaved = vehicleService.createVehicle(vehicleObj, newTypeTest.getEntityId());
		vfSaved.setVehicleFileStatus(vfsSaved);
		VehicleFile vfSavedNew = vehicleFileRepo.saveVehicle(vfSaved);
		VehicleSearchRepresentation vehicleSearchRepresentation = new VehicleSearchRepresentation();
		vehicleSearchRepresentation.setRegistrationNumber(vfSavedNew.getVehicle().getRegistrationNumber());
		List<VehicleFileRepresentation> result = vehicleFileFinder.searchVehicleFile(vehicleSearchRepresentation);
		Assert.assertNotEquals(0, result.size());
		// vehicleFileRepo.deleteVehicleFile(vfSaved);
	}

	/**
	 * Search vehicle file by chassis.
	 */
	@Test
	public void searchVehicleFileByChassis() {
		VehicleFileStatus vfs = new VehicleFileStatus();
		vfs.setLabel("VehiclefileStatusLabel" + Calendar.getInstance().getTime());
		VehicleFileStatus vfsSaved = vehicleFileStatusRepo.saveVehicleFileStatus(vfs);

		TVV tvvObj = tVVFactory.createTVV();
		tvvObj.setLabel("UnitTestTVV");

		TVV tvvSaved = tVVRepository.saveTVV(tvvObj);
		TechnicalCase tc = techCaseFactory.createTechCase();
		tc.setTvv(tvvSaved);
		TechnicalCase tcSaved = techCaseRepository.saveTechCase(tc);

		Vehicle vehicleObj = new Vehicle();
		vehicleObj.setTechnicalCase(tcSaved);
		vehicleObj.setCounterMark("testCountCh" + Calendar.getInstance().getTime());
		vehicleObj.setChasisNumber("testChassisCH" + Calendar.getInstance().getTime());
		vehicleObj.setRegistrationNumber("testRegCH" + Calendar.getInstance().getTime());
		assertNotNull(vehicleService);
		TypeOfTest typeTestObj = new TypeOfTest();
		typeTestObj.setLabel("TypeTestJunit");
		TypeOfTest newTypeTest = typeTestRepo.saveTypeOfTest(typeTestObj);
		assertNotNull(newTypeTest);
		VehicleFile vfSaved = vehicleService.createVehicle(vehicleObj, newTypeTest.getEntityId());
		vfSaved.setVehicleFileStatus(vfsSaved);
		VehicleFile vfSavedNew = vehicleFileRepo.saveVehicle(vfSaved);
		VehicleSearchRepresentation vehicleSearchRepresentation = new VehicleSearchRepresentation();

		vehicleSearchRepresentation.setChasisNumber(vfSavedNew.getVehicle().getChasisNumber());
		List<VehicleFileRepresentation> result = vehicleFileFinder.searchVehicleFile(vehicleSearchRepresentation);
		Assert.assertNotEquals(0, result.size());
		// vehicleFileRepo.deleteVehicleFile(vfSaved);
	}

	/**
	 * Search vehicle file by pc family.
	 */
	@Test
	public void searchVehicleFileByPCFamily() {
		VehicleFileStatus vfs = new VehicleFileStatus();
		vfs.setLabel("VehiclefileStatusLabel" + Calendar.getInstance().getTime());
		VehicleFileStatus vfsSaved = vehicleFileStatusRepo.saveVehicleFileStatus(vfs);
		ProjectCodeFamily pcf = projectCodeFamilyFactory.createPCFamily();
		pcf.setProjectCodeLabel("PCFLabel" + Calendar.getInstance().getTime());
		pcf.setVehicleFamilyLabel("vehicleFamilyLabel" + Calendar.getInstance().getTime());
		ProjectCodeFamily pcfSaved = projectCodeFamilyRepository.saveProjectCodeFamily(pcf);
		TVV tvvObj = tVVFactory.createTVV();
		tvvObj.setLabel("UnitTestTVV");
		tvvObj.setProjectCodeFamily(pcfSaved);
		TVV tvvSaved = tVVRepository.saveTVV(tvvObj);
		TechnicalCase tc = techCaseFactory.createTechCase();
		tc.setTvv(tvvSaved);
		TechnicalCase tcSaved = techCaseRepository.saveTechCase(tc);
		Vehicle vehicleObj = new Vehicle();
		vehicleObj.setCounterMark("testCountPC" + Calendar.getInstance().getTime());
		vehicleObj.setChasisNumber("testChassisPC" + Calendar.getInstance().getTime());
		vehicleObj.setRegistrationNumber("testRegPC" + Calendar.getInstance().getTime());
		vehicleObj.setTechnicalCase(tcSaved);
		assertNotNull(vehicleService);
		TypeOfTest typeTestObj = new TypeOfTest();
		typeTestObj.setLabel("TypeTestJunit");
		TypeOfTest newTypeTest = typeTestRepo.saveTypeOfTest(typeTestObj);
		assertNotNull(newTypeTest);
		VehicleFile vfSaved = vehicleService.createVehicle(vehicleObj, newTypeTest.getEntityId());
		vfSaved.setVehicleFileStatus(vfsSaved);
		VehicleFile vfSavedNew = vehicleFileRepo.saveVehicle(vfSaved);
		VehicleSearchRepresentation vehicleSearchRepresentation = new VehicleSearchRepresentation();
		List<String> pcfList = new ArrayList<String>();
		pcfList.add(pcfSaved.getProjectCodeLabel() + pcfSaved.getVehicleFamilyLabel());
		vehicleSearchRepresentation.setProjectCodeFamilyList(pcfList);
		List<VehicleFileRepresentation> result = vehicleFileFinder.searchVehicleFile(vehicleSearchRepresentation);
		Assert.assertNotEquals(0, result.size());
		// vehicleFileRepo.deleteVehicleFile(vfSaved);
	}

	/**
	 * Search vehicle file by bw.
	 */
	@Test
	public void searchVehicleFileByBW() {
		VehicleFileStatus vfs = new VehicleFileStatus();
		vfs.setLabel("VehiclefileStatusLabel" + Calendar.getInstance().getTime());
		VehicleFileStatus vfsSaved = vehicleFileStatusRepo.saveVehicleFileStatus(vfs);
		Bodywork bw = bodyworkFactory.createBodywork();
		bw.setLabel("BWLabel1");
		Bodywork bwSaved = bodyworkRepository.saveBodywork(bw);
		TVV tvvObj = tVVFactory.createTVV();
		tvvObj.setLabel("UnitTestTVV");
		tvvObj.setBodyWork(bwSaved);
		TVV tvvSaved = tVVRepository.saveTVV(tvvObj);
		TechnicalCase tc = techCaseFactory.createTechCase();
		tc.setTvv(tvvSaved);
		TechnicalCase tcSaved = techCaseRepository.saveTechCase(tc);
		Vehicle vehicleObj = new Vehicle();
		vehicleObj.setCounterMark("testCountBW" + Calendar.getInstance().getTime());
		vehicleObj.setChasisNumber("testChassisBW" + Calendar.getInstance().getTime());
		vehicleObj.setRegistrationNumber("testRegBW" + Calendar.getInstance().getTime());
		vehicleObj.setTechnicalCase(tcSaved);
		assertNotNull(vehicleService);
		TypeOfTest typeTestObj = new TypeOfTest();
		typeTestObj.setLabel("TypeTestJunit");
		TypeOfTest newTypeTest = typeTestRepo.saveTypeOfTest(typeTestObj);
		assertNotNull(newTypeTest);
		VehicleFile vfSaved = vehicleService.createVehicle(vehicleObj, newTypeTest.getEntityId());
		vfSaved.setVehicleFileStatus(vfsSaved);
		VehicleFile vfSavedNew = vehicleFileRepo.saveVehicle(vfSaved);
		VehicleSearchRepresentation vehicleSearchRepresentation = new VehicleSearchRepresentation();
		List<String> bwList = new ArrayList<String>();
		bwList.add(bwSaved.getLabel());
		vehicleSearchRepresentation.setBodyWorkList(bwList);
		List<VehicleFileRepresentation> result = vehicleFileFinder.searchVehicleFile(vehicleSearchRepresentation);
		Assert.assertNotEquals(0, result.size());
		// vehicleFileRepo.deleteVehicleFile(vfSaved);
	}

	/**
	 * Search vehicle file by fuel.
	 */
	@Test
	public void searchVehicleFileByFuel() {
		VehicleFileStatus vfs = new VehicleFileStatus();
		vfs.setLabel("VehiclefileStatusLabel" + Calendar.getInstance().getTime());
		VehicleFileStatus vfsSaved = vehicleFileStatusRepo.saveVehicleFileStatus(vfs);
		Fuel fuel = fuelFactory.createFuel();
		fuel.setLabel("fuel");
		Fuel fuelSaved = fuelRepository.saveFuel(fuel);
		TVV tvvObj = tVVFactory.createTVV();
		tvvObj.setLabel("UnitTestTVV");
		tvvObj.setFuel(fuelSaved);
		TVV tvvSaved = tVVRepository.saveTVV(tvvObj);
		TechnicalCase tc = techCaseFactory.createTechCase();
		tc.setTvv(tvvSaved);
		TechnicalCase tcSaved = techCaseRepository.saveTechCase(tc);
		Vehicle vehicleObj = new Vehicle();
		vehicleObj.setCounterMark("testCountFL" + Calendar.getInstance().getTime());
		vehicleObj.setChasisNumber("testChassisFL" + Calendar.getInstance().getTime());
		vehicleObj.setRegistrationNumber("testRegFL" + Calendar.getInstance().getTime());
		vehicleObj.setTechnicalCase(tcSaved);
		assertNotNull(vehicleService);
		TypeOfTest typeTestObj = new TypeOfTest();
		typeTestObj.setLabel("TypeTestJunit");
		TypeOfTest newTypeTest = typeTestRepo.saveTypeOfTest(typeTestObj);
		assertNotNull(newTypeTest);
		VehicleFile vfSaved = vehicleService.createVehicle(vehicleObj, newTypeTest.getEntityId());
		vfSaved.setVehicleFileStatus(vfsSaved);
		VehicleFile vfSavedNew = vehicleFileRepo.saveVehicle(vfSaved);
		VehicleSearchRepresentation vehicleSearchRepresentation = new VehicleSearchRepresentation();
		List<Long> fuelList = new ArrayList<Long>();
		fuelList.add(fuelSaved.getEntityId());
		vehicleSearchRepresentation.setFuelList(fuelList);
		List<VehicleFileRepresentation> result = vehicleFileFinder.searchVehicleFile(vehicleSearchRepresentation);
		Assert.assertNotEquals(0, result.size());
		// vehicleFileRepo.deleteVehicleFile(vfSaved);
	}

	/**
	 * Search vehicle file by fuel type.
	 */
	@Test
	public void searchVehicleFileByFuelType() {
		VehicleFileStatus vfs = new VehicleFileStatus();
		vfs.setLabel("VehiclefileStatusLabel" + Calendar.getInstance().getTime());
		VehicleFileStatus vfsSaved = vehicleFileStatusRepo.saveVehicleFileStatus(vfs);
		FuelType ft = fuelTypeFactory.createFuelType();
		ft.setFuelTypeLable("FuelTypeTest");
		FuelType fuelTypeSaved = fuelTypeRepository.saveFuelType(ft);
		Fuel fuel = fuelFactory.createFuel();
		fuel.setLabel("fuel");
		fuel.setFuelType(fuelTypeSaved);
		Fuel fuelSaved = fuelRepository.saveFuel(fuel);
		TVV tvvObj = tVVFactory.createTVV();
		tvvObj.setLabel("UnitTestTVV");
		tvvObj.setFuel(fuelSaved);
		TVV tvvSaved = tVVRepository.saveTVV(tvvObj);
		TechnicalCase tc = techCaseFactory.createTechCase();
		tc.setTvv(tvvSaved);
		TechnicalCase tcSaved = techCaseRepository.saveTechCase(tc);
		Vehicle vehicleObj = new Vehicle();
		vehicleObj.setCounterMark("testCountFT" + Calendar.getInstance().getTime());
		vehicleObj.setChasisNumber("testChassisFT" + Calendar.getInstance().getTime());
		vehicleObj.setRegistrationNumber("testRegFT" + Calendar.getInstance().getTime());

		vehicleObj.setTechnicalCase(tcSaved);
		assertNotNull(vehicleService);
		TypeOfTest typeTestObj = new TypeOfTest();
		typeTestObj.setLabel("TypeTestJunit");
		TypeOfTest newTypeTest = typeTestRepo.saveTypeOfTest(typeTestObj);
		assertNotNull(newTypeTest);
		VehicleFile vfSaved = vehicleService.createVehicle(vehicleObj, newTypeTest.getEntityId());
		vfSaved.setVehicleFileStatus(vfsSaved);
		VehicleFile vfSavedNew = vehicleFileRepo.saveVehicle(vfSaved);
		VehicleSearchRepresentation vehicleSearchRepresentation = new VehicleSearchRepresentation();
		List<String> fuelTypeList = new ArrayList<String>();
		fuelTypeList.add(fuelTypeSaved.getFuelTypeLable());
		vehicleSearchRepresentation.setFuelTypeList(fuelTypeList);
		List<VehicleFileRepresentation> result = vehicleFileFinder.searchVehicleFile(vehicleSearchRepresentation);
		Assert.assertNotEquals(0, result.size());
		// vehicleFileRepo.deleteVehicleFile(vfSaved);
	}

	/**
	 * Search vehicle file by cb.
	 */
	@Test
	public void searchVehicleFileByCB() {
		VehicleFileStatus vfs = new VehicleFileStatus();
		vfs.setLabel("VehiclefileStatusLabel" + Calendar.getInstance().getTime());
		VehicleFileStatus vfsSaved = vehicleFileStatusRepo.saveVehicleFileStatus(vfs);
		CarBrand cb = carBrandFactory.createCarBrand();
		cb.setBrandLabel("TestCarBrand" + Calendar.getInstance().getTime());
		cb.setMakerLabel("TestCarBrand" + Calendar.getInstance().getTime());
		CarBrand cbSaved = carBrandRepository.saveCarBrand(cb);

		Set<CarBrand> carBrandSet = new HashSet<CarBrand>();
		carBrandSet.add(cbSaved);

		TVV tvvObj = tVVFactory.createTVV();
		tvvObj.setLabel("UnitTestTVV" + Calendar.getInstance().getTime());
		tvvObj.setCarBrandSet(carBrandSet);
		TVV tvvSaved = tVVRepository.saveTVV(tvvObj);

		TechnicalCase tc = techCaseFactory.createTechCase();
		tc.setTvv(tvvSaved);
		TechnicalCase tcSaved = techCaseRepository.saveTechCase(tc);

		Vehicle vehicleObj = new Vehicle();
		vehicleObj.setCounterMark("testCountCB" + Calendar.getInstance().getTime());
		vehicleObj.setChasisNumber("testChassisCB" + Calendar.getInstance().getTime());
		vehicleObj.setRegistrationNumber("testRegCB" + Calendar.getInstance().getTime());
		vehicleObj.setTechnicalCase(tcSaved);
		assertNotNull(vehicleService);
		TypeOfTest typeTestObj = new TypeOfTest();
		typeTestObj.setLabel("TypeTestJunit" + Calendar.getInstance().getTime());
		TypeOfTest newTypeTest = typeTestRepo.saveTypeOfTest(typeTestObj);
		assertNotNull(newTypeTest);
		VehicleFile vfSaved = vehicleService.createVehicle(vehicleObj, newTypeTest.getEntityId());
		vfSaved.setVehicleFileStatus(vfsSaved);
		VehicleFile vfSavedNew = vehicleFileRepo.saveVehicle(vfSaved);
		VehicleSearchRepresentation vehicleSearchRepresentation = new VehicleSearchRepresentation();
		List<String> carBrandList = new ArrayList<String>();
		carBrandList.add(cbSaved.getBrandLabel() + cbSaved.getMakerLabel());
		vehicleSearchRepresentation.setCarBrandList(carBrandList);
		List<VehicleFileRepresentation> result = vehicleFileFinder.searchVehicleFile(vehicleSearchRepresentation);
		Assert.assertNotEquals(0, result.size());
		// vehicleFileRepo.deleteVehicleFile(vfSaved);
	}

	/**
	 * Search vehicle file by engine.
	 */
	@Test
	public void searchVehicleFileByEngine() {
		VehicleFileStatus vfs = new VehicleFileStatus();
		vfs.setLabel("VehiclefileStatusLabel" + Calendar.getInstance().getTime());
		VehicleFileStatus vfsSaved = vehicleFileStatusRepo.saveVehicleFileStatus(vfs);
		Engine engine = engineFactory.createEngine();
		engine.setEngineLabel("EngineLabelTest");
		Engine engnSaved = engineRepository.saveEngine(engine);
		TVV tvvObj = tVVFactory.createTVV();
		tvvObj.setLabel("UnitTestTVV");
		tvvObj.setEngine(engnSaved);
		TVV tvvSaved = tVVRepository.saveTVV(tvvObj);
		TechnicalCase tc = techCaseFactory.createTechCase();
		tc.setTvv(tvvSaved);
		TechnicalCase tcSaved = techCaseRepository.saveTechCase(tc);
		Vehicle vehicleObj = new Vehicle();
		vehicleObj.setCounterMark("testCountEN" + Calendar.getInstance().getTime());
		vehicleObj.setChasisNumber("testChassisEN" + Calendar.getInstance().getTime());
		vehicleObj.setRegistrationNumber("testRegEN" + Calendar.getInstance().getTime());
		vehicleObj.setTechnicalCase(tcSaved);
		assertNotNull(vehicleService);
		TypeOfTest typeTestObj = new TypeOfTest();
		typeTestObj.setLabel("TypeTestJunit");
		TypeOfTest newTypeTest = typeTestRepo.saveTypeOfTest(typeTestObj);
		assertNotNull(newTypeTest);
		VehicleFile vfSaved = vehicleService.createVehicle(vehicleObj, newTypeTest.getEntityId());
		vfSaved.setVehicleFileStatus(vfsSaved);
		VehicleFile vfSavedNew = vehicleFileRepo.saveVehicle(vfSaved);
		VehicleSearchRepresentation vehicleSearchRepresentation = new VehicleSearchRepresentation();
		List<String> engineList = new ArrayList<String>();
		engineList.add(engnSaved.getEngineLabel());
		vehicleSearchRepresentation.setEngineList(engineList);
		List<VehicleFileRepresentation> result = vehicleFileFinder.searchVehicleFile(vehicleSearchRepresentation);
		Assert.assertNotEquals(0, result.size());
		// vehicleFileRepo.deleteVehicleFile(vfSaved);
	}

	/**
	 * Search vehicle file by gb.
	 */
	@Test
	public void searchVehicleFileByGB() {
		VehicleFileStatus vfs = new VehicleFileStatus();
		vfs.setLabel("VehiclefileStatusLabel" + Calendar.getInstance().getTime());
		VehicleFileStatus vfsSaved = vehicleFileStatusRepo.saveVehicleFileStatus(vfs);
		GearBox gb = gearBoxFactory.createGearBox();
		gb.setLabel("GearBoxLabel");
		GearBox gbSaved = gearBoxRepository.saveGearBox(gb);
		TVV tvvObj = tVVFactory.createTVV();
		tvvObj.setLabel("UnitTestTVV");
		tvvObj.setGearBox(gbSaved);
		TVV tvvSaved = tVVRepository.saveTVV(tvvObj);
		TechnicalCase tc = techCaseFactory.createTechCase();
		tc.setTvv(tvvSaved);
		TechnicalCase tcSaved = techCaseRepository.saveTechCase(tc);
		Vehicle vehicleObj = new Vehicle();
		vehicleObj.setCounterMark("testCountGB" + Calendar.getInstance().getTime());
		vehicleObj.setChasisNumber("testChassisGB" + Calendar.getInstance().getTime());
		vehicleObj.setRegistrationNumber("testRegGB" + Calendar.getInstance().getTime());
		vehicleObj.setTechnicalCase(tcSaved);
		assertNotNull(vehicleService);
		TypeOfTest typeTestObj = new TypeOfTest();
		typeTestObj.setLabel("TypeTestJunit");
		TypeOfTest newTypeTest = typeTestRepo.saveTypeOfTest(typeTestObj);
		assertNotNull(newTypeTest);
		VehicleFile vfSaved = vehicleService.createVehicle(vehicleObj, newTypeTest.getEntityId());
		vfSaved.setVehicleFileStatus(vfsSaved);
		VehicleFile vfSavedNew = vehicleFileRepo.saveVehicle(vfSaved);
		VehicleSearchRepresentation vehicleSearchRepresentation = new VehicleSearchRepresentation();
		List<String> gbList = new ArrayList<String>();
		gbList.add(gbSaved.getLabel());
		vehicleSearchRepresentation.setGearBoxList(gbList);
		List<VehicleFileRepresentation> result = vehicleFileFinder.searchVehicleFile(vehicleSearchRepresentation);
		Assert.assertNotEquals(0, result.size());
		// vehicleFileRepo.deleteVehicleFile(vfSaved);
	}

	/**
	 * Search vehicle file by model yr.
	 */
	@Test
	public void searchVehicleFileByModelYr() {
		VehicleFileStatus vfs = new VehicleFileStatus();
		vfs.setLabel("VehiclefileStatusLabel" + Calendar.getInstance().getTime());
		VehicleFileStatus vfsSaved = vehicleFileStatusRepo.saveVehicleFileStatus(vfs);
		TVV tvvObj = tVVFactory.createTVV();
		tvvObj.setLabel("UnitTestTVV");

		TVV tvvSaved = tVVRepository.saveTVV(tvvObj);
		TechnicalCase tc = techCaseFactory.createTechCase();
		tc.setTvv(tvvSaved);
		TechnicalCase tcSaved = techCaseRepository.saveTechCase(tc);

		Vehicle vehicleObj = new Vehicle();
		vehicleObj.setTechnicalCase(tcSaved);
		vehicleObj.setCounterMark("testCountMY" + Calendar.getInstance().getTime());
		vehicleObj.setChasisNumber("testChassisMY" + Calendar.getInstance().getTime());
		vehicleObj.setRegistrationNumber("testRegMY" + Calendar.getInstance().getTime());
		vehicleObj.setModelYear("11111");
		assertNotNull(vehicleService);
		TypeOfTest typeTestObj = new TypeOfTest();
		typeTestObj.setLabel("TypeTestJunit");
		TypeOfTest newTypeTest = typeTestRepo.saveTypeOfTest(typeTestObj);
		assertNotNull(newTypeTest);
		VehicleFile vfSaved = vehicleService.createVehicle(vehicleObj, newTypeTest.getEntityId());
		vfSaved.setVehicleFileStatus(vfsSaved);
		VehicleFile vfSavedNew = vehicleFileRepo.saveVehicle(vfSaved);
		VehicleSearchRepresentation vehicleSearchRepresentation = new VehicleSearchRepresentation();
		List<String> modelYrList = new ArrayList<String>();
		modelYrList.add("11111");
		vehicleSearchRepresentation.setModelYear(modelYrList);
		List<VehicleFileRepresentation> result = vehicleFileFinder.searchVehicleFile(vehicleSearchRepresentation);
		Assert.assertNotEquals(0, result.size());
		// vehicleFileRepo.deleteVehicleFile(vfSaved);
	}
}
