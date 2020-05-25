package org.seedstack.pv2.infrastructure.data.image;


import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.domain.image.ImageType;


/**
 * The Class ImageTypeDTOAssembler.
 */
public class ImageTypeDTOAssembler extends BaseAssembler<ImageType, ImageTypeDTO>{	


	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(ImageTypeDTO targetDto,
			ImageType sourceAggregate) {
		targetDto.setId(sourceAggregate.getEntityId());
		targetDto.setImageJpg(sourceAggregate.getImageJpg());
		targetDto.setImagePng(sourceAggregate.getImagePng());
		targetDto.setImageAIPublic(sourceAggregate.getImageAIPublic());
		targetDto.setImageAIWork(sourceAggregate.getImageAIWork());
		targetDto.setImageIgs(sourceAggregate.getImageIgs());	
		
	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(ImageType targetAggregate,
			ImageTypeDTO sourceDto) {
		targetAggregate.setEntityId(sourceDto.getId());
		targetAggregate.setImageJpg(sourceDto.getImageJpg());
		targetAggregate.setImagePng(sourceDto.getImagePng());
		targetAggregate.setImageAIPublic(sourceDto.getImageAIPublic());
		targetAggregate.setImageAIWork(sourceDto.getImageAIWork());
		targetAggregate.setImageIgs(sourceDto.getImageIgs());
		targetAggregate.setPictoId(sourceDto.getPictoId());
		
	}

}
