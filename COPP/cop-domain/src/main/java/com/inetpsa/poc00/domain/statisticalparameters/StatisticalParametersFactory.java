/*
 * Creation : Dec 6, 2016
 */
package com.inetpsa.poc00.domain.statisticalparameters;

import org.seedstack.business.domain.GenericFactory;

public interface StatisticalParametersFactory extends GenericFactory<StatisticalCalculationParameters> {

    public StatisticalCalculationParameters createSCP();
}
