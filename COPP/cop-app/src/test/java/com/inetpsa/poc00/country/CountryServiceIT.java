/*
 * Creation : Jan 23, 2017
 */
package com.inetpsa.poc00.country;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.country.CountryService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.country.Country;
import com.inetpsa.poc00.domain.country.CountryFactory;
import com.inetpsa.poc00.domain.country.CountryRepository;

@RunWith(SeedITRunner.class)
public class CountryServiceIT {

    @Inject
    CountryRepository countryRepo;

    @Inject
    CountryFactory countryFactory;

    @Inject
    CountryService countryService;

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void saveCountry() {
        Country country = countryFactory.createCountry();
        country.setLabel("newCountry");
        String response = countryService.saveCountry(country);
        assertEquals(response, ConstantsApp.SUCCESS);
        country.setLabel("updatedCountry");
        response = countryService.saveCountry(country);
        assertEquals(response, ConstantsApp.SUCCESS);
        country.setLabel("UpdetedNewCountry");
        response = countryService.saveCountry(country);
        assertEquals(response, ConstantsApp.SUCCESS);

    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void deleteCountry() {
        Country country = countryFactory.createCountry();
        country.setLabel("newCountry");
        String response = countryService.saveCountry(country);
        assertEquals(response, ConstantsApp.SUCCESS);
        List<Country> countryList = countryRepo.getCountryDataByLabel(country.getLabel());
        countryService.deleteCountry(countryList.get(0).getEntityId());
        Country loadedCountry = countryRepo.loadCountry(countryList.get(0).getEntityId());
        assertEquals(loadedCountry, null);

    }

}
