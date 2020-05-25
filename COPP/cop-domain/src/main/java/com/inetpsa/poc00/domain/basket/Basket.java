package com.inetpsa.poc00.domain.basket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.user.User;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * The Class Basket.
 */
@Entity
@Table(name = "COPQTBKT")
public class Basket extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long entityId;

    /** The user. */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    /** The vehicles. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "basket")
    private List<VehicleFile> vehicleFiles = new ArrayList<>();

    /** The creation date. */
    @Column(name = "CREATION_DATE")
    private Date creationDate = new Date();

    /** The update date. */
    @Column(name = "UPDATE_DATE")
    private Date updateDate = new Date();

    /**
     * Gets the entity id.
     * 
     * @return the entityId
     */
    @Override
    public Long getEntityId() {
        return entityId;
    }

    /**
     * Sets the entity id.
     * 
     * @param entityId the entityId to set
     */
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    /**
     * Gets the user.
     * 
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user.
     * 
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
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
     * Gets the update date.
     * 
     * @return the updateDate
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * Sets the last update.
     */
    @PreUpdate
    public void setLastUpdate() {
        this.updateDate = new Date();
    }

    /**
     * Gets the vehicle files.
     *
     * @return the vehicle files
     */
    public List<VehicleFile> getVehicleFiles() {
        return vehicleFiles;
    }

    /**
     * Sets the vehicle files.
     *
     * @param vehicleFiles the new vehicle files
     */
    public void setVehicleFiles(List<VehicleFile> vehicleFiles) {
        this.vehicleFiles = vehicleFiles;
    }

}
