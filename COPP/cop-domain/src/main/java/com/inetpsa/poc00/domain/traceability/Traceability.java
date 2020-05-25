/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.traceability;

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

import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTTRA")
public class Traceability extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@Column(name = "TRACEABILITY_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long entityId;

	/** The user id. */
	@Column(name = "USER_ID")
	private String userId;

	/** The user profile. */
	@Column(name = "USER_PROFILE")
	private String userProfile;

	/** The description. */
	@Column(name = "DESCRIPTION")
	private String description;

	/** The new value. */
	@Column(name = "NEW_VALUE", length = 4000)
	private String newValue;

	/** The old value. */
	@Column(name = "OLD_VALUE", length = 4000)
	private String oldValue;

	/** The screen id. */
	@Column(name = "SCREEN_ID")
	private String screenId;

	/** The id. */
	@Column(name = "OBJECT_ID")
	private Long objectId;

	/** The vehicle history. */
	@ManyToOne
	@JoinColumn(name = "VEHICLE_FILE_ID")
	private VehicleFile vehicleFileHistory;

	/** The creation date. */
	@Column(name = "CREATION_DATE")
	private Date creationDate = new Date();

	/** The update date. */
	@Column(name = "UPDATE_DATE")
	private Date updationDate = new Date();

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public Traceability() {
		super();
	}

	/**
	 * Instantiates a new traceability.
	 * 
	 * @param userId the user id
	 * @param userProfile the user profile
	 * @param description the description
	 * @param newValue the new value
	 * @param oldvalue the oldvalue
	 * @param updationDate the updation date
	 */
	public Traceability(String userId, String userProfile, String description, String newValue, String oldvalue, Date updationDate) {
		this.userId = userId;
		this.userProfile = userProfile;
		this.description = description;
		this.newValue = newValue;
		this.oldValue = oldvalue;
		this.updationDate = updationDate;
	}

	/**
	 * Modify the last Update Date for all existing Row while updating.
	 */
	@PreUpdate
	public void setLastUpdate() {
		this.updationDate = new Date();
	}

	/* (non-Javadoc)
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
	 * Gets the user id.
	 * 
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 * 
	 * @param userId the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the user profile.
	 * 
	 * @return the user profile
	 */
	public String getUserProfile() {
		return userProfile;
	}

	/**
	 * Sets the user profile.
	 * 
	 * @param userProfile the new user profile
	 */
	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the new value.
	 * 
	 * @return the new value
	 */
	public String getNewValue() {
		return newValue;
	}

	/**
	 * Sets the new value.
	 * 
	 * @param newValue the new new value
	 */
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	/**
	 * Gets the old value.
	 * 
	 * @return the old value
	 */
	public String getOldValue() {
		return oldValue;
	}

	/**
	 * Sets the old value.
	 * 
	 * @param oldValue the new old value
	 */
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
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
	 * Gets the update date.
	 * 
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updationDate;
	}

	/**
	 * Gets the screen id.
	 * 
	 * @return the screenId
	 */
	public String getScreenId() {
		return screenId;
	}

	/**
	 * Sets the screen id.
	 * 
	 * @param screenId the screenId to set
	 */
	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

	/**
	 * @return the vehicleFileHistory
	 */
	public VehicleFile getVehicleFileHistory() {
		return vehicleFileHistory;
	}

	/**
	 * @param vehicleFileHistory the vehicleFileHistory to set
	 */
	public void setVehicleFileHistory(VehicleFile vehicleFileHistory) {
		this.vehicleFileHistory = vehicleFileHistory;
	}

	/**
	 * Getter objectId
	 * 
	 * @return the objectId
	 */
	public Long getObjectId() {
		return objectId;
	}

	/**
	 * Setter objectId
	 * 
	 * @param objectId the objectId to set
	 */
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	/**
	 * Getter updationDate
	 * 
	 * @return the updationDate
	 */
	public Date getUpdationDate() {
		return updationDate;
	}

}
