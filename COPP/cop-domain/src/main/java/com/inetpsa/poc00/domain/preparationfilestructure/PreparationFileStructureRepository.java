/*
 * Creation : Oct 13, 2016
 */
package com.inetpsa.poc00.domain.preparationfilestructure;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface PreparationFileStructureRepository.
 */
public interface PreparationFileStructureRepository extends GenericRepository<PreparationFileStructure, Long> {

    /**
     * Save pfs.
     *
     * @param pfs the pfs
     * @return the preparation file structure
     */
    public PreparationFileStructure savePfs(PreparationFileStructure pfs);

}
