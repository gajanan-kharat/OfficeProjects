package com.inetpsa.poc00.rest.tvvvaluedtcl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTvvDepTCL;

/**
 * The Interface TvvValuedTCLFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface TvvValuedTCLFinder {

	/**
	 * Gets the all tvv valued tcl.
	 * 
	 * @param tvvId the tvv id
	 * @return the all tvv valued tcl
	 */
	List<TvvValuedTvvDepTCLRepresentation> getAllTvvValuedTCL(long tvvId);

	/**
	 * Gets the all valued tcl.
	 * 
	 * @param tvvId the tvv id
	 * @return the all valued tcl
	 */
	List<TvvValuedTvvDepTCL> getAllValuedTCL(long tvvId);

}
