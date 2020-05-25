/*
 * Creation : May 27, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedesdeptcl;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface TvvValuedEsDepTCLRepository.
 */
public interface TvvValuedEsDepTCLRepository extends GenericRepository<TvvValuedEsDepTCL, Long> {

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

	List<TvvValuedEsDepTCL> getAllValuedEsDepTCL(long tvvId, Long entityId);
}
