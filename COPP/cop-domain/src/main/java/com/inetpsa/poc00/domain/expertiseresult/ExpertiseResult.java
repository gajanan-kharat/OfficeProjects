/*
 * Creation : Feb 2, 2017
 */
package com.inetpsa.poc00.domain.expertiseresult;

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
 * The Class ExpertiseResult.
 */
@Entity
@Table(name = "COPQTEPR")
public class ExpertiseResult extends BaseAggregateRoot<Long> {

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
	private Date creationDate = new Date();

	/** The update date. */
	@Column(name = "UPDATION_DATE")
	private Date updateDate = new Date();

	/** The vehicle file. */
	@ManyToOne
	@JoinColumn(name = "VEHICLE_FILE_ID")
	private VehicleFile vehicleFileExp;

	/** The user expr. */
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	/**
	 * Modify the last Update Date for all existing Row while updating.
	 */
	@PreUpdate
	public void setLastUpdate() {
		this.updateDate = new Date();
	}

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
	 * Getter comment.
	 *
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Setter comment.
	 *
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
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
	 * Getter updateDate.
	 *
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * Setter updateDate.
	 *
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * Getter vehicleFileExp.
	 *
	 * @return the vehicleFileExp
	 */
	public VehicleFile getVehicleFileExp() {
		return vehicleFileExp;
	}

	/**
	 * Setter vehicleFileExp.
	 *
	 * @param vehicleFileExp the vehicleFileExp to set
	 */
	public void setVehicleFileExp(VehicleFile vehicleFileExp) {
		this.vehicleFileExp = vehicleFileExp;
	}

	/**
	 * Getter user.
	 *
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Setter user.
	 *
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
