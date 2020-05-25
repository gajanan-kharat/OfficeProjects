/*
 * Creation : Nov 28, 2016
 */
package com.inetpsa.poc00.domain.statisticalsample;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating StatisticalSample objects.
 */
public interface StatisticalSampleFactory extends GenericFactory<StatisticalSample> {

    /**
     * Creates a new StatisticalSample object.
     *
     * @return the statistical sample
     */
    public StatisticalSample createStatisticalSample();

}
