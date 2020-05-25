/*
 * Creation : Aug 22, 2016
 */
package com.inetpsa.poc00.rest.vehicle;

import java.util.List;

import com.inetpsa.poc00.rest.country.CountryRepresentation;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.fuel.FuelRepresentation;
import com.inetpsa.poc00.rest.vehiclefilestatus.VehicleFileStatusRepresentation;

/**
 * The Class VehicleSearchRepresentation.
 */
public class VehicleModelRepresentation {

	/** The counter mark. */
	private String counterMark;

	/** The registration number. */

	private String registrationNumber;

	/** The chases number. */

	private String chasisNumber;

	/** The project code family list. */
	private List<String> projectCodeFamilyList;

	/** The model year. */
	private List<String> modelYear;

	/** The status list. */
	private List<VehicleFileStatusRepresentation> statusList;

	/** The user id. */
	private String userId;
	/** The body work list. */
	private List<String> bodyWorkList;
	/** The ems list. */
	private List<EmissionStandardRepresentation> emsList;
	/** The ems list. */
	private List<String> carBrandList;
	/** The engine list. */
	private List<String> engineList;

	/** The gear box list. */
	private List<String> gearBoxList;
	/** The fuel list. */
	private List<FuelRepresentation> fuelList;
	/** The fuel type list. */
	private List<String> fuelTypeList;
	/** The country list. */
	private List<CountryRepresentation> countryList;

	/**
	 * Gets the fuel list.
	 * 
	 * @return the fuel list
	 */
	public List<FuelRepresentation> getFuelList() {
		return fuelList;
	}

	/**
	 * Sets the fuel list.
	 * 
	 * @param fuelList the new fuel list
	 */
	public void setFuelList(List<FuelRepresentation> fuelList) {
		this.fuelList = fuelList;
	}

	/**
	 * Gets the ems list.
	 * 
	 * @return the ems list
	 */
	public List<EmissionStandardRepresentation> getEmsList() {
		return emsList;
	}

	/**
	 * Sets the ems list.
	 * 
	 * @param emsList the new ems list
	 */
	public void setEmsList(List<EmissionStandardRepresentation> emsList) {
		this.emsList = emsList;
	}

	/**
	 * Gets the project code family list.
	 * 
	 * @return the project code family list
	 */
	public List<String> getProjectCodeFamilyList() {
		return projectCodeFamilyList;
	}

	/**
	 * Sets the project code family list.
	 * 
	 * @param projectCodeFamilyList the new project code family list
	 */
	public void setProjectCodeFamilyList(List<String> projectCodeFamilyList) {
		this.projectCodeFamilyList = projectCodeFamilyList;
	}

	/**
	 * Gets the model year.
	 * 
	 * @return the model year
	 */
	public List<String> getModelYear() {
		return modelYear;
	}

	/**
	 * Sets the model year.
	 * 
	 * @param modelYear the new model year
	 */
	public void setModelYear(List<String> modelYear) {
		this.modelYear = modelYear;
	}

	/**
	 * Gets the status list.
	 * 
	 * @return the status list
	 */
	public List<VehicleFileStatusRepresentation> getStatusList() {
		return statusList;
	}

	/**
	 * Sets the status list.
	 * 
	 * @param statusList the new status list
	 */
	public void setStatusList(List<VehicleFileStatusRepresentation> statusList) {
		this.statusList = statusList;
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
	 * Gets the counter mark.
	 * 
	 * @return the counter mark
	 */
	public String getCounterMark() {
		return counterMark;
	}

	/**
	 * Sets the counter mark.
	 * 
	 * @param counterMark the new counter mark
	 */
	public void setCounterMark(String counterMark) {
		this.counterMark = counterMark;
	}

	/**
	 * Gets the registration number.
	 * 
	 * @return the registration number
	 */
	public String getRegistrationNumber() {
		return registrationNumber;
	}

	/**
	 * Sets the registration number.
	 * 
	 * @param registrationNumber the new registration number
	 */
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	/**
	 * Gets the chasis number.
	 * 
	 * @return the chasis number
	 */
	public String getChasisNumber() {
		return chasisNumber;
	}

	/**
	 * Sets the chasis number.
	 * 
	 * @param chasisNumber the new chasis number
	 */
	public void setChasisNumber(String chasisNumber) {
		this.chasisNumber = chasisNumber;
	}

	/**
	 * Getter countryList.
	 *
	 * @return the countryList
	 */
	public List<CountryRepresentation> getCountryList() {
		return countryList;
	}

	/**
	 * Setter countryList.
	 *
	 * @param countryList the countryList to set
	 */
	public void setCountryList(List<CountryRepresentation> countryList) {
		this.countryList = countryList;
	}

	/**
	 * Gets the body work list.
	 *
	 * @return the body work list
	 */
	public List<String> getBodyWorkList() {
		return bodyWorkList;
	}

	/**
	 * Sets the body work list.
	 *
	 * @param bodyWorkList the new body work list
	 */
	public void setBodyWorkList(List<String> bodyWorkList) {
		this.bodyWorkList = bodyWorkList;
	}

	/**
	 * Gets the car brand list.
	 *
	 * @return the car brand list
	 */
	public List<String> getCarBrandList() {
		return carBrandList;
	}

	/**
	 * Sets the car brand list.
	 *
	 * @param carBrandList the new car brand list
	 */
	public void setCarBrandList(List<String> carBrandList) {
		this.carBrandList = carBrandList;
	}

	/**
	 * Gets the engine list.
	 *
	 * @return the engine list
	 */
	public List<String> getEngineList() {
		return engineList;
	}

	/**
	 * Sets the engine list.
	 *
	 * @param engineList the new engine list
	 */
	public void setEngineList(List<String> engineList) {
		this.engineList = engineList;
	}

	/**
	 * Gets the gear box list.
	 *
	 * @return the gear box list
	 */
	public List<String> getGearBoxList() {
		return gearBoxList;
	}

	/**
	 * Sets the gear box list.
	 *
	 * @param gearBoxList the new gear box list
	 */
	public void setGearBoxList(List<String> gearBoxList) {
		this.gearBoxList = gearBoxList;
	}

	/**
	 * Gets the fuel type list.
	 *
	 * @return the fuel type list
	 */
	public List<String> getFuelTypeList() {
		return fuelTypeList;
	}

	/**
	 * Sets the fuel type list.
	 *
	 * @param fuelTypeList the new fuel type list
	 */
	public void setFuelTypeList(List<String> fuelTypeList) {
		this.fuelTypeList = fuelTypeList;
	}

}
