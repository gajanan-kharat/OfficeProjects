package com.inetpsa.poc00.rest.carfactory;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;

/**
 * The Interface CarFactoryFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface CarFactoryFinder {

    /**
     * Find all usine data.
     * 
     * @return the list
     */
    List<CarFactoryRepresentation> findAllUsineData();

    /**
     * Find car factory data by label.
     * 
     * @param carFactoryLabel the car factory label
     * @return the list
     */
    List<CarFactoryRepresentation> findCarFactoryDataByLabel(String carFactoryLabel);

    List<CarFactoryRepresentation> getFactoriesForTVV(Long entityId);

}
