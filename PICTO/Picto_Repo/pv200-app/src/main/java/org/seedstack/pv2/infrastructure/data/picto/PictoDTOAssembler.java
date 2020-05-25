package org.seedstack.pv2.infrastructure.data.picto;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.domain.picto.Picto;
import org.seedstack.pv2.infrastructure.data.pictofamily.PictoFamilyDTO;


/**
 * The Class PictoDTOAssembler.
 */
public class PictoDTOAssembler extends BaseAssembler<Picto, PictoDTO> {

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(PictoDTO targetDto, Picto sourceAggregate) {
		targetDto.setId(sourceAggregate.getEntityId());
		PictoFamilyDTO pictoFamilyDTO = new PictoFamilyDTO();
		targetDto.setFamilyID(pictoFamilyDTO);
	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(Picto targetAggregate, PictoDTO sourceDto) {
		targetAggregate.setEntityId(sourceDto.getId());
		targetAggregate.setVariantType(sourceDto.getVariantType());
		targetAggregate.setPictoUrl(sourceDto.getPictoUrl());
		targetAggregate.setIsFrontagePicto(sourceDto.getIsFrontagePicto());
		targetAggregate.setIsVisible(sourceDto.getIsVisible());
		targetAggregate.setCreateDate(sourceDto.getCreateDate());
		targetAggregate.setModifyDate(sourceDto.getModifyDate());
		targetAggregate.setImageLocation(sourceDto.getImageLocation());
		targetAggregate.setVersion(sourceDto.getVersion());

	}
}