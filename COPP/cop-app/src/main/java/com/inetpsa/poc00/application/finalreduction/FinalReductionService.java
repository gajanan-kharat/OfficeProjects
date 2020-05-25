/*
 * Creation : Jan 2, 2017
 */
package com.inetpsa.poc00.application.finalreduction;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatio;

/**
 * The Interface FinalReductionService.
 */
@Service
public interface FinalReductionService {

	/**
	 * Save final reduction.
	 *
	 * @param finalReduction the final reduction
	 * @return the final reduction ratio
	 */
	public FinalReductionRatio saveFinalReduction(FinalReductionRatio finalReduction);

	/**
	 * Delete final reduction.
	 *
	 * @param finalRedutionId the final redution id
	 * @return true, if successful
	 */
	public boolean deleteFinalReduction(Long finalRedutionId);

}
