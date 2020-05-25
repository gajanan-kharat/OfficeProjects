package com.inetpsa.poc00.rest.technicalcase;

import java.util.Date;

import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupRepresentation;
import com.inetpsa.poc00.rest.tvv.TvvRepresentation;

/**
 * The Class TechnicalCaseRepresentation.
 */
public class TechnicalCaseRepresentation {

    /** The entity id. */
    protected Long entityId;

    /** The tvv worst case. */
    private boolean tvvWorstCase;

    /** The tvv. */
    private TvvRepresentation tvv;

    /** The emission standard. */
    private EmissionStandardRepresentation emissionStandard;

    /** The technical group. */
    private TechnicalGroupRepresentation technicalGroup;

    /** The creation date. */
    private Date creationDate;

    /** The modification date. */
    private Date modificationDate;

    /**
     * Instantiates a new technical case representation.
     */
    public TechnicalCaseRepresentation() {
        super();
    }

    /**
     * Instantiates a new technical case representation.
     * 
     * @param entityId the entity id
     * @return the entityId
     */

    public TechnicalCaseRepresentation(Long entityId) {
        this.entityId = entityId;
    }

    /**
     * Instantiates a new technical case representation.
     * 
     * @param entityId the entity id
     * @param tvvWorstCase the tvv worst case
     * @param tvv the tvv
     * @param emissionStandard the emission standard
     * @param technicalGroup the technical group
     */
    public TechnicalCaseRepresentation(Long entityId, boolean tvvWorstCase, TvvRepresentation tvv, EmissionStandardRepresentation emissionStandard,
            TechnicalGroupRepresentation technicalGroup) {
        super();
        this.entityId = entityId;
        this.tvvWorstCase = tvvWorstCase;
        this.tvv = tvv;
        this.emissionStandard = emissionStandard;
        this.technicalGroup = technicalGroup;
    }

    /**
     * Gets the entity id.
     * 
     * @return the entity id
     */
    public Long getEntityId() {
        return entityId;
    }

    /**
     * Sets the entity id.
     * 
     * @param entityId the new entity id
     */
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    /**
     * Checks if is tvv worst case.
     * 
     * @return the tvvWorstCase
     */
    public boolean isTvvWorstCase() {
        return tvvWorstCase;
    }

    /**
     * Sets the tvv worst case.
     * 
     * @param tvvWorstCase the tvvWorstCase to set
     */
    public void setTvvWorstCase(boolean tvvWorstCase) {
        this.tvvWorstCase = tvvWorstCase;
    }

    /**
     * Gets the tvv.
     * 
     * @return the tvv
     */
    public TvvRepresentation getTvv() {
        return tvv;
    }

    /**
     * Sets the tvv.
     * 
     * @param tvv the tvv to set
     */
    public void setTvv(TvvRepresentation tvv) {
        this.tvv = tvv;
    }

    /**
     * Gets the emission standard.
     * 
     * @return the emissionStandard
     */
    public EmissionStandardRepresentation getEmissionStandard() {
        return emissionStandard;
    }

    /**
     * Sets the emission standard.
     * 
     * @param emissionStandard the emissionStandard to set
     */
    public void setEmissionStandard(EmissionStandardRepresentation emissionStandard) {
        this.emissionStandard = emissionStandard;
    }

    /**
     * Gets the technical group.
     * 
     * @return the technicalGroup
     */
    public TechnicalGroupRepresentation getTechnicalGroup() {
        return technicalGroup;
    }

    /**
     * Sets the technical group.
     * 
     * @param technicalGroup the technicalGroup to set
     */
    public void setTechnicalGroup(TechnicalGroupRepresentation technicalGroup) {
        this.technicalGroup = technicalGroup;
    }

    /**
     * Gets the creation date.
     * 
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date.
     * 
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets the modification date.
     * 
     * @return the modificationDate
     */
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
     * Sets the modification date.
     * 
     * @param modificationDate the modificationDate to set
     */
    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

}
