package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.DataSet;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.fuel.FuelRepository;
import com.inetpsa.poc00.domain.fueltype.FuelType;

/**
 * The Class FuelJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "Fuel")
public class FuelJpaRepository extends BaseJpaRepository<Fuel, Long> implements FuelRepository {

    /** The logger. */
    @Logging
    private final Logger logger = LoggerFactory.getLogger(FuelJpaRepository.class);

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.fuel.FuelRepository#saveFuel(com.inetpsa.poc00.domain.fuel.Fuel)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Fuel saveFuel(Fuel fuelObject) {

        if (fuelObject.getEntityId() == null || fuelObject.getEntityId() == 0)
            return super.save(fuelObject);

        return entityManager.merge(fuelObject);

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.fuel.FuelRepository#saveFuel(com.inetpsa.poc00.domain.fueltype.FuelType)
     */
    @Override
    public FuelType saveFuel(FuelType object) {
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.fuel.FuelRepository#deleteAll()
     */
    @Override
    public long deleteAll() {
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.fuel.FuelRepository#count()
     */
    @Override
    public long count() {
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.fuel.FuelRepository#deleteFuel(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public int deleteFuel(Long id) {
        try {
            return entityManager.createQuery("DELETE FROM Fuel c where c.entityId = " + id).executeUpdate();
        } catch (Exception e) {
            logger.error(" Error occured while deleting data ", e);
            return 0;
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.fuel.FuelRepository#getAllFuel()
     */
    @Override
    public List<Fuel> getAllFuel() {

        TypedQuery<Fuel> query = entityManager.createQuery("SELECT fuel FROM Fuel fuel", Fuel.class);

        return query.getResultList();

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.fuel.FuelRepository#getAllFuelForCopiedFCList(java.lang.Long)
     */
    @Override
    public List<Fuel> getAllFuelForCopiedFCList(Long entityId) {

        Query query = entityManager.createNativeQuery(
                "SELECT * FROM COPQTAFL C JOIN COPQTMFF L ON (L.FUELID = C.ID) JOIN COPQTFLS FCL ON (FCL.ID =  L.FCLISTID) WHERE FCL.ID = ?1",
                Fuel.class);

        query.setParameter(1, entityId);

        List<Fuel> result = query.getResultList();

        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.fuel.FuelRepository#getAllFuelForCopiedPGList(java.lang.Long)
     */
    @Override
    public List<Fuel> getAllFuelForCopiedPGList(Long entityId) {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM COPQTAFL C JOIN COPQTMPF L ON (L.FUEL_ID = C.ID) JOIN COPQTPLL PGL ON (PGL.ID = L.PGLIST_ID) WHERE PGL.ID = ?1",
                Fuel.class);

        query.setParameter(1, entityId);

        List<Fuel> result = query.getResultList();

        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.fuel.FuelRepository#getFuelByLabel(java.lang.String)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<Fuel> getFuelByLabel(String label) {
        logger.info("querry running to check if label present");

        TypedQuery<Fuel> query = entityManager.createQuery("FROM Fuel f WHERE f.label='" + label + "'", Fuel.class);

        return query.getResultList();
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Fuel loadFuel(long fuelId) {

        return super.load(fuelId);
    }

}
