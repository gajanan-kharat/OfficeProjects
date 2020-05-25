/*
 * Creation : Oct 13, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.genericpreparationchecklist.GenericPreparationChecklist;
import com.inetpsa.poc00.domain.genericpreparationchecklist.GenericPreparationChecklistRepository;

/**
 * The Class GenericPreparationChecklistJpaRepository.
 */
public class GenericPreparationChecklistJpaRepository extends BaseJpaRepository<GenericPreparationChecklist, Long>
        implements GenericPreparationChecklistRepository {

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.genericpreparationchecklist.GenericPreparationChecklistRepository#saveGpc(com.inetpsa.poc00.domain.genericpreparationchecklist.GenericPreparationChecklist)
     */
    @Override
    public GenericPreparationChecklist saveGpc(GenericPreparationChecklist gpcObj) {
        return super.save(gpcObj);
    }

}
