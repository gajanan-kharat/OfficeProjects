/*
 * Creation : Jun 13, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedestdl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.tvvvaluedesdeptdl.TvvValuedEsDepTDL;

/**
 * The Interface TvvValuedEsDepTDLFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface TvvValuedEsDepTDLFinder {

	/**
	 * Gets the all valued tdl.
	 * 
	 * @param tvvID the tvv id
	 * @param emsID the ems id
	 * @return the all valued tdl
	 */
	List<TvvValuedEsDepTDLRepresentation> getAllValuedTDL(long tvvID, long emsID);

	List<TvvValuedEsDepTDL> getAllValuedEsDepTDL(long tvvId, long emsId);

}
