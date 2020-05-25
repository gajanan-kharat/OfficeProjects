/*
 * Creation : Jan 17, 2017
 */
package com.inetpsa.poc00.vehiclecoefficient;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoeffFactory;
import com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoeffRepository;
import com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoefficents;
import com.inetpsa.poc00.rest.vehiclecoefficent.VehicleCoeffFinder;
import com.inetpsa.poc00.rest.vehiclecoefficent.VehicleCoeffRepresentation;

@RunWith(SeedITRunner.class)
public class VehicleCoeffFinderIT {

	/** The v co repo. */
	@Inject
	VehicleCoeffRepository vCoRepo;
	/** The v co repo. */
	@Inject
	VehicleCoeffFactory vehicleCoeffFactory;
	@Inject
	VehicleCoeffFinder vehicleCoeffFinder;

	@Test
	public final void testGetAllVehicleCoefficents() {
		VehicleCoefficents vehicleCoefficents = vehicleCoeffFactory.createVehicleCoefficents();
		vehicleCoefficents.setLabel("VehicleCoefficentsLabel" + Calendar.getInstance().getTime());
		vCoRepo.saveVehicleCoefficents(vehicleCoefficents);
		List<VehicleCoeffRepresentation> list = vehicleCoeffFinder.getAllVehicleCoefficents();
		assertNotNull(list);
	}

}
