package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.domain.country.Country;
import com.inetpsa.poc00.rest.country.CountryFinder;
import com.inetpsa.poc00.rest.country.CountryRepresentation;

/**
 * The Class JPACountryFinder.
 */
public class JPACountryFinder implements CountryFinder {

    /** The entity manager. */
    @Inject
    private EntityManager entityManager;

    /** The logger. */
    @Logging
    private Logger logger;

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.country.CountryFinder#findAllPaysData()
     */
    /*
     * @see com.inetpsa.poc00.rest.Country.CountryFinder#findAllPaysData() get Country Data
     */
    @Override
    public List<CountryRepresentation> findAllPaysData() {

        logger.info("querry running to get Country value");
        TypedQuery<CountryRepresentation> query = entityManager.createQuery(
                "select new " + CountryRepresentation.class.getName() + "(c.label,c.entityId)" + " from Country c" + " ORDER BY c.entityId asc",
                CountryRepresentation.class);

        return query.getResultList();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.country.CountryFinder#getAllCountries()
     */
    @Override
    public List<String> getAllCountryNames() {
        logger.info("querry running to get Country value");
        TypedQuery<String> query = entityManager.createQuery("select c.label from Country c", String.class);

        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.country.CountryFinder#findCountryDataByLabel(java.lang.String)
     */
    @Override
    public List<CountryRepresentation> findCountryDataByLabel(String countryLable) {
        logger.info("querry running to check if label present");

        TypedQuery<CountryRepresentation> query = entityManager.createQuery(
                "select new " + CountryRepresentation.class.getName() + "(c.entityId) from Country c where c.label='" + countryLable + "'",
                CountryRepresentation.class);

        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.country.CountryFinder#CountryData()
     */
    @Override
    public List<CountryRepresentation> getCountryData() {
        logger.info("querry running to get CountryData");

        TypedQuery<CountryRepresentation> query = entityManager.createQuery(
                "select new " + CountryRepresentation.class.getName() + "(c.entityId,c.label) from Country c", CountryRepresentation.class);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.country.CountryFinder#countryByTechnicalCase(java.lang.Long)
     */
    @Override
    public List<Country> countryByTechnicalCase(Long tcId) {
        TypedQuery<Country> query = entityManager.createQuery(
                "FROM Country c where c.entityId in (SELECT c.entityId FROM TVV tvv LEFT JOIN tvv.countrySet c where tvv.technicalCase.entityId="
                        + tcId + ")",
                Country.class);
        List<Country> countryList = query.getResultList();

        if (countryList.isEmpty()) {
            TypedQuery<Country> query2 = entityManager.createQuery("FROM Country c", Country.class);
            countryList = query2.getResultList();
        }
        return countryList;
    }
}
