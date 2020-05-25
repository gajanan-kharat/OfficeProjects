package com.inetpsa.poc00.rest.basket;

import java.util.ArrayList;
import java.util.List;

import com.inetpsa.poc00.rest.vehicle.VehicleRepresentation;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileRepresentation;

/**
 * The Class BasketRepresentation.
 */
public class BasketRepresentation {

	/** The entity id. */
	private Long entityId;

	/** The user. */
	private String userId;

	/** The vehicle id. */
	private Long vehicleId;

	/** The vehicles. */
	private List<VehicleRepresentation> vehicles = new ArrayList<>();

	/** The vehicle file rep list. */
	private List<VehicleFileRepresentation> vehicleFileRepList;

	/** The vehicle count. */
	private Long vehicleCount;

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
	 * Gets the vehicles.
	 * 
	 * @return the vehicles
	 */
	public List<VehicleRepresentation> getVehicles() {
		return vehicles;
	}

	/**
	 * Sets the vehicles.
	 * 
	 * @param vehicles the new vehicles
	 */
	public void setVehicles(List<VehicleRepresentation> vehicles) {
		this.vehicles = vehicles;
	}

	/**
	 * Gets the vehicle count.
	 * 
	 * @return the vehicle count
	 */
	public Long getVehicleCount() {
		return vehicleCount;
	}

	/**
	 * Sets the vehicle count.
	 * 
	 * @param count the new vehicle count
	 */
	public void setVehicleCount(Long count) {
		this.vehicleCount = count;
	}

	/**
	 * Gets the vehicle id.
	 * 
	 * @return the vehicle id
	 */
	public Long getVehicleId() {
		return vehicleId;
	}

	/**
	 * Sets the vehicle id.
	 * 
	 * @param vehicleId the new vehicle id
	 */
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	/**
	 * Gets the vehicle file rep list.
	 * 
	 * @return the vehicle file rep list
	 */
	public List<VehicleFileRepresentation> getVehicleFileRepList() {
		return vehicleFileRepList;
	}

	/**
	 * Sets the vehicle file rep list.
	 * 
	 * @param vehicleFileRepList the new vehicle file rep list
	 */
	public void setVehicleFileRepList(List<VehicleFileRepresentation> vehicleFileRepList) {
		this.vehicleFileRepList = vehicleFileRepList;
	}

}
