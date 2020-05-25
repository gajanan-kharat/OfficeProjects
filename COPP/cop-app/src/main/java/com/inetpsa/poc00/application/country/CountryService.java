/*
 * Creation : Dec 29, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.application.country;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.country.Country;

/**
 * The CountryService.
 *
 * @author mehaj
 */
@Service
public interface CountryService {
    
    /**
     * Persist country.
     *
     * @param country the country
     * @return the country
     */
    public Country persistCountry(Country country);

    /**
     * Save country.
     *
     * @param country the country
     * @return the string
     */
    public String saveCountry(Country country);

    /**
     * Delete country.
     *
     * @param countryId the country id
     * @return the string
     */
    public String deleteCountry(long countryId);
}
