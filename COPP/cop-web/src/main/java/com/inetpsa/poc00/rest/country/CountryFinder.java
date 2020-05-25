package com.inetpsa.poc00.rest.country;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.country.Country;

/**
 * The Interface CountryFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface CountryFinder {

    /**
     * Find all pays data.
     * 
     * @return the list
     */
    List<CountryRepresentation> findAllPaysData();

    /**
     * Gets the all country names.
     * 
     * @return the all country names
     */
    List<String> getAllCountryNames();

    /**
     * Find country data by label.
     * 
     * @param countryLable the country lable
     * @return the list
     */
    List<CountryRepresentation> findCountryDataByLabel(String countryLable);

    /**
     * Country data.
     * 
     * @return the list
     */
    List<CountryRepresentation> getCountryData();

    List<Country> countryByTechnicalCase(Long tcId);

}
