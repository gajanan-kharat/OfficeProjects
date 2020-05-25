package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.seed.DataSet;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.receptionfile.ReceptionFile;
import com.inetpsa.poc00.domain.receptionfile.ReceptionFileRepository;

/**
 * The Class ReceptionFileJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "ReceptionFile")
public class ReceptionFileJpaRepository extends BaseJpaRepository<ReceptionFile, Long> implements ReceptionFileRepository {

	/** The jpa entity manager. */
	@Inject
	private EntityManager jpaEntityManager;

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(ReceptionFileJpaRepository.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tirebrand.ReceptionFileRepository#deleteAll()
	 */
	@Override
	public int deleteAll() {
		try {

			return jpaEntityManager.createQuery("DELETE FROM ReceptionFile t ").executeUpdate();

		} catch (Exception e) {
			logger.error(" Error occured while deleting data ", e);
			return 0;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tirebrand.ReceptionFileRepository#count()
	 */
	@Override
	public long count() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tirebrand.ReceptionFileRepository#saveReceptionFile(com.inetpsa.poc00.domain.tirebrand.ReceptionFile)
	 */
	@Override
	public ReceptionFile saveReceptionFile(ReceptionFile receptionFile) {
		if (receptionFile.getEntityId() == null)
			return super.save(receptionFile);

		return jpaEntityManager.merge(receptionFile);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tirebrand.ReceptionFileRepository#persistReceptionFile(com.inetpsa.poc00.domain.tirebrand.ReceptionFile)
	 */
	@Override
	public void persistReceptionFile(ReceptionFile receptionFile) {
		saveReceptionFile(receptionFile);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tirebrand.ReceptionFileRepository#getAllReceptionFiles()
	 */
	@Override
	public List<ReceptionFile> getAllReceptionFiles() {
		TypedQuery<ReceptionFile> query = jpaEntityManager.createQuery("SELECT t FROM ReceptionFile t ", ReceptionFile.class);

		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tirebrand.ReceptionFileRepository#deleteReceptionFile(java.lang.Long)
	 */
	@Override
	public int deleteReceptionFile(Long id) {

		try {

			return jpaEntityManager.createQuery("DELETE FROM ReceptionFile t where t.entityId = " + id).executeUpdate();

		} catch (Exception e) {
			logger.error(" Error occured while deleting data ", e);
			return 0;
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.receptionfile.ReceptionFileRepository#updateReceptionFile(com.inetpsa.poc00.domain.receptionfile.ReceptionFile)
	 */
	@Override
	public int updateReceptionFile(ReceptionFile receptionFile) {/*
																	 * if (receptionFile.getEntityId() == null || receptionFile.getEntityId() == 0) { int result = 0; try {
																	 * result = entityManager .createQuery("update ReceptionFile t , VehicleFile v " + "set t.reservation =" +
																	 * receptionFile.getReservation() + ", v.vehicle.parkingLot.parkingNumber=" +
																	 * receptionFile.getVehicleFile().getVehicle().getParkingLot().getParkingNumber() +
																	 * "where  where v.receptionFile.entityId = t.entityId and t.entityId=" + receptionFile.getEntityId())
																	 * .executeUpdate(); } catch (Exception e) { logger.error("\n*** Result of update exception : {} ", e);
																	 * 
																	 * } return result; }
																	 */
		return 0;
	}

}
