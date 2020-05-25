/*
 * Creation : Dec 22, 2016
 */
package com.inetpsa.poc00.rest.resultset;

import java.util.List;

import com.inetpsa.poc00.rest.vehiclefile.VehicleFileRepresentation;

public class PollGasResultSetListRepresentation {
	/**
	 * The PollGasResultSetRepresentation List
	 */
	private List<PollGasResultSetRepresentation> listResultSet;
	/*
	 * VehicleFileRepresentation
	 */
	private VehicleFileRepresentation vehicleFileRepresentation;

	/**
	 * Getter listResultSet
	 * 
	 * @return the listResultSet
	 */
	public List<PollGasResultSetRepresentation> getListResultSet() {
		return listResultSet;
	}

	/**
	 * Setter listResultSet
	 * 
	 * @param listResultSet the listResultSet to set
	 */
	public void setListResultSet(List<PollGasResultSetRepresentation> listResultSet) {
		this.listResultSet = listResultSet;
	}

	/**
	 * Getter vehicleFileRepresentation
	 * 
	 * @return the vehicleFileRepresentation
	 */
	public VehicleFileRepresentation getVehicleFileRepresentation() {
		return vehicleFileRepresentation;
	}

	/**
	 * Setter vehicleFileRepresentation
	 * 
	 * @param vehicleFileRepresentation the vehicleFileRepresentation to set
	 */
	public void setVehicleFileRepresentation(VehicleFileRepresentation vehicleFileRepresentation) {
		this.vehicleFileRepresentation = vehicleFileRepresentation;
	}
}
