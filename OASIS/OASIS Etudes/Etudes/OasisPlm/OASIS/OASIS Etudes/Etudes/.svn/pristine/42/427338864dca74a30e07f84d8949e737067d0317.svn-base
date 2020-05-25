/*
 * Author: U224106
 * Creation: 25 août 2014
 */
package com.inetpsa.oaz00.ws.scilab.services.impl;

import javasci.SciDouble;
import javasci.SciDoubleArray;
import javasci.Scilab;

import com.inetpsa.oaz00.ws.scilab.model.SciMotherRequirementType;
import com.inetpsa.oaz00.ws.scilab.model.SciRequirementType;
import com.inetpsa.oaz00.ws.scilab.services.SciConnectorAbstractService;

/**
 * The Class ArithStatSciCalculationServiceImpl.
 * 
 * @author U224106
 */
public class ArithStatSciCalculationServiceImpl extends SciConnectorAbstractService {

    /** The names par. */
    private String[] namesPar;

    /** The data. */
    private String[] data;

    /** The Scilab Mother requirement. */
    private SciMotherRequirementType motherRequirement;

    /**
     * Instantiates a new Arithmetic Statistical Scilab calculation service implementation.
     * 
     * @param pMotherRequirement the Mother requirement
     */
    public ArithStatSciCalculationServiceImpl(SciMotherRequirementType pMotherRequirement) {
        super();
        this.motherRequirement = pMotherRequirement;
        this.namesPar = new String[motherRequirement.getContributorList().size()];
        this.data = new String[3 * motherRequirement.getContributorList().size()];
        int cIndex = 0;
        for (SciRequirementType contributor : motherRequirement.getContributorList()) {
            this.namesPar[cIndex] = contributor.getCalculationName();
            this.data[3 * cIndex] = contributor.getNominal().toString();
            this.data[3 * cIndex + 1] = contributor.getValueInf().toString();
            this.data[3 * cIndex + 2] = contributor.getValueSup().toString();
            cIndex++;
        }
    }

    /**
     * Send to SCILAB. {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.scilab.services.SciCalculationService#sendToScilab()
     */
    public void sendToScilab() {
        cleanVariables(new String[] { "model", "data", "namesPar", "risque_min", "risque_max" });

        // Creation of Scilab objects
        scilabStringArray("model", new String[] { motherRequirement.getTransferModel().getCalculationFormula() });
        scilabStringArray("data", this.data);
        scilabStringArray("namesPar", this.namesPar);

        // TODO - TNC must be a % value - PSA to check if PLM sends %
        new SciDouble("risque_max", motherRequirement.getTnc().doubleValue() / 1000000);
        new SciDouble("risque_min", motherRequirement.getTnc().doubleValue() / 1000000);
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
        String SciCmd = "out=calculCOTE(model, namesPar, data, risque_max, risque_min)";
        Scilab.Exec(SciCmd);
    }

    /**
     * Gets the Scilab data. {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.scilab.services.SciCalculationService#getScilabData()
     */
    public double[] getScilabData() {
        SciDouble sciOutC = new SciDouble("outCote");
        Scilab.Exec("outCote=length(out)");
        SciDoubleArray SciCoteData = new SciDoubleArray("OutC", (int) sciOutC.getData(), 1);
        Scilab.Exec("OutC=out");

        double[] d = SciCoteData.getData();
        cleanVariables(new String[] { "OutC", "out", "outCote" });
        return d;
    }
}
