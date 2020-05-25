/*
 * Creation : May 30, 2016
 */
package org.seedstack.pv2.domain.notificationContrib;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;
import org.seedstack.pv2.domain.user.User;


/**
 * The Class NotificationContrib.
 */
@Entity
@Table(name = "PV2QTNTF")
public class NotificationContrib extends BaseAggregateRoot<Long> {
    
    /** The entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long entityId;
    
    /** The contrib id. */
    @ManyToOne
    @JoinColumn(name = "USR_ID")
    private User contribId;

    
	/** Picto family Id. */
	@ManyToOne
    @JoinColumn(name = "PFM_ID")
    private PictoFamily familyID;

    /**
     * Getter familyID.
     *
     * @return the familyID
     */
    public PictoFamily getFamilyID() {
        return familyID;
    }

    /**
     * Setter familyID.
     *
     * @param familyID the familyID to set
     */
    public void setFamilyID(PictoFamily familyID) {
        this.familyID = familyID;
    }

    /**
     * Gets the entity id.
     *
     * @return the id
     */
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
     * Gets the user id.
     *
     * @return the user id
     */
    public User getUserId() {
        return this.contribId;
    }

    /**
     * Sets the user id.
     *
     * @param user the new user id
     */
    public void setUserId(User user) {
        this.contribId = user;
    }
}
