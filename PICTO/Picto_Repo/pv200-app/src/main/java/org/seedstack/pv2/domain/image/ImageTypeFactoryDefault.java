package org.seedstack.pv2.domain.image;

import org.seedstack.business.domain.BaseFactory;
import org.seedstack.pv2.domain.picto.Picto;


/**
 * Image Factory implementation.
 */
public class ImageTypeFactoryDefault extends BaseFactory<ImageType> implements
ImageTypeFactory {
	
	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.image.ImageTypeFactory#createImage(java.lang.Long, java.lang.Boolean, java.lang.Boolean, java.lang.Boolean, java.lang.Boolean, java.lang.Boolean, org.seedstack.pv2.domain.picto.Picto)
	 */
	@Override
	public ImageType createImage(Long id, Boolean imageJpg, Boolean imagePng,
			Boolean imageAIWork, Boolean imageAIPublic, Boolean imageIgs,
			Picto pictoId){
		return new ImageType(id, imageJpg, imagePng, imageAIWork, imageAIPublic,
				imageIgs, pictoId);
		
	}

}
