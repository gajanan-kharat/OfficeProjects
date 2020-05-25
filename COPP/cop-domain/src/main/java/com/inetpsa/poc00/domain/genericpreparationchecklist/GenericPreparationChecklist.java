package com.inetpsa.poc00.domain.genericpreparationchecklist;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.genericpreparationitem.GenericPreparationItem;
import com.inetpsa.poc00.domain.preparationfilestructure.PreparationFileStructure;

/**
 * The Class GenericPreparationChecklist.
 */
@Entity
@Table(name = "COPQTGPC")
public class GenericPreparationChecklist extends BaseAggregateRoot<Long> {

	/**
	 * The desciption.
	 */
	@Column(name = "DESCRIPTION")
	String description;
	/**
	 * The enabled.
	 */
	@Column(name = "ENABLED")
	boolean enabled;
	/**
	 * The entity id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	Long entityId;
	/**
	 * The label.
	 */
	@Column(name = "LABEL")
	String label;
	/**
	 * The order.
	 */
	@Column(name = "ORDER_NUMBER")
	Integer order;
	/**
	 * The preparation file structure.
	 */
	@ManyToOne
	@JoinColumn(name = "FILE_STRUCTURE_ID")
	private PreparationFileStructure preparationFileStructure;

	/**
	 * The preparation items.
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "genericPreparationChecklist")
	private List<GenericPreparationItem> preparationItems = new ArrayList<GenericPreparationItem>();

	/**
	 * Instantiates a new generic preparation checklist.
	 */
	public GenericPreparationChecklist() {
		super();
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
	 * Checks if is enabled.
	 *
	 * @return true, if is enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled the new enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
	 * Gets the preparation file structure.
	 *
	 * @return the preparation file structure
	 */
	public PreparationFileStructure getPreparationFileStructure() {
		return preparationFileStructure;
	}

	/**
	 * Sets the preparation file structure.
	 *
	 * @param preparationFileStructure the new preparation file structure
	 */
	public void setPreparationFileStructure(PreparationFileStructure preparationFileStructure) {
		this.preparationFileStructure = preparationFileStructure;
	}

	/**
	 * Gets the preparation items.
	 *
	 * @return the preparation items
	 */
	public List<GenericPreparationItem> getPreparationItems() {
		return preparationItems;
	}

	/**
	 * Sets the preparation items.
	 *
	 * @param preparationItems the new preparation items
	 */
	public void setPreparationItems(List<GenericPreparationItem> preparationItems) {
		this.preparationItems = preparationItems;
	}

}// end GenericPreparationChecklist