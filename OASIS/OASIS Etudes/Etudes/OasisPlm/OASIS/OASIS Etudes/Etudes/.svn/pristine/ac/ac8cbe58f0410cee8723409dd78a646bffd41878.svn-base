/*
 * Author: U224106
 * Creation: 25 août 2014
 */
package com.inetpsa.oaz00.ws.scilab.utils;

import com.inetpsa.oaz00.ws.scilab.model.SciMotherRequirementType;

/**
 * The Class SemiQuadOutputAdapter.
 * 
 * @author U224106
 */
public class SemiQuadOutputAdapter {

    /**
     * Creates the Semi Quadratic Scilab result.
     * 
     * @param pMotherRequirement the Mother requirement
     * @param semiQuadData the Semi Quadratic data
     */
    public static void createSemiQuadScilabResult(SciMotherRequirementType pMotherRequirement, double[] semiQuadData) {

        pMotherRequirement.setNominal(NumberUtil.getTruncatedNumber(semiQuadData[0]));
        pMotherRequirement.setCenteringMax(NumberUtil.getTruncatedNumber(semiQuadData[1]));

        pMotherRequirement.setArithmeticalIT(NumberUtil.getTruncatedNumber(semiQuadData[2]));
        pMotherRequirement.setArithmeticalITInf(NumberUtil.getTruncatedNumber(semiQuadData[3]));
        pMotherRequirement.setArithmeticalITSup(NumberUtil.getTruncatedNumber(semiQuadData[4]));
        pMotherRequirement.setArithmeticalValueInf(NumberUtil.getTruncatedNumber(semiQuadData[0] + semiQuadData[3]));
        pMotherRequirement.setArithmeticalValueSup(NumberUtil.getTruncatedNumber(semiQuadData[0] + semiQuadData[4]));

        pMotherRequirement.setSemiQuadraticIT(NumberUtil.getTruncatedNumber(semiQuadData[5]));
        pMotherRequirement.setSemiQuadraticITInf(NumberUtil.getTruncatedNumber(semiQuadData[6]));
        pMotherRequirement.setSemiQuadraticITSup(NumberUtil.getTruncatedNumber(semiQuadData[7]));
        pMotherRequirement.setSemiQuadraticValueInf(NumberUtil.getTruncatedNumber(semiQuadData[0] + semiQuadData[6]));
        pMotherRequirement.setSemiQuadraticValueSup(NumberUtil.getTruncatedNumber(semiQuadData[0] + semiQuadData[7]));

        pMotherRequirement.setStatisticalIT(NumberUtil.getTruncatedNumber(semiQuadData[8]));
        pMotherRequirement.setStatisticalITInf(NumberUtil.getTruncatedNumber(semiQuadData[9]));
        pMotherRequirement.setStatisticalITSup(NumberUtil.getTruncatedNumber(semiQuadData[10]));
        pMotherRequirement.setStatisticalValueInf(NumberUtil.getTruncatedNumber(semiQuadData[0] + semiQuadData[9]));
        pMotherRequirement.setStatisticalValueSup(NumberUtil.getTruncatedNumber(semiQuadData[0] + semiQuadData[10]));
    }
}