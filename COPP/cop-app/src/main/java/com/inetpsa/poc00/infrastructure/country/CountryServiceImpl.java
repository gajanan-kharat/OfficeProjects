/*
 * Creation : Dec 29, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.infrastructure.country;

import java.util.List;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.country.CountryService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.country.Country;
import com.inetpsa.poc00.domain.country.CountryFactory;
import com.inetpsa.poc00.domain.country.CountryRepository;

/**
 * The CountryServiceImpl class
 * 
 * @author mehaj
 */
public class CountryServiceImpl implements CountryService {
	@Inject
	CountryRepository countryRepository;

	@Inject
	CountryFactory countryFactory;

	@Inject
	TraceabilityService traceabilityService;
	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(CountryServiceImpl.class);

	@Override
	public Country persistCountry(Country country) {
		return countryRepository.persistCountry(country);
	}

	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	@Override
	public String saveCountry(Country countryObj) {

		List<Country> countryData = countryRepository.getCountryDataByLabel(countryObj.getLabel());
		if (!countryData.isEmpty()) {

			if (countryData.get(0).getEntityId() != countryObj.getEntityId()) {
				return countryData.get(0).getLabel();
			}
		} else if (countryObj.getEntityId() == null) {
			if (countryObj.getLabel() != null && countryObj.getLabel() != "") {
				// save
				Country newCountry = countryRepository.saveCountry(countryObj);
				traceabilityService.historyForSave(newCountry, ConstantsApp.SPECIFICCOP_SCREEN_ID);
			}
		} else {
			// update
			Country oldCountry = countryRepository.load(countryObj.getEntityId());
			traceabilityService.historyForUpdate(oldCountry, countryObj, ConstantsApp.SPECIFICCOP_SCREEN_ID);
			countryRepository.persistCountry(countryObj);

		}

		return ConstantsApp.SUCCESS;
	}

	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	@Override
	public String deleteCountry(long countryId) {

		Country objToDelete = countryRepository.load(countryId);
		try {
			long deletedrows = countryRepository.deleteCountry(countryId);
			if (deletedrows > 0) {
				logger.info("Sucessfully deleted Country data");
				traceabilityService.historyForDelete(objToDelete, ConstantsApp.SPECIFICCOP_SCREEN_ID);
				return ConstantsApp.SUCCESS;
			}
			logger.error(" Error occured while deleting data  :", countryId);
		} catch (Exception e) {
			logger.error(" Error occured while deleting data ", e);
			return ConstantsApp.FAILURE;
		}
		return ConstantsApp.FAILURE;
	}

}
