/*
 * Creation : May 24, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedtcl;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface TvvValuedTCLRepository.
 */
public interface TvvValuedTCLRepository extends GenericRepository<TvvValuedTvvDepTCL, Long> {

	/**
	 * Save tvv valued tvv dep tcl.
	 * 
	 * @param object the object
	 * @return the tvv dep tcl
	 */
	TvvValuedTvvDepTCL saveTvvValuedTvvDepTCL(TvvValuedTvvDepTCL object);

	/**
	 * Persist tvv valued tvv dep tcl.
	 * 
	 * @param object the object
	 */
	void persistTvvValuedTvvDepTCL(TvvValuedTvvDepTCL object);

	/**
	 * Delete all.
	 * 
	 * @return the long
	 */
	long deleteAll();

	/**
	 * Count.
	 * 
	 * @return the long
	 */
	long count();

	/**
	 * Delete tvv valued tvv dep tcl.
	 * 
	 * @param id the id
	 */
	void deleteTvvValuedTvvDepTCL(long id);

	List<TvvValuedTvvDepTCL> getAllValuedTCL(long tvvId);
}
