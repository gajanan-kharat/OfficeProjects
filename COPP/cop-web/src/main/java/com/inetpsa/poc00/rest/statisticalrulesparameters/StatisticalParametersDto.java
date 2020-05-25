/*
 * Creation : Dec 5, 2016
 */
package com.inetpsa.poc00.rest.statisticalrulesparameters;

import java.util.List;

/**
 * The Class StatisticalParametersDto.
 */
public class StatisticalParametersDto {

    /** The statistical parameter list. */
    private List<StatisticalParameterRepresentation> statisticalParameterList;

    /**
     * Gets the statistical parameter list.
     *
     * @return the statistical parameter list
     */
    public List<StatisticalParameterRepresentation> getStatisticalParameterList() {
        return statisticalParameterList;
    }

    /**
     * Sets the statistical parameter list.
     *
     * @param statisticalParameterList the new statistical parameter list
     */
    public void setStatisticalParameterList(List<StatisticalParameterRepresentation> statisticalParameterList) {
        this.statisticalParameterList = statisticalParameterList;
    }

}
