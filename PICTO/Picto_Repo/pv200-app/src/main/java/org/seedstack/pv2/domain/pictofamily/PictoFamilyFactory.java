package org.seedstack.pv2.domain.pictofamily;

import org.seedstack.business.domain.GenericFactory;
import org.seedstack.pv2.domain.category.Category;
import org.seedstack.pv2.domain.color.Color;
import org.seedstack.pv2.domain.type.Type;

/**
 * Picto Family Factory interface.
 *
 */
public interface PictoFamilyFactory extends GenericFactory<PictoFamily> {

	/**
	 * factory create method
	 * 
	 * @param id
	 * @param referenceNum
	 * @param name
	 * @param informationType
	 * @param informationLabelFR
	 * @param informationLabelEN
	 * @param validationLevel
	 * @param adminInfo
	 * @param functionFR
	 * @param functionEN
	 * @param refCharte
	 * @param isCommand
	 * @param commandInformation
	 * @param witnessActive
	 * @param witnessAlert
	 * @param witnessFailure
	 * @param rhnInfoFR
	 * @param rhnInfoEN
	 * @param keywordFR
	 * @param keywordEN
	 * @return the PictoFamily
	 */
	PictoFamily createPictoFamily(Long id, String referenceNum, String name,
			String informationType, String informationLabelFR,
			String informationLabelEN, String validationLevel,
			String adminInfo, String functionFR, String functionEN,
			Type typeId, String refCharte, Boolean isCommand,
			String commandInformation, Color witnessActive, Color witnessAlert,
			Color witnessFailure, String rhnInfoFR, String rhnInfoEN,
			Category categoryId, String keywordFR, String keywordEN);

}
