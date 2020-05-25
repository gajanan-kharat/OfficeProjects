/*
 * Author: U224106
 * Creation: 2 déc. 2014
 */
package com.inetpsa.oaz00.ws.scilab.model;

import java.lang.reflect.Field;

import com.inetpsa.oaz00.ws.scilab.utils.SciObjectInputAdapter;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.RequirementType;

/**
 * The Class SciRequirementType.
 * 
 * @author U224106
 */
public class SciRequirementType extends RequirementType {

    /** The original requirement. */
    private RequirementType originalRequirement;

    /** The calculation name. */
    private String calculationName;

    /** The Scilab Monte Carlo distribution. */
    private String scilabMCDistribution;

    /** The tnc objective. */
    protected Double tncObjective;

    /** The cap objective. */
    protected Double capObjective;

    /** The centering objective. */
    protected Double centeringMaxObjective;

    /** The value inf objective. */
    protected Double valueInfObjective;

    /** The value sup objective. */
    protected Double valueSupObjective;

    /**
     * Instantiates a new Scilab Requirement type.
     * 
     * @param pRequirement the requirement
     * @throws SecurityException the security exception
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IllegalAccessException the illegal access exception
     */
    public SciRequirementType(RequirementType pRequirement) throws SecurityException, IllegalArgumentException, IllegalAccessException {
        super();
        originalRequirement = pRequirement;

        SciObjectInputAdapter.generateScilabObject(pRequirement, this, RequirementType.class);
        setStatus(true);

        // Remove potential dash in the name
        setCalculationName(this.getName().replaceAll("-", ""));
        if (law != null)
            generateParamDefinition();

        this.valueInfObjective = this.valueInf;
        this.valueSupObjective = this.valueSup;
    }

    /**
     * Get originalRequirement.
     * 
     * @return the originalRequirement
     */
    public RequirementType getOriginalRequirement() {
        return originalRequirement;
    }

    /**
     * Set originalRequirement.
     * 
     * @param originalRequirement the originalRequirement to set
     */
    public void setOriginalRequirement(RequirementType originalRequirement) {
        this.originalRequirement = originalRequirement;
    }

    /**
     * Get calculationName.
     * 
     * @return the calculationName
     */
    public String getCalculationName() {
        return calculationName;
    }

    /**
     * Set calculationName.
     * 
     * @param calculationName the calculationName to set
     */
    public void setCalculationName(String calculationName) {
        this.calculationName = calculationName;
    }

    /**
     * Get scilabMCDistribution.
     * 
     * @return the scilabMCDistribution
     */
    public String getScilabMCDistribution() {
        return scilabMCDistribution;
    }

    /**
     * Set scilabMCDistribution.
     * 
     * @param scilabMCDistribution the scilabMCDistribution to set
     */
    public void setScilabMCDistribution(String scilabMCDistribution) {
        this.scilabMCDistribution = scilabMCDistribution;
    }

    /**
     * Gets the value inf objective.
     * 
     * @return the value inf objective
     */
    public Double getValueInfObjective() {
        return valueInfObjective;
    }

    /**
     * Sets the value inf objective.
     * 
     * @param valueInfObjective the new value inf objective
     */
    public void setValueInfObjective(Double valueInfObjective) {
        this.valueInfObjective = valueInfObjective;
    }

    /**
     * Gets the value sup objective.
     * 
     * @return the value sup objective
     */
    public Double getValueSupObjective() {
        return valueSupObjective;
    }

    /**
     * Sets the value sup objective.
     * 
     * @param valueSupObjective the new value sup objective
     */
    public void setValueSupObjective(Double valueSupObjective) {
        this.valueSupObjective = valueSupObjective;
    }

    /**
     * Method to build Scilab distribution definition Ex: A=normalMontecarlo( ''A'',15.0,3.75,0.0,30.0,5.0,0.0,1.0,1.0,8.0,0.0,30.0)
     */
    public void generateParamDefinition() {
        String sRet = this.calculationName + "=";
        String sp1 = "%nan";
        String sp2 = "%nan";
        String sMin = "%nan";
        String sMax = "%nan";

        if (paramLaw1 != null && !paramLaw1.isNaN())
            sp1 = paramLaw1.toString();
        else
            return;

        if (paramLaw2 != null && !paramLaw2.isNaN())
            sp2 = paramLaw2.toString();

        if (valueInf != null && !valueInf.isNaN())
            sMin = valueInf.toString();

        if (valueSup != null && !valueSup.isNaN())
            sMax = valueSup.toString();

        switch (law) {
        case UNIFORM:
            sRet += "uniformeMontecarlo(''" + calculationName + "''," + sp1 + "," + sp2 + "," + sMin + "," + sMax + ",0.0)";
            break;
        case LOG:
            sRet += "lognormalMontecarlo(''" + calculationName + "''," + sp1 + "," + sp2 + "," + sMin + "," + sMax + ",0.0)";
            break;
        case EXPO:
            // Exponential distribution is a specific Weibull
            // Exponential ( X ) = Weibull ( 1, 1/X )
            if (null != paramLaw1) {
                sp2 = new Double(1 / paramLaw1).toString();
                sp1 = new Double(1).toString();
            }
        case WEIBULL:
            sRet += "weibullMontecarlo(''" + calculationName + "''," + sp1 + "," + sp2 + "," + sMin + "," + sMax + ",0.0)";
            break;
        case RAYLEIGH:
            // Rayleigh distribution is a specific Weibull
            // Rayleigh ( s ) = Weibull ( 2, s )
            if (null != paramLaw1) {
                sp2 = new Double(1 / paramLaw1).toString();
                sp1 = new Double(2).toString();
                sRet += "weibullMontecarlo(''" + calculationName + "''," + sp1 + "," + sp2 + "," + sMin + "," + sMax + ",0.0)";
            }
            break;
        case BINOMIAL:
            Double sp1Double = Double.parseDouble(sp1);
            Integer sp1Integer = sp1Double.intValue();
            sp1 = sp1Integer.toString();
            sRet += "binomialeMontecarlo(''" + calculationName + "''," + sp1 + "," + sp2 + ")";
            break;
        case POISSON:
            sRet += "poissonMontecarlo(''" + calculationName + "''," + sp1 + ")";
            break;
        default:
            // Lets assume Normal distribution is default
            sRet += "normalMontecarlo(''" + calculationName + "''," + sp1 + "," + sp2 + "," + sMin + "," + sMax + ",0.0,%nan,%nan,%nan,8,%nan,%nan)";
            break;
        }
        this.scilabMCDistribution = sRet;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object otherReq) {
        if (otherReq == null)
            return false;
        if (otherReq == this)
            return true;
        if (!(otherReq instanceof SciRequirementType))
            return false;
        SciRequirementType otherSciReq = (SciRequirementType) otherReq;
        return otherSciReq.getName().equals(this.getName()) && otherSciReq.getId().equals(this.getId());
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

    /**
     * {@inheritDoc}
     * 
     * @return String.
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder output = new StringBuilder(128);
        output.append("\n######## Contributor Result - ").append(getName()).append(" ########\n");
        try {
            Field[] fields = this.getClass().getSuperclass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(this) != null)
                    output.append("\t<").append(field.getName()).append(">").append(field.get(this)).append("</").append(field.getName())
                            .append(">\n");
            }
            fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(this) != null)
                    output.append("\t<").append(field.getName()).append(">").append(field.get(this)).append("</").append(field.getName())
                            .append(">\n");
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return output.toString();
    }

}
