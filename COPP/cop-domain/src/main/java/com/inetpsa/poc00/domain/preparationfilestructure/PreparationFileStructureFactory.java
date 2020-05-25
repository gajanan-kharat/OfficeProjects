/*
 * Creation : Oct 13, 2016
 */
package com.inetpsa.poc00.domain.preparationfilestructure;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating PreparationFileStructure objects.
 */
public interface PreparationFileStructureFactory extends GenericFactory<PreparationFileStructure> {

    /**
     * Creates a new PreparationFileStructure object.
     *
     * @return the preparation file structure
     */
    PreparationFileStructure createPreparationFileStructure();

}
