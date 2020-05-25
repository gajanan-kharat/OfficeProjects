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
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechRepository;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology;

// TODO: Auto-generated Javadoc
/**
 * The Class VehicleTechnologyJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "VehicleTechnology")
public class VehicleTechnologyJpaRepository extends BaseJpaRepository<VehicleTechnology, Long> implements VehicleTechRepository {

    /** The logger. */
    @Logging
    private final Logger logger = LoggerFactory.getLogger(VehicleTechnologyJpaRepository.class);

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.vehicletechnology.VehicleTechRepository#saveVehicleTechnology(com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology)
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.vehicletechnology.VehicleTechRepository#saveVehicleTechnology(com.inetpsa.poc00.domain.vehicletechnology.
     * VehicleTechnology)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public VehicleTechnology saveVehicleTechnology(VehicleTechnology vt) {

        if (vt.getEntityId() == null || vt.getEntityId() == 0)
            return super.save(vt);

        return entityManager.merge(vt);

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.vehicletechnology.VehicleTechRepository#deleteVehicleTechnology(long)
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.vehicletechnology.VehicleTechRepository#deleteVehicleTechnology(long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public int deleteVehicleTechnology(long id) {

        try {
            return entityManager.createQuery("DELETE FROM VehicleTechnology c where c.entityId = " + id).executeUpdate();
        } catch (Exception e) {

            logger.error(" Error occured while deleting data ", e);
            return 0;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.vehicletechnology.VehicleTechRepository#deleteAll()
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.vehicletechnology.VehicleTechRepository#deleteAll()
     */
    @Override
    public long deleteAll() {
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.vehicletechnology.VehicleTechRepository#count()
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.vehicletechnology.VehicleTechRepository#count()
     */
    @Override
    public long count() {
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.vehicletechnology.VehicleTechRepository#getAllVehicleTechnology()
     */
    @Override
    public List<VehicleTechnology> getAllVehicleTechnology() {

        TypedQuery<VehicleTechnology> query = entityManager.createQuery("SELECT vt FROM VehicleTechnology vt ", VehicleTechnology.class);

        return query.getResultList();

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.vehicletechnology.VehicleTechRepository#getAllVTForCopiedFCList(java.lang.Long)
     */
    @Override
    public List<VehicleTechnology> getAllVTForCopiedFCList(Long entityId) {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM COPQTAVT C JOIN COPQTMFV L ON (L.VTECHNOLOGYID = C.ID) JOIN COPQTFLS FCL ON (FCL.ID =  L.FCLISTID) WHERE FCL.ID = ?1",
                VehicleTechnology.class);

        query.setParameter(1, entityId);

        List<VehicleTechnology> result = query.getResultList();

        return result;

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.vehicletechnology.VehicleTechRepository#getAllVTForCopiedPGList(java.lang.Long)
     */
    @Override
    public List<VehicleTechnology> getAllVTForCopiedPGList(Long entityId) {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM COPQTAVT C JOIN COPQTMPV L ON (L.VTECHNOLOGY_ID = C.ID) JOIN COPQTPLL PGL ON (PGL.ID = L.PGLIST_ID) WHERE PGL.ID =?1",
                VehicleTechnology.class);

        query.setParameter(1, entityId);

        List<VehicleTechnology> result = query.getResultList();

        return result;

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.vehicletechnology.VehicleTechRepository#getVehicleTechnologyByLabel(java.lang.String)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<VehicleTechnology> getVehicleTechnologyByLabel(String vehicleTechnologyLabel) {

        TypedQuery<VehicleTechnology> query = entityManager.createQuery(
                "SELECT vehicleTech FROM VehicleTechnology vehicleTech where vehicleTech.label='" + vehicleTechnologyLabel + "'",
                VehicleTechnology.class);

        return query.getResultList();

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.vehicletechnology.VehicleTechRepository#loadVehicleTech(long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public VehicleTechnology loadVehicleTech(long vehicleTechId) {

        return super.load(vehicleTechId);
    }
}
