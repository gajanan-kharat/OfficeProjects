/*
 * Creation : Jan 11, 2017
 */
/**
 * 
 */
package com.inetpsa.poc00.vehicletechnology;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.vehicletechnology.VehicleTechnologyService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechFactory;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechRepository;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology;

// TODO: Auto-generated Javadoc
/**
 * The VehicleTechnologyServiceIT.
 *
 * @author mehaj
 */
@RunWith(SeedITRunner.class)
public class VehicleTechnologyServiceIT {
	/** The veh tech repo. */
	@Inject
	VehicleTechRepository vehicleTechRepository;

	/** The vehicle technology service. */
	@Inject
	VehicleTechnologyService vehicleTechnologyService;

	/** The veh tech factory. */
	@Inject
	VehicleTechFactory vehTechFactory;

	/**
	 * Test method for
	 * {@link com.inetpsa.poc00.infrastructure.vehicletechnology.VehicleTechnologyServiceImpl#saveVehicleTechnology(com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology)}
	 * .
	 */
	@Test
	@WithUser(id = "poch1", password = "poch1")

	public final void testSaveVehicleTechnology() {
		VehicleTechnology vehTechObj = new VehicleTechnology();
		vehTechObj.setLabel("vehicleTechnologyLabel" + Calendar.getInstance().getTime());
		String result = vehicleTechnologyService.saveVehicleTechnology(vehTechObj);
		assertEquals(ConstantsApp.SUCCESS, result);
	}

	/**
	 * Test method for
	 * {@link com.inetpsa.poc00.infrastructure.vehicletechnology.VehicleTechnologyServiceImpl#deleteVehicleTechnology(long)}
	 * .
	 */
	@Test
	@WithUser(id = "poch1", password = "poch1")
	public final void testDeleteVehicleTechnology() {
		VehicleTechnology vehTechObj = new VehicleTechnology();
		vehTechObj.setLabel("vehicleTechnologyLabel1" + Calendar.getInstance().getTime());
		String result = vehicleTechnologyService.saveVehicleTechnology(vehTechObj);
		List<VehicleTechnology> vehTechObjSaved = vehicleTechRepository.getVehicleTechnologyByLabel("vehicleTechnologyLabel1" + Calendar.getInstance().getTime());
		String resultDelete = vehicleTechnologyService.deleteVehicleTechnology(vehTechObjSaved.get(0).getEntityId());
		assertEquals(ConstantsApp.SUCCESS, resultDelete);
	}

}
