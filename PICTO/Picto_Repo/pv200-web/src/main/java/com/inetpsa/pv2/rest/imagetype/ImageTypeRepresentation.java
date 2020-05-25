package com.inetpsa.pv2.rest.imagetype;

import com.inetpsa.pv2.rest.picto.PictoRepresentation;

/**
 * The Class ImageTypeRepresentation.
 */
public class ImageTypeRepresentation {

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
    private PictoRepresentation pictoId;

    /**
     * Instantiates a new image type representation.
     */
    public ImageTypeRepresentation() {
        // Default constructor
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

}
