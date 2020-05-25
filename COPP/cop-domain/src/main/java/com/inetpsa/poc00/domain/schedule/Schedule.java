package com.inetpsa.poc00.domain.schedule;

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
 * The Class Schedule.
 */
@Entity
@Table(name = "COPQTSDL")
public class Schedule extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long entityId;

	/** The arrival date. */
	@Column(name = "ARRIVAL_DATE")
	private Date arrivalDate;

	/** The preparation date. */
	@Column(name = "PREPARATION_DATE")
	private Date preparationDate;

	/** The conditioning date. */
	@Column(name = "CONDITIONING_DATE")
	private Date conditioningDate;

	/** The test begining date. */
	@Column(name = "TEST_BEGINNING_DATE")
	private Date testBeginingDate;

	/** The restitution date. */
	@Column(name = "RESTITUTION_DATE")
	private Date restitutionDate;

	/** The archiving date. */
	@Column(name = "ARCHIVING_DATE")
	private Date archivingDate;

	/** The vehicle file. */
	@OneToOne(mappedBy = "schedule")
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
	 * Gets the arrival date.
	 * 
	 * @return the arrivalDate
	 */
	public Date getArrivalDate() {
		return arrivalDate;
	}

	/**
	 * Sets the arrival date.
	 * 
	 * @param arrivalDate the arrivalDate to set
	 */
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	/**
	 * Gets the preparation date.
	 * 
	 * @return the preparationDate
	 */
	public Date getPreparationDate() {
		return preparationDate;
	}

	/**
	 * Sets the preparation date.
	 * 
	 * @param preparationDate the preparationDate to set
	 */
	public void setPreparationDate(Date preparationDate) {
		this.preparationDate = preparationDate;
	}

	/**
	 * Gets the conditioning date.
	 * 
	 * @return the conditioningDate
	 */
	public Date getConditioningDate() {
		return conditioningDate;
	}

	/**
	 * Sets the conditioning date.
	 * 
	 * @param conditioningDate the conditioningDate to set
	 */
	public void setConditioningDate(Date conditioningDate) {
		this.conditioningDate = conditioningDate;
	}

	/**
	 * Gets the test begining date.
	 * 
	 * @return the testBeginingDate
	 */
	public Date getTestBeginingDate() {
		return testBeginingDate;
	}

	/**
	 * Sets the test begining date.
	 * 
	 * @param testBeginingDate the testBeginingDate to set
	 */
	public void setTestBeginingDate(Date testBeginingDate) {
		this.testBeginingDate = testBeginingDate;
	}

	/**
	 * Gets the restitution date.
	 * 
	 * @return the restitutionDate
	 */
	public Date getRestitutionDate() {
		return restitutionDate;
	}

	/**
	 * Sets the restitution date.
	 * 
	 * @param restitutionDate the restitutionDate to set
	 */
	public void setRestitutionDate(Date restitutionDate) {
		this.restitutionDate = restitutionDate;
	}

	/**
	 * Gets the archiving date.
	 * 
	 * @return the archivingDate
	 */
	public Date getArchivingDate() {
		return archivingDate;
	}

	/**
	 * Sets the archiving date.
	 * 
	 * @param archivingDate the archivingDate to set
	 */
	public void setArchivingDate(Date archivingDate) {
		this.archivingDate = archivingDate;
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
