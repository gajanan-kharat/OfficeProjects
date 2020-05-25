package com.inetpsa.pv2.rest.pictofamily;

import org.seedstack.pv2.constants.PictoConstants;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;


/**
 * The Class PictoFamilyAssemblerHelper.
 */
public class PictoFamilyAssemblerHelper {

	/**
	 * Do assemble dto from aggregate.
	 *
	 * @param targetDto the p target dto
	 * @param sourceEntity the p source entity
	 */
	public static void doAssembleDtoFromAggregate(PictoFamilyRepresentation targetDto, PictoFamily sourceEntity) {
		targetDto.setFamilyId(sourceEntity.getEntityId());
		targetDto.setReferenceNum(sourceEntity.getReferenceNum());
		targetDto.setName(sourceEntity.getName());
		targetDto.setInformationTypeLabel(sourceEntity.getInformationType());
		if (PictoConstants.TYPE_VALID.equals(sourceEntity.getInformationType())) {
			EnumarationInfo info = EnumarationInfo.NOTHING;
			targetDto.setInformationType(info.getInformationCode());
		}

		if (PictoConstants.TYPE_INFO.equals(sourceEntity.getInformationType())) {
			EnumarationInfo info = EnumarationInfo.INFO;
			targetDto.setInformationType(info.getInformationCode());
		}

		if (PictoConstants.TYPE_WARNING.equals(sourceEntity.getInformationType())) {
			EnumarationInfo info = EnumarationInfo.WARN;
			targetDto.setInformationType(info.getInformationCode());
		}
		targetDto.setInformationFR(sourceEntity.getInformationLabelFR());
		targetDto.setInformationEN(sourceEntity.getInformationLabelEN());
		targetDto.setAdminInfo(sourceEntity.getAdminInfo());
		targetDto.setFunctionFR(sourceEntity.getFunctionFR());
		targetDto.setFunctionEN(sourceEntity.getFunctionEN());
		targetDto.setRefCharte(sourceEntity.getRefCharte());
		targetDto.setCommand(sourceEntity.getIsCommand());
		targetDto.setCommandInformation(sourceEntity.getCommandInformation());
		targetDto.setIsRHNWitness(sourceEntity.getIsRHNWitness());
		targetDto.setIsRHNActive(sourceEntity.getIsRHNActive());
		targetDto.setIsRHNAlert(sourceEntity.getIsRHNAlert());
		targetDto.setIsRHNDefault(sourceEntity.getIsRHNDefault());
		targetDto.setRhnInfoEN(sourceEntity.getRhnInfoEN());
		targetDto.setRhnInfoFR(sourceEntity.getRhnInfoFR());
		targetDto.setKeywordEN(sourceEntity.getKeywordEN());
		targetDto.setKeywordFR(sourceEntity.getKeywordFR());
		targetDto.setValidationLevel(sourceEntity.getValidationLevel());

	}

}
