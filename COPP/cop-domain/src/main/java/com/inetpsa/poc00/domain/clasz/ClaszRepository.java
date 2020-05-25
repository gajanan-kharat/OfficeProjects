package com.inetpsa.poc00.domain.clasz;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of Clasz.
 */

public interface ClaszRepository extends GenericRepository<Clasz, Long> {

    /**
     * Save clasz.
     * 
     * @param classObject the class object
     * @return the clasz
     */
    Clasz saveClasz(Clasz classObject);

    /**
     * Persist clasz.
     * 
     * @param classObject the class object
     */
    void persistClasz(Clasz classObject);

    /**
     * Count.
     * 
     * @return the long
     */
    long count();

    /**
     * Delete clasz.
     * 
     * @param id the id
     * @return the int
     */
    int deleteClasz(Long id);

    /**
     * Gets the all clasz for copied fc list.
     *
     * @param entityId the entity id
     * @return the all clasz for copied fc list
     */
    List<Clasz> getAllClaszForCopiedFCList(Long entityId);

    /**
     * Gets the all clasz for copied pg list.
     *
     * @param entityId the entity id
     * @return the all clasz for copied pg list
     */
    List<Clasz> getAllClaszForCopiedPGList(Long entityId);

    /**
     * Gets the clasz by label.
     *
     * @param label the label
     * @return the clasz by label
     */
    public List<Clasz> getClaszByLabel(String label);

    public Clasz loadClasz(long claszId);

}
