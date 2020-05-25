package com.inetpsa.poc00.infrastructure.jpa.gloablreference;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.factory.CarFactory;
import com.inetpsa.poc00.domain.factory.CarFactoryRepository;

/**
 * The Class CarFactoryJpaRepository.
 */
public class CarFactoryJpaRepository extends BaseJpaRepository<CarFactory, Long> implements CarFactoryRepository {

	/** The logger. */
	@Logging
	private Logger logger;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.factory.CarFactoryRepository#saveFactoryObject(com.inetpsa.poc00.domain.factory.CarFactory)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public CarFactory saveFactoryObject(CarFactory carFactory) {

		return super.save(carFactory);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.factory.CarFactoryRepository#persistFactoryObject(com.inetpsa.poc00.domain.factory.CarFactory)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public CarFactory persistFactoryObject(CarFactory carFactory) {

		return entityManager.merge(carFactory);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.factory.CarFactoryRepository#deleteAll(long)
	 */

	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public long deleteCarFactory(long id) {
		logger.info("delete value from Car Factory where id=" + id);
		try {
			return entityManager.createQuery("DELETE FROM CarFactory where entityId=" + id).executeUpdate();
		} catch (Exception e) {
			logger.error(" Error occured while deleting data ", e);
			return 0;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.factory.CarFactoryRepository#count()
	 */
	@Override
	public long count() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.country.CountryRepository#getCountryByLabel()
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<CarFactory> getCarFactoryDataByLabel(String carFacLabel) {

		TypedQuery<CarFactory> query = entityManager.createQuery("SELECT carFac FROM CarFactory carFac where carFac.label='" + carFacLabel + "'", CarFactory.class);

		return query.getResultList();

	}

	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public CarFactory loadCarFactory(long carFacId) {

		return super.load(carFacId);
	}
}
