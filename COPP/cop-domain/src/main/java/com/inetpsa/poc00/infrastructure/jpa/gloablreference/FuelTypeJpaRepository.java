package com.inetpsa.poc00.infrastructure.jpa.gloablreference;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.fueltype.FuelType;
import com.inetpsa.poc00.domain.fueltype.FuelTypeRepository;

/**
 * The Class FuelTypeJpaRepository.
 */
public class FuelTypeJpaRepository extends BaseJpaRepository<FuelType, Long> implements FuelTypeRepository {

    /** The logger. */
    @Logging
    private Logger logger;

    /*
     * @see com.inetpsa.poc00.domain.fueltype.FuelTypeRepository#saveFuelType(com.inetpsa.poc00.domain.fueltype.FuelType)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public FuelType saveFuelType(FuelType fuelType) {

        return super.save(fuelType);

    }

    /*
     * @see com.inetpsa.poc00.domain.fueltype.FuelTypeRepository#persistFuelType(com.inetpsa.poc00.domain.fueltype.FuelType)
     */
    @Override

    public FuelType persistFuelType(FuelType fuelType) {

        return entityManager.merge(fuelType);

    }

    /*
     * @see com.inetpsa.poc00.domain.fueltype.FuelTypeRepository#deleteAll(long)
     */
    @Override
    public int deleteAll(long id) {
        logger.info("deleting value from fuleType table where id=" + id);
        try {
            return entityManager.createQuery("DELETE FROM FuelType where entityId=" + id).executeUpdate();
        } catch (Exception e) {
            logger.error(" Error occured while deleting data ", e);
            return 0;

        }
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public FuelType loadFuelType(long fuelTypeId) {

        return super.load(fuelTypeId);
    }

}
