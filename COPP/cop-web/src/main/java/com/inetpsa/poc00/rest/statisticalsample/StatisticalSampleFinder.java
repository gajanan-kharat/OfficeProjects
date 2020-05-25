/*
 * Creation : Nov 29, 2016
 */
package com.inetpsa.poc00.rest.statisticalsample;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;

/**
 * The Interface StatisticalSampleFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface StatisticalSampleFinder {

	/**
	 * Gets the statistical sample by tvv.
	 *
	 * @param tvvLabel the tvv label
	 * @return the statistical sample by tvv
	 */
	public List<StatisticalSampleRepresentation> getStatisticalSampleByTvv(String tvvLabel);

	/**
	 * Gets the statistical sample.
	 *
	 * @param esLabel the es label
	 * @param esVersion the es version
	 * @param carFactoryLabel the car factory label
	 * @param statisticalRuleLabel the statistical rule label
	 * @return the statistical sample
	 */
	public List<StatisticalSampleRepresentation> getStatisticalSample(String esLabel, String esVersion, String carFactoryLabel, String statisticalRuleLabel, String tvvLabel);
}
