/*
 * Creation : Dec 02, 2016
 */
package com.inetpsa.poc00.domain.statisticalparameters;

import org.seedstack.business.domain.BaseFactory;

public class StatisticalParametersFactoryDefault extends BaseFactory<StatisticalCalculationParameters> implements StatisticalParametersFactory {

    @Override
    public StatisticalCalculationParameters createSCP() {
        return new StatisticalCalculationParameters();
    }

}
