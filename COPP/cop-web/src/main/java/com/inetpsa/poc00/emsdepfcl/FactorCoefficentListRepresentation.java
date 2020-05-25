/*
 * Creation : Apr 15, 2016
 */
package com.inetpsa.poc00.emsdepfcl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inetpsa.poc00.common.BaseRepresentation;
import com.inetpsa.poc00.rest.category.CategoryRepresentation;
import com.inetpsa.poc00.rest.clasz.ClaszRepresentation;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.fuel.FuelRepresentation;
import com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeRepresentation;
import com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyRepresentation;

/**
 * The Class FactorCoefficentListRepresentation.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FactorCoefficentListRepresentation extends BaseRepresentation {

	/** The entity id. */
	protected long entityId;

	/** The description. */
	private String description;

	/** The label. */
	private String label;

	/** The version. */
	private String version;

	/** The vehicle technology set. */

	private List<VehicleTechnologyRepresentation> vehicleTechnologySet = new ArrayList<VehicleTechnologyRepresentation>();

	/** The Fuel injection types. */

	private List<FuelInjectionTypeRepresentation> fuelInjectionTypes = new ArrayList<FuelInjectionTypeRepresentation>();

	/** The Categories. */

	private List<CategoryRepresentation> categories = new ArrayList<CategoryRepresentation>();

	private List<ClaszRepresentation> classes = new ArrayList<ClaszRepresentation>();

	/** The Fuels. */

	private List<FuelRepresentation> fuels = new ArrayList<FuelRepresentation>();
	private Set<FactorCoeffApplicationTypeRepresentation> fcApplicationTypes = new HashSet<FactorCoeffApplicationTypeRepresentation>();
	/** The factor or coeffiecients. */
	private List<FactorCoefficentsRepresentation> factorOrCoeffiecients = new ArrayList<FactorCoefficentsRepresentation>();

	/** The emission standard. */

	private EmissionStandardRepresentation emissionStandard;

	/**
	 * Instantiates a new factor coefficent list representation.
	 */
	public FactorCoefficentListRepresentation() {
		// Default Constructor
	}

	/**
	 * Instantiates a new factor coefficent list representation.
	 * 
	 * @param entityId the entity id
	 * @param description the description
	 * @param version the version
	 * @param label the label
	 */
	public FactorCoefficentListRepresentation(long entityId, String description, String version, String label) {
		this.entityId = entityId;
		this.description = description;
		this.version = version;
		this.label = label;
	}

	/**
	 * Instantiates a new factor coefficent list representation.
	 * 
	 * @param entityId the entity id
	 * @param description the description
	 * @param version the version
	 * @param label the label
	 * @param emsId the ems id
	 */
	public FactorCoefficentListRepresentation(long entityId, String description, String version, String label, long emsId) {
		this.entityId = entityId;
		this.description = description;
		this.version = version;
		this.label = label;
		this.emissionStandard = new EmissionStandardRepresentation(emsId);

	}

	/**
	 * Instantiates a new factor coefficent list representation.
	 * 
	 * @param entityId the entity id
	 */
	public FactorCoefficentListRepresentation(long entityId) {
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
	 * @param dataList the new vehicle technology set
	 */
	public void setVehicleTechnologySet(List<VehicleTechnologyRepresentation> vehicleTechnologyList) {
		this.vehicleTechnologySet = vehicleTechnologyList;
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
	 * @param dataList the new fuel injection types
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
	 * @param dataList the new categories
	 */
	public void setCategories(List<CategoryRepresentation> categoryList) {
		this.categories = categoryList;
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
	 * @param dataList the new fuels
	 */
	public void setFuels(List<FuelRepresentation> fuels) {
		this.fuels = fuels;
	}

	/**
	 * Gets the factor or coeffiecients.
	 * 
	 * @return the factor or coeffiecients
	 */
	public List<FactorCoefficentsRepresentation> getFactorOrCoeffiecients() {
		return factorOrCoeffiecients;
	}

	/**
	 * Sets the factor or coeffiecients.
	 * 
	 * @param dataList the new factor or coeffiecients
	 */
	public void setFactorOrCoeffiecients(List<FactorCoefficentsRepresentation> factorOrCoeffiecients) {
		this.factorOrCoeffiecients = factorOrCoeffiecients;
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

	public Set<FactorCoeffApplicationTypeRepresentation> getFcApplicationTypes() {
		return fcApplicationTypes;
	}

	public void setFcApplicationTypes(Set<FactorCoeffApplicationTypeRepresentation> fcApplicationTypes) {
		this.fcApplicationTypes = fcApplicationTypes;
	}

	public List<ClaszRepresentation> getClasses() {
		return classes;
	}

	public void setClasses(List<ClaszRepresentation> classes) {
		this.classes = classes;
	}

}
