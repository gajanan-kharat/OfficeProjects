package com.inetpsa.poc00.domain.genericpreparationitem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.genericpreparationchecklist.GenericPreparationChecklist;

/**
 * The Class GenericPreparationItem.
 */
@Entity
@Table(name = "COPQTGPI")
public class GenericPreparationItem extends BaseAggregateRoot<Long> {

	/**
	 * The authorized comment.
	 */
	@Column(name = "AUTH_COMMENT")
	boolean authorizedComment;
	/**
	 * The data type.
	 */
	@Column(name = "DATATYPE")
	String dataType;
	/**
	 * The entity id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	Long entityId;
	/**
	 * The generic preparation checklist.
	 */
	@ManyToOne
	@JoinColumn(name = "CHECKLIST_ID")
	private GenericPreparationChecklist genericPreparationChecklist;
	/**
	 * The help text.
	 */
	@Column(name = "HELPTEXT")
	String helpText;
	/**
	 * The label.
	 */
	@Column(name = "LABEL")
	String label;
	/**
	 * The manadatoy.
	 */
	@Column(name = "MANDATORY")
	boolean mandatory;
	/**
	 * The order.
	 */
	@Column(name = "ORDER_NUMBER")
	Integer order;
	/**
	 * The unit.
	 */
	@Column(name = "UNIT")
	String unit;

	/**
	 * Instantiates a new generic preparation item.
	 */
	public GenericPreparationItem() {
		// Default Constructor
	}

	public GenericPreparationItem(String label, String helpText, String dataType, Integer order, String unit, Boolean authorizedComment, Boolean mandatory) {
		this.label = label;
		this.helpText = helpText;
		this.dataType = dataType;
		this.order = order;
		this.unit = unit;
		this.authorizedComment = authorizedComment;
		this.mandatory = mandatory;

	}

	/**
	 * Checks if is authorized comment.
	 *
	 * @return true, if is authorized comment
	 */
	public boolean isAuthorizedComment() {
		return authorizedComment;
	}

	/**
	 * Sets the authorized comment.
	 *
	 * @param authorizedComment the new authorized comment
	 */
	public void setAuthorizedComment(boolean authorizedComment) {
		this.authorizedComment = authorizedComment;
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
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.domain.BaseEntity#getEntityId()
	 */
	@Override
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
	 * Gets the generic preparation checklist.
	 *
	 * @return the generic preparation checklist
	 */
	public GenericPreparationChecklist getGenericPreparationChecklist() {
		return genericPreparationChecklist;
	}

	/**
	 * Sets the generic preparation checklist.
	 *
	 * @param genericPreparationChecklist the new generic preparation checklist
	 */
	public void setGenericPreparationChecklist(GenericPreparationChecklist genericPreparationChecklist) {
		this.genericPreparationChecklist = genericPreparationChecklist;
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
	 * Checks if is mandatory.
	 *
	 * @return true, if is mandatory
	 */
	public boolean isMandatory() {
		return mandatory;
	}

	/**
	 * Sets the mandatory.
	 *
	 * @param mandatory the new mandatory
	 */
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
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

}// end GenericPreparationItem