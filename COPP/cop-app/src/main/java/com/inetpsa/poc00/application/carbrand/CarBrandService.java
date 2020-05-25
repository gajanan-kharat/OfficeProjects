/*
 * Creation : Jan 2, 2017
 */
package com.inetpsa.poc00.application.carbrand;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.carbrand.CarBrand;

/**
 * The Interface CarBrandService.
 */
@Service
public interface CarBrandService {

	/**
	 * Save car brand.
	 *
	 * @param carBrand the car brand
	 * @return the car brand
	 */
	public CarBrand saveCarBrand(CarBrand carBrand);

	/**
	 * Delete car brand.
	 *
	 * @param carBrandId the car brand id
	 * @return true, if successful
	 */
	public boolean deleteCarBrand(Long carBrandId);

}
