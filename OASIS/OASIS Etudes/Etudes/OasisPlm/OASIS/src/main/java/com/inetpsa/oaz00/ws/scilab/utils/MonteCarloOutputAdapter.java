/*
 * Author: U224106
 * Creation: 25 août 2014
 */
package com.inetpsa.oaz00.ws.scilab.utils;

import java.util.ArrayList;
import java.util.List;

import com.inetpsa.oaz00.ws.scilab.model.SciMotherRequirementType;
import com.inetpsa.oaz00.ws.scilab.model.SciRequirementType;
import com.inetpsa.oaz00.ws.scilab.services.SciConnectorAbstractService;

/**
 * The Class MonteCarloOutputAdapter.
 * 
 * @author U224106
 */
public class MonteCarloOutputAdapter {

    /**
     * Creates the Monte Carlo Scilab result.
     * 
     * @param pMotherRequirement the Mother requirement
     * @param mcData the Monte Carlo data
     */
    public static void createMCScilabResult(SciMotherRequirementType pMotherRequirement, double[] mcData) {

        pMotherRequirement.setMean(NumberUtil.getTruncatedNumber(mcData[2]));
        pMotherRequirement.setStandardDeviation(NumberUtil.getTruncatedNumber(mcData[3]));
        pMotherRequirement.setCap(NumberUtil.getTruncatedNumber(mcData[4]));
        pMotherRequirement.setCpk(NumberUtil.getTruncatedNumber(mcData[5]));
        pMotherRequirement.setCenteringMax(NumberUtil.getTruncatedNumber(mcData[6]));
        // Set TNC value in PPM instead of %
        pMotherRequirement.setTnc(NumberUtil.getTruncatedNumber(mcData[8] * 10000));
        pMotherRequirement.setValueInf(NumberUtil.getTruncatedNumber(mcData[9]));
        pMotherRequirement.setValueSup(NumberUtil.getTruncatedNumber(mcData[10]));

        List<SciRequirementType> contributorList = new ArrayList<SciRequirementType>();
        if (pMotherRequirement.getContributorList().size() != (int) mcData[11]) {
            System.out
                    .println("Alert! MonteCarloOutputAdapter.createMCScilabResult(): Contributor list's size is not equal to mcData[11] from SCILAB result.");
        }

        for (int i = 1; i <= mcData[11]; i++) {
            SciRequirementType contributor = pMotherRequirement.getContributorList().get(i - 1);
            contributor.setCap(NumberUtil.getTruncatedNumber(mcData[13 + 2 * (i - 1)]));
            contributor.setCpk(NumberUtil.getTruncatedNumber(mcData[13 + 2 * (i - 1) + 1]));
            contributorList.add(contributor);
        }
        pMotherRequirement.setContributorList(contributorList);
        pMotherRequirement.setTransferModel(pMotherRequirement.getTransferModel());

        pMotherRequirement.setPlotPath(pMotherRequirement.getPlotPathPrefix() + "_Prest_"
                + SciConnectorAbstractService.normalizeFileName(pMotherRequirement.getCode()) + ".gif");

    }

}