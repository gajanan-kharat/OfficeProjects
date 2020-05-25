/*
 * Creation : Apr 20, 2016
 */
package com.inetpsa.poc00.pollutantorgas;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit;
import com.inetpsa.poc00.mandatorydata.MandatoryDataRepresentation;

/**
 * The Interface PollutantGasLimitsFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface PollutantGasLimitsFinder {

	/**
	 * Gets the all data for ems dep list.
	 *
	 * @param entityId the entity id
	 * @return the all data for ems dep list
	 */
	public List<PollutantGasLimitRepresentation> getAllDataForEMSDepList(long entityId);

	/**
	 * Gets the all pollutants for dep list.
	 *
	 * @param entityId the entity id
	 * @return the all pollutants for dep list
	 */
	List<PollutantGasLimit> getAllPollutantsForDepList(long entityId);

	/**
	 * Gets the all min pg limits for label.
	 *
	 * @param label the label
	 * @return the all min pg limits for label
	 */
	List<Double> getAllMinPGLimitsForLabel(String label);

	/**
	 * Gets the all max pg limits for label.
	 *
	 * @param label the label
	 * @return the all max pg limits for label
	 */
	List<Double> getAllMaxPGLimitsForLabel(String label);

	/**
	 * Gets the all pollutant gas limit.
	 *
	 * @param emsVersion the ems version
	 * @param emsId the ems id
	 * @return the all pollutant gas limit
	 */
	List<MandatoryDataRepresentation> getAllPollutantGasLimit(String emsVersion, long emsId);

}
