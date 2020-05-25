package com.inetpsa.poc00.domain.opacityresult;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * The Class OpacityResult.
 */
@Entity
@Table(name = "COPQTOPR")
public class OpacityResult extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long entityId;

    /** The measure1. */
    @Column(name = "MEASURE_1")
    private Long measure1;

    /** The measure2. */
    @Column(name = "MEASURE_2")
    private Long measure2;

    /** The measure3. */
    @Column(name = "MEASURE_3")
    private Long measure3;

    /** The measure4. */
    @Column(name = "MEASURE_4")
    private Long measure4;

    /** The measure avg. */
    @Column(name = "MEASURE_AVERAGE")
    private Long measureAvg;

    /** The opacity decision. */
    @Column(name = "OPACITY_DECISION")
    private String opacityDecision;

    /** The value. */
    @Column(name = "VALUE")
    private String value;

    /** The comment. */
    @Column(name = "COMMENT")
    private String comment;

    /** The creation date. */
    @Column(name = "CREATION_DATE")
    private Date creationDate = new Date();

    /** The updatedate. */
    @Column(name = "UPDATE_DATE")
    private Date updateDate = new Date();

    /** The vehicle file. */
    @OneToOne(mappedBy = "opacityResult")
    private VehicleFile vehicleFile;

    /**
     * Gets the entity id.
     * 
     * @return the entityId
     */
    @Override
    public Long getEntityId() {
        return entityId;
    }

    /**
     * Sets the entity id.
     * 
     * @param entityId the entityId to set
     */
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    /**
     * Gets the measure1.
     * 
     * @return the measure1
     */
    public Long getMeasure1() {
        return measure1;
    }

    /**
     * Sets the measure1.
     * 
     * @param measure1 the measure1 to set
     */
    public void setMeasure1(Long measure1) {
        this.measure1 = measure1;
    }

    /**
     * Gets the measure2.
     * 
     * @return the measure2
     */
    public Long getMeasure2() {
        return measure2;
    }

    /**
     * Sets the measure2.
     * 
     * @param measure2 the measure2 to set
     */
    public void setMeasure2(Long measure2) {
        this.measure2 = measure2;
    }

    /**
     * Gets the measure3.
     * 
     * @return the measure3
     */
    public Long getMeasure3() {
        return measure3;
    }

    /**
     * Sets the measure3.
     * 
     * @param measure3 the measure3 to set
     */
    public void setMeasure3(Long measure3) {
        this.measure3 = measure3;
    }

    /**
     * Gets the measure4.
     * 
     * @return the measure4
     */
    public Long getMeasure4() {
        return measure4;
    }

    /**
     * Sets the measure4.
     * 
     * @param measure4 the measure4 to set
     */
    public void setMeasure4(Long measure4) {
        this.measure4 = measure4;
    }

    /**
     * Gets the measure avg.
     * 
     * @return the measureAvg
     */
    public Long getMeasureAvg() {
        return measureAvg;
    }

    /**
     * Sets the measure avg.
     * 
     * @param measureAvg the measureAvg to set
     */
    public void setMeasureAvg(Long measureAvg) {
        this.measureAvg = measureAvg;
    }

    /**
     * Gets the opacity decision.
     * 
     * @return the opacityDecision
     */
    public String getOpacityDecision() {
        return opacityDecision;
    }

    /**
     * Sets the opacity decision.
     * 
     * @param opacityDecision the opacityDecision to set
     */
    public void setOpacityDecision(String opacityDecision) {
        this.opacityDecision = opacityDecision;
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value.
     * 
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the comment.
     * 
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment.
     * 
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
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
     * Gets the updateDate.
     * 
     * @return the updateDate
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * Gets the vehicle file.
     * 
     * @return the vehicleFile
     */
    public VehicleFile getVehicleFile() {
        return vehicleFile;
    }

    /**
     * Sets the vehicle file.
     * 
     * @param vehicleFile the vehicleFile to set
     */
    public void setVehicleFile(VehicleFile vehicleFile) {
        this.vehicleFile = vehicleFile;
    }
}
