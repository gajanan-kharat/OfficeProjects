package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.DataSet;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffAppRepository;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffApplicationType;

/**
 * The Class FactorCoeffAppJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "FactorCoeffApplicationType")
public class FactorCoeffAppJpaRepository extends BaseJpaRepository<FactorCoeffApplicationType, Long> implements FactorCoeffAppRepository {

    /** The logger. */
    @Logging
    private final Logger logger = LoggerFactory.getLogger(FactorCoeffAppJpaRepository.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffAppRepository#saveFactorCoeffAppType(com.inetpsa.poc00.domain.
     * factorcoeffapplicationtype.FactorCoeffApplicationType)
     */
    @Override
    public FactorCoeffApplicationType saveFactorCoeffAppType(FactorCoeffApplicationType fcaObject) {

        if (fcaObject.getEntityId() == null || fcaObject.getEntityId() == 0)
            return super.save(fcaObject);

        return entityManager.merge(fcaObject);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffAppRepository#deleteFactorCoeffAppType(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public int deleteFactorCoeffAppType(Long id) {
        try {

            return entityManager.createQuery("DELETE FROM FactorCoeffApplicationType c where c.entityId = " + id).executeUpdate();

        } catch (Exception e) {
            logger.error(" Error occured while deleting data ", e);
            return 0;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffAppRepository#deleteAll()
     */
    @Override
    public long deleteAll() {

        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffAppRepository#count()
     */
    @Override
    public long count() {

        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.FactorCoeffApplicationType.FactorCoeffApplicationTypeRepository#getFactorCoeffAppTypeDataByLabel()
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<FactorCoeffApplicationType> getFactorCoeffAppTypeDataByLabel(String factorCoeffAppTypeLabel) {

        TypedQuery<FactorCoeffApplicationType> query = entityManager.createQuery(
                "SELECT fcat FROM FactorCoeffApplicationType fcat where fcat.label='" + factorCoeffAppTypeLabel + "'",
                FactorCoeffApplicationType.class);

        return query.getResultList();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffAppRepository#persistFactorCoeffAppType(com.inetpsa.poc00.domain.
     * factorcoeffapplicationtype.FactorCoeffApplicationType)
     */
    @Override
    public FactorCoeffApplicationType persistFactorCoeffAppType(FactorCoeffApplicationType object) {
        return super.save(object);
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public FactorCoeffApplicationType loadFactorCoeffAppType(long factorCoeffAppId) {

        return super.load(factorCoeffAppId);
    }
}
