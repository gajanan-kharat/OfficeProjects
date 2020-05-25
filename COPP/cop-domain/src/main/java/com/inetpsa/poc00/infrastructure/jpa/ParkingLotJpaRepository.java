package com.inetpsa.poc00.infrastructure.jpa;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.seed.DataSet;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.parkinglot.ParkingLot;
import com.inetpsa.poc00.domain.parkinglot.ParkingLotRepository;

/**
 * The Class ParkingLotJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "ParkingLot")
public class ParkingLotJpaRepository extends BaseJpaRepository<ParkingLot, Long> implements ParkingLotRepository {

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.parkinglot.ParkingLotRepository#saveParkingLot(com.inetpsa.poc00.domain.parkinglot.ParkingLot)
	 */
	@Override
	public ParkingLot saveParkingLot(ParkingLot parkingLot) {
		
		if (parkingLot.getEntityId() == null || parkingLot.getEntityId() == 0) {
			return super.save(parkingLot);
		}

		return entityManager.merge(parkingLot);
	}
	

}
