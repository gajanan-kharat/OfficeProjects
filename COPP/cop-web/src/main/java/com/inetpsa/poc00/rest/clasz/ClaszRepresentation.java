package com.inetpsa.poc00.rest.clasz;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.emsdepfcl.FactorCoefficentListRepresentation;
import com.inetpsa.poc00.pollutantorgas.PollutantGasLimitListRepresentation;
import com.inetpsa.poc00.rest.category.CategoryRepresentation;

/**
 * The Class ClaszRepresentation.
 */
public class ClaszRepresentation {

	/** The entity id. */
	private Long entityId;

	/** The label. */
	private String label;

	/** The description. */
	private String description;

	/** The edited. */
	private boolean edited = false;

	/** The category set. */
	@JsonIgnore
	private List<CategoryRepresentation> categorySet;

	/** The factor or coeffiecients. */
	@JsonIgnore
	private Set<FactorCoefficentListRepresentation> factorCoefficentList;

	/** The pollutant gas limits. */
	@JsonIgnore
	private Set<PollutantGasLimitListRepresentation> pollutantGasLimitList;

	/**
	 * Instantiates a new clasz representation.
	 */
	/*
	 * Default Constructor
	*/
	public ClaszRepresentation() {
		// Default Constructor
	}

	/**
	 * Instantiates a new clasz representation.
	 * 
	 * @param entityId the entity id
	 * @param description the description
	 * @param label the label
	 */
	/*
	 * Constructor 
	 * Parameter
	 * 		entityId, description, label 
	*/
	public ClaszRepresentation(Long entityId, String description, String label) {

		this.entityId = entityId;
		this.description = description;
		this.label = label;

	}

	/**
	 * Instantiates a new clasz representation.
	 * 
	 * @param entityId the entity id
	 */
	public ClaszRepresentation(Long entityId) {

		this.entityId = entityId;

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
	 * Gets the category set.
	 * 
	 * @return the category set
	 */
	public List<CategoryRepresentation> getCategorySet() {
		return categorySet;
	}

	/**
	 * Sets the category set.
	 * 
	 * @param categorySet the new category set
	 */
	public void setCategorySet(List<CategoryRepresentation> categorySet) {
		this.categorySet = categorySet;
	}

	/**
	 * Checks if is edited.
	 * 
	 * @return true, if is edited
	 */
	public boolean isEdited() {
		return edited;
	}

	/**
	 * Sets the edited.
	 * 
	 * @param edited the new edited
	 */
	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	/**
	 * Gets the factor coefficent list.
	 * 
	 * @return the factor coefficent list
	 */
	public Set<FactorCoefficentListRepresentation> getFactorCoefficentList() {
		return factorCoefficentList;
	}

	/**
	 * Sets the factor coefficent list.
	 * 
	 * @param factorCoefficentList the new factor coefficent list
	 */
	public void setFactorCoefficentList(Set<FactorCoefficentListRepresentation> factorCoefficentList) {
		this.factorCoefficentList = factorCoefficentList;
	}

	/**
	 * Gets the pollutant gas limit list.
	 * 
	 * @return the pollutant gas limit list
	 */
	public Set<PollutantGasLimitListRepresentation> getPollutantGasLimitList() {
		return pollutantGasLimitList;
	}

	/**
	 * Sets the pollutant gas limit list.
	 * 
	 * @param pollutantGasLimitList the new pollutant gas limit list
	 */
	public void setPollutantGasLimitList(Set<PollutantGasLimitListRepresentation> pollutantGasLimitList) {
		this.pollutantGasLimitList = pollutantGasLimitList;
	}

}
