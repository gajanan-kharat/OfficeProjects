/*
 * Creation : Oct 13, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.preparationfilestructure.PreparationFileStructure;
import com.inetpsa.poc00.domain.preparationfilestructure.PreparationFileStructureRepository;

/**
 * The Class PreparationFileStructureJpaRepository.
 */
public class PreparationFileStructureJpaRepository extends BaseJpaRepository<PreparationFileStructure, Long>
        implements PreparationFileStructureRepository {

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.preparationfilestructure.PreparationFileStructureRepository#savePfs(com.inetpsa.poc00.domain.preparationfilestructure.PreparationFileStructure)
     */
    @Override
    public PreparationFileStructure savePfs(PreparationFileStructure pfs) {

        return super.save(pfs);
    }

}
