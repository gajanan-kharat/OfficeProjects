/*
 * Creation : Apr 20, 2016
 */
package com.inetpsa.poc00.pollutantorgas;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inetpsa.poc00.common.BaseRepresentation;
import com.inetpsa.poc00.rest.category.CategoryRepresentation;
import com.inetpsa.poc00.rest.clasz.ClaszRepresentation;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.fuel.FuelRepresentation;
import com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeRepresentation;
import com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyRepresentation;

/**
 * The Class PollutantGasLimitListRepresentation.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PollutantGasLimitListRepresentation extends BaseRepresentation {

	/** The entity id. */
	protected long entityId;

	/** The description. */
	private String description;

	/** The version. */
	private String version;

	/** The label. */
	private String label;

	/** The pollutant gas limit. */
	private List<PollutantGasLimitRepresentation> pollutantGasLimit;

	/** The vehicle technology set. */
	private List<VehicleTechnologyRepresentation> vehicleTechnologySet = new ArrayList<VehicleTechnologyRepresentation>();

	/** The fuel injection types. */
	private List<FuelInjectionTypeRepresentation> fuelInjectionTypes = new ArrayList<FuelInjectionTypeRepresentation>();

	/** The categories. */
	private List<CategoryRepresentation> categories = new ArrayList<CategoryRepresentation>();

	/** The fuels. */
	private List<FuelRepresentation> fuels = new ArrayList<FuelRepresentation>();

	/** The emission standard. */
	private EmissionStandardRepresentation emissionStandard;

	/** The classes. */
	private List<ClaszRepresentation> classes = new ArrayList<ClaszRepresentation>();

	/**
	 * Instantiates a new pollutant gas limit list representation.
	 */
	public PollutantGasLimitListRepresentation() {
		// Default Constructor

	}

	/**
	 * Instantiates a new pollutant gas limit list representation.
	 * 
	 * @param entityId the entity id
	 * @param description the description
	 * @param version the version
	 * @param label the label
	 * @param emsId the ems id
	 */
	public PollutantGasLimitListRepresentation(long entityId, String description, String version, String label, long emsId) {
		this.entityId = entityId;
		this.description = description;
		this.version = version;
		this.label = label;
		this.emissionStandard = new EmissionStandardRepresentation(emsId);

	}

	/**
	 * Instantiates a new pollutant gas limit list representation.
	 * 
	 * @param entityId the entity id
	 */
	public PollutantGasLimitListRepresentation(Long entityId) {
		this.entityId = entityId;

	}

	/**
	 * Gets the entity id.
	 * 
	 * @return the entity id
	 */
	public long getEntityId() {
		return entityId;
	}

	/**
	 * Sets the entity id.
	 * 
	 * @param entityId the new entity id
	 */
	public void setEntityId(long entityId) {
		this.entityId = entityId;
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
	 * Gets the version.
	 * 
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 * 
	 * @param version the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the label.
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 * 
	 * @param label the new label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets the pollutant gas limit.
	 * 
	 * @return the pollutant gas limit
	 */
	public List<PollutantGasLimitRepresentation> getPollutantGasLimit() {
		return pollutantGasLimit;
	}

	/**
	 * Sets the pollutant gas limit.
	 * 
	 * @param dataList the new pollutant gas limit
	 */
	public void setPollutantGasLimit(List<PollutantGasLimitRepresentation> dataList) {
		this.pollutantGasLimit = dataList;
	}

	/**
	 * Gets the vehicle technology set.
	 * 
	 * @return the vehicle technology set
	 */
	public List<VehicleTechnologyRepresentation> getVehicleTechnologySet() {
		return vehicleTechnologySet;
	}

	/**
	 * Sets the vehicle technology set.
	 * 
	 * @param vehicleTechnologySet the new vehicle technology set
	 */
	public void setVehicleTechnologySet(List<VehicleTechnologyRepresentation> vehicleTechnologySet) {
		this.vehicleTechnologySet = vehicleTechnologySet;
	}

	/**
	 * Gets the fuel injection types.
	 * 
	 * @return the fuel injection types
	 */
	public List<FuelInjectionTypeRepresentation> getFuelInjectionTypes() {
		return fuelInjectionTypes;
	}

	/**
	 * Sets the fuel injection types.
	 * 
	 * @param fuelInjectionTypes the new fuel injection types
	 */
	public void setFuelInjectionTypes(List<FuelInjectionTypeRepresentation> fuelInjectionTypes) {
		this.fuelInjectionTypes = fuelInjectionTypes;
	}

	/**
	 * Gets the categories.
	 * 
	 * @return the categories
	 */
	public List<CategoryRepresentation> getCategories() {
		return categories;
	}

	/**
	 * Sets the categories.
	 * 
	 * @param categories the new categories
	 */
	public void setCategories(List<CategoryRepresentation> categories) {
		this.categories = categories;
	}

	/**
	 * Gets the fuels.
	 * 
	 * @return the fuels
	 */
	public List<FuelRepresentation> getFuels() {
		return fuels;
	}

	/**
	 * Sets the fuels.
	 * 
	 * @param fuels the new fuels
	 */
	public void setFuels(List<FuelRepresentation> fuels) {
		this.fuels = fuels;
	}

	/**
	 * Gets the emission standard.
	 * 
	 * @return the emission standard
	 */
	public EmissionStandardRepresentation getEmissionStandard() {
		return emissionStandard;
	}

	/**
	 * Sets the emission standard.
	 * 
	 * @param emissionStandard the new emission standard
	 */
	public void setEmissionStandard(EmissionStandardRepresentation emissionStandard) {
		this.emissionStandard = emissionStandard;
	}

	/**
	 * Gets the classes.
	 * 
	 * @return the classes
	 */
	public List<ClaszRepresentation> getClasses() {
		return classes;
	}

	/**
	 * Sets the classes.
	 * 
	 * @param classes the new classes
	 */
	public void setClasses(List<ClaszRepresentation> classes) {
		this.classes = classes;
	}

	@Override
	public String toString() {
		return "PollutantGasLimitListRepresentation [entityId=" + entityId + ", version=" + version + ", label=" + label + ", pollutantGasLimit=" + pollutantGasLimit + ", emissionStandard=" + emissionStandard + "]";
	}

}
