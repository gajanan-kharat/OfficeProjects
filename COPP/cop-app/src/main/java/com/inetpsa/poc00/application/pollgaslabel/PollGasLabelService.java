/*
 * Creation : Jan 3, 2017
 */
package com.inetpsa.poc00.application.pollgaslabel;

import java.util.List;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;

/**
 * The Interface PollGasLabelService.
 */
@Service
public interface PollGasLabelService {

	/**
	 * Save poll gas label.
	 * 
	 * @param pollutantGasLabel the pollutant gas label
	 * @return the string
	 */
	public String savePollGasLabel(PollutantGasLabel pollutantGasLabel);

	/**
	 * Delete poll gas label.
	 * 
	 * @param pollutantGasLabelId the pollutant gas label id
	 * @return the string
	 */

	String deletePollGasLabel(Long pollutantGasLabelId);

	/**
	 * Gets the all used pg labels.
	 *
	 * @param emissionStdId the emission std id
	 * @return the all used pg labels
	 */
	List<PollutantGasLabel> getAllUsedPgLabels(Long emissionStdId);
}
