package com.inetpsa.poc00.domain.receptionfile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.user.User;

/**
 * The Class ReceptionFile.
 */
@Entity
@Table(name = "COPQTRCF")
public class ReceptionFile extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long entityId;

    /** The user. */
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User userRcp;

    /** The observation. */
    @Column(name = "OBSERVATION")
    private String observation;

    /** The reservation. */
    @Column(name = "RESERVATION")
    private String reservation;

    /** The requester. */
    @Column(name = "REQUESTER")
    private String requester;

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
     * Gets the observation.
     * 
     * @return the observation
     */
    public String getObservation() {
        return observation;
    }

    /**
     * Sets the observation.
     * 
     * @param observation the observation to set
     */
    public void setObservation(String observation) {
        this.observation = observation;
    }

    /**
     * Gets the reservation.
     * 
     * @return the reservation
     */
    public String getReservation() {
        return reservation;
    }

    /**
     * Sets the reservation.
     * 
     * @param reservation the reservation to set
     */
    public void setReservation(String reservation) {
        this.reservation = reservation;
    }

    /**
     * Gets the requester.
     * 
     * @return the requester
     */
    public String getRequester() {
        return requester;
    }

    /**
     * Sets the requester.
     * 
     * @param requester the requester to set
     */
    public void setRequester(String requester) {
        this.requester = requester;
    }

    /**
     * Gets the user rcp.
     * 
     * @return the userRcp
     */
    public User getUserRcp() {
        return userRcp;
    }

    /**
     * Sets the user rcp.
     * 
     * @param userRcp the userRcp to set
     */
    public void setUserRcp(User userRcp) {
        this.userRcp = userRcp;
    }
}
