package com.inetpsa.poc00.domain.gearbox;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of GearBox.
 */

public interface GearBoxRepository extends GenericRepository<GearBox, Long> {

    /**
     * Saves the GearBox.
     * 
     * @param object the GearBox to save
     * @return the GearBox
     */
    GearBox saveGearBox(GearBox object);

    /**
     * Persists the GearBox.
     *
     * @param object the GearBox to persist
     * @return the gear box
     */
    GearBox persistGearBox(GearBox object);

    /**
     * Delete all categories.
     *
     * @param id the id
     * @return number of categories deleted
     */
    int deleteAll(Long id);

    /**
     * Count the number of categories in the repository.
     * 
     * @return GearBox count
     */
    long count();

    /**
     * Gets the all gear box.
     *
     * @return the all gear box
     */
    public List<GearBox> getAllGearBox();

    public GearBox loadGearBox(long gearBoxId);

}
