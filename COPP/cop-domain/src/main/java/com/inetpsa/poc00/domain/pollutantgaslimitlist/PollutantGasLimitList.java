/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.pollutantgaslimitlist;

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
import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjectionType;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology;

/**
 * The PollutantGasLimitList aggregate root.
 */

@Entity
@Table(name = "COPQTPLL")
public class PollutantGasLimitList extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long entityId;

    /** The description. */
    @Column(name = "DESCRIPTION")
    private String description;

    /** The version. */
    @Column(name = "VERSION")
    private String version;

    /** The label. */
    @Column(name = "LABEL")
    private String label;

    /** The pollutant gas limit. */
    @OneToMany(mappedBy = "pollutantGasLimitList", cascade = CascadeType.ALL)
    private List<PollutantGasLimit> pollutantGasLimit;

    /** The vehicle technology set. */
    @ManyToMany
    @JoinTable(name = "COPQTMPV", joinColumns = { @JoinColumn(name = "PGLIST_ID") }, inverseJoinColumns = { @JoinColumn(name = "VTECHNOLOGY_ID") })
    private Set<VehicleTechnology> vehicleTechnologySet = new HashSet<VehicleTechnology>();

    /** The Fuel injection types. */
    @ManyToMany
    @JoinTable(name = "COPQTMPI", joinColumns = { @JoinColumn(name = "PGLIST_ID") }, inverseJoinColumns = { @JoinColumn(name = "FUELIT_ID") })
    private Set<FuelInjectionType> fuelInjectionTypes = new HashSet<FuelInjectionType>();

    /** The Categories. */
    @ManyToMany
    @JoinTable(name = "COPQTMPC", joinColumns = { @JoinColumn(name = "PGLIST_ID") }, inverseJoinColumns = { @JoinColumn(name = "CAT_ID") })
    private Set<Category> categories = new HashSet<Category>();

    /** The Fuels. */
    @ManyToMany
    @JoinTable(name = "COPQTMPF", joinColumns = { @JoinColumn(name = "PGLIST_ID") }, inverseJoinColumns = { @JoinColumn(name = "FUEL_ID") })
    private Set<Fuel> fuels = new HashSet<Fuel>();

    /** The classes. */
    @ManyToMany
    @JoinTable(name = "COPQTMPS", joinColumns = { @JoinColumn(name = "PGLIST_ID") }, inverseJoinColumns = { @JoinColumn(name = "CLASZ_ID") })
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
    public PollutantGasLimitList() {
    	super();
    }

    /**
     * Instantiates a new pollutant gas limit list.
     * 
     * @param label the label
     * @param description the description
     * @param version the version
     */
    PollutantGasLimitList(String label, String description, String version) {
        this.label = label;
        this.description = description;
        this.version = version;
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
     * Gets the emission standard.
     * 
     * @return the emission standard
     */
    public EmissionStandard getEmissionStandard() {
        return emissionStandard;
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
     * Gets the pollutant gas limit.
     * 
     * @return the pollutant gas limit
     */
    public List<PollutantGasLimit> getPollutantGasLimit() {
        return pollutantGasLimit;
    }

    /**
     * Sets the pollutant gas limit.
     * 
     * @param pollutantGasLimit the new pollutant gas limit
     */
    public void setPollutantGasLimit(List<PollutantGasLimit> pollutantGasLimit) {
        this.pollutantGasLimit = pollutantGasLimit;
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
