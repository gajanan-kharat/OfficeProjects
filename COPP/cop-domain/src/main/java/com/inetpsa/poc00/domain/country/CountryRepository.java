package com.inetpsa.poc00.domain.country;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of Country.
 */

public interface CountryRepository extends GenericRepository<Country, Long> {

	/**
	 * Saves the Country.
	 *
	 * @param object the object
	 * @return the Country
	 */
	Country saveCountry(Country object);

	/**
	 * Persists the Country.
	 *
	 * @param object the Country to persist
	 * @return the country
	 */
	Country persistCountry(Country object);

	/**
	 * Count the number of categories in the repository.
	 * 
	 * @return Country count
	 */
	long count();

	/**
	 * Gets the all countries.
	 *
	 * @return the all countries
	 */
	public List<Country> getAllCountries();

	/**
	 * Delete all categories.
	 *
	 * @param id the id
	 * @return number of categories deleted
	 */
	long deleteCountry(long id);

	/**
	 * GetCountry by Label.
	 *
	 * @param label the label
	 * @return List<Country>
	 */
	List<Country> getCountryDataByLabel(String label);

	/**
	 * Load country.
	 *
	 * @param countryId the country id
	 * @return the country
	 */
	public Country loadCountry(long countryId);

	/**
	 * Gets the country for tvv.
	 *
	 * @param tvvId the tvv id
	 * @return the country for tvv
	 */
	List<Country> getCountryForTVV(Long tvvId);
}
