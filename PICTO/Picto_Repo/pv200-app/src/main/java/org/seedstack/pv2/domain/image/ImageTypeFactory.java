package org.seedstack.pv2.domain.image;


import org.seedstack.business.domain.GenericFactory;
import org.seedstack.pv2.domain.picto.Picto;


/**
 * A factory for creating ImageType objects.
 */
public interface ImageTypeFactory  extends GenericFactory<ImageType> {
	
	/**
	 * factory create method.
	 *
	 * @param id the id
	 * @param imageJpg the image jpg
	 * @param imagePng the image png
	 * @param imageAIWork the image AI work
	 * @param imageAIPublic the image AI public
	 * @param imageIgs the image igs
	 * @param pictoId the picto id
	 * @return the image type
	 */
	ImageType createImage(Long id, Boolean imageJpg, Boolean imagePng,
			Boolean imageAIWork, Boolean imageAIPublic, Boolean imageIgs,
			Picto pictoId);

}



