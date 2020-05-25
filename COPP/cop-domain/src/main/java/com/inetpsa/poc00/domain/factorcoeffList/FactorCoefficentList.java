/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.factorcoeffList;

import java.util.ArrayList;
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
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.DomainConstants;
import com.inetpsa.poc00.domain.category.Category;
import com.inetpsa.poc00.domain.clasz.Clasz;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffApplicationType;
import com.inetpsa.poc00.domain.factorcoefficient.FactorCoefficents;
import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjectionType;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTFLS")
public class FactorCoefficentList extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long entityId;

    /** The description. */
    @Column(name = "DESCRIPTION")
    private String description;

    /** The label. */
    @Column(name = "LABEL")
    private String label;

    /** The version. */
    @Column(name = "VERSION")
    private String version;

    /** The vehicle technology set. */
    @ManyToMany
    @JoinTable(name = "COPQTMFV", joinColumns = { @JoinColumn(name = "FCLISTID") }, inverseJoinColumns = { @JoinColumn(name = "VTECHNOLOGYID") })
    private List<VehicleTechnology> vehicleTechnologySet = new ArrayList<VehicleTechnology>();

    /** The Fuel injection types. */
    @ManyToMany
    @JoinTable(name = "COPQTMFI", joinColumns = { @JoinColumn(name = "FCLISTID") }, inverseJoinColumns = { @JoinColumn(name = "FUELITID") })
    private List<FuelInjectionType> fuelInjectionTypes = new ArrayList<FuelInjectionType>();

    /** The Categories. */
    @ManyToMany
    @JoinTable(name = "COPQTMFC", joinColumns = { @JoinColumn(name = "FCLISTID") }, inverseJoinColumns = { @JoinColumn(name = "CATEGORYID") })
    private Set<Category> categories = new HashSet<Category>();

    /** The Fuels. */
    @ManyToMany
    @JoinTable(name = "COPQTMFF", joinColumns = { @JoinColumn(name = "FCLISTID") }, inverseJoinColumns = { @JoinColumn(name = "FUELID") })
    private List<Fuel> fuels = new ArrayList<Fuel>();

    /** The factor or coeffiecients. */
    @OneToMany(mappedBy = "fcList", cascade = CascadeType.ALL)
    private List<FactorCoefficents> factorOrCoeffiecients;

    /** The fc application types. */
    @ManyToMany
    @JoinTable(name = "COPQTMCA", joinColumns = { @JoinColumn(name = "FCL_ID") }, inverseJoinColumns = { @JoinColumn(name = "FCLAPPTYPE_ID") })
    private Set<FactorCoeffApplicationType> fcApplicationTypes = new HashSet<FactorCoeffApplicationType>();

    /** The classes. */
    @ManyToMany
    @JoinTable(name = "COPQTMFS", joinColumns = { @JoinColumn(name = "FCLISTID") }, inverseJoinColumns = { @JoinColumn(name = "CLASSID") })
    private Set<Clasz> classes = new HashSet<Clasz>();

    /** The emission standard. */
    @ManyToOne
    @JoinColumn(name = "EMS_ID")
    private EmissionStandard emissionStandard;
    
    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public FactorCoefficentList() {
    	super();
    }

    /**
     * Instantiates a new factor coefficent list.
     * 
     * @param label the label
     * @param description the description
     * @param version the version
     * @param fcApplicationTypes the fc application types
     */
    public FactorCoefficentList(String label, String description, String version, Set<FactorCoeffApplicationType> fcApplicationTypes) {
        this.label = label;
        this.description = description;
        this.version = version;
        this.fcApplicationTypes = fcApplicationTypes;
    }

    /**
     * Instantiates a new factor coefficent list.
     * 
     * @param label the label
     * @param description the description
     * @param version the version
     */
    public FactorCoefficentList(String label, String description, String version) {
        this.label = label;
        this.description = description;
        this.version = version;
    }

    /**
     * Gets the vehicle technology set.
     * 
     * @return the vehicle technology set
     */
    public List<VehicleTechnology> getVehicleTechnologySet() {
        return vehicleTechnologySet;
    }

    /**
     * Sets the vehicle technology set.
     * 
     * @param vehicleTechnologySet the new vehicle technology set
     */
    public void setVehicleTechnologySet(List<VehicleTechnology> vehicleTechnologySet) {
        this.vehicleTechnologySet = vehicleTechnologySet;
    }

    /**
     * Gets the fuel injection types.
     * 
     * @return the fuel injection types
     */
    public List<FuelInjectionType> getFuelInjectionTypes() {
        return fuelInjectionTypes;
    }

    /**
     * Sets the fuel injection types.
     * 
     * @param fuelInjectionTypes the new fuel injection types
     */
    public void setFuelInjectionTypes(List<FuelInjectionType> fuelInjectionTypes) {
        this.fuelInjectionTypes = fuelInjectionTypes;
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
     * @param set the new categories
     */
    public void setCategories(Set<Category> set) {
        this.categories = set;
    }

    /**
     * Gets the fuels.
     * 
     * @return the fuels
     */
    public List<Fuel> getFuels() {
        return fuels;
    }

    /**
     * Sets the fuels.
     * 
     * @param fuels the new fuels
     */
    public void setFuels(List<Fuel> fuels) {
        this.fuels = fuels;
    }

    /**
     * Sets the fc application types.
     * 
     * @param fcApplicationTypes the new fc application types
     */
    public void setFcApplicationTypes(Set<FactorCoeffApplicationType> fcApplicationTypes) {
        this.fcApplicationTypes = fcApplicationTypes;
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
     * Gets the factor or coeffiecients.
     * 
     * @return the factor or coeffiecients
     */
    public List<FactorCoefficents> getFactorOrCoeffiecients() {
        return factorOrCoeffiecients;
    }

    /**
     * Sets the factor or coeffiecients.
     * 
     * @param factorOrCoeffiecients the new factor or coeffiecients
     */
    public void setFactorOrCoeffiecients(List<FactorCoefficents> factorOrCoeffiecients) {
        this.factorOrCoeffiecients = factorOrCoeffiecients;
    }

    /**
     * Sets the fc application type.
     * 
     * @param fcApplicationTypes the new fc application type
     */
    public void setFcApplicationType(Set<FactorCoeffApplicationType> fcApplicationTypes) {
        this.fcApplicationTypes = fcApplicationTypes;
    }

    /**
     * Gets the fc application types.
     * 
     * @return the fc application types
     */
    public Set<FactorCoeffApplicationType> getFcApplicationTypes() {
        return this.fcApplicationTypes;
    }

    /**
     * Gets the emission standard.
     * 
     * @return the emission standard
     */
    public EmissionStandard getEmissionStandard() {
        return this.emissionStandard;
    }

    /**
     * Sets the emission standard.
     * 
     * @param emissionStandard the new emission standard
     */
    public void setEmissionStandard(EmissionStandard emissionStandard) {
        this.emissionStandard = emissionStandard;
    }

    /**
     * Gets the classes.
     * 
     * @return the classes
     */
    public Set<Clasz> getClasses() {
        return classes;
    }

    /**
     * Sets the classes.
     * 
     * @param classes the new classes
     */
    public void setClasses(Set<Clasz> classes) {
        this.classes = classes;
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
