package com.inetpsa.poc00.domain.genomelcdvcodevalue;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.genomelcdvcode.GenomeLCDVCode;

/**
 * Entity Class : GenomeLCDVCodeValue table Name : COPQTGCV.
 */
@Entity
@Table(name = "COPQTGCV")
public class GenomeLCDVCodeValue extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LCDV_CODE_VALUE_ID")
    private Long entityId;

    /** The genome lcdv code. */
    @ManyToOne
    @JoinColumn(name = "LCDV_CODE_ID")
    private GenomeLCDVCode genomeLcdvCode;

    /** The fr label. */
    @Column(name = "FR_LABEL")
    private String frLabel;

    /** The z2 label. */
    @Column(name = "Z2_LABEL")
    private String z2Label;

    /** The lcdv code value. */
    @Column(name = "LCDV_CODE_VALUE")
    private String lcdvCodeValue;

    /** The update date. */
    @Column(name = "UPDATE_DATE")
    private Date updateDate = new Date();

    /** The creation date. */
    @Column(name = "CREATION_DATE")
    private Date creationDate = new Date();

    /**
     * Modify the last Update Date for all existing Row while updating.
     */

    @PreUpdate
    public void setLastUpdate() {
        this.updateDate = new Date();
    }

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public GenomeLCDVCodeValue() {
    	super();
    }

    /**
     * Instantiates a new genome lcdv code value.
     * 
     * @param frLabel the fr label
     * @param z2Label the z2 label
     * @param lcdvCodeValue the lcdv code value
     * @param entityId the entity id
     * @param genomLcdvCode the genom lcdv code
     */
    public GenomeLCDVCodeValue(String frLabel, String z2Label, String lcdvCodeValue, Long entityId, GenomeLCDVCode genomLcdvCode) {

        this.entityId = entityId;
        this.frLabel = frLabel;
        this.z2Label = z2Label;
        this.lcdvCodeValue = lcdvCodeValue;
        this.genomeLcdvCode = genomLcdvCode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.domain.BaseEntity#getEntityId()
     */
    @Override
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
     * Gets the fr label.
     * 
     * @return the fr label
     */
    public String getFrLabel() {
        return frLabel;
    }

    /**
     * Sets the fr label.
     * 
     * @param frLabel the new fr label
     */
    public void setFrLabel(String frLabel) {
        this.frLabel = frLabel;
    }

    /**
     * Gets the z2 label.
     * 
     * @return the z2 label
     */
    public String getZ2Label() {
        return z2Label;
    }

    /**
     * Sets the z2 label.
     * 
     * @param z2Label the new z2 label
     */
    public void setZ2Label(String z2Label) {
        this.z2Label = z2Label;
    }

    /**
     * Gets the lcdv code value.
     * 
     * @return the lcdv code value
     */
    public String getLcdvCodeValue() {
        return lcdvCodeValue;
    }

    /**
     * Sets the lcdv code value.
     * 
     * @param lcdvcodeValue the new lcdv code value
     */
    public void setLcdvCodeValue(String lcdvcodeValue) {
        this.lcdvCodeValue = lcdvcodeValue;
    }

    /**
     * Gets the update date.
     * 
     * @return the update date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * Gets the creation date.
     * 
     * @return the creation date
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Gets the genome lcdv code.
     * 
     * @return the genome lcdv code
     */
    public GenomeLCDVCode getGenomeLcdvCode() {
        return genomeLcdvCode;
    }

    /**
     * Sets the genome lcdv code.
     * 
     * @param genomeLcdvCode the new genome lcdv code
     */
    public void setGenomeLcdvCode(GenomeLCDVCode genomeLcdvCode) {
        this.genomeLcdvCode = genomeLcdvCode;
    }

}
