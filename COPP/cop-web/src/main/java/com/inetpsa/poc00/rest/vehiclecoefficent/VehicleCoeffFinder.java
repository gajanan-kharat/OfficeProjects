package com.inetpsa.poc00.rest.vehiclecoefficent;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;

/**
 * The Interface VehicleCoeffFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface VehicleCoeffFinder {

	/**
	 * Gets the all vehicle coefficents.
	 * 
	 * @return the all vehicle coefficents
	 */
	public List<VehicleCoeffRepresentation> getAllVehicleCoefficents();

}
