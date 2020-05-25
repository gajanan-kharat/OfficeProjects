package com.inetpsa.poc00.domain.typeapprovalarea;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of TypeApprovalArea.
 */

public interface TypeApprovalAreaRepository extends GenericRepository<TypeApprovalArea, Long> {

    /**
     * Saves the GenomeLCDVTypeApprovalArea.
     * 
     * @param object the TypeApprovalArea to save
     * @return the TypeApprovalArea
     */
    TypeApprovalArea saveTypeApprovalArea(TypeApprovalArea object);

    /**
     * Persists the TypeApprovalArea.
     * 
     * @param object the TypeApprovalArea to persist
     */
    void persistTypeApprovalArea(TypeApprovalArea object);

    /**
     * Delete all categories
     * 
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     * 
     * @return TypeApprovalArea count
     */
    long count();

    int deleteTypeApprovalArea(long id);

    List<TypeApprovalArea> getTypeApprovalAreaByLabel(String typeappAreaLabel);

    public TypeApprovalArea loadTypeApproval(long typeId);
}
