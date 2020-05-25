package com.inetpsa.pv2.rest.imagetype;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.domain.image.ImageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ImageTypeAssembler.
 */
public class ImageTypeAssembler extends BaseAssembler<ImageType, ImageTypeRepresentation> {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(ImageTypeAssembler.class);

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Override
    public void doAssembleDtoFromAggregate(ImageTypeRepresentation targetDto, ImageType sourceEntity) {
        if (targetDto != null && sourceEntity != null) {
            try {
                targetDto.setId(sourceEntity.getEntityId());
                targetDto.setImageAIPublic(sourceEntity.getImageAIPublic());
                targetDto.setImageAIWork(sourceEntity.getImageAIWork());
                targetDto.setImageIgs(sourceEntity.getImageIgs());
                targetDto.setImageJpg(sourceEntity.getImageJpg());
                targetDto.setImagePng(sourceEntity.getImagePng());
            } catch (Exception e) {
                logger.error("Error in setting Target DTO in method doAssembleDtoFromAggregate of ImageTypeAssembler class ", e);
            }

        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
     */
    @Override
    public void doMergeAggregateWithDto(ImageType targetEntity, ImageTypeRepresentation sourceDto) {
        targetEntity.setEntityId(sourceDto.getId());
        targetEntity.setImageAIPublic(sourceDto.getImageAIPublic());
        targetEntity.setImageAIWork(sourceDto.getImageAIWork());
        targetEntity.setImageIgs(sourceDto.getImageIgs());
        targetEntity.setImageJpg(sourceDto.getImageJpg());
        targetEntity.setImagePng(sourceDto.getImagePng());
    }

}
