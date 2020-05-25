/*
 * Creation : Feb 2, 2017
 */
package com.inetpsa.poc00.domain.specialtestcondition;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.unit.Unit;
import com.inetpsa.poc00.domain.user.User;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * The Class SpecialTestCondition.
 */
@Entity
@Table(name = "COPQTSTC")
public class SpecialTestCondition extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	protected Long entityId;

	/** The vehicle file. */
	@ManyToOne
	@JoinColumn(name = "VEHICLE_FILE_ID")
	private VehicleFile vehicleFile;

	/** The unit. */
	@ManyToOne
	@JoinColumn(name = "UNIT_ID")
	private Unit unit;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The value. */
	@Column(name = "VALUE")
	private String value;

	/** The creation date. */
	@Column(name = "CREATION_DATE")
	private Date creationDate;

	/** The user. */
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	/**
	 * Getter entityId.
	 *
	 * @return the entityId
	 */
	@Override
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Setter entityId.
	 *
	 * @param entityId the entityId to set
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Getter vehicleFile.
	 *
	 * @return the vehicleFile
	 */
	public VehicleFile getVehicleFile() {
		return vehicleFile;
	}

	/**
	 * Setter vehicleFile.
	 *
	 * @param vehicleFile the vehicleFile to set
	 */
	public void setVehicleFile(VehicleFile vehicleFile) {
		this.vehicleFile = vehicleFile;
	}

	/**
	 * Getter unit.
	 *
	 * @return the unit
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * Setter unit.
	 *
	 * @param unit the unit to set
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * Getter label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Setter label.
	 *
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Getter value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Setter value.
	 *
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Getter creationDate.
	 *
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Setter creationDate.
	 *
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Getter user
	 * 
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Setter user
	 * 
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
