package com.inetpsa.poc00.domain.tirebrand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

/**
 * The persistent class for the copqttbr database table.
 */
@Entity
@Table(name = "COPQTTBR")
public class TireBrand extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long entityId;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/**
	 * Instantiates a new tire brand.
	 */
	public TireBrand() {
		super();
	}

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return this.label;
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
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.domain.BaseEntity#toString()
	 */
	@Override
	public String toString() {
		return "TireBrand [label = " + label + "]";
	}

}