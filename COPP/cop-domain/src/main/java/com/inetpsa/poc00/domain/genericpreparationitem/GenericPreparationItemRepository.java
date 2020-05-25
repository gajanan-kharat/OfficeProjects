/*
 * Creation : Oct 13, 2016
 */
package com.inetpsa.poc00.domain.genericpreparationitem;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface GenericPreparationItemRepository.
 */
public interface GenericPreparationItemRepository extends GenericRepository<GenericPreparationItem, Long> {

    /**
     * Save gpi.
     *
     * @param gpiObj the gpi obj
     * @return the generic preparation item
     */
    public GenericPreparationItem saveGpi(GenericPreparationItem gpiObj);
}
