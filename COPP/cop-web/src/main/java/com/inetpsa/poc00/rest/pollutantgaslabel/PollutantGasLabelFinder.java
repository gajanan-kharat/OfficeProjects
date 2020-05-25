package com.inetpsa.poc00.rest.pollutantgaslabel;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;

/**
 * The Interface PollutantGasLabelFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface PollutantGasLabelFinder {

    /**
     * Gets the all pollutant data.
     *
     * @return the all pollutant data
     */
    public List<PollutantGasLabelRepresentation> getAllPollutantData();

    /**
     * Gets the all PG lables.
     *
     * @return the all PG lables
     */
    public List<PollutantGasLabelRepresentation> getAllPGLables();

    /**
     * Find pollutant gas label data by label.
     *
     * @param label the label
     * @return the list
     */
    public List<PollutantGasLabelRepresentation> findPollutantGasLabelDataByLabel(String label);

	/**
	 * Gets the PG lfor statistical parameter.
	 *
	 * @param statisticalRuleId the statistical rule id
	 * @return the PG lfor statistical parameter
	 */
    public List<PollutantGasLabelRepresentation> getPGLforStatisticalParameter(Long statisticalRuleId);

}
