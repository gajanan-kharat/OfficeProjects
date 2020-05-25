/*
 * Creation : Apr 6, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa.es;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardRepository;

/**
 * The Class EmissionStandardJpaRepository.
 */
public class EmissionStandardJpaRepository extends BaseJpaRepository<EmissionStandard, Long> implements EmissionStandardRepository {

    /** The logger. */
    @Logging
    private Logger logger;

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.es.EmissionStandardRepository#count()
     */

    @Override
    public long count() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.es.EmissionStandardRepository#saveEmissionStd(com.inetpsa.poc00.domain.es.EmissionStandard)
     */
    /*
     * @see com.inetpsa.poc00.domain.es.EmissionStandardRepository#saveEmissionStd(com.inetpsa.poc00.domain.es.EmissionStandard)
     */
    @Override
    public EmissionStandard saveEmissionStd(EmissionStandard emissionStandard) {
        return super.save(emissionStandard);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.es.EmissionStandardRepository#persistEmissionStandard(com.inetpsa.poc00.domain.es.EmissionStandard)
     */
    /*
     * @see com.inetpsa.poc00.domain.es.EmissionStandardRepository#persistEmissionStandard(com.inetpsa.poc00.domain.es.EmissionStandard)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public EmissionStandard persistEmissionStandard(EmissionStandard emissionStandard) {
        return entityManager.merge(emissionStandard);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.es.EmissionStandardRepository#saveEmissionStandard(com.inetpsa.poc00.domain.es.EmissionStandard)
     */
    /*
     * @see com.inetpsa.poc00.domain.es.EmissionStandardRepository#saveEmissionStandard(com.inetpsa.poc00.domain.es.EmissionStandard)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public EmissionStandard saveEmissionStandard(com.inetpsa.poc00.domain.es.EmissionStandard emissionStandard) {
        if (emissionStandard.getEntityId() != null && emissionStandard.getEntityId() != 0)
            return super.save(emissionStandard);

        super.persist(emissionStandard);
        entityManager.flush();

        return super.load(emissionStandard.getEntityId());

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.es.EmissionStandardRepository#deleteEmissionStandard(long)
     */
    /*
     * @see com.inetpsa.poc00.domain.es.EmissionStandardRepository#deleteEmissionStandard(long)
     */
    @Override
    public int deleteEmissionStandard(long entityId) {
        logger.info("delete value from EmissionStandard where id=" + entityId);
        try {
            return entityManager.createQuery("DELETE FROM EmissionStandard where entityId=" + entityId).executeUpdate();

        } catch (Exception e) {
            logger.error(" Error occured while deleting data ", e);
            return 0;

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.es.EmissionStandardRepository#deleteAll()
     */
    /*
     * @see com.inetpsa.poc00.domain.es.EmissionStandardRepository#deleteAll()
     */
    @Override
    public long deleteAll() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.es.EmissionStandardRepository#deleteAll(long)
     */
    /*
     * @see com.inetpsa.poc00.domain.es.EmissionStandardRepository#deleteAll(long)
     */
    @Override
    public int deleteAll(long entityId) {
        logger.info("delete value from EmissionStandard where id=" + entityId);
        try {
            return entityManager.createQuery("DELETE FROM EmissionStandard where entityId=" + entityId).executeUpdate();
        } catch (Exception e) {
            logger.error(" Error occured while deleting data ", e);
            return 0;

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.es.EmissionStandardRepository#getAllEmissionStandards()
     */
    @Override
    public List<EmissionStandard> getAllEmissionStandards() {

        TypedQuery<EmissionStandard> query = entityManager.createQuery("SELECT ES FROM EmissionStandard ES", EmissionStandard.class);

        return query.getResultList();

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.es.EmissionStandardRepository#getEmissionStandardByLabel(java.lang.String)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<EmissionStandard> getEmissionStandardByLabel(String esLabel) {
        logger.info("querry running to check if label present");

        TypedQuery<EmissionStandard> query = entityManager.createQuery("FROM EmissionStandard es WHERE es.esLabel='" + esLabel + "'",
                EmissionStandard.class);

        return query.getResultList();
    }

    /**
     * {@inheritDoc} Get MAX version available for given label -used to set new version
     * 
     * @see com.inetpsa.poc00.rest.emissionstandard.EmissionStandardFinder#getMaxVersionForLabel(java.lang.String)
     */
    @Override
    public Double getMaxVersionForLabel(String label) {

        Query query = entityManager.createNativeQuery("SELECT MAX(CAST(T.VERSION AS DECIMAL(10,1))) FROM COPQTAES T WHERE T.LABEL = ?");

        query.setParameter(1, label);
        return ((BigDecimal) query.getSingleResult()).doubleValue();
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public EmissionStandard loadEmission(long emissionId) {
        return super.load(emissionId);
    }

}
