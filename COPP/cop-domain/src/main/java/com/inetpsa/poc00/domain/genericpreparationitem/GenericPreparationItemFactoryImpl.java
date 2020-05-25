/*
 * Creation : Oct 13, 2016
 */
package com.inetpsa.poc00.domain.genericpreparationitem;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class GenerricPreparationItemFactoryImpl.
 */
public class GenericPreparationItemFactoryImpl extends BaseFactory<GenericPreparationItem> implements GenericPreparationItemFactory {

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.genericpreparationitem.GenericPreparationItemFactory#createGenericPreparationItem()
     */
    @Override
    public GenericPreparationItem createGenericPreparationItem() {

        return new GenericPreparationItem();
    }

}
