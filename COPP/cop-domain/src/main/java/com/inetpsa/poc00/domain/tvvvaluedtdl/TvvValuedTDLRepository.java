/*
 * Creation : May 24, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedtdl;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

public interface TvvValuedTDLRepository extends GenericRepository<TvvValuedTvvDepTDL, Long> {
	/**
	 * Save tvv dep tdl.
	 * 
	 * @param object the object
	 * @return the tvv dep tdl
	 */
	TvvValuedTvvDepTDL saveTvvValuedTvvDepTDL(TvvValuedTvvDepTDL tvvValuedTvvDepTDL);

	/**
	 * Persist tvv dep tdl.
	 * 
	 * @param object the object
	 */
	void persistTvvValuedTvvDepTDL(TvvValuedTvvDepTDL tvvValuedTvvDepTDL);

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
	 * Delete tvv dep tdl.
	 * 
	 * @param entityId the entity id
	 */
	void deleteTvvValuedTvvDepTDL(long entityId);

	List<TvvValuedTvvDepTDL> getAllValuedTDL(long tvvId);

}
