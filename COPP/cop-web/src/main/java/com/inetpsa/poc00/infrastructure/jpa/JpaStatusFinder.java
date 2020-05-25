package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.rest.status.StatusFinder;
import com.inetpsa.poc00.rest.status.StatusRepresentation;


/**
 * The Class JpaStatusFinder.
 */
public class JpaStatusFinder implements StatusFinder {

    /** The entity manager. */
    @Inject
    private EntityManager entityManager;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaStatusFinder.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.status.StatusFinder#getAllStatus()
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<StatusRepresentation> getAllStatus() {
        TypedQuery<StatusRepresentation> query = entityManager.createQuery(
                "select new " + StatusRepresentation.class.getName() + "(status.entityId,status.guiLabel,status.label)" + " from Status status ",
                StatusRepresentation.class);
        return query.getResultList();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.status.StatusFinder#getStatusForEmissionStandard(java.lang.String)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Status getStatusForEmissionStandard(String statusLabel) {
        Status status = null;
        try {
            TypedQuery<Status> query = entityManager.createQuery("select s from Status s where Upper(s.label) = '" + statusLabel + "'", Status.class);
            status = query.getSingleResult();
        } catch (Exception e) {
            LOGGER.error("Error in getting status", e);
        }
        return status;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.status.StatusFinder#getStatusForLabel(java.lang.String)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public StatusRepresentation getStatusForLabel(String statusLabel) {
        StatusRepresentation status = null;
        try {
            TypedQuery<StatusRepresentation> query = entityManager.createQuery("select new " + StatusRepresentation.class.getName()
                    + "(status.entityId,status.guiLabel,status.label)" + " from Status status where Upper(status.label) = '" + statusLabel + "'",
                    StatusRepresentation.class);
            status = query.getSingleResult();
        } catch (Exception e) {
            LOGGER.error("Error in Retrieving status", e);
        }
        return status;
    }

    /**
     * Finds Status by Label
     * 
     * @param statusLabel status Label to serach
     * @return Status entity
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Status findStatusByLabel(String statusLabel) {
        Status status = null;
        try {
            Query query = entityManager.createQuery(" from Status status where Upper(status.label)  = ?1");
            query.setParameter(1, statusLabel.toUpperCase());
            List statuses = query.getResultList();
            if (statuses != null && !statuses.isEmpty()) {
                status = (Status) statuses.get(0);
            }
        } catch (Exception e) {
            LOGGER.error("Error in fetching status", e);
        }
        return status;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.status.StatusFinder#getAllStatusNaturesForTvv()
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<Status> getAllStatusNaturesForTvv() {
        TypedQuery<Status> query = entityManager.createQuery("select status from Status status ", Status.class);
        return query.getResultList();
    }

}
