/*
 * Creation : Sep 21, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa.vehiclefile;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.DomainConstants;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;

/**
 * The Class VehicleFileJpaRepository.
 */
public class VehicleFileJpaRepository extends BaseJpaRepository<VehicleFile, Long> implements VehicleFileRepository {

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository#saveVehicle(com.inetpsa.poc00.domain.vehiclefile.VehicleFile)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public VehicleFile saveVehicle(VehicleFile obj) {
        return super.save(obj);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository#loadVehicle(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public VehicleFile loadVehicle(Long id) {
        return super.load(id);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository#saveVehicleReception(com.inetpsa.poc00.domain.vehiclefile.VehicleFile)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public VehicleFile saveVehicleReception(VehicleFile obj) {
        if (obj.getEntityId() == null) {
            return super.save(obj);
        }

        return entityManager.merge(obj);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository#getVehicleFileCount(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Long getVehicleFileCount(Long basketId) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(vf) FROM VehicleFile vf WHERE vf.basket.entityId =" + basketId
                + " and vf.vehicleFileStatus.label != '" + DomainConstants.VEHICLEFILESTATUS_ARCHIVED + "'", Long.class);

        return query.getSingleResult();
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<Long> getAllVehicleFile() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT vf.entityId FROM VehicleFile vf ", Long.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public VehicleFile getVehicleFileByLabel(String vehicleCondition) {
        TypedQuery<VehicleFile> query = entityManager.createQuery("From VehicleFile vf where " + vehicleCondition
                + " and vf.vehicleFileStatus.label != '" + DomainConstants.VEHICLEFILESTATUS_ARCHIVED + "'", VehicleFile.class);

        List<VehicleFile> vehicleFileObj = query.getResultList();
        if (!vehicleFileObj.isEmpty()) {
            return vehicleFileObj.get(0);
        }
        return null;
    }

}
