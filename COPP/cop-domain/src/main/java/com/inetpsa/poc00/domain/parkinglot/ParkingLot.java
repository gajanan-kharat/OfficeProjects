package com.inetpsa.poc00.domain.parkinglot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.vehicle.Vehicle;

/**
 * The Class ParkingLot.
 */
@Entity
@Table(name = "COPQTPKL")
public class ParkingLot extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long entityId;

	/** The parking number. */
	@Column(name = "PARKING_NUMBER")
	private String parkingNumber;

	/** The vehicle. */
	@OneToOne(mappedBy = "parkingLot")
	private Vehicle vehicle;

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
	 * Gets the parking number.
	 * 
	 * @return the parkingNumber
	 */
	public String getParkingNumber() {
		return parkingNumber;
	}

	/**
	 * Sets the parking number.
	 * 
	 * @param parkingNumber the parkingNumber to set
	 */
	public void setParkingNumber(String parkingNumber) {
		this.parkingNumber = parkingNumber;
	}

	/**
	 * Gets the vehicle.
	 * 
	 * @return the vehicle
	 */
	public Vehicle getVehicle() {
		return vehicle;
	}

	/**
	 * Sets the vehicle.
	 * 
	 * @param vehicle the vehicle to set
	 */
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
}
