/*
 * Author: U224106
 * Creation: 2 déc. 2014
 */
package com.inetpsa.oaz00.ws.scilab.model;

import java.lang.reflect.Field;
import java.util.List;

import com.inetpsa.xml.services.oasisplm.interfaceoasis.RequirementType;

/**
 * The Class SciMotherRequirementType.
 * 
 * @author U224106
 */
public class SciMotherRequirementType extends SciRequirementType {

    /** The transfer model. */
    private SciTransferModelType transferModel;

    /** The contributor list. */
    private List<SciRequirementType> contributorList;

    /** The contributor list as of the creation. */
    private List<SciRequirementType> originalContributorList;

    /** The plot path prefix. */
    private String plotPathPrefix;

    /** The plot path. */
    private String plotPath;

    /**
     * Instantiates a new Scilab Mother requirement type.
     * 
     * @param pRequirement the requirement
     * @param pTransferModel the transfer model
     * @param pContributorList the contributor list
     * @throws SecurityException the security exception
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IllegalAccessException the illegal access exception
     */
    public SciMotherRequirementType(RequirementType pRequirement, SciTransferModelType pTransferModel, List<SciRequirementType> pContributorList)
            throws SecurityException, IllegalArgumentException, IllegalAccessException {
        super(pRequirement);
        this.transferModel = pTransferModel;
        this.contributorList = pContributorList;
        this.originalContributorList = pContributorList;
        this.plotPathPrefix = "";
        this.plotPath = "";
        this.tncObjective = this.tnc;
        this.capObjective = this.cap;
        this.centeringMaxObjective = this.centeringMax;
    }

    /**
     * Get transferModels.
     * 
     * @return the transferModel
     */
    public SciTransferModelType getTransferModel() {
        return transferModel;
    }

    /**
     * Set transferModel.
     * 
     * @param transferModel the transferModel to set
     */
    public void setTransferModel(SciTransferModelType transferModel) {
        this.transferModel = transferModel;
    }

    /**
     * Get contributorList.
     * 
     * @return the contributorList
     */
    public List<SciRequirementType> getContributorList() {
        return contributorList;
    }

    /**
     * Set contributorList.
     * 
     * @param contributorList the contributorList to set
     */
    public void setContributorList(List<SciRequirementType> contributorList) {
        this.contributorList = contributorList;
    }

    /**
     * Gets the tnc objective.
     * 
     * @return the tnc objective
     */
    public Double getTncObjective() {
        return tncObjective;
    }

    /**
     * Sets the tnc objective.
     * 
     * @param tncObjective the new tnc objective
     */
    public void setTncObjective(Double tncObjective) {
        this.tncObjective = tncObjective;
    }

    /**
     * Gets the cap objective.
     * 
     * @return the cap objective
     */
    public Double getCapObjective() {
        return capObjective;
    }

    /**
     * Sets the cap objective.
     * 
     * @param capObjective the new cap objective
     */
    public void setCapObjective(Double capObjective) {
        this.capObjective = capObjective;
    }

    /**
     * Gets the centering max objective.
     * 
     * @return the centering max objective
     */
    public Double getCenteringMaxObjective() {
        return centeringMaxObjective;
    }

    /**
     * Sets the centering max objective.
     * 
     * @param centeringMaxObjective the new centering max objective
     */
    public void setCenteringMaxObjective(Double centeringMaxObjective) {
        this.centeringMaxObjective = centeringMaxObjective;
    }

    /**
     * Get plotPathPrefix.
     * 
     * @return the plotPathPrefix
     */
    public String getPlotPathPrefix() {
        return plotPathPrefix;
    }

    /**
     * Set plotPathPrefix.
     * 
     * @param plotPathPrefix the plotPathPrefix to set
     */
    public void setPlotPathPrefix(String plotPathPrefix) {
        this.plotPathPrefix = plotPathPrefix;
    }

    /**
     * Get plotPath.
     * 
     * @return the plotPath
     */
    public String getPlotPath() {
        return plotPath;
    }

    /**
     * Set plotPath.
     * 
     * @param plotPath the plotPath to set
     */
    public void setPlotPath(String plotPath) {
        this.plotPath = plotPath;
    }

    /**
     * Method to recursively calculate the height of a multi-level tree.
     * 
     * @return height
     */
    public int getDepth() {
        // if not leaf, get the height of children recursively.
        int maxDepth = 0;
        for (SciRequirementType child : this.getContributorList()) {
            if (child instanceof SciMotherRequirementType)
                maxDepth = Math.max(maxDepth, ((SciMotherRequirementType) child).getDepth());
        }
        return maxDepth + 1;
    }

    public List<SciRequirementType> getContributorList(boolean isMonteCarloReport) {
        if (isMonteCarloReport)
            return originalContributorList;
        return contributorList;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.scilab.model.SciRequirementType#toString()
     */
    public String toString() {
        StringBuilder output = new StringBuilder(128);
        output.append("######## Mother Req Result ########\n");
        try {
            Field[] fields = this.getClass().getSuperclass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(this) != null)
                    output.append("<").append(field.getName()).append(">").append(field.get(this)).append("</").append(field.getName()).append(">\n");
            }
            fields = this.getClass().getSuperclass().getSuperclass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(this) != null)
                    output.append("<").append(field.getName()).append(">").append(field.get(this)).append("</").append(field.getName()).append(">\n");
            }
            fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(this) != null)
                    output.append("<").append(field.getType()).append(">").append(field.get(this).toString()).append("</").append(field.getType())
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
