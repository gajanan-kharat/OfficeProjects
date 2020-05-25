/*
 * Author: U224106
 * Creation: 25 août 2014
 */
package com.inetpsa.oaz00.ws.scilab.model;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.inetpsa.oaz00.ws.scilab.utils.SciObjectInputAdapter;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.TransferModelType;

/**
 * The Class SciTransferModelType.
 * 
 * @author U224106
 */
public class SciTransferModelType extends TransferModelType {

    /** The original transfer model. */
    private TransferModelType originalTransferModel;

    /** The calculation formula. */
    private String calculationFormula;

    /**
     * Instantiates a new Scilab Transfer model type.
     * 
     * @param pTransferModel the transfer model
     * @throws SecurityException the security exception
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IllegalAccessException the illegal access exception
     */
    public SciTransferModelType(TransferModelType pTransferModel) throws SecurityException, IllegalArgumentException, IllegalAccessException {
        super();
        originalTransferModel = pTransferModel;
        SciObjectInputAdapter.generateScilabObject(pTransferModel, this, TransferModelType.class);
        setCalculationFormula(this.formula);
        setStatus(true);
    }

    /**
     * Getter originalTransferModel.
     * 
     * @return the originalTransferModel
     */
    public TransferModelType getOriginalTransferModel() {
        return originalTransferModel;
    }

    /**
     * Setter originalTransferModel.
     * 
     * @param originalTransferModel the originalTransferModel to set
     */
    public void setOriginalTransferModel(TransferModelType originalTransferModel) {
        this.originalTransferModel = originalTransferModel;
    }

    /**
     * Getter calculationFormula.
     * 
     * @return the calculationFormula
     */
    public String getCalculationFormula() {
        return calculationFormula;
    }

    /**
     * Setter calculationFormula.
     * 
     * @param calculationFormula the calculationFormula to set
     */
    public void setCalculationFormula(String calculationFormula) {
        if (null != calculationFormula) {
            // Pattern for finding the contributors. For example, #UR-2001#
            Pattern pattern = Pattern.compile("(#)[a-zA-Z0-9]+(-)[0-9]+(#)");
            Matcher matcher = pattern.matcher(calculationFormula);
            while (matcher.find()) {
                String var = matcher.group();
                String varReplacement = var.replace("-", "");
                varReplacement = varReplacement.replace("#", "");
                calculationFormula = calculationFormula.replace(var, varReplacement);
            }
        }
        this.calculationFormula = calculationFormula;
    }

    /**
     * {@inheritDoc}
     * 
     * @return String.
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder output = new StringBuilder(128);
        output.append("\n######## Contributor Result - ").append(getId()).append(" ########\n");
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
