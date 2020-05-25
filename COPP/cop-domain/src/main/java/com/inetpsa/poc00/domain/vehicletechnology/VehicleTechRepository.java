package com.inetpsa.poc00.domain.vehicletechnology;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

// TODO: Auto-generated Javadoc
/**
 * The Interface VehicleTechRepository.
 */

public interface VehicleTechRepository extends GenericRepository<VehicleTechnology, Long> {

    /**
     * Save vehicle technology.
     *
     * @param object the object
     * @return the vehicle technology
     */
    VehicleTechnology saveVehicleTechnology(VehicleTechnology object);

    /**
     * Delete all.
     *
     * @return the long
     */
    long deleteAll();

    /**
     * Count.
     *
     * @return the long
     */
    long count();

    /**
     * Delete vehicle technology.
     *
     * @param id the id
     * @return the int
     */
    int deleteVehicleTechnology(long id);

    /**
     * Gets the all vehicle technology.
     *
     * @return the all vehicle technology
     */
    List<VehicleTechnology> getAllVehicleTechnology();

    /**
     * Gets the all vt for copied fc list.
     *
     * @param entityId the entity id
     * @return the all vt for copied fc list
     */
    List<VehicleTechnology> getAllVTForCopiedFCList(Long entityId);

    /**
     * Gets the all vt for copied pg list.
     *
     * @param entityId the entity id
     * @return the all vt for copied pg list
     */
    List<VehicleTechnology> getAllVTForCopiedPGList(Long entityId);

    /**
     * Gets the vehicle technology by label.
     *
     * @param vehicleTechnologyLabel the vehicle technology label
     * @return the vehicle technology by label
     */
    List<VehicleTechnology> getVehicleTechnologyByLabel(String vehicleTechnologyLabel);

    /**
     * Load vehicle tech.
     *
     * @param vehicleTechId the vehicle tech id
     * @return the vehicle technology
     */
    public VehicleTechnology loadVehicleTech(long vehicleTechId);
}
