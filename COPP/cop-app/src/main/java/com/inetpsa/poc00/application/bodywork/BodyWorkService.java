/*
 * Creation : Dec 30, 2016
 */
package com.inetpsa.poc00.application.bodywork;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.bodywork.Bodywork;

/**
 * The Interface BodyWorkService.
 */
@Service
public interface BodyWorkService {

	/**
	 * Save body work.
	 *
	 * @param bodyWork the body work
	 * @return the bodywork
	 */
	public Bodywork saveBodyWork(Bodywork bodyWork);

	/**
	 * Delelte body work.
	 *
	 * @param bodyWorkId the body work id
	 * @return true, if successful
	 */
	public boolean delelteBodyWork(Long bodyWorkId);
}
