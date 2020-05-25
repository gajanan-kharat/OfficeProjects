/*
 * Creation : Feb 2, 2017
 */
package com.inetpsa.poc00.domain.conditioningresult;

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

import com.inetpsa.poc00.domain.user.User;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * The Class ConditioningResult.
 */
@Entity
@Table(name = "COPQTCDR")
public class ConditioningResult extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long entityId;

	/** The value. */
	@Column(name = "VALUE")
	private String value;

	/** The comment. */
	@Column(name = "COMMENT")
	private String comment;

	/** The creation date. */
	@Column(name = "CREATION_DATE")
	private Date creationDate;

	/** The upadation date. */
	@Column(name = "UPDATION_DATE")
	private Date upadationDate;

	/** The vehicle file. */
	@ManyToOne
	@JoinColumn(name = "VEHICLE_FILE_ID")
	private VehicleFile vehicleFile;

	/** The user. */
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	/**
	 * Instantiates a new conditioning result.
	 */
	public ConditioningResult() {
		super();
	}

	/**
	 * Modify the last Update Date for all existing Row while updating.
	 */
	@PreUpdate
	public void setLastUpdate() {
		this.upadationDate = new Date();
	}

	/**
	 * Getter entityId
	 * 
	 * @return the entityId
	 */
	@Override
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Setter entityId
	 * 
	 * @param entityId the entityId to set
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Getter value
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Setter value
	 * 
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Getter comment
	 * 
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Setter comment
	 * 
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Getter creationDate
	 * 
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Setter creationDate
	 * 
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Getter upadationDate
	 * 
	 * @return the upadationDate
	 */
	public Date getUpadationDate() {
		return upadationDate;
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

	/**
	 * Getter vehicleFile
	 * 
	 * @return the vehicleFile
	 */
	public VehicleFile getVehicleFile() {
		return vehicleFile;
	}

	/**
	 * Setter vehicleFile
	 * 
	 * @param vehicleFile the vehicleFile to set
	 */
	public void setVehicleFile(VehicleFile vehicleFile) {
		this.vehicleFile = vehicleFile;
	}

}
