package com.inetpsa.poc00.domain.factory;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of Factory.
 */

public interface CarFactoryRepository extends GenericRepository<CarFactory, Long> {

    /**
     * Saves the Factory.
     * 
     * @param object the factory to save
     * @return the Carfactory
     */
    CarFactory saveFactoryObject(CarFactory object);

    /**
     * Persists the Factory.
     *
     * @param object the factory to persist
     * @return the car factory
     */
    CarFactory persistFactoryObject(CarFactory object);

    /**
     * Count the number of categories in the repository.
     * 
     * @return factory count
     */
    long count();

    /**
     * Delete all categories.
     *
     * @param carFactoryId the car factory id
     * @return Deleted carfactory Id
     */
    long deleteCarFactory(long carFactoryId);

    /**
     * Gets the car factory data by label.
     *
     * @param carFacLabel the car fac label
     * @return the car factory data by label
     */
    List<CarFactory> getCarFactoryDataByLabel(String carFacLabel);

    public CarFactory loadCarFactory(long carFacId);
}
