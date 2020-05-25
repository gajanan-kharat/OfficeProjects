/*
 * Creation : Oct 13, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.genericpreparationitem.GenericPreparationItem;
import com.inetpsa.poc00.domain.genericpreparationitem.GenericPreparationItemRepository;

/**
 * The Class GenericPreparationItemJpaRepository.
 */
public class GenericPreparationItemJpaRepository extends BaseJpaRepository<GenericPreparationItem, Long> implements GenericPreparationItemRepository {

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.genericpreparationitem.GenericPreparationItemRepository#saveGpi(com.inetpsa.poc00.domain.genericpreparationitem.GenericPreparationItem)
     */
    @Override
    public GenericPreparationItem saveGpi(GenericPreparationItem gpiObj) {
        return super.save(gpiObj);
    }

}
