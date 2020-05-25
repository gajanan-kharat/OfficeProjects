/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.tvv;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.DomainConstants;
import com.inetpsa.poc00.domain.bodywork.Bodywork;
import com.inetpsa.poc00.domain.carbrand.CarBrand;
import com.inetpsa.poc00.domain.category.Category;
import com.inetpsa.poc00.domain.clasz.Clasz;
import com.inetpsa.poc00.domain.country.Country;
import com.inetpsa.poc00.domain.engine.Engine;
import com.inetpsa.poc00.domain.factory.CarFactory;
import com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatio;
import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjectionType;
import com.inetpsa.poc00.domain.gearbox.GearBox;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamily;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.testnature.TestNature;
import com.inetpsa.poc00.domain.tvvvaluedcoastdown.TvvValuedCoastDown;
import com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCL;
import com.inetpsa.poc00.domain.tvvvaluedesdeptdl.TvvValuedEsDepTDL;
import com.inetpsa.poc00.domain.tvvvaluedfcl.TvvValuedEsDepFCL;
import com.inetpsa.poc00.domain.tvvvaluedpgl.TvvValuedEsDepPGL;
import com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTvvDepTCL;
import com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTvvDepTDL;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology;

/**
 * The TVV aggregate root.
 */

@Entity
@Table(name = "COPQTTVV")
public class TVV extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long entityId;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The version. */
	@Column(name = "VERSION")
	private String version;

	/** The validity end date. */
	@Column(name = "VALIDITY_END_DATE")
	private Date validityEndDate;

	/** The validitiy start date. */
	@Column(name = "VALIDITY_START_DATE")
	private Date validitiyStartDate;

	/** The creation date. */
	@Column(name = "CREATION_DATE")
	private Date creationDate;

	/** The modification date. */
	@Column(name = "MODIFICATION_DATE")
	private Date modificationDate;

	/** The sampling label. */
	@Column(name = "SAMPLING_LABEL")
	private String samplingLabel;

	/** The project code family. */
	@ManyToOne
	@JoinColumn(name = "PCORFAMILY_ID")
	private ProjectCodeFamily projectCodeFamily;

	/** The final reduction ratio. */
	@ManyToOne
	@JoinColumn(name = "FINALRRATIO_ID")
	private FinalReductionRatio finalReductionRatio;

	/** The gear box. */
	@ManyToOne
	@JoinColumn(name = "GEARBOX_ID")
	private GearBox gearBox;

	/** The engine. */
	@ManyToOne
	@JoinColumn(name = "ENGINE_ID")
	private Engine engine;

	/** The body work. */
	@ManyToOne
	@JoinColumn(name = "BODYWORK_ID")
	private Bodywork bodyWork;

	/** The fuel type. */
	@ManyToOne
	@JoinColumn(name = "FUEL_ID")
	private Fuel fuel;

	/** The status. */
	@ManyToOne
	@JoinColumn(name = "STATUS_ID")
	private Status status;

	/** The test nature. */
	@ManyToOne
	@JoinColumn(name = "NATURE_ID")
	private TestNature testNature;

	/** The technical case. */
	@OneToOne(mappedBy = "tvv", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TechnicalCase technicalCase;

	/** The tvv valued tvv dep tdl. */
	@OneToMany(mappedBy = "tvvObj", cascade = CascadeType.ALL)
	private List<TvvValuedTvvDepTDL> tvvValuedTvvDepTDL = new ArrayList<>();

	/** The tvv valued tvv dep tcl. */
	@OneToMany(mappedBy = "tvvObj", cascade = CascadeType.ALL)
	private List<TvvValuedTvvDepTCL> tvvValuedTvvDepTCL = new ArrayList<>();

	/** The tvv valued es dep tdl. */
	@OneToMany(mappedBy = "tvvObj", cascade = CascadeType.ALL)
	private List<TvvValuedEsDepTDL> tvvValuedEsDepTDL = new ArrayList<>();

	/** The tvv valued es dep tcl. */
	@OneToMany(mappedBy = "tvvObj", cascade = CascadeType.ALL)
	private List<TvvValuedEsDepTCL> tvvValuedEsDepTCL = new ArrayList<>();

	/** The tvv valued es dep fcl. */
	@OneToMany(mappedBy = "tvvObj", cascade = CascadeType.ALL)
	private List<TvvValuedEsDepFCL> tvvValuedEsDepFCL = new ArrayList<>();

	/** The tvv valued es dep pgl. */
	@OneToMany(mappedBy = "tvvObj", cascade = CascadeType.ALL)
	private List<TvvValuedEsDepPGL> tvvValuedEsDepPGL = new ArrayList<>();

	/** The tvv valued coast down. */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "VALUED_COASTDWON_ID")
	private TvvValuedCoastDown tvvValuedCoastDown;

	/** The category. */
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;

	/** The vehical technology. */
	@ManyToOne
	@JoinColumn(name = "VEHICLETECHNOLOGY_ID")
	private VehicleTechnology vehicalTechnology;

	/** The clasz. */
	@ManyToOne
	@JoinColumn(name = "CLASZ_ID")
	private Clasz clasz;

	/** The fuel injection type. */
	@ManyToOne
	@JoinColumn(name = "FUELINJECTIONTYPE_ID")
	private FuelInjectionType fuelInjectionType;

	/** The factory set. */
	@ManyToMany
	@JoinTable(name = "COPQTMTF", joinColumns = { @JoinColumn(name = "TVV_ID") }, inverseJoinColumns = { @JoinColumn(name = "FACTORY_ID") })
	private Set<CarFactory> factorySet = new HashSet<>();

	/** The car brand set. */
	@ManyToMany
	@JoinTable(name = "COPQTMTM", joinColumns = { @JoinColumn(name = "TVV_ID") }, inverseJoinColumns = { @JoinColumn(name = "CARMB_ID") })
	private Set<CarBrand> carBrandSet = new HashSet<>();

	/** The country set. */
	@ManyToMany
	@JoinTable(name = "COPQTMTC", joinColumns = { @JoinColumn(name = "TVV_ID") }, inverseJoinColumns = { @JoinColumn(name = "COUNTRY_ID") })
	private Set<Country> countrySet = new HashSet<>();

	/**
	 * Instantiates a new tvv.
	 */
	public TVV() {
		super();
	}

	/**
	 * Instantiates a new tvv.
	 * 
	 * @param tvvLabel the tvv label
	 */
	public TVV(String tvvLabel) {
		this.label = tvvLabel;
	}

	/**
	 * Gets the tvv valued tvv dep tcl.
	 * 
	 * @return the tvv valued tvv dep tcl
	 */
	public List<TvvValuedTvvDepTCL> getTvvValuedTvvDepTCL() {
		return tvvValuedTvvDepTCL;
	}

	/**
	 * Sets the tvv valued tvv dep tcl.
	 * 
	 * @param tvvValuedTvvDepTCL the new tvv valued tvv dep tcl
	 */
	public void setTvvValuedTvvDepTCL(List<TvvValuedTvvDepTCL> tvvValuedTvvDepTCL) {
		this.tvvValuedTvvDepTCL = tvvValuedTvvDepTCL;
	}

	/**
	 * Gets the project code family.
	 * 
	 * @return the project code family
	 */
	public ProjectCodeFamily getProjectCodeFamily() {
		return projectCodeFamily;
	}

	/**
	 * Sets the project code family.
	 * 
	 * @param projectCodeFamily the new project code family
	 */
	public void setProjectCodeFamily(ProjectCodeFamily projectCodeFamily) {
		this.projectCodeFamily = projectCodeFamily;
	}

	/**
	 * Gets the final reduction ratio.
	 * 
	 * @return the final reduction ratio
	 */
	public FinalReductionRatio getFinalReductionRatio() {
		return finalReductionRatio;
	}

	/**
	 * Sets the final reduction ratio.
	 * 
	 * @param finalRedusctionRatio the new final reduction ratio
	 */
	public void setFinalReductionRatio(FinalReductionRatio finalRedusctionRatio) {
		this.finalReductionRatio = finalRedusctionRatio;
	}

	/**
	 * Gets the gear box.
	 * 
	 * @return the gear box
	 */
	public GearBox getGearBox() {
		return gearBox;
	}

	/**
	 * Sets the gear box.
	 * 
	 * @param gearBox the new gear box
	 */
	public void setGearBox(GearBox gearBox) {
		this.gearBox = gearBox;
	}

	/**
	 * Gets the engine.
	 * 
	 * @return the engine
	 */
	public Engine getEngine() {
		return engine;
	}

	/**
	 * Sets the engine.
	 * 
	 * @param engine the new engine
	 */
	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	/**
	 * Gets the body work.
	 * 
	 * @return the body work
	 */
	public Bodywork getBodyWork() {
		return bodyWork;
	}

	/**
	 * Sets the body work.
	 * 
	 * @param bodyWork the new body work
	 */
	public void setBodyWork(Bodywork bodyWork) {
		this.bodyWork = bodyWork;
	}

	/**
	 * Gets the fuel type.
	 * 
	 * @return the fuel type
	 */
	public Fuel getFuel() {
		return fuel;
	}

	/**
	 * Sets the fuel type.
	 * 
	 * @param fuel the new fuel
	 */
	public void setFuel(Fuel fuel) {
		this.fuel = fuel;
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
	 * Gets the validity end date.
	 * 
	 * @return the validity end date
	 */
	public Date getValidityEndDate() {
		return validityEndDate;
	}

	/**
	 * Sets the validity end date.
	 * 
	 * @param validityEndDate the new validity end date
	 */
	public void setValidityEndDate(Date validityEndDate) {
		this.validityEndDate = validityEndDate;
	}

	/**
	 * Gets the validitiy start date.
	 * 
	 * @return the validitiy start date
	 */
	public Date getValiditiyStartDate() {
		return validitiyStartDate;
	}

	/**
	 * Sets the validitiy start date.
	 * 
	 * @param validitiyStartDate the new validitiy start date
	 */
	public void setValiditiyStartDate(Date validitiyStartDate) {
		this.validitiyStartDate = validitiyStartDate;
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
	 * Gets the car brand set.
	 * 
	 * @return the car brand set
	 */
	public Set<CarBrand> getCarBrandSet() {
		return carBrandSet;
	}

	/**
	 * Sets the car brand set.
	 * 
	 * @param carBrandSet the new car brand set
	 */
	public void setCarBrandSet(Set<CarBrand> carBrandSet) {
		this.carBrandSet = carBrandSet;
	}

	/**
	 * Gets the technical case list.
	 * 
	 * @return the technical case list
	 */
	public TechnicalCase getTechnicalCase() {
		return technicalCase;
	}

	/**
	 * Sets the technical case list.
	 * 
	 * @param technicalCase the new technical case
	 */
	public void setTechnicalCase(TechnicalCase technicalCase) {
		this.technicalCase = technicalCase;
	}

	/**
	 * Gets the tvv valued tvv dep tdl.
	 * 
	 * @return the tvv valued tvv dep tdl
	 */
	public List<TvvValuedTvvDepTDL> getTvvValuedTvvDepTDL() {
		return tvvValuedTvvDepTDL;
	}

	/**
	 * Sets the tvv valued tvv dep tdl.
	 * 
	 * @param tvvValuedTvvDepTDL the new tvv valued tvv dep tdl
	 */
	public void setTvvValuedTvvDepTDL(List<TvvValuedTvvDepTDL> tvvValuedTvvDepTDL) {
		this.tvvValuedTvvDepTDL = tvvValuedTvvDepTDL;
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
	 * Gets the tvv valued es dep fcl.
	 * 
	 * @return the tvv valued es dep fcl
	 */
	public List<TvvValuedEsDepFCL> getTvvValuedEsDepFCL() {
		return tvvValuedEsDepFCL;
	}

	/**
	 * Sets the tvv valued es dep fcl.
	 * 
	 * @param tvvValuedEsDepFCL the new tvv valued es dep fcl
	 */
	public void setTvvValuedEsDepFCL(List<TvvValuedEsDepFCL> tvvValuedEsDepFCL) {
		this.tvvValuedEsDepFCL = tvvValuedEsDepFCL;
	}

	/**
	 * Gets the tvv valued es dep pgl.
	 * 
	 * @return the tvv valued es dep pgl
	 */
	public List<TvvValuedEsDepPGL> getTvvValuedEsDepPGL() {
		return tvvValuedEsDepPGL;
	}

	/**
	 * Sets the tvv valued es dep pgl.
	 * 
	 * @param tvvValuedEsDepPGL the new tvv valued es dep pgl
	 */
	public void setTvvValuedEsDepPGL(List<TvvValuedEsDepPGL> tvvValuedEsDepPGL) {
		this.tvvValuedEsDepPGL = tvvValuedEsDepPGL;
	}

	/**
	 * Gets the tvv valued coast down.
	 * 
	 * @return the tvv valued coast down
	 */
	public TvvValuedCoastDown getTvvValuedCoastDown() {
		return tvvValuedCoastDown;
	}

	/**
	 * Gets the category.
	 * 
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * Sets the category.
	 * 
	 * @param category the new category
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * Gets the vehical technology.
	 * 
	 * @return the vehical technology
	 */
	public VehicleTechnology getVehicalTechnology() {
		return vehicalTechnology;
	}

	/**
	 * Sets the vehical technology.
	 * 
	 * @param vehicalTechnology the new vehical technology
	 */
	public void setVehicalTechnology(VehicleTechnology vehicalTechnology) {
		this.vehicalTechnology = vehicalTechnology;
	}

	/**
	 * Sets the tvv valued coast down.
	 * 
	 * @param tvvValuedCoastDown the new tvv valued coast down
	 */
	public void setTvvValuedCoastDown(TvvValuedCoastDown tvvValuedCoastDown) {
		this.tvvValuedCoastDown = tvvValuedCoastDown;
	}

	/**
	 * Gets the creation date.
	 * 
	 * @return the creation date
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 * 
	 * @param creationDate the new creation date
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Gets the modification date.
	 * 
	 * @return the modification date
	 */
	public Date getModificationDate() {
		return modificationDate;
	}

	/**
	 * Sets the modification date.
	 * 
	 * @param modificationDate the new modification date
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

	/**
	 * Gets the test nature.
	 * 
	 * @return the test nature
	 */
	public TestNature getTestNature() {
		return testNature;
	}

	/**
	 * Sets the test nature.
	 * 
	 * @param testNature the new test nature
	 */
	public void setTestNature(TestNature testNature) {
		this.testNature = testNature;
	}

	/**
	 * Gets the factory set.
	 * 
	 * @return the factory set
	 */
	public Set<CarFactory> getFactorySet() {
		return factorySet;
	}

	/**
	 * Sets the factory set.
	 * 
	 * @param factorySet the new factory set
	 */
	public void setFactorySet(Set<CarFactory> factorySet) {
		this.factorySet = factorySet;
	}

	/**
	 * Gets the clasz.
	 *
	 * @return the clasz
	 */
	public Clasz getClasz() {
		return clasz;
	}

	/**
	 * Sets the clasz.
	 *
	 * @param clasz the new clasz
	 */
	public void setClasz(Clasz clasz) {
		this.clasz = clasz;
	}

	/**
	 * Gets the fuel injection type.
	 *
	 * @return the fuel injection type
	 */
	public FuelInjectionType getFuelInjectionType() {
		return fuelInjectionType;
	}

	/**
	 * Sets the fuel injection type.
	 *
	 * @param fuelInjectionType the new fuel injection type
	 */
	public void setFuelInjectionType(FuelInjectionType fuelInjectionType) {
		this.fuelInjectionType = fuelInjectionType;
	}

	/**
	 * Gets the country set.
	 *
	 * @return the country set
	 */
	public Set<Country> getCountrySet() {
		return countrySet;
	}

	/**
	 * Sets the country set.
	 *
	 * @param countrySet the new country set
	 */
	public void setCountrySet(Set<Country> countrySet) {
		this.countrySet = countrySet;
	}

	/**
	 * Getter samplingLabel.
	 *
	 * @return the samplingLabel
	 */
	public String getSamplingLabel() {
		return samplingLabel;
	}

	/**
	 * Setter samplingLabel.
	 *
	 * @param samplingLabel the samplingLabel to set
	 */
	public void setSamplingLabel(String samplingLabel) {
		this.samplingLabel = samplingLabel;
	}

}
