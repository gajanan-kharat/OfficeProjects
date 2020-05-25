package com.inetpsa.poc00.rest.preparationfile;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.inetpsa.poc00.rest.pfstructure.PreparationFileStructureRepresentation;
import com.inetpsa.poc00.rest.preparationchecklist.PreparationCheckListRepresentation;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileRepresentation;

/**
 * The Class PreparationFileRepresentation.
 */
public class PreparationFileRepresentation {

	/** The entity id. */
	private Long entityId;

	/** The creation date. */
	private Date creationDate;

	/** The modification date. */
	private Date modificationDate;

	/** The type of test. */
	private String typeOfTest;

	/** The vehicle file status. */
	private String vehicleFileStatus;

	/** The vehicle file status gui. */
	private String vehicleFileStatusGui;

	/** The counter mark. */
	private String counterMark;

	/** The registration number. */
	private String registrationNumber;

	/** The chasis number. */
	private String chasisNumber;

	/** The vin. */
	private String vin;

	/** The project code family label. */
	private String projectCodeFamilyLabel;

	/** The body work label. */
	private String bodyWorkLabel;

	/** The car brand maker. */
	private Set<String> carBrandMaker = new HashSet<>();

	/** The model year. */
	private String modelYear;

	/** The es label. */
	private String esLabel;

	/** The engine label. */
	private String engineLabel;

	private String powerCV;
	private String powerKW;
	private String torque;
	private String fuelInjectionType;
	/** The gear box label. */
	private String gearBoxLabel;

	/** The fuel type label. */
	private String fuelTypeLabel;

	/** The fuel label. */
	private String fuelLabel;

	/** The country label. */
	private String countryLabel;

	/** The co decision higher lim. */
	private double coDecisionHigherLim;

	/** The co decision lower lim. */
	private double coDecisionLowerLim;

	/** The lambda decision lower lim. */
	private double lambdaDecisionLowerLim;

	/** The lambda decision higher lim. */
	private double lambdaDecisionHigherLim;

	/** The opac dec higher lim. */
	private double opacDecHigherLim;

	/** The opac dec lower lim. */
	private double opacDecLowerLim;

	/** The prep check list representation. */
	private List<PreparationCheckListRepresentation> prepCheckListRepresentation;

	/** The prep file structure. */
	private PreparationFileStructureRepresentation prepFileStructRepresentation;

	/** The vehicle file representation. */
	private VehicleFileRepresentation vehicleFileRepresentation;

	/**
	 * Instantiates a new preparation file representation.
	 */
	public PreparationFileRepresentation() {
		super();
	}

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
	 * Gets the creation date.
	 * 
	 * @return the creation date
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 * 
	 * @param creationDate the new creation date
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Gets the modification date.
	 * 
	 * @return the modification date
	 */
	public Date getModificationDate() {
		return modificationDate;
	}

	/**
	 * Sets the modification date.
	 * 
	 * @param modificationDate the new modification date
	 */
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	/**
	 * Gets the prep check list representation.
	 * 
	 * @return the prep check list representation
	 */
	public List<PreparationCheckListRepresentation> getPrepCheckListRepresentation() {
		return prepCheckListRepresentation;
	}

	/**
	 * Sets the prep check list representation.
	 * 
	 * @param prepCheckListRepresentation the new prep check list representation
	 */
	public void setPrepCheckListRepresentation(List<PreparationCheckListRepresentation> prepCheckListRepresentation) {
		this.prepCheckListRepresentation = prepCheckListRepresentation;
	}

	/**
	 * Gets the type of test.
	 * 
	 * @return the type of test
	 */
	public String getTypeOfTest() {
		return typeOfTest;
	}

	/**
	 * Sets the type of test.
	 * 
	 * @param typeOfTest the new type of test
	 */
	public void setTypeOfTest(String typeOfTest) {
		this.typeOfTest = typeOfTest;
	}

	/**
	 * Gets the vehicle file status.
	 * 
	 * @return the vehicle file status
	 */
	public String getVehicleFileStatus() {
		return vehicleFileStatus;
	}

	/**
	 * Sets the vehicle file status.
	 * 
	 * @param vehicleFileStatus the new vehicle file status
	 */
	public void setVehicleFileStatus(String vehicleFileStatus) {
		this.vehicleFileStatus = vehicleFileStatus;
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
	 * Gets the vin.
	 * 
	 * @return the vin
	 */
	public String getVin() {
		return vin;
	}

	/**
	 * Sets the vin.
	 * 
	 * @param vin the new vin
	 */
	public void setVin(String vin) {
		this.vin = vin;
	}

	/**
	 * Gets the body work label.
	 * 
	 * @return the body work label
	 */
	public String getBodyWorkLabel() {
		return bodyWorkLabel;
	}

	/**
	 * Sets the body work label.
	 * 
	 * @param bodyWorkLabel the new body work label
	 */
	public void setBodyWorkLabel(String bodyWorkLabel) {
		this.bodyWorkLabel = bodyWorkLabel;
	}

	/**
	 * Gets the model year.
	 * 
	 * @return the model year
	 */
	public String getModelYear() {
		return modelYear;
	}

	/**
	 * Sets the model year.
	 * 
	 * @param modelYear the new model year
	 */
	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}

	/**
	 * Gets the es label.
	 * 
	 * @return the es label
	 */
	public String getEsLabel() {
		return esLabel;
	}

	/**
	 * Sets the es label.
	 * 
	 * @param esLabel the new es label
	 */
	public void setEsLabel(String esLabel) {
		this.esLabel = esLabel;
	}

	/**
	 * Gets the engine label.
	 * 
	 * @return the engine label
	 */
	public String getEngineLabel() {
		return engineLabel;
	}

	/**
	 * Sets the engine label.
	 * 
	 * @param engineLabel the new engine label
	 */
	public void setEngineLabel(String engineLabel) {
		this.engineLabel = engineLabel;
	}

	/**
	 * Gets the gear box label.
	 * 
	 * @return the gear box label
	 */
	public String getGearBoxLabel() {
		return gearBoxLabel;
	}

	/**
	 * Sets the gear box label.
	 * 
	 * @param gearBoxLabel the new gear box label
	 */
	public void setGearBoxLabel(String gearBoxLabel) {
		this.gearBoxLabel = gearBoxLabel;
	}

	/**
	 * Gets the fuel type label.
	 * 
	 * @return the fuel type label
	 */
	public String getFuelTypeLabel() {
		return fuelTypeLabel;
	}

	/**
	 * Sets the fuel type label.
	 * 
	 * @param fuelTypeLabel the new fuel type label
	 */
	public void setFuelTypeLabel(String fuelTypeLabel) {
		this.fuelTypeLabel = fuelTypeLabel;
	}

	/**
	 * Gets the fuel label.
	 * 
	 * @return the fuel label
	 */
	public String getFuelLabel() {
		return fuelLabel;
	}

	/**
	 * Sets the fuel label.
	 * 
	 * @param fuelLabel the new fuel label
	 */
	public void setFuelLabel(String fuelLabel) {
		this.fuelLabel = fuelLabel;
	}

	/**
	 * Gets the country label.
	 * 
	 * @return the country label
	 */
	public String getCountryLabel() {
		return countryLabel;
	}

	/**
	 * Sets the country label.
	 * 
	 * @param countryLabel the new country label
	 */
	public void setCountryLabel(String countryLabel) {
		this.countryLabel = countryLabel;
	}

	/**
	 * Gets the project code family label.
	 * 
	 * @return the project code family label
	 */
	public String getProjectCodeFamilyLabel() {
		return projectCodeFamilyLabel;
	}

	/**
	 * Sets the project code family label.
	 * 
	 * @param projectCodeFamilyLabel the new project code family label
	 */
	public void setProjectCodeFamilyLabel(String projectCodeFamilyLabel) {
		this.projectCodeFamilyLabel = projectCodeFamilyLabel;
	}

	/**
	 * Gets the car brand maker.
	 * 
	 * @return the car brand maker
	 */
	public Set<String> getCarBrandMaker() {
		return carBrandMaker;
	}

	/**
	 * Sets the car brand maker.
	 * 
	 * @param carBrandMaker the new car brand maker
	 */
	public void setCarBrandMaker(Set<String> carBrandMaker) {
		this.carBrandMaker = carBrandMaker;
	}

	/**
	 * Gets the prep file struct representation.
	 * 
	 * @return the prep file struct representation
	 */
	public PreparationFileStructureRepresentation getPrepFileStructRepresentation() {
		return prepFileStructRepresentation;
	}

	/**
	 * Sets the prep file struct representation.
	 * 
	 * @param prepFileStructRepresentation the new prep file struct representation
	 */
	public void setPrepFileStructRepresentation(PreparationFileStructureRepresentation prepFileStructRepresentation) {
		this.prepFileStructRepresentation = prepFileStructRepresentation;
	}

	/**
	 * Gets the vehicle file representation.
	 * 
	 * @return the vehicle file representation
	 */
	public VehicleFileRepresentation getVehicleFileRepresentation() {
		return vehicleFileRepresentation;
	}

	/**
	 * Sets the vehicle file representation.
	 * 
	 * @param vehicleFileRepresentation the new vehicle file representation
	 */
	public void setVehicleFileRepresentation(VehicleFileRepresentation vehicleFileRepresentation) {
		this.vehicleFileRepresentation = vehicleFileRepresentation;
	}

	/**
	 * Gets the co decision higher lim.
	 * 
	 * @return the co decision higher lim
	 */
	public double getCoDecisionHigherLim() {
		return coDecisionHigherLim;
	}

	/**
	 * Sets the co decision higher lim.
	 * 
	 * @param coDecisionHigherLim the new co decision higher lim
	 */
	public void setCoDecisionHigherLim(double coDecisionHigherLim) {
		this.coDecisionHigherLim = coDecisionHigherLim;
	}

	/**
	 * Gets the co decision lower lim.
	 * 
	 * @return the co decision lower lim
	 */
	public double getCoDecisionLowerLim() {
		return coDecisionLowerLim;
	}

	/**
	 * Sets the co decision lower lim.
	 * 
	 * @param coDecisionLowerLim the new co decision lower lim
	 */
	public void setCoDecisionLowerLim(double coDecisionLowerLim) {
		this.coDecisionLowerLim = coDecisionLowerLim;
	}

	/**
	 * Gets the lambda decision lower lim.
	 * 
	 * @return the lambda decision lower lim
	 */
	public double getLambdaDecisionLowerLim() {
		return lambdaDecisionLowerLim;
	}

	/**
	 * Sets the lambda decision lower lim.
	 * 
	 * @param lambdaDecisionLowerLim the new lambda decision lower lim
	 */
	public void setLambdaDecisionLowerLim(double lambdaDecisionLowerLim) {
		this.lambdaDecisionLowerLim = lambdaDecisionLowerLim;
	}

	/**
	 * Gets the lambda decision higher lim.
	 * 
	 * @return the lambda decision higher lim
	 */
	public double getLambdaDecisionHigherLim() {
		return lambdaDecisionHigherLim;
	}

	/**
	 * Sets the lambda decision higher lim.
	 * 
	 * @param lambdaDecisionHigherLim the new lambda decision higher lim
	 */
	public void setLambdaDecisionHigherLim(double lambdaDecisionHigherLim) {
		this.lambdaDecisionHigherLim = lambdaDecisionHigherLim;
	}

	/**
	 * Gets the opac dec higher lim.
	 * 
	 * @return the opac dec higher lim
	 */
	public double getOpacDecHigherLim() {
		return opacDecHigherLim;
	}

	/**
	 * Sets the opac dec higher lim.
	 * 
	 * @param opacDecHigherLim the new opac dec higher lim
	 */
	public void setOpacDecHigherLim(double opacDecHigherLim) {
		this.opacDecHigherLim = opacDecHigherLim;
	}

	/**
	 * Gets the opac dec lower lim.
	 * 
	 * @return the opac dec lower lim
	 */
	public double getOpacDecLowerLim() {
		return opacDecLowerLim;
	}

	/**
	 * Sets the opac dec lower lim.
	 * 
	 * @param opacDecLowerLim the new opac dec lower lim
	 */
	public void setOpacDecLowerLim(double opacDecLowerLim) {
		this.opacDecLowerLim = opacDecLowerLim;
	}

	/**
	 * Gets the vehicle file status gui.
	 * 
	 * @return the vehicle file status gui
	 */
	public String getVehicleFileStatusGui() {
		return vehicleFileStatusGui;
	}

	/**
	 * Sets the vehicle file status gui.
	 * 
	 * @param vehicleFileStatusGui the new vehicle file status gui
	 */
	public void setVehicleFileStatusGui(String vehicleFileStatusGui) {
		this.vehicleFileStatusGui = vehicleFileStatusGui;
	}

	public String getPowerCV() {
		return powerCV;
	}

	public void setPowerCV(String powerCV) {
		this.powerCV = powerCV;
	}

	public String getPowerKW() {
		return powerKW;
	}

	public void setPowerKW(String powerKW) {
		this.powerKW = powerKW;
	}

	public String getTorque() {
		return torque;
	}

	public void setTorque(String torque) {
		this.torque = torque;
	}

	public String getFuelInjectionType() {
		return fuelInjectionType;
	}

	public void setFuelInjectionType(String fuelInjectionType) {
		this.fuelInjectionType = fuelInjectionType;
	}

}
