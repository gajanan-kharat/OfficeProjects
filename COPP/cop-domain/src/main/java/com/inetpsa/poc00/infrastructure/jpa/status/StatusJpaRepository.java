package com.inetpsa.poc00.infrastructure.jpa.status;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.status.StatusRepository;

/**
 * The Class StatusJpaRepository.
 */
public class StatusJpaRepository extends BaseJpaRepository<Status, Long> implements StatusRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusJpaRepository.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.status.StatusRepository#saveStatus(com.inetpsa.poc00.domain.status.Status)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Status saveStatus(Status status) {

        if (status.getEntityId() == null || status.getEntityId() == 0) {
            return super.save(status);
        }
        entityManager.merge(status);
        entityManager.flush();

        return this.load(status.getEntityId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.status.StatusRepository#persistStatus(com.inetpsa.poc00.domain.status.Status)
     */
    @Override
    public void persistStatus(Status object) {
        super.persist(object);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.status.StatusRepository#deleteAll()
     */
    @Override
    public long deleteAll() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.status.StatusRepository#count()
     */
    @Override
    public long count() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.status.StatusRepository#deleteStatus(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public long deleteStatus(Long statusid) {
        super.delete(statusid);
        return statusid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.status.StatusRepository#getAllStatus()
     */
    @Override
    public List<Status> getAllStatus() {

        TypedQuery<Status> query = entityManager.createQuery("SELECT S FROM Status S ", Status.class);

        List<Status> statusData = query.getResultList();

        return statusData;
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Status getStatus(String statusLabel) {
        Status status = null;
        try {
            TypedQuery<Status> query = entityManager.createQuery("select s from Status s where Upper(s.label) = '" + statusLabel + "'", Status.class);
            status = query.getSingleResult();
        } catch (Exception e) {

        }
        return status;
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Status getStatusForEmissionStandard(String statusLabel) {
        Status status = null;
        try {
            TypedQuery<Status> query = entityManager.createQuery("select s from Status s where Upper(s.label) = '" + statusLabel + "'", Status.class);
            status = query.getSingleResult();
        } catch (Exception e) {
            LOGGER.error("Error in retriving status", e);
        }
        return status;
    }

}
