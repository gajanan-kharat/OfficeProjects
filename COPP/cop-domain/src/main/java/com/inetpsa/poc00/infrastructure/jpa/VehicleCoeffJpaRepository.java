package com.inetpsa.poc00.infrastructure.jpa;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.DataSet;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoeffRepository;
import com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoefficents;

/**
 * The Class VehicleCoeffJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "VehicleCoefficents")
public class VehicleCoeffJpaRepository extends BaseJpaRepository<VehicleCoefficents, Long> implements VehicleCoeffRepository {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoeffRepository#saveVehicleCoefficents(com.inetpsa.poc00.domain.fxvehiclecoefficients.
     * VehicleCoefficents)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public VehicleCoefficents saveVehicleCoefficents(VehicleCoefficents vCObject) {

        return super.save(vCObject);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoeffRepository#deleteVehicleCoefficents(java.lang.Long)
     */
    @Override
    public void deleteVehicleCoefficents(Long id) {
        super.delete(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoeffRepository#persistVehicleCoefficents(com.inetpsa.poc00.domain.fxvehiclecoefficients.
     * VehicleCoefficents)
     */
    @Override
    public void persistVehicleCoefficents(VehicleCoefficents object) {
        entityManager.merge(object).getEntityId();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoeffRepository#deleteAll()
     */
    @Override
    public long deleteAll() {

        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.fxvehiclecoefficients.VehicleCoeffRepository#count()
     */
    @Override
    public long count() {

        return 0;
    }

    @Override
    public VehicleCoefficents loadVehicleCoeff(long vehicleCoeffId) {

        return super.load(vehicleCoeffId);
    }

}
