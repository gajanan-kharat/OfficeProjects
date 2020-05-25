/*
 * Creation : Dec 27, 2016
 */
package com.inetpsa.poc00.application.tvvdeptdl;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.tvvdependent.TvvDepTDL;

/**
 * The Interface TvvDepTDLService.
 */
@Service
public interface TvvDepTDLService {

	/**
	 * Insert tvv dep tdl.
	 *
	 * @param tvvDepTDL the tvv dep tdl
	 * @return the tvv dep tdl
	 */
	TvvDepTDL insertTvvDepTDL(TvvDepTDL tvvDepTDL);

	/**
	 * Update tvv dep tdl.
	 *
	 * @param tvvDepTDL the tvv dep tdl
	 * @return the tvv dep tdl
	 */
	TvvDepTDL updateTvvDepTDL(TvvDepTDL tvvDepTDL);

	/**
	 * Delete tvv dep tdl.
	 *
	 * @param tvvDepTDL the tvv dep tdl
	 * @return the int
	 */
	int deleteTvvDepTDL(TvvDepTDL tvvDepTDL);

}
