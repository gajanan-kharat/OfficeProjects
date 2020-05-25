/*
 * Creation : Oct 13, 2016
 */
package com.inetpsa.poc00.domain.genericpreparationchecklist;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating GenericPreparationChecklist objects.
 */
public interface GenericPreparationChecklistFactory extends GenericFactory<GenericPreparationChecklist> {

    /**
     * Creates a new GenericPreparationChecklist object.
     *
     * @return the generic preparation checklist
     */
    GenericPreparationChecklist createGenericPreparationChecklist();
}
