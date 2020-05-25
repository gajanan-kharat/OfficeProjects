/*
 * Creation : May 27, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedesdeptdl;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface TvvValuedEsDepTDLRepository.
 */
public interface TvvValuedEsDepTDLRepository extends GenericRepository<TvvValuedEsDepTDL, Long> {

	/**
	 * 
	 * Save tvv valued es dep tdl.
	 * 
	 * @param tvvValuedEsDepTDL the tvv valued es dep tdl
	 * @return the tvv valued tvv dep tdl
	 */
	TvvValuedEsDepTDL saveTvvValuedEsDepTDL(TvvValuedEsDepTDL tvvValuedEsDepTDL);

	/**
	 * Persist tvv valued es dep tdl.
	 * 
	 * @param tvvValuedEsDepTDL the tvv valued es dep tdl
	 */
	void persistTvvValuedEsDepTDL(TvvValuedEsDepTDL tvvValuedEsDepTDL);

	/**
	 * 
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

	List<TvvValuedEsDepTDL> getAllValuedEsDepTDL(long tvvId, Long entityId);

}
