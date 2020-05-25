/*
 * Creation : Jan 11, 2017
 */
package com.inetpsa.poc00.vehicletechnology;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.es.EmissionStandardRepository;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListFactory;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListRepository;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListFactory;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListRepository;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechFactory;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechRepository;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology;
import com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyFinder;
import com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyRepresentation;

// TODO: Auto-generated Javadoc
/**
 * The Class VehicleTechnologyFinderIT.
 */
@RunWith(SeedITRunner.class)
public class VehicleTechnologyFinderIT {

	/** The vehicle technology finder. */
	@Inject
	VehicleTechnologyFinder vehicleTechnologyFinder;
	/** The veh tech repo. */
	@Inject
	VehicleTechRepository vehicleTechRepository;

	/** The veh tech factory. */
	@Inject
	VehicleTechFactory vehTechFactory;

	/** The es factory. */
	@Inject
	EmissionStandardFactory esFactory;

	/** The emission standard repo. */
	@Inject
	EmissionStandardRepository emissionStandardRepo;

	/** The factor coeff list factory. */
	@Inject
	FactorCoeffListFactory factorCoeffListFactory;

	/** The factor coeff list repository. */
	@Inject
	FactorCoeffListRepository factorCoeffListRepository;

	/** The pollutant gas limit list factory. */
	@Inject
	PollutantGasLimitListFactory pollutantGasLimitListFactory;

	/** The pollutant gas limit list repo. */
	@Inject
	PollutantGasLimitListRepository pollutantGasLimitListRepo;

	/**
	 * Test get all vehicle technologies.
	 */
	@Test
	public final void testGetAllVehicleTechnologies() {
		VehicleTechnology vehTechObj = new VehicleTechnology();
		vehTechObj.setLabel("vehicleTechnologyLabel" + Calendar.getInstance().getTime());
		vehicleTechRepository.saveVehicleTechnology(vehTechObj);
		List<VehicleTechnologyRepresentation> listVehicleTechRepresentation = vehicleTechnologyFinder.getAllVehicleTechnologies();
		assertNotNull(listVehicleTechRepresentation);
	}

	/**
	 * Test find vehicle technology data by label.
	 */
	@Test
	public final void testFindVehicleTechnologyDataByLabel() {
		VehicleTechnology vehTechObj = new VehicleTechnology();
		vehTechObj.setLabel("vehicleTechnologyLabel1" + Calendar.getInstance().getTime());
		vehicleTechRepository.saveVehicleTechnology(vehTechObj);
		List<VehicleTechnologyRepresentation> vehicleTechRepresentationList = vehicleTechnologyFinder.findVehicleTechnologyDataByLabel("vehicleTechnologyLabel1" + Calendar.getInstance().getTime());
		assertNotNull(vehicleTechRepresentationList);
	}

	/**
	 * Test get all v technologies for es.
	 */
	@Test
	public final void testGetAllVTechnologiesForES() {
		EmissionStandard emissionStandard = esFactory.createEmissonStandard();
		emissionStandard.setEsLabel("EmissionStadForVT" + Calendar.getInstance().getTime());
		EmissionStandard emissionStandardSaved = emissionStandardRepo.saveEmissionStandard(emissionStandard);
		Set<EmissionStandard> emissionStandardList = new HashSet<EmissionStandard>();
		emissionStandardList.add(emissionStandardSaved);
		VehicleTechnology vehTechObj = new VehicleTechnology();
		vehTechObj.setLabel("vehicleTechnologyLabel1" + Calendar.getInstance().getTime());
		vehTechObj.setEmissionStandards(emissionStandardList);
		vehicleTechRepository.saveVehicleTechnology(vehTechObj);
		List<VehicleTechnologyRepresentation> vehTechList = vehicleTechnologyFinder.getAllVTechnologiesForES(emissionStandardSaved.getEntityId());
		assertNotNull(vehTechList);
	}

	/**
	 * Test get all v technologies for fc list.
	 */
	@Test
	public final void testGetAllVTechnologiesForFCList() {
		FactorCoefficentList fc = factorCoeffListFactory.createFactorCoeffList();
		fc.setLabel("FactorCoeffApplicationLabel" + Calendar.getInstance().getTime());
		FactorCoefficentList fcSaved = factorCoeffListRepository.saveFactorCoeffList(fc);
		VehicleTechnology vehTechObj = new VehicleTechnology();
		Set<FactorCoefficentList> factorCoefficentList = new HashSet<>();
		factorCoefficentList.add(fcSaved);
		vehTechObj.setLabel("vehicleTechnologyLabel12" + Calendar.getInstance().getTime());
		vehTechObj.setFactorCoefficentList(factorCoefficentList);
		vehicleTechRepository.saveVehicleTechnology(vehTechObj);
		List<VehicleTechnologyRepresentation> vehTechList = vehicleTechnologyFinder.getAllVTechnologiesForFCList(fcSaved.getEntityId());
		assertNotNull(vehTechList);
	}

	/**
	 * Test get all technologies for copied es.
	 */
	@Test
	public final void testGetAllTechnologiesForCopiedES() {
		EmissionStandard emissionStandard = esFactory.createEmissonStandard();
		emissionStandard.setEsLabel("EmissionStadForCopiedVT" + Calendar.getInstance().getTime());
		EmissionStandard emissionStandardSaved = emissionStandardRepo.saveEmissionStandard(emissionStandard);
		Set<EmissionStandard> emissionStandardList = new HashSet<EmissionStandard>();
		emissionStandardList.add(emissionStandardSaved);
		VehicleTechnology vehTechObj = new VehicleTechnology();
		vehTechObj.setLabel("vehicleTechnologyLabel13" + Calendar.getInstance().getTime());
		vehTechObj.setEmissionStandards(emissionStandardList);
		vehicleTechRepository.saveVehicleTechnology(vehTechObj);
		List<VehicleTechnology> vehTechList = vehicleTechnologyFinder.getAllTechnologiesForCopiedES(emissionStandardSaved.getEntityId());
		assertNotNull(vehTechList);
	}

	/**
	 * Test get all vt for copied pg list.
	 */
	@Test
	public final void testGetAllVTForCopiedPGList() {
		PollutantGasLimitList pollutantGasLimitList = pollutantGasLimitListFactory.createPollutantGasLimitList();
		pollutantGasLimitList.setLabel("PollutantGasLimitListLabel");
		PollutantGasLimitList pollutantGasLimitListSaved = pollutantGasLimitListRepo.savePollutantGasLimitList(pollutantGasLimitList);
		Set<PollutantGasLimitList> pollutantGasLimitListList = new HashSet<PollutantGasLimitList>();
		pollutantGasLimitListList.add(pollutantGasLimitListSaved);
		VehicleTechnology vehTechObj = new VehicleTechnology();
		vehTechObj.setLabel("vehicleTechnologyLabel14" + Calendar.getInstance().getTime());
		vehTechObj.setPollutantGasLimitList(pollutantGasLimitListList);
		vehicleTechRepository.saveVehicleTechnology(vehTechObj);
		List<VehicleTechnology> vehTechList = vehicleTechnologyFinder.getAllVTForCopiedPGList(pollutantGasLimitListSaved.getEntityId());
		assertNotNull(vehTechList);
	}

	/**
	 * Test get all vt for copied fc list.
	 */
	@Test
	public final void testGetAllVTForCopiedFCList() {
		FactorCoefficentList fc = factorCoeffListFactory.createFactorCoeffList();
		fc.setLabel("FactorCoeffApplicationLabel" + Calendar.getInstance().getTime());
		FactorCoefficentList fcSaved = factorCoeffListRepository.saveFactorCoeffList(fc);
		VehicleTechnology vehTechObj = new VehicleTechnology();
		Set<FactorCoefficentList> factorCoefficentList = new HashSet<>();
		factorCoefficentList.add(fcSaved);
		vehTechObj.setLabel("vehicleTechnologyLabel15" + Calendar.getInstance().getTime());
		vehTechObj.setFactorCoefficentList(factorCoefficentList);
		vehicleTechRepository.saveVehicleTechnology(vehTechObj);
		List<VehicleTechnology> vehTechList = vehicleTechnologyFinder.getAllVTForCopiedFCList(fcSaved.getEntityId());
		assertNotNull(vehTechList);
	}

}
