/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.testnature;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTest;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTTNR")
public class TestNature extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TEST_ID")
	protected long entityId;

	/** The label. */
	@Column(name = "DESCRIPTION")
	private String label;

	/** The type. */
	@Column(name = "VALUE")
	private String type;

	/** The hierarchy. */
	@Column(name = "HIERARCHY")
	private int hierarchy;

	/** The vehicle file list. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "testNature")
	private List<TypeOfTest> typeOfTestList;

	/** The status. */
	@ManyToMany
	@JoinTable(name = "COPQTMSN", joinColumns = { @JoinColumn(name = "TEST_ID") }, inverseJoinColumns = { @JoinColumn(name = "STATUS_ID") })
	private Set<Status> status = new HashSet<Status>();

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public TestNature() {
		super();
	}

	/**
	 * Instantiates a new test nature.
	 * 
	 * @param testNatureId the test nature id
	 */
	public TestNature(long testNatureId) {
		this.entityId = testNatureId;
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public Set<Status> getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status the status to set
	 */
	public void setStatus(Set<Status> status) {
		this.status = status;
	}

	/* (non-Javadoc)
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
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	public int getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(int hierarchy) {
		this.hierarchy = hierarchy;
	}

	/**
	 * to String implementation.
	 * 
	 * @return the string
	 */
	@Override
	public String toString() {
		return type;
	}
}
