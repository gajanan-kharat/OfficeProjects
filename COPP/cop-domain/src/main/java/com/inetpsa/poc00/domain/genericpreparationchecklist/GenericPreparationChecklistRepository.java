/*
 * Creation : Oct 13, 2016
 */
package com.inetpsa.poc00.domain.genericpreparationchecklist;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface GenericPreparationChecklistRepository.
 */
public interface GenericPreparationChecklistRepository extends GenericRepository<GenericPreparationChecklist, Long> {

    /**
     * Save gpc.
     *
     * @param gpcObj the gpc obj
     * @return the generic preparation checklist
     */
    public GenericPreparationChecklist saveGpc(GenericPreparationChecklist gpcObj);
}
