/*
 * Creation : Nov 29, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.domain.tvvvaluedpglimit;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface TvvValuedPollGasLimitRepository.
 */
public interface TvvValuedPollGasLimitRepository extends GenericRepository<TvvValuedPollutantGasLimit, Long> {

	/**
	 * Gets the all limits for ems dep list.
	 *
	 * @param entityId the entity id
	 * @return the all limits for ems dep list
	 */
	List<TvvValuedPollutantGasLimit> getAllLimitsForEmsDepList(Long entityId);

}
