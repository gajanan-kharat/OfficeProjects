package org.seedstack.pv2.domain.picto;

import java.util.Date;

import org.seedstack.business.domain.BaseFactory;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;

/**
 * Picto Factory implementation.
 */
public class PictoFactoryDefault extends BaseFactory<Picto> implements
		PictoFactory {

	@Override
	public Picto createPicto(Long id, String variantType, String pictoUrl,
			Boolean isFrontagePicto, Boolean isVisible, PictoFamily familyID,
			Date createDate, Date modifyDate, String valid,String version) {
		return new Picto(id, variantType, pictoUrl, isFrontagePicto, isVisible,
				familyID, createDate, modifyDate);
	}
}
