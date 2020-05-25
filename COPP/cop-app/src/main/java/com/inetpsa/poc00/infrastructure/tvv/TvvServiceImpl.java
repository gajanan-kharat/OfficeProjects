/*
 * Creation : Dec 30, 2016
 */
package com.inetpsa.poc00.infrastructure.tvv;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.application.tvv.TvvService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.carbrand.CarBrand;
import com.inetpsa.poc00.domain.carbrand.CarBrandRepository;
import com.inetpsa.poc00.domain.country.Country;
import com.inetpsa.poc00.domain.country.CountryRepository;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.factory.CarFactory;
import com.inetpsa.poc00.domain.factory.CarFactoryObjectCreation;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroupRepository;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.status.StatusRepository;
import com.inetpsa.poc00.domain.technicalcase.TechCaseFactory;
import com.inetpsa.poc00.domain.technicalcase.TechCaseRepository;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.technicalgroup.TechGroupRepository;
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvv.TVVFactory;
import com.inetpsa.poc00.domain.tvv.TVVRepository;
import com.inetpsa.poc00.domain.tvvvaluedcoastdown.TvvValuedCoastDown;
import com.inetpsa.poc00.domain.tvvvaluedcoastdown.TvvValuedCoastDownFactory;
import com.inetpsa.poc00.domain.tvvvaluedfcl.TvvValuedEsDepFCLFactory;

/**
 * The Class TvvServiceImpl.
 */
public class TvvServiceImpl implements TvvService {

	/** The tvv factory. */
	@Inject
	private TVVFactory tvvFactory;

	/** The car factory factory. */
	@Inject
	private CarFactoryObjectCreation carFactoryFactory;

	/** The valued coast down factory. */
	@Inject
	private TvvValuedCoastDownFactory valuedCoastDownFactory;

	/** The technical case factory. */
	@Inject
	private TechCaseFactory technicalCaseFactory;

	/** The tvv repository. */
	@Inject
	private TVVRepository tvvRepository;

	/** The car brand repository. */
	@Inject
	private CarBrandRepository carBrandRepository;

	/** The country repo. */
	@Inject
	private CountryRepository countryRepo;

	/** The status repository. */
	@Inject
	private StatusRepository statusRepository;

	/** The technical group repository. */
	@Inject
	private TechGroupRepository technicalGroupRepository;

	/** The regulation group repository. */
	@Inject
	private RegulationGroupRepository regulationGroupRepository;
	@Inject
	TraceabilityService tracabilityService;
	@Inject
	private TechCaseRepository technicalCaseRepository;
	@Inject
	private TvvValuedEsDepFCLFactory tvvValuedEsDepFCLFactory;

	/**
	 * Update tvv.
	 *
	 * @param tvvId the tvv id
	 * @param updatedTvv the updated tvv
	 * @param valuedCoastDown the valued coast down
	 * @return the tvv
	 */
	@Override
	public ServiceResponseDto updateTvv(long tvvId, TVV updatedTvv, TvvValuedCoastDown valuedCoastDown) {
		TVV oldObject = tvvRepository.loadTVV(tvvId);

		boolean changeVersion = compareTvvFields(updatedTvv, oldObject);

		// Compare Emission Standard
		EmissionStandard oldEs = oldObject.getTechnicalCase().getEmissionStandard();
		EmissionStandard newEs = updatedTvv.getTechnicalCase().getEmissionStandard();
		String oldEsLabel = oldEs.getEsLabel() + oldEs.getVersion();
		String newEsLabel = newEs.getEsLabel() + newEs.getVersion();
		if (!oldEsLabel.equalsIgnoreCase(newEsLabel)) {
			changeVersion = true;
		}

		// Modify TVV
		if (!changeVersion) {
			updatedTvv.setModificationDate(new Date());

			if (valuedCoastDown != null) {
				valuedCoastDown.setTvvObj(updatedTvv);
				updatedTvv.setTvvValuedCoastDown(valuedCoastDown);
			}

			TechnicalGroup tGroup = technicalGroupRepository.getTechnicalGroupForTechnicalCase(oldObject.getTechnicalCase().getEntityId());
			if (tGroup != null) {
				RegulationGroup rg = regulationGroupRepository.getRegulationGroupForTG(tGroup.getEntityId());
				if (rg != null)
					tGroup.setRegulationGroup(rg);
				updatedTvv.getTechnicalCase().setTechnicalGroup(tGroup);
			}

			TVV savedTvv = tvvRepository.saveTVV(updatedTvv);
			ServiceResponseDto responseDto = new ServiceResponseDto();
			responseDto.setTvv(savedTvv);
			responseDto.setChangeVersion(false);

			return responseDto;

		}
		// to Create new Version of TVV

		TVV newVersionTVV = createNewVersion(updatedTvv, newEs);
		newVersionTVV = tvvRepository.saveTVV(newVersionTVV);
		ServiceResponseDto responseDto = new ServiceResponseDto();
		responseDto.setTvv(newVersionTVV);
		responseDto.setChangeVersion(true);

		return responseDto;
	}

	/**
	 * Compare tvv fields.
	 *
	 * @param updatedTvv the updated tvv
	 * @param oldObject the old object
	 * @return true, if successful
	 */
	private boolean compareTvvFields(TVV updatedTvv, TVV oldObject) {
		boolean changeVersion = false;

		if (oldObject.getCategory() != null && updatedTvv.getClass() != null && oldObject.getClasz() != null) {

			if (!oldObject.getClasz().getLabel().equalsIgnoreCase(updatedTvv.getClasz().getLabel())) {
				changeVersion = true;

			}

		}
		if (oldObject.getFactorySet() != null && updatedTvv.getFactorySet() != null && (!oldObject.getFactorySet().equals(updatedTvv.getFactorySet()))) {

			changeVersion = true;
		}
		if (oldObject.getVehicalTechnology() != null && updatedTvv.getVehicalTechnology() != null && !oldObject.getVehicalTechnology().getLabel().equalsIgnoreCase(updatedTvv.getVehicalTechnology().getLabel())) {

			changeVersion = true;
		}
		if (oldObject.getFuelInjectionType() != null && !oldObject.getFuelInjectionType().getLabel().equalsIgnoreCase(updatedTvv.getFuelInjectionType().getLabel())) {
			changeVersion = true;
		}
		// Compare TVV Fields for change
		if (oldObject.getEngine().getEntityId() != updatedTvv.getEngine().getEntityId() || oldObject.getBodyWork().getEntityId() != updatedTvv.getBodyWork().getEntityId() || oldObject.getFuel().getEntityId() != updatedTvv.getFuel().getEntityId() || oldObject.getGearBox().getEntityId() != updatedTvv.getGearBox().getEntityId()
				|| oldObject.getProjectCodeFamily().getEntityId() != updatedTvv.getProjectCodeFamily().getEntityId() || changeVersion)
			changeVersion = true;
		return changeVersion;
	}

	/**
	 * Creates the new version.This method is called from Update TVV if some of the fields of TVV are Changed, new
	 * version should be changed instead of updating TVV
	 *
	 * @param tvv the tvv
	 * @param emissionStandard the emission standard
	 * @return the tvv
	 */
	private TVV createNewVersion(TVV tvv, EmissionStandard emissionStandard) {

		TVV newObject = tvvFactory.createTVV();
		// Copy All attributes excepts collections for lists
		newObject.setLabel(tvv.getLabel());
		newObject.setCreationDate(new Date());
		newObject.setModificationDate(new Date());
		// GEt latest version and set it to TVV
		Double majoMinorVersion = tvvRepository.getMaxVersionForLabel(tvv.getLabel()) + 1;
		newObject.setVersion(majoMinorVersion.toString());
		newObject.setBodyWork(tvv.getBodyWork());
		newObject.setFuel(tvv.getFuel());
		newObject.setSamplingLabel(tvv.getSamplingLabel());
		Set<CarFactory> factoryList = tvv.getFactorySet();
		Set<CarFactory> carFactorySet = new HashSet<>();
		if (factoryList != null) {
			for (CarFactory carFactoryObj : factoryList) {
				CarFactory carFactory = carFactoryFactory.createCarFactoryObject(carFactoryObj.getEntityId(), carFactoryObj.getLabel());

				carFactorySet.add(carFactory);

			}
		}
		newObject.setFactorySet(carFactorySet);
		/*List<TvvValuedEsDepFCL> tvvFCLList = tvv.getTvvValuedEsDepFCL();
		if (tvvFCLList != null) {
			for (TvvValuedEsDepFCL tvvValuedEsDepFCL : tvvFCLList) {
				newObject.getTvvValuedEsDepFCL().add(tvvValuedEsDepFCL);
			}
		}*/
		if (tvv.getFinalReductionRatio() != null)
			newObject.setFinalReductionRatio(tvv.getFinalReductionRatio());
		List<CarBrand> carBrandList = carBrandRepository.getCarBrandsForTVV(tvv.getEntityId());
		Set<CarBrand> carBrandSet = new HashSet<>();
		for (CarBrand carBrand : carBrandList) {

			carBrandSet.add(carBrand);

		}

		List<Country> countryList = countryRepo.getCountryForTVV(tvv.getEntityId());
		Set<Country> countrySet = new HashSet<>();
		for (Country country : countryList) {
			countrySet.add(country);
		}
		newObject.setCountrySet(countrySet);

		if (tvv.getTvvValuedCoastDown() != null) {
			TvvValuedCoastDown valuedCoastDown = valuedCoastDownFactory.createValuedCoastDown(tvv.getTvvValuedCoastDown());
			valuedCoastDown.setTvvObj(newObject);
			newObject.setTvvValuedCoastDown(valuedCoastDown);

		}
		newObject.setCarBrandSet(carBrandSet);
		newObject.setEngine(tvv.getEngine());
		newObject.setGearBox(tvv.getGearBox());
		newObject.setProjectCodeFamily(tvv.getProjectCodeFamily());
		newObject.setCategory(tvv.getCategory());
		newObject.setClasz(tvv.getClasz());
		newObject.setVehicalTechnology(tvv.getVehicalTechnology());
		newObject.setFuelInjectionType(tvv.getFuelInjectionType());
		// Set initial version to DRAFT
		Status status = statusRepository.getStatusForEmissionStandard(ConstantsApp.DRAFT);

		newObject.setStatus(status);

		// Associate Emission Standard through technical case
		TechnicalCase technicalCase = technicalCaseFactory.createTechCase();
		technicalCase.setEmissionStandard(emissionStandard);
		technicalCase.setTvv(newObject);
		newObject.setTechnicalCase(technicalCase);

		return newObject;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.tvv.TvvService#createVersion(java.lang.Long)
	 */
	@Override
	public TVV createVersion(Long tvvId) {
		TVV oldObject = tvvRepository.load(tvvId);

		EmissionStandard emissionStandard = oldObject.getTechnicalCase().getEmissionStandard();

		TVV newVersionTVV = createNewVersion(oldObject, emissionStandard);
		newVersionTVV = tvvRepository.saveTVV(newVersionTVV);
		return newVersionTVV;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.tvv.TvvService#copyTvv(java.lang.String, com.inetpsa.poc00.domain.tvv.TVV,
	 *      java.util.Set, java.util.Set, com.inetpsa.poc00.domain.tvvvaluedcoastdown.TvvValuedCoastDown)
	 */
	@Override
	public TVV copyTvv(String tvvLabel, TVV tvv, Set<CarFactory> carFactorySet, Set<CarBrand> carBrandSet, TvvValuedCoastDown valuedCoastDown) {
		TVV newObject = tvvFactory.createTVV();

		newObject.setLabel(tvvLabel);
		newObject.setCreationDate(new Date());
		newObject.setModificationDate(new Date());
		if (valuedCoastDown != null) {
			valuedCoastDown.setTvvObj(newObject);

			newObject.setTvvValuedCoastDown(valuedCoastDown);
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setLenient(false);

		newObject.setVersion("1.0");
		newObject.setBodyWork(tvv.getBodyWork());
		newObject.setFuel(tvv.getFuel());
		if (tvv.getFinalReductionRatio() != null)
			newObject.setFinalReductionRatio(tvv.getFinalReductionRatio());
		newObject.setFactorySet(carFactorySet);
		newObject.setCarBrandSet(carBrandSet);
		newObject.setEngine(tvv.getEngine());
		newObject.setGearBox(tvv.getGearBox());
		newObject.setProjectCodeFamily(tvv.getProjectCodeFamily());
		newObject.setCategory(tvv.getCategory());
		newObject.setVehicalTechnology(tvv.getVehicalTechnology());
		newObject.setFuelInjectionType(tvv.getFuelInjectionType());
		newObject.setClasz(tvv.getClasz());
		Status status = statusRepository.getStatusForEmissionStandard(ConstantsApp.DRAFT);

		newObject.setStatus(status);
		// Copy Lists
		EmissionStandard emissionStandard = tvv.getTechnicalCase().getEmissionStandard();

		// Associate Emission Standard through technical case
		TechnicalCase technicalCase = technicalCaseFactory.createTechCase();
		technicalCase.setEmissionStandard(emissionStandard);
		technicalCase.setTvv(newObject);
		newObject.setTechnicalCase(technicalCase);
		newObject = tvvRepository.saveTVV(newObject);
		return newObject;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.tvv.TvvService#saveTvvObject(com.inetpsa.poc00.domain.tvv.TVV)
	 */

	@Override
	public TVV saveTvvObject(boolean isChangeVersion, TVV tvvObjectToSave, TVV oldtvv) {
		tvvRepository.saveTVV(tvvObjectToSave);
		if (isChangeVersion)
			tracabilityService.historyForSave(tvvObjectToSave, ConstantsApp.TVV_SCREEN_ID);
		else
			tracabilityService.historyForUpdate(oldtvv, tvvObjectToSave, ConstantsApp.TVV_SCREEN_ID);
		return tvvObjectToSave;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.tvv.TvvService#deleteTvv(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public int deleteTvv(Long tvvId) {
		TVV tvv = tvvRepository.loadTVV(tvvId);

		if (tvv.getStatus().getLabel().equalsIgnoreCase(ConstantsApp.VALID)) {

			return -1;

		}

		tvvRepository.deleteTvv(tvv.getEntityId());
		tracabilityService.historyForDelete(tvv, ConstantsApp.TVV_SCREEN_ID);
		return 0;
	}
}
