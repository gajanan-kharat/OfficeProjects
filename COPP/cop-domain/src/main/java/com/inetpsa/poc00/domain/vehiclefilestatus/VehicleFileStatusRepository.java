package com.inetpsa.poc00.domain.vehiclefilestatus;

import org.seedstack.business.domain.GenericRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;

/**
 * The Interface VehicleFileStatusRepository.
 */
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface VehicleFileStatusRepository extends GenericRepository<VehicleFileStatus, Long> {

	/**
	 * Gets the vehicle file statusby label.
	 * 
	 * @param label the label
	 * @return the vehicle file statusby label
	 */
	public VehicleFileStatus getVehicleFileStatusbyLabel(String label);

	/**
	 * Load vehicle file status.
	 * 
	 * @param id the id
	 * @return the vehicle file status
	 */
	public VehicleFileStatus loadVehicleFileStatus(Long id);

	/**
	 * Save vehicle file status.
	 *
	 * @param vehicleFileStatusObj the vehicle file status obj
	 * @return the vehicle file status
	 */
	public VehicleFileStatus saveVehicleFileStatus(VehicleFileStatus vehicleFileStatusObj);
}
