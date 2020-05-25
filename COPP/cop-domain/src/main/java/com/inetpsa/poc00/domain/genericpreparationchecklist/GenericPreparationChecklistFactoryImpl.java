/*
 * Creation : Oct 13, 2016
 */
package com.inetpsa.poc00.domain.genericpreparationchecklist;

import org.seedstack.business.domain.BaseFactory;

public class GenericPreparationChecklistFactoryImpl extends BaseFactory<GenericPreparationChecklist> implements GenericPreparationChecklistFactory {

    @Override
    public GenericPreparationChecklist createGenericPreparationChecklist() {

        return new GenericPreparationChecklist();
    }

}
