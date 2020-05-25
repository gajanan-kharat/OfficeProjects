/*
 * Creation : Oct 14, 2016
 */
package com.inetpsa.poc00.rest.genericpreparationchecklist;

import java.util.List;

import com.inetpsa.poc00.rest.genericpreparationitem.PreparationItemRepresentation;

/**
 * The Class GenericPreparationCheckListRepresentation.
 */
public class GenericPreparationCheckListRepresentation {

	/** The entity id. */
	private Long entityId;

	/** The label. */
	private String label;

	/** The description. */
	private String description;

	/** The order. */
	private Integer order;

	/** The enabled. */
	private Boolean enabled;

	/** The preparation items. */
	private List<PreparationItemRepresentation> preparationItems;

	/**
	 * Instantiates a new generic preparation check list representation.
	 */
	public GenericPreparationCheckListRepresentation() {
		// Default Constructor
	}

	/**
	 * Instantiates a new generic preparation check list representation.
	 *
	 * @param entityId the entity id
	 * @param label the label
	 * @param order the order
	 * @param description the description
	 * @param enabled the enabled
	 */
	public GenericPreparationCheckListRepresentation(Long entityId, String label, Integer order, String description, Boolean enabled) {
		this.entityId = entityId;
		this.label = label;
		this.order = order;
		this.description = description;
		this.enabled = enabled;

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
	 * Gets the order.
	 *
	 * @return the order
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * Sets the order.
	 *
	 * @param order the new order
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * Gets the preparation items.
	 *
	 * @return the preparation items
	 */
	public List<PreparationItemRepresentation> getPreparationItems() {
		return preparationItems;
	}

	/**
	 * Sets the preparation items.
	 *
	 * @param preparationItems the new preparation items
	 */
	public void setPreparationItems(List<PreparationItemRepresentation> preparationItems) {
		this.preparationItems = preparationItems;
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

}
