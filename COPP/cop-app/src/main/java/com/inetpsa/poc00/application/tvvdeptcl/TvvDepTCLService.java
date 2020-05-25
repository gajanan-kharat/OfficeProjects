/*
 * Creation : Dec 27, 2016
 */
package com.inetpsa.poc00.application.tvvdeptcl;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.tvvdependent.TvvDepTCL;

@Service
public interface TvvDepTCLService {
	/**
	 * Insert tvv dep tcl.
	 *
	 * @param tvvDepTCL the tvv dep tcl
	 * @return the tvv dep tcl
	 */
	TvvDepTCL insertTvvDepTCL(TvvDepTCL tvvDepTCL);

	/**
	 * Update tvv dep tcl.
	 *
	 * @param tvvDepTCL the tvv dep tcl
	 * @return the tvv dep tcl
	 */
	TvvDepTCL updateTvvDepTCL(TvvDepTCL tvvDepTCL);

	/**
	 * Delete tvv dep tcl.
	 *
	 * @param tvvDepTCL the tvv dep tcl
	 * @return the int
	 */
	int deleteTvvDepTCL(TvvDepTCL tvvDepTCL);
}
