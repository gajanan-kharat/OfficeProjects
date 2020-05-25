package org.seedstack.pv2.infrastructure.data.pictofamily;

import java.util.Iterator;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.domain.picto.Picto;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;
import org.seedstack.pv2.infrastructure.data.picto.PictoDTO;
import org.seedstack.pv2.infrastructure.data.picto.PictoDTOAssembler;

/**
 * Class : PictoFamilyDTOAssembler
 *
 */
public class PictoFamilyDTOAssembler extends BaseAssembler<PictoFamily, PictoFamilyDTO> {

	@Inject
	private PictoDTOAssembler m_pictoDTOAssembler;

	@Override
	protected void doAssembleDtoFromAggregate(PictoFamilyDTO targetDto, PictoFamily sourceAggregate) {
		targetDto.setFamilyId(sourceAggregate.getEntityId());
		for (Iterator<Picto> iterator = sourceAggregate.getPictos().iterator(); iterator.hasNext();) {
			Picto category = (Picto) iterator.next();
			PictoDTO pictoDTO = new PictoDTO();
			m_pictoDTOAssembler.assembleDtoFromAggregate(pictoDTO, category);
			targetDto.getPictos().add(pictoDTO);
		}

	}

	@Override
	public void doMergeAggregateWithDto(PictoFamily targetAggregate, PictoFamilyDTO sourceDto) {

		targetAggregate.setEntityId(sourceDto.getFamilyId());
		targetAggregate.setReferenceNum(sourceDto.getReferenceNum());
		targetAggregate.setName(sourceDto.getName());
		targetAggregate.setInformationType(sourceDto.getInformationType());
		targetAggregate.setInformationLabelEN(sourceDto.getInformationEN());
		targetAggregate.setInformationLabelFR(sourceDto.getInformationFR());
		targetAggregate.setValidationLevel(sourceDto.getValidationLevel());
		targetAggregate.setAdminInfo(sourceDto.getAdminInfo());
		targetAggregate.setFunctionEN(sourceDto.getFunctionEN());
		targetAggregate.setFunctionFR(sourceDto.getFunctionFR());
		targetAggregate.setRefCharte(sourceDto.getRefCharte());
		targetAggregate.setIsCommand(sourceDto.getCommand());
		targetAggregate.setIsRHNWitness(sourceDto.getIsRHNWitness());
		targetAggregate.setIsRHNActive(sourceDto.getIsRHNActive());
		targetAggregate.setIsRHNAlert(sourceDto.getIsRHNAlert());
		targetAggregate.setIsRHNDefault(sourceDto.getIsRHNDefault());
		targetAggregate.setRhnInfoEN(sourceDto.getRhnInfoEN());
		targetAggregate.setRhnInfoFR(sourceDto.getRhnInfoFR());
		targetAggregate.setKeywordEN(sourceDto.getKeywordEN());
		targetAggregate.setKeywordFR(sourceDto.getKeywordFR());

	}
}