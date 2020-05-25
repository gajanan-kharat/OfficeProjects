/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.regulationgroup;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.DomainConstants;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCL;
import com.inetpsa.poc00.domain.statisticalcalculationrule.StatisticalCalculationRule;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;
import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalArea;
import com.inetpsa.poc00.domain.wltpvaluedlowhighdata.WLTPVLowHighData;

/**
 * The RegulationGroup aggregate root.
 */

@Entity
@Table(name = "COPQTTRG")
public class RegulationGroup extends BaseAggregateRoot<Long> {

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

	/** The creation date. */
	@Column(name = "CREATION_DATE")
	private Date creationDate;

	/** The modification date. */
	@Column(name = "MODIFICATION_DATE")
	private Date modificationDate;
	
	/** The technical groups. */
	@OneToMany(mappedBy = "regulationGroup", targetEntity = TechnicalGroup.class)
	private Set<TechnicalGroup> technicalGroups;

	/** The rg valued es dep TCL. */
	@OneToMany(mappedBy = "regulationGroup", cascade = CascadeType.ALL)
	private List<RGValuedESDependentTCL> rgValuedEsDepTCL;

	/** The wltp V low high data. */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "WLTP_ID")
	private WLTPVLowHighData wltpVLowHighData;

	/** The type approval area. */
	@ManyToOne
	@JoinColumn(name = "AREA_ID")
	private TypeApprovalArea typeApprovalArea;

	/** The status. */
	@ManyToOne
	@JoinColumn(name = "STATUS_ID")
	private Status regulationgroupstatus;
	

	/** The emission standardfor rg. */
	@ManyToOne
	@JoinColumn(name = "EMISSION_ID")
	private EmissionStandard emissionStandardforRg;

	/** The statistical calculation rule. */
	@ManyToOne
	@JoinColumn(name = "Statistical_ID")
	private StatisticalCalculationRule statisticalCalculationRule;
	
	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public RegulationGroup() {
		super();
	}

	/**
	 * Instantiates a new regulation group.
	 *
	 * @param regulationgrp the regulationgrp
	 */
	public RegulationGroup(RegulationGroup regulationgrp) {
		super();
		this.entityId = regulationgrp.entityId;
		this.description = regulationgrp.description;
		this.label = regulationgrp.label;
		this.version = regulationgrp.version;
		this.creationDate = regulationgrp.creationDate;
		this.modificationDate = regulationgrp.modificationDate;
		this.technicalGroups = regulationgrp.technicalGroups;
		this.typeApprovalArea = regulationgrp.typeApprovalArea;
		this.regulationgroupstatus = regulationgrp.regulationgroupstatus;
		this.emissionStandardforRg = regulationgrp.emissionStandardforRg;
		this.rgValuedEsDepTCL = regulationgrp.rgValuedEsDepTCL;
		this.wltpVLowHighData = regulationgrp.wltpVLowHighData;

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
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the technical groups.
	 * 
	 * @return the technicalGroups
	 */
	public Set<TechnicalGroup> getTechnicalGroups() {
		return technicalGroups;
	}

	/**
	 * Sets the technical groups.
	 * 
	 * @param technicalGroups the technicalGroups to set
	 */
	public void setTechnicalGroups(Set<TechnicalGroup> technicalGroups) {
		this.technicalGroups = technicalGroups;
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
	 * Gets the version string.
	 * 
	 * @return the version string
	 */
	public String getVersionString() {
		return version;
	}

	/**
	 * Sets the version string.
	 * 
	 * @param version the new version string
	 */
	public void setVersionString(String version) {
		this.version = version;
	}

	/**
	 * Gets the type approval area.
	 * 
	 * @return the type approval area
	 */
	public TypeApprovalArea getTypeApprovalArea() {
		return typeApprovalArea;
	}

	/**
	 * Sets the type approval area.
	 * 
	 * @param typeApprovalArea the new type approval area
	 */
	public void setTypeApprovalArea(TypeApprovalArea typeApprovalArea) {
		this.typeApprovalArea = typeApprovalArea;
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
	 * Gets the regulationgroupstatus.
	 *
	 * @return the regulationgroupstatus
	 */
	public Status getRegulationgroupstatus() {
		return regulationgroupstatus;
	}

	/**
	 * Sets the regulationgroupstatus.
	 *
	 * @param regulationgroupstatus the regulationgroupstatus to set
	 */
	public void setRegulationgroupstatus(Status regulationgroupstatus) {
		this.regulationgroupstatus = regulationgroupstatus;
	}

	/**
	 * Gets the emission standardfor rg.
	 *
	 * @return the emissionStandardforRg
	 */
	public EmissionStandard getEmissionStandardforRg() {
		return emissionStandardforRg;
	}

	/**
	 * Sets the emission standardfor rg.
	 *
	 * @param emissionStandardforRg the emissionStandardforRg to set
	 */
	public void setEmissionStandardforRg(EmissionStandard emissionStandardforRg) {
		this.emissionStandardforRg = emissionStandardforRg;
	}

	/**
	 * Gets the rg valued es dep TCL.
	 *
	 * @return the rg valued es dep TCL
	 */
	public List<RGValuedESDependentTCL> getRgValuedEsDepTCL() {
		return rgValuedEsDepTCL;
	}

	/**
	 * Sets the rg valued es dep TCL.
	 *
	 * @param rgValuedEsDepTCL the new rg valued es dep TCL
	 */
	public void setRgValuedEsDepTCL(List<RGValuedESDependentTCL> rgValuedEsDepTCL) {
		this.rgValuedEsDepTCL = rgValuedEsDepTCL;
	}

	/**
	 * Gets the wltp V low high data.
	 *
	 * @return the wltp V low high data
	 */
	public WLTPVLowHighData getWltpVLowHighData() {
		return wltpVLowHighData;
	}

	/**
	 * Sets the wltp V low high data.
	 *
	 * @param wltpVLowHighData the new wltp V low high data
	 */
	public void setWltpVLowHighData(WLTPVLowHighData wltpVLowHighData) {
		this.wltpVLowHighData = wltpVLowHighData;
	}

	/**
	 * Gets the statistical calculation rule.
	 *
	 * @return the statistical calculation rule
	 */
	public StatisticalCalculationRule getStatisticalCalculationRule() {
		return statisticalCalculationRule;
	}

	/**
	 * Sets the statistical calculation rule.
	 *
	 * @param statisticalCalculationRule the new statistical calculation rule
	 */
	public void setStatisticalCalculationRule(StatisticalCalculationRule statisticalCalculationRule) {
		this.statisticalCalculationRule = statisticalCalculationRule;
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
