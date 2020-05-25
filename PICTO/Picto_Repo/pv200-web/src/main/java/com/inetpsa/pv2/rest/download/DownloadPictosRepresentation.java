/*
 * Creation : May 20, 2016
 */
package com.inetpsa.pv2.rest.download;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class DownloadPictosRepresentation.
 */
public class DownloadPictosRepresentation {

	/** The image type. */
	List<String> imageType = new ArrayList<String>();
	
	/** The picto ids. */
	List<String> pictoIds = new ArrayList<String>();
	
	/** The variants. */
	List<String> variants = new ArrayList<String>();

	/**
	 * Gets the image type.
	 *
	 * @return the image type
	 */
	public List<String> getImageType() {
		return imageType;
	}

	/**
	 * Sets the image type.
	 *
	 * @param imageType the new image type
	 */
	public void setImageType(List<String> imageType) {
		this.imageType = imageType;
	}

	/**
	 * Gets the picto ids.
	 *
	 * @return the picto ids
	 */
	public List<String> getPictoIds() {
		return pictoIds;
	}

	/**
	 * Sets the picto ids.
	 *
	 * @param pictoIds the new picto ids
	 */
	public void setPictoIds(List<String> pictoIds) {
		this.pictoIds = pictoIds;
	}

	/**
	 * Gets the variants.
	 *
	 * @return the variants
	 */
	public List<String> getVariants() {
		return variants;
	}

	/**
	 * Sets the variants.
	 *
	 * @param variants the new variants
	 */
	public void setVariants(List<String> variants) {
		this.variants = variants;
	}

}
