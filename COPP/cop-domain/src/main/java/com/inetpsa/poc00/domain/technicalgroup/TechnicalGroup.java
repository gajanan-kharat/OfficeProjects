/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.technicalgroup;

import java.util.Date;
import java.util.Set;

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
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTTGP")
public class TechnicalGroup extends BaseAggregateRoot<Long> {

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

	/** The sampling label. */
	@Column(name = "SAMPLING_LABEL")
	private String samplingLabel;

	/** The creation date. */
	@Column(name = "CREATION_DATE")
	private Date creationDate;

	/** The modification date. */
	@Column(name = "MODIFICATION_DATE")
	private Date modificationDate;

	/** The techgroupstatus. */
	@ManyToOne
	@JoinColumn(name = "STATUS_ID")
	private Status techgroupstatus;

	/** The regulation group. */
	@ManyToOne
	@JoinColumn(name = "REGULATIONGROUP_ID")
	private RegulationGroup regulationGroup;

	/**
	 * The technical case.
	 * 
	 * @return the regulationGroup
	 */
	@OneToMany(mappedBy = "technicalGroup", targetEntity = TechnicalCase.class)
	private Set<TechnicalCase> technicalCase;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public TechnicalGroup() {
		super();
	}

	/**
	 * Instantiates a new technical group.
	 * 
	 * @param technicalGroup the technical group
	 */
	public TechnicalGroup(TechnicalGroup technicalGroup) {
		super();
		this.entityId = technicalGroup.entityId;
		this.description = technicalGroup.description;
		this.label = technicalGroup.label;
		this.version = technicalGroup.version;
		this.samplingLabel = technicalGroup.samplingLabel;
		this.creationDate = technicalGroup.creationDate;
		this.modificationDate = technicalGroup.modificationDate;
		this.techgroupstatus = technicalGroup.techgroupstatus;
		this.regulationGroup = technicalGroup.regulationGroup;
		this.technicalCase = technicalGroup.technicalCase;
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
	 * Gets the sampling label.
	 * 
	 * @return the samplingLabel
	 */

	public String getSamplingLabel() {
		return samplingLabel;
	}

	/**
	 * Sets the sampling label.
	 * 
	 * @param samplingLabel the samplingLabel to set
	 */
	public void setSamplingLabel(String samplingLabel) {
		this.samplingLabel = samplingLabel;
	}

	/**
	 * Gets the technical case.
	 * 
	 * @return the technicalCase
	 */
	public Set<TechnicalCase> getTechnicalCase() {
		return technicalCase;
	}

	/**
	 * Sets the technical case.
	 * 
	 * @param technicalCase the technicalCase to set
	 */
	public void setTechnicalCase(Set<TechnicalCase> technicalCase) {
		this.technicalCase = technicalCase;
	}

	/**
	 * Sets the regulation group.
	 * 
	 * @param regulationGroup the regulationGroup to set
	 */
	public void setRegulationGroup(RegulationGroup regulationGroup) {
		this.regulationGroup = regulationGroup;
	}

	/**
	 * Gets the techgroupstatus.
	 * 
	 * @return the techgroupstatus
	 */

	public Status getTechgroupstatus() {
		return techgroupstatus;
	}

	/**
	 * Sets the techgroupstatus.
	 * 
	 * @param techgroupstatus the techgroupstatus to set
	 */
	public void setTechgroupstatus(Status techgroupstatus) {
		this.techgroupstatus = techgroupstatus;
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
	 * Gets the creation date.
	 * 
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 * 
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Gets the modification date.
	 * 
	 * @return the modificationDate
	 */
	public Date getModificationDate() {
		return modificationDate;
	}

	/**
	 * Sets the modification date.
	 * 
	 * @param modificationDate the modificationDate to set
	 */
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
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
