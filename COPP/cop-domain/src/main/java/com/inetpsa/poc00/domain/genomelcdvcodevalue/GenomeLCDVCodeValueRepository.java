package com.inetpsa.poc00.domain.genomelcdvcodevalue;

import org.seedstack.business.domain.GenericRepository;

public interface GenomeLCDVCodeValueRepository extends GenericRepository<GenomeLCDVCodeValue, Long> {

	/**
	 * Saves the GenomeLCDVCodeValue.
	 * 
	 * @param object the GenomeLCDVCodeValue to save
	 * @return the GenomeLCDVCodeValue
	 */
	GenomeLCDVCodeValue saveGenomeLCDVCodeValue(GenomeLCDVCodeValue object);

	/**
	 * Persists the GenomeLCDVCodeValue.
	 * 
	 * @param object the GenomeLCDVCodeValue to persist
	 */
	void persistGenomeLCDVCodeValue(GenomeLCDVCodeValue object);

	/**
	 * Delete all categories
	 * 
	 * @return number of categories deleted
	 */
	long deleteAll();

	/**
	 * Count the number of categories in the repository.
	 * 
	 * @return GenomeLCDVCodeValue count
	 */
	long count();

}
