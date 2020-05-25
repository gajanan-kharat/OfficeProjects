/*
 * Author: U224106
 * Creation: 25 août 2014
 */
package com.inetpsa.oaz00.ws.scilab.utils;

import com.inetpsa.oaz00.ws.scilab.model.SciMotherRequirementType;

/**
 * The Class ArithStatOutputAdapter.
 * 
 * @author U224106
 */
public class ArithStatOutputAdapter {

    /**
     * Creates the Arithmetic Statistical Scilab result.
     * 
     * @param pMotherRequirement the Mother requirement
     * @param arithStatData the Arithmetic Statistical data
     */
    public static void createArithStatScilabResult(SciMotherRequirementType pMotherRequirement, double[] arithStatData) {

        pMotherRequirement.setNominal(NumberUtil.getTruncatedNumber(arithStatData[0]));

        pMotherRequirement.setArithmeticalIT(NumberUtil.getTruncatedNumber(arithStatData[1]));
        pMotherRequirement.setArithmeticalValueInf(NumberUtil.getTruncatedNumber(arithStatData[2]));
        pMotherRequirement.setArithmeticalValueSup(NumberUtil.getTruncatedNumber(arithStatData[3]));
        pMotherRequirement.setArithmeticalITInf(NumberUtil.getTruncatedNumber(arithStatData[2] - arithStatData[0]));
        pMotherRequirement.setArithmeticalITSup(NumberUtil.getTruncatedNumber(arithStatData[3] - arithStatData[0]));

        pMotherRequirement.setStatisticalIT(NumberUtil.getTruncatedNumber(arithStatData[4]));
        pMotherRequirement.setStatisticalValueInf(NumberUtil.getTruncatedNumber(arithStatData[5]));
        pMotherRequirement.setStatisticalValueSup(NumberUtil.getTruncatedNumber(arithStatData[6]));
        pMotherRequirement.setStatisticalITInf(NumberUtil.getTruncatedNumber(arithStatData[5] - arithStatData[0]));
        pMotherRequirement.setStatisticalITSup(NumberUtil.getTruncatedNumber(arithStatData[6] - arithStatData[0]));

        pMotherRequirement.setCenteringMax(NumberUtil.getTruncatedNumber(arithStatData[7]));
    }
}