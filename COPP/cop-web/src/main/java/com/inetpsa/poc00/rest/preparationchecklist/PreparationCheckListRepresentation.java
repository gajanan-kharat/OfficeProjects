package com.inetpsa.poc00.rest.preparationchecklist;

import java.util.List;

import com.inetpsa.poc00.rest.preparationresult.PreparationResultRepresentation;

/**
 * The Class PreparationCheckListRepresentation.
 */
public class PreparationCheckListRepresentation {

	/** The entity id. */
	private Long entityId;

	/** The label. */
	private String label;

	/** The enabled. */
	private Boolean enabled;

	/** The order. */
	private int order;

	/** The description. */
	private String description;
	
	/** The type of list. */
	private int typeOfList;
	
	/** The preparation result list. */
	private List<PreparationResultRepresentation> preparationResultList; 

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
	 * Gets the enabled.
	 * 
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled.
	 * 
	 * @param enabled the new enabled
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Gets the order.
	 * 
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * Sets the order.
	 * 
	 * @param order the new order
	 */
	public void setOrder(int order) {
		this.order = order;
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
	 * Gets the preparation result list.
	 * 
	 * @return the preparation result list
	 */
	public List<PreparationResultRepresentation> getPreparationResultList() {
		return preparationResultList;
	}

	/**
	 * Sets the preparation result list.
	 * 
	 * @param preparationResultList the new preparation result list
	 */
	public void setPreparationResultList(List<PreparationResultRepresentation> preparationResultList) {
		this.preparationResultList = preparationResultList;
	}

	public int getTypeOfList() {
		return typeOfList;
	}

	public void setTypeOfList(int typeOfList) {
		this.typeOfList = typeOfList;
	}

}
