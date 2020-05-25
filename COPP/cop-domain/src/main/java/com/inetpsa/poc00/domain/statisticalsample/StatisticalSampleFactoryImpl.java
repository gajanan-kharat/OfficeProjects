/*
 * Creation : Nov 28, 2016
 */
package com.inetpsa.poc00.domain.statisticalsample;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class StatisticalSampleFactoryImpl.
 */
public class StatisticalSampleFactoryImpl extends BaseFactory<StatisticalSample> implements StatisticalSampleFactory {

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleFactory#createStatisticalSample()
     */
    @Override
    public StatisticalSample createStatisticalSample() {
        return new StatisticalSample();
    }

}
