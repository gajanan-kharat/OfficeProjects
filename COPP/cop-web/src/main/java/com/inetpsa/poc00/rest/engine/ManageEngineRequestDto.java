package com.inetpsa.poc00.rest.engine;

import java.util.List;


/**
 * The Class ManageEngineRequestDto.
 */
public class ManageEngineRequestDto {

	/** The engine representation. */
	private List<EngineRepresentation> engineRepresentation;

	/**
	 * Gets the engine representation.
	 *
	 * @return the engine representation
	 */
	public List<EngineRepresentation> getEngineRepresentation() {
		return engineRepresentation;
	}

	/**
	 * Sets the engine representation.
	 *
	 * @param engineRepresentation the new engine representation
	 */
	public void setEngineRepresentation(List<EngineRepresentation> engineRepresentation) {
		this.engineRepresentation = engineRepresentation;
	}

}
