package com.inetpsa.poc00.rest.carbrand;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;

/**
 * The Interface CarBrandFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface CarBrandFinder {

	/**
	 * Find all constructeur data.
	 *
	 * @return the list
	 */
	List<CarBrandRepresentation> findAllConstructeurData();

	/**
	 * Find all car brand data.
	 *
	 * @return the list
	 */
	List<CarBrandRepresentation> findAllCarBrandData();

	/**
	 * Find car brand data by label.
	 *
	 * @param brandLable the brand lable
	 * @param makerLable the maker lable
	 * @return the list
	 */
	List<CarBrandRepresentation> findCarBrandDataByLabel(String brandLable, String makerLable);

	/**
	 * Gets the car brands for TVV.
	 *
	 * @param tvvId the tvv id
	 * @return the car brands for TVV
	 */
	List<CarBrandRepresentation> getCarBrandsForTVV(long tvvId);

	/**
	 * Gets the all car brand data for TVV.
	 *
	 * @param kmatList the kmat list
	 * @return the all car brand data for TVV
	 */
	List<CarBrandRepresentation> getAllCarBrandDataForTVV(List<String> kmatList);

	/**
	 * Find all car brand label.
	 *
	 * @return the list
	 */
	public List<String> findAllCarBrandLabel();

}
