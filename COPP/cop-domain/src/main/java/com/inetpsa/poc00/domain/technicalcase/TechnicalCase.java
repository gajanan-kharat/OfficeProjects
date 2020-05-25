package com.inetpsa.poc00.domain.technicalcase;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.vehicle.Vehicle;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTTTC")
public class TechnicalCase extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long entityId;

    /** The tvv worst case. */
    @Column(name = "TVV_WORST_CASE")
    private boolean tvvWorstCase;

    /** The tvv. */
    @OneToOne
    @JoinColumn(name = "TVV_ID")
    private TVV tvv;

    /** The emission standard. */
    @OneToOne
    @JoinColumn(name = "EMS_ID")
    private EmissionStandard emissionStandard;

    /** The technical group. */
    @ManyToOne
    @JoinColumn(name = "TGROUP_ID")
    private TechnicalGroup technicalGroup;

    /** The vehicle set. */
    @OneToMany(mappedBy = "technicalCase")
    private Set<Vehicle> vehicleSet = new HashSet<Vehicle>();

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public TechnicalCase() {
    	super();
    }

    /**
     * Instantiates a new technical case.
     * 
     * @param entityId the entity id
     * @param tvvWorstCase the tvv worst case
     * @param tvv the tvv
     * @param emissionStandard the emission standard
     * @param technicalGroup the technical group
     * @param creationDate the creation date
     * @param modificationDate the modification date
     * @return the technicalGroup
     */

    public TechnicalCase(Long entityId, boolean tvvWorstCase, TVV tvv, EmissionStandard emissionStandard, TechnicalGroup technicalGroup,
            Date creationDate, Date modificationDate) {
        super();
        this.entityId = entityId;
        this.tvvWorstCase = tvvWorstCase;
        this.tvv = tvv;
        this.emissionStandard = emissionStandard;
        this.technicalGroup = technicalGroup;

    }

    /**
     * Instantiates a new technical case.
     * 
     * @param technicalCase the technical case
     */
    public TechnicalCase(TechnicalCase technicalCase) {
        this.entityId = technicalCase.entityId;
        this.tvvWorstCase = technicalCase.tvvWorstCase;
        this.tvv = technicalCase.tvv;
        this.emissionStandard = technicalCase.emissionStandard;
        this.technicalGroup = technicalCase.technicalGroup;
    }

    /**
     * Gets the tvv.
     * 
     * @return the tvv
     */
    public TVV getTvv() {
        return tvv;
    }

    /**
     * Sets the tvv.
     * 
     * @param tvv the new tvv
     */
    public void setTvv(TVV tvv) {
        this.tvv = tvv;
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

    /*
     * (non-Javadoc)
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
    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    /**
     * Gets the tvv worst case.
     * 
     * @return the tvv worst case
     */
    public boolean getTvvWorstCase() {
        return tvvWorstCase;
    }

    /**
     * Sets the tvv worst case.
     * 
     * @param tvvWorstCase the new tvv worst case
     */
    public void setTvvWorstCase(boolean tvvWorstCase) {
        this.tvvWorstCase = tvvWorstCase;
    }

    /**
     * Gets the technical group.
     * 
     * @return the technical group
     */
    public TechnicalGroup getTechnicalGroup() {
        return technicalGroup;
    }

    /**
     * Sets the technical group.
     * 
     * @param technicalGroup the new technical group
     */
    public void setTechnicalGroup(TechnicalGroup technicalGroup) {
        this.technicalGroup = technicalGroup;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.domain.BaseEntity#toString()
     */
    @Override
    public String toString() {
        return emissionStandard.getEsLabel() + DomainConstants.VERSION_SEPARATOR + emissionStandard.getVersion();
    }

    /**
     * Gets the vehicle set.
     * 
     * @return the vehicleSet
     */
    public Set<Vehicle> getVehicleSet() {
        return vehicleSet;
    }

    /**
     * Sets the vehicle set.
     * 
     * @param vehicleSet the vehicleSet to set
     */
    public void setVehicleSet(Set<Vehicle> vehicleSet) {
        this.vehicleSet = vehicleSet;
    }
}
