package com.inetpsa.poc00.rest.tirebrand;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.tirebrand.TireBrand;

/**
 * The Interface TireBrandFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface TireBrandFinder {

    /**
     * Gets the all tire brands.
     *
     * @return the all tire brands
     */
    List<TireBrand> getAllTireBrands();

    /**
     * Gets the tire brand by label.
     *
     * @param label the label
     * @return the tire brand by label
     */
    List<TireBrand> getTireBrandByLabel(String label);

}
