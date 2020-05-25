package org.seedstack.pv2.infrastructure.data.image;


import org.seedstack.business.assembler.MatchingEntityId;
import org.seedstack.business.assembler.MatchingFactoryParameter;
import org.seedstack.pv2.domain.picto.Picto;


/**
 * The Class ImageTypeDTO.
 */
public class ImageTypeDTO {
	
	/** The id. */
	private Long id;
	
	/** The image jpg. */
	private Boolean imageJpg;
	
	/** The image png. */
	private Boolean imagePng;
	
	/** The image AI work. */
	private Boolean imageAIWork;
	
	/** The image AI public. */
	private Boolean imageAIPublic;
	
	/** The image igs. */
	private Boolean imageIgs;
	
	/** The picto id. */
	private Picto pictoId;
	
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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Gets the image jpg.
	 *
	 * @return the imageJpg
	 */
	@MatchingFactoryParameter(index = 1)
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
	@MatchingFactoryParameter(index = 2)
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
	@MatchingFactoryParameter(index = 3)
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
	@MatchingFactoryParameter(index = 4)
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
	 * Gets the image igs.
	 *
	 * @return the imageIgs
	 */
	@MatchingFactoryParameter(index = 5)
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
	
	/**
	 * Gets the picto id.
	 *
	 * @return the pictoId
	 */
	@MatchingFactoryParameter(index = 6)
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

}
