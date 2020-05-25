/*
 * Creation : Jan 3, 2017
 */
package com.inetpsa.poc00.application.engine;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.engine.Engine;

/**
 * The Interface EngineService.
 */
@Service
public interface EngineService {

	/**
	 * Save engine.
	 *
	 * @param engine the engine
	 * @return the engine
	 */
	public Engine saveEngine(Engine engine);

	/**
	 * Delete engine.
	 *
	 * @param engineId the engine id
	 * @return true, if successful
	 */
	public boolean deleteEngine(Long engineId);

}
