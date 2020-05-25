/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.rgvaluedesdependenttcl;

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

import com.inetpsa.poc00.DomainConstants;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.rgvaluedtestcondition.RegGrpValdTestCondition;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTREC")
public class RGValuedESDependentTCL extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	protected Long entityId;

	/** The description. */
	@Column(name = "DESCRIPTION")
	private String description;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The version. */
	@Column(name = "VERSION")
	private String version;
	
	/** The rg valued generic test condition. */
	@OneToMany(mappedBy = "rgValuedEsDepTCL", cascade = CascadeType.ALL)
	private List<RegGrpValdTestCondition> rgValuedGenericTestCondition;
	
	/** The regulation group. */
	@ManyToOne
	@JoinColumn(name = "REGULATIONGROUP_ID")
	private RegulationGroup regulationGroup;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public RGValuedESDependentTCL() {
		super();
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
	 * Gets the rg valued generic test condition.
	 *
	 * @return the rg valued generic test condition
	 */
	public List<RegGrpValdTestCondition> getRgValuedGenericTestCondition() {
		return rgValuedGenericTestCondition;
	}

	/**
	 * Sets the rg valued generic test condition.
	 *
	 * @param rgValuedGenericTestCondition the new rg valued generic test condition
	 */
	public void setRgValuedGenericTestCondition(List<RegGrpValdTestCondition> rgValuedGenericTestCondition) {
		this.rgValuedGenericTestCondition = rgValuedGenericTestCondition;
	}

	/**
	 * Gets the regulation group.
	 *
	 * @return the regulation group
	 */
	public RegulationGroup getRegulationGroup() {
		return regulationGroup;
	}

	/**
	 * Sets the regulation group.
	 *
	 * @param regulationGroup the new regulation group
	 */
	public void setRegulationGroup(RegulationGroup regulationGroup) {
		this.regulationGroup = regulationGroup;
	}

	/**
	 * to String implementation.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return label + DomainConstants.VERSION_SEPARATOR + version;
	}
}
