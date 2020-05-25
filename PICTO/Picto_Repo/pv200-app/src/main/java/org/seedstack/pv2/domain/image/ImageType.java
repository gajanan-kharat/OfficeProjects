package org.seedstack.pv2.domain.image;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.pv2.domain.picto.Picto;


/**
 * The Class ImageType.
 */
@Entity
@Table(name = "PV2QTIMG")
public class ImageType extends BaseAggregateRoot<Long> {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** The image jpg. */
	@Column(name = "IMG_JPG")
	private Boolean imageJpg;

	/** The image png. */
	@Column(name = "IMG_PNG")
	private Boolean imagePng;

	/** The image AI work. */
	@Column(name = "IMG_AI_WORK")
	private Boolean imageAIWork;

	/** The image AI public. */
	@Column(name = "IMG_AI_PUBLIC")
	private Boolean imageAIPublic;

	/** The image igs. */
	@Column(name = "IMG_IGS")
	private Boolean imageIgs;

	/** The picto id. */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PIC_ID")
	private Picto pictoId;

	/**
	 * Instantiates a new image type.
	 */
	public ImageType() {

	}

	/**
	 * Instantiates a new image type.
	 *
	 * @param id the id
	 * @param imageJpg the image jpg
	 * @param imagePng the image png
	 * @param imageAIWork the image AI work
	 * @param imageAIPublic the image AI public
	 * @param imageIgs the image igs
	 * @param pictoId the picto id
	 */
	public ImageType(Long id, Boolean imageJpg, Boolean imagePng, Boolean imageAIWork, Boolean imageAIPublic, Boolean imageIgs, Picto pictoId) {
		this.id = id;
		this.imageJpg = imageJpg;
		this.imagePng = imagePng;
		this.imageAIWork = imageAIWork;
		this.imageAIPublic = imageAIPublic;
		this.imageIgs = imageIgs;
		this.pictoId = pictoId;
	}

	/**
	 * Gets the entity id.
	 *
	 * @return the id
	 */
	public Long getEntityId() {
		return id;
	}

	/**
	 * Sets the entity id.
	 *
	 * @param id the id to set
	 */
	public void setEntityId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the image jpg.
	 *
	 * @return the imageJpg
	 */
	public Boolean getImageJpg() {
		return imageJpg;
	}

	/**
	 * Sets the image jpg.
	 *
	 * @param imageJpg the imageJpg to set
	 */
	public void setImageJpg(Boolean imageJpg) {
		this.imageJpg = imageJpg;
	}

	/**
	 * Gets the image png.
	 *
	 * @return the imagePng
	 */
	public Boolean getImagePng() {
		return imagePng;
	}

	/**
	 * Sets the image png.
	 *
	 * @param imagePng the imagePng to set
	 */
	public void setImagePng(Boolean imagePng) {
		this.imagePng = imagePng;
	}

	/**
	 * Gets the image AI work.
	 *
	 * @return the imageAIWork
	 */
	public Boolean getImageAIWork() {
		return imageAIWork;
	}

	/**
	 * Sets the image AI work.
	 *
	 * @param imageAIWork the imageAIWork to set
	 */
	public void setImageAIWork(Boolean imageAIWork) {
		this.imageAIWork = imageAIWork;
	}

	/**
	 * Gets the image AI public.
	 *
	 * @return the imageAIPublic
	 */
	public Boolean getImageAIPublic() {
		return imageAIPublic;
	}

	/**
	 * Sets the image AI public.
	 *
	 * @param imageAIPublic the imageAIPublic to set
	 */
	public void setImageAIPublic(Boolean imageAIPublic) {
		this.imageAIPublic = imageAIPublic;
	}

	/**
	 * Gets the picto id.
	 *
	 * @return the pictoId
	 */
	public Picto getPictoId() {
		return pictoId;
	}

	/**
	 * Sets the picto id.
	 *
	 * @param pictoId the pictoId to set
	 */
	public void setPictoId(Picto pictoId) {
		this.pictoId = pictoId;
	}

	/**
	 * Gets the image igs.
	 *
	 * @return the imageIgs
	 */
	public Boolean getImageIgs() {
		return imageIgs;
	}

	/**
	 * Sets the image igs.
	 *
	 * @param imageIgs the imageIgs to set
	 */
	public void setImageIgs(Boolean imageIgs) {
		this.imageIgs = imageIgs;
	}

}
