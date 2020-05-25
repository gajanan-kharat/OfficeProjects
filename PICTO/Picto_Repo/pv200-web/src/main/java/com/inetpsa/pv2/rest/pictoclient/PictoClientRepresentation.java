package com.inetpsa.pv2.rest.pictoclient;

import java.util.Date;

import com.inetpsa.pv2.rest.picto.PictoRepresentation;
import com.inetpsa.pv2.rest.user.UserRepresentation;


/**
 * The Class PictoClientRepresentation.
 */
public class PictoClientRepresentation {

	/** The id. */
	private Long id;
	
	/** The download date. */
	private Date downloadDate;
	
	/** The download flag. */
	private byte downloadFlag;
	
	/** The picto id. */
	private PictoRepresentation pictoId;
	
	/** The user id. */
	private UserRepresentation userId;
	
	/** The is open local img. */
	private boolean isOpenLocalImg;

	/**
	 * Instantiates a new picto client representation.
	 */
	public PictoClientRepresentation() {

	}

	/**
	 * Instantiates a new picto client representation.
	 *
	 * @param id the id
	 * @param downloadDate the download date
	 * @param downloadFlag the download flag
	 * @param pictoId the picto id
	 * @param userId the user id
	 * @param isOpenLocalImg the is open local img
	 */
	public PictoClientRepresentation(Long id, Date downloadDate, byte downloadFlag, PictoRepresentation pictoId, UserRepresentation userId, boolean isOpenLocalImg) {
		this.id = id;
		this.downloadDate = downloadDate;
		this.downloadFlag = downloadFlag;
		this.pictoId = pictoId;
		this.userId = userId;
		this.isOpenLocalImg = isOpenLocalImg;

	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the download date.
	 *
	 * @return the downloadDate
	 */
	public Date getDownloadDate() {
		return downloadDate;
	}

	/**
	 * Sets the download date.
	 *
	 * @param downloadDate the downloadDate to set
	 */
	public void setDownloadDate(Date downloadDate) {
		this.downloadDate = downloadDate;
	}

	/**
	 * Gets the download flag.
	 *
	 * @return the downloadFlag
	 */
	public byte getDownloadFlag() {
		return downloadFlag;
	}

	/**
	 * Sets the download flag.
	 *
	 * @param downloadFlag the downloadFlag to set
	 */
	public void setDownloadFlag(byte downloadFlag) {
		this.downloadFlag = downloadFlag;
	}

	/**
	 * Gets the picto id.
	 *
	 * @return the pictoId
	 */
	public PictoRepresentation getPictoId() {
		return pictoId;
	}

	/**
	 * Sets the picto id.
	 *
	 * @param pictoId the pictoId to set
	 */
	public void setPictoId(PictoRepresentation pictoId) {
		this.pictoId = pictoId;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the userId
	 */
	public UserRepresentation getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the userId to set
	 */
	public void setUserId(UserRepresentation userId) {
		this.userId = userId;
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
