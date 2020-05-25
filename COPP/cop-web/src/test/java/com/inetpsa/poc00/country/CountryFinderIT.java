/*
 * Creation : Jan 16, 2017
 */
package com.inetpsa.poc00.country;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.domain.country.Country;
import com.inetpsa.poc00.domain.country.CountryFactory;
import com.inetpsa.poc00.domain.country.CountryRepository;
import com.inetpsa.poc00.rest.country.CountryFinder;
import com.inetpsa.poc00.rest.country.CountryRepresentation;

@RunWith(SeedITRunner.class)
public class CountryFinderIT {
	@Inject
	CountryFinder countryFinder;
	@Inject
	CountryFactory countryFactory;
	@Inject
	CountryRepository countryRepository;

	@Test
	public final void testFindAllPaysData() {
		Country country = countryFactory.createCountry();
		country.setLabel("CountryLabel" + Calendar.getInstance().getTime());
		countryRepository.saveCountry(country);
		List<CountryRepresentation> listCountry = countryFinder.findAllPaysData();
		assertNotNull(listCountry);
	}

	@Test
	public final void testGetAllCountryNames() {
		Country country = countryFactory.createCountry();
		country.setLabel("CountryLabel" + Calendar.getInstance().getTime());
		countryRepository.saveCountry(country);
		List<String> listCountry = countryFinder.getAllCountryNames();
		assertNotNull(listCountry);
	}

	@Test
	public final void testFindCountryDataByLabel() {
		Country country = countryFactory.createCountry();
		country.setLabel("CountryLabel" + Calendar.getInstance().getTime());
		Country countrySaved = countryRepository.saveCountry(country);
		List<CountryRepresentation> listCountry = countryFinder.findCountryDataByLabel(countrySaved.getLabel());
		assertNotNull(listCountry);
	}

	@Test
	public final void testGetCountryData() {
		Country country = countryFactory.createCountry();
		country.setLabel("CountryLabel" + Calendar.getInstance().getTime());
		countryRepository.saveCountry(country);
		List<CountryRepresentation> listCountry = countryFinder.getCountryData();
		assertNotNull(listCountry);
	}

}
