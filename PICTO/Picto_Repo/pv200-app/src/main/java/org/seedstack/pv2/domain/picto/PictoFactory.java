package org.seedstack.pv2.domain.picto;

import java.util.Date;

import org.seedstack.business.domain.GenericFactory;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;

/**
 * Picto Factory interface.
 *
 */

public interface PictoFactory extends GenericFactory<Picto> {

	/**
	 * factory create method
	 * 
	 * @param id
	 * @param variantType
	 * @param pictoUrl
	 * @param isFrontagePicto
	 * @param isVisible
	 * @param familyID
	 * @param createDate
	 * @param modifyDate
	 * @return the Picto
	 */
	Picto createPicto(Long id, String variantType, String pictoUrl,
			Boolean isFrontagePicto, Boolean isVisible, PictoFamily familyID,
			Date createDate, Date modifyDate,String valid,String version);

}
