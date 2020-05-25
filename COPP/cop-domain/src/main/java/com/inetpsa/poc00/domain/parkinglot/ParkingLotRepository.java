package com.inetpsa.poc00.domain.parkinglot;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface ParkingLotRepository.
 */
public interface ParkingLotRepository extends GenericRepository<ParkingLot, Long> {

	/**
	 * Save parking lot.
	 * 
	 * @param parkingLot the parking lot
	 * @return the parking lot
	 */
	ParkingLot saveParkingLot(ParkingLot parkingLot);

}
