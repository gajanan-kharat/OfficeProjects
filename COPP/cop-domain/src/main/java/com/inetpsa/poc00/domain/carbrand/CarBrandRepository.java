package com.inetpsa.poc00.domain.carbrand;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

// TODO: Auto-generated Javadoc
/**
 * Repository interface of CarBrand.
 */

public interface CarBrandRepository extends GenericRepository<CarBrand, Long> {

    /**
     * Saves the CarBrand.
     * 
     * @param object the CarBrand to save
     * @return the CarBrand
     */
    CarBrand saveCarBrand(CarBrand object);

    /**
     * Persists the CarBrand.
     * 
     * @param object the CarBrand to persist
     * @return the car brand
     */
    CarBrand persistCarBrand(CarBrand object);

    /**
     * Delete all categories.
     * 
     * @param id the id
     * @return number of categories deleted
     */
    int deleteAll(long id);

    /**
     * Count the number of categories in the repository.
     * 
     * @return CarBrand count
     */
    long count();

    /**
     * Gets the all car brand.
     * 
     * @return the all car brand
     */
    public List<CarBrand> getAllCarBrand();

    /**
     * Gets the car brands for tvv.
     *
     * @param entityId the entity id
     * @return the car brands for tvv
     */
    List<CarBrand> getCarBrandsForTVV(Long entityId);

    public CarBrand loadCarBrand(long carBrandId);

}
