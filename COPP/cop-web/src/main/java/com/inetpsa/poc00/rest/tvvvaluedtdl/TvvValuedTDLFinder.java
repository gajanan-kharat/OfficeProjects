/*
 * Creation : Jun 10, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedtdl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTvvDepTDL;

/**
 * The Interface TvvValuedTDLFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface TvvValuedTDLFinder {

	/**
	 * Gets the all tvv valued tdl.
	 * 
	 * @param tvvId the tvv id
	 * @return the all tvv valued tdl
	 */
	List<TvvValuedTvvDepTDLRepresentation> getAllTvvValuedTDL(long tvvId);

	/**
	 * Gets the all valued tdl.
	 * 
	 * @param tvvId the tvv id
	 * @return the all valued tdl
	 */
	List<TvvValuedTvvDepTDL> getAllValuedTDL(long tvvId);
}
