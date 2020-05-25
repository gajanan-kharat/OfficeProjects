package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.vehicle.Vehicle;
import com.inetpsa.poc00.rest.vehicle.VehicleFinder;

/**
 * The Class JpaVehicleFinder.
 */
public class JpaVehicleFinder implements VehicleFinder {

    /** The entity manager. */
    @Inject
    EntityManager entityManager;

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.vehicle.VehicleFinder#getAllVehicleById(java.lang.Long)
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.vehicle.VehicleFinder#getAllVehicleById(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Vehicle getAllVehicleById(Long vehicleId) {

        TypedQuery<Vehicle> query = entityManager.createQuery("SELECT vehicle FROM Vehicle vehicle WHERE vehicle.entityId = :vehicleId",
                Vehicle.class);
        query.setParameter("vehicleId", vehicleId);

        List<Vehicle> vehicleList = query.getResultList();

        if (vehicleList != null && !vehicleList.isEmpty()) {
            return vehicleList.get(0);
        }
        return null;

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.vehicle.VehicleFinder#getVehicleId(java.lang.String)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Vehicle getVehicleId(String vehicleCondition) {
        TypedQuery<Vehicle> query = entityManager.createQuery("From Vehicle v where " + vehicleCondition, Vehicle.class);

        List<Vehicle> vehicleObj = query.getResultList();
        if (!vehicleObj.isEmpty()) {
            return vehicleObj.get(0);
        }
        return null;
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Vehicle getVehicleByVehicleFileId(Long vehicleFileId) {
        TypedQuery<Vehicle> query = entityManager.createQuery(
                "FROM Vehicle v where v.entityId=(select vf.vehicle.entityId FROM VehicleFile vf where vf.entityId =" + vehicleFileId + ")",
                Vehicle.class);
        List<Vehicle> vehicleList = query.getResultList();
        if (!vehicleList.isEmpty())
            return vehicleList.get(0);

        return null;
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<String> getModelYear() {
        TypedQuery<String> query = entityManager.createQuery("select DISTINCT v.modelYear FROM Vehicle v", String.class);

        return query.getResultList();
    }

}
