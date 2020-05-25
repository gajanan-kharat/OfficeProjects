package org.seedstack.pv2.domain.pictofamily;

import org.seedstack.business.domain.BaseFactory;
import org.seedstack.pv2.domain.category.Category;
import org.seedstack.pv2.domain.color.Color;
import org.seedstack.pv2.domain.type.Type;

/**
 * PictoFamily Factory implementation.
 */

public class PictoFamilyFactoryDefault extends BaseFactory<PictoFamily>
		implements PictoFamilyFactory {

	@Override
	public PictoFamily createPictoFamily(Long id, String referenceNum,
			String name, String informationType, String informationLabelFR,
			String informationLabelEN, String validationLevel,
			String adminInfo, String functionFR, String functionEN,
			Type typeId, String refCharte, Boolean isCommand,
			String commandInformation, Color witnessActive, Color witnessAlert,
			Color witnessFailure, String rhnInfoFR, String rhnInfoEN,
			Category categoryId, String keywordFR, String keywordEN) {

		return new PictoFamily(id, referenceNum, name, informationType,
				informationLabelFR, informationLabelEN, validationLevel,
				adminInfo, functionFR, functionEN, typeId, refCharte,
				isCommand, commandInformation, witnessActive, witnessAlert,
				witnessFailure, rhnInfoFR, rhnInfoEN, categoryId, keywordFR,
				keywordEN);

	}
}
