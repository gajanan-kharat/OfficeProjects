/*
 * Creation : Apr 6, 2016
 */
package org.seedstack.pv2.infrastructure.data.pictoclient;

import java.util.Date;

import org.seedstack.business.assembler.MatchingEntityId;
import org.seedstack.business.assembler.MatchingFactoryParameter;
import org.seedstack.pv2.domain.picto.Picto;
import org.seedstack.pv2.domain.user.User;

/**
 * The Class PictoClientDTO.
 */
public class PictoClientDTO {

    /** The id. */
    private Long id;

    /** The download date. */
    private Date downloadDate;

    /** The download flag. */
    private byte downloadFlag;

    /** The picto id. */
    private Picto pictoId;
    
    /** The user id. */
    private User userId;

    /**
     * Gets the id.
     *
     * @return the id
     */
    @MatchingEntityId
    @MatchingFactoryParameter(index = 0)
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the download date.
     *
     * @return the download date
     */
    @MatchingEntityId
    @MatchingFactoryParameter(index = 3)
    public Date getDownloadDate() {
        return downloadDate;
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
    @MatchingEntityId
    @MatchingFactoryParameter(index = 4)
    public byte getDownloadFlag() {
        return downloadFlag;
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
     * Gets the pic id.
     *
     * @return the pic id
     */
    @MatchingEntityId
    @MatchingFactoryParameter(index = 1)
    public Picto getPicId() {
        return pictoId;
    }

    /**
     * Sets the pic id.
     *
     * @param picId the new pic id
     */
    public void setPicId(Picto picId) {
        this.pictoId = picId;
    }

    /**
     * Gets the usr id.
     *
     * @return the usr id
     */
    @MatchingEntityId
    @MatchingFactoryParameter(index = 2)
    public User getUsrId() {
        return userId;
    }

    /**
     * Sets the usr id.
     *
     * @param usrId the new usr id
     */
    public void setUsrId(User usrId) {
        this.userId = usrId;
    }

}
