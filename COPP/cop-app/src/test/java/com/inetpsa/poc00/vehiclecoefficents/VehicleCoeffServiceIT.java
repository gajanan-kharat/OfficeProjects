/*
 * Creation : Jan 17, 2017
 */
package com.inetpsa.poc00.vehiclecoefficents;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.vehiclecoefficent.VehicleCoeffService;
import com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoeffFactory;
import com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoeffRepository;
import com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoefficents;

@RunWith(SeedITRunner.class)
public class VehicleCoeffServiceIT {
	/** The v co repo. */
	@Inject
	VehicleCoeffRepository vCoRepo;
	/** The v co repo. */
	@Inject
	VehicleCoeffFactory vehicleCoeffFactory;
	@Inject
	VehicleCoeffService vehicleCoeffService;

	@Test
	@WithUser(id = "poch1", password = "poch1")
	public final void testSaveVehicleCoefficents() {
		VehicleCoefficents vehicleCoefficents = vehicleCoeffFactory.createVehicleCoefficents();
		vehicleCoefficents.setLabel("VehicleCoefficentsLabel" + Calendar.getInstance().getTime());
		VehicleCoefficents vehicleCoefficentsSaved = vehicleCoeffService.saveVehicleCoefficents(vehicleCoefficents);
		assertNotNull(vehicleCoefficentsSaved);
	}

}
