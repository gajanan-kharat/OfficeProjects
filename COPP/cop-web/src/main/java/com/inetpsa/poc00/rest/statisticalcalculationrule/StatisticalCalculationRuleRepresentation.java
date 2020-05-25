/*
 * Creation : Nov 28, 2016
 */
package com.inetpsa.poc00.rest.statisticalcalculationrule;

import java.util.Set;

import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;

/**
 * The Class StatisticalCalculationRuleRepresentation.
 */
public class StatisticalCalculationRuleRepresentation {

	/** The entity id. */
	private long entityId;

	/** The label. */
	private String label;

	/** The description. */
	private String description;

	/** The regulation group list. */
	private Set<RegulationGroup> regulationGroupList;

	/**
	 * Instantiates a new statistical calculation rule representation.
	 */
	public StatisticalCalculationRuleRepresentation() {
		super();
	}

	/**
	 * Instantiates a new statistical calculation rule representation.
	 * 
	 * @param entityId the entity id
	 * @param label the label
	 * @param description the description
	 */
	public StatisticalCalculationRuleRepresentation(long entityId, String label, String description) {
		super();
		this.entityId = entityId;
		this.label = label;
		this.description = description;
	}

	/**
	 * Gets the entity id.
	 * 
	 * @return the entity id
	 */
	public long getEntityId() {
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

}
