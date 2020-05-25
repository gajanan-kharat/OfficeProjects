/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.typeapprovalarea;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;

/**
 * The Class TypeApprovalArea.
 */
@Entity
@Table(name = "COPQTTAA")
public class TypeApprovalArea extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	protected Long entityId;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The description. */
	@Column(name = "DESCRIPTION")
	private String description;


	/** The regulation group. */
	@OneToMany(mappedBy = "typeApprovalArea", cascade = CascadeType.ALL)
	private Set<RegulationGroup> regulationGroup;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public TypeApprovalArea() {
		super();
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
	 * Gets the regulation group.
	 * 
	 * @return the regulation group
	 */
	public Set<RegulationGroup> getRegulationGroup() {
		return regulationGroup;
	}

	/**
	 * Sets the regulation group.
	 * 
	 * @param regulationGroup the new regulation group
	 */
	public void setRegulationGroup(Set<RegulationGroup> regulationGroup) {
		this.regulationGroup = regulationGroup;
	}


	/**
	 * to String implementation.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return label ;
	}
}
