/*
 * Creation : Apr 12, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa.emsdependent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDL;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDLRepository;

/**
 * The Class EmissionStdDepTDLJpaRepository.
 */
public class EmissionStdDepTDLJpaRepository extends BaseJpaRepository<EmissionStdDepTDL, Long> implements EmissionStdDepTDLRepository {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(EmissionStdDepTDLJpaRepository.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDLRepository#saveEmissonStdDepTDL(com.inetpsa.poc00.domain.esdependent.tdl.
     * EmissionStdDepTDL)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public EmissionStdDepTDL saveEmissonStdDepTDL(EmissionStdDepTDL tdlObj) {

        try {
            if (tdlObj.getEntityId() != null && tdlObj.getEntityId() != 0)
                return super.save(tdlObj);
            super.persist(tdlObj);
            entityManager.flush();
        } catch (Exception e) {
            LOGGER.error(" **** End: Exception occured while running saveEmissonStdDepTDL Method", e);
        }
        return super.load(tdlObj.getEntityId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDLRepository#deleteAll()
     */
    @Override
    public long deleteAll() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDLRepository#count()
     */
    @Override
    public long count() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDLRepository#deleteEmsDepTDL(long)
     */
    @Override
    public void deleteEmsDepTDL(long entityId) {
        try {
            super.delete(entityId);
        } catch (Exception e) {
            LOGGER.error(" **** End: Exception occured while running saveEmissonStdDepTDL Method -deleting List", e);
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.emsdeptdl.EmsDepTDLFinder#getLatestEmissionStandardDepLists(java.lang.Long)
     */
    @Override
    public List<EmissionStdDepTDL> getLatestEmissionStandardDepLists(Long emsId) {

        List<EmissionStdDepTDL> resultList = new ArrayList<EmissionStdDepTDL>();

        Query idSubQuery = entityManager.createQuery(
                "select MAX(t1.entityId) from EmissionStdDepTDL t1 where t1.emissionStandard.entityId= " + emsId + " group by t1.label ");

        List<Long> idList = idSubQuery.getResultList();

        for (Long id : idList) {

            TypedQuery<EmissionStdDepTDL> query = entityManager.createQuery(
                    "select t from EmissionStdDepTDL t where t.entityId =" + id + " and t.emissionStandard.entityId= " + emsId,
                    EmissionStdDepTDL.class);

            resultList.add(query.getSingleResult());
        }

        return resultList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDLRepository#getEmissionStandardDepLists(java.lang.Long)
     */
    @Override
    public List<EmissionStdDepTDL> getEmissionStandardDepLists(Long esEntityId) {
        TypedQuery<EmissionStdDepTDL> query = entityManager
                .createQuery("select t from EmissionStdDepTDL t where t.emissionStandard.entityId= " + esEntityId, EmissionStdDepTDL.class);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.emsdeptdl.EmsDepTDLFinder#getMaxVersionForLabel(java.lang.String)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Double getMaxVersionForLabel(String label) {

        Query query = entityManager.createNativeQuery("SELECT MAX(CAST(T.VERSION AS DECIMAL(10,1))) FROM COPQTEDL T WHERE T.LABEL = ?");

        query.setParameter(1, label);

        return ((BigDecimal) query.getSingleResult()).doubleValue();
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public EmissionStdDepTDL loadEmsDepTdl(long emsDepTdlId) {

        return super.load(emsDepTdlId);
    }

}
