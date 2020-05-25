package com.inetpsa.poc00.rest.tvvvaluedestcl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCL;

/**
 * The Interface TvvValuedEsDepTCLFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface TvvValuedEsDepTCLFinder {

	/**
	 * Gets the all valued tdl.
	 * 
	 * @param tvvID the tvv id
	 * @param emsID the ems id
	 * @return the all valued tdl
	 */
	List<TvvValuedEsDepTCLRepresentation> getAllValuedTDL(long tvvID, long emsID);

	/**
	 * Gets the all valued es dep tcl.
	 * 
	 * @param tvvId the tvv id
	 * @param emsId the ems id
	 * @return the all valued es dep tcl
	 */
	List<TvvValuedEsDepTCL> getAllValuedEsDepTCL(long tvvId, long emsId);

}
