package com.inetpsa.poc00.infrastructure.jpa.gloablreference;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.country.Country;
import com.inetpsa.poc00.domain.country.CountryRepository;

/**
 * The Class CountryJpaRepository.
 */
public class CountryJpaRepository extends BaseJpaRepository<Country, Long> implements CountryRepository {

	/** The logger. */
	@Logging
	private Logger logger;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.country.CountryRepository#saveCountry(com.inetpsa.poc00.domain.country.Country)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Country saveCountry(Country country) {

		return super.save(country);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.country.CountryRepository#persistCountry(com.inetpsa.poc00.domain.country.Country)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Country persistCountry(Country country) {

		return entityManager.merge(country);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.country.CountryRepository#deleteAll(long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public long deleteCountry(long id) {
		logger.info("delete value from Country where id=" + id);
		try {
			return entityManager.createQuery("DELETE FROM Country where entityId=" + id).executeUpdate();
		} catch (Exception e) {
			logger.error(" Error occured while deleting data ", e);
			return 0;

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.country.CountryRepository#count()
	 */
	@Override
	public long count() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.country.CountryRepository#getAllCountries()
	 */
	@Override
	public List<Country> getAllCountries() {

		TypedQuery<Country> query = entityManager.createQuery("SELECT ctry FROM Country ctry", Country.class);

		return query.getResultList();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.country.CountryRepository#getCountryByLabel()
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<Country> getCountryDataByLabel(String ctryLabel) {

		TypedQuery<Country> query = entityManager.createQuery("SELECT ctry FROM Country ctry where ctry.label='" + ctryLabel + "'", Country.class);

		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.country.CountryRepository#loadCountry(long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Country loadCountry(long countryId) {

		return super.load(countryId);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.country.CountryRepository#getCountryForTVV(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<Country> getCountryForTVV(Long tvvId) {
		TypedQuery<Country> query = entityManager.createQuery("select ctry from Country ctry INNER JOIN ctry.tvvSet t where t.entityId=" + tvvId, Country.class);
		return query.getResultList();
	}
}
