/*
 * Author: U224106
 * Creation: 25 août 2014
 */
package com.inetpsa.oaz00.ws.scilab.services.impl;

import java.util.HashSet;
import java.util.Set;

import javasci.SciDouble;
import javasci.SciDoubleArray;
import javasci.Scilab;

import com.inetpsa.oaz00.ws.checker.formula.services.EquationVariables;
import com.inetpsa.oaz00.ws.scilab.model.SciMotherRequirementType;
import com.inetpsa.oaz00.ws.scilab.model.SciRequirementType;
import com.inetpsa.oaz00.ws.scilab.services.SciConnectorAbstractService;

/**
 * The Class SemiQuadSciCalculationServiceImpl.
 * 
 * @author U224106
 */
public class SemiQuadSciCalculationServiceImpl extends SciConnectorAbstractService {

    /** The Contributor list. */
    private String[] contributorList;

    /** The data. */
    private double[] data;

    /** The Mother requirement. */
    private SciMotherRequirementType motherRequirement;

    /**
     * Instantiates a new Semi Quadratic Scilab calculation service implementation.
     * 
     * @param pMotherRequirement the mother requirement
     * @param ecVariables Used to get the variables (Contributor codes) used in the formula and pass only those to the scilab calculations.
     */
    public SemiQuadSciCalculationServiceImpl(SciMotherRequirementType pMotherRequirement, EquationVariables ecVariables) {
        super();
        this.motherRequirement = pMotherRequirement;
        this.contributorList = new String[motherRequirement.getContributorList().size()];
        this.data = new double[5 * motherRequirement.getContributorList().size()];
        int cIndex = 0;
        // Get the list of all the variables exist in the formula
        Set<String> ecVariablesSet = new HashSet<String>(ecVariables.getVariables());
        
        for (SciRequirementType contributor : motherRequirement.getContributorList()) {
        		this.contributorList[cIndex] = contributor.getCalculationName();
        		// If the contributors are used in formula then only pass them to scilab calculation.
        		if(ecVariablesSet.contains(contributor.getCode())) {
	        		this.data[5 * cIndex] = contributor.getNominal().doubleValue();
	        		this.data[5 * cIndex + 1] = contributor.getValueInf().doubleValue();
	        		this.data[5 * cIndex + 2] = contributor.getValueSup().doubleValue();
	        		this.data[5 * cIndex + 3] = contributor.getMean().doubleValue();
	        		this.data[5 * cIndex + 4] = contributor.getStandardDeviation().doubleValue();
        		}
        		cIndex++;
        }
    }

    /**
     * Send to SCILAB. {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.scilab.services.SciCalculationService#sendToScilab()
     */
    public void sendToScilab() {
        Scilab.Exec("clear(''formula'',''contributorList'',''valuesPar'',''TNCObj'',''CPKObj'')");
        // Creation of Scilab objects
        scilabStringArray("formula", new String[] { motherRequirement.getTransferModel().getCalculationFormula() });
        scilabStringArray("contributorList", this.contributorList);
        new SciDoubleArray("valuesPar", 5, motherRequirement.getContributorList().size(), this.data);

        // TODO - TNC must be a % value - PSA to check if PLM sends %
        new SciDouble("TNCObj", motherRequirement.getTnc().doubleValue() / 1000000);
        new SciDouble("CPKObj", motherRequirement.getCpk().doubleValue());
    }

    /**
     * Compute in SCILAB. {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.scilab.services.SciCalculationService#computeInScilab()
     */
    public void computeInScilab() {
        this.sendToScilab();
        cleanVariable("out");

        // Calculation execution
        String SciCmd = "out=SemiQuadCalc(formula, contributorList, valuesPar, TNCObj, CPKObj)";
        Scilab.Exec(SciCmd);
    }

    /**
     * Gets the Scilab data. {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.scilab.services.SciCalculationService#getScilabData()
     */
    public double[] getScilabData() {
        SciDouble sciOutC = new SciDouble("outSemiQuad");
        Scilab.Exec("outSemiQuad=length(out)");
        SciDoubleArray SciSemiQuadData = new SciDoubleArray("OutC", (int) sciOutC.getData(), 1);
        Scilab.Exec("OutC=out");

        double[] out = SciSemiQuadData.getData();
        return out;
    }
}
