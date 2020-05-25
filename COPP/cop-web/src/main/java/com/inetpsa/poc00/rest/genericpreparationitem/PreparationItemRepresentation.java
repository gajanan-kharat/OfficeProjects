/*
 * Creation : Oct 14, 2016
 */
package com.inetpsa.poc00.rest.genericpreparationitem;

/**
 * The Class GenericPreparationItemRepresentation.
 */
public class PreparationItemRepresentation {

	/** The entity id. */
	private Long entityId;

	/** The label. */
	private String label;

	/** The help text. */
	private String helpText;

	/** The order. */
	private Integer order;

	/** The unit. */
	private String unit;

	/** The data type. */
	private String dataType;

	/** The authorized comment. */
	private Boolean authorizedComment;

	/** The mandatory. */
	private Boolean mandatory;

	/**
	 * Instantiates a new generic preparation item representation.
	 */
	public PreparationItemRepresentation() {
		// Default Constructor
	}

	/**
	 * Instantiates a new preparation item representation.
	 *
	 * @param entityId the entity id
	 * @param label the label
	 * @param helpText the help text
	 * @param unit the unit
	 * @param dataType the data type
	 * @param order the order
	 * @param mandatory the mandatory
	 * @param authorizedComment the authorized comment
	 */
	public PreparationItemRepresentation(Long entityId, String label, String helpText, String unit, String dataType, Integer order, Boolean mandatory, Boolean authorizedComment) {

		this.entityId = entityId;
		this.label = label;
		this.helpText = helpText;
		this.unit = unit;
		this.dataType = dataType;
		this.order = order;
		this.mandatory = mandatory;
		this.authorizedComment = authorizedComment;
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
	 * Gets the help text.
	 *
	 * @return the help text
	 */
	public String getHelpText() {
		return helpText;
	}

	/**
	 * Sets the help text.
	 *
	 * @param helpText the new help text
	 */
	public void setHelpText(String helpText) {
		this.helpText = helpText;
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
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * Sets the unit.
	 *
	 * @param unit the new unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * Gets the data type.
	 *
	 * @return the data type
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * Sets the data type.
	 *
	 * @param dataType the new data type
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * Gets the authorized comment.
	 *
	 * @return the authorized comment
	 */
	public Boolean getAuthorizedComment() {
		return authorizedComment;
	}

	/**
	 * Sets the authorized comment.
	 *
	 * @param authorizedComment the new authorized comment
	 */
	public void setAuthorizedComment(Boolean authorizedComment) {
		this.authorizedComment = authorizedComment;
	}

	/**
	 * Gets the mandatory.
	 *
	 * @return the mandatory
	 */
	public Boolean getMandatory() {
		return mandatory;
	}

	/**
	 * Sets the mandatory.
	 *
	 * @param mandatory the new mandatory
	 */
	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

}
