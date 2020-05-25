/*
 * Creation : Oct 13, 2016
 */
package com.inetpsa.poc00.domain.preparationfilestructure;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class PreparationFileStructureFactoryImpl.
 */
public class PreparationFileStructureFactoryImpl extends BaseFactory<PreparationFileStructure> implements PreparationFileStructureFactory {

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.preparationfilestructure.PreparationFileStructureFactory#createPreparationFileStructure()
     */
    @Override
    public PreparationFileStructure createPreparationFileStructure() {
        return new PreparationFileStructure();
    }

}
