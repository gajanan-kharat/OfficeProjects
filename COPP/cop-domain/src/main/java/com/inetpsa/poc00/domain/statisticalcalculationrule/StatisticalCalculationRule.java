/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.statisticalcalculationrule;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.statisticalparameters.StatisticalCalculationParameters;

/**
 * The Class StatisticalCalculationRule.
 */
@Entity
@Table(name = "COPQTSCR")
public class StatisticalCalculationRule extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long entityId;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The description. */
	@Column(name = "DESCRIPTION")
	private String description;

	/** The label key. */
	@Column(name = "LABEL_KEY")
	String labelKey;

	/** The statistical calculation parameters. */
	@OneToMany(mappedBy = "statisticalCalculationRule")
	private Set<StatisticalCalculationParameters> statisticalCalculationParameters;

	/**
	 * Gets the entity id.
	 * 
	 * @return the entity id
	 */
	@OneToMany(mappedBy = "statisticalCalculationRule")
	private Set<RegulationGroup> regulationGroupList = new HashSet<RegulationGroup>();

	/**
	 * Gets the entity id.
	 * 
	 * @return the entity id
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
	public void setEntityId(long entityId) {
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
	 * Gets the statistical calculation parameters.
	 * 
	 * @return the statistical calculation parameters
	 */
	public Set<StatisticalCalculationParameters> getStatisticalCalculationParameters() {
		return statisticalCalculationParameters;
	}

	/**
	 * Sets the statistical calculation parameters.
	 * 
	 * @param statisticalCalculationParameters the new statistical calculation parameters
	 */
	public void setStatisticalCalculationParameters(Set<StatisticalCalculationParameters> statisticalCalculationParameters) {
		this.statisticalCalculationParameters = statisticalCalculationParameters;
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
	 * Gets the regulation group list.
	 * 
	 * @return the regulation group list
	 */
	public Set<RegulationGroup> getRegulationGroupList() {
		return regulationGroupList;
	}

	/**
	 * Sets the regulation group list.
	 * 
	 * @param regulationGroupList the new regulation group list
	 */
	public void setRegulationGroupList(Set<RegulationGroup> regulationGroupList) {
		this.regulationGroupList = regulationGroupList;
	}

	/**
	 * Gets the label key.
	 * 
	 * @return the label key
	 */
	public String getLabelKey() {
		return labelKey;
	}

	/**
	 * Sets the label key.
	 * 
	 * @param labelKey the new label key
	 */
	public void setLabelKey(String labelKey) {
		this.labelKey = labelKey;
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
	 * to String implementation.
	 * 
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Label : " + label + " Description : " + description;
	}

}
