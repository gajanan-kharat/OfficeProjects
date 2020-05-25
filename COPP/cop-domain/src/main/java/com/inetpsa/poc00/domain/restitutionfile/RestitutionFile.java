package com.inetpsa.poc00.domain.restitutionfile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.user.User;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * The Class RestitutionFile.
 */
@Entity
@Table(name = "COPQTRTF")
public class RestitutionFile extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long entityId;

	/** The user. */
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User userRst;

	/** The observation. */
	@Column(name = "OBSERVATION")
	private String observation;

	/** The reservation. */
	@Column(name = "RESERVATION")
	private String reservation;

	/** The requester. */
	@Column(name = "REQUESTER")
	private String requester;

	/** The vehicle file. */
	@OneToOne(mappedBy = "restitutionFile")
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
	 * Gets the observation.
	 * 
	 * @return the observation
	 */
	public String getObservation() {
		return observation;
	}

	/**
	 * Sets the observation.
	 * 
	 * @param observation the observation to set
	 */
	public void setObservation(String observation) {
		this.observation = observation;
	}

	/**
	 * Gets the reservation.
	 * 
	 * @return the reservation
	 */
	public String getReservation() {
		return reservation;
	}

	/**
	 * Sets the reservation.
	 * 
	 * @param reservation the reservation to set
	 */
	public void setReservation(String reservation) {
		this.reservation = reservation;
	}

	/**
	 * Gets the requester.
	 * 
	 * @return the requester
	 */
	public String getRequester() {
		return requester;
	}

	/**
	 * Sets the requester.
	 * 
	 * @param requester the requester to set
	 */
	public void setRequester(String requester) {
		this.requester = requester;
	}

	/**
	 * @return the vehicleFile
	 */
	public VehicleFile getVehicleFile() {
		return vehicleFile;
	}

	/**
	 * @param vehicleFile the vehicleFile to set
	 */
	public void setVehicleFile(VehicleFile vehicleFile) {
		this.vehicleFile = vehicleFile;
	}

	/**
	 * @return the userRst
	 */
	public User getUserRst() {
		return userRst;
	}

	/**
	 * @param userRst the userRst to set
	 */
	public void setUserRst(User userRst) {
		this.userRst = userRst;
	}
}
