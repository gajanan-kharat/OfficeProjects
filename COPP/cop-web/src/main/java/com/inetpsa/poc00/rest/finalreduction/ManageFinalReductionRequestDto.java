package com.inetpsa.poc00.rest.finalreduction;

import java.util.List;


/**
 * The Class ManageFinalReductionRequestDto.
 */
public class ManageFinalReductionRequestDto {

	/** The final reduction representation list. */
	private List<FinalReductionRepresentation> finalReductionRepresentationList;

	/**
	 * Gets the final reduction representation list.
	 *
	 * @return the final reduction representation list
	 */
	public List<FinalReductionRepresentation> getFinalReductionRepresentationList() {
		return finalReductionRepresentationList;
	}

	/**
	 * Sets the final reduction representation list.
	 *
	 * @param finalReductionRepresentationList the new final reduction representation list
	 */
	public void setFinalReductionRepresentationList(
			List<FinalReductionRepresentation> finalReductionRepresentationList) {
		this.finalReductionRepresentationList = finalReductionRepresentationList;
	}
}
