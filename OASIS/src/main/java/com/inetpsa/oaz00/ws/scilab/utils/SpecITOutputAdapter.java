/*
 * Author: U224106
 * Creation: 25 août 2014
 */
package com.inetpsa.oaz00.ws.scilab.utils;

import java.util.Iterator;
import java.util.List;

import com.inetpsa.oaz00.ws.scilab.model.SciMotherRequirementType;
import com.inetpsa.oaz00.ws.scilab.model.SciRequirementType;

/**
 * The Class SpecITOutputAdapter.
 * 
 * @author U224106
 */
public class SpecITOutputAdapter {

    /**
     * Creates the TI Specification Scilab result.
     * 
     * @param motherList the Mother requirement list
     * @param contributorList the contributor list
     * @param prestRes the prestation result values
     * @param contribRes the contributor result values
     * @param prestNames the prestation names
     * @param contribNames the contributor names
     * @param graphPathPrefix the graph path prefix
     */
    public static int createSpecITScilabResult(List<SciMotherRequirementType> motherList, List<SciRequirementType> contributorList,
            double[] prestRes, double[] contribRes, String[] prestNames, String[] contribNames, String graphPathPrefix) {

        // Number of optimization iterations
        int nbLignes = (int) prestRes[0];
        int nbIter = nbLignes / motherList.size();

        // Just keep last iteration results.
        if (nbIter > 0) {
            // Prestation process
            fillInSpecITMotherRequirementsDetails(motherList, prestRes, prestNames, graphPathPrefix, nbIter);

            // Contributor process
            fillInSpecITContributorsDetails(contributorList, contribRes, contribNames, nbIter);

            for (Iterator<SciMotherRequirementType> iterator = motherList.iterator(); iterator.hasNext();) {
                SciMotherRequirementType motherRequirement = iterator.next();
                for (Iterator<SciRequirementType> iterator2 = motherRequirement.getContributorList().iterator(); iterator2.hasNext();) {
                    SciRequirementType contributor = iterator2.next();
                    for (Iterator<SciRequirementType> iterator3 = contributorList.iterator(); iterator3.hasNext();) {
                        SciRequirementType requirement = iterator3.next();
                        if (contributor.getName().equals(requirement.getName())) {
                            contributor.setValueInf(NumberUtil.getTruncatedNumber(requirement.getValueInf()));
                            contributor.setValueSup(NumberUtil.getTruncatedNumber(requirement.getValueSup()));
                        }
                    }
                }
            }

        }
        return nbIter;
    }

    /**
     * Fill in TI Specification Mother requirements details.
     * 
     * @param motherList the Mother requirement list
     * @param prestRes the prestation result values
     * @param prestNames the prestation names
     * @param graphPathPrefix the graph path prefix
     * @param nbIter the number of iterations
     */
    private static void fillInSpecITMotherRequirementsDetails(List<SciMotherRequirementType> motherList, double[] prestRes, String[] prestNames,
            String graphPathPrefix, int nbIter) {
        int nbPrest = motherList.size();

        for (int i = 0; i < nbPrest; i++) {
            int iCAP = 1 * nbIter * nbPrest + (nbIter - 1) * nbPrest + i + 2;
            int iCPK = 2 * nbIter * nbPrest + (nbIter - 1) * nbPrest + i + 2;
            int iTNC = 3 * nbIter * nbPrest + (nbIter - 1) * nbPrest + i + 2;
            int iDecReel = 4 * nbIter * nbPrest + (nbIter - 1) * nbPrest + i + 2;

            String name = prestNames[(nbIter - 1) * nbPrest + i];
            for (Iterator<SciMotherRequirementType> iterator = motherList.iterator(); iterator.hasNext();) {
                SciMotherRequirementType motherRequirement = iterator.next();
                if (motherRequirement.getCalculationName().equals(name)) {
                    motherRequirement.setCap(NumberUtil.getTruncatedNumber(prestRes[iCAP]));
                    motherRequirement.setCpk(NumberUtil.getTruncatedNumber(prestRes[iCPK]));
                    // Set TNC value in PPM instead of %
                    motherRequirement.setTnc(NumberUtil.getTruncatedNumber(prestRes[iTNC] * 10000));
                    motherRequirement.setCenteringMax(NumberUtil.getTruncatedNumber(prestRes[iDecReel]));
                    motherRequirement.setPlotPath(graphPathPrefix + "_Prest_" + name + "_IT" + (nbIter - 1) + "_Hist.gif");
                    break;
                }
            }
        }
    }

    /**
     * Fill in TI Specification contributors details.
     * 
     * @param contributorList the contributor list
     * @param parRes the contributor result values
     * @param parNames the contributor names
     * @param nbIter the number of iterations
     */
    private static void fillInSpecITContributorsDetails(List<SciRequirementType> contributorList, double[] parRes, String[] parNames, int nbIter) {
        int nbContrib = contributorList.size();
        for (int i = 0; i < nbContrib; i++) {
            int iInf = 2 * nbIter * nbContrib + (nbIter - 1) * nbContrib + i + 2;
            int iSup = 3 * nbIter * nbContrib + (nbIter - 1) * nbContrib + i + 2;

            String name = parNames[(nbIter - 1) * nbContrib + i];
            for (Iterator<SciRequirementType> iterator = contributorList.iterator(); iterator.hasNext();) {
                SciRequirementType contributor = iterator.next();
                if (contributor.getCalculationName().equals(name)) {
                    contributor.setValueInf(NumberUtil.getTruncatedNumber(parRes[iInf]));
                    contributor.setValueSup(NumberUtil.getTruncatedNumber(parRes[iSup]));
                    break;
                }
            }
        }
    }
}
