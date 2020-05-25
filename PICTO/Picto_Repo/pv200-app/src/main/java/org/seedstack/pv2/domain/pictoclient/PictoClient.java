package org.seedstack.pv2.domain.pictoclient;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.pv2.domain.picto.Picto;
import org.seedstack.pv2.domain.user.User;


/**
 * The persistent class for the pv2qtdis database table.
 */
@Entity
@Table(name = "PV2QTDIS")
public class PictoClient extends BaseAggregateRoot<Long> {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    /** The download date. */
    @Temporal(TemporalType.DATE)
    @Column(name = "DOWNLOAD_DATE")
    private Date downloadDate;

    /** The download flag. */
    @Column(name = "DOWNLOAD_FLAG")
    private byte downloadFlag;

    /** The is open local img. */
    @Column(name = "IS_OPEN_LOCAL_IMG")
    private boolean isOpenLocalImg;


    /** The picto id. */
    @ManyToOne
    @JoinColumn(name = "PIC_ID")
    private Picto pictoId;


    /** The user id. */
    @ManyToOne
    @JoinColumn(name = "USR_ID")
    private User userId;

    /**
     * Instantiates a new picto client.
     */
    public PictoClient() {
    }

    /**
     * Gets the entity id.
     *
     * @return the pictoId
     */
    public Long getEntityId() {
        return this.id;
    }

    /**
     * Sets the entity id.
     *
     * @param id the new entity id
     */
    public void setEntityId(Long id) {
        this.id = id;
    }

    /**
     * Gets the download date.
     *
     * @return the download date
     */
    public Date getDownloadDate() {
        return this.downloadDate;
    }

    /**
     * Sets the download date.
     *
     * @param downloadDate the new download date
     */
    public void setDownloadDate(Date downloadDate) {
        this.downloadDate = downloadDate;
    }

    /**
     * Gets the download flag.
     *
     * @return the download flag
     */
    public byte getDownloadFlag() {
        return this.downloadFlag;
    }

    /**
     * Sets the download flag.
     *
     * @param downloadFlag the new download flag
     */
    public void setDownloadFlag(byte downloadFlag) {
        this.downloadFlag = downloadFlag;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public User getUserId() {
        return this.userId;
    }

    /**
     * Sets the user id.
     *
     * @param user the new user id
     */
    public void setUserId(User user) {
        this.userId = user;
    }

    /**
     * Gets the picto.
     *
     * @return the picto
     */
    public Picto getPicto() {
        return this.pictoId;
    }

    /**
     * Sets the picto.
     *
     * @param picto the new picto
     */
    public void setPicto(Picto picto) {
        this.pictoId = picto;
    }

    /**
     * Getter isOpenLocalImg.
     *
     * @return the isOpenLocalImg
     */
    public boolean getIsOpenLocalImg() {
        return isOpenLocalImg;
    }

    /**
     * Setter isOpenLocalImg.
     *
     * @param isOpenLocalImg the isOpenLocalImg to set
     */
    public void setIsOpenLocalImg(boolean isOpenLocalImg) {
        this.isOpenLocalImg = isOpenLocalImg;
    }

}