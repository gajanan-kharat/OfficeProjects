/*
 * Creation : Oct 13, 2016
 */
package com.inetpsa.poc00.domain.genericpreparationitem;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating GenericPreparationItem objects.
 */
public interface GenericPreparationItemFactory extends GenericFactory<GenericPreparationItem> {

    /**
     * Creates a new GenericPreparationItem object.
     *
     * @return the generic preparation item
     */
    GenericPreparationItem createGenericPreparationItem();
}
