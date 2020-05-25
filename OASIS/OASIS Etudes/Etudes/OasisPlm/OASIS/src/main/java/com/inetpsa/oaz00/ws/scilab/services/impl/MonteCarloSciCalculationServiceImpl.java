/*
 * Author: U224106
 * Creation: 18 déc. 2014
 */
package com.inetpsa.oaz00.ws.scilab.services.impl;

import java.util.Iterator;

import javasci.SciDouble;
import javasci.SciDoubleArray;
import javasci.SciString;
import javasci.Scilab;

import org.apache.log4j.Logger;

import com.inetpsa.oaz00.ws.scilab.model.SciMotherRequirementType;
import com.inetpsa.oaz00.ws.scilab.model.SciRequirementType;
import com.inetpsa.oaz00.ws.scilab.services.SciConnectorAbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class ScilabMonteCarloSimulationTask.
 * 
 * @author U224106
 */
public class MonteCarloSciCalculationServiceImpl extends SciConnectorAbstractService {

    /** The Mother requirement. */
    private SciMotherRequirementType motherRequirement;

    /** The Constant SAMPLING_SIZE. */
    private static final double SAMPLING_SIZE = 10000.0;

    /** The logger. */
    private static Logger logger = Logger.getLogger(MonteCarloSciCalculationServiceImpl.class);

    /**
     * Instantiates a new Monte Carlo Scilab calculation service implementation.
     * 
     * @param pMotherRequirement the mother requirement
     */
    public MonteCarloSciCalculationServiceImpl(SciMotherRequirementType pMotherRequirement) {
        super();

        motherRequirement = pMotherRequirement;
    }

    /**
     * Send to SCILAB. {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.scilab.services.SciCalculationService#sendToScilab()
     */
    public void sendToScilab() {
        cleanVariables(new String[] { "graphe", "dataFile", "ntirages", "motherRequirement", "formula", "uncertainty", "infValue", "supValue",
                "contributorList" });

        // Creation of Scilab objects
        new SciString("graphe", motherRequirement.getPlotPathPrefix());
        new SciString("dataFile", "");
        new SciDouble("ntirages", MonteCarloSciCalculationServiceImpl.SAMPLING_SIZE);
        scilabStringArray("motherRequirement", new String[] { normalizeFileName(motherRequirement.getCode()) });
        scilabStringArray("formula", new String[] { motherRequirement.getTransferModel().getCalculationFormula() });
        scilabDoubleArray("uncertainty", new Double[] { new Double(motherRequirement.getTransferModel().getUncertainty()) });
        scilabDoubleArray("infValue", new Double[] { motherRequirement.getValueInf() });
        scilabDoubleArray("supValue", new Double[] { motherRequirement.getValueSup() });
        logger.debug("");
        logger.debug("Formula as sent to SCILAB -----> " + motherRequirement.getTransferModel().getCalculationFormula());
        logger.debug("");

        // Contributors list initialization
        Scilab.Exec("varTmpJava=''contributorList=list(''");
        for (Iterator<SciRequirementType> iterator = motherRequirement.getContributorList().iterator(); iterator.hasNext();) {
            SciRequirementType contributor = iterator.next();
            cleanVariable(contributor.getCalculationName());

            Scilab.Exec(contributor.getScilabMCDistribution(), 0);

            if (iterator.hasNext())
                Scilab.Exec("varTmpJava=varTmpJava+''" + contributor.getCalculationName() + ",''");
            else
                Scilab.Exec("varTmpJava=varTmpJava+''" + contributor.getCalculationName() + "''");
        }
        Scilab.Exec("varTmpJava=varTmpJava+'')''");
        Scilab.Exec("execstr(varTmpJava);clear(varTmpJava)");

    }

    /**
     * Compute in SCILAB. {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.scilab.services.SciCalculationService#computeInScilab()
     */
    public void computeInScilab() {
        sendToScilab();
        cleanVariables(new String[] { "resultsMC", "out" });
        // Calculation execution
        String SciCmd = "[resultsMC,out]=EvaluationMontecarlo(motherRequirement, formula, contributorList, uncertainty, ntirages, dataFile, infValue, supValue, graphe)";
        if (logger.isDebugEnabled())
            Scilab.Exec(SciCmd, 1);
        else
            Scilab.Exec(SciCmd);
    }

    /**
     * Gets the Scilab data. Recuperation des donnees du calcul
     * <p>
     * Output structure
     * <ul>
     * <li>2 first values = number of mother requirements and number of data</li>
     * <li>For each mother requirement
     * <ul>
     * <li>Mean</li>
     * <li>Standard deviation</li>
     * <li>Cap</li>
     * <li>Cpk</li>
     * <li>Real offcentering</li>
     * <li>Sigma offcentering</li>
     * <li>TNC</li>
     * <li>Simulated MIN</li>
     * <li>Simulated MAX</li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     * {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.scilab.services.SciCalculationService#getScilabData()
     */
    public double[] getScilabData() {
        SciDouble sciOutC = new SciDouble("outC");
        Scilab.Exec("outC=length(out)");
        SciDoubleArray SciMCData = new SciDoubleArray("mcOut", (int) sciOutC.getData(), 1);
        Scilab.Exec("mcOut=out");
        double[] d = SciMCData.getData();
        cleanVariables(new String[] { "mcOut", "out", "outC" });
        return d;
    }

}
