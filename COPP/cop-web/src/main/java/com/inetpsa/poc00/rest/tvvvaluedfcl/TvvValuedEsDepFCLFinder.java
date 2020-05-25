/*
 * Creation : Jun 15, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedfcl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.tvvvaluedfcl.TvvValuedEsDepFCL;
import com.inetpsa.poc00.rest.sampling.SamplingRuleRepresentation;

/**
 * The Interface TvvValuedEsDepFCLFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface TvvValuedEsDepFCLFinder {

	/**
	 * Gets the all valued es dep fcl.
	 * 
	 * @param tvvId the tvv id
	 * @param emsId the ems id
	 * @return the all valued es dep fcl
	 */
	List<TvvValuedEsDepFCL> getAllValuedEsDepFCL(long tvvId, long emsId);

	/**
	 * Gets the all valued es dep fcl for tvv.
	 *
	 * @param tvvId the tvv id
	 * @return the all valued es dep fcl for tvv
	 */
	List<TvvValuedEsDepFCLRepresentation> getAllValuedEsDepFCLForTVV(long tvvId);

	List<SamplingRuleRepresentation> getSamplingRuleForTG(String samplingRuleLabel);

	List<TvvValuedEsDepFCL> getAllValuedEsDepFCL(long tvvId);

}
