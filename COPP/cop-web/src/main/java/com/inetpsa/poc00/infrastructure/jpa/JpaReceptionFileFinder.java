/*
 * Creation : Apr 15, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.rest.receptionfile.ReceptionFileFinder;
import com.inetpsa.poc00.rest.receptionfile.ReceptionFileRepresentation;

/**
 * The Class JpaReceptionFileFinder.
 */
public class JpaReceptionFileFinder implements ReceptionFileFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tirebrand.ReceptionFileFinder#getAllReceptionFiles()
	 */
	// AS UNION DOES NOT SUPPORTED IN HIBERNATE, WE ARE FORCED TO USE TWO DIFFERENT TYPE QUERY - ANKURP
	@Override
	public List<ReceptionFileRepresentation> getAllReceptionFiles(String userId) {

		logger.info("Load Reception File Data, User-Id : {}", userId);

		List<ReceptionFileRepresentation> list;

		/*
		 * Query to fetch the Vehicle file from the basket for Logged in User
		 */
		TypedQuery<ReceptionFileRepresentation> query = entityManager.createQuery("SELECT new " + ReceptionFileRepresentation.class.getName() + "(vf.receptionFile.entityId,vf.vehicle.counterMark,vf.vehicle.chasisNumber, vf.vehicle.registrationNumber, " + " vf.vehicle.technicalCase.tvv.projectCodeFamily.projectCodeLabel , vf.typeOfTest.label, rf.reservation,"
				+ " vf.entityId, CONCAT(COALESCE(u.firstName, ' ' ) , ' ' , COALESCE(u.lastName, ' ') , ' ' , COALESCE(u.userId, ' ')), pf.parkingNumber, vf.vehicle.entityId, pf.entityId, scd.arrivalDate)" + " FROM VehicleFile vf LEFT JOIN vf.receptionFile rf LEFT JOIN vf.schedule scd LEFT JOIN vf.vehicle.parkingLot pf LEFT JOIN rf.userRcp u "
				+ " WHERE vf.vehicleFileStatus.label = ?2 AND vf.basket.user.entityId in(select u.entityId from User u where u.team.entityId = (select team.entityId from Team team LEFT JOIN team.usersList ul where ul.userId = ?1))" + " ORDER BY CASE WHEN vf.basket.user.userId = ?1 THEN 1 ELSE 2 END ASC", ReceptionFileRepresentation.class);

		query.setParameter(1, userId);
		query.setParameter(2, Constants.VEHICLEFILESTATUS_LABEL);

		list = query.getResultList();

		/*
		 * Query to fetch the Vehicle file which are saved by logged in User
		 */
		TypedQuery<ReceptionFileRepresentation> query2 = entityManager.createQuery("SELECT new " + ReceptionFileRepresentation.class.getName() + "(vf.receptionFile.entityId,vf.vehicle.counterMark,vf.vehicle.chasisNumber, vf.vehicle.registrationNumber, " + " vf.vehicle.technicalCase.tvv.projectCodeFamily.projectCodeLabel , vf.typeOfTest.label, rf.reservation,"
				+ " vf.entityId, CONCAT(COALESCE(u.firstName, ' ' ) , ' ' , COALESCE(u.lastName, ' ') , ' ' , COALESCE(u.userId, ' ')), pf.parkingNumber, vf.vehicle.entityId, pf.entityId, scd.arrivalDate)" + " FROM VehicleFile vf LEFT JOIN vf.receptionFile rf LEFT JOIN vf.schedule scd LEFT JOIN vf.vehicle.parkingLot pf LEFT JOIN rf.userRcp u "
				+ " WHERE vf.vehicleFileStatus.label = ?2 AND vf.receptionFile.userRcp.entityId in(select u.entityId from User u where u.team.entityId = (select team.entityId from Team team LEFT JOIN team.usersList ul where ul.userId = ?1))", ReceptionFileRepresentation.class);
		query2.setParameter(1, userId);
		query2.setParameter(2, Constants.VEHICLEFILESTATUS_LABEL);
		List<ReceptionFileRepresentation> list2 = query2.getResultList();

		/*
		 * Loop to Add only uncommon record from list2
		 */
		for (ReceptionFileRepresentation receptionFileObj : list2) {
			boolean check = true;
			for (int i = 0; i < list.size(); i++) {
				if (receptionFileObj.getReceptionFileId() == list.get(i).getReceptionFileId()) {
					check = false;
					break;
				}
			}
			if (check) {
				list.add(receptionFileObj);
			}

		}
		return list;
	}

	@Override
	public ReceptionFileRepresentation getVehicleFileData(String id) {

		logger.info("getVehicleFileData ================================>" + id);
		ReceptionFileRepresentation rf = new ReceptionFileRepresentation();
		List<ReceptionFileRepresentation> list;
		TypedQuery<ReceptionFileRepresentation> query = entityManager.createQuery("SELECT new " + ReceptionFileRepresentation.class.getName() + "(vf.receptionFile.entityId,vf.vehicle.counterMark, vf.vehicle.chasisNumber, vf.vehicle.registrationNumber, " + " vf.vehicle.technicalCase.tvv.projectCodeFamily.projectCodeLabel , vf.typeOfTest.label, rf.reservation,"
				+ " vf.entityId, CONCAT(COALESCE(u.firstName, ' ' ) , ' ' , COALESCE(u.lastName, ' ') , ' ' , COALESCE(u.userId, ' ')), pf.parkingNumber, vf.vehicle.entityId, pf.entityId, scd.arrivalDate)" + " FROM VehicleFile vf LEFT JOIN vf.receptionFile rf LEFT JOIN vf.schedule scd LEFT JOIN vf.vehicle.parkingLot pf LEFT JOIN rf.userRcp u "
				+ " WHERE vf.vehicleFileStatus.label = ?2 AND vf.entityId = ?1 ", ReceptionFileRepresentation.class);
		Long vehicleId = Long.valueOf(id);
		query.setParameter(1, vehicleId);
		query.setParameter(2, Constants.VEHICLEFILESTATUS_LABEL);
		list = query.getResultList();
		logger.info("list ------------> list  :" + list);

		if (!list.isEmpty()) {
			rf = list.get(0);
			logger.info("getVehicleFileData ------------> rf  :" + rf);

		}
		logger.info("getVehicleFileData saasasasa------------> rf  :" + rf);
		return rf;
	}

}