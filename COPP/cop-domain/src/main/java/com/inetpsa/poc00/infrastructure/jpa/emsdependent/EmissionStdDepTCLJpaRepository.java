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
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCLRepository;

/**
 * The Class EmissionStdDepTCLJpaRepository.
 */
public class EmissionStdDepTCLJpaRepository extends BaseJpaRepository<EmissionStdDepTCL, Long> implements EmissionStdDepTCLRepository {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(EmissionStdDepTCLJpaRepository.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCLRepository#deleteAll()
     */
    @Override
    public long deleteAll() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCLRepository#count()
     */
    @Override
    public long count() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCLRepository#saveEmissonStdDepTCL(com.inetpsa.poc00.domain.esdependent.tcl.
     * EmissionStdDepTCL)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public EmissionStdDepTCL saveEmissonStdDepTCL(EmissionStdDepTCL emsDepTCL) {

        try {
            if (emsDepTCL.getEntityId() != null && emsDepTCL.getEntityId() != 0)
                return super.save(emsDepTCL);

            super.persist(emsDepTCL);
            entityManager.flush();

        } catch (Exception e) {
            LOGGER.error(" **** End: Exception occured while running saveEmissonStdDepTCL Method", e);

        }
        return super.load(emsDepTCL.getEntityId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCLRepository#deleteEmsDepTCL(long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public void deleteEmsDepTCL(long id) {
        super.delete(id);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCLRepository#getLatestEmissionStandardDepLists(java.lang.Long)
     */
    @Override
    public List<EmissionStdDepTCL> getLatestEmissionStandardDepLists(Long emsId) {
        List<EmissionStdDepTCL> resultList = new ArrayList<EmissionStdDepTCL>();
        Query idSubQuery = entityManager.createQuery(
                "select MAX(t1.entityId) from EmissionStdDepTCL t1 where t1.emissionStandard.entityId = " + emsId + "  group by t1.label) ");
        List<Long> idList = idSubQuery.getResultList();
        for (Long id : idList) {
            TypedQuery<EmissionStdDepTCL> query = entityManager.createQuery("select t from EmissionStdDepTCL t where t.entityId =" + id,
                    EmissionStdDepTCL.class);
            resultList.add(query.getSingleResult());
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCLRepository#getEmissionStandardDepLists(java.lang.Long)
     */
    @Override
    public List<EmissionStdDepTCL> getEmissionStandardDepLists(Long esEntityId) {
        TypedQuery<EmissionStdDepTCL> query = entityManager
                .createQuery("select t from EmissionStdDepTCL t where t.emissionStandard.entityId= " + esEntityId, EmissionStdDepTCL.class);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.emsdeptcl.EmsDepTCLFinder#getMaxVersionForLabel(java.lang.String)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Double getMaxVersionForLabel(String label) {

        Query query = entityManager.createNativeQuery("SELECT MAX(CAST(T.VERSION AS DECIMAL(10,1))) FROM COPQTECL T WHERE T.LABEL = ?");

        query.setParameter(1, label);
        return ((BigDecimal) query.getSingleResult()).doubleValue();
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public EmissionStdDepTCL loadEmsDepTcl(long emsDepTclId) {

        return super.load(emsDepTclId);
    }

}
