/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.es;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.DomainConstants;
import com.inetpsa.poc00.domain.category.Category;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDL;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjectionType;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRule;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCL;
import com.inetpsa.poc00.domain.tvvvaluedesdeptdl.TvvValuedEsDepTDL;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTAES")
public class EmissionStandard extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long entityId;

	/** The description. */
	@Column(name = "DESCRIPTION")
	private String description;

	/** The version. */
	@Column(name = "VERSION")
	private String version;

	/** The es label. */
	@Column(name = "LABEL")
	private String esLabel;

	/** The genome tvv rule. */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TVV_RULE_ID")
	private GenomeTVVRule genomeTvvRule;

	/** The vehicle technology set. */
	@ManyToMany
	@JoinTable(name = "COPQTMEV", joinColumns = { @JoinColumn(name = "EMS_ID") }, inverseJoinColumns = { @JoinColumn(name = "VTECHNOLOGY_ID") })
	private Set<VehicleTechnology> vehicleTechnologySet = new HashSet<VehicleTechnology>();

	/** The Fuel injection types. */
	@ManyToMany
	@JoinTable(name = "COPQTMEI", joinColumns = { @JoinColumn(name = "EMS_ID") }, inverseJoinColumns = { @JoinColumn(name = "FUELIT_ID") })
	private Set<FuelInjectionType> fuelInjectionTypes = new HashSet<FuelInjectionType>();

	/** The Categories. */
	@ManyToMany
	@JoinTable(name = "COPQTMEC", joinColumns = { @JoinColumn(name = "EMS_ID") }, inverseJoinColumns = { @JoinColumn(name = "CATEGORY_ID") })
	private Set<Category> categories = new HashSet<Category>();

	/** The Fuels. */
	@ManyToMany
	@JoinTable(name = "COPQTMEF", joinColumns = { @JoinColumn(name = "EMS_ID") }, inverseJoinColumns = { @JoinColumn(name = "FUEL_ID") })
	private Set<Fuel> fuels = new HashSet<Fuel>();

	/** The status. */
	@ManyToOne
	@JoinColumn(name = "STATUS_ID")
	private Status status;

	/** The emission std dep td lists. */
	@OneToMany(mappedBy = "emissionStandard", cascade = CascadeType.ALL)
	private List<EmissionStdDepTDL> emissionStdDepTDLists = new ArrayList<EmissionStdDepTDL>();

	/** The emission std dep tc lists. */
	@OneToMany(mappedBy = "emissionStandard", cascade = CascadeType.ALL)
	private List<EmissionStdDepTCL> emissionStdDepTCLists = new ArrayList<EmissionStdDepTCL>();

	/** The tvv valued es dep tdl. */
	@OneToMany(mappedBy = "emissionStandard", cascade = CascadeType.ALL)
	private List<TvvValuedEsDepTDL> tvvValuedEsDepTDL = new ArrayList<TvvValuedEsDepTDL>();

	/** The emission std dep tc lists. */
	@OneToMany(mappedBy = "emissionStandard", cascade = CascadeType.ALL)
	private List<TvvValuedEsDepTCL> tvvValuedEsDepTCL = new ArrayList<TvvValuedEsDepTCL>();

	/** The regulation grp. */
	@OneToMany(mappedBy = "emissionStandardforRg")
	private List<RegulationGroup> regulationGrp = new ArrayList<RegulationGroup>();
	/** The fcists. */
	@OneToMany(mappedBy = "emissionStandard", cascade = CascadeType.ALL)
	private List<FactorCoefficentList> fclists = new ArrayList<FactorCoefficentList>();

	/** The pg lists. */
	@OneToMany(mappedBy = "emissionStandard", cascade = CascadeType.ALL)
	private List<PollutantGasLimitList> pgLists = new ArrayList<PollutantGasLimitList>();

	/** The technical cases. */
	@OneToMany(mappedBy = "emissionStandard", cascade = CascadeType.MERGE)
	private List<TechnicalCase> technicalCases = new ArrayList<TechnicalCase>();

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public EmissionStandard() {
		// Default Constructor
	}

	/**
	 * Instantiates a new emission standard.
	 * 
	 * @param entityId the entity id
	 * @param esLabel the es label
	 * @param description the description
	 * @param version the version
	 */
	public EmissionStandard(Long entityId, String esLabel, String description, String version) {
		this.entityId = entityId;
		this.esLabel = esLabel;
		this.description = description;
		this.version = version;
	}

	/**
	 * Instantiates a new emission standard.
	 * 
	 * @param esLabel the es label
	 * @param description the description
	 * @param version the version
	 */
	public EmissionStandard(String esLabel, String description, String version) {
		this.esLabel = esLabel;
		this.description = description;
		this.version = version;
	}

	/**
	 * Sets the emission std dep tc lists.
	 * 
	 * @param emissionStdDepTCLists the new emission std dep tc lists
	 */
	public void setEmissionStdDepTCLists(List<EmissionStdDepTCL> emissionStdDepTCLists) {
		this.emissionStdDepTCLists = emissionStdDepTCLists;
	}

	/**
	 * Sets the pg lists.
	 * 
	 * @param pgLists the new pg lists
	 */
	public void setPgLists(List<PollutantGasLimitList> pgLists) {
		this.pgLists = pgLists;
	}

	/**
	 * Gets the emission std dep tc lists.
	 * 
	 * @return the emission std dep tc lists
	 */
	public List<EmissionStdDepTCL> getEmissionStdDepTCLists() {
		return emissionStdDepTCLists;
	}

	/**
	 * Sets the emission std dep td lists.
	 * 
	 * @param emissionStdDepTDLists the new emission std dep td lists
	 */
	public void setEmissionStdDepTDLists(List<EmissionStdDepTDL> emissionStdDepTDLists) {
		this.emissionStdDepTDLists = emissionStdDepTDLists;
	}

	/**
	 * Gets the categories.
	 * 
	 * @return the categories
	 */
	public Set<Category> getCategories() {
		return categories;
	}

	/**
	 * Sets the categories.
	 * 
	 * @param categories the new categories
	 */
	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	/**
	 * Gets the fuels.
	 * 
	 * @return the fuels
	 */
	public Set<Fuel> getFuels() {
		return fuels;
	}

	/**
	 * Sets the fuels.
	 * 
	 * @param fuels the new fuels
	 */
	public void setFuels(Set<Fuel> fuels) {
		this.fuels = fuels;
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
	 * Gets the es label.
	 * 
	 * @return the es label
	 */
	public String getEsLabel() {
		return esLabel;
	}

	/**
	 * Sets the es label.
	 * 
	 * @param esLabel the new es label
	 */
	public void setEsLabel(String esLabel) {
		this.esLabel = esLabel;
	}

	/**
	 * Gets the genome tvv rule.
	 * 
	 * @return the genome tvv rule
	 */
	public GenomeTVVRule getGenomeTvvRule() {
		return genomeTvvRule;

	}

	/**
	 * Sets the genome tvv rule.
	 * 
	 * @param genomeTvvRule the new genome tvv rule
	 */
	public void setGenomeTvvRule(GenomeTVVRule genomeTvvRule) {
		this.genomeTvvRule = genomeTvvRule;
	}

	/**
	 * Gets the vehicle technology set.
	 * 
	 * @return the vehicle technology set
	 */
	public Set<VehicleTechnology> getVehicleTechnologySet() {
		return vehicleTechnologySet;
	}

	/**
	 * Sets the vehicle technology set.
	 * 
	 * @param vehicleTechnologySet the new vehicle technology set
	 */
	public void setVehicleTechnologySet(Set<VehicleTechnology> vehicleTechnologySet) {
		this.vehicleTechnologySet = vehicleTechnologySet;
	}

	/**
	 * Gets the fuel injection types.
	 * 
	 * @return the fuel injection types
	 */
	public Set<FuelInjectionType> getFuelInjectionTypes() {
		return fuelInjectionTypes;
	}

	/**
	 * Sets the fuel injection types.
	 * 
	 * @param fuelInjectionTypes the new fuel injection types
	 */
	public void setFuelInjectionTypes(Set<FuelInjectionType> fuelInjectionTypes) {
		this.fuelInjectionTypes = fuelInjectionTypes;
	}

	/**
	 * Gets the emission std dep td lists.
	 * 
	 * @return the emission std dep td lists
	 */
	public List<EmissionStdDepTDL> getEmissionStdDepTDLists() {
		return emissionStdDepTDLists;
	}

	/**
	 * Gets the fcists.
	 * 
	 * @return the fcists
	 */
	public List<FactorCoefficentList> getFclists() {
		return fclists;
	}

	/**
	 * Sets the fcists.
	 * 
	 * @param fclists the new fclists
	 */
	public void setFclists(List<FactorCoefficentList> fclists) {
		this.fclists = fclists;
	}

	/**
	 * Gets the pg lists.
	 * 
	 * @return the pg lists
	 */
	public List<PollutantGasLimitList> getPgLists() {
		return pgLists;
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status the new status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Gets the technical cases.
	 * 
	 * @return the technical cases
	 */
	public List<TechnicalCase> getTechnicalCases() {
		return technicalCases;
	}

	/**
	 * Sets the technical cases.
	 * 
	 * @param technicalCases the new technical cases
	 */
	public void setTechnicalCases(List<TechnicalCase> technicalCases) {
		this.technicalCases = technicalCases;
	}

	/**
	 * Gets the tvv valued es dep tdl.
	 * 
	 * @return the tvv valued es dep tdl
	 */
	public List<TvvValuedEsDepTDL> getTvvValuedEsDepTDL() {
		return tvvValuedEsDepTDL;
	}

	/**
	 * Sets the tvv valued es dep tdl.
	 * 
	 * @param tvvValuedEsDepTDL the new tvv valued es dep tdl
	 */
	public void setTvvValuedEsDepTDL(List<TvvValuedEsDepTDL> tvvValuedEsDepTDL) {
		this.tvvValuedEsDepTDL = tvvValuedEsDepTDL;
	}

	/**
	 * Gets the tvv valued es dep tcl.
	 * 
	 * @return the tvv valued es dep tcl
	 */
	public List<TvvValuedEsDepTCL> getTvvValuedEsDepTCL() {
		return tvvValuedEsDepTCL;
	}

	/**
	 * Sets the tvv valued es dep tcl.
	 * 
	 * @param tvvValuedEsDepTCL the new tvv valued es dep tcl
	 */
	public void setTvvValuedEsDepTCL(List<TvvValuedEsDepTCL> tvvValuedEsDepTCL) {
		this.tvvValuedEsDepTCL = tvvValuedEsDepTCL;
	}

	/**
	 * Gets the regulation grp.
	 * 
	 * @return the regulationGrp
	 */
	public List<RegulationGroup> getRegulationGrp() {
		return regulationGrp;
	}

	/**
	 * Sets the regulation grp.
	 * 
	 * @param regulationGrp the regulationGrp to set
	 */
	public void setRegulationGrp(List<RegulationGroup> regulationGrp) {
		this.regulationGrp = regulationGrp;
	}

	/**
	 * to String implementation.
	 * 
	 * @return the string
	 */
	@Override
	public String toString() {
		return esLabel + DomainConstants.VERSION_SEPARATOR + version;
	}
}
