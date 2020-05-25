package com.inetpsa.poc00.domain.genomelcdvcode;

import org.seedstack.business.domain.GenericRepository;



public interface GenomeLCDVCodeRepository extends GenericRepository<GenomeLCDVCode, Long> {
	/**
     * Saves the GenomeLCDVCode.
     *
     * @param object the GenomeLCDVCode to save
     * @return the GenomeLCDVCode
     */
    GenomeLCDVCode saveGenomeLCDVCode(GenomeLCDVCode object);

    /**
     * Persists the GenomeLCDVCode.
     *
     * @param object the GenomeLCDVCode to persist
     */
    void persistGenomeLCDVDictionary(GenomeLCDVCode object);

    /**
     * Delete all categories
     *
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     *
     * @return dictionary count
     */
    long count();
	
}
