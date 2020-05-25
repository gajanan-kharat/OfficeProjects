/*
 * Creation : Feb 2, 2017
 */
package com.inetpsa.poc00.domain.expertiseresult;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface ExpertiseResultRepository.
 */
public interface ExpertiseResultRepository extends GenericRepository<ExpertiseResult, Long> {
	ExpertiseResult saveExpertiseResult(ExpertiseResult ExpertiseResult);
}
