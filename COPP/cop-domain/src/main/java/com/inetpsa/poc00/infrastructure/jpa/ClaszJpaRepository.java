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
import com.inetpsa.poc00.domain.clasz.Clasz;
import com.inetpsa.poc00.domain.clasz.ClaszRepository;

/**
 * The Class ClaszJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "Clasz")
public class ClaszJpaRepository extends BaseJpaRepository<Clasz, Long> implements ClaszRepository {

    /** The logger. */
    @Logging
    private final Logger logger = LoggerFactory.getLogger(ClaszJpaRepository.class);

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.clasz.ClaszRepository#saveClasz(com.inetpsa.poc00.domain.clasz.Clasz)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Clasz saveClasz(Clasz classObject) {

        if (classObject.getEntityId() == null || classObject.getEntityId() == 0) {
            return super.save(classObject);
        }
        return entityManager.merge(classObject);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.clasz.ClaszRepository#persistClasz(com.inetpsa.poc00.domain.clasz.Clasz)
     */
    @Override
    public void persistClasz(Clasz classObject) {
        entityManager.merge(classObject).getEntityId();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.clasz.ClaszRepository#deleteClasz(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public int deleteClasz(Long id) {
        try {

            return entityManager.createQuery("DELETE FROM Clasz c where c.entityId = " + id).executeUpdate();

        } catch (Exception e) {

            logger.error("Error occured while deleting data ", e);
            return 0;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.clasz.ClaszRepository#count()
     */
    @Override
    public long count() {

        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.clasz.ClaszRepository#getAllClaszForCopiedFCList(java.lang.Long)
     */
    @Override
    public List<Clasz> getAllClaszForCopiedFCList(Long entityId) {
        logger.info("Getting Clasz objects for Copied FC List");

        Query query = entityManager.createNativeQuery(
                "SELECT * FROM COPQTACS C JOIN COPQTMFS L ON (L.CLASSID = C.ID) JOIN COPQTFLS FCL ON (FCL.ID =  L.FCLISTID) WHERE FCL.ID = ?1",
                Clasz.class);

        query.setParameter(1, entityId);

        return query.getResultList();

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.clasz.ClaszRepository#getAllClaszForCopiedPGList(java.lang.Long)
     */
    @Override
    public List<Clasz> getAllClaszForCopiedPGList(Long entityId) {
        logger.info("Getting Clasz objects for Copied PG List");

        Query query = entityManager.createNativeQuery(
                "SELECT * FROM COPQTACS C JOIN COPQTMPS L ON (L.CLASZ_ID = C.ID) JOIN COPQTPLL PGL ON (PGL.ID = L.PGLIST_ID) WHERE PGL.ID =?1",
                Clasz.class);

        query.setParameter(1, entityId);

        List<Clasz> result = query.getResultList();

        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.clasz.ClaszRepository#getClaszByLabel(java.lang.String)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<Clasz> getClaszByLabel(String label) {
        logger.info("Query running to check if label present");

        TypedQuery<Clasz> query = entityManager.createQuery("FROM Clasz clasz WHERE clasz.label='" + label + "'", Clasz.class);

        return query.getResultList();
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Clasz loadClasz(long claszId) {

        return super.load(claszId);
    }

}
